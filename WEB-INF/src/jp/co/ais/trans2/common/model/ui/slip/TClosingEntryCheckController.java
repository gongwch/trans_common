package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.event.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.close.*;

/**
 * ���Z�d��`�F�b�N�R���g���[��
 */
public class TClosingEntryCheckController extends TController {

	/** �R���| */
	protected TClosingEntryCheck entryCheck;

	/** ���Z�i�K�����͉\(�`�F�b�N��) */
	protected boolean isEdit = false;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param entryCheck
	 */
	public TClosingEntryCheckController(TClosingEntryCheck entryCheck) {
		this.entryCheck = entryCheck;

		addEvent();

		init();
	}

	/**
	 * �C�x���g�o�^
	 */
	protected void addEvent() {
		entryCheck.chk.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				switchStage();
			}
		});

		entryCheck.ctrlSlipDate.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				switchStage();
			}
		});
	}

	/**
	 * ��������
	 */
	protected void init() {

		// ���p��/�s��
		boolean isView = true;

		isView &= getCompany().getFiscalPeriod().getMaxSettlementStage() != 0;

		// ���[�U���o���F�؎҂͕\��
		isView &= getUser().getUserAccountRole() == UserAccountRole.ACCOUNT;

		entryCheck.setVisible(isView);
	}

	/**
	 * ���Z�i�K�����͉\���ǂ���(�`�F�b�N��)
	 * 
	 * @param isEdit true:�\
	 */
	public void setEditMode(boolean isEdit) {
		this.isEdit = isEdit;

		switchStage();
	}

	/**
	 * ���Z�i�K�ύX
	 */
	protected void switchStage() {

		// ���͉\
		entryCheck.num.setEditable(isEdit && entryCheck.chk.isSelected());

		if (!entryCheck.chk.isSelected()) {
			entryCheck.num.clear();
			return;
		}

		Date slipDate = entryCheck.ctrlSlipDate.getValue();

		if (slipDate == null) {
			return; // NULL�������ǔO�̂��߂Ƀ`�F�b�N
		}

		FiscalPeriod period = getCompany().getFiscalPeriod();
		Date closedDate = period.getLastDateOfClosedPeriod();// ���ߌ�
		int stage = period.getSettlementStage(); // ���Z�i�K
		int max = period.getMaxSettlementStage();

		// ���ߌ��Ɠ����Ȃ猈�Z�i�K
		if (DateUtil.getYear(closedDate) == DateUtil.getYear(slipDate)
			&& DateUtil.getMonth(closedDate) == DateUtil.getMonth(slipDate)) {

			if (max < stage + 1) {
				// ����MAX�Œ��߂��Ă���
				entryCheck.num.setNumber(1);

			} else {
				entryCheck.num.setNumber(stage + 1);
			}

		} else {
			entryCheck.num.setNumber(1);
		}
	}

	/**
	 * ���Z�i�K�̍Đݒ�
	 */
	public void resetStage() {
		switchStage();
	}
}
