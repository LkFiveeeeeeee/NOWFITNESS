package project.cn.edu.tongji.sse.nowfitness.view.MomentsView;

import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModelList;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;

public interface MomentsMethod {
    void querySuccess(ResponseModel<MomentsModelList> models);
    void queryError(Throwable e);
}
