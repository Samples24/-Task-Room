package projects.mostafagad.task_Room.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class Arabic_EditText extends android.support.v7.widget.AppCompatEditText {

    public Arabic_EditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "hacen_saudi.ttf"));
    }
}
