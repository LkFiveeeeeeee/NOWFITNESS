package project.cn.edu.tongji.sse.nowfitness.presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView.LoginView;
import project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView.LoginMethod;

public class LoginPresenter extends BasePresenter{
    private LoginView loginView;
    private LoginMethod loginMethod;

    public LoginPresenter(LoginView loginView,LoginMethod loginMethod){
        this.loginView = loginView;
        this.loginMethod = loginMethod;
    }

    public void initView(){
        loginView.initView();
    }

    public boolean verifyForUserName(String userName){

        if(userName.length() < 6){
            loginView.userNameSetError("用户名长度过短");
            return false;
        }
        loginView.userNameSetError("");
        return true;
    }

    public boolean verifyForPassWord(String passWord){
        if(passWord.length() < 6){
            loginView.passWordSetError("密码长度过短");
            return false;
        }
        loginView.passWordSetError("");
        return true;
    }

    public void queryForVerify(String userName, String passWord){
        if(verifyForUserName(userName) && verifyForPassWord(passWord)){
            RequestBody userNameBody = RequestBody.create(MediaType.parse("text/plain"),userName);
            RequestBody passWordBody = RequestBody.create(MediaType.parse("text/plain"),passWord);
            subscriptions.add(apiRepository.verifyInfo(userNameBody,passWordBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(loginMethod::loginSuccess,loginMethod::loginError)
            );
            //onResponse 成功跳转主页面,失败Toast提示
        }
    }

    public void queryForUserInfo(String userName){
        subscriptions.add(apiRepository.queryUserInfo(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginMethod::querySuccess,loginMethod::loginError)
        );
    }
}
