package com.roughike.bottombar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

/*
 * BottomBar library for Android
 * Copyright (c) 2016 Iiro Krankka (http://github.com/roughike).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
class BottomBarBadge extends TextView {
    private int count;
    private int maxCount;
    private String countString;
    private boolean isVisible = false;

    BottomBarBadge(Context context) {
        super(context);
    }

    /**
     * Set the unread / new item / whatever count for this Badge.
     *
     * @param count the value this Badge should show.
     */
    void setCount(int count) {
        this.count = count;
        setText(String.valueOf(count));
    }

    /**
     * set badge with max count
     *
     * @param count    count
     * @param maxCount max count
     * @param showMore show + or not
     */
    void setCount(int count, int maxCount, boolean showMore) {
        this.count = count;
        this.maxCount = maxCount;
        if (count > maxCount) {
            setText(showMore ? maxCount + "+" : String.valueOf(maxCount));
        } else {
            setText(String.valueOf(count));
        }
    }

    /**
     * set badge support strings
     *
     * @param countString count string
     */
    void setCountString(String countString) {
        this.countString = countString;
        setText(countString);
    }

    /**
     * get current count string
     *
     * @return badge string
     */
    String getCountString() {
        return countString;
    }

    /**
     * get max count
     *
     * @return max count
     */
    public int getMaxCount() {
        return maxCount;
    }

    /**
     * Get the currently showing count for this Badge.
     *
     * @return current count for the Badge.
     */
    int getCount() {
        return count;
    }

    /**
     * Shows the badge with a neat little scale animation.
     */
    void show() {
        isVisible = true;
        ViewCompat.animate(this)
                .setDuration(150)
                .alpha(1)
                .scaleX(1)
                .scaleY(1)
                .start();
    }

    /**
     * Hides the badge with a neat little scale animation.
     */
    void hide() {
        isVisible = false;
        ViewCompat.animate(this)
                .setDuration(150)
                .alpha(0)
                .scaleX(0)
                .scaleY(0)
                .start();
    }

    /**
     * Is this badge currently visible?
     *
     * @return true is this badge is visible, otherwise false.
     */
    boolean isVisible() {
        return isVisible;
    }

    void attachToTab(BottomBarTab tab, int backgroundColor) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        setLayoutParams(params);
        setGravity(Gravity.CENTER);
        MiscUtils.setTextAppearance(this, R.style.BB_BottomBarBadge_Text);

        setColoredCircleBackground(backgroundColor);
        wrapTabAndBadgeInSameContainer(tab);
    }

    void setColoredCircleBackground(int circleColor) {
        int padding = MiscUtils.dpToPixel(getContext(), 5);
        GradientDrawable drawable = BadgeCircle.make(circleColor, getContext());
        setBackgroundCompat(drawable);
        setPadding(padding, 0, padding, 0);
    }

    private void wrapTabAndBadgeInSameContainer(final BottomBarTab tab) {
        ViewGroup tabContainer = (ViewGroup) tab.getParent();
        tabContainer.removeView(tab);

        final BadgeContainer badgeContainer = new BadgeContainer(getContext());
        badgeContainer.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        badgeContainer.addView(tab);
        badgeContainer.addView(this);

        tabContainer.addView(badgeContainer, tab.getIndexInTabContainer());

        badgeContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                badgeContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                adjustPositionAndSize(tab);
            }
        });
    }

    void removeFromTab(BottomBarTab tab) {
        BadgeContainer badgeAndTabContainer = (BadgeContainer) getParent();
        ViewGroup originalTabContainer = (ViewGroup) badgeAndTabContainer.getParent();

        badgeAndTabContainer.removeView(tab);
        originalTabContainer.removeView(badgeAndTabContainer);
        originalTabContainer.addView(tab, tab.getIndexInTabContainer());
    }

    void adjustPositionAndSize(BottomBarTab tab) {
        AppCompatImageView iconView = tab.getIconView();
        ViewGroup.LayoutParams params = getLayoutParams();

//        int size = Math.max(getWidth(), getHeight());
        float xOffset = (float) (iconView.getWidth() / 1.25);

        setX(iconView.getX() + xOffset);
        setTranslationY(10);

//        if (params.width != size || params.height != size) {
//            params.width = size;
//            params.height = size;
//        }
        setLayoutParams(params);
    }

    @SuppressWarnings("deprecation")
    private void setBackgroundCompat(Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(background);
        } else {
            setBackgroundDrawable(background);
        }
    }
}
