package td.ekod.map_of_france.service;

import org.springframework.stereotype.Service;

/**
 * Service pour les calculs géographiques
 */
@Service
public class GeographyService {
    
    private static final double EARTH_RADIUS_KM = 6371.0;
    
    /**
     * Calcule la distance entre deux points géographiques en utilisant la formule de Haversine
     * @param lat1 
     * @param lon1 
     * @param lat2 
     * @param lon2 
     * @return
     */
    public double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        // Conversion des degrés en radians
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);
        
        // Différences
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;
        
        // Formule de Haversine
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                   Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        // Distance en kilomètres, arrondie sans décimale
        return Math.round(EARTH_RADIUS_KM * c);
    }
    
    /**
     * Calcule une zone géographique approximative basée sur une distance maximale
     * @param centerLat
     * @param centerLon
     * @param maxDistanceKm
     * @return
     */
    public double[] calculateBoundingBox(double centerLat, double centerLon, double maxDistanceKm) {
        // Approximation : 1 degré de latitude ≈ 111 km
        // 1 degré de longitude ≈ 111 km * cos(latitude)
        
        double latDelta = maxDistanceKm / 111.0;
        double lonDelta = maxDistanceKm / (111.0 * Math.cos(Math.toRadians(centerLat)));
        
        double minLat = centerLat - latDelta;
        double maxLat = centerLat + latDelta;
        double minLon = centerLon - lonDelta;
        double maxLon = centerLon + lonDelta;
        
        // Limiter aux bornes valides de la Terre
        minLat = Math.max(minLat, -90.0);
        maxLat = Math.min(maxLat, 90.0);
        minLon = Math.max(minLon, -180.0);
        maxLon = Math.min(maxLon, 180.0);
        
        return new double[]{minLat, maxLat, minLon, maxLon};
    }
    
    /**
     * Vérifie si un point est dans les limites de la France métropolitaine
     * @param latitude
     * @param longitude
     * @return
     */
    public boolean isInFranceMetropolitaine(double latitude, double longitude) {
        // Limites approximatives de la France métropolitaine
        return latitude >= 41.0 && latitude <= 51.5 &&
               longitude >= -5.5 && longitude <= 9.5;
    }
}
