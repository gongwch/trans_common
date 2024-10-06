package jp.co.ais.trans2.common.ledger;

import java.io.*;

import jp.co.ais.trans2.common.report.*;

/**
 * ���[�u�b�N�����C�A�E�g�N���X�ł��B<br>
 * ���[�́A�����y�[�W�̃V�[�g����\������܂��B<br>
 * 1�̃V�[�g�́A�w�b�_�[�A���ׁA�t�b�^�[����\������܂��B<br>
 * 
 * @author AIS
 */
public class LedgerBookAndLayout implements Serializable {

	/**
	 * book
	 */
	protected LedgerBook book;

	/**
	 * layout
	 */
	protected TReportLayout layout;

	public LedgerBookAndLayout(LedgerBook book, TReportLayout layout) {
		this.book = book;
		this.layout = layout;
	}

	public LedgerBook getBook() {
		return book;
	}

	public void setBook(LedgerBook book) {
		this.book = book;
	}

	public TReportLayout getLayout() {
		return layout;
	}

	public void setLayout(TReportLayout layout) {
		this.layout = layout;
	}

}
