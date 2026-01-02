# Spring Boot Application Architecture Patterns

This repository demonstrates the implementation of a sample application using different architectural patterns. 

## Motivation
There are no silver bullet solutions.
When it comes to architecting a modern application, you might often hear that **Anemic Domain Model is BAD**, 
or **Transaction Script is an AntiPattern**, etc.
You might also hear many people recommending **Clean Architecture** or **Hexagonal Architecture** with **DDD** as the better/best architecture to follow.

However, **there is no free lunch. Every powerful solution comes with its own set of tradeoffs**.
It is important to understand the tradeoffs and choose the architecture that best fits the requirements of the application.

If you are building a microservice for a specific subdomain, maybe a simple layered architecture is good enough.
If you are building a monolithic application that has low-to-medium complexity, maybe a simple modular monolith architecture is good enough.
But if you are building a complex application with high complexity involving multiple subdomains, workflows, etc 
then you might want to consider a modular monolith architecture with DDD and Hexagonal Architecture.

You might wonder **why should we settle for a good-enough architecture instead of the best architecture?**.

**The answer is simple: It all comes down to Cost Vs Benefit. A good-enough architecture is often sufficient for most applications and can be easier to implement, maintain, and evolve compared to a perfect architecture.**

In my opinion, it is easier to fix an under-engineered system than an overengineered system.
**Adding things is easier than removing things**.

Imagine you are working on an existing large codebase, and you are 99% sure that one of the libraries is not being used.
But you won't dare to remove it because it might break something else.
The same rule applies to architecture. Once you overengineer something, then it is very hard to fix it. 

Instead, **you should start with a good-enough architecture and evolve it as you go.**

So, in this repository, I am going to demonstrate the implementation of a sample application using different architectural patterns.
Starting with a simple layered architecture and gradually moving to a modular monolith architecture with DDD and Hexagonal Architecture.
In each module, the design and architecture of the application is improved by adopting the best practices and patterns.

- Layered Architecture
- Package-By-Module Architecture
- Simple Modular Monolith Architecture
- Modular Monolith with [Tomato Architecture](https://tomato-architecture.github.io/)
- Modular Monolith with DDD and Hexagonal Architecture

## Sample Application Overview (Meetup4j)
The application we are going to build is an event management system with the following UseCases:

- Create events
- List events
- View event details
- Cancel event
- Register for an event
- Cancel registration
- Get the list of attendees for an event
- Get the status of a user's registration for an event
- Get the list of user's upcoming events and past attended events

## Architecture Patterns
In this repository the meetup4j REST API is built using the following architectural patterns:

### Layered Architecture
- Layered architecture using the package-by-layer approach
- Code is structured by technical layers (Controller, Service, Repository, Mapper, etc.)
- Used primitive types (String, Integer, etc.) instead of Domain Types (Value Objects)
- Used JPA entities as Domain Models
- Anemic Domain Models with Transaction Script Pattern

### Package-By-Module Architecture
- Code is structured by modules/subsystems (events, registrations, notifications, etc.)
- Used primitive types (String, Integer, etc.) instead of Domain Types (Value Objects)
- Used JPA entities as Domain Models
- Anemic Domain Models with Transaction Script Pattern
- Cross-module communication using Events

#### Improvements:
- The package-by-module structure provides better modularity.
- It is easier to explore the module-specific code.
- It is easier to do internal refactoring without affecting other modules.
- The package-by-module structure makes it easier to extract the module as a separate microservice if needed.

### Simple Modular Monolith Architecture
- Code is structured by modules/subsystems (events, registrations, notifications, etc.)
- Used primitive types (String, Integer, etc.) instead of Domain Types (Value Objects)
- Used JPA entities as Domain Models
- Anemic Domain Models with Transaction Script Pattern
- Cross-module communication using Spring Modulith Persistent Events
- Automated Module Boundaries verification using ArchUnit/Spring Modulith

#### Improvements:
- Using Spring Modulith we can check for module boundaries automatically via tests.
- Spring Modulith provides support for Persistent Events for cross-module communication.

### Modular Monolith with Tomato Architecture
- Modular monolith using the [Tomato Architecture](https://tomato-architecture.github.io/)
- Used Rich Domain Types (Entities, Value Objects) instead of primitive types (String, Integer, etc.)
- Used JPA entities as Domain Models with behavior
- Used Spring Converters to automatically convert request parameters/body to domain objects
- Cross-module communication using Spring Modulith Persistent Events

#### Improvements:
- Using Value Objects helps in rejecting the invalid input parameters early so that we don't have to write defensive code all over the place.
- Domain specific logic is encapsulated in the domain models.
- Spring Converters make it easy to convert request parameters/body to domain objects.

### Modular Monolith with DDD and Hexagonal Architecture
- Modular monolith using the package-by-module approach
- Used Rich Domain Types (Entities, Value Objects) instead of primitive types (String, Integer, etc.)
- Used separate Domain Models and JPA entities for persistence
- Used Spring Converters to automatically convert request parameters/body to domain objects
- Cross module communication using Events
- ArchUnit test for verifying Hexagonal Architecture

#### Improvements:
- Clear separation of domain models and persistence models.
- Separate services for Command and Query operations. Enables to adopt CQRS pattern if needed. 
- Hexagonal Architecture helps in keeping the domain logic separate from the infrastructure.

## How to run?
Install the following prerequisites:

* JDK 25
* Docker and Docker Compose
* Your favourite IDE (Recommended: [IntelliJ IDEA](https://www.jetbrains.com/idea/))

Install JDK, Maven, Gradle, etc using [SDKMAN](https://sdkman.io/)

```shell
$ curl -s "https://get.sdkman.io" | bash
$ source "$HOME/.sdkman/bin/sdkman-init.sh"
$ sdk install java 25-tem
$ sdk install maven
```

Now you can test or run each application using the following commands:

```shell
# run tests for all modules
$ mvn clean verify

# run application/tests for a specific module
$ cd meetup4j-modulith-simple (or) cd meetup4j-modulith-tomato (or) cd meetup4j-modulith-ddd-ha
# run tests
$ mvn clean verify
# start application using Docker Compose
$ mvn spring-boot:run
# start application using Testcontainers
$ mvn spring-boot:test-run
```
