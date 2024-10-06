package jp.co.ais.trans.common.bizui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * �E�v���ʃt�B�[���h
 * 
 * @author roh
 */
public class TSlipMemoField extends TButtonField {

	/** UID */
	private static final long serialVersionUID = -3179324050026858374L;

	/** �R���g���[�� */
	private TSlipMemoFieldCtrl ctrl;

	/** �s�E�v�Ɠ`�[�E�v�̋敪 */
	private int memoType;

	/** �L������ */
	private String termBasisDate;

	/** ��ЃR�[�h */
	private String companyCode;

	/** �E�v�f�[�^�敪 */
	private String slipDataType;

	/** �`�[�E�v */
	public static final int SLIP_DATA = 0;

	/** �s�E�v */
	public static final int MEMO_DATA = 1;

	/** Call�N���X */
	private List<CallBackListener> callCtrlList;

	/**
	 * �E�v���ʃt�B�[���h
	 * 
	 * @author roh
	 * @param type �E�v�敪<br>
	 *            SLIP_DATA : �`�[�E�v MEMO_DATA : �s�E�v
	 */

	public TSlipMemoField(int type) {
		super();
		memoType = type;
		ctrl = new TSlipMemoFieldCtrl(this);
		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());
		initComponents();

	}

	/**
	 * ��ʍ\�z
	 */

	private void initComponents() {

		// �{�^���T�C�Y�����l
		this.setButtonSize(85);
		// �t�B�[���h�T�C�Y�����l
		this.setFieldSize(75);
		// ���͌��������l
		this.setMaxLength(10);
		// ���͌��������l
		this.setNoticeMaxLength(80);
		// IME���[�h
		this.setImeMode(false);
		// Notice�̓��͂��ɂ���
		this.setNoticeEditable(true);
		// �`�[�E�v�̏ꍇ
		if (memoType == SLIP_DATA) {
			this.setLangMessageID("C00569");
			// �s�E�v�̏ꍇ
		} else if (memoType == MEMO_DATA) {
			this.setLangMessageID("C00119");
		}

		// ���X�g�t�H�[�J�X��
		setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				if (!getField().isEditable()) {
					return true;
				}
				if (!isValueChanged()) {
					return true;
				}

				for (CallBackListener callCtrl : callCtrlList) {
					callCtrl.before();
				}

				boolean sts = ctrl.slipMomoLostFocus();

				for (CallBackListener listener : callCtrlList) {
					listener.after();
				}

				return sts;
			}
		});

		// �{�^��������
		addButtonActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				ctrl.btnActionPerformed();
			}
		});

	}

	/**
	 * ��ЃR�[�h�K��
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h�ݒ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �K�p�敪�K��
	 * 
	 * @return memoType �K�p�敪
	 */
	public int getMemoType() {
		return memoType;
	}

	/**
	 * �K�p�敪�ݒ�
	 * 
	 * @param memoType �K�p�敪
	 */
	public void setMemoType(int memoType) {
		this.memoType = memoType;
	}

	/**
	 * �f�[�^�^�C�v�K��
	 * 
	 * @return slipDataType �f�[�^�^�C�v
	 */
	public String getSlipDataType() {
		return slipDataType;
	}

	/**
	 * �f�[�^�^�C�v�ݒ�
	 * 
	 * @param slipDataType �f�[�^�^�C�v
	 */
	public void setSlipDataType(String slipDataType) {
		this.slipDataType = slipDataType;
	}

	/**
	 * �L�����Ԏ擾
	 * 
	 * @return �L������
	 */
	public String getTermBasisDate() {
		return termBasisDate;
	}

	/**
	 * �L�����Ԑݒ�
	 * 
	 * @param termBasisDate �L������
	 */
	public void setTermBasisDate(String termBasisDate) {
		this.termBasisDate = termBasisDate;
	}

	/**
	 * CallBackListener��ǉ�����
	 * 
	 * @param callCtrl CallBackListener
	 */
	public void addCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}

	/**
	 * field �e�L�X�g�t�B�[���h�ɕ������ݒ肷��
	 * 
	 * @param value �ݒ蕶����
	 */
	@Override
	public void setValue(String value) {
		super.setValue(value);
		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}
		ctrl.slipMomoLostFocus();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
		}
	}
}
