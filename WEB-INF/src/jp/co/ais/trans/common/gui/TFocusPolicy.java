package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.text.*;

import com.klg.jclass.table.*;

import jp.co.ais.trans2.common.gui.*;

/**
 * �^�u���t�H�[�J�X�ړ��|���V�[ <br>
 * �|���V�[�P�ʂł�synchronized��ǉ������B
 */
public class TFocusPolicy extends FocusTraversalPolicy {

	/** �� */
	protected static final int SEQ_NORMAL = 1;

	/** �t�� */
	protected static final int SEQ_REVERSE = -1;

	/** �R���|�[�l���g���X�g */
	protected List<Component> listTab = null;

	/** �C���f�b�N�X */
	protected int index = 0;

	/**
	 * constructor.
	 * 
	 * @param list �R���|�[�l���g�̃��X�g
	 */
	public TFocusPolicy(List<Component> list) {
		super();
		this.listTab = list;
	}

	/**
	 * �擪�R���|�[�l���g�擾
	 * 
	 * @param focusCycleRoot
	 * @return �擪�R���|�[�l���g
	 */
	@Override
	public Component getFirstComponent(Container focusCycleRoot) {

		Component comp = null;

		synchronized (this) {

			comp = this.getComponentByIndex(0, SEQ_NORMAL);
		}

		return comp;
	}

	/**
	 * �ŏI�R���|�[�l���g�擾
	 * 
	 * @param focusCycleRoot
	 * @return �ŏI�R���|�[�l���g
	 */
	@Override
	public Component getLastComponent(Container focusCycleRoot) {

		Component comp = null;

		synchronized (this) {

			comp = this.getComponentByIndex(-1, SEQ_REVERSE);
		}

		return comp;
	}

	/**
	 * ���R���|�[�l���g�擾
	 * 
	 * @param focusCycleRoot ���[�g�R���e�i
	 * @param aComponent ���݂̃R���|�[�l���g
	 */
	@Override
	public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {

		Component comp = null;

		synchronized (this) {

			int pos = this.indexOf(aComponent);

			// combobox�ȂǁA�^�u��component list�ɓo�^����Ă��Ȃ�
			// object��aComponent�ɐݒ肳��Ă��邱�Ƃ�����B
			if (pos < 0) {
				pos = this.index;
			}

			comp = this.getComponentByIndex(pos + 1, SEQ_NORMAL);
		}

		return comp;
	}

	/**
	 * �O�R���|�[�l���g�擾
	 * 
	 * @param focusCycleRoot ���[�g�R���e�i
	 * @param aComponent ���݂̃R���|�[�l���g
	 */
	@Override
	public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {

		Component comp = null;

		synchronized (this) {

			int pos = this.indexOf(aComponent);

			// combobox�ȂǁA�^�u��component list�ɓo�^����Ă��Ȃ�
			// object��aComponent�ɐݒ肳��Ă��邱�Ƃ�����B
			if (pos < 0) {
				pos = this.index;
			}

			comp = this.getComponentByIndex(pos - 1, SEQ_REVERSE);
		}

		return comp;
	}

	/**
	 * �f�t�H���g�擾
	 * 
	 * @param focusCycleRoot ���[�g�R���e�i
	 */
	@Override
	public Component getDefaultComponent(Container focusCycleRoot) {

		Component comp = null;

		synchronized (this) {

			comp = this.getComponentByIndex(0, SEQ_NORMAL);
		}

		return comp;
	}

	/**
	 * ���̗L����Component���擾����B
	 * 
	 * @param pos �w��index
	 * @param seq �����l
	 * @return �L��Component
	 */
	protected Component getComponentByIndex(int pos, int seq) {

		if (this.countActiveComponent() <= 0) {
			return null;
		}

		for (int listPos = this.trimIndex(pos); listPos < this.listTab.size(); listPos += seq) {

			// 0�Ԗڂ܂�Active�łȂ��R���|�[�l���g�̏ꍇ�A���[�܂Ŗ߂�
			if (listPos < 0) {
				listPos = this.listTab.size() - 1;
			}

			Component comp = this.listTab.get(listPos);

			if (this.isActiveComponent(comp)) {
				// �L���ȃR���|�[�l���g����

				this.index = listPos;

				// combobox�̂Ƃ�
				if (comp instanceof JComboBox) {

					JComboBox combo = (JComboBox) comp;
					if (combo.isEditable()) {
						return combo.getEditor().getEditorComponent();
					}
				}

				return comp;
			}
		}

		// �L���ȃR���|�[�l���g�Ȃ�
		return null;
	}

	/**
	 * �Y�����̃T�C�N���b�N�B
	 * 
	 * @param pos �w��index
	 * @return �␳index
	 */
	protected int trimIndex(int pos) {

		if (this.isNullOrEmpty()) {
			return 0;
		}

		if (pos < 0) {
			pos += this.listTab.size();
		}

		return pos % this.listTab.size();
	}

	/**
	 * �^�u���ړ��ł���(focus�ł���)component�̐����擾����B
	 * 
	 * @return ��
	 */
	protected int countActiveComponent() {

		if (this.listTab == null) {
			return 0;
		}

		int count = 0;

		for (Iterator i = this.listTab.iterator(); i.hasNext();) {
			Component comp = (Component) i.next();
			if (this.isActiveComponent(comp)) {
				count += 1;
			}
		}

		return count;
	}

	/**
	 * �^�u���ړ��ł���(focus�ł���)component���`�F�b�N����B
	 * 
	 * @param comp �R���|�[�l���g
	 * @return true:�L��
	 */
	protected boolean isActiveComponent(Component comp) {

		if (comp == null) {
			return false;
		}

		// not enable.
		if ((!comp.isFocusable()) || (!comp.isShowing()) || (!comp.isEnabled()) || (!comp.isVisible())) {
			return false;
		}

		// textfield, textarea
		if (comp instanceof JTextComponent) {

			// not editable.
			if (!((JTextComponent) comp).isEditable()) {
				return false;
			}
		}

		// popupCalendar
		if (comp instanceof TPopupCalendar) {

			// **
			// not editable.
			// **
			if (!((TPopupCalendar) comp).isEditable()) {
				return false;
			}
		}

		// table
		if (comp instanceof BaseTable) {

			// row = 0
			if (((BaseTable) comp).getRowCount() == 0) {
				return false;
			}
		}

		// table
		if (comp instanceof JCTable) {

			// row = 0
			if (((JCTable) comp).getNumRows() == 0) {
				return false;
			}
		}

		return true;
	}

	/**
	 * ���X�g�̏��
	 * 
	 * @return true:null�܂���size=0
	 */
	protected boolean isNullOrEmpty() {

		if (this.listTab == null) {
			return true;
		}

		if (this.listTab.size() == 0) {
			return true;
		}

		return false;
	}

	/**
	 * comboBox�̂Ƃ��AEditorComponent���r����B
	 * 
	 * @param comp
	 * @return index
	 */
	protected int indexOf(Component comp) {

		int pos = this.listTab.indexOf(comp);

		if (0 <= pos) {
			return pos;
		}

		int cnt = 0;
		for (Iterator i = this.listTab.iterator(); i.hasNext();) {
			Component test = (Component) i.next();
			if (test == null) continue;

			if (test instanceof JComboBox) {
				JComboBox comb = (JComboBox) test;
				if (comb.getEditor() != null && comb.getEditor().getEditorComponent() != null) {
					if (comp.equals(comb.getEditor().getEditorComponent())) {
						return cnt;
					}
				}
			}
			cnt += 1;
		}

		return -1;
	}
}