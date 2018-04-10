package cn.dingrui.italker.activities;

import android.content.Context;
import android.content.Intent;

import cn.dingrui.common.app.BaseActivity;
import cn.dingrui.italker.R;
import cn.dingrui.italker.frags.account.UpdateInfoFragment;

/**
 * Created by dingrui
 */

public class AccountActivity extends BaseActivity {

    private UpdateInfoFragment mUpdateInfoFragment;

    /**
     * 账户Activity显示的入口
     *
     * @param context Context
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        mUpdateInfoFragment = new UpdateInfoFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container,mUpdateInfoFragment)
                .commit();
    }

    // Activity中收到剪切图片成功的回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mUpdateInfoFragment.onActivityResult(requestCode, resultCode, data);
    }
}
