package demo.core.com.coreapp.http;

import com.powerrich.corelib.http.intercepter.DownloadProgressInterceptor;
import com.powerrich.corelib.http.intercepter.UpLoadProgressInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/16
 * 版权：
 */

public class ApiServiceManager {

    public static final String BASE_URL = "http://liangyifan.xyz:8083";

    public static final int TIME_OUT = 5;
    private Retrofit retrofit;
    public ApiService api;
    private OkHttpClient okHttpClient;


    /**
     * 获取 APi的入口文件
     * @return
     */
    public static ApiService getApi(){
        return Utils.sApiServiceManager.api;
    }

    private static class Utils{
        private static ApiServiceManager sApiServiceManager = new ApiServiceManager();
    }

    private ApiServiceManager() {
        if (okHttpClient == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient.Builder()
//                    .addInterceptor(logging)
                    .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .addInterceptor(new DownloadProgressInterceptor())
                    .addInterceptor(new UpLoadProgressInterceptor())
                    .build();
        }
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加Rx适配器
                    .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                    .client(okHttpClient)
                    .build();
        if (api == null) {
            api = retrofit.create(ApiService.class);
        }


    }
}
