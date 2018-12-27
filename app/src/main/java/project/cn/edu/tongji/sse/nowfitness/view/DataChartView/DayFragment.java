package project.cn.edu.tongji.sse.nowfitness.view.DataChartView;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.StepLab;
import project.cn.edu.tongji.sse.nowfitness.model.StepModel;
import project.cn.edu.tongji.sse.nowfitness.model.StepModelList;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.pedometerModule.StepService.StepService;
import project.cn.edu.tongji.sse.nowfitness.pedometerModule.StepService.UpdateUICallBack;
import project.cn.edu.tongji.sse.nowfitness.presenter.DataChartPresenter;
import project.cn.edu.tongji.sse.nowfitness.view.DataChartView.StepArcView.StepArcView;
import project.cn.edu.tongji.sse.nowfitness.view.method.ConstantMethod;

public class DayFragment extends Fragment implements DataChartMethod {
    /**CardView For Day*/
    private View dayView;
    private CardView stepContainer;
    private TextView stepView;
    private int dayCount;
    private StepArcView stepChart;
    private boolean isBind = false;
    private ServiceConnection serviceConnection;
    private DataChartPresenter dataChartPresenter = new DataChartPresenter(this);
    private TextView stepText;
    private TextView stepKm;
    private TextView stepEnergy;
    private TextView relativeStep;
    private ImageView trendImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dayView  = inflater.inflate(R.layout.daychart_item,container,false);
        dataChartPresenter.getStepsData(1);

        return dayView;
    }

    public void initView(View view){
        stepContainer = (CardView) view.findViewById(R.id.step_cardview);
        stepChart = (StepArcView) view.findViewById(R.id.step_chart);
        stepView = (TextView) view.findViewById(R.id.step_text);
        stepText = (TextView) view.findViewById(R.id.step_text);
        stepKm = (TextView) view.findViewById(R.id.step_km);
        stepEnergy = (TextView) view.findViewById(R.id.step_energy);
        relativeStep = (TextView) view.findViewById(R.id.relative_step);
        trendImage = (ImageView) view.findViewById(R.id.trend_image);
        setServiceConnection();
    }



    public void setServiceConnection(){
        Intent intent = new Intent(getContext(),StepService.class);
        serviceConnection = new ServiceConnection() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                StepService stepService = ((StepService.StepBinder) iBinder).getService();
                stepChart.setCurrentCount(7000,stepService.getStepCount());
                StepModel stepModel = StepLab.get().getStepModel();
                UserInfoModel userInfoModel = UserInfoLab.get().getUserInfoModel();
                stepText.setText(stepModel.getStep());
                stepKm.setText((int) (Double.valueOf(stepModel.getStep()) * 0.75) + "m");
                stepEnergy.setText(String.valueOf((int) ConstantMethod.countCalories(
                        Integer.valueOf(stepModel.getStep()),userInfoModel.getWeight())) + "Kcal");
                setRelativeStep(stepModel,Integer.valueOf(stepModel.getStep()));
                stepService.registerCallBack(new UpdateUICallBack() {
                    @Override
                    public void updateUI(int stepCounts) {
                        stepChart.setCurrentCount(7000,stepCounts);
                        stepText.setText(String.valueOf(stepCounts));
                        stepKm.setText((int) (stepCounts * 0.75) + "m");
                        stepEnergy.setText(String.valueOf((int) ConstantMethod.countCalories(
                                stepCounts,userInfoModel.getWeight())) + "Kcal");
                        setRelativeStep(stepModel,stepCounts);
                    }
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };

        isBind = Objects.requireNonNull(getActivity()).bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            getActivity().startForegroundService(intent);
        }else{
            getActivity().startService(intent);
        }
    }

    public void setRelativeStep(StepModel step,int stepCount){
        if(stepCount - step.getYesterdayStep() >= 0){
            relativeStep.setTextColor(getResources().getColor(R.color.red));
            trendImage.setImageResource(R.drawable.arrowup);
        }else{
            trendImage.setImageResource(R.drawable.arrowdown);
            relativeStep.setTextColor(getResources().getColor(R.color.colorSecondary));
        }
        relativeStep.setText(String.valueOf(
                stepCount - step.getYesterdayStep()
        ));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(isBind){
            Objects.requireNonNull(this.getActivity()).unbindService(serviceConnection);
        }
    }

    @Override
    public void querySuccess(ResponseModel<StepModelList> stepModelListResponseModel) {
        if(stepModelListResponseModel.getStatus() >= 200 && stepModelListResponseModel.getStatus() < 300){
            StepLab.get().getStepModel().setYesterdayStep(Integer.
                    valueOf(stepModelListResponseModel.getData().getStepModels().get(0).getStep()));
        }else{
            StepLab.get().getStepModel().setYesterdayStep(0);
        }
        initView(dayView);
    }

    @Override
    public void queryError(Throwable e) {
        StepLab.get().getStepModel().setYesterdayStep(0);
        e.printStackTrace();
    }
}
