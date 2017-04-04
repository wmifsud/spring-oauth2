# spring-oauth2 example
poc for spring-oauth2 rest

1. Execute schema.sql file on localhost postgres database.
2. Import postman OAUTH2_example.
3. Run mvn clean install.
4. Start application by running ./target/spring-oauth2.jar
5. First call fetchToken method from postmane which will give you a token.
6. Use provided token to fetch users.