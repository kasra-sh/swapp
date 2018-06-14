package ir.kasra_sh.swapp;


import ir.kasra_sh.picohttpd.server.nio.ResponseWriter;
import ir.kasra_sh.swapp.routing.RequestPath;

public class Extras {
    private RequestPath requestPath;
    private String dirPath;
    private String clientIP;
    private ResponseWriter responseWriter;

    Extras() {
    }

    Extras(RequestPath requestPath) {
        this.requestPath = requestPath;
    }

    public RequestPath getRequestPath() {
        return requestPath;
    }

    void setRequestPath(RequestPath requestPath) {
        this.requestPath = requestPath;
    }

    public String pathExtra(int i) {
        return requestPath.getExtra(i);
    }

    public int extraCount() {
        return requestPath.extraCount();
    }

    public String pathExtras(boolean leadSlash, boolean trailSlash) {
        return requestPath.getExtrasAsString(leadSlash, trailSlash);
    }

    public String getClientIP() {
        return clientIP;
    }

    void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public ResponseWriter getWriter() {
        return responseWriter;
    }

    void setResponseWriter(ResponseWriter responseWriter) {
        this.responseWriter = responseWriter;
    }

    public String getDirPath() {
        return dirPath;
    }

    void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }
}
