package jp.co.ais.trans2.model.slip;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;

/**
 * �`�[��Book�ɃR���o�[�g����r�W�l�X���W�b�N
 * 
 * @author AIS
 */
public abstract class SlipToBookConverter extends TModel {

	/**
	 * Slip��Book�ɕϊ�����
	 * 
	 * @param slip �`�[
	 * @return SlipBook
	 */
	public SlipBook toBook(Slip slip) {

		try {

			CompanyManager cm = (CompanyManager) getComponent(CompanyManager.class);
			Company company = cm.get(slip.getCompanyCode());

			return toBook(slip, company);

		} catch (TException e) {
			return null;
		}

	}

	/**
	 * Slip��Book�ɕϊ�����
	 * 
	 * @param slip
	 * @param company
	 * @return SlipBook
	 */
	public SlipBook toBook(Slip slip, Company company) {

		// book����
		SlipBook book = createSlipBook();
		book.setCompany(company);

		// �w�b�_�[����
		SlipBookHeader header = getSlipBookHeader(slip);
		book.addHeader(header);

		// ���גǉ�
		int rowNo = 1;
		for (SWK_DTL row : slip.getDetails()) {
			SlipBookDetail detail = createSlipBookDetail();
			detail.setSlipDetail(row);
			detail.setRowNo(rowNo);
			book.addDetail(detail);
			rowNo++;
		}

		// �t�b�^�[(���v���z)����
		SlipBookFooter footer = getSlipBookFooter(slip);
		book.getLastSheet().setFooter(footer);

		return book;

	}

	/**
	 * SlipBook�̃C���X�^���X�𐶐�����B<br>
	 * �e�X��SlipBook������Γ��Y���\�b�h��Override���邱�ƁB
	 * 
	 * @return book
	 */
	protected SlipBook createSlipBook() {
		return new SlipBook();
	}

	/**
	 * @return ����
	 */
	protected SlipBookDetail createSlipBookDetail() {
		return new SlipBookDetail();
	}

	/**
	 * �w�b�_�[�}�b�s���O
	 * 
	 * @param slip
	 * @return SlipBookHeader
	 */
	protected SlipBookHeader getSlipBookHeader(Slip slip) {

		SlipBookHeader header = createSlipBookHeader();

		// �^�C�g��
		header.setTitle(slip.getHeader().getSWK_DEN_SYU_NAME_K());

		// �`�[���t
		header.setSlipDate(slip.getSlipDate());

		// ���͕���
		header.setDepartmentCode(slip.getHeader().getSWK_IRAI_DEP_CODE());
		header.setDepartmentName(slip.getHeader().getSWK_IRAI_DEP_NAME());

		// ���͎҃R�[�h
		header.setEmployeeCode(slip.getHeader().getSWK_IRAI_EMP_CODE());
		header.setEmployeeName(slip.getHeader().getSWK_IRAI_EMP_NAME());

		// �`�[�ԍ�
		header.setSlipNo(slip.getHeader().getSWK_DEN_NO());
		header.setUpdateCount(slip.getHeader().getSWK_UPD_CNT());

		// �؜ߔԍ�
		header.setVoucherNo(slip.getHeader().getSWK_SEI_NO());

		// �E�v
		header.setRemarkCode(slip.getHeader().getSWK_TEK_CODE());
		header.setRemark(slip.getHeader().getSWK_TEK());

		// ����
		AccountBook book = slip.getDetails().get(0).getAccountBook();
		header.setAccountBook(book);
		if (AccountBook.BOTH == book) {
			header.setAccountBook(AccountBook.OWN);
		}

		// ���Z�i�K
		header.setSettlementStage(slip.getHeader().getSWK_KSN_KBN());

		return header;

	}

	/**
	 * SlipBookHeader�̃C���X�^���X�𐶐�����B<br>
	 * �e�X��SlipBookHeader������Γ��Y���\�b�h��Override���邱�ƁB
	 * 
	 * @return bookHeader
	 */
	protected SlipBookHeader createSlipBookHeader() {
		return new SlipBookHeader();
	}

	/**
	 * �t�b�^�[��Ԃ�
	 * 
	 * @param slip
	 * @return SlipBookFooter
	 */
	protected SlipBookFooter getSlipBookFooter(Slip slip) {

		SlipBookFooter footer = createSlipBookFooter();

		// �ؕ����v
		footer.setDebitAmount(slip.getDebitKeyAmount());

		// �ݕ����v
		footer.setCreditAmount(slip.getCreditKeyAmount());

		// �O�ݑS��(�t�b�^�[��`�����ɏ]��)
		for (int i = 0; i < footer.getMaxForeignCurrencyCount(); i++) {
			Currency foreignCurrency = slip.getForeignCurrencyAt(i);
			if (foreignCurrency != null) {

				footer.setForeignCurrencyCode(i, foreignCurrency.getCode());
				footer.setForeignDecimalPoint(i, foreignCurrency.getDecimalPoint());

				// �ؕ��O�݌v
				footer.setForeignDebitAmount(i, slip.getDebitForeignAmountAt(i));

				// �ݕ��O�݌v
				footer.setForeignCreditAmount(i, slip.getCreditForeignAmountAt(i));
			}
		}

		return footer;

	}

	/**
	 * @return �t�b�^�[
	 */
	protected SlipBookFooter createSlipBookFooter() {
		SlipBookFooter footer = new SlipBookFooter();

		int count = 2;
		try {
			count = Util.avoidNullAsInt(ServerConfig.getProperty("trans.slip.max.foreign.currency.count"));
		} catch (Throwable e) {
			// �����Ȃ�
			count = 2;
		}

		footer.setMaxForeignCurrencyCount(count);

		return footer;
	}
}
