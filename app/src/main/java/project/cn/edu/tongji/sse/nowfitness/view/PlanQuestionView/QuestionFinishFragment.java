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

import java.util.Objects;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.Constant;
import project.cn.edu.tongji.sse.nowfitness.model.QuestionList;
import project.cn.edu.tongji.sse.nowfitness.view.method.ConstantMethod;

public class QuestionFinishFragment extends BaseFragment {
    private Button finishButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_finish,container,false);
        this.questionView = view.findViewById(R.id.question_container);
        this.questionView.setMaxCardElevation(questionView.getCardElevation()
                * Constant.MAX_ELEVATION_FACTOR);
        initView(view);
        return view;
    }

    private void initView(View view){
        finishButton = view.findViewById(R.id.finish_button);
        finishButton.setEnabled(true);
        setListener();
    }

    private void setListener(){
        finishButton.setOnClickListener((View view) -> {
            if(QuestionList.get().calculateScore()){
                Intent intent = new Intent(getActivity(),QuestionResultView.class);
                //向结果activity传递结果value,并跳转
                intent.putExtra(Constant.RESULT_INTEGER,sortValue(QuestionList.get().getScore()));
                startActivity(intent);
                Objects.requireNonNull(getActivity()).finish();
            }else{
                ConstantMethod.toastShort(getContext(),"您有未进行选择的问题,请仔细查阅!");
            }
        });
    }

    private int sortValue(int score){
        if(score > Constant.A_RANK){
            double d = Math.random();
            int i = (int) (d*100);
            if(i % 2 == 0){
                i = 0;
            }else{
                i = 1;
            }
            return i;
        }else if(score > Constant.B_RANK){
            return 2;
        }else if(score > Constant.C_RANK){
            return 3;
        }else{
            return 4;
        }
    }


}
