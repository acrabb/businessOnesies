package edu.berkeley.cs160.onesies.metaapp;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Join;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class MACanvas extends View {
	private Canvas			mCanvas;
	private Bitmap			mBitmap;
	private ArrayList<Path> mPaths;
	private Path			mPath;
	private Paint			mPaint;
	private int 			mBackgroundColor = R.color.canvasColor;
	private int 			mBrushSize = 15;
	
	private float			mX;
	private float			mY;
	private float			mLeftMost=Float.MAX_VALUE;
	private float			mRightMost=0;
	private float			mTopMost=Float.MAX_VALUE;
	private float			mBottomMost=0;
	
	// ----- Top and Left margins of drawn bitmap. Essentially: (x,y)
	private int			t = 0;
	private int			l= 0;
	
	public MACanvas(Context context) {
		super(context);
		this.setBackgroundColor(getResources().getColor(mBackgroundColor));
		
		mPaths = new ArrayList<Path>();
		mPaint = new Paint();
		mPaint.setColor(Color.BLACK);
		mPaint.setStrokeWidth(mBrushSize);
		mPaint.setStyle(Paint.Style.STROKE);
		// These two settings affect whether or not dots are allowed. (+1 more below)
		mPaint.setStrokeJoin(Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setDither(true);
		
		this.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mX = event.getX();
				mY = event.getY();
				if(mX < mLeftMost){
					mLeftMost = mX;
				}
				if (mX > mRightMost){
					mRightMost = mX;
				}
				if(mY < mTopMost){
					mTopMost = mY;
				}
				if (mY > mBottomMost){
					mBottomMost = mY;
				}
				
				switch(event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						mPath = new Path();
						mPaths.add(mPath);
						mPath.moveTo(event.getX(), event.getY());
						// +1 and +1 to allow for dots with settings above.
						mPath.lineTo(event.getX()+1, event.getY()+1);
						break;
					case MotionEvent.ACTION_MOVE:
						mPath.lineTo(event.getX(), event.getY());
						break;
					case MotionEvent.ACTION_UP:
						mPath.lineTo(event.getX(), event.getY());
						break;
					default:
						break;
				}
				invalidate();
				return true;
			}
		});
	}
	
	public Bitmap getSketch() {
		int buffer = 8;
		t = Math.max(0, (int) mTopMost-buffer);
		l = Math.max(0, (int) mLeftMost-buffer);
		
//		Display display = getWindowManager().getDefaultDisplay();
		
		// Trying to fix custom element crash.
//		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//		Display display = wm.getDefaultDisplay();
//		
//		Point size = new Point();
//		display.getSize(size);
//		int right = size.x;
//		int bottom = size.y;
		int right = mCanvas.getWidth();
		int bottom = mCanvas.getHeight();
		
		
		
		int b = Math.min(bottom, (int) mBottomMost+buffer);
		int r = Math.min(right, (int) mRightMost+buffer);
		int height = b-t;
		int width = r-l;
		if(width < 0 || height < 0) {
			return null;
		}
		Log.i("ACACACAC", String.format(">>> Bit with h: %d, w:%d", height, width));
		Bitmap bit = Bitmap.createBitmap(mBitmap, l, t, width, height);
		return bit;
	}
	public void clear() {
		mPaths.clear();
		mLeftMost=Float.MAX_VALUE;
		mRightMost=0;
		mTopMost=Float.MAX_VALUE;
		mBottomMost=0;
		
		invalidate();
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		// Redraw the canvas with all current shapes/lines/etc.
//		if(mBitmap == null) {
			mBitmap = Bitmap.createBitmap(canvas.getWidth(),
					canvas.getHeight(), Bitmap.Config.ALPHA_8);
			mCanvas = new Canvas(mBitmap);
//		}
		// Draw all paths with their appropriate/original width.
		for(Path path : mPaths) {
			if(path != null) {
				mPaint.setStrokeWidth(mBrushSize);
				mCanvas.drawPath(path, mPaint);
			}
		}
		// Set the width back to what's been selected.
		canvas.drawBitmap(mBitmap, 0, 0, null);
		mPaint.setStrokeWidth(mBrushSize);
	}

	public int getTopMargin() {
		return t;
	}

	public int getLeftMargin() {
		return l;
	}

}
