package project.cn.edu.tongji.sse.nowfitness.model;

/**
 * Created by a on 2018/12/6.
 */

public class Response {

    /**
     * timestamp : 2018-12-05T16:35:56.397+0000
     * status : 500
     * error : Internal Server Error
     * message : No message available
     * path : /user/update
     */

    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

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
}
