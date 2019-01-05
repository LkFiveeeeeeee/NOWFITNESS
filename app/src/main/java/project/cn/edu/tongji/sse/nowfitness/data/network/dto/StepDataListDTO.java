package project.cn.edu.tongji.sse.nowfitness.data.network.dto;

/**
 * Create by LK on 2018/1/2.
 */

public class StepDataListDTO {
    /**
     * id : 2
     * date : 2018-12-20T00:00:00.000+0800
     * steps : 100
     * calories : 250
     */

    private int id;
    private String date;
    private int steps;
    private int calories;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
