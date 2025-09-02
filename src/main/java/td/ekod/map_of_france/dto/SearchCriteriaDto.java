package td.ekod.map_of_france.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO pour les critères de recherche de villes
 */
public class SearchCriteriaDto {
    
    @NotNull(message = "La latitude est obligatoire")
    @DecimalMin(value = "-90.0", message = "La latitude doit être entre -90 et 90")
    @DecimalMax(value = "90.0", message = "La latitude doit être entre -90 et 90")
    private Double latitude;
    
    @NotNull(message = "La longitude est obligatoire")
    @DecimalMin(value = "-180.0", message = "La longitude doit être entre -180 et 180")
    @DecimalMax(value = "180.0", message = "La longitude doit être entre -180 et 180")
    private Double longitude;
    
    @Min(value = 1, message = "Le nombre maximum de villes doit être au moins 1")
    @Max(value = 100, message = "Le nombre maximum de villes ne peut pas dépasser 100")
    private Integer maxCities = 10;
    
    @Min(value = 1, message = "La distance maximale doit être au moins 1 km")
    @Max(value = 1000, message = "La distance maximale ne peut pas dépasser 1000 km")
    private Double maxDistance = 50.0;
    
    @Min(value = 0, message = "La population minimale doit être positive")
    private Integer minPopulation = 0;
    
    private String region; // "TOUTES" ou nom de région spécifique
    
    // Constructeurs
    public SearchCriteriaDto() {}
    
    public SearchCriteriaDto(Double latitude, Double longitude, Integer maxCities, 
                           Double maxDistance, Integer minPopulation, String region) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.maxCities = maxCities;
        this.maxDistance = maxDistance;
        this.minPopulation = minPopulation;
        this.region = region;
    }
    
    // Getters et Setters
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    
    public Integer getMaxCities() { return maxCities; }
    public void setMaxCities(Integer maxCities) { this.maxCities = maxCities; }
    
    public Double getMaxDistance() { return maxDistance; }
    public void setMaxDistance(Double maxDistance) { this.maxDistance = maxDistance; }
    
    public Integer getMinPopulation() { return minPopulation; }
    public void setMinPopulation(Integer minPopulation) { this.minPopulation = minPopulation; }
    
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
}
