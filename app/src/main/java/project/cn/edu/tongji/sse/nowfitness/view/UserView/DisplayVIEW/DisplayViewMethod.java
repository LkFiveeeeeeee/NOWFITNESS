package project.cn.edu.tongji.sse.nowfitness.view.UserView.DisplayVIEW;

import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.model.IndividualModel;

public interface DisplayViewMethod {
    void queryForSuccess(List<IndividualModel> modelList);
    void queryError(Throwable e);
}
