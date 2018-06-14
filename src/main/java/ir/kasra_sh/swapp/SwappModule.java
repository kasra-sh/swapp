package ir.kasra_sh.swapp;


import ir.kasra_sh.picohttpd.http.request.HTTPMethod;
import ir.kasra_sh.picohttpd.http.request.Request;
import ir.kasra_sh.picohttpd.http.response.Response;
import ir.kasra_sh.swapp.routing.Route;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.zip.Deflater;

public abstract class SwappModule {

    private HashMap<Route, BaseHandler> modulePaths = new HashMap<>();

    protected final void path(String path, BaseHandler handler) {
        path(Route.make(path, false), handler);
    }

    protected final void path(Route r, BaseHandler handler) {
        modulePaths.put(r, handler);
    }

    protected final void path(Route r, BaseHandler handler, HTTPMethod... methods) {
        r.setMethods(methods);
        modulePaths.put(r, handler);
    }

    protected final void get(Route r, BaseHandler handler) {
        path(r, handler, HTTPMethod.GET);
    }

    protected final void get(String path, BaseHandler handler) {
        get(Route.make(path, false), handler);
    }

    protected final void post(Route r, BaseHandler handler) {
        path(r, handler, HTTPMethod.POST);
    }

    protected final void post(String path, BaseHandler handler) {
        post(Route.make(path, false), handler);
    }

    protected final void head(Route r, BaseHandler handler) {
        path(r, handler, HTTPMethod.HEAD);
    }

    protected final void head(String path, BaseHandler handler) {
        head(Route.make(path, false), handler);
    }

    protected final void put(Route r, BaseHandler handler) {
        path(r, handler, HTTPMethod.PUT);
    }

    protected final void put(String path, BaseHandler handler) {
        put(Route.make(path, false), handler);
    }

    protected final void delete(Route r, BaseHandler handler) {
        path(r, handler, HTTPMethod.DELETE);
    }

    protected final void delete(String path, BaseHandler handler) {
        delete(Route.make(path, false), handler);
    }

    protected final void staticAsset(String path, String dir, AssetHandler assetHandler) {
        get(Route.make(path, true), new BaseHandler() {

            @Override
            public Response handle(Request r, Extras ex) throws Exception {
                String fp = dir;
                System.out.println("url" + r.getUrl());
                if (fp.endsWith("/") && fp.length() > 1) {
                    fp = fp.substring(0, fp.length() - 1);
                }
                fp += ex.pathExtras(false, false);
                System.out.println("fp : "+fp);
                String[] e = fp.split("\\.");
                String ext = "";
                if (e.length > 0) {
                    ext = e[e.length - 1];
                }
                if (assetHandler == null) {
                    return defHandler.handleAsset(r, fp, ext, ex);
                } else {
                    return assetHandler.handleAsset(r, fp, ext, ex);
                }
            }
        });
    }

    public static byte[] compress(byte[] data) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        return outputStream.toByteArray();
    }

    private final static AssetHandler defHandler = new AssetHandler() {
        @Override
        public Response handleAsset(Request request, String filePath, String fileExt, Extras extras) {
            Path p = Paths.get(filePath);
            int fsize = 0;
            try {
                fsize = (int) Files.size(p);
                String name = p.getFileName().toString();
                System.out.println("path = "+filePath);
                return Responses.fileStream(200, name, fileExt, FileCache.load(filePath), fsize);
            } catch (IOException e) {
            }
            return Responses.err(404, "File not found: "+filePath);
        }
    };

    public HashMap<Route, BaseHandler> getModulePaths() {
        return modulePaths;
    }
}
