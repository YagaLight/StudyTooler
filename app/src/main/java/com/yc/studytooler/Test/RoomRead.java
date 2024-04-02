package com.yc.studytooler.Test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yc.studytooler.MainApplication;
import com.yc.studytooler.R;
import com.yc.studytooler.bean.UserInfo;
import com.yc.studytooler.dao.UserDao;
import com.yc.studytooler.repository.UserRepository;
import com.yc.studytooler.utils.ToastUtil;

import java.util.List;

/**
 * @ClassName TestLogin
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/2 5:43
 * @VERSION 1.0
 */
public class RoomRead extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_room;

    private UserRepository userRepository;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_room);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        tv_room = findViewById(R.id.tv_room);
        userRepository = new UserRepository();
        readRoom();
    }

    // 读取数据库中的所有书籍记录
    private void readRoom() {
        List<UserInfo> bookList = userRepository.getAllUsers(); // 获取所有书籍记录
        String desc = String.format("数据库查询到%d条记录，详情如下：", bookList.size());
        for (int i = 0; i < bookList.size(); i++) {
            UserInfo info = bookList.get(i);
            desc = String.format("%s\n第%d条记录信息如下：", desc, i + 1);
            desc = String.format("%s\n　用户名为：%s", desc, info.getUser_name());
            desc = String.format("%s\n　密码为：%s", desc, info.getUser_pwd());
        }
        if (bookList.size() <= 0) {
            desc = "数据库查询到的记录为空";
        }
        tv_room.setText(desc);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_delete) {
            userRepository.deleteAllUser().observe(RoomRead.this,isSuccess->{
                if (isSuccess == true) {
                    ToastUtil.show(this,"数据已全部删除");
                    readRoom(); // 读取数据库中的所有书籍记录
                }else{
                    ToastUtil.show(this,"数据删除失败");
                }
            });

        }
    }
}
