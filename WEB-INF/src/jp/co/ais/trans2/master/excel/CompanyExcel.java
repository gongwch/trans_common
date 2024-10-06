package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.common.model.format.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.close.*;
import jp.co.ais.trans2.model.company.*;

/**
 * ��ЃR���g���[���}�X�^�}�X�^�G�N�Z��
 * 
 * @author AIS
 */
public class CompanyExcel extends TExcel {

	/** �������� */
	protected CompanySearchCondition condition = null;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 * @param condition
	 */
	public CompanyExcel(String lang, CompanySearchCondition condition) {
		super(lang);

		this.condition = condition;
	}

	/**
	 * �ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param list
	 * @return �G�N�Z��
	 * @throws TException
	 */
	public byte[] getExcel(List<Company> list) throws TException {

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
	public void createReport(List<Company> list) {

		// �V�[�g�ǉ�
		// ��ЃR���g���[���}�X�^
		TExcelSheet sheet = addSheet(getWord("C00910"));

		// �J�����ݒ�
		// ��ЃR�[�h
		sheet.addColumn(getWord("C00596"), 4200);
		// ��Ж���
		sheet.addColumn(getWord("C00685"), 12600);
		// ��З���
		sheet.addColumn(getWord("C00686"), 8400);
		// �Z��1
		sheet.addColumn(getWord("C00687"), 12600);
		// �Z��2
		sheet.addColumn(getWord("C00688"), 12600);
		// �Z���J�i
		sheet.addColumn(getWord("C01152"), 12600);
		// �X�֔ԍ�
		sheet.addColumn(getWord("C00527"), 4200);
		// �d�b�ԍ�
		sheet.addColumn(getWord("C00393"), 4200);
		// FAX�ԍ�
		sheet.addColumn(getWord("C00690"), 4200);
		if (condition.isInvoiceFlg()) {
			// �K�i���������s���Ǝғo�^�ԍ�
			sheet.addColumn(getWord("C12171"), 8000);
		}
		// �J�n�N����
		sheet.addColumn(getWord("C00055"), 4200);
		// �I���N����
		sheet.addColumn(getWord("C00261"), 4200);
		// �Ȗږ���
		sheet.addColumn(getWord("C00700"), 8400);
		// �⏕�Ȗږ���
		sheet.addColumn(getWord("C00701"), 8400);
		// ����Ȗږ���
		sheet.addColumn(getWord("C00702"), 8400);
		// �Ǘ��敪1
		sheet.addColumn(getWord("C02359"), 8400);
		// �Ǘ��敪2
		sheet.addColumn(getWord("C02360"), 8400);
		// �Ǘ��敪3
		sheet.addColumn(getWord("C02361"), 8400);
		// �Ǘ��敪4
		sheet.addColumn(getWord("C02362"), 8400);
		// �Ǘ��敪5
		sheet.addColumn(getWord("C02363"), 8400);
		// �Ǘ��敪6
		sheet.addColumn(getWord("C02364"), 8400);
		// ���v���׋敪1
		sheet.addColumn(getWord("C00943"), 8400);
		// ���v���ז���1
		sheet.addColumn(getWord("C00709"), 8400);
		// ���v���׋敪2
		sheet.addColumn(getWord("C00944"), 8400);
		// ���v���ז���2
		sheet.addColumn(getWord("C00710"), 8400);
		// ���v���׋敪3
		sheet.addColumn(getWord("C00945"), 8400);
		// ���v���ז���3
		sheet.addColumn(getWord("C00711"), 8400);
		// �����̔ԕ�����
		sheet.addColumn(getWord("C00224"), 4200);
		// �����ݒ荀��1
		sheet.addColumn(getWord("C01110"), 4200);
		// �����ݒ荀��2
		sheet.addColumn(getWord("C01111"), 4200);
		// �����ݒ荀��3
		sheet.addColumn(getWord("C01112"), 4200);
		// ���[�g���Z�[������
		sheet.addColumn(getWord("C00557"), 8400);
		// �������Œ[������
		sheet.addColumn(getWord("C00082"), 8400);
		// ��������Œ[������
		sheet.addColumn(getWord("C00083"), 8400);
		// �`�[����敪
		sheet.addColumn(getWord("C01248"), 4200);
		// ������̏����l
		sheet.addColumn(getWord("C01000"), 4200);
		// ���Z�敪
		sheet.addColumn(getWord("C00897"), 4200);
		// ���Z�i�K�敪
		sheet.addColumn(getWord("C00145"), 4200);
		// ���Z�`�[���͋敪
		sheet.addColumn(getWord("C01056"), 8400);
		// �]���փ��[�g�敪
		sheet.addColumn(getWord("C00454"), 8400);
		// ���Ԍ��Z�J�z�敪
		sheet.addColumn(getWord("C11141"), 8400);
		// ����
		sheet.addColumn(getWord("C00105"), 4200);
		// ���ꏳ�F
		sheet.addColumn(getWord("C00157"), 4200);
		// �o�����F
		sheet.addColumn(getWord("C01616"), 4200);
		// ��ʉ�
		sheet.addColumn(getWord("C00907"), 4200);
		// �@�\�ʉ�
		sheet.addColumn(getWord("C11084"), 4200);
		// �O���[�v��v�敪
		sheet.addColumn(getWord("C11142"), 8400);
		// IFRS�敪
		sheet.addColumn(getWord("C11143"), 4200);
		if (condition.isShowARSignerEng()) {
			// AR�F�p��������SIGNER�\����
			sheet.addColumn(getWord("C12175"), 12600);
		}

		if (condition.isInvoiceFlg()) {
			// �C���{�C�X���x
			sheet.addColumn(getWord("C12199"), 4500);
		}
		// �F�ݒ�
		sheet.addColumn(getWord("C11144"), 4200);

		if (condition.isShowInvoice()) {
			// INVOICE(��Њ�b���p���)--�ǉ�

			StringBuilder sb = new StringBuilder();
			sb.append(getWord("COP988"));
			sb.append(" ");

			// ��Ж���
			StringBuilder hdrName = new StringBuilder();
			hdrName.append(sb.toString());
			hdrName.append(getWord("C00685"));
			hdrName.append(getWord("C11896"));
			sheet.addColumn(hdrName.toString(), 12600);
			// ��З���
			hdrName = new StringBuilder();
			hdrName.append(sb.toString());
			hdrName.append(getWord("C00686"));
			hdrName.append(getWord("C11896"));
			sheet.addColumn(hdrName.toString(), 8400);
			// �Z��1
			hdrName = new StringBuilder();
			hdrName.append(sb.toString());
			hdrName.append(getWord("C11893"));
			sheet.addColumn(hdrName.toString(), 12600);
			// �Z��2
			hdrName = new StringBuilder();
			hdrName.append(sb.toString());
			hdrName.append(getWord("C11894"));
			sheet.addColumn(hdrName.toString(), 12600);
		}
		if (!condition.isNotShowSpc()) {
			// SPC��Џ��--�ǉ�

			// �A���p��ЃR�[�h
			sheet.addColumn(getWord("C11762"), 8400);
			// �T�C���ҁE��E��
			sheet.addColumn(getWord("C11763"), 8400);
			// �O�������S����
			sheet.addColumn(getWord("C11764"), 4200);
			// SPC�A���p�d�b�ԍ�
			sheet.addColumn(getWord("C11765"), 8400);
			// DEBIT NOTE��ЏZ��1
			sheet.addColumn(getWord("C11767"), 8400);
			// DEBIT NOTE��ЏZ��2
			sheet.addColumn(getWord("C11768"), 8400);
			// DEBIT NOTE��ЏZ��3
			sheet.addColumn(getWord("C11769"), 8400);
			// DEBIT NOTE�t�b�^���
			sheet.addColumn(getWord("C11770"), 8400);
		}

		// ���ו`��
		for (Company bean : list) {

			TExcelRow newRow = sheet.addRow();
			newRow.addCell(bean.getCode());
			newRow.addCell(bean.getName());
			newRow.addCell(bean.getNames());
			newRow.addCell(bean.getAddress1());
			newRow.addCell(bean.getAddress2());
			newRow.addCell(bean.getAddressKana());
			newRow.addCell(bean.getZip());
			newRow.addCell(bean.getPhoneNo());
			newRow.addCell(bean.getFaxNo());
			if (condition.isInvoiceFlg()) {
				newRow.addCell(bean.getInvRegNo());
			}
			newRow.addCell(bean.getDateFrom());
			newRow.addCell(bean.getDateTo());

			AccountConfig ac = bean.getAccountConfig();
			FiscalPeriod fp = bean.getFiscalPeriod();
			newRow.addCell(ac.getItemName());
			newRow.addCell(ac.getSubItemName());
			newRow.addCell(ac.getDetailItemName());
			newRow.addCell(ac.getManagement1Name());
			newRow.addCell(ac.getManagement2Name());
			newRow.addCell(ac.getManagement3Name());
			newRow.addCell(ac.getManagement4Name());
			newRow.addCell(ac.getManagement5Name());
			newRow.addCell(ac.getManagement6Name());

			newRow.addCell(getWord(NonAccountingDivision.getNonAccountName(ac.getNonAccounting1())),
				SwingConstants.CENTER);
			newRow.addCell(ac.getNonAccounting1Name());

			newRow.addCell(getWord(NonAccountingDivision.getNonAccountName(ac.getNonAccounting2())),
				SwingConstants.CENTER);
			newRow.addCell(ac.getNonAccounting2Name());

			newRow.addCell(getWord(NonAccountingDivision.getNonAccountName(ac.getNonAccounting3())),
				SwingConstants.CENTER);
			newRow.addCell(ac.getNonAccounting3Name());

			newRow.addCell(ac.getSlipNoDigit());
			newRow.addCell(getWord(SlipNoAdopt.getSlipNoAdoptName(ac.getSlipNoAdopt1())));

			newRow.addCell(getWord(SlipNoAdopt.getSlipNoAdoptName(ac.getSlipNoAdopt2())));

			newRow.addCell(getWord(SlipNoAdopt.getSlipNoAdoptName(ac.getSlipNoAdopt3())));

			if (ac.getExchangeFraction().getValue() == 0) {
				// �؂�̂�
				newRow.addCell(getWord("C01207"), SwingConstants.CENTER);
			} else {
				// �l�̌ܓ�
				newRow.addCell(getWord("C00215"), SwingConstants.CENTER);
			}

			if (ac.getReceivingFunction().getValue() == 0) {
				// �؂�̂�
				newRow.addCell(getWord("C01207"), SwingConstants.CENTER);
			} else if (ac.getReceivingFunction().getValue() == 1) {
				// �؂�グ
				newRow.addCell(getWord("C01208"), SwingConstants.CENTER);
			} else {
				// �l�̌ܓ�
				newRow.addCell(getWord("C00215"), SwingConstants.CENTER);
			}

			if (ac.getPaymentFunction().getValue() == 0) {
				// �؂�̂�
				newRow.addCell(getWord("C01207"), SwingConstants.CENTER);
			} else {
				// �l�̌ܓ�
				newRow.addCell(getWord("C00215"), SwingConstants.CENTER);
			}

			if (ac.isSlipPrint() == true) {
				// �g�p����
				newRow.addCell(getWord("C00281"), SwingConstants.CENTER);
			} else {
				// �g�p���Ȃ�
				newRow.addCell(getWord("C00282"), SwingConstants.CENTER);
			}

			if (ac.getSlipPrintDefault() == true) {
				// ON=�������
				newRow.addCell(getWord("C02368"), SwingConstants.CENTER);
			} else {
				// OFF=�����~
				newRow.addCell(getWord("C01001"), SwingConstants.CENTER);
			}

			if (ac.getConvertType().getValue() == 0) {
				// �|�Z
				newRow.addCell(getWord("C00065"), SwingConstants.CENTER);
			} else {
				// ���Z
				newRow.addCell(getWord("C00563"), SwingConstants.CENTER);
			}

			if (fp.getMaxSettlementStage() == 0) {
				// 0=�s��Ȃ�
				newRow.addCell(getWord("C00038"), SwingConstants.CENTER);
			} else {
				// >0=XX�����Z(�s��)
				String settlementLevel = FormatUtil.settlementLevelFormat(fp.getMaxSettlementStage(), lang, true);
				newRow.addCell(settlementLevel, SwingConstants.CENTER);
			}

			if (fp.getSettlementTerm().getValue() == 0) {
				// �N��
				newRow.addCell(getWord("C11145"), SwingConstants.CENTER);
			} else if (fp.getSettlementTerm().getValue() == 1) {
				// ����
				newRow.addCell(getWord("C00435"), SwingConstants.CENTER);
			} else if (fp.getSettlementTerm().getValue() == 2) {
				// �l����
				newRow.addCell(getWord("C10592"), SwingConstants.CENTER);
			} else {
				// ����
				newRow.addCell(getWord("C00147"), SwingConstants.CENTER);
			}

			if (ac.getEvaluationRateDate().getValue() == 0) {
				// ��������
				newRow.addCell(getWord("C11146"), SwingConstants.CENTER);
			} else {
				// ��������
				newRow.addCell(getWord("C11147"), SwingConstants.CENTER);
			}

			if (ac.isCarryJournalOfMidtermClosingForward()) {
				// �J��z��
				newRow.addCell(getWord("C11148"), SwingConstants.CENTER);
			} else {
				// �J��z���Ȃ�
				newRow.addCell(getWord("C11218"), SwingConstants.CENTER);
			}

			// ��
			newRow.addCell(bean.getFiscalPeriod().getMonthBeginningOfPeriod() + getWord("C02782"),
				SwingConstants.CENTER);

			if (ac.isUseFieldApprove() == true) {
				// ���F����
				newRow.addCell(getWord("C00283"), SwingConstants.CENTER);
			} else {
				// ���F���Ȃ�
				newRow.addCell(getWord("C11149"), SwingConstants.CENTER);
			}

			if (ac.isUseApprove() == true) {
				// ���F����
				newRow.addCell(getWord("C00283"), SwingConstants.CENTER);
			} else {
				// ���F���Ȃ�
				newRow.addCell(getWord("C11149"), SwingConstants.CENTER);
			}

			newRow.addCell(ac.getKeyCurrency().getCode(), SwingConstants.CENTER);
			newRow.addCell(ac.getFunctionalCurrency().getCode(), SwingConstants.CENTER);

			if (ac.isUseGroupAccount() == true) {
				// �g�p����
				newRow.addCell(getWord("C00281"), SwingConstants.CENTER);
			} else {
				// �g�p���Ȃ�
				newRow.addCell(getWord("C00282"), SwingConstants.CENTER);
			}

			if (ac.isUseIfrs() == true) {
				// �g�p����
				newRow.addCell(getWord("C00281"), SwingConstants.CENTER);
			} else {
				// �g�p���Ȃ�
				newRow.addCell(getWord("C00282"), SwingConstants.CENTER);
			}
			if (condition.isShowARSignerEng()) {
				// AR�F�p��������SIGNER�\����
				newRow.addCell(bean.getAR_SIGN_NAME());
			}

			if (condition.isInvoiceFlg()) {
				// �C���{�C�X���x
				newRow.addCell(bean.isCMP_INV_CHK_FLG() ? getWord("C00281") : getWord("C00282"), SwingConstants.CENTER);
			}

			newRow.addCell(Util.to16RGBColorCode(bean.getColor()));

			if (condition.isShowInvoice()) {
				// INVOICE(��Њ�b���p���)--�ǉ�
				newRow.addCell(bean.getKAI_NAME_ENG());
				newRow.addCell(bean.getKAI_NAME_S_ENG());
				newRow.addCell(bean.getJYU_1_ENG());
				newRow.addCell(bean.getJYU_2_ENG());
			}
			if (!condition.isNotShowSpc()) {
				// SPC��Џ��--�ǉ�
				newRow.addCell(bean.getConnCompanyCode());
				newRow.addCell(bean.getSignerPosition());
				newRow.addCell(bean.getRemitterName());
				newRow.addCell(bean.getConnPhoneNo());
				newRow.addCell(bean.getDebitNoteAddress1());
				newRow.addCell(bean.getDebitNoteAddress2());
				newRow.addCell(bean.getDebitNoteAddress3());
				newRow.addCell(bean.getDebitNoteInfo());
			}

		}

	}
}
