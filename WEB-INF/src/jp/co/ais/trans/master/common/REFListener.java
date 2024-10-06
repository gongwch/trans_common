package jp.co.ais.trans.master.common;

import java.util.*;

/**
 * 
 */
public interface REFListener {

	/**
	 * @return Map
	 */
	public abstract Map primaryKeysAppending();

	/**
	 * 
	 */
	public abstract void goodCodeInputted();

	/**
	 * 
	 */
	public abstract void badCodeInputted();

	/**
	 * 
	 */
	public abstract void textCleared();

	/**
	 * 
	 */
	public abstract void codeChanged();
}
