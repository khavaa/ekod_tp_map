package td.ekod.map_of_france.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour GeographyService
 */
@ExtendWith(MockitoExtension.class)
class GeographyServiceTest {
    
    @InjectMocks
    private GeographyService geographyService;
    
    @Test
    void testCalculateHaversineDistance_ParisToLyon() {
        // Paris: 48.8566, 2.3522
        // Lyon: 45.7640, 4.8357
        // Distance réelle: ~463 km
        
        double distance = geographyService.calculateHaversineDistance(
            48.8566, 2.3522,  // Paris
            45.7640, 4.8357   // Lyon
        );
        
        // Vérifier que la distance est proche de 463 km (±10 km)
        assertTrue(distance >= 450 && distance <= 480, 
            "Distance Paris-Lyon devrait être ~463 km, obtenu: " + distance);
    }
    
    @Test
    void testCalculateHaversineDistance_SamePoint() {
        double distance = geographyService.calculateHaversineDistance(
            48.8566, 2.3522,  // Paris
            48.8566, 2.3522   // Paris (même point)
        );
        
        assertEquals(0.0, distance, "Distance entre le même point devrait être 0");
    }
    
    @Test
    void testCalculateBoundingBox() {
        double[] bbox = geographyService.calculateBoundingBox(
            48.8566, 2.3522,  // Paris
            50.0               // 50 km
        );
        
        assertNotNull(bbox);
        assertEquals(4, bbox.length);
        
        // Vérifier que les bornes sont cohérentes
        assertTrue(bbox[0] < bbox[1], "minLat devrait être < maxLat");
        assertTrue(bbox[2] < bbox[3], "minLon devrait être < maxLon");
        
        // Vérifier que Paris est dans la bounding box
        assertTrue(48.8566 >= bbox[0] && 48.8566 <= bbox[1], "Paris devrait être dans la bbox lat");
        assertTrue(2.3522 >= bbox[2] && 2.3522 <= bbox[3], "Paris devrait être dans la bbox lon");
    }
    
    @Test
    void testIsInFranceMetropolitaine_Paris() {
        boolean result = geographyService.isInFranceMetropolitaine(48.8566, 2.3522);
        assertTrue(result, "Paris devrait être en France métropolitaine");
    }
    
    @Test
    void testIsInFranceMetropolitaine_London() {
        boolean result = geographyService.isInFranceMetropolitaine(51.5074, -0.1278);
        assertFalse(result, "Londres ne devrait pas être en France métropolitaine");
    }
}
