package projects.mostafagad.task.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class Arabic_Button extends android.support.v7.widget.AppCompatButton {

    public Arabic_Button(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "hacen_saudi.ttf"));
    }
}
