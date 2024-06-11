package cc.geektip.gateway.core.datasource.connection;

import cc.geektip.gateway.core.datasource.Connection;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;

/**
 * @description: HTTP 连接对象
 * @author: Fish
 * @date: 2024/5/26
 */
@Slf4j
public class HttpConnection implements Connection {

    private final OkHttpClient httpClient;
    private final Request.Builder requestBuilder;

    public HttpConnection(String uri) {
        httpClient = new OkHttpClient();
        requestBuilder = new Request.Builder()
                .url(uri)
                .addHeader("accept", "*/*")
                .addHeader("connection", "Keep-Alive")
                .addHeader("Content-Type", "application/json;charset=GBK")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
    }

    @Override
    public Object execute(String method, String[] parameterTypes, String[] parameterNames, Object[] args) {
        String res = "";
        try {
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), args[0].toString());
            Request request = requestBuilder.post(body).build();
            try (Response response = httpClient.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        res = response.body().string();
                    }
                }
            }
        } catch (IOException e) {
            log.error("HTTP Request Error", e);
        }
        return res;
    }

}