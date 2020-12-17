[![Build Status](https://travis-ci.org/HerbMat/bank-application.svg?branch=main)](https://travis-ci.org/HerbMat/bank-application)
# Bank application
## Introduction
Example project for Digital Colliers. It is application which operates on three entities. 
Account Type, Customer and Transaction. It allows to search transactions for given criteria.
It is protected by basic auth
## Prerequisites
* Git
* Java 11
* Docker + docker-compose
## Necessary configuration
In application.yml we have to specify:
```
spring:
  data:
    mongodb:
      database: #database name
      host: #database host
      port: #database port
  security:
    user:
      name: #username
      password: #user password
```
## Running application
```
./gradlew bootRun
```
## Running tests
```
./gradlew check
```
## Running mongodb image
```
docker-compose up mongo
```

## Loading initial data on startup
We have to add to application.yml
```
bank-application:
  mongo:
    load-data: true
```
