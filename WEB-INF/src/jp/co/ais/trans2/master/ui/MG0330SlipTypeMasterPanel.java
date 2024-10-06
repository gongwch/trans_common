package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * �`�[��ʃ}�X�^ �w�����
 */
public class MG0330SlipTypeMasterPanel extends TMainPanel {

	/** �V�K�{�^�� */
	public TImageButton btnNew;

	/** �����{�^�� */
	public TImageButton btnSearch;

	/** �ҏW�{�^�� */
	public TImageButton btnModify;

	/** ���ʃ{�^�� */
	public TImageButton btnCopy;

	/** �폜�{�^�� */
	public TImageButton btnDelete;

	/** �G�N�Z���{�^�� */
	public TImageButton btnExcel;

	/** ���������p�l�� */
	public TPanel pnlSearchCondition;

	/** �`�[��ʔ͈͌��� */
	public TSlipTypeReferenceRange TSlipTypeReferenceRange;

	/** �ꗗ */
	public TTable tbl;

	/**
	 * �ꗗ�̃J������`
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** �`�[��ʃR�[�h */
		denSyuCode,
		/** �V�X�e���敪 */
		sysKbn,
		/** �`�[��ʖ��� */
		denSyuName,
		/** �`�[��ʗ��� */
		denSyuNames,
		/** ���[�^�C�g�� */
		reportTitle,
		/** �f�[�^�敪 */
		dataKbn,
		/** ���V�X�e���敪 */
		anotherSysKbn,
		/** �`�[�ԍ��̔ԃt���O */
		denNoFlg,
		/** ���͒P�� */
		acceptUnit,
		/** ����ŋ敪 */
		TaxCulKbn,
		/** �d��C���^�[�t�F�[�X�敪 */
		swkIfKbn,
		/** �U�ߓ`�[��ʃR�[�h */
		revDenSyuCode,
		/** �U�ߓ`�[��ʖ��� */
		revDenSyuName,
		/** �C���{�C�X���x�`�F�b�N */
		invoiceCheck
	}

	@Override
	public void initComponents() {

		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		pnlSearchCondition = new TPanel();
		TSlipTypeReferenceRange = new TSlipTypeReferenceRange();

		tbl = new TTable();
		tbl.addColumn(SC.denSyuCode, getWord("C00837"), 100);// �`�[��ʃR�[�h
		tbl.addColumn(SC.sysKbn, getWord("C00980"), 100);// �V�X�e���敪
		tbl.addColumn(SC.denSyuName, getWord("C00838"), 150);// �`�[��ʖ���
		tbl.addColumn(SC.denSyuNames, getWord("C00839"), 150);// �`�[��ʗ���
		tbl.addColumn(SC.reportTitle, getWord("C02757"), 200);// ���[�^�C�g��
		tbl.addColumn(SC.dataKbn, getWord("C00567"), 150);// �f�[�^�敪
		tbl.addColumn(SC.anotherSysKbn, getWord("C01221"), 150);// ���V�X�e���敪
		tbl.addColumn(SC.denNoFlg, getWord("C11168"), 150);// �`�[�ԍ��̔ԃt���O
		tbl.addColumn(SC.acceptUnit, getWord("C11169"), 200);// ���͒P��
		tbl.addColumn(SC.TaxCulKbn, getWord("C01642"), 100);// ����ŋ敪
		tbl.addColumn(SC.swkIfKbn, getWord("C00299"), 200);// �d��C���^�[�t�F�[�X�敪
		tbl.addColumn(SC.revDenSyuCode, getWord("C11280"), 130);// �U�ߓ`�[��ʃR�[�h
		tbl.addColumn(SC.revDenSyuName, getWord("C11281"), 130);// �U�ߓ`�[��ʖ���

		int invoiceWith = MG0330SlipTypeMasterPanelCtrl.isInvoice ? 130 : -1;
		tbl.addColumn(SC.invoiceCheck, getWord("C12199"), invoiceWith);// �C���{�C�X���x�`�F�b�N

	}

	@Override
	public void allocateComponents() {

		// �V�K�{�^��
		int x = HEADER_LEFT_X;
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setSize(25, 110);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		// �����{�^��
		x = x + btnNew.getWidth() + HEADER_MARGIN_X;
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// �ҏW�{�^��
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnModify.setLangMessageID("C00481");
		btnModify.setShortcutKey(KeyEvent.VK_F3);
		btnModify.setSize(25, 110);
		btnModify.setLocation(x, HEADER_Y);
		pnlHeader.add(btnModify);
		tbl.addSpreadSheetSelectChange(btnModify);

		// ���ʃ{�^��
		x = x + btnModify.getWidth() + HEADER_MARGIN_X;
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setSize(25, 110);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// �폜�{�^��
		x = x + btnCopy.getWidth() + HEADER_MARGIN_X;
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setSize(25, 110);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// �G�N�Z���{�^��
		x = x + btnDelete.getWidth() + HEADER_MARGIN_X;
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setSize(25, 130);
		btnExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExcel);

		// �㕔
		TPanel pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(0, 85));
		pnlBodyTop.setMinimumSize(new Dimension(0, 85));
		pnlBodyTop.setPreferredSize(new Dimension(0, 85));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// ���������p�l��
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setBorder(TBorderFactory.createTitledBorder(getWord("C01060")));
		pnlSearchCondition.setLocation(30, 10);
		pnlSearchCondition.setSize(360, 75);
		pnlBodyTop.add(pnlSearchCondition);

		// ����挟���͈�
		TSlipTypeReferenceRange.setLocation(20, 20);
		pnlSearchCondition.add(TSlipTypeReferenceRange);

		// ����
		TPanel pnlBodyButtom = new TPanel();
		pnlBodyButtom.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlBody.add(pnlBodyButtom, gc);

		// �ꗗ
		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBodyButtom.add(tbl, gc);

	}

	@Override
	public void setTabIndex() {
		int i = 1;
		TSlipTypeReferenceRange.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}
}
