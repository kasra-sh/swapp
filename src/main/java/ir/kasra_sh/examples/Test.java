package ir.kasra_sh.examples;

import ir.kasra_sh.swapp.*;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Test extends SwappModule {
    {
        get("/sync/get", ((r, ex) -> {
                return Responses.ok("This is a Synchronous Response!\n");
        }));

        post("/test", ((r, ex) -> {
            return Responses.json(200, new String(r.getBody()));
        }));

        asyncGet("/async/get", (r, ex) -> {
            return Responses.ok("This is an Asynchronous Response!\n");
        });

        post("/async/post", (r, ex) -> {
            // Fire an async task to respond when the request is processed
            Async.execute(r, ex.getWriter(), () -> {

                String body = (r.hasBody()?new String(r.getBody()):"");

                return Responses.ok("I am a simple [Async] response! (POST):\n" +
                        body+"\n");
            });

            // return null because response is handled by your task
            return null;
        });

        // /home/USER/uploads/index.html -> will be served by IP:PORT/static/index.html
        staticAsset("/static", "/home/USER/uploads", null);

        // Start server with [Number Of Processors] workers
        new Swapp().addCustomHeader("Access-Control-Allow-Origin","*").addModule(this).start(5001);

    }

    public static void main(String[] args) {
        new Test();
    }

}
