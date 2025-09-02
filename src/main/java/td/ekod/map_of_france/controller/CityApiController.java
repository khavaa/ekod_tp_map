package td.ekod.map_of_france.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import td.ekod.map_of_france.dto.CityDto;
import td.ekod.map_of_france.dto.SearchCriteriaDto;
import td.ekod.map_of_france.service.CityService;
import td.ekod.map_of_france.service.GeographyService;

/**
 * Contrôleur REST API pour la recherche de villes
 */
@RestController
@RequestMapping("/api/cities")
@CrossOrigin(origins = "*")
public class CityApiController {
    
    private static final Logger logger = LoggerFactory.getLogger(CityApiController.class);
    
    @Autowired
    private CityService cityService;
    
    @Autowired
    private GeographyService geographyService;
    
    /**
     * Recherche des villes selon les critères fournis
     * @param criteria
     * @param bindingResult 
     * @return
     */
    @PostMapping("/search")
    public ResponseEntity<?> searchCities(@Valid @RequestBody SearchCriteriaDto criteria, 
                                         BindingResult bindingResult) {
        logger.info("Requête de recherche reçue: {}", criteria);
        
        // Vérifier les erreurs de validation
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> 
                errors.put(error.getField(), error.getDefaultMessage())
            );
            logger.warn("Erreurs de validation: {}", errors);
            return ResponseEntity.badRequest().body(Map.of("errors", errors));
        }
        
        // Vérifier que les coordonnées sont en France métropolitaine
        if (!geographyService.isInFranceMetropolitaine(criteria.getLatitude(), criteria.getLongitude())) {
            logger.warn("Coordonnées hors de France: lat={}, lon={}", criteria.getLatitude(), criteria.getLongitude());
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Les coordonnées doivent être en France métropolitaine",
                "latitude", criteria.getLatitude(),
                "longitude", criteria.getLongitude()
            ));
        }
        
        try {
            List<CityDto> cities = cityService.searchCities(criteria);
            
            Map<String, Object> response = new HashMap<>();
            response.put("cities", cities);
            response.put("count", cities.size());
            response.put("criteria", criteria);
            
            logger.info("Recherche terminée, {} villes trouvées", cities.size());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Erreur lors de la recherche de villes", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Erreur interne du serveur: " + e.getMessage()));
        }
    }
    
    /**
     * Récupère toutes les régions disponibles
     * @return
     */
    @GetMapping("/regions")
    public ResponseEntity<?> getAllRegions() {
        try {
            List<String> regions = cityService.getAllRegions();
            logger.debug("Récupération de {} régions", regions.size());
            return ResponseEntity.ok(Map.of("regions", regions));
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des régions", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Erreur lors de la récupération des régions"));
        }
    }
    
    /**
     * Récupère les statistiques par région
     * @return
     */
    @GetMapping("/statistics")
    public ResponseEntity<?> getStatistics() {
        try {
            List<Object[]> regionStats = cityService.getRegionStatistics();
            long totalCities = cityService.getTotalCityCount();
            
            Map<String, Object> response = new HashMap<>();
            response.put("totalCities", totalCities);
            response.put("regionStatistics", regionStats);
            
            logger.debug("Statistiques récupérées: {} villes au total", totalCities);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des statistiques", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Erreur lors de la récupération des statistiques"));
        }
    }
    
    /**
     * Récupère une ville par son ID
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCityById(@PathVariable Long id) {
        try {
            var city = cityService.findById(id);
            if (city == null) {
                return ResponseEntity.notFound().build();
            }
            
            CityDto dto = cityService.convertToDto(city);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération de la ville {}", id, e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Erreur lors de la récupération de la ville"));
        }
    }
    
    /**
     * Endpoint de santé pour vérifier que l'API fonctionne
     * @return
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("timestamp", System.currentTimeMillis());
        status.put("service", "City API");
        status.put("scope", "France métropolitaine uniquement");
        return ResponseEntity.ok(status);
    }
}
