# Roche home assignment

### Installation

This project requires JDK 8 and maven for building and testing it.

Install Java on linux using this command:

    sudo apt-get install openjdk-8-jdk

## Install maven

    sudo apt-get install maven

## How to build

Note (limitation): you need to have a mongo instance running to build. This can be avoided by using an embedded Mongo
instance but for time limit I have been using a docker-compose to fire up mongo

    cd src/test/resources/com/roche/ddd/
    docker-compose up

    mvn clean package

## Code coverage is executed by Jacoco in the build process.

    mvn clean install

The results are in an HTML page generated under :

    target/site/jacoco/index.html

## How to test

    mvn clean test

Test results are located at:

    target/surefire-reports

## Design

The implementation is an exercise of DDD implementation in hexagonal architecture and for the sake of making more interesting the Product CRUD has been implemented with a JPA database whitke the Order by a MongoDB that is more
suited for aggregate implementation being the aggregate defined as  a group of business objects which always need to be consistent. And matter of fact an Order is and collection of product that compose the order and so it needs to
be managed as a single transactional unit.

Hexagonal architecture is a model of designing software applications around domain logic to isolate it from external factors.

The domain logic is specified in a business core, which we'll call the inside part, the rest being outside parts. Access to domain logic from the outside is available through ports and adapters.

This exercise into three layers; application (outside), domain (inside), and infrastructure (outside):

Through the application layer, the user or any other program interacts with the application. This area should contain things like user interfaces, RESTful controllers, and JSON serialization libraries. It includes anything that exposes entry to our application and orchestrates the execution of domain logic.

In the domain layer, we keep the code that touches and implements business logic. This is the core of our application. Additionally, this layer should be isolated from both the application part and the infrastructure part. On top of that, it should also contain interfaces that define the API to communicate with external parts, like the database, which the domain interacts with.

I have used a normal CRUD for the Product and aggregate logic for the orders: Mongo document == Order aggregate.

Lastly, the infrastructure layer is the part that contains anything that the application needs to work such as database configuration or Spring configuration. Besides, it also implements infrastructure-dependent interfaces from the domain layer.

Lastly, the infrastructure layer is the part that contains anything that the application needs to work such as database configuration or Spring configuration. Besides, it also implements infrastructure-dependent interfaces from the domain layer.

In our exercise it implements the access to the JPA database for Products and the document based NO-SQL database Mongo for the Orders.

In term of java patterns used, the decoupling of the layer is implemented by interfaces and adapters.


The feature set includes:

* Swagger definition :

    http://localhost:8080/swagger-ui.html

* Stacked layer design composed of:

    - 2.3.5.RELEASE (Java)
    - controller layer spring MVC with RestControllers
    - service layer
    - spring data repositories (JPA/Mongo)
    - hibernate persistence
    - DTO beans for rest i/o

## how to run it:

    java -jar target/roche-0.0.1.jar
    (make sure port 8080 is available)

# Limitations !!!

The code is not in product ready status for the simple reason that it has not been code reviewed and not passed proper dev-qa
I could not make the in between Date range working. Too tired.
I had no time to add a cucumber layer to implement the BDD testing. 

