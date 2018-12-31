package project.cn.edu.tongji.sse.nowfitness.view.userview.DisplayVIEW;

import project.cn.edu.tongji.sse.nowfitness.model.IndividualsList;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;

public interface DisplayViewMethod {
    void queryForSuccess(ResponseModel<IndividualsList> modelList);
    void queryError(Throwable e);
}
