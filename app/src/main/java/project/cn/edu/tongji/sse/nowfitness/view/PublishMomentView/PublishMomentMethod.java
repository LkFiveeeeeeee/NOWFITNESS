package project.cn.edu.tongji.sse.nowfitness.view.PublishMomentView;

import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;

/**
 * Created by LK on 2018/11/26.
 */

public interface PublishMomentMethod {
    void postSuccess(ResponseModel responseModel);
    void postError(Throwable e);
}
