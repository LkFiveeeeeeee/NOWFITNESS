package project.cn.edu.tongji.sse.nowfitness.data.network.dto;

public class IndividualDTO {
    /**
     * id : 5
     * userName : bbb
     * password :
     * height : 110
     * weight : 1
     * sex : man
     * age : 0
     * picture : 4604f7044a7a4fccbd942e50d7dc23ef.jpg
     * salt :
     * token :
     * states : false
     * nickName :
     */

    private int id;
    private String userName;
    private String password;
    private String sex;
    private int age;
    private String picture;
    private String salt;
    private String token;
    private boolean states;
    private String nickName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isStates() {
        return states;
    }

    public void setStates(boolean states) {
        this.states = states;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
