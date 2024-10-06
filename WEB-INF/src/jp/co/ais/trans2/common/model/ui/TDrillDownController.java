package jp.co.ais.trans2.common.model.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.model.format.*;
import jp.co.ais.trans2.common.model.ui.slip.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.aprvrole.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.history.*;
import jp.co.ais.trans2.model.program.*;
import jp.co.ais.trans2.model.slip.*;
import jp.co.ais.trans2.model.slip.SWK_DTL;
import jp.co.ais.trans2.model.slip.SWK_HDR;
import jp.co.ais.trans2.model.tag.*;

/**
 * �`�[�Ɖ�R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TDrillDownController extends TController {

	/** true:IFRS�敪��蔭�����͉Ȗڂ̔������t���O�ɂ�萧��@�\�L����Client�� */
	public static boolean allowOccurDateBlank = ClientConfig
		.isFlagOn("trans.slip.detail.allow.occurdate.blank.when.noifrs");

	/** true:�������b�Z�[�W���\�� */
	public static boolean isCompletionNotMessage = ClientConfig.isFlagOn("trans.GL0050.show.completion.not.message");

	/** BS��������@�\�g�����ǂ�����Client�� */
	public static boolean isUseBS = ClientConfig.isFlagOn("trans.slip.use.bs");

	/** true:BS��������E�ďo�ǂ���ł��}�[�N���L��<Server> */
	public static boolean isShowAllBS = ClientConfig.isFlagOn("trans.slip.use.bs.sousai.mark");

	/** true:AP��������@�\������Client�� */
	public static boolean isNotUseAP = ClientConfig.isFlagOn("trans.slip.disable.ap.zan");

	/** true:AR��������@�\������Client�� */
	public static boolean isNotUseAR = ClientConfig.isFlagOn("trans.slip.disable.ar.zan");

	/** �t�։�Ђ̃h�����_�E���@�\�g�����ǂ�����Client�� */
	public static boolean isUseGroupCompanyDrillDown = ClientConfig.isFlagOn("trans.slip.drilldown.use.group.company");

	/** �`�[�Ɖ�_�C�A���O */
	protected TDrillDown field;

	/** �Ɖ�Ώۂ̓`�[���X�g */
	protected List<SlipBooks> slipBooksList;

	/** �Ɖ�Ώۂ̓`�[ */
	protected SlipBooks slipBooks;

	/** CallBackListener */
	protected List<TDrillDownCallBackListener> callBackListenerList = null;

	/** SlipStateListener */
	protected List<TDrillDownSlipStateListener> slipStateListenerList = null;

	/** �Y�t�_�C�A���O�R���g���[���[ */
	protected TAttachListDialogCtrl ctrl = null;

	/** �A�N�Z�X�ł���v���O�����ꗗ */
	protected List<SystemizedProgram> prgGroupList = null;

	/** ���W�b�N�� */
	protected Map<String, String> logicMap = new TreeMap<String, String>();

	/** true:�tⳋ@�\�L�� */
	public static boolean isUseTag = ClientConfig.isFlagOn("trans.slip.use.tag");

	/** ���F���I�v�V����: �\�Ȍ����܂ŏ��F */
	protected boolean isApproveAsMuchAsPossible = false;

	/** ���F�O���[�v���X�g */
	protected List<AprvRoleGroup> grpList;

	/** ���F���[�����X�g */
	protected List<AprvRole> roleList;

	/** Row�ԍ� */
	protected int row;

	/**
	 * @param field
	 */
	public TDrillDownController(TDrillDown field) {
		this.field = field;
		initEvent();
		initField();
		initData();
	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * �t�B�[���h������
	 */
	protected void initField() {

		// �ő剻�\��
		field.setExtendedState(Frame.MAXIMIZED_BOTH);

		// �@�\�ʉ݂͑I��s��
		field.cmbBookCurrencyComboBox.getComboBox().setEnabled(false);

		// �d��ꗗ������
		initSlipDetailTable(getCompany());

		if (!isUseGroupCompanyDrillDown) {
			field.btnPrevSlip.setVisible(false);
			field.btnNextSlip.setVisible(false);
		}
	}

	/**
	 * �`�[���W�b�N������ێ�.
	 */
	protected void initData() {
		// 011 �����`�[
		logicMap.put("GL0010", "GL0010InputCashFlowSlipPanelCtrl");
		// 012 �o���`�[
		logicMap.put("GL0020", "GL0020OutputCashFlowSlipPanelCtrl");
		// 013 �U�֓`�[
		logicMap.put("GL0030", "GL0030TransferSlipPanelCtrl");
		// 014 �U�ߓ`�[
		logicMap.put("GL6010", "GL6010ReversingSlipPanelCtrl");
		// 023 ���v��
		logicMap.put("AP0010", "AP0010PaymentSlipPanelCtrl");
		// 031 ���v��
		logicMap.put("AR0010", "AR0010ReceivableSlipPanelCtrl");
	}

	/**
	 * �d��ꗗ������
	 * 
	 * @param company
	 */
	protected void initSlipDetailTable(Company company) {

		AccountConfig ac = company.getAccountConfig();

		// �Ȗ�
		String itemCaption = ac.getItemName();
		if (!Util.isNullOrEmpty(ac.getSubItemName())) {
			itemCaption = itemCaption + " �E " + ac.getSubItemName();
		}
		if (ac.isUseDetailItem() && !Util.isNullOrEmpty(ac.getDetailItemName())) {
			itemCaption = itemCaption + " �E " + ac.getDetailItemName();
		}

		// �Ǘ�1�`3
		int management1to3Width = -1;
		String management1to3Caption = "";
		if (ac.isUseManagement1() || ac.isUseManagement2() || ac.isUseManagement3()) {

			management1to3Width = 242;

			if (ac.isUseManagement1()) {
				management1to3Caption = ac.getManagement1Name();
			}
			if (ac.isUseManagement2()) {
				management1to3Caption = management1to3Caption + " �E " + ac.getManagement2Name();
			}
			if (ac.isUseManagement3()) {
				management1to3Caption = management1to3Caption + " �E " + ac.getManagement3Name();
			}

		}

		// �Ǘ�4�`6
		int management4to6Width = -1;
		String management4to6Caption = "";
		if (ac.isUseManagement4() || ac.isUseManagement5() || ac.isUseManagement6()) {

			management4to6Width = 242;

			if (ac.isUseManagement4()) {
				management4to6Caption = ac.getManagement4Name();
			}
			if (ac.isUseManagement5()) {
				management4to6Caption = management4to6Caption + " �E " + ac.getManagement5Name();
			}
			if (ac.isUseManagement6()) {
				management4to6Caption = management4to6Caption + " �E " + ac.getManagement6Name();
			}

		}

		// ���v����1�`3
		int nonAccountWidth = -1;
		String nonAccountCaption = "";
		if (ac.isUseNotAccounting1() || ac.isUseNotAccounting2() || ac.isUseNotAccounting3()) {

			nonAccountWidth = 150;

			if (ac.isUseNotAccounting1()) {
				nonAccountCaption = ac.getNonAccounting1Name();
			}
			if (ac.isUseNotAccounting2()) {
				nonAccountCaption = nonAccountCaption + " �E " + ac.getNonAccounting2Name();
			}
			if (ac.isUseNotAccounting3()) {
				nonAccountCaption = nonAccountCaption + " �E " + ac.getNonAccounting3Name();
			}

		}

		field.tblSlipDetail.addColumn(TDrillDown.SC.item, itemCaption, 190);
		// �ؕ����z
		field.tblSlipDetail.addColumn(TDrillDown.SC.debit, getWord("C01557"), 100, SwingConstants.RIGHT);
		// �ݕ����z
		field.tblSlipDetail.addColumn(TDrillDown.SC.credit, getWord("C01559"), 100, SwingConstants.RIGHT);
		// "�ʉ� �E ������ �E ��"
		field.tblSlipDetail.addColumn(TDrillDown.SC.currencyCode,
			getWord("C00371") + "�E" + getWord("C00431") + "�E" + getWord("C00312"), 120, SwingConstants.CENTER);
		// �s�E�v
		field.tblSlipDetail.addColumn(TDrillDown.SC.remark, getWord("C00119"), 242);
		// "�v�㕔�� �E ����� �E �Ј�"
		field.tblSlipDetail.addColumn(TDrillDown.SC.department,
			getWord("C00863") + "�E" + getWord("C00408") + "�E" + getWord("C00246"), 242);
		field.tblSlipDetail.addColumn(TDrillDown.SC.management1to3, management1to3Caption, management1to3Width);
		field.tblSlipDetail.addColumn(TDrillDown.SC.management4to6, management4to6Caption, management4to6Width);
		field.tblSlipDetail.addColumn(TDrillDown.SC.nonAccount, nonAccountCaption, nonAccountWidth);
		field.tblSlipDetail.addColumn(TDrillDown.SC.bs, getWord("C04291"), isUseBS ? 100 : -1); // BS����
		field.tblSlipDetail.addColumn(TDrillDown.SC.ap, getWord("C01084"), !isNotUseAP ? 100 : -1); // ���c��
		field.tblSlipDetail.addColumn(TDrillDown.SC.ar, getWord("C01080"), !isNotUseAR ? 100 : -1); // ���c��
		field.tblSlipDetail.setRowHeight(50);
	}

	/**
	 * ��ʂ̃C�x���g��`
	 */
	protected void initEvent() {

		// ����̏���
		field.cmbBookComboBox.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (ItemEvent.SELECTED == e.getStateChange()) {
					cmbBookComboBox_itemStateChanged();
				}
			}
		});

		// �ʉݑI���̏���
		field.cmbBookCurrencyComboBox.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (ItemEvent.SELECTED == e.getStateChange()) {
					cmbBookCurrencyComboBox_itemStateChanged();
				}
			}
		});

		// ��
		field.btnPrevSlip.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnPrevSlip_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// ��
		field.btnNextSlip.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNextSlip_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		field.btnApprove.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDoApprove_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		field.btnDeny.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDoDenySlip_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		field.btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDoCancel_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// �Y�t�\���{�^���̏���
		field.btnAttachment.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnAttachment_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// ���F�����{�^���̏���
		field.btnApproveHistory.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnApproveHistory_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// �v���r���[�{�^���̏���
		field.btnPreview.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnPreview_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// ����{�^���̏���
		field.btnReturn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnReturn_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// �C���{�^���̏���
		field.btnModify.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// ���ʃ{�^���̏���
		field.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		field.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				fireChangedSlipHeaderListener();
			}
		});
	}

	/**
	 * ���낪�I�����ꂽ�ꍇ
	 */
	protected void cmbBookComboBox_itemStateChanged() {

		try {

			if (field.cmbBookComboBox.getComboBox().getSelectedIndex() == 0) {
				field.cmbBookCurrencyComboBox.getComboBox().setEnabled(false);
			} else {
				field.cmbBookCurrencyComboBox.getComboBox().setEnabled(true);
			}
			field.cmbBookCurrencyComboBox.getComboBox().setSelectedIndex(0);

			// �`�[�f�[�^�Z�b�g
			setSlip(getSlip());

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �\���ʉ݂��I�����ꂽ�ꍇ
	 */
	protected void cmbBookCurrencyComboBox_itemStateChanged() {

		try {
			// �`�[�f�[�^�Z�b�g
			setSlip(getSlip());

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * ���`�[
	 */
	protected void btnPrevSlip_Click() {

		try {

			SlipBooks books = slipBooks;

			// ���`�[�\��
			if (slipBooksList != null) {
				int index = slipBooksList.indexOf(slipBooks) - 1;
				if (index >= 0) {
					books = slipBooksList.get(index);
					row--;
				}
			}

			// �w�b�_�[�������s�v
			show(books, false);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * ���`�[
	 */
	protected void btnNextSlip_Click() {

		try {

			SlipBooks books = slipBooks;

			// ���`�[�\��
			if (slipBooksList != null) {
				int index = slipBooksList.indexOf(slipBooks) + 1;
				if (index < slipBooksList.size()) {
					books = slipBooksList.get(index);
					row++;
				}
			}

			// �w�b�_�[�������s�v
			show(books, false);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * [���F]��������
	 */
	protected void btnDoApprove_Click() {
		try {
			TApproveOptionDialog d = new TApproveOptionDialog(field, true);
			d.setLocationRelativeTo(field);
			d.setVisible(true);
			if (!d.isOK()) {
				// �L�����Z���̏ꍇ�������f
				return;
			}
			isApproveAsMuchAsPossible = d.isApproveAsMuchAsPossible();
			SWK_HDR hdr = slipBooks.getSlip().getHeader();
			if (hdr == null) {
				throw new TException("I00071");
			}
			SlipDen localDen = getDen(hdr);
			SlipDen bean = (SlipDen) request(getSlipManagerClass(), "checkAndApproveSlip", localDen,
				isApproveAsMuchAsPossible);
			fireUpateSlipStateListener(bean, row);

			// �w�b�_�[���X�V
			updateSlipBookHDR(bean);

			// ����{�^���𐧌�
			switchApprovalBtnsEnabled(bean.getSWK_UPD_KBN());

			setSlip(getSlip());
			if (!isCompletionNotMessage) {
				// �������b�Z�[�W
				showMessage(field, "I00013");
			}
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [�۔F]�{�^����������
	 */
	protected void btnDoDenySlip_Click() {
		try {
			// FIXME ���[�N�t���[���F�ł͂Ȃ��ʏ�̏��F�̏ꍇ�̔۔F�ɂ��Ă͕K�v�ɉ����Đ���ǉ�
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [���F���]��������
	 */
	protected void btnDoCancel_Click() {
		try {
			SWK_HDR hdr = slipBooks.getSlip().getHeader();
			if (hdr == null) {
				throw new TException("I00071");
			}
			SlipDen localDen = getDen(hdr);
			SlipDen bean = (SlipDen) request(getSlipManagerClass(), "checkAndCancelApprovedSlip", localDen);
			fireUpateSlipStateListener(bean, row);

			// �w�b�_�[���X�V
			updateSlipBookHDR(bean);
			// �{�^�������ۂ̐ؑ�
			switchApprovalBtnsEnabled(bean.getSWK_UPD_KBN());

			setSlip(getSlip());
			if (!isCompletionNotMessage) {
				// �������b�Z�[�W
				showMessage(field, "I00013");
			}

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * SlipBooks�̃w�b�_�[�X�V
	 * 
	 * @param slipDen
	 */
	protected void updateSlipBookHDR(SlipDen slipDen) {
		SWK_HDR hdr = slipBooks.getSlip().getHeader();
		hdr.setSWK_UPD_KBN(slipDen.getSWK_UPD_KBN());
		hdr.getSyoCtl().setAPRV_ROLE_CODE(slipDen.getAPRV_ROLE_CODE());
		hdr.getSyoCtl().setNEXT_APRV_ROLE_CODE(slipDen.getNEXT_APRV_ROLE_CODE());
	}

	/**
	 * �w�����[�Y�t�\��]��������
	 */
	protected void btnAttachment_Click() {

		try {

			Slip slip = slipBooks.getSlip();

			List<SWK_ATTACH> attachments = (List<SWK_ATTACH>) request(SlipAttachmentManager.class, "get",
				slip.getCompanyCode(), slip.getSlipNo());

			if (attachments == null || attachments.isEmpty()) {
				attachments = new ArrayList<SWK_ATTACH>();
			}

			// �Y�t�f�[�^�ݒ�
			slip.getHeader().setAttachments(attachments);

			ctrl = createAttachListDialogCtrl();
			ctrl.setSlip(slip); // �`�[
			ctrl.show();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * @return �Y�t�ꗗ�_�C�A���OCtrl
	 */
	protected TAttachListDialogCtrl createAttachListDialogCtrl() {
		return new TAttachListDialogCtrl(field.pnlBody, getProgramInfo());
	}

	/**
	 * �w�����[���F����]��������
	 */
	protected void btnApproveHistory_Click() {

		try {

			Slip slip = slipBooks.getSlip();

			ApproveHistoryCondition condition = new ApproveHistoryCondition();
			condition.setCompanyCode(slip.getCompanyCode());
			condition.setSlipNo(slip.getSlipNo());

			List<ApproveHistory> historyList = (List<ApproveHistory>) request(ApproveHistoryManager.class, "get",
				condition);

			if (historyList == null || historyList.isEmpty()) {
				showMessage("I00022");
				return;
			}

			TApproveHistoryDialog dialog = new TApproveHistoryDialog(field, true);
			dialog.setData(historyList);
			dialog.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �w�����[�v���r���[]��������
	 */
	protected void btnPreview_Click() {

		try {

			Slip slip = slipBooks.getSlip();

			List<String> slipNoList = new ArrayList<String>();
			slipNoList.add(slip.getSlipNo());

			// �f�[�^���o
			byte[] data = null;

			// GROUP��v���g����Ђ̏ꍇ�A�t�֐�����o����
			AccountConfig ac = getCompany().getAccountConfig();
			if (ac.isUseGroupAccount()) {
				data = (byte[]) request(getManagerClass(), field.groupMethodName, slipNoList);

				// GROUP��v���g��Ȃ���Ђ̏ꍇ�A���Ђ̂�
			} else {
				data = (byte[]) request(getManagerClass(), field.methodName, slip.getCompanyCode(), slipNoList);
			}

			String fileName = slip.getHeader().getSWK_DEN_SYU_NAME();
			printOutPDF(data, fileName);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * ����{�^������
	 */
	protected void btnReturn_Click() {
		field.setVisible(false);
		field.dispose();
		fireChangedSlipHeaderListener();
	}

	/**
	 * �`�[�w�b�_�ύX���X�i�[��ݒ�
	 */
	protected void fireChangedSlipHeaderListener() {

		if (ctrl != null) {
			SWK_HDR hdr = slipBooks.getSlip().getHeader();
			hdr.setExistAttachment(ctrl.getView().tbl.getRowCount() != 0);

			if (getCallBackListenerList() != null && !getCallBackListenerList().isEmpty()) {
				for (TDrillDownCallBackListener listener : getCallBackListenerList()) {
					listener.changedSlipHeader(hdr);
				}
			}
		}
	}

	/**
	 * �`�[��ԕύX���X�i�[��ݒ�
	 * 
	 * @param slipDen
	 * @param n
	 */
	protected void fireUpateSlipStateListener(SlipDen slipDen, int n) {
		if (getSlipStateListenerList() != null && !getSlipStateListenerList().isEmpty()) {
			for (TDrillDownSlipStateListener listener : getSlipStateListenerList()) {
				listener.updateSlipState(slipDen, n);
			}
		}

	}

	/**
	 * �w�肳�ꂽ�`�[ �ɕR�t���`�[��\������
	 * 
	 * @param books �`�[
	 * @throws Exception
	 */
	public void show(SlipBooks books) throws Exception {
		show(books, true);
	}

	/**
	 * �w�肳�ꂽ�`�[ �ɕR�t���`�[��\������
	 * 
	 * @param books �`�[
	 * @param isInitHeader true:�w�b�_�[�`��
	 * @throws Exception
	 */
	public void show(SlipBooks books, boolean isInitHeader) throws Exception {

		if (books == null) {
			// �w��̃f�[�^�͊��ɍ폜����Ă��܂��B
			throw new TException("I00167");
		}

		// �`�[�Z�b�g
		slipBooks = books;

		if (isInitHeader) {
			// �w�b�_�[�t�B�[���h������
			initSlipHeader(slipBooks);
		}

		// �t�։�Ђ̃h�����_�E���@�\
		if (isUseGroupCompanyDrillDown && slipBooksList != null) {
			// ���ݕ\��������ɂ�荶�{�^���A�E�{�^���̐���

			int index = slipBooksList.indexOf(slipBooks);

			field.btnPrevSlip.setEnabled(index > 0);
			field.btnNextSlip.setEnabled(index < slipBooksList.size() - 1);
		}

		// �䒠������
		initBook(slipBooks);

		// �`�[�f�[�^�Z�b�g
		setSlip(getSlip());

		// �\��
		field.setVisible(true);

		// �{�^����������
		SWK_HDR swk_hdr = slipBooks.getSlip().getHeader();
		switchApprovalBtnsEnabled(swk_hdr.getSWK_UPD_KBN());

	}

	/**
	 * �w�肳�ꂽ��ЃR�[�h�A�`�[�ԍ��ɕR�t���`�[�𒊏o���\������
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param slipNo �`�[�ԍ�
	 * @throws Exception
	 */
	public void show(String companyCode, String slipNo) throws Exception {

		SlipBooks books = null;

		// �t�։�Ђ̃h�����_�E���@�\
		if (isUseGroupCompanyDrillDown) {
			slipBooksList = getSlipBooksList(companyCode, slipNo);

			if (slipBooksList != null && !slipBooksList.isEmpty()) {

				// �����l
				books = slipBooksList.get(0);

				// ��ЃR�[�h�Ɠ����̓`�[��\������
				for (SlipBooks bean : slipBooksList) {
					if (bean.getSlip() != null && companyCode.equals(bean.getSlip().getCompanyCode())) {
						books = bean;
						break;
					}
				}
			}

		} else {
			// �ʏ탂�[�h
			books = getSlipBooks(companyCode, slipNo);
		}

		show(books);
	}

	/**
	 * �w�肳�ꂽ��ЃR�[�h�A�`�[�ԍ�,row�ԍ��ɕR�t���`�[�𒊏o���\������
	 * 
	 * @param companyCode
	 * @param slipNO
	 * @param i
	 * @throws Exception
	 */
	public void show(String companyCode, String slipNO, int i) throws Exception {
		show(companyCode, slipNO);
		row = i;
	}

	/**
	 * �C���A���ʃ{�^�������s�� flg
	 * 
	 * @param flg false �����s��
	 */
	public void setBtnPush(boolean flg) {

		field.btnModify.setEnabled(flg);
		field.btnCopy.setEnabled(flg);
	}

	/**
	 * �䒠�̏�����
	 * 
	 * @param books
	 */
	protected void initBook(SlipBooks books) {
		if (books == null) {
			return;
		}

		Slip slip = books.getOwnBookSlip();
		// �����`�[���Ȃ���΁AIFRS�����\��
		if (slip == null || slip.getDetails() == null || slip.getDetails().isEmpty()) {
			slip = books.getIFRSBookSlip();
			if (slip != null && slip.getDetails() != null && !slip.getDetails().isEmpty()) {
				field.cmbBookComboBox.selectAccountBook(AccountBook.IFRS);
			}
		}

		// �����O�ݍ��v�\��
		int count = 2;
		try {
			count = Util.avoidNullAsInt(ClientConfig.getProperty("trans.slip.max.foreign.currency.count"));
		} catch (Throwable e) {
			// �����Ȃ�
			count = 2;
		}

		if (count > 2) {
			// �\�̌��܂ŕ\������
			for (int i = 2; i < count; i++) {
				if (i >= field.foreignList.size()) {
					break;
				}
				field.foreignList.get(i).setVisible(true);
			}

		}

	}

	/**
	 * �w�b�_�[�̏�����
	 * 
	 * @param books
	 */
	protected void initSlipHeader(SlipBooks books) {

		Slip slip = books.getOwnBookSlip();
		TDrillDownHeader header = getDrillDownHeader(slip);

		// �t�։�Ђ̃h�����_�E���@�\
		if (isUseGroupCompanyDrillDown) {
			header.txCompany.setVisible(true);
		}

		field.setHeader(header);

		if (!isUseTag) {
			// �����̏ꍇ�A�tⳋ@�\ ��\��
			field.ctrlDrillDownHeader.btnTag.setVisible(false);
			field.ctrlDrillDownHeader.ctrlTag1.setVisible(false);
			field.ctrlDrillDownHeader.ctrlTag2.setVisible(false);
		}
		if (!getCompany().getAccountConfig().isUseWorkflowApprove()) {
			field.ctrlDrillDownHeader.ctrlAprvGroup.setVisible(false);
		}

		// �C�x���g��`
		initFieldEvent();

	}

	/**
	 * �t�B�[���h�̃C�x���g��`
	 */
	protected void initFieldEvent() {
		// �tⳋ@�\�ۑ��̏���
		field.ctrlDrillDownHeader.btnTag.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnTag_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * �tⳕۑ��{�^��
	 */
	protected void btnTag_Click() {

		try {
			// �ۑ����G���e�B�e�B�Z�b�g
			// �tⳋ@�\
			List<SWK_TAG> list = new ArrayList<SWK_TAG>();
			if (field.ctrlDrillDownHeader.ctrlTag1.getEntity() != null) {
				Tag bean = field.ctrlDrillDownHeader.ctrlTag1.getEntity();
				SWK_TAG tag = new SWK_TAG();
				tag.setKAI_CODE(getCompanyCode());
				tag.setSWK_DEN_NO(field.ctrlDrillDownHeader.txSlipNo.getValue());
				tag.setSEQ(1);
				tag.setTAG_CODE(bean.getCode());
				tag.setTAG_COLOR(bean.getColor());
				tag.setTAG_TITLE(field.ctrlDrillDownHeader.ctrlTag1.getName()); // ��ʒl���g�p
				list.add(tag);
			}
			if (field.ctrlDrillDownHeader.ctrlTag2.getEntity() != null) {
				Tag bean = field.ctrlDrillDownHeader.ctrlTag2.getEntity();
				SWK_TAG tag = new SWK_TAG();
				tag.setKAI_CODE(getCompanyCode());
				tag.setSWK_DEN_NO(field.ctrlDrillDownHeader.txSlipNo.getValue());
				tag.setSEQ(2);
				tag.setTAG_CODE(bean.getCode());
				tag.setTAG_COLOR(bean.getColor());
				tag.setTAG_TITLE(field.ctrlDrillDownHeader.ctrlTag2.getName()); // ��ʒl���g�p
				list.add(tag);
			}
			slipBooks.getSlip().getHeader().setSwkTags(list);
			request(SlipTagManager.class, "entry", slipBooks.getSlip());

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w����ʂŎw�肳�ꂽ�䒠�A�\���ʉ݂ɕR�t���`�[��Ԃ�
	 * 
	 * @return �w����ʂŎw�肳�ꂽ�䒠�A�\���ʉ݂ɕR�t���`�[
	 */
	protected Slip getSlip() {

		if (slipBooks == null) {
			return null;
		}

		if (AccountBook.OWN == field.cmbBookComboBox.getAccountBook()) {
			return slipBooks.getOwnBookSlip();
		} else if (AccountBook.IFRS == field.cmbBookComboBox.getAccountBook()) {
			if (CurrencyType.KEY == field.cmbBookCurrencyComboBox.getCurrencyType()) {
				return slipBooks.getIFRSBookSlip();
			}
			return slipBooks.getIFRSFuncSlip();
		}

		return null;

	}

	/**
	 * �`�[�����w����ʂɃZ�b�g����
	 * 
	 * @param slip �`�[
	 * @throws TException
	 */
	protected void setSlip(Slip slip) throws TException {

		// �w�b�_�[�̃Z�b�g
		field.ctrlDrillDownHeader.setSlipHeader(slip);

		// ���ׂ̃Z�b�g
		field.tblSlipDetail.removeRow();
		for (SWK_DTL swk : slip.getDetails()) {
			field.tblSlipDetail.addRow(toTableList(slip, swk));
		}

		// �t�b�^�[�̃Z�b�g
		field.txDebitSum.setValue(null);
		field.txDebitInSum1.setValue(null);
		field.txDebitInSum2.setValue(null);
		field.txCreditSum.setValue(null);
		field.txCreditInSum1.setValue(null);
		field.txCreditInSum2.setValue(null);
		field.txCur1.setText(null);
		field.txCur2.setText(null);

		if (slip.getDetails() == null || slip.getDetails().isEmpty()) {
			return;
		}

		// �ؕ��v
		if (slip.getDebitKeyAmount() != null) {
			field.txDebitSum.setValue(
				NumberFormatUtil.formatNumber(slip.getDebitKeyAmount(), slip.getBaseCurrency().getDecimalPoint()));
		}
		// �ؕ��O�݌v1
		if (slip.getForeignCurrencyAt(0) != null) {
			field.txDebitInSum1.setValue(NumberFormatUtil.formatNumber(slip.getDebitForeignAmountAt(0),
				slip.getForeignCurrencyAt(0).getDecimalPoint()));
			field.txCur1.setText(slip.getForeignCurrencyAt(0).getCode());
		}
		// �ؕ��O�݌v2
		if (slip.getForeignCurrencyAt(1) != null) {
			field.txDebitInSum2.setValue(NumberFormatUtil.formatNumber(slip.getDebitForeignAmountAt(1),
				slip.getForeignCurrencyAt(1).getDecimalPoint()));
			field.txCur2.setText(slip.getForeignCurrencyAt(1).getCode());
		}

		// �ݕ��v
		if (slip.getCreditKeyAmount() != null) {
			field.txCreditSum.setValue(
				NumberFormatUtil.formatNumber(slip.getCreditKeyAmount(), slip.getBaseCurrency().getDecimalPoint()));
		}
		// �ݕ��O�݌v1
		if (slip.getForeignCurrencyAt(0) != null) {
			field.txCreditInSum1.setValue(NumberFormatUtil.formatNumber(slip.getCreditForeignAmountAt(0),
				slip.getForeignCurrencyAt(0).getDecimalPoint()));
		}
		// �ݕ��O�݌v2
		if (slip.getForeignCurrencyAt(1) != null) {
			field.txCreditInSum2.setValue(NumberFormatUtil.formatNumber(slip.getCreditForeignAmountAt(1),
				slip.getForeignCurrencyAt(1).getDecimalPoint()));
		}

		// �����O�ݍ��v�̕\��
		for (int i = 2; i < field.foreignList.size(); i++) {
			TDrillDownForeignField foreignField = field.foreignList.get(i);

			if (foreignField.isVisible()) {
				Currency foreignCurrency = slip.getForeignCurrencyAt(i);
				if (foreignCurrency != null) {
					int digit = foreignCurrency.getDecimalPoint();
					String debit = NumberFormatUtil.formatNumber(slip.getDebitForeignAmountAt(i), digit);
					String credit = NumberFormatUtil.formatNumber(slip.getCreditForeignAmountAt(i), digit);

					foreignField.txDebitInSum.setValue(debit);
					foreignField.txCreditInSum.setValue(credit);
					foreignField.txCur.setText(foreignCurrency.getCode());
				}
			}
		}

	}

	/**
	 * ���׍s���ꗗ�ɃZ�b�g����`���ɕϊ�����
	 * 
	 * @param slip
	 * @param swk
	 * @return �ꗗ�f�[�^
	 * @throws TException
	 */
	protected List<Object> toTableList(Slip slip, SWK_DTL swk) throws TException {

		List<Object> list = new ArrayList<Object>();

		// �Ȗ�
		list.add(StringUtil.getHtmlString(SwingConstants.LEFT,
			new String[] { Util.avoidNull(swk.getSWK_KMK_CODE()) + " " + Util.avoidNull(swk.getSWK_KMK_NAME_S()),
					Util.avoidNull(swk.getSWK_HKM_CODE()) + " " + Util.avoidNull(swk.getSWK_HKM_NAME_S()),
					Util.avoidNull(swk.getSWK_UKM_CODE()) + " " + Util.avoidNull(swk.getSWK_UKM_NAME_S()) }));

		// ���z
		String amount = StringUtil.getHtmlString(SwingConstants.RIGHT, new String[] {
				FormatUtil.foreingAmountFormat(slip.getBaseCurrency().getCode(), swk.getSWK_CUR_CODE(),
					swk.getSWK_IN_KIN(), swk.getCUR_DEC_KETA()),
				FormatUtil.rateFormat(slip.getBaseCurrency().getCode(), swk.getSWK_CUR_CODE(), swk.getSWK_CUR_RATE()),
				NumberFormatUtil.formatNumber(swk.getSWK_KIN(), slip.getBaseCurrency().getDecimalPoint()) });

		// �ؕ�
		if (swk.isDR()) {
			list.add(amount);
			list.add(null);

			// �ݕ�
		} else {
			list.add(null);
			list.add(amount);
		}

		// ������
		String occurDate = DateUtil.toYMDString(swk.getHAS_DATE());
		if (isAllowOccurDateBlank()) {
			if (!swk.isUseItemOccurDate()) {
				// �Ȗڂ����������g�p�̏ꍇ�A�������̓u�����N�ɂ���
				occurDate = "";
			}
		}

		// �ʉ݁A�������A��
		list.add(StringUtil.getHtmlString(SwingConstants.LEFT,
			new String[] { FormatUtil.currencyFormat(slip.getBaseCurrency().getCode(), swk.getSWK_CUR_CODE()),
					occurDate,
					Util.avoidNull(swk.getSWK_ZEI_CODE()) + " " + Util.avoidNull(swk.getSWK_ZEI_NAME_S()) }));

		// �s�E�v
		list.add(StringUtil.getHtmlString(SwingConstants.LEFT,
			StringUtil
				.getParagraphString(
					Util.avoidNull(swk.getSWK_GYO_TEK_CODE()) + " " + Util.avoidNull(swk.getSWK_GYO_TEK()), 40, 3)
				.toArray()));

		// �v�㕔��A�����A�Ј�
		list.add(StringUtil.getHtmlString(SwingConstants.LEFT,
			new String[] { Util.avoidNull(swk.getSWK_DEP_CODE()) + " " + Util.avoidNull(swk.getSWK_DEP_NAME_S()),
					Util.avoidNull(swk.getSWK_TRI_CODE()) + " " + Util.avoidNull(swk.getSWK_TRI_NAME_S()),
					Util.avoidNull(swk.getSWK_EMP_CODE()) + " " + Util.avoidNull(swk.getSWK_EMP_NAME_S()) }));

		// �Ǘ�1�`3
		list.add(StringUtil.getHtmlString(SwingConstants.LEFT,
			new String[] { Util.avoidNull(swk.getSWK_KNR_CODE_1()) + " " + Util.avoidNull(swk.getSWK_KNR_NAME_S_1()),
					Util.avoidNull(swk.getSWK_KNR_CODE_2()) + " " + Util.avoidNull(swk.getSWK_KNR_NAME_S_2()),
					Util.avoidNull(swk.getSWK_KNR_CODE_3()) + " " + Util.avoidNull(swk.getSWK_KNR_NAME_S_3()) }));

		// �Ǘ�4�`6
		list.add(StringUtil.getHtmlString(SwingConstants.LEFT,
			new String[] { Util.avoidNull(swk.getSWK_KNR_CODE_4()) + " " + Util.avoidNull(swk.getSWK_KNR_NAME_S_4()),
					Util.avoidNull(swk.getSWK_KNR_CODE_5()) + " " + Util.avoidNull(swk.getSWK_KNR_NAME_S_5()),
					Util.avoidNull(swk.getSWK_KNR_CODE_6()) + " " + Util.avoidNull(swk.getSWK_KNR_NAME_S_6()) }));

		// ���v����1�`3
		list.add(StringUtil.getHtmlString(SwingConstants.LEFT, new String[] { Util.avoidNull(swk.getSWK_HM_1()),
				Util.avoidNull(swk.getSWK_HM_2()), Util.avoidNull(swk.getSWK_HM_3()) }));

		// BS����}�[�N
		String bsMark = "";
		String bsSlipNo = "";
		if (isUseBS) {
			if (isShowAllBS) {
				// �S�ċL�ڂ̏ꍇ�A�������E���K���L��
				if (swk.getSWK_KESI_KBN() == SWK_DTL.KESI_KBN.BASE) {
					bsMark = getWord("C03191"); // �� ��
				} else if (swk.getSWK_KESI_KBN() == SWK_DTL.KESI_KBN.FORWARD) {
					bsMark = getWord("C11766"); // ��
				}
				bsSlipNo = Util.avoidNull(swk.getSWK_SOUSAI_DEN_NO()); // �����`�[�ԍ�
			} else {
				// �]������ BS�����͍s�ԍ��܂ŋL�ڂ̂��̂̂ݖ��m�ɕ\�L
				if (swk.getSWK_SOUSAI_GYO_NO() != null) {
					if (swk.getSWK_KESI_KBN() == SWK_DTL.KESI_KBN.BASE) {
						bsMark = getWord("C03191"); // �� ��
					} else {
						bsMark = getWord("C11766"); // ��
					}
					bsSlipNo = swk.getSWK_SOUSAI_DEN_NO(); // �����`�[�ԍ�
				}
			}
			if (Util.isNullOrEmpty(bsSlipNo)) {
				bsSlipNo = Util.avoidNull(swk.getSWK_KESI_DEN_NO()); // �����ԍ�
			}
		}
		list.add(StringUtil.getHtmlString(SwingConstants.LEFT, new String[] { bsMark, bsSlipNo }));

		int aparKbn = swk.getSWK_APAR_KESI_KBN();

		// AP�}�[�N
		String apMark = "";
		String apSlipNo = "";
		if (!isNotUseAP && (aparKbn == SWK_DTL.APAR_KESI_KBN.AP_BASE || aparKbn == SWK_DTL.APAR_KESI_KBN.AP_FORWARD)) {

			if (aparKbn == SWK_DTL.APAR_KESI_KBN.AP_BASE) {
				apMark = getWord("C03191"); // �� ��
			} else {
				apMark = getWord("C11766"); // ��
			}
			apSlipNo = swk.getSWK_APAR_DEN_NO(); // �����`�[�ԍ�
		}
		list.add(StringUtil.getHtmlString(SwingConstants.LEFT, new String[] { apMark, apSlipNo }));

		// AR�}�[�N
		String arMark = "";
		String arSlipNo = "";
		if (!isNotUseAR && (aparKbn == SWK_DTL.APAR_KESI_KBN.AR_BASE || aparKbn == SWK_DTL.APAR_KESI_KBN.AR_FORWARD)) {

			if (aparKbn == SWK_DTL.APAR_KESI_KBN.AR_BASE) {
				arMark = getWord("C03191"); // �� ��
			} else {
				arMark = getWord("C11766"); // ��
			}
			arSlipNo = swk.getSWK_APAR_DEN_NO(); // �����`�[�ԍ�
		}
		list.add(StringUtil.getHtmlString(SwingConstants.LEFT, new String[] { arMark, arSlipNo }));
		return list;
	}

	/**
	 * @return true:�������u�����N�\
	 */
	protected boolean isAllowOccurDateBlank() {
		return allowOccurDateBlank && !getCompany().getAccountConfig().isUseIfrs();
	}

	/**
	 * �h�����_�E���w�b�_�[��Ԃ�
	 * 
	 * @param slip
	 * @return �`�[�ɉ������h�����_�E���w�b�_�[
	 */
	public TDrillDownHeader getDrillDownHeader(Slip slip) {

		if (SlipKind.PURCHASE == slip.getSlipKind()) {
			return new TDrillDownHeaderAP(getLoginLanguage());
		} else if (SlipKind.SALES == slip.getSlipKind()) {
			return new TDrillDownHeaderAR(getLoginLanguage());
		} else if (SlipKind.EMPLOYEE == slip.getSlipKind()) {
			return new TDrillDownHeaderEP(getLoginLanguage());
		} else {
			return new TDrillDownHeader(getLoginLanguage());
		}

	}

	/**
	 * �w��̉�ЃR�[�h�A�`�[�ԍ��ɕR�t���`�[��Ԃ�
	 * 
	 * @param companyCode
	 * @param slipNo
	 * @return �`�[
	 * @throws Exception
	 */
	protected SlipBooks getSlipBooks(String companyCode, String slipNo) throws Exception {
		List<SlipBooks> list = getSlipBooksList(companyCode, slipNo);

		if (list == null || list.isEmpty()) {
			return null;
		}

		return list.get(0);
	}

	/**
	 * �w��̉�ЃR�[�h�A�`�[�ԍ��ɕR�t���`�[��Ԃ� <br>
	 * �t�։�Ђ̓`�[�擾
	 * 
	 * @param companyCode
	 * @param slipNo
	 * @return �`�[
	 * @throws Exception
	 */
	protected List<SlipBooks> getSlipBooksList(String companyCode, String slipNo) throws Exception {

		SlipCondition condition = createSlipCondition();

		// �t�։�Ђ̃h�����_�E���@�\�ȊO�A��ЃR�[�h�ݒ�
		if (!isUseGroupCompanyDrillDown) {
			// ��ЃR�[�h
			condition.setCompanyCode(companyCode);
		}
		// �`�[�ԍ�
		condition.setSlipNo(slipNo);
		condition.setSearchWorkFlow(true);
		List<SlipBooks> list = (List<SlipBooks>) request(getManagerClass(), "getSlipBooks", condition);

		if (list == null || list.isEmpty()) {
			return null;
		}

		return list;

	}

	/**
	 * @return �`�[��������
	 */
	protected SlipCondition createSlipCondition() {
		return new SlipCondition();
	}

	/**
	 * @return �}�l�[�W���̎擾
	 */
	protected Class getManagerClass() {
		return field.managerClass;
	}

	/**
	 * @return callBackListener��߂��܂��B
	 */
	public List<TDrillDownCallBackListener> getCallBackListenerList() {
		return callBackListenerList;
	}

	/**
	 * @param callBackListener callBackListener��ǉ�����
	 */
	public void addCallBackListener(TDrillDownCallBackListener callBackListener) {

		if (callBackListenerList == null) {
			callBackListenerList = new ArrayList<TDrillDownCallBackListener>();
		}
		callBackListenerList.add(callBackListener);
	}

	/**
	 * @return slipStateListeners��߂��܂��B
	 */
	public List<TDrillDownSlipStateListener> getSlipStateListenerList() {
		return slipStateListenerList;
	}

	/**
	 * @param slipStateListener slipStateListener��ǉ�����
	 */
	public void addSlipStateListener(TDrillDownSlipStateListener slipStateListener) {
		if (slipStateListenerList == null) {
			slipStateListenerList = new ArrayList<TDrillDownSlipStateListener>();
		}
		slipStateListenerList.add(slipStateListener);
	}

	/**
	 * �C���{�^������
	 */
	protected void btnModify_Click() {
		// �C���\�`�F�b�N
		try {
			Slip slip = slipBooks.getSlip().clone();

			DEN_SYU_MST denSyuMst = getDEN_SYU_MST(slip.getSlipType());

			if (!isCheck(slip, denSyuMst, true)) {
				return;
			}

			// �`�[��ʕ\��
			SWK_HDR hdr = slip.getHeader();
			// �u�����N�������疾���I�ɃZ�b�g
			if (Util.isNullOrEmpty(hdr.getSWK_DEN_NO())) {
				hdr.setSWK_DEN_NO(field.ctrlDrillDownHeader.txSlipNo.getValue());
			}
			startSlipEntryChange(hdr, denSyuMst, true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * ���ʃ{�^������
	 */
	protected void btnCopy_Click() {
		// ���ʉ\�`�F�b�N
		try {
			Slip slip = slipBooks.getSlip().clone();

			DEN_SYU_MST denSyuMst = getDEN_SYU_MST(slip.getSlipType());

			if (!isCheck(slip, denSyuMst, false)) {
				return;
			}

			// �`�[��ʕ\��
			SWK_HDR hdr = slip.getHeader();
			// �u�����N�������疾���I�ɃZ�b�g
			if (Util.isNullOrEmpty(hdr.getSWK_DEN_NO())) {
				hdr.setSWK_DEN_NO(field.ctrlDrillDownHeader.txSlipNo.getValue());
			}
			startSlipEntryChange(hdr, denSyuMst, false);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �`�[�ҏW�`�F�b�N
	 * 
	 * @param slip Slip
	 * @param denSyuMst
	 * @param isModify
	 * @return boolean
	 */
	protected boolean isCheck(Slip slip, DEN_SYU_MST denSyuMst, boolean isModify) {

		// �Y���`�[�̓`�[��ʃ}�X�^�D�d��C���^�[�t�F�[�X�敪 = �Ώ�
		if (Util.avoidNull(denSyuMst.getTA_SYS_KBN()).equals("1")) {
			// �w��̓`�[�͏C���ł��܂���B
			String msg = "IAC006";
			if (!isModify) {
				// ���ʂ̏ꍇ�F�w��̓`�[�͕��ʂł��܂���B
				msg = "W00124";
			}
			showMessage(msg);
			return false;
		}

		if (getTargetPrgCode(denSyuMst) == null) {
			// �w��̓`�[�͏C���ł��܂���B
			String msg = "IAC006";
			if (!isModify) {
				// ���ʂ̏ꍇ�F�w��̓`�[�͕��ʂł��܂���B
				msg = "W00124";
			}
			showMessage(msg);
			return false;
		}

		if (isModify) {
			SlipState slipState = slip.getHeader().getSlipState();
			if (slipState == SlipState.APPROVE || slipState == SlipState.FIELD_APPROVE) {
				// �Y���`�[�̓`�[�̍X�V�敪�� ���ꏳ�F �܂��� �o�����F
				// �w��̓`�[�͏��F����Ă��܂��B
				showMessage("W00127");
				return false;

			} else if (slipState == SlipState.UPDATE) {
				// �Y���`�[�̓`�[�̍X�V�敪�� �X�V
				// �w��̓`�[�͏C���ł��܂���B
				showMessage("W00126");
				return false;

			}

		}
		SWK_HDR hdr = slip.getHeader();
		// �t�֓`�[��NG
		String cmpCode = null;
		for (SlipBooks slipBook : slipBooksList) {
			int tuke = slipBook.getSlip().getHeader().getSWK_TUKE_KBN();
			if (tuke == 0) {
				cmpCode = slipBook.getSlip().getCompanyCode();
				break;
			}
		}
		// if (hdr.getSWK_TUKE_KBN() != 0 && !Util.equals(cmpCode, hdr.getKAI_CODE())
		// && !Util.equals(getCompanyCode(), hdr.getKAI_CODE())) {
		if (!(hdr.getSWK_TUKE_KBN() == 0 && Util.equals(getCompanyCode(), hdr.getKAI_CODE()))) {
			// �w��̓`�[�͍쐬���ꂽ���O�C����Ђ��قȂ�ׁA�C���ł��܂���B �`�[�쐬��ЁF{0}
			String msg = "I01073";
			if (!isModify) {
				// �w��̓`�[�͍쐬���ꂽ���O�C����Ђ��قȂ�ׁA���ʂł��܂���B �`�[�쐬��ЁF{0}
				msg = "I01074";
			}
			showMessage(msg, cmpCode);
			return false;
		}

		// �m�F�̈ב��݃`�F�b�N����
		try {
			request(SlipManager.class, "checkSlipInfo", hdr.getKAI_CODE(), hdr.getSWK_DEN_NO(), hdr.getSWK_UPD_CNT());
		} catch (TException e) {
			errorHandler(e);
		}

		return true;
	}

	/**
	 * �`�[��ʃ}�X�^�擾
	 * 
	 * @param denSyuCode
	 * @return DEN_SYU_MST
	 * @throws TException
	 */
	protected DEN_SYU_MST getDEN_SYU_MST(String denSyuCode) throws TException {

		return (DEN_SYU_MST) request(DEN_SYU_MSTDao.class, "getDEN_SYU_MSTByKaicodeDensyucode", getCompanyCode(),
			denSyuCode);

	}

	/**
	 * �`�[��ʕ\��
	 * 
	 * @param hdr
	 * @param denSyuMst
	 * @param isModify
	 * @throws Exception
	 */
	protected void startSlipEntryChange(SWK_HDR hdr, DEN_SYU_MST denSyuMst, boolean isModify) throws Exception {
		TMainCtrl ins = TMainCtrl.getInstance();

		Program mgrProgram = null;

		if (prgGroupList == null) {
			if (ins.isUseMenuMaster) {
				prgGroupList = ins.getMenuPrograms(TLoginInfo.getCompany(), TLoginInfo.getUser());
			} else {
				prgGroupList = ins.getMenuProgramsOld(TLoginInfo.getCompany(), TLoginInfo.getUser());
			}
		}

		String prgCode = getTargetPrgCode(denSyuMst);
		Class pnlCtrl = (Class) request(SlipManager.class, "getSlipPanel", prgCode, denSyuMst.getDEN_SYU_CODE());
		if (pnlCtrl == null) {
			// �w��̓`�[�͏C���ł��܂���B
			String msg = "IAC006";
			if (!isModify) {
				// ���ʂ̏ꍇ�F�w��̓`�[�͕��ʂł��܂���B
				msg = "W00124";
			}
			showMessage(msg);
			return;
		}

		if (prgGroupList != null) {
			for (SystemizedProgram prg : prgGroupList) {
				if (prg.getMenuDisp() != null) {
					for (MenuDisp menu : prg.getMenuDisp()) {
						mgrProgram = getManagerProgram(menu.getProgram(), pnlCtrl);
						if (mgrProgram != null) {
							break;
						}
					}
				} else if (prg.getPrograms() != null) {
					for (Program program : prg.getPrograms()) {
						mgrProgram = getManagerProgram(program, pnlCtrl);
						if (mgrProgram != null) {
							break;
						}
					}
				}

				if (mgrProgram != null) {
					break;
				}
			}
		}

		if (mgrProgram != null) {
			// ���j���[�N��
			ins.startProgram(mgrProgram.getCode(), mgrProgram.getName(), mgrProgram.getLoadClassName(), true);
		}

		TSlipPanelCtrl denCtrl = getManagerCtrl(ins, pnlCtrl);

		if (denCtrl == null) {
			denCtrl = getManagerCtrl(ins, pnlCtrl);
		}
		if (denCtrl != null) {
			TSlipPanel panel = (TSlipPanel) denCtrl.getPanel();

			// ��������
			denCtrl.searchSlipAddResultNoneDialog(isModify, hdr, 0);

			panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); // �J�[�\���߂�
			panel.requestFocus(); // TODO

		}
	}

	/**
	 * �v���O�����R�[�h����
	 * 
	 * @param denSyuMst
	 * @return prgCode
	 */
	protected String getTargetPrgCode(DEN_SYU_MST denSyuMst) {
		String dataKbn = denSyuMst.getDATA_KBN();
		String prgCode = null;
		if (dataKbn.equals("11")) {
			prgCode = "GL0010";
		} else if (dataKbn.equals("12")) {
			prgCode = "GL0020";
		} else if (dataKbn.equals("13")) {
			prgCode = "GL0030";
		} else if (dataKbn.equals("14")) {
			prgCode = "GL6010";
		} else if (dataKbn.equals("23")) {
			prgCode = "AP0010";
		} else if (dataKbn.equals("31")) {
			prgCode = "AR0010";
		}
		return prgCode;

	}

	/**
	 * @param prg
	 * @param pnlCtrl
	 * @return �v���O����
	 */
	protected static Program getManagerProgram(Program prg, Class pnlCtrl) {

		if (prg == null) {
			return null;
		}
		String ldName = Util.avoidNull(prg.getLoadClassName());
		if (equals(ldName, pnlCtrl.getName())) {
			return prg;
		}

		if (!Util.isNullOrEmpty(ldName)) {
			try {
				Class cls = Class.forName(ldName);
				if (equals(cls.getSuperclass().getName(), pnlCtrl.getName())) {
					// �J�X�^�}�C�Y�`�F�b�N
					return prg;
				}
			} catch (ClassNotFoundException e) {
				// �G���[�Ȃ�
			}
		}

		return null;
	}

	/**
	 * @param ins
	 * @param pnlCtrl
	 * @return MANAGER CTRL
	 */
	protected TSlipPanelCtrl getManagerCtrl(TMainCtrl ins, Class pnlCtrl) {

		if (ins.selectedProgram != null) {
			for (TAppletClientBase denCtrl : ins.selectedProgram.values()) {
				if (denCtrl instanceof TSlipPanelCtrl) {
					String ldName = Util.avoidNull(denCtrl.getClass().getName());
					if (equals(ldName, pnlCtrl.getName())) {
						return (TSlipPanelCtrl) denCtrl;
					}
				}
			}
		}

		if (ins.frameProgram != null) {
			for (TPanelBusiness biz : ins.frameProgram.values()) {
				if (biz instanceof TSlipPanel) {
					String ldName = Util.avoidNull(biz.getClass().getName());
					if (equals(ldName, pnlCtrl.getName())) {
						return ((TSlipPanel) biz).getController();
					}
				}
			}
		}

		return null;
	}

	/**
	 * �����񓯈��r
	 * 
	 * @param a
	 * @param b
	 * @return true:����
	 */
	public static boolean equals(String a, String b) {
		return Util.avoidNullNT(a).equals(Util.avoidNullNT(b));
	}

	/**
	 * �d��w�b�_�[�Ɋ�Â��āASlipDen���쐬
	 * 
	 * @param hdr
	 * @return SlipDen
	 */
	protected SlipDen getDen(SWK_HDR hdr) {
		SlipDen bean = new SlipDen();
		bean.setKAI_CODE(hdr.getKAI_CODE());
		bean.setSWK_DEN_DATE(hdr.getSWK_DEN_DATE());
		bean.setSWK_DEN_NO(hdr.getSWK_DEN_NO());
		bean.setSWK_BEFORE_DEN_NO(hdr.getSWK_BEFORE_DEN_NO());
		bean.setSWK_BEFORE_UPD_KBN(hdr.getSWK_BEFORE_UPD_KBN());
		bean.setSWK_DATA_KBN(hdr.getSWK_DATA_KBN());
		bean.setSWK_UPD_KBN(hdr.getSWK_UPD_KBN());
		bean.setSWK_DEN_DATE(hdr.getSWK_DEN_DATE());

		bean.setSWK_IRAI_EMP_CODE(hdr.getSWK_IRAI_EMP_CODE());
		bean.setSWK_TEK(hdr.getSWK_TEK());
		bean.setSWK_DEN_SYU(hdr.getSWK_DEN_SYU());
		bean.setSWK_CUR_CODE(hdr.getSWK_CUR_CODE());
		bean.setSWK_UPD_CNT(hdr.getSWK_UPD_CNT());
		bean.setSWK_KSN_KBN(hdr.getSWK_KSN_KBN());

		return bean;

	}

	/**
	 * ���F�N���X��Ԃ�
	 * 
	 * @return �N���X
	 */
	protected Class getSlipManagerClass() {
		return SlipManager.class;
	}

	/**
	 * ���F�֌W�{�^�������ۂ�ؑ�
	 * 
	 * @param updKbn �`�[�X�V�敪
	 */
	protected void switchApprovalBtnsEnabled(int updKbn) {
		SlipState state = SlipState.getSlipState(updKbn);
		// ���F : �X�V/�o�����F�ȊO�͉����\
		boolean isApprovable = state != SlipState.UPDATE && state != SlipState.APPROVE;
		// �۔F : �X�V�ȊO�͉����\
		boolean isDeniable = state != SlipState.UPDATE;
		// ���F��� : �X�V/����ȊO�͉����\
		boolean isCancellable = state != SlipState.UPDATE && state != SlipState.ENTRY;
		if (state == null) {
			// �X�V�敪������Ɏ擾�ł��Ă��Ȃ��ꍇ���ׂĉ����s��
			isApprovable = false;
			isDeniable = false;
			isCancellable = false;
		}
		field.btnApprove.setEnabled(isApprovable);
		field.btnDeny.setEnabled(isDeniable);
		field.btnCancel.setEnabled(isCancellable);

	}

	/**
	 * ���F�֌W�̃��C���{�^���̕\����ؑ�
	 * 
	 * @param flag
	 */
	public void setAprvBtnVisible(boolean flag) {
		field.btnApprove.setVisible(false);
		field.btnDeny.setVisible(false);
		field.btnCancel.setVisible(false);
		if (getCompany() == null || getCompany().getAccountConfig() == null) {
			// ��Аݒ肪�ǂݍ��߂Ȃ��ꍇ��\��
			return;
		}
		AccountConfig ac = getCompany().getAccountConfig();
		if (ac.isUseWorkflowApprove()) {
			field.btnApprove.setVisible(flag);
			field.btnCancel.setVisible(flag);
			// ���[�N�t���[���F�̏ꍇ�۔F�͔�\��
			field.btnDeny.setVisible(false);
		} else {
			// TODO ���[�N�t���[���F�ɂ̂ݑΉ������T�[�o�[�����̂��߁A�ʏ�̏ꍇ�͔�\��
			// �����������ɏC��
			field.btnApprove.setVisible(false);
			field.btnDeny.setVisible(false);
			field.btnCancel.setVisible(false);
		}
	}

}
