package edu.berkeley.cs160.onesies.metaapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;


public class DevelopmentActivity extends Activity {

	private MAScreen			mScreen;
	private RelativeLayout		mDevRelLayout;
	private MASidebar			mSidebar;
	private PopupWindow			mPopup;
	private GridLayout			mShapesGridView;
	private GridLayout			mElementsGridView;

	private View 				mShapesContentView;
	private View 				mElementsContentView;
	
	
	private LayoutInflater		mLayoutInflater;
	private Resources			mResources;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_main, menu);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.development_main);
		
		mLayoutInflater = (LayoutInflater)getApplicationContext().
				getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mResources = getResources();
		
		mDevRelLayout = (RelativeLayout) findViewById(R.id.developmentRelative);
		mScreen = (MAScreen) findViewById(R.id.maScreen);
		mSidebar = (MASidebar) findViewById(R.id.sidebar);
		mSidebar.setUp(this);
		
	}
	
	
	private void uiElementTapped(View element) {
		// Take the view, and add it to the MAScreen object.
		makeToast("Hello Image!");
		Bitmap map = createBitmapOfView(element);
//		ImageView clone = createImageViewWithBitmap(map);
		
		MAScreenElement clone = createElement(map);
		/*
		 * FIND SOME WAY TO MAKE THE CLONE BE WRAP CONTENT. CURRENTLY ITS AT
		 * FILL_PARENT OR SOMETHING.
		 */
		
//		clone.setBackgroundColor(getResources().getColor(R.color.testingPurple));
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
		200, 100);
//		params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
//		params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
		clone.setLayoutParams(params);
		
//		clone.setScaleType(ScaleType.CENTER); //NOT COMPATIBLE WITH MASCREENELEMENT
		/*
		clone.setOnTouchListener(new View.OnTouchListener() {
			
			float dx = 0, dy = 0;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				RelativeLayout.LayoutParams params;
				switch(event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						params = (RelativeLayout.LayoutParams) v.getLayoutParams();
//						params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
//						params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;		
//						params.width = 200;
//						params.height = 100;		
						v.setLayoutParams(params);
						dx = event.getX();
						dy = event.getY();
						break;
					case MotionEvent.ACTION_MOVE:
						float x = event.getX();
						float y = event.getY();
						params = (RelativeLayout.LayoutParams) v.getLayoutParams();
						params.leftMargin = (int) (params.leftMargin + (x-dx));
						params.topMargin = (int) (params.topMargin + (y-dy));
						v.setLayoutParams(params);
						break;
					case MotionEvent.ACTION_UP:
						break;
					default:
						break;
				}
				mScreen.invalidate();
				return true;
			}
		});
		*/
		mScreen.addView(clone);
	}
	
	
	/*****************************************************************************
	 * "Call Backs" for MASidebar
	 *****************************************************************************/
	
	public void showShapesPopup(View button) {
		mShapesContentView = mLayoutInflater.inflate(R.layout.shape_popup, null);
		mPopup = new PopupWindow(mShapesContentView, (int) mResources.getDimension(R.dimen.shapePopupWidth),
				(int) mResources.getDimension(R.dimen.shapePopupHeight), false);
		/*
		 * setBackgroundDrawable MUST MUST MUST be called in order for the popup to be
		 * dismissed properly
		 */
		mShapesGridView = (GridLayout) mShapesContentView.findViewById(R.id.shapesGrid);
		
//		mShapesGridView
		
		mPopup.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_bg));
		mPopup.setOutsideTouchable(true);
		mPopup.showAsDropDown(button, 0, 0);
	}
	
	public void showElementsPopup(View button) {
		mElementsContentView = mLayoutInflater.inflate(R.layout.elements_popup, null);
		mPopup = new PopupWindow(mElementsContentView, (int) mResources.getDimension(R.dimen.elementsPopupWidth),
				(int) mResources.getDimension(R.dimen.elementsPopupHeight), false);
		/*
		 * setBackgroundDrawable MUST MUST MUST be called in order for the popup to be
		 * dismissed properly
		 */
		mElementsGridView = (GridLayout) mElementsContentView.findViewById(R.id.elementsGrid);
		int numChildren = mElementsGridView.getChildCount();
		View v;
		for (int i=0; i<numChildren; i++) {
			v = mElementsGridView.getChildAt(i);
			v.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					uiElementTapped(v);
				}
			});
		}
		
		mPopup.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_bg));
		mPopup.setOutsideTouchable(true);
		mPopup.showAsDropDown(button, 0, 0);
	}
	
	
//	private void sandbox() {
//		ImageView image = createImageViewWithBitmap(createBitmapOfView(mSidebar));
//		image.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				screenElementTapped(v);
//			}
//		});
//		Dialog dia = new Dialog(this);
//		dia.setContentView(image);
//		dia.show();
//	}
	
	/*
	 * THIS PROBABLY SHOULDN'T BE USED IN REALITY.
	 * WE SHOULD CREATE AN ARRAYLIST<IMAGEVIEW> TO STORE IMAGES, THEN USE
	 * AN ADAPTER TO FILL THE GRIDVIEW.
	 */
//	private void addImageViewToGridView(ImageView image, View gView) {
//		gView.addView(image);
//	}
	
	
	/*****************************************************************************
	 * MISC METHODS
	 *****************************************************************************/
	
	private Bitmap createBitmapOfView(View theView) {
		Bitmap b = Bitmap.createBitmap(theView.getWidth(), theView.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(b);
		theView.draw(c);
		return b;
	}
	private ImageView createImageViewWithBitmap(Bitmap bitmap) {
		ImageView image = new ImageView(getApplicationContext());
		image.setImageBitmap(bitmap);
		return image;
	}
	//------------- Testing bitmap background ----------------
	private MAScreenElement createElement(Bitmap back) {
		MAScreenElement element = new MAScreenElement(getApplicationContext(), mScreen);
		element.setBackgroundDrawable(new BitmapDrawable(back));
		
		return element;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{ 
	   if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) { 
	       // Do your thing 
		   makeToast("ACHAL ROX MY SOX");
	       return true;
	   } else {
	       return super.onKeyDown(keyCode, event); 
	   }
	}
	
	public void makeToast(String format, Object... args) {
		Toast.makeText(getApplicationContext(),
				String.format(format, args), Toast.LENGTH_SHORT).show();
	}
}
