package com.shivshakti.customcontrols;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;

import com.shivshakti.R;


public class DialogBallLoadingView extends DialogFragment {

    public DialogBallLoadingView() {
    }

//    Animation operatingAnim;
    Dialog mDialog;
    JumpBall mJumpBall;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (mDialog == null) {
            try {
                mDialog = new Dialog(getActivity(), R.style.cat_dialog);
                mDialog.setContentView(R.layout.dialog_custom_progress_ball);
                mDialog.setCanceledOnTouchOutside(true);
                mDialog.getWindow().setGravity(Gravity.CENTER);
//                operatingAnim = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//                operatingAnim.setRepeatCount(Animation.INFINITE);
//                operatingAnim.setDuration(2000);
//
//                LinearInterpolator lin = new LinearInterpolator();
//                operatingAnim.setInterpolator(lin);

                View view = mDialog.getWindow().getDecorView();

                mJumpBall = (JumpBall) view.findViewById(R.id.jump_ball);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mDialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (mJumpBall != null)
                mJumpBall.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        try {
            mJumpBall.pause();
//            operatingAnim.reset();
//            mJumpBall.clearAnimation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void finishAnim() {
        try {
            mJumpBall.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        try {
            mJumpBall.finish();
            mDialog = null;
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
