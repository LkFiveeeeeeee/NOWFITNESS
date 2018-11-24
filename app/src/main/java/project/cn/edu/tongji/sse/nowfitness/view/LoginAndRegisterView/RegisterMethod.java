package project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView;

import android.widget.Toast;

import project.cn.edu.tongji.sse.nowfitness.model.LoginModel;

public interface RegisterMethod {
    void RegisterSuccees(LoginModel loginModel);

    void RegisterApplyError(Throwable e);
}
