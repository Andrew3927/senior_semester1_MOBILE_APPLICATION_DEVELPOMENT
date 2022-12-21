// https://blog.csdn.net/weixin_43878652/article/details/110495402?ops_request_misc=&request_id=&biz_id=102&utm_term=%E6%96%B0%E5%BB%BA%E4%B8%80%E4%B8%AA%E8%AE%A1%E6%97%B6%E5%99%A8%E8%BF%9B%E7%A8%8B&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-1-110495402.142^v68^js_top,201^v4^add_ask,213^v2^t3_esquery_v3&spm=1018.2226.3001.4187
package com.example.newthrea_timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int count = 0;  // 保存计时的值
    private boolean Stop = true; // 计时状态，如果为停止状态则Stop值为true
    public TextView view1;  // 获取显示计时值的TextView控件
    private Button btn1;  // 获取启动计时线程的Button控件
    private Button btn2;  // 获取停止计时线程的Button控件
    private Handler handler = new Handler();  // 在此处MainActivity.java文件中，handler的作用是定时执行计时任务
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view1 = findViewById(R.id.viewOne);
        btn1 = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        btn1.setOnClickListener(new onStart());
        btn2.setOnClickListener(new onEnd());
        if(savedInstanceState!=null){
            count = savedInstanceState.getInt("count");
            Stop = savedInstanceState.getBoolean("stop");
        }
        Log.d("life circle","onCreate;count: "+count+" stop: "+Stop);
        handler.post(new testThread());
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("count",count);
        savedInstanceState.putBoolean("stop",Stop);
    }
    // 下面该函数实现：Android 按返回键不销毁当前Activity
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            // 在activity中调用 moveTaskToBack (boolean nonRoot)方法即可将activity 退到后台，注意不是finish()退出。
            // 参数为false——代表只有当前activity是task根，指应用启动的第一个activity时，才有效;
            // 参数为true——则忽略这个限制，任何activity都可以有效。
            Stop = true;
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    protected void onDestroy(){
        super.onDestroy();
        Log.d("life circle","onDestroy; count: "+count+" stop: "+Stop);
    }
    private class onStart implements View.OnClickListener {
        public void onClick(View view) {
            Stop=false;
        }
    }

    private class onEnd implements View.OnClickListener {
        public void onClick(View view) {
            Stop=true;
        }
    }

    private class testThread extends Thread {
        public void run() {
            if(!Stop){count++;}
            view1.setText(String.format(Locale.getDefault(),"%d",count));
            handler.postDelayed(this,1000);
        }
    }
}