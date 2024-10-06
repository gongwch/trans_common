package jp.co.ais.trans.common.gui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * JComboBox�ɁA�^�u���C���^�[�t�F�C�X��ǉ�����ComboBox.
 */
public class TComboBox extends JComboBox implements TInterfaceTabControl, TTableComponent {

	/**  */
	protected int tabControlNo = -1;

	/**  */
	protected boolean isTabEnabled = true;

	/**  */
	protected int rowIndex = -1;

	/** TableCellEditor���p�� */
	protected boolean tableCellEditor = false;

	/**
	 * Constructor.
	 */
	public TComboBox() {
		super();

		// �t�@���N�V�����L�[�p���X�i
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {
				handleKeyPressed(evt);
			}
		});

		// �t�H�[�J�X������Ƃ��S�I�����
		this.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent evt) {
				handleFocusGained();
			}
		});

		// �}�E�X�z�C�[���ł̒l�ύX
		this.addMouseWheelListener(new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent e) {
				JComboBox source = (JComboBox) e.getSource();
				if (!source.hasFocus()) {
					return;
				}

				int ni = source.getSelectedIndex() + e.getWheelRotation();
				if (ni >= 0 && ni < source.getItemCount()) {
					source.setSelectedIndex(ni);
				}
			}
		});

	}

	/**
	 * �t�@���N�V�����L�[����.
	 * 
	 * @param evt �L�[�C�x���g
	 */
	protected void handleKeyPressed(KeyEvent evt) {

		TGuiUtil.transferFocusByEnterKey(this, evt);
		TGuiUtil.dispatchPanelBusinessFunctionKey(this, evt);
	}

	/**
	 * focus gained����.
	 */
	protected void handleFocusGained() {

		this.getEditor().selectAll();
	}

	/**
	 * add key listener
	 * 
	 * @param listener
	 */
	@Override
	public void addKeyListener(KeyListener listener) {

		this.getEditor().getEditorComponent().addKeyListener(listener);
		super.addKeyListener(listener);
	}

	/**
	 * remove focus listener
	 * 
	 * @param listener
	 */
	@Override
	public void removeKeyListener(KeyListener listener) {

		this.getEditor().getEditorComponent().removeKeyListener(listener);
		super.removeKeyListener(listener);
	}

	/**
	 * add focus listener
	 * 
	 * @param listener
	 */
	@Override
	public void addFocusListener(FocusListener listener) {

		this.getEditor().getEditorComponent().addFocusListener(listener);
		super.addFocusListener(listener);
	}

	/**
	 * remove focus listener
	 * 
	 * @param listener
	 */
	@Override
	public void removeFocusListener(FocusListener listener) {

		this.getEditor().getEditorComponent().removeFocusListener(listener);
		super.removeFocusListener(listener);
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#getTabControlNo()
	 */
	public int getTabControlNo() {
		return this.tabControlNo;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		this.tabControlNo = no;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#isTabEnabled()
	 */
	public boolean isTabEnabled() {
		return this.isTabEnabled;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#setTabEnabled(boolean)
	 */
	public void setTabEnabled(boolean bool) {
		this.isTabEnabled = bool;
	}

	/**
	 * �l(�L�[)�E�\��������ǉ�
	 * 
	 * @param text �e�L�X�g
	 * @param value �l
	 */
	public void addTextValueItem(Object value, String text) {

		addItem(new TTextValue(text, value));
	}

	/**
	 * ���X�g���v�f�S�Ă̒l��ǉ�
	 * 
	 * @param itemList �l���X�g
	 */
	public void addItemList(List<Object> itemList) {
		for (Object obj : itemList) {
			addItem(obj);
		}
	}

	/**
	 * �l���X�g�̃f�[�^���f���ݒ�
	 * 
	 * @param itemList �f�[�^���f��
	 */
	public void setModel(List<Object> itemList) {
		DefaultComboBoxModel model = new DefaultComboBoxModel();

		for (Object value : itemList) {
			model.addElement(value);
		}

		setModel(model);
	}

	/**
	 * �l���X�g�̃f�[�^���f���ݒ�
	 * 
	 * @param itemList �f�[�^���f��
	 */
	public void setModel(Object[] itemList) {
		setModel(new DefaultComboBoxModel(itemList));
	}

	/**
	 * �l(�L�[)�E�\�������̃f�[�^���f���ݒ�
	 * 
	 * @param itemMap �f�[�^���f��
	 */
	public void setModel(Map<Object, String> itemMap) {
		DefaultComboBoxModel model = new DefaultComboBoxModel();

		for (Object key : itemMap.keySet()) {
			model.addElement(new TTextValue(itemMap.get(key), key));
		}

		setModel(model);
	}

	/**
	 * �l(�L�[)�E�\�������̃f�[�^���f���ݒ�
	 * 
	 * @param texts �\���������X�g
	 * @param values �l���X�g
	 */
	public void setModel(String[] texts, Object[] values) {
		DefaultComboBoxModel model = new DefaultComboBoxModel();

		for (int i = 0; i < texts.length; i++) {
			model.addElement(new TTextValue(texts[i], values[i]));
		}

		setModel(model);
	}

	/**
	 * �I������Ă���A�C�e�����擾
	 * 
	 * @return �A�C�e��
	 */
	@Override
	public Object getSelectedItem() {

		Object item = super.getSelectedItem();

		if (item instanceof TTextValue) {
			return ((TTextValue) item).getText();
		}

		return item;
	}

	/**
	 * �I������Ă���l���擾
	 * 
	 * @return �l
	 */
	public Object getSelectedItemValue() {
		Object item = super.getSelectedItem();

		if (item instanceof TTextValue) {
			return ((TTextValue) item).getValue();
		}

		return item;
	}

	/**
	 * �I������Ă���TextValue���擾<br>
	 * �l��TextValue�ł͂Ȃ��ꍇ�Anull��Ԃ�.
	 * 
	 * @return TextValue
	 */
	public TTextValue getSelectedTextValue() {
		Object item = super.getSelectedItem();

		if (item instanceof TTextValue) {
			return ((TTextValue) item);
		}

		return null;
	}

	/**
	 * �\�������őI����ύX����
	 * 
	 * @param text �\������
	 */
	public void setSelectedText(String text) {
		if (text == null) {
			return;
		}

		ComboBoxModel model = super.getModel();

		// �o�^Item�����݂��Ȃ��ꍇ�A�����Ȃ��B
		if (model == null || model.getSize() == 0) {
			return;
		}

		// �w��̕�����Ɠ���Item���s��I����Ԃɂ���B
		for (int i = 0; i < model.getSize(); i++) {
			Object item = model.getElementAt(i);

			if (item instanceof TTextValue) {
				String txt = ((TTextValue) item).getText();

				if (txt != null && text.equals(txt)) {
					super.setSelectedItem(item);
					return;
				}

			} else {
				super.setSelectedItem(text);
				return;
			}
		}
	}

	/**
	 * �l���w�肵�đI����ύX����
	 * 
	 * @param value �l
	 */
	public void setSelectedItemValue(Object value) {
		if (value == null) {
			return;
		}

		ComboBoxModel model = super.getModel();

		// �o�^Item�����݂��Ȃ��ꍇ�A�����Ȃ��B
		if (model == null || model.getSize() == 0) {
			return;
		}

		// �w��̕�����Ɠ���ItemValue���s��I����Ԃɂ���B
		for (int i = 0; i < model.getSize(); i++) {
			Object item = model.getElementAt(i);

			if (item instanceof TTextValue) {
				Object v = ((TTextValue) item).getValue();

				if (v != null && value.equals(v)) {
					super.setSelectedItem(item);
					return;
				}

			} else {
				super.setSelectedItem(value);
				return;
			}
		}
	}

	/**
	 * �\��������S�ĕԂ�.
	 * 
	 * @return ���X�g
	 */
	public List<String> getTextList() {

		List<String> list = new LinkedList<String>();

		ComboBoxModel model = super.getModel();

		if (model == null || model.getSize() == 0) {
			return list;
		}

		// �w��̕�����Ɠ���ItemValue���s��I����Ԃɂ���B
		for (int i = 0; i < model.getSize(); i++) {
			Object item = model.getElementAt(i);

			if (item instanceof TTextValue) {
				String text = ((TTextValue) item).getText();
				list.add(text);

			} else {
				list.add(item.toString());
			}
		}

		return list;
	}

	/**
	 * �w�蕶�����\�����X�g�Ɋ܂܂�Ă��邩�ǂ���
	 * 
	 * @param text ����
	 * @return true:�܂�
	 */
	public boolean containsText(String text) {
		return getTextList().contains(text);
	}

	/**
	 * �l��S�ĕԂ�.
	 * 
	 * @return ���X�g
	 */
	public List<Object> getItemValueList() {

		List<Object> list = new LinkedList<Object>();

		ComboBoxModel model = super.getModel();

		if (model == null || model.getSize() == 0) {
			return list;
		}

		// �w��̕�����Ɠ���ItemValue���s��I����Ԃɂ���B
		for (int i = 0; i < model.getSize(); i++) {
			Object item = model.getElementAt(i);

			if (item instanceof TTextValue) {
				Object value = ((TTextValue) item).getValue();
				list.add(value);

			} else {
				list.add(item);
			}
		}

		return list;
	}

	/**
	 * �w��l�����X�g�Ɋ܂܂�Ă��邩�ǂ���
	 * 
	 * @param value �l
	 * @return true:�܂�
	 */
	public boolean containsValue(Object value) {
		return getItemValueList().contains(value);
	}

	/**
	 * �\�������ƒl��ێ�����N���X
	 */
	public static class TTextValue {

		/** �\������ */
		protected String view;

		/** �l */
		protected Object value;

		/**
		 * �R���X�g���N�^
		 * 
		 * @param text �\������
		 */
		public TTextValue(String text) {
			this(text, text);
		}

		/**
		 * �R���X�g���N�^
		 * 
		 * @param text �\������
		 * @param value �l
		 */
		public TTextValue(String text, Object value) {
			this.view = text;
			this.value = value;
		}

		/**
		 * �\�������擾
		 * 
		 * @return �\������
		 */
		public String getText() {
			return view;
		}

		/**
		 * �\�������ݒ�
		 * 
		 * @param text �\������
		 */
		public void setText(String text) {
			this.view = text;
		}

		/**
		 * �l�擾
		 * 
		 * @return �l
		 */
		public Object getValue() {
			return value;
		}

		/**
		 * �l�ݒ�
		 * 
		 * @param value �l
		 */
		public void setValue(Object value) {
			this.value = value;
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return this.view;
		}

		/**
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {

			if (obj == null || !(obj instanceof TTextValue)) {
				return false;
			}

			if (super.equals(obj)) {
				return true;
			}

			TTextValue target = (TTextValue) obj;
			return equalsValue(target.value);
		}

		/**
		 * �l�̔�r
		 * 
		 * @param targetValue �Ώےl
		 * @return true:����
		 */
		public boolean equalsValue(Object targetValue) {
			if (targetValue == null) {
				return this.value == null;

			} else if (this.value == null) {
				return false;
			}

			return this.value.equals(targetValue);
		}
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellRenderer(jp.co.ais.trans2.common.gui.TTable)
	 */
	public TableCellRenderer getCellRenderer(TTable tbl) {
		return new TComboBoxRenderer(this, tbl);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellEditor(jp.co.ais.trans2.common.gui.TTable)
	 */
	public TableCellEditor getCellEditor(TTable tbl) {

		tableCellEditor = true;
		return new TComboBoxEditor(this, tbl);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getRowIndex()
	 */
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#setRowIndex(int)
	 */
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	/**
	 * ����������align getter
	 * 
	 * @return ����������align
	 */
	public int getDefaultCellRendererHorizontalAlignment() {
		return SwingConstants.CENTER;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#isTableCellEditor()
	 */
	public boolean isTableCellEditor() {
		return tableCellEditor;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#setTableCellEditor(boolean)
	 */
	public void setTableCellEditor(boolean tableCellEditor) {
		this.tableCellEditor = tableCellEditor;
	}
}
