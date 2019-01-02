package project.cn.edu.tongji.sse.nowfitness.view.PublishMomentView;

import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;

public interface PublishMomentMethod {
    void postSuccess(ResponseModel responseModel);
    void postError(Throwable e);
}
