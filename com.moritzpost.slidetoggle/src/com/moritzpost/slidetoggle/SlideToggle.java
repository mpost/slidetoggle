package com.moritzpost.slidetoggle;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SlideToggle extends HorizontalScrollView {

	public static final int TOGGLE_ITEM_WIDTH = 100;

	private static final int DOUBLE_TOGGLE_ITEM_WIDTH = TOGGLE_ITEM_WIDTH * 2;
	private static final int TOGGLE_DELAY = 500;
	private static final String LEFT_TOGGLE = "leftToggle";
	private static final String RIGHT_TOGGLE = "rightToggle";

	private Handler handler;
	private boolean leftToggleEnabled;
	private boolean rightToggleEnabled;
	private boolean hasChanged;

	public SlideToggle(Context context, AttributeSet attrs) {
		super(context, attrs);

		setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				System.out.println(event.getAction() + "  . " + event.getX() + " - " + event.getY()
						+ " | " + getMeasuredHeight());
				if (event.getAction() == MotionEvent.ACTION_UP
						|| event.getAction() == MotionEvent.ACTION_CANCEL) {
					smoothScrollTo(TOGGLE_ITEM_WIDTH, 0);
					hasChanged = false;
					return true;
				}
				return false;
			}
		});

		getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						getViewTreeObserver().removeGlobalOnLayoutListener(this);
						applyLayout();
						scrollTo(TOGGLE_ITEM_WIDTH, 0);
					}

				});

		handler = new Handler();
	}

	private void applyLayout() {
		int measuredWidth = getMeasuredWidth();
		System.out.println(measuredWidth);
		LinearLayout slider = (LinearLayout) findViewById(R.id.slidetoggle_slider);
		slider.setMinimumWidth(measuredWidth + TOGGLE_ITEM_WIDTH * 2);
		slider.setPadding(TOGGLE_ITEM_WIDTH, 0, TOGGLE_ITEM_WIDTH, 0);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		layoutParams.leftMargin = -TOGGLE_ITEM_WIDTH;

		LinearLayout sliderContent = (LinearLayout) findViewById(R.id.slidetoggle_slider_content);
		sliderContent.setMinimumWidth(measuredWidth);
	}

	@Override
	protected void onScrollChanged(int h, int v, int oldh, int oldv) {
		super.onScrollChanged(h, v, oldh, oldv);
		handleScrollRight(h);
		handleScrollLeft(h);
	}

	public void handleScrollRight(int h) {
		if (h == 0 && !handler.hasMessages(0, LEFT_TOGGLE) && !hasChanged) {
			handler.postAtTime(new Runnable() {

				@Override
				public void run() {
					toggleLeft();
					hasChanged = true;
				}
			}, LEFT_TOGGLE, SystemClock.uptimeMillis() + TOGGLE_DELAY);
		}
	}

	public void handleScrollLeft(int h) {
		if (h == DOUBLE_TOGGLE_ITEM_WIDTH && !handler.hasMessages(0, RIGHT_TOGGLE) && !hasChanged) {
			handler.postAtTime(new Runnable() {

				@Override
				public void run() {
					toggleRight();
					hasChanged = true;
				}
			}, RIGHT_TOGGLE, SystemClock.uptimeMillis() + TOGGLE_DELAY);
		}
	}

	protected void toggleRight() {
		ViewGroup parent = (ViewGroup) getParent();
		ImageView rightToggleImage = (ImageView) parent.findViewById(R.id.image_right_toggle);
		if (rightToggleEnabled) {
			rightToggleImage.setImageResource(R.drawable.note_disabled);
		} else {
			rightToggleImage.setImageResource(R.drawable.note_enabled);
		}
		rightToggleEnabled = !rightToggleEnabled;
	}

	public void toggleLeft() {
		ViewGroup parent = (ViewGroup) getParent();
		ImageView leftToggleImage = (ImageView) parent.findViewById(R.id.image_left_toggle);
		if (leftToggleEnabled) {
			leftToggleImage.setImageResource(R.drawable.star_disabled);
		} else {
			leftToggleImage.setImageResource(R.drawable.star_enabled);
		}
		leftToggleEnabled = !leftToggleEnabled;
	}

}
