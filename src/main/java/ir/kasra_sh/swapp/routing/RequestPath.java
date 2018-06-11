package ir.kasra_sh.swapp.routing;

import java.util.ArrayList;

// internally created
// used by developer
public class RequestPath {
    boolean match;
    private ArrayList<String> extras = new ArrayList<>();
    private String baseUrl;

    RequestPath(boolean match, String baseUrl,ArrayList<String> extras) {
        this.match = match;
        this.extras = extras;
        this.baseUrl = baseUrl;
    }

    RequestPath(boolean match) {
        this.match = match;
    }

    public RequestPath addExtra(String p) {
        this.extras.add(p);
        return this;
    }

    public int extraCount(){
        return extras.size();
    }

    public String getExtra(int i){
        return extras.get(i);
    }

    public String getExtrasAsString(boolean startSlash, boolean endSlash) {
        StringBuilder sb = new StringBuilder(2048);
        if (startSlash) sb.append("/");
        for (int i = 0; i < extras.size(); i++) {
            sb.append(extras.get(i)).append("/");
        }
        if (!endSlash) {
            if (sb.length()>1) {
                sb.setLength(sb.length()-1);
            }
        }
        return sb.toString();
    }

}
