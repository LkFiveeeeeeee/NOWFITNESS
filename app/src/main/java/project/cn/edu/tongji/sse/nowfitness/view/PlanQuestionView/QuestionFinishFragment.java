package project.cn.edu.tongji.sse.nowfitness.view.PlanQuestionView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.Constant;
import project.cn.edu.tongji.sse.nowfitness.model.QuestionList;
import project.cn.edu.tongji.sse.nowfitness.view.method.ConstantMethod;

public class QuestionFinishFragment extends BaseFragment {
    private TextView questionText;
    private Button finishButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_finish,container,false);
        this.questionView = (CardView) view.findViewById(R.id.question_container);
        this.questionView.setMaxCardElevation(questionView.getCardElevation()
                * QuestionAdapter.MAX_ELEVATION_FACTOR);
        initView(view);
        return view;
    }

    private void initView(View view){
        questionText = view.findViewById(R.id.question_text);
        finishButton = view.findViewById(R.id.finish_button);
        finishButton.setEnabled(true);
        setListener();
    }

    private void setListener(){
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(QuestionList.get().calculateScore()){
               //     ConstantMethod.toastShort(getContext(),"success,请仔细查阅!" +" " + QuestionList.get().getScore());
                    Intent intent = new Intent(getActivity(),QuestionResultView.class);
                    intent.putExtra(ConstantMethod.result_Integer,sortValue(QuestionList.get().getScore()));
                    startActivity(intent);
                    Objects.requireNonNull(getActivity()).finish();
                }else{
                    ConstantMethod.toastShort(getContext(),"您有未进行选择的问题,请仔细查阅!");
                }
            }
        });
    }

    private int sortValue(int score){
        if(score > 83){
            double d = Math.random();
            int i = (int) (d*100);
            return i % 2 == 0 ? 0 : 1;
        }else if(score > 65){
            return 2;
        }else if(score > 49){
            return 3;
        }else{
            return 4;
        }
    }


}
