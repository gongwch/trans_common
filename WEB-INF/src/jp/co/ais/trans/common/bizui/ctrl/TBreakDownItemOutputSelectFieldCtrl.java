package jp.co.ais.trans.common.bizui.ctrl;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.master.entity.CMP_MST;

/**
 * ����Ȗ� �o�͂���/�o�͂��Ȃ� �̑I���R���|�[�l���g�̃R���g���[���N���X�ł��B
 * 
 * @author AIS
 */
public class TBreakDownItemOutputSelectFieldCtrl extends TAppletClientBase {

	/** 
	 * ��ЃR���g���[���}�X�^Entity�ł��B<br>
	 * ���YEntity�ɂ��������ăt�B�[���h������������܂��B
	 */
	protected CMP_MST cmpMst = null;

	/** ����Ȗ� �o�͂���/�o�͂��Ȃ� �̑I���R���|�[�l���g */
	protected TBreakDownItemOutputSelectField field;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param itemField
	 */
	public TBreakDownItemOutputSelectFieldCtrl(TBreakDownItemOutputSelectField field) {
		this.field = field;
		setCmpMst(getDefaultCmpMst());
	}

	/**
	 * �f�t�H���g�̉�ЃR���g���[���}�X�^Entity��Ԃ��܂��B<br>
	 * �Z�b�g�����l�̓��O�C����񂩂�擾����<br>
	 * �u����Ȗږ��́v�A�u����Ȗڂ��g�p���邩<br>
	 * �݂̂ł��̂Œ��ӂ��ĉ������B
	 * @return �f�t�H���g�̉�ЃR���g���[���}�X�^Entity 
	 */
	public CMP_MST getDefaultCmpMst() {

		CMP_MST cmpMst = new CMP_MST();
		if (TClientLoginInfo.getInstance().getCompanyInfo().isUseBreakDownItem()) {
			cmpMst.setCMP_UKM_KBN(1);
		} else {
			cmpMst.setCMP_UKM_KBN(0);
		}
		cmpMst.setCMP_UKM_NAME(TClientLoginInfo.getInstance().getCompanyInfo().getBreakDownItemName());
		return cmpMst;
	}

	/**
	 * ������
	 *
	 */
	public void init() {
		field.initComponent();
		field.rdoNotOutput.setSelected(true);
		field.setVisible(getCmpMst().getCMP_UKM_KBN() == 1);
	}

	/**
	 * @return ��ЃR���g���[���}�X�^Entity��߂��܂��B
	 */
	public CMP_MST getCmpMst() {
		return cmpMst;
	}

	/**
	 * @param cmpMst ��ЃR���g���[���}�X�^Entity��ݒ肵�܂��B
	 */
	public void setCmpMst(CMP_MST cmpMst) {
		this.cmpMst = cmpMst;
	}

	/**
	 * ����Ȗڂ��o�͂��邩��Ԃ��܂��B
	 * @return true�Ȃ�Ԃ��Afalse�Ȃ�Ԃ��Ȃ�
	 */
	public boolean isOutput() {
		return field.rdoOutput.isSelected();
	}

}
