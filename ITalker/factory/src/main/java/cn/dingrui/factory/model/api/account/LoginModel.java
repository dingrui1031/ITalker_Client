package cn.dingrui.factory.model.api.account;

/**
 * Created by dingrui
 */

public class LoginModel {

    private String account;
    private String password;
    private String name;

    public LoginModel(String account, String password) {
        this(account, password, null);
    }

    public LoginModel(String account, String password, String name) {
        this.account = account;
        this.password = password;
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
