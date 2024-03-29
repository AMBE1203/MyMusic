package com.ambe.myappplaymusic.transition;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.ViewGroup;
import com.ambe.myappplaymusic.R;

public class PlayButtonTransition extends Transition {

    public static final int MODE_PLAY = 0;
    public static final int MODE_PAUSE = 1;

    private static final String PROPNAME_DRAWABLE = PlayButtonTransition.class.getName() + ":drawable";
    private static final String[] sTransitionProperties = {PROPNAME_DRAWABLE};

    private final int mMode;

    public PlayButtonTransition(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PlayButton);
        int mode = a.getInt(R.styleable.PlayButton_mode, MODE_PLAY);
        a.recycle();
        mMode = mode;
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        // Add fake value to force calling of createAnimator method
        captureValues(transitionValues, "start");
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        // Add fake value to force calling of createAnimator method
        captureValues(transitionValues, "end");
    }

    private void captureValues(TransitionValues transitionValues, Object value) {
        if (transitionValues.view instanceof FloatingActionButton) {
            transitionValues.values.put(PROPNAME_DRAWABLE, value);
        }
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {

        if (endValues == null || !(endValues.view instanceof FloatingActionButton)) {
            return null;
        }

        FloatingActionButton fabView = (FloatingActionButton) endValues.view;
        Context context = fabView.getContext();
        AnimatedVectorDrawable drawable;
        if (mMode == MODE_PLAY) {
            drawable = (AnimatedVectorDrawable) context.getDrawable(R.drawable.ic_play_animatable);
        } else {
            drawable = (AnimatedVectorDrawable) context.getDrawable(R.drawable.ic_pause_animatable);
        }
        fabView.setImageDrawable(drawable);

        return new AnimatedVectorDrawableWrapper(drawable);
    }

}
