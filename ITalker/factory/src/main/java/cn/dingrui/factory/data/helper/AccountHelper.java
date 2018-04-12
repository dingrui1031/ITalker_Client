package cn.dingrui.factory.data.helper;

import android.text.TextUtils;

import cn.dingrui.common.factory.data.DataSource;
import cn.dingrui.factory.Factory;
import cn.dingrui.factory.R;
import cn.dingrui.factory.model.api.RspModel;
import cn.dingrui.factory.model.api.account.AccountRspModel;
import cn.dingrui.factory.model.api.account.RegisterModel;
import cn.dingrui.factory.model.db.User;
import cn.dingrui.factory.net.NetWork;
import cn.dingrui.factory.net.RemoteService;
import cn.dingrui.factory.persistence.Account;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dingrui
 */

public class AccountHelper {

    /**
     * 注册的接口，异步调用
     *
     * @param registerModel 传递一个注册的model
     * @param callback      成功与失败的接口回送
     */
    public static void register(RegisterModel registerModel, final DataSource.Callback<User> callback) {
        //调用Retrofit对我们的网络请求接口做代理
        RemoteService remoteService = NetWork.remote();
        //得到一个Call
        Call<RspModel<AccountRspModel>> call = remoteService.accountRegister(registerModel);
        //异步请求
        call.enqueue(new Callback<RspModel<AccountRspModel>>() {
            @Override
            public void onResponse(Call<RspModel<AccountRspModel>> call, Response<RspModel<AccountRspModel>> response) {
                //网络请求成功
                //从返回中得到我们全局的model，内部使用Gson解析
                RspModel<AccountRspModel> rspModel = response.body();
                if (rspModel.success()) {
                    //拿到实体
                    AccountRspModel accountRspModel = rspModel.getResult();
                    //判断绑定状态，是否绑定设备
                    if (accountRspModel.isBind()) {
                        User user = accountRspModel.getUser();
                        //进行数据库写入和缓存绑定
                        // 第一种，之间保存
                        user.save();
                    /*
                    // 第二种通过ModelAdapter
                    FlowManager.getModelAdapter(User.class)
                            .save(user);

                    // 第三种，事务中
                    DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
                    definition.beginTransactionAsync(new ITransaction() {
                        @Override
                        public void execute(DatabaseWrapper databaseWrapper) {
                            FlowManager.getModelAdapter(User.class)
                                    .save(user);
                        }
                    }).build().execute();
                    */
                        //同步到xml持久化
                        Account.login(accountRspModel);
                        //判断绑定状态
                        if (accountRspModel.isBind()) {
                            //然后返回
                            callback.onDataLoaded(user);
                        } else {
                            //进行绑定
                            bindPush(callback);
                        }
                    }
                } else {
                    // todo 错误解析
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
                //网络请求失败
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }

    /**
     * 对设备id进行绑定
     *
     * @param callback
     */
    public static void bindPush(final DataSource.Callback<User> callback) {
        String pushId = Account.getPushId();
        //检查是否为空
        if (TextUtils.isEmpty(pushId))
            return;

        NetWork.remote();
        Account.setBind(true);
    }

    /**
     * 请求的回调部分封装
     */
    private static class AccountRspCallback implements Callback<RspModel<AccountRspModel>> {

        final DataSource.Callback<User> callback;

        AccountRspCallback(DataSource.Callback<User> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<RspModel<AccountRspModel>> call,
                               Response<RspModel<AccountRspModel>> response) {
            // 请求成功返回
            // 从返回中得到我们的全局Model，内部是使用的Gson进行解析
            RspModel<AccountRspModel> rspModel = response.body();
            if (rspModel.success()) {
                // 拿到实体
                AccountRspModel accountRspModel = rspModel.getResult();
                // 获取我的信息
                User user = accountRspModel.getUser();
                // 第一种，之间保存
                user.save();
                    /*
                    // 第二种通过ModelAdapter
                    FlowManager.getModelAdapter(User.class)
                            .save(user);

                    // 第三种，事务中
                    DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
                    definition.beginTransactionAsync(new ITransaction() {
                        @Override
                        public void execute(DatabaseWrapper databaseWrapper) {
                            FlowManager.getModelAdapter(User.class)
                                    .save(user);
                        }
                    }).build().execute();
                    */
                // 同步到XML持久化中
                Account.login(accountRspModel);

                // 判断绑定状态，是否绑定设备
                if (accountRspModel.isBind()) {
                    // 设置绑定状态为True
                    Account.setBind(true);
                    // 然后返回
                    if (callback != null)
                        callback.onDataLoaded(user);
                } else {
                    // 进行绑定的唤起
                    bindPush(callback);
                }
            } else {
                // 错误解析
                Factory.decodeRspCode(rspModel, callback);
            }
        }

        @Override
        public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
            // 网络请求失败
            if (callback != null)
                callback.onDataNotAvailable(R.string.data_network_error);
        }
    }
}
