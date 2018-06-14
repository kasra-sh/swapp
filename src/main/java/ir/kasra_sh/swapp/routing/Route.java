package ir.kasra_sh.swapp.routing;

import ir.kasra_sh.picohttpd.http.request.HTTPMethod;
import ir.kasra_sh.picohttpd.utils.NanoLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Route {
    private String basePath;
    private int extras;
    private List<HTTPMethod> methods = new ArrayList<>();

    Route() { }

    Route(String basePath, int ex) {
        this.basePath = basePath;
        this.extras = ex;
    }

    public static Route make(String path, boolean wildCard) {
        Route rd = new Route();
        rd.extras = wildCard?-1:0;
        rd.basePath = path;
        NanoLogger.i("Router","Added ("+rd.basePath+(wildCard?"~":"")+")");
        return rd;
    }

    public static Route make(String basePath, int extras) {
        return make(basePath,false).setExtras(extras);
    }

    private Route setExtras(int ex){
        this.extras = ex;
        return this;
    }

    private Route setBasePath(String base){
        this.basePath = base;
        return this;
    }

    String getBasePath() {
        return basePath;
    }

    int getExtras() {
        return extras;
    }

    public List<HTTPMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<HTTPMethod> methods) {
        this.methods = methods;
    }

    public void setMethods(HTTPMethod ... methods) {
        this.methods.addAll(Arrays.asList(methods));
    }
}
