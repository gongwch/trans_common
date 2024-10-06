package jp.co.ais.trans2.master.ui;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.model.program.*;

/**
 * プログラムマスタのコントローラ<br>
 * システム区分使用版
 * 
 * @author AIS
 */
public class MG0241ProgramMasterPanelCtrl extends MG0240ProgramMasterPanelCtrl {

	@Override
	public void start() {

		try {

			// 指示画面生成
			createMainView();

			// 指示画面を初期化する
			initMainView();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 指示画面のファクトリ。新規に指示画面を生成し、イベントを定義する。
	 */
	@Override
	protected void createMainView() {
		mainView = new MG0241ProgramMasterPanel();
		addMainViewEvent();
	}

	/**
	 * 指示画面で入力されたプログラムの検索条件を返す
	 * 
	 * @return 検索条件
	 */
	@Override
	protected ProgramSearchCondition getSearchCondition() {

		ProgramSearchCondition condition = super.getSearchCondition();
		condition.setSystemCode(((MG0241ProgramMasterPanel) mainView).ctrlSystemDiv.getCode());

		return condition;

	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	@Override
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0241ProgramMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent();

	}

	/**
	 * 編集画面を初期化する
	 * 
	 * @param mode_ 操作モード。
	 * @param program プログラム。修正、複写の場合は当該プログラム情報を編集画面にセットする。
	 */
	@Override
	protected void initEditView(Mode mode_, Program program) {
		super.initEditView(mode_, program);

		if (Mode.MODIFY == mode_ || Mode.COPY == mode_) {

			TSystemDivisionReference ctrlSystemDiv = ((MG0241ProgramMasterDialog) editView).ctrlSystemDiv;

			ctrlSystemDiv.setCode(program.getSysCode()); // システム区分コード
			ctrlSystemDiv.refleshEntity();
			ctrlSystemDiv.setEntity(ctrlSystemDiv.getEntity());

		}

	}

	/**
	 * 編集画面で入力されたプログラムを返す
	 * 
	 * @return 編集画面で入力されたプログラム
	 */
	@Override
	protected Program getInputedProgram() {

		Program program = super.getInputedProgram();
		program.setSysCode(((MG0241ProgramMasterDialog) editView).ctrlSystemDiv.getCode()); // システム区分コード

		return program;

	}

	/**
	 * 編集画面で入力した値が妥当かをチェックする
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	@Override
	protected boolean isInputRight() throws Exception {

		// システム区分が未入力の場合エラー
		if (Util.isNullOrEmpty(((MG0241ProgramMasterDialog) editView).ctrlSystemDiv.getCode())) {
			showMessage(editView, "I00037", "C00980");
			((MG0241ProgramMasterDialog) editView).ctrlSystemDiv.code.requestFocus();
			return false;
		}

		// プログラムコードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlProgramCode.getValue())) {
			showMessage(editView, "I00037", "C00818");
			editView.ctrlProgramCode.requestFocus();
			return false;
		}

		// プログラム名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlProgramName.getValue())) {
			showMessage(editView, "I00037", "C00819");
			editView.ctrlProgramName.requestFocus();
			return false;
		}

		// プログラム略称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlProgramNames.getValue())) {
			showMessage(editView, "I00037", "C00820");
			editView.ctrlProgramNames.requestFocus();
			return false;
		}

		// プログラム検索名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlProgramNamek.getValue())) {
			showMessage(editView, "I00037", "C00821");
			editView.ctrlProgramNamek.requestFocus();
			return false;
		}

		// ロードモジュールファイル名が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlModuleName.getValue())) {
			showMessage(editView, "I00037", "C00823");
			editView.ctrlModuleName.requestFocus();
			return false;
		}

		// 開始年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			showMessage(editView, "I00037", "C00055");
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// 終了年月日が未入力の場合エラー
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

		// 新規、複写の場合は同一プログラムが既に存在したらエラー
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
	 * プログラム情報を一覧に表示する形式に変換し返す
	 * 
	 * @param program プログラム
	 * @return 一覧に表示する形式に変換されたプログラム
	 */
	@Override
	protected String[] getRowData(Program program) {
		return new String[] { program.getSysCode(), program.getCode(), program.getName(), program.getNames(),
				program.getNamek(), program.getComment(), program.getLoadClassName(),
				DateUtil.toYMDString(program.getTermFrom()), DateUtil.toYMDString(program.getTermTo()) };
	}

}
