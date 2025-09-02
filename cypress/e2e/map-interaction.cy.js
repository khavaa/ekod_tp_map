describe('Carte Interactive de France', () => {
  beforeEach(() => {
    cy.visit('/')
  })

  it('Devrait afficher la page principale avec la carte', () => {
    cy.get('h4').should('contain', 'Carte Interactive de France')
    cy.get('#map').should('be.visible')
    cy.get('.sidebar').should('be.visible')
  })

  it('Devrait afficher les filtres de recherche', () => {
    cy.get('#filtersForm').should('be.visible')
    cy.get('#maxCities').should('have.value', '10')
    cy.get('#maxDistance').should('have.value', '50')
    cy.get('#minPopulation').should('have.value', '0')
    cy.get('#regionFilter').should('be.visible')
  })

  it('Devrait permettre de cliquer sur la carte et afficher des résultats', () => {
    // Attendre que la carte soit chargée
    cy.get('#map', { timeout: 10000 }).should('be.visible')
    
    // Cliquer sur la carte (coordonnées approximatives de Paris)
    cy.get('#map').click(400, 300)
    
    // Vérifier que les résultats apparaissent
    cy.get('#resultsSection', { timeout: 10000 }).should('be.visible')
    cy.get('#cityCount').should('not.contain', '0')
    cy.get('#cityList').should('not.be.empty')
  })

  it('Devrait afficher les informations de ville au survol', () => {
    // Cliquer sur la carte pour avoir des résultats
    cy.get('#map').click(400, 300)
    
    // Attendre les résultats
    cy.get('#cityList .city-item', { timeout: 10000 }).should('exist')
    
    // Survoler une ville
    cy.get('#cityList .city-item').first().trigger('mouseover')
    
    // Vérifier que l'encart d'informations apparaît
    cy.get('#cityInfo').should('be.visible')
    cy.get('#cityInfoTitle').should('not.be.empty')
    cy.get('#cityInfoContent').should('not.be.empty')
  })

  it('Devrait permettre de modifier les filtres', () => {
    cy.get('#maxCities').clear().type('5')
    cy.get('#maxDistance').clear().type('100')
    cy.get('#minPopulation').clear().type('50000')
    
    // Vérifier que les valeurs ont été mises à jour
    cy.get('#maxCities').should('have.value', '5')
    cy.get('#maxDistance').should('have.value', '100')
    cy.get('#minPopulation').should('have.value', '50000')
  })

  it('Devrait appliquer les filtres après un clic sur la carte', () => {
    // Modifier les filtres
    cy.get('#maxCities').clear().type('3')
    cy.get('#maxDistance').clear().type('30')
    
    // Cliquer sur la carte
    cy.get('#map').click(400, 300)
    
    // Vérifier que le bouton d'application des filtres est activé
    cy.get('#applyFilters').should('not.be.disabled')
    
    // Appliquer les filtres
    cy.get('#applyFilters').click()
    
    // Vérifier que les résultats sont limités
    cy.get('#cityList .city-item', { timeout: 10000 }).should('have.length.at.most', 3)
  })

  it('Devrait afficher les statistiques de la base de données', () => {
    cy.get('.stats-info').should('be.visible')
    cy.get('.stats-info').should('contain', 'villes françaises')
  })

  it('Devrait être responsive', () => {
    // Tester sur mobile
    cy.viewport(375, 667)
    cy.get('#map').should('be.visible')
    cy.get('.sidebar').should('be.visible')
    
    // Tester sur tablette
    cy.viewport(768, 1024)
    cy.get('#map').should('be.visible')
    cy.get('.sidebar').should('be.visible')
  })
})
