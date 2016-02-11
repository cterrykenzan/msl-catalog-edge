# Swagger generated server

## Overview
This server was generated by the [swagger-codegen](https://github.com/swagger-api/swagger-codegen) project. By using the 
[swagger-spec](https://github.com/swagger-api/swagger-core/wiki) from a remote server, you can easily generate a server stub.  This
is an example of building a swagger-enabled JAX-RS server.

This example uses the [JAX-RS](https://jax-rs-spec.java.net/) framework.

## Before starting
Run the build maven script to generate swagger generated code, package local jars and instal on local repository. Run this from the `/server` directory
```
mvn -f build-pom.xml install
```

## Useful commands 

To generate sources from swagger spec
```
mvn -P build clean generate-sources
```

To run the server on port `9003`, execute the following:
```
mvn -P dev clean jetty:run
```

To format code
```
mvn clean formatter:format
```

### Install dependencies without running tests 
```
mvn -P no-test clean install
```

### RPM packaging
```
mvn -P no-tests package && mvn rpm:rpm
```

##Reports
###Surefire reports:
```
mvn site
```
report gets generated under `/target/site/index.html`
 
###Cobertura
```
mvn cobertura:cobertura
```
report gets generated under `/target/site/cobertura/index.html`

###EclEmma 
```
mvn package
```
report gets generated under `/target/site/jacoco`

## Some dependencies required installation before running
- msl-models
- msl-account-data-client
- msl-catalog-data-client
- msl-ratings-data-client