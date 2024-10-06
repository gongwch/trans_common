package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �h�����_�E���w�b�_�[AR
 * 
 * @author AIS
 */
public class TDrillDownHeaderAR extends TDrillDownHeader {

	/** �����R�[�h */
	public TLabelField txTriCode;

	/** ����旪�� */
	public TLabelField txTriNames;

	/** ������ */
	public TLabelField txArDate;

	/** �����ʉ� */
	public TLabelField txCurCode;

	/** �������z */
	public TLabelField txArKin;

	/** ��s���� */
	public TLabelField txKoza;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param lang ����R�[�h
	 */
	public TDrillDownHeaderAR(String lang) {
		super(lang);
	}

	/**
	 * �R���|�[�l���g�̏����ݒ�
	 */
	@Override
	public void initComponents() {
		super.initComponents();
		txTriCode = new TLabelField();
		txTriNames = new TLabelField();
		txArDate = new TLabelField();
		txCurCode = new TLabelField();
		txArKin = new TLabelField();
		txKoza = new TLabelField();
	}

	/**
	 * �R���|�[�l���g�̔z�u���s���܂��B
	 */
	@Override
	public void allocateComponents() {
		super.allocateComponents();
		int x = 10;
		int y = txRemarkCode.getY() + txRemarkCode.getHeight() + 10;

		setMaximumSize(new Dimension(0, 155));
		setMinimumSize(new Dimension(0, 155));
		setPreferredSize(new Dimension(0, 155));

		// �����(�R�[�h)
		txTriCode.setEditable(false);
		txTriCode.setLabelSize(65);
		txTriCode.setFieldSize(80);
		txTriCode.setLangMessageID("C00408");
		txTriCode.setLocation(x, y);
		add(txTriCode);

		// �����(����)
		txTriNames.setEditable(false);
		txTriNames.setLabelSize(0);
		txTriNames.setFieldSize(215);
		txTriNames.setLocation(x + txTriCode.getWidth() - 5, y);
		add(txTriNames);

		// ������
		txArDate.setEditable(false);
		txArDate.setLabelSize(80);
		txArDate.setFieldSize(75);
		txArDate.setLangMessageID("C01273");
		txArDate.getField().setHorizontalAlignment(0);
		txArDate.setLocation(txTriNames.getX() + txTriNames.getWidth() + 10, y);
		add(txArDate);

		// �������z(�ʉ�)
		txCurCode.setEditable(false);
		txCurCode.setLabelSize(65);
		txCurCode.setFieldSize(60);
		txCurCode.getField().setHorizontalAlignment(0);
		txCurCode.setLangMessageID("C01689");
		txCurCode.setLocation(x, txTriCode.getY() + txTriCode.getHeight() + 1);
		add(txCurCode);

		// �������z
		txArKin.setEditable(false);
		txArKin.setLabelSize(0);
		txArKin.setFieldSize(235);
		txArKin.getField().setHorizontalAlignment(4);
		txArKin.setLocation(txCurCode.getX() + txCurCode.getWidth() - 5, txTriCode.getY() + txTriCode.getHeight() + 1);
		add(txArKin);

		// ��s����
		txKoza.setEditable(false);
		txKoza.setLangMessageID("C00857");
		txKoza.setLabelSize(80);
		txKoza.setFieldSize(225);
		txKoza.setLocation(txArDate.getX(), txArDate.getY() + txArDate.getHeight() + 1);
		add(txKoza);

		// ���ߐ����Z�b�g
		txTriCode.setOpaque(false);
		txTriNames.setOpaque(false);
		txArDate.setOpaque(false);
		txSNo.setOpaque(false);
		txCurCode.setOpaque(false);
		txArKin.setOpaque(false);
		txKoza.setOpaque(false);
	}

	/**
	 * �`�[�w�b�_�[���Z�b�g���܂��B
	 * 
	 * @param slip
	 */
	@Override
	public void setSlipHeader(Slip slip) throws TException {
		super.setSlipHeader(slip);
		// �x����R�[�h
		txTriCode.setValue(Util.avoidNull(slip.getHeader().getSWK_TRI_CODE()));
		// �x���旪��
		txTriNames.setValue(Util.avoidNull(slip.getHeader().getSWK_TRI_NAME_S()));
		// ������
		txArDate.setValue(DateUtil.toYMDString(slip.getHeader().getSWK_AR_DATE()));
		// �������z(�ʉ�)
		txCurCode.setValue(Util.avoidNull(slip.getHeader().getSWK_CUR_CODE()));
		// �������z
		txArKin.setValue(NumberFormatUtil.formatNumber(slip.getHeader().getSWK_IN_KIN(), slip.getHeader()
			.getSWK_CUR_DEC_KETA()));
		// ��s����
		txKoza.setValue(Util.avoidNull(slip.getHeader().getSWK_CBK_NAME()));
	}

}
