package project.cn.edu.tongji.sse.nowfitness.view.Datachartview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import project.cn.edu.tongji.sse.nowfitness.R;

/**
 * Created by LK on 2018/12/11.
 */

public class DataChartView extends AppCompatActivity {
    private ImageView backIcon;
    private SegmentTabLayout switchLayout;
    private final String[] titles = {"日","周","月"};
    private DataChartAdapter dataChartAdapter;
    private BanSwipingViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datachart_view);
        dataChartAdapter = new DataChartAdapter(getSupportFragmentManager());
        initView();
    }

    private void initView(){
        backIcon =  findViewById(R.id.back_icon);
        switchLayout = findViewById(R.id.switch_bar);
        switchLayout.setTabData(titles);
        viewPager = findViewById(R.id.chart_pager);
        viewPager.setAdapter(dataChartAdapter);

        setListener();
    }

    private void setListener(){
        backIcon.setOnClickListener((View view) -> finish());
        switchLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                //DO Nothing
            }
        });
        viewPager.setCurrentItem(0);
    }
}
