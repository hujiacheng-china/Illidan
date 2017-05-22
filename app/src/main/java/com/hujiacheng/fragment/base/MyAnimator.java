package com.hujiacheng.fragment.base;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;



/**
 * Created by HuJiaCheng on 2017/3/21.
 */

public class MyAnimator {
    public static int slideDuration = 200;
    public static final String TAG = MyAnimator.class.getSimpleName();

    public static void slideUp(View slideView, View showView) {
        slideUp(slideView, showView, slideDuration);
    }

    public static void slideUp(final View slideView, final View showView, final int duration) {
        if (showView.getVisibility() == View.GONE) {
            showView.setVisibility(View.VISIBLE);
        }
        showView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                showView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Log.e(TAG, "slideView height = " + slideView.getHeight() + " showView getMeasuredHeight = " + showView.getMeasuredHeight());
                ObjectAnimator animator = ObjectAnimator.ofFloat(slideView, "translationY", showView.getHeight(), 0);
                animator.setDuration(duration);
                animator.setInterpolator(new DecelerateInterpolator());
                animator.start();
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
//                    showView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            }
        });
    }

    public static void slideDown(View slideView, View hideView) {
        slideDown(slideView, hideView, slideDuration);
    }

    public static void slideDown(final View slideView, final View hideView, int duration) {
        if (hideView.getVisibility() == View.VISIBLE) {
            Log.e(TAG, "slideView height = " + slideView.getHeight() + " hideView Height = " + hideView.getHeight());
            ObjectAnimator animator = ObjectAnimator.ofFloat(slideView, "translationY", 0, hideView.getHeight());
            animator.setDuration(duration);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.start();
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    hideView.setVisibility(View.GONE);
                    ObjectAnimator head = ObjectAnimator.ofFloat(slideView, "translationY", hideView.getHeight(), 0 );
                    head.setDuration(0);
                    head.start();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }

    }
}
