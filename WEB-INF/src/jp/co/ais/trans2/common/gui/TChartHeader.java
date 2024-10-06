package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;

/**
 * �`���[�g�̃w�b�_�\��
 */
public class TChartHeader extends TChart {

	/**  */
	public FontMetrics fm = null;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param cell
	 */
	public TChartHeader(TChart cell) {
		super(cell.baseDt, cell.maxDays, cell.unit);

		// ���ݓ��t
		setCurrentDate(cell.getCurrentDate());
		// �v�Z�p��
		setCalcWidth(cell.getCalcWidth());

		// true:����
		setTransparent(cell.isTransparent());
		// �w�i�F
		setBackground(cell.getBackground());
		// �����F
		setForeground(cell.getForeground());
	}

	@Override
	protected TChartPainter createPainter() {
		return new TChartHeaderPainter();
	}

	/**
	 * �t�H���g�ύX����
	 */
	@Override
	public void setFont(Font font) {
		super.setFont(font);

		fm = getFontMetrics(getFont());
	}

	/**
	 * �\�������̎擾
	 * 
	 * @param day
	 * @return �\������
	 */
	public Date getHeaderDate(int day) {
		Date dt = DateUtil.addDay(baseDt, day);
		return dt;
	}

	/**
	 * �\�������̎擾
	 * 
	 * @param dt
	 * @return �\������
	 */
	public String[] getTitle1(Date dt) {
		String[] title = new String[] { toTitle1(dt), toTitle2(dt) };
		return title;
	}

	/**
	 * �\�������̎擾
	 * 
	 * @param dt
	 * @return �\������
	 */
	public String getTitle2(Date dt) {
		return toTitle2(dt);
	}

	/**
	 * �^�C�g����i�̓��t�t�H�[�}�b�g
	 * 
	 * @param dt ���t
	 * @return �^�C�g����i
	 */
	public String toTitle1(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (title1DateFormat) {
			return title1DateFormat.format(dt);
		}
	}

	/**
	 * �^�C�g�����i�̓��t�t�H�[�}�b�g
	 * 
	 * @param dt ���t
	 * @return �^�C�g�����i
	 */
	public String toTitle2(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (title2DateFormat) {
			return title2DateFormat.format(dt);
		}
	}

	/**
	 * �w����t�̍��W�̎擾
	 * 
	 * @param day ����
	 * @return ���W
	 */
	public int getHeaderPosition(double day) {
		int pos = (int) (day / maxDays * getDrawWidth());

		if (pos == 0) {
			return getDrawX();
		}

		return pos;
	}

	/**
	 * @return �`��Y
	 */
	@Override
	public int getDrawY() {
		return 2;
	}

	/**
	 * �w�b�_�[�A�t�b�^�[��`�悷��
	 * 
	 * @return true �w�b�_�[�A�t�b�^�[��`�悷��
	 */
	@Override
	public boolean isDrawHeaderAndFooter() {
		return false;
	}
}
