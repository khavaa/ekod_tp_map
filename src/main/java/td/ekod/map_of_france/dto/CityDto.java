package td.ekod.map_of_france.dto;

/**
 * DTO pour transférer les informations d'une ville avec sa distance
 */
public class CityDto {
    private Long id;
    private String name;
    private String postalCode;
    private Double latitude;
    private Double longitude;
    private Integer population;
    private String region;
    private String department;
    private Double distance;
    
    public CityDto() {}
    
    public CityDto(Long id, String name, String postalCode, Double latitude, Double longitude,
                   Integer population, String region, String department, Double distance) {
        this.id = id;
        this.name = name;
        this.postalCode = postalCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.population = population;
        this.region = region;
        this.department = department;
        this.distance = distance;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    
    public Integer getPopulation() { return population; }
    public void setPopulation(Integer population) { this.population = population; }
    
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public Double getDistance() { return distance; }
    public void setDistance(Double distance) { this.distance = distance; }
}
