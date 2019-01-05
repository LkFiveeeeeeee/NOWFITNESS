package project.cn.edu.tongji.sse.nowfitness.data.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import project.cn.edu.tongji.sse.nowfitness.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWorkUtils {
    private NetWorkUtils(){}

    public static Retrofit makeRetrofit(){
        return new Retrofit.Builder()
                .baseUrl("http://47.107.167.12:8080/api/")  // base url
                .client(makeClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    static OkHttpClient makeClient(){
        final int timeout = 5;
        return new OkHttpClient.Builder()
                .connectTimeout(timeout,TimeUnit.SECONDS)
                .readTimeout(timeout,TimeUnit.SECONDS)
                .addInterceptor(makeLoggingInterceptor())
                .build();
    }


    static Interceptor makeLoggingInterceptor(){
        if(BuildConfig.DEBUG){
            return new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);
        }else{
            return new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.NONE);
        }
    }
}
