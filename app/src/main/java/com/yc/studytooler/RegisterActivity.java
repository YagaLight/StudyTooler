package com.yc.studytooler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.yc.studytooler.bean.UserInfo;
import com.yc.studytooler.repository.UserRepository;
import com.yc.studytooler.utils.ImageConverter;

/**
 * @ClassName RegisterActivity
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/1 6:19
 * @VERSION 1.0
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,View.OnFocusChangeListener{

    private EditText et_name;
    private EditText et_pwd;
    private EditText et_pwd_again;

    private Button bt_register;
    private Button bt_reset;

    private UserRepository userRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_name = findViewById(R.id.et_name);
        et_pwd = findViewById(R.id.et_password);
        et_pwd_again = findViewById(R.id.et_pwd_again);
        bt_register = findViewById(R.id.bt_register);
        bt_reset = findViewById(R.id.bt_reset);

        et_pwd.setOnFocusChangeListener(this);
        et_pwd_again.setOnFocusChangeListener(this);

        bt_register.setOnClickListener(this);
        bt_reset.setOnClickListener(this);
        userRepository = new UserRepository();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bt_register){
            String name = et_name.getText().toString().trim();
            String password = et_pwd.getText().toString().trim();
            String pwd_again = et_pwd_again.getText().toString().trim();

            if(name.equals("") || password.equals("") || pwd_again.equals("")){
                Toast.makeText(RegisterActivity.this,"请补充信息！",Toast.LENGTH_SHORT).show();
                return;
            }
            UserInfo old_userInfo = userRepository.getUser(name,password);
            if(old_userInfo != null){
                Toast.makeText(RegisterActivity.this,"用户名已存在，请重新输入",Toast.LENGTH_SHORT).show();
            }else{
                UserInfo new_userInfo = new UserInfo();
                new_userInfo.setUser_name(name);
                new_userInfo.setUser_pwd(password);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.user_head);
                new_userInfo.setUser_head(ImageConverter.bitmapToByteArray(bitmap));
                userRepository.insert(new_userInfo).observe(RegisterActivity.this,isSuccess->{
                    if(isSuccess){
                        //构建返回登录页面的Intent
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("username",new_userInfo.getUser_name());
                        returnIntent.putExtra("password",new_userInfo.getUser_pwd());
                        returnIntent.putExtra("head",new_userInfo.getUser_head());
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    }else{

                    }
                });

            }
        } else if (v.getId() == R.id.bt_reset) {
            et_name.setText("");
            et_pwd.setText("");
            et_pwd_again.setText("");
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFoucus) {
        if(v.getId() == R.id.et_password && hasFoucus){
            if(et_name.getText().toString().equals("")){
                Toast.makeText(RegisterActivity.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.et_pwd_again) {
            if(hasFoucus){
                if(et_pwd.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
                }
            }
            if(!hasFoucus){
                if(!et_pwd.getText().toString().equals(et_pwd_again.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"两次密码不一致，请重新输入！",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
