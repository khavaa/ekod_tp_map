package td.ekod.map_of_france.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import td.ekod.map_of_france.dto.CityDto;
import td.ekod.map_of_france.dto.SearchCriteriaDto;
import td.ekod.map_of_france.service.CityService;
import td.ekod.map_of_france.service.GeographyService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CityApiController.class)
class CityApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityService cityService;

    @MockBean
    private GeographyService geographyService;

    @Autowired
    private ObjectMapper objectMapper;

    private SearchCriteriaDto validCriteria;
    private List<CityDto> mockCities;

    @BeforeEach
    void setUp() {
        validCriteria = new SearchCriteriaDto();
        validCriteria.setLatitude(48.8566);
        validCriteria.setLongitude(2.3522);
        validCriteria.setMaxCities(10);
        validCriteria.setMaxDistance(50.0);
        validCriteria.setMinPopulation(0);
        validCriteria.setRegion("TOUTES");

        CityDto paris = new CityDto();
        paris.setName("Paris");
        paris.setLatitude(48.8566);
        paris.setLongitude(2.3522);
        paris.setPopulation(2148271);
        paris.setRegion("Île-de-France");
        paris.setDistance(0.0);

        mockCities = Arrays.asList(paris);
    }

    @Test
    void searchCities_WithValidCriteria_ShouldReturnCities() throws Exception {
        // Given
        when(geographyService.isInFranceMetropolitaine(any(Double.class), any(Double.class)))
                .thenReturn(true);
        when(cityService.searchCities(any(SearchCriteriaDto.class)))
                .thenReturn(mockCities);

        // When & Then
        mockMvc.perform(post("/api/cities/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCriteria)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cities").isArray())
                .andExpect(jsonPath("$.cities[0].name").value("Paris"))
                .andExpect(jsonPath("$.cities[0].population").value(2148271))
                .andExpect(jsonPath("$.count").value(1));
    }

    @Test
    void searchCities_WithInvalidCoordinates_ShouldReturnBadRequest() throws Exception {
        // Given
        validCriteria.setLatitude(60.0); // Hors de France
        validCriteria.setLongitude(10.0);
        when(geographyService.isInFranceMetropolitaine(any(Double.class), any(Double.class)))
                .thenReturn(false);

        // When & Then
        mockMvc.perform(post("/api/cities/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCriteria)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Les coordonnées doivent être en France métropolitaine"));
    }

    @Test
    void searchCities_WithInvalidCriteria_ShouldReturnBadRequest() throws Exception {
        // Given
        validCriteria.setLatitude(null); // Coordonnée invalide

        // When & Then
        mockMvc.perform(post("/api/cities/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCriteria)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void searchCities_WithMaxCitiesExceeded_ShouldReturnBadRequest() throws Exception {
        // Given
        validCriteria.setMaxCities(100); // Limite dépassée

        // When & Then
        mockMvc.perform(post("/api/cities/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCriteria)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void searchCities_WithNegativeDistance_ShouldReturnBadRequest() throws Exception {
        // Given
        validCriteria.setMaxDistance(-10.0); // Distance négative

        // When & Then
        mockMvc.perform(post("/api/cities/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCriteria)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void searchCities_WithNegativePopulation_ShouldReturnBadRequest() throws Exception {
        // Given
        validCriteria.setMinPopulation(-1000); // Population négative

        // When & Then
        mockMvc.perform(post("/api/cities/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCriteria)))
                .andExpect(status().isBadRequest());
    }
}
