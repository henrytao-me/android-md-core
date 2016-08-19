package me.henrytao.mdcore.widgets;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import me.henrytao.mdcore.core.MdCompat;
import me.henrytao.mdcore.widgets.arcanimator.AlphaAnimator;
import me.henrytao.mdcore.widgets.arcanimator.ArcAnimator;
import me.henrytao.mdcore.widgets.arcanimator.Side;

/**
 * Created by henrytao on 8/15/16.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FabSheetWindow {

  private static final AccelerateInterpolator ACCELERATE = new AccelerateInterpolator();

  private static final AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();

  private static final DecelerateInterpolator DECELERATE = new DecelerateInterpolator();

  private static final int DEGREE = 45;

  private static final long DURATION = 200;

  private static final int FAB_SIZE = 48;

  public static FabSheetWindow create(FloatingActionButton fab, View sheet) {
    return new FabSheetWindow(fab, sheet);
  }

  private static void onAnimationEnd(Animator animation, @NonNull final OnAnimationEndListener onAnimationEndListener) {
    animation.addListener(new Animator.AnimatorListener() {
      @Override
      public void onAnimationCancel(Animator animation) {

      }

      @Override
      public void onAnimationEnd(Animator animation) {
        onAnimationEndListener.onAnimationEnd(animation);
      }

      @Override
      public void onAnimationRepeat(Animator animation) {

      }

      @Override
      public void onAnimationStart(Animator animation) {

      }
    });
  }

  private final FabInfo mFabInfo;

  private Context mContext;

  private int mDegree = DEGREE;

  private long mDuration = DURATION;

  private Animator mFabAnimation;

  private boolean mIsCreated;

  private Animator mOverlayAnimation;

  private Animator mSheetAnimation;

  private boolean mShowing;

  private ViewGroup vContent;

  private FloatingActionButton vFab;

  private ViewGroup vOverlay;

  private ViewGroup vRoot;

  private View vSheet;

  private CircularRevealFrameLayout vSheetContainer;

  protected FabSheetWindow(FloatingActionButton fab, View sheet) {
    mContext = fab.getContext().getApplicationContext();
    vFab = fab;
    vSheet = sheet;
    mFabInfo = new FabInfo(vFab);
  }

  public void destroy() {
    onDestroyView();
  }

  public void dismiss() {
    if (!mIsCreated) {
      return;
    }
    if (isAnimating()) {
      return;
    }

    mFabAnimation = createFabDimissAnimation().setDuration(getDuration());
    mSheetAnimation = createSheetDismissAnimation().setDuration(getDuration());
    mOverlayAnimation = createOverlayDismissAnimation().setDuration(getDuration());

    mFabAnimation.setStartDelay(getDuration());

    onAnimationEnd(mFabAnimation, new OnAnimationEndListener() {
      @Override
      public void onAnimationEnd(Animator animation) {
        mShowing = false;
      }
    });
    onAnimationEnd(mSheetAnimation, new OnAnimationEndListener() {
      @Override
      public void onAnimationEnd(Animator animation) {
        vSheetContainer.setVisibility(View.GONE);
      }
    });
    onAnimationEnd(mOverlayAnimation, new OnAnimationEndListener() {
      @Override
      public void onAnimationEnd(Animator animation) {
        vOverlay.setVisibility(View.GONE);
      }
    });

    mFabAnimation.start();
    mSheetAnimation.start();
    mOverlayAnimation.start();
  }

  public int getDegree() {
    return mDegree;
  }

  public void setDegree(int degree) {
    mDegree = degree;
  }

  public long getDuration() {
    return mDuration;
  }

  public void setDuration(long duration) {
    mDuration = duration;
  }

  public boolean isShowing() {
    return mShowing;
  }

  public void show() {
    if (!mIsCreated) {
      mIsCreated = true;
      onCreateView();
    }
    if (isAnimating()) {
      return;
    }

    mShowing = true;

    mFabAnimation = createFabShowAnimation().setDuration(getDuration());
    mSheetAnimation = createSheetShowAnimation().setDuration(getDuration());
    mOverlayAnimation = createOverlayShowAnimation().setDuration(getDuration());

    mSheetAnimation.setStartDelay(getDuration());
    mOverlayAnimation.setStartDelay(getDuration() / 2);

    mFabAnimation.start();
    mSheetAnimation.start();
    mOverlayAnimation.start();
  }

  protected void onCreateView() {
    vRoot = (ViewGroup) vFab.getRootView();

    vOverlay = new FrameLayout(mContext);
    vOverlay.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    vOverlay.setBackgroundColor(Color.parseColor("#4C000000"));
    vRoot.addView(vOverlay);

    vContent = new FrameLayout(mContext);
    vContent.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    vRoot.addView(vContent);

    vSheetContainer = new CircularRevealFrameLayout(mContext);
    vSheetContainer.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    vSheetContainer.addView(vSheet, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    vContent.addView(vSheetContainer);

    vOverlay.measure(0, 0);
    vContent.measure(0, 0);

    ViewCompat.setX(vSheetContainer, mFabInfo.bottomRight.x - vSheetContainer.getMeasuredWidth());
    ViewCompat.setY(vSheetContainer, mFabInfo.bottomRight.y - vSheetContainer.getMeasuredHeight());

    vOverlay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismiss();
      }
    });
  }

  protected void onDestroyView() {
    mContext = null;
    if (mFabAnimation != null) {
      mFabAnimation.end();
    }
    mFabAnimation = null;
    if (mOverlayAnimation != null) {
      mOverlayAnimation.end();
    }
    mOverlayAnimation = null;
    if (mSheetAnimation != null) {
      mSheetAnimation.end();
    }
    mSheetAnimation = null;
    vRoot.removeView(vOverlay);
    vRoot.removeView(vContent);
    vRoot = null;
    vOverlay = null;
    vContent = null;
    vSheetContainer = null;
    vSheet = null;
    vFab = null;
  }

  private Animator createFabDimissAnimation() {
    ValueAnimator animator = ArcAnimator.create(vFab, mFabInfo.relativeCenter.x, mFabInfo.relativeCenter.y, getDegree(), Side.LEFT);
    animator.setInterpolator(DECELERATE);
    return animator;
  }

  private Animator createFabShowAnimation() {
    FabInfo.Pointer target = getTargetRelativePointer();
    ValueAnimator animator = ArcAnimator.create(vFab, target.x, target.y, getDegree(), Side.LEFT);
    animator.setInterpolator(ACCELERATE);
    return animator;
  }

  private Animator createOverlayDismissAnimation() {
    Animator animator = AlphaAnimator.create(vOverlay, 1.0f, 0f);
    animator.setInterpolator(DECELERATE);
    return animator;
  }

  private Animator createOverlayShowAnimation() {
    Animator animator = AlphaAnimator.create(vOverlay, 0.0f, 1.0f);
    animator.setInterpolator(ACCELERATE);
    return animator;
  }

  private Animator createSheetDismissAnimation() {
    int dx = vSheetContainer.getMeasuredWidth() / 2;
    int dy = vSheetContainer.getMeasuredHeight() / 2;
    float radius = (float) Math.hypot(dx, dy);
    Animator animator = MdCompat.createCircularReveal(vSheetContainer, dx, dy, radius, mFabInfo.radius);
    animator.setInterpolator(ACCELERATE_DECELERATE);
    return animator;
  }

  private Animator createSheetShowAnimation() {
    int dx = vSheetContainer.getMeasuredWidth() / 2;
    int dy = vSheetContainer.getMeasuredHeight() / 2;
    float radius = (float) Math.hypot(dx, dy);
    Animator animator = MdCompat.createCircularReveal(vSheetContainer, dx, dy, mFabInfo.radius, radius);
    animator.setInterpolator(ACCELERATE_DECELERATE);
    return animator;
  }

  private FabInfo.Pointer getTargetAbsolutePointer() {
    FabInfo.Pointer distance = getTargetDistance();
    return new FabInfo.Pointer(mFabInfo.center.x - distance.x, mFabInfo.center.y - distance.y);
  }

  private FabInfo.Pointer getTargetDistance() {
    float sheetX = mFabInfo.bottomRight.x - vSheetContainer.getMeasuredWidth();
    float sheetY = mFabInfo.bottomRight.y - vSheetContainer.getMeasuredHeight();
    float targetX = sheetX + vSheetContainer.getMeasuredWidth() / 2;
    float targetY = sheetY + vSheetContainer.getMeasuredHeight() / 2;
    return new FabInfo.Pointer(mFabInfo.center.x - targetX, mFabInfo.center.y - targetY);
  }

  private FabInfo.Pointer getTargetRelativePointer() {
    FabInfo.Pointer distance = getTargetDistance();
    return new FabInfo.Pointer(mFabInfo.relativeCenter.x - distance.x, mFabInfo.relativeCenter.y - distance.y);
  }

  private boolean isAnimating() {
    return mFabAnimation != null && mSheetAnimation != null && mOverlayAnimation != null &&
        (mFabAnimation.isRunning() || mSheetAnimation.isRunning() || mOverlayAnimation.isRunning());
  }

  private interface OnAnimationEndListener {

    void onAnimationEnd(Animator animation);
  }

  private static class FabInfo {

    private final Pointer bottomRight;

    private final Pointer center;

    private final int height;

    private final float radius;

    private final Pointer relativeBottomRight;

    private final Pointer relativeCenter;

    private final Pointer relativeTopLeft;

    private final Pointer topLeft;

    private final int width;

    FabInfo(FloatingActionButton fab) {
      int[] location = new int[2];
      fab.getLocationInWindow(location);

      width = fab.getMeasuredWidth();
      height = fab.getMeasuredHeight();
      topLeft = new Pointer(location[0], location[1]);
      center = new Pointer(topLeft.x + width / 2, topLeft.y + height / 2);
      bottomRight = new Pointer(topLeft.x + width, topLeft.y + height);
      relativeTopLeft = new Pointer(ViewCompat.getX(fab), ViewCompat.getY(fab));
      relativeCenter = new Pointer(relativeTopLeft.x + width / 2, relativeTopLeft.y + height / 2);
      relativeBottomRight = new Pointer(relativeTopLeft.x + width, relativeTopLeft.y + height);
      radius = Math.min(width / 2, MdCompat.dpToPx(FAB_SIZE));
    }

    private static class Pointer {

      private final int x;

      private final int y;

      Pointer(int x, int y) {
        this.x = x;
        this.y = y;
      }

      Pointer(float x, float y) {
        this((int) x, (int) y);
      }
    }
  }
}
