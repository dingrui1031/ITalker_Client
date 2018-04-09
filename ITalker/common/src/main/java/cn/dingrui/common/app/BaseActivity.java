package cn.dingrui.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by ding192 on 2018/4/2.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在界面未初始化之前调用的初始化窗口
        initWindow();

        if (initArgs(getIntent().getExtras())) {
            //得到界面id并设置到Activity界面中
            int layoutId = getContentLayoutId();
            setContentView(layoutId);

            initWidget();
            initData();
        } else {
            finish();
        }
    }

    /**
     * 初始化窗口
     */
    protected void initWindow() {

    }

    /**
     * 初始化相关参数
     *
     * @param bundle 参数Bundle
     * @return 如果参数正确返回true，错误返回false
     */
    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    /**
     * 得到当前界面的资源文件id
     *
     * @return 资源文件id
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget() {
        ButterKnife.bind(this);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 当点击界面导航栏返回键时，结束当前页面
     *
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    /**
     * 点击返回键
     */
    @Override
    public void onBackPressed() {
        //得到当前activity下所有fragment
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        //判断是否为空
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                //判断是否是我们能够处理的Fragment类型
                if (fragment instanceof BaseFragment) {
                    //判断是否拦截了返回
                    if (((BaseFragment) fragment).onBackPressed()) {
                        //如果是，直接返回
                        return;
                    }
                }
            }
        }
        super.onBackPressed();
        finish();
    }
}
