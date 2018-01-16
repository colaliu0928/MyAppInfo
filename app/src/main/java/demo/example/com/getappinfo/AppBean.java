package demo.example.com.getappinfo;

import android.graphics.drawable.Drawable;

/**
 * 类的作用：封装Javabean
 * lenovo 刘珂珂
 * 2018/1/15
 * 20:39
 */

public class AppBean {
    private Drawable appIcon;
    private String appName;
    private String appVersion;
    private String lastUpdateTime;

    public AppBean(Drawable appIcon, String appName, String appVersion, String lastUpdateTime) {
        this.appIcon = appIcon;
        this.appName = appName;
        this.appVersion = appVersion;
        this.lastUpdateTime = lastUpdateTime;
    }

    public AppBean() {
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
