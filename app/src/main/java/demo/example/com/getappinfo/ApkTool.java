package demo.example.com.getappinfo;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 类的作用：获取非系统的APP信息
 * lenovo 刘珂珂
 * 2018/1/15
 * 20:42
 */

public class ApkTool {

    static  String TAG = "ApkTool";
    public static List<AppBean> mLocalInstallApps = null;

    public static List<AppBean> scanLocalInstallAppList(PackageManager packageManager) {
        List<AppBean> myAppInfos = new ArrayList<>();
        try {
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo packageInfo = packageInfos.get(i);


                //过滤掉系统app
                if((ApplicationInfo.FLAG_SYSTEM& packageInfo.applicationInfo.flags) !=0) {
                    continue;
                }

                AppBean myAppInfo = new AppBean();
                //应用名称
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageInfo.packageName, 0);
                String applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
                myAppInfo.setAppName(applicationName);
                if (packageInfo.applicationInfo.loadIcon(packageManager) == null) {
                    continue;
                }
                //应用图标
                myAppInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(packageManager));
                //版本号
                myAppInfo.setAppVersion(packageInfo.versionName);
                Date date = new Date(packageInfo.lastUpdateTime);
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHH");
                //最后更新时间
                myAppInfo.setLastUpdateTime(format.format(date));
                myAppInfos.add(myAppInfo);
            }
        }catch (Exception e){
            Log.e(TAG,"===============获取应用包信息失败");
        }
        return myAppInfos;
    }

}
