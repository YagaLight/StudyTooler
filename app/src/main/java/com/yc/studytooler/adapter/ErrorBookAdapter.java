package com.yc.studytooler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.studytooler.R;
import com.yc.studytooler.bean.ErrorBook;

import java.util.List;

/**
 * @ClassName ErrorNoteAdapter
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/16 21:55
 * @VERSION 1.0
 */
public class ErrorBookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ErrorBook> errorBooks;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnAddItemClickListener addItemClickListener;
    private OnLongItemClickListener longClickListener;

    private AdapterInteraction adapterInteraction;
    private boolean isSelectMode = false;
    // 视图类型常量
    private static final int TYPE_ADD = 0;
    private static final int TYPE_ITEM = 1;


    public ErrorBookAdapter(List<ErrorBook> errorBooks,
                            Context context,
                            OnItemClickListener onItemClickListener,
                            OnAddItemClickListener addItemClickListener,
                            OnLongItemClickListener longClickListener,
                            AdapterInteraction adapterInteraction) {
        this.errorBooks = errorBooks;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        this.addItemClickListener = addItemClickListener;
        this.longClickListener = longClickListener;
        this.adapterInteraction = adapterInteraction;
        this.mInflater = LayoutInflater.from(context);  // 初始化 LayoutInflater
    }

    @Override
    public int getItemViewType(int position) {
        // 假设最后一个位置总是加号
        return position == errorBooks.size() - 1 ? TYPE_ADD : TYPE_ITEM;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.item_error_book, parent, false);
        return new ErrorBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ErrorBookViewHolder viewHolder = (ErrorBookViewHolder) holder;
        if (getItemViewType(position) == TYPE_ITEM) {
            ErrorBook errorBook = errorBooks.get(position);
            viewHolder.textView.setText(errorBook.getSubject_name());
            if (errorBook.getSubjectImageId() != 0) {
                viewHolder.imageView.setImageResource(errorBook.getSubjectImageId());
            } else {
                viewHolder.imageView.setImageResource(R.drawable.img_default);
            }
            //设置复选框
            viewHolder.checkBox.setChecked(errorBook.isSelected());
            viewHolder.checkBox.setVisibility(isSelectMode ? View.VISIBLE : View.GONE);

            viewHolder.itemView.setOnClickListener(v -> {
                if (isSelectMode) {
                    errorBook.setSelected(!errorBook.isSelected());
                    viewHolder.checkBox.setChecked(errorBook.isSelected());
                    notifyItemChanged(position);  // 优化视图更新
                    notifyItemSelectionChanged();
                } else {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, position);
                    }
                }
            });
            viewHolder.itemView.setOnLongClickListener(v -> {

                if (!isSelectMode) {
                    isSelectMode = true;
                    notifyDataSetChanged();
                    if (longClickListener != null) {
                        longClickListener.onLongItemClick(v, position);
                    }
//                    if (adapterInteraction != null) {
//                        adapterInteraction.onSelectionModeChanged(true);
//                    }
//                    return true;
                }
                return false;
            });
        } else { // TYPE_ADD
            viewHolder.imageView.setImageResource(R.drawable.img_add); // 加号图标
            viewHolder.textView.setText("创 建"); // 加号项文本
            viewHolder.itemView.setOnClickListener(v -> {
                if (addItemClickListener != null) {
                    addItemClickListener.onAddItemClick(v, position);
                }
            });
        }
    }


    public void setSelectionMode(boolean selectMode) {
        isSelectMode = selectMode;
        notifyDataSetChanged();
        if (adapterInteraction != null) {
            adapterInteraction.onSelectionModeChanged(selectMode);
        }
    }

    @Override
    public int getItemCount() {
        return errorBooks.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnAddItemClickListener {
        void onAddItemClick(View view, int position);
    }

    public interface OnLongItemClickListener {
        void onLongItemClick(View view, int position);
    }

    public interface AdapterInteraction {
        void onSelectionModeChanged(boolean isActive);
        void onItemSelectionChanged(boolean anySelected); // 添加一个新的方法来通知选中状态的变化
    }

    // 在适配器中调用此方法，每当选中状态变更时
    private void notifyItemSelectionChanged() {
        boolean anySelected = anyItemsSelected();
        if (adapterInteraction != null) {
            adapterInteraction.onItemSelectionChanged(anySelected);
        }
    }

    private boolean anyItemsSelected() {
        for (ErrorBook book : errorBooks) {
            if (book.isSelected()) {
                return true;
            }
        }
        return false;
    }


    public static class ErrorBookViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        CheckBox checkBox;

        public ErrorBookViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_error_book);
            textView = itemView.findViewById(R.id.tv_subject);
            checkBox = itemView.findViewById(R.id.cb_select); // 假设您已在布局中定义了ID为checkBoxSelect的复选框
            checkBox.setVisibility(View.GONE); // 默认隐藏复选框
        }
    }

}
