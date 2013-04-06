/**
 * 
 */
package edu.berkeley.cs160.onesies.metaapp;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * @author andre
 *
 */
public class MAProject {
	
	private Hashtable<String, MAScreen> mScreens;

	/**
	 * 
	 */
	public MAProject() {
		// TODO Auto-generated constructor stub
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
	
	public ArrayList<String> getScreenNames() {
		// IS this okay???
		return new ArrayList<String>(mScreens.keySet());
	}

}
