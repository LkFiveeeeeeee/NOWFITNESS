package project.cn.edu.tongji.sse.nowfitness.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;



@Entity
public class StepModel {
    @Id(autoincrement = true)
    private Long id;

    private int userId;
    private String step;
    private String today;

    @Generated(hash = 2139198900)
    public StepModel(Long id, int userId, String step, String today) {
        this.id = id;
        this.userId = userId;
        this.step = step;
        this.today = today;
    }

    @Generated(hash = 654390059)
    public StepModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }
}
