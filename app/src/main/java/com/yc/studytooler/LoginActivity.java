package com.yc.studytooler;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.yc.studytooler.adapter.LoginUserBaseAdapter;
import com.yc.studytooler.bean.UserInfo;
import com.yc.studytooler.fragment.PageOneFragment;
import com.yc.studytooler.repository.UserRepository;
import com.yc.studytooler.utils.ImageConverter;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    //声明变量
    private Boolean login_state = false;
    private ImageView img_personal_head;
    private EditText et_username;
    private EditText et_password;
    private Checkable cb_rm;
    private TextView tv_register;
    private Button btn_confirm;
    private ImageButton im_qq;
    private ImageButton im_weChat;

    private ImageView img_user_list;

    private List<UserInfo> userInfoList = new ArrayList<>();


    private UserRepository userRepository;

    private ActivityResultLauncher<Intent> registerActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        img_personal_head = findViewById(R.id.img_personal_head);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        cb_rm = findViewById(R.id.cb_rm);
        tv_register = findViewById(R.id.tv_register);
        btn_confirm = findViewById(R.id.btn_confirm);
        im_qq = findViewById(R.id.im_qq);
        im_weChat = findViewById(R.id.im_wechat);

        initLogin();
        et_username.setOnClickListener(this);
        et_password.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);

        im_qq.setOnClickListener(this);
        im_weChat.setOnClickListener(this);

        // 初始化ActivityResultLauncher
        registerActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // 确保Intent不为空
                        Intent data = result.getData();
                        if (data != null) {
                            // 从Intent中获取用户信息
                            String username = data.getStringExtra("username");
                            String password = data.getStringExtra("password");
                            // String avatarUri = data.getStringExtra("avatarUri"); // 如果你传递了这个值
                            byte[] head = data.getByteArrayExtra("head");
                            et_username.setText(username);
                            et_password.setText(password);
                            if(head != null){
                                img_personal_head.setImageBitmap(ImageConverter.byteArrayToBitmap(head));
                            }

                            // 如果你的登录页面需要显示头像，也可以根据avatarUri来更新头像的显示
                        }
                    }
                }
        );



    }

    public void initLogin(){
        userRepository = new UserRepository();
        userInfoList = userRepository.getAllUsersInLogin();
    }


    @Override
    public void onClick(View v) {
        //点击用户名框，会显示一系列注册过的用户名
        if(v.getId() == R.id.et_username){

        }else if(v.getId() == R.id.sp_user_list){
            if(userInfoList.size() <= 0){
                Log.d("LoginActivity","数据库里没有用户信息");
            }
            LoginUserBaseAdapter adapter = new LoginUserBaseAdapter(LoginActivity.this, userInfoList);
            Spinner sp_user_list = findViewById(R.id.sp_user_list);
            sp_user_list.setAdapter(adapter);
            sp_user_list.setOnItemSelectedListener(new MySelectedListener());
        }else if(v.getId() == R.id.btn_confirm){
            String name = et_username.getText().toString().trim();
            String pwd = et_password.getText().toString().trim();
            if(name.equals("")){
                Toast.makeText(LoginActivity.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
                return;
            }

            if(pwd.equals("")){
                Toast.makeText(LoginActivity.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
                return;
            }

            UserInfo userInfo = userRepository.getUser(name,pwd);
            if (userInfo != null ){
                Intent returnIntent = new Intent();
                returnIntent.putExtra("username",name);
                returnIntent.putExtra("head",userInfo.getUser_head());
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }else{
                Toast.makeText(LoginActivity.this,"用户名或密码不正确！",Toast.LENGTH_SHORT).show();
            }

        }else if(v.getId() == R.id.tv_register){
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            registerActivityResultLauncher.launch(intent);
        }else if(v.getId() == R.id.et_password){
            if(et_username.getText().equals("")){
                Toast.makeText(LoginActivity.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 定义一个选择监听器，它实现了接口OnItemSelectedListener
    private class MySelectedListener implements AdapterView.OnItemSelectedListener {
        // 选择事件的处理方法，其中arg2代表选择项的序号
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            UserInfo user = userInfoList.get(arg2);
//            Toast.makeText(LoginActivity.this, "您选择的是" + userInfoList.get(arg2).user_name, Toast.LENGTH_LONG).show();
            img_personal_head.setImageBitmap(ImageConverter.byteArrayToBitmap(user.getUser_head()));
            et_username.setText(user.getUser_name());
            if(user.getRemember() != 0){
                et_password.setText(user.getUser_pwd());
                cb_rm.setChecked(true);
            }
        }

        // 未选择时的处理方法，通常无需关注
        public void onNothingSelected(AdapterView<?> arg0) {}
    }

}
