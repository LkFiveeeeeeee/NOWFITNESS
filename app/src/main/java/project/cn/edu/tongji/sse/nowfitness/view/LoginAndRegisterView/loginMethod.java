package project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView;

import project.cn.edu.tongji.sse.nowfitness.model.LoginModel;

public interface loginMethod {
    void loginSuccess(LoginModel loginModel);
    void loginError(Throwable e);
}
