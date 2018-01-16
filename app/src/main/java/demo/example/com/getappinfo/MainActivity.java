package demo.example.com.getappinfo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView lv_app_list;
    private AppAdapter mAppAdapter;
    public Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_app_list = findViewById(R.id.lv_app_list);
        mAppAdapter = new AppAdapter();
        lv_app_list.setAdapter(mAppAdapter);
        initAppList();

    }


    private void initAppList(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                //扫描得到APP列表
                final List<AppBean> appInfos = ApkTool.scanLocalInstallAppList(MainActivity.this.getPackageManager());
                final List<AppBean> sort = sort(appInfos);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAppAdapter.setData(sort);
                    }
                });
            }
        }.start();
    }


    class AppAdapter extends BaseAdapter {

        List<AppBean> myAppInfos = new ArrayList<>();

        public void setData(List<AppBean> myAppInfos) {
            this.myAppInfos = myAppInfos;
            notifyDataSetChanged();
        }

        public List<AppBean> getData() {
            return myAppInfos;
        }

        @Override
        public int getCount() {
            if (myAppInfos != null && myAppInfos.size() > 0) {
                return myAppInfos.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (myAppInfos != null && myAppInfos.size() > 0) {
                return myAppInfos.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            if (myAppInfos != null && myAppInfos.size() > 0) {
                return position;
            }
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mViewHolder;
            AppBean myAppInfo = myAppInfos.get(position);
            if (convertView == null) {
                mViewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_app_info, null);
                mViewHolder.iv_app_icon = convertView.findViewById(R.id.iv_app_icon);
                mViewHolder.tv_app_name = convertView.findViewById(R.id.tv_app_name);
                mViewHolder.tv_app_version = convertView.findViewById(R.id.tv_app_version);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }
            mViewHolder.iv_app_icon.setImageDrawable(myAppInfo.getAppIcon());
            mViewHolder.tv_app_name.setText(myAppInfo.getAppName());
            mViewHolder.tv_app_version.setText(myAppInfo.getAppVersion());
            return convertView;
        }

        class ViewHolder {

            ImageView iv_app_icon;
            TextView tv_app_name;
            TextView tv_app_version;
        }
    }

    /**
     * 根据最后更新时间对应用列表进行排序
     */
    public List<AppBean> sort(List<AppBean> list){

        List<AppBean> arrayList = new ArrayList<>();

        HashMap<AppBean,Long> map = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            map.put(list.get(i), (long) Integer.parseInt(list.get(i).getLastUpdateTime()));
        }

        //将map.entrySet()转换成list
        ArrayList<Map.Entry<AppBean, Long>> mapList = new ArrayList<>(map.entrySet());
        //降序排列
        Collections.sort(mapList, new Comparator<Map.Entry<AppBean, Long>>() {
            @Override
            public int compare(Map.Entry<AppBean, Long> o1, Map.Entry<AppBean, Long> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        for (Map.Entry<AppBean, Long> mapping : mapList) {
            Log.e("xxx","==============="+mapping.getKey().getLastUpdateTime());
            arrayList.add(mapping.getKey());
        }

        return arrayList;

    }


}
