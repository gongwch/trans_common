package jp.co.ais.trans2.common.model.ui.slip;

import java.math.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.calc.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * ���z���̓w�b�_�t���`�[�p�l���R���g���[��
 */
public abstract class TSlipAddPartsPanelCtrl extends TSlipPanelCtrl {

	/** �w����� */
	@SuppressWarnings("hiding")
	protected TSlipAddPartsPanel mainView = (TSlipAddPartsPanel) super.mainView;

	/** �w�b�_�[���׍s */
	protected SWK_DTL headerDetail;

	/**
	 * ��ʃw�b�_�̏����ݒ�
	 */
	@Override
	protected void initHeaderView() {
		super.initHeaderView();

		// �w�b�_�p����
		headerDetail = createHeaderDetail();

		// �w�b�_�[����̓��O�C����Ђ��Z�b�g���邩�H
		if (!isDepSetting && isDefaultBlankDepartment()) {
			// �v�㕔��̏����l��null�̏ꍇ�A���[�U���̐ݒ�s�v
			setDepartment(null);
		} else {
			// �f�t�H���g�v�㕔��
			setDepartment(getUser().getDepartment());
		}

		mainView.ctrlItem.getSearchCondition().setDepartmentCode(getUser().getDepartment().getCode());

		// �f�t�H���g�ʉ݃R�[�h
		setCurrecy(keyCurrency);

		// ��ʉ݌���
		int digit = keyCurrency.getDecimalPoint();
		mainView.ctrlKeyAmount.setDecimalPoint(digit);

		// �w�b�_�[���׍s�ǉ�
		mainView.ctrlDetail.setOutherDetail(headerDetail);
	}

	/**
	 * �w�b�_�[���׍s�쐬
	 * 
	 * @return �w�b�_�[���׍s
	 */
	protected SWK_DTL createHeaderDetail() {
		SWK_DTL dtl = new SWK_DTL();

		dtl.setAppropriateCompany(getCompany()); // �v����
		dtl.setCurrency(getCompany().getAccountConfig().getKeyCurrency()); // �ʉ�
		dtl.setSWK_CUR_RATE(BigDecimal.ONE); // ���[�g1
		dtl.setSWK_ZEI_KBN(SWK_DTL.ZEI_KBN.TAX_NONE); // ��ې�
		dtl.setSWK_AUTO_KBN(SWK_DTL.AUTO_KBN.AUTO); // �����d��

		switch (SlipKind.get(slipType.getDataType())) {
			case EMPLOYEE:
			case OUTPUT_CASH_FLOW:
			case PURCHASE:
				dtl.setDC(Dc.CREDIT); // ��
				break;

			case INPUT_CASH_FLOW:
			case SALES:
			default:
				dtl.setDC(Dc.DEBIT); // ��
				break;
		}

		return dtl;
	}

	/**
	 * ��ʖ��ׂ̏����ݒ�
	 */
	@Override
	protected void initDetailView() {
		super.initDetailView();

		// �ݎ؂̃f�t�H���g�l
		switch (SlipKind.get(slipType.getDataType())) {
			case INPUT_CASH_FLOW:
			case SALES:
				mainView.ctrlDetail.setDefaultDC(Dc.CREDIT); // ��
				break;

			case EMPLOYEE:
			case OUTPUT_CASH_FLOW:
			case PURCHASE:
			default:
				mainView.ctrlDetail.setDefaultDC(Dc.DEBIT); // ��
				break;
		}

	}

	/**
	 * �w����ʂ̃C�x���g��`�B
	 */
	@Override
	protected void addViewEvent() {
		super.addViewEvent();

		// �v�㕔��
		mainView.ctrlDepartment.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag) {
					return;
				}
				changedDepartment();
			}
		});

		// �Ȗ�
		mainView.ctrlItem.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag) {
					return;
				}
				changedItem();
			}
		});

		// �ʉ�
		mainView.ctrlCurrency.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag) {
					return;
				}
				changedCurrency();
			}
		});

		// �ʉ݃��[�g
		mainView.ctrlRate.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!mainView.ctrlRate.isValueChanged2()) {
					return true;
				}

				changedRate();
				return true;
			}
		});

		// ���͋��z
		mainView.ctrlInputAmount.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!mainView.ctrlInputAmount.isValueChanged2()) {
					return true;
				}

				changedInputAmount();
				return true;
			}
		});

		// ����z
		mainView.ctrlKeyAmount.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!mainView.ctrlKeyAmount.isValueChanged2()) {
					return true;
				}

				changedKeyAmount();
				return true;
			}
		});
	}

	/**
	 * �����o�^���w�b�_�[���ڂ̃`�F�b�N
	 * 
	 * @return false:NG
	 */
	@Override
	protected boolean checkPatternSaveInput() {

		BigDecimal inKin = mainView.ctrlInputAmount.getBigDecimal(); // ���͋��z
		BigDecimal kin = mainView.ctrlKeyAmount.getBigDecimal(); // �M�݋��z

		if (!DecimalUtil.isZero(inKin) && !DecimalUtil.isZero(kin) && inKin.signum() != kin.signum()) {
			showMessage("I00125"); // ���͋��z�ƖM�݋��z�̕������قȂ�܂��B
			mainView.ctrlKeyAmount.requestFocus();
			return false;
		}
		return true;
	}

	/**
	 * �w�b�_�[���ڂ̃`�F�b�N
	 * 
	 * @return false:NG
	 */
	@Override
	protected boolean checkHeaderInput() {

		// ������̓`�F�b�N
		if (!checkInputBlank(mainView.ctrlDepartment.code, mainView.ctrlDepartment.btn.getText())) {
			return false;
		}

		// �Ȗ�
		TItemGroup item = mainView.ctrlItem;
		if (!checkInputBlank(item.ctrlItemReference.code, item.ctrlItemReference.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(item.ctrlSubItemReference.code, item.ctrlSubItemReference.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(item.ctrlDetailItemReference.code, item.ctrlDetailItemReference.btn.getText())) {
			return false;
		}

		// ���z�֌W�̃`�F�b�N
		if (!checkHeaderAmountInput()) {
			return false;
		}

		// �E�v�R�[�h���̓`�F�b�N
		if (!checkInputBlank(mainView.ctrlSlipRemark.name, "C00384")) {// �E�v

			return false;
		}

		return true;
	}

	/**
	 * �w�b�_�[���z���ڂ̓��̓`�F�b�N
	 * 
	 * @return false:NG
	 */
	protected boolean checkHeaderAmountInput() {

		// �ʉ�
		if (!checkInputBlank(mainView.ctrlCurrency.code, mainView.ctrlCurrency.btn.getText())) {
			return false;
		}

		// ���[�g
		if (!checkInputBlank(mainView.ctrlRate.getField(), mainView.ctrlRate.getLabel().getText())) {
			return false;
		}

		// ���͋��z
		BigDecimal inKin = mainView.ctrlInputAmount.getBigDecimal();

		// ��ʉ݂ƈقȂ�ʉ݂̏ꍇ�́A0��F�߂�.
		if (keyCurrency.getCode().equals(mainView.ctrlCurrency.getCode())) {
			if (DecimalUtil.isZero(inKin)) {
				showMessage("I00037", mainView.ctrlInputAmount.getLabel().getText());// {0}����͂��Ă��������B
				mainView.ctrlInputAmount.requestFocus();
				return false;
			}
		}

		// �M�݋��z
		BigDecimal kin = mainView.ctrlKeyAmount.getBigDecimal();
		if (DecimalUtil.isZero(kin)) {
			showMessage("I00037", mainView.ctrlKeyAmount.getLabel().getText());// {0}����͂��Ă��������B
			mainView.ctrlKeyAmount.requestFocus();

			return false;
		}

		if (!DecimalUtil.isZero(inKin) && inKin.signum() != kin.signum()) {
			showMessage("I00125");// ���͋��z�ƖM�݋��z�̕������قȂ�܂�
			mainView.ctrlKeyAmount.requestFocus();
			return false;
		}

		return true;
	}

	/**
	 * �l�N���A
	 */
	@Override
	protected void clearComponents() {
		super.clearComponents();

		mainView.ctrlDepartment.clear();
		mainView.ctrlItem.clear();
		changedItem();

		mainView.ctrlEvidenceNo.clear();
		mainView.ctrlCurrency.clear();
		mainView.ctrlRate.clear();
		mainView.ctrlInputAmount.clear();
		mainView.ctrlKeyAmount.clear();
		changedKeyAmount();

		if (isDefaultBlankDepartment()) {
			// �v�㕔��̏����l��null�̏ꍇ�A���[�U���̐ݒ�s�v
			setDepartment(null);
		} else {
			// �f�t�H���g�v�㕔��
			setDepartment(getUser().getDepartment());
		}

		// �f�t�H���g�ʉ�
		setCurrecy(keyCurrency);

		mainView.ctrlCurrency.setEditable(false);
		mainView.ctrlRate.setEditable(false);
		mainView.ctrlKeyAmount.setEditable(false);

		// ���׃N���A
		headerDetail = createHeaderDetail();
		mainView.ctrlDetail.setOutherDetail(headerDetail);

	}

	/**
	 * @return true:�v�㕔�叉���u�����N
	 */
	protected boolean isDefaultBlankDepartment() {
		return TSlipDetailPanelCtrl.departmentDefaultBlank;
	}

	/**
	 * ��ʔ��f
	 */
	@Override
	protected void dispatch() {
		super.dispatch();

		// �w�b�_����
		SWK_HDR hdr = slip.getHeader();

		// �v�㕔��
		setDepartment(hdr.getDepartment());

		// ���Ȗ�
		mainView.ctrlItem.setEntity(hdr.getItem());
		changedItem();

		// �ʉ݃R�[�h
		setCurrecy(hdr.getCurrency());

		mainView.ctrlEvidenceNo.setValue(hdr.getSWK_SEI_NO()); // ������No.
		mainView.ctrlRate.setNumber(hdr.getSWK_CUR_RATE()); // ���[�g
		mainView.ctrlInputAmount.setNumber(hdr.getSWK_IN_KIN()); // �O��
		mainView.ctrlKeyAmount.setNumber(hdr.getSWK_KIN()); // �M��

		// ���ׂ֔��f
		dispatchHeaderDetail(hdr);

		changedKeyAmount();
	}

	/**
	 * �w�b�_�����w�b�_�p���ׂɓ]�L
	 * 
	 * @param hdr �w�b�_
	 */
	protected void dispatchHeaderDetail(SWK_HDR hdr) {
		// ����
		headerDetail.setSWK_ZEI_KBN(2);// �ŋ敪
		headerDetail.setSWK_ZEI_KIN(BigDecimal.ZERO);// ����Ŋz
		headerDetail.setSWK_ZEI_RATE(BigDecimal.ZERO);// �ŗ�
		headerDetail.setSWK_GYO_TEK(hdr.getSWK_TEK());// �s�E�v
		headerDetail.setSWK_K_KAI_CODE(getCompanyCode());// �v���к���
		headerDetail.setSWK_TUKE_KBN(0);// ��Њԕt�֓`�[�敪
		headerDetail.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);// ���͏���Ŋz
		headerDetail.setSWK_KESI_KBN(0);// �����敪
		headerDetail.setDepartment(hdr.getDepartment()); // �v�㕔��
		headerDetail.setItem(hdr.getItem()); // ���Ȗ�
		headerDetail.setSWK_SEI_NO(hdr.getSWK_SEI_NO()); // ������No.
		headerDetail.setCurrency(hdr.getCurrency()); // �ʉ݃R�[�h
		headerDetail.setSWK_CUR_RATE(hdr.getSWK_CUR_RATE()); // ���[�g
		headerDetail.setSWK_IN_KIN(hdr.getSWK_IN_KIN()); // �O��
		headerDetail.setSWK_KIN(hdr.getSWK_KIN()); // �M��
		headerDetail.setHAS_DATE(hdr.getSWK_DEN_DATE()); // �������i�`�[���t�j
	}

	/**
	 * ��ʓ��͂̔��f(�w�b�_)
	 * 
	 * @param hdr �w�b�_
	 */
	@Override
	protected void reflectHeader(SWK_HDR hdr) {
		super.reflectHeader(hdr);

		hdr.setDepartment(mainView.ctrlDepartment.getEntity()); // �v�㕔��
		hdr.setItem(mainView.ctrlItem.getEntity()); // ���Ȗ�

		hdr.setSWK_SEI_NO(mainView.ctrlEvidenceNo.getValue()); // ������No.
		hdr.setCurrency(mainView.ctrlCurrency.getEntity()); // �ʉ݃R�[�h
		hdr.setSWK_CUR_RATE(mainView.ctrlRate.getBigDecimal()); // ���[�g
		hdr.setSWK_IN_KIN(mainView.ctrlInputAmount.getBigDecimal()); // �O��
		hdr.setSWK_KIN(mainView.ctrlKeyAmount.getBigDecimal()); // �M��

		// ����
		headerDetail.setSWK_ZEI_KBN(2);// �ŋ敪
		headerDetail.setSWK_ZEI_KIN(BigDecimal.ZERO);// ����Ŋz
		headerDetail.setSWK_ZEI_RATE(BigDecimal.ZERO);// �ŗ�
		headerDetail.setSWK_GYO_TEK(hdr.getSWK_TEK());// �s�E�v
		headerDetail.setSWK_K_KAI_CODE(getCompanyCode());// �v���к���
		headerDetail.setSWK_TUKE_KBN(0);// ��Њԕt�֓`�[�敪
		headerDetail.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);// ���͏���Ŋz
		headerDetail.setSWK_KESI_KBN(0);// �����敪
		headerDetail.setDepartment(mainView.ctrlDepartment.getEntity()); // �v�㕔��
		headerDetail.setItem(mainView.ctrlItem.getEntity()); // ���Ȗ�
		headerDetail.setSWK_SEI_NO(mainView.ctrlEvidenceNo.getValue()); // ������No.
		headerDetail.setCurrency(mainView.ctrlCurrency.getEntity()); // �ʉ݃R�[�h
		headerDetail.setSWK_CUR_RATE(mainView.ctrlRate.getBigDecimal()); // ���[�g
		headerDetail.setSWK_IN_KIN(mainView.ctrlInputAmount.getBigDecimal()); // �O��
		headerDetail.setSWK_KIN(mainView.ctrlKeyAmount.getBigDecimal()); // �M��
		headerDetail.setHAS_DATE(mainView.ctrlSlipDate.getValue());// �������i�`�[���t���w��j
	}

	/**
	 * ��ʓ��͂̔��f(�p�^�[������)
	 */
	@Override
	protected void reflectPatternDetails() {

		SWK_HDR hdr = slip.getHeader();
		String slipRemarks = hdr.getSWK_TEK();

		slip.clearDetail();

		for (SWK_DTL dtl : mainView.ctrlDetail.getEntityList()) {

			if (this.headerDetail.equals(dtl)) {
				// �w�b�_�[���ׂ͏��O
				continue;
			}

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
	 * �`�[�p�^�[�������Ɣ��f
	 */
	@Override
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

			// ���[�g���M�݋��z���Ƃ��Ă���
			SWK_HDR hdr_ = slip.getHeader();
			BigDecimal patternRate = hdr_.getSWK_CUR_RATE();
			BigDecimal patternKin = hdr_.getSWK_KIN();
			BigDecimal patternInKin = null;
			if (DecimalUtil.isNullOrZero(patternKin) //
				&& hdr_.getSlipKind() == SlipKind.PURCHASE) {
				// ���`�[�͎x�����z�J����
				patternKin = DecimalUtil.avoidNull(hdr_.getSWK_SIHA_KIN());
			} else if (DecimalUtil.isNullOrZero(patternKin) //
				&& hdr_.getSlipKind() == SlipKind.EMPLOYEE) {
				// �o��`�[�͌o����z�J�����i�O�݊܂ށj
				patternKin = DecimalUtil.avoidNull(hdr_.getSWK_KEIHI_KIN());
				patternInKin = DecimalUtil.avoidNull(hdr_.getSWK_IN_SIHA_KIN());
			}

			// �V�K����
			slip = null;
			setSlipDate(slipDate); // ���t�͌��̂܂�
			
			if (!DecimalUtil.isNullOrZero(patternRate)) {
				// �p�^�[�����[�g��ZERO����Ȃ�������
				setPatternRate(patternRate);
				setPatternKin(patternKin);
				if (hdr_.getSlipKind() == SlipKind.EMPLOYEE) {
					// �o��Z�̏ꍇ�̂�
					setPatternInKin(patternInKin);
				}
			}
			mainView.ctrlSlipNo.clear();
			switchNew();

			return true;

		} catch (Exception ex) {
			errorHandler(ex);
			return false;
		}
	}

	/**
	 * �p�^�[���̃��[�g����ʂɃZ�b�g
	 * 
	 * @param rate
	 */
	protected void setPatternRate(BigDecimal rate) {
		mainView.ctrlRate.setNumberValue(rate);
	}

	/**
	 * �p�^�[���̖M�݋��z����ʂɃZ�b�g
	 * 
	 * @param kin
	 */
	protected void setPatternKin(BigDecimal kin) {
		mainView.ctrlKeyAmount.setNumberValue(kin);
		changedKeyAmount();
	}

	/**
	 * �p�^�[���̊O�݋��z����ʂɃZ�b�g
	 * 
	 * @param inKin
	 */
	protected void setPatternInKin(BigDecimal inKin) {
		// �o��Z�̂݁i�p���p�j
	}

	/**
	 * �`�[���t�̕ύX
	 */
	@Override
	protected void changedSlipDate() {
		super.changedSlipDate();

		// �`�[���t������Ƃ��ăZ�b�g
		Date slipDate = mainView.ctrlSlipDate.getValue();

		mainView.ctrlDepartment.getSearchCondition().setValidTerm(slipDate);
		mainView.ctrlItem.getSearchCondition().setValidTerm(slipDate);
		mainView.ctrlCurrency.getSearchCondition().setValidTerm(slipDate);

		// ���[�g�ύX
		BigDecimal old = mainView.ctrlRate.getBigDecimal();
		BigDecimal nuw = getCurrencyRate();

		if (nuw == null) {
			mainView.ctrlRate.clear();

		} else if (old.compareTo(nuw) != 0) {
			mainView.ctrlRate.setNumberValue(nuw);
			changedRate();
		}
	}

	/**
	 * �v�㕔��ύX
	 */
	protected void changedDepartment() {
		Department dept = mainView.ctrlDepartment.getEntity();
		setDepartment(dept);

		if (dept == null) {
			return;
		}

		// �Ȗڐ������`�F�b�N
		String nowCode = dept.getCode();
		String oldCode = mainView.ctrlItem.getSearchCondition().getDepartmentCode();

		if (!nowCode.equals(Util.avoidNull(oldCode))) {
			// ����R�[�h���ύX�ɂȂ����ꍇ�A������ύX
			mainView.ctrlItem.getSearchCondition().setDepartmentCode(nowCode);

			// �����ύX�ɂ��A�������`�F�b�N��OK�Ȃ�c��
			mainView.ctrlItem.ctrlItemReference.refleshEntity();
			changedItem();
		}
	}

	/**
	 * �v�㕔��ݒ�
	 * 
	 * @param dept �v�㕔��
	 */
	protected void setDepartment(Department dept) {
		mainView.ctrlDepartment.setEntity(dept);

		// ���ה��f
		headerDetail.setDepartment(dept);

		if (dept == null) {
			mainView.ctrlItem.setEntity(null);
			mainView.ctrlItem.ctrlItemReference.setEditable(false);
			changedItem();

			return;
		}

		// �Ȗڏ�����
		mainView.ctrlItem.ctrlItemReference.setEditable(true);
	}

	/**
	 * �ȖڕύX
	 */
	protected void changedItem() {
		Item item = mainView.ctrlItem.getEntity();

		// ���ה��f
		headerDetail.setItem(item);

		if (item == null) {
			clearInputForItem();
			changedCurrency();

			// �ʉݏ����l
			setCurrecy(keyCurrency);
			mainView.ctrlCurrency.setEditable(false);
			return;
		}

		// �⏕������
		if (item.getSubItem() != null) {
			item = item.getSubItem();
		}
		// ���󂪂���
		if (item.getDetailItem() != null) {
			item = item.getDetailItem();
		}

		// ���ʉݓ��̓t���O
		mainView.ctrlCurrency.setEditable(item.isUseForeignCurrency());
		if (!item.isUseForeignCurrency()) {
			setCurrecy(keyCurrency);
			changedCurrency();
		}
	}

	/**
	 * �Ȗڊ֘A���͕��̂ݏ������
	 */
	public void clearInputForItem() {
		// �N���A
		mainView.ctrlCurrency.clear();
		mainView.ctrlRate.clear();
		mainView.ctrlKeyAmount.clear();

		// ���͐���
		mainView.ctrlCurrency.setEditable(false);
		mainView.ctrlRate.setEditable(false);
		mainView.ctrlKeyAmount.setEditable(false);

		// �����l
		setCurrecy(keyCurrency);
	}

	/**
	 * �ʉݕύX
	 */
	protected void changedCurrency() {
		Currency currency = mainView.ctrlCurrency.getEntity();

		setCurrecy(currency);

		if (currency == null) {
			return;
		}

		// ���[�g
		mainView.ctrlRate.setNumberValue(getCurrencyRate());
		changedRate();
	}

	/**
	 * �ʉݐݒ�
	 * 
	 * @param currency �ʉ�
	 */
	protected void setCurrecy(Currency currency) {
		mainView.ctrlCurrency.setEntity(currency);

		// �w�b�_�[�ʉݘA�����[�h
		if (mainView.isUseHeaderDefaultCurreny()) {
			mainView.ctrlDetail.setCurrecy(currency);
		}

		// ���ה��f
		headerDetail.setCurrency(currency);

		mainView.ctrlDetail.makeCurrencyComboBox();

		if (currency == null) {
			mainView.ctrlRate.clear();
			mainView.ctrlRate.setEditable(false);
			mainView.ctrlKeyAmount.clear();
			mainView.ctrlKeyAmount.setEditable(false);
			changedKeyAmount();

			return;
		}

		// ���͐���
		boolean isKey = keyCurrency.getCode().equals(currency.getCode());
		mainView.ctrlRate.setEditable(!isKey);
		mainView.ctrlKeyAmount.setEditable(!isKey);

		// ���[�g
		mainView.ctrlRate.setNumberValue(isKey ? BigDecimal.ONE : null);
		changedRate();

		// �����_�ύX
		int digit = currency.getDecimalPoint();
		mainView.ctrlInputAmount.setDecimalPoint(digit);
		changedInputAmount();
	}

	/**
	 * �ʉ݃��[�g �ύX
	 */
	protected void changedRate() {
		// ���ה��f
		headerDetail.setSWK_CUR_RATE(mainView.ctrlRate.getBigDecimal());

		// �M�݊��Z
		Currency currency = mainView.ctrlCurrency.getEntity();

		if (currency == null || mainView.ctrlInputAmount.isEmpty()) {
			return;
		}

		BigDecimal inAmount = mainView.ctrlInputAmount.getBigDecimal();
		mainView.ctrlKeyAmount.setNumber(convertKeyAmount(inAmount));

		changedKeyAmount();
	}

	/**
	 * ���͋��z�̕ύX
	 */
	protected void changedInputAmount() {
		BigDecimal inAmount = mainView.ctrlInputAmount.getBigDecimal();
		mainView.ctrlKeyAmount.setNumber(convertKeyAmount(inAmount));

		// ���ה��f
		headerDetail.setSWK_IN_KIN(inAmount);

		changedKeyAmount();
	}

	/**
	 * ����z�̕ύX
	 */
	protected void changedKeyAmount() {
		// ���ה��f
		headerDetail.setSWK_KIN(mainView.ctrlKeyAmount.getBigDecimal());

		summary();
	}

	/**
	 * �ʉ݃��[�g�擾
	 * 
	 * @return ���[�g
	 */
	protected BigDecimal getCurrencyRate() {
		try {
			Currency currency = mainView.ctrlCurrency.getEntity();

			if (currency == null) {
				return null;
			}

			if (currency.getCode().equals(keyCurrency.getCode())) {
				return BigDecimal.ONE;
			}

			Date date = mainView.ctrlSlipDate.getValue();

			boolean isClosing = mainView.ctrlCloseEntry.isSelected();

			return (BigDecimal) request(RateManager.class, isClosing ? "getSettlementRate" : "getRate", currency, date);

		} catch (TException ex) {
			errorHandler(ex);
			return null;
		}
	}

	/**
	 * ��ʓ��͏������Ɋ���z�Ɋ��Z
	 * 
	 * @param inAmount ���͋��z
	 * @return ��ʉ݋��z
	 */
	protected BigDecimal convertKeyAmount(BigDecimal inAmount) {

		if (DecimalUtil.isNullOrZero(inAmount)) {
			return BigDecimal.ZERO;
		}

		BigDecimal rate = mainView.ctrlRate.getBigDecimal();
		Currency currency = mainView.ctrlCurrency.getEntity();

		if (currency == null || DecimalUtil.isNullOrZero(rate)) {
			return BigDecimal.ZERO;
		}

		// ���Z
		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(conf.getExchangeFraction());
		param.setConvertType(conf.getConvertType());
		param.setDigit(keyCurrency.getDecimalPoint());
		param.setForeignAmount(inAmount);
		param.setRate(rate);
		param.setRatePow(currency.getRatePow());

		return calculator.exchangeKeyAmount(param);
	}

	/**
	 * �M�݋��z���O�݋��z�Ɋ��Z
	 * 
	 * @param keyAmount �M�݋��z
	 * @return �O�݋��z
	 */
	protected BigDecimal convertForeignAmount(BigDecimal keyAmount) {

		if (DecimalUtil.isNullOrZero(keyAmount)) {
			return BigDecimal.ZERO;
		}

		BigDecimal rate = mainView.ctrlRate.getBigDecimal();
		Currency currency = mainView.ctrlCurrency.getEntity();

		if (currency == null || DecimalUtil.isNullOrZero(rate)) {
			return BigDecimal.ZERO;
		}

		// ���Z
		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(conf.getExchangeFraction());
		param.setConvertType(conf.getConvertType());
		param.setDigit(currency.getDecimalPoint());
		param.setKeyAmount(keyAmount);
		param.setRate(rate);
		param.setRatePow(currency.getRatePow());

		return calculator.exchangeForeignAmount(param);
	}
}
