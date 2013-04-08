package edu.berkeley.cs160.onesies.metaapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 
 * This is a class to simply take a load off the DevelopmentActivity class and 
 * just handle the sidebar view. It should be instantiated with a view, and any
 * internal handlings of the sidebar should be handled via function calls to
 * this class.
 * 
 * @author andre
 *
 */
public class MASidebar extends LinearLayout{

	private DevelopmentActivity mActivity;
	
	private Button mHomeButton;
	private Button mNotesButton;
	private Button mDrawButton;
	private Button mShapesButton;
	private Button mElementButton;
	private Button mMenuButton;
	
	private View mButtonOptionsView;
//	private View mTextOptionsView;
	private Button mLinkButton;
	private Button mEditTextButton;
	
	private View mCurrentContextOptions = null;
	
	private int backColor = R.color.sidebarColor;
	
	public MASidebar(Context context) {
		super(context);
		this.setBackgroundColor(getResources().getColor(backColor));
	}

	public MASidebar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setBackgroundColor(getResources().getColor(backColor));
	}

	public MASidebar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setBackgroundColor(getResources().getColor(backColor));
	}
	
	public void setUp(TestingActivity activity) {
		this.setBackgroundColor(Color.GRAY);
	}
	
	public void setUp(DevelopmentActivity activity) {
		mActivity = activity;
		
		//---Main Dev Buttons ---------------------------------
		mHomeButton = (Button) findViewById(R.id.homeButton);
		mNotesButton = (Button) findViewById(R.id.notesButton);
		mDrawButton = (Button) findViewById(R.id.drawButton);
		mShapesButton = (Button) findViewById(R.id.shapesButton);
		mElementButton = (Button) findViewById(R.id.elementsButton);
		mMenuButton = (Button) findViewById(R.id.menuButton);
		
		mButtonOptionsView = (View) findViewById(R.id.button_options);
//		mTextOptionsView = (View) findViewById(R.id.text_options);
		mLinkButton = (Button) findViewById(R.id.linkButton);
		
		mEditTextButton = (Button) findViewById(R.id.editTextButton);
		
		
		mHomeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mActivity.makeToast("Hello Home!");
			}
		});
		mNotesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mActivity.makeToast("Hello Notes!");
			}
		});
		mDrawButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mActivity.makeToast("Hello Draw!");
			}
		});
		mShapesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mActivity.showShapesPopup(arg0);
			}
		});
		mElementButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mActivity.showElementsPopup(arg0);
			}
		});
		mMenuButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mActivity.showMenuPopup(v);
			}
		});
		mLinkButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mActivity.showLinkPopup(arg0);
			}
		});
		mEditTextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mActivity.onEditTextTapped();
			}
		});
	}

	public void showDefaultSidebar() {
		if (mCurrentContextOptions != null) {
			mCurrentContextOptions.setVisibility(INVISIBLE);
			mCurrentContextOptions = null;
		}
	}
	public void showElementContextBar() {
		if (mCurrentContextOptions != mButtonOptionsView) {			
			mButtonOptionsView.setVisibility(VISIBLE);
		}
		mCurrentContextOptions = mButtonOptionsView;
	}

	public DevelopmentActivity getmActivity() {
		return mActivity;
	}

	public void setmActivity(DevelopmentActivity mActivity) {
		this.mActivity = mActivity;
	}
	
	
	public void makeToast(String format, Object... args) {
		Toast.makeText(getContext(),
				String.format(format, args), Toast.LENGTH_SHORT).show();
	}
}
