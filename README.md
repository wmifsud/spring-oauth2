# spring-oauth2 example
poc for spring-oauth2 rest

1. Execute schema.sql file on localhost postgres database.
2. Import postman OAUTH2_example.
3. Run mvn clean install.
4. Start application by running ./target/spring-oauth2.jar
5. First call FetchToken method from postman which will give you a token.
6. Use provided token to call FetchUsers.
7. Upon token expiry, you can use RefreshExpiredToken call to generate new token without the need to re-authenticate via the FetchToken call.