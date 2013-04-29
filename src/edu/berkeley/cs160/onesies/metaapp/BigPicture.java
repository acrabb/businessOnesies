/**
 * 
 */
package edu.berkeley.cs160.onesies.metaapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

/**
 * @author andre
 *
 */
public class BigPicture extends Activity {
	
	private MAModel			mModel;
	
	private Intent			mIntent;
	
	private RelativeLayout	mMap;
	private float 			mScaleFactor = 1;
	
	private int				mDefaultScreenWidth = 300;
	private float			mLastX;
	private float			mLastY;
	private int				mLastTopMargin;
	private int				mLastLeftMargin;
	
	
	private Canvas			mCanvas;
	private Bitmap			mBitmap;
	
	private boolean			mScreenWasDragged = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Inflate the menu; this adds items to the action bar if it is present.
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.big_picture_main);
		mModel = MAModel.getInstance();
		mMap = (RelativeLayout) findViewById(R.id.bigPicture);
		
		mIntent = getIntent();
		
//		mBitmap = Bitmap.createBitmap(mMap.getWidth(), mMap.getHeight(), Config.ARGB_8888);
//		mCanvas = new Canvas(mBitmap);
//		mMap.addView(mCanvas, mMap.getLayoutParams());			
			
		addAllToMap();
	}
	
	
	private void addAllToMap() {
		int width;
		int height;
		
		ArrayList<MAScreen> screens = mModel.getmCurrentProject().getScreens();
		MAScreen m;
		for(int i=0; i < screens.size(); i++)
		{
			m = screens.get(i);
			// get the scale factor based on how many screens are to be displayed
			
			// create an image view of the screen
			final ImageView iv = DevelopmentActivity.createImageViewWithBitmapForContext(
					DevelopmentActivity.createBitmapOfView(m)
					, this);
			iv.setTag(i);
			// set the onTouch for the view
			iv.setOnTouchListener(new View.OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					RelativeLayout.LayoutParams params;
					params = (RelativeLayout.LayoutParams) iv.getLayoutParams();
					float x = event.getX();
					float y = event.getY();
					switch(event.getActionMasked()) {
						case MotionEvent.ACTION_DOWN:
							mLastX = x;
							mLastY = y;
							mLastTopMargin = params.topMargin;
							mLastLeftMargin = params.leftMargin;
							Log.d("ACACAC", String.format("DOWN"));
							break;
						case MotionEvent.ACTION_MOVE:
							/**/
							int horizDiff = (int)(x-mLastX);
							int vertDiff = (int)(y-mLastY);
//							Log.d("ACACAC", String.format("dx:%d, dy:%d", horizDiff, vertDiff));
//							if (Math.abs(horizDiff) > 20 || Math.abs(vertDiff) > 20) {
								mScreenWasDragged = true;
								params.leftMargin += horizDiff;
								params.topMargin += vertDiff;
								params.rightMargin -= horizDiff;
								params.bottomMargin -= vertDiff;
								iv.setLayoutParams(params);
//							}
							/**/
							break;
						case MotionEvent.ACTION_UP:
							if (!mScreenWasDragged) {
								screenWasTapped(iv);
							}
							mScreenWasDragged = false;
							break;
						default:
							break;
					}
					iv.invalidate();
					return true;
				}
			});
			
			// add the screen to the relative layout.
			addToMap(iv);
		}
	}
	
	private void addToMap(ImageView iv) {
		View v;
			// set the width/height of each screen according to the scale factor
		int width = (int) (mDefaultScreenWidth * mScaleFactor);
		int height = (int) (width*16/10 * mScaleFactor);
		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(width, height);
		
		for (int i = 0; i < mMap.getChildCount(); i++) {
			v = mMap.getChildAt(i);
			p.leftMargin += width + 20;
		}
		
		iv.setLayoutParams(p);
		mMap.addView(iv);
	}
	
	private void screenWasTapped(View iv){
		if(mIntent.getBooleanExtra("RETURN_TAPPED", false)) {
			finishWithIndex((Integer) iv.getTag());
		}
	}
	
	private void finishWithIndex(int i)
	{
	   Intent intent = new Intent(BigPicture.this, DevelopmentActivity.class);
	   intent.putExtra("INDEX", i);
	   if (getParent() == null) {
		   setResult(Activity.RESULT_OK, intent);
	   } else {
		   getParent().setResult(Activity.RESULT_OK, intent);
	   }
	   finish();
	}

}
