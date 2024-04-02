package com.yc.studytooler;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.yc.studytooler.adapter.TabPagerAdapter;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_pager);
        vp_content = findViewById(R.id.vp_content);
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
