package moviesassignment.com.movies.repository.serverApi;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DefaultHeaderInterceptor implements Interceptor {

    public DefaultHeaderInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json; charset=utf-8").addHeader("api_key","acd1f9d9f8a4544107e17ee38209fc8c").build();
        return chain.proceed(request);
    }
}