package project.cn.edu.tongji.sse.nowfitness.view.UserView.DisplayVIEW;

import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;

public interface FollowMethod {
    void postSuccess(ResponseModel responseModel);
    void deleteSuccess(ResponseModel responseModel);
    void sendError(Throwable e);
}
