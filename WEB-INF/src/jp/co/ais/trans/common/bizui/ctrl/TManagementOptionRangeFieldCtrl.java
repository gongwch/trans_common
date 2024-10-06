package jp.co.ais.trans.common.bizui.ctrl;

import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.info.*;

/**
 * �Ǘ��R���|�[�l���g�R���g���[��
 */
public class TManagementOptionRangeFieldCtrl extends TAppletClientBase {

	/** �t�B�[���h */
	protected TManagementOptionRangeField managementField;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param managementField �t�B�[���h
	 */
	public TManagementOptionRangeFieldCtrl(TManagementOptionRangeField managementField) {
		this.managementField = managementField;
		// �Ǘ�DROP���쐬����
		createManagementDrop();
	}

	/**
	 * �Ǘ������쐬����
	 */
	protected void createManagementDrop() {
		// ��Џ��擾
		TCompanyInfo compInfo = TClientLoginInfo.getInstance().getCompanyInfo();

		// �Ǘ��敪�\��
		boolean[] mngViews = compInfo.isUseManageDivs();
		String[] mngNames = compInfo.getManageDivNames();

		Map<Object, String> map = new TreeMap<Object, String>();

		// �����Ȃ�
		map.put(0, String.valueOf(getWord("C01748")));
		// �����
		map.put(1, String.valueOf(getWord("C00408")));
		// �Ј�
		map.put(2, String.valueOf(getWord("C00246")));
		// �Ǘ�1
		if (mngViews[0]) {
			map.put(3, mngNames[0]);
		}
		// �Ǘ�2
		if (mngViews[1]) {
			map.put(4, mngNames[1]);
		}
		// �Ǘ�3
		if (mngViews[2]) {
			map.put(5, mngNames[2]);
		}
		// �Ǘ�4
		if (mngViews[3]) {
			map.put(6, mngNames[3]);
		}
		// �Ǘ�5
		if (mngViews[4]) {
			map.put(7, mngNames[4]);
		}
		// �Ǘ�6
		if (mngViews[5]) {
			map.put(8, mngNames[5]);
		}

		managementField.getCmbManagement().setModel(map);

		// �����Ȃ���I������
		managementField.getCmbManagement().setSelectedIndex(0);
	}
}
