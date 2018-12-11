package project.cn.edu.tongji.sse.nowfitness.view.PlanQuestionView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.Question;
import project.cn.edu.tongji.sse.nowfitness.model.QuestionList;
import project.cn.edu.tongji.sse.nowfitness.model.QuestionModel;

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
    /*    viewPager.setPageTransformer(false,(ViewPager.PageTransformer) (page, position) -> {

            int pageWidth = page.getWidth();
            int pageHeight = page.getHeight();
            float MIN_SCALE = 0.9f;
            float MIN_ALPHA = 0.5f;

            if (position < -1) {
                page.setAlpha(0);
            } else if (position <= 1) // a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
            {
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    page.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    page.setTranslationX(-horzMargin + vertMargin / 2);
                }

                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);

                page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                        / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else {
                page.setAlpha(0);
            }

        });*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }




}



