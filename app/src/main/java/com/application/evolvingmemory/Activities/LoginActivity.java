package com.application.evolvingmemory.Activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.application.evolvingmemory.R;
import com.application.evolvingmemory.databinding.ActivityLoginBinding;
import com.qmuiteam.qmui.span.QMUITouchableSpan;



public class LoginActivity extends AppCompatActivity  {

    String str_treaty1,str_treaty2;
    SpannableString span_treaty;
    private ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //沉浸式透明
        immerseTransparent();
        setContentView(R.layout.activity_login);

        //视图绑定
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());

        //创建功能
        init();

    }

    void immerseTransparent(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    void init(){

        //登录跳转
        activityLoginBinding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer showStr = new StringBuffer();
                if (activityLoginBinding.checkTreaty.isChecked()) {
                    Intent main_int = new Intent();
                    main_int.setAction("main_intent");
                    startActivity(main_int);
                    showStr.delete(0,showStr.length());
                    showStr.append("登录跳转");
                }else{
                    showStr.delete(0,showStr.length());
                    showStr.append("请先同意注册协议");
                }

                Toast toast = Toast.makeText(LoginActivity.this, showStr, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        //输入框数字变动
        activityLoginBinding.phoneEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(TextUtils.isEmpty(activityLoginBinding.phoneEdit.getText())){
                    //设置晃动
                    activityLoginBinding.phoneEdit.setShakeAnimation();
                    //设置提示
                    Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                }

                if(s.length() == 13){
                    activityLoginBinding.loginBtn.setBackground(getResources().getDrawable(R.drawable.corners_button_exist));
                    activityLoginBinding.loginBtn.setEnabled(true);
                }else {
                    activityLoginBinding.loginBtn.setBackground(getResources().getDrawable(R.drawable.corners_button_none));

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //查看协议
        activityLoginBinding.treatyTxt.setMovementMethodDefault();
        activityLoginBinding.treatyTxt.setNeedForceEventToParent(true);
        activityLoginBinding.treatyTxt.setText(generateSp(activityLoginBinding.treatyTxt.getText().toString()));

        //免密登录
        activityLoginBinding.freeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "免密登录", Toast.LENGTH_SHORT).show();
            }
        });

        //QQ登录
        activityLoginBinding.qqModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "QQ登录", Toast.LENGTH_SHORT).show();
            }
        });

        //微信登录
        activityLoginBinding.wechatModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "微信登录", Toast.LENGTH_SHORT).show();
            }
        });

    }


    //获取Span位置（注意颜色是int型）
    private SpannableString generateSp(String text){

        str_treaty1 = "《用户注册服务协议》";
        str_treaty2 = "《隐私保护政策》";
        span_treaty = new SpannableString(text);
        int start = 0, end, index;
        while((index = text.indexOf(str_treaty1, start)) > -1){
            end = index + str_treaty1.length();
            span_treaty.setSpan(new QMUITouchableSpan(getResources().getColor(R.color.normalTextColor), getResources().getColor(R.color.pressedTextColor),
                    getResources().getColor(R.color.normalBackgroundColor), getResources().getColor(R.color.pressedBackgroundColor)) {
                @Override
                public void onSpanClick(View widget) {
                    Toast.makeText(LoginActivity.this, "用户注册服务协议", Toast.LENGTH_SHORT).show();
                }
            }, index, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            start = end;
        }

        start = 0;
        while((index = text.indexOf(str_treaty2, start)) > -1){
            end = index + str_treaty2.length();
            span_treaty.setSpan(new QMUITouchableSpan(getResources().getColor(R.color.normalTextColor), getResources().getColor(R.color.pressedTextColor),
                    getResources().getColor(R.color.normalBackgroundColor), getResources().getColor(R.color.pressedBackgroundColor)) {
                @Override
                public void onSpanClick(View widget) {
                    Toast.makeText(LoginActivity.this, "隐私保护政策", Toast.LENGTH_SHORT).show();
                }
            }, index, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            start = end;
        }

        return span_treaty;
    }

}