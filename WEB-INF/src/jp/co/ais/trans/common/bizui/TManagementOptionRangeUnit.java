package jp.co.ais.trans.common.bizui;

import java.awt.*;
import javax.swing.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.TransUtil;

/**
 * 任意管理コンポーネント
 * 
 * @author ookawara
 */
public class TManagementOptionRangeUnit extends TPanel implements TInterfaceLangMessageID {

	/** シリアルUID */
	protected static final long serialVersionUID = 6975993877695662983L;

	/**
	 * コンストラクタ
	 */
	public TManagementOptionRangeUnit() {
		super();

		initComponents();
	}

	/**
	 * 画面構築
	 */
	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		basePanel = new TPanel();
		managementOption1 = new TManagementOptionRangeField();
		managementOption2 = new TManagementOptionRangeField();
		managementOption3 = new TManagementOptionRangeField();

		setLayout(new GridBagLayout());

		basePanel.setLayout(new GridBagLayout());
		basePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		basePanel.setLangMessageID("C00814");
		basePanel.setMaximumSize(new Dimension(325, 235));
		basePanel.setMinimumSize(new Dimension(325, 235));
		basePanel.setPreferredSize(new Dimension(325, 235));
		add(basePanel, new GridBagConstraints());

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(-10, 0, 0, 10);
		basePanel.add(managementOption1, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 10);
		basePanel.add(managementOption2, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 10);
		basePanel.add(managementOption3, gridBagConstraints);
	}

	/**
	 * 「任意管理フィールド１」コンポーネントを返す
	 * 
	 * @return 任意管理フィールド１フィールド
	 */
	public TManagementOptionRangeField getTManagementOption1() {
		return this.managementOption1;
	}

	/**
	 * 「任意管理フィールド２」コンポーネントを返す
	 * 
	 * @return 任意管理フィールド２フィールド
	 */
	public TManagementOptionRangeField getTManagementOption2() {
		return this.managementOption2;
	}

	/**
	 * 「任意管理フィールド３」コンポーネントを返す
	 * 
	 * @return 任意管理フィールド３フィールド
	 */
	public TManagementOptionRangeField getTManagementOption3() {
		return this.managementOption3;
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
	 * パネル全体 タブ移動順番号を設定する.
	 * 
	 * @param no
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		managementOption1.setTabControlNo(no);
		managementOption2.setTabControlNo(no);
		managementOption3.setTabControlNo(no);
	}

	/**
	 * コードの存在のチェックを行わないモードへ変更
	 */
	public void setNonCheckMode() {
		managementOption1.setNonCheckMode();
		managementOption2.setNonCheckMode();
		managementOption3.setNonCheckMode();
	}

	protected TManagementOptionRangeField managementOption1;

	protected TManagementOptionRangeField managementOption2;

	protected TManagementOptionRangeField managementOption3;

	protected TPanel basePanel;

	/**
	 * 指定番目の管理選択コンポーネントを返す
	 * @param index 上から何番目か
	 * @return 指定番目の管理選択コンポーネント
	 */
	public TManagementOptionRangeField getTManagementOptionRangeFieldAt(int index) {
		if (index == 1) {
			return managementOption1;
		} else if (index == 2) {
			return managementOption2;
		} else if (index == 3) {
			return managementOption3;
		}
		return null;
	}

	/**
	 * 指定番目の管理選択コンポーネントで選択された集計単位を返す。
	 * return 指定番目の管理選択コンポーネントで選択された集計単位
	 */
	public TransUtil.SumGroup getSelectedSumGroupAt(int index) {
		return getTManagementOptionRangeFieldAt(index).getSelectedSumGroup();
	}

	/**
	 * 上から順に入力されているかを返す
	 * @return 上から順に入力されている場合true、入力されていない場合false 
	 */
	public boolean isEnteredAtTheTop() {

		int noneOrdinal = TransUtil.SumGroup.None.ordinal();
		int knrKbn1 = getSelectedSumGroupAt(1).ordinal();
		int knrKbn2 = getSelectedSumGroupAt(2).ordinal();
		int knrKbn3 = getSelectedSumGroupAt(3).ordinal();

		if (knrKbn1 == noneOrdinal && (knrKbn2 != noneOrdinal || knrKbn3 != noneOrdinal)) {
			return false;
		}

		if (knrKbn2 == noneOrdinal && knrKbn3 != noneOrdinal) {
			return false;
		}

		return true;
	}

	/**
	 * 管理指定に重複があれば、重複しているフィールドの番号を返します。<br>
	 * 0の場合、重複はありません。
	 * @return 0(重複なし) それ以外は重複があったフィールドの番号
	 */
	public int getSameKnrSelectedIndex() {

		int noneOrdinal = TransUtil.SumGroup.None.ordinal();;
		int knrKbn1 = getSelectedSumGroupAt(1).ordinal();
		int knrKbn2 = getSelectedSumGroupAt(2).ordinal();
		int knrKbn3 = getSelectedSumGroupAt(3).ordinal();
		if (knrKbn1 == knrKbn2 && knrKbn1 != noneOrdinal) {
			return 1;
		}
		if (knrKbn1 == knrKbn3 && knrKbn1 != noneOrdinal) {
			return 1;
		}
		if (knrKbn2 == knrKbn3 && knrKbn2 != noneOrdinal) {
			return 2;
		}
		return 0;

	}

}
