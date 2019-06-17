package com.powerrich.corelib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.powerrich.corelib.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * huyuanhao
 * 带加载条的webview
 */
public class LoadingWebView extends WebView {
    private Context mContext;
    private ProgressBar mProgressBar;
    private boolean isDownload = true;
    private String textSize = "35px";

    /**
     * 网页缓存目录
     */
    private static final String cacheDirPath = Environment
            .getExternalStorageDirectory() + "/LoadingWebView/webCache/";

    private static final String TAG = "IntentUtils";
    /**
     * 系统可以处理的url正则
     */
    private static final Pattern ACCEPTED_URI_SCHEME = Pattern.compile("(?i)"
            + // switch on case insensitive matching
            '('
            + // begin group for scheme
            "(?:http|https|ftp|file)://" + "|(?:inline|data|about|javascript):" + "|(?:.*:.*@)"
            + ')' + "(.*)");


    public LoadingWebView(Context context) {
        this(context, null);
    }

    public LoadingWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initContext(context);
    }

    private void initContext(Context context) {
        this.mContext = context;
        requestFocus();
        setInitialScale(39);
        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//支持通过Javascript打开新窗口
        getSettings().setJavaScriptEnabled(true);//设置WebView属性，能够执行Javascript脚本
        getSettings().setUseWideViewPort(true);//将图片调整到适合webview的大小
        getSettings().setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        getSettings().setDomStorageEnabled(true);//设置是否启用了DOM Storage API
        getSettings().setDatabaseEnabled(true);//开启database storage API功能
        getSettings().setDatabasePath(cacheDirPath); //设置数据库缓存路径
        getSettings().setAppCachePath(cacheDirPath);//设置Application Caches缓存目录
        getSettings().setAppCacheEnabled(true);//开启Application Caches功能
        //支持屏幕缩放
        getSettings().setSupportZoom(true);
        getSettings().setBuiltInZoomControls(true);

        requestFocusFromTouch();//可弹出输入框

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int scale = dm.densityDpi;
        textSize = scale/12 + "px";
        if (scale == 240) { //设置自动适配
            getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (scale == 160) {
            getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else {
            getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        }
    }

    public void loadData(String content){
        String head = "<head><style>* {font-size:" + textSize + "}{color:#212121;}img{max-width: 100%; width:100%; height: auto;}</style></head>";
        String resultStr = "<html>" + head + "<body>" + content + "</body></html>";
        this.loadDataWithBaseURL(null, resultStr, "text/html", "utf-8", null);
    }

    public void setZoom(boolean b){
        getSettings().setSupportZoom(b);
    }
    public void setTextZoom(int size){
        getSettings().setTextZoom(size);
    }

    /**
     * 加载网页url
     *
     * @param url
     */
    public void loadUrl(String url) {
        //这里不能加setWebViewClient，否则会覆盖自定义的WebViewClient
        setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { // 重写此方法表明点击网页里面的链接不调用系统浏览器，而是在本WebView中显示
//                if (url.contains("tel:")) {
//                    callPhone(url);
//                    return true;
//                } else {
//                    return false;
//                }
                if (isAcceptedScheme(url)) return false;
                try {
                    // 以下固定写法,表示跳转到第三方应用
                    final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url) );
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mContext.startActivity(intent);
                    isDownload = false;  //该字段是用于判断是否需要跳转浏览器下载
                } catch (Exception e) {
                    // 防止没有安装的情况
                    e.printStackTrace();
                }
                return true;
            }
        });

        //链接中有需要跳转下载的链接时跳转浏览器下载
        this.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(final String url, String userAgent, String contentDisposition,
                                        String mimetype, long contentLength) {
                if (isDownload) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            Uri uri = Uri.parse(url);
                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            intent.setData(uri);
                            mContext.startActivity(intent);
                        }
                    }).start();
                }
                isDownload = true;//重置为初始状态
            }
        });
    }

    /**
     * 该url是否属于浏览器能处理的内部协议
     */
    public boolean isAcceptedScheme(String url) {
        //正则中忽略了大小写，保险起见，还是转成小写
        String lowerCaseUrl = url.toLowerCase();
        Matcher acceptedUrlSchemeMatcher = ACCEPTED_URI_SCHEME.matcher(lowerCaseUrl);
        if (acceptedUrlSchemeMatcher.matches()) {
            return true;
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    public void callPhone(final String url) {
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse(url);
        intent.setData(data);
        mContext.startActivity(intent);
    }

    /**
     * 添加进度条
     */
    public void addProgressBar() {
        mProgressBar = new ProgressBar(getContext(), null,
                android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, 5, 0, 0));
        mProgressBar.setProgressDrawable(getContext().getResources()
                .getDrawable(R.drawable.pg));
        addView(mProgressBar);//添加进度条至LoadingWebView中

        setWebChromeClient(new WebChromeClient());//设置setWebChromeClient对象
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressBar.setVisibility(GONE);
            } else {
                if (mProgressBar.getVisibility() == GONE)
                    mProgressBar.setVisibility(VISIBLE);
                mProgressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
    }

    /**
     * 回收webview
     */
    public void destroyWebView() {
        clearCache(true);
        clearHistory();
    }
}

