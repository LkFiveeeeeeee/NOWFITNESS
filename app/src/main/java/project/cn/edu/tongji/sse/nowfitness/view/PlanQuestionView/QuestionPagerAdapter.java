package project.cn.edu.tongji.sse.nowfitness.view.PlanQuestionView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.QuestionModel;

public class QuestionPagerAdapter extends PagerAdapter implements QuestionAdapter{
    private List<CardView> questionViews;
    private List<QuestionModel> questionModelList;
    private float baseElevation;
    Button chooseA;
    Button chooseB;
    Button chooseC;
    Button chooseD;


    public QuestionPagerAdapter(){
        questionViews = new ArrayList<>();
        questionModelList = new ArrayList<>();
    }

    public void addQuestionItem(QuestionModel questionModel){
        questionViews.add(null);
        questionModelList.add(questionModel);
    }

    @Override
    public float getBaseElevation() {
        return baseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return questionViews.get(position);
    }

    @Override
    public int getCount() {
        return questionModelList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.question_item,container,false);
        container.addView(view);
        bind(questionModelList.get(position),view);
        CardView questionView = (CardView) view.findViewById(R.id.question_container);
        if(baseElevation == 0){
            baseElevation = questionView.getCardElevation();
        }

        questionView.setMaxCardElevation(baseElevation * MAX_ELEVATION_FACTOR);
        questionViews.set(position,questionView);
        return view;
    }

    private void bind(QuestionModel item,View view){
        view.getParent().requestDisallowInterceptTouchEvent(true);
        TextView questText = (TextView) view.findViewById(R.id.question_text);
        chooseA = (Button) view.findViewById(R.id.choose_A);
        chooseB = (Button) view.findViewById(R.id.choose_B);
        chooseC = (Button) view.findViewById(R.id.choose_C);
        chooseD = (Button) view.findViewById(R.id.choose_D);
        questText.setText(item.getQuestionText());
        chooseA.setText(item.getChooseAText());
        chooseB.setText(item.getChooseBText());
        chooseC.setText(item.getChooseCText());
        chooseD.setText(item.getChooseDText());
        chooseA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetColor(chooseA,chooseB,chooseC,chooseD);
            }
        });
        chooseB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetColor(chooseB,chooseA,chooseC,chooseD);
            }
        });
        chooseC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetColor(chooseC,chooseA,chooseB,chooseD);
            }
        });
        chooseD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetColor(chooseD,chooseA,chooseB,chooseC);
            }
        });
    }



    private void resetColor(Button buttonA,Button buttonB,
                            Button buttonC,Button buttonD){
        buttonA.setEnabled(false);
        buttonB.setEnabled(true);
        buttonC.setEnabled(true);
        buttonD.setEnabled(true);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        questionViews.set(position,null);
    }

}
