// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************

// Commande personnalisée pour attendre que l'application soit prête
Cypress.Commands.add('waitForAppReady', () => {
  cy.get('#map', { timeout: 15000 }).should('be.visible')
  cy.get('.stats-info', { timeout: 10000 }).should('be.visible')
})

// Commande pour cliquer sur la carte à des coordonnées spécifiques
Cypress.Commands.add('clickOnMap', (x, y) => {
  cy.get('#map').click(x, y)
  cy.wait(1000) // Attendre que la requête soit traitée
})

// Commande pour vérifier qu'une recherche a retourné des résultats
Cypress.Commands.add('verifySearchResults', (minResults = 1) => {
  cy.get('#resultsSection', { timeout: 10000 }).should('be.visible')
  cy.get('#cityCount').should('not.contain', '0')
  cy.get('#cityList .city-item').should('have.length.at.least', minResults)
})
