package edu.berkeley.cs160.onesies.metaapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
public class MASidebar extends RelativeLayout{

	private DevelopmentActivity mActivity;
	private int backColor = R.color.sidebarColor;
	
	private Button mHomeButton;
	private Button mNotesButton;
	private Button mSketchButton;
	private Button mShapesButton;
	private Button mElementButton;
	private Button mDeleteButton;
	private Button mMenuButton;
	
//	private View	mSketchOptionsView;
//	private View 	mButtonOptionsView;
//	private View	mCurrentContextOptions = null;
	//---- SKETCH ZONE CONTEXT BUTTONS ----
	private Button	mAddButton;
	//---- MABUTTON CONTEXT BUTTONS ----
	private Button	mLinkButton;
	//---- ______ CONTEXT BUTTONS ----
	private Button	mEditTextButton;
	

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
		mSketchButton = (Button) findViewById(R.id.sketchButton);
		mShapesButton = (Button) findViewById(R.id.shapesButton);
		mElementButton = (Button) findViewById(R.id.elementsButton);
		mMenuButton = (Button) findViewById(R.id.menuButton);
		
		//---Contextual Buttons ---------------------------------
//		mSketchOptionsView = (View) findViewById(R.id.sketch_options);
//		mButtonOptionsView = (View) findViewById(R.id.button_options);
//		mTextOptionsView = (View) findViewById(R.id.text_options);
		mLinkButton = (Button) findViewById(R.id.linkButton);
		mEditTextButton = (Button) findViewById(R.id.editTextButton);
		mDeleteButton = (Button) findViewById(R.id.deleteElementButton);
		mAddButton = (Button) findViewById(R.id.addButton);
		
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
		mSketchButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mActivity.showSketchZone(arg0);
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
		mDeleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mActivity.onDeleteButtonTapped(v);
			}
		});
		mMenuButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mActivity.showMenuPopup(v);
			}
		});
		
		
	//---- SKETCH ZONE CONTEXT BUTTONS ----
		mAddButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				makeToast("ADD ME");
				mActivity.onAddSketchTapped();
			}
		});
	//---- MABUTTON CONTEXT BUTTONS ----
		mLinkButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mActivity.showLinkPopup(arg0);
			}
		});
	//---- _______ CONTEXT BUTTONS ----
		mEditTextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mActivity.onEditTextTapped();
			}
		});
		
		showDefaultSidebar();
	}

	public void showDefaultSidebar() {
		mLinkButton.setVisibility(INVISIBLE);
		mEditTextButton.setVisibility(INVISIBLE);
		mDeleteButton.setVisibility(INVISIBLE);
		mAddButton.setVisibility(INVISIBLE);
//		if (mCurrentContextOptions != null) {
//			mCurrentContextOptions.setVisibility(INVISIBLE);
//			mCurrentContextOptions = null;
//		}
	}
	public void showSketchZoneBar() {
		mLinkButton.setVisibility(INVISIBLE);
		mEditTextButton.setVisibility(INVISIBLE);
		mDeleteButton.setVisibility(INVISIBLE);
		mAddButton.setVisibility(VISIBLE);
//		if(mCurrentContextOptions != mSketchOptionsView) {
//			mSketchOptionsView.setVisibility(VISIBLE);
//			mCurrentContextOptions = mSketchOptionsView;
//		}
	}
	public void showElementContextBar(MAScreenElement element) {
		if (element == null)
			return;
		if(element.getmType() == ElementType.BUTTON) {
			mLinkButton.setVisibility(VISIBLE);
		} else {
			mLinkButton.setVisibility(INVISIBLE);
		}
		mEditTextButton.setVisibility(VISIBLE);
		mDeleteButton.setVisibility(VISIBLE);
		mAddButton.setVisibility(INVISIBLE);
//		if (mCurrentContextOptions != mButtonOptionsView) {			
//			mButtonOptionsView.setVisibility(VISIBLE);
//			mCurrentContextOptions = mButtonOptionsView;
//		}
	}
	public void showCustomElementBar() {
		mLinkButton.setVisibility(VISIBLE);
		mEditTextButton.setVisibility(INVISIBLE);
		mDeleteButton.setVisibility(VISIBLE);
		mAddButton.setVisibility(INVISIBLE);
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
