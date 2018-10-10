package projects.mostafagad.task_Room.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import me.biubiubiu.justifytext.library.JustifyTextView;

public class Arabic_TextViewJus extends JustifyTextView {

    public Arabic_TextViewJus(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/arabic/hacen_saudi.ttf"));
    }
}
