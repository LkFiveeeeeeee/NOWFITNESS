package project.cn.edu.tongji.sse.nowfitness.view.PlanQuestionView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import java.util.ArrayList;
import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.model.Question;


public class QuestionFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<Question> questionModelList;
    private List<BaseFragment> fragments = new ArrayList<>();
    QuestionFragmentPagerAdapter(FragmentManager fragmentManager, List<Question> questionModels){
        super(fragmentManager);
        questionModelList = questionModels;
        for(int i = 0;i < questionModelList.size();i++){
            QuestionFragment questionFragment = QuestionFragment.newInstance(i);
            fragments.add(questionFragment);
        }
        fragments.add(new QuestionFinishFragment());
    }




    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }




    @Override
    public int getCount() {
        return questionModelList.size()+1;
    }
}
