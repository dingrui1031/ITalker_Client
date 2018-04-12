package cn.dingrui.factory.presenter.account;

import android.support.annotation.StringRes;
import android.text.TextUtils;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import cn.dingrui.common.factory.data.DataSource;
import cn.dingrui.common.factory.presenter.BasePresenter;
import cn.dingrui.factory.R;
import cn.dingrui.factory.data.helper.AccountHelper;
import cn.dingrui.factory.model.api.account.LoginModel;
import cn.dingrui.factory.model.db.User;
import cn.dingrui.factory.persistence.Account;

/**
 * 登陆的逻辑实现
 * Created by dingrui
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter, DataSource.Callback<User> {

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void login(String phone, String password) {
        start();

        LoginContract.View view = getView();

        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            view.showError(R.string.data_account_login_invalid_parameter);
        } else {
            // 尝试传递PushId
            LoginModel loginModel = new LoginModel(phone, password, Account.getPushId());
            AccountHelper.login(loginModel, this);
        }
    }

    @Override
    public void onDataLoaded(User user) {
        final LoginContract.View view = getView();
        if (view == null)
            return;
        //强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.loginSuccess();
            }
        });
    }

    @Override
    public void onDataNotAvailable(@StringRes final int strRes) {
        final LoginContract.View view = getView();
        if (view == null)
            return;
        //此时是从网络回送回来的，并不保证处于主线程状态
        //强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showError(strRes);
            }
        });
    }
}
