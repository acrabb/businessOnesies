package edu.berkeley.cs160.onesies.metaapp;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class Element {

	private float centerx;
	private float centery;
	private String text;
	private ArrayList<Point> vertices;
	private float top,left,right,bottom;
	private final Paint paint = new Paint();
	
	public Element(final float centerx, final float centery) {
		this.centerx = centerx;
		this.centery = centery;
		vertices = new ArrayList<Point>();
		this.createElement();
	}
	
	public float getX() {
		return centerx;
	}
	
	public float getY() {
		return centery;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	//this method will be overridden by subclasses to create different polygons
	public void createElement() {
		final float height = 50;
		final float width = 200;
		left = centerx - width;
		right = centerx + width;
		top = centerx + height;
		bottom = centerx - height;
		Point p1 = new Point(left, centery + height);
		p1.addNeighbor(centerx - width, centery - height);
		p1.addNeighbor(centerx + width, centery + height);
		Point p4 = new Point(centerx + width, centery - height);
		p4.addNeighbor(centerx - width, centery - height);
		p4.addNeighbor(centerx + width, centery + height);
		vertices.add(p1);
		vertices.add(p4);
	}
	
	public boolean contains(final float x, final float y) {
		return y < top && y > bottom && x > left && x < right;
	}
	//this draw method should get passed a canvas to which this should be drawn on 
	public void drawElement(Canvas canvas) {
		for (Point p : vertices) 
			p.drawPointAndNeighbors(canvas);
		
		Paint paint1 = new Paint();
		paint1.setColor(Color.BLUE);
		paint1.setTextSize(20); 
		paint1.setTextAlign(Paint.Align.CENTER);
		paint1.setTypeface(Typeface.DEFAULT_BOLD);
		canvas.drawText(text, centerx, centery, paint1);
	}
	
	public class Point {
		final private ArrayList<Point> neighbors = new ArrayList<Point>();
		private float x,y;
		
		public Point(final float x, final float y) {
			this.x = x;
			this.y = y;
		}
		
		public float getX() {
			return x;
		}
		
		public float getY() {
			return y;
		}
		
		public void addNeighbor(final float x, final float y) {
			final Point neighbor = new Point(x,y);
			neighbors.add(neighbor);
		}
		
		//this draw method is called by a UIElement and does the literally drawing on canvas
		public void drawPointAndNeighbors(Canvas canvas) {
			paint.setStrokeWidth(10);
			paint.setColor(Color.RED);
			for (Point neighbor : neighbors)
				canvas.drawLine(x, y, neighbor.getX(), neighbor.getY(), paint);
		}
	}
}
