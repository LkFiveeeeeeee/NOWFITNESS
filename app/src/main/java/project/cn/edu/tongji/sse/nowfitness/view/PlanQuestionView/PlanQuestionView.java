package project.cn.edu.tongji.sse.nowfitness.view.PlanQuestionView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.QuestionModel;

public class PlanQuestionView extends AppCompatActivity{
    private ViewPager viewPager;
    private ShadowTransFormer shadowFragmentTransFormer;
    private List<QuestionModel> questionModelList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_view);
        initView();
    }

    public void initView(){
        viewPager = (ViewPager) findViewById(R.id.question_viewpager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        questionModelList.add(new QuestionModel("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX","A","B","C","D"));
        questionModelList.add(new QuestionModel("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX","A","B","C","D"));
        questionModelList.add(new QuestionModel("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX","A","B","C","D"));
        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                if(position != questionModelList.size()){
                    return new QuestionFragment();
                }else{
                    return new QuestionFinishFragment();
                }
            }

            @Override
            public int getCount() {
                return questionModelList.size() + 1;
            }
        });
        viewPager.setPageTransformer(false,shadowFragmentTransFormer);
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

}



