package lyon.browser.tv_recyclerview_sample.Activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.leanback.widget.ArrayObjectAdapter;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lb_search_fragment);
        setContentView(R.layout.tv_horizontal_grid_view);
        context = this;
        searchBar = findViewById(R.id.lb_search_bar);
        searchOrbView = (SearchOrbView)searchBar.findViewById(R.id.lb_search_bar_speech_orb);
        searchOrbView.setFocusable(true);

        contentLyout = (LinearLayout)findViewById(R.id.content_layout);
        Item item =new Item("item",30);
        setTabRow("first title",item.getTabContent());
        Item item2 =new Item("item2",30);
        setTabRow("2 title",item2.getTabContent());
        Item item3=new Item("item3",300);
        setGrieRow("grid",item3.getTabContent());

        searchOrbView.requestFocus();
    }


    public void setTabRow(String title ,List<String> list){
        TextView titleView = new TextView(context);
        titleView.setTextColor(Color.WHITE);
        titleView.setTextSize(Utils.dpToPx(context, 32));
        titleView.setText(title);
        contentLyout.addView(titleView);
        HorizontalGridView horizontalGridView = new HorizontalGridView(context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        horizontalGridView.setLayoutManager(layoutManager);
        horizontalGridView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TabAdapter tabAdapter = new TabAdapter();

        tabAdapter.setTabContent(list);
        horizontalGridView.setAdapter(tabAdapter);
        contentLyout.addView(horizontalGridView);

    }
    HorizontalGridView GridView;
    public void setGrieRow(String title ,List<String> list){
        TextView titleView = new TextView(context);
        titleView.setTextColor(Color.WHITE);
        titleView.setTextSize(Utils.dpToPx(context, 32));
        titleView.setText(title);
        contentLyout.addView(titleView);
        GridView = new HorizontalGridView(context);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,7);
        GridView.setLayoutManager(gridLayoutManager);
        GridView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TabAdapter tabAdapter = new TabAdapter();

        tabAdapter.setTabContent(list);
        GridView.setAdapter(tabAdapter);
        contentLyout.addView(GridView);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK  ){
            if(GridView.hasFocus()){
                searchOrbView.requestFocus();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
