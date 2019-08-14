package me.solby.xboot;

import me.solby.xtool.json.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class WebSecurityConfigTest {

    @LocalServerPort
    int port;
    private TestRestTemplate restTemplate;
    private URL base;

    @Before
    public void setUp() throws MalformedURLException {
        restTemplate = new TestRestTemplate("user001", "001");

        base = new URL("http://localhost:" + port);
    }

    @Test
    public void whenUserHasAuth_ThenSuccess() throws IllegalStateException, URISyntaxException {
        ResponseEntity<String> tokenRsp = restTemplate.getForEntity(base.toString() + "/auth/token", String.class);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authentication", "Bearer " + tokenRsp.getBody());

        URI uri = new URI("http://localhost:" + port + "/test/success");
        RequestEntity entity = new RequestEntity(httpHeaders, HttpMethod.GET, uri);

        ResponseEntity<String> response = restTemplate.exchange(entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("test"));
    }

    @Test
    public void whenUserNoAuth_ThenFailure() throws IllegalStateException {
        ResponseEntity<String> response = restTemplate.getForEntity(base.toString() + "/test/failure", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("noauth"));
    }

    @Test
    public void whenUserWithWrongCredentials_thenUnauthorizedPage() {
        restTemplate = new TestRestTemplate("user001", "wrongpassword");
        ResponseEntity<String> response = restTemplate.getForEntity(base.toString(), String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Unauthorized"));
    }

    @Test
    public void testGetToken() {
        ResponseEntity<String> response = restTemplate.getForEntity(base.toString() + "/auth/token", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(JsonUtil.toJson(response));
    }
}
