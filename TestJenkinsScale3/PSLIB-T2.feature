Feature: Tester les champs du formulaire avec format invalides (email, phone)
    @TestCaseKey=PSLIB-T2
    Scenario: Tester les champs du formulaire avec format invalides (email, phone)
        
        Given que je suis sur la page “ contact ”, 
        When je remplie le formulaire avec format invalides (e-mail, phone)
        Then je ne dois pas pouvoir envoyer le formulaire 
        And  le message d’erreur doit pouvoir s’afficher