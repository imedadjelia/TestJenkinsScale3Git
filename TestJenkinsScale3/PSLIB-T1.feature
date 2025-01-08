Feature: Tester la le bouton contact 
    @TestCaseKey=PSLIB-T1
    Scenario: Tester la le bouton contact 
        
        Feature : Navigate to contact page
        
          Scenario : User navigates to the contact page
            Given User is on the SLIB home page
            When User clicks on the "Contact" link
            Then User should be on the contact page