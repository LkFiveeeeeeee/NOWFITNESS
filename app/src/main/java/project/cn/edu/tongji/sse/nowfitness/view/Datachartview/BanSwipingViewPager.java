package project.cn.edu.tongji.sse.nowfitness.view.Datachartview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class BanSwipingViewPager extends ViewPager {

    private boolean isSwiping = false;

    public BanSwipingViewPager(@NonNull Context context) {
        super(context);
    }

    public BanSwipingViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isSwiping;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isSwiping;
    }
}
