package project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView;

import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.Token;

public interface loginMethod {
    void loginSuccess(ResponseModel<Token> responseModel);
    void loginError(Throwable e);
}
