package project.cn.edu.tongji.sse.nowfitness.view.UserView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.bumptech.glide.Glide;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.victor.ringbutton.RingButton;
import com.zhihu.matisse.Matisse;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.Constant;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.FileHelper;
import project.cn.edu.tongji.sse.nowfitness.presenter.UserViewPresenter;
import project.cn.edu.tongji.sse.nowfitness.view.Datachartview.DataChartView;
import project.cn.edu.tongji.sse.nowfitness.view.PersonPageView.PersonPageView;
import project.cn.edu.tongji.sse.nowfitness.view.PersonPageView.ToPersonPageView;
import project.cn.edu.tongji.sse.nowfitness.view.PlanQuestionView.PlanQuestionView;
import project.cn.edu.tongji.sse.nowfitness.view.UserSettingView.UserSettingView;
import project.cn.edu.tongji.sse.nowfitness.view.UserView.DisplayVIEW.DisplayView;
import project.cn.edu.tongji.sse.nowfitness.view.method.ConstantMethod;
import project.cn.edu.tongji.sse.nowfitness.view.UserView.CalendarView.CalendarControlMethod;
import project.cn.edu.tongji.sse.nowfitness.view.UserView.CalendarView.ConstantColor;

/**
 * Created by LK on 2018/11/22.
 */

public class UserViewFragment extends Fragment implements CalendarControlMethod, UserViewMethod,SensorEventListener,ToPersonPageView {
    /*temp para*/
    private String tag = "Test Sensor";
    private String classTag = "UserViewFragment";
    private SecureRandom random = new SecureRandom();


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
                Log.d(tag, "onStart: OK 加速器传感器");
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
        Log.d(tag, "onStart: OK here is an event");
        int type = sensorEvent.sensor.getType();
        if(type == Sensor.TYPE_ACCELEROMETER){
            float[] values = sensorEvent.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            if((Math.abs(x) > 17  || Math.abs(y) > 17 || Math.abs(z)>17 )&& ! isShake){
                Log.d(tag, "onStart: OK 加速器传感器 The event is OK");
                isShake = true;
                Observable.just("Success")
                        .subscribe(this::shakeSuccess,this::queryError);
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //DO NOTHING
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
                Date date = stringConvertToDate(userInfoModel.getDateCheckList().get(i));
                java.util.Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);

                map.put(getSchemeCalendar(calendar.get(java.util.Calendar.YEAR)
                        ,calendar.get(java.util.Calendar.MONTH) + 1,
                        calendar.get(java.util.Calendar.DAY_OF_MONTH)," ").toString(),
                        getSchemeCalendar(calendar.get(java.util.Calendar.YEAR)
                                ,calendar.get(java.util.Calendar.MONTH) + 1,
                                calendar.get(java.util.Calendar.DAY_OF_MONTH)," "));
            }
        }


        calendarView.setSchemeDate(map);

    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener((SensorEventListener) this,accelerateSensor,SensorManager.SENSOR_DELAY_UI);
        Log.d(tag, "onStart: OK 加速器传感器 The event is OK");
        setInitView();
    }

    @Override
    public Calendar getSchemeCalendar(int year, int month, int day, String text) {

        int color = ConstantColor.color[this.random.nextInt(100) % ConstantColor.color.length];

        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);
        calendar.setScheme(text);
        return calendar;
    }

    @Override
    public Date stringConvertToDate(String dateString) {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat")
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            date = sdf.parse(dateString);
        }catch (Exception e){
            Log.d(classTag, "stringConvertToDate: ");
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
        if(!(heightNum.getText().toString().equals("0")||weightNum.getText().toString().equals("0"))){
            double num = userViewPresenter.getBMINum();
            DecimalFormat decimalFormat = new DecimalFormat("#.#");

            if(num >= -Constant.EPSILON && num <= Constant.EPSILON){
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

                UserInfoModel userInfoModel = UserInfoLab.get().getUserInfoModel();
                jumpToPersonPage((int)userInfoModel.getId(),userInfoModel.getUserName(),userInfoModel.getNickName(),userInfoModel.getPictureUrl());

            }
        });
        followLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(),DisplayView.class);
                intent.putExtra(Constant.TYPE_KEY,
                        Constant.STARS_TYPE_S);
                startActivity(intent);
            }
        });
        fansLayout.setOnClickListener((View view) -> {
            Intent intent = new Intent(getActivity(),DisplayView.class);
            intent.putExtra(Constant.TYPE_KEY,
                    Constant.FANS_TYPE_S);
            startActivity(intent);
        });
        heightNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //DO NOTHING
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //DO NOTHING
            }

            @Override
            public void afterTextChanged(Editable editable) {
                userViewPresenter.setHeight(heightNum.getText().toString());
            }
        });
        weightNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //DO NOTHING
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //DO NOTHING
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
    public void queryError(Throwable e) {
        Log.d(classTag, e.toString());
        ConstantMethod.toastShort(getContext(),"网络错误!!!");
        Log.d("error", "queryError: " + e.toString());
    }

    @Override
    public void shakeSuccess(String s) {
        isShake = false;
        dialog.show();

    }

    @Override
    public void applyForImageChange(ResponseModel model) {
        if(model.getStatus() >= 200 && model.getStatus() < 300){
            ConstantMethod.toastShort(getContext(),"更换成功!");
        }else{
            ConstantMethod.toastShort(getContext(),"由于网络原因,更换失败!!!");
        }
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
        if (requestCode == Constant.REQUEST_IMAGE_CODE && resultCode == Activity.RESULT_OK) {
            List<Uri> imageUri = Matisse.obtainResult(data);
            String url = FileHelper.getFilePath(getContext(), imageUri.get(0));
            Glide.with(myView).load(imageUri.get(0)).into(avatarImageView);
            userViewPresenter.setAvatar(imageUri.get(0).toString());
            userViewPresenter.postAvatar(url,(int) UserInfoLab.get().getUserInfoModel().getId());
        }
    }


    @Override
    public void jumpToPersonPage(int id, String userName, String nickName, String personPhoto) {
        Intent intent = new Intent();
        intent.putExtra("userId",id);
        intent.putExtra("nickName",nickName);
        intent.putExtra("photo",personPhoto);
        intent.putExtra("userName",userName);
        intent.setClass(getActivity(), PersonPageView.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        userViewPresenter.onViewDestroyed();
        super.onDestroy();
    }
}
