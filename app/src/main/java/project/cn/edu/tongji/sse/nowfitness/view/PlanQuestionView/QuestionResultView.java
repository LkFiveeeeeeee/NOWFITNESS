package project.cn.edu.tongji.sse.nowfitness.view.PlanQuestionView;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.R;

public class QuestionResultView extends AppCompatActivity {

    private int type;
    private List<String> sportsExamples;
    private List<String> functions;
    private List<String> people;
    private int []  image;
    private TextView sportsExa,sportsFun,sportsPeo;
    private ImageView heartImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        //type = intent.getIntExtra()
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_result_view);
        initData();
        initView();
    }
    public void initView(){
        sportsExa = (TextView)findViewById(R.id.sports_example);
        sportsFun = (TextView)findViewById(R.id.sports_function);
        sportsPeo = (TextView)findViewById(R.id.who_need);
        heartImage = (ImageView)findViewById(R.id.heart_image);
        heartImage.setImageResource(image[type]);
        sportsExa.setText(sportsExamples.get(type));
        sportsFun.setText(functions.get(type));
        sportsPeo.setText(people.get(type));
    }
    public void initData(){
        sportsExamples.add("举重、短跑、拳击、格斗");
        sportsExamples.add("马拉松、长距离游泳、激烈球类运动,HIIT ");
        sportsExamples.add("慢跑，健身舞，高尔夫，较为缓和的球类运动");
        sportsExamples.add("太极，瑜伽 ");
        sportsExamples.add("慢走,买菜，走楼梯");
        functions.add("对提升爆发力,增大肌肉维度，等力量素质以及敏捷度有很大作用，增强神经系统");
        functions.add("极大程度增强了心肺功能和身体耐力，提高乳酸忍受度，对燃烧脂肪有很大作用 ");
        functions.add("对心肺及血管的保健功效，有助于体内毒素的排出，提升醒脑，保持身体机能的健康强健");
        functions.add("能够改善关节炎，高血压，调节身体内部状态平衡，减压放松 ");
        functions.add("促进身体的正常运行,放松身心");
        people.add("有长期中高强度运动的习惯，或是对爆发力、肌肉维度等有强烈提升意愿的人群，或是肌肉中快肌纤维粗壮且占比高的人");
        people.add("身体肌肉中红肌纤维占比多，心肺功能强壮的人，或是减肥意愿强烈的人群");
        people.add("学习工作压力较大，久坐身体肌肉僵硬，活动量较小的中青年人群");
        people.add("上了年纪的中老年人，或是长期处于高压高强度的工作环境下的人");
        people.add("年纪很大，身体关节器官老化，身体患有某些疾病，急需保健恢复的人群");
        image = new int[5];
        image[0] = R.drawable.red;
        image[1] = R.drawable.org;
        image[2] = R.drawable.yel;
        image[3] = R.drawable.gre;
        image[4] = R.drawable.gra;
    }
}
