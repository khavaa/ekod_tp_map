#!/bin/bash

echo "🧪 Test complet de l'application Carte Interactive de France"
echo "=========================================================="

# Couleurs pour les messages
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Fonction pour afficher les résultats
print_result() {
    if [ $1 -eq 0 ]; then
        echo -e "${GREEN}✅ $2${NC}"
    else
        echo -e "${RED}❌ $2${NC}"
        exit 1
    fi
}

echo -e "${YELLOW}1. Compilation de l'application...${NC}"
./mvnw clean compile
print_result $? "Compilation réussie"

echo -e "${YELLOW}2. Tests unitaires...${NC}"
./mvnw test
print_result $? "Tests unitaires réussis"

echo -e "${YELLOW}3. Construction de l'application...${NC}"
./mvnw package -DskipTests
print_result $? "Construction réussie"

echo -e "${YELLOW}4. Test Docker Compose...${NC}"
docker-compose up -d
sleep 30

# Test de l'API
echo -e "${YELLOW}5. Test de l'API...${NC}"
curl -f http://localhost:8080/api/cities/search \
  -H "Content-Type: application/json" \
  -d '{"latitude":48.8566,"longitude":2.3522,"maxCities":5,"maxDistance":50.0,"minPopulation":0,"region":"TOUTES"}' \
  > /dev/null 2>&1
print_result $? "API fonctionnelle"

# Test de l'interface web
echo -e "${YELLOW}6. Test de l'interface web...${NC}"
curl -f http://localhost:8080/ > /dev/null 2>&1
print_result $? "Interface web accessible"

echo -e "${YELLOW}7. Nettoyage...${NC}"
docker-compose down
print_result $? "Nettoyage terminé"

echo -e "${GREEN}🎉 Tous les tests sont passés avec succès !${NC}"
echo -e "${GREEN}L'application est prête pour le déploiement.${NC}"
