package cn.dingrui.factory.net;

import cn.dingrui.factory.model.api.RspModel;
import cn.dingrui.factory.model.api.account.AccountRspModel;
import cn.dingrui.factory.model.api.account.LoginModel;
import cn.dingrui.factory.model.api.account.RegisterModel;
import cn.dingrui.factory.model.api.user.UserUpdateModel;
import cn.dingrui.factory.model.card.UserCard;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * 网络请求的所有的接口
 * @author dingrui
 */
public interface RemoteService {

    /**
     * 注册接口
     *
     * @param model 传入的是RegisterModel
     * @return 返回的是RspModel<AccountRspModel>
     */
    @POST("account/register")
    Call<RspModel<AccountRspModel>> accountRegister(@Body RegisterModel model);

    /**
     * 登录接口
     *
     * @param model LoginModel
     * @return RspModel<AccountRspModel>
     */
    @POST("account/login")
    Call<RspModel<AccountRspModel>> accountLogin(@Body LoginModel model);

    /**
     * 绑定设备Id
     *
     * @param pushId 设备Id
     * @return RspModel<AccountRspModel>
     */
    @POST("account/bind/{pushId}")
    Call<RspModel<AccountRspModel>> accountBind(@Path(encoded = true, value = "pushId") String pushId);

    // 用户更新的接口
    @PUT("user/update")
    Call<RspModel<UserCard>> userUpdate(@Body UserUpdateModel model);

}
