package project.cn.edu.tongji.sse.nowfitness.model;

public class LoginModel {
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    String result;
    public LoginModel(String response){
        result = response;
    }
}
