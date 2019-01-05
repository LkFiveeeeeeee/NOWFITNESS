package project.cn.edu.tongji.sse.nowfitness.view.Datachartview;



import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.StepModelList;


/**
 * Create by LK on 2018/12/26.
 */

public interface DataChartMethod {
    void querySuccess(ResponseModel<StepModelList> stepModelListResponseModel);
    void queryError(Throwable e);
}
