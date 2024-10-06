package jp.co.ais.trans.common.bizui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;

import jp.co.ais.trans.common.bizui.ctrl.TBreakDownItemOutputSelectFieldCtrl;
import jp.co.ais.trans.common.gui.TPanel;
import jp.co.ais.trans.common.gui.TRadioButton;
import jp.co.ais.trans.master.entity.CMP_MST;

/**
 * 内訳科目 出力する/出力しない の選択コンポーネントです。
 * @author AIS
 *
 */
public class TBreakDownItemOutputSelectField extends TPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = -4232877318775150603L;

	/** コントローラ */
	protected TBreakDownItemOutputSelectFieldCtrl ctrl = null;

	/**
	 * 
	 * @param
	 */
	public TBreakDownItemOutputSelectField() {
		ctrl = getController();
		ctrl.init();
	}

	/**
	 * 初期化します。<br>
	 * 内訳科目を使わない設定の場合、フィールド自体が非表示となります。
	 */
	public void initComponent() {

		setLayout(new GridBagLayout());

		CMP_MST cmpMst = ctrl.getCmpMst();

		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		setLangMessageID(cmpMst.getCMP_UKM_NAME());

		GridBagConstraints gridBagConstraints;

		setMaximumSize(new Dimension(180, 45));
		setMinimumSize(new Dimension(180, 45));
		setPreferredSize(new Dimension(180, 45));

		// 出力する
		rdoOutput = new TRadioButton();
		rdoOutput.setLangMessageID("C01158");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 5, 0);
		add(rdoOutput, gridBagConstraints);
		
		// 出力しない
		rdoNotOutput = new TRadioButton();
		rdoNotOutput.setLangMessageID("C01157");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		add(rdoNotOutput, gridBagConstraints);

		bg = new ButtonGroup();
		bg.add(rdoOutput);
		bg.add(rdoNotOutput);
	}

	/**
	 * コントローラのファクトリメソッドです。
	 * @return コントローラ
	 */
	protected TBreakDownItemOutputSelectFieldCtrl getController() {
		return new TBreakDownItemOutputSelectFieldCtrl(this);
	}

	/**
	 * 内訳科目を出力するかを返します。
	 * @return trueなら返す、falseなら返さない
	 */
	public boolean isOutput() {
		return ctrl.isOutput();
	}

	/** 出力する */
	public TRadioButton rdoOutput;

	/** 出力しない */
	public TRadioButton rdoNotOutput;

	/** ButtonGroup */
	public ButtonGroup bg;

}
