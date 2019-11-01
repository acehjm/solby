package me.solby.xtool.client;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * me.solby.itool.client
 *
 * @author majhdk
 * @date 2019-06-11
 */
class HttpClientUtilTest {

    @Test
    void sendDelete() {
        var uri = URI.create("http://localhost:8080/auth/userid?userId=001");
        var response = HttpClientUtil.syncSendDelete(uri,
                HttpClientUtil.getHeaderWithAuthToken("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9"));
        System.out.println(response.body());
        System.out.println(response.statusCode());
    }

    @Test
    void sendGet1() {
        var uri = URI.create("http://localhost:8080/auth/token");
        var headers = Map.of("token", "t001");

        var bodyHandler = HttpResponse.BodyHandlers.ofString();

        var response = HttpClientUtil.syncSendGet(uri, headers, bodyHandler);
        System.out.println(response.headers());
        System.out.println(response.body());
    }

    @Test
    void sendGet2() {
        var uri = URI.create("http://localhost:8080/auth/token");
        var response = HttpClientUtil.syncSendGet(uri,
                HttpClientUtil.getHeaderWithAuthToken("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9"),
                HttpResponse.BodyHandlers.ofString());
        System.out.println(response.headers());
        System.out.println(response.body());
    }

    @Test
    void header() {
        String[] header = Map.of("aa", "aaa", "bb", "bbb")
                .entrySet().stream()
                .map(it -> List.of(it.getKey(), it.getValue()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
                .toArray(new String[]{});
        System.out.println(Arrays.asList(header));
    }

    @Test
    void form() {
        var formStr = Map.of("name", "aa", "age", 18).entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
        System.out.println(formStr);
    }
}