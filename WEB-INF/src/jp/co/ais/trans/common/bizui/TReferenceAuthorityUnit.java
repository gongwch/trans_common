package jp.co.ais.trans.common.bizui;

import java.awt.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.Util;

/**
 * 参照権限フィールドコンポーネント
 * 
 * @author ookawara
 */
public class TReferenceAuthorityUnit extends TPanel implements TInterfaceLangMessageID {

	/** シリアルUID */
	private static final long serialVersionUID = 6975993877695662983L;

	/**
	 * コンストラクタ
	 */
	public TReferenceAuthorityUnit() {
		super();

		initComponents();
	}

	/**
	 * 画面構築
	 */
	private void initComponents() {
		GridBagConstraints gridBagConstraints;

		basePanel = new TPanel();
		ctrlInputDepartment = new TInputDepartmentField();
		ctrlInputPerson = new TInputPersonField();

		// 初期値をセット
		ctrlInputPerson.setInputDepartmentCode(Util.avoidNull(ctrlInputDepartment.getField().getText()));

		ctrlInputDepartment.addCallControl(new CallBackListener() {

			public void after() {
				ctrlInputPerson.setInputDepartmentCode(Util.avoidNull(ctrlInputDepartment.getField().getText()));
			}
		});

		setLayout(new GridBagLayout());

		basePanel.setLayout(new GridBagLayout());
		gridBagConstraints = new GridBagConstraints();
		add(basePanel, new GridBagConstraints());

		ctrlInputDepartment.setButtonSize(85);
		ctrlInputDepartment.setFieldSize(75);
		ctrlInputDepartment.setMaxLength(10);
		ctrlInputDepartment.setImeMode(false);
		ctrlInputDepartment.setNoticeSize(135);
		ctrlInputDepartment.setTabEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(ctrlInputDepartment, gridBagConstraints);

		ctrlInputPerson.setButtonSize(85);
		ctrlInputPerson.setFieldSize(75);
		ctrlInputPerson.setMaxLength(10);
		ctrlInputPerson.setImeMode(false);
		ctrlInputPerson.setNoticeSize(135);
		ctrlInputPerson.setTabEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(ctrlInputPerson, gridBagConstraints);
	}

	/**
	 * サイズをセット(Small:伝票サイズ)
	 */
	public void setSizeSmall() {
		this.setMaximumSize(new Dimension(310, 40));
		this.setMinimumSize(new Dimension(310, 40));
		this.setPreferredSize(new Dimension(310, 40));

		GridBagConstraints gridBagConstraints;

		basePanel.setLayout(new GridBagLayout());

		basePanel.setMaximumSize(new Dimension(310, 40));
		basePanel.setMinimumSize(new Dimension(310, 40));
		basePanel.setPreferredSize(new Dimension(310, 40));
		ctrlInputDepartment.setButtonSize(85);
		ctrlInputDepartment.setFieldSize(75);
		ctrlInputDepartment.setNoticeSize(135);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(0, 10, 0, 20);
		basePanel.add(ctrlInputDepartment, gridBagConstraints);

		ctrlInputPerson.setButtonSize(85);
		ctrlInputPerson.setFieldSize(75);
		ctrlInputPerson.setNoticeSize(135);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(0, 10, 0, 20);
		basePanel.add(ctrlInputPerson, gridBagConstraints);
	}

	/**
	 * 一括入力フィールドサイズ設定
	 * 
	 * @param size サイズ
	 */
	public void setFieldSize(int size) {
		ctrlInputDepartment.setFieldSize(size);
		ctrlInputPerson.setFieldSize(size);
	}

	/**
	 * 一括名称フィールドサイズ設定
	 * 
	 * @param size サイズ
	 */
	public void setNoticeSize(int size) {
		ctrlInputDepartment.setNoticeSize(size);
		ctrlInputPerson.setNoticeSize(size);
	}

	/**
	 * 「入力部門」コンポーネントを返す
	 * 
	 * @return 入力部門フィールド
	 */
	public TInputDepartmentField getTInputDepartmentField() {
		return this.ctrlInputDepartment;
	}

	/**
	 * 「入力者」コンポーネントを返す
	 * 
	 * @return 入力者フィールド
	 */
	public TInputPersonField getTInputPersonField() {
		return this.ctrlInputPerson;
	}

	/**
	 * 「パネル」コンポーネントを返す
	 * 
	 * @return パネル
	 */
	public TPanel getTBasePanel() {
		return this.basePanel;
	}

	/**
	 * 科目フィールドから値を取得
	 * 
	 * @return 科目コード
	 */
	public String getItemCode() {
		return ctrlInputDepartment.getField().getText();
	}

	/**
	 * 補助科目フィールドから値を取得
	 * 
	 * @return 補助科目コード
	 */
	public String getSubItemCode() {
		return ctrlInputPerson.getField().getText();
	}

	/**
	 * パネル全体 タブ移動順番号を設定する.
	 * 
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 * @param no
	 */
	public void setTabControlNo(int no) {
		ctrlInputDepartment.setTabControlNo(no);
		ctrlInputPerson.setTabControlNo(no);
	}

	/**
	 * コードの存在のチェックを行わないモードへ変更
	 */
	public void setNonCheckMode() {
		ctrlInputDepartment.setChekcMode(false);
		ctrlInputPerson.setChekcMode(false);
	}

	private TInputDepartmentField ctrlInputDepartment;

	private TInputPersonField ctrlInputPerson;

	private TPanel basePanel;

}
