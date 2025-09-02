# 🗺️ Carte Interactive de France

Application web interactive permettant de visualiser et d'explorer les villes françaises depuis une carte de la France métropolitaine.

## 📋 Description du projet

Cette application fullstack permet aux utilisateurs de :
- Cliquer sur la carte de France pour rechercher des villes proches
- Filtrer les résultats par distance, population et région
- Visualiser les villes trouvées sur la carte et dans une liste
- Obtenir des informations détaillées sur chaque ville
- Calculer les distances avec la formule de Haversine

## 🛠️ Technologies utilisées

### Backend
- **Java 17** avec **Spring Boot 3.5.5**
- **Spring Data JPA** pour la persistance
- **PostgreSQL** comme base de données
- **Spring Web** pour l'API REST
- **Thymeleaf** pour le rendu des pages

### Frontend
- **Leaflet.js** pour la carte interactive
- **Bootstrap 5** pour l'interface utilisateur
- **JavaScript** pour l'interactivité
- **Image de carte personnalisée** (carte_fr.png)

### Infrastructure
- **Docker** et **Docker Compose** pour la conteneurisation
- **Maven** pour la gestion des dépendances

## 🚀 Installation et exécution

### Prérequis
- Java 17 ou supérieur
- Docker et Docker Compose
- Maven 3.6+

### Lancement avec Docker

1. **Cloner le projet**
```bash
git clone <votre-repo>
cd map_of_france
```

2. **Lancer avec Docker Compose**
```bash
docker-compose up -d
```

3. **Accéder à l'application**
- Interface web : http://localhost:8080
- API REST : http://localhost:8080/api/cities

### Lancement en développement

1. **Démarrer PostgreSQL**
```bash
docker-compose up -d postgres
```

2. **Compiler et lancer l'application**
```bash
./mvnw clean compile
./mvnw spring-boot:run
```

## 📊 Données

L'application utilise un fichier CSV contenant **2018 villes françaises** avec :
- Nom de la ville
- Coordonnées géographiques (latitude, longitude)
- Population
- Région
- Code postal (généré automatiquement)
- Département (généré automatiquement)

## 🔌 API REST

### Endpoints disponibles

#### `POST /api/cities/search`
Recherche des villes selon des critères géographiques.

**Corps de la requête :**
```json
{
  "latitude": 48.8566,
  "longitude": 2.3522,
  "maxCities": 10,
  "maxDistance": 50.0,
  "minPopulation": 100000,
  "region": "TOUTES"
}
```

**Réponse :**
```json
{
  "cities": [
    {
      "id": 1,
      "name": "Paris",
      "postalCode": "75000",
      "latitude": 48.8566,
      "longitude": 2.3522,
      "population": 2148271,
      "region": "Île-de-France",
      "department": "Paris",
      "distance": 0.0
    }
  ],
  "count": 1,
  "criteria": { ... }
}
```

#### `GET /api/cities/regions`
Récupère toutes les régions disponibles.

#### `GET /api/cities/statistics`
Récupère les statistiques par région.

#### `GET /api/cities/{id}`
Récupère une ville par son ID.

#### `GET /api/cities/health`
Vérifie le statut de l'API.

## 🧪 Tests

### Tests unitaires
```bash
./mvnw test
```

### Tests d'intégration
Les tests d'intégration vérifient :
- Le calcul de distance avec la formule de Haversine
- La validation des coordonnées géographiques
- Le fonctionnement de l'API REST

## 🏗️ Architecture

```
src/
├── main/
│   ├── java/td/ekod/map_of_france/
│   │   ├── entity/          # Entités JPA
│   │   ├── repository/      # Repositories Spring Data
│   │   ├── service/         # Services métier
│   │   ├── controller/      # Contrôleurs REST et Web
│   │   └── dto/            # Data Transfer Objects
│   └── resources/
│       ├── templates/       # Templates Thymeleaf
│       ├── static/         # Ressources statiques (images, CSS, JS)
│       └── fr.csv          # Données des villes françaises
└── test/                   # Tests unitaires et d'intégration
```

## 🎯 Fonctionnalités

### Interface utilisateur
- **Carte interactive** : Cliquez sur la France pour rechercher des villes
- **Filtres dynamiques** : Distance, population, région
- **Liste des résultats** : Affichage compact avec distances
- **Interaction bidirectionnelle** : Survol synchronisé entre carte et liste
- **Informations détaillées** : Popup avec détails de chaque ville

### Calculs géographiques
- **Formule de Haversine** : Calcul précis des distances
- **Validation géographique** : Limitation à la France métropolitaine
- **Optimisation des requêtes** : Filtrage par zone géographique

## 📈 Performance

- **Base de données optimisée** : Index sur les coordonnées géographiques
- **Requêtes efficaces** : Filtrage par bounding box avant calcul exact
- **Interface responsive** : Optimisée pour tous les écrans
- **Chargement rapide** : Données pré-chargées au démarrage

## 🔧 Configuration

### Variables d'environnement
```properties
# Base de données
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/mydatabase
SPRING_DATASOURCE_USERNAME=myuser
SPRING_DATASOURCE_PASSWORD=secret

# Serveur
SERVER_PORT=8080
```

### Docker Compose
```yaml
services:
  postgres:
    image: 'postgres:latest'
    environment:
      - 'POSTGRES_DB=mydatabase'
      - 'POSTGRES_PASSWORD=secret'
      - 'POSTGRES_USER=myuser'
    ports:
      - '5432'
```

## 📝 Réflexion sur le projet

### Ce qui a bien fonctionné
- **Intégration des données CSV** : Chargement automatique des 2018 villes
- **Interface utilisateur** : Expérience fluide et intuitive
- **Calculs géographiques** : Précision des distances avec Haversine
- **Architecture modulaire** : Séparation claire des responsabilités

### Améliorations possibles
- **Cache des résultats** : Mise en cache des recherches fréquentes
- **Pagination** : Gestion des grandes listes de résultats
- **Recherche textuelle** : Recherche par nom de ville
- **Export des données** : Export des résultats en CSV/JSON

### Choix techniques
- **Spring Boot** : Framework robuste et bien documenté
- **Leaflet.js** : Bibliothèque de cartes légère et flexible
- **PostgreSQL** : Base de données relationnelle performante
- **Docker** : Conteneurisation pour la portabilité

## 📞 Support

Pour toute question ou problème :
1. Vérifiez que Docker est en cours d'exécution
2. Consultez les logs : `docker-compose logs`
3. Vérifiez que le port 8080 est disponible

## 📄 Licence

Ce projet est développé dans le cadre d'un devoir académique.
