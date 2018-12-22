package project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView;

import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.Token;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;

public interface LoginMethod {
    void loginSuccess(ResponseModel<Token> responseModel);
    void loginError(Throwable e);
    void querySuccess(ResponseModel<UserInfoModel> responseModel);

}
