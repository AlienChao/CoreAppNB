package demo.core.com.coreapp.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.powerrich.corelib.activity.base.BaseActivity;
import com.powerrich.corelib.manager.DownloadManager;
import com.powerrich.corelib.manager.UploadManager;
import com.powerrich.corelib.permission.CcPermissions;
import com.powerrich.corelib.permission.PermissionName;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import demo.core.com.coreapp.R;
import demo.core.com.coreapp.http.ApiServiceManager;
import demo.core.com.coreapp.http.BaseSubscriber;
import demo.core.com.coreapp.http.bean.BannerListBean;
import demo.core.com.coreapp.http.bean.BaseBean;
import demo.core.com.coreapp.http.bean.CategoryListBean;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HttpActivity extends BaseActivity {


    @BindView(R.id.bt1)
    Button mBt1;
    @BindView(R.id.bt2)
    Button mBt2;
    @BindView(R.id.bt3)
    Button mBt3;
    @BindView(R.id.bt4)
    Button mBt4;
    @BindView(R.id.bt5)
    Button mBt5;

    @BindView(R.id.tv2)
    TextView mTv;
    private String str = "";


    @SuppressLint("ResourceType")
    @Override
    public int onCreateContentView() {
        setTitleBack("http请求");
        return R.layout.activity_http;
    }

    private DownloadManager mDownloadManager;
    private UploadManager mUploadManager;


    @OnClick({R.id.bt1, R.id.bt2, R.id.bt3, R.id.bt4, R.id.bt5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                //不带dialog请求
                this.exeHttp(ApiServiceManager.getApi().getCategoryList())
                        .subscribe(new BaseSubscriber<CategoryListBean>() {
                            @Override
                            public void result(CategoryListBean bean) {
                                mTv.setText(bean.toString());
                            }
                        });

                break;
            case R.id.bt2:
                //带dialog请求
                this.exeHttpWithDialog(ApiServiceManager.getApi().getBannerList(1, 10))
                        .subscribe(new BaseSubscriber<BannerListBean>() {
                            @Override
                            public void result(BannerListBean bean) {
                                mTv.setText(bean.toString());
                            }
                        });

                break;
            case R.id.bt3:
                str = "";
                //一次请求两个接口
                this.exeHttpWithDialog(Observable.merge(ApiServiceManager.getApi().getCategoryList(), ApiServiceManager
                        .getApi().getBannerList(1, 10)))
                        .subscribe(new BaseSubscriber<BaseBean<?>>() {
                            @Override
                            public void result(BaseBean<?> bean) {
                                str += bean.toString();
                                mTv.setText(str);
                            }
                        });
                break;
            case R.id.bt4:
                CcPermissions.with(this)
                        .permission(PermissionName.WRITE_EXTERNAL_STORAGE, PermissionName.READ_EXTERNAL_STORAGE)
                        .request(new com.powerrich.corelib.permission.Consumer() {
                            @Override
                            public void accept(List<String> granted, boolean isAll) {
                                if (mDownloadManager == null) {
                                    List<String> sdCardPaths = SDCardUtils.getSDCardPaths();
                                    String path = sdCardPaths.get(0) + "/testPaht/NDK.zip";
                                    File file = new File(path);
                                    mDownloadManager = new DownloadManager(HttpActivity.this,
                                            ApiServiceManager.getApi().download("http://172.10.102.101:8082/upload/download"),
                                            1024 * 14191, file);
                                }
                                mDownloadManager.setCallBack(new DownloadManager.DownloadCallback() {
                                    @Override
                                    public void success() {
                                    }

                                    @Override
                                    public void fail() {
                                    }
                                });
                                mDownloadManager.start();
                            }
                        });
                break;
            case R.id.bt5:

                CcPermissions.with(this)
                        .permission(PermissionName.WRITE_EXTERNAL_STORAGE, PermissionName.READ_EXTERNAL_STORAGE)
                        .request(new com.powerrich.corelib.permission.Consumer() {
                            @Override
                            public void accept(List<String> granted, boolean isAll) {
                                if (mUploadManager == null) {
                                    List<String> sdCardPaths = SDCardUtils.getSDCardPaths();
                                    String path = sdCardPaths.get(0) + "/testPaht/NDK.zip";
                                    File file = FileUtils.getFileByPath(path);
                                    RequestBody requestFile =
                                            RequestBody.create(null, file);
                                    MultipartBody.Part body =
                                            MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                                    MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
                                    multipartBuilder.setType(MultipartBody.FORM);
                                    multipartBuilder.addPart(body);
                                    MultipartBody build = multipartBuilder.build();

                                    mUploadManager = new UploadManager(HttpActivity.this,
                                            ApiServiceManager.getApi().uploadFile("http://172.10.102.101:8082/upload/image"
                                            ,build),UploadManager.PROGRESS_DIALOG);

                                }
                                mUploadManager.setCallBack(new UploadManager.UploadCallback() {
                                    @Override
                                    public void success() {

                                    }

                                    @Override
                                    public void fail() {

                                    }
                                });
                                mUploadManager.start();
                            }
                        });

                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDownloadManager != null) {
            mDownloadManager.close();
        }
    }
}
