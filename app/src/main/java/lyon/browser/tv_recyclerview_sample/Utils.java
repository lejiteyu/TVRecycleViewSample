package lyon.browser.tv_recyclerview_sample;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class Utils {
    static String TAG = Utils.class.getName();

    public static String FormatStackTrace(Throwable throwable) {
        if(throwable==null) return "";
        String rtn = throwable.getStackTrace().toString();
        try {
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            throwable.printStackTrace(printWriter);
            printWriter.flush();
            writer.flush();
            rtn = writer.toString();
            printWriter.close();
            writer.close();
        } catch (IOException e) {
                System.out.println(TAG + ": an error FormatStackTrace..." + Utils.FormatStackTrace(e));
        } catch (Exception ex) {
                System.out.println(TAG + ": an error FormatStackTrace..." + Utils.FormatStackTrace(ex));
        }
        return rtn;
    }

    public static int dpToPx(Context context, int dp){
        if(context == null) {
            return -1;
        }

        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    //放大
    public static void zoomIn(Context mContext, View v){
        zoomIn(mContext,v,500);
    }

    public static void zoomIn(Context mContext,View v , int time) {
        Animation scaleSmallAnimation = null;
        Animation scaleBigAnimation;
        if (scaleSmallAnimation == null) {
            scaleSmallAnimation = AnimationUtils.loadAnimation(mContext, R.anim.anim_scale_small);
            scaleSmallAnimation.setDuration(time);
        }
        v.startAnimation(scaleSmallAnimation);
    }

    //縮小
    public static void zoomOut(Context mContext,View v){
        zoomOut(mContext,v,500);
    }

    public static void zoomOut(Context mContext,View v, int time) {
        Animation scaleBigAnimation = null;
        if (scaleBigAnimation == null) {
            scaleBigAnimation = AnimationUtils.loadAnimation(mContext, R.anim.anim_scale_big);
            scaleBigAnimation.setDuration(time);
        }
        v.startAnimation(scaleBigAnimation);
    }

}
