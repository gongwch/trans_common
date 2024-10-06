package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * �ʉ݊��Z�t�B�[���h
 * 
 * @author moriya
 */
public class TCurrencyExchangeField extends TPanel {

	/** �V���A��UID */
	protected static final long serialVersionUID = 1L;

	/** �f�t�H���g */
	public static final int TYPE_NOMAL = 0;

	/** �R���|�[�l���g�ʒu�����\ */
	public static final int TYPE_ADJUSTABLE = 1;

	/** �R���g���[���N���X */
	protected TCurrencyExchangeFieldCtrl ctrl;

	/** �x�[�X�p�l�� */
	protected TPanel pnlBase;

	/** ���[�g����t */
	protected TPopupCalendar rateBaseDate;

	/** �ʉ� */
	protected TCurrencyField currencyField;

	/** ���[�g */
	protected TRateNumericField numRate;

	/** ���͋��z */
	protected TINputNumericField numInputAmount;

	/** ����z */
	protected TBaseNumericField numBaseCurrencyAmount;

	/** �I��ʉ݂̏����_�ȉ����� */
	protected int digit = 0;

	/** Call�N���X */
	protected List<CallBackListener> callCtrlList = new LinkedList<CallBackListener>();

	/**
	 * �R���X�g���N�^
	 * 
	 * @param rateBaseDate �A�����t�R���|�[�l���g
	 */
	public TCurrencyExchangeField(TPopupCalendar rateBaseDate) {
		this(rateBaseDate, TYPE_NOMAL);
	}

	/**
	 * �R���X�g���N�^ �e�R���|�[�l���g�̈ʒu���ςɂȂ�ꍇ�g�p����
	 * 
	 * @param rateBaseDate �A�����t�R���|�[�l���g
	 * @param type �^�C�v
	 */
	public TCurrencyExchangeField(TPopupCalendar rateBaseDate, int type) {
		super();

		this.rateBaseDate = rateBaseDate;

		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());

		switch (type) {
			case TYPE_ADJUSTABLE:
				initVariableComponents();
				break;

			default:
				initComponents();
				break;
		}

		this.ctrl = new TCurrencyExchangeFieldCtrl(this);

		componentEvent();

	}

	/**
	 * ��ʍ\�z
	 */
	protected void initComponents() {

		GridBagConstraints gridBagConstraints;

		pnlBase = new TPanel();
		currencyField = new TCurrencyField();
		numRate = new TRateNumericField();
		numInputAmount = new TINputNumericField();
		numBaseCurrencyAmount = new TBaseNumericField();

		pnlBase.setLayout(new GridBagLayout());
		pnlBase.setMaximumSize(new Dimension(430, 75));
		pnlBase.setMinimumSize(new Dimension(430, 75));
		pnlBase.setPreferredSize(new Dimension(430, 75));

		currencyField.setNoticeSize(0);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlBase.add(currencyField, gridBagConstraints);

		numRate.setFieldHAlignment(2);
		numRate.setFieldSize(130);
		numRate.setLabelHAlignment(3);
		numRate.setLabelSize(36);
		numRate.setLangMessageID("C00556");
		numRate.setMaxLength(15);
		numRate.getField().setNumericFormat(NumberFormatUtil.makeNumberFormat(4));
		numRate.getField().setPositiveOnly(true);
		numRate.getField().setNumber(1);
		numRate.setEditable(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(0, 80, 0, 0);
		pnlBase.add(numRate, gridBagConstraints);

		numInputAmount.setFieldHAlignment(2);
		numInputAmount.setFieldSize(130);
		numInputAmount.setLabelSize(60);
		numInputAmount.setLangMessageID("C00574");
		numInputAmount.setMaxLength(17);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(0, 25, 0, 0);
		pnlBase.add(numInputAmount, gridBagConstraints);

		numBaseCurrencyAmount.setFieldHAlignment(2);
		numBaseCurrencyAmount.setFieldSize(130);
		numBaseCurrencyAmount.setLabelSize(60);
		numBaseCurrencyAmount.setLangMessageID("C00576");
		numBaseCurrencyAmount.setMaxLength(17);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		pnlBase.add(numBaseCurrencyAmount, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		add(pnlBase, gridBagConstraints);

	}

	/**
	 * �R���|�[�l���g�z�u���ςɂȂ�ꍇ ��ʍ\�z
	 */
	protected void initVariableComponents() {

		GridBagConstraints gridBagConstraints;

		currencyField = new TCurrencyField();
		numRate = new TRateNumericField();
		numInputAmount = new TINputNumericField();
		numBaseCurrencyAmount = new TBaseNumericField();

		gridBagConstraints = new GridBagConstraints();
		add(currencyField, gridBagConstraints);

		numRate.setFieldHAlignment(2);
		numRate.setFieldSize(130);
		numRate.setLabelHAlignment(3);
		numRate.setLabelSize(36);
		numRate.setLangMessageID("C00556");
		numRate.setMaxLength(15);
		numRate.getField().setNumericFormat(NumberFormatUtil.makeNumberFormat(4));
		numRate.getField().setPositiveOnly(true);
		numRate.getField().setNumber(1);
		numRate.setEditable(false);
		gridBagConstraints = new GridBagConstraints();
		add(numRate, gridBagConstraints);

		numInputAmount.setFieldHAlignment(2);
		numInputAmount.setFieldSize(130);
		numInputAmount.setLabelSize(60);
		numInputAmount.setLangMessageID("C00574");
		numInputAmount.setMaxLength(17);
		gridBagConstraints = new GridBagConstraints();
		add(numInputAmount, gridBagConstraints);

		numBaseCurrencyAmount.setFieldHAlignment(2);
		numBaseCurrencyAmount.setFieldSize(130);
		numBaseCurrencyAmount.setLabelSize(60);
		numBaseCurrencyAmount.setLangMessageID("C00576");
		numBaseCurrencyAmount.setMaxLength(17);
		gridBagConstraints = new GridBagConstraints();
		add(numBaseCurrencyAmount, gridBagConstraints);
	}

	/**
	 * �e�R���|�[�l���g�̃C�x���g
	 */
	protected void componentEvent() {

		// �ʉ݃R�[�h���X�g�t�H�[�J�X
		currencyField.addCallControl(new CallBackListener() {

			@Override
			public void after(boolean sts) {

				if (sts) {
					ctrl.currencyFieldFocusLost(Util.avoidNull(currencyField.getValue()));
				}

			}
		});

		// ���[�g���X�g�t�H�[�J�X
		numRate.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				for (CallBackListener callCtrl : callCtrlList) {
					callCtrl.before();
				}

				boolean sts = ctrl.numRateLostFocus();

				for (CallBackListener listener : callCtrlList) {
					listener.after();
				}
				return sts;

			}
		});

		// ���͋��z���X�g�t�H�[�J�X
		numInputAmount.getField().setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				for (CallBackListener callCtrl : callCtrlList) {
					callCtrl.before();
				}

				ctrl.numInputAmountLostFocus();

				for (CallBackListener listener : callCtrlList) {
					listener.after();
				}

				return true;
			}
		});

		// ����z���X�g�t�H�[�J�X
		numBaseCurrencyAmount.getField().setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				for (CallBackListener callCtrl : callCtrlList) {
					callCtrl.before();
				}

				ctrl.numBaseCurrencyAmountLostFocus();

				for (CallBackListener listener : callCtrlList) {
					listener.after();
				}
				return true;
			}
		});
	}

	/**
	 * �ʉ݃t�B�[���h Call�N���X���Z�b�g����B<BR>
	 * 
	 * @param callCtrl CallControl�N��
	 */
	public void addCurCallControl(CallBackListener callCtrl) {
		this.currencyField.addCallControl(callCtrl);
	}

	/**
	 * ���[�g Call�N���X���Z�b�g����B<BR>
	 * 
	 * @param callCtrl CallControl�N��
	 */
	public void addRateCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}

	/**
	 * ���͋��z Call�N���X���Z�b�g����B<BR>
	 * 
	 * @param callCtrl CallControl�N��
	 */
	public void addInputAmountCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}

	/**
	 * ����z Call�N���X���Z�b�g����B<BR>
	 * 
	 * @param callCtrl CallControl�N��
	 */
	public void addBaseCurAmountCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}

	/**
	 * ���[�g����t�̎擾
	 * 
	 * @return TLabelPopupCalendar
	 */
	public Date getSelectedDate() {
		return rateBaseDate.getValue();
	}

	/**
	 * ���[�g����t�̐ݒ�
	 * 
	 * @param slipDate
	 */
	public void setSelectedDate(Date slipDate) {
		this.rateBaseDate.setValue(slipDate);

		// �ʉ݌����̗L���������ݒ肷��
		currencyField.setTermBasisDate(DateUtil.toYMDString(rateBaseDate.getValue()));
	}

	/**
	 * �R���|�[�l���g������������
	 */
	public void clear() {
		ctrl.initCtrlValue();
	}

	/**
	 * �ʉ݃R�[�h�̎擾
	 * 
	 * @return TCurrencyField
	 */
	public TCurrencyField getCurrencyField() {
		return currencyField;
	}

	/**
	 * �ʉ݃R�[�h�̐ݒ�
	 * 
	 * @param currencyField
	 */
	public void setCurrencyField(TCurrencyField currencyField) {
		this.currencyField = currencyField;
	}

	/**
	 * ���[�g�̎擾
	 * 
	 * @return TLabelNumericField
	 */
	public TLabelNumericField getNumRate() {
		return numRate;
	}

	/**
	 * ���[�g�̐ݒ�
	 * 
	 * @param numRate
	 */
	public void setNumRate(TRateNumericField numRate) {
		this.numRate = numRate;
	}

	/**
	 * ���͋��z�̎擾
	 * 
	 * @return TLabelNumericField
	 */
	public TLabelNumericField getNumInputAmount() {
		return numInputAmount;
	}

	/**
	 * ���͋��z�̐ݒ�
	 * 
	 * @param numInputAmount
	 */
	public void setNumInputAmount(TINputNumericField numInputAmount) {
		this.numInputAmount = numInputAmount;
	}

	/**
	 * ����z�̎擾
	 * 
	 * @return TLabelNumericField
	 */
	public TLabelNumericField getNumBaseCurrencyAmount() {
		return numBaseCurrencyAmount;
	}

	/**
	 * ����z�̐ݒ�
	 * 
	 * @param numJapaneseCurrencyAmount
	 */
	public void setNumBaseCurrencyAmount(TBaseNumericField numJapaneseCurrencyAmount) {
		this.numBaseCurrencyAmount = numJapaneseCurrencyAmount;
	}

	/**
	 * �����_�������擾
	 * 
	 * @return digit
	 */
	public int getDigit() {
		return this.digit;
	}

	/**
	 * �����_�����̐ݒ�
	 * 
	 * @param digit
	 */
	public void setDigit(int digit) {
		this.digit = digit;
	}

	/**
	 * �p�l���̎擾
	 * 
	 * @return TPanel
	 */
	public TPanel getBasePanel() {
		return pnlBase;
	}

	/**
	 * �p�l���̐ݒ�
	 * 
	 * @param pnlBase
	 */
	public void setBasePanel(TPanel pnlBase) {
		this.pnlBase = pnlBase;
	}

	/**
	 * �ŐV���Z���̎擾
	 */
	public void setExchangeInfo() {
		ctrl.currencyFieldFocusLost(Util.avoidNull(currencyField.getValue()));
	}

	/**
	 * �^�u�ړ����ԍ���ʉ݊��Z�R���|�[�l���g�S�̂ɐݒ肷��.
	 * 
	 * @param no
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		currencyField.setTabControlNo(no);
		numRate.setTabControlNo(no);
		numInputAmount.setTabControlNo(no);
		numBaseCurrencyAmount.setTabControlNo(no);
	}

	/**
	 * �M�݋��z�������v�Z�Őݒ肷��i���͋��z���X�g�t�H�[�J�X�����j
	 */
	public void reflectBaseAmountValue() {
		ctrl.numInputAmountLostFocus();
	}

	/**
	 * ���͊z�N���X
	 */
	public class TINputNumericField extends TLabelNumericField {

		/**
		 * �R���X�g���N�^
		 */
		public TINputNumericField() {
			super();
		}

		/**
		 * @see jp.co.ais.trans.common.gui.TLabelNumericField#setNumberValue(java.lang.Number)
		 */
		@Override
		public void setNumberValue(Number value) {
			getField().setNumber(value);
			for (CallBackListener callCtrl : callCtrlList) {
				callCtrl.before();
			}
			ctrl.numInputAmountLostFocus();
			for (CallBackListener listener : callCtrlList) {
				listener.after();
			}
		}
	}

	/**
	 * �M�݊z�N���X
	 */
	public class TBaseNumericField extends TLabelNumericField {

		/**
		 * �R���X�g���N�^
		 */
		public TBaseNumericField() {
			super();
		}

		/**
		 * @see jp.co.ais.trans.common.gui.TLabelNumericField#setNumberValue(java.lang.Number)
		 */
		@Override
		public void setNumberValue(Number value) {
			getField().setNumber(value);
			for (CallBackListener callCtrl : callCtrlList) {
				callCtrl.before();
			}

			ctrl.numBaseCurrencyAmountLostFocus();

			for (CallBackListener listener : callCtrlList) {
				listener.after();
			}
		}
	}

	/**
	 * ���[�g�N���X
	 */
	public class TRateNumericField extends TLabelNumericField {

		/**
		 * �R���X�g���N�^
		 */
		public TRateNumericField() {
			super();
		}

		/**
		 * @see jp.co.ais.trans.common.gui.TLabelNumericField#setNumberValue(java.lang.Number)
		 */
		@Override
		public void setNumberValue(Number value) {
			getField().setNumber(value);
			for (CallBackListener callCtrl : callCtrlList) {
				callCtrl.before();
			}

			ctrl.numRateLostFocus();

			for (CallBackListener listener : callCtrlList) {
				listener.after();
			}
		}
	}
}
