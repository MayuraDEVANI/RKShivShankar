package com.shivshakti.customcontrols;

import android.animation.Animator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.shivshakti.R;


public class DialogRKLoadingView extends DialogFragment {

    public DialogRKLoadingView() {
    }

    //    Animation operatingAnim;
    Dialog mDialog;
    LottieAnimationView animationView2;
    LottieAnimationView animationView;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (mDialog == null) {
            try {
                mDialog = new Dialog(getActivity(), R.style.cat_dialog);
                mDialog.setContentView(R.layout.dialog_custom_progress_rk);
                mDialog.setCanceledOnTouchOutside(true);
                mDialog.getWindow().setGravity(Gravity.CENTER);
//                operatingAnim = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//                operatingAnim.setRepeatCount(Animation.INFINITE);
//                operatingAnim.setDuration(2000);
//
//                LinearInterpolator lin = new LinearInterpolator();
//                operatingAnim.setInterpolator(lin);

                View view = mDialog.getWindow().getDecorView();
                animationView = (LottieAnimationView) view.findViewById(R.id.animation_view);
                animationView2 = (LottieAnimationView) view.findViewById(R.id.animation_view2);
                startAnim();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mDialog;
    }

    private void startAnim() {
        animationView.setProgress(0f);
        animationView.playAnimation();
        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animationView2.setProgress(0f);
                animationView2.playAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animationView2.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animationView.setProgress(0f);
                animationView.playAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (animationView != null)
                startAnim();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        try {
            finishAnim();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void finishAnim() {
        try {
            animationView.cancelAnimation();
            animationView2.cancelAnimation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        try {
            finishAnim();
            mDialog = null;
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
