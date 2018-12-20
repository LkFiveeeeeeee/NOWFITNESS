package project.cn.edu.tongji.sse.nowfitness.view.PlanQuestionView;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.presenter.BasePresenter;

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
        finishButton.setEnabled(false);
    }
}
