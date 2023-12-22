package lyon.browser.tv_recyclerview_sample;

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
}
