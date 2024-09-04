# MDD
MDD (Monde de Dev) is a developer-focused social network by ORION, designed to connect developers, help with job searches, and support company recruitment. The MVP allows users to follow programming topics, view related articles, write posts, and comment.

## Technologies used

### Front-end
![Angular](https://img.shields.io/badge/angular-18-%23DD0031.svg?style=for-the-badge&logo=angular&logoColor=white&labelColor=%23DD0031&color=gray) &nbsp;
![Angular Material](https://img.shields.io/badge/Angular%20Material-18.2.0-009688.svg?style=for-the-badge&logo=angular&logoColor=white&labelColor=196FCD&color=gray)

### Back-end
![Java](https://img.shields.io/badge/Java-17-007396.svg?style=for-the-badge&logo=openjdk&logoColor=white&labelColor=007396&color=gray) &nbsp;
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.2-6DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white&labelColor=6DB33F&color=gray) &nbsp;
![Maven](https://img.shields.io/badge/Maven-4.0.0-C71A36.svg?style=for-the-badge&logo=apachemaven&logoColor=white&labelColor=C71A36&color=gray) &nbsp;
![MySQL](https://img.shields.io/badge/MySQL-8.0.33-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white&labelColor=4479A1&color=gray) &nbsp;
![Springdoc OpenAPI](https://img.shields.io/badge/Springdoc%20OpenAPI-2.5.0-85EA2D.svg?style=for-the-badge&logo=openapiinitiative&logoColor=white&labelColor=85EA2D&color=gray) &nbsp;
![JJWT](https://img.shields.io/badge/JJWT-0.11.5-ED1C24.svg?style=for-the-badge&logo=jsonwebtokens&logoColor=white&labelColor=ED1C24&color=gray) &nbsp;
![Lombok](https://img.shields.io/badge/Lombok-1.18.28-FF6347.svg?style=for-the-badge&logo=lombok&logoColor=white&labelColor=FF6347&color=gray)

### Spring Boot Starter Dependencies
![Data JPA](https://img.shields.io/badge/Data%20JPA-6DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white&labelColor=6DB33F&color=gray) &nbsp;
![Web](https://img.shields.io/badge/Web-6DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white&labelColor=6DB33F&color=gray) &nbsp;
![Validation](https://img.shields.io/badge/Validation-6DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white&labelColor=6DB33F&color=gray) &nbsp;
![OAuth2 Resource Server](https://img.shields.io/badge/OAuth2%20Resource%20Server-6DB33F.svg?style=for-the-badge&logo=springsecurity&logoColor=white&labelColor=6DB33F&color=gray) &nbsp;
![Security](https://img.shields.io/badge/Security-6DB33F.svg?style=for-the-badge&logo=springsecurity&logoColor=white&labelColor=6DB33F&color=gray)

## Getting Started

### Clone the Project

>git clone https://github.com/guillaumebeysson/MDD.git

### MySQL
- Install MySQL
- Connect to MySQL with your root user
- Run `CREATE DATABASE mdd; USE mdd;`

### Back-end
- Open directory `/MDD/back` in your IDE
- Change environment variables `${DB_USERNAME}` `${DB_PASSWORD}` with the same username and password than you defined in your MySQL
- Run `mvn spring-boot:run` to start the back-end. Hibernate will automatically create the tables based on the entity definitions.
- Go to `/MDD/back/src/main/resources/sql` and execute all queries in `populate.sql` to populate your database (users, topics, posts, etc...)

### Swagger Documentation
[![Swagger](https://img.shields.io/badge/Swagger-Link-green.svg?style=for-the-badge&logo=swagger&logoColor=white&labelColor=85EA2D&color=gray)](http://localhost:8080/swagger-ui/index.html)

### Front-end
- Open directory `/MDD/front` in your IDE
- Install the dependencies: `npm install`
- Build the project: `ng build`
- Start the front-end development server: `ng serve`
- To use the app, navigate to: http://localhost:4200
- To test the app, you can create a new user or use existing credentials: 

![USERNAME](https://img.shields.io/badge/USERNAME-Guillaume-blue.svg?style=flat-square&color=blue&labelColor=gray) &nbsp;
![PASSWORD](https://img.shields.io/badge/PASSWORD-Coucou12@-red.svg?style=flat-square&color=red&labelColor=gray)