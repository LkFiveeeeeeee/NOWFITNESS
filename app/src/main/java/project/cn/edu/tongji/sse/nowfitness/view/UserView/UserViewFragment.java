package project.cn.edu.tongji.sse.nowfitness.view.UserView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.victor.ringbutton.RingButton;
import com.zhihu.matisse.Matisse;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.FileHelper;
import project.cn.edu.tongji.sse.nowfitness.presenter.UserViewPresenter;
import project.cn.edu.tongji.sse.nowfitness.view.DataChartView.DataChartView;
import project.cn.edu.tongji.sse.nowfitness.view.PlanQuestionView.PlanQuestionView;
import project.cn.edu.tongji.sse.nowfitness.view.UserSettingView.UserSettingView;
import project.cn.edu.tongji.sse.nowfitness.view.UserView.DisplayVIEW.DisplayView;
import project.cn.edu.tongji.sse.nowfitness.view.method.ConstantMethod;
import project.cn.edu.tongji.sse.nowfitness.view.method.PermissionMethod;
import project.cn.edu.tongji.sse.nowfitness.view.UserView.CalendarView.CalendarControlMethod;
import project.cn.edu.tongji.sse.nowfitness.view.UserView.CalendarView.ConstantColor;


public class UserViewFragment extends Fragment implements CalendarControlMethod, UserViewMethod,SensorEventListener{
    /*temp para*/
    private List<Uri> imageUri;


    private UserViewPresenter userViewPresenter;

    /*Calendar Para*/
    private TextView monthDay;
    private TextView yearDay;
    private TextView lunarDay;
    private TextView currentDay;
    private CalendarView calendarView;
    /*Calendar Para*/


    /*others Para*/
    private CircleImageView avatarImageView;
    private LinearLayout momentLayout;
    private LinearLayout followLayout;
    private LinearLayout fansLayout;
    private TextView momentNum;
    private TextView fansNum;
    private TextView followersNum;
    private EditText heightNum;
    private EditText weightNum;
    private TextView BMINum;
    private ImageView sexImage;
    private TextView nickName;
    private View myView;
    private RingButton settingButton;
    /*others Para*/

    private SensorManager sensorManager;
    private Sensor accelerateSensor;
    private boolean isShake = false;
    private AlertDialog dialog;

    /*摇一摇相关*/

    @Override
    public void onStart() {
        super.onStart();
        sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager != null){
            accelerateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if(accelerateSensor != null){
                sensorManager.registerListener((SensorEventListener) this,accelerateSensor,SensorManager.SENSOR_DELAY_UI);
                Log.d("Test Sensor", "onStart: OK 加速器传感器");
            }
        }
    }

    @Override
    public void onPause() {
        if(sensorManager != null){
            sensorManager.unregisterListener((SensorEventListener) this);
        }
        super.onPause();

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.d("Test Sensor", "onStart: OK here is an event");
        int type = sensorEvent.sensor.getType();
        if(type == Sensor.TYPE_ACCELEROMETER){
            float[] values = sensorEvent.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            if((Math.abs(x) > 17  || Math.abs(y) > 17 || Math.abs(z)>17 )&& ! isShake){
                Log.d("Test Sensor", "onStart: OK 加速器传感器 The event is OK");
                isShake = true;
                Observable.just("Success")
                        .subscribe(this::shakeSuccess,this::queryError);
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.userview,container,false);
        userViewPresenter.initView();
        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewPresenter = new UserViewPresenter(this,this);
    }

    public void initView(){
        initUserView();
        initCalendarView();
        initDialog();
    }

    /*Calendar Method*/

    private void initDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        dialog = builder.setIcon(R.drawable.running)
                .setTitle("ε=ε=ε=┌(；´ﾟｪﾟ)┘")
                .setMessage("是否展开今日运动计划调查")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getActivity(),PlanQuestionView.class);
                        startActivity(intent);
                    }
                })
                .setNeutralButton("取消",null)
                .create();
    }

    @Override
    public void initCalendarView() {
        monthDay = (TextView) myView.findViewById(R.id.tv_month_day);
        yearDay = (TextView) myView.findViewById(R.id.tv_year);
        lunarDay = (TextView) myView.findViewById(R.id.tv_lunar);
        currentDay = (TextView) myView.findViewById(R.id.tv_current_day);
        calendarView = (CalendarView) myView.findViewById(R.id.calendarView);
        setInitView();
    }

    @Override
    public void setInitView() {

        UserInfoModel userInfoModel = UserInfoLab.get().getUserInfoModel();
        Log.d("String1111111", userInfoModel.getPictureUrl());
        if(userInfoModel.getPictureUrl() != null){
            Glide.with(myView).load(userInfoModel.getPictureUrl()).into(avatarImageView);
        }
        momentNum.setText(String.valueOf(userInfoModel.getMomentsNum()));
        followersNum.setText(String.valueOf(userInfoModel.getFollowingNum()));
        fansNum.setText(String.valueOf(userInfoModel.getFansNum()));
        heightNum.setText(String.valueOf(userInfoModel.getHeight()));
        weightNum.setText(String.valueOf(userInfoModel.getWeight()));
        nickName.setText(userInfoModel.getNickName());
        if(userInfoModel.getSex().equals("男")){
            sexImage.setImageResource(R.drawable.male);
        }else if(userInfoModel.getSex().equals("女")){
            sexImage.setImageResource(R.drawable.female);
        }


        yearDay.setText(String.valueOf(calendarView.getCurYear()));
        monthDay.setText(calendarView.getCurMonth() + "月" + calendarView.getCurDay() + "日");
        lunarDay.setText("今日");
        currentDay.setText(String.valueOf(calendarView.getCurDay()));


        Map<String, Calendar> map = new HashMap<>();

        if(userInfoModel.getDateCheckList() != null){
            for(int i = 0;i < userInfoModel.getDateCheckList().size();i++){
                Date date = StringConvertToDate(userInfoModel.getDateCheckList().get(i));
                java.util.Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);

                Log.d("111111111", "setInitView: " + date.toString());
                Log.d("111111111", "setInitView: " + date.getYear());
                Log.d("111111111", "setInitView: " + date.getMonth());
                Log.d("111111111", "setInitView: " + date.getDay());
                map.put(getSchemeCalendar(calendar.get(java.util.Calendar.YEAR)
                        ,calendar.get(java.util.Calendar.MONTH) + 1,
                        calendar.get(java.util.Calendar.DAY_OF_MONTH)," ").toString(),
                        getSchemeCalendar(calendar.get(java.util.Calendar.YEAR)
                                ,calendar.get(java.util.Calendar.MONTH) + 1,
                                calendar.get(java.util.Calendar.DAY_OF_MONTH)," "));
            }
        }


    /*    map.put(getSchemeCalendar(calendarView.getCurYear(), calendarView.getCurMonth(), 3, " ").toString(),
                getSchemeCalendar(calendarView.getCurYear(), calendarView.getCurMonth(), 3, " "));
        map.put(getSchemeCalendar(calendarView.getCurYear(), calendarView.getCurMonth(), 4, " ").toString(),
                getSchemeCalendar(calendarView.getCurYear(), calendarView.getCurMonth(), 4, " "));
        map.put(getSchemeCalendar(calendarView.getCurYear(), calendarView.getCurMonth(), 5, " ").toString(),
                getSchemeCalendar(calendarView.getCurYear(), calendarView.getCurMonth(), 5, " "));
        map.put(getSchemeCalendar(calendarView.getCurYear(), calendarView.getCurMonth(), 6, " ").toString(),
                getSchemeCalendar(calendarView.getCurYear(), calendarView.getCurMonth(), 6, " "));*/

        calendarView.setSchemeDate(map);

    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener((SensorEventListener) this,accelerateSensor,SensorManager.SENSOR_DELAY_UI);
        Log.d("Test Sensor", "onStart: OK 加速器传感器 The event is OK");
        setInitView();
    }

    @Override
    public Calendar getSchemeCalendar(int year, int month, int day, String text) {
        Random random = new Random();
        int color = ConstantColor.color[random.nextInt(100) % ConstantColor.color.length];

        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);
        calendar.setScheme(text);
        return calendar;
    }

    @Override
    public Date StringConvertToDate(String dateString) {
        Date date = new Date();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            date = sdf.parse(dateString);
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }

    /*Calendar Method*/


    /*UserViewFragment Method*/
    @Override
    public void initUserView() {
        avatarImageView =
                (CircleImageView) myView.findViewById(R.id.avatar);
        momentLayout = (LinearLayout) myView.findViewById(R.id.indimoment);
        followLayout = (LinearLayout) myView.findViewById(R.id.indifollow);
        fansLayout = (LinearLayout) myView.findViewById(R.id.indifans);
        momentNum = (TextView) myView.findViewById(R.id.momentnum);
        fansNum = (TextView) myView.findViewById(R.id.fansnum);
        followersNum = (TextView) myView.findViewById(R.id.followersnum);
        heightNum = (EditText) myView.findViewById(R.id.height_view);
        weightNum = (EditText) myView.findViewById(R.id.weight_view);
        BMINum = (TextView) myView.findViewById(R.id.bmiview);
        nickName = (TextView) myView.findViewById(R.id.username);
        sexImage = (ImageView) myView.findViewById(R.id.sex);
        settingButton = (RingButton) myView.findViewById(R.id.ringButton);
        setListener();
    }

    @Override
    public void setBMINum() {
        if(heightNum.getText().equals("0")||weightNum.getText().equals("0")){
            return;
        }else{
            //TODO calculate BMI number
            double num = userViewPresenter.getBMINum();
            DecimalFormat decimalFormat = new DecimalFormat("#.#");

            if(num == 0){
                return;
            }
            if(num < 18.5){
                BMINum.setText(decimalFormat.format(num) + " 过轻 ");
            }else if(num < 24){
                BMINum.setText(decimalFormat.format(num) + " 正常 ");
            }else if(num < 28){
                BMINum.setText(decimalFormat.format(num) + " 超重 ");
            }else{
                BMINum.setText(decimalFormat.format(num) + " 肥胖 ");
            }
        }
    }



    @Override
    public void setListener() {
        momentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Intent

            }
        });
        followLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Intent
                Intent intent = new Intent(getActivity(),DisplayView.class);
                intent.putExtra(ConstantMethod.type_Key,
                        ConstantMethod.stars_Type);
                startActivity(intent);
            }
        });
        fansLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Intent
                Intent intent = new Intent(getActivity(),DisplayView.class);
                intent.putExtra(ConstantMethod.type_Key,
                        ConstantMethod.fans_Type);
                startActivity(intent);
            }
        });
        heightNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userViewPresenter.setHeight(heightNum.getText().toString());
            }
        });
        weightNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userViewPresenter.setWeight(weightNum.getText().toString());
            }
        });
        avatarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstantMethod.useMatisseFromFragment(UserViewFragment.this);
            }
        });
        settingButton.setOnClickListener(new RingButton.OnClickListener() {
            @Override
            public void clickUp() {
                Intent intent = new Intent(getContext(),DataChartView.class);
                startActivity(intent);
            }

            @Override
            public void clickDown() {
                Intent intent = new Intent(getContext(),UserSettingView.class);
                startActivity(intent);
            }
        });
    }
    /*UserViewFragment Method*/


    /* 请求回调操作*/

      @Override
    public void jumpToStars() {

    }

    @Override
    public void jumpToFans() {

    }

    @Override
    public void jumpToMyMoments() {

    }

    @Override
    public void queryError(Throwable e) {
        e.printStackTrace();
        Log.d("error", "queryError: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @Override
    public void shakeSuccess(String s) {
        isShake = false;
        Log.d("Test shake", "shakeSuccess: " + isShake);
        Log.d("Test Sensor", s);
        dialog.show();

    }

    @Override
    public void applyForImageChange(ResponseModel responseDTO) {
        if(responseDTO.getStatus() == 200){
            Log.d("test post", "applyForImageChange: success!!!!!!");
        }
        Log.d("test post", responseDTO.getError());
    }

    @Override
    public void applyForBodyInfoChange() {

    }

    @Override
    public void applyForMomentsInfoChange() {

    }

    /*   请求回调操作*/

    @Override
    public void onDestroyView() {
        userViewPresenter.onViewDestroyed();
        super.onDestroyView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantMethod.REQUEST_IMAGE_CODE && resultCode == Activity.RESULT_OK) {
            imageUri = Matisse.obtainResult(data);
            Log.d("1111", "onActivityResult:succsess Image ");
            String url = FileHelper.getFilePath(getContext(),imageUri.get(0));
            Log.d("AAAA", url);
            Glide.with(myView).load(imageUri.get(0)).into(avatarImageView);
            userViewPresenter.setAvatar(imageUri.get(0).toString());
            Log.d("AAAA", url);
            userViewPresenter.postAvatar(url,(int) UserInfoLab.get().getUserInfoModel().getId());
        }
    }



}
