# 🚗🚗🚗🚗🚗
A sample project of a Car Rental web application.

# Getting Started

## How to start application
`cd` into the folder containing the project's `pom.xml` file, and enter the following into a terminal:
```
 $ mvn spring-boot:run
```

# Test
Tests can be found at [test/java/carrental/carrentalweb](https://github.com/niiicolai/CarRentalWeb/tree/main/src/test/java/carrental/carrentalweb).

## Test Database
The test environment is designed to use a seperate version of the original database. The configuration can be found at [DatabaseParameterResolver.java](https://github.com/niiicolai/CarRentalWeb/blob/main/src/test/java/carrental/carrentalweb/parameter_resolvers/DatabaseParameterResolver.java#L28-L32).

## Entity Factories
Find factory methods to create test data at [entity_factories](https://github.com/niiicolai/CarRentalWeb/tree/main/src/test/java/carrental/carrentalweb/entity_factories).

## Parameter Resolver
Parameter resolvers can be used to define instances of objects that Spring Boot should inject into test methods.

## How to execute tests
`cd` into the folder containing the project's `pom.xml` file, and enter the following into a terminal:
```
$ mvn test
```
Sample results:
```
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.099 s - in carrental.carrentalweb.service.UserServiceTest
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 68, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  9.618 s
[INFO] Finished at: 2022-12-12T20:48:23+01:00
[INFO] ------------------------------------------------------------------------
```

# Deploy

## How to deploy
`cd` into the folder containing the project's `pom.xml` file, and enter the following into a terminal:
```
$ mvn package azure-webapp:deploy
```