package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.model.close.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.user.*;

/**
 * �`�[���t�ƌ��Z�i�K�R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TSlipDateSettlementStageController extends TController {

	/** �R���|�[�l���g */
	protected TSlipDateSettlementStage field;

	/** �Ώۉ�� */
	protected Company company = null;

	/** �N�[���[�U�[ */
	protected User user = null;

	/**
	 * @param field
	 */
	public TSlipDateSettlementStageController(TSlipDateSettlementStage field) {
		this.field = field;
		init();
		refresh();
	}

	/**
	 * ������
	 */
	protected void init() {
		company = getCompany();
		user = getUser();
	}

	/**
	 * ���t���b�V��
	 */
	protected void refresh() {

		// ���Z���g�p���Ȃ��ꍇ�A�܂��̓��[�U�[���o���S���ł͂Ȃ��ꍇ�A���Z�i�K�͔�\��
		FiscalPeriod fp = company.getFiscalPeriod();
		boolean isUseSettlement = (fp.getMaxSettlementStage() != 0);
		if (!user.isAccountant()) {
			isUseSettlement = false;
		}

		field.chkSettlementStage.setVisible(isUseSettlement);

	}

	/**
	 * �`�[�쐬�`�F�b�N
	 * 
	 * @return true�F����Afalse�F�ُ�
	 */
	public boolean canCreateSlip() {

		// �`�[���t�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(field.slipDate.getValue())) {
			showMessage(field, "I00037", "C00599");// �`�[���t����͂��Ă��������B
			field.slipDate.requestTextFocus();
			field.chkSettlementStage.setSelected(false);
			return false;
		}

		// �`�[���t�����Z�`�[���͉\���ł͖����ꍇ�G���[
		if (field.chkSettlementStage.isSelected() && !field.slipDate.isSettlementDate()) {
			showMessage(field, "I00045");// ���Z�d��͌��Z���̖����ł̂ݓ��͂ł��܂��B
			field.slipDate.requestTextFocus();
			field.chkSettlementStage.setSelected(false);
			return false;
		}

		// ���ɒ��߂��Ă���ꍇ�G���[
		if (field.slipDate.isClosed(company, field.chkSettlementStage.getStage())) {
			showMessage(field, "I00131");// �w��̓`�[���t�͒��߂��Ă��܂��B
			field.slipDate.requestTextFocus();
			field.chkSettlementStage.setSelected(false);
			return false;
		}

		// ���Ō����������s��ꂽ�ꍇ�G���[
		if (field.chkSettlementStage.isSelected()
			&& field.slipDate.isClosed(company, field.chkSettlementStage.getStage())) {
			showMessage(field, "I00132");// �����������s��ꂽ�ׁA���Z�i�K�ɕύX������܂��B
			field.slipDate.requestTextFocus();
			field.chkSettlementStage.setSelected(false);
			return false;
		}

		// ���Z�d��`�F�b�N
		if (field.chkSettlementStage.num.isEditable()) {

			// ���Z�i�K�̓��̓`�F�b�N
			if (field.chkSettlementStage.num.isEmpty()) {
				// ���Z�i�K����͂��Ă�������
				showMessage("I00037", "C00718");
				field.chkSettlementStage.num.requestFocus();
				return false;
			}

			// ���Z�i�K�͈̔̓`�F�b�N
			int stage = field.chkSettlementStage.num.getInt();
			int max = company.getFiscalPeriod().getMaxSettlementStage();

			if (stage <= 0 || max < stage) {
				// {0}��{1}�`{2}�͈̔͂Ŏw�肵�Ă�������
				showMessage("I00247", "C00718", 1, max);// ���Z�i�K

				field.chkSettlementStage.num.requestFocus();
				return false;
			}
		}

		return true;
	}

	/**
	 * ���Z�i�K��Ԃ�
	 * 
	 * @return ���Z�i��
	 */
	public int getSettlementStage() {
		if (field.chkSettlementStage.isSelected()) {
			return field.chkSettlementStage.getStage();
		}
		return 0;
	}

}
