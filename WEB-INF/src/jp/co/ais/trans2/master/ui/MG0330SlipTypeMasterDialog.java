package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * �`�[��ʃ}�X�^ �ҏWDialog
 */
public class MG0330SlipTypeMasterDialog extends TDialog {

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �V�X�e���敪 */
	public TSystemDivisionReference ctrlSysKbn;

	/** �`�[��ʃR�[�h */
	public TLabelField ctrlSlipTypeCode;

	/** �`�[��ʖ��� */
	public TLabelField ctrlSlipTypeName;

	/** �`�[��ʗ��� */
	public TLabelField ctrlSlipTypeNames;

	/** ���[�^�C�g�� */
	public TLabelField ctrlSlipTitle;

	/** �f�[�^�敪 */
	public TLabelComboBox cboDataDiv;

	/** �`�[�ԍ��̔ԕ��@ */
	public TLabelComboBox cboDenNoFlg;

	/** ���V�X�e���敪 */
	public TLabelComboBox cboAnoSysKbn;

	/** ����P�� */
	public TLabelComboBox cboAcceptUnit;

	/** ����Ōv�Z�敪 */
	public TLabelComboBox cboTaxCulDiv;

	/** �d��C���^�[�t�F�[�X�敪 */
	public TLabelComboBox cboJnlIfDiv;

	/** �U�ߓ`�[��ʃR�[�h */
	public TSlipTypeReference ctrlRevSlipTypeReference;

	/** invoice���x�`�F�b�N */
	public TLabelComboBox cboInvoiceCheck;

	/**
	 * @param parent
	 * @param mordal
	 */
	public MG0330SlipTypeMasterDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlSlipTypeCode = new TLabelField();
		ctrlSysKbn = new TSystemDivisionReference();
		ctrlSlipTypeName = new TLabelField();
		ctrlSlipTypeNames = new TLabelField();
		ctrlSlipTitle = new TLabelField();
		cboDataDiv = new TLabelComboBox();
		cboDenNoFlg = new TLabelComboBox();
		cboAnoSysKbn = new TLabelComboBox();
		cboAcceptUnit = new TLabelComboBox();
		cboTaxCulDiv = new TLabelComboBox();
		cboJnlIfDiv = new TLabelComboBox();
		ctrlRevSlipTypeReference = new TSlipTypeReference();
		cboInvoiceCheck = new TLabelComboBox();
	}

	@Override
	public void allocateComponents() {

		setSize(680, 400);

		// �m��{�^��
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(getWidth() - 240 - HEADER_MARGIN_X, HEADER_Y);
		pnlHeader.add(btnSettle);

		// �߂�{�^��
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(getWidth() - 130, HEADER_Y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);

		// �`�[��ʃR�[�h
		ctrlSlipTypeCode.setFieldSize(35);
		ctrlSlipTypeCode.setLocation(25, 10);
		ctrlSlipTypeCode.setMaxLength(3);
		ctrlSlipTypeCode.setImeMode(false);
		ctrlSlipTypeCode.setAllowedSpace(false);
		ctrlSlipTypeCode.setLangMessageID("C00837");
		pnlBody.add(ctrlSlipTypeCode);

		// �V�X�e���敪
		TGuiUtil.setComponentSize(ctrlSysKbn, new Dimension(330, 20));
		TGuiUtil.setComponentSize(ctrlSysKbn.btn, new Dimension(100, 20));
		ctrlSysKbn.setLocation(30, 40);
		pnlBody.add(ctrlSysKbn);

		// �`�[��ʖ���
		ctrlSlipTypeName.setFieldSize(290);
		ctrlSlipTypeName.setLocation(25, 70);
		ctrlSlipTypeName.setLangMessageID("C00838");
		ctrlSlipTypeName.setMaxLength(40);
		pnlBody.add(ctrlSlipTypeName);

		// �`�[��ʗ���
		ctrlSlipTypeNames.setFieldSize(290);
		ctrlSlipTypeNames.setLocation(25, 100);
		ctrlSlipTypeNames.setLangMessageID("C00839");
		ctrlSlipTypeNames.setMaxLength(40);
		pnlBody.add(ctrlSlipTypeNames);

		// ���[�^�C�g��
		ctrlSlipTitle.setFieldSize(290);
		ctrlSlipTitle.setLocation(25, 130);
		ctrlSlipTitle.setLangMessageID("C02757");
		ctrlSlipTitle.setMaxLength(40);
		pnlBody.add(ctrlSlipTitle);

		// �f�[�^�敪
		cboDataDiv.setComboSize(200);
		cboDataDiv.setLocation(5, 160);
		cboDataDiv.setLangMessageID("C00567");
		pnlBody.add(cboDataDiv);

		// �`�[�ԍ��̔ԕ��@
		cboDenNoFlg.setComboSize(170);
		cboDenNoFlg.setLabelSize(150);
		cboDenNoFlg.setLocation(320, 160);
		cboDenNoFlg.setLangMessageID("C01256");
		pnlBody.add(cboDenNoFlg);

		// ���V�X�e���敪
		cboAnoSysKbn.setComboSize(200);
		cboAnoSysKbn.setLocation(5, 190);
		cboAnoSysKbn.setLangMessageID("C01221");
		pnlBody.add(cboAnoSysKbn);

		// ����P��
		cboAcceptUnit.setComboSize(170);
		cboAcceptUnit.setLocation(350, 190);
		cboAcceptUnit.setLangMessageID("C00018");
		pnlBody.add(cboAcceptUnit);

		// ����Ōv�Z�敪
		cboTaxCulDiv.setComboSize(200);
		cboTaxCulDiv.setLocation(5, 220);
		cboTaxCulDiv.setLangMessageID("C00287");
		pnlBody.add(cboTaxCulDiv);

		// �d��C���^�[�t�F�[�X�敪
		cboJnlIfDiv.setComboSize(170);
		cboJnlIfDiv.setLabelSize(150);
		cboJnlIfDiv.setLocation(320, 220);
		cboJnlIfDiv.setLangMessageID("C00299");
		pnlBody.add(cboJnlIfDiv);

		// �U�ߓ`�[��ʃR�[�h
		TGuiUtil.setComponentSize(ctrlRevSlipTypeReference, new Dimension(330, 20));
		TGuiUtil.setComponentSize(ctrlRevSlipTypeReference.btn, new Dimension(100, 20));
		ctrlRevSlipTypeReference.setLocation(30, 250);
		ctrlRevSlipTypeReference.btn.setLangMessageID("C11279");
		pnlBody.add(ctrlRevSlipTypeReference);

		// invoice���x�`�F�b�N
		cboInvoiceCheck.setComboSize(80);
		cboInvoiceCheck.setLocation(5, 280);
		cboInvoiceCheck.setLangMessageID("C12199");
		pnlBody.add(cboInvoiceCheck);

	}

	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlSlipTypeCode.setTabControlNo(i++);
		ctrlSysKbn.setTabControlNo(i++);
		ctrlSlipTypeName.setTabControlNo(i++);
		ctrlSlipTypeNames.setTabControlNo(i++);
		ctrlSlipTitle.setTabControlNo(i++);
		cboDataDiv.setTabControlNo(i++);
		cboDenNoFlg.setTabControlNo(i++);
		cboAnoSysKbn.setTabControlNo(i++);
		cboAcceptUnit.setTabControlNo(i++);
		cboTaxCulDiv.setTabControlNo(i++);
		cboJnlIfDiv.setTabControlNo(i++);
		ctrlRevSlipTypeReference.setTabControlNo(i++);
		cboInvoiceCheck.setTabControlNo(i++);

		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}
