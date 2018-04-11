package cn.dingrui.factory.presenter.account;

import cn.dingrui.common.factory.presenter.BasePresenter;

/**
 * Created by dingrui
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter{

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void login(String phone, String password) {

    }
}
