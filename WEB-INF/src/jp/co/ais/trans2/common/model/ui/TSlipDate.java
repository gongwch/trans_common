package jp.co.ais.trans2.common.model.ui;

import java.awt.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �`�[���t�t�B�[���h
 * 
 * @author AIS
 */
public class TSlipDate extends TLabelPopupCalendar {

	/** serialVersionUID */
	private static final long serialVersionUID = -6142880729524534321L;

	/** �R���g���[�� */
	protected TSlipDateController controller;

	/** �N */
	public static final int TYPE_Y = TCalendar.TYPE_Y;

	/** �N�� */
	public static final int TYPE_YM = TCalendar.TYPE_YM;

	/** �N���� */
	public static final int TYPE_YMD = TCalendar.TYPE_YMD;

	/**
	 * �R���X�g���N�^.
	 */
	public TSlipDate() {

		this(TYPE_YMD);
	}

	/**
	 * �R���|�[�l���g������������<BR>
	 * 
	 * @param calType ���t�\���`��
	 */
	public TSlipDate(int calType) {

		super(calType);

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();

		// �R���g���[������
		controller = createController();

	}

	/**
	 * �R���g���[������
	 * 
	 * @return �R���g���[��
	 */
	protected TSlipDateController createController() {
		return new TSlipDateController(this);
	}

	/**
	 * �R���|�[�l���g������������<BR>
	 */
	protected void initComponents() {
		//
	}

	/**
	 * �R���|�[�l���g��z�u����
	 */
	protected void allocateComponents() {

		setLabelSize(60);
		setPreferredSize(new Dimension(getCalendarSize() + 60 + 5, 20));
		setSize(new Dimension(getCalendarSize() + 60 + 5, 20));
		setLangMessageID("C00599");
	}

	/**
	 * setLangMessageID()���g������
	 * 
	 * @deprecated
	 * @param text �ݒ蕶����
	 */
	@Override
	public void setLabelText(String text) {
		this.label.setLangMessageID(text);
	}

	/**
	 * ���ݓ��t��ݒ肷��B�i��s�`�[���t�l������j
	 */
	public void initSlipDate() {
		this.setValue(controller.getInitDate());
	}

	/**
	 * ������1�����Z�b�g����B<br>
	 * ���Z�i�K�͍l�����Ȃ��B
	 */
	public void setFirstDateOfCurrentPeriod() {
		controller.setFirstDateOfCurrentPeriod();
	}

	/**
	 * �����̖������Z�b�g����B<br>
	 * ���Z�i�K�͍l�����Ȃ��B
	 */
	public void setLastDateOfCurrentPeriod() {
		controller.setLastDateOfCurrentPeriod();
	}

	/**
	 * ������1�����Z�b�g����B<br>
	 * ���Z�i�K���l������B
	 */
	public void setFirstDateOfCurrentPeriodOfSettlement() {
		controller.setFirstDateOfCurrentPeriodOfSettlement();
	}

	/**
	 * �����̖������Z�b�g����B<br>
	 * ���Z�i�K���l������B
	 */
	public void setLastDateOfCurrentPeriodOfSettlement() {
		controller.setLastDateOfCurrentPeriodOfSettlement();
	}

	/**
	 * ��������Z�b�g����
	 */
	public void setDateBeginningOfPeriod() {
		controller.setDateBeginningOfPeriod();
	}

	/**
	 * ���߂��Ă��Ȃ�����Ԃ��B<br>
	 * ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse�B<br>
	 * ���Z�i�K�͍l�����Ȃ��B
	 * 
	 * @return ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse
	 */
	public boolean isClosed() {
		return controller.isClosed();
	}

	/**
	 * �w��̌��Z�i�K�܂Œ��߂��Ă��Ȃ�����Ԃ��B<br>
	 * ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse�B<br>
	 * 
	 * @param settlementStage ���Z�i�K
	 * @return ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse
	 */
	public boolean isClosed(int settlementStage) {
		return controller.isClosed(settlementStage);
	}

	/**
	 * �w��̌��Z�i�K�܂Œ��߂��Ă��Ȃ�����Ԃ��B<br>
	 * ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse�B<br>
	 * 
	 * @param company ���
	 * @param settlementStage ���Z�i�K
	 * @return ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse
	 */
	public boolean isClosed(Company company, int settlementStage) {
		return controller.isClosed(company, settlementStage);
	}

	/**
	 * ���߂��Ă��Ȃ�����Ԃ��B<br>
	 * ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse�B<br>
	 * ���Z�i�K�͍l������B
	 * 
	 * @return ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse
	 */
	public boolean isClosedPeriodOfSettlement() {
		return controller.isClosedPeriodOfSettlement();
	}

	/**
	 * �w���Ђ���ɁA���߂��Ă��Ȃ�����Ԃ��B<br>
	 * ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse�B<br>
	 * ���Z�i�K�͍l������B
	 * 
	 * @param company ���
	 * @return ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse
	 */
	public boolean isClosedPeriodOfSettlement(Company company) {
		return controller.isClosedPeriodOfSettlement(company);
	}

	/**
	 * ��s�`�[���͓��t�𒴂��Ă��Ȃ�����Ԃ��B<br>
	 * �����Ă���ꍇtrue�A�����Ă��Ȃ��ꍇfalse
	 * 
	 * @return ��s�`�[���͓��t�𒴂��Ă���ꍇtrue�A���Ȃ��ꍇfalse
	 */
	public boolean isPriorOver() {
		return controller.isPriorOver();
	}

	/**
	 * �w���Ђ���ɁA��s�`�[���͓��t�𒴂��Ă��Ȃ�����Ԃ��B<br>
	 * �����Ă���ꍇtrue�A�����Ă��Ȃ��ꍇfalse
	 * 
	 * @param company ���
	 * @return ��s�`�[���͓��t�𒴂��Ă���ꍇtrue�A���Ȃ��ꍇfalse
	 */
	public boolean isPriorOver(Company company) {
		return controller.isPriorOver(company);
	}

	/**
	 * ���Z�\���t(���Z��������)����Ԃ�
	 * 
	 * @return true(���Z�\���t) / false(���Z�\���t�ȊO)
	 */
	public boolean isSettlementDate() {
		return controller.isSettlementDate();
	}

	/**
	 * �R�[���o�b�N���X�i�[�o�^
	 * 
	 * @param listener �R�[���o�b�N���X�i�[
	 */
	public void addCallBackListener(TCallBackListener listener) {
		controller.addCallBackListener(listener);
	}

	/**
	 * �R�[���o�b�N���X�i�[�N���A
	 */
	public void clearCallBackListenerList() {
		controller.clearCallBackListenerList();
	}

	/**
	 * @return �R�[���o�b�N���X�i�[��߂��܂��B
	 */
	public List<TCallBackListener> getCallBackListenerList() {
		return controller.getCallBackListenerList();
	}

	/**
	 * @param callBackListenerList �R�[���o�b�N���X�i�[��ݒ肵�܂��B
	 */
	public void setCallBackListenerList(List<TCallBackListener> callBackListenerList) {
		controller.setCallBackListenerList(callBackListenerList);
	}

	/**
	 * �R�[���o�b�N�p��Overide
	 * 
	 * @see jp.co.ais.trans.common.gui.TLabelPopupCalendar#setValue(java.util.Date)
	 */
	@Override
	public void setValue(Date date) {
		super.setValue(date);

		controller.changedValue();
	}
}