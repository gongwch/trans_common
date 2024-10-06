package jp.co.ais.trans2.common.gui.table;

import java.awt.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �`���[�g�f�[�^����
 */
public class TChartDetailItem extends TransferBase implements Cloneable, TChartBarInterface {

	/** �J�n�� */
	protected Date from = null;

	/** �I���� */
	protected Date to = null;

	/** �\���F */
	protected Color fillColor = null;

	/** �\������ */
	protected String label = null;

	/** �\���������� */
	protected String shortLabel = null;

	/** �t�b�^�[���� */
	protected String footerLabel = null;

	/** �\�������F */
	protected Color foreColor = null;

	/** �Ώۏ�� */
	protected Object obj = null;

	/** �`�恡 */
	protected Rectangle rectangle = null;

	/**
	 * �R���X�g���N�^�[
	 */
	public TChartDetailItem() {
		//
	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param from
	 * @param to
	 * @param fillColor
	 * @param label
	 * @param shortLabel
	 * @param footerLabel
	 * @param foreColor
	 * @param obj
	 */
	public TChartDetailItem(Date from, Date to, Color fillColor, String label, String shortLabel, String footerLabel,
		Color foreColor, Object obj) {
		this.from = from;
		this.to = to;
		this.fillColor = fillColor;
		this.label = label;
		this.shortLabel = shortLabel;
		this.footerLabel = footerLabel;
		this.foreColor = foreColor;
		this.obj = obj;
	}

	/**
	 * �J�n���̎擾
	 * 
	 * @return from �J�n��
	 */
	public Date getFrom() {
		return from;
	}

	/**
	 * �J�n���̐ݒ�
	 * 
	 * @param from �J�n��
	 */
	public void setFrom(Date from) {
		this.from = from;
	}

	/**
	 * �I�����̎擾
	 * 
	 * @return to �I����
	 */
	public Date getTo() {
		return to;
	}

	/**
	 * �I�����̐ݒ�
	 * 
	 * @param to �I����
	 */
	public void setTo(Date to) {
		this.to = to;
	}

	/**
	 * �\���F�̎擾
	 * 
	 * @return fillColor �\���F
	 */
	public Color getFillColor() {
		return fillColor;
	}

	/**
	 * �\���F�̐ݒ�
	 * 
	 * @param fillColor �\���F
	 */
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	/**
	 * �\�������̎擾
	 * 
	 * @return label �\������
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * �\�������̐ݒ�
	 * 
	 * @param label �\������
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * �\����������̎擾
	 * 
	 * @return shortLabel �\����������
	 */
	public String getShortLabel() {
		return shortLabel;
	}

	/**
	 * �\����������̐ݒ�
	 * 
	 * @param shortLabel �\����������
	 */
	public void setShortLabel(String shortLabel) {
		this.shortLabel = shortLabel;
	}

	/**
	 * �t�b�^�[�����̎擾
	 * 
	 * @return footerLabel �t�b�^�[����
	 */
	public String getFooterLabel() {
		return footerLabel;
	}

	/**
	 * �t�b�^�[�����̐ݒ�
	 * 
	 * @param footerLabel �t�b�^�[����
	 */
	public void setFooterLabel(String footerLabel) {
		this.footerLabel = footerLabel;
	}

	/**
	 * �\�������F�̎擾
	 * 
	 * @return foreColor �\�������F
	 */
	public Color getForeColor() {
		return foreColor;
	}

	/**
	 * �\�������F�̐ݒ�
	 * 
	 * @param foreColor �\�������F
	 */
	public void setForeColor(Color foreColor) {
		this.foreColor = foreColor;
	}

	/**
	 * �Ώۏ��̎擾
	 * 
	 * @return obj �Ώۏ��
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * �Ώۏ��̐ݒ�
	 * 
	 * @param obj �Ώۏ��
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}

	/**
	 * �`�恡�̎擾
	 * 
	 * @return rectangle �`�恡
	 */
	public Rectangle getRectangle() {
		return rectangle;
	}

	/**
	 * �`�恡�̐ݒ�
	 * 
	 * @param rectangle �`�恡
	 */
	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

}
