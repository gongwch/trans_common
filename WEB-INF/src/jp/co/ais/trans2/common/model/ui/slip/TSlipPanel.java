package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.objsave.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �`�[���̓p�l��
 */
public abstract class TSlipPanel extends TMainObjectSavePanel {

	/** �V�K���[�h */
	protected static final int MODE_NEW = 1;

	/** �ҏW���[�h */
	protected static final int MODE_MOD = 2;

	/** ���J�n�ʒu */
	protected final int X_POINT = 5;

	/** ��ԃ��x�� */
	public TLabel lblState;

	/** �V�K�{�^�� */
	public TImageButton btnNew;

	/** �C���{�^�� */
	public TImageButton btnModify;

	/** ���ʃ{�^�� */
	public TImageButton btnCopy;

	/** �폜�{�^�� */
	public TImageButton btnDelete;

	/** �m�� */
	public TImageButton btnEntry;

	/** �d�󎫏��{�^�� */
	public TImageButton btnPattern;

	/** �d�󎫏��o�^�{�^�� */
	public TImageButton btnPatternSave;

	/** �v���r���[�{�^�� */
	public TImageButton btnPreview;

	/** �`�[�w�b�_�p�l�� */
	public TPanel pnlSlipHdr;

	/** �`�[���t */
	public TSlipDate ctrlSlipDate;

	/** �`�[�ԍ� */
	public TSlipNo ctrlSlipNo;

	/** �؜�No./������No. */
	public TLabelField ctrlEvidenceNo;

	/** �Y�t�t�@�C���{�^�� */
	public TAttachButton btnAttach;

	/** ���Z�d�� */
	public TClosingEntryCheck ctrlCloseEntry;

	/** �`�[�����~ */
	public TSlipPrintStopCheck ctrlPrintStop;

	/** �`�[�E�v */
	public TRemarkReference ctrlSlipRemark;

	/** ���F�O���[�v */
	public TAprvRoleGroupReference ctrlAprvGroup;

	/** �w�b�_�Ɩ��ׂ̋��E�� */
	public JSeparator sepSlip;

	/** �`�[���׃p�l�� */
	public TPanel pnlSlipDtl;

	/** ���� */
	public TSlipDetailPanel ctrlDetail;

	/** �V�K/�ҏW���[�h */
	public int mode = MODE_NEW;

	/** �w�b�_�Ɩ��ׂ̋��E�� �� */
	public JSplitPane spBody;

	/** �R���g���[���[ */
	public TSlipPanelCtrl controller;

	/** �t�1 */
	public TTagReference ctrlTag1;

	/** �t�2 */
	public TTagReference ctrlTag2;
	
	/**
	 * �R���X�g���N�^.
	 */
	public TSlipPanel() {
		super();
	}
	
	/**
	 * �R���X�g���N�^.
	 * 
	 * @param company
	 */
	public TSlipPanel(Company company) {
		super(company);
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#initComponents()
	 */
	@Override
	public void initComponents() {
		lblState = new TLabel();
		btnNew = new TImageButton(IconType.NEW);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnEntry = new TImageButton(IconType.SETTLE);
		btnPattern = new TImageButton(IconType.PATTERN);
		btnPatternSave = new TImageButton(IconType.PATTERN);
		btnPreview = new TImageButton(IconType.PREVIEW);

		pnlSlipHdr = new TPanel();
		pnlSlipDtl = new TPanel();

		// �w�b�_����
		ctrlSlipDate = new TSlipDate();
		ctrlSlipNo = new TSlipNo();
		ctrlCloseEntry = new TClosingEntryCheck(ctrlSlipDate);
		ctrlEvidenceNo = new TLabelField();
		btnAttach = new TAttachButton(this);
		ctrlSlipRemark = new TRemarkReference(true);
		ctrlAprvGroup = new TAprvRoleGroupReference();
		ctrlPrintStop = new TSlipPrintStopCheck();

		// ����
		ctrlDetail = createDetailPanel();

		// �����ݒ� --

		// �`�[���t
		ctrlSlipDate.setAllowableBlank(false);

		// �`�[�ԍ�
		ctrlSlipNo.setLabelSize(60);

		// �؜ߔԍ�
		ctrlEvidenceNo.setLabelSize(60);
		ctrlEvidenceNo.setMaxLength(20);

		// �`�[�E�v
		ctrlSlipRemark.btn.setLangMessageID("C00569");

		// �tⳋ@�\
		ctrlTag1 = new TTagReference();
		ctrlTag2 = new TTagReference();

	}

	/**
	 * ���׃p�l���쐬
	 * 
	 * @return ���׃p�l��
	 */
	public TSlipDetailPanel createDetailPanel() {

		if (isTFormMode()) {
			return new TFormSlipDetailPanel(this);
		}
		return new TSlipDetailPanel(this);
	}

	/**
	 * @return true: T-Form���[�h
	 */
	public boolean isTFormMode() {
		return false; // �e�`�[���͉�ʂ�Override����
	}

	/**
	 * @return T-Form���[�h���̏������׌���
	 */
	public int getTFormDetailCount() {
		return 2; // �e�`�[���͉�ʂ�Override����
	}

	/**
	 * Panel��UI������������������. <br>
	 * ���b�Z�[�W�̕ϊ��\���B �^�u���̐ݒ�B<br>
	 * ���b�Z�[�WID�̐ݒ�A�^�u���ԍ��̐ݒ肪���������瑦����method���Ăяo���B
	 */
	public void resetPanelTabIndex() {
		initPanel();
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#allocateComponents()
	 */
	@Override
	public void allocateComponents() {

		// �{�^����
		allocateButtons();

		// BODY
		pnlBody.setLayout(new BorderLayout());

		TPanel hdr = new TPanel(new GridBagLayout());

		// �w�b�_�[�̈�
		pnlSlipHdr.setLayout(null);
		TGuiUtil.setComponentSize(pnlSlipHdr, new Dimension(0, 0));

		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;

		gc.gridx = 0;
		gc.gridy = 0;
		hdr.add(pnlSlipHdr, gc);

		allocateSlipHeader();
		pnlBody.add(hdr, BorderLayout.NORTH);

		// ���ח̈�
		pnlSlipDtl.setLayout(new BorderLayout());
		pnlSlipDtl.add(ctrlDetail);

		// ���[�U�[�ݒ�ɂ��A�\���R���|�[�l���g��؂�ւ���
		if (ClientConfig.isFlagOn("trans.slip.splitpane")) {
			// �ϋ��E��
			spBody = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, pnlSlipHdr, pnlSlipDtl);
			spBody.setOpaque(false);
			pnlSlipHdr.setMinimumSize(new Dimension(0, 0));

			pnlBody.add(spBody, BorderLayout.CENTER);

		} else {
			// �ʏ틫�E��
			sepSlip = new JSeparator();
			TGuiUtil.setComponentSize(sepSlip, new Dimension(0, 3));
			gc.gridx = 0;
			gc.gridy = 1;
			hdr.add(sepSlip, gc);

			pnlSlipDtl.setMinimumSize(new Dimension(0, 550));

			pnlBody.add(pnlSlipDtl, BorderLayout.CENTER);

		}

	}

	/**
	 * �{�^�����̔z�u
	 */
	public void allocateButtons() {
		int x = X_POINT;
		// switchNew();
		lblState.setSize(30, 25);
		lblState.setHorizontalAlignment(SwingConstants.CENTER);
		lblState.setOpaque(true);
		lblState.setLocation(x, HEADER_Y);
		pnlHeader.add(lblState);

		// �V�K�{�^��
		x = x + lblState.getWidth() + HEADER_MARGIN_X;
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F2);
		btnNew.setSize(25, 110);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		// �C��
		x = x + btnNew.getWidth() + HEADER_MARGIN_X;
		btnModify.setLangMessageID("C01760");
		btnModify.setShortcutKey(KeyEvent.VK_F3);
		btnModify.setSize(25, 110);
		btnModify.setLocation(x, HEADER_Y);
		pnlHeader.add(btnModify);

		// ����
		x = x + btnModify.getWidth() + HEADER_MARGIN_X;
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setSize(25, 110);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// �폜
		x = x + btnCopy.getWidth() + HEADER_MARGIN_X;
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setSize(25, 110);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// �m��
		x = x + btnDelete.getWidth() + HEADER_MARGIN_X;
		btnEntry.setLangMessageID("C01019");
		btnEntry.setShortcutKey(KeyEvent.VK_F8);
		btnEntry.setSize(25, 110);
		btnEntry.setLocation(x, HEADER_Y);
		pnlHeader.add(btnEntry);

		// �p�^�[��
		x = x + btnEntry.getWidth() + HEADER_MARGIN_X;
		btnPattern.setLangMessageID("C00300");
		btnPattern.setShortcutKey(KeyEvent.VK_F11);
		btnPattern.setSize(25, 110);
		btnPattern.setLocation(x, HEADER_Y);
		pnlHeader.add(btnPattern);

		// �p�^�[���o�^
		x = x + btnPattern.getWidth() + HEADER_MARGIN_X;
		btnPatternSave.setLangMessageID("C11887"); // �����o�^
		btnPatternSave.setShortcutKey(KeyEvent.VK_F12);
		btnPatternSave.setSize(25, 110);
		btnPatternSave.setLocation(x, HEADER_Y);
		pnlHeader.add(btnPatternSave);

		// �v���r���[
		x = x + btnPatternSave.getWidth() + HEADER_MARGIN_X;
		btnPreview.setLangMessageID("C10019"); // �v���r���[
		btnPreview.setShortcutKey(KeyEvent.VK_F9);
		btnPreview.setSize(25, 110);
		btnPreview.setLocation(x, HEADER_Y);
		pnlHeader.add(btnPreview);
	}

	/**
	 * �w�b�_�̔z�u
	 */
	public abstract void allocateSlipHeader();

	/**
	 * �p����Ńw�b�_�̔ԍ����Z�b�g������ɌĂ�
	 * 
	 * @param i �ԍ�
	 */
	public void setTabIndex(int i) {

		// ����
		i = ctrlDetail.setTabIndex(i);

		// �{�^��
		btnNew.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnEntry.setTabControlNo(i++);
		btnPattern.setTabControlNo(i++);
		btnPatternSave.setTabControlNo(i++);
		btnPreview.setTabControlNo(i++);
	}

	/**
	 * �V�K���[�h�ؑ�
	 */
	public void switchNew() {
		lblState.setBackground(new Color(0, 240, 255));
		lblState.setText(getShortWord("C00303"));

		mode = MODE_NEW;
	}

	/**
	 * �ҏW���[�h�ؑ�
	 */
	public void switchModify() {
		lblState.setBackground(new Color(255, 255, 50));
		lblState.setText(getShortWord("C00169"));

		mode = MODE_MOD;
	}

	/**
	 * �V�K���[�h���ǂ���
	 * 
	 * @return true:�V�K���[�h
	 */
	public boolean isModifyMode() {
		return mode == MODE_MOD;
	}

	/**
	 * @param tSlipPanelCtrl
	 */
	public void setSlipController(TSlipPanelCtrl tSlipPanelCtrl) {
		this.controller = tSlipPanelCtrl;

	}

	/**
	 * �`�[���CTRL�̎擾
	 * 
	 * @return controller �`�[���CTRL
	 */
	public TSlipPanelCtrl getController() {
		return controller;
	}

	/**
	 * �`�[���CTRL�̐ݒ�
	 * 
	 * @param controller �`�[���CTRL
	 */
	public void setController(TSlipPanelCtrl controller) {
		this.controller = controller;
	}
}
