package jp.co.ais.trans2.common.gui.table;

import java.awt.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * �`���[�g�f�[�^
 */
public class TChartItem extends TransferBase implements Cloneable {

	/** �\�����ڃ��X�g */
	protected List<TChartDetailItem> itemList = null;

	/** �c�[���`�b�v */
	protected String toolTipText = null;

	/** �I�𒆍��WX */
	protected int selectedX = -1;

	/** �I�𒆍��WY */
	protected int selectedY = -1;

	/** �E�v���׊e�s�f�[�^���X�g */
	protected List<TChartDetailItem> summaryDetailItemList = null;

	/** �s�����ʎw�� */
	protected int summaryRowHeight = 0;

	/** DETAIL���[�h�{���� */
	protected int detailModeRowHeight = 0;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public TChartItem clone() {
		try {
			TChartItem clone = (TChartItem) super.clone();
			if (itemList != null) clone.itemList = new ArrayList<TChartDetailItem>(itemList);
			return clone;

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * �R���X�g���N�^�[
	 */
	public TChartItem() {
		this.itemList = new ArrayList<TChartDetailItem>();
	}

	/**
	 * @return �f�[�^����
	 */
	public int getCount() {
		return itemList.size();
	}

	/**
	 * @return true:�󃊃X�g
	 */
	public boolean isEmpty() {
		return itemList.isEmpty();
	}

	/**
	 * @param i
	 * @return ���׍���
	 */
	public TChartDetailItem get(int i) {
		return itemList.get(i);
	}

	/**
	 * @return TChartDetailItem
	 */
	public TChartDetailItem createItem() {
		return new TChartDetailItem();
	}

	/**
	 * @param from
	 * @param to
	 * @param fillColor
	 * @param label
	 * @param shortLabel
	 * @param footerLabel
	 * @param foreColor
	 * @param obj
	 * @return TChartDetailItem
	 */
	public TChartDetailItem createItem(Date from, Date to, Color fillColor, String label, String shortLabel,
		String footerLabel, Color foreColor, Object obj) {
		return new TChartDetailItem(from, to, fillColor, label, shortLabel, footerLabel, foreColor, obj);
	}

	/**
	 * ���ڒǉ�
	 * 
	 * @param from �J�n��
	 * @param to �I����
	 * @param fillColor �\���F
	 * @param label �\������
	 * @param shortLabel �\����������
	 * @param footerLabel �t�b�^�[���x��
	 * @param foreColor �\�������F
	 * @param obj ���f�[�^
	 */
	public void addItem(Date from, Date to, Color fillColor, String label, String shortLabel, String footerLabel,
		Color foreColor, Object obj) {
		TChartDetailItem item = createItem(from, to, fillColor, label, shortLabel, footerLabel, foreColor, obj);
		itemList.add(item);
	}

	/**
	 * �\�[�g
	 */
	public void sort() {
		if (itemList == null) {
			return;
		}

		// from-to���я��Ń\�[�g
		TChartDetailItemComparator comparator = new TChartDetailItemComparator();
		Collections.sort(itemList, comparator);
	}

	/**
	 * ���ڃN���A
	 */
	public void clear() {
		itemList.clear();
	}

	/**
	 * �\�����ڃ��X�g�̎擾
	 * 
	 * @return itemList �\�����ڃ��X�g
	 */
	public List<TChartDetailItem> getItemList() {
		return itemList;
	}

	/**
	 * �\�����ڃ��X�g�̐ݒ�
	 * 
	 * @param itemList �\�����ڃ��X�g
	 */
	public void setItemList(List<TChartDetailItem> itemList) {
		this.itemList = itemList;
	}

	/**
	 * �c�[���`�b�v�̎擾
	 * 
	 * @return toolTipText �c�[���`�b�v
	 */
	public String getToolTipText() {
		return toolTipText;
	}

	/**
	 * �c�[���`�b�v�̐ݒ�
	 * 
	 * @param toolTipText �c�[���`�b�v
	 */
	public void setToolTipText(String toolTipText) {
		this.toolTipText = toolTipText;
	}

	/**
	 * �I�𒆍��WX�̎擾
	 * 
	 * @return selectedX �I�𒆍��WX
	 */
	public int getSelectedX() {
		return selectedX;
	}

	/**
	 * �I�𒆍��WX�̐ݒ�
	 * 
	 * @param selectedX �I�𒆍��WX
	 */
	public void setSelectedX(int selectedX) {
		this.selectedX = selectedX;
	}

	/**
	 * �I�𒆍��WY�̎擾
	 * 
	 * @return selectedY �I�𒆍��WY
	 */
	public int getSelectedY() {
		return selectedY;
	}

	/**
	 * �I�𒆍��WY�̐ݒ�
	 * 
	 * @param selectedY �I�𒆍��WY
	 */
	public void setSelectedY(int selectedY) {
		this.selectedY = selectedY;
	}

	/**
	 * �E�v���׊e�s�f�[�^���X�g�̎擾
	 * 
	 * @return summaryDetailItemList �E�v���׊e�s�f�[�^���X�g
	 */
	public List<TChartDetailItem> getSummaryDetailItemList() {
		return summaryDetailItemList;
	}

	/**
	 * �E�v���׊e�s�f�[�^���X�g�̐ݒ�
	 * 
	 * @param summaryDetailItemList �E�v���׊e�s�f�[�^���X�g
	 */
	public void setSummaryDetailItemList(List<TChartDetailItem> summaryDetailItemList) {
		this.summaryDetailItemList = summaryDetailItemList;
	}

	/**
	 * �s�����ʎw��̎擾
	 * 
	 * @return summaryRowHeight �s�����ʎw��
	 */
	public int getSummaryRowHeight() {
		return summaryRowHeight;
	}

	/**
	 * �s�����ʎw��̐ݒ�
	 * 
	 * @param summaryRowHeight �s�����ʎw��
	 */
	public void setSummaryRowHeight(int summaryRowHeight) {
		this.summaryRowHeight = summaryRowHeight;
	}

	/**
	 * DETAIL���[�h�{�����̎擾
	 * 
	 * @return detailModeRowHeight DETAIL���[�h�{����
	 */
	public int getDetailModeRowHeight() {
		return detailModeRowHeight;
	}

	/**
	 * DETAIL���[�h�{�����̐ݒ�
	 * 
	 * @param detailModeRowHeight DETAIL���[�h�{����
	 */
	public void setDetailModeRowHeight(int detailModeRowHeight) {
		this.detailModeRowHeight = detailModeRowHeight;
	}

}
