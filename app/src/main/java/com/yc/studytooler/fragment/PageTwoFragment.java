package com.yc.studytooler.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yc.studytooler.LoginActivity;
import com.yc.studytooler.R;
import com.yc.studytooler.utils.ImageConverter;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @ClassName PageTwoActivity
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/2 2:48
 * @VERSION 1.0
 */
public class PageTwoFragment extends Fragment implements View.OnClickListener {

    private CircleImageView img_personal_head;

    private TextView tv_user_name;
    private Context context;

    private ActivityResultLauncher<Intent> img_head_Launcher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_tab_second,container,false);
        img_personal_head = view.findViewById(R.id.img_personal_head);
        tv_user_name = view.findViewById(R.id.tv_user_name);
        img_personal_head.setOnClickListener(this);
        context = getContext();

        img_head_Launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String username = data.getStringExtra("username");
                            byte[] head = data.getByteArrayExtra("head");
                           tv_user_name.setText(username);
                            Log.d("PageTwoFragment","用户信息是："+username);
                            if (head != null) {
                                img_personal_head.setImageBitmap(ImageConverter.byteArrayToBitmap(head));
                            }
                        }
                    }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.img_personal_head){
            new AlertDialog.Builder(context)
                    .setTitle("登录提示")
                    .setMessage("你此时没有登录，是否需要登录？")
                    .setPositiveButton("确认",(dialog, which) -> {
                        Intent intent = new Intent(context, LoginActivity.class);
                        img_head_Launcher.launch(intent);
                    })
                    .setNegativeButton("取消",null)
                    .show();
        }
    }
}
