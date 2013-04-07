/**
 * 
 */
package edu.berkeley.cs160.onesies.metaapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;

/**
 * @author andre
 *
 */
public class MAProject {
	
	private Hashtable<String, MAScreen> mScreens;
	private String 						mName;
	/**
	 * 
	 */
	public MAProject(String name) {
		mName = name;
		mScreens = new Hashtable<String, MAScreen>();
	}
	
	
	public void addScreenToProject(String name, MAScreen screen) {
		mScreens.put(name, screen);
	}
	public void deleteScreenToProject(String name) {
		mScreens.remove(name);
	}
	public void renameScreen(String oldName, String newName) {
		MAScreen screen =  mScreens.remove(oldName);
		mScreens.put(newName, screen);
	}
	public MAScreen getScreenWithName(String name) {
		return mScreens.get(name);
	}
	public String getNextDefaultScreenName() {
		String name = String.format("Screen %d", mScreens.size()+1);
		return name;
	}
	public int getNumScreens(){
		return mScreens.size();
	}
	public ArrayList<MAScreen> getScreens() {
		ArrayList<MAScreen> retVal = new ArrayList<MAScreen>(mScreens.values());
		
		Collections.sort(retVal, new Comparator<MAScreen>() {
			@Override
			public int compare(MAScreen one, MAScreen other) {
				// TODO Auto-generated method stub
				return one.getName().compareToIgnoreCase(other.getName());
			}
		});
		
		return retVal;
	}
	
	public ArrayList<String> getScreenNames() {
		// IS this okay???
		ArrayList<String> retVal = new ArrayList<String>(mScreens.keySet());
		Collections.sort(retVal);
		return retVal;
	}

}
