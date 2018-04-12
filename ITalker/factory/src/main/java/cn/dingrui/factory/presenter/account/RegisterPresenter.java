package cn.dingrui.factory.presenter.account;


import android.support.annotation.StringRes;
import android.text.TextUtils;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.regex.Pattern;

import cn.dingrui.common.common.Common;
import cn.dingrui.common.factory.data.DataSource;
import cn.dingrui.common.factory.presenter.BasePresenter;
import cn.dingrui.factory.R;
import cn.dingrui.factory.data.helper.AccountHelper;
import cn.dingrui.factory.model.api.account.RegisterModel;
import cn.dingrui.factory.model.db.User;
import cn.dingrui.factory.persistence.Account;

/**
 * 注册的逻辑实现
 * Created by dingrui
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter, DataSource.Callback<User> {

    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    @Override
    public void register(String phone, String name, String password) {
        //调用start，默认启动了loading
        start();
        // 得到View接口
        RegisterContract.View view = getView();

        if (!checkMobile(phone)) {
            //手机不合法
            view.showError(R.string.data_account_register_invalid_parameter_mobile);
        } else if (password.length() < 6) {
            //密码要大于6位
            view.showError(R.string.data_account_register_invalid_parameter_password);
        } else if (name.length() < 2) {
            //姓名要大于两位
            view.showError(R.string.data_account_register_invalid_parameter_name);
        } else {
            //网络请求

            //构造model，进行请求调用
            RegisterModel registerModel = new RegisterModel(phone, password, name, Account.getPushId());
            AccountHelper.register(registerModel, this);
        }
    }

    /**
     * 检查手机号是否合法
     *
     * @param phone
     * @return true 是否合法
     */
    @Override
    public boolean checkMobile(String phone) {
        //手机号不为空，且满足格式
        return !TextUtils.isEmpty(phone)
                && Pattern.matches(Common.Constant.REGEX_MOBILE, phone);
    }

    /**
     * 数据加载成功
     *
     * @param user
     */
    @Override
    public void onDataLoaded(User user) {
        // 当网络请求成功，注册好了，回送一个用户信息回来
        // 告知界面，注册成功
        final RegisterContract.View view = getView();
        if (view == null)
            return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                //调用主界面注册成功
                view.registerSuccess();
            }
        });
    }

    /**
     * 数据加载失败
     *
     * @param strRes
     */
    @Override
    public void onDataNotAvailable(@StringRes final int strRes) {
        // 网络请求告知注册失败
        final RegisterContract.View view = getView();
        if (view == null)
            return;
        // 此时是从网络回送回来的，并不保证处于主现场状态
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                // 调用主界面注册失败显示错误
                view.showError(strRes);
            }
        });
    }
}
