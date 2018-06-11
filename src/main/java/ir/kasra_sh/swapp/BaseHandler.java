package ir.kasra_sh.swapp;

import ir.kasra_sh.nanoserver.http.request.Request;
import ir.kasra_sh.nanoserver.http.response.Response;

public interface BaseHandler {
    Response handle(Request r, Extras ex) throws Exception;
}
