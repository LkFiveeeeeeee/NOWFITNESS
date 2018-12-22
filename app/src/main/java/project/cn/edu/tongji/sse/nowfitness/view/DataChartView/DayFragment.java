package project.cn.edu.tongji.sse.nowfitness.view.DataChartView;

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
import android.widget.TextView;

import java.util.Objects;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.pedometerModule.StepService.StepService;
import project.cn.edu.tongji.sse.nowfitness.pedometerModule.StepService.UpdateUICallBack;
import project.cn.edu.tongji.sse.nowfitness.view.DataChartView.StepArcView.StepArcView;

public class DayFragment extends Fragment {
    /**CardView For Day*/
    private CardView stepContainer;
    private TextView stepView;
    private int dayCount;
    private StepArcView stepChart;
    private boolean isBind = false;
    private ServiceConnection serviceConnection;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.daychart_item,container,false);
        initView(view);

        return view;
    }

    public void initView(View view){
        stepContainer = (CardView) view.findViewById(R.id.step_cardview);
        stepChart = (StepArcView) view.findViewById(R.id.step_chart);
        stepView = (TextView) view.findViewById(R.id.step_text);
        setServiceConnection();
    }

    public void setServiceConnection(){
        Intent intent = new Intent(getContext(),StepService.class);
        serviceConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                StepService stepService = ((StepService.StepBinder) iBinder).getService();
                stepChart.setCurrentCount(7000,stepService.getStepCount());
                stepService.registerCallBack(new UpdateUICallBack() {
                    @Override
                    public void updateUI(int stepCounts) {
                        stepChart.setCurrentCount(7000,stepCounts);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(isBind){
            Objects.requireNonNull(this.getActivity()).unbindService(serviceConnection);
        }
    }
}
