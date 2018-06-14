package ir.kasra_sh.swapp;


import ir.kasra_sh.picohttpd.http.request.Request;
import ir.kasra_sh.picohttpd.http.response.Response;
import ir.kasra_sh.picohttpd.http.response.ResponseCode;

public class ErrorHandlers {
    public static BaseHandler err404 = new BaseHandler() {
        @Override
        public Response handle(Request r, Extras ex) {
            return Responses.text(404, "404 - Not Found: "+r.getUrl()+"\n");
        }
    };

    public static BaseHandler err500 = new BaseHandler() {
        @Override
        public Response handle(Request r, Extras ex) {
            return Responses.err(500, "Internal Server Error!");
        }
    };

    public static BaseHandler errMethodNotAllowed = new BaseHandler() {
        @Override
        public Response handle(Request r, Extras ex) {
            return Response.makeText(
                    ResponseCode.METHOD_NOT_ALLOWED,
                    ("Method Not Allowed ("+r.method().name()+")!\n").getBytes());
        }
    };
}
