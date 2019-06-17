package demo.core.com.coreapp.http;


import demo.core.com.coreapp.http.bean.BannerListBean;
import demo.core.com.coreapp.http.bean.CategoryListBean;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/5/21 0021
 * 版权：
 */

public interface ApiService {

    @GET("android/list")
    Observable<CategoryListBean> getCategoryList();
    @GET("android/page")
    Observable<BannerListBean> getBannerList(@Query("page") int page, @Query("pageSize") int pageSize);

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    @POST
    Observable<ResponseBody> uploadFile(@Url String url, @Body MultipartBody body);
}
