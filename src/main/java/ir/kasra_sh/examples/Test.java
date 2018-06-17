package ir.kasra_sh.examples;

import ir.kasra_sh.swapp.Async;
import ir.kasra_sh.swapp.Responses;
import ir.kasra_sh.swapp.Swapp;
import ir.kasra_sh.swapp.SwappModule;

public class Test extends SwappModule {
    {
        get("/sync/get", ((r, ex) -> Responses.ok("I am a simple sync response! (GET)\n")));

        post("/test", ((r, ex) -> {
            return Responses.json(200, new String(r.getBody()));
        }));

        get("/async/get", (r, ex) -> {
            Async.execute(r, ex.getWriter(), () -> Responses.ok("I am a simple [Async] response! (GET)\n"));
            return null;
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
        staticAsset("/","/home/USER/uploads", null);

        // Start server with [Number Of Processors] workers
        new Swapp().addCustomHeader("Access-Control-Allow-Origin","*").addModule(this).start(5001);

    }

    public static void main(String[] args) {
        new Test();
    }

}
