package jp.co.ais.trans2.master.ui;

import jp.co.ais.trans2.common.model.ui.*;

/**
 * �v���O�����}�X�^�̎w����ʃ��C�A�E�g<br>
 * �V�X�e���敪�g�p��
 * 
 * @author AIS
 */
public class MG0241ProgramMasterPanel extends MG0240ProgramMasterPanel {

	/** �V�X�e���敪 */
	public TSystemDivisionReference ctrlSystemDiv;

	@Override
	public void initComponents() {
		super.initComponents();

		ctrlSystemDiv = new TSystemDivisionReference();
	}

	@Override
	public void allocateComponents() {
		super.allocateComponents();

		// �V�X�e���敪
		pnlSearchCondition.remove(ctrlSystem);
		ctrlSystemDiv.setLocation(20, 20);
		pnlSearchCondition.add(ctrlSystemDiv);
		ctrlSystemDiv.getController().setShow3rdColumn(false);

	}

	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlSystemDiv.setTabControlNo(i++);
		ctrlProgramRange.setTabControlNo(i++);
		chkOutputTermEnd.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}

}
