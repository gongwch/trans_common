package jp.co.ais.trans2.master.ui;

import java.awt.*;

import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �v���O�����}�X�^�̕ҏW���<br>
 * �V�X�e���敪�g�p��
 * 
 * @author AIS
 */
public class MG0241ProgramMasterDialog extends MG0240ProgramMasterDialog {

	/** �V�X�e���敪 */
	public TSystemDivisionReference ctrlSystemDiv;

	/**
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0241ProgramMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		super.initComponents();

		ctrlSystemDiv = new TSystemDivisionReference();
	}

	@Override
	public void allocateComponents() {
		super.allocateComponents();

		// �V�X�e���敪
		ctrlSystemDiv.setLocation(ctrlSystem.getX(), ctrlSystem.getY());
		ctrlSystemDiv.getController().setShow3rdColumn(false);
		pnlBody.remove(ctrlSystem);
		pnlBody.add(ctrlSystemDiv);

		// �߂�{�^�����ؕs�v
		btnClose.setForClose(true);

	}

	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlSystemDiv.setTabControlNo(i++);
		ctrlProgramCode.setTabControlNo(i++);
		ctrlProgramName.setTabControlNo(i++);
		ctrlProgramNames.setTabControlNo(i++);
		ctrlProgramNamek.setTabControlNo(i++);
		ctrlComment.setTabControlNo(i++);
		ctrlModuleName.setTabControlNo(i++);
		dtBeginDate.setTabControlNo(i++);
		dtEndDate.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}
