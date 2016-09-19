package me.henrytao.mdcore.widgets;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
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

  private static final DecelerateInterpolator DECELERATE = new DecelerateInterpolator();

  private static void onAnimationEnd(Animator animation, @NonNull final OnAnimationEndListener onAnimationEndListener) {
    animation.addListener(new Animator.AnimatorListener() {
      @Override
      public void onAnimationCancel(Animator animation) {

      }

      @Override
      public void onAnimationEnd(Animator animation) {
        onAnimationEndListener.onAnimationEnd(animation);
        animation.removeListener(this);
      }

      @Override
      public void onAnimationRepeat(Animator animation) {

      }

      @Override
      public void onAnimationStart(Animator animation) {

      }
    });
  }

  private static void onAnimationStart(Animator animation, @NonNull final OnAnimationStartListener onAnimationStartListener) {
    animation.addListener(new Animator.AnimatorListener() {
      @Override
      public void onAnimationCancel(Animator animation) {

      }

      @Override
      public void onAnimationEnd(Animator animation) {
        animation.removeListener(this);
      }

      @Override
      public void onAnimationRepeat(Animator animation) {

      }

      @Override
      public void onAnimationStart(Animator animation) {
        onAnimationStartListener.onAnimationStart(animation);
      }
    });
  }

  private final int mBackgroundColor;

  private final int mDegree;

  private final long mDuration;

  private final Integer mFabMaxBottom;

  private Context mContext;

  private Animator mFabAnimation;

  private FabInfo mFabInfo;

  private boolean mIsCreated;

  private OnDismissListener mOnDismissListener;

  private OnShowListener mOnShowListener;

  private Animator mOverlayAnimation;

  private Animator mSheetAnimation;

  private boolean mShowing;

  private ViewGroup vContent;

  private FloatingActionButton vFab;

  private ViewGroup vOverlay;

  private ViewGroup vRoot;

  private View vSheet;

  private CircularRevealFrameLayout vSheetContainer;

  private FabSheetWindow(FloatingActionButton fab, View sheet, int backgroundColor, int degree, long duration, Integer fabMaxBottom,
      OnShowListener onShowListener, OnDismissListener onDismissListener) {
    mContext = fab.getContext().getApplicationContext();
    vFab = fab;
    vSheet = sheet;
    mBackgroundColor = backgroundColor;
    mDegree = degree;
    mDuration = duration;
    mFabMaxBottom = fabMaxBottom;
    mOnShowListener = onShowListener;
    mOnDismissListener = onDismissListener;
  }

  public void destroy() {
    onDestroyView();
  }

  public void dismiss() {
    if (!mIsCreated) {
      return;
    }
    if (isAnimating() || !isShowing()) {
      return;
    }

    mFabAnimation = createFabDimissAnimation().setDuration(mDuration);
    mSheetAnimation = createSheetDismissAnimation().setDuration(mDuration);
    mOverlayAnimation = createOverlayDismissAnimation().setDuration(mDuration);

    mFabAnimation.setStartDelay(mDuration);

    onAnimationEnd(mFabAnimation, new OnAnimationEndListener() {
      @Override
      public void onAnimationEnd(Animator animation) {
        mShowing = false;
        if (mOnDismissListener != null) {
          mOnDismissListener.onDismiss(FabSheetWindow.this);
        }
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

  public void invalidate() {
    if (mIsCreated) {
      vOverlay.invalidate();
      vContent.invalidate();
      ViewCompat.setX(vSheetContainer, mFabInfo.bottomRight.x - vSheetContainer.getMeasuredWidth());
      ViewCompat.setY(vSheetContainer, mFabInfo.bottomRight.y - vSheetContainer.getMeasuredHeight());
    }
  }

  public boolean isShowing() {
    return mShowing;
  }

  public void reset() {
    if (!mIsCreated) {
      return;
    }
    mShowing = false;
    if (mFabAnimation != null) {
      mFabAnimation.cancel();
    }
    if (mSheetAnimation != null) {
      mSheetAnimation.cancel();
    }
    if (mOverlayAnimation != null) {
      mOverlayAnimation.cancel();
    }
    vFab.setVisibility(View.VISIBLE);
    ViewCompat.setX(vFab, mFabInfo.relativeTopLeft.x);
    ViewCompat.setY(vFab, mFabInfo.relativeTopLeft.y);
    vSheetContainer.setVisibility(View.GONE);
    vOverlay.setVisibility(View.GONE);
  }

  public void show() {
    if (!mIsCreated) {
      mIsCreated = true;
      onCreateView();
    }
    if (isAnimating() || isShowing()) {
      return;
    }
    mShowing = true;

    invalidate();

    vFab.setVisibility(View.VISIBLE);
    vSheetContainer.setVisibility(View.INVISIBLE);
    vOverlay.setVisibility(View.INVISIBLE);

    mFabAnimation = createFabShowAnimation().setDuration(mDuration);
    mSheetAnimation = createSheetShowAnimation().setDuration(mDuration);
    mOverlayAnimation = createOverlayShowAnimation().setDuration(mDuration);

    mSheetAnimation.setStartDelay(mDuration);
    mOverlayAnimation.setStartDelay(mDuration / 2);

    onAnimationStart(mSheetAnimation, new OnAnimationStartListener() {
      @Override
      public void onAnimationStart(Animator animation) {
        vSheetContainer.setVisibility(View.VISIBLE);
      }
    });
    onAnimationStart(mOverlayAnimation, new OnAnimationStartListener() {
      @Override
      public void onAnimationStart(Animator animation) {
        vOverlay.setVisibility(View.VISIBLE);
      }
    });

    onAnimationEnd(mSheetAnimation, new OnAnimationEndListener() {
      @Override
      public void onAnimationEnd(Animator animation) {
        if (mOnShowListener != null) {
          mOnShowListener.onShow(FabSheetWindow.this);
        }
      }
    });

    mFabAnimation.start();
    mSheetAnimation.start();
    mOverlayAnimation.start();
  }

  private Animator createFabDimissAnimation() {
    ValueAnimator animator = ArcAnimator.create(vFab, mFabInfo.relativeCenter.x, mFabInfo.relativeCenter.y, mDegree, Side.LEFT);
    animator.setInterpolator(DECELERATE);
    return animator;
  }

  private Animator createFabShowAnimation() {
    FabInfo.Pointer target = getTargetRelativePointer();
    ValueAnimator animator = ArcAnimator.create(vFab, target.x, target.y, mDegree, Side.LEFT);
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
    FabInfo.Pointer target = getTargetDistance();
    int dx = vSheetContainer.getMeasuredWidth() / 2;
    int dy = vSheetContainer.getMeasuredHeight() / 2;
    int dyFab = dy + (int) (target.y - (mFabMaxBottom != null ? (float) Math.min(mFabMaxBottom, target.y) : target.y));
    float radius = (float) Math.hypot(dx, Math.max(dy, dyFab));
    Animator animator = MdCompat.createCircularReveal(vSheetContainer, dx, dyFab, radius, mFabInfo.radius);
    animator.setInterpolator(ACCELERATE);
    return animator;
  }

  private Animator createSheetShowAnimation() {
    FabInfo.Pointer target = getTargetDistance();
    int dx = vSheetContainer.getMeasuredWidth() / 2;
    int dy = vSheetContainer.getMeasuredHeight() / 2;
    int dyFab = dy + (int) (target.y - (mFabMaxBottom != null ? (float) Math.min(mFabMaxBottom, target.y) : target.y));
    float radius = (float) Math.hypot(dx, Math.max(dy, dyFab));
    Animator animator = MdCompat.createCircularReveal(vSheetContainer, dx, dyFab, mFabInfo.radius, radius);
    animator.setInterpolator(DECELERATE);
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
    int x = mFabInfo.relativeCenter.x - distance.x;
    int y = mFabInfo.relativeCenter.y - (int) (mFabMaxBottom != null ? (float) Math.min(mFabMaxBottom, distance.y) : distance.y);
    return new FabInfo.Pointer(x, y);
  }

  private boolean isAnimating() {
    return mFabAnimation != null && mSheetAnimation != null && mOverlayAnimation != null &&
        (mFabAnimation.isRunning() || mSheetAnimation.isRunning() || mOverlayAnimation.isRunning());
  }

  private void onCreateView() {
    mFabInfo = new FabInfo(vFab);
    vRoot = (ViewGroup) vFab.getRootView();

    vOverlay = new FrameLayout(mContext);
    vOverlay.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    vOverlay.setBackgroundColor(mBackgroundColor);
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

    invalidate();

    vOverlay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismiss();
      }
    });
  }

  private void onDestroyView() {
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
    mOnShowListener = null;
    mOnDismissListener = null;
  }

  private interface OnAnimationEndListener {

    void onAnimationEnd(Animator animation);
  }

  private interface OnAnimationStartListener {

    void onAnimationStart(Animator animation);
  }

  public interface OnDismissListener {

    void onDismiss(FabSheetWindow fsw);
  }

  public interface OnShowListener {

    void onShow(FabSheetWindow fsw);
  }

  public static class Builder {

    private static final int BACKGROUND_COLOR = Color.parseColor("#4C000000");

    private static final int DEGREE = 45;

    private static final long DURATION = 200;

    private int mBackgroundColor = BACKGROUND_COLOR;

    private int mDegree = DEGREE;

    private long mDuration = DURATION;

    private Integer mFabMaxBottom = null;

    private OnDismissListener mOnDismissListener;

    private OnShowListener mOnShowListener;

    private FloatingActionButton vFab;

    private View vSheet;

    public Builder(FloatingActionButton fab, View sheet) {
      vFab = fab;
      vSheet = sheet;
    }

    public FabSheetWindow build() {
      return new FabSheetWindow(vFab, vSheet, mBackgroundColor, mDegree, mDuration, mFabMaxBottom, mOnShowListener, mOnDismissListener);
    }

    public Builder setBackgroundColor(@ColorInt int backgroundColor) {
      mBackgroundColor = backgroundColor;
      return this;
    }

    public Builder setDegree(int degree) {
      mDegree = degree;
      return this;
    }

    public Builder setDuration(long duration) {
      mDuration = duration;
      return this;
    }

    public Builder setFabMaxBottom(int fabMaxBottom) {
      mFabMaxBottom = fabMaxBottom;
      return this;
    }

    public Builder setOnDismissListener(OnDismissListener onDismissListener) {
      mOnDismissListener = onDismissListener;
      return this;
    }

    public Builder setOnShowListener(OnShowListener onShowListener) {
      mOnShowListener = onShowListener;
      return this;
    }
  }

  private static class FabInfo {

    private static final int FAB_SIZE = 48;

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
