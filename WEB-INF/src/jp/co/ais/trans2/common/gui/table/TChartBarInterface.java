package jp.co.ais.trans2.common.gui.table;

import java.awt.*;
import java.util.*;

/**
 * �`���[�g�̃o�[�\��I/F
 */
public interface TChartBarInterface {

	/**
	 * �J�n���̎擾
	 * 
	 * @return from �J�n��
	 */
	public Date getFrom();

	/**
	 * �I�����̎擾
	 * 
	 * @return to �I����
	 */
	public Date getTo();

	/**
	 * �\���F�̎擾
	 * 
	 * @return fillColor �\���F
	 */
	public Color getFillColor();

	/**
	 * �\�������̎擾
	 * 
	 * @return label �\������
	 */
	public String getLabel();

	/**
	 * �\����������̎擾
	 * 
	 * @return shortLabel �\����������
	 */
	public String getShortLabel();

	/**
	 * �t�b�^�[�����̎擾
	 * 
	 * @return footerLabel �t�b�^�[����
	 */
	public String getFooterLabel();

	/**
	 * �\�������F�̎擾
	 * 
	 * @return foreColor �\�������F
	 */
	public Color getForeColor();

	/**
	 * �Ώۏ��̎擾
	 * 
	 * @return obj �Ώۏ��
	 */
	public Object getObj();

	/**
	 * �`�恡�̎擾
	 * 
	 * @return rectangle �`�恡
	 */
	public Rectangle getRectangle();

	/**
	 * �`�恡�̐ݒ�
	 * 
	 * @param rectangle �`�恡
	 */
	public void setRectangle(Rectangle rectangle);
}
