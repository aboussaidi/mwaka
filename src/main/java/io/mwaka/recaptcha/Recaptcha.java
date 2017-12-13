package io.mwaka.recaptcha;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Getter
@Setter
public class Recaptcha {

    private final static Logger logger = LoggerFactory.getLogger("heroku");

    private String secret;
    private String response;
    private String remoteip;

    public Recaptcha(String secret, String response, String remoteip) {
        this.secret = secret;
        this.response = response;
        this.remoteip = remoteip;
    }

    public Boolean siteverify() {
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody recaptchaForm = new FormBody.Builder()
                .add("secret", getSecret())
                .add("response", getResponse())
                .add("remoteip", getRemoteip())
                .build();
        Request request = new Request.Builder()
                .url("https://www.google.com/recaptcha/api/siteverify")
                .post(recaptchaForm)
                .build();
        try {
            Response response = client.newCall(request).execute();
            ObjectNode node = new ObjectMapper().readValue(response.body().string(), ObjectNode.class);
            if (node.get("success").asBoolean()) {
                return true;
            } else {
                logger.error(node.get("error-codes").asText());
                return false;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}
