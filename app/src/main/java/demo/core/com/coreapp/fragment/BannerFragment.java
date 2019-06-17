package demo.core.com.coreapp.fragment;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.powerrich.corelib.fragment.base.BaseFragment;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import demo.core.com.coreapp.R;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/27
 * 版权：
 * <p>
 * 轮播图
 */
public class BannerFragment extends BaseFragment {

    @BindView(R.id.banner)
    Banner mBanner;

    @Override
    protected View onCreateContentView() {
      return inflateContentView(R.layout.fragment_banner);
    }

    @Override
    protected void bindViewAfter() {
        initBanner();
    }


    private void initBanner() {
        List<String> list = new ArrayList<>();
        list.add("http://pic28.photophoto.cn/20130818/0020033143720852_b.jpg");
        list.add("http://pic28.photophoto.cn/20130818/0020033143720852_b.jpg");
        list.add("http://pic28.photophoto.cn/20130818/0020033143720852_b.jpg");

        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setImages(list);
        mBanner.start();
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            Glide.with(context).load(path).into(imageView);
        }
    }

}
