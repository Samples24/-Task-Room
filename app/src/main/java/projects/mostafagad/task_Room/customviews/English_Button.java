package projects.mostafagad.task_Room.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class English_Button extends android.support.v7.widget.AppCompatButton {

    public English_Button(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/english/Poppins-Regular.ttf"));
    }
}
