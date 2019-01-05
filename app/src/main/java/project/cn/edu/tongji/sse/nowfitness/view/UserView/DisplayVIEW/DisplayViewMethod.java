package project.cn.edu.tongji.sse.nowfitness.view.UserView.DisplayVIEW;

import project.cn.edu.tongji.sse.nowfitness.model.IndividualsList;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;

/**
 * Created by LK on 2018/11/28.
 */

public interface DisplayViewMethod {
    void queryForSuccess(ResponseModel<IndividualsList> modelList);
    void queryError(Throwable e);
}
