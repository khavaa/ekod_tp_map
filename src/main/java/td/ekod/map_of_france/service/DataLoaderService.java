package td.ekod.map_of_france.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import td.ekod.map_of_france.entity.City;
import td.ekod.map_of_france.repository.CityRepository;

/**
 * Service pour charger les données des villes depuis le fichier CSV
 */
@Service
public class DataLoaderService implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(DataLoaderService.class);
    
    @Autowired
    private CityRepository cityRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Vérifier si des données existent déjà
        if (cityRepository.count() > 0) {
            logger.info("Des données existent déjà dans la base. Skip du chargement.");
            return;
        }
        
        logger.info("Début du chargement des données depuis fr.csv");
        
        try {
            ClassPathResource resource = new ClassPathResource("fr.csv");
            
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                
                String line;
                int lineNumber = 0;
                int loadedCount = 0;
                
                // Lire l'en-tête
                String header = reader.readLine();
                if (header == null || !header.startsWith("name,latitude,longitude,region,population")) {
                    logger.warn("Format de fichier CSV inattendu. En-tête: {}", header);
                }
                lineNumber++;
                
                // Lire les données
                while ((line = reader.readLine()) != null) {
                    lineNumber++;
                    
                    try {
                        String[] fields = parseCsvLine(line);
                        
                        if (fields.length >= 5) {
                            String name = fields[0].trim();
                            double latitude = Double.parseDouble(fields[1].trim());
                            double longitude = Double.parseDouble(fields[2].trim());
                            String region = fields[3].trim();
                            int population = Integer.parseInt(fields[4].trim());
                            
                            String postalCode = "N/A";
                            String department = getDepartmentFromPostalCode(postalCode);
                            
                            City city = new City(name, postalCode, latitude, longitude, population, region, department);
                            cityRepository.save(city);
                            loadedCount++;
                            
                            if (loadedCount % 100 == 0) {
                                logger.debug("Chargé {} villes...", loadedCount);
                            }
                        } else {
                            logger.warn("Ligne {} ignorée (format invalide): {}", lineNumber, line);
                        }
                    } catch (Exception e) {
                        logger.warn("Erreur lors du traitement de la ligne {}: {} - {}", lineNumber, line, e.getMessage());
                    }
                }
                
                logger.info("Chargement terminé: {} villes chargées depuis {} lignes", loadedCount, lineNumber - 1);
            }
        } catch (Exception e) {
            logger.error("Erreur lors du chargement du fichier CSV", e);
        }
    }
    
    /**
     * Parse une ligne CSV en gérant les virgules dans les guillemets
     */
    private String[] parseCsvLine(String line) {
        // Simple parsing - peut être amélioré pour gérer les guillemets
        return line.split(",");
    }
    
    /**
     * Extrait le département à partir du code postal
     */
    private String getDepartmentFromPostalCode(String postalCode) {
        if (postalCode != null && postalCode.length() >= 2 && !postalCode.equals("N/A")) {
            return postalCode.substring(0, 2);
        }
        return "Inconnu";
    }
}
