package com.feveral.seniorhigh;

import androidx.fragment.app.Fragment;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

/**
 * Created by feveral on 2017/8/12.
 */

public abstract class BaseFragment extends Fragment {

    private Animation.AnimationListener AnimationListener;

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation animation;
        if (enter) {
            animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
            animation.setAnimationListener(AnimationListener);
        } else {
            animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);
        }
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(animation);
        return animationSet;
    }

    public void setAnimationListener(Animation.AnimationListener listener) {
        AnimationListener = listener;
    }

    public abstract int getTitleColorId();

    public abstract int getTitleStringId();

}
