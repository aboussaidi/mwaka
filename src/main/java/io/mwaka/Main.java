package io.mwaka;

import com.jcabi.aspects.Loggable;
import io.mwaka.model.Error;
import io.mwaka.recaptcha.Recaptcha;
import io.mwaka.service.UserService;
import io.mwaka.service.impl.UserServiceImpl;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

@Loggable
public class Main {
    final static Logger logger = LoggerFactory.getLogger(new ProcessBuilder().environment().get("DEBUG"));

    @Loggable(value = Loggable.DEBUG,prepend = true)
    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        staticFiles.location("public");
        get("/", (req, res) ->
                        new ModelAndView(null, "coming-soon.ftl"),
                new FreeMarkerEngine());

        get("/signup", (request, response) -> {
            return new ModelAndView(null, "signup.ftl");
        }, new FreeMarkerEngine());

        get("/signin", (request, response) -> {
            return new ModelAndView(null, "signin.ftl");
        }, new FreeMarkerEngine());

        post("/signin", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            try {
                Recaptcha recaptcha = new Recaptcha("6LcMHjsUAAAAAC3yg_aXZbitb6NZ6ccOh4cUrx1W",
                        request.queryMap("g-recaptcha-response").value(),
                        request.ip());
                if (!recaptcha.siteverify()) {
                    Error error = new Error("020", "There was an error with the recaptcha code, please try again.");
                    model.put("error", error);
                    return new ModelAndView(model, "signin.ftl");
                }
                UserService userService = new UserServiceImpl();
                userService.login(request.queryMap("email").value(),
                        request.queryMap("password").value());
                return new ModelAndView(null, "index.ftl");
            } catch (Exception e) {
                logger.error(e.getMessage());
                return new ModelAndView(null, "error.ftl");
            }
        }, new FreeMarkerEngine());


        post("/signup", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            try {
                Recaptcha recaptcha = new Recaptcha("6LcMHjsUAAAAAC3yg_aXZbitb6NZ6ccOh4cUrx1W",
                        request.queryMap("g-recaptcha-response").value(),
                        request.ip());
                if (!recaptcha.siteverify()) {
                    Error error = new Error("020", "There was an error with the recaptcha code, please try again.");
                    model.put("error", error);
                    return new ModelAndView(model, "signup.ftl");
                }
                UserService userService = new UserServiceImpl();
                userService.register(request.queryMap("email").value(),
                        request.queryMap("password").value(),
                        request.queryMap("first-name").value(),
                        request.queryMap("last-name").value());
                return new ModelAndView(null, "index.ftl");
            } catch (Exception e) {
                logger.error(e.getMessage());
                return new ModelAndView(null, "error.ftl");
            }
        }, new FreeMarkerEngine());

        post("/slack", (req, res) -> {
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            System.out.print("Body : " + req.body());
            RequestBody body = RequestBody.create(mediaType, req.body());
            Request request = new Request.Builder()
                    .url("https://hooks.slack.com/services/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")
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        });
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }
}