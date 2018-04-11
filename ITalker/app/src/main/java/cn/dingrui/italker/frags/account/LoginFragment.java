package cn.dingrui.italker.frags.account;

import android.content.Context;

import cn.dingrui.common.common.app.BaseFragment;
import cn.dingrui.common.common.app.PresenterFragment;
import cn.dingrui.factory.presenter.account.LoginContract;
import cn.dingrui.factory.presenter.account.LoginPresenter;
import cn.dingrui.italker.R;

/**
 * Created by dingrui
 */

public class LoginFragment extends PresenterFragment<LoginContract.Presenter> implements LoginContract.View {
    private AccountTrigger mAccountTrigger;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //拿到activity的引用
        mAccountTrigger = (AccountTrigger) context;
    }

    @Override
    protected LoginContract.Presenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAccountTrigger.triggerView();
    }

    @Override
    public void loginSuccess() {

    }
}
