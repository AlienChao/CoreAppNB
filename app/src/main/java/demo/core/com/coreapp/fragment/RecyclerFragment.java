package demo.core.com.coreapp.fragment;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.powerrich.corelib.activity.base.BaseActivity;
import com.powerrich.corelib.fragment.base.BaseRecyclerFragment;

import java.util.ArrayList;
import java.util.List;

import demo.core.com.coreapp.R;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/27
 * 版权：
 */
public class RecyclerFragment extends BaseRecyclerFragment<RecyclerFragment.MyBean> {
    List<MyBean> list;

    @Override
    protected int getItemRes() {
        return R.layout.item_recyclerview;
    }

    @Override
    protected void bindItem(BaseViewHolder helper, MyBean item) {
        int position = list.indexOf(item);
        TextView tv = helper.getView(R.id.tv1);
        tv.setCompoundDrawablesWithIntrinsicBounds(null, mContext.getResources().getDrawable(R.mipmap.ic_launcher), null, null);
        tv.setText(item.name);
    }

    @Override
    protected void loadData() {
        list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            MyBean bean = new MyBean("name" + i, "img" + i);
            list.add(bean);
        }
        initAdapter(list, 2, 5, new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
    }

    class MyBean {
        String name;
        String img;
        public MyBean(String name, String img) {
            this.name = name;
            this.img = img;
        }
    }
}
