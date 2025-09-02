package td.ekod.map_of_france.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import td.ekod.map_of_france.entity.City;

/**
 * Repository pour l'entité City avec requêtes personnalisées
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    
    /**
     * Trouve toutes les villes distinctes par région
     */
    @Query("SELECT DISTINCT c.region FROM City c ORDER BY c.region")
    List<String> findAllDistinctRegions();
    
    /**
     * Trouve les villes dans une zone géographique donnée
     * @param minLat
     * @param maxLat
     * @param minLon
     * @param maxLon
     * @param minPopulation
     * @param region
     * @return
     */
    @Query("""
        SELECT c FROM City c 
        WHERE c.latitude BETWEEN :minLat AND :maxLat 
        AND c.longitude BETWEEN :minLon AND :maxLon 
        AND (:minPopulation IS NULL OR c.population >= :minPopulation)
        AND (:region IS NULL OR :region = 'TOUTES' OR c.region = :region)
        ORDER BY c.population DESC
        """)
    List<City> findCitiesInGeographicArea(
            @Param("minLat") Double minLat,
            @Param("maxLat") Double maxLat,
            @Param("minLon") Double minLon,
            @Param("maxLon") Double maxLon,
            @Param("minPopulation") Integer minPopulation,
            @Param("region") String region
    );
    
    /**
     * Trouve toutes les villes avec filtres de population et région
     */
    @Query("""
        SELECT c FROM City c 
        WHERE (:minPopulation IS NULL OR c.population >= :minPopulation)
        AND (:region IS NULL OR :region = 'TOUTES' OR c.region = :region)
        ORDER BY c.population DESC
        """)
    List<City> findCitiesWithFilters(
            @Param("minPopulation") Integer minPopulation,
            @Param("region") String region
    );
    
    /**
     * Trouve les villes par région
     */
    List<City> findByRegionOrderByPopulationDesc(String region);
    
    /**
     * Compte le nombre de villes par région
     */
    @Query("SELECT c.region, COUNT(c) FROM City c GROUP BY c.region ORDER BY c.region")
    List<Object[]> countCitiesByRegion();
    
    /**
     * Recherche textuelle de villes par nom avec limite
     */
    @Query("SELECT c FROM City c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%')) ORDER BY c.population DESC")
    List<City> findByNameContainingIgnoreCaseOrderByPopulationDesc(@Param("query") String query, int limit);
    
}
