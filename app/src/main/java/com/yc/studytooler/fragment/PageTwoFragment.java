package com.yc.studytooler.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.yc.studytooler.R;

import com.yc.studytooler.bean.Converters;
import com.yc.studytooler.viewmodel.SharedViewModel;

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
    protected View mView; // 声明一个视图对象
//    private ActivityResultLauncher<Intent> img_head_Launcher;

    SharedViewModel sharedViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d("PageThreeFragment","进入到onCreateView里面了");
        context = getContext();
        mView = inflater.inflate(R.layout.fragment_tab_third,container,false);
        img_personal_head = mView.findViewById(R.id.img_personal_head);
        tv_user_name = mView.findViewById(R.id.tv_user_name);


//        img_personal_head.setOnClickListener(this);




//        img_head_Launcher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Intent data = result.getData();
//                        if (data != null) {
//                            String username = data.getStringExtra("username");
//                            byte[] head = data.getByteArrayExtra("head");
//                           tv_user_name.setText(username);
//                            UserManager.getInstance().setUserName(username);
//                            Log.d("PageThreeFragment","用户信息是："+username);
//                            if (head != null) {
//                                img_personal_head.setImageBitmap(ImageConverter.byteArrayToBitmap(head));
//                            }
////                            UserManager.getInstance().setUserName(username);
//
//                        }
//                    }
//        });
        return mView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("PageThreeFragment","进入到onViewCreated里面了");
//        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        if (getActivity() != null) {
            sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
            // Fragment中观察数据
            sharedViewModel.getUserName().observe(getViewLifecycleOwner(), userName -> {
                // 更新UI
                Log.d("PageThreeFragment","更新用户名"+userName);
                tv_user_name.setText(userName);
            });


            sharedViewModel.getAvatarUrl().observe(getViewLifecycleOwner(), avatarUrl -> {
                if (avatarUrl != null) {
                    Log.d("PageThreeFragment","更新用户头像"+avatarUrl);
                    img_personal_head.setImageBitmap(Converters.byteArrayToBitmap(avatarUrl));

                }
            });
        }


    }

    @Override
    public void onClick(View v) {
//        if(v.getId() == R.id.img_personal_head){
//            new AlertDialog.Builder(context)
//                    .setTitle("登录提示")
//                    .setMessage("你此时没有登录，是否需要登录？")
//                    .setPositiveButton("确认",(dialog, which) -> {
//                        Intent intent = new Intent(context, LoginActivity.class);
//                        img_head_Launcher.launch(intent);
//                    })
//                    .setNegativeButton("取消",null)
//                    .show();
//        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Log.d("PageThreeFragment","进入到onAttach里了");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("PageThreeFragment","进入到onCreate了");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d("PageThreeFragment","进入到onStart()里了");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("PageThreeFragment","进入到onResume()里了");
        super.onResume();
//        observePunches();
//        initPunches();
    }

    @Override
    public void onPause() {
        Log.d("PageThreeFragment","进入到onPause()里了");

        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("PageThreeFragment","进入到onStop()里了");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d("PageThreeFragment","进入到onDestroyView()里了");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("PageThreeFragment","进入到onDestroy()里了");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d("PageThreeFragment","进入到onDetach()里了");
        super.onDetach();
    }
}
