package project.cn.edu.tongji.sse.nowfitness.data.network.dto;

import java.util.List;

public class StepDataDTO {

    /**
     * days : 3
     * stepsDataModelList : [{"id":2,"date":"2018-12-20T00:00:00.000+0800","steps":100,"calories":250},{"id":2,"date":"2018-12-19T00:00:00.000+0800","steps":21021,"calories":42545},{"id":2,"date":"2018-12-18T00:00:00.000+0800","steps":45,"calories":6786}]
     */

    private int days;
    private List<StepsDataModelListBean> stepsDataModelList;

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public List<StepsDataModelListBean> getStepsDataModelList() {
        return stepsDataModelList;
    }

    public void setStepsDataModelList(List<StepsDataModelListBean> stepsDataModelList) {
        this.stepsDataModelList = stepsDataModelList;
    }

    public static class StepsDataModelListBean {
        /**
         * id : 2
         * date : 2018-12-20T00:00:00.000+0800
         * steps : 100
         * calories : 250
         */

        private int id;
        private String date;
        private int steps;
        private int calories;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getSteps() {
            return steps;
        }

        public void setSteps(int steps) {
            this.steps = steps;
        }

        public int getCalories() {
            return calories;
        }

        public void setCalories(int calories) {
            this.calories = calories;
        }
    }
}
