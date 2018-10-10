package projects.mostafagad.task_Room.helpers;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.os.Build;

import java.util.Locale;


public class Localization {

    Locale myLocale;


    @SuppressWarnings("deprecation")
    public void setSystemLocaleLegacy(Configuration config, Locale locale) {
        config.locale = locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public Locale getSystemLocale(Configuration config) {
        return config.getLocales().get(0);
    }

    @TargetApi(Build.VERSION_CODES.N)
    public void setSystemLocale(Configuration config, Locale locale) {
        config.setLocale(locale);
    }

}