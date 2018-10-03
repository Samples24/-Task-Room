package projects.mostafagad.task.helpers;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.widget.Toast;

public class Global {

    private Context context;
    private static final int SHORT_TOAST_DURATION = 2000;

    public static void makeLongToast(Context context, String text, long durationInMillis) {
        final Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        new CountDownTimer(Math.max(durationInMillis - SHORT_TOAST_DURATION, 1000), 1000) {
            @Override
            public void onFinish() {
                t.show();
            }

            @Override
            public void onTick(long millisUntilFinished) {
                t.show();
            }
        }.start();
    }


    public static void Count_timer(int to, int from) {
        new CountDownTimer(to, from) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

            }
        }.start();
    }


}