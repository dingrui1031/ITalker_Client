package cn.dingrui.italker.frags.account;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.EditText;
import android.widget.TextView;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dingrui.common.common.app.PresenterFragment;
import cn.dingrui.factory.presenter.account.RegisterContract;
import cn.dingrui.factory.presenter.account.RegisterPresenter;
import cn.dingrui.italker.R;
import cn.dingrui.italker.activities.MainActivity;

/**
 * Created by dingrui
 */

public class RegisterFragment extends PresenterFragment<RegisterContract.Presenter> implements RegisterContract.View {

    @BindView(R.id.edit_phone)
    EditText mEditPhone;
    @BindView(R.id.edit_password)
    EditText mEditPassword;
    @BindView(R.id.edit_name)
    EditText mEditName;
    @BindView(R.id.txt_go_login)
    TextView mTxtGoLogin;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    @BindView(R.id.loading)
    Loading mLoading;

    private AccountTrigger mAccountTrigger;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //拿到activity的引用
        mAccountTrigger = (AccountTrigger) context;
    }

    @Override
    protected RegisterContract.Presenter initPresenter() {
        return new RegisterPresenter(this);
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_register;
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        String phone = mEditPhone.getText().toString();
        String password = mEditPassword.getText().toString();
        String name = mEditName.getText().toString();

        //调用p层进行注册
        mPresenter.register(phone, name, password);
    }

    @OnClick(R.id.txt_go_login)
    void onToLoginClick() {
        //让accountactivity进行界面切换
        mAccountTrigger.triggerView();
    }

    @Override
    public void showError(@StringRes int str) {
        super.showError(str);
        //当显示错误的时候触发，肯定是结束了
        //停止loading
        mLoading.stop();
        //让控件可以输入
        mEditPhone.setEnabled(true);
        mEditPassword.setEnabled(true);
        mEditName.setEnabled(true);
        //让按钮可以点击
        mBtnSubmit.setEnabled(true);
    }

    @Override
    public void showLoading() {
        super.showLoading();
        //开始loading
        mLoading.start();
        //让控件不可以输入
        mEditPhone.setEnabled(false);
        mEditPassword.setEnabled(false);
        mEditName.setEnabled(false);
        //让按钮不可以点击
        mBtnSubmit.setEnabled(false);
    }

    @Override
    public void registerSuccess() {
        //注册成功跳转到主界面
        MainActivity.show(getContext());
        getActivity().finish();
    }

}
