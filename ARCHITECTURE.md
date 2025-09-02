# 🏗️ Architecture de l'Application

## Diagramme d'architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    FRONTEND (Browser)                      │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐ │
│  │   Leaflet.js    │  │   Bootstrap 5   │  │ JavaScript  │ │
│  │   (Carte)       │  │   (UI)          │  │ (Logique)   │ │
│  └─────────────────┘  └─────────────────┘  └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
                              │
                              │ HTTP/REST
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                 SPRING BOOT APPLICATION                     │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐ │
│  │  WebController  │  │ CityApiController│  │ Thymeleaf   │ │
│  │  (Pages HTML)   │  │  (API REST)     │  │ (Templates) │ │
│  └─────────────────┘  └─────────────────┘  └─────────────┘ │
│                              │                              │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐ │
│  │  CityService    │  │GeographyService │  │DataLoader   │ │
│  │  (Métier)       │  │ (Calculs)       │  │ (CSV)       │ │
│  └─────────────────┘  └─────────────────┘  └─────────────┘ │
│                              │                              │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐ │
│  │ CityRepository  │  │   City Entity   │  │    DTOs     │ │
│  │  (JPA)          │  │   (JPA)         │  │ (Transfer)  │ │
│  └─────────────────┘  └─────────────────┘  └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
                              │
                              │ JDBC
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    POSTGRESQL DATABASE                      │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────────────────────────────────────────────────┐ │
│  │                TABLE: cities                            │ │
│  │  ┌─────────┬─────────┬─────────┬─────────┬─────────────┐ │ │
│  │  │   id    │  name   │ latitude│longitude│ population  │ │ │
│  │  ├─────────┼─────────┼─────────┼─────────┼─────────────┤ │ │
│  │  │    1    │  Paris  │ 48.8566 │ 2.3522  │  2148271    │ │ │
│  │  │    2    │Marseille│ 43.2964 │ 5.37    │  868277     │ │ │
│  │  │   ...   │   ...   │   ...   │   ...   │    ...      │ │ │
│  │  │  2018   │   ...   │   ...   │   ...   │    ...      │ │ │
│  │  └─────────┴─────────┴─────────┴─────────┴─────────────┘ │ │
│  └─────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

## Flux de données

### 1. Recherche de villes
```
User Click → JavaScript → API REST → Service → Repository → Database
     ↓
Database → Repository → Service → API REST → JavaScript → UI Update
```

### 2. Chargement initial
```
Application Start → DataLoader → CSV File → Database
     ↓
WebController → Thymeleaf → HTML → Browser
```

## Composants principaux

### Frontend
- **Leaflet.js** : Gestion de la carte interactive
- **Bootstrap 5** : Interface utilisateur responsive
- **JavaScript** : Logique d'interaction et communication API

### Backend
- **WebController** : Servir les pages HTML
- **CityApiController** : API REST pour les recherches
- **CityService** : Logique métier et orchestration
- **GeographyService** : Calculs géographiques (Haversine)
- **DataLoaderService** : Chargement des données CSV

### Persistance
- **CityRepository** : Interface JPA pour les requêtes
- **City Entity** : Modèle de données
- **PostgreSQL** : Base de données relationnelle

## Technologies et frameworks

| Couche | Technologie | Rôle |
|--------|-------------|------|
| Frontend | Leaflet.js | Carte interactive |
| Frontend | Bootstrap 5 | Interface utilisateur |
| Frontend | JavaScript | Logique client |
| Backend | Spring Boot | Framework principal |
| Backend | Spring Web | Contrôleurs REST |
| Backend | Spring Data JPA | Persistance |
| Backend | Thymeleaf | Templates HTML |
| Database | PostgreSQL | Base de données |
| Infrastructure | Docker | Conteneurisation |

## Patterns utilisés

- **MVC** : Modèle-Vue-Contrôleur
- **Repository** : Abstraction de la persistance
- **DTO** : Transfert de données
- **Service Layer** : Logique métier
- **Dependency Injection** : Injection de dépendances Spring
