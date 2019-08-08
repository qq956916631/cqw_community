package com.cqw.community.util;

import com.alibaba.fastjson.JSON;
import com.cqw.community.domain.AccessTokenRequest;
import com.cqw.community.domain.GithubUser;
import okhttp3.*;

import java.io.IOException;

public class AuthorizeUtil {
    private AuthorizeUtil() {
    }

    public static String getAccessToken(AccessTokenRequest accessTokenRequest) {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        String json = com.alibaba.fastjson.JSON.toJSONString(accessTokenRequest);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String responseString = response.body().string();
            return responseString.split("&")[0].split("=")[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GithubUser getGithubUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        // Github提供的文档只是说明了curl
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            GithubUser githubUser = new GithubUser();
            String json = response.body().string();
            githubUser = JSON.parseObject(json, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
