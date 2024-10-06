package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.history.*;
import jp.co.ais.trans2.model.history.ApproveHistory.SYO_FLG;

/**
 * ���F�����_�C�A���OCtrl
 */
public class TApproveHistoryDialogCtrl extends TController {

	/** ���F�����_�C�A���O */
	protected TApproveHistoryDialog dialog = null;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param dialog
	 */
	public TApproveHistoryDialogCtrl(TApproveHistoryDialog dialog) {
		this.dialog = dialog;

		addEvents();
	}

	/**
	 * �C�x���g�ǉ�
	 */
	protected void addEvents() {

		dialog.btnReturn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});
	}

	/**
	 * �f�[�^�ݒ�
	 * 
	 * @param list
	 */
	public void setData(List<ApproveHistory> list) {

		dialog.tbl.removeRow();

		if (list == null) {
			return;
		}

		for (ApproveHistory bean : list) {
			dialog.tbl.addRow(getRow(bean));
		}

	}

	/**
	 * @param bean
	 * @return �s�f�[�^
	 */
	protected List getRow(ApproveHistory bean) {

		String syoFlag = "";

		switch (bean.getSYO_FLG()) {
			case SYO_FLG.APPROVE:
				syoFlag = getWord("C01168"); // 0:���F
				break;
			case SYO_FLG.DENY:
				syoFlag = getWord("C00447"); // 1:�۔F
				break;
			case SYO_FLG.CANCEL:
				syoFlag = getWord("C00405"); // 2:���
				break;
			case SYO_FLG.ENTRY:
				syoFlag = getWord("C01258"); // 3:�o�^
				break;
			case SYO_FLG.MODIFY:
				syoFlag = getWord("C01760"); // 4:�C��
				break;
			case SYO_FLG.UPDATE:
				syoFlag = getWord("C00169"); // 5:�X�V
				break;
		}

		List<Object> list = new ArrayList<Object>();
		list.add(bean.getKAI_CODE());
		list.add(DateUtil.toYMDString(bean.getSWK_DEN_DATE()));
		list.add(bean.getSWK_DEN_NO());
		list.add(getWord(SlipState.getSlipStateName(bean.getSWK_UPD_KBN())));
		list.add(bean.getEMP_CODE());
		list.add(bean.getEMP_NAME());
		list.add(bean.getEMP_NAME_S());
		list.add(getWord(syoFlag));
		list.add(DateUtil.toYMDHMSString(bean.getINP_DATE()));
		return list;
	}
}
