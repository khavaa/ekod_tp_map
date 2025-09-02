package td.ekod.map_of_france.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import td.ekod.map_of_france.service.CityService;

/**
 * Contrôleur web pour servir les pages HTML
 */
@Controller
public class WebController {
    
    @Autowired
    private CityService cityService;
    
    /**
     * Page d'accueil avec la carte interactive
     * @param model 
     * @return
     */
    @GetMapping("/")
    public String index(Model model) {
        // Ajouter les données nécessaires pour l'initialisation de la page
        model.addAttribute("totalCities", cityService.getTotalCityCount());
        model.addAttribute("regions", cityService.getAllRegions());
        return "index";
    }
    
    /**
     * Page d'information sur l'application
     */
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("statistics", cityService.getRegionStatistics());
        return "about";
    }
}
