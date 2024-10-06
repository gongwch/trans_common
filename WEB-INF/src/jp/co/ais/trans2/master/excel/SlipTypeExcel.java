package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �`�[��ʃ}�X�^ �G�N�Z��
 */
public class SlipTypeExcel extends TExcel {

	/** �C���{�C�X���x�`�F�b�N���邩�ǂ��� true:�\������ */
	protected boolean isInvoice = false;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 * @param condition
	 */
	public SlipTypeExcel(String lang, SlipTypeSearchCondition condition) {
		super(lang);
		isInvoice = condition.isInvoiceFlg();
	}

	/**
	 * �`�[��ʈꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param sliptypeList �����ꗗ
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<SlipType> sliptypeList) throws TException {

		try {
			createReport(sliptypeList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����o�͂���
	 * 
	 * @param sliptypeList
	 */
	public void createReport(List<SlipType> sliptypeList) {

		// �V�[�g�ǉ�
		TExcelSheet sheet = addSheet(getWord("C00912"));// �`�[��ʃ}�X�^

		// �J�����ݒ�
		sheet.addColumn(getWord("C00837"), 4200);// �`�[��ʃR�[�h
		sheet.addColumn(getWord("C00980"), 4200);// �V�X�e���敪
		sheet.addColumn(getWord("C00838"), 6300);// �`�[��ʖ���
		sheet.addColumn(getWord("C00839"), 6300);// �`�[��ʗ���
		sheet.addColumn(getWord("C02757"), 8400);// ���[�^�C�g��
		sheet.addColumn(getWord("C02047"), 6300);// �f�[�^�敪
		sheet.addColumn(getWord("C01221"), 6300);// ���V�X�e���敪
		sheet.addColumn(getWord("C00392"), 8400);// �`�[�ԍ��̔ԃt���O
		sheet.addColumn(getWord("C11169"), 8400);// ���͒P��
		sheet.addColumn(getWord("C00287"), 4200);// ����Ōv�Z�敪
		sheet.addColumn(getWord("C00299"), 8400);// �d��C���^�[�t�F�[�X�敪
		sheet.addColumn(getWord("C11280"), 6200);// �U�ߓ`�[��ʃR�[�h
		sheet.addColumn(getWord("C11281"), 6200);// �U�ߓ`�[��ʖ���

		if (isInvoice) {
			sheet.addColumn(getWord("C12199"), 4500);// �C���{�C�X���x�`�F�b�N
		}

		// ���ו`��
		for (SlipType slipType : sliptypeList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(slipType.getCode());
			newRow.addCell(slipType.getSystemDiv());
			newRow.addCell(slipType.getName());
			newRow.addCell(slipType.getNames());
			newRow.addCell(slipType.getNamek());
			newRow
				.addCell(getWord(DataDivision.getDataDivisionName(DataDivision.getDataDivision(slipType.getDataType()))));
			newRow.addCell(getWord(slipType.getAnotherSystemDivisionName()));
			newRow.addCell(getWord(slipType.getSlipIndexDivisionName()));
			newRow.addCell(getWord(AcceptUnit.getAcceptUnitName(slipType.isAcceptUnit())));
			newRow.addCell(getWord(TaxCalcType.getTaxCalcTypeName(TaxCalcType.getTaxCulKbn(slipType
				.isInnerConsumptionTaxCalculation()))));
			newRow.addCell(getWord(SlipState.getSlipStateName(slipType.getSlipState())));

			if (slipType.getReversingSlipType() != null) {
				newRow.addCell(slipType.getReversingSlipType().getCode());
				newRow.addCell(slipType.getReversingSlipType().getName());
			} else {
				newRow.addEmptyCell();
				newRow.addEmptyCell();
			}

			if (isInvoice) {
				newRow.addCell(slipType.isINV_SYS_FLG() ? getWord("C02100") : getWord("C02099"));
			}

		}

	}
}
