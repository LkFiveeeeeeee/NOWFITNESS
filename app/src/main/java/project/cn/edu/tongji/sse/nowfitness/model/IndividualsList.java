package project.cn.edu.tongji.sse.nowfitness.model;

import java.util.List;


/**
 * Create by LK on 2018/12/24.
 */

public class IndividualsList {
    private int totalNum;

    public IndividualsList(){
        //DO NOTHING
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<IndividualModel> getIndividualModels() {
        return individualModels;
    }

    public void setIndividualModels(List<IndividualModel> individualModels) {
        this.individualModels = individualModels;
    }

    public void setTrueForAll(){
        for(IndividualModel individualModel:individualModels){
            individualModel.setStated(true);
        }
    }

    private List<IndividualModel> individualModels;
}
