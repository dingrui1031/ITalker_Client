package cn.dingrui.common.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ding192 on 2018/4/2.
 */

public abstract class BaseFragment extends Fragment {
    private View mRoot;
    private Unbinder mUnbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //初始化参数
        initArgs(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null) {
            int layoutId = getContentLayoutId();
            //初始化当前根布局，但是不在创建时就添加到container里面
            View root = inflater.inflate(layoutId, container, false);
            initWidget(root);
            mRoot = root;
        } else {
            if (mRoot.getParent() != null) {
                //把当前root从其父控件中移除
                ((ViewGroup) mRoot.getParent()).removeView(mRoot);
            }
        }
        return mRoot;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //当view创建完成后初始化数据
        initData();
    }

    /**
     * 初始化相关参数
     *
     * @param bundle 参数Bundle
     */
    protected void initArgs(Bundle bundle) {

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
    protected void initWidget(View root) {
        mUnbinder = ButterKnife.bind(this, root);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 返回键触发时调用
     *
     * @return 返回true表示我已经处理返回逻辑，activity不用自己finish
     * 返回false表示我没有处理返回逻辑，activity自己走自己的逻辑
     */
    public boolean onBackPressed() {
        return false;
    }
}
