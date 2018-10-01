package projects.mostafagad.task.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class Arabic_EditText extends EditText {

    public Arabic_EditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "hacen_saudi.ttf"));
    }
}
