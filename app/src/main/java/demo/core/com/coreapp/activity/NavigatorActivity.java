package demo.core.com.coreapp.activity;

import android.support.v4.app.Fragment;

import com.powerrich.corelib.activity.base.BaseNavigatorActivity;

import java.util.ArrayList;
import java.util.List;

import demo.core.com.coreapp.fragment.RefreshFragment;

public class NavigatorActivity extends BaseNavigatorActivity {

    @Override
    protected List<Fragment> getFragments() {
        setTitleBack("指示器ViewPager");
        List<Fragment> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new RefreshFragment());
        }
        return list;
    }

    @Override
    protected List<String> getTitles() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("title" + i);
        }
        return list;
    }

}
