package project.cn.edu.tongji.sse.nowfitness.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.TokenDTO;

@Entity
public class Token {
    @Transient
    public static int TOKEN_ID = 1;

    @Id
    long id;
    
    String tokenValue;


    @Generated(hash = 47599984)
    public Token(long id, String tokenValue) {
        this.id = id;
        this.tokenValue = tokenValue;
    }

    @Generated(hash = 79808889)
    public Token() {
    }

    public Token(TokenDTO tokenDTO){
        this.tokenValue = tokenDTO.getToken();
    }

    public String getTokenValue() {
        return this.tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
