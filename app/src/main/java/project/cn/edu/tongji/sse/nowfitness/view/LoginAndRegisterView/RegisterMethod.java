package project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView;

import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.Token;

public interface RegisterMethod {
    void RegisterSuccees(ResponseModel<Token> responseModel);

    void RegisterApplyError(Throwable e);
}
