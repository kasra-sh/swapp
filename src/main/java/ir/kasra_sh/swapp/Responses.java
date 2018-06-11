package ir.kasra_sh.swapp;

import ir.kasra_sh.nanoserver.http.response.Response;
import ir.kasra_sh.nanoserver.http.response.ResponseCode;
import ir.kasra_sh.nanoserver.utils.MimeTypes;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Responses {
    public static Response redirectTemp(String location) {
        return Response.make(ResponseCode.TEMPORARY_REDIRECT)
                .header("Location", URLUtil.encode(location)).body(new byte[0]);
    }

    public static Response redirectPerm(String location) {
        String loc;
        try {
            loc = URLEncoder.encode(location, StandardCharsets.UTF_8.name());
        }catch (Exception e){
            loc = location;
        }

        return Response.make(ResponseCode.PERMANENT_REDIRECT)
                .header("Location", loc);
    }

    public static Response ok(byte[] bytes) {
        return Response.make(200).body(bytes);
    }

    public static Response ok(String body) {
        return ok(body.getBytes(StandardCharsets.UTF_8)).type(MimeTypes.Text.TXT);
    }

    public static Response err(int code, String message) {
        return Response.make(code).body(message.getBytes(StandardCharsets.UTF_8));
    }

    public static Response raw(int code, byte[] body) {
        return Response.make(code).body(body);
    }

    public static Response raw(int code, String mime, byte[] body) {
        return raw(code,body).type(mime);
    }

    public static Response raw(int code, String mime, String body) {
        return raw(code, mime, body.getBytes(StandardCharsets.UTF_8));
    }

    public static Response bin(int code, byte[] data) {
        return raw(code,data).type(MimeTypes.Application.BIN);
    }

    public static Response text(int code, String body) {
        return raw(code, body.getBytes(StandardCharsets.UTF_8));
    }

    public static Response html(int code, String html) {
        return raw(200, html.getBytes(StandardCharsets.UTF_8));
    }

    public static Response json(int code, String json) {
        return Response.make(code).bodyJson(json.getBytes(StandardCharsets.UTF_8));
    }

    public static Response fileStream(int code, String fileName, String fileExt ,InputStream stream, int fileSize) {
        Response resp = Response.make(code);
        resp.header("Content-Length", String.valueOf(fileSize));
        if (fileName!=null){
            if (!fileName.equals("")) {
                resp.header("Content-Disposition","attachment; filename=\""+fileName+"\"");
            }
        }
        resp.setStreamBody(true);
        resp.setMime(MimeTypes.byExt(fileExt));
        resp.setSBody(stream);
        resp.setSBodyLen(fileSize);
        return resp;
    }
}
