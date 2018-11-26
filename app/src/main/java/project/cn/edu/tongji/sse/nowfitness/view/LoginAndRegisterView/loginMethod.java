package project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView;

import project.cn.edu.tongji.sse.nowfitness.model.SignModel;

public interface loginMethod {
    void loginSuccess(SignModel signModel);
    void loginError(Throwable e);
}
