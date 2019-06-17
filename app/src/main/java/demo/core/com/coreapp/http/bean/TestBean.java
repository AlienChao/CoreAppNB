package demo.core.com.coreapp.http.bean;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/19
 * 版权：
 */

public class TestBean extends BaseBean {
    private CategoryListBean bean1;
    private BannerListBean bean2;

    public CategoryListBean getBean1() {
        return bean1;
    }

    public void setBean1(CategoryListBean bean1) {
        this.bean1 = bean1;
    }

    public BannerListBean getBean2() {
        return bean2;
    }

    public void setBean2(BannerListBean bean2) {
        this.bean2 = bean2;
    }

    public TestBean(CategoryListBean bean1, BannerListBean bean2) {
        this.bean1 = bean1;
        this.bean2 = bean2;
    }
}
