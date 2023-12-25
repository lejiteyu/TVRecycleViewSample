package lyon.browser.tv_recyclerview_sample.Adapter;


import android.graphics.Color;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lyon.browser.tv_recyclerview_sample.R;
import lyon.browser.tv_recyclerview_sample.Utils;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{
    private List<Item> itemList;
    String title;
    public ItemAdapter(List<Item> itemList,String title) {
        this.itemList = itemList;
        this.title=title;
    }

    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }
    private SparseArray<Integer> lastFocusedPositions = new SparseArray<>(); // 记录每行上次被焦点选中的位置
    private SparseArray<Integer> currentFocusedPositions = new SparseArray<>(); // 记录每行当前焦点位置

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view,title);
    }

    @Override
    public void onBindViewHolder( ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.bind(item,position);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public boolean onkeyDown(int keyCode, KeyEvent event){
        if(event.getAction()==KeyEvent.ACTION_UP){
            switch (event.getKeyCode()){
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:

                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    break;

            }
        }

        return false;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView tabRecyclerView;
        private TabAdapter tabAdapter;
        private TextView titleTextView;
        private String title;

        public ItemViewHolder(View itemView,String title) {
            super(itemView);
            this.title=title;
            tabRecyclerView = itemView.findViewById(R.id.tabRecyclerView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            titleTextView.setTextColor(Color.WHITE);
            titleTextView.setTextSize(Utils.dpToPx(itemView.getContext(), 32));
            // 設定橫列的 RecyclerView
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            tabRecyclerView.setLayoutManager(layoutManager);

            // 為整個 itemView 添加 focus 變化監聽器
            itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    // 在這裡處理 focus 變化事件，例如修改背景顏色等
                    if (hasFocus) {
                        itemView.setBackgroundColor(Color.BLUE);
                    } else {
                        itemView.setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            });

            // 設定橫列的 Adapter
            tabAdapter = new TabAdapter();

            tabRecyclerView.setAdapter(tabAdapter);
            tabAdapter.setOnItemFocusChangeListener(new TabAdapter.ItemFocusChange() {
                @Override
                public void onFocusChange(View view, boolean hasFocus, int position ,int rowPos) {
                    if (hasFocus) {
                        itemView.setBackgroundColor(Color.argb(200,222,133,0));
                    } else {
                        itemView.setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            });
        }

        public void bind(Item item, int position) {
            // 更新標題文字
            if(title!=null){
                titleTextView.setText(title);
            }else {
                titleTextView.setText("Title_" + (position + 1));
            }
            // 更新橫列的內容
            tabAdapter.setRowPos(position);
            tabAdapter.setTabContent(item.getTabContent());
            tabAdapter.setLastFocusedPosition(position, 0); // 初始focus為第一個tab
            tabAdapter.notifyDataSetChanged();

            tabAdapter.setOnItemFocusChangeListener(new TabAdapter.ItemFocusChange() {
                @Override
                public void onFocusChange(View view, boolean hasFocus, int position, int rowPos) {

                }
            });
        }
    }
}
