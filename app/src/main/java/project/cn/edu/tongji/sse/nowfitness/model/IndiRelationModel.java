package project.cn.edu.tongji.sse.nowfitness.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class IndiRelationModel {
    @Id(autoincrement = true)
    Long id;

    long starId;
    long followId;








    @Generated(hash = 635889064)
    public IndiRelationModel(Long id, long starId, long followId) {
        this.id = id;
        this.starId = starId;
        this.followId = followId;
    }
    @Generated(hash = 1199831523)
    public IndiRelationModel() {
    }







   
    public Long getPrimaryId() {
        return this.id;
    }
    public void setPrimaryId(Long primaryId) {
        this.id = primaryId;
    }
    public long getStarId() {
        return this.starId;
    }
    public void setStarId(long starId) {
        this.starId = starId;
    }
    public long getFollowId() {
        return this.followId;
    }
    public void setFollowId(long followId) {
        this.followId = followId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
