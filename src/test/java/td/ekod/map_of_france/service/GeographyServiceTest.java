package td.ekod.map_of_france.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class GeographyServiceTest {

    private GeographyService geographyService;

    @BeforeEach
    void setUp() {
        geographyService = new GeographyService();
    }

    @Test
    @DisplayName("Calcul de distance Haversine entre Paris et Lyon")
    void calculateHaversineDistance_ParisToLyon_ShouldReturnCorrectDistance() {
        // Given
        double parisLat = 48.8566;
        double parisLon = 2.3522;
        double lyonLat = 45.76;
        double lyonLon = 4.84;
        
        // Distance approximative Paris-Lyon : ~392 km
        double expectedDistance = 392.0;
        double tolerance = 10.0; // Tolérance de 10 km

        // When
        double actualDistance = geographyService.calculateHaversineDistance(parisLat, parisLon, lyonLat, lyonLon);

        // Then
        assertEquals(expectedDistance, actualDistance, tolerance);
    }

    @Test
    @DisplayName("Calcul de distance Haversine - même point")
    void calculateHaversineDistance_SamePoint_ShouldReturnZero() {
        // Given
        double lat = 48.8566;
        double lon = 2.3522;

        // When
        double distance = geographyService.calculateHaversineDistance(lat, lon, lat, lon);

        // Then
        assertEquals(0.0, distance, 0.001);
    }

    @Test
    @DisplayName("Vérification coordonnées en France métropolitaine - Paris")
    void isInFranceMetropolitaine_Paris_ShouldReturnTrue() {
        // Given
        double parisLat = 48.8566;
        double parisLon = 2.3522;

        // When
        boolean result = geographyService.isInFranceMetropolitaine(parisLat, parisLon);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Vérification coordonnées en France métropolitaine - Marseille")
    void isInFranceMetropolitaine_Marseille_ShouldReturnTrue() {
        // Given
        double marseilleLat = 43.2964;
        double marseilleLon = 5.37;

        // When
        boolean result = geographyService.isInFranceMetropolitaine(marseilleLat, marseilleLon);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Vérification coordonnées hors France - Londres")
    void isInFranceMetropolitaine_London_ShouldReturnFalse() {
        // Given
        double londonLat = 51.5074;
        double londonLon = -0.1278;

        // When
        boolean result = geographyService.isInFranceMetropolitaine(londonLat, londonLon);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Vérification coordonnées hors France - Madrid")
    void isInFranceMetropolitaine_Madrid_ShouldReturnFalse() {
        // Given
        double madridLat = 40.4168;
        double madridLon = -3.7038;

        // When
        boolean result = geographyService.isInFranceMetropolitaine(madridLat, madridLon);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Vérification limites nord de la France")
    void isInFranceMetropolitaine_NorthLimit_ShouldReturnTrue() {
        // Given - Lille (limite nord)
        double lilleLat = 50.6292;
        double lilleLon = 3.0573;

        // When
        boolean result = geographyService.isInFranceMetropolitaine(lilleLat, lilleLon);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Vérification limites sud de la France")
    void isInFranceMetropolitaine_SouthLimit_ShouldReturnTrue() {
        // Given - Perpignan (limite sud)
        double perpignanLat = 42.6886;
        double perpignanLon = 2.8948;

        // When
        boolean result = geographyService.isInFranceMetropolitaine(perpignanLat, perpignanLon);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Vérification limites ouest de la France")
    void isInFranceMetropolitaine_WestLimit_ShouldReturnTrue() {
        // Given - Brest (limite ouest)
        double brestLat = 48.3906;
        double brestLon = -4.4861;

        // When
        boolean result = geographyService.isInFranceMetropolitaine(brestLat, brestLon);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Vérification limites est de la France")
    void isInFranceMetropolitaine_EastLimit_ShouldReturnTrue() {
        // Given - Strasbourg (limite est)
        double strasbourgLat = 48.5833;
        double strasbourgLon = 7.7458;

        // When
        boolean result = geographyService.isInFranceMetropolitaine(strasbourgLat, strasbourgLon);

        // Then
        assertTrue(result);
    }
}
