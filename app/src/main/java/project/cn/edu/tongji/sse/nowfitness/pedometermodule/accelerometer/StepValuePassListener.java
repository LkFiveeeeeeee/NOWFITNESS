package project.cn.edu.tongji.sse.nowfitness.pedometermodule.accelerometer;

@FunctionalInterface
public interface StepValuePassListener {
    void stepChanged(int steps);
}
