package jp.co.ais.trans2.common.gui.ac;

import jp.co.ais.trans2.model.*;

/**
 * 
 */
public class CommitAdapter implements CommitListener {

	/**
	 * �l�I����̏���
	 * 
	 * @param value
	 */
	public void commit(Object value) {
		//
	}

	/**
	 * �v�b�V������l���擾
	 * 
	 * @param value
	 * @return �ݒ�l
	 */
	public String getText(Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof AutoCompletable) {
			return ((AutoCompletable) value).getName();
		} else if (value instanceof TReferable) {
			return ((TReferable) value).getNames();
		}

		return value.toString();
	}

	/**
	 * @return true: push���������s����
	 */
	public boolean isDoPush() {
		return false;
	}

}
