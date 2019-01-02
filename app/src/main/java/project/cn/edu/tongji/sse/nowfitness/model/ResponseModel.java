package project.cn.edu.tongji.sse.nowfitness.model;

public class ResponseModel<T> {

    /**
     * timestamp : 2018-12-11 22:49:52.94
     * status : 400
     * error : user already login
     * message : user already login
     * path : /user/login
     * data : null
     */

    public ResponseModel(){
        //DO NOTHING
    }

    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private T data;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
