package jp.co.ais.trans.common.bizui.ctrl;

import java.awt.event.*;
import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;

/**
 * ���Z�i�K�t�B�[���h�R���g���[��
 */
public class TSettlementSelectFieldCtrl extends TAppletClientBase {

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "InformationServlet";

	/** �p�l�� */
	private TSettlementSelectField panel;

	/** �ݒ萔�l */
	private String ksdKbn = "";

	/**
	 * �R���X�g���N�^
	 * 
	 * @param panel �p�l��
	 */
	public TSettlementSelectFieldCtrl(TSettlementSelectField panel) {
		try {
			this.panel = panel;

			initSettlementDiv();

			this.panel.numSettlementDivision.addFocusListener(new FocusAdapter() {

				public void focusLost(FocusEvent evt) {
					if (TSettlementSelectFieldCtrl.this.panel.rdoSettlement.isSelected()
						&& Util.isNullOrEmpty(TSettlementSelectFieldCtrl.this.panel.numSettlementDivision.getValue())) {

						// �i�K�\�����u�����N�̏ꍇ�A�ݒ萔�l��}��
						TSettlementSelectFieldCtrl.this.panel.numSettlementDivision.setValue(ksdKbn);
					}
				}
			});

		} catch (IOException ex) {
			errorHandler(this.panel, "E00009");
		}
	}

	/**
	 * ���Z���� �����l�FGL���۰�Ͻ�.���Z�i�K�敪
	 * 
	 * @return boolean
	 * @throws IOException
	 */
	private boolean initSettlementDiv() throws IOException {

		// FLAG
		addSendValues("FLAG", "KSD_KBN");

		if (!request(TARGET_SERVLET)) {
			errorHandler(this.panel);
			return false;
		}

		Map resultMap = getResult();

		// ���Z�i�K�敪���擾����
		ksdKbn = Util.avoidNull(resultMap.get("KSD_KBN"));

		// ���Z�i�K �u�����N/0(���g�p)�̏ꍇ�A�ʏ��I��
		panel.rdoNormally.setSelected(Util.isNullOrEmpty(ksdKbn) || "0".equals(ksdKbn));

		radioSettlementChange();

		return true;
	}

	/**
	 * ���Z���� 1�`9�܂œ��͉\�B���Z�敪���u�ʏ�v�̏ꍇ�A���͕s�B���Z�̏ꍇ�A�K�{�B
	 */
	public void radioSettlementChange() {
		boolean isSetSelect = panel.rdoSettlement.isSelected();
		panel.numSettlementDivision.setEditable(isSetSelect);
		panel.numSettlementDivision.setValue(isSetSelect ? ksdKbn : "");
	}
}
