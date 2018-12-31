package project.cn.edu.tongji.sse.nowfitness.model;

import project.cn.edu.tongji.sse.nowfitness.data.network.dto.RelationDTO;

public class FollowingRelation {
    private boolean states;

    public void setStates(boolean states) {
        this.states = states;
    }

    public boolean getStates() {
        return states;
    }
    public FollowingRelation(RelationDTO relationDTO){
        this.states = relationDTO.getStates();
    }
}
