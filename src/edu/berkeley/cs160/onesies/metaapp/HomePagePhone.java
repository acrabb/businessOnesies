package edu.berkeley.cs160.onesies.metaapp;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

public class HomePagePhone extends View {
	
	final int[] colours = {Color.GRAY, Color.BLUE, Color.CYAN};
	final float minSwipeDist = 80;
	final ArrayList<Screen> screens = new ArrayList<Screen>();
	private int index, modIndex;
	private float prevx;
	private Context context;
	public HomePagePhone(Context context) {
		super(context);
		this.context = context;
		index = 0;
		modIndex = index;
		final Element e = new Element(215, 250);
		e.setText("Create New");
		Screen s = new Screen();
		s.addElementToScreen(e);
		screens.add(s);
		
		final Element e1 = new Element(215, 160);
		e1.setText("DUMMY BUTTON");
		Screen s1 = new Screen();
		s1.addElementToScreen(e1);
		screens.add(s1);
	}
	
	public void onDraw(Canvas canvas) {
		screens.get(modIndex).drawScreen(canvas);

	}

	public boolean onTouchEvent(MotionEvent me) {
		final float currentX = me.getX();
		switch (me.getAction()) {
		case (MotionEvent.ACTION_DOWN):
			prevx = currentX;
			break;
		case (MotionEvent.ACTION_UP):
			final float distance = Math.abs(currentX - prevx);
			if (distance > minSwipeDist) {
				if (currentX > prevx) {
					index += 1;
				} else {
					index -= 1;
				}
				modIndex = ((index % screens.size()) + screens.size()) % screens.size();
			} else {
				boolean clicked = false;
				for (Screen s : screens) {
					clicked = s.clickedButton(currentX, me.getY());
					if (clicked) {
						break;
					}
				}
				if (clicked && modIndex == 0) {
					System.out.println("Clicked");
					Intent mIntent = new Intent(context, DevelopmentActivity.class);
					context.startActivity(mIntent);
				} 
			}
			invalidate();
		default:
			break;
	}
		return true;
	}
}
