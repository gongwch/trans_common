package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * �x�����@�G�N�Z��
 * 
 * @author AIS
 */
public class PaymentMethodExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public PaymentMethodExcel(String lang) {
		super(lang);
	}

	/**
	 * ���[��Ԃ�
	 * 
	 * @param list
	 * @return ���[
	 * @throws TException
	 */
	public byte[] getExcel(List<PaymentMethod> list) throws TException {

		try {
			createReport(list);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����o�͂���
	 * 
	 * @param list
	 */
	public void createReport(List<PaymentMethod> list) {

		TExcelSheet sheet = addSheet(getWord("C02350")); // �x�����@�}�X�^

		// �x�����@�R�[�h
		sheet.addColumn(getWord("C00864"), 4000);
		// �x�����@����
		sheet.addColumn(getWord("C00865"), 8000);
		// �x�����@����������
		sheet.addColumn(getWord("C00866"), 8000);
		// �Ȗ�
		sheet.addColumn(getWord("C00077"), 4000);
		// �⏕
		sheet.addColumn(getWord("C00488"), 4000);
		// ����
		sheet.addColumn(getWord("C00025"), 4000);
		// �v�㕔��R�[�h
		sheet.addColumn(getWord("C00571"), 4000);
		// �x���Ώۋ敪
		sheet.addColumn(getWord("C01096"), 5000);
		// �x�������R�[�h
		sheet.addColumn(getWord("C01097"), 5000);
		// �J�n�N����
		sheet.addColumn(getWord("C00055"), 5000);
		// �I���N����
		sheet.addColumn(getWord("C00261"), 5000);
		// �t�H�[�}�b�g

		// ���ו`��
		for (PaymentMethod bean : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(bean.getCode());
			newRow.addCell(bean.getName());
			newRow.addCell(bean.getNamek());

			newRow.addCell(bean.getItemCode());
			newRow.addCell(bean.getSubItemCode());
			newRow.addCell(bean.getDetailItemCode());
			newRow.addCell(bean.getDepartmentCode());

			newRow.addCell(bean.isUseEmployeePayment() ? getWord("C01119") : getWord("C01124"), SwingConstants.CENTER);
			newRow.addCell(getWord(PaymentKind.getPaymentKindName(bean.getPaymentKind())), SwingConstants.CENTER);

			newRow.addCell(DateUtil.toYMDString(bean.getDateFrom()), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(bean.getDateTo()), SwingConstants.CENTER);

		}

	}
}
