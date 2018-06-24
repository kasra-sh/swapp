package ir.kasra_sh.swapp;

import ir.kasra_sh.picohttpd.http.request.Request;
import ir.kasra_sh.picohttpd.http.response.Response;
import ir.kasra_sh.picohttpd.server.nio.ResponseWriter;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class Async {
    public interface Task {
        Response run() throws IOException;
    }

    private static Executor executor = Executors.newWorkStealingPool(20);

    public static void setExecutor(Executor executor) {
        Async.executor = executor;
    }

    public static void execute(Request r, ResponseWriter writer, Task task) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response res = task.run();
                    if (res.isStreamBody())
                        writer.writeStream(res.asByteArray(), res.getSBody(), res.getSBodyLen());
                    else
                        writer.write(res);
                    Log.i("Swapp/Async", r.method() + r.getAddress().toString() + " - " + r.getUrl() + " - " + res.getStatus());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
