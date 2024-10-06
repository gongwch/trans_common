package jp.co.ais.trans2.model.slip;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.define.*;

/**
 * ����Ǘ��̓`�[
 */
public class SlipBooks implements Serializable, Cloneable {

	/** �`�[ */
	protected Slip slip;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param slip ���f�[�^(���땪���i�V�̏��)
	 */
	public SlipBooks(Slip slip) {
		this.slip = slip;
	}

	/**
	 * �`�[�̎��
	 * 
	 * @return �`�[�̎��
	 */
	public SlipKind getSlipKind() {
		return slip.getSlipKind();
	}

	/**
	 * ���̓`�[���擾
	 * 
	 * @return ���`�[
	 */
	public Slip getSlip() {
		return slip;
	}

	/**
	 * �����ʉݒ���
	 * 
	 * @return �`�[
	 */
	public Slip getOwnBookSlip() {
		Slip newSlip = slip.clone();

		List<SWK_DTL> list = newSlip.getDetails();
		for (SWK_DTL dtl : list.toArray(new SWK_DTL[list.size()])) {
			if (dtl.isFunctionalCurrency() || !AccountBook.isOWN(dtl.getSWK_ADJ_KBN())) {
				// ��������Ȃ��d��͏��O
				list.remove(dtl);
			}
		}

		return newSlip;
	}

	/**
	 * IFRS����
	 * 
	 * @return �`�[
	 */
	public Slip getIFRSBookSlip() {
		Slip newSlip = slip.clone();

		List<SWK_DTL> list = newSlip.getDetails();
		for (SWK_DTL dtl : list.toArray(new SWK_DTL[list.size()])) {
			if (dtl.isFunctionalCurrency() || !AccountBook.isIFRS(dtl.getSWK_ADJ_KBN())) {
				// IFRS����Ȃ��d��͏��O
				list.remove(dtl);
			}
		}

		return newSlip;
	}

	/**
	 * �@�\�ʉݒ���
	 * 
	 * @return �`�[
	 */
	public Slip getIFRSFuncSlip() {
		Slip newSlip = slip.clone();

		List<SWK_DTL> list = newSlip.getDetails();
		for (SWK_DTL dtl : list.toArray(new SWK_DTL[list.size()])) {
			if (!dtl.isFunctionalCurrency()) {
				// �@�\�ʉݎd��ȊO�͏��O
				list.remove(dtl);
			}
		}

		return newSlip;
	}

	/**
	 * �`�[�̃X�e�[�^�X(�X�V�敪)��Ԃ�
	 * 
	 * @return �`�[�̃X�e�[�^�X(�X�V�敪)
	 */
	public SlipState getSlipState() {
		return getSlip().getHeader().getSlipState();
	}

	@Override
	public SlipBooks clone() {

		try {

			SlipBooks slipBooks = (SlipBooks) super.clone();
			Slip slip1 = this.slip.clone();
			slipBooks.slip = slip1;
			return slipBooks;
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

}
