package project.cn.edu.tongji.sse.nowfitness.model;


import project.cn.edu.tongji.sse.nowfitness.data.network.dto.IndividualDTO;

public class IndividualModel {


    private int id;
    private String picture;
    private String userName;
    private String nickName;
    private String sex;
    private boolean stated;

    public IndividualModel(IndividualDTO individualDTO){
        this.id = individualDTO.getId();
        this.picture = individualDTO.getPicture();
        this.userName = individualDTO.getUserName();
        this.nickName = individualDTO.getNickName();
        this.sex = individualDTO.getSex();
        this.stated = individualDTO.isStates();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isStated() {
        return stated;
    }

    public void setStated(boolean stated) {
        this.stated = stated;
    }







}
