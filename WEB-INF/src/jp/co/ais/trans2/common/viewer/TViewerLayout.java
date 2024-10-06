package jp.co.ais.trans2.common.viewer;

import java.util.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans2.common.ledger.*;

/**
 * ���[Viewer�̃��C�A�E�g���
 * 
 * @author AIS
 */
public abstract class TViewerLayout {

	/** LedgerBook */
	protected LedgerBook book;

	/**
	 * Viewer�̏�����
	 * 
	 * @param viewer
	 * @throws TException
	 */
	public abstract void init(TFrameViewer viewer) throws TException;

	/**
	 * �w�b�_�[�`��
	 * 
	 * @param header �w�b�_�[
	 * @throws TException
	 */
	public abstract void drawHeader(LedgerSheetHeader header) throws TException;

	/**
	 * ���ו`��
	 * 
	 * @param detail
	 * @throws TException
	 */
	public abstract void drawDetail(List<LedgerSheetDetail> detail) throws TException;

	/**
	 * Book�擾
	 * 
	 * @return Book
	 */
	public LedgerBook getBook() {
		return book;
	}

	/**
	 * Book�ݒ�
	 * 
	 * @param book Book
	 */
	public void setBook(LedgerBook book) {
		this.book = book;
	}

	/**
	 * ���O�C�����[�U�̌���R�[�h�ɑ΂���P�ꕶ����Ԃ�.
	 * 
	 * @param wordID �P��ID
	 * @return �P�ꕶ��
	 */
	public String getWord(String wordID) {
		String lang = TClientLoginInfo.getInstance().getUserLanguage();
		return MessageUtil.getWord(lang, wordID);
	}

	/**
	 * ���O�C�����[�U�̌���R�[�h�ɑ΂���P�ꕶ��(����)��Ԃ�.
	 * 
	 * @param wordID �P��ID
	 * @return �P�ꕶ��(����)
	 */
	public String getShortWord(String wordID) {
		String lang = TClientLoginInfo.getInstance().getUserLanguage();
		return MessageUtil.getShortWord(lang, wordID);
	}
}
