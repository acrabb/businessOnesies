package edu.berkeley.cs160.onesies.metaapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button mNewProjectButton;
	private Intent mIntent;
	private final double iPhoneScalar = 0.66; 
	private final static DisplayMetrics display = new DisplayMetrics();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindowManager().getDefaultDisplay().getMetrics(display);
		RelativeLayout activityMain = (RelativeLayout)findViewById(R.id.background);
		
		RelativeLayout iPhone = new RelativeLayout(this);
		final int height = (int) (iPhoneScalar * display.heightPixels);
		final int width = (int) (iPhoneScalar * display.widthPixels);
		final int leftOffset = (int) ((1- iPhoneScalar) * display.heightPixels / 3);
		final int topOffset = (int) ((1- iPhoneScalar) * display.widthPixels / 2);
		final int iPhoneID = this.getResources().getIdentifier("iphone4", "drawable", this.getPackageName());
		RelativeLayout.LayoutParams iPhoneParams = new RelativeLayout.LayoutParams(width, height);
		iPhoneParams.leftMargin = leftOffset;
		iPhoneParams.topMargin = topOffset;
		
		iPhone.setLayoutParams(iPhoneParams);
		iPhone.setBackgroundResource(iPhoneID);
		
		HomePagePhone screen = new HomePagePhone(this);
		RelativeLayout.LayoutParams screenParams = new RelativeLayout.LayoutParams(width - 100, (int) (height *iPhoneScalar));
		screenParams.leftMargin = 53;
		screenParams.topMargin = 138;
		screen.setLayoutParams(screenParams);
		
		iPhone.addView(screen);
		activityMain.addView(iPhone);
		System.out.println("height" + height
							+ " width" + width 
							+ " leftOffset" + leftOffset
							+ " topOffset" + topOffset
							+ " iPhoneID" + iPhoneID);
		
	
		/*
		mNewProjectButton = (Button) findViewById(R.id.newProjectButton);
		mNewProjectButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO set up new project
				// show DevelopmentActivity
				mIntent = new Intent(MainActivity.this, DevelopmentActivity.class);
//				mIntent.putExtra(getString(R.string.BRUSH_SIZE), mBrushSize);
				startActivity(mIntent);

			}
		});*/
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void makeToast(String format, Object... args) {
		Toast.makeText(getApplicationContext(),
				String.format(format, args), Toast.LENGTH_SHORT).show();
	}
	
	
	public static float distance(float x1, float y1, float x2, float y2) {
		float dist  = 0;
		dist = (float) Math.sqrt(Math.pow(Math.abs(x2-x1), 2) + Math.pow(Math.abs(y2-y1), 2));
//		dist = (float) Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
//		Math.sqrt(Math.pow(p1.x-p2.x, 2) + Math.pow(p1.y-p2.y, 2));
		return dist;
	}

}
