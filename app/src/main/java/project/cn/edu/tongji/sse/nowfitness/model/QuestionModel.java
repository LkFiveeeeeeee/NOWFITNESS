package project.cn.edu.tongji.sse.nowfitness.model;

public class QuestionModel {
    private String questionText;
    private String chooseAText;
    private String chooseBText;
    private String chooseCText;
    private String chooseDText;

    public QuestionModel(String question,String A,String B,String C,String D){
        questionText = question;
        chooseAText = A;
        chooseBText = B;
        chooseCText = C;
        chooseDText = D;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getChooseAText() {
        return chooseAText;
    }

    public void setChooseAText(String chooseAText) {
        this.chooseAText = chooseAText;
    }

    public String getChooseBText() {
        return chooseBText;
    }

    public void setChooseBText(String chooseBText) {
        this.chooseBText = chooseBText;
    }

    public String getChooseCText() {
        return chooseCText;
    }

    public void setChooseCText(String chooseCText) {
        this.chooseCText = chooseCText;
    }

    public String getChooseDText() {
        return chooseDText;
    }

    public void setChooseDText(String chooseDText) {
        this.chooseDText = chooseDText;
    }


}
