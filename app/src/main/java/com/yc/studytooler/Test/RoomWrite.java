package com.yc.studytooler.Test;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yc.studytooler.R;
import com.yc.studytooler.bean.UserInfo;
import com.yc.studytooler.repository.UserRepository;
import com.yc.studytooler.utils.ToastUtil;

/**
 * @ClassName TestLogin
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/2 5:43
 * @VERSION 1.0
 */
public class RoomWrite extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name; // 声明一个编辑框对象
    private EditText et_password; // 声明一个编辑框对象

    private UserRepository userRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_room);
        et_name = findViewById(R.id.et_name);
        et_password = findViewById(R.id.et_password);
        findViewById(R.id.btn_save).setOnClickListener(this);
        userRepository = new UserRepository();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save) {
            String name = et_name.getText().toString();
            String password = et_password.getText().toString();

            if (TextUtils.isEmpty(name)) {
                ToastUtil.show(this, "请先填写书籍名称");
                return;
            } else if (TextUtils.isEmpty(password)) {
                ToastUtil.show(this, "请先填写作者姓名");
                return;
            }
            // 以下声明一个书籍信息对象，并填写它的各字段值
            UserInfo info = new UserInfo();
            info.setUser_name(name);
            info.setUser_pwd(password);
            userRepository.insert(info).observe(RoomWrite.this,isSuccess->{
                if (isSuccess == true) {
                    ToastUtil.show(this, "数据已写入Room数据库");
                }else{
                    ToastUtil.show(this, "数据没有写入Room数据库");
                }
            });

        }
    }
}
