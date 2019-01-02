package project.cn.edu.tongji.sse.nowfitness.model;

import java.util.List;
import java.util.PrimitiveIterator;

public class StepModelList {


    private List<StepModel> stepModels;


    private int days;

    public StepModelList(){
        //DO NOTHING
    }

    public List<StepModel> getStepModels() {
        return stepModels;
    }

    public void setStepModels(List<StepModel> stepModels) {
        this.stepModels = stepModels;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    //获得一定时间范围内的卡路里
    public int getCalories(){
        int calories = 0;
        for(StepModel stepModel:stepModels){
            calories += stepModel.getCalories();
        }
        return  calories;
    }
}
