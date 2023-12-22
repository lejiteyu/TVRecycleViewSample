package lyon.browser.tv_recyclerview_sample;

import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowHeaderPresenter;
import androidx.leanback.widget.RowPresenter;

public class GridRowPresenter extends ListRowPresenter {
    String TAG = GridRowPresenter.class.getSimpleName();
    public GridRowPresenter() {
        super();
        setHeaderPresenter(new CustomRowHeaderPresenter());
    }

    @Override
    protected void onBindRowViewHolder(RowPresenter.ViewHolder holder, Object item) {
        super.onBindRowViewHolder(holder, item);
    }

    public class CustomRowHeaderPresenter extends RowHeaderPresenter {
        @Override
        public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
            Presenter.ViewHolder viewHolder = super.onCreateViewHolder(parent);
//            RowHeaderView rowHeaderView = (RowHeaderView) viewHolder.view;
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
            super.onBindViewHolder(viewHolder,item);
            HeaderItem headerItem = item == null ? null : ((Row) item).getHeaderItem();
            Log.d(TAG,"20190325 headerItem:"+headerItem.getName());
            RowHeaderPresenter.ViewHolder vh = (RowHeaderPresenter.ViewHolder) viewHolder;
            TextView title = vh.view.findViewById(R.id.row_header);
            title.setTextSize(Utils.dpToPx(viewHolder.view.getContext(), 30));
            title.setTextColor(viewHolder.view.getContext().getResources().getColor(R.color.white));
            title.setGravity(Gravity.CENTER |Gravity.LEFT);
            if(!TextUtils.isEmpty(headerItem.getName())) {
                if(headerItem.getName().contains("<font")){
                    title.setText(Html.fromHtml(headerItem.getName()));
                }else
                    title.setText(headerItem.getName());
            }else{
                title.setVisibility(View.GONE);
            }
        }
    }
}