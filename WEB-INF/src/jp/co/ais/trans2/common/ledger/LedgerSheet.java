package jp.co.ais.trans2.common.ledger;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;;

/**
 * ���[�V�[�g�N���X�ł��B<br>
 * ���[1�y�[�W���̃f�[�^��ێ����܂��B<br>
 * 1�V�[�g�́A���[�w�b�_�[�f�[�^�A���׃��X�g�A�t�b�^�[�f�[�^����\������܂��B<br>
 * @author AIS
 *
 */
public class LedgerSheet implements Serializable {

	private static final long serialVersionUID = 3475428084169032258L;

	// ���[�w�b�_�[
	protected LedgerSheetHeader header;

	// ���[����
	protected List<LedgerSheetDetail> detail;

	// ���[�t�b�^�[
	protected LedgerSheetFooter footer;	

	public LedgerSheet() {
		detail = new ArrayList<LedgerSheetDetail>();
	}

	/**
	 * ���[�w�b�_�[getter
	 * @return
	 */
	public LedgerSheetHeader getHeader() {
		return header;
	}

	/**
	 * ���[�w�b�_�[setter
	 * @param header
	 */
	public void setHeader(LedgerSheetHeader header) {
		this.header = header;
	}

	/**
	 * ���[����getter
	 * @return
	 */
	public List<LedgerSheetDetail> getDetail() {
		return detail;
	}

	/**
	 * ���[����setter
	 * @param detail
	 */
	public void setDetail(List<LedgerSheetDetail> detail) {
		this.detail = detail;
	}

	/**
	 * ���׍s�̍s����Ԃ�
	 * @return
	 */
	public int getRowCount() {
		return detail.size();
	}

	/**
	 * ���ׂ�ǉ�����
	 * @param detail
	 */
	public void addDetail(LedgerSheetDetail detail) {
		this.detail.add(detail);
	}

	/**
	 * ���[�t�b�^�[getter
	 * @return
	 */
	public LedgerSheetFooter getFooter() {
		return footer;
	}

	/**
	 * ���[�t�b�^�[setter
	 * @param footer
	 */
	public void setFooter(LedgerSheetFooter footer) {
		this.footer = footer;
	}

}
