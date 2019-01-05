package project.cn.edu.tongji.sse.nowfitness.presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import project.cn.edu.tongji.sse.nowfitness.model.Constant;
import project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView.LoginView;
import project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView.LoginMethod;

public class LoginPresenter extends BasePresenter{
    private final LoginView loginView;
    private final LoginMethod loginMethod;

    public LoginPresenter(LoginView loginView,LoginMethod loginMethod){
        this.loginView = loginView;
        this.loginMethod = loginMethod;
    }

    public void initView(){
        loginView.initView();
    }

    // 验证用户名长度
    public boolean verifyForUserName(String userName){

        if(userName.length() < Constant.MIN_LENGTH){
            loginView.userNameSetError("用户名长度过短");
            return false;
        }
        loginView.userNameSetError("");
        return true;
    }

    //验证密码长度
    public boolean verifyForPassWord(String passWord){
        if(passWord.length() < Constant.MIN_LENGTH){
            loginView.passWordSetError("密码长度过短");
            return false;
        }
        loginView.passWordSetError("");
        return true;
    }

    //向后台请求登录
    public void queryForVerify(String userName, String passWord){
        if(verifyForUserName(userName) && verifyForPassWord(passWord)){
            RequestBody userNameBody = RequestBody.create(MediaType.parse("text/plain"),userName);
            RequestBody passWordBody = RequestBody.create(MediaType.parse("text/plain"),passWord);
            subscriptions.add(apiRepository.verifyInfo(userNameBody,passWordBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(loginMethod::loginSuccess,loginMethod::netError)
            );
            //onResponse 成功跳转主页面,失败Toast提示
        }
    }

    public void queryForUserInfo(String userName){
        subscriptions.add(apiRepository.queryUserInfo(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginMethod::querySuccess,loginMethod::netError)
        );
    }
}
