package project.cn.edu.tongji.sse.nowfitness.view.DataChartView;



import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.StepModelList;

public interface DataChartMethod {
    void querySuccess(ResponseModel<StepModelList> stepModelListResponseModel);
    void queryError(Throwable e);
}
