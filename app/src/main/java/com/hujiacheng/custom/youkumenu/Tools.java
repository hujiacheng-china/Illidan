package com.hujiacheng.custom.youkumenu;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by HuJiaCheng on 2017/3/13.
 */
public class Tools {
    public static void hideView(View view,int duration,int startOffset) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotation",0,180);
        animator.setDuration(duration);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setStartDelay(startOffset);
        animator.start();

        view.setPivotX(view.getWidth()/2);
        view.setPivotY(view.getHeight());
    }
    public static void showView(View view,int duration,int startOffset) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotation",180,360);
        animator.setDuration(duration);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setStartDelay(startOffset);
        animator.start();

        view.setPivotX(view.getWidth()/2);
        view.setPivotY(view.getHeight());
    }

    public static void hideView(View view,int startOffset) {
        hideView(view,400,startOffset);
//        RotateAnimation ra = new RotateAnimation(0,180,view.getWidth()/2,view.getHeight());
//        ra.setDuration(400);
//        ra.setInterpolator(new DecelerateInterpolator());
//        ra.setFillAfter(true);
//        ra.setStartOffset(startOffset);
//        view.startAnimation(ra);

//        AnimatorSet set = new AnimatorSet();
//        set.playTogether(animator,animator);
//        set.start();
    }

    public static void showView(View view,int startOffset) {
        showView(view,400,startOffset);
//        RotateAnimation ra = new RotateAnimation(180,360,view.getWidth()/2,view.getHeight());
//        ra.setDuration(400);
//        ra.setInterpolator(new DecelerateInterpolator());
//        ra.setFillAfter(true);
//        ra.setStartOffset(startOffset);
//        view.startAnimation(ra);
    }
}
