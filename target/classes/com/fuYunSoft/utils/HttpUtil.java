package com.fuYunSoft.utils;

import okhttp3.*;
import java.io.IOException;
import java.util.Map;

public class HttpUtil {
    private static final OkHttpClient client = new OkHttpClient();

    public static String get(String url, Map<String, String> params) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        params.forEach(urlBuilder::addQueryParameter);

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }
}