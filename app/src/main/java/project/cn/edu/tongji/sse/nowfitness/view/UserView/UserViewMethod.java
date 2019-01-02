package project.cn.edu.tongji.sse.nowfitness.view.UserView;

import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;

public interface UserViewMethod {
    void initUserView();
    void setBMINum();
    void setListener();
    void queryError(Throwable e);
    void shakeSuccess(String s);
    void applyForImageChange(ResponseModel responseDTO);
}
