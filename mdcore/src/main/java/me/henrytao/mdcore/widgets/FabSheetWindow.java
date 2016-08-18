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
import me.henrytao.mdcore.widgets.arcanimator.AlphaAnimator;
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

  private final FabInfo mFabInfo;

  private Context mContext;

  private int mDegree;

  private boolean mIsCreated;

  private ViewGroup vContent;

  private FloatingActionButton vFab;

  private ViewGroup vOverlay;

  private ViewGroup vRoot;

  private View vSheet;

  private CircularRevealFrameLayout vSheetContainer;

  public FabSheetWindow(FloatingActionButton fab, View sheet) {
    mContext = fab.getContext().getApplicationContext();
    vFab = fab;
    vSheet = sheet;
    mDegree = 45;
    mFabInfo = new FabInfo(vFab);
  }

  public void dismiss() {
  }

  public FabInfo.Pointer getTargetRelativePointer() {
    FabInfo.Pointer distance = getTargetDistance();
    return new FabInfo.Pointer(mFabInfo.relativeCenter.x - distance.x, mFabInfo.relativeCenter.y - distance.y);
  }

  public void show() {
    if (!mIsCreated) {
      mIsCreated = true;
      onCreateView();
    }

    Animator fabAnimation = createFabShowAnimation().setDuration(200);
    Animator sheetAnimation = createSheetShowAnimation().setDuration(200);
    Animator overlayAnimation = createOverlayShowAnimation().setDuration(200);

    sheetAnimation.setStartDelay(150);
    overlayAnimation.setStartDelay(100);

    fabAnimation.start();
    sheetAnimation.start();
    overlayAnimation.start();
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

    //FrameLayout a = new FrameLayout(mContext);
    //a.setLayoutParams(new FrameLayout.LayoutParams(mFabInfo.width, mFabInfo.height));
    //a.setBackgroundColor(Color.parseColor("#4C000000"));
    //vRoot.addView(a);
    //ViewCompat.setX(a, mFabInfo.topLeft.x);
    //ViewCompat.setY(a, mFabInfo.topLeft.y);
    //
    //FrameLayout b = new FrameLayout(mContext);
    //b.setLayoutParams(new FrameLayout.LayoutParams(mFabInfo.width, mFabInfo.height));
    //b.setBackgroundColor(Color.parseColor("#4C000000"));
    //vRoot.addView(b);
    //FabInfo.Pointer target = getTargetAbsolutePointer();
    //ViewCompat.setX(b, target.x - mFabInfo.width / 2);
    //ViewCompat.setY(b, target.y - mFabInfo.height / 2);
  }

  protected void onDestroyView() {

  }

  private Animator createFabShowAnimation() {
    FabInfo.Pointer target = getTargetRelativePointer();
    ValueAnimator animator = ArcAnimator.create(vFab, target.x, target.y, mDegree, Side.LEFT);
    animator.setInterpolator(ACCELERATE);
    return animator;
  }

  private Animator createOverlayShowAnimation() {
    Animator animator = AlphaAnimator.create(vOverlay, 0.0f, 1.0f);
    animator.setInterpolator(ACCELERATE);
    return animator;
  }

  private Animator createSheetShowAnimation() {
    int dx = vSheetContainer.getMeasuredWidth() / 2;
    int dy = vSheetContainer.getMeasuredHeight() / 2;
    float radius = (float) Math.hypot(dx, dy);
    Animator animator = MdCompat.createCircularReveal(vSheetContainer, dx, dy, 0, radius);
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

  private static class FabInfo {

    private final Pointer bottomRight;

    private final Pointer center;

    private final int height;

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
