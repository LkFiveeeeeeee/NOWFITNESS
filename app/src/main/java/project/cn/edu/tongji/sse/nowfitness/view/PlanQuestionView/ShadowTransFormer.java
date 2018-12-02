package project.cn.edu.tongji.sse.nowfitness.view.PlanQuestionView;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;

public class ShadowTransFormer implements ViewPager.OnPageChangeListener,ViewPager.PageTransformer {
    private ViewPager questionViewPager;
    private QuestionAdapter questionAdapter;
    private float lastOffset;
    private boolean scalingEnabled;

    public ShadowTransFormer(ViewPager viewPager,QuestionAdapter adapter){
        questionViewPager = viewPager;
        questionAdapter = adapter;
        questionViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int realCurrentPosition;
        int nextPosition;
        float baseElevation = questionAdapter.getBaseElevation();
        float realOffset;
        boolean goingLeft = lastOffset > positionOffset;

        if(goingLeft){
            realCurrentPosition = position + 1;
            nextPosition = position;
            realOffset = 1 - positionOffset;
        }else{
            nextPosition = position+1;
            realCurrentPosition = position;
            realOffset = positionOffset;
        }

        if(nextPosition > questionAdapter.getCount() - 1
                ||realCurrentPosition >questionAdapter.getCount() - 1
                ){
            return;
        }

        CardView currentView = questionAdapter.getCardViewAt(realCurrentPosition);
        if(currentView != null){
            if(scalingEnabled){
                currentView.setScaleX((float) (1+0.1*(1-realOffset)));
                currentView.setScaleY((float) (1+0.1*(1-realOffset)));
            }
            currentView.setCardElevation((baseElevation +
                    baseElevation * (QuestionAdapter.MAX_ELEVATION_FACTOR - 1) * (1 - realOffset)));
        }

        CardView nextView = questionAdapter.getCardViewAt(nextPosition);
        if(nextView != null){
            if(scalingEnabled){
                nextView.setScaleX((float) (1+0.1*(realOffset)));
                nextView.setScaleY((float) (1+0.1*(realOffset)));
            }
            nextView.setCardElevation((baseElevation +
                    baseElevation * (QuestionAdapter.MAX_ELEVATION_FACTOR - 1) * (realOffset)));
        }
        lastOffset = positionOffset;
    }

    public void enableScaling(boolean enable){
        if(scalingEnabled && !enable){
            CardView currentView = questionAdapter.getCardViewAt(questionViewPager.getCurrentItem());
            if(currentView != null){
                currentView.animate().scaleX(1.1f);
                currentView.animate().scaleY(1.1f);
            }
        }else if(!scalingEnabled && enable){
            CardView currentView = questionAdapter.getCardViewAt(questionViewPager.getCurrentItem());
            if(currentView != null){
                currentView.animate().scaleX(1.1f);
                currentView.animate().scaleY(1.1f);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void transformPage(@NonNull View page, float position) {

    }
}
