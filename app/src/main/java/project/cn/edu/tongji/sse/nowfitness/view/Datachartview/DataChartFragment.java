package project.cn.edu.tongji.sse.nowfitness.view.Datachartview;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
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

import java.util.ArrayList;
import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.Constant;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.StepModel;
import project.cn.edu.tongji.sse.nowfitness.model.StepModelList;
import project.cn.edu.tongji.sse.nowfitness.presenter.DataChartPresenter;
import project.cn.edu.tongji.sse.nowfitness.view.method.ConstantMethod;

public class DataChartFragment
        extends Fragment implements DataChartMethod{
    public static final String DAY_NUMBER = "DAYNUMBER";


    private CombinedChart combinedChart;
    private DataChartPresenter dataChartPresenter;
    private LinearLayout dataColumn;
    private TextView stepText;
    private TextView stepEnergy;
    private TextView stepKm;
    private View noDataView;
    private View badNetworkView;
    private List<StepModel> stepModels = new ArrayList<>();
    private List<Entry> entries = new ArrayList<>();
    private List<BarEntry> barEntries = new ArrayList<>();
    private List<String> dateList = new ArrayList<>();


    static DataChartFragment newInstance(int count) {
        DataChartFragment dataChartFragment = new DataChartFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DAY_NUMBER, count);
        dataChartFragment.setArguments(bundle);
        return dataChartFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.datachart_item, container, false);
        dataChartPresenter = new DataChartPresenter(this);
        initView(view);

        return view;
    }

    public void initView(View view) {
        combinedChart = (CombinedChart) view.findViewById(R.id.data_linechart);
        dataColumn = (LinearLayout) view.findViewById(R.id.data_column);
        noDataView = view.findViewById(R.id.no_data);
        badNetworkView = view.findViewById(R.id.bad_network);
        TextView warningWord = (TextView) noDataView.findViewById(R.id.warning_word);
        stepEnergy = (TextView) view.findViewById(R.id.step_energy);
        stepKm = (TextView) view.findViewById(R.id.step_km);
        stepText = (TextView) view.findViewById(R.id.step_text);
        warningWord.setText("暂无数据");
        getStepCount();
    }

    private void getStepCount() {
        Bundle bundle = getArguments();
        assert bundle != null;
        int dayCount = bundle.getInt(DAY_NUMBER);
        dataChartPresenter.getStepsData(dayCount);
    }

    private void setWarningViewGone(){
        noDataView.setVisibility(View.GONE);
    }

    private void setChartViewGone(){
        combinedChart.setVisibility(View.GONE);
        dataColumn.setVisibility(View.GONE);
    }

    private void setNetworkViewGone(){
        badNetworkView.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    private void setDataColumn(){
        int countStep = 0;
        int calories = 0;
        for(StepModel stepModel:stepModels){
            countStep += Integer.valueOf(stepModel.getStep());
            calories += stepModel.getCalories();
        }
        int km = (int) (countStep * Constant.AVA_STEP / Constant.ONE_THOUSAND);
        stepText.setText(countStep + "步");
        stepEnergy.setText(calories + "KCal");
        stepKm.setText(km + "Km");
    }


    private void initChart()  {

        for(int i = 0;i < stepModels.size();i++){
            entries.add(new Entry((float) i, Float.valueOf(stepModels.get(i).getStep())));
            barEntries.add(new BarEntry(i,Float.valueOf(stepModels.get(i).getStep())));
            dateList.add(stepModels.get(i).getToday());
        }
        Legend legend = combinedChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        setXAxisData();

        showDataOnChart();

        combinedChart.invalidate();

    }
    private void showDataOnChart(){
        CombinedData combinedData = new CombinedData();


        combinedData.setData(getLineData(entries));
        combinedData.setData(getBarData(barEntries));
        combinedChart.setData(combinedData);
        final int visibleSize = 5;
        if(combinedData.getEntryCount() > visibleSize){
            combinedChart.setVisibleXRangeMaximum(visibleSize);
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
        combinedChart.getAxisLeft().setAxisMinimum(0);
        combinedChart.getAxisRight().setAxisMinimum(0);
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


    private void setXAxisData(){
        combinedChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Log.d("temp1111", "getFormattedValue: " + value);
                return (strToDate(dateList.get((int) value)) + "日");
            }
        });
    }

    private int strToDate(String dataStr) {

        int index = dataStr.lastIndexOf('-');
        Log.d("坐标转换", "strToDate: " + dataStr);

        dataStr = dataStr.substring(index + 1,index + 3);
        Log.d("坐标转换", "strToDate: " + dataStr);
        return Integer.valueOf(dataStr);
    }

    @Override
    public void querySuccess(ResponseModel<StepModelList> stepModelListResponseModel) {
        if(stepModelListResponseModel.getStatus() >= 200 && stepModelListResponseModel.getStatus()< 300){
            if(stepModelListResponseModel.getData().getDays() < 3){
                setChartViewGone();
                setNetworkViewGone();
            }else{
                stepModels = stepModelListResponseModel.getData().getStepModels();
                setNetworkViewGone();
                setWarningViewGone();
                setDataColumn();
                initChart();
            }
        }else{
            ConstantMethod.toastShort(getContext(),stepModelListResponseModel.getError());
            setChartViewGone();
            setNetworkViewGone();
            setWarningViewGone();
        }
    }

    @Override
    public void queryError(Throwable e) {
        setChartViewGone();
        setWarningViewGone();
        e.printStackTrace();
        Log.d("DataChartFragment", "queryError: !");
    }

    @Override
    public void onDestroyView() {
        dataChartPresenter.onViewDestroyed();
        super.onDestroyView();
    }
}