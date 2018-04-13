package cn.dingrui.factory.presenter.user;


import cn.dingrui.common.factory.presenter.BaseContract;

/**
 * 更新用户信息的基本的契约
 * @author dingrui
 */
public interface UpdateInfoContract {
    interface Presenter extends BaseContract.Presenter {
        // 更新
        void update(String photoFilePath, String desc, boolean isMan);
    }

    interface View extends BaseContract.View<Presenter> {
        // 回调成功
        void updateSucceed();
    }
}
