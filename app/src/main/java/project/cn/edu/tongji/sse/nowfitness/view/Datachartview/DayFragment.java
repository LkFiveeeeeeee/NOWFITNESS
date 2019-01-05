package project.cn.edu.tongji.sse.nowfitness.view.Datachartview;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.Constant;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.StepLab;
import project.cn.edu.tongji.sse.nowfitness.model.StepModelList;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.pedometermodule.stepservice.StepService;
import project.cn.edu.tongji.sse.nowfitness.presenter.DataChartPresenter;
import project.cn.edu.tongji.sse.nowfitness.view.Datachartview.StepArcView.StepArcView;
import project.cn.edu.tongji.sse.nowfitness.view.method.ConstantMethod;

public class DayFragment extends Fragment implements DataChartMethod {
    /**CardView For Day*/
    private View dayView;
    private int yesterdayCount = 0;
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dayView  = inflater.inflate(R.layout.daychart_item,container,false);
        dataChartPresenter.getStepsData(1);
        initView(dayView);
        return dayView;
    }

    private void initView(View view){
        stepChart = view.findViewById(R.id.step_chart);
        stepText = view.findViewById(R.id.step_text);
        stepKm = view.findViewById(R.id.step_km);
        stepEnergy = view.findViewById(R.id.step_energy);
        relativeStep = view.findViewById(R.id.relative_step);
        trendImage = view.findViewById(R.id.trend_image);

    }

    @Override
    public void onStart() {
        super.onStart();
        setServiceConnection();
    }

    // 连接计步Service
    public void setServiceConnection(){
        Intent intent = new Intent(getContext(),StepService.class);
        initView(dayView);
        serviceConnection = new ServiceConnection() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                //绑定,并初始化视图
                StepService stepService = ((StepService.StepBinder) iBinder).getService();
                stepChart.setCurrentCount(7000,stepService.getStepCount());
                UserInfoModel userInfoModel = UserInfoLab.get().getUserInfoModel();
                stepText.setText(String.valueOf(stepService.getStepCount()));
                stepKm.setText((int) ((double) stepService.getStepCount() * 0.75) + "m");
                stepEnergy.setText(String.valueOf((int) ConstantMethod.countCalories(
                        stepService.getStepCount(),userInfoModel.getWeight())) + "Kcal");
                //重写回调接口,当计步数据变化时,此View同样动态变化
                stepService.registerCallBack((int stepCounts) -> {
                    stepChart.setCurrentCount(Constant.MAX_STEPS,stepCounts);
                    stepText.setText(String.valueOf(stepCounts));
                    stepKm.setText((int) (stepCounts * Constant.AVA_STEP) + "m");
                    stepEnergy.setText(String.valueOf((int) ConstantMethod.countCalories(
                            stepCounts,userInfoModel.getWeight())) + "Kcal");
                    setRelativeStep(stepCounts);
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                //DO NOTHING
            }
        };

        isBind = Objects.requireNonNull(getActivity()).bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            getActivity().startForegroundService(intent);
        }else{
            getActivity().startService(intent);
        }
    }

    //计算与昨天相比较的相对步数
    public void setRelativeStep(int stepCount){
        if(stepCount - yesterdayCount >= 0){
            if(isAdded()){
                relativeStep.setTextColor(getResources().getColor(R.color.red));
            }
            trendImage.setImageResource(R.drawable.arrowup);
        }else{
            trendImage.setImageResource(R.drawable.arrowdown);
            if(isAdded()){
                relativeStep.setTextColor(getResources().getColor(R.color.colorSecondary));
            }

        }
        relativeStep.setText(String.valueOf(
                stepCount - yesterdayCount
        ));
    }

    //当该Fragment前台不可见时,解绑service
    @Override
    public void onStop() {
        super.onStop();
        if(isBind){
            Objects.requireNonNull(this.getActivity()).unbindService(serviceConnection);
        }
    }


    @Override
    public void querySuccess(ResponseModel<StepModelList> stepModelListResponseModel) {
        if(stepModelListResponseModel.getStatus() >= 200 && stepModelListResponseModel.getStatus() < 300
                && stepModelListResponseModel.getData().getDays() != 0){
            yesterdayCount = Integer.
                    valueOf(stepModelListResponseModel.getData().getStepModels().get(0).getStep());
        }else{
            yesterdayCount = 0;
        }

        setRelativeStep(Integer.valueOf(StepLab.get().getStepModel().getStep()));
    }

    @Override
    public void queryError(Throwable e) {
        yesterdayCount = 0;
        setRelativeStep(Integer.valueOf(StepLab.get().getStepModel().getStep()));
        e.printStackTrace();
        Log.d("DayFragment", "queryError: ");
        ConstantMethod.toastShort(getContext(),"网络连接错误");
    }

    @Override
    public void onDestroy() {
        dataChartPresenter.onViewDestroyed();
        super.onDestroy();
    }
}
