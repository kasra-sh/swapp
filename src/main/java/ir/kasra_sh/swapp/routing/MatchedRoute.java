package ir.kasra_sh.swapp.routing;

import ir.kasra_sh.swapp.BaseHandler;

public class MatchedRoute {
    private BaseHandler handler;
    private RequestPath requestPath;
    private Route route;

    public MatchedRoute(BaseHandler handler, RequestPath requestPath, Route route) {
        this.handler = handler;
        this.requestPath = requestPath;
        this.route = route;
    }

    public BaseHandler getHandler() {
        return handler;
    }

    public void setHandler(BaseHandler handler) {
        this.handler = handler;
    }

    public RequestPath getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(RequestPath requestPath) {
        this.requestPath = requestPath;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
