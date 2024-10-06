package jp.co.ais.trans2.master.ui;

import java.awt.*;

import jp.co.ais.trans2.master.model.ui.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �Ȗڃ}�X�^�̕ҏW���<br>
 * �q�C���x�v�Z�t���O�ǉ���
 */
public class MG0081ItemMasterDialog extends MG0080ItemMasterDialog {

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0081ItemMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	/**
	 * �R���|�[�l���g�̏�����
	 */
	@Override
	public void initComponents() {
		super.initComponents();

		chk = new TItemStatusVoyageUnit(company);
	}

	/**
	 * �R���|�[�l���g�̔z�u
	 */
	@Override
	public void allocateComponents() {
		super.allocateComponents();

		setSize(790, 630);
	}

}
