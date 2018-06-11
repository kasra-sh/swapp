package ir.kasra_sh.swapp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URLUtil {
    public static String encode(String url) {
        try {
            StringBuilder sb = new StringBuilder(1024);
            String[] p = url.split("/");
            for (int i = 1; i < p.length; i++) {
                sb.append("/").append(URLEncoder.encode(p[i],"UTF-8"));
            }
            return sb.toString();
        }catch (Exception e){
            return url;
        }
    }

    public static String decode(String url){
        try {
            return URLEncoder.encode(url,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            return url;
        }
    }
}
