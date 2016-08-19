/*
 * Copyright 2016 "Henry Tao <hi@henrytao.me>"
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.henrytao.mdcore.widgets.arcanimator;

import android.graphics.PointF;

import java.util.Arrays;

/**
 * Created by henrytao on 8/15/16.
 */
class ArcMetric {

  public static ArcMetric evaluate(float startX, float startY, float endX, float endY, float degree, Side side) {
    ArcMetric arcMetric = new ArcMetric();
    arcMetric.mStartPoint.set(startX, startY);
    arcMetric.mEndPoint.set(endX, endY);
    arcMetric.setDegree(degree);
    arcMetric.mSide = side;
    arcMetric.createAxisVariables();

    arcMetric.calcStartEndSeg();
    arcMetric.calcRadius();
    arcMetric.calcMidAxisSeg();
    arcMetric.calcMidPoint();
    arcMetric.calcAxisPoints();
    arcMetric.calcZeroPoint();
    arcMetric.calcDegrees();

    return arcMetric;
  }

  float mAnimationDegree;

  PointF mAxisPoint[] = new PointF[2];

  float mEndDegree;

  PointF mEndPoint = new PointF();

  float mMidAxisSegment;

  //SEGMENTS. This Segments create virtual triangle except mZeroStartSegment

  PointF mMidPoint = new PointF();

  float mRadius;

  //Side of animation
  Side mSide;

  float mSideDegree;

  //DEGREES.

  float mStartDegree;

  float mStartEndSegment;

  PointF mStartPoint = new PointF();

  PointF mZeroPoint = new PointF();

  float mZeroStartDegree;

  float mZeroStartSegment;

  protected ArcMetric() {
  }

  @Override
  public String toString() {
    return "ArcMetric{" +
        "\nmStartPoint=" + mStartPoint +
        "\n mEndPoint=" + mEndPoint +
        "\n mMidPoint=" + mMidPoint +
        "\n mAxisPoint=" + Arrays.toString(mAxisPoint) +
        "\n mZeroPoint=" + mZeroPoint +
        "\n mStartEndSegment=" + mStartEndSegment +
        "\n mRadius=" + mRadius +
        "\n mMidAxisSegment=" + mMidAxisSegment +
        "\n mZeroStartSegment=" + mZeroStartSegment +
        "\n mAnimationDegree=" + mAnimationDegree +
        "\n mSideDegree=" + mSideDegree +
        "\n mZeroStartDegree=" + mZeroStartDegree +
        "\n mStartDegree=" + mStartDegree +
        "\n mEndDegree=" + mEndDegree +
        "\n mSide=" + mSide +
        '}';
  }

  /**
   * Return evaluated end degree
   *
   * @return the end degree
   */
  public float getEndDegree() {
    return mEndDegree;
  }

  /**
   * Return evaluated start degree
   *
   * @return the start degree
   */
  public float getStartDegree() {
    return mStartDegree;
  }

  public void setDegree(float degree) {
    degree = Math.abs(degree);
    if (degree > 180) {
      setDegree(degree % 180);
    } else if (degree == 180) {
      setDegree(degree - 1);
    } else if (degree < 30) {
      setDegree(30);
    } else {
      this.mAnimationDegree = degree;
    }
  }

  PointF getAxisPoint() {
    return mAxisPoint[mSide.value];
  }

  private void calcAxisPoints() {
    if (mStartPoint.y > mEndPoint.y || mStartPoint.y == mEndPoint.y) {
      mAxisPoint[0].x = mMidPoint.x + mMidAxisSegment * (mEndPoint.y - mStartPoint.y) / mStartEndSegment;
      mAxisPoint[0].y = mMidPoint.y - mMidAxisSegment * (mEndPoint.x - mStartPoint.x) / mStartEndSegment;

      mAxisPoint[1].x = mMidPoint.x - mMidAxisSegment * (mEndPoint.y - mStartPoint.y) / mStartEndSegment;
      mAxisPoint[1].y = mMidPoint.y + mMidAxisSegment * (mEndPoint.x - mStartPoint.x) / mStartEndSegment;
    } else {
      mAxisPoint[0].x = mMidPoint.x - mMidAxisSegment * (mEndPoint.y - mStartPoint.y) / mStartEndSegment;
      mAxisPoint[0].y = mMidPoint.y + mMidAxisSegment * (mEndPoint.x - mStartPoint.x) / mStartEndSegment;

      mAxisPoint[1].x = mMidPoint.x + mMidAxisSegment * (mEndPoint.y - mStartPoint.y) / mStartEndSegment;
      mAxisPoint[1].y = mMidPoint.y - mMidAxisSegment * (mEndPoint.x - mStartPoint.x) / mStartEndSegment;
    }
  }

  private void calcDegrees() {
    mZeroStartSegment = (float) Math.sqrt(Math.pow(mZeroPoint.x - mStartPoint.x, 2) +
        Math.pow(mZeroPoint.y - mStartPoint.y, 2));
    mZeroStartDegree = Utils.acos((2 * Math.pow(mRadius, 2) - Math.pow(mZeroStartSegment, 2)) / (2 * Math.pow(mRadius, 2)));
    switch (mSide) {
      case RIGHT:
        if (mStartPoint.y <= mZeroPoint.y) {
          if (mStartPoint.y > mEndPoint.y ||
              (mStartPoint.y == mEndPoint.y && mStartPoint.x > mEndPoint.x)) {
            mStartDegree = mZeroStartDegree;
            mEndDegree = mStartDegree + mAnimationDegree;
          } else {
            mStartDegree = mZeroStartDegree;
            mEndDegree = mStartDegree - mAnimationDegree;
          }
        } else if (mStartPoint.y >= mZeroPoint.y) {
          if (mStartPoint.y < mEndPoint.y ||
              (mStartPoint.y == mEndPoint.y && mStartPoint.x > mEndPoint.x)) {
            mStartDegree = 0 - mZeroStartDegree;
            mEndDegree = mStartDegree - mAnimationDegree;
          } else {
            mStartDegree = 0 - mZeroStartDegree;
            mEndDegree = mStartDegree + mAnimationDegree;
          }
        }
        break;
      case LEFT:
        if (mStartPoint.y <= mZeroPoint.y) {
          if (mStartPoint.y > mEndPoint.y ||
              (mStartPoint.y == mEndPoint.y && mStartPoint.x < mEndPoint.x)) {
            mStartDegree = 180 - mZeroStartDegree;
            mEndDegree = mStartDegree - mAnimationDegree;
          } else {
            mStartDegree = 180 - mZeroStartDegree;
            mEndDegree = mStartDegree + mAnimationDegree;
          }
        } else if (mStartPoint.y >= mZeroPoint.y) {
          if (mStartPoint.y < mEndPoint.y ||
              (mStartPoint.y == mEndPoint.y && mStartPoint.x < mEndPoint.x)) {
            mStartDegree = 180 + mZeroStartDegree;
            mEndDegree = mStartDegree + mAnimationDegree;
          } else {
            mStartDegree = 180 + mZeroStartDegree;
            mEndDegree = mStartDegree - mAnimationDegree;
          }
        }
        break;
    }
  }

  private void calcMidAxisSeg() {
    mMidAxisSegment = mRadius * Utils.sin(mSideDegree);
  }

  private void calcMidPoint() {
    mMidPoint.x = mStartPoint.x + mStartEndSegment / 2 * (mEndPoint.x - mStartPoint.x) / mStartEndSegment;
    mMidPoint.y = mStartPoint.y + mStartEndSegment / 2 * (mEndPoint.y - mStartPoint.y) / mStartEndSegment;
  }

  private void calcRadius() {
    mSideDegree = (180 - mAnimationDegree) / 2;
    mRadius = mStartEndSegment / Utils.sin(mAnimationDegree) * Utils.sin(mSideDegree);
  }

  private void calcStartEndSeg() {
    mStartEndSegment = (float) Math.sqrt(Math.pow(mStartPoint.x - mEndPoint.x, 2) +
        Math.pow(mStartPoint.y - mEndPoint.y, 2));

  }

  private void calcZeroPoint() {
    switch (mSide) {
      case RIGHT:
        mZeroPoint.x = mAxisPoint[Side.RIGHT.value].x + mRadius;
        mZeroPoint.y = mAxisPoint[Side.RIGHT.value].y;
        break;
      case LEFT:
        mZeroPoint.x = mAxisPoint[Side.LEFT.value].x - mRadius;
        mZeroPoint.y = mAxisPoint[Side.LEFT.value].y;
        break;
    }
  }

  private void createAxisVariables() {
    for (int i = 0; i < mAxisPoint.length; i++) {
      mAxisPoint[i] = new PointF();
    }
  }
}
