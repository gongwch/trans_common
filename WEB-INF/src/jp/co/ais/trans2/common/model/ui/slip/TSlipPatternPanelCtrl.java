package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.security.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �p�^�[�����̓R���g���[��
 */
public abstract class TSlipPatternPanelCtrl extends TSlipPanelCtrl {

	/**
	 * ��ʃw�b�_�̏����ݒ�<br>
	 * ���t�n�Ȃ�
	 */
	@Override
	protected void initHeaderView() {
		// �V�K���[�h
		switchNew();

		// �`�[�E�v
		mainView.ctrlSlipRemark.getSearchCondition().setSlipRemark(true);
		mainView.ctrlSlipRemark.getSearchCondition().setSlipRowRemark(false);
		mainView.ctrlSlipRemark.getSearchCondition().setDataType(slipType.getDataType()); // �f�[�^�敪
	}
	
	/**
	 * invoice�g�p���邩�ǂ���(�p�^�[����false)
	 */
	@Override
	protected void initInvoiceFlg() {

		isInvoice = false;
	}

	/**
	 * ��ʖ��ׂ̏����ݒ�<br>
	 * ���t�n�Ȃ�
	 */
	@Override
	protected void initDetailView() {
		// �v���O�������
		mainView.ctrlDetail.getController().setProgramInfo(getProgramInfo());

		// �X�v���b�h��ԕۑ��L�[
		mainView.ctrlDetail.setTableKeyName(getTableKeyName());

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
	 * �l�N���A<br>
	 * ���t�n�Ȃ�
	 */
	@Override
	protected void clearComponents() {
		// �w�b�_
		mainView.ctrlSlipNo.clear(); // �`�[�ԍ�
		mainView.ctrlEvidenceNo.clear(); // �؜ߔԍ�
		mainView.ctrlSlipRemark.clear(); // �`�[�E�v
		mainView.ctrlPrintStop.clear(); // �`�[�����~
		mainView.ctrlCloseEntry.clear(); // ���Z�d��

		// ����
		mainView.ctrlDetail.init();

		// �p�^�[���ԍ����͉�
		mainView.ctrlSlipNo.setEditable(true);
	}

	/**
	 * �����t�H�[�J�X
	 */
	@Override
	protected void requestFocusFirst() {
		mainView.ctrlCloseEntry.chk.requestFocus();
	}

	/**
	 * �`�[�����Ɣ��f<br>
	 * �Ăяo���̂̓p�^�[��
	 * 
	 * @param isModify true:�C�� false:����
	 * @return true:����
	 */
	@Override
	protected boolean searchSlipAddResult(boolean isModify) {

		try {
			// ����
			TSlipPatternSearchCtrl ctrl = createPatternSearchCtrl();

			if (!isModify) {
				// ���ʃ��[�h
				ctrl.switchSelfOnly(); // ���g�̃f�[�^�̂݌Ăяo��
				ctrl.setIncludeLocked(true); // �r���܂ނ�
			}

			if (ctrl.show() != TSlipSearchCtrl.OK_OPTION) {
				mainView.btnPattern.requestFocus();
				return false;
			}

			// ��U�N���A
			clearView();

			// ���f
			SWK_HDR hdr = ctrl.getSelectedRow();

			// �`�[�\�z
			slip = (Slip) request(getManagerClass(), "getPatternSlip", hdr, isModify);

			// ��ʔ��f
			dispatch();

			if (isModify) {
				// �C��
				switchModify();

			} else {
				// ����(�V�K����)
				slip = null;
				mainView.ctrlSlipNo.clear();

				switchNew();
			}

			mainView.ctrlSlipNo.setEditable(!isModify);

			return true;

		} catch (Exception ex) {
			errorHandler(ex);
			return false;
		}
	}

	/**
	 * �`�[�m��
	 */
	@Override
	public void doEntry() {
		try {
			// ���̓`�F�b�N
			if (!checkInput()) {
				return;
			}

			if (!showConfirmMessage("Q00004")) {// �m�肵�܂����H
				return;
			}

			mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			// �`�[�ɔ��f
			reflectSlip();

			// �o�^
			request(getManagerClass(), "entryPattern", slip);

			clearView();

			showMessage("I00013");// ����ɏ��������s����܂����B

		} catch (Exception ex) {
			errorHandler(ex);

		} finally {
			mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	/**
	 * �`�[�폜
	 */
	@Override
	public void doDelete() {
		try {
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜
			request(getManagerClass(), "deletePattern", slip);

			// �N���A
			clearView();

			showMessage("I00013");// ����ɏ��������s����܂����B

		} catch (Exception ex) {
			errorHandler(ex);
		}
	}

	/**
	 * �`�[���t�֘A�̃`�F�b�N
	 * 
	 * @return false:NG
	 */
	@Override
	protected boolean checkSlipDate() {
		// �Ȃ�
		return true;
	}

	/**
	 * �w�b�_�[���ڂ̃`�F�b�N
	 * 
	 * @return false:NG
	 * @throws TException
	 */
	@Override
	protected boolean checkHeaderInput() throws TException {
		// �E�v�R�[�h���̓`�F�b�N
		if (!checkInputBlank(mainView.ctrlSlipNo.getField(), "C00987")) {// �p�^�[���ԍ�
			return false;
		}

		// �V�K�̏ꍇ�A�R�[�h���݃`�F�b�N
		if (!mainView.isModifyMode()) {
			Boolean exists = (Boolean) request(getManagerClass(), "existsPattern", mainView.ctrlSlipNo.getValue());
			if (exists) {
				showMessage("I00138");// �w��̃p�^�[���ԍ��͊��ɑ��݂��܂��B
				mainView.ctrlSlipNo.requestFocus();
				return false;
			}
		}

		return true;
	}

	/**
	 * ���׍��ڂ̃`�F�b�N
	 * 
	 * @return false:NG
	 */
	@Override
	protected boolean checkDetailInput() {
		// �Ȃ�
		return true;
	}

	/**
	 * ��ʓ��͂̔��f(�w�b�_)
	 * 
	 * @param hdr �w�b�_
	 */
	@Override
	protected void reflectHeader(SWK_HDR hdr) {
		super.reflectHeader(hdr);

		// ���t�������̂Ō��Z�`�[�̏ꍇ�͎�����1��
		if (mainView.ctrlCloseEntry.isSelected()) {
			hdr.setSWK_KSN_KBN(1);
		}
	}

	/**
	 * �ב֍����v�s�ǉ�
	 * 
	 * @param slip_ �Ώ�
	 * @return �ב֍����v�s�ǉ���
	 */
	@Override
	protected Slip addLossOrProfit(Slip slip_) {
		// �����v�s�͒ǉ����Ȃ�
		return slip_;
	}

	/**
	 * ������̖���(�s�v)
	 * 
	 * @return ����
	 */
	@Override
	protected String getPrintName() {
		return "";
	}

	/**
	 * �p�^�[���p
	 * 
	 * @see jp.co.ais.trans2.model.security.TExclusive#getExclusiveControlMethod()
	 */
	@Override
	public TExclusiveControlMethod getExclusiveControlMethod() {
		return new SlipPatternExclusiveControlMethod(this.getProgramInfo(), getSlipTypeNo());
	}

	/**
	 * �r������(�S��)
	 */
	@Override
	protected void unlockAll() {
		try {
			// �r������
			request(getManagerClass(), "unlockPatternAll", slipType.getCode());

		} catch (TException ex) {
			errorHandler(ex);
		}
	}

	/**
	 * �r������(��)
	 */
	@Override
	protected void unlock() {
		try {
			if (slip != null && !Util.isNullOrEmpty(slip.getSlipNo())) {
				// �`�[�w��̔r������
				request(getManagerClass(), "unlockPattern", slip);

			} else {
				unlockAll();
			}

		} catch (TException ex) {
			errorHandler(ex);
		}
	}

}
