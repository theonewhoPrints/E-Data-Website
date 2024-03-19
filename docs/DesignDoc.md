---
geometry: margin=1in
---
# PROJECT Design Documentation

> _The following template provides the headings for your Design
> Documentation.  As you edit each section make sure you remove these
> commentary 'blockquotes'; the lines that start with a > character
> and appear in the generated PDF in italics but do so only **after** all team members agree that the requirements for that section and current Sprint have been met. **Do not** delete future Sprint expectations._

## Team Information
* Team name: Mansfield Minions
* Team members
  * Isaac Soares
  * Nadeem Mustafa
  * Evan Kinsey
  * Jacky Chan
  * Anthony Visiko

## Executive Summary   

OnlyVillains is a client-for website! Changing the direction of power and fairness us villains have in our schemes, solving the inequality they have when versing 'heroes' and 'figures of authority', by giving a way for people to help support our cause of sinisterness through donating to villain schemes so they can be more successful! This platform will be open to use for all people who want to donate to a villain's schemes, and villains as well who pass through the admin's requirements to post their schemes to get funding for.


### Purpose
>  _**[Sprint 2 & 4]** 
Provide a very brief statement about the project and the most important user group and user goals._
The goal of this project is to create a functional website that enables users to register an account and log in. The primary purpose of the website is to assist villainous schemes by allowing users to add items to their cart and proceed to checkout by making donations. Additionally, the website should provide functionality for villains to establish their own accounts, enabling them to create schemes directly on the platform. These schemes will then be managed by administrators who also have their own accounts. Each user account will have specific permissions, with administrators possessing the highest level of access among the three user.

### Glossary and Acronyms
> _**[Sprint 2 & 4]** Provide a table of terms and acronyms._

| Term | Definition |
|------|------------|
| SPA | Single Page |


## Requirements

This section describes the features of the application.

> _In this section you do not need to be exhaustive and list every
> story.  Focus on top-level features from the Vision document and
> maybe Epics and critical Stories._

### Definition of MVP
> _**[Sprint 2 & 4]** Provide a simple description of the Minimum Viable Product._
The MVP of this onlyvillains website includes the following core features:
* User registration and login functionality
* A search bar enabling users to find schemes by name
* Capability to create their schemes and add them to the website
* Notifications alerting users if they already have a scheme in their cart or when they add a scheme to the cart

### MVP Features
>  _**[Sprint 4]** Provide a list of top-level Epics and/or Stories of the MVP._

### Enhancements
> _**[Sprint 4]** Describe what enhancements you have implemented for the project._


## Application Domain

This section describes the application domain.

![Domain Model](DomainAnalysis2.png)

> _**[Sprint 2 & 4]** Provide a high-level overview of the domain for this application. You
> can discuss the more important domain entities and their relationship to each other._

Domain Entities
* Evil Basket: This entity is a container that holds a collection of schemes.
* Schemes: This is a entity that represents a plan of malicious purpose.
* Evil Cupboard: This entity is a storage location for all the schemes in the system.
* Manager: This is a type of user with access who reviews submitted schemes, manages Evil Baskets, and has full access to the Evil Cupboard.
* Villiain: This is a type of user who can submit schemes.
* Helper: This is a type of user who can search schemes and check them out in the Evil Basket.
* Server: This entity is the backend system that stores information about users, schemes, Evil Baskets, and other aspects of the system.
* File: This entity is the files on the server that store the data used by the system.

Relationship 
* Manager adds/removes schemes to/from Evil Basket: A manager can add schemes to and remove schemes from Evil Baskets.
* Manager has all schemes in Evil Cupboard: A manager has all the schemes in the Evil Cupboard.
* Manager checks schemes: A manager checks schemes.
* Manager/Villiain/Helper identifies with username: All three users have usernames that they use to identify themselves with the system.
* Scheme is a type of ranking profile: A scheme can be ranked that can be viewed by users.
* Villiain submits schemes: Villiains can submit schemes.
* Helper searches through schemes: A Helper can search through schemes.
* Helper checks out Evil Basket: A Helper can checkout schemes in their Evil Basket.
* Evil Basket has schemes: An Evil Basket contains Schemes.
* Server saves to/loads from File: The server saves information to files and loads information from files.


## Architecture and Design

This section describes the application architecture.

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture. 
**NOTE**: detailed diagrams are required in later sections of this document.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)

The web application, is built using the Model–View–ViewModel (MVVM) architecture pattern. 

The Model stores the application data objects including any functionality to provide persistance. 

The View is the client-side SPA built with Angular utilizing HTML, CSS and TypeScript. The ViewModel provides RESTful APIs to the client (View) as well as any logic required to manipulate the data objects from the Model.

Both the ViewModel and Model are built using Java and Spring Framework. Details of the components within these tiers are supplied below.


### Overview of User Interface

This section describes the web interface flow; this is how the user views and interacts with the web application.

> _Provide a summary of the application's user interface.  Describe, from the user's perspective, the flow of the pages in the web application._


### View Tier
> _**[Sprint 4]** Provide a summary of the View Tier UI of your architecture.
> Describe the types of components in the tier and describe their
> responsibilities.  This should be a narrative description, i.e. it has
> a flow or "story line" that the reader can follow._

> _**[Sprint 4]** You must  provide at least **2 sequence diagrams** as is relevant to a particular aspects 
> of the design that you are describing.  (**For example**, in a shopping experience application you might create a 
> sequence diagram of a customer searching for an item and adding to their cart.)
> As these can span multiple tiers, be sure to include an relevant HTTP requests from the client-side to the server-side 
> to help illustrate the end-to-end flow._

> _**[Sprint 4]** To adequately show your system, you will need to present the **class diagrams** where relevant in your design. Some additional tips:_
 >* _Class diagrams only apply to the **ViewModel** and **Model** Tier_
>* _A single class diagram of the entire system will not be effective. You may start with one, but will be need to break it down into smaller sections to account for requirements of each of the Tier static models below._
 >* _Correct labeling of relationships with proper notation for the relationship type, multiplicities, and navigation information will be important._
 >* _Include other details such as attributes and method signatures that you think are needed to support the level of detail in your discussion._

### ViewModel Tier
- VillainController: Directs VillainDAO to manipulate data
- UserController: Directs UserDAO to manipulate data

> _**[Sprint 4]** Provide a summary of this tier of your architecture. This
> section will follow the same instructions that are given for the View
> Tier above._

> _At appropriate places as part of this narrative provide **one** or more updated and **properly labeled**
> static models (UML class diagrams) with some details such as critical attributes and methods._
> 
![Replace with your ViewModel Tier class diagram 1, etc.](model-placeholder.png)

### Model Tier
- Scheme: Logic for a Scheme object 
- VillainDAO: Interface for VillainFileDAO
- VillainFileDAO: Logic to get and manipulate data in villains.json
- User: Logic for a User object, used for log in
- UserDAO: Interface for UserFileDAO
- UserFileDAO: Logic to get data in users.json

> _**[Sprint 2, 3 & 4]** Provide a summary of this tier of your architecture. This
> section will follow the same instructions that are given for the View
> Tier above._

> _At appropriate places as part of this narrative provide **one** or more updated and **properly labeled**
> static models (UML class diagrams) with some details such as critical attributes and methods._
> 
![Replace with your Model Tier class diagram 1, etc.](model-placeholder.png)

## OO Design Principles

- Single Responsibility: Each class in our architecture has a clear and singular purpose; for instance, the ViewModel solely manages the presentation logic, while the Model handles data manipulation and storage.
- High Cohesion: Components within the ViewModel and Model are closely related and work together efficiently to achieve specific functionalities, promoting better maintainability and readability of the codebase.
- Information Expert: Responsibilities are assigned to the classes that possess the necessary information and expertise to fulfill them; for instance, the VillainDAO handles database interactions, leveraging its knowledge of data access.
- Low Coupling: Our design minimizes the dependencies between classes, ensuring changes in one component do not heavily impact others, enhancing the system's flexibility and scalability.
- Law of Demeter: Objects interact with closely-related neighbors only, reducing the ripple effects of changes and promoting a more modular and maintainable design.
- Dependency Inversion: Higher-level modules depend on abstractions rather than concrete implementations, facilitating easier integration and testing while promoting code reusability and flexibility.
- Controller: The VillainController acts as an intermediary between the ViewModel and Model, facilitating user interactions and ensuring separation of concerns.
- Open/Closed: Our design allows for extension through inheritance or composition without modifying existing code, promoting code reuse and ensuring stability.
- Pure Fabrication: When necessary, we introduce classes that do not represent real-world concepts but are created solely to enable separation of concerns and enhance maintainability.
- Polymorphism: Through interfaces and inheritance, our design allows objects of different types to be treated uniformly, promoting flexibility and extensibility in handling various data types and behaviors.

> _**[Sprint 2, 3 & 4]** Will eventually address upto **4 key OO Principles** in your final design. Follow guidance in augmenting those completed in previous Sprints as indicated to you by instructor. Be sure to include any diagrams (or clearly refer to ones elsewhere in your Tier sections above) to support your claims._

> _**[Sprint 3 & 4]** OO Design Principles should span across **all tiers.**_

## Static Code Analysis/Future Design Improvements
> _**[Sprint 4]** With the results from the Static Code Analysis exercise, 
> **Identify 3-4** areas within your code that have been flagged by the Static Code 
> Analysis Tool (SonarQube) and provide your analysis and recommendations.  
> Include any relevant screenshot(s) with each area._

> _**[Sprint 4]** Discuss **future** refactoring and other design improvements your team would explore if the team had additional time._

## Testing
> _This section will provide information about the testing performed
> and the results of the testing._

### Acceptance Testing
> _**[Sprint 2 & 4]** Report on the number of user stories that have passed all their
> acceptance criteria tests, the number that have some acceptance
> criteria tests failing, and the number of user stories that
> have not had any testing yet. Highlight the issues found during
> acceptance testing and if there are any concerns._

### Unit Testing and Code Coverage
> _**[Sprint 4]** Discuss your unit testing strategy. Report on the code coverage
> achieved from unit testing of the code base. Discuss the team's
> coverage targets, why you selected those values, and how well your
> code coverage met your targets._

>_**[Sprint 2 & 4]** **Include images of your code coverage report.** If there are any anomalies, discuss
> those._

## Ongoing Rationale
>_**[Sprint 1, 2, 3 & 4]** Throughout the project, provide a time stamp **(yyyy/mm/dd): Sprint # and description** of any _**mayor**_ team decisions or design milestones/changes and corresponding justification._
