package ir.kasra_sh.swapp;

import ir.kasra_sh.picohttpd.http.request.Request;
import ir.kasra_sh.picohttpd.http.response.Response;

public interface ExceptionHandler {
    Response handle(Request r, Extras ex, Throwable t, boolean showException);
}
