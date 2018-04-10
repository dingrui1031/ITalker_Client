package cn.dingrui.italker;

import cn.dingrui.common.app.BaseActivity;
import cn.dingrui.italker.activities.MainActivity;
import cn.dingrui.italker.frags.assist.PermissionsFragment;

/**
 * Created by dingrui
 */

public class LaunchActivity extends BaseActivity {
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onResume() {
        super.onResume();

        //检查权限
        if (PermissionsFragment.haveAll(this, getSupportFragmentManager())) {
            MainActivity.show(this);
            finish();
        }
    }
}
