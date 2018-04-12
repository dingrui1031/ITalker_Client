package cn.dingrui.factory.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import cn.dingrui.factory.Factory;
import cn.dingrui.factory.model.api.account.AccountRspModel;
import cn.dingrui.factory.model.db.User;
import cn.dingrui.factory.model.db.User_Table;


/**
 * 持久化存储
 * Created by dingrui
 */

public class Account {

    private static final String KEY_PUSH_ID = "KEY_PUSH_ID";
    private static final String KEY_IS_BIND = "KEY_IS_BIND";
    private static final String KEY_TOKEN = "KEY_TOKEN";
    private static final String KEY_USER_ID = "KEY_USER_ID";
    private static final String KEY_ACCOUNT = "KEY_ACCOUNT";

    // 设备的推送Id
    private static String pushId;
    // 设备Id是否已经绑定到了服务器
    private static boolean isBind;
    // 登录状态的Token，用来接口请求
    private static String token;
    // 登录的用户ID
    private static String userId;
    // 登录的账户
    private static String account;

    /**
     * 存储数据到xml，进行持久化
     * @param context
     */
    private static void save(Context context){
        //获取数据持久化的SP
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(),Context.MODE_PRIVATE);
        //数据存储
        sp.edit()
                .putString(KEY_PUSH_ID, pushId)
                .putBoolean(KEY_IS_BIND, isBind)
                .putString(KEY_TOKEN, token)
                .putString(KEY_USER_ID, userId)
                .putString(KEY_ACCOUNT, account)
                //异步操作
                .apply();
    }

    /**
     * 进行数据加载
     * @param context
     */
    public static void load(Context context){
        //获取数据持久化的SP
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(),Context.MODE_PRIVATE);
        //获取数据
        pushId = sp.getString(KEY_PUSH_ID, "");
        isBind = sp.getBoolean(KEY_IS_BIND, false);
        token = sp.getString(KEY_TOKEN, "");
        userId = sp.getString(KEY_USER_ID, "");
        account = sp.getString(KEY_ACCOUNT, "");
    }

    /**
     * 设置并存储设备的Id
     *
     * @param pushId 设备的推送ID
     */
    public static void setPushId(String pushId) {
        Account.pushId = pushId;
    }

    public static String getPushId() {
        return pushId;
    }

    /**
     * 返回当前账户是否登录
     * @return true已登录
     */
    public static boolean isLogin(){
        // 用户Id 和 Token 不为空
        return !TextUtils.isEmpty(userId)
                && !TextUtils.isEmpty(token);
    }

    /**
     * 是否已经完善了用户信息
     *
     * @return True 是完成了
     */
    public static boolean isComplete() {
        // 首先保证登录成功
        if (isLogin()) {
            User self = getUser();
            return !TextUtils.isEmpty(self.getDesc())
                    && !TextUtils.isEmpty(self.getPortrait())
                    && self.getSex() != 0;
        }
        // 未登录返回信息不完全
        return false;
    }


    /**
     * 是否已经绑定到了服务器
     *
     * @return True已绑定
     */
    public static boolean isBind() {
        return isBind;
    }

    /**
     * 设置绑定状态
     */
    public static void setBind(boolean isBind) {
        Account.isBind = isBind;
        Account.save(Factory.app());
    }

    /**
     * 保存我自己的信息到持久化XML中
     *
     * @param model AccountRspModel
     */
    public static void login(AccountRspModel model) {
        // 存储当前登录的账户, token, 用户Id，方便从数据库中查询我的信息
        Account.token = model.getToken();
        Account.account = model.getAccount();
        Account.userId = model.getUser().getId();
        save(Factory.app());
    }

    /**
     * 获取当前登录的用户信息
     *
     * @return User
     */
    public static User getUser() {
        // 如果为null返回一个new的User，其次从数据库查询
        return TextUtils.isEmpty(userId) ? new User() : SQLite.select()
                .from(User.class)
                .where(User_Table.id.eq(userId))
                .querySingle();
    }

}
