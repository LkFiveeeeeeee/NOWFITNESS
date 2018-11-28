package project.cn.edu.tongji.sse.nowfitness.view.MomentsView;

import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;

public interface MomentsMethod {
    void querySuccess(List<MomentsModel> models);
    void queryError(Throwable e);
}
