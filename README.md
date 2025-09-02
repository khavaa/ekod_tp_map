# Carte Interactive de France

Une application web interactive permettant de visualiser et d'explorer les villes françaises depuis une carte de la France métropolitaine.

## 🎯 Fonctionnalités

- **Carte interactive** : Carte cliquable de la France métropolitaine
- **Recherche de villes** : Recherche par coordonnées géographiques
- **Filtres avancés** : Nombre max de villes, distance, population, région
- **Calcul de distance** : Utilisation de la formule de Haversine
- **Interface responsive** : Compatible mobile et desktop
- **Base de données** : Plus de 2000 villes françaises

## 🛠️ Technologies

- **Backend** : Java 17, Spring Boot 3.5.5
- **Base de données** : PostgreSQL
- **Frontend** : HTML5, CSS3, JavaScript, Bootstrap 5
- **Cartes** : Leaflet.js
- **Tests** : JUnit 5, MockMvc, Cypress
- **Containerisation** : Docker, Docker Compose
- **CI/CD** : GitHub Actions

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

### Endpoint de recherche

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

### Réponse

```json
{
  "cities": [
    {
      "name": "Paris",
      "latitude": 48.8566,
      "longitude": 2.3522,
      "population": 2148271,
      "region": "Île-de-France",
      "distance": 0.0
    }
  ],
  "count": 1
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
│   │   └── dto/           # Objets de transfert
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

- **Carte interactive** : Carte Leaflet avec image personnalisée
- **Filtres dynamiques** : Interface de filtrage en temps réel
- **Liste des résultats** : Affichage des villes trouvées
- **Informations détaillées** : Popups et encarts d'informations
- **Design responsive** : Adaptation mobile/desktop

## 📝 Données

L'application utilise un fichier CSV contenant :
- Nom de la ville
- Coordonnées géographiques (latitude, longitude)
- Région
- Population

Plus de 2000 villes françaises sont incluses.

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
