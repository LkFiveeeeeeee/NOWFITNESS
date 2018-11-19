package project.cn.edu.tongji.sse.nowfitness.presenter;

import project.cn.edu.tongji.sse.nowfitness.view.LoginView;

public class LoginPresenter {
    private LoginView loginView;

    public LoginPresenter(LoginView loginView){
        this.loginView = loginView;
    }

    public void initView(){
        loginView.initView();
    }

    public boolean vertifyForUserName(String userName){

        if(userName.length() < 8){
            loginView.userNameSetError("用户名长度过短");
            return false;
        }
        loginView.userNameSetError("");
        return true;
    }

    public boolean vertifyForPassWord(String passWord){
        if(passWord.length() < 6){
            loginView.passWordSetError("密码长度过短");
            return false;
        }
        loginView.passWordSetError("");
        return true;
    }

    public void queryForVertify(String userName,String passWord){
        if(vertifyForUserName(userName) && vertifyForPassWord(passWord)){

            //TODO
            //onResponse 成功跳转主页面,失败Toast提示
        }




    }
}
