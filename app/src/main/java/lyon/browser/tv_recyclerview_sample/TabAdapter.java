package lyon.browser.tv_recyclerview_sample;

import android.graphics.Color;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TabAdapter  extends RecyclerView.Adapter<TabAdapter.TabViewHolder>{
    private List<String> tabContent;
    private SparseArray<Integer> lastFocusedPositions = new SparseArray<>(); // 记录每行上次被焦点选中的位置
    private SparseArray<Integer> currentFocusedPositions = new SparseArray<>(); // 记录每行当前焦点位置


    public TabAdapter() {
        this.tabContent = new ArrayList<>();
    }

    public void setTabContent(List<String> tabContent) {
        this.tabContent = tabContent;
    }

    @Override
    public TabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_layout, parent, false);
        return new TabViewHolder(view);
    }

    @Override
    public void onBindViewHolder( TabViewHolder holder, int position) {
        String tabText = tabContent.get(position);
        holder.bind(tabText);


        // 设置焦点
        int lastFocusedPosition = lastFocusedPositions.get(holder.getAdapterPosition(), 0);
        int currentFocusedPosition = currentFocusedPositions.get(holder.getAdapterPosition(), 0);
        // 检查当前焦点位置是否已被记录，如果是，则将焦点移动到上次移动的位置
        if (currentFocusedPosition != 0) {
            holder.tabTextView.post(new Runnable() {
                @Override
                public void run() {

                    holder.tabTextView.requestFocus();
                }
            });
        } else if (position == lastFocusedPosition) {
            holder.tabTextView.requestFocus();
        }

    }

    // 设置每行上次被焦点选中的位置
    public void setLastFocusedPosition(int row, int position) {
        lastFocusedPositions.put(row, position);
    }



    @Override
    public int getItemCount() {
        return tabContent.size();
    }

    public static class TabViewHolder extends RecyclerView.ViewHolder {
        private TextView tabTextView;

        public TabViewHolder( View itemView) {
            super(itemView);
            tabTextView = itemView.findViewById(R.id.tabTextView);

            // 為 TextView 添加 click 事件
            tabTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 在這裡處理 click 事件，例如顯示相應的內容
                    Toast.makeText(view.getContext(), "Tab Clicked: " + tabTextView.getText(), Toast.LENGTH_SHORT).show();
                }
            });

            // 為 TextView 添加 focus 變化監聽器
            tabTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    // 在這裡處理 focus 變化事件，例如修改背景顏色等
                    if (hasFocus) {
                        tabTextView.setScaleX(1.2f);
                        tabTextView.setScaleY(1.2f);
                    } else {
                        tabTextView.setScaleX(1.0f);
                        tabTextView.setScaleY(1.0f);
                    }
                    if(itemFocusChange!=null){
                        itemFocusChange.onFocusChange(view, hasFocus, getAdapterPosition());
                    }
                }
            });
        }

        public void bind(String tabText) {
            tabTextView.setText(tabText);
        }
    }

    private static ItemFocusChange itemFocusChange;
    interface ItemFocusChange{
        public void  onFocusChange(View view, boolean hasFocus , int position);
    }

    public void setOnItemFocusChangeListener(ItemFocusChange itemFocusChange){
        this.itemFocusChange=itemFocusChange;
    }
}
