package lyon.browser.tv_recyclerview_sample.cardView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.leanback.widget.BaseCardView;

import lyon.browser.tv_recyclerview_sample.R;

public class CardView extends BaseCardView {
    String TAG = CardView.class.getSimpleName();
    TextView textView;
    Context context;


    public CardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(getStyledContext(context, attrs, defStyleAttr), attrs, defStyleAttr);
        buildLoadingCardView(getImageCardViewStyle(context, attrs, defStyleAttr));
        this.context=context;
    }

    private void buildLoadingCardView(int styleResId) {
        setFocusable(false);
        setFocusableInTouchMode(false);
        setCardType(CARD_TYPE_MAIN_ONLY);
        setBackgroundResource(R.drawable.btn_circle_blue);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.cardview_text_icon, this);

//        RelativeLayout bg = (RelativeLayout)view.findViewById(R.id.bg);
//        bg.setBackground(context.getResources().getDrawable(R.drawable.btn_circle_blue));

        textView = (TextView)view.findViewById(R.id.textView);

    }

    private static Context getStyledContext(Context context, AttributeSet attrs, int defStyleAttr) {
        int style = getImageCardViewStyle(context, attrs, defStyleAttr);
        return new ContextThemeWrapper(context, style);
    }

    private static int getImageCardViewStyle(Context context, AttributeSet attrs, int defStyleAttr) {
        // Read style attribute defined in XML layout.
        int style = null == attrs ? 0 : attrs.getStyleAttribute();
        if (0 == style) {
            // Not found? Read global ImageCardView style from Theme attribute.
            TypedArray styledAttrs =
                    context.obtainStyledAttributes(
                            R.styleable.LeanbackTheme);
            style = styledAttrs.getResourceId(
                    R.styleable.LeanbackTheme_imageCardViewStyle, 0);
            styledAttrs.recycle();
        }
        return style;
    }

    public CardView(Context context) {
        this(context, null);
    }

    public CardView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.imageCardViewStyle);
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        Log.d(TAG,"setSelected:"+selected);
    }

    @Override
    public void setFocusable(boolean focusable) {
        super.setFocusable(focusable);
        Log.d(TAG,"setFocusable:"+focusable);
    }
}
