package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.code.*;

/**
 * �R�[�h�R���{�{�b�N�X�̃R���g���[��
 * 
 * @author AIS
 */
public class TCodeComboBoxController extends TController {

	/** �R���{�{�b�N�X */
	protected TCodeComboBox comboBox;

	/** OP_CODE_MST���X�g */
	protected List<OP_CODE_MST> codeList;

	/**
	 * @param comboBox �t�B�[���h
	 */
	public TCodeComboBoxController(TCodeComboBox comboBox) {

		this.comboBox = comboBox;

		init();

	}

	/**
	 * ������
	 */
	protected void init() {

		// �R���{�{�b�N�X����
		getList();

	}

	/**
	 * �R���{�{�b�N�X�̃��X�g��Ԃ�
	 */
	protected void getList() {

		try {

			comboBox.getComboBox().removeAllItems();

			List<OP_CODE_MST> list = OPLoginUtil.getCodeMstList(comboBox.isLocal(), comboBox.getCodeDivision(),
				comboBox.getCodes());

			if (this.comboBox.isAddBlank()) {
				comboBox.getComboBox().addTextValueItem(null, "");
			}
			if (list != null) {
				for (OP_CODE_MST bean : list) {
					addItem(bean);
				}
			}
			// �ޔ�
			codeList = list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * �R���{�{�b�N�X�I������ǉ�����
	 * 
	 * @param bean
	 */
	protected void addItem(OP_CODE_MST bean) {
		comboBox.getComboBox().addTextValueItem(bean, bean.getCODE_NAME());
	}

	/**
	 * �R�[�h����R���{�{�b�N�X�I������ǉ�����<br>
	 * �Y���R�[�h���񑶍݂̏ꍇ�ǉ����Ȃ�
	 * 
	 * @param code
	 */
	public void addItem(String code) {
		OP_CODE_MST bean = OPLoginUtil.getCodeMst(comboBox.isLocal(), comboBox.getCodeDivision(), code);
		if (bean != null) {
			addItem(bean);
		}
	}

	/**
	 * @return Servlet��Class��Ԃ�
	 */
	protected Class getModelClass() {
		return CodeManager.class;
	}

	/**
	 * @see jp.co.ais.trans.common.client.TPanelCtrlBase#getPanel()
	 */
	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * OP�R�[�h����Ԃ�
	 * 
	 * @param code �R�[�h
	 * @return OP_CODE_MST
	 */
	protected OP_CODE_MST getCodeMst(String code) {
		if (codeList == null) {
			return null;
		}
		if (comboBox.isLocal()) {
			for (OP_CODE_MST bean : codeList) {
				if (bean.getCODE_NAME().equals(Util.avoidNull(code))) {
					return bean;
				}
			}
		}
		
		for (OP_CODE_MST bean : codeList) {
			if (bean.getCODE().equals(Util.avoidNull(code))) {
				return bean;
			}
		}

		return null;
	}

}
