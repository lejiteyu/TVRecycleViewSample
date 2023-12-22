package lyon.browser.tv_recyclerview_sample;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.leanback.app.SearchSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ClassPresenterSelector;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.VerticalGridPresenter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

public class TVSearchFragment extends SearchSupportFragment implements SearchSupportFragment.SearchResultProvider{
    private final String TAG = TVSearchFragment.class.getSimpleName();
    ArrayObjectAdapter mRowsAdapter;
    public String keyword="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // 使用標籤設置行

        ClassPresenterSelector rowPresenterSelector = new ClassPresenterSelector();
        ListRowPresenter gridRowPresenter = new ListRowPresenter(){
            @Override
            protected void onBindRowViewHolder(RowPresenter.ViewHolder holder, Object item) {
                super.onBindRowViewHolder(holder, item);
                ViewHolder vh = (ViewHolder) holder;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 6);
                gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//content 置中
                vh.getGridView().setLayoutManager(gridLayoutManager);
                vh.getGridView().setGravity(Gravity.CENTER_HORIZONTAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                layoutParams.leftMargin = Utils.dpToPx(getContext(),20);
                vh.getGridView().setLayoutParams(layoutParams);
            }
        };
        rowPresenterSelector.addClassPresenter(ListRow.class, new ListRowPresenter());
        rowPresenterSelector.addClassPresenter(GridRow.class, gridRowPresenter);
//        rowPresenterSelector.addClassPresenter(GridRow.class, new VerticalGridPresenter());
        mRowsAdapter = new ArrayObjectAdapter(rowPresenterSelector);
        setSearchResultProvider(this);//setAdapter
        setOnItemViewClickedListener(getDefaultItemClickedListener());
        setOnItemViewSelectedListener(selectedListener());

        getSearch();
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private class SearchRunnable implements Runnable {
        public void setSearchQuery(String query) {
            Log.d(TAG,"SearchRunnable query:"+query);
            keyword = query;
        }
        @Override
        public void run() {
            getSearch();
        }
    }

    // 用於演示目的的虛擬卡片類別
    static class DummyCard {
        String title;

        DummyCard(String title) {
            this.title = title;
        }
    }

    @Override
    public ObjectAdapter getResultsAdapter() {
        return mRowsAdapter;
    }

    @Override
    public boolean onQueryTextChange(String newQuery) {
        Log.d(TAG,"TVSearchFragment onQueryTextChange:"+newQuery);
        mRowsAdapter.clear();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG,"TVSearchFragment onQueryTextSubmit:"+query);
        mRowsAdapter.clear();
        return false;
    }

    private OnItemViewClickedListener getDefaultItemClickedListener() {
        return new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                Log.d(TAG, "getDefaultItemClickedListener:" + item);
            }
        };
    }

    private OnItemViewSelectedListener selectedListener() {
        return new OnItemViewSelectedListener() {
            @Override
            public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                if (item != null)
                    Log.d(TAG, "onItemSelected SearchKey:" + item.toString());
            }
        };
    }


    private void getSearch(){
        synchronized (keyword) {
            // 假設您有一個用於標籤的自定義 ObjectAdapter

            // 添加 "Tab_1" 到 "Tab_15" 的標籤
            for (int i = 1; i <= 2; i++) {
                HeaderItem tab = new HeaderItem(i, "Tab_" + i);
                // 在這裡添加每個標籤的內容，如果需要的話
                // 為演示目的添加一個測試卡片
                ArrayObjectAdapter rowAdapter = new ArrayObjectAdapter(new CardPresenter(getContext()));
                for(int j=0;j<20;j++) {

                    rowAdapter.add(new DummyCard("項目 " + j));
                }
                ListRow listRow = new ListRow(tab, rowAdapter);
                mRowsAdapter.add(listRow);
                mRowsAdapter.notifyArrayItemRangeChanged(0,rowAdapter.size()-1);
            }

            ArrayObjectAdapter rowAdapter =  new ArrayObjectAdapter(new CardPresenter(getContext()));
            HeaderItem tab = new HeaderItem(1, "GridTab_" + 1);
            for(int j=0;j<20;j++) {

                rowAdapter.add(new DummyCard("項目 " + j));
            }
            GridRow gridRow = new GridRow(tab, rowAdapter);

            mRowsAdapter.add(gridRow);
            mRowsAdapter.notifyArrayItemRangeChanged(0,rowAdapter.size()-1);
        }
    }
}
