<h1>user-crud-app</h1>

User management CRUD application, backend based on Kotlin and frontend on React.js.
- User has name, email, age and a phone number. Required parameters to create a user are: name, email, phone number
- User age should be received from the endpoint: https://api.agify.io/?name=?

<strong>Run application</strong>
1) cd crud-kotlin-api
2) ./gradlew build
3) docker build -t hajiyev86/crud-app-back-end:0.0.1 .

4) cd ../curd-react-app
5) npm install
6) npm run build
7) docker build -t  hajiyev86/crud-app-front-end:0.0.1 .
8) cd ..
9) docker-compose up

<strong>backend</strong>
- For backend used Spring-Data-JPA, used H2 database.
- To make HTTP requests to the external API used RestTemplate. 
- I used caching(@Cacheable) to improve performance and reduce the number of external API requests 
- To enhance resilience and fault tolerance, I used circuit breaker pattern using the @CircuitBreaker.
- I added Search and Pagination functionality to filter, handle large datasets efficiently. 
- I wrote some unit and integration tests to ensure that the service functions correctly, handles various scenarios, and meets the specified requirements.
- Logging, localization, Docker

<strong>frontend</strong>
- Used Axios for making HTTP requests
- Used react-i18next to enable internationalization (i18n) allowing for localization and translation
- Used react-router-dom for handling routing and navigation.
- Used Validator for performing data validation
- Used mui for ui componments 
- React context, Docker, bootsrap 

