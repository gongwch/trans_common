package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;
import java.math.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.model.ui.slip.TSlipDetailPanel.*;
import jp.co.ais.trans2.common.objsave.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.aprvrole.*;
import jp.co.ais.trans2.model.calc.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.item.ItemSearchCondition.*;
import jp.co.ais.trans2.model.security.*;
import jp.co.ais.trans2.model.slip.*;
import jp.co.ais.trans2.model.tag.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * �`�[���̓R���g���[��
 */
public abstract class TSlipPanelCtrl extends TController implements TExclusive {

	/** true:�`�[���ʎ��`�[���t������<Client> */
	public static boolean notReservationSlipDate = ClientConfig.isFlagOn("trans.slip.copy.notreservation.slipdate");

	/** true:�`�[���ʎ����Z�敪������<Client> */
	public static boolean notReservationCloseEntry = ClientConfig.isFlagOn("trans.slip.copy.notreservation.closeentry");

	/** true:�`�[���ʎ���������`�[���t�ɕύX<Client> */
	public static boolean isCopySlipDateToOccurDate = ClientConfig
		.isFlagOn("trans.slip.detail.copy.slipdate.to.occurdate");

	/** true:�t�@�C���Y�t�@�\�L�� */
	public static boolean isUseAttachment = ClientConfig.isFlagOn("trans.slip.use.attachment");

	/** true:�ꎞ�v���r���[�@�\�L�� */
	public static boolean isUseTempPreview = ClientConfig.isFlagOn("trans.slip.use.temp.preview");

	/** true:�����s�E�v���N���A����@�\���� */
	public static boolean isNoClearSameRemark = ClientConfig.isFlagOn("trans.slip.detail.noclear.same.remark");

	/** true:�������������N���A����@�\�L�� */
	public static boolean isClearSameOccurDate = ClientConfig.isFlagOn("trans.slip.detail.clear.same.occurdate");

	/** true:BS����͌������𗘗p��Client�� */
	public static boolean isBsTermLastDay = ClientConfig.isFlagOn("trans.slip.bs.term.lastday");

	/** true:�����o�^�@�\�L�� */
	public static boolean isUsePatternSave = ClientConfig.isFlagOn("trans.slip.use.pattern.save");

	/** true:�tⳋ@�\�L�� */
	public static boolean isUseTag = ClientConfig.isFlagOn("trans.slip.use.tag");

	/** �v�Z���W�b�N */
	protected TCalculator calculator = TCalculatorFactory.getCalculator();

	/** ��v�n�ݒ� */
	protected AccountConfig conf = getCompany().getAccountConfig();

	/** ��ʉ� */
	protected Currency keyCurrency = conf.getKeyCurrency();

	/** ��ʉݏ����_���� */
	protected int keyDigit = keyCurrency.getDecimalPoint();

	/** �`�[��� */
	protected SlipType slipType;

	/** �w����� */
	protected TSlipPanel mainView = null;

	/** �`�[�f�[�^(�ҏW��) */
	protected Slip slip;

	/** �����o�^�_�C�A���O */
	protected TSlipPatternSaveDialog patternSaveView;

	/** �w�b�_�[����̓��O�C����Ђ��Z�b�g���邩�H(���ꕔ�Ő錾) */
	protected boolean isDepSetting = ClientConfig.isFlagOn("trans.slip.department.default.setting");

	/** �C�����F�x�������Čv�Z�����Ȃ�(AP�p�ɍ��ꕔ�Ő錾) */
	protected boolean isNotChangePaymentDay = ClientConfig.isFlagOn("trans.slip.modify.not.change.paymentday");

	/** �ҏW�����ʂ�(true:�ҏW)(AP�p�ɍ��ꕔ�Ő錾) */
	protected boolean isModifyMode = false;

	/** �ۗ��`�F�b�N�{�b�N�X���\���Ƃ��邩(AP�p�ɍ��ꕔ�Ő錾) */
	protected boolean isHideSuspention = ClientConfig.isFlagOn("trans.AP0010.hide.suspension.check");

	/** �x�����̃f�t�H���g��Վ��Ƃ��邩(AP�p�ɍ��ꕔ�Ő錾) */
	protected boolean isDefaultManual = ClientConfig.isFlagOn("trans.AP0010.paymentday.default.manual");

	/** true: 2023INVOICE���x�Ή����g�p���� */
	public static boolean isInvoiceTaxProperty = ClientConfig.isFlagOn("trans.slip.use.invoice.tax");

	/** true: 2023INVOICE���x�Ή����g�p����(��Џ��܂�) */
	public boolean isInvoice = false;

	/** �����̓`�[���(IFRS) */
	protected String patternSlipType = null;

	/** �������`�F�b�N���g�p���邩 */
	protected boolean isUseHasDateChk = conf.isUseHasDateCheck();

	/** ڰăL���b�V���}�b�v */
	protected Map<String, BigDecimal> rateMapByOccurDate = new HashMap<String, BigDecimal>();

	/**
	 * �R���X�g���N�^.
	 */
	public TSlipPanelCtrl() {
		// �w����ʐ���
		mainView = createMainView();

		if (mainView.isTFormMode()) {
			// T�t�H�[�����[�h�̏ꍇ�A�`�[��ʃ^�C�v��ݒ肷��
			((TFormSlipDetailPanel) mainView.ctrlDetail).setSlipInputType(getSlipInputType());
			((TFormSlipDetailPanel) mainView.ctrlDetail).init(mainView.getTFormDetailCount());
		}
	}

	/**
	 * @see jp.co.ais.trans2.common.client.TController#start()
	 */
	@Override
	public void start() {

		try {

			// �C���{�C�X�g�p���邩�ǂ���
			if (isInvoiceTaxProperty) {
				initInvoiceFlg();
			}

			// �`�[��ޕʂ̏����ݒ�
			initSlipType();

			// �ꎞ�`�[�ۑ�/�����̏�����
			initSaveLoad();

			// �w����ʂ�����������
			initHeaderView();
			initDetailView();

			// �C�x���g�o�^
			addViewEvent();

			// �r������
			unlockAll();

			// �����t�H�[�J�X
			requestFocusFirst();

			// �w����ʕ\��
			mainView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �����t�H�[�J�X
	 */
	protected void requestFocusFirst() {
		mainView.ctrlSlipDate.requestFocus();
	}

	/**
	 * @see jp.co.ais.trans2.common.client.TController#getPanel()
	 */
	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * �w����ʂ̃t�@�N�g��
	 * 
	 * @return �p�l��
	 */
	protected abstract TSlipPanel createMainView();

	/**
	 * invoice�g�p���邩�ǂ���
	 */
	protected void initInvoiceFlg() {

		isInvoice = getCompany().isCMP_INV_CHK_FLG();
	}

	/**
	 * �`�[��ޕʂ̏����ݒ�
	 * 
	 * @throws TException
	 */
	protected void initSlipType() throws TException {
		String slipTypeNo = getSlipTypeNo();

		// �f�[�^�敪
		SlipTypeSearchCondition condition = new SlipTypeSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCode(slipTypeNo);

		List<SlipType> typeList = (List<SlipType>) request(SlipTypeManager.class, "get", condition);

		if (typeList.isEmpty()) {
			throw new TException("I00128", slipTypeNo);// �`�[���[{0}]���ݒ肳��Ă��܂���B
		}
		slipType = typeList.get(0);
	}

	/**
	 * �`�[���(�ԍ�)
	 * 
	 * @return �`�[���
	 */
	protected abstract String getSlipTypeNo();

	/**
	 * ��ʃw�b�_�̏����ݒ�
	 */
	protected void initHeaderView() {
		// �V�K���[�h
		switchNew();

		// �`�[���t�̃f�t�H���g�\��
		mainView.ctrlSlipDate.initSlipDate();

		// �L�������ݒ�
		changedSlipDate();

		// �`�[�E�v
		mainView.ctrlSlipRemark.getSearchCondition().setSlipRemark(true);
		mainView.ctrlSlipRemark.getSearchCondition().setSlipRowRemark(false);
		mainView.ctrlSlipRemark.getSearchCondition().setDataType(slipType.getDataType()); // �f�[�^�敪

		if (!isUseAttachment) {
			// �����̏ꍇ�A�Y�t�{�^����\��
			mainView.btnAttach.setVisible(false);
		}

		if (!isUseTempPreview) {
			// �����̏ꍇ�A�v���r���[�{�^����\��
			mainView.btnPreview.setVisible(false);
		}

		if (!isUsePatternSave) {
			// �����̏ꍇ�A�����o�^�{�^����\��
			mainView.btnPatternSave.setVisible(false);

			if (isUseTempPreview) {
				// �L���̏ꍇ�A�v���r���[�{�^�����W�C��
				mainView.btnPreview.setLocation(mainView.btnPatternSave.getLocation());
			}
		}

		if (!isUseTag) {
			// �����̏ꍇ�A�tⳋ@�\ ��\��
			mainView.ctrlTag1.setVisible(false);
			mainView.ctrlTag2.setVisible(false);
		}
		// ���[�N�t���[���F�@�\������
		initWorkFlowApprove();

	}

	/**
	 * ���F�O���[�v�̕\���ݒ�
	 */
	protected void initWorkFlowApprove() {
		boolean isUseWorkFlowApprove = isUseWorkFlowApprove();
		mainView.ctrlAprvGroup.setVisible(isUseWorkFlowApprove);
		// �l�ݒ�
		initAprvGroup();
	}

	/**
	 * ���F�O���[�v �����l�ݒ�
	 */
	protected void initAprvGroup() {
		AprvRoleGroup grp = getUser().getAprvRoleGroup();
		mainView.ctrlAprvGroup.setEntity(grp);

	}

	/**
	 * ���[�N�t���[���F�@�\�𗘗p���邩
	 * 
	 * @return true:���p����
	 */
	protected boolean isUseWorkFlowApprove() {
		return getCompany().getAccountConfig().isUseWorkflowApprove();
	}

	/**
	 * ��ʖ��ׂ̏����ݒ�
	 */
	protected void initDetailView() {

		// �v���O�������
		mainView.ctrlDetail.getController().setProgramInfo(getProgramInfo());

		// �`�[���
		mainView.ctrlDetail.getController().setSlipType(this.slipType);

		// �`�[���t
		Date slipDate = mainView.ctrlSlipDate.getValue();

		// �`�[���t������Ƃ��ăZ�b�g
		mainView.ctrlDetail.setBaseDate(slipDate);

		// �X�v���b�h��ԕۑ��L�[
		mainView.ctrlDetail.setTableKeyName(getTableKeyName());
		mainView.ctrlDetail.tbl.restoreComponent(); // ����

		// �`�[���͎��
		mainView.ctrlDetail.ctrlItem.getItemSearchCondition().setSlipInputType(getSlipInputType());
		mainView.ctrlDetail.ctrlItem.getSubItemSearchCondition().setSlipInputType(getSlipInputType());
		mainView.ctrlDetail.ctrlItem.getDetailItemSearchCondition().setSlipInputType(getSlipInputType());

		// �s�E�v�̃f�[�^�敪
		mainView.ctrlDetail.ctrlRemark.getSearchCondition().setDataType(slipType.getDataType());

		// �ŋ敪�̃f�t�H���g
		mainView.ctrlDetail.setDefaultTaxInnerDivision(slipType.isInnerConsumptionTaxCalculation());
	}

	/**
	 * �`�[���̓^�C�v
	 * 
	 * @return �`�[���̓^�C�v
	 */
	protected abstract SlipInputType getSlipInputType();

	/**
	 * �X�v���b�h�e�[�u����ԕۑ��L�[
	 * 
	 * @return �X�v���b�h�e�[�u����ԕۑ��L�[
	 */
	protected String getTableKeyName() {
		return getProgramCode() + "_" + getUserCode();
	}

	/**
	 * �w����ʂ̃C�x���g��`�B
	 */
	protected void addViewEvent() {

		// �V�K�{�^������
		mainView.btnNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				doNew();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// �C���{�^������
		mainView.btnModify.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doModify();
			}
		});

		// ���ʃ{�^������
		mainView.btnCopy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doCopy();
			}
		});

		// �폜�{�^������
		mainView.btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				doDelete();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// �m��{�^������
		mainView.btnEntry.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				doEntry();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// �d�󎫏��{�^������
		mainView.btnPattern.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doPattern();
			}
		});

		// �����o�^�{�^������
		mainView.btnPatternSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				doPatternSave();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// �v���r���[�{�^������
		mainView.btnPreview.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				doPreview();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// �`�[���t
		mainView.ctrlSlipDate.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {

				if (!mainView.ctrlSlipDate.isValueChanged2()) {
					return true;
				}

				changedSlipDate();
				return true;
			}
		});

		// ���Z�i�K
		mainView.ctrlCloseEntry.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				changedClosingEntry();
			}
		});
	}

	/**
	 * Manager���w�肷��.
	 * 
	 * @return Manager�N���X��`
	 */
	protected abstract Class getManagerClass();

	/**
	 * �V�K���[�h�ɃX�C�b�`
	 */
	protected void switchNew() {
		mainView.switchNew();
		mainView.btnDelete.setEnabled(false);
	}

	/**
	 * �ҏW���[�h�ɃX�C�b�`
	 */
	protected void switchModify() {
		mainView.switchModify();
		mainView.btnDelete.setEnabled(true);
	}

	/**
	 * �w����ʂ�����������
	 */
	protected void clearView() {
		// �r������
		unlock();

		slip = null;

		// �V�K���[�h
		switchNew();

		// �e�R���|�[�l���g������������
		clearComponents();

		// �����t�H�[�J�X
		requestFocusFirst();
	}

	/**
	 * �r������(�S��)
	 */
	protected void unlockAll() {
		try {
			// �r������
			request(getManagerClass(), "unlockAll", slipType.getCode());

		} catch (TException ex) {
			errorHandler(ex);
		}
	}

	/**
	 * �r������(��)
	 */
	protected void unlock() {
		try {
			if (slip != null && !Util.isNullOrEmpty(slip.getSlipNo())) {

				// �Y�t�t�@�C����ޔ�
				List<SWK_ATTACH> attachments = null;

				try {
					if (slip.getHeader() != null) {
						attachments = slip.getHeader().getAttachments();
						slip.getHeader().setAttachments(null);
					}

					// �`�[�w��̔r������
					request(getManagerClass(), "unlock", slip);

				} finally {
					if (attachments != null) {
						slip.getHeader().setAttachments(attachments);
					}
				}

			} else {
				unlockAll();
			}

		} catch (TException ex) {
			errorHandler(ex);
		}
	}

	/**
	 * �l�N���A
	 */
	protected void clearComponents() {
		// �w�b�_
		mainView.ctrlSlipNo.clear(); // �`�[�ԍ�
		mainView.ctrlEvidenceNo.clear(); // �؜ߔԍ�
		mainView.ctrlSlipRemark.clear(); // �`�[�E�v
		mainView.ctrlPrintStop.clear(); // �`�[�����~
		mainView.ctrlCloseEntry.clear(); // ���Z�d��
		mainView.btnAttach.clear(); // �Y�t

		mainView.ctrlTag1.clear(); // �tⳋ@�\
		mainView.ctrlTag2.clear(); // �tⳋ@�\

		// �`�[���t�̃f�t�H���g�\��
		mainView.ctrlSlipDate.initSlipDate();

		// ���F�O���[�v
		initAprvGroup();

		// ����
		mainView.ctrlDetail.init();

		// �`�[���t���f
		changedSlipDate();
	}

	/**
	 * �`�[���t����ʃZ�b�g
	 * 
	 * @param slipDate �`�[���t
	 */
	protected void setSlipDate(Date slipDate) {
		mainView.ctrlSlipDate.setValue(slipDate);
		changedSlipDate();
	}

	/**
	 * �V�K
	 */
	public void doNew() {
		try {
			if (!showConfirmMessage("Q00018")) {// �ҏW���̃f�[�^�̓N���A����܂����A��낵���ł����H
				return;
			}

			// �N���A
			clearView();

		} catch (Exception ex) {
			errorHandler(ex);
		}
	}

	/**
	 * �`�[����
	 */
	public void doModify() {
		searchSlip(true);
	}

	/**
	 * �`�[����
	 */
	public void doCopy() {
		searchSlip(false);
	}

	/**
	 * �`�[�����Ɣ��f
	 * 
	 * @param isModify true:�C�� false:����
	 */
	protected void searchSlip(boolean isModify) {
		searchSlipAddResult(isModify);
	}

	/**
	 * �`�[�����Ɣ��f
	 * 
	 * @param isModify true:�C�� false:����
	 * @return true:����
	 */
	protected boolean searchSlipAddResult(boolean isModify) {

		try {
			// ����
			TSlipSearchCtrl ctrl = createSlipSearchCtrl();

			if (!isModify) {
				ctrl.switchCopyMode(); // ���ʃ��[�h
			}

			if (ctrl.show() != TSlipSearchCtrl.OK_OPTION) {
				if (isModify) {
					mainView.btnModify.requestFocus();
				} else {
					mainView.btnCopy.requestFocus();
				}
				return false;
			}

			// ���݂̓`�[���t������Ă���
			Date slipDate = mainView.ctrlSlipDate.getValue();

			// ��U�N���A
			clearView();

			// ���f
			SWK_HDR hdr = ctrl.getSelectedRow();

			// �`�[�\�z
			slip = (Slip) request(getManagerClass(), "getSlip", hdr, isModify);

			// ���ʂ̏ꍇ
			if (!isModify) {

				if (isInvoice && slip.getDetails() != null && !slip.getDetails().isEmpty()) {
					// ���ʂ̂ݔ��f

					List<SWK_DTL> list = slip.getDetails();
					if (TSlipDetailPanelCtrl.isGroupAccounting() && !(this instanceof TSlipPatternPanelCtrl)) {

						// ���ʓ`�[���͂̏ꍇ�A��Њԕt�֑Ή����s���G�p�^�[���̏ꍇ�A�����s�v
						list = setupCompanyTranfer(list);

					}
					patternSlipType = slip.getHeader().getSWK_DEN_SYU();

					if (checkInvoiceItemTaxCode(list)) {
						// �C���{�C�X�p:�����Ə���ł��K�i�A�K�i���`�F�b�N
						patternSlipType = null;
						return false;
					}
					patternSlipType = null;
				}

				// �`�[�ԍ��N���A
				slip.setSlipNo(null);

				// ���ʎ��A�Y�t�t�@�C�����N���A
				slip.getHeader().setAttachments(null);
				// ���ʎ��A�tⳋ@�\���N���A
				slip.getHeader().setSwkTags(null);

				Map<String, BigDecimal> rateMap = new HashMap<String, BigDecimal>();

				// ���ׂ̕R�t��������
				for (SWK_DTL dtl : slip.getDetails()) {
					dtl.setSWK_DEN_NO(null);
					dtl.setSWK_KESI_KBN(SWK_DTL.KESI_KBN.NOMAL);
					dtl.setSWK_KESI_DATE(null); // �����`�[���t
					dtl.setSWK_KESI_DEN_NO(null); // �����`�[�ԍ�
					dtl.setSWK_SOUSAI_DATE(null); // ���E�`�[���t
					dtl.setSWK_SOUSAI_DEN_NO(null); // ���E�`�[�ԍ�
					dtl.setSWK_SOUSAI_GYO_NO(null); // ���E�s�ԍ�

					dtl.setAP_ZAN(null);
					dtl.setAR_ZAN(null);
					dtl.setBsDetail(null);

					if (isCopySlipDateToOccurDate) {
						// ���ʎ��ɔ�������`�[���t�ɕύX����
						dtl.setHAS_DATE(slipDate);

						Company kcompany = dtl.getAppropriateCompany();
						Currency currency = dtl.getCurrency();
						ConsumptionTax tax = dtl.getTax();

						BigDecimal amount = dtl.getSWK_IN_KIN();
						BigDecimal taxAmount = dtl.getSWK_IN_ZEI_KIN();

						BigDecimal rate = null;
						String key = currency != null ? currency.getCode() : keyCurrency.getCode();
						if (rateMap.containsKey(key)) {
							rate = rateMap.get(key);
						} else {
							rate = getCurrencyRate(currency, slip.isClosingSlip(), slipDate);
							rateMap.put(key, rate);
						}

						// �������`�F�b�N���g�p����ꍇ
						if (isUseHasDateChk) {
							rate = getCurrencyRateByOccurDate(slipDate, currency.getCode(), slip.isClosingSlip());
						}

						BigDecimal keyAmount = convertKeyAmount(amount, rate, kcompany, currency);
						BigDecimal keyTaxAmount = convertKeyTaxAmount(taxAmount, rate, kcompany, currency, tax);

						dtl.setSWK_CUR_RATE(rate);
						dtl.setSWK_KIN(keyAmount);
						dtl.setSWK_ZEI_KIN(keyTaxAmount);
					}
				}

				if (ctrl.isSelectedCancel()) {
					// ��
					slip = slip.toCancelSlip();

				} else if (ctrl.isSelectedReverse()) {
					// �t
					slip = slip.toReverseSlip();
				}

			}

			// ��ʔ��f
			dispatch();

			if (isModify) {
				// �C��
				switchModify();

			} else {
				// ����(�V�K����)
				slip = null;
				if (!notReservationSlipDate) {
					setSlipDate(slipDate); // ���t�͌��̂܂�
				}
				mainView.ctrlSlipNo.clear();

				if (!notReservationCloseEntry) {
					mainView.ctrlCloseEntry.setSelected(false); // ���Z�d��
				}

				switchNew();
			}

			return true;

		} catch (Exception ex) {
			errorHandler(ex);
			return false;
		}
	}

	/**
	 * ��ʓ��͏������Ɋ���z�Ɋ��Z
	 * 
	 * @param amount ���͋��z
	 * @param rate
	 * @param kcompany
	 * @param currency
	 * @return ��ʉ݋��z
	 */
	protected BigDecimal convertKeyAmount(BigDecimal amount, BigDecimal rate, Company kcompany, Currency currency) {

		if (DecimalUtil.isNullOrZero(amount)) {
			return BigDecimal.ZERO;
		}

		if (kcompany == null || currency == null) {
			return BigDecimal.ZERO;
		}

		// ���Z
		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(kcompany.getAccountConfig().getExchangeFraction());
		param.setConvertType(conf.getConvertType());
		param.setDigit(keyCurrency.getDecimalPoint());
		param.setForeignAmount(amount);
		param.setRate(rate);
		param.setRatePow(currency.getRatePow());

		return calculator.exchangeKeyAmount(param);
	}

	/**
	 * ��ʓ��͏������Ɋ����Ŋz�Ɋ��Z
	 * 
	 * @param taxAmount ���͏���Ŋz
	 * @param rate
	 * @param kcompany
	 * @param currency
	 * @param tax
	 * @return ��ʉݏ���Ŋz
	 */
	protected BigDecimal convertKeyTaxAmount(BigDecimal taxAmount, BigDecimal rate, Company kcompany, Currency currency,
		ConsumptionTax tax) {

		if (DecimalUtil.isNullOrZero(taxAmount)) {
			return BigDecimal.ZERO;
		}

		if (kcompany == null || currency == null || tax == null) {
			return BigDecimal.ZERO;
		}

		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(kcompany.getAccountConfig().getExchangeFraction());
		param.setConvertType(conf.getConvertType());
		param.setDigit(keyCurrency.getDecimalPoint());
		param.setForeignAmount(taxAmount);
		param.setRate(rate);
		param.setRatePow(currency.getRatePow());

		return calculator.exchangeKeyAmount(param);
	}

	/**
	 * �ʉ݃��[�g�擾
	 * 
	 * @param currency
	 * @param isClosing
	 * @param occurDate
	 * @return ���[�g
	 */
	protected BigDecimal getCurrencyRate(Currency currency, boolean isClosing, Date occurDate) {
		try {

			if (currency == null) {
				return null;
			}

			if (currency.getCode().equals(keyCurrency.getCode())) {
				return BigDecimal.ONE;
			}

			if (occurDate == null) {
				return null;
			}

			return (BigDecimal) request(RateManager.class, isClosing ? "getSettlementRate" : "getRate", currency,
				occurDate);

		} catch (TException ex) {
			errorHandler(ex);
			return null;
		}
	}

	/**
	 * �`�[�����R���g���[������
	 * 
	 * @return �`�[�����R���g���[��
	 */
	protected TSlipSearchCtrl createSlipSearchCtrl() {
		TSlipSearchCtrl ctrl = new TSlipSearchCtrl(mainView);
		ctrl.setSlipType(slipType.getCode()); // �`�[���
		ctrl.setDataKind(slipType.getDataType()); // �f�[�^�敪
		ctrl.setIncludeOtherSystem(true); // ���V�XOK

		return ctrl;
	}

	/**
	 * ��ʔ��f
	 */
	protected void dispatch() {

		if (TSlipDetailPanelCtrl.isGroupAccounting() && !(this instanceof TSlipPatternPanelCtrl)) {

			// ���ʓ`�[���͂̏ꍇ�A��Њԕt�֑Ή����s���G�p�^�[���̏ꍇ�A�����s�v
			setupCompanyTranfer();

		}

		// �w�b�_
		SWK_HDR hdr = slip.getHeader();

		setSlipDate(hdr.getSWK_DEN_DATE()); // �`�[���t
		mainView.ctrlSlipNo.setValue(hdr.getSWK_DEN_NO()); // �`�[�ԍ�
		mainView.ctrlSlipNo.setUpdateCount(hdr.getSWK_UPD_CNT()); // �C����
		mainView.ctrlEvidenceNo.setValue(hdr.getSWK_SEI_NO()); // �؜ߔԍ�
		mainView.ctrlSlipRemark.setCode(hdr.getSWK_TEK_CODE()); // �`�[�E�v
		mainView.ctrlSlipRemark.setNames(hdr.getSWK_TEK()); // �`�[�E�v

		mainView.btnAttach.setAttachments(hdr.getAttachments());

		if (hdr.getSwkTags() != null) {
			// �tⳋ@�\
			for (SWK_TAG tag : hdr.getSwkTags()) {
				Tag bean = new Tag();
				bean.setCompanyCode(tag.getKAI_CODE());
				bean.setCode(tag.getTAG_CODE());
				bean.setColor(tag.getTAG_COLOR());
				bean.setTitle(tag.getTAG_TITLE());

				if (tag.getSEQ() == 1) {
					mainView.ctrlTag1.setEntity(bean);
				} else if (tag.getSEQ() == 2) {
					mainView.ctrlTag2.setEntity(bean);
				}
			}
		}
		mainView.ctrlAprvGroup.setEntity(hdr.getAprRoleGroup());
		if (slip.isClosingSlip()) {
			mainView.ctrlCloseEntry.setSelected(true); // ���Z�d��
		}

		// ����
		List<SWK_DTL> dtlList = new ArrayList<SWK_DTL>(slip.getDetails().size());
		for (SWK_DTL dtl : slip.getDetails()) {
			SWK_DTL cdtl = dtl.clone();

			// ���ł̏ꍇ�A���z�̕ҏW
			if (dtl.isTaxInclusive()) {
				cdtl.setSWK_IN_KIN(cdtl.getSWK_IN_KIN().add(cdtl.getSWK_IN_ZEI_KIN()));
				cdtl.setSWK_KIN(cdtl.getSWK_KIN().add(cdtl.getSWK_ZEI_KIN()));
			}

			dtlList.add(cdtl);

			// ��ʔ��f�O�̖��א���
			adjustDetails(hdr, cdtl);
		}

		mainView.ctrlDetail.setSlipNo(slip.getSlipNo());
		mainView.ctrlDetail.setEntityList(dtlList);

	}

	/**
	 * invoice�p �ȖڃR�[�h������ŉȖڂ��m�F�A��K�i���K�i��
	 * 
	 * @param list
	 * @return ���s���邩�ǂ��� true:���s���Ȃ�
	 */
	protected boolean checkInvoiceItemTaxCode(List<SWK_DTL> list) {

		if (chkSlipTypeInvoice()) {
			return false;
		}
		List<Message> msgList = new ArrayList<Message>();
		int row = 0;
		int rowNo = 1;

		for (SWK_DTL dtl : list) {
			row++;
			if (Util.isNullOrEmpty(dtl.getSWK_KMK_CODE())) {
				continue;
			}

			String kmk = Util.avoidNull(dtl.getSWK_KMK_CODE());
			String hkm = Util.avoidNull(dtl.getSWK_HKM_CODE());
			String ukm = Util.avoidNull(dtl.getSWK_UKM_CODE());

			if (TLoginInfo.isTaxAutoItem(kmk, hkm, ukm)) {
				Message msg = new Message();
				msg.setMessage(getMessage("I01119", row));
				msg.setSubMessageID(Integer.toString(rowNo));
				msgList.add(msg);
				rowNo++;
			}

		}

		msgList = checkCustomerTaxInvReg(list, msgList, rowNo); // ��K�i���K�i��

		if (msgList.size() != 0) {
			if (ConfermMessageListDialog.OK_OPTION != showConfermMessageList(mainView, getMessage("I01111"), msgList)) {
				// ���b�Z�[�W�\�� ���s���܂����H
				return true;
			}
		}

		return false;

	}

	/**
	 * invoice�p upload�����f�[�^�̎����Ə���ł��K�i�A�K�i���`�F�b�N
	 * 
	 * @param list
	 * @param msgList ���b�Z�[�Wlist
	 * @param rowNo ���Ԕԍ�
	 * @return ���b�Z�[�Wlist
	 */
	protected List<Message> checkCustomerTaxInvReg(List<SWK_DTL> list, List<Message> msgList, int rowNo) {
		int row = 0;

		for (SWK_DTL dtl : list) {
			row++;
			Message msg = new Message();

			if (Util.isNullOrEmpty(dtl.getSWK_TRI_CODE()) || Util.isNullOrEmpty(dtl.getSWK_ZEI_CODE())) {
				continue;
			}
			// �����A����ł̃G���e�B�e�B�擾
			Customer cus = dtl.getCustomer();
			if (cus == null) {
				cus = setCustomerEntity(dtl.getSWK_K_KAI_CODE(), dtl.getSWK_TRI_CODE());
			}
			ConsumptionTax tax = dtl.getTax();
			if (cus == null || tax == null) {
				continue;
			}

			if (cus.isNO_INV_REG_FLG()) {
				if (!tax.isNO_INV_REG_FLG()) {
					msg.setMessage(getMessage("I01112", row, "C12197", tax.getCode() + ":" + tax.getNames()));
					msg.setSubMessageID(Integer.toString(rowNo));
					msgList.add(msg);
					rowNo++;
					// x�s�� ��K�i���������s���Ǝ҂ɑ΂��āy{�����}�z���ݒ肳��Ă��܂��B
				}
			} else {
				if (tax.isNO_INV_REG_FLG()) {
					msg.setMessage(getMessage("I01112", row, "C12196", tax.getCode() + ":" + tax.getNames()));
					msg.setSubMessageID(Integer.toString(rowNo));
					msgList.add(msg);
					rowNo++;
					// x�s�� �K�i���������s���Ǝ҂ɑ΂��āy{�����}�z���ݒ肳��Ă��܂��B
				}
			}
		}
		return msgList;

	}

	/**
	 * ��ʔ��f�O�̖��א���
	 * 
	 * @param hdr
	 * @param dtl
	 */
	protected void adjustDetails(SWK_HDR hdr, SWK_DTL dtl) {

		if (!isNoClearSameRemark) {
			// �s�E�v�̐���
			if (Util.isNullOrEmpty(dtl.getSWK_GYO_TEK_CODE())
				&& Util.avoidNull(hdr.getSWK_TEK()).equals(dtl.getSWK_GYO_TEK())) {
				// ���׍s�E�v�R�[�h�Ȃ��A���s�E�v���`�[�E�v�̏ꍇ�A���׍s�E�v���N���A����
				dtl.setSWK_GYO_TEK(null);
			}
		}

		if (isClearSameOccurDate) {
			// �������̐���(��ʉ݂̏ꍇ�̂�)
			if (keyCurrency.getCode().equals(dtl.getSWK_CUR_CODE()) && dtl.getHAS_DATE() != null
				&& hdr.getSWK_DEN_DATE() != null && dtl.getHAS_DATE().compareTo(hdr.getSWK_DEN_DATE()) == 0) {
				// ���׍s���������`�[���t�̏ꍇ�A���ה��������N���A����
				dtl.setHAS_DATE(null);
			}
		}
	}

	/**
	 * ��Њԕt�֎d��ݒ�(�C���{�C�X���[�j���O���b�Z�[�W�p)
	 * 
	 * @param list
	 * @return �d�󖾍�
	 */
	protected List<SWK_DTL> setupCompanyTranfer(List<SWK_DTL> list) {

		// �����}�b�v�쐬
		Map<String, SWK_DTL> details = new TreeMap<String, SWK_DTL>();
		// �e��Ђ̊�ʉ�
		String keyCur = conf.getKeyCurrency().getCode();

		for (SWK_DTL dtl : list) {

			// �ʉݏ��̉�ЃR�[�h�����O�C����ЃR�[�h�ɕύX
			Currency currency = dtl.getCurrency();
			if (currency != null) {
				currency.setCompanyCode(getCompanyCode());
			}

			// �e��Ўd��擾���邽�߁A�}�b�v�Ɉꎞ�ۑ�
			String key = dtl.getSWK_DEN_NO() + "<>" + dtl.getSWK_GYO_NO();
			details.put(key, dtl);
		}

		// �����}�b�v�Ǝd��W���[�i���ɂ���Ē����������s��
		for (SWK_DTL dtl : list) {

			// �v���Ђ̓��O�C����ЂƈقȂ�i�t�֎d��j
			if (!dtl.getKAI_CODE().equals(getCompanyCode()) && !dtl.isAutoDetail()) {

				String key = dtl.getSWK_DEN_NO() + "<>" + (dtl.getSWK_GYO_NO() - 1);
				SWK_DTL baseDtl = details.get(key);

				if (baseDtl != null) {

					// �M�݊z�A����Ŋz�ȊO�A���͋��z�A����ł͂��ׂĎq��Ђ̎d�󖾍ׂ��\������B

					// ����Ŋz
					BigDecimal taxAmount = null;

					if (Util.equals(keyCur, dtl.getSWK_CUR_CODE())
						&& !DecimalUtil.isNullOrZero(dtl.getSWK_IN_ZEI_KIN())) {
						// ����ʉ� = �e��Ђ̊�ʉ݂��ꏏ�̏ꍇ�͓��͏���Ŋz�����̂܂܃Z�b�g����
						taxAmount = dtl.getSWK_IN_ZEI_KIN();
					} else if (DecimalUtil.isNullOrZero(dtl.getSWK_IN_ZEI_KIN())) {
						// �q��Ђ̓��͏���Ŋz���O�̏ꍇ�A����Ŋz�͂O�Ƃ���
						taxAmount = BigDecimal.ZERO;
					} else {
						taxAmount = getKeyTaxAmount(dtl, baseDtl.getSWK_KIN());
					}

					// �ʉݏ��i�ʉ݃R�[�h�A�����_�ȉ������_�����j
					dtl.setCurrency(baseDtl.getCurrency());

					// ���[�g
					dtl.setSWK_CUR_RATE(baseDtl.getSWK_CUR_RATE());

					// �M�݊z
					dtl.setSWK_KIN(baseDtl.getSWK_KIN().subtract(taxAmount));

					// ����Ŋz
					dtl.setSWK_ZEI_KIN(taxAmount);

				}

			}
		}

		// �����d��O��
		List<SWK_DTL> newDetails = new ArrayList<SWK_DTL>();

		for (SWK_DTL dtl : list) {
			if (!dtl.isAutoDetail()) {
				newDetails.add(dtl);
			}
		}
		return newDetails;
	}

	/**
	 * ��Њԕt�֎d��ݒ�
	 */
	protected void setupCompanyTranfer() {

		// �����}�b�v�쐬
		Map<String, SWK_DTL> details = new TreeMap<String, SWK_DTL>();
		// �e��Ђ̊�ʉ�
		String keyCur = conf.getKeyCurrency().getCode();

		for (SWK_DTL dtl : slip.getDetails()) {

			// �ʉݏ��̉�ЃR�[�h�����O�C����ЃR�[�h�ɕύX
			Currency currency = dtl.getCurrency();
			if (currency != null) {
				currency.setCompanyCode(getCompanyCode());
			}

			// �e��Ўd��擾���邽�߁A�}�b�v�Ɉꎞ�ۑ�
			String key = dtl.getSWK_DEN_NO() + "<>" + dtl.getSWK_GYO_NO();
			details.put(key, dtl);
		}

		// �����}�b�v�Ǝd��W���[�i���ɂ���Ē����������s��
		for (SWK_DTL dtl : slip.getDetails()) {

			// �v���Ђ̓��O�C����ЂƈقȂ�i�t�֎d��j
			if (!dtl.getKAI_CODE().equals(getCompanyCode()) && !dtl.isAutoDetail()) {

				String key = dtl.getSWK_DEN_NO() + "<>" + (dtl.getSWK_GYO_NO() - 1);
				SWK_DTL baseDtl = details.get(key);

				if (baseDtl != null) {

					// �M�݊z�A����Ŋz�ȊO�A���͋��z�A����ł͂��ׂĎq��Ђ̎d�󖾍ׂ��\������B

					// ����Ŋz
					BigDecimal taxAmount = null;

					if (Util.equals(keyCur, dtl.getSWK_CUR_CODE())
						&& !DecimalUtil.isNullOrZero(dtl.getSWK_IN_ZEI_KIN())) {
						// ����ʉ� = �e��Ђ̊�ʉ݂��ꏏ�̏ꍇ�͓��͏���Ŋz�����̂܂܃Z�b�g����
						taxAmount = dtl.getSWK_IN_ZEI_KIN();
					} else if (DecimalUtil.isNullOrZero(dtl.getSWK_IN_ZEI_KIN())) {
						// �q��Ђ̓��͏���Ŋz���O�̏ꍇ�A����Ŋz�͂O�Ƃ���
						taxAmount = BigDecimal.ZERO;
					} else {
						taxAmount = getKeyTaxAmount(dtl, baseDtl.getSWK_KIN());
					}

					// �ʉݏ��i�ʉ݃R�[�h�A�����_�ȉ������_�����j
					dtl.setCurrency(baseDtl.getCurrency());

					// ���[�g
					dtl.setSWK_CUR_RATE(baseDtl.getSWK_CUR_RATE());

					// �M�݊z
					dtl.setSWK_KIN(baseDtl.getSWK_KIN().subtract(taxAmount));

					// ����Ŋz
					dtl.setSWK_ZEI_KIN(taxAmount);

				}

			}
		}

		// �����d��O��
		List<SWK_DTL> newDetails = new ArrayList<SWK_DTL>();

		for (SWK_DTL dtl : slip.getDetails()) {
			if (!dtl.isAutoDetail()) {
				newDetails.add(dtl);
			}
		}
		slip.setDetails(newDetails);
	}

	/**
	 * �d��W���[�i���������Ɋ����Ŋz�Ɋ��Z
	 * 
	 * @param dtl �d��W���[�i��
	 * @param amount �M�݋��z
	 * @return ��ʉݏ���Ŋz
	 */
	protected BigDecimal getKeyTaxAmount(SWK_DTL dtl, BigDecimal amount) {

		if (amount == null) {
			return BigDecimal.ZERO;
		}

		Company kcompany = dtl.getAppropriateCompany();

		if (kcompany == null) {
			return BigDecimal.ZERO;
		}

		AccountConfig kconf = kcompany.getAccountConfig();
		Currency currency = getCompany().getAccountConfig().getKeyCurrency();
		ConsumptionTax tax = dtl.getTax();

		if (kconf == null || currency == null || tax == null) {
			return BigDecimal.ZERO;
		}

		TTaxCalculation param = TCalculatorFactory.createTaxCalculation();
		param.setInside(true); // ����or�O��
		param.setAmount(amount); // �Ώۋ��z
		param.setTax(tax); // ����ŏ��
		param.setDigit(currency.getDecimalPoint()); // �����_����
		param.setReceivingFunction(kconf.getReceivingFunction()); // �؎�
		param.setPaymentFunction(kconf.getPaymentFunction()); // ����
		return calculator.calculateTax(param);

	}

	/**
	 * �`�[�폜
	 */
	public void doDelete() {
		try {
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜
			request(getManagerClass(), "delete", slip);

			// �N���A
			clearView();

			showMessage("I00013");// ����ɏ��������s����܂����B

		} catch (Exception ex) {
			errorHandler(ex);
		}
	}

	/**
	 * �`�[�m��
	 */
	public void doEntry() {
		try {
			// ���̓`�F�b�N
			if (!checkInput()) {
				return;
			}

			String msgID = "Q00004"; // �m�肵�܂����H

			if (mainView.btnAttach.isVisible()
				&& (mainView.btnAttach.getAttachments() == null || mainView.btnAttach.getAttachments().isEmpty())) {
				msgID = "Q00075"; // �Y�t�t�@�C��������܂��񂪁A�m�肵�܂����H
			}

			if (!showConfirmMessage(msgID)) {
				return;
			}

			mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			// �`�[�ɔ��f
			reflectSlip();

			// �L�������Ɩ��ׂ̐������`�F�b�N
			if (!checkError()) {
				return;
			}
			// �����~�`�F�b�N����Ă��Ȃ�������������
			boolean isPrint = !mainView.ctrlPrintStop.isSelected();

			// �o�^
			byte[] bytes = (byte[]) request(getManagerClass(), "entry", slip, isPrint);

			// ���(PDF�\��)
			if (isPrint) {
				TPrinter printer = new TPrinter();
				printer.preview(bytes, getPrintName() + ".pdf");
			}

			// ��ʃN���A �`�[���t�����ێ�
			Date slipDate = mainView.ctrlSlipDate.getValue();

			clearView();

			setSlipDate(slipDate);

			showMessage("I00013");// ����ɏ��������s����܂����B

		} catch (Exception ex) {
			errorHandler(ex);

		} finally {
			mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	/**
	 * �ꎞ�`�[�ۑ�/�����̏�����
	 */
	protected void initSaveLoad() {

		mainView.addSaveListener(new TObjectSaveListener<Slip>() {

			/**
			 * �v���O����ID�擾
			 * 
			 * @return �v���O����ID
			 */
			@Override
			public String getProgramCode() {
				return TSlipPanelCtrl.this.getProgramCode();
			}

			/**
			 * �ۑ��L�[�̎擾
			 * 
			 * @param obj �ΏۃI�u�W�F�N�g
			 * @return �ۑ��L�[
			 */
			@Override
			public String getKey(Slip obj) {
				return obj.getSlipNo();
			}

			/**
			 * �ۑ��I�u�W�F�N�g�̐ݒ�
			 * 
			 * @param obj �ΏۃI�u�W�F�N�g
			 */
			@Override
			public void setSaveObject(Slip obj) {
				dispatchTempSlip(obj);
			}

			/**
			 * �ۑ��I�u�W�F�N�g�̎擾
			 * 
			 * @return �I�u�W�F�N�g
			 */
			@Override
			public Slip getSaveObject() {
				Slip obj = getSaveSlip();
				obj.setSlipNo(DateUtil.toYMDHMSString(Util.getCurrentDate()) + " " + obj.getRemarks());
				return obj;
			}
		});

	}

	/**
	 * �ꎞ�ۑ��`�[���擾
	 * 
	 * @return �ꎞ�ۑ��`�[
	 */
	protected Slip getSaveSlip() {

		Slip saveSlip = slip;

		try {
			if (slip != null) {
				slip = slip.clone();
			}

			// �`�[�ɔ��f
			reflectSlip();

			Slip tempSlip = slip;

			return tempSlip;

		} catch (Exception ex) {
			errorHandler(ex);
		} finally {
			slip = saveSlip; // ����
		}
		return null;
	}

	/**
	 * �ꎞ�ۑ��p�^�[���`�[���擾
	 * 
	 * @return �ꎞ�ۑ��p�^�[���`�[
	 */
	protected Slip getSavePatternSlip() {

		Slip saveSlip = slip;

		try {
			if (slip != null) {
				slip = slip.clone();
			}

			// �p�^�[���`�[�ɔ��f
			reflectPatternSlip();

			Slip tempSlip = slip;

			return tempSlip;

		} catch (Exception ex) {
			errorHandler(ex);
		} finally {
			slip = saveSlip; // ����
		}
		return null;
	}

	/**
	 * �ꎞ�`�[��������
	 * 
	 * @param tempSlip �ꎞ�`�[
	 */
	protected void dispatchTempSlip(Slip tempSlip) {

		try {

			if (tempSlip.getDetails() == null) {
				tempSlip.setDetails(new ArrayList<SWK_DTL>());
			}

			// ��U�N���A
			clearView();

			// ���ʃ��[�h
			slip = tempSlip;

			// �`�[�ԍ��N���A
			slip.setSlipNo(null);

			// ���ׂ̕R�t��������
			for (SWK_DTL dtl : slip.getDetails()) {
				dtl.setSWK_DEN_NO(null);
				dtl.setSWK_KESI_KBN(SWK_DTL.KESI_KBN.NOMAL);
				dtl.setSWK_KESI_DATE(null); // �����`�[���t
				dtl.setSWK_KESI_DEN_NO(null); // �����`�[�ԍ�
				dtl.setSWK_SOUSAI_DATE(null); // ���E�`�[���t
				dtl.setSWK_SOUSAI_DEN_NO(null); // ���E�`�[�ԍ�

				dtl.setAP_ZAN(null);
				dtl.setAR_ZAN(null);
				dtl.setBsDetail(null);
			}

			// ��ʔ��f
			dispatch();

			// ����(�V�K����)
			slip = null;
			mainView.ctrlSlipNo.clear();

			switchNew();
		} catch (Exception ex) {
			errorHandler(ex);
		}
	}

	/**
	 * �`�[�ɔ��f
	 * 
	 * @throws TException
	 */
	protected void reflectSlip() throws TException {

		// Slip�\�z
		if (slip == null) {
			slip = new Slip();
		}

		// �w�b�_���f
		SWK_HDR hdr = slip.getHeader();
		reflectHeader(hdr);

		// ���ה��f
		reflectDetails();

		// �ב֍����v�s�ǉ�
		slip = addLossOrProfit(slip);

		// ���ׂɃw�b�_���Z�b�g
		slip.synchDetails();
	}

	/**
	 * �p�^�[���`�[�ɔ��f
	 */
	protected void reflectPatternSlip() {

		// Slip�\�z
		if (slip == null) {
			slip = new Slip();
		}

		// �w�b�_���f
		SWK_HDR hdr = slip.getHeader();
		hdr.setINP_DATE(null);
		hdr.setUPD_DATE(null);
		hdr.setSWK_INP_DATE(null);
		reflectHeader(hdr);
		hdr.setSWK_DEN_DATE(null); // �`�[���t
		hdr.setSWK_IRAI_DATE(null); // �˗���
		hdr.setSWK_DEN_NO(patternSaveView.ctrlPatternNo.getValue());
		hdr.setSWK_UKE_DEP_CODE(null);
		hdr.setSWK_UPD_CNT(0);

		if (mainView.ctrlCloseEntry.isSelected()) {
			// ���t�������̂Ō��Z�`�[�̏ꍇ�͎�����1��
			hdr.setSWK_KSN_KBN(1);
		}

		// ���ה��f
		reflectPatternDetails();

		// ���ׂɃw�b�_���Z�b�g
		slip.synchDetails();
	}

	/**
	 * �ב֍����v�s�ǉ�
	 * 
	 * @param slip_ �Ώ�
	 * @return �ב֍����v�s�ǉ���
	 * @throws TException
	 */
	protected Slip addLossOrProfit(Slip slip_) throws TException {
		Slip cslip = slip_.clone();
		cslip.getHeader().setAttachments(null); // �s�v�ȓY�t������

		// ��x�A����ŋ��z�𑫂�
		for (SWK_DTL dtl : cslip.getDetails()) {
			if (!DecimalUtil.isNullOrZero(dtl.getSWK_ZEI_KIN())) {
				dtl.setSWK_KIN(dtl.getSWK_KIN().add(dtl.getSWK_ZEI_KIN()));
			}
		}

		// �����v�s�ǉ�
		cslip = (Slip) request(getManagerClass(), "addLossOrProfit", cslip);

		// ����ŋ��z��߂�
		for (SWK_DTL dtl : cslip.getDetails()) {
			if (!DecimalUtil.isNullOrZero(dtl.getSWK_ZEI_KIN())) {
				dtl.setSWK_KIN(dtl.getSWK_KIN().subtract(dtl.getSWK_ZEI_KIN()));
			}
		}

		cslip.getHeader().setAttachments(slip_.getHeader().getAttachments()); // �Y�t�𕜋�
		return cslip;
	}

	/**
	 * �L�������Ɩ��ׂ̐������`�F�b�N
	 * 
	 * @return false:�G���[����
	 * @throws TException
	 */
	protected boolean checkError() throws TException {

		List<Message> errorList = null;

		// �Y�t�t�@�C����ޔ�
		List<SWK_ATTACH> attachments = null;

		try {
			if (slip.getHeader() != null) {
				attachments = slip.getHeader().getAttachments();
				slip.getHeader().setAttachments(null);
			}

			// �`�[���̓`�F�b�N
			errorList = (List<Message>) request(getManagerClass(), "checkError", slip);

		} finally {
			if (attachments != null) {
				slip.getHeader().setAttachments(attachments);
			}
		}

		if (!errorList.isEmpty()) {
			if (!getCompany().getAccountConfig().isSlipTermCheck()) {
				// �L�����ԃ`�F�b�N�������ꍇ�̓G���[�\�����ďI��
				showMessageList(mainView, errorList);
				return false;

			}

			for (Message error : errorList) {

				// ���ߏ��s���̏ꍇ�͍ă��O�C���𑣂�
				if (error.getErrorType() == SlipError.CLOSED) {
					showMessage("W01136");// �����[�U�ɂ�茎���������s���܂����B�ă��O�C�����s���A�ēx���͂������Ă��������B
					return false;
				}

				// 1���ł����ԊO�G���[�ȊO������Ε\�����ďI��
				if (error.getErrorType() != SlipError.TERM_OUT) {
					showMessageList(mainView, errorList);
					return false;
				}
			}

			// ���ԊO�G���[�݂̂̏ꍇ�A����҂Ɋm�F����OK�Ȃ�o�^
			if (ConfermMessageListDialog.OK_OPTION != showConfermMessageList(mainView, "Q00054", // �L�����Ԃ��߂��Ă���f�[�^���܂܂�܂��B��낵���ł����H
				errorList)) {
				return false;
			}
		}

		return true;

	}

	/**
	 * ������̖���
	 * 
	 * @return ����
	 */
	protected abstract String getPrintName();

	/**
	 * ���͒l�`�F�b�N
	 * 
	 * @return false:NG
	 * @throws TException
	 */
	protected boolean checkInput() throws TException {

		// �`�[���t�`�F�b�N
		if (!checkSlipDate()) {
			return false;
		}

		// �w�b�_�[�`�F�b�N
		if (!checkHeaderInput()) {
			return false;
		}

		// T-Form�̏ꍇ�A���׃`�F�b�N�������s��
		if (mainView.isTFormMode() && !mainView.ctrlDetail.checkInput()) {
			return false;
		}

		// ���׃`�F�b�N
		if (!checkDetailInput()) {
			return false;
		}

		// invoice�p����ł̌v�Z�x�����b�Z�[�W
		if (isInvoice && !checkInvoiceTax()) {
			return false;
		}

		return true;
	}

	/**
	 * �`�[���t�֘A�̃`�F�b�N
	 * 
	 * @return false:NG
	 * @throws TException
	 */
	protected boolean checkSlipDate() throws TException {

		// ����
		List<SWK_DTL> dtlList = mainView.ctrlDetail.getEntityList();

		// ���Z�`�[�̏ꍇ�A���Z�`�[���͉\����
		if (mainView.ctrlCloseEntry.isSelected() && !mainView.ctrlSlipDate.isSettlementDate()) {
			showMessage("I00045");// ���Z�d��͌��Z���̖����ł̂ݓ��͂ł��܂��B
			mainView.ctrlSlipDate.requestFocus();
			return false;
		}

		// ��s�`�[���t�`�F�b�N
		if (mainView.ctrlSlipDate.isPriorOver()) {
			showMessage("I00130");// �w��̓`�[���t�͐�s�`�[���t�𒴂��Ă��܂��B
			mainView.ctrlSlipDate.requestFocus();
			return false;
		}

		// ���߃`�F�b�N
		if (mainView.ctrlSlipDate.isClosed(mainView.ctrlCloseEntry.getStage())) {
			showMessage("I00131");// �w��̓`�[���t�͒��߂��Ă��܂��B
			mainView.ctrlSlipDate.requestFocus();
			return false;
		}

		if (mainView.ctrlCloseEntry.isSelected()
			&& mainView.ctrlSlipDate.isClosed(mainView.ctrlCloseEntry.getStage())) {
			showMessage("I00132");// �����������s��ꂽ�ׁA���Z�i�K�ɕύX������܂��B
			mainView.ctrlCloseEntry.resetStage();
			mainView.ctrlCloseEntry.chk.requestFocus();
			return false;
		}

		// ���Z�d��`�F�b�N
		if (mainView.ctrlCloseEntry.num.isEditable()) {

			// ���Z�i�K�̓��̓`�F�b�N
			if (mainView.ctrlCloseEntry.num.isEmpty()) {
				// ���Z�i�K����͂��Ă�������
				showMessage("I00037", "C00718");
				mainView.ctrlCloseEntry.num.requestFocus();
				return false;
			}

			// ���Z�i�K�͈̔̓`�F�b�N
			int stage = mainView.ctrlCloseEntry.num.getInt();
			int max = getCompany().getFiscalPeriod().getMaxSettlementStage();

			if (stage <= 0 || max < stage) {
				// {0}��{1}�`{2}�͈̔͂Ŏw�肵�Ă�������
				showMessage("I00247", "C00718", 1, max);// ���Z�i�K

				mainView.ctrlCloseEntry.num.requestFocus();
				return false;
			}
		}

		// �v���̐�s�A���߃`�F�b�N

		// ���O�C�����[�U�̉�ЃR�[�h
		String keyCompany = getCompanyCode();

		// �ŐVCompany�擾(���Œ��߂��Ă���ꍇ��z��)
		CompanySearchCondition compParam = new CompanySearchCondition();

		for (SWK_DTL dtl : dtlList) {
			String kcompany = dtl.getSWK_K_KAI_CODE();
			if (keyCompany.equals(kcompany)) {
				continue;
			}

			compParam.addCode(kcompany);
		}

		if (!compParam.getCodeList().isEmpty()) {
			List<Company> kcompanyList = (List<Company>) request(CompanyManager.class, "get", compParam);

			for (Company kcompany : kcompanyList) {
				// ��s�`�[���t�`�F�b�N
				if (mainView.ctrlSlipDate.isPriorOver(kcompany)) {
					showMessage("I00133", kcompany.getCode());// �w��̓`�[���t�͌v���А�[{0}]�̐�s�`�[���t�𒴂��Ă��܂��B
					mainView.ctrlSlipDate.requestFocus();
					return false;
				}

				// ���߃`�F�b�N
				if (mainView.ctrlSlipDate.isClosed(kcompany, mainView.ctrlCloseEntry.getStage())) {
					showMessage("I00134", kcompany.getCode());// �w��̓`�[���t�͌v���А�[{0}]�Œ��߂��Ă��܂��B
					mainView.ctrlSlipDate.requestFocus();
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * �w�b�_�[���ڂ̃`�F�b�N
	 * 
	 * @return false:NG
	 * @throws TException
	 */
	@SuppressWarnings("unused")
	protected boolean checkHeaderInput() throws TException {
		// �E�v�R�[�h���̓`�F�b�N
		if (!checkInputBlank(mainView.ctrlSlipRemark.name, "C00384")) {// �E�v
			return false;
		}
		// ���F�O���[�v
		if (isUseWorkFlowApprove()
			&& !checkInputBlank(mainView.ctrlAprvGroup.code, mainView.ctrlAprvGroup.btn.getLangMessageID())) {
			// ���[�N�t���[���F���p���ɋ�
			return false;
		}
		return true;
	}

	/**
	 * ���׍��ڂ̃`�F�b�N
	 * 
	 * @return false:NG
	 * @throws TException
	 */
	@SuppressWarnings("unused")
	protected boolean checkDetailInput() throws TException {

		String keyCompany = getCompanyCode(); // ���O�C�����[�U�̉�ЃR�[�h
		String keyCurrencyCode = keyCurrency.getCode(); // ��ʉ݃R�[�h

		// ����
		List<SWK_DTL> dtlList = mainView.ctrlDetail.getEntityList();

		// ���׃`�F�b�N
		if (mainView.ctrlDetail.getDetailRowCount() == 0) {
			showMessage("I00037", "C01766");// {0}����͂��Ă��������B���׍s
			mainView.ctrlDetail.ctrlKDepartment.requestTextFocus();
			return false;
		}

		// �o�����X�`�F�b�N
		BigDecimal dr = BigDecimal.ZERO;
		BigDecimal cr = BigDecimal.ZERO;
		Map<String, BigDecimal[]> map = new TreeMap<String, BigDecimal[]>();

		for (SWK_DTL dtl : dtlList) {

			if (DecimalUtil.isNullOrZero(dtl.getSWK_KIN())) {
				showMessage("I00135");// ���ׂɋ��z����͂��Ă��������B
				return false;
			}

			dr = dr.add(dtl.getDebitKeyAmount());
			cr = cr.add(dtl.getCreditKeyAmount());

			// �O�ł͍��v�ɏ���Ńv���X
			if (!dtl.isTaxInclusive()) {
				dr = dr.add(dtl.getDebitTaxAmount());
				cr = cr.add(dtl.getCreditTaxAmount());
			}

			// �O��
			String currencyCode = dtl.getSWK_CUR_CODE();

			if (keyCurrencyCode.equals(currencyCode)) {
				continue;
			}

			BigDecimal[] dec = map.get(currencyCode);
			if (dec == null) {
				dec = new BigDecimal[] { BigDecimal.ZERO, BigDecimal.ZERO };
				map.put(currencyCode, dec);
			}

			dec[0] = dec[0].add(dtl.getDebitInputAmount());
			dec[1] = dec[1].add(dtl.getCreditInputAmount());

			if (!dtl.isTaxInclusive()) {
				dec[0] = dec[0].add(dtl.getDebitTaxInputAmount());
				dec[1] = dec[1].add(dtl.getCreditTaxInputAmount());
			}
		}

		for (SWK_DTL dtl : dtlList) {
			// BS��������`�F�b�N
			SWK_DTL bs = dtl.getBsDetail();

			if (bs == null) {
				continue;
			}

			if (!checkBsSlipDate(bs)) {
				return false;
			}
		}

		// �O�݃o�����X���
		boolean isFBalance = false;
		for (BigDecimal[] dec : map.values()) {
			if (DecimalUtil.isZero(dec[0]) && DecimalUtil.isZero(dec[1])) {
				continue; // ����0/0�͊܂߂Ȃ�
			}
			isFBalance = dec[0].compareTo(dec[1]) == 0;

			if (!isFBalance) {
				break;
			}
		}

		if (!isFBalance && dr.compareTo(cr) != 0) {
			showMessage("I00136");// �ݎ؂��o�����X���Ă��܂���B
			return false;
		}

		// �@�\�ʉݐݒ�`�F�b�N
		Set<String> companys = new TreeSet<String>();
		for (SWK_DTL dtl : dtlList) {
			Company kcomp = dtl.getAppropriateCompany();
			if (companys.contains(kcomp.getCode())) {
				continue;
			}

			Currency fCurrency = kcomp.getAccountConfig().getFunctionalCurrency();

			if (Util.isNullOrEmpty(fCurrency.getCode())) {
				showMessage("I00137", kcomp.getCode());// ���[{0}]�̋@�\�ʉ݂��ݒ肳��Ă��܂���B
				return false;
			}

			companys.add(kcomp.getCode());
		}

		// �������`�F�b�N
		int row = 0;
		int rowCount = mainView.ctrlDetail.tbl.getRowCount();
		Boolean isClosing = mainView.ctrlCloseEntry.isSelected();
		for (SWK_DTL dtl : dtlList) {
			Date hasDate = dtl.getHAS_DATE();
			if (hasDate == null) {
				continue;
			}
			String curCode = dtl.getSWK_CUR_CODE();
			if (row < rowCount) {
				BigDecimal rate = getCurrencyRateByOccurDate(hasDate, curCode, isClosing);
				if (isUseHasDateChk && rate == null) {
					showMessage("I01161"); // �������ɑΉ�����ʉ݃��[�g���ݒ肳��Ă��܂���B
					mainView.ctrlDetail.tbl.requestFocus(row, SC.issuerDay);
					return false;
				} else if (rate != null) {
					dtl.setSWK_CUR_RATE(rate);
					mainView.ctrlDetail.getEntityList().set(row, dtl);
				}
			}
			row = row + 1;
		}

		return true;
	}

	/**
	 * BS����̓`�[���t�`�F�b�N
	 * 
	 * @param bs
	 * @return true:OK
	 */
	protected boolean checkBsSlipDate(SWK_DTL bs) {

		Date termDate = mainView.ctrlSlipDate.getValue();
		String msg = "I00455";
		if (isBsTermLastDay) {
			// BS����\�̊������̎擾
			termDate = DateUtil.getLastDate(termDate);
			msg = "I00594"; // �`�[�N���𒴂���BS����̌v��N���͓��͂ł��܂���B
		}

		if (!Util.isSmallerThenByYMD(bs.getSWK_DEN_DATE(), termDate)) {
			// �`�[���t�𒴂���BS����̌v����t�͓��͂ł��܂���B
			showMessage(msg);
			mainView.ctrlSlipDate.requestTextFocus();
			return false;
		}
		return true;
	}

	/**
	 * ���̓u�����N�`�F�b�N
	 * 
	 * @param field �Ώۃt�B�[���h
	 * @param name �G���[���̕\����
	 * @return false:NG
	 */
	protected boolean checkInputBlank(TTextField field, String name) {
		if (field.isVisible() && field.isEditable() && field.isEmpty()) {
			showMessage("I00037", name);// {0}����͂��Ă��������B
			field.requestFocusInWindow();
			return false;
		}

		return true;
	}

	/**
	 * 2023INVOICE���x�p�G���[���b�Z�[�W�F����Ŋz�Ɠ��͏���Ŋz������Ȃ��ꍇ�̓G���[
	 * 
	 * @return false:NG
	 * @throws TException
	 */
	protected boolean checkInvoiceTax() throws TException {

		if (chkSlipTypeInvoice()) {
			return true;
		}

		List<String> list = new ArrayList<String>(); // �G���[���X�g
		Map<String, SWK_DTL> totalMap = new HashMap<String, SWK_DTL>(); // ���z�p
		Map<String, SWK_DTL> addMap = new HashMap<String, SWK_DTL>(); // ���Z�p
		List<SWK_DTL> dtlList = mainView.ctrlDetail.getEntityList(); // ��ʖ��׃��X�g
		String cmpCode = "";
		String code = "";
		SWK_DTL dtlMap = new SWK_DTL();
		// �v�Z�p
		BigDecimal taxAmountSum = BigDecimal.ZERO;
		BigDecimal amountSum = BigDecimal.ZERO;
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal taxAmount = BigDecimal.ZERO;

		// ���z�p
		for (SWK_DTL dtl : dtlList) {
			if (Util.isNullOrEmpty(dtl.getSWK_ZEI_CODE())) {
				continue;
			}

			Company kcompany = dtl.getAppropriateCompany();
			AccountConfig kconf = kcompany.getAccountConfig();

			ConsumptionTax tax = dtl.getTax();

			if (tax == null) {
				cmpCode = dtl.getAppropriateCompany().getCode();
				code = dtl.getSWK_ZEI_CODE();
				tax = setTaxEntity(cmpCode, code);
				if (tax == null) {
					continue;
				}
			}

			// keySet:����Ń��[�g+�ʉ݃R�[�h+���E�O�ŋ敪
			String key = DecimalUtil.toBigDecimalNVL(tax.getRate()) + "<>" + dtl.getSWK_CUR_CODE() + "<>"
				+ dtl.getSWK_ZEI_KBN();
			if (totalMap.containsKey(key)) {

				dtlMap = totalMap.get(key);

				// ���׋��z�擾
				amount = getAmount(dtl, amount);
				amountSum = dtlMap.getSWK_IN_KIN().add(amount);

			} else {
				dtlMap = new SWK_DTL();
				dtlMap.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);
				dtlMap.setCUR_DEC_KETA(dtl.getCUR_DEC_KETA());
				dtlMap.setTax(tax);

				taxAmountSum = BigDecimal.ZERO;

				// ���׋��z�擾
				amountSum = getAmount(dtl, amountSum);

			}

			TTaxCalculation param = TCalculatorFactory.createTaxCalculation();
			param.setTax(tax); // ����ŏ��
			param.setDigit(dtl.getCUR_DEC_KETA()); // �����_����
			param.setReceivingFunction(kconf.getReceivingFunction()); // �؎�
			param.setPaymentFunction(kconf.getPaymentFunction()); // ����
			param.setInside(dtl.isTaxInclusive()); // ���E�O��
			param.setAmount(amountSum); // �Ώۋ��z

			// 1�s�v�Z�p
			taxAmountSum = calculator.calculateTax(param);

			dtlMap.setSWK_IN_ZEI_KIN(taxAmountSum);
			dtlMap.setSWK_IN_KIN(amountSum);
			dtlMap.setSWK_ZEI_KBN(dtl.getSWK_ZEI_KBN());

			totalMap.put(key, dtlMap);
		}

		// �P�����Z�v�Z
		for (SWK_DTL dtl : dtlList) {

			if (Util.isNullOrEmpty(dtl.getSWK_ZEI_CODE())) {
				continue;
			}

			ConsumptionTax tax = dtl.getTax();
			if (tax == null) {
				cmpCode = dtl.getAppropriateCompany().getCode();
				code = dtl.getSWK_ZEI_CODE();
				tax = setTaxEntity(cmpCode, code);
				if (tax == null) {
					continue;
				}
			}

			dtlMap = new SWK_DTL();
			taxAmountSum = BigDecimal.ZERO;

			// keySet:����Ń��[�g+�ʉ݃R�[�h+���E�O�ŋ敪
			String key = DecimalUtil.toBigDecimalNVL(tax.getRate()) + "<>" + dtl.getSWK_CUR_CODE() + "<>"
				+ dtl.getSWK_ZEI_KBN();

			if (addMap.containsKey(key)) {
				dtlMap = addMap.get(key);
				taxAmountSum = DecimalUtil.avoidNull(dtlMap.getSWK_IN_ZEI_KIN());
			} else {
				dtlMap = new SWK_DTL();
				dtlMap.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);
				dtlMap.setCUR_DEC_KETA(dtl.getCUR_DEC_KETA());
				amountSum = BigDecimal.ZERO;
				taxAmountSum = BigDecimal.ZERO;
			}

			taxAmount = DecimalUtil.avoidNull(dtl.getSWK_IN_ZEI_KIN());
			if (!dtl.isDR()) {
				// Cr�̏ꍇ�������]
				taxAmount = taxAmount.negate();
			}

			taxAmountSum = taxAmountSum.add(taxAmount);

			dtlMap.setSWK_IN_ZEI_KIN(taxAmountSum);
			dtlMap.setSWK_ZEI_KBN(dtl.getSWK_ZEI_KBN());
			dtlMap.setTax(tax);
			dtlMap.setCUR_DEC_KETA(dtl.getCUR_DEC_KETA());

			addMap.put(key, dtlMap);

		}

		for (String key : totalMap.keySet()) {
			SWK_DTL totalDtl = totalMap.get(key);
			SWK_DTL addDtl = addMap.get(key);

			// ��Βl
			BigDecimal totalDtlKin = totalDtl.getSWK_IN_ZEI_KIN().abs();
			BigDecimal addDtlKin = addDtl.getSWK_IN_ZEI_KIN().abs();

			if (!DecimalUtil.equals(totalDtlKin, addDtlKin)) {
				String inOutZei = totalDtl.getSWK_ZEI_KBN() == TaxCalcType.OUT.value ? getWord(TaxCalcType.OUT
					.getName()) : getWord(TaxCalcType.IN.getName()); // ����or�O��
				String zeiRate = inOutZei + " " + getWord("C01554")
					+ DecimalUtil.toBigDecimalNVL(totalDtl.getTax().getRate()) + "%";// �ŗ�XX%
				String totalZei = NumberFormatUtil.formatNumber(totalDtlKin, totalDtl.getCUR_DEC_KETA());
				String addZei = NumberFormatUtil.formatNumber(addDtlKin, addDtl.getCUR_DEC_KETA());

				String message = getMessage("I01080", zeiRate, totalZei, addZei);
				// ����ŁF{0}�̑��z�ŏ���ł��v�Z�����[{1}]�ł����A����Ōv�Z�㑍�z�����[{2}]�ł��B

				list.add(message);
			}
		}

		if (list.size() != 0) {
			if (ConfermMessageListDialog.OK_OPTION != showConfermList(mainView, getMessage("I01079"), list)) {
				// ����Ŋz�����z�v�Z�Ɩ��׍��v�ňقȂ��Ă��܂��B���s���܂����H
				return false;
			}
		}
		return true;

	}

	/**
	 * INVOICE�p�F�`�[��ʂŃ`�F�b�N���x�g�p���邩
	 * 
	 * @return false:�g�p����
	 */
	protected boolean chkSlipTypeInvoice() {

		if (slipType == null || !slipType.isINV_SYS_FLG()) {
			return true;
		}

		if (slipType.getCode().equals("031") && Util.isNullOrEmpty(getCompany().getInvRegNo())) {
			// ���v�ォ��Ѓ}�X�^�ɓK�i���������s���ƎҔԍ������͂���Ă��Ȃ��ꍇ�̓G���[���b�Z�[�W�s�v
			return true;
		}
		return false;
	}

	/**
	 * �����Entity�擾
	 * 
	 * @param cmpCode ��ЃR�[�h
	 * @param code ����R�[�h
	 * @return �����
	 * @throws TException
	 */
	protected ConsumptionTax setTaxEntity(String cmpCode, String code) throws TException {

		ConsumptionTaxSearchCondition condition = new ConsumptionTaxSearchCondition();
		condition.setCompanyCode(cmpCode);
		condition.setCode(code);
		List<ConsumptionTax> list = (List<ConsumptionTax>) request(ConsumptionTaxManager.class, "get", condition);

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * �C���{�C�X �����Entity�擾
	 * 
	 * @param cmpCode ��ЃR�[�h
	 * @param code �����R�[�h
	 * @return bean
	 */
	protected Customer setCustomerEntity(String cmpCode, String code) {

		List<Customer> list = new ArrayList<Customer>();
		try {

			CustomerSearchCondition condition = new CustomerSearchCondition();
			condition.setCompanyCode(cmpCode);
			condition.setCode(code);

			list = (List<Customer>) request(CustomerManager.class, "get", condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

		} catch (Exception e) {
			errorHandler(e);
		}

		return list.get(0);
	}

	/**
	 * ���׋��z�̎擾
	 * 
	 * @param dtl
	 * @param amount
	 * @return ���׋��z
	 */
	protected BigDecimal getAmount(SWK_DTL dtl, BigDecimal amount) {

		amount = DecimalUtil.avoidNull(dtl.getSWK_IN_KIN());

		if (!dtl.isDR()) {
			// Cr�̏ꍇ�}�C�i�X
			amount = amount.negate();
		}

		return amount;
	}

	/**
	 * �������ɑ΂���ʉ݃��[�g�擾
	 * 
	 * @param hasDate ������
	 * @param curCode �ʉ݃R�[�h
	 * @param isClosing 
	 * 
	 * @return ���[�g
	 */
	protected BigDecimal getCurrencyRateByOccurDate(Date hasDate, String curCode, Boolean isClosing) {
		BigDecimal rate = null;
		try {

			if (curCode.equals(keyCurrency.getCode())) {
				return BigDecimal.ONE;
			}
			String key = curCode + "<>" + DateUtil.toYMDString(hasDate);

			if (rateMapByOccurDate.containsKey(key) && rateMapByOccurDate.get(key) != null) {
				rate = rateMapByOccurDate.get(key);
			} else {
				rate = (BigDecimal) request(RateManager.class,
						isClosing ? "getSettlementRateByOccurDate" : "getRateByOccurDate", curCode, hasDate);
				rateMapByOccurDate.put(key, rate);
			}
		} catch (TException ex) {
			errorHandler(ex);
			return null;
		}
		return rate;
	}

	/**
	 * ��ʓ��͂̔��f(�w�b�_)
	 * 
	 * @param hdr �w�b�_
	 */
	protected void reflectHeader(SWK_HDR hdr) {

		// �V�K���ǂ���
		boolean isNew = hdr.getINP_DATE() == null;

		hdr.setSWK_UPD_KBN(SlipState.ENTRY.value); // �X�V�敪
		hdr.setSWK_SHR_KBN(Slip.SHR_KBN.NON_LOCKED); // �r��

		hdr.setKAI_CODE(getCompanyCode()); // ��ЃR�[�h
		hdr.setSWK_DEN_DATE(mainView.ctrlSlipDate.getValue()); // �`�[���t
		hdr.setSWK_DEN_NO(mainView.ctrlSlipNo.getValue()); // �`�[�ԍ�
		hdr.setSWK_SEI_NO(mainView.ctrlEvidenceNo.getValue()); // �؜ߔԍ�/������No.
		hdr.setSWK_TEK_CODE(mainView.ctrlSlipRemark.getCode()); // �`�[�E�v
		hdr.setSWK_TEK(mainView.ctrlSlipRemark.getNames());// �`�[�E�v
		hdr.setSWK_KSN_KBN(mainView.ctrlCloseEntry.getStage());// ���Z�d��

		hdr.setSWK_DEN_SYU_NAME(slipType.getName());
		hdr.setSWK_DEN_SYU_NAME_S(slipType.getNames());
		hdr.setSWK_DEN_SYU_NAME_K(slipType.getNamek());

		// �`�[�ݒ�
		if (isNew) {
			hdr.setSWK_SYS_KBN(slipType.getSystemDiv()); // �V�X�e���敪
			hdr.setSWK_DEN_SYU(slipType.getCode()); // �`�[���
			hdr.setSWK_DATA_KBN(slipType.getDataType()); // �f�[�^�敪
		}

		// �ʉݏ��
		hdr.setKEY_CUR_CODE(keyCurrency.getCode());
		hdr.setKEY_CUR_DEC_KETA(keyCurrency.getDecimalPoint());
		hdr.setFUNC_CUR_CODE(conf.getFunctionalCurrency().getCode());
		hdr.setFUNC_CUR_DEC_KETA(conf.getFunctionalCurrency().getDecimalPoint());

		// �`�[�Y�t
		hdr.setAttachments(mainView.btnAttach.getAttachments());

		// �tⳋ@�\
		List<SWK_TAG> list = new ArrayList<SWK_TAG>();
		if (mainView.ctrlTag1.getEntity() != null) {
			Tag bean = mainView.ctrlTag1.getEntity();
			SWK_TAG tag = new SWK_TAG();
			tag.setKAI_CODE(getCompanyCode());
			tag.setSWK_DEN_NO(hdr.getSWK_DEN_NO());
			tag.setSEQ(1);
			tag.setTAG_CODE(bean.getCode());
			tag.setTAG_COLOR(bean.getColor());
			tag.setTAG_TITLE(mainView.ctrlTag1.getName()); // ��ʒl���g�p
			list.add(tag);
		}
		if (mainView.ctrlTag2.getEntity() != null) {
			Tag bean = mainView.ctrlTag2.getEntity();
			SWK_TAG tag = new SWK_TAG();
			tag.setKAI_CODE(getCompanyCode());
			tag.setSWK_DEN_NO(hdr.getSWK_DEN_NO());
			tag.setSEQ(2);
			tag.setTAG_CODE(bean.getCode());
			tag.setTAG_COLOR(bean.getColor());
			tag.setTAG_TITLE(mainView.ctrlTag2.getName()); // ��ʒl���g�p
			list.add(tag);
		}
		hdr.setSwkTags(list);
		hdr.setSWK_APRV_GRP_CODE(mainView.ctrlAprvGroup.getCode());
		hdr.setAprRoleGroup(mainView.ctrlAprvGroup.getEntity());
	}

	/**
	 * ��ʓ��͂̔��f(����)
	 */
	protected void reflectDetails() {
		SWK_HDR hdr = slip.getHeader();
		String slipRemarks = hdr.getSWK_TEK();

		slip.clearDetail();

		for (SWK_DTL dtl : mainView.ctrlDetail.getEntityList()) {
			SWK_DTL cdtl = dtl.clone();

			// ���ł̏ꍇ�A���z�̕ҏW
			if (cdtl.isTaxInclusive()) {
				cdtl.setSWK_IN_KIN(cdtl.getSWK_IN_KIN().subtract(cdtl.getSWK_IN_ZEI_KIN()));
				cdtl.setSWK_KIN(cdtl.getSWK_KIN().subtract(cdtl.getSWK_ZEI_KIN()));
			}

			// �s�E�v���ݒ�̏ꍇ�́A�`�[�E�v���Z�b�g
			if (Util.isNullOrEmpty(cdtl.getSWK_GYO_TEK())) {
				cdtl.setSWK_GYO_TEK(slipRemarks);
			}

			slip.addDetail(cdtl);
		}
	}

	/**
	 * ��ʓ��͂̔��f(�p�^�[������)
	 */
	protected void reflectPatternDetails() {

		SWK_HDR hdr = slip.getHeader();
		String slipRemarks = hdr.getSWK_TEK();

		slip.clearDetail();

		for (SWK_DTL dtl : mainView.ctrlDetail.getEntityList()) {
			SWK_DTL cdtl = dtl.clone();

			// ���ł̏ꍇ�A���z�̕ҏW
			if (cdtl.isTaxInclusive()) {
				cdtl.setSWK_IN_KIN(cdtl.getSWK_IN_KIN().subtract(cdtl.getSWK_IN_ZEI_KIN()));
				cdtl.setSWK_KIN(cdtl.getSWK_KIN().subtract(cdtl.getSWK_ZEI_KIN()));
			}

			// �s�E�v���ݒ�̏ꍇ�́A�`�[�E�v���Z�b�g
			if (Util.isNullOrEmpty(cdtl.getSWK_GYO_TEK())) {
				cdtl.setSWK_GYO_TEK(slipRemarks);
			}

			cdtl.setSWK_KESI_KBN(SWK_DTL.KESI_KBN.NOMAL);
			cdtl.setSWK_KESI_DATE(null); // �����`�[���t
			cdtl.setSWK_KESI_DEN_NO(null); // �����`�[�ԍ�
			cdtl.setSWK_SOUSAI_DATE(null); // ���E�`�[���t
			cdtl.setSWK_SOUSAI_DEN_NO(null); // ���E�`�[�ԍ�
			cdtl.setAP_ZAN(null);
			cdtl.setAR_ZAN(null);
			cdtl.setBsDetail(null);

			slip.addDetail(cdtl);
		}
	}

	/**
	 * �d�󎫏�����
	 */
	public void doPattern() {
		searchPatternSlip();
	}

	/**
	 * �����o�^
	 */
	public void doPatternSave() {

		// ���̓`�F�b�N
		if (!checkPatternSaveInput()) {
			return;
		}

		createPatternSaveView();
		patternSaveView.setLocationRelativeTo(null);
		patternSaveView.setVisible(true);
	}

	/**
	 * ���͒l�`�F�b�N
	 * 
	 * @return false:NG
	 */
	protected boolean checkPatternSaveInput() {

		return true;
	}

	/**
	 * �����o�^�_�C�A���O���J��
	 */
	protected void createPatternSaveView() {

		// ���[�h�I���_�C�A���O����
		patternSaveView = new TSlipPatternSaveDialog(mainView.getParentFrame(), true);
		// �C�x���g��`
		addPatternSaveViewEvent();
	}

	/**
	 * �����o�^�_�C�A���O�C�x���g��`
	 */
	protected void addPatternSaveViewEvent() {

		// ����{�^��������
		patternSaveView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				patternSaveView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				patternSaveView.setVisible(false);
				patternSaveView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// �m��{�^��������
		patternSaveView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				patternSaveView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnPatternSave_Settle_click();
				patternSaveView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * �����o�^�_�C�A���O�m��{�^��������
	 */
	protected void btnPatternSave_Settle_click() {

		try {
			// ���̓`�F�b�N
			if (!checkPatternNoInput()) {
				return;
			}

			// �`�[�ɔ��f
			Slip saveSlip = getSavePatternSlip();

			// �p�^�[���ԍ����݃`�F�b�N
			SWK_HDR existsPattern = (SWK_HDR) request(getManagerClass(), "getExistsPattern",
				patternSaveView.ctrlPatternNo.getValue());
			if (existsPattern != null) {
				if (!showConfirmMessage(patternSaveView, "Q00079")) { // �w��̃p�^�[���ԍ��͊��ɑ��݂��܂��B�㏑�����܂����H
					patternSaveView.ctrlPatternNo.requestFocus();
					return;
				}
				saveSlip.getHeader().setSWK_UPD_CNT(existsPattern.getSWK_UPD_CNT());
				saveSlip.getHeader().setINP_DATE(existsPattern.getINP_DATE());
				saveSlip.getHeader().setSWK_INP_DATE(existsPattern.getINP_DATE());
				saveSlip.getHeader().setSWK_SYS_KBN(existsPattern.getSWK_SYS_KBN()); // �V�X�e���敪
				saveSlip.getHeader().setSWK_DEN_SYU(existsPattern.getSWK_DEN_SYU()); // �`�[���
				saveSlip.getHeader().setSWK_DATA_KBN(existsPattern.getSWK_DATA_KBN()); // �f�[�^�敪
			}

			// �o�^
			request(getManagerClass(), "entryPattern", saveSlip);

			patternSaveView.setVisible(false);

		} catch (Exception ex) {
			errorHandler(ex);
		}
	}

	/**
	 * ���͒l�`�F�b�N
	 * 
	 * @return false:NG
	 */
	protected boolean checkPatternNoInput() {

		// �p�^�[���ԍ��������͂̏ꍇ
		if (!checkInputBlank(patternSaveView.ctrlPatternNo.getField(), "C00987")) {
			return false;
		}
		return true;
	}

	/**
	 * �v���r���[
	 */
	public void doPreview() {

		try {
			Slip tempSlip = getSaveSlip();

			if (tempSlip.getDetails() == null) {
				tempSlip.setDetails(new ArrayList<SWK_DTL>());
			}

			// TODO:�o�͉\�̐���
			if (tempSlip.getDetails().size() == 0) {
				tempSlip.addDetail(new SWK_DTL());
			}

			byte[] data = (byte[]) request(SlipManager.class, "getTempSlipReport", tempSlip);
			if (data != null && data.length != 0) {
				TPrinter printer = new TPrinter();
				printer.preview(data, getPrintName() + ".pdf");
			}
		} catch (Exception ex) {
			errorHandler(ex);
		}
	}

	/**
	 * �`�[�p�^�[�������Ɣ��f
	 */
	protected void searchPatternSlip() {
		searchPatternSlipAddResult();
	}

	/**
	 * �`�[�p�^�[�������Ɣ��f
	 * 
	 * @return true:����
	 */
	protected boolean searchPatternSlipAddResult() {

		try {
			TSlipPatternSearchCtrl ctrl = createPatternSearchCtrl();

			ctrl.switchSelfOnly(); // ���g�̃f�[�^�̂݌Ăяo��
			ctrl.setIncludeLocked(true); // �r�����f�[�^���܂�

			if (ctrl.show() != TSlipSearchCtrl.OK_OPTION) {
				mainView.btnPattern.requestFocus();
				return false;
			}

			// ���݂̓`�[���t������Ă���
			Date slipDate = mainView.ctrlSlipDate.getValue();

			// ��U�N���A
			clearView();

			// ���f
			SWK_HDR hdr = ctrl.getSelectedRow();

			// �`�[�\�z
			slip = (Slip) request(getManagerClass(), "getPatternSlip", hdr);

			if (isInvoice && slip.getDetails() != null && !slip.getDetails().isEmpty()) {
				patternSlipType = slip.getHeader().getSWK_DEN_SYU();
				if (checkInvoiceItemTaxCode(slip.getDetails())) {
					// �C���{�C�X�p:�����Ə���ł��K�i�A�K�i���`�F�b�N
					patternSlipType = null;
					return false;
				}
				patternSlipType = null;
			}

			// ��ʔ��f
			dispatch();

			// �V�K����
			slip = null;
			setSlipDate(slipDate); // ���t�͌��̂܂�
			mainView.ctrlSlipNo.clear();
			switchNew();

			return true;

		} catch (Exception ex) {
			errorHandler(ex);
			return false;
		}
	}

	/**
	 * �p�^�[�������R���g���[������
	 * 
	 * @return �p�^�[�������R���g���[��
	 */
	protected TSlipPatternSearchCtrl createPatternSearchCtrl() {
		TSlipPatternSearchCtrl ctrl = new TSlipPatternSearchCtrl(mainView);
		ctrl.setSlipType(slipType.getCode()); // �`�[���
		ctrl.setDataKind(slipType.getDataType()); // �f�[�^�敪

		return ctrl;
	}

	/**
	 * �`�[���t�̕ύX
	 */
	protected void changedSlipDate() {
		Date slipDate = mainView.ctrlSlipDate.getValue();

		// �`�[���t������Ƃ��ăZ�b�g
		mainView.ctrlSlipRemark.getSearchCondition().setValidTerm(slipDate);
		mainView.ctrlDetail.setBaseDate(slipDate);
	}

	/**
	 * ���Z�d��̐ؑ�
	 */
	protected void changedClosingEntry() {
		mainView.ctrlDetail.setClosingEntry(mainView.ctrlCloseEntry.isSelected());
	}

	/**
	 * ���v�l�v�Z
	 */
	protected void summary() {
		mainView.ctrlDetail.summary();
	}

	/**
	 * ��ʋN��/�I�����̔r����������
	 * 
	 * @see jp.co.ais.trans2.model.security.TExclusive#getExclusiveControlMethod()
	 */
	@Override
	public TExclusiveControlMethod getExclusiveControlMethod() {
		return new SlipExclusiveControlMethod(this.getProgramInfo(), getSlipTypeNo());
	}

	/**
	 * �`�[�����Ɣ��f(�h�����_�E����ʂ���J��)
	 * 
	 * @param isModify true:�C�� false:����
	 * @param hdr
	 * @param copyMode 0:�ʏ� 1:�ԓ` 2:�t�`
	 * @return true:����
	 */
	public boolean searchSlipAddResultNoneDialog(boolean isModify, SWK_HDR hdr, int copyMode) {

		try {
			// �N�������l�����Ĉ�U�V�K���Z�b�g
			clearView();

			// ���[�h�Z�b�g
			this.isModifyMode = isModify;

			// ���݂̓`�[���t������Ă���
			Date slipDate = mainView.ctrlSlipDate.getValue();

			// �`�[�\�z
			slip = (Slip) request(getManagerClass(), "getSlip", hdr, isModify);

			// ���ʂ̏ꍇ
			if (!isModify) {
				// �`�[�ԍ��N���A
				slip.setSlipNo(null);

				// ���ʎ��A�Y�t�t�@�C�����N���A
				slip.getHeader().setAttachments(null);

				// ���ʎ��A�tⳋ@�\���N���A
				slip.getHeader().setSwkTags(null);

				Map<String, BigDecimal> rateMap = new HashMap<String, BigDecimal>();

				// ���ׂ̕R�t��������
				for (SWK_DTL dtl : slip.getDetails()) {
					dtl.setSWK_DEN_NO(null);
					dtl.setSWK_KESI_KBN(SWK_DTL.KESI_KBN.NOMAL);
					dtl.setSWK_KESI_DATE(null); // �����`�[���t
					dtl.setSWK_KESI_DEN_NO(null); // �����`�[�ԍ�
					dtl.setSWK_SOUSAI_DATE(null); // ���E�`�[���t
					dtl.setSWK_SOUSAI_DEN_NO(null); // ���E�`�[�ԍ�
					dtl.setSWK_SOUSAI_GYO_NO(null); // ���E�s�ԍ�

					dtl.setAP_ZAN(null);
					dtl.setAR_ZAN(null);
					dtl.setBsDetail(null);

					if (isCopySlipDateToOccurDate) {
						// ���ʎ��ɔ�������`�[���t�ɕύX����
						dtl.setHAS_DATE(slipDate);

						Company kcompany = dtl.getAppropriateCompany();
						Currency currency = dtl.getCurrency();
						ConsumptionTax tax = dtl.getTax();

						BigDecimal amount = dtl.getSWK_IN_KIN();
						BigDecimal taxAmount = dtl.getSWK_IN_ZEI_KIN();

						BigDecimal rate = null;
						String key = currency != null ? currency.getCode() : keyCurrency.getCode();
						if (rateMap.containsKey(key)) {
							rate = rateMap.get(key);
						} else {
							rate = getCurrencyRate(currency, slip.isClosingSlip(), slipDate);
							rateMap.put(key, rate);
						}

						// �������`�F�b�N���g�p����ꍇ
						if (isUseHasDateChk) {
							rate = getCurrencyRateByOccurDate(slipDate, currency.getCode(), slip.isClosingSlip());
						}

						BigDecimal keyAmount = convertKeyAmount(amount, rate, kcompany, currency);
						BigDecimal keyTaxAmount = convertKeyTaxAmount(taxAmount, rate, kcompany, currency, tax);

						dtl.setSWK_CUR_RATE(rate);
						dtl.setSWK_KIN(keyAmount);
						dtl.setSWK_ZEI_KIN(keyTaxAmount);
					}
				}

				if (copyMode == 1) {
					// ��
					slip = slip.toCancelSlip();

				} else if (copyMode == 2) {
					// �t
					slip = slip.toReverseSlip();
				}

			}

			// ��ʔ��f
			dispatch();

			if (isModify) {
				// �C��
				switchModify();

			} else {
				// ����(�V�K����)
				slip = null;
				if (!notReservationSlipDate) {
					setSlipDate(slipDate); // ���t�͌��̂܂�
				}
				mainView.ctrlSlipNo.clear();

				if (!notReservationCloseEntry) {
					mainView.ctrlCloseEntry.setSelected(false); // ���Z�d��
				}

				switchNew();
			}

			return true;

		} catch (Exception ex) {
			errorHandler(ex);
			return false;
		}
	}
}