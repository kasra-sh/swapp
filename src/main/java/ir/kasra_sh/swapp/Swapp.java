package ir.kasra_sh.swapp;

import ir.kasra_sh.picohttpd.http.request.HTTPMethod;
import ir.kasra_sh.picohttpd.http.request.Request;
import ir.kasra_sh.picohttpd.http.response.Response;
import ir.kasra_sh.picohttpd.server.Pico;
import ir.kasra_sh.picohttpd.server.interfaces.HTTPHandler;
import ir.kasra_sh.picohttpd.server.nio.ResponseWriter;
import ir.kasra_sh.picohttpd.utils.NanoLogger;
import ir.kasra_sh.swapp.routing.MatchedRoute;
import ir.kasra_sh.swapp.routing.Route;
import ir.kasra_sh.swapp.routing.Router;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

import static ir.kasra_sh.picohttpd.http.response.ResponseCode.METHOD_NOT_ALLOWED;


public final class Swapp {
    private Pico pico = new Pico();
    private boolean debugMode = true;
    private ExceptionHandler exceptionHandler;
    private boolean started = false;
    private Router router = new Router();
    private HashMap<String, String> cHeaders =new HashMap<>();
    private Set<Map.Entry<String,String>> cHeaderSet = new HashSet<>();

    public final void start(int port) {
        start(port, 0);
    }

    public final void start(int port, int workers) {
        if (started) return;
        started = true;
        if (workers<=0) {
            workers = Runtime.getRuntime().availableProcessors();
        }
        pico.setHandler(new SwappHandler(router));
        try {
            if (workers>0) pico.setExecutor(new ForkJoinPool(workers));
            pico.start(port);
            NanoLogger.i("Nano",
                    "Started on " + port + ", "
                            + workers + " workers"
            );
        } catch (IOException e) {
            started = false;
            e.printStackTrace();
        }
    }

    public final Swapp debugOn() {
        debugMode = true;
        return this;
    }

    public final Swapp debugOff() {
        debugMode = false;
        return this;
    }

    public final Swapp logTo(String filePath) throws FileNotFoundException {
        Log.setOutput(new FileOutputStream(filePath));
        return this;
    }

    public final Swapp addCustomHeader(String key, String value){
        cHeaders.remove(key);
        cHeaders.put(key,value);
        cHeaderSet = cHeaders.entrySet();
        return this;
    }

    public final Swapp addModule(SwappModule module) {
        for (Map.Entry<Route, BaseHandler> rh: module.getModulePaths().entrySet()){
            router.addRoute(rh.getKey(), rh.getValue());
        }
        return this;
    }

    public Swapp() {
        exceptionHandler = new ExceptionHandler() {
            @Override
            public Response handle(Request r, Extras ex, Throwable t, boolean showException) {
                StringBuilder err = new StringBuilder(2048);
                err.append("<h2>500 - Internal Server Error</h2>");
                if (showException) {
                    err.append("<h3>> Message: ").append(t.toString()).append("</h3>");
                    StackTraceElement[] stackTrace = t.getStackTrace();
                    for (int i = 0, stackTraceLength = stackTrace.length; i < stackTraceLength; i++) {
                        StackTraceElement ste = stackTrace[i];
                        err.append("<i>").append("[").append(i).append("]").append("> StackTrace : ").append(ste.toString()).append("</i></br></br>");
                    }
                }
                Log.e("SwappModule", r.getAddress()+" - "+r.getUrl()+" - 500");
                return Responses.html(500, err.toString());
            }
        };
    }

    private class SwappHandler implements HTTPHandler {

        private Router router;

        public SwappHandler(Router router) {
            this.router = router;
        }

        @Override
        public void handleRequest(Request request, ResponseWriter responseWriter) {
            MatchedRoute mr = null;
            try {
                mr = router.route(request.getUrl());
                if (mr == null) {
                    reqLog(404, request.getAddress().toString(), request.getUrl(), request.method());
                    responseWriter.write(injectCustom(ErrorHandlers.err404.handle(request, null)));
                } else {
                    if (!mr.getRoute().getMethods().contains(request.method()) && !mr.getRoute().getMethods().isEmpty()) {
//                        Log.i("Route", "405 - "+request.method());
                        reqLog(METHOD_NOT_ALLOWED, request.getAddress().toString(), request.getUrl(), request.method());
                        responseWriter.write(injectCustom(Responses.err(METHOD_NOT_ALLOWED, "405 - Method not allowed "+request.method())));
                        return;
                    }
                    Extras extras = new Extras(mr.getRequestPath());
                    extras.setResponseWriter(responseWriter);
                    Response r = mr.getHandler().handle(request, extras);
                    if (r!=null) {
                        Response resp = r.header("Connection","close");
//                        resp.body(compress(resp))
                        if (resp.isStreamBody()) {
                            injectCustom(resp);
                            responseWriter.writeStream(resp.asByteArray(), resp.getSBody(), resp.getSBodyLen());
                            reqLog(resp.getStatus(), request.getAddress().toString(), request.getUrl(), request.method());

                        } else {
                            responseWriter.write(injectCustom(resp));
                            reqLog(resp.getStatus(), request.getAddress().toString(), request.getUrl(), request.method());
                        }
                    }
                }
            } catch (Exception e) {
                try {
                    responseWriter.write(
                            exceptionHandler.handle(
                                    request, mr != null ? new Extras(mr.getRequestPath()) : null,
                                    e,
                                    debugMode
                            ).header("Connection","close")
                    );
                } catch (Exception e1) {
                }
//                e.printStackTrace();
            }
        }

        private Response injectCustom(Response r){
            if (cHeaderSet.isEmpty()) return r;
            for (Map.Entry<String,String> ch: cHeaderSet) {
                r.header(ch.getKey(), ch.getValue());
            }
            return r;
        }

        private void doNotFound(Request r, ResponseWriter w) {
            w.write(Response.make(404).bodyText(("Not Found : " + r.getUrl()).getBytes()));
        }

    }

    private void reqLog(int code, String ip, String url, HTTPMethod method) {
        Log.i("Swapp", method+ip+" - "+url+" - "+code);

    }

}
