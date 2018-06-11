package ir.kasra_sh.swapp;

import ir.kasra_sh.nanoserver.http.request.HTTPMethod;
import ir.kasra_sh.nanoserver.http.request.Request;
import ir.kasra_sh.nanoserver.http.response.Response;
import ir.kasra_sh.nanoserver.server.nio.ResponseWriter;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class Async {
    public interface Task {
        Response run();
    }

    private static final Executor executor = Executors.newCachedThreadPool();

    public static final void execute(Request r, ResponseWriter writer, Task task) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Response res = task.run();
                writer.write(res);
                Log.i("Swapp/Async", r.method()+r.getAddress().toString()+" - "+r.getUrl()+" - "+res.getStatus());
            }
        });
    }

}
