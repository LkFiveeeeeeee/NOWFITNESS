package project.cn.edu.tongji.sse.nowfitness.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class IndiInfoModel {
    @Id(autoincrement = true)
    Long id;

    String userName;
    String sex;
    String avatarUrl;







    @Generated(hash = 646748371)
    public IndiInfoModel(Long id, String userName, String sex, String avatarUrl) {
        this.id = id;
        this.userName = userName;
        this.sex = sex;
        this.avatarUrl = avatarUrl;
    }
    @Generated(hash = 2033531147)
    public IndiInfoModel() {
    }







    public Long getPrimaryId() {
        return this.id;
    }
    public void setPrimaryId(Long primaryId) {
        this.id = primaryId;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getAvatarUrl() {
        return this.avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
