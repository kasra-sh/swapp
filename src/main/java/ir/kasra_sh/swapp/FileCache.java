package ir.kasra_sh.swapp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

public class FileCache {
    private final static ConcurrentHashMap<String, byte[]> files = new ConcurrentHashMap<>();

    public static synchronized ByteArrayInputStream load(String file) throws IOException {
        byte[] f = files.get(file);
        if (f == null) {
            Path p = Paths.get(file);
//            if (Files.size(p) > 1024*1024*10) {
//                return Files.readAllBytes(p);
//            }
            Log.d("FileCache", "Cached : "+file, null);
            f =
//                    SwappModule.compress(
                    Files.readAllBytes(p);
//            );
            files.putIfAbsent(file, f);
        }
        return new ByteArrayInputStream(f);
    }
}
