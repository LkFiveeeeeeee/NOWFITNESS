package project.cn.edu.tongji.sse.nowfitness.presenter;

import project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView.RegisterView;

public class RegisterPresenter {
    private RegisterView registerView;
    public RegisterPresenter(RegisterView registerView){
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
}
