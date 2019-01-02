package project.cn.edu.tongji.sse.nowfitness.view.PlanQuestionView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.Question;
import project.cn.edu.tongji.sse.nowfitness.model.QuestionList;

public class PlanQuestionView extends AppCompatActivity{
    private ViewPager viewPager;
    private Toolbar toolbar;
    private QuestionFragmentPagerAdapter questionFragmentPagerAdapter;
    private List<Question> questionModelList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_view);
        initView();
    }

    public void initView(){
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        viewPager = (ViewPager) findViewById(R.id.question_viewpager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        questionModelList = QuestionList.get().getQuestions();
        questionFragmentPagerAdapter = new QuestionFragmentPagerAdapter(fragmentManager,questionModelList);
        viewPager.setAdapter(questionFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(questionFragmentPagerAdapter.getCount());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }




}



