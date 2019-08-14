package me.solby.xtool.client;

import me.solby.xtool.exception.HttpClientException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * JDK HttpClient工具类
 *
 * @author majhdk
 * @date 2019-06-11
 */
public class HttpClientUtil {

    /**
     * HttpClient
     */
    private static HttpClient httpClient;

    static {
        httpClient = HttpClient.newBuilder()
                //Http协议
                .version(HttpClient.Version.HTTP_2)
                //是否允许转发
                .followRedirects(HttpClient.Redirect.NORMAL)
                //连接超时时间
                .connectTimeout(Duration.ofMillis(5000))
                //代理
                .proxy(ProxySelector.getDefault())
                //HTTP/2 priority
                .priority(1)
                //认证
//                .authenticator(Authenticator.getDefault())
                //cookie处理
//                .cookieHandler(CookieHandler.getDefault())
                //连接池
                .executor(Executors.newFixedThreadPool(3))
                .build();
    }

    private HttpClientUtil() {
    }

    /*Concurrent GET------------------------------------------------------------------*/

    /**
     * 并发发送GET请求
     *
     * @param uriList uri列表
     * @param headers 请求头
     * @return List
     */
    public static List<CompletableFuture<HttpResponse<String>>> sendConcurrentGet(final List<URI> uriList,
                                                                                  final Map<String, String> headers) {
        var requests = uriList.stream()
                .map(uri -> HttpRequest.newBuilder(uri)
                        .headers(getHeaders(headers))
                        .build())
                .collect(Collectors.toList());

        return requests.stream()
                .map(request -> httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()))
                .collect(Collectors.toList());
    }

    /*FILE----------------------------------------------------------------------------*/

    /**
     * 上传文件
     *
     * @param uri      构造uri
     * @param filePath 文件类路径
     * @return HttpResponse
     */
    public static HttpResponse<String> uploadFile(final URI uri, final String filePath) {
        try {
            var request = HttpRequest.newBuilder()
                    .uri(uri)
                    .POST(HttpRequest.BodyPublishers.ofFile(Paths.get(filePath)))
                    .build();

            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (FileNotFoundException e) {
            throw new HttpClientException("文件不存在", e);
        } catch (InterruptedException | IOException e) {
            throw new HttpClientException("远程调用失败", e);
        }
    }

    /**
     * 下载文件
     *
     * @param uri      构造uri
     * @param savePath 文件保存路径
     * @return HttpResponse
     */
    public static HttpResponse<Path> downloadFile(final URI uri, final Path savePath) {
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();

        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofFile(savePath));
        } catch (IOException | InterruptedException e) {
            throw new HttpClientException("远程调用失败", e);
        }
    }

    /*POST----------------------------------------------------------------------------*/

    /**
     * 同步发送POST请求
     *
     * @param uri         构造uri
     * @param headers     请求头
     * @param requestBody json参数
     * @return HttpResponse
     */
    public static HttpResponse<String> syncSendPostWithJson(final URI uri, final Map<String, String> headers,
                                                            final String requestBody) {
        var request = postRequest(uri, headers, requestBody);
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new HttpClientException("远程调用失败", e);
        }
    }

    /**
     * 异步发送POST请求
     *
     * @param uri         构造uri
     * @param headers     请求头
     * @param requestBody json参数
     * @return HttpResponse
     */
    public static CompletableFuture<HttpResponse<String>> asyncSendPostWithJson(final URI uri, final Map<String, String> headers,
                                                                                final String requestBody) {
        var request = postRequest(uri, headers, requestBody);
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * 同步发送POST请求(Form)
     *
     * @param uri     构造uri
     * @param headers 请求头
     * @param form    表单参数
     * @return HttpResponse
     */
    public static HttpResponse<String> syncSendPostWithForm(final URI uri, final Map<String, String> headers,
                                                            final Map<String, String> form) {
        var request = postRequest(uri, headers, getForm(form));
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new HttpClientException("远程调用失败", e);
        }
    }

    /**
     * 异步发送POST请求(Form)
     *
     * @param uri     构造uri
     * @param headers 请求头
     * @param form    表单参数
     * @return HttpResponse
     */
    public static CompletableFuture<HttpResponse<String>> asyncSendPostWithForm(final URI uri, final Map<String, String> headers,
                                                                                final Map<String, String> form) {
        var request = postRequest(uri, headers, getForm(form));
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * 获取POST请求
     *
     * @param uri     构造uri
     * @param headers 请求头
     * @return HttpRequest
     */
    private static HttpRequest postRequest(final URI uri, final Map<String, String> headers, final String requestBody) {
        return HttpRequest.newBuilder()
                .uri(uri)
                .headers(getHeaders(headers))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }

    /*PUT-----------------------------------------------------------------------------*/

    /**
     * 同步发送PUT请求(Json)
     *
     * @param uri         构造uri
     * @param headers     请求头
     * @param requestBody json参数
     * @return HttpResponse
     */
    public static HttpResponse<String> syncSendPutWithJson(final URI uri, final Map<String, String> headers,
                                                           final String requestBody) {
        var request = putRequest(uri, headers, requestBody);
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new HttpClientException("远程调用失败", e);
        }
    }

    /**
     * 异步发送PUT请求(Json)
     *
     * @param uri         构造uri
     * @param headers     请求头
     * @param requestBody json参数
     * @return HttpResponse
     */
    public static CompletableFuture<HttpResponse<String>> asyncSendPutWithJson(final URI uri, final Map<String, String> headers,
                                                                               final String requestBody) {
        var request = putRequest(uri, headers, requestBody);
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * 同步发送PUT请求(Form)
     *
     * @param uri     构造uri
     * @param headers 请求头
     * @param form    表单参数
     * @return HttpResponse
     */
    public static HttpResponse<String> syncSendPutWithForm(final URI uri, final Map<String, String> headers,
                                                           final Map<String, String> form) {
        var request = putRequest(uri, headers, getForm(form));
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new HttpClientException("远程调用失败", e);
        }
    }

    /**
     * 异步发送PUT请求(Form)
     *
     * @param uri     构造uri
     * @param headers 请求头
     * @param form    表单参数
     * @return HttpResponse
     */
    public static CompletableFuture<HttpResponse<String>> asyncSendPutWithForm(final URI uri, final Map<String, String> headers,
                                                                               final Map<String, String> form) {
        var request = putRequest(uri, headers, getForm(form));
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * 获取PUT请求
     *
     * @param uri     构造uri
     * @param headers 请求头
     * @return HttpRequest
     */
    private static HttpRequest putRequest(final URI uri, final Map<String, String> headers, final String requestBody) {
        return HttpRequest.newBuilder()
                .uri(uri)
                .headers(getHeaders(headers))
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }

    /*DELETE--------------------------------------------------------------------------*/

    /**
     * 同步发送删除请求
     *
     * @param uri     构造uri
     * @param headers 请求头
     * @return HttpResponse
     */
    public static HttpResponse<String> syncSendDelete(final URI uri, final Map<String, String> headers) {
        var request = deleteRequest(uri, headers);
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new HttpClientException("远程调用失败", e);
        }
    }

    /**
     * 异步发送删除请求
     *
     * @param uri     构造uri
     * @param headers 请求头
     * @return HttpResponse
     */
    public static CompletableFuture<HttpResponse<String>> asyncSendDelete(final URI uri, final Map<String, String> headers) {
        var request = deleteRequest(uri, headers);
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * 获取DELETE请求
     *
     * @param uri     构造uri
     * @param headers 请求头
     * @return HttpRequest
     */
    private static HttpRequest deleteRequest(final URI uri, final Map<String, String> headers) {
        return HttpRequest.newBuilder()
                .uri(uri)
                .headers(getHeaders(headers))
                .DELETE()
                .build();
    }

    /*GET------------------------------------------------------------------------------*/

    /**
     * 同步发送GET请求
     *
     * @param uri         构造uri
     * @param headers     请求头
     * @param bodyHandler 相应体处理
     * @param <T>
     * @return HttpResponse
     */
    public static <T> HttpResponse<T> syncSendGet(final URI uri, final Map<String, String> headers,
                                                  final HttpResponse.BodyHandler<T> bodyHandler) {
        var request = getRequest(uri, headers);
        try {
            return httpClient.send(request, bodyHandler);
        } catch (IOException | InterruptedException e) {
            throw new HttpClientException("远程调用失败", e);
        }
    }

    /**
     * 异步发送GET请求
     *
     * @param uri         构造uri
     * @param headers     请求头
     * @param bodyHandler 相应体处理
     * @param <T>
     * @return HttpResponse
     */
    public static <T> CompletableFuture<HttpResponse<T>> asyncSendGet(final URI uri, final Map<String, String> headers,
                                                                      final HttpResponse.BodyHandler<T> bodyHandler) {
        var request = getRequest(uri, headers);
        return httpClient.sendAsync(request, bodyHandler);
    }

    /**
     * 获取GET请求
     *
     * @param uri     构造uri
     * @param headers 请求头
     * @return HttpRequest
     */
    private static HttpRequest getRequest(final URI uri, final Map<String, String> headers) {
        return HttpRequest.newBuilder()
                .uri(uri)
                .headers(getHeaders(headers))
                .GET()
                .build();
    }

    /*Header------------------------------------------------------------------------------*/

    /**
     * 生成Header
     *
     * @param headers 请求头
     * @return 字符串数组
     */
    private static String[] getHeaders(final Map<String, String> headers) {
        return headers.entrySet().stream()
                .map(it -> List.of(it.getKey(), it.getValue()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
                .toArray(new String[]{});
    }

    /**
     * Header中生成Token
     *
     * @param token 访问令牌
     * @return 键值对
     */
    public static Map<String, String> getHeaderWithAuthToken(final String token) {
        return Map.of("Authorization", "Bearer " + token);
    }

    /**
     * 生成Header
     * {Authorization, Bearer ...}
     * {Content-Type,application/x-www-form-urlencoded}
     *
     * @param token 访问令牌
     * @return 键值对
     */
    public static Map<String, String> getHeaderWithAuthTokenAndForm(final String token) {
        return Map.of("Authorization", "Bearer " + token,
                "Content-Type", "application/x-www-form-urlencoded");
    }

    /**
     * 生成Header
     * {Authorization, Bearer ...}
     * {Content-Type,application/json}
     *
     * @param token 访问令牌
     * @return 键值对
     */
    public static Map<String, String> getHeaderWithAuthTokenAndJson(final String token) {
        return Map.of("Authorization", "Bearer " + token,
                "Content-Type", "application/json");
    }

    /*other-------------------------------------------------------------------------------*/

    /**
     * 获取表单
     *
     * @param form 表单
     * @return 字符串
     */
    private static String getForm(final Map<String, String> form) {
        return form.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }

}
