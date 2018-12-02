package project.cn.edu.tongji.sse.nowfitness.view.PlanQuestionView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.model.QuestionModel;

public class QuestionFragmentPagerAdapter extends FragmentPagerAdapter implements QuestionAdapter {
    private List<QuestionFragment> fragmentList;
    private float baseElevation;
    public QuestionFragmentPagerAdapter(FragmentManager fm,List<QuestionModel> questionModels,float baseElevation){
        super(fm);
        fragmentList = new ArrayList<>();
        this.baseElevation = baseElevation;
    }

    @Override
    public float getBaseElevation() {
        return baseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return fragmentList.get(position).getQuestionView();
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

   /* @Override
    public Object instantiateItem(ViewGroup container, int position) {
        QuestionFragment fragment = (QuestionFragment) super.instantiateItem(container,position);
        fragmentList.set(position,fragment);
        fragment.bindView(questionModelList.get(position));
        return fragment;
    }*/

    public void addQuestionFragment(QuestionFragment fragment){
        fragmentList.add(fragment);
    }
}
