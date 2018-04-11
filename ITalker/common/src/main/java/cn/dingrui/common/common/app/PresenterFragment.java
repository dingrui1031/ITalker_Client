package cn.dingrui.common.common.app;

import android.content.Context;
import android.support.annotation.StringRes;

import cn.dingrui.common.factory.presenter.BaseContract;

/**
 * Created by dingrui
 */

public abstract class PresenterFragment<Presenter extends BaseContract.Presenter> extends BaseFragment implements BaseContract.View<Presenter> {

    protected Presenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //在界面onAttach之后就初始化Presenter
        initPresenter();
    }

    /**
     * 初始化Presenter
     *
     * @return Presenter
     */
    protected abstract Presenter initPresenter();


    @Override
    public void showError(@StringRes int str) {
        //显示错误
        BaseApplication.showToast(str);
    }

    @Override
    public void showLoading() {
        // todo 显示一个Loading
    }

    @Override
    public void setPresenter(Presenter presenter) {
        //View中赋值Presenter
        mPresenter = presenter;
    }

}
