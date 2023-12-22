package lyon.browser.tv_recyclerview_sample;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.leanback.widget.Presenter;

import java.util.ArrayList;

public class CardPresenter extends Presenter {

    String TAG = CardPresenter.class.getName();
    static Context context;
    ArrayList<String> arrayList;
    boolean hasFocus=false;
    private int sSelectedBackgroundColor;
    private int sDefaultBackgroundColor;
    CardView cardView;

    public CardPresenter(Context context, ArrayList<String> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }
    public CardPresenter(Context context ){
        this.context=context;
        this.arrayList=null;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        context = parent.getContext();
        sDefaultBackgroundColor = parent.getResources().getColor(android.R.color.transparent);
        sSelectedBackgroundColor = parent.getResources().getColor(R.color.blue_light);
        cardView = new CardView(context)
        {
            @Override
            public void setSelected(boolean selected) {
                Log.d(TAG,"onItemSelected SearchKey:"+selected);
                super.setSelected(selected);
                updateCardBackgroundColor(this, selected);
                hasFocus=selected;
            }

        };

        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
//        cardView.setBackgroundColor(sDefaultBackgroundColor);


        return new ViewHolder(cardView);
    }


    private void updateCardBackgroundColor(CardView view, boolean selected) {
        int color = selected ? sSelectedBackgroundColor : sDefaultBackgroundColor;
        // Both background colors should be set because the view's background is temporarily visible
        // during animations.
        TextView t = (TextView)view.findViewById(R.id.textView);
        Log.d(TAG,"updateCardBackgroundColor:"+t.getText());

    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final Object item) {
        View rootView = viewHolder.view;
        rootView.setBackgroundColor(sDefaultBackgroundColor);
        TextView t = (TextView)rootView.findViewById(R.id.textView);
        ImageView icon = (ImageView)rootView.findViewById(R.id.icon) ;
        if(item instanceof TVSearchFragment.DummyCard)
            t.setText(((TVSearchFragment.DummyCard) item).title+"");
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

}
