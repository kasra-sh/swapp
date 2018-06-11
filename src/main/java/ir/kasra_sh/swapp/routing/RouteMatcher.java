package ir.kasra_sh.swapp.routing;

import java.util.ArrayList;
import java.util.Arrays;

public class RouteMatcher {
    public static RequestPath match(String path, Route r) {
        RequestPath ret;
        ArrayList<String> pa = new ArrayList<>();
        String ppath = path.endsWith("/")&&path.length()>1? path.substring(0, path.length()-1): path;
        if (ppath.equalsIgnoreCase(r.getBasePath())) {
            return new RequestPath(true, r.getBasePath(), new ArrayList<>());
        }
        if (ppath.toLowerCase().startsWith(r.getBasePath().toLowerCase())) {
            if (r.getExtras() == 0) return new RequestPath(false, r.getBasePath(), pa);
            String ns = ppath.substring(r.getBasePath().length(), ppath.length());
            String[] parts = ns.split("/");
            if (r.getExtras() == -1) {
//                System.out.println("endless");
                //                    System.out.println("part: "+parts[i]);
                pa.addAll(Arrays.asList(parts));
                return new RequestPath(true, r.getBasePath(), pa);
            }else {
                if (parts.length==r.getExtras()) {
//                    System.out.println("extraParts = "+r.getExtras());
                    //                        System.out.println("part: "+parts[i]);
                    pa.addAll(Arrays.asList(parts));
                    return new RequestPath(true, r.getBasePath(), pa);
                }
            }
        }
        return new RequestPath(false, r.getBasePath(), pa);
    }
}
