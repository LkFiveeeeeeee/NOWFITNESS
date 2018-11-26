package project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView;

import project.cn.edu.tongji.sse.nowfitness.model.SignModel;

public interface RegisterMethod {
    void RegisterSuccees(SignModel signModel);

    void RegisterApplyError(Throwable e);
}
