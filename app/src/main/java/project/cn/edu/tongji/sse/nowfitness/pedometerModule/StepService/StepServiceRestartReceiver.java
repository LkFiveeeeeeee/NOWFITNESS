package project.cn.edu.tongji.sse.nowfitness.pedometerModule.StepService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StepServiceRestartReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("RestartReceiver", "onReceive: ");
        context.startService(new Intent(context,StepService.class));
    }
}
