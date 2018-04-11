package cn.dingrui.factory;

import android.app.Application;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import cn.dingrui.common.common.app.BaseApplication;

/**
 * Created by dingrui
 */

public class Factory {

    // 单例模式ø
    private static final Factory instance;
    private final Executor executor;

    static {
        instance = new Factory();
    }

    private Factory() {
        // 新建一个4个线程的线程池
        executor = Executors.newFixedThreadPool(4);
    }

    /**
     * 返回全局的Application
     *
     * @return Application
     */
    public static Application app() {
        return BaseApplication.getInstance();
    }


    /**
     * 异步运行的方法
     *
     * @param runnable Runnable
     */
    public static void runOnAsync(Runnable runnable) {
        // 拿到单例，拿到线程池，然后异步执行
        instance.executor.execute(runnable);
    }

}
