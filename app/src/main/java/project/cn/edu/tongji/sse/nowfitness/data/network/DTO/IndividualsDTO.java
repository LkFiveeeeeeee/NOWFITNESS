package project.cn.edu.tongji.sse.nowfitness.data.network.DTO;

import java.util.List;

public class IndividualsDTO {
    private int totalNum;

    private List<IndividualDTO> users;

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
