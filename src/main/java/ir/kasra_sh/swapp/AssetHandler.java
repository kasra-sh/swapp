package ir.kasra_sh.swapp;

import ir.kasra_sh.nanoserver.http.request.Request;
import ir.kasra_sh.nanoserver.http.response.Response;

public interface AssetHandler {
    Response handleAsset(Request request, String filePath, String fileExt, Extras extras);
}
