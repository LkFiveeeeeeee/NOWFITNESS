package project.cn.edu.tongji.sse.nowfitness.model;

import java.util.List;

public class IndividualsList {
    private int totalNum;

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

    private List<IndividualModel> individualModels;
}
