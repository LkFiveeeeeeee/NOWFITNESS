package project.cn.edu.tongji.sse.nowfitness.data.network.dto;

import java.util.List;


/**
 * Create by LK on 2018/12/26.
 */

public class StepDataDTO {

    /**
     * days : 3
     * stepsDataModelList : [{"id":2,"date":"2018-12-20T00:00:00.000+0800","steps":100,"calories":250}]
     */

    private int days;
    private List<StepDataListDTO> stepsDataModelList;

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public List<StepDataListDTO> getStepsDataModelList() {
        return stepsDataModelList;
    }

    public void setStepsDataModelList(List<StepDataListDTO> stepsDataModelList) {
        this.stepsDataModelList = stepsDataModelList;
    }


}
