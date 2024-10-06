package jp.co.ais.trans2.common.model.ui.payment;

import java.awt.*;
import java.util.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * �x�����R���|�[�l���g
 */
public class TPaymentDate extends TPanel {

	/** �x���敪 */
	public TPaymentDateComboBox ctrlPayType;

	/** �J�����_�[ */
	public TPopupCalendar calendar;

	/** �x�����������t�B�[���h */
	public TPaymentSettingReference ctrlPaymentSetting;

	/** �`�[���t */
	public TSlipDate ctrlSlipDate;

	/** �R���g���[���[ */
	public TPaymentDateController controller;

	/** ���ߓ� */
	public Date closeDay = null;

	/** �C�����[�h�t���O */
	protected boolean isModify = false;

	/**
	 * �R���X�g���N�^.
	 */
	public TPaymentDate() {

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();

		// �R���g���[���[������
		createController();
	}

	/**
	 * �R���g���[���[������
	 */
	protected void createController() {
		// �R���g���[���[������
		controller = new TPaymentDateController(this);
	}

	/**
	 * ��ʍ\�z
	 */
	protected void initComponents() {

		ctrlPayType = new TPaymentDateComboBox();
		calendar = new TPopupCalendar();

		this.isModify = false;
	}

	/**
	 * �qitem�̏�����.
	 */
	protected void allocateComponents() {

		setLayout(new GridBagLayout());

		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		// �x���敪
		ctrlPayType.getLabel().setLangMessageID("C01098");// �x����
		add(ctrlPayType, gridBagConstraints);

		// �J�����_�[
		add(calendar, gridBagConstraints);

		resize();
	}

	/**
	 * panel�T�C�Y���A���񂾃A�C�e���̉����̍��v�ɂ���
	 */
	public void resize() {
		Dimension size = this.getPreferredSize();
		size.setSize(ctrlPayType.getWidth() + calendar.getWidth(), size.getHeight());
		TGuiUtil.setComponentSize(this, size);
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		ctrlPayType.setTabControlNo(tabControlNo);
		calendar.setTabControlNo(tabControlNo);
	}

	/**
	 * �x�������擾����
	 * 
	 * @return �x����
	 */
	public Date getPaymentDate() {
		return calendar.getValue();
	}

	/**
	 * �x���� ��ݒ肷��
	 * 
	 * @param date �x����
	 */
	public void setPaymentDate(Date date) {
		calendar.setValue(date);
	}

	/**
	 * �x�������Ɠ`�[���t�����Ɏx������ݒ肷��(����)
	 */
	public void setPaymentDate() {
		controller.setPaymentDate();
	}

	/**
	 * �t�H�[�J�X�ݒ�
	 */
	public void requestTextFocus() {
		calendar.requestFocus();
	}

	/**
	 * �x���敪���擾����
	 * 
	 * @return �x���敪
	 */
	public PaymentDateType getPaymentDateType() {
		return ctrlPayType.getPaymentDateType();
	}

	/**
	 * �N���A
	 */
	public void clear() {
		ctrlPayType.init();
		calendar.clear();
	}

	/**
	 * �x���敪��ݒ肷��
	 * 
	 * @param paymentDateType
	 */
	public void setPaymentDateType(PaymentDateType paymentDateType) {

		ctrlPayType.setPaymentDateType(paymentDateType);

	}

	/**
	 * ���͐���
	 * 
	 * @param isEditable
	 */
	public void setEditable(boolean isEditable) {
		ctrlPayType.setEnabled(isEditable);
		calendar.setEditable(isEditable);
	}

	/**
	 * �x���\�����ǂ���
	 * 
	 * @return true:�x���\
	 */
	public boolean isPaymentDate() {
		return controller.isPaymentDate();
	}

	/**
	 * �Q�Ƃ���R���|�[�l���g��ݒ肷��
	 * 
	 * @param ctrlSlipDate
	 * @param ctrlPaymentSetting
	 */
	public void setReferenceComponent(TSlipDate ctrlSlipDate, TPaymentSettingReference ctrlPaymentSetting) {

		// �����̕ۑ�
		this.ctrlPaymentSetting = ctrlPaymentSetting;
		this.ctrlSlipDate = ctrlSlipDate;

		// �C�x���g�̐ݒ�
		controller.setReferenceComponentEvent();
	}

	/**
	 * ���ߓ���ݒ肷��B
	 * 
	 * @param closeDay
	 */
	public void setCloseDay(Date closeDay) {
		this.closeDay = closeDay;
	}

	/**
	 * �x���������͂���Ă��邩
	 * 
	 * @return true:���͍ρAfalse:������
	 */
	public boolean isEmpty() {
		return calendar.isEmpty();
	}

	/**
	 * �C�����[�h ��ݒ肷��B
	 * 
	 * @param isModify
	 */
	public void setModifyMode(boolean isModify) {
		this.isModify = isModify;
	}

	/**
	 * �C�����[�h ��ݒ肷��B
	 * 
	 * @return isModify
	 */
	public boolean isModifyMode() {
		return this.isModify;
	}

}
