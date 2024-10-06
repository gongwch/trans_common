package jp.co.ais.trans2.common.model.ui;

import static jp.co.ais.trans2.define.DepositKind.*;

import java.util.*;

import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.define.*;

/**
 * �a����ڑI���R���{�{�b�N�X�R���g���[���[
 *
 * @author AIS
 */
public class TDepositKindComboBoxController extends TController {

	/** �R���{�{�b�N�X */
	public TDepositKindComboBox cmbo;

	/** �a����ڍ��ڃ��X�g */
	public List<DepositKindElement> list;

	/**
	 * �R���X�g���N�^
	 *
	 * @param cmbo �R���{�{�b�N�X
	 */
	public TDepositKindComboBoxController(TDepositKindComboBox cmbo) {
		super();
		this.cmbo = cmbo;
		setSortList();
	}

	/**
	 * �a����ڍ��ڃ��X�g��ݒ�
	 */
	public void setSortList() {

		list = new ArrayList<DepositKindElement>();
		list.add(new DepositKindElement("", null));
		list.add(new DepositKindElement(TModelUIUtil.getShortWord(getDepositKindName(ORDINARY)), ORDINARY));
		list.add(new DepositKindElement(TModelUIUtil.getShortWord(getDepositKindName(CURRENT)), CURRENT));
		list.add(new DepositKindElement(TModelUIUtil.getShortWord(getDepositKindName(FOREIGN_CURRENCY)), FOREIGN_CURRENCY));
		list.add(new DepositKindElement(TModelUIUtil.getShortWord(getDepositKindName(SAVINGS)), SAVINGS));
		list.add(new DepositKindElement(TModelUIUtil.getShortWord(getDepositKindName(FIXED)), FIXED));

		for (DepositKindElement ele : list) {
			cmbo.getComboBox().addItem(ele.getName());
		}
	}

	/**
	 * �I�����ꂽ�a����ڂ�Ԃ�
	 *
	 * @return DepositKind
	 */
	public DepositKind getSelectedDepositKind() {
		return list.get(cmbo.getSelectedIndex()).getDepositKind();
	}

	/**
	 * �a����ڂ��Z�b�g����
	 *
	 * @param depositKind
	 */
	public void setSelectedDepositKind(DepositKind depositKind) {

		if (depositKind == null) {
			return;
		}

		int i = 0;
		for (DepositKindElement ele : list) {
			if (depositKind.equals(ele.getDepositKind())) {
				cmbo.setSelectedIndex(i);
				return;
			}
			i++;
		}
	}

	/** �a����ڍ��� */
	protected class DepositKindElement {

		/** ���� */
		protected String name;

		/** �a����� */
		protected DepositKind depositKind;

		/**
		 * �R���X�g���N�^
		 *
		 * @param name ����
		 * @param depositKind �a�����
		 */
		public DepositKindElement(String name, DepositKind depositKind) {
			this.name = name;
			this.depositKind = depositKind;
		}

		/**
		 * �a�����
		 *
		 * @return �a�����
		 */
		public DepositKind getDepositKind() {
			return depositKind;
		}

		/**
		 * �a�����
		 *
		 * @param depositKind �a�����
		 */
		public void setDepositKind(DepositKind depositKind) {
			this.depositKind = depositKind;
		}

		/**
		 * ����
		 *
		 * @return ����
		 */
		public String getName() {
			return name;
		}

		/**
		 * ����
		 *
		 * @param name ����
		 */
		public void setName(String name) {
			this.name = name;
		}
	}
}
