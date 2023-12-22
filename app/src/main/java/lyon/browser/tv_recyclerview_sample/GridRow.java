package lyon.browser.tv_recyclerview_sample;

import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ObjectAdapter;

public class GridRow extends ListRow {

    private static final String TAG = GridRow.class.getSimpleName();


    public GridRow(HeaderItem header, ObjectAdapter adapter) {
        super(header, adapter);
    }

    public GridRow(long id, HeaderItem header, ObjectAdapter adapter) {
        super(id, header, adapter);
    }

    public GridRow(ObjectAdapter adapter) {
        super(adapter);
    }



}
