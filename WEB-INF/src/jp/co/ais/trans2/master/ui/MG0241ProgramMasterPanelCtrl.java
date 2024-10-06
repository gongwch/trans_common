package jp.co.ais.trans2.master.ui;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.model.program.*;

/**
 * �v���O�����}�X�^�̃R���g���[��<br>
 * �V�X�e���敪�g�p��
 * 
 * @author AIS
 */
public class MG0241ProgramMasterPanelCtrl extends MG0240ProgramMasterPanelCtrl {

	@Override
	public void start() {

		try {

			// �w����ʐ���
			createMainView();

			// �w����ʂ�����������
			initMainView();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	@Override
	protected void createMainView() {
		mainView = new MG0241ProgramMasterPanel();
		addMainViewEvent();
	}

	/**
	 * �w����ʂœ��͂��ꂽ�v���O�����̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	@Override
	protected ProgramSearchCondition getSearchCondition() {

		ProgramSearchCondition condition = super.getSearchCondition();
		condition.setSystemCode(((MG0241ProgramMasterPanel) mainView).ctrlSystemDiv.getCode());

		return condition;

	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	@Override
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0241ProgramMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent();

	}

	/**
	 * �ҏW��ʂ�����������
	 * 
	 * @param mode_ ���샂�[�h�B
	 * @param program �v���O�����B�C���A���ʂ̏ꍇ�͓��Y�v���O��������ҏW��ʂɃZ�b�g����B
	 */
	@Override
	protected void initEditView(Mode mode_, Program program) {
		super.initEditView(mode_, program);

		if (Mode.MODIFY == mode_ || Mode.COPY == mode_) {

			TSystemDivisionReference ctrlSystemDiv = ((MG0241ProgramMasterDialog) editView).ctrlSystemDiv;

			ctrlSystemDiv.setCode(program.getSysCode()); // �V�X�e���敪�R�[�h
			ctrlSystemDiv.refleshEntity();
			ctrlSystemDiv.setEntity(ctrlSystemDiv.getEntity());

		}

	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ�v���O������Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�v���O����
	 */
	@Override
	protected Program getInputedProgram() {

		Program program = super.getInputedProgram();
		program.setSysCode(((MG0241ProgramMasterDialog) editView).ctrlSystemDiv.getCode()); // �V�X�e���敪�R�[�h

		return program;

	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	@Override
	protected boolean isInputRight() throws Exception {

		// �V�X�e���敪�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(((MG0241ProgramMasterDialog) editView).ctrlSystemDiv.getCode())) {
			showMessage(editView, "I00037", "C00980");
			((MG0241ProgramMasterDialog) editView).ctrlSystemDiv.code.requestFocus();
			return false;
		}

		// �v���O�����R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlProgramCode.getValue())) {
			showMessage(editView, "I00037", "C00818");
			editView.ctrlProgramCode.requestFocus();
			return false;
		}

		// �v���O�������̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlProgramName.getValue())) {
			showMessage(editView, "I00037", "C00819");
			editView.ctrlProgramName.requestFocus();
			return false;
		}

		// �v���O�������̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlProgramNames.getValue())) {
			showMessage(editView, "I00037", "C00820");
			editView.ctrlProgramNames.requestFocus();
			return false;
		}

		// �v���O�����������̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlProgramNamek.getValue())) {
			showMessage(editView, "I00037", "C00821");
			editView.ctrlProgramNamek.requestFocus();
			return false;
		}

		// ���[�h���W���[���t�@�C�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlModuleName.getValue())) {
			showMessage(editView, "I00037", "C00823");
			editView.ctrlModuleName.requestFocus();
			return false;
		}

		// �J�n�N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			showMessage(editView, "I00037", "C00055");
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// �I���N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtEndDate.getValue())) {
			showMessage(editView, "I00037", "C00261");
			editView.dtEndDate.requestTextFocus();
			return false;
		}

		if (!Util.isSmallerThenByYMD(editView.dtBeginDate.getValue(), editView.dtEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.dtBeginDate.requestFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓���v���O���������ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {

			ProgramSearchCondition condition = new ProgramSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(editView.ctrlProgramCode.getValue());

			List<Program> list = getProgram(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00055", "C00818");
				editView.ctrlProgramCode.requestTextFocus();
				return false;
			}
		}

		return true;

	}

	/**
	 * �v���O���������ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param program �v���O����
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�v���O����
	 */
	@Override
	protected String[] getRowData(Program program) {
		return new String[] { program.getSysCode(), program.getCode(), program.getName(), program.getNames(),
				program.getNamek(), program.getComment(), program.getLoadClassName(),
				DateUtil.toYMDString(program.getTermFrom()), DateUtil.toYMDString(program.getTermTo()) };
	}

}
