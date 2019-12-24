package ru.alex.hub;

import java.util.Locale;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;

public class Caller {
    private final RequestBodyEntity body;

    public Caller() {
        Unirest.setTimeouts(10000, 60_000*5);
        HttpRequestWithBody httpRequestWithBody = Unirest
            .post("https://hub-beta.dreamkas.ru:443/viki/v1/get_min_retail_price/v1")
            .header("Content-Type", "application/json; charset=utf-8")
            .header("Accept-Language", Locale.getDefault().getLanguage())
            .basicAuth("viki", "Dms6RcU62YhFxKLPKdwYjQBqaZU7V4Wv");
        body = httpRequestWithBody.body("{\"uuid\":\"6d66e48f-f478-498d-8f2f-ea43073020c6\",\"data\":{\"v_code\":\"200\",\"alcohol_content\":40.0,\"alcohol_volume\":0.75,\"offset\":180}}");
    }

    public String call() {
        try {
            HttpResponse<String> response = body.asString();
            return response.getBody();
        } catch (UnirestException e) {
            return String.format("ERROR %s", e.getMessage());
        }
    }
}
