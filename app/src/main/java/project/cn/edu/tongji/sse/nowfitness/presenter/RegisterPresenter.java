package project.cn.edu.tongji.sse.nowfitness.presenter;

import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import project.cn.edu.tongji.sse.nowfitness.data.APIRepositoryImpl;
import project.cn.edu.tongji.sse.nowfitness.model.Constant;
import project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView.RegisterMethod;
import project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView.RegisterView;

/**
 * Created by LK on 2018/11/18.
 */

public class RegisterPresenter extends BasePresenter{
    private RegisterView registerView;
    private RegisterMethod registerMethod;

    public RegisterPresenter(RegisterView registerView, RegisterMethod registerMethod){
        apiRepository = new APIRepositoryImpl();
        this.registerMethod = registerMethod;
        this.registerView = registerView;
    }

    public void showAnimate(){
        registerView.showEnterAnimation();
    }

    public void initView(){
        registerView.initView();
    }

    public boolean verifyUserName(String userName){
        if(userName.length() < Constant.MIN_LENGTH){
            registerView.userNameSetError("用户名过短");
            return false;
        }
        registerView.userNameSetError("");
        return true;
    }

    public boolean verifyPassWord(String passWord){
        if(passWord.length() < Constant.MIN_LENGTH){
            registerView.passWordSetError("密码过短");
            return false;
        }
        registerView.passWordSetError("");
        return true;
    }

    public boolean verifyPassWordAgain(String passWord, String passWordTwo){
        if(!passWord.equals(passWordTwo)){
            registerView.repeatPassWordError("两次密码不相同");
            return false;
        }
        registerView.repeatPassWordError("");
        return true;
    }

    public void applyRegister(String userName,String passWord){
        RequestBody userNameBody = RequestBody.create(MediaType.parse("text/plain"),userName);
        RequestBody passWordBody = RequestBody.create(MediaType.parse("text/plain"),passWord);
        Log.d("applyRegister", "applyRegister: ");
        subscriptions.add(apiRepository.applyInfo(userNameBody,passWordBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(registerMethod::registerSuccess,registerMethod::netError)
        );
    }

    public void queryForUserInfo(String userName){
        subscriptions.add(apiRepository.queryUserInfo(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(registerMethod::querySuccess,registerMethod::netError)
        );
    }

}
