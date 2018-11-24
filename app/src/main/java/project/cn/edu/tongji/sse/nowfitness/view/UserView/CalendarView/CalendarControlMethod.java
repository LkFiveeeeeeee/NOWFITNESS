package project.cn.edu.tongji.sse.nowfitness.view.UserView.CalendarView;

import com.haibin.calendarview.Calendar;

import java.util.ArrayList;

public interface CalendarControlMethod {

    public void initCalendarView();

    public void setInitView();

    public Calendar getSchemeCalendar(int year, int month, int day, String text);
}
