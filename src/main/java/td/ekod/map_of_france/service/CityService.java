package td.ekod.map_of_france.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import td.ekod.map_of_france.dto.CityDto;
import td.ekod.map_of_france.dto.SearchCriteriaDto;
import td.ekod.map_of_france.entity.City;
import td.ekod.map_of_france.repository.CityRepository;

/**
 * Service pour la gestion des villes et recherches géographiques
 */
@Service
public class CityService {
    
    private static final Logger logger = LoggerFactory.getLogger(CityService.class);
    
    @Autowired
    private CityRepository cityRepository;
    
    @Autowired
    private GeographyService geographyService;
    
    /**
     * Recherche les villes selon les critères donnés
     * @param criteria 
     * @return
     */
    public List<CityDto> searchCities(SearchCriteriaDto criteria) {
        logger.debug("Recherche de villes avec critères: lat={}, lon={}, maxCities={}, maxDistance={}, minPop={}, region={}",
                criteria.getLatitude(), criteria.getLongitude(), criteria.getMaxCities(), 
                criteria.getMaxDistance(), criteria.getMinPopulation(), criteria.getRegion());
        
        // Calculer la zone géographique approximative
        double[] boundingBox = geographyService.calculateBoundingBox(
                criteria.getLatitude(), 
                criteria.getLongitude(), 
                criteria.getMaxDistance()
        );
        
        // Récupérer les villes dans la zone
        List<City> candidates = cityRepository.findCitiesInGeographicArea(
                boundingBox[0],
                boundingBox[1],
                boundingBox[2],
                boundingBox[3],
                criteria.getMinPopulation(),
                criteria.getRegion()
        );
        
        logger.debug("Trouvé {} villes candidates dans la zone géographique", candidates.size());
        
        // Calculer les distances exactes et filtrer
        List<CityDto> results = new ArrayList<>();
        
        for (City city : candidates) {
            double distance = geographyService.calculateHaversineDistance(
                    criteria.getLatitude(), criteria.getLongitude(),
                    city.getLatitude(), city.getLongitude()
            );
            
            // Filtrer par distance maximale
            if (distance <= criteria.getMaxDistance()) {
                CityDto dto = convertToDto(city, distance);
                results.add(dto);
            }
        }
        
        // Trier par distance puis par population
        results.sort((a, b) -> {
            int distanceComparison = Double.compare(a.getDistance(), b.getDistance());
            if (distanceComparison != 0) {
                return distanceComparison;
            }
            return Integer.compare(b.getPopulation(), a.getPopulation());
        });
        
        // Limiter le nombre de résultats
        if (results.size() > criteria.getMaxCities()) {
            results = results.subList(0, criteria.getMaxCities());
        }
        
        logger.debug("Retour de {} villes après filtrage et tri", results.size());
        return results;
    }
    
    /**
     * Récupère toutes les régions disponibles
     * @return
     */
    public List<String> getAllRegions() {
        return cityRepository.findAllDistinctRegions();
    }
    
    /**
     * Récupère les statistiques par région
     * @return
     */
    public List<Object[]> getRegionStatistics() {
        return cityRepository.countCitiesByRegion();
    }
    
    /**
     * Trouve une ville par son ID
     * @param id
     * @return
     */
    public City findById(Long id) {
        return cityRepository.findById(id).orElse(null);
    }
    
    /**
     * Convertit une entité City en DTO avec distance
     * @param city
     * @param distance
     * @return
     */
    private CityDto convertToDto(City city, double distance) {
        return new CityDto(
                city.getId(),
                city.getName(),
                city.getPostalCode(),
                city.getLatitude(),
                city.getLongitude(),
                city.getPopulation(),
                city.getRegion(),
                city.getDepartment(),
                distance
        );
    }
    
    /**
     * Convertit une entité City en DTO sans distance
     * @param city
     * @return
     */
    public CityDto convertToDto(City city) {
        return convertToDto(city, 0.0);
    }
    
    /**
     * Compte le nombre total de villes
     * @return
     */
    public long getTotalCityCount() {
        return cityRepository.count();
    }
}
