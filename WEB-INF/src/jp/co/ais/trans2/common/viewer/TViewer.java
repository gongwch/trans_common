package jp.co.ais.trans2.common.viewer;

import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans2.common.ledger.LedgerBook;

/**
 * Book�\���C���^�[�t�F�[�X
 * @author AIS
 *
 */
public interface TViewer {

	/**
	 * Book��\������
	 * @param book
	 * @param layout
	 */
	public void show(LedgerBook book, TViewerLayout layout) throws TException;

}
