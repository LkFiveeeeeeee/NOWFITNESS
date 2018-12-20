package project.cn.edu.tongji.sse.nowfitness.view.DataChartView;


import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.StepModel;
import project.cn.edu.tongji.sse.nowfitness.view.DataChartView.StepArcView.StepArcView;

public class DataChartFragment extends Fragment {
    public final static String DAY_NUMBER = "DAYNUMBER";



    /**CommbindChart*/
    private int dayCount;
    private CombinedChart combinedChart;
    private CombinedData combinedData;
    private List<StepModel> stepModels = new ArrayList<>();
    private List<Entry> entries = new ArrayList<Entry>();
    private List<BarEntry> barEntries = new ArrayList<>();


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

        combinedChart = (CombinedChart) view.findViewById(R.id.data_linechart);

        getStepCount();
        initFakeData();
    }

    private void getStepCount() {
        Bundle bundle = getArguments();
        dayCount = bundle.getInt(DAY_NUMBER);
        setViewGone();
    }

    private void setViewGone() {
        /*if (dayCount != 1) {
            stepContainer.setVisibility(View.GONE);
        }*/
    }

    private void initFakeData()  {
        for(int i = 1; i <= 30;i++){
            StepModel stepModel = new StepModel();
            stepModel.setToday("2018-"+"11-" + i);
            stepModel.setStep(i*100+"");
            stepModels.add(stepModel);
        }
        initChart();

        //TODO

    }

    private void initChart()  {

        for(StepModel model:stepModels){
            entries.add(new Entry((float) strToDate(model.getToday()),Float.valueOf(model.getStep())));
            barEntries.add(new BarEntry((int) strToDate(model.getToday()),Float.valueOf(model.getStep())));
        }


        Legend legend = combinedChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        setXdata();

        showDataOnChart();

        combinedChart.invalidate();

    }
    private void showDataOnChart(){
        combinedData = new CombinedData();


        combinedData.setData(getLineData(entries));
        combinedData.setData(getBarData(barEntries));
        combinedChart.setData(combinedData);

        if(combinedData.getEntryCount() > 5){
            combinedChart.setVisibleXRangeMaximum(5);
        }else{
            combinedChart.fitScreen();
        }
        combinedChart.getDescription().setEnabled(false);
        combinedChart.setDrawGridBackground(false);
        combinedChart.setHighlightPerTapEnabled(true);
        combinedChart.getXAxis().setDrawGridLines(false);
        combinedChart.getXAxis().setDrawAxisLine(false);
        combinedChart.getXAxis().setGranularity(2f);
        combinedChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        combinedChart.getAxisLeft().setDrawGridLines(false);
        combinedChart.getAxisRight().setDrawGridLines(false);
        combinedChart.animateX(2000);
        combinedChart.setExtraLeftOffset(10);
        combinedChart.setExtraRightOffset(10);
        combinedChart.getXAxis().setGranularity(1f);

        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setAxisMaximum(combinedData.getXMax() + 0.5f);
        xAxis.setAxisMinimum(combinedData.getXMin() - 0.5f);

    }

    private LineData getLineData(List<Entry> entries){
        LineData lineData = new LineData();
        LineDataSet lineDataSet = new LineDataSet(entries,"每日步数");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        lineDataSet.setColor(Color.parseColor("#66A3AB"));
        lineDataSet.setCircleColor(Color.parseColor("#66A3AB"));
        lineDataSet.setCircleRadius(5);
        lineDataSet.setLineWidth(3);
        lineDataSet.setValueTextSize(12);
        lineDataSet.setValueTextColor(R.color.colorSecondary);
        lineData.addDataSet(lineDataSet);
        return lineData;
    }

    private BarData getBarData(List<BarEntry> entries){
        BarData barData = new BarData();
        BarDataSet barDataSet = new BarDataSet(entries,"每日步数");
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet.setDrawValues(false);
        barDataSet.setColor(Color.parseColor("#90CAF9"));
        barDataSet.setHighlightEnabled(true);
        barDataSet.setValueTextSize(10);
        barData.addDataSet(barDataSet);

        float barWidth = 0.45f;
        barData.setBarWidth(barWidth);
        return barData;

    }


    private void setXdata(){
        combinedChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return ((int) value) + "日";
            }
        });
    }

    private int strToDate(String dataStr) {

        int index = dataStr.lastIndexOf('-');
        Log.d("坐标转换", "strToDate: " + dataStr);

        dataStr = dataStr.substring(index + 1);
        Log.d("坐标转换", "strToDate: " + dataStr);
        return Integer.valueOf(dataStr);
    }

}