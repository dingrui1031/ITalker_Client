package cn.dingrui.italker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;

import cn.dingrui.factory.Factory;
import cn.dingrui.factory.data.helper.AccountHelper;
import cn.dingrui.factory.persistence.Account;

/**
 * Created by dingrui
 */

public class MessageReceiver extends BroadcastReceiver {

    private static final String TAG = MessageReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null)
            return;

        Bundle bundle = intent.getExtras();

        //判断当前消息的意图
        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_CLIENTID:
                Log.e(TAG, "GET_CLIENTID:" + bundle.toString());
                //当id初始化的时候
                //获取设备id
                onClientInit(bundle.getString("clientid"));
                break;
            case PushConsts.GET_MSG_DATA:
                //常规消息送达
                byte[] payloads = bundle.getByteArray("payload");
                if (payloads != null) {
                    String message = new String(payloads);
                    Log.e(TAG, "GET_MSG_DATA:" + message);
                    //当消息送达时
                    onMessageArrived(message);
                }
                break;
            default:
                Log.e(TAG, "OTHER:" + bundle.toString());
                break;
        }
    }

    /**
     * 当id初始化的时候
     *
     * @param id 设备id
     */
    private void onClientInit(String id) {
        //设置设备id
        Account.setPushId(id);
        if (Account.isLogin()) {
            //已登录，进行设备id绑定
            AccountHelper.bindPush(null);
        }
    }

    /**
     * 当消息送达时
     *
     * @param message 新消息
     */
    private void onMessageArrived(String message) {
        // 交给Factory处理
        Factory.dispatchPush(message);
    }

}
