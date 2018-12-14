package project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView;

import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;

public interface RegisterMethod {
    void RegisterSuccees(ResponseModel responseModel);

    void RegisterApplyError(Throwable e);
}
