package project.cn.edu.tongji.sse.nowfitness.presenter;

import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import project.cn.edu.tongji.sse.nowfitness.data.APIRepositaryImpl;
import project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView.RegisterMethod;
import project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView.RegisterView;

public class RegisterPresenter extends BasePresenter{
    private RegisterView registerView;
    private RegisterMethod registerMethod;
    public RegisterPresenter(RegisterView registerView, RegisterMethod registerMethod){
        apiRepositary = new APIRepositaryImpl();
        this.registerMethod = registerMethod;
        this.registerView = registerView;
    }

    public void showAnimate(){
        registerView.showEnterAnimaton();
    }

    public void initView(){
        registerView.initView();
    }

    public boolean vertifyUserName(String userName){
        if(userName.length() < 6){
            registerView.userNameSetError("用户名过短");
            return false;
        }
        registerView.userNameSetError("");
        return true;
    }

    public boolean vertifyPassWord(String passWord){
        if(passWord.length() < 8){
            registerView.passWordSetError("密码过短");
            return false;
        }
        registerView.passWordSetError("");
        return true;
    }

    public boolean vertifyPassWordAgain(String passWord,String passWordTwo){
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
        subscriptions.add(apiRepositary.applyInfo(userNameBody,passWordBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(registerMethod::RegisterSuccees,registerMethod::RegisterApplyError)
        );
    }

}
