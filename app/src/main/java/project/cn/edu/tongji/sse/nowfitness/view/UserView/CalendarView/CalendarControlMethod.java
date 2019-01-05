package project.cn.edu.tongji.sse.nowfitness.view.UserView.CalendarView;

import com.haibin.calendarview.Calendar;

import java.util.Date;

/**
 * Created by LK on 2018/11/24.
 */

public interface CalendarControlMethod {

    void initCalendarView();

    void setInitView();

    Calendar getSchemeCalendar(int year, int month, int day, String text);

    Date stringConvertToDate(String dateString);
}
