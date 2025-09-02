package td.ekod.map_of_france.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import td.ekod.map_of_france.dto.SearchCriteriaDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
class CityApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void searchCities_IntegrationTest_ShouldReturnCities() throws Exception {
        // Given
        SearchCriteriaDto criteria = new SearchCriteriaDto();
        criteria.setLatitude(48.8566); // Paris
        criteria.setLongitude(2.3522);
        criteria.setMaxCities(5);
        criteria.setMaxDistance(100.0);
        criteria.setMinPopulation(0);
        criteria.setRegion("TOUTES");

        // When & Then
        mockMvc.perform(post("/api/cities/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(criteria)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cities").isArray())
                .andExpect(jsonPath("$.count").isNumber())
                .andExpect(jsonPath("$.cities[0].name").exists())
                .andExpect(jsonPath("$.cities[0].latitude").exists())
                .andExpect(jsonPath("$.cities[0].longitude").exists())
                .andExpect(jsonPath("$.cities[0].distance").exists());
    }

    @Test
    void searchCities_WithRegionFilter_ShouldReturnFilteredResults() throws Exception {
        // Given
        SearchCriteriaDto criteria = new SearchCriteriaDto();
        criteria.setLatitude(48.8566); // Paris
        criteria.setLongitude(2.3522);
        criteria.setMaxCities(10);
        criteria.setMaxDistance(200.0);
        criteria.setMinPopulation(0);
        criteria.setRegion("Île-de-France");

        // When & Then
        mockMvc.perform(post("/api/cities/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(criteria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cities").isArray());
    }

    @Test
    void searchCities_WithPopulationFilter_ShouldReturnFilteredResults() throws Exception {
        // Given
        SearchCriteriaDto criteria = new SearchCriteriaDto();
        criteria.setLatitude(48.8566); // Paris
        criteria.setLongitude(2.3522);
        criteria.setMaxCities(10);
        criteria.setMaxDistance(200.0);
        criteria.setMinPopulation(100000); // Villes de plus de 100k habitants
        criteria.setRegion("TOUTES");

        // When & Then
        mockMvc.perform(post("/api/cities/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(criteria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cities").isArray());
    }
}
