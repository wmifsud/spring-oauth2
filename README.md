# Spring OAuth2

This system lets you retrieve a list of users provided you have the required permissions to do so.

Installation.

    1. Import postman OAUTH2_example.
    2. Run "mvn clean install -Pdocker"
    Note that tomcat is set to receive requests on port 9080.

How-to
Fetch new token from Spring Security OAuth2 server.

    /oauth/token?grant_type=password&username=u&password=p (METHOD = POST) Provides new token to fetch users from resource server.


Sample JSON Response

    {
      "access_token": "f5312457-6e5a-4526-90d3-ece33144c145",
      "token_type": "bearer",
      "refresh_token": "dfafb5f3-fdd8-4117-9fe0-5dae90b886c0",
      "expires_in": 119,
      "scope": "read write trust"
    }

Retrieving list of users with provided token.

    /user/?access_token=f5312457-6e5a-4526-90d3-ece33144c145 Retrieves list of users provided that token does not expire.

Sample JSON Response

    {
      "id": 1,
      "name": "Sam",
      "age": 30,
      "salary": 70000
    }

Retrieving list of users with provided token.

    /oauth/token?grant_type=refresh_token&refresh_token=796bb048-950b-4910-ad30-404eda3abb5e Retrieves new access token from refresh token hence, no need to re-authenticate until refresh token expires.

Sample JSON Response

    {
      "access_token": "7e2bcdb4-fdbd-42d4-92a6-b74d75332b45",
      "token_type": "bearer",
      "refresh_token": "796bb048-950b-4910-ad30-404eda3abb5e",
      "expires_in": 119,
      "scope": "read write trust"
    }