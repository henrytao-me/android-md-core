package me.henrytao.mdcore.widgets;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import me.henrytao.mdcore.core.MdCompat;
import me.henrytao.mdcore.widgets.arcanimator.ArcAnimator;
import me.henrytao.mdcore.widgets.arcanimator.Side;

/**
 * Created by henrytao on 8/15/16.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FabSheetWindow {

  private final static AccelerateInterpolator ACCELERATE = new AccelerateInterpolator();

  private final static AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();

  private final static DecelerateInterpolator DECELERATE = new DecelerateInterpolator();

  private final int mCurrentX;

  private final int mCurrentY;

  private final int mDegree;

  private final View mSheet;

  private final int mTargetX;

  private final int mTargetY;

  private FrameLayout mContainer;

  private Context mContext;

  private FloatingActionButton mFab;

  private ViewGroup mRoot;

  public FabSheetWindow(FloatingActionButton fab, View sheet) {
    mFab = fab;
    mSheet = sheet;
    mRoot = (ViewGroup) fab.getRootView();
    mContext = fab.getContext();

    int diff = MdCompat.dpToPx(16);
    mCurrentX = (int) ViewCompat.getX(mFab);
    mCurrentY = (int) ViewCompat.getY(mFab);
    mTargetX = mCurrentX - diff * 2;
    mTargetY = mCurrentY - diff;
    mDegree = 15;
  }

  public void dismiss() {
    if (mContainer != null) {
      mRoot.removeView(mContainer);
    }
    mContext = null;
    mFab = null;
    mRoot = null;
  }

  public void show() {
    //mContainer = new FrameLayout(mContext);
    //mContainer.setLayoutParams(new FrameLayout.LayoutParams(
    //    ViewGroup.LayoutParams.MATCH_PARENT,
    //    ViewGroup.LayoutParams.MATCH_PARENT));
    //mContainer.setBackgroundColor(Color.parseColor("#4C000000"));
    //mRoot.addView(mContainer);
    //int cx = mFab.getWidth() / 2;
    //int cy = mFab.getHeight() / 2;
    //float initalRadius = (float) Math.hypot(cx, cy);
    //Animator anim = ViewAnimationUtils.createCircularReveal(mFab, cx, cy, initalRadius, 0);
    //anim.addListener(new AnimatorListenerAdapter() {
    //  @Override
    //  public void onAnimationEnd(Animator animation) {
    //    super.onAnimationEnd(animation);
    //    mFab.setVisibility(View.INVISIBLE);
    //  }
    //});
    //anim.start();

    //Animation fabAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fsw_fab_animation);
    //mFab.startAnimation(fabAnimation);

    //if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
    //  ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
    //  animator.setDuration(2000);
    //  animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
    //    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    //    @Override
    //    public void onAnimationUpdate(ValueAnimator animation) {
    //      float value = (Float) animation.getAnimatedValue();
    //      mFab.setTranslationX(-(float) (200 * Math.sin(value * Math.PI)));
    //      mFab.setTranslationY(-(float) (200 * Math.cos(value * Math.PI)));
    //    }
    //  });
    //  animator.addListener(new AnimatorListenerAdapter() {
    //
    //  });
    //  animator.start();
    //}

    //FrameLayout view = new FrameLayout(mContext);
    //view.setLayoutParams(new FrameLayout.LayoutParams(
    //    ViewGroup.LayoutParams.MATCH_PARENT,
    //    ViewGroup.LayoutParams.MATCH_PARENT));
    //view.setBackgroundColor(Color.parseColor("#4C000000"));
    //view.setVisibility(View.INVISIBLE);
    //mRoot.addView(view);
    //int cx = mRoot.getWidth() / 2;
    //int cy = mRoot.getHeight() / 2;
    //float finalRadius = (float) Math.hypot(cx, cy);
    //Animator animator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
    //view.setVisibility(View.VISIBLE);
    //animator.start();

    //CircularRevealFrameLayout view = new CircularRevealFrameLayout(mContext);
    //view.setLayoutParams(new FrameLayout.LayoutParams(
    //    ViewGroup.LayoutParams.MATCH_PARENT,
    //    ViewGroup.LayoutParams.MATCH_PARENT));
    //view.setBackgroundColor(Color.parseColor("#4C000000"));
    ////view.setVisibility(View.INVISIBLE);
    //mRoot.addView(view);
    //int cx = mRoot.getWidth() / 2;
    //int cy = mRoot.getHeight() / 2;
    //float finalRadius = (float) Math.hypot(cx, cy);
    ////Animator animator = createCheckoutRevealAnimator(view, cx, cy + 300, 0, finalRadius + 300);
    ////view.setVisibility(View.VISIBLE);
    ////animator.start();
    //Animator animator = MdCompat.createCircularReveal(view, cx, cy + 300, 0, finalRadius + 300);
    //animator.start();

    createFabShowAnimation().start();
    createSheetShowAnimation().start();
  }

  protected void onCreateView() {

  }

  protected void onDestroyView() {

  }

  private Animator createFabShowAnimation() {
    ValueAnimator animator = ArcAnimator
        .create(mFab, mTargetX, mTargetY, mDegree, Side.LEFT)
        .setDuration(200);
    animator.setInterpolator(ACCELERATE);
    return animator;
  }

  private Animator createSheetShowAnimation() {
    CircularRevealFrameLayout view = new CircularRevealFrameLayout(mContext);
    view.setLayoutParams(new FrameLayout.LayoutParams(MdCompat.dpToPx(200), MdCompat.dpToPx(400)));
    view.setBackgroundColor(Color.parseColor("#4C000000"));
    mRoot.addView(view);
    int dx = view.getMeasuredWidth() / 2;
    int dy = view.getMeasuredHeight() / 2;
    float radius = (float) Math.hypot(dx, dy);
    Animator animator = MdCompat.createCircularReveal(view, dx, dy, 0, radius);
    animator.setDuration(500);
    animator.setInterpolator(ACCELERATE_DECELERATE);
    return animator;
  }
}
