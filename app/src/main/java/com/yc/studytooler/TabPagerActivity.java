package com.yc.studytooler;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.yc.studytooler.adapter.TabPagerAdapter;

import com.yc.studytooler.manager.UserManager;
import com.yc.studytooler.viewmodel.SharedViewModel;

/**
 * @ClassName TabPagerActivity
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/2 2:57
 * @VERSION 1.0
 */
public class TabPagerActivity extends AppCompatActivity {

    private ViewPager2 vp_content;
    private RadioGroup rg_tabbar;

    private SharedViewModel sharedViewModel;
    // 在Activity或Fragment中获取AppViewModel实例

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_pager);
        vp_content = findViewById(R.id.vp_content);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        //获取从LoginIntent发送的数据
        Intent intent = getIntent();
        if (intent != null) {
            // 获取传递的用户名和头像
            Log.d("TabPagerActivity","进入这里初始化数据");
            String username = intent.getStringExtra("username");
            byte[] userHead = intent.getByteArrayExtra("head");
            sharedViewModel.setUserName(username);
            sharedViewModel.setAvatarUrl(userHead);
            UserManager.getInstance().setUserName(username);
        }

        //构建一个翻页适配器
        TabPagerAdapter adapter = new TabPagerAdapter(this);
        vp_content.setAdapter(adapter);
        vp_content.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                rg_tabbar.check(rg_tabbar.getChildAt(position).getId());
            }
        });

        rg_tabbar = findViewById(R.id.rg_tabbar);
        rg_tabbar.setOnCheckedChangeListener((group, checkedId) -> {
            for (int pos=0; pos<rg_tabbar.getChildCount(); pos++) {
                RadioButton tab = (RadioButton) rg_tabbar.getChildAt(pos);
                if (tab.getId() == checkedId) {
                    vp_content.setCurrentItem(pos); // ViewPager2设置当前项
                }
            }
        });

    }
}
