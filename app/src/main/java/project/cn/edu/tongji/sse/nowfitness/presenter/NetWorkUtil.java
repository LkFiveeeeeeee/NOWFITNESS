package project.cn.edu.tongji.sse.nowfitness.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * @Package: project.cn.edu.tongji.sse.nowfitness.presenter
 * @ClassName: NetWorkUtil
 * @Description: 检测当前网络连接是否可用
 * @Author: omf
 * @UpdateDate: 2019/1/5 14:45
 */
public class NetWorkUtil {
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if(mConnectivityManager == null){
                return false;
            }
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
