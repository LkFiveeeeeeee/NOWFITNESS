package project.cn.edu.tongji.sse.nowfitness.view.DataChartView;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.StepModel;

public class DataChartFragment extends Fragment {
    public final static String DAY_NUMBER = "DAYNUMBER";
    private CardView stepContainer;
    private TextView stepView;
    private int dayCount;
    private LineChart lineChart;
    private List<StepModel> stepModels = new ArrayList<>();
    private List<Entry> entries = new ArrayList<Entry>();
    static DataChartFragment newInstance(int count) {
        DataChartFragment dataChartFragment = new DataChartFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DAY_NUMBER, count);
        dataChartFragment.setArguments(bundle);
        return dataChartFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.datachart_item, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {
        stepContainer = (CardView) view.findViewById(R.id.step_cardview);
        stepView = (TextView) view.findViewById(R.id.step_text);
        lineChart = (LineChart) view.findViewById(R.id.data_linechart);
        initFakeData();
        getStepCount();
        initFakeData();
    }

    private void getStepCount() {
        Bundle bundle = getArguments();
        dayCount = bundle.getInt(DAY_NUMBER);
        setViewGone();
    }

    private void setViewGone() {
        if (dayCount != 1) {
            stepContainer.setVisibility(View.GONE);
        }
    }

    private void initFakeData(){
        for(int i = 1; i <= 7;i++){
            StepModel stepModel = new StepModel();
            stepModel.setToday(i+"");
            stepModel.setStep(i*100+"");
            stepModels.add(stepModel);
        }
        initChart();
    }

    private void initChart(){
        for(StepModel model:stepModels){
            entries.add(new Entry(Float.valueOf(model.getToday()),Float.valueOf(model.getStep())));
        }
        LineDataSet dataSet = new LineDataSet(entries,"步数");
        dataSet.setColor(R.color.colorAccent);
        dataSet.setValueTextColor(R.color.textColor);
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }
}