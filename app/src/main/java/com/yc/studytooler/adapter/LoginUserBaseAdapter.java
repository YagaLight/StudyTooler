package com.yc.studytooler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yc.studytooler.R;
import com.yc.studytooler.bean.UserInfo;
import com.yc.studytooler.utils.ImageConverter;

import java.util.List;

public class LoginUserBaseAdapter extends BaseAdapter {

    private Context mContext; // 声明一个上下文对象
    private List<UserInfo> mUserList; // 声明一个行星信息列表


    public LoginUserBaseAdapter(Context mContext, List<UserInfo> mUserList) {
        this.mContext = mContext;
        this.mUserList = mUserList;
    }

    @Override
    public int getCount() {
        return mUserList.size();
    }

    @Override
    public Object getItem(int i) {
        return mUserList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) { // 转换视图为空
            holder = new ViewHolder(); // 创建一个新的视图持有者
            // 根据布局文件item_list.xml生成转换视图对象
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_user_list, null);
            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder); // 将视图持有者保存到转换视图当中
        } else { // 转换视图非空
            // 从转换视图中获取之前保存的视图持有者
            holder = (ViewHolder) convertView.getTag();
        }
       UserInfo user = mUserList.get(position);
        if(user.getUser_head() == null || user.getUser_head().length == 0){
            holder.iv_icon.setImageResource(R.drawable.user_head); // 显示行星的图片
        }else{
            holder.iv_icon.setImageBitmap(ImageConverter.byteArrayToBitmap(user.getUser_head()));
        }
        holder.tv_name.setText(user.getUser_name()); // 显示行星的名称
        holder.iv_icon.requestFocus();
        return convertView;
    }

    // 定义一个视图持有者，以便重用列表项的视图资源
    public final class ViewHolder {
        public ImageView iv_icon; // 声明图片的图像视图对象
        public TextView tv_name; // 声明名称的文本视图对象

    }

}
