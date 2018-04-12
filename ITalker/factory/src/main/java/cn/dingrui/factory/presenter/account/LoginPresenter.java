package cn.dingrui.factory.presenter.account;

import android.support.annotation.StringRes;

import cn.dingrui.common.factory.data.DataSource;
import cn.dingrui.common.factory.presenter.BasePresenter;
import cn.dingrui.factory.model.db.User;

/**
 * 登陆的逻辑实现
 * Created by dingrui
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter,DataSource.Callback<User>{

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void login(String phone, String password) {

    }

    @Override
    public void onDataLoaded(User user) {

    }

    @Override
    public void onDataNotAvailable(@StringRes int strRes) {

    }
}
