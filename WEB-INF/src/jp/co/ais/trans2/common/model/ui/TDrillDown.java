package jp.co.ais.trans2.common.model.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �`�[�Ɖ�R���|�[�l���g
 * 
 * @author AIS
 */
public class TDrillDown extends TFrame {

	/** �C���{�^���\�����`�[�ҏW���\�ɂ��邩�ۂ� */
	public static boolean isDrillDownSlipModify = ClientConfig.isFlagOn("trans.use.drilldown.slip.modify");

	/** ���ʃ{�^���\�����`�[�ҏW���\�ɂ��邩�ۂ� */
	public static boolean isDrillDownSlipCopy = ClientConfig.isFlagOn("trans.use.drilldown.slip.copy");

	/** �R���g���[�� */
	public TDrillDownController ctrl;

	/** ���C���{�^���̈� */
	public TPanel pnlButton;

	/** ����敪 */
	public TBookComboBox cmbBookComboBox;

	/** �ʉݑI�� */
	public TBookCurrencyComboBox cmbBookCurrencyComboBox;

	/** �{�^���̈� */
	public TPanel pnlButtons;

	/** �� */
	public TButton btnPrevSlip;

	/** �� */
	public TButton btnNextSlip;

	/** ���F�{�^�� */
	public TButton btnApprove;

	/** �۔F�{�^�� */
	public TButton btnDeny;

	/** ���F����{�^�� */
	public TButton btnCancel;

	/** �Y�t�o�̓{�^�� */
	public TButton btnAttachment;

	/** ���F�����{�^�� */
	public TButton btnApproveHistory;

	/** �v���r���[�{�^�� */
	public TButton btnPreview;

	/** ����{�^�� */
	public TButton btnReturn;

	/** ���E�� */
	public JSeparator sep;

	/** �{�e�B�̈� */
	public TPanel pnlBody;

	/** �`�[�w�b�_�[ */
	public TPanel pnlSlipHeader;

	/** �h�����_�E���w�b�_�[�t�B�[���h */
	public TDrillDownHeader ctrlDrillDownHeader;

	/** ���E�� */
	public JSeparator sep2;

	/** �`�[���� */
	public TTable tblSlipDetail;

	/** �`�[�t�b�^�[ */
	public TPanel pnlSlipFotter;

	/** �ؕ����v���z */
	public TLabelNumericField txDebitSum;

	/** �O�ݎؕ��v1 */
	public TLabelNumericField txDebitInSum1;

	/** �O�ݎؕ��v2 */
	public TLabelNumericField txDebitInSum2;

	/** �ݕ����v���z */
	public TNumericField txCreditSum;

	/** �O�ݑݕ��v1 */
	public TNumericField txCreditInSum1;

	/** �O�ݑݕ��v2 */
	public TNumericField txCreditInSum2;

	/** �O��1 */
	public TTextField txCur1;

	/** �O��2 */
	public TTextField txCur2;

	/** �O�݃t�B�[���h���X�g */
	public List<TDrillDownForeignField> foreignList;

	/** �O���[�v��Ђ̊֐��� */
	protected String groupMethodName = "getReportBySlipNos";

	/** �ʏ�̊֐��� */
	protected String methodName = "getReport";

	/** �}�l�[�W���N���X�� */
	protected Class managerClass = SlipManager.class;

	/** �C���{�^�� */
	public TButton btnModify;

	/** ���ʃ{�^�� */
	public TButton btnCopy;

	/** �X�v���b�h�V�[�g�̃J������` */
	public enum SC {
		/** �Ȗ� */
		item,
		/** �ؕ� */
		debit,
		/** �ݕ� */
		credit,
		/** �ʉ� */
		currencyCode,
		/** �E�v */
		remark,
		/** ���� */
		department,
		/** �Ǘ�1 */
		management1to3,
		/** �Ǘ�4 */
		management4to6,
		/** ���v���� */
		nonAccount,
		/** BS��� */
		bs,
		/** AP��� */
		ap,
		/** AR��� */
		ar
	}

	/**
	 * �R���X�g���N�^
	 */
	public TDrillDown() {

		initComponents();

		allocateComponents();

		setTabIndex();

		ctrl = createController();

		// TMainCtrl�Ɋ֘A�t��
		TMainCtrl.getInstance().addFrame(this);
	}

	/**
	 * �R���|�[�l���g�̏����ݒ�
	 */
	public void initComponents() {
		pnlButton = new TPanel();
		cmbBookComboBox = new TBookComboBox();
		cmbBookCurrencyComboBox = new TBookCurrencyComboBox();
		pnlButtons = new TPanel();
		btnApprove = new TButton();
		btnDeny = new TButton();
		btnCancel = new TButton();
		btnAttachment = new TImageButton(IconType.ATTACHMENT);
		btnApproveHistory = new TImageButton(IconType.PATTERN);
		btnPreview = new TImageButton(IconType.PREVIEW);
		btnReturn = new TButton();
		sep = new JSeparator();
		sep2 = new JSeparator();
		pnlBody = new TPanel();
		pnlSlipHeader = new TPanel();
		tblSlipDetail = new TTable();
		pnlSlipFotter = new TPanel();
		txDebitSum = new TLabelNumericField();
		txCreditSum = new TNumericField();

		btnPrevSlip = new TImageButton(IconType.ALLOW_PREVIOUS);
		btnNextSlip = new TImageButton(IconType.ALLOW_NEXT);

		// �C��
		btnModify = new TImageButton(IconType.EDIT);
		// ����
		btnCopy = new TImageButton(IconType.COPY);

		// �O�݃t�B�[���h�̏�����
		initForeignField();
	}

	/**
	 * �O�݃t�B�[���h�̏�����
	 */
	public void initForeignField() {

		foreignList = new ArrayList<TDrillDownForeignField>();
		for (int i = 0; i < 4; i++) {
			foreignList.add(new TDrillDownForeignField());
		}

		// �����@�\�̉e���Ή�
		txDebitInSum1 = foreignList.get(0).txDebitInSum;
		txDebitInSum2 = foreignList.get(1).txDebitInSum;
		txCreditInSum1 = foreignList.get(0).txCreditInSum;
		txCreditInSum2 = foreignList.get(1).txCreditInSum;
		txCur1 = foreignList.get(0).txCur;
		txCur2 = foreignList.get(1).txCur;

	}

	/**
	 * �R���|�[�l���g�̔z�u���s���܂��B
	 */
	public void allocateComponents() {

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		setTitle(getWord("C03662") + getWord("C00075")); // �`�[�Ɖ���
		setSize(820, 650);

		// ���C���{�^���̈�
		GridBagConstraints gc = new GridBagConstraints();
		pnlButton.setLayout(new GridBagLayout());
		pnlButton.setMaximumSize(new Dimension(0, 40));
		pnlButton.setMinimumSize(new Dimension(0, 40));
		pnlButton.setPreferredSize(new Dimension(0, 40));
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTHWEST;
		add(pnlButton, gc);

		// ����敪
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 15, 0, 0);
		gc.anchor = GridBagConstraints.WEST;
		pnlButton.add(cmbBookComboBox, gc);

		// �\���ʉ�
		pnlButton.add(cmbBookCurrencyComboBox, gc);

		// ��
		pnlButton.add(btnPrevSlip, gc);

		// ��
		pnlButton.add(btnNextSlip, gc);

		// �{�^���̈�
		pnlButtons.setLayout(null);
		TGuiUtil.setComponentSize(pnlButtons, 700, 25);
		gc.insets = new Insets(0, 0, 0, 20);
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.anchor = GridBagConstraints.EAST;
		pnlButton.add(pnlButtons, gc);

		int x = 0;
		int y = 0;
		// �C��
		if (isDrillDownSlipModify) {
			btnModify.setLangMessageID("C01760");
			btnModify.setShortcutKey(KeyEvent.VK_F3);
			btnModify.setSize(25, 120);
			btnModify.setLocation(x, y);
			pnlButtons.add(btnModify);

			int width = btnModify.getWidth() + 5;
			x += width;
			TGuiUtil.setComponentSize(pnlButtons, pnlButtons.getWidth() + width, 25);
		}

		// ����
		if (isDrillDownSlipCopy) {
			btnCopy.setLangMessageID("C00459");
			btnCopy.setShortcutKey(KeyEvent.VK_F4);
			btnCopy.setSize(25, 120);
			btnCopy.setLocation(x, y);
			pnlButtons.add(btnCopy);
			int width = btnCopy.getWidth() + 5;
			x += width;
			TGuiUtil.setComponentSize(pnlButtons, pnlButtons.getWidth() + width, 25);

		}
		// ���F�{�^��
		btnApprove.setLangMessageID("C01168");
		btnApprove.setShortcutKey(KeyEvent.VK_F8);
		btnApprove.setSize(25, 120);
		btnApprove.setLocation(x, y);
		btnApprove.setVisible(false);
		pnlButtons.add(btnApprove);
		x += btnApprove.getWidth() + 5;

		// �۔F�{�^��
		btnDeny.setLangMessageID("C00447");
		btnDeny.setShortcutKey(KeyEvent.VK_F9);
		btnDeny.setSize(25, 120);
		btnDeny.setLocation(x, y);
		btnDeny.setVisible(false);
		pnlButtons.add(btnDeny);
		x += btnDeny.getWidth() + 5;

		// ���F����{�^��
		btnCancel.setLangMessageID("C00285");
		btnCancel.setShortcutKey(KeyEvent.VK_F10);
		btnCancel.setSize(25, 120);
		btnCancel.setLocation(x, y);
		btnCancel.setVisible(false);
		pnlButtons.add(btnCancel);
		x += btnCancel.getWidth() + 70;

		// ���F�����@�\
		if (ClientConfig.isFlagOn("trans.slip.use.approve.history")) {
			// �����̏ꍇ�A��\��
			// ���F�����{�^��
			btnApproveHistory.setLangMessageID("C11755"); // ���F����
			btnApproveHistory.setSize(25, 120);
			btnApproveHistory.setShortcutKey(KeyEvent.VK_F5);
			btnApproveHistory.setLocation(x, y);
			pnlButtons.add(btnApproveHistory);

			int width = btnApproveHistory.getWidth() + 5;
			x += width;

			TGuiUtil.setComponentSize(pnlButtons, pnlButtons.getWidth() + width, 25);
		}

		if (ClientConfig.isFlagOn("trans.slip.use.attachment")) {
			// �����̏ꍇ�A��\��
			// �Y�t�\���{�^��
			btnAttachment.setLangMessageID("C11610"); // �Y�t�\��
			btnAttachment.setSize(25, 120);
			btnAttachment.setShortcutKey(KeyEvent.VK_F6);
			btnAttachment.setLocation(x, y);
			pnlButtons.add(btnAttachment);

			int width = btnAttachment.getWidth() + 5;
			x += width;

			TGuiUtil.setComponentSize(pnlButtons, pnlButtons.getWidth() + width, 25);
		}

		// �v���r���[�{�^��
		btnPreview.setLangMessageID("C10019");
		btnPreview.setSize(25, 120);
		btnPreview.setShortcutKey(KeyEvent.VK_F7);
		btnPreview.setLocation(x, y);
		pnlButtons.add(btnPreview);

		x += btnPreview.getWidth() + 5;

		// ����{�^��
		btnReturn.setText(getWord("C02374")); // ����
		btnReturn.setSize(25, 120);
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setLocation(x, y);
		pnlButtons.add(btnReturn);

		// ���E��
		sep = new JSeparator();
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridy = 1;
		add(sep, gc);

		// �{�f�B�̈�
		pnlBody.setLayout(new GridBagLayout());
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy = 2;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.anchor = GridBagConstraints.NORTHWEST;
		add(pnlBody, gc);

		// �`�[�w�b�_�[
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTHWEST;
		pnlSlipHeader.setLayout(new GridBagLayout());
		pnlBody.add(pnlSlipHeader, gc);

		// ���E��
		sep2 = new JSeparator();
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridy = 1;
		pnlBody.add(sep2, gc);

		// �`�[����
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.gridy = 2;
		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.insets = new Insets(10, 30, 0, 30);
		pnlBody.add(tblSlipDetail, gc);

		// �`�[�t�b�^�[
		pnlSlipFotter.setLayout(null);
		pnlSlipFotter.setMaximumSize(new Dimension(0, 75));
		pnlSlipFotter.setMinimumSize(new Dimension(0, 75));
		pnlSlipFotter.setPreferredSize(new Dimension(0, 75));

		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridy = 3;
		gc.anchor = GridBagConstraints.NORTHWEST;
		pnlBody.add(pnlSlipFotter, gc);

		// �ؕ����v���z
		x = 145;
		txDebitSum.setEditable(false);
		txDebitSum.setFieldSize(100);
		txDebitSum.setLabelSize(100);
		txDebitSum.setLangMessageID("C00165"); // ���v
		txDebitSum.setLocation(x, 0);
		pnlSlipFotter.add(txDebitSum);

		// �ݕ����v���z
		x = txDebitSum.getX() + txDebitSum.getWidth();
		txCreditSum.setEditable(false);
		txCreditSum.setSize(100, 20);
		txCreditSum.setLocation(x, 0);
		pnlSlipFotter.add(txCreditSum);

		// �O�݃t�B�[���h�̃R���|�[�l���g�̔z�u���s���܂��B
		allocateForeignField();

	}

	/**
	 * �O�݃t�B�[���h�̃R���|�[�l���g�̔z�u���s���܂��B
	 */
	public void allocateForeignField() {

		int x = txDebitSum.getX();
		int y = txDebitSum.getHeight() + txDebitSum.getY();

		List<String> words = new ArrayList<String>();
		words.add("C01764"); // �O�݌v1
		words.add("C01765"); // �O�݌v2
		words.add("C11608"); // �O�݌v3
		words.add("C11609"); // �O�݌v4

		for (int i = 0; i < 4; i++) {

			if (i == 2) {
				// �O�݌v3��X�AY�ύX
				x = txCur1.getX() + txCur1.getWidth() + 20;
				y = txCur1.getY();
			}

			TDrillDownForeignField field = foreignList.get(i);
			TLabelNumericField txDebitInSum = field.txDebitInSum;
			TNumericField txCreditInSum = field.txCreditInSum;
			TTextField txCur = field.txCur;

			// �O�ݎؕ��v1
			txDebitInSum.setEditable(false);
			txDebitInSum.setFieldSize(100);
			txDebitInSum.setLabelSize(100);
			txDebitInSum.setLangMessageID(words.get(i)); // �O�݌vX
			txDebitInSum.setLocation(x, y);
			pnlSlipFotter.add(txDebitInSum);

			x += txDebitInSum.getWidth();

			// �O�ݑݕ��v1
			txCreditInSum.setEditable(false);
			txCreditInSum.setSize(100, 20);
			txCreditInSum.setLocation(x, y);
			pnlSlipFotter.add(txCreditInSum);

			x += txCreditInSum.getWidth();

			// �ʉ�1
			txCur.setEditable(false);
			txCur.setSize(60, 20);
			txCur.setLocation(x, y);
			pnlSlipFotter.add(txCur);

			if (i >= 2) {
				// �O��3�`�ȏ�̓f�t�H���g��\��
				field.setVisible(false);
			}

			x = txDebitInSum.getX();
			y += txDebitInSum.getHeight();
		}

	}

	/**
	 * �R���|�[�l���g�Ƀ^�u����ݒ肵�܂��B
	 */
	public void setTabIndex() {
		int i = 1;
		cmbBookComboBox.setTabControlNo(i++);
		cmbBookCurrencyComboBox.setTabControlNo(i++);
		btnPrevSlip.setTabControlNo(i++);
		btnNextSlip.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnApprove.setTabControlNo(i++);
		btnDeny.setTabControlNo(i++);
		btnCancel.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnApproveHistory.setTabControlNo(i++);
		btnAttachment.setTabControlNo(i++);
		btnPreview.setTabControlNo(i++);
		btnReturn.setTabControlNo(i++);
	}

	/**
	 * �w�肳�ꂽ�`�[�ɕR�t���`�[��\������
	 * 
	 * @param slipBooks �`�[
	 * @throws Exception
	 */
	public void show(SlipBooks slipBooks) throws Exception {
		ctrl.show(slipBooks);
	}

	/**
	 * �w�肳�ꂽ��ЃR�[�h�A�`�[�ԍ��ɕR�t���`�[�𒊏o���\������
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param slipNo �`�[�ԍ�
	 * @throws Exception
	 */
	public void show(String companyCode, String slipNo) throws Exception {
		ctrl.show(companyCode, slipNo);
	}

	/**
	 * �w�肳�ꂽ��ЃR�[�h�A�`�[�ԍ��ARow�ԍ��ɕR�t���`�[�𒊏o���\������
	 * 
	 * @param companyCode
	 * @param slipNO
	 * @param i �s
	 * @throws Exception
	 */
	public void show(String companyCode, String slipNO, int i) throws Exception {
		ctrl.show(companyCode, slipNO, i);
	}

	/**
	 * �C���A���ʉ����s��
	 * 
	 * @param flg false �����s��
	 */
	public void setBtnPush(boolean flg) {
		ctrl.setBtnPush(flg);
	}

	/**
	 * �h�����_�E���w�b�_�[���Z�b�g
	 * 
	 * @param header
	 */
	public void setHeader(TDrillDownHeader header) {

		ctrlDrillDownHeader = header;
		pnlSlipHeader.setMaximumSize(header.getMaximumSize());
		pnlSlipHeader.setMinimumSize(header.getMinimumSize());
		pnlSlipHeader.setPreferredSize(header.getPreferredSize());

		GridBagConstraints gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		pnlSlipHeader.add(header, gc);

		initFrame();
	}

	/**
	 * �R���g���[������
	 * 
	 * @return ctrl
	 */
	public TDrillDownController createController() {
		return new TDrillDownController(this);
	}

	/**
	 * �O���[�v��Ђ̊֐����̎擾
	 * 
	 * @return groupMethodName �O���[�v��Ђ̊֐���
	 */
	public String getGroupMethodName() {
		return groupMethodName;
	}

	/**
	 * �O���[�v��Ђ̊֐����̐ݒ�
	 * 
	 * @param groupMethodName �O���[�v��Ђ̊֐���
	 */
	public void setGroupMethodName(String groupMethodName) {
		this.groupMethodName = groupMethodName;
	}

	/**
	 * �ʏ�̊֐����̎擾
	 * 
	 * @return methodName �ʏ�̊֐���
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * �ʏ�̊֐����̐ݒ�
	 * 
	 * @param methodName �ʏ�̊֐���
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * �}�l�[�W���N���X���̎擾
	 * 
	 * @return managerClass �}�l�[�W���N���X��
	 */
	public Class getManagerClass() {
		return managerClass;
	}

	/**
	 * �}�l�[�W���N���X���̐ݒ�
	 * 
	 * @param managerClass �}�l�[�W���N���X��
	 */
	public void setManagerClass(Class managerClass) {
		this.managerClass = managerClass;
	}

	/**
	 * ���F�֘A�{�^���̕\����ؑ�
	 * @param flag true:�\��
	 */
	public void setAprvBtnVisible(boolean flag) {
		ctrl.setAprvBtnVisible(flag);
	}

}
