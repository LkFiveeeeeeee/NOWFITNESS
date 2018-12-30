package project.cn.edu.tongji.sse.nowfitness.model;

import java.util.ArrayList;
import java.util.List;

public class QuestionList {
    private static QuestionList questionList;


    private List<Question> questions;



    private int score = 0;


    private int[] bonusIndex = new int[10];


    public int[] getBonusIndex() {
        return bonusIndex;
    }

    public QuestionList(){
        initList();
        initBonus();
    }

    public static QuestionList get(){
        if(questionList == null){
            questionList = new QuestionList();
        }
        return questionList;
    }
    public List<Question> getQuestions() {
        return questions;
    }


    private void initList(){
        questions = new ArrayList<>();
        questions.add(new Question("昨夜睡眠时间",new String[]{"7.5-9.5小时","9.5小时以上",
            "6-7.5小时","6小时以下"},new int[]{10,8,7,5}));
        questions.add(new Question("今日心情状态",new String[]{"开心兴奋","郁闷压抑",
            "紧张焦虑","悲伤难过"},new int[]{10,7,7,5}));
        questions.add(new Question("当前饥饿状态",new String[]{"饭后2-3小时","饭前1-2小时",
            "空腹","饭后一小时内"},new int[]{10,9,7,2}));
        questions.add(new Question("今日身体概况",new String[]{"无任何不适","身体某部分肌肉有轻微酸痛",
                "身体器官或关节有轻微不适","身体某部分肌肉严重酸痛","身体器官或关节有一样"},
                new int[]{10,8,6,2,0}));
        questions.add(new Question("今日工作或者学习时长",new String[]{"6小时以内","6-8小时","8-10小时",
            "10小时以上"},new int[]{9,7,6,3}));
        questions.add(new Question("当前时间",new String[]{"15:00-18:00","19:00-22:00",
                "9:00-11:00","5:00-6:30","其他"},new int[]{10,9,7,5,4}));
        questions.add(new Question("近期运动频率",new String[]{"每周5-7次","每周3-4次",
                "每周1-2次","极少"},new int[]{10,9,6,3}));
        questions.add(new Question("近期长做的运动类型",new String[]{"高强度无氧运动(举重、短跑等)",
            "高强度有氧运动(长跑、激烈球类运动等)","中强度运动(慢跑、健身等)","低强度运动(太极、瑜伽等)","无"}
            ,new int[]{10,10,8,6,2}));
        questions.add(new Question("最近一次激烈运动时间",new String[]{"96-48小时内","48-24小时内",
            "24-12小时内","12小时内","其他"},new int[]{10,9,7,4,3}));
        questions.add(new Question("近期运动目标",new String[]{"准备比赛","高效减脂或增肌","维持或提高身体素质",
            "休闲娱乐","调节身心"},new int[]{10,10,8,6,4}));
    }

    private void initBonus(){
        for(int i = 0;i < 10;i++){
            bonusIndex[i] = -1;
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean calculateScore(){
        for(int i = 0;i < bonusIndex.length;i++){
            if(bonusIndex[i] == -1){
                score = 0;
                return false;
            }
            score += questions.get(i).getBonus()[bonusIndex[i]];
        }
        return true;
    }

}
