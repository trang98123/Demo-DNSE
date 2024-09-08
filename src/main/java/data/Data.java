package data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Data {
    public String generateScreenshotName(String scenario_name) {
        DateFormat df = new SimpleDateFormat("ddMMyy.HHmmss.SSS_");
        Date dateobj = new Date();
        return df.format(dateobj) + scenario_name + ".jpg";
    }
}
