package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.item.*;

/**
 * �ȖځE�⏕�E����̃��j�b�g�͈̔͌����R���|�[�l���g
 * 
 * @author AIS
 */
public class TItemGroupRange extends TPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = 3051866409639326455L;

	/** �J�n�t�B�[���h */
	public TItemGroup ctrlItemGroupFrom;

	/** �I���t�B�[���h */
	public TItemGroup ctrlItemGroupTo;

	/**
	 * 
	 *
	 */
	public TItemGroupRange() {

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();

		// ������
		init();

	}

	/**
	 * �R���|�[�l���g������������<BR>
	 */
	protected void initComponents() {
		ctrlItemGroupFrom = new TItemGroup();
		ctrlItemGroupTo = new TItemGroup();
	}

	/**
	 * �R���|�[�l���g��z�u����
	 */
	protected void allocateComponents() {

		setSize(ctrlItemGroupFrom.getWidth(), ctrlItemGroupFrom.getHeight() + ctrlItemGroupTo.getHeight());

		setLayout(null);

		// �J�n
		ctrlItemGroupFrom.setLocation(0, 0);
		add(ctrlItemGroupFrom);

		// �I��
		ctrlItemGroupTo.setLocation(0, ctrlItemGroupFrom.getHeight());
		add(ctrlItemGroupTo);

	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		ctrlItemGroupFrom.setTabControlNo(tabControlNo);
		ctrlItemGroupTo.setTabControlNo(tabControlNo);
	}

	/**
	 * ������
	 */
	public void init() {

		// �Ȗڂ͈͎̔w��
		ctrlItemGroupFrom.ctrlItemReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				field_verify_after();
			}
		});

		ctrlItemGroupTo.ctrlItemReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				field_verify_after();
			}
		});

		// �⏕�Ȗڂ͈͎̔w��
		ctrlItemGroupFrom.ctrlSubItemReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				field_verify_after();
			}
		});

		ctrlItemGroupTo.ctrlSubItemReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				field_verify_after();
			}
		});

		// ����Ȗڂ͈͎̔w��
		ctrlItemGroupFrom.ctrlDetailItemReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				field_verify_after();
			}
		});

		ctrlItemGroupTo.ctrlDetailItemReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				field_verify_after();
			}
		});

	}

	/**
	 * �J�n�t�B�[���h��verify�ǉ�����
	 */
	protected void field_verify_after() {

		// �Ȗڂ͈͎̔w��
		ctrlItemGroupTo.ctrlItemReference.getSearchCondition().setCodeFrom(
			ctrlItemGroupFrom.ctrlItemReference.getCode());

		// �Ȗڂ��Ƃ��ɓ��͂���Ă��āA���ȖڃR�[�h����v����ꍇ�A�⏕�Ȗڂ͈͎̔w��
		if (ctrlItemGroupFrom.ctrlItemReference.getCode() != null
			&& ctrlItemGroupTo.ctrlItemReference.getCode() != null
			&& ctrlItemGroupFrom.ctrlItemReference.getCode().equals(ctrlItemGroupTo.ctrlItemReference.getCode())) {

			ctrlItemGroupTo.ctrlSubItemReference.getSearchCondition().setCodeFrom(
				ctrlItemGroupFrom.ctrlSubItemReference.getCode());

			// �Ȗڂ������́A�܂��͉Ȗڂ��قȂ�ꍇ�͔͈͎w�薳���B
		} else {
			ctrlItemGroupTo.ctrlSubItemReference.getSearchCondition().setCodeFrom(null);
			ctrlItemGroupTo.ctrlDetailItemReference.getSearchCondition().setCodeFrom(null);
			ctrlItemGroupTo.ctrlDetailItemReference.getSearchCondition().setCodeTo(null);
		}

		if (ctrlItemGroupFrom.ctrlSubItemReference.getCode() != null
			&& ctrlItemGroupTo.ctrlSubItemReference.getCode() != null
			&& ctrlItemGroupFrom.ctrlItemReference.getCode().equals(ctrlItemGroupTo.ctrlItemReference.getCode())
			&& ctrlItemGroupFrom.ctrlSubItemReference.getCode().equals(ctrlItemGroupTo.ctrlSubItemReference.getCode())) {

			ctrlItemGroupTo.ctrlDetailItemReference.getSearchCondition().setCodeFrom(
				ctrlItemGroupFrom.ctrlDetailItemReference.getCode());
		} else {

			ctrlItemGroupTo.ctrlDetailItemReference.getSearchCondition().setCodeFrom(null);

		}

		// �Ȗڂ͈͎̔w��
		ctrlItemGroupFrom.ctrlItemReference.getSearchCondition().setCodeTo(ctrlItemGroupTo.ctrlItemReference.getCode());

		// �Ȗڂ��Ƃ��ɓ��͂���Ă��āA���ȖڃR�[�h����v����ꍇ�A�⏕�Ȗڂ͈͎̔w��
		if (ctrlItemGroupFrom.ctrlItemReference.getCode() != null
			&& ctrlItemGroupTo.ctrlItemReference.getCode() != null
			&& ctrlItemGroupFrom.ctrlItemReference.getCode().equals(ctrlItemGroupTo.ctrlItemReference.getCode())) {

			ctrlItemGroupFrom.ctrlSubItemReference.getSearchCondition().setCodeTo(
				ctrlItemGroupTo.ctrlSubItemReference.getCode());

			// �Ȗڂ������́A�܂��͉Ȗڂ��قȂ�ꍇ�͔͈͎w�薳���B
		} else {
			ctrlItemGroupFrom.ctrlSubItemReference.getSearchCondition().setCodeTo(null);
			ctrlItemGroupTo.ctrlDetailItemReference.getSearchCondition().setCodeFrom(null);
			ctrlItemGroupTo.ctrlDetailItemReference.getSearchCondition().setCodeTo(null);
		}

		if (ctrlItemGroupFrom.ctrlSubItemReference.getCode() != null
			&& ctrlItemGroupTo.ctrlSubItemReference.getCode() != null
			&& ctrlItemGroupFrom.ctrlItemReference.getCode().equals(ctrlItemGroupTo.ctrlItemReference.getCode())
			&& ctrlItemGroupFrom.ctrlSubItemReference.getCode().equals(ctrlItemGroupTo.ctrlSubItemReference.getCode())) {

			ctrlItemGroupFrom.ctrlDetailItemReference.getSearchCondition().setCodeTo(
				ctrlItemGroupTo.ctrlDetailItemReference.getCode());
		} else {

			ctrlItemGroupFrom.ctrlDetailItemReference.getSearchCondition().setCodeTo(null);

		}

	}

	/**
	 * �召�`�F�b�N
	 * 
	 * @return true(����) / false(�G���[)
	 */
	public boolean isSmallerFrom() {

		// �Ȗڂ̊J�n�A�I���̂����ꂩ���u�����N�̏ꍇtrue
		if (Util.isNullOrEmpty(ctrlItemGroupFrom.ctrlItemReference.getCode())
			|| Util.isNullOrEmpty(ctrlItemGroupTo.ctrlItemReference.getCode())) {
			return true;
		}

		// �Ȗڂ̊J�n�A�I�����Ƃ��ɓ��͂���Ă��āA���J�n > �I���̏ꍇ�G���[
		if (!Util.isSmallerThen(ctrlItemGroupFrom.ctrlItemReference.getCode(),
			ctrlItemGroupTo.ctrlItemReference.getCode())) {
			return false;
		}

		// �⏕�Ȗڂ̊J�n�A�I���̂����ꂩ���u�����N�̏ꍇtrue
		if (Util.isNullOrEmpty(ctrlItemGroupFrom.ctrlSubItemReference.getCode())
			|| Util.isNullOrEmpty(ctrlItemGroupTo.ctrlSubItemReference.getCode())) {
			return true;
		}

		// �Ȗڂ������ŁA���J�n > �I���̏ꍇ�G���[
		if (ctrlItemGroupFrom.ctrlItemReference.getCode().equals(ctrlItemGroupTo.ctrlItemReference.getCode())
			&& !Util.isSmallerThen(ctrlItemGroupFrom.ctrlSubItemReference.getCode(),
				ctrlItemGroupTo.ctrlSubItemReference.getCode())) {
			return false;
		}

		// ����Ȗڂ̊J�n�A�I���̂����ꂩ���u�����N�̏ꍇtrue
		if (Util.isNullOrEmpty(ctrlItemGroupFrom.ctrlDetailItemReference.getCode())
			|| Util.isNullOrEmpty(ctrlItemGroupTo.ctrlDetailItemReference.getCode())) {
			return true;
		}

		// ����Ȗڂ̊J�n�A�I�����Ƃ��ɓ��͂���Ă��āA���J�n > �I���̏ꍇ�G���[
		if (ctrlItemGroupFrom.ctrlItemReference.getCode().equals(ctrlItemGroupTo.ctrlItemReference.getCode())
			&& ctrlItemGroupFrom.ctrlSubItemReference.getCode().equals(ctrlItemGroupTo.ctrlSubItemReference.getCode())
			&& !Util.isSmallerThen(ctrlItemGroupFrom.ctrlDetailItemReference.getCode(),
				ctrlItemGroupTo.ctrlDetailItemReference.getCode())) {
			return false;
		}

		return true;

	}

	/**
	 * �J�n�t�B�[���h�őI�����ꂽ�ȖځE�⏕�E�����Ԃ�
	 * 
	 * @return �I�����ꂽ�ȖځE�⏕�E����<br>
	 *         (Item�̒��ɊK�w�I�ɉȖځE�⏕�E��������ĕԂ�)
	 */
	public Item getFromEntity() {
		return ctrlItemGroupFrom.getEntity();
	}

	/**
	 * �I���t�B�[���h�őI�����ꂽ�ȖځE�⏕�E�����Ԃ�
	 * 
	 * @return �I�����ꂽ�ȖځE�⏕�E����<br>
	 *         (Item�̒��ɊK�w�I�ɉȖځE�⏕�E��������ĕԂ�)
	 */
	public Item getToEntity() {
		return ctrlItemGroupTo.getEntity();
	}

	/**
	 * �N���A����
	 */
	public void clear() {
		ctrlItemGroupFrom.clear();
		ctrlItemGroupTo.clear();
	}

}
