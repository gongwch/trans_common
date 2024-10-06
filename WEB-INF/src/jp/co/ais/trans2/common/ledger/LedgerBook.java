package jp.co.ais.trans2.common.ledger;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;

/**
 * ���[�u�b�N�N���X�ł��B<br>
 * ���[�́A�����y�[�W�̃V�[�g����\������܂��B<br>
 * 1�̃V�[�g�́A�w�b�_�[�A���ׁA�t�b�^�[����\������܂��B<br>
 * 
 * @author AIS
 */
public class LedgerBook implements Serializable {

	/**  */
	private static final long serialVersionUID = 6717516728568642593L;

	/** �V�[�g */
	protected List<LedgerSheet> sheet;

	/** �ڎ� */
	protected List<LedgerContents> contents;

	/** 1�V�[�g������̕\���s�� */
	protected int maxRowCount;

	/** ���ݕێ����Ă���s�� */
	protected int currentRowCount = 0;

	/** �f�t�H���g��1�y�[�W������̕\���s�� */
	public final static int DEFAULT_MAX_ROW_COUNT = 30;

	/** ������ */
	protected String currentDate = null;

	/** �^�C�g�� */
	protected String title = null;

	/**
	 * �R���X�g���N�^
	 */
	public LedgerBook() {

		maxRowCount = DEFAULT_MAX_ROW_COUNT;

		sheet = new ArrayList<LedgerSheet>();
		contents = new ArrayList<LedgerContents>();

	}

	/**
	 * ���������擾���܂��B
	 * 
	 * @param lngCode
	 * @return ������
	 */
	public String getCurrentDate(String lngCode) {

		if (currentDate == null) {
			setCurrentDate(lngCode);
		}
		return currentDate.substring(0, 10);

	}

	/**
	 * �������Ԃ��擾���܂��B
	 * 
	 * @param lngCode
	 * @return ��������
	 */
	public String getCurrentTime(String lngCode) {

		if (currentDate == null) {
			setCurrentDate(lngCode);
		}
		return currentDate.substring(11, currentDate.length());

	}

	/**
	 * �o�͎��̃^�C���X�^���v�Z�b�g
	 * 
	 * @param lngCode
	 */
	private void setCurrentDate(String lngCode) {
		currentDate = Util.getCurrentDateString(lngCode);
	}

	/**
	 * �V�[�g��ǉ����܂��B
	 * 
	 * @param gedgerSheet �V�[�g
	 */
	public void addSheet(LedgerSheet gedgerSheet) {
		this.sheet.add(gedgerSheet);
	}

	/**
	 * �S�ẴV�[�g��Ԃ��܂��B
	 * 
	 * @return List<���[�V�[�g�N���X>
	 */
	public List<LedgerSheet> getSheet() {
		return sheet;
	}

	/**
	 * �w��̃C���f�b�N�X�̃V�[�g��Ԃ��܂��B
	 * 
	 * @param index �C���f�b�N�X
	 * @return ���[�V�[�g�N���X
	 */
	public LedgerSheet getSheetAt(int index) {
		return sheet.get(index);
	}

	/**
	 * �w�b�_�[��ǉ����܂��B
	 * 
	 * @param header
	 */
	public void addHeader(LedgerSheetHeader header) {
		LedgerSheet newSheet = new LedgerSheet();
		newSheet.setHeader(header);
		addSheet(newSheet);
		currentRowCount = 0;
	}

	/**
	 * ���׍s��ǉ����܂��B
	 * 
	 * @param detail
	 */
	public void addDetail(LedgerSheetDetail detail) {

		// �s���������̏ꍇ�͖������ɒǉ�����B
		if (maxRowCount <= 0) {
			getLastSheet().addDetail(detail);
			return;
		}

		// �y�[�W�̐擪�̏ꍇ�͋󔒍s�͒ǉ����Ȃ�
		if (detail.isBlank()) {
			if (getLastSheet().getRowCount() == maxRowCount || getLastSheet().getRowCount() == 0) {
				return;
			}
		}

		// ���݂̃V�[�g���ő�s�ɒB���Ă���ꍇ�A�w�b�_�[���R�s�[���A���̃V�[�g�ɖ��ׂ�ǉ�����
		if (getLastSheet().getRowCount() == maxRowCount) {
			addHeader(getLastSheet().getHeader());
			addDetail(detail);
		} else {
			getLastSheet().addDetail(detail);
		}

	}

	/**
	 * �\���s�����w�肵�Ė��׍s��ǉ����܂��B<br>
	 * ��addDetail(LedgerSheetDetail detail)�ƕ��p����ƃo�O�̌��Ȃ̂Œ���
	 * 
	 * @param detail
	 * @param addCount �ǉ��s��
	 */
	public void addDetail(LedgerSheetDetail detail, int addCount) {

		// �s���������̏ꍇ�͖������ɒǉ�����B
		if (maxRowCount <= 0) {
			getLastSheet().addDetail(detail);
			currentRowCount += addCount;
			return;
		}

		// �y�[�W�̐擪�̏ꍇ�͋󔒍s�͒ǉ����Ȃ�
		if (detail.isBlank()) {
			if (currentRowCount == maxRowCount || currentRowCount == 0) {
				return;
			}
		}

		// ���肫��Ȃ��ꍇ�͐V�K�y�[�W�𐶐�����
		if (currentRowCount != 0 && currentRowCount + addCount > maxRowCount) {
			addHeader(getLastSheet().getHeader());
			addDetail(detail);
			currentRowCount += addCount;
			return;
		}

		// ���݂̃V�[�g���ő�s�ɒB���Ă���ꍇ�A�w�b�_�[���R�s�[���A���̃V�[�g�ɖ��ׂ�ǉ�����
		if (currentRowCount == maxRowCount) {
			addHeader(getLastSheet().getHeader());
			addDetail(detail);
			currentRowCount += addCount;

		} else {
			getLastSheet().addDetail(detail);
			currentRowCount += addCount;

		}

	}

	/**
	 * ���׍s���܂Ƃ߂Ēǉ����܂��B<br>
	 * �ǉ�����y�[�W�ɏ\���ȗ]�������������ꍇ�A���̃y�[�W����ǉ����܂��B
	 * 
	 * @param detail
	 */
	public void addDetailsWhenNoSpaceThenNewPage(List<? extends LedgerSheetDetail> detail) {

		// �s���������̏ꍇ�͖������ɒǉ�����B
		if (maxRowCount <= 0) {
			for (LedgerSheetDetail bean : detail) {
				addDetail(bean);
			}
			return;
		}

		// ���肫��Ȃ��ꍇ�͐V�K�y�[�W�𐶐�����
		if (getLastSheet().getRowCount() != 0 && getLastSheet().getRowCount() + detail.size() > maxRowCount) {
			addHeader(getLastSheet().getHeader());
		}

		for (LedgerSheetDetail bean : detail) {
			addDetail(bean);
		}

	}

	/**
	 * 1�y�[�W������̕\���s��getter
	 * 
	 * @return Max�s
	 */
	public int getMaxRowCount() {
		return maxRowCount;
	}

	/**
	 * 1�y�[�W������̕\���s��setter
	 * 
	 * @param maxRowCount
	 */
	public void setMaxRowCount(int maxRowCount) {
		this.maxRowCount = maxRowCount;
	}

	/**
	 * @return ���[�V�[�g�N���X
	 */
	public LedgerSheet getLastSheet() {
		return sheet.get(sheet.size() - 1);
	}

	/**
	 * ���[�̑��Ő���Ԃ��܂��B
	 * 
	 * @return ���Ő�
	 */
	public int getSheetCount() {
		return sheet.size();
	}

	/**
	 * �ڎ���ǉ�
	 * 
	 * @param ledgerContents ���[�ڎ��N���X
	 */
	public void addContents(LedgerContents ledgerContents) {

		this.contents.add(ledgerContents);
	}

	/**
	 * �ڎ���Ԃ�
	 * 
	 * @return List<���[�ڎ��N���X>
	 */
	public List<LedgerContents> getContents() {
		return contents;
	}

	/**
	 * �^�C�g����Ԃ�
	 * 
	 * @return �^�C�g��
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * �^�C�g�����Z�b�g����
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * �v�f�����邩
	 * 
	 * @return isEmpty
	 */
	public boolean isEmpty() {
		return (sheet == null || sheet.isEmpty());
	}

	/**
	 * ���݂̍ŏI�V�[�g�̍ŏI�s��Ԃ�
	 * 
	 * @return ���݂̍ŏI�V�[�g�̍ŏI�s
	 */
	public LedgerSheetDetail getCurrentDetail() {
		if (isEmpty()) {
			return null;
		}
		LedgerSheet lastSheet = getLastSheet();
		// ���݃V�[�g�ɍs�������ꍇ�͑O�V�[�g�̍ŏI�s
		if (lastSheet.detail == null || lastSheet.detail.isEmpty()) {
			if (sheet.size() > 1) {
				lastSheet = sheet.get(sheet.size() - 2);
				if (lastSheet.detail == null || lastSheet.detail.isEmpty()) {
					return null;
				}
				return lastSheet.detail.get(lastSheet.detail.size() - 1);
			}
			return null;
		}
		return lastSheet.detail.get(lastSheet.detail.size() - 1);
	}

}
