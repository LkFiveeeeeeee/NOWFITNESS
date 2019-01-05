package project.cn.edu.tongji.sse.nowfitness.model;


import android.Manifest;

/**
 * Created by LK on 2018/11/24.
 */


public class Constant {
    private Constant(){}
    public static final String APP_ID = "1108077260";
    public static final double AVA_STEP = 0.75;
    public static final int ONE_THOUSAND = 1000;
    public static final int MAX_STEPS = 7000;
    public static final int SPAN_COUNT = 3;
    public static final int MAX_ELEVATION_FACTOR = 8;
    public static final int A_RANK = 83;
    public static final int B_RANK = 65;
    public static final int C_RANK = 49;
    public static final int MIN_LENGTH = 6;
    public static final int MAX_LENGTH = 15;
    public static final float EPSILON = 0.000001F;
    public static final int STARS_TYPE = 1;
    public static final int FANS_TYPE = 0;
    public static final  String[] PERMISSIONS_STORAGE ={
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static final String RESULT_INTEGER = "result";
    public static final int REQUEST_PERMISSION_CODE = 55;
    public static final int REQUEST_IMAGE_CODE = 188;
    public static final String TYPE_KEY = "clickType";
    public static final String STARS_TYPE_S = "following";
    public static final String FANS_TYPE_S = "followers";

    public static final int NET_CODE_200 = 200;
    public static final int NET_CODE_300 = 300;

}
