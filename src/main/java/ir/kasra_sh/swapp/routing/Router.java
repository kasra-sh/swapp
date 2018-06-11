package ir.kasra_sh.swapp.routing;

import ir.kasra_sh.swapp.BaseHandler;

import java.util.ArrayList;

public class Router {

    class RoutePair {
        private Route r;
        private BaseHandler h;

        public RoutePair(Route r, BaseHandler h) {
            this.r = r;
            this.h = h;
        }

        public Route getR() {
            return r;
        }

        public BaseHandler getH() {
            return h;
        }
    }

    private ArrayList<RoutePair> routes = new ArrayList<>();

    public void addRoute(Route r, BaseHandler h){
        routes.add(new RoutePair(r,h));
    }

    public MatchedRoute route(String path) {
        for (int i = 0; i < routes.size(); i++) {
            RoutePair r = routes.get(i);
            RequestPath rp = RouteMatcher.match(path, r.getR());
            if (rp.match) return new MatchedRoute(r.getH(), rp, r.getR());
        }
        return null;
    }

}
