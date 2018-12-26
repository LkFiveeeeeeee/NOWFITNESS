package project.cn.edu.tongji.sse.nowfitness.model;

public class Question {
    private String[] options;
    private int[] bonus;
    private String title;

    Question(String title, String[] options, int[] bonus) {
        this.options = options;
        this.bonus = bonus;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    int[] getBonus() {
        return bonus;
    }

    public void setBonus(int[] bonus) {
        this.bonus = bonus;
    }

    public int getOptionsSize(){
        if(options != null) {
            return options.length;
        }else {
            return -1;
        }
    }

    public int getBonusSize(){
        if(bonus != null) {
            return bonus.length;
        }else {
            return -1;
        }
    }
}
