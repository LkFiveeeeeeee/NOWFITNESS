package project.cn.edu.tongji.sse.nowfitness.view.PlanQuestionView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.Question;
import project.cn.edu.tongji.sse.nowfitness.model.QuestionList;
import project.cn.edu.tongji.sse.nowfitness.model.QuestionModel;

public class QuestionFragment extends BaseFragment {
    private TextView questionText;
    private Button chooseA;
    private Button chooseB;
    private Button chooseC;
    private Button chooseD;
    private Button chooseE;
    private Question questionModel;
    private int quesIndex;

    static QuestionFragment newInstance(int index){
        QuestionFragment questionFragment = new QuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("INDEX",index);
        questionFragment.setArguments(bundle);
        return questionFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_item,container,false);
        questionView = (CardView) view.findViewById(R.id.question_container);
        questionView.setMaxCardElevation(questionView.getCardElevation()
            * QuestionAdapter.MAX_ELEVATION_FACTOR);
        Bundle bundle = getArguments();
        quesIndex = bundle.getInt("INDEX");
        questionModel = QuestionList.get().getQuestions().get(quesIndex);
        initView(view);
        bindView(questionModel);
        return view;
    }
    private void initView(View view){
        questionText = (TextView) view.findViewById(R.id.question_text);
        chooseA = (Button) view.findViewById(R.id.choose_A);
        chooseB = (Button) view.findViewById(R.id.choose_B);
        chooseC = (Button) view.findViewById(R.id.choose_C);
        chooseD = (Button) view.findViewById(R.id.choose_D);
        chooseE = (Button) view.findViewById(R.id.choose_E);
    }
    public void bindView(Question questionModel){
        int size = questionModel.getBonusSize();

        questionText.setText(questionModel.getTitle());

        chooseA.setText(questionModel.getOptions()[0]);
        chooseB.setText(questionModel.getOptions()[1]);
        chooseC.setText(questionModel.getOptions()[2]);
        chooseD.setText(questionModel.getOptions()[3]);
        if(size == 4){
            chooseE.setVisibility(View.GONE);
        }else{
            chooseE.setText(questionModel.getOptions()[4]);
        }
        chooseA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetColor(size,chooseA,chooseB,chooseC,chooseD,chooseE);
            }
        });
        chooseB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetColor(size,chooseB,chooseA,chooseC,chooseD,chooseE);
            }
        });
        chooseC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetColor(size,chooseC,chooseA,chooseB,chooseD,chooseE);
            }
        });
        chooseD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetColor(size,chooseD,chooseA,chooseB,chooseC,chooseE);
            }
        });
        chooseE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetColor(size,chooseE,chooseA,chooseB,chooseC,chooseD);
            }
        });
    }

    private void resetColor(int size,Button buttonA,Button buttonB,
                            Button buttonC,Button buttonD,Button buttonE){
        buttonA.setEnabled(false);
        buttonB.setEnabled(true);
        buttonC.setEnabled(true);
        buttonD.setEnabled(true);
        if(size == 5){
            buttonE.setEnabled(true);
        }
        setChooseState();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void setChooseState(){
        List<Button> buttons = new ArrayList<>();
        buttons.add(chooseA);
        buttons.add(chooseB);
        buttons.add(chooseC);
        buttons.add(chooseD);
        if(QuestionList.get().getQuestions().get(quesIndex).getOptionsSize() == 5){
            buttons.add(chooseE);
        }
        for(int i = 0; i < buttons.size();i++){
            if(!buttons.get(i).isEnabled()){
                Log.d("QSFragment", "onResume: " + i + " button!!!!!");
                QuestionList.get().getBonusIndex()[quesIndex] = i;
                break;
            }
        }
    }

    public CardView getQuestionView(){
        return questionView;
    }
}
