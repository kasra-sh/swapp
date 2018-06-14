package ir.kasra_sh.swapp;

import ir.kasra_sh.picohttpd.http.request.Request;
import ir.kasra_sh.picohttpd.http.response.Response;
import ir.kasra_sh.picohttpd.server.nio.ResponseWriter;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

public class Async {
    public interface Task {
        Response run();
    }

    private static final Executor executor = new ForkJoinPool(20);

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
