package projects.mostafagad.task_Room.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class English_NumberTextView extends android.support.v7.widget.AppCompatTextView {

    public English_NumberTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "carre.ttf"));
    }
}
