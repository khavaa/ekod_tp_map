package td.ekod.map_of_france.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Entité représentant une ville française avec ses coordonnées géographiques
 */
@Entity
@Table(name = "cities")
public class City {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le nom de la ville ne peut pas être vide")
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotBlank(message = "Le code postal ne peut pas être vide")
    @Column(name = "postal_code", nullable = false)
    private String postalCode;
    
    @NotNull(message = "La latitude ne peut pas être nulle")
    @Column(name = "latitude", nullable = false)
    private Double latitude;
    
    @NotNull(message = "La longitude ne peut pas être nulle")
    @Column(name = "longitude", nullable = false)
    private Double longitude;
    
    @PositiveOrZero(message = "La population doit être positive ou nulle")
    @Column(name = "population")
    private Integer population;
    
    @NotBlank(message = "La région ne peut pas être vide")
    @Column(name = "region", nullable = false)
    private String region;
    
    @Column(name = "department")
    private String department;
    
    // Constructeurs
    public City() {}
    
    public City(String name, String postalCode, Double latitude, Double longitude, 
                Integer population, String region, String department) {
        this.name = name;
        this.postalCode = postalCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.population = population;
        this.region = region;
        this.department = department;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public Double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
    public Double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    public Integer getPopulation() {
        return population;
    }
    
    public void setPopulation(Integer population) {
        this.population = population;
    }
    
    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", population=" + population +
                ", region='" + region + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
