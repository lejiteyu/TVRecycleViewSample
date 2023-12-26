package lyon.browser.tv_recyclerview_sample.Adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lyon.browser.tv_recyclerview_sample.R;

public class TabAdapter  extends RecyclerView.Adapter<TabAdapter.TabViewHolder>{
    private List<String> tabContent;
    public static int selectedPosition = RecyclerView.NO_POSITION;
    private  int rowPos = 0;
    private  int itemPos = 0;
    public TabAdapter(int rowPos) {
        this.tabContent = new ArrayList<>();
        this.rowPos=rowPos;
    }

    public void setTabContent(List<String> tabContent) {
        this.tabContent = tabContent;
    }

    @Override
    public TabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_layout, parent, false);
        return new TabViewHolder(view , itemFocusChange ,rowPos);
    }

    @Override
    public void onBindViewHolder( TabViewHolder holder, int position) {
        String tabText = tabContent.get(position);
        holder.bind(tabText);
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
        holder.itemView.setFocusable(true);
    }


    public int getSelectedPosition(){
        return selectedPosition;
    }

    public int getRowPos(){
        return rowPos;
    }

    public void setRowPos(int rowPos){
        this.rowPos=rowPos;
    }

    public void setItemPos(int pos){
        this.itemPos = pos;
    }

    public int getItemPos(){
        return itemPos;
    }

    @Override
    public int getItemCount() {
        return tabContent.size();
    }

    public static class TabViewHolder extends RecyclerView.ViewHolder {
        private TextView tabTextView;

        public TabViewHolder( View itemView, ItemFocusChange itemFocusChange, int rowPos) {
            super(itemView);
            tabTextView = itemView.findViewById(R.id.tabTextView);
            itemView.setFocusable(true);
            // 為 TextView 添加 click 事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 在這裡處理 click 事件，例如顯示相應的內容
                    Toast.makeText(view.getContext(), "Tab Clicked: " + tabTextView.getText(), Toast.LENGTH_SHORT).show();
                    itemClick.onClick(view,getAdapterPosition() ,rowPos);
                    if(itemView.isFocused()){
                        itemView.setSelected(true);
                    }else{
                        itemView.setSelected(false);
                    }

//                    itemView.clearFocus();
                    itemView.requestFocus();
                    //itemView.setSelected(true);
                }
            });

            // 為 TextView 添加 focus 變化監聽器
            itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    // 在這裡處理 focus 變化事件，例如修改背景顏色等
                    if (hasFocus) {
                        itemView.setScaleX(1.2f);
                        itemView.setScaleY(1.2f);
                        itemView.setSelected(true);
                    } else {
                        itemView.setScaleX(1.0f);
                        itemView.setScaleY(1.0f);
                        itemView.setSelected(false);
                    }
                    if(itemFocusChange!=null){
                        itemFocusChange.onFocusChange(view, hasFocus, getAdapterPosition() ,rowPos);
                    }

                    // 更新選中位置
                    if (hasFocus) {
                        selectedPosition = getAdapterPosition();

                    }
                }
            });




        }

        public void bind(String tabText) {
            tabTextView.setText(tabText);
        }
    }

    private ItemFocusChange itemFocusChange;
    public interface ItemFocusChange{
        public void  onFocusChange(View view, boolean hasFocus , int position ,int rowPos);
    }

    public void setOnItemFocusChangeListener(ItemFocusChange itemFocusChange){
        this.itemFocusChange=itemFocusChange;
    }

    private static ItemClick itemClick;
    public interface ItemClick{
        public void  onClick(View view, int position ,int rowPos);
    }

    public void setOnItemClickListener(ItemClick itemClick){
        this.itemClick=itemClick;
    }
}
