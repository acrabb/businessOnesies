package edu.berkeley.cs160.onesies.metaapp;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;

import android.view.View;
import android.widget.RelativeLayout;

/*
 * UndoModel does the backend handling of undo's that can be
 * called on a given screen in development
 */
public class UndoModel {
	private ArrayDeque<View> recentElements;
	private ArrayDeque<UndoStatus> undoStatus;
	private ArrayDeque<RelativeLayout.LayoutParams> prevStates;
	private ArrayDeque<String> prevTexts;
	private MAScreen screen;
	private final int MAX_UNDO = 10;
	
	public UndoModel(MAScreen screen) {
		this.screen = screen;
		recentElements = new ArrayDeque<View>(MAX_UNDO);
		undoStatus = new ArrayDeque<UndoStatus>(MAX_UNDO);
		prevStates = new ArrayDeque<RelativeLayout.LayoutParams>(MAX_UNDO);
		prevTexts = new ArrayDeque<String>(MAX_UNDO);
	}
	
	/* UndoHandler calls underlying support from the actual screen themselves, which will take care of the revision.*/
	public void undoHandler() {
		try {
			final MAScreenElement screenElement = (MAScreenElement) recentElements.pop();
			final UndoStatus status = undoStatus.pop();
			final RelativeLayout.LayoutParams prevState = prevStates.pop();
			final String prevText = prevTexts.pop();
			
			switch (status) {
			case ADD:
				screen.undoAdd(screenElement);
				break;
			case REMOVE:
				screen.undoRemove(screenElement);
				break;
			case TEXT:				
				screen.undoText(screenElement, prevText);
				break;
			case MOVE:
				screen.undoMove(screenElement, prevState);
				break;
			case RESIZE:
				screen.undoResize(screenElement, prevState);
				break;
			default:
				break;
			}
		} catch (NoSuchElementException nse) {
			// doesn't do anything if there's nothing to be undone (i.e. deque is empty)
		}
	}
	
	/* Updates Model by adding most recent move to top of deque, this covers Add/Remove case, so it pushes dummy values
	 	to prevStates and prevTexts to increment it with other deque's properly. */
	public void updateModel(final MAScreenElement screenElement, final UndoStatus status) {
		recentElements.push(screenElement);
		undoStatus.push(status);
		prevStates.push(new RelativeLayout.LayoutParams(0,0));
		prevTexts.push("");
	}
	
	/* Overloaded updateModel where we cover Text case by updating prevTexts properly. */
	public void updateModel(final MAScreenElement screenElement, final UndoStatus status, final String text) {
		recentElements.push(screenElement);
		undoStatus.push(status);
		prevStates.push(new RelativeLayout.LayoutParams(0,0));
		if (text != null) {
			prevTexts.push(text);
		} else {
			prevTexts.push("");
		}
	}
	
	/* Overloaded updateModel where we cover Move/Resize case by updating prevStates properly. */
	public void updateModel(final MAScreenElement screenElement, final UndoStatus status, final RelativeLayout.LayoutParams prevState) {
		recentElements.push(screenElement);
		undoStatus.push(status);
		prevStates.push(prevState);
		prevTexts.push("");
	}
}
