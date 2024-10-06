package jp.co.ais.trans2.master.ui;

import jp.co.ais.trans2.common.model.ui.*;

/**
 * プログラムマスタの指示画面レイアウト<br>
 * システム区分使用版
 * 
 * @author AIS
 */
public class MG0241ProgramMasterPanel extends MG0240ProgramMasterPanel {

	/** システム区分 */
	public TSystemDivisionReference ctrlSystemDiv;

	@Override
	public void initComponents() {
		super.initComponents();

		ctrlSystemDiv = new TSystemDivisionReference();
	}

	@Override
	public void allocateComponents() {
		super.allocateComponents();

		// システム区分
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
