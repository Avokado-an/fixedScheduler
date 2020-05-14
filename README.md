# fixedScheduler
Basic organizer where you can add plans (their description and date due to which it should be finished), create themes(their description) for plans and inner themes, edit your plans and themes. 
Registration goes via spring security with google messages for account confirmation and password restoration. User input is validated.
Information is saved using JPA + Hibernate, design - freemarker + bootstrap, user validation - spring security + google messages, lombok for entity classes, built using maven, spring for managing beans.
Next to fix/add:
1) Edit homepage(add project description or sth else)
2) Fix error with non-existing email
