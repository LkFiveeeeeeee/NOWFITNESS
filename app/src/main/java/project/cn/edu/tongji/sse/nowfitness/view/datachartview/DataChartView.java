package project.cn.edu.tongji.sse.nowfitness.view.datachartview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import project.cn.edu.tongji.sse.nowfitness.R;

public class DataChartView extends AppCompatActivity {
    ImageView backIcon;
    SegmentTabLayout switchLayout;
    private String[] titles = {"日","周","月"};
    private DataChartAdapter dataChartAdapter;
    private BanSwipingViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datachart_view);
        dataChartAdapter = new DataChartAdapter(getSupportFragmentManager());
        initView();
    }

    public void initView(){
        backIcon = (ImageView) findViewById(R.id.back_icon);
        switchLayout = findViewById(R.id.switch_bar);
        switchLayout.setTabData(titles);
        viewPager = findViewById(R.id.chart_pager);
        viewPager.setAdapter(dataChartAdapter);

        setListener();
    }

    private void setListener(){
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
