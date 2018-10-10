package projects.mostafagad.task_Room.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class English_EditText extends android.support.v7.widget.AppCompatEditText {

    public English_EditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/arabic/kufi.ttf"));
    }
}
