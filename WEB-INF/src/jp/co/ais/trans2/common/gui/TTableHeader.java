package jp.co.ais.trans2.common.gui;

import java.awt.event.*;

import javax.swing.table.*;

/**
 * TTable�`��p�w�b�_�[
 */
public class TTableHeader extends JTableHeader {

	/** ��b�X�v���b�h */
	public BaseTable base;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param base ��b�X�v���b�h
	 */
	@Deprecated
	public TTableHeader(BaseTable base) {
		this.base = base;
	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param cm
	 * @param base ��b�X�v���b�h
	 */
	public TTableHeader(TableColumnModel cm, BaseTable base) {
		super(cm);

		this.base = base;
	}

	/**
	 * @see javax.swing.table.JTableHeader#getToolTipText(java.awt.event.MouseEvent)
	 */
	@Override
	public String getToolTipText(MouseEvent event) {

		if (base != null && base.adapter != null) {
			return base.adapter.getHeaderToolTipText(event);
		}

		return super.getToolTipText(event);
	}

}
