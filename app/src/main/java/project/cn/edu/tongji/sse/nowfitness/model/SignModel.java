package project.cn.edu.tongji.sse.nowfitness.model;

public class SignModel {
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    String result;
    public SignModel(String response){
        result = response;
    }
}
