package lyon.browser.tv_recyclerview_sample.Activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.BrowseFrameLayout;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.SearchBar;
import androidx.leanback.widget.SearchOrbView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import lyon.browser.tv_recyclerview_sample.Adapter.Item;
import lyon.browser.tv_recyclerview_sample.Adapter.ItemAdapter;
import lyon.browser.tv_recyclerview_sample.Adapter.TabAdapter;
import lyon.browser.tv_recyclerview_sample.Fragment.TVSearchFragment;
import lyon.browser.tv_recyclerview_sample.R;
import lyon.browser.tv_recyclerview_sample.Utils;
import lyon.browser.tv_recyclerview_sample.cardView.CardPresenter;

public class TVHorizontalGridViewActivity extends Activity {
    String TAG = TVHorizontalGridViewActivity.class.getSimpleName();
    Context context;
    SearchOrbView searchOrbView;
    SearchBar searchBar;
    LinearLayout contentLyout;
    private SparseArray<Integer> lastFocusedPositions = new SparseArray<>(); // 记录每行上次被焦点选中的位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lb_search_fragment);
        setContentView(R.layout.tv_horizontal_grid_view);
        context = this;
        searchBar = findViewById(R.id.lb_search_bar);
        searchOrbView = (SearchOrbView)searchBar.findViewById(R.id.lb_search_bar_speech_orb);
        searchOrbView.setFocusable(true);
        searchOrbView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                rowPosold=-1;
                Toast.makeText(context,"rowPosold==-1",Toast.LENGTH_SHORT).show();
            }
        });

        contentLyout = (LinearLayout)findViewById(R.id.content_layout);
        Item item =new Item("item",30);
        setTabRow("first title",item.getTabContent());
        Item item2 =new Item("item2",30);
        setTabRow("2 title",item2.getTabContent());
        Item item3=new Item("item3",60);
        setGrieRow("grid",item3.getTabContent());
        //Grid 應為最下面一物件, 否則需要增加scrollView 來滑動 將導致 有兩個scrollview 重疊 而無法正常工作！
//        Item item4 =new Item("item4",30);
//        setTabRow("4 title",item4.getTabContent());
//        Item item5 =new Item("item5",30);
//        setTabRow("5 title",item5.getTabContent());

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                searchOrbView.requestFocus();
            }
        },500);

    }


    Handler mHandler = new Handler(Looper.getMainLooper());
    int rowPos = 0;
    int rowPosold = 0;
    public HorizontalGridView setTabRow(String title ,List<String> list){
        TextView titleView = new TextView(context);
        titleView.setTextColor(Color.WHITE);
        titleView.setTextSize(Utils.dpToPx(context, 32));
        titleView.setText(title);
        contentLyout.addView(titleView);
        rowPos++;
        int viewPos = 0;
        HorizontalGridView horizontalGridView = new HorizontalGridView(context){
            @Override
            public boolean onKeyDown(int keyCode, KeyEvent event) {
                TabAdapter tabAdapter = (TabAdapter) getAdapter();
                if(event.getAction()==KeyEvent.ACTION_DOWN) {
                    if(isScrolling(this)){
                        return true;
                    }
                    if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                        if (tabAdapter.getSelectedPosition() < 1) {
                            return true;
                        }
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        if (tabAdapter.getSelectedPosition() >=tabAdapter.getItemCount()) {
                            return true;
                        }
                    }
                }
                return super.onKeyDown(keyCode, event);
            }
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        horizontalGridView.setLayoutManager(layoutManager);

        horizontalGridView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TabAdapter tabAdapter = new TabAdapter(rowPos);

        horizontalGridView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Toast.makeText(context,"horizontalGridView has focus:"+hasFocus+" ,row:"+tabAdapter.getRowPos(),Toast.LENGTH_LONG).show();
            }
        });

        tabAdapter.setOnItemFocusChangeListener(new TabAdapter.ItemFocusChange() {
            @Override
            public void onFocusChange(View view, boolean hasFocus, int position, int rowP) {
                tabAdapter.setItemPos(position);
                if(hasFocus) {
                    if(rowP!=rowPosold){
                        rowPosold = rowP;
                        int pos= lastFocusedPositions.get(rowP,0);
                        View v = contentLyout.getChildAt(getRowItem((rowP)));
                        if(v instanceof HorizontalGridView){
                            HorizontalGridView horizontalGridView = (HorizontalGridView)v;
                            Log.e(TAG,"rowP:"+rowP+",pos:"+pos);
                            if(pos<horizontalGridView.getAdapter().getItemCount()) {
                                try {
                                    horizontalGridView.getLayoutManager().findViewByPosition(pos).requestFocus();
                                }catch (NullPointerException e){

                                }
                            }
                            Toast.makeText(context,"tabAdapter has focus:"+hasFocus+" ,row:"+rowP+", position:"+position+",pos:"+pos,Toast.LENGTH_LONG).show();

                        }
                    }else{
                        lastFocusedPositions.put(rowP,position);
                        Toast.makeText(context,"tabAdapter has focus:"+hasFocus+" ,row:"+rowP+", position:"+position,Toast.LENGTH_LONG).show();

                    }
                }
            }
        });
        tabAdapter.setOnItemClickListener(new TabAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position, int rowPos) {
                try {
                    horizontalGridView.getLayoutManager().findViewByPosition(position).requestFocus();
                } catch (NullPointerException e) {
                    Log.d(TAG,""+Utils.FormatStackTrace(e));
                }
            }
        });
        tabAdapter.setTabContent(list);
        horizontalGridView.setAdapter(tabAdapter);

        horizontalGridView.setSelectedPosition(0);
        contentLyout.addView(horizontalGridView);
        return  horizontalGridView;
    }

    private int getRowItem(int row){
        int pos = row-1;
        return pos+row;
    }

    HorizontalGridView GridView;
    int GridViewPos = 0;
    int gridRowNum = 7;
    int preLoadTime =3;
    public void setGrieRow(String title ,List<String> list){
        TextView titleView = new TextView(context);
        titleView.setTextColor(Color.WHITE);
        titleView.setTextSize(Utils.dpToPx(context, 32));
        titleView.setText(title);
        contentLyout.addView(titleView);
        rowPos++;
        GridView = new HorizontalGridView(context){
            @Override
            public boolean onKeyDown(int keyCode, KeyEvent event) {
                TabAdapter tabAdapter = (TabAdapter) getAdapter();
                if(event.getAction()==KeyEvent.ACTION_DOWN) {
                    if(isScrolling(this)){
                        return true;
                    }
                    if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                        if (tabAdapter.getSelectedPosition()%gridRowNum < 1) {
                            return true;
                        }
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        if (tabAdapter.getSelectedPosition()%gridRowNum >gridRowNum) {
                            return true;
                        }
                    }
                }
                return super.onKeyDown(keyCode, event);
            }
        };
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,gridRowNum);
        GridView.setLayoutManager(gridLayoutManager);
        GridView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TabAdapter tabAdapter = new TabAdapter(rowPos,gridRowNum);
        GridView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    GridView.scrollToPosition(GridViewPos);

                }
            }
        });
        tabAdapter.setOnItemClickListener(new TabAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position, int rowP) {
                lastFocusedPositions.put(rowP, position);
                Toast.makeText(context, "tabAdapter  ,row:" + rowP + ", position:" + position + ",p:" + position % gridRowNum, Toast.LENGTH_LONG).show();

                if (position > tabAdapter.getItemCount() - gridRowNum && preLoadTime>0) {
                    preLoadTime--;
                    int count =  tabAdapter.getItemCount();
                    int addCount = 30;
                    for (int i =count; i < count + addCount; i++)
                        list.add("preLoad_" + i);

                    tabAdapter.notifyItemRangeChanged(position,tabAdapter.getItemCount()-1);
                }
                try {
                    GridView.getLayoutManager().findViewByPosition(position).requestFocus();
                } catch (NullPointerException e) {
                    Log.d(TAG,""+Utils.FormatStackTrace(e));
                }
            }
        });
        tabAdapter.setOnItemFocusChangeListener(new TabAdapter.ItemFocusChange() {
            @Override
            public void onFocusChange(View view, boolean hasFocus, int position, int rowP) {
                GridViewPos = position;
                if(hasFocus) {
                    if (rowP != rowPosold) {
                        rowPosold = rowP;
                        int pos = lastFocusedPositions.get(rowP, 0);
                        View v = contentLyout.getChildAt(getRowItem((rowP)));
                        if (v instanceof HorizontalGridView) {
                            HorizontalGridView horizontalGridView = (HorizontalGridView) v;
                            Log.e(TAG, "rowP:" + rowP + ",pos:" + pos);
                            if (pos < horizontalGridView.getAdapter().getItemCount()) {
                                try {
                                    horizontalGridView.getLayoutManager().findViewByPosition(pos).requestFocus();
                                } catch (NullPointerException e) {

                                }
                            }
                            Toast.makeText(context, "tabAdapter has focus:" + hasFocus + " ,row:" + rowP + ", position:" + position + ",pos:" + pos + ",p:" + position % gridRowNum, Toast.LENGTH_LONG).show();

                        }
                    } else {
                        lastFocusedPositions.put(rowP, position);
                        Toast.makeText(context, "tabAdapter has focus:" + hasFocus + " ,row:" + rowP + ", position:" + position + ",p:" + position % gridRowNum, Toast.LENGTH_LONG).show();
                        if (position > tabAdapter.getItemCount() - gridRowNum && preLoadTime>0) {
                            preLoadTime--;
                            int count =  tabAdapter.getItemCount();
                            int addCount = 30;
                            for (int i =count; i < count + addCount; i++)
                                list.add("preLoad_" + i);

                            tabAdapter.notifyItemRangeChanged(position,tabAdapter.getItemCount()-1);
                        }
                    }
                }
            }
        });
        GridView.setNextFocusRightId(GridView.getId());

        tabAdapter.setTabContent(list);
        GridView.setAdapter(tabAdapter);
        contentLyout.addView(GridView);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK  ){
            if(GridView.hasFocus()){
                int rowP = 1;
                View view = contentLyout.getChildAt(rowP);
                if(view instanceof HorizontalGridView){
                    HorizontalGridView horizontalGridView = (HorizontalGridView)view;
                    int pos= lastFocusedPositions.get(rowP,0);
                    Log.e(TAG,"rowP:"+rowP+",pos:"+pos);
                    if(pos<horizontalGridView.getAdapter().getItemCount()) {
                        try {
                            horizontalGridView.getLayoutManager().findViewByPosition(pos).requestFocus();
                        }catch (NullPointerException e){

                        }
                    }
                    rowPosold=0;
                    return true;
                }

                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean isScrolling(HorizontalGridView view) {
        if (view == null) {
            return false;
        }
        return view.getScrollState() != HorizontalGridView.SCROLL_STATE_IDLE;
    }
}
