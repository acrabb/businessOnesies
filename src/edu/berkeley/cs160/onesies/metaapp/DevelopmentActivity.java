package edu.berkeley.cs160.onesies.metaapp;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;


public class DevelopmentActivity extends Activity {

	private MAScreen			mScreen;
	private RelativeLayout		mDevRelLayout;
	/*
	 * 0: Home button
	 * 1: Info button
	 * 2: Draw button
	 * 3: Shapes button
	 * 4: Elements button
	 */
	private ArrayList<Button>	sidebarButtons;
	private ArrayList<Integer>	sidebarButtonIds;
//	private Button homeButton;
//	private Button notesButton;
//	private Button drawButton;
//	private Button shapesButton;
//	private Button elementButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_main, menu);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.development_main);
		
		mDevRelLayout = (RelativeLayout) findViewById(R.id.developmentRelative);
		mScreen = new MAScreen(getApplicationContext());
//		mDevRelLayout.addView(mScreen);
		
		
	}
	
}
