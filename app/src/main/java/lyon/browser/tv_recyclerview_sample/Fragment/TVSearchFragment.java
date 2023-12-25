package lyon.browser.tv_recyclerview_sample.Fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.leanback.app.SearchSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ClassPresenterSelector;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.SearchOrbView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import lyon.browser.tv_recyclerview_sample.R;
import lyon.browser.tv_recyclerview_sample.Utils;
import lyon.browser.tv_recyclerview_sample.cardView.CardPresenter;
import lyon.browser.tv_recyclerview_sample.cardView.GridRow;
import lyon.browser.tv_recyclerview_sample.cardView.GridRowPresenter;

public class TVSearchFragment extends SearchSupportFragment implements SearchSupportFragment.SearchResultProvider{
    private final String TAG = TVSearchFragment.class.getSimpleName();
    ArrayObjectAdapter mRowsAdapter;
    public String keyword="";
    HorizontalGridView horizontalGridView;
    View view;
    HorizontalGridView listRowView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 使用標籤設置行
        ClassPresenterSelector rowPresenterSelector = new ClassPresenterSelector();

        ListRowPresenter listRowPresenter =new ListRowPresenter(){
            @Override
            protected void onBindRowViewHolder(RowPresenter.ViewHolder holder, Object item) {
                super.onBindRowViewHolder(holder, item);
                ViewHolder vh = (ViewHolder) holder;
                listRowView = vh.getGridView();
            }
        };
        GridRowPresenter gridRowPresenter = new GridRowPresenter(){
            @Override
            protected void onBindRowViewHolder(RowPresenter.ViewHolder holder, Object item) {
                super.onBindRowViewHolder(holder, item);
                ViewHolder vh = (ViewHolder) holder;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), rowCount);
                gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//content 置中
                vh.getGridView().setLayoutManager(gridLayoutManager);
                horizontalGridView = vh.getGridView();
                vh.getGridView().setGravity(Gravity.CENTER_HORIZONTAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                layoutParams.leftMargin = Utils.dpToPx(getContext(),20);
                // 添加間隔
                vh.getGridView().addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);


                        outRect.right = 5;
                    }
                });
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.lb_search_fragment, container, false);
        View view = super.onCreateView(inflater, container, savedInstanceState);
        searchOrbView = (SearchOrbView)view.findViewById(R.id.lb_search_bar_speech_orb);
        searchOrbView.requestFocus();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction()==KeyEvent.ACTION_DOWN){
            if(keyCode == KeyEvent.KEYCODE_DPAD_UP){
                if (horizontalGridView !=null && horizontalGridView.isFocused()
                ) {
                    if(listRowView!=null){
                        listRowView.requestFocus();
                        return true;
                    }
                }else if(listRowView!=null && listRowView.isFocused()){
                    if(searchOrbView!=null){
                        searchOrbView.requestFocus();
                        return true;
                    }
                }
            }else if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN){
                if (searchOrbView!=null && searchOrbView.isFocused()
                ) {
                    if(listRowView!=null && listRowView!=null){
                        listRowView.requestFocus();
                        return true;
                    }
                }else if(listRowView!=null && listRowView.isFocused()){
                    if(horizontalGridView!=null){
                        horizontalGridView.requestFocus();
                        return true;
                    }
                }
            }
        }



        return false;
    }


    SearchOrbView searchOrbView;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchOrbView = (SearchOrbView)view.findViewById(R.id.lb_search_bar_speech_orb);
        searchOrbView.setFocusable(true);
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
    public static class DummyCard {
        public String title;

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
                Grid2Adapter =  new ArrayObjectAdapter(new CardPresenter(getContext()));

                for (int i = 0; i < 25 ; i++) {
                    Grid2Adapter.add(new DummyCard("Grid項目3_ " + i));
                }
                changeGridTab2(changePos,Grid2Adapter);
            }
        };
    }

    private OnItemViewSelectedListener selectedListener() {
        return new OnItemViewSelectedListener() {
            @Override
            public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                if (item != null)
                    Log.d(TAG, "onItemSelected SearchKey:" + item.toString());

                if(row instanceof GridRow){
                    GridRow gridRow = (GridRow) row;

                }
            }
        };
    }

    int rowCount = 6;
    int changePos =0;
    ArrayObjectAdapter Grid2Adapter =  new ArrayObjectAdapter(new CardPresenter(getContext()));
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
            for(int j=0;j<40;j++) {

                rowAdapter.add(new DummyCard("Grid項目1_ " + j));
            }
            GridRow gridRow = new GridRow(tab, rowAdapter);

            mRowsAdapter.add(gridRow);
            mRowsAdapter.notifyArrayItemRangeChanged(0,rowAdapter.size()-1);

            int ROW_INDEX =0;

            Grid2Adapter =  new ArrayObjectAdapter(new CardPresenter(getContext()));
            changePos = mRowsAdapter.size();
            for (int i = 0; i < 20 ; i++) {
                Grid2Adapter.add(new DummyCard("Grid項目2_ " + i));
            }
            changeGridTab2(changePos,Grid2Adapter);
        }
    }

    private void changeGridTab2(int ROW_INDEX,ArrayObjectAdapter Grid2Adapter){
        ArrayObjectAdapter Adapter =  new ArrayObjectAdapter(new CardPresenter(getContext()));
        for (int i = 0; i < Grid2Adapter.size() ; i++) {
            ROW_INDEX = mRowsAdapter.size();
            Adapter.add(Grid2Adapter.get(i));
            //rows 換行機制
            if (i % rowCount == rowCount - 1) {
                if (i < rowCount) {
                    HeaderItem tab2 = new HeaderItem(ROW_INDEX, "GridTab2_" + 2);
                    GridRow gridRow2 = new GridRow(tab2, Adapter);
                    if(ROW_INDEX>changePos+(i / rowCount)){
                        mRowsAdapter.replace(changePos+(i / rowCount),gridRow2);
                    }else
                        mRowsAdapter.add(gridRow2);
                } else {
                    HeaderItem header = new HeaderItem(ROW_INDEX, "");
                    GridRow gridRow2 = new GridRow(header, Adapter);
                    if(ROW_INDEX>changePos+(i / rowCount)){
                        mRowsAdapter.replace(changePos+(i / rowCount),gridRow2);
                    }else
                    mRowsAdapter.add(gridRow2);
                }
                Adapter =  new ArrayObjectAdapter(new CardPresenter(getContext()));
            }
        }
        if(Adapter.size()>0) {
            if (ROW_INDEX >= rowCount) {
                HeaderItem header = new HeaderItem(ROW_INDEX, "");
                GridRow gridRow2 = new GridRow(header, Adapter);
                if(ROW_INDEX>changePos+changePos+(Grid2Adapter.size() / rowCount)){
                    mRowsAdapter.replace(changePos+changePos+(Grid2Adapter.size() / rowCount),gridRow2);
                }else
                mRowsAdapter.add(gridRow2);
            } else {
                HeaderItem tab2 = new HeaderItem(ROW_INDEX, "GridTab2_" + 2);
                GridRow gridRow2 = new GridRow(tab2, Adapter);
                if(ROW_INDEX>changePos+changePos+(Grid2Adapter.size() / rowCount)){
                    mRowsAdapter.replace(changePos+changePos+(Grid2Adapter.size() / rowCount),gridRow2);
                }else
                    mRowsAdapter.add(gridRow2);
            }
        }
        mRowsAdapter.notifyArrayItemRangeChanged(changePos,mRowsAdapter.size()-1);
    }
}
