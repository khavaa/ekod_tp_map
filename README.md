# Carte Interactive de France

Une application web interactive permettant de visualiser et d'explorer les villes françaises depuis une carte de la France métropolitaine.

## 🎯 Fonctionnalités

### Fonctionnalités principales
- **Carte interactive** : Carte cliquable de la France métropolitaine avec image personnalisée
- **Recherche de villes** : Recherche par coordonnées géographiques
- **Filtres avancés** : Nombre max de villes, distance, population, région
- **Calcul de distance** : Utilisation de la formule de Haversine
- **Interface responsive** : Compatible mobile et desktop
- **Base de données** : Plus de 2000 villes françaises

### Fonctionnalités bonus 🚀
- **Recherche textuelle** : Recherche de villes par nom avec autocomplétion
- **Tri dynamique** : Tri par distance, population ou nom
- **Mise en cache** : Optimisation des performances avec Spring Cache
- **Pagination** : Gestion des grandes listes de résultats
- **Clustering de villes** : Regroupement des villes proches
- **Monitoring des performances** : Suivi des temps de réponse

## 🛠️ Technologies

- **Backend** : Java 17, Spring Boot 3.5.5
- **Base de données** : PostgreSQL
- **Frontend** : HTML5, CSS3, JavaScript, Bootstrap 5
- **Cartes** : Leaflet.js
- **Tests** : JUnit 5, MockMvc, Cypress
- **Containerisation** : Docker, Docker Compose
- **CI/CD** : GitHub Actions
- **Cache** : Spring Cache avec ConcurrentMapCacheManager

## 🚀 Installation et exécution

### Prérequis

- Java 17+
- Maven 3.6+
- Docker et Docker Compose
- Node.js 18+ (pour les tests Cypress)

### Lancement avec Docker Compose

```bash
# Cloner le projet
git clone <repository-url>
cd map_of_france

# Lancer l'application complète
docker-compose up -d

# L'application sera accessible sur http://localhost:8080
```

### Lancement en développement

```bash
# Démarrer PostgreSQL
docker-compose up postgres -d

# Lancer l'application
./mvnw spring-boot:run
```

## 🧪 Tests

### Tests unitaires et d'intégration

```bash
# Tous les tests
./mvnw test

# Tests unitaires seulement
./mvnw test -Dtest="*Test"

# Tests d'intégration
./mvnw test -Dtest="*IntegrationTest"
```

### Tests end-to-end avec Cypress

```bash
# Installer les dépendances
npm install

# Lancer l'application
./mvnw spring-boot:run

# Dans un autre terminal, lancer Cypress
npm run cypress:open
# ou
npm run cypress:run
```

## 📊 API

### Endpoint de recherche géographique

```http
POST /api/cities/search
Content-Type: application/json

{
  "latitude": 48.8566,
  "longitude": 2.3522,
  "maxCities": 10,
  "maxDistance": 50.0,
  "minPopulation": 0,
  "region": "TOUTES"
}
```

### Endpoint de recherche textuelle (Nouveau)

```http
GET /api/cities/search-text?query=Paris&limit=10
```

### Réponse

```json
{
  "cities": [
    {
      "id": 1,
      "name": "Paris",
      "latitude": 48.8566,
      "longitude": 2.3522,
      "population": 2148271,
      "region": "Île-de-France",
      "postalCode": "N/A",
      "distance": 0.0
    }
  ],
  "count": 1,
  "query": "Paris"
}
```

## 🏗️ Architecture

```
src/
├── main/
│   ├── java/td/ekod/map_of_france/
│   │   ├── controller/     # Contrôleurs REST
│   │   ├── service/        # Services métier
│   │   ├── repository/     # Accès aux données
│   │   ├── entity/         # Entités JPA
│   │   ├── dto/           # Objets de transfert
│   │   └── config/        # Configuration (Cache, etc.)
│   └── resources/
│       ├── templates/      # Templates Thymeleaf
│       ├── static/         # Ressources statiques
│       └── fr.csv         # Données des villes
└── test/
    ├── java/              # Tests unitaires et d'intégration
    └── resources/         # Configuration de test
```

## 🔧 Configuration

### Variables d'environnement

- `SPRING_DATASOURCE_URL` : URL de la base de données
- `SPRING_DATASOURCE_USERNAME` : Nom d'utilisateur DB
- `SPRING_DATASOURCE_PASSWORD` : Mot de passe DB

### Profils

- `default` : Configuration de production
- `test` : Configuration de test avec H2

## 📈 CI/CD

Le projet utilise GitHub Actions pour :

- **Tests unitaires** : Exécution automatique sur chaque push/PR
- **Tests d'intégration** : Validation de l'API
- **Tests E2E** : Validation des parcours utilisateur avec Cypress
- **Build Docker** : Construction et test de l'image Docker
- **Déploiement** : Déploiement automatique sur la branche main

## 🎨 Interface utilisateur

- **Carte interactive** : Carte Leaflet avec image personnalisée de la France
- **Filtres dynamiques** : Interface de filtrage en temps réel
- **Liste des résultats** : Affichage des villes trouvées avec tri
- **Recherche textuelle** : Barre de recherche avec autocomplétion
- **Informations détaillées** : Popups et encarts d'informations
- **Design responsive** : Adaptation mobile/desktop

## 📝 Données

L'application utilise un fichier CSV contenant :
- Nom de la ville
- Coordonnées géographiques (latitude, longitude)
- Région
- Population
- Département

Plus de 2000 villes françaises sont incluses.

## 🚀 Fonctionnalités bonus implémentées

### 1. Recherche textuelle
- Recherche de villes par nom avec insensibilité à la casse
- Limitation du nombre de résultats
- Tri par population décroissante

### 2. Mise en cache
- Cache des résultats de recherche textuelle
- Cache des listes de régions
- Amélioration des performances

### 3. Interface améliorée
- Barre de recherche textuelle
- Tri dynamique des résultats
- Meilleure ergonomie

## 🤝 Contribution

1. Fork le projet
2. Créer une branche feature (`git checkout -b feature/nouvelle-fonctionnalite`)
3. Commit les changements (`git commit -am 'Ajouter nouvelle fonctionnalité'`)
4. Push vers la branche (`git push origin feature/nouvelle-fonctionnalite`)
5. Créer une Pull Request

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.

## 👥 Auteur

Développé dans le cadre du cours "Développement des composants métier" - EKOD

## 🔄 Réflexion sur l'expérience

### Ce qui a fonctionné
- **Architecture Spring Boot** : Framework robuste et bien documenté
- **Base de données PostgreSQL** : Performance et fiabilité
- **Interface Leaflet** : Facile à intégrer et personnaliser
- **Tests automatisés** : Cypress pour les tests E2E très efficace
- **Docker** : Simplifie le déploiement et la portabilité

### Ce qui a été modifié
- **Carte** : Passage d'OpenStreetMap à une image personnalisée de la France
- **Données** : Intégration d'un fichier CSV personnalisé
- **Interface** : Ajout de fonctionnalités bonus (recherche textuelle, cache)

### Ce qui pourrait être amélioré
- **Performance** : Mise en place d'un cache Redis pour la production
- **Sécurité** : Ajout d'authentification et d'autorisation
- **Monitoring** : Intégration d'outils de monitoring (Prometheus, Grafana)
- **API** : Versioning de l'API et documentation OpenAPI/Swagger

### Choix techniques justifiés
- **Spring Boot** : Écosystème mature, nombreuses intégrations
- **PostgreSQL** : Base relationnelle robuste pour les données géographiques
- **Leaflet** : Bibliothèque légère et flexible pour les cartes
- **Docker** : Standardisation de l'environnement de déploiement
