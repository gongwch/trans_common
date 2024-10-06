package jp.co.ais.trans2.master.ui;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * ��s�����}�X�^��ʃR���g���[��<br>
 * IBAN�����ǉ���
 * 
 * @author AIS
 */
public class MP0031BankAccountMasterPanelCtrl extends MP0030BankAccountMasterPanelCtrl {

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	@Override
	protected void createMainView() {
		mainView = new MP0031BankAccountMasterPanel();
		addMainViewEvent();
	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	@Override
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MP0031BankAccountMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent();

	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ��s������Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ��s����
	 */
	@Override
	protected BankAccount getInputedData() {
		BankAccount bean = super.getInputedData();

		MP0031BankAccountMasterDialog dialog = (MP0031BankAccountMasterDialog) editView;

		// ��s���i�p���j
		bean.setBankNameE(dialog.ctrlBankNameE.getValue());

		// �x�X���i�p���j
		bean.setBranchNameE(dialog.ctrlBranchNameE.getValue());

		// IBAN�R�[�h
		bean.setIBan(dialog.ctrlIBan.getValue());

		// ��s���ʎq
		bean.setBankIndentify(dialog.ctrlBankIndentify.getValue());

		// �������ʎq
		bean.setBankAccountIndentify(dialog.ctrlBankAccountIndentify.getValue());

		// SWIFT�R�[�h
		bean.setSwift(dialog.ctrlSwift.getValue());
		
		// Bank Country
		bean.setBankCountry(dialog.ctrlBankCountry.getCode());

		// �Z��1
		bean.setBnkAdr1(dialog.ctrlBnkAdr1.getValue());

		// �Z��1�i�p���j
		bean.setBnkAdr1E(dialog.ctrlBnkAdr1E.getValue());

		// �Z��2
		bean.setBnkAdr2(dialog.ctrlBnkAdr2.getValue());

		// �Z��2�i�p���j
		bean.setBnkAdr2E(dialog.ctrlBnkAdr2E.getValue());

		return bean;
	}

	/**
	 * ��ʂɋ�s���������Z�b�g����
	 * 
	 * @param bean
	 */
	@Override
	protected void setEditDialog(BankAccount bean) {
		super.setEditDialog(bean);

		MP0031BankAccountMasterDialog dialog = (MP0031BankAccountMasterDialog) editView;

		// ��s���i�p���j
		dialog.ctrlBankNameE.setValue(bean.getBankNameE());

		// �x�X���i�p���j
		dialog.ctrlBranchNameE.setValue(bean.getBranchNameE());

		// IBAN�R�[�h
		dialog.ctrlIBan.setValue(bean.getIBan());

		// ��s���ʎq
		dialog.ctrlBankIndentify.setValue(bean.getBankIndentify());

		// �������ʎq
		dialog.ctrlBankAccountIndentify.setValue(bean.getBankAccountIndentify());

		// SWIFT�R�[�h
		dialog.ctrlSwift.setValue(bean.getSwift());
		
		// BANK COUNTRY
		dialog.ctrlBankCountry.code.setValue(bean.getBankCountry());
		dialog.ctrlBankCountry.refleshEntity();

		// �Z��1
		dialog.ctrlBnkAdr1.setValue(bean.getBnkAdr1());

		// �Z��1�i�p���j
		dialog.ctrlBnkAdr1E.setValue(bean.getBnkAdr1E());

		// �Z��2
		dialog.ctrlBnkAdr2.setValue(bean.getBnkAdr2());

		// �Z��2�i�p���j
		dialog.ctrlBnkAdr2E.setValue(bean.getBnkAdr2E());
	}

	/**
	 * �G���e�B�e�B���ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param bean �f�[�^
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ��s����
	 */
	@Override
	protected String[] getRowData(BankAccount bean) {

		List<String> list = new ArrayList<String>();

		list.add(bean.getCode());
		list.add(bean.getName());
		list.add(bean.getCurrencyCode());
		list.add(bean.getBankCode());
		list.add(bean.getBankName());
		list.add(bean.getBankNameE()); // ��s���i�p���j
		list.add(bean.getBranchCode());
		list.add(bean.getBranchName());
		list.add(bean.getBranchNameE()); // �x�X���i�p���j
		list.add(bean.getClientCode());
		list.add(bean.getClientName());
		list.add(bean.getClientNameJ());
		list.add(bean.getClientNameE());
		list.add(getWord(DepositKind.getDepositKindName(bean.getDepositKind())));
		list.add(bean.getAccountNo());
		list.add(bean.isUseEmployeePayment() ? getWord("C02149") : getWord("C02148"));
		list.add(bean.isUseExPayment() ? getWord("C02151") : getWord("C02150"));
		list.add(bean.getDepartmentCode());
		list.add(bean.getDepartmentNames());
		list.add(bean.getItemCode());
		list.add(bean.getItemNames());
		list.add(bean.getSubItemCode());
		list.add(bean.getSubItemNames());
		list.add(bean.getDetailItemCode());
		list.add(bean.getDetailItemNames());
		list.add(bean.getBankIndentify()); // ��s���ʎq
		list.add(bean.getBankAccountIndentify()); // �������ʎq
		list.add(bean.getIBan()); // IBAN�R�[�h
		list.add(bean.getSwift()); // SWIFT�R�[�h
		list.add(bean.getBankCountry()); // BANK COUNTRY
		list.add(bean.getBnkAdr1()); // �Z��1
		list.add(bean.getBnkAdr1E()); // �Z��1�i�p���j
		list.add(bean.getBnkAdr2()); // �Z��2
		list.add(bean.getBnkAdr2E()); // �Z��2�i�p���j
		list.add(DateUtil.toYMDString(bean.getDateFrom()));
		list.add(DateUtil.toYMDString(bean.getDateTo()));

		return list.toArray(new String[list.size()]);
	}
}
