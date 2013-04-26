package edu.berkeley.cs160.onesies.metaapp;
/*
 * Enum denoting different kinds of changes that
 * can be reverted by Undo Button
 */
public enum UndoStatus {

	ADD {
		public String toString() {
			return "undoAdd";
		}
	},
	
	REMOVE {
		public String toString() {
			return "undoRemove";
		}
	},
	
	TEXT {
		public String toString() {
			return "undoText";
		}
	},
	
	MOVE {
		public String toString() {
			return "undoMove";
		}
	},
	
	RESIZE {
		public String toString() {
			return "undoResize";
		}
	};
}
