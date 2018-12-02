package project.cn.edu.tongji.sse.nowfitness.view.PlanQuestionView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.QuestionModel;

public class QuestionFragment extends Fragment {
    private CardView questionView;
    private TextView questionText;
    private Button chooseA;
    private Button chooseB;
    private Button chooseC;
    private Button chooseD;
    private QuestionModel questionModel = new QuestionModel("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX","A","B","C","D");


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_item,container,false);
        questionView = (CardView) view.findViewById(R.id.question_container);
        questionView.setMaxCardElevation(questionView.getCardElevation()
            * QuestionAdapter.MAX_ELEVATION_FACTOR);
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
    }
    public void bindView(QuestionModel questionModel){
        questionText.setText(questionModel.getQuestionText());

        chooseA.setText(questionModel.getChooseAText());
        chooseB.setText(questionModel.getChooseBText());
        chooseC.setText(questionModel.getChooseCText());
        chooseD.setText(questionModel.getChooseDText());
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
    public CardView getQuestionView(){
        return questionView;
    }
}
