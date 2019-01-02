package project.cn.edu.tongji.sse.nowfitness.data.network.dto;

import java.util.List;

public class IndividualsDTO {
    private int totalNum;

    private List<IndividualDTO> users;

    private IndividualsDTO(){
        //DO NOTHING
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<IndividualDTO> getUsers() {
        return users;
    }

    public void setUsers(List<IndividualDTO> users) {
        this.users = users;
    }


}
