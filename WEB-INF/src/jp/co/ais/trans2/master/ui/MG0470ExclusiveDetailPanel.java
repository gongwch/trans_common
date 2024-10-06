package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * �r�����׈ꗗ�̎w����ʃ��C�A�E�g
 * 
 * @author AIS
 */
public class MG0470ExclusiveDetailPanel extends TMainPanel {

	/** �����{�^�� */
	public TImageButton btnSearch;

	/** �G�N�Z���{�^�� */
	public TImageButton btnExcel;

	/** �r���ꗗ */
	public TTable tbl;

	/**
	 * �ꗗ�̃J������`
	 */
	public enum SC {

		/** ��ЃR�[�h */
		KAI_CODE,
		/** �����敪 */
		SHORI_KBN,
		/** �r���L�[ */
		HAITA_KYE,
		/** �s�ԍ� */
		GYO_NO,
		/** �������� */
		INP_DATE,
		/** �v���O����ID */
		PRG_ID,
		/** �v���O�������� */
		PRG_NAME,
		/** ���[�U�[ID */
		USR_ID,
		/** ���[�U���� */
		USR_NAME,

	}

	@Override
	public void initComponents() {

		btnSearch = new TImageButton(IconType.SEARCH);
		btnExcel = new TImageButton(IconType.EXCEL);

		tbl = new TTable();
		tbl.addColumn(SC.KAI_CODE, "C00596", 100, SwingConstants.LEFT);
		tbl.addColumn(SC.SHORI_KBN, "C02829", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.HAITA_KYE, "C11950", 150, SwingConstants.LEFT);
		tbl.addColumn(SC.GYO_NO, "C01588", 100, SwingConstants.RIGHT);
		tbl.addColumn(SC.INP_DATE, "C11757", 150, SwingConstants.CENTER);
		tbl.addColumn(SC.PRG_ID, "C10994", 100, SwingConstants.LEFT);
		tbl.addColumn(SC.PRG_NAME, "C00819", 200, SwingConstants.LEFT);
		tbl.addColumn(SC.USR_ID, "C10995", 100, SwingConstants.LEFT);
		tbl.addColumn(SC.USR_NAME, "C00691", 200, SwingConstants.LEFT);

	}

	@Override
	public void allocateComponents() {

		// �����{�^��
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(HEADER_LEFT_X, HEADER_Y);
		pnlHeader.add(btnSearch);

		// �G�N�Z���{�^��
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setSize(25, 130);
		btnExcel.setLocation(150, HEADER_Y);
		pnlHeader.add(btnExcel);

		// �ꗗ
		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBody.add(tbl, gc);

	}

	@Override
	public void setTabIndex() {
		int i = 0;
		btnSearch.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
		tbl.setTabControlNo(i++);

	}

}
