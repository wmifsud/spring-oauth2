package com.oauth2;

import com.oauth2.entity.Person;
import com.oauth2.entity.Student;
import com.oauth2.entity.Teacher;
import com.oauth2.model.AuthTokenInfo;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class SpringRestClient
{

    private static final String REST_SERVICE_URI = "http://localhost:9080/SpringOAuth2";

    private static final String AUTH_SERVER_URI = "http://localhost:9080/SpringOAuth2/oauth/token";

    private static final String QPM_PASSWORD_GRANT = "?grant_type=password&username=bill&password=abc123";

    private static final String QPM_ACCESS_TOKEN = "?access_token=";

    /*
     * Prepare HTTP Headers.
     */
    private static HttpHeaders getHeaders()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    /*
     * Add HTTP Authorization header, using Basic-Authentication to send client-credentials.
     */
    private static HttpHeaders getHeadersWithClientCredentials()
    {
        String plainClientCredentials = "my-trusted-client:secret";
        String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));

        HttpHeaders headers = getHeaders();
        headers.add("Authorization", "Basic " + base64ClientCredentials);
        return headers;
    }

    /*
     * Send a POST request [on /oauth/token] to get an access-token, which will then be send with each request.
     */
    @SuppressWarnings({ "unchecked" })
    private static AuthTokenInfo sendTokenRequest()
    {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(getHeadersWithClientCredentials());
        ResponseEntity<Object> response = restTemplate.exchange(AUTH_SERVER_URI + QPM_PASSWORD_GRANT, HttpMethod.POST, request, Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
        AuthTokenInfo tokenInfo = null;

        if (map != null)
        {
            tokenInfo = new AuthTokenInfo();
            tokenInfo.setAccess_token((String) map.get("access_token"));
            tokenInfo.setToken_type((String) map.get("token_type"));
            tokenInfo.setRefresh_token((String) map.get("refresh_token"));
            tokenInfo.setExpires_in((Integer) map.get("expires_in"));
            tokenInfo.setScope((String) map.get("scope"));
            System.out.println(tokenInfo);
            //System.out.println("access_token ="+map.get("access_token")+", token_type="+map.get("token_type")+", refresh_token="+map.get("refresh_token")
            //+", expires_in="+map.get("expires_in")+", scope="+map.get("scope"));;
        }
        else
        {
            System.out.println("No user exist----------");
        }
        return tokenInfo;
    }

    /*
     * Send a GET request to get list of all persons.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static void listAllPersons(AuthTokenInfo tokenInfo)
    {
        Assert.notNull(tokenInfo, "Authenticate first please......");

        System.out.println("\nTesting listAllPersons API-----------");
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        ResponseEntity<List> response = restTemplate.exchange(REST_SERVICE_URI + "/person/" + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(),
                                                              HttpMethod.GET, request, List.class);
        List<LinkedHashMap<String, Object>> personsMap = (List<LinkedHashMap<String, Object>>) response.getBody();

        if (personsMap != null)
        {
            for (LinkedHashMap<String, Object> map : personsMap)
            {
                if (map.get("personType").equals("STUDENT")) {
                    System.out.println("Student : id=" + map.get("id") + ", Name=" + map.get("name") + ", Surname=" + map.get("surname") + ", ID=" + map.get("idCard") + ", CurrentYear=" + map.get("currentYear") + ", LessonsPerWeek=" + map.get("lessonsPerWeek"));
                }
                else {
                    System.out.println("Teacher : id=" + map.get("id") + ", Name=" + map.get("name") + ", Surname=" + map.get("surname") + ", ID=" + map.get("idCard") + ", subject=" + map.get("subject"));
                }
            }
        }
        else
        {
            System.out.println("No user exist----------");
        }
    }

    /*
     * Send a GET request to get a specific person.
     */
    private static void getPerson(AuthTokenInfo tokenInfo)
    {
        Assert.notNull(tokenInfo, "Authenticate first please......");
        System.out.println("\nTesting getPerson API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        ResponseEntity<Person> response = restTemplate.exchange(REST_SERVICE_URI + "/person/1" + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(),
                                                              HttpMethod.GET, request, Person.class);
        Person person = response.getBody();
        System.out.println(person);
    }

    /*
     * Send a POST request to create a new person.
     */
    private static void createPerson(AuthTokenInfo tokenInfo)
    {
        Assert.notNull(tokenInfo, "Authenticate first please......");
        System.out.println("\nTesting create Person API----------");
        RestTemplate restTemplate = new RestTemplate();
        Teacher teacher = new Teacher();
        teacher.setName("Carl");
        teacher.setSurname("Xuereb");
        teacher.setIdCard("111111M");
        teacher.setSubject("Physics");

        HttpEntity<Object> request = new HttpEntity<>(teacher, getHeaders());
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/person/create" + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(),
                                               request, Teacher.class);
        System.out.println("Location : " + uri.toASCIIString());
    }

    /*
     * Send a PUT request to update an existing person.
     */
    private static void updatePerson(AuthTokenInfo tokenInfo)
    {
        Assert.notNull(tokenInfo, "Authenticate first please......");
        System.out.println("\nTesting update Person API----------");
        RestTemplate restTemplate = new RestTemplate();
        Student student = new Student();
        student.setName("Wayne");
        student.setSurname("Stellini");
        student.setIdCard("999999M");
        HttpEntity<Object> request = new HttpEntity<>(student, getHeaders());
        ResponseEntity<Student> response = restTemplate.exchange(REST_SERVICE_URI + "/person/1" + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(),
                                                              HttpMethod.PUT, request, Student.class);
        System.out.println(response.getBody());
    }

    /*
     * Send a DELETE request to delete a specific person.
     */
    private static void deletePerson(AuthTokenInfo tokenInfo)
    {
        Assert.notNull(tokenInfo, "Authenticate first please......");
        System.out.println("\nTesting delete User API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        restTemplate.exchange(REST_SERVICE_URI + "/person/3" + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(),
                              HttpMethod.DELETE, request, Person.class);
    }


    /*
     * Send a DELETE request to delete all persons.
     */
    private static void deleteAllPersons(AuthTokenInfo tokenInfo)
    {
        Assert.notNull(tokenInfo, "Authenticate first please......");
        System.out.println("\nTesting all delete Users API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        restTemplate.exchange(REST_SERVICE_URI + "/person/" + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(),
                              HttpMethod.DELETE, request, Person.class);
    }

    public static void main(String args[])
    {
        AuthTokenInfo tokenInfo = sendTokenRequest();
        listAllPersons(tokenInfo);

        getPerson(tokenInfo);

        createPerson(tokenInfo);
        listAllPersons(tokenInfo);

        updatePerson(tokenInfo);
        listAllPersons(tokenInfo);

        deletePerson(tokenInfo);
        listAllPersons(tokenInfo);

        deleteAllPersons(tokenInfo);
        listAllPersons(tokenInfo);
    }
}