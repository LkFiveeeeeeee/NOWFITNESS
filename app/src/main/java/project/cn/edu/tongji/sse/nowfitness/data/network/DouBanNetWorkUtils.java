package project.cn.edu.tongji.sse.nowfitness.data.network;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import project.cn.edu.tongji.sse.nowfitness.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by a on 2018/12/25.
 */

public class DouBanNetWorkUtils {
    public static Retrofit makeRetrofit(){
        return new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/book/")
                .client(makeClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    static OkHttpClient makeClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5,TimeUnit.SECONDS)
                .addInterceptor(makeLoggingInterceptor())
                //     .addInterceptor()
                .build();
    }

    /*
    //给Retrofit添加header
    static Interceptor makeHeadersInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request().newBuilder()
                .addHeader()
                .build());
            }
        }
    }*/

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
