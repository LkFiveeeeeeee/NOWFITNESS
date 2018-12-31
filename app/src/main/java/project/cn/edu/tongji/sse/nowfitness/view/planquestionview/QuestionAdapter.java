package project.cn.edu.tongji.sse.nowfitness.view.planquestionview;

import android.support.v7.widget.CardView;

public interface QuestionAdapter {
    int MAX_ELEVATION_FACTOR = 8;
    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
