package edu.berkeley.cs160.onesies.metaapp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
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
	private int 	backColor = R.color.sidebarColor;
	
	private Button 	mHomeButton;
	private Button 	mNotesButton;
	private Button 	mAddButton;
	private Button 	mMenuButton;
	private Button  mUndoButton;
	
//	private View	mSketchOptionsView;
//	private View 	mButtonOptionsView;
//	private View	mCurrentContextOptions = null;
	private Button	mSketchAddButton;
	private Button	mSketchCancelButton;
	private Button	mDeleteButton;
	private Button	mElementForwardButton;
	private Button	mElementBackwardButton;
	private Button	mLinkButton;
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
	
	//-------------------------------------------------------------------------
	public void setUp(DevelopmentActivity activity) {
		mActivity = activity;
		
		//---Main Dev Buttons ---------------------------------
		mHomeButton = (Button) findViewById(R.id.homeButton);
		mNotesButton = (Button) findViewById(R.id.notesButton);
		// mSketchButton = (Button) findViewById(R.id.sketchButton);
		// mShapesButton = (Button) findViewById(R.id.shapesButton);
		// mElementButton = (Button) findViewById(R.id.elementsButton);
		mAddButton = (Button) findViewById(R.id.addButton);
		mMenuButton = (Button) findViewById(R.id.menuButton);
		mUndoButton = (Button) findViewById(R.id.undoButton);
		
		//---Contextual Buttons ---------------------------------
//		mSketchOptionsView = (View) findViewById(R.id.sketch_options);
//		mButtonOptionsView = (View) findViewById(R.id.button_options);
//		mTextOptionsView = (View) findViewById(R.id.text_options);
		mLinkButton = (Button) findViewById(R.id.linkButton);
		mEditTextButton = (Button) findViewById(R.id.editTextButton);
		mDeleteButton = (Button) findViewById(R.id.deleteElementButton);
		mSketchAddButton = (Button) findViewById(R.id.sketchAddButton);
		mSketchCancelButton = (Button) findViewById(R.id.sketchCancelButton);
		mElementForwardButton = (Button) findViewById(R.id.elementForward);
		mElementBackwardButton = (Button) findViewById(R.id.elementBackward); 
		
		mHomeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
//				mActivity.makeToast("Hello Home!");
				mActivity.onHomeButtonTapped();
			}
		});
		mNotesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
//				mActivity.makeToast("Hello Home!");
				mActivity.onHomeButtonTapped();
			}
		});
		mAddButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mActivity.showAddPopup(arg0);
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
		
		mUndoButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mActivity.onUndoTapped();
			}
		});
		
		
	//---- SKETCH ZONE CONTEXT BUTTONS ----
		mSketchAddButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mActivity.onAddSketchTapped();
			}
		});
		mSketchCancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				mActivity.onAddSketchTapped();
				mActivity.hideSketchZone();
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
		mElementForwardButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mActivity.onElementForwardTapped();
			}
		});
		mElementBackwardButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mActivity.onElementBackwardTapped();
			}
		});
		
		showDefaultSidebar();
	}

	//-------------------------------------------------------------------------
	public void showDefaultSidebar() {
		mHomeButton.setVisibility(VISIBLE);
		mNotesButton.setVisibility(GONE);
		mUndoButton.setVisibility(VISIBLE);
		mMenuButton.setVisibility(VISIBLE);
		
		
		mLinkButton.setVisibility(INVISIBLE);
		mEditTextButton.setVisibility(INVISIBLE);
		mDeleteButton.setVisibility(INVISIBLE);
		mElementForwardButton.setVisibility(INVISIBLE);
		mElementBackwardButton.setVisibility(INVISIBLE);
		mSketchAddButton.setVisibility(INVISIBLE);
		mSketchCancelButton.setVisibility(INVISIBLE);
//		if (mCurrentContextOptions != null) {
//			mCurrentContextOptions.setVisibility(INVISIBLE);
//			mCurrentContextOptions = null;
//		}
	}
	public void showSketchZoneBar() {
		mHomeButton.setVisibility(VISIBLE);
		mNotesButton.setVisibility(GONE);
		mUndoButton.setVisibility(INVISIBLE);
		mMenuButton.setVisibility(INVISIBLE);
		
		mLinkButton.setVisibility(INVISIBLE);
		mEditTextButton.setVisibility(INVISIBLE);
		mDeleteButton.setVisibility(INVISIBLE);
		mElementForwardButton.setVisibility(INVISIBLE);
		mElementBackwardButton.setVisibility(INVISIBLE);
		mSketchAddButton.setVisibility(VISIBLE);
		mSketchCancelButton.setVisibility(VISIBLE);
		mUndoButton.setVisibility(INVISIBLE);
//		if(mCurrentContextOptions != mSketchOptionsView) {
//			mSketchOptionsView.setVisibility(VISIBLE);
//			mCurrentContextOptions = mSketchOptionsView;
//		}
	}
	public void showElementContextBar(MAScreenElement element) {
		if (element == null)
			return;
		switch (element.getmType()) {
		case BUTTON:
			mLinkButton.setVisibility(VISIBLE);
			mEditTextButton.setVisibility(VISIBLE);
			break;
		case TEXT_LABEL:
			mEditTextButton.setVisibility(VISIBLE);
			mLinkButton.setVisibility(VISIBLE);
			break;
		case CHECKBOX:
		case RADIOBUTTON:
			mEditTextButton.setVisibility(VISIBLE);
			break;
		case SHAPE:
			mLinkButton.setVisibility(VISIBLE);
			break;
		default:
			break;
		}

		mDeleteButton.setVisibility(VISIBLE);
		mElementForwardButton.setVisibility(VISIBLE);
		// IMPLEMENT FIRST
//		mElementBackwardButton.setVisibility(VISIBLE);
		mSketchAddButton.setVisibility(INVISIBLE);
		mSketchCancelButton.setVisibility(INVISIBLE);
		mUndoButton.setVisibility(VISIBLE);
//		if (mCurrentContextOptions != mButtonOptionsView) {			
//			mButtonOptionsView.setVisibility(VISIBLE);
//			mCurrentContextOptions = mButtonOptionsView;
//		}
	}
	public void showCustomElementBar() {
		mLinkButton.setVisibility(VISIBLE);
		mEditTextButton.setVisibility(INVISIBLE);
		mDeleteButton.setVisibility(VISIBLE);
		mElementForwardButton.setVisibility(VISIBLE);
		mElementBackwardButton.setVisibility(INVISIBLE);
		mSketchAddButton.setVisibility(INVISIBLE);
		mSketchCancelButton.setVisibility(INVISIBLE);
		mUndoButton.setVisibility(VISIBLE);
	}
	
	public void showTestBar() {
		// TODO HACK HACK HACK HACK HACK HACK HACK HACK
		// TODO HACK HACK HACK HACK HACK HACK HACK HACK
		// TODO HACK HACK HACK HACK HACK HACK HACK HACK
		// TODO HACK HACK HACK HACK HACK HACK HACK HACK
		
		mHomeButton.setVisibility(VISIBLE);
		mNotesButton.setVisibility(INVISIBLE);
		mUndoButton.setVisibility(INVISIBLE);
		mMenuButton.setVisibility(VISIBLE);
		
		mLinkButton.setVisibility(INVISIBLE);
		mEditTextButton.setVisibility(INVISIBLE);
		mDeleteButton.setVisibility(INVISIBLE);
		mElementForwardButton.setVisibility(INVISIBLE);
		mElementBackwardButton.setVisibility(INVISIBLE);
		mSketchAddButton.setVisibility(INVISIBLE);
		mSketchCancelButton.setVisibility(INVISIBLE);
	}

	//-------------------------------------------------------------------------
	public DevelopmentActivity getmActivity() {
		return mActivity;
	}

	public void setmActivity(DevelopmentActivity mActivity) {
		this.mActivity = mActivity;
	}
	
	
	//-------------------------------------------------------------------------
	public void makeToast(String format, Object... args) {
		Toast.makeText(getContext(),
				String.format(format, args), Toast.LENGTH_SHORT).show();
	}
}
