package edu.berkeley.cs160.onesies.metaapp;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class Screen {

	private final ArrayList<Element> elements = new ArrayList<Element>();
	private final Paint paint = new Paint();
	private int backgroundColor = Color.GRAY;
	
	public Screen() {
		paint.setStrokeWidth(20);
		paint.setColor(Color.RED);
	}
	
	public void addElementToScreen(Element e) {
		elements.add(e);
	}
	
	public void drawScreen(Canvas canvas) {
		final int width = 400;
		final int height = 700;
		/*canvas.drawLine(0,0,width,0, paint);
		canvas.drawLine(0,0,0,height, paint);
		canvas.drawLine(width, height, 0, height, paint);
		canvas.drawLine(width, height, width, 0, paint);
		 */
		canvas.drawColor(backgroundColor);
		
		for (Element e : elements) {
			e.drawElement(canvas);
		}
	}
	
	public boolean clickedButton(final float x, final float y) {
		for (Element e : elements) {
			if (e.contains(x, y)) {
				return true;
			}
		}
		return false;
	}
	
}
