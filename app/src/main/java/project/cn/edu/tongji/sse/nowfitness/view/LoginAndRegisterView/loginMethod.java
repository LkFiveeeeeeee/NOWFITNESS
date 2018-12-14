package project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView;

import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;

public interface loginMethod {
    void loginSuccess(ResponseModel responseModel);
    void loginError(Throwable e);
}
