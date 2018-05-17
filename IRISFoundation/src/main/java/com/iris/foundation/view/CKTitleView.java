package com.iris.foundation.view;

import com.iris.irisfoundation.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

//因为适应性问题,将imgBt改为textView,同时取消写死的imageBt宽度
public class CKTitleView extends RelativeLayout {
	// view
	TextView btLeft, btRight;
	TextView tvLeft, tvRight;
	LinearLayout center;
	// style
	Drawable backgroundDrawable;
	Drawable btLeftDrawable, btRightDrawable;
	int tvLeftColor, tvRightColor;
	float tvLeftSize, tvRightSize;
	String tvLeftText, tvRightText;
	int btLeftWidth = 44, btRightWidth = 44;
	int defultColor;
	int tvMarginLeft, tvMarginRight;
	// params
	RelativeLayout.LayoutParams btLeftParams, btRightParams, tvLeftParams, tvRightParams, centerParams;
	int btLeftID = 200, btRightID = 300;
	TitleClick titleClick;

	public static interface TitleClick {
		public void btLeftClick(View v);

		public void btRightClick(View v);
	};

	public void setTitleClick(TitleClick titleClick) {
		this.titleClick = titleClick;
	}

	public CKTitleView(Context context) {
		this(context, null);
	}

	@SuppressLint({ "NewApi", "Recycle" })
	public CKTitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		btLeft = new TextView(context);
		btLeft.setId(btLeftID);
		btLeft.setGravity(Gravity.CENTER);
		btRight = new TextView(context);
		btRight.setId(btRightID);
		btRight.setGravity(Gravity.CENTER);
		tvLeft = new TextView(context);
		tvLeft.setGravity(Gravity.CENTER);
		tvRight = new TextView(context);
		tvRight.setGravity(Gravity.CENTER);
		center = new LinearLayout(context);

		int defultDimension = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 44,
				context.getResources().getDisplayMetrics());
		int defultTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 8,
				context.getResources().getDisplayMetrics());
		defultColor = Color.parseColor("#FFFFFF");
		TypedArray ty = context.obtainStyledAttributes(attrs, R.styleable.CKTitleView);
		btLeftWidth = (int) ty.getDimension(R.styleable.CKTitleView_leftBtWidth, defultDimension);
		btRightWidth = (int) ty.getDimension(R.styleable.CKTitleView_rightBtWidth, defultDimension);
		btLeftDrawable = ty.getDrawable(R.styleable.CKTitleView_leftBtDrawable);
		btRightDrawable = ty.getDrawable(R.styleable.CKTitleView_rightBtDrawable);
		tvLeftText = ty.getString(R.styleable.CKTitleView_leftTvText);
		tvRightText = ty.getString(R.styleable.CKTitleView_rightTvText);
		tvLeftColor = ty.getColor(R.styleable.CKTitleView_leftTvColor, defultColor);
		tvRightColor = ty.getColor(R.styleable.CKTitleView_rightTvColor, defultColor);
		tvLeftSize = ty.getDimension(R.styleable.CKTitleView_leftTvSize, defultTextSize);
		tvRightSize = ty.getDimension(R.styleable.CKTitleView_rightTvSize, defultTextSize);
		tvMarginLeft = (int) ty.getDimension(R.styleable.CKTitleView_tvMarginLeft, defultTextSize);
		tvMarginRight = (int) ty.getDimension(R.styleable.CKTitleView_tvMarginRight, defultTextSize);
		if (btLeftDrawable != null) {
			btLeft.setBackground(btLeftDrawable);
		}
		if (btRightDrawable != null) {
			btRight.setBackground(btRightDrawable);
		}
		if (tvLeftText != null) {
			tvLeft.setText(tvLeftText);
			tvLeft.setTextColor(tvLeftColor);
			tvLeft.setTextSize(tvLeftSize);
		}
		if (tvRightText != null) {
			tvRight.setText(tvRightText);
			tvRight.setTextColor(tvRightColor);
			tvRight.setTextSize(tvRightSize);
		}
		btLeftParams = new RelativeLayout.LayoutParams(btLeftWidth, RelativeLayout.LayoutParams.MATCH_PARENT);
		btLeftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		addView(btLeft, btLeftParams);

		btRightParams = new RelativeLayout.LayoutParams(btRightWidth, RelativeLayout.LayoutParams.MATCH_PARENT);
		btRightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		addView(btRight, btRightParams);

		tvLeftParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		tvLeftParams.addRule(RelativeLayout.RIGHT_OF, btLeftID);
		// tvLeftParams.setMarginStart(tvMarginLeft);
		tvLeftParams.setMargins(tvMarginLeft, 0, 0, 0);
		addView(tvLeft, tvLeftParams);

		tvRightParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		tvRightParams.addRule(RelativeLayout.LEFT_OF, btRightID);
		// tvRightParams.setMarginEnd(tvMarginRight);
		tvLeftParams.setMargins(0, 0, tvMarginRight, 0);
		addView(tvRight, tvRightParams);

		centerParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		centerParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		center.setGravity(Gravity.CENTER);
		addView(center, centerParams);

		btLeft.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (titleClick != null) {
					titleClick.btLeftClick(v);
				}
			}
		});
		btRight.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (titleClick != null) {
					titleClick.btRightClick(v);
				}
			}
		});
	}

	public TextView getLeftButoon() {
		return btLeft;
	}

	public TextView getRightButoon() {
		return btRight;
	}

	public TextView getLeftTextView() {
		return tvLeft;
	}

	public TextView getRightTextView() {
		return tvRight;
	}

	public LinearLayout getLinearLayout() {
		return center;
	}
}
