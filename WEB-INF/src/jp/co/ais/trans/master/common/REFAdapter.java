package jp.co.ais.trans.master.common;

import java.util.*;

/**
 * 
 */
public abstract class REFAdapter implements REFListener {

	public Map primaryKeysAppending() {
		return null;
	}

	public void goodCodeInputted() {
		// do nothing
	}

	public void badCodeInputted() {
		// do nothing
	}

	public void textCleared() {
		badCodeInputted();
	}

	public void codeChanged() {
		// do nothing
	}
}
