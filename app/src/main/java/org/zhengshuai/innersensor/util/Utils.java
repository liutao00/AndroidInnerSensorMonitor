package org.zhengshuai.innersensor.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by zhengshuai on 12/27/15.
 */
public class Utils {
    public static String getCurrentTimeFormat(String timeFormat) {
        String time = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(timeFormat);
        Calendar calendar = Calendar.getInstance();
        time = dateFormat.format(calendar.getTime());
        return time;

    }

    public static String getTextFromStream(InputStream is) {
        byte[] bytes = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            while ((len = is.read(bytes)) != -1){
                bos.write(bytes, 0, len);
            }
            String text = new String(bos.toByteArray());
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
