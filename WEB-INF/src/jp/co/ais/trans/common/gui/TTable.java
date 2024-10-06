package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.gui.table.*;
import jp.co.ais.trans.common.util.*;

/**
 * JCListTable�ɁA�^�u���A�s�񃉃x����MessageID�C���^�[�t�F�C�X��ǉ�����Table.
 */
public class TTable extends JCListTable implements TInterfaceTableLangMessageID, TInterfaceTabControl, JCScrollListener {

	/** �����X�N���[���o�[ */
	private static final int VERTSB = 1;

	/** ���I���̏ꍇ�̃Z���i�sor��j���l */
	private static final int CELL_UNSELECTION = -999;

	/** Enter�L�[�������̑J�ڐ���w�肷�� */
	private static int enterTraverseDefault = JCTableEnum.TRAVERSE_DOWN;

	/** SHIFT+Enter�L�[�������̑J�ڐ���w�肷�� */
	private static int enterReverseDefault = JCTableEnum.TRAVERSE_UP;

	/** �\��t�H���g�J���[ */
	private static Color foreground = ClientConfig.getTableLabelFontColor();

	/** �\��o�b�N�J���[ */
	private static Color background = ClientConfig.getTableLabelColor();

	/** �Z��1�J���[ */
	private static Color cellBackground1 = ClientConfig.getTableCellBackColor1();

	/** �Z��2�J���[ */
	private static Color cellBackground2 = ClientConfig.getTableCellBackColor2();

	/** �Z���t�H���g�J���[ */
	private static Color cellFont = ClientConfig.getTableCellFontColor();

	static {
		// Enter�L�[�������̑J�ڐ���w�肷��
		String type = ClientConfig.getTableEnterTraverse();

		if ("TRAVERSE_RIGHT".equals(type)) {

			enterTraverseDefault = JCTableEnum.TRAVERSE_RIGHT;
			enterReverseDefault = JCTableEnum.TRAVERSE_LEFT;

		} else if ("TRAVERSE_LEFT".equals(type)) {

			enterTraverseDefault = JCTableEnum.TRAVERSE_LEFT;
			enterReverseDefault = JCTableEnum.TRAVERSE_RIGHT;

		} else if ("TRAVERSE_UP".equals(type)) {

			enterTraverseDefault = JCTableEnum.TRAVERSE_UP;
			enterReverseDefault = JCTableEnum.TRAVERSE_DOWN;
		}

		// �I�����J���[
		UIManager.put("Table.selectionBackground", ClientConfig.getTableSelectColor());
		UIManager.put("Table.selectionForeground", ClientConfig.getTableSelectCellFontColor());
	}

	/** �^�uNo */
	protected int tabControlNo = -1;

	/** �^�u�J�� */
	protected boolean isTabEnabled = true;

	/** �����I�� */
	protected boolean isSelectMultiLine = false;

	/** �s�ԍ��\��/��\�� */
	protected boolean isRowLabelNumber = true;

	/** �S�s�`�F�b�NON/OFF���[�h */
	protected boolean isAllCheck = true;

	/** �s�^�C�g�� */
	protected String[] rowTitleMessageID = null;

	/** ��^�C�g�� */
	protected String[] colTitleMessageID = null;

	/** �`�F�b�N�{�b�N�X�J�����ԍ� */
	protected int checkBoxColumnNumber = 0;

	/** �ԍ��t�����n�߂�s�ԍ� */
	protected int startLabelNumberRow = 0;

	/** �s���x�� */
	protected Vector rowLabels;

	/** �񃉃x�� */
	protected Vector columnLabels;

	/** �Z���̏c�T�C�Y */
	protected CellSize cellSize;

	/** �����A���{�^�� */
	protected TButton btn = null;

	/** true:Enter�L�[�Ń{�^�����s */
	protected boolean isEnterToButton = false;

	/** �X�N���[���o�[�̐����l��ێ� */
	protected int currentVValue;

	/** �X�N���[���o�[�̉��l��ێ� */
	protected int currentHValue;

	/** �\�[�g�@�\ */
	protected boolean sort;

	/** �\�[�g�����������X�g */
	protected List<Boolean> sortList;

	/** ���t�^�J�����C���f�b�N�X */
	protected List<Integer> dateSortIndexList = new LinkedList<Integer>();

	/** ���z�^�J�����C���f�b�N�X */
	protected List<Integer> numberSortIndexList = new LinkedList<Integer>();

	/** Vector�^�f�[�^�\�[�X�i�\�[�g�p�j */
	private JCVectorDataSource ds;

	/** ENTER�łǂ��Ɉړ����邩 */
	protected int enterTraverseType = enterTraverseDefault;

	/** SHIFT+ENTER�łǂ��Ɉړ����邩 */
	protected int enterReverseType = enterReverseDefault;

	/** �e�[�u����T�C�Y�������ݒ肷�邩�ǂ��� true:�����ݒ� */
	protected boolean autoColumnWidthSet = false;

	/**
	 * Constructor.
	 */
	public TTable() {
		super();
		initComponents();
		// �X�N���[���o�[�p�̃��X�i�[�̒ǉ�
		super.addScrollListener(this);
	}

	/**
	 * �F���Z�b�g
	 */
	public void resetColor() {
		CellStyleModel labelStyle = this.getDefaultLabelStyle();
		labelStyle.setForeground(foreground);
		labelStyle.setBackground(background);

		CellStyleModel cellStyle = this.getDefaultCellStyle();
		cellStyle.setForeground(cellFont);
		cellStyle.setRepeatBackgroundColors(new Color[] { cellBackground1, cellBackground2 });

		repaint();
	}

	/**
	 * TTable�f�t�H���g�ݒ�
	 */
	private void initComponents() {

		// �e��\���ݒ�
		this.setFocusIndicator(JCTableEnum.FOCUS_NONE);
		this.setSelectionType(JCListTable.SELECT_ROW);
		this.setResizeInteractive(false);

		// �s�̕\��
		this.setRowLabelDisplay(true);
		this.setCharWidth(JCTableEnum.LABEL, 3);

		// ��̕\��
		this.setColumnLabelDisplay(true);

		// �t�H�[�J�X
		this.setFocusable(true);

		// ���x���܂݂̑I��
		this.setSelectIncludeLabels(true);

		// �s��T�C�Y�̕ύX����
		this.setAllowCellResize(JCTableEnum.RESIZE_COLUMN);

		// Scroll�o�[�\��
		this.setVertSBDisplay(JCTableEnum.SCROLLBAR_AS_NEEDED);
		this.setHorizSBDisplay(JCTableEnum.SCROLLBAR_AS_NEEDED);

		// AutoEdit��W����
		this.setAutoEdit(true);

		// �\��X�^�C��
		CellStyleModel labelStyle = this.getDefaultLabelStyle();
		labelStyle.setClipHints(CellStyleModel.SHOW_NONE);
		labelStyle.setFont(labelStyle.getFont().deriveFont(Font.BOLD));
		labelStyle.setHorizontalAlignment(JCTableEnum.CENTER);
		labelStyle.setVerticalAlignment(JCTableEnum.BOTTOM);
		labelStyle.setForeground(foreground);
		labelStyle.setBackground(background);

		// �Z���X�^�C��
		CellStyleModel cellStyle = this.getDefaultCellStyle();
		cellStyle.setVerticalAlignment(JCTableEnum.BOTTOM);
		cellStyle.setClipHints(CellStyleModel.SHOW_NONE);

		Color[] cellBackColor = { cellBackground1, cellBackground2 };
		cellStyle.setForeground(cellFont);
		cellStyle.setRepeatBackgroundColors(cellBackColor);
		cellStyle.setRepeatBackground(JCTableEnum.REPEAT_ROW);

		// �Z���{�[�_�[
		cellStyle.setCellBorder(new JCCellBorder(JCTableEnum.BORDER_PLAIN));

		// �s�̍���
		this.setPixelHeight(JCTableEnum.ALL, 20);

		// �e�탊�X�i

		// �}�E�X���X�i�i�{�^�����s�j
		this.addMouseListener(new MouseButtonConnectedListener());

		// �}�E�X���X�i�i�`�F�b�N�{�b�N�X�j
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent ev) {

				// �C�x���g�����ʒu�ɂ���Z���̈ʒu�i�s�E��j���擾
				JCCellPosition cp = TTable.this.XYToCell(ev.getX(), ev.getY());

				if (cp == null) {
					return;
				}

				// �f�[�^�A�w�b�_�����݂��Ȃ��ʒu���N���b�N�����ꍇ�̓N���b�N�����͍s�Ȃ�Ȃ��B
				if (cp.row == CELL_UNSELECTION && cp.column == CELL_UNSELECTION) {
					return;
				}

				// �s���x���N���b�N
				if (cp.column == JCTableEnum.LABEL) {
					TTable.this.traverse(cp.row, 0, true, true);
					TTable.this.setRowSelection(getCurrentRow(), getCurrentRow());
				}

				// �E/���N���b�N�Ή�
				if (ev.getButton() == MouseEvent.BUTTON2 || ev.getButton() == MouseEvent.BUTTON3) {

					// ���x���̏ꍇ�͑ΏۊO
					if (cp.column != JCTableEnum.LABEL && cp.row != JCTableEnum.LABEL) {
						TTable.this.setCurrentCell(cp.row, 0);
					}

					TTable.this.setRowSelection(getCurrentRow(), getCurrentRow());
				}

				// �w�b�_�[���N���b�N
				if (ev.getButton() == MouseEvent.BUTTON1 && cp.row == JCTableEnum.LABEL) {

					int type = -1; // ��̃R���|�[�l���g�^�C�v(-1=�Ȃ�)
					Component comp = null; // �R���|�[�l���g�����݂���ŏ�ʍs�̃I�u�W�F�N�g

					if (sort || (isAllCheck && cp.column == checkBoxColumnNumber)) {

						// ��Ԑ擪�̃R���|�[�l���g��F��������.(��J�����ƃ\�[�g���l��)
						for (int i = 0; i < TTable.this.getNumRows(); i++) {
							comp = TTable.this.getComponent(i, cp.column);

							if (comp == null || !comp.isEnabled()) {
								continue;
							}

							if (comp instanceof TCheckBox) {
								type = 1;
								break;

							} else if (comp instanceof TTableRadioGroup) {
								type = 2;
								break;
							}
						}
					}

					// �`�F�b�N�{�b�N�X�S�`�F�b�N����
					if (isAllCheck && cp.column == checkBoxColumnNumber) {

						try {
							JCVectorDataSource ds_ = (JCVectorDataSource) TTable.this.getDataSource();

							switch (type) {
								case 1: // �`�F�b�N�{�b�N�X
									boolean isCheck = ((TCheckBox) comp).isSelected();

									for (int i = 0; i < ds_.getNumRows(); i++) {
										TCheckBox checkBox = (TCheckBox) TTable.this.getComponent(i,
											checkBoxColumnNumber);
										if (checkBox != null && checkBox.isEnabled()) {
											checkBox.setSelected(!isCheck);
										}
									}

									setCurrentCell(0, checkBoxColumnNumber);
									setRowSelection(0, 0);

									break;

								// �ۗ�
								// case 2: // ���W�I�O���[�v
								// // �擪��INDEX�ɍ��킹��
								// int index = ((TTableRadioGroup) comp).getSelectedIndex();
								//
								// for (int i = 0; i < ds_.getNumRows(); i++) {
								// TTableRadioGroup radio = (TTableRadioGroup) TTable.this.getComponent(i,
								// checkBoxColumnNumber);
								// if (radio != null) {
								// radio.setSelectedIndex(index + 1);
								// }
								// }
								//
								// setCurrentCell(0, 0);
								// setRowSelection(0, 0);
								//
								// break;
							}

							// �f�[�^�̕�
							if (!(comp instanceof TTableCheckBox)) {
								Object obj = TTable.this.getDataSource().getTableDataItem(0, checkBoxColumnNumber);

								if (obj instanceof Boolean) {
									ds_.setCell(JCTableEnum.ALLCELLS, checkBoxColumnNumber, !((Boolean) obj));
								}
							}

						} catch (Exception e) {
							ev.consume();
						}
					}

					// �\�[�g����(�R���|�[�l���g�L��̏ꍇ�͍s�Ȃ�Ȃ�)
					if (sort && type == -1) {

						boolean isAsc = sortList.get(cp.column);

						Sort.sortByColumn(TTable.this, cp.column, isAsc ? Sort.ASCENDING : Sort.DESCENDING,
							new ObjectComparator(cp.column));

						// �A�ԐU��Ȃ���
						if (isRowLabelNumber) {
							TableDataView tdv = (TableDataView) TTable.this.getDataView();
							for (int i = 0; i < ds.getNumRows(); i++) {
								ds.setRowLabel(tdv.getDataRow(i), i + 1);
							}
						}

						sortList.set(cp.column, !isAsc);
					}
				}
			}
		});

		// �L�[���X�i�[�ݒ�
		setKeyListener();

		// �Z���I�����X�i
		this.addTraverseCellListener(new JCTraverseCellAdapter() {

			@Override
			public void traverseCell(JCTraverseCellEvent ev) {

				// �}���`���C���ݒ�ȊO�́A��s�I������
				if (!isSelectMultiLine) {
					TTable.this.setRowSelection(TTable.this.getCurrentRow(), TTable.this.getCurrentRow());
				}
			}
		});

		// row�I�𐧌䃊�X�i
		this.addSelectListener(new JCSelectAdapter() {

			@Override
			public void select(JCSelectEvent ev) {

				if (!TTable.this.isSelectMultiLine) {
					// 1�s�����I���ł��Ȃ��悤�ɂ���
					if (ev.getAction() == JCSelectEvent.EXTEND && Math.abs(ev.getStartRow() - ev.getEndRow()) > 0) {
						ev.setCancelled(true);
					}
				}
			}
		});
	}

	/**
	 * �L�[���X�i�[
	 */
	protected void setKeyListener() {
		// �L�[�p���X�i
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent ev) {

				// �t�@���N�V�����L�[�p�n���h��
				handleKeyPressed(ev);

				switch (ev.getKeyCode()) {
					case KeyEvent.VK_ENTER:
						// Enter�L�[�����i�f�[�^�ďo���Ȃ��ꍇ�͎��s�I���j
						ev.consume();

						if (TTable.this.btn != null && isEnterToButton) {
							TTable.this.btn.doClick();

						} else {
							TTable.this.traverse(ev.isShiftDown() ? enterReverseType : enterTraverseType);
							TTable.this.requestFocus();// �t�H�[�J�X���V�[�g�ɖ߂�
						}

						TTable.this.setRowSelection(TTable.this.getCurrentRow(), TTable.this.getCurrentRow());

						break;
				}
			}

			@Override
			public void keyReleased(KeyEvent ev) {

				switch (ev.getKeyCode()) {
					case KeyEvent.VK_UP:
					case KeyEvent.VK_DOWN:
						if (ev.isShiftDown()) {
							return;
						}

					case KeyEvent.VK_PAGE_UP:
					case KeyEvent.VK_PAGE_DOWN:
					case KeyEvent.VK_TAB:
					case KeyEvent.VK_ESCAPE:

						if (TTable.this.getSelectionPolicy() != JCTableEnum.SELECT_NONE) {
							// �e�[�u�����I���\�̍ۂ͌��ݍs���đI��
							TTable.this.setRowSelection(TTable.this.getCurrentRow(), TTable.this.getCurrentRow());
						}

						break;

					case KeyEvent.VK_SPACE:
						// �X�y�[�X�L�[����(�`�F�b�N�{�b�N�X������ꍇ����)
						try {
							JCVectorDataSource ds_ = (JCVectorDataSource) TTable.this.getDataSource();

							if (getSelectionPolicy() == JCTableEnum.SELECT_NONE) {
								// �I���Ȃ���Ԃ̏ꍇ�́A�J�[�\�����
								int row = getCurrentRow();
								int column = getCurrentColumn();
								Component comp = TTable.this.getComponent(row, column);

								if (comp == null || !comp.isEnabled()) {
									return;
								}

								if (comp instanceof TCheckBox) {
									TCheckBox checkBox = (TCheckBox) comp;
									checkBox.setSelected(!checkBox.isSelected());

								} else if (comp instanceof TTableRadioGroup) {
									TTableRadioGroup radio = (TTableRadioGroup) comp;
									radio.nextSelected();
								}

								// �f�[�^�̕�
								if (!(comp instanceof TTableCheckBox)) {
									Object obj = TTable.this.getDataSource().getTableDataItem(row, column);
									if (obj instanceof Boolean) {
										ds_.setCell(row, column, !((Boolean) obj));
									}
								}

							} else {
								// �I������̏ꍇ�́A�I���s�S��
								Collection<JCCellRange> selectRanges = getSelectedCells();

								int[] rows = TTable.this.getSelectedRows();

								for (int row : rows) {
									// �R���|�[�l���g�̕�
									Component comp = TTable.this.getComponent(row, checkBoxColumnNumber);

									if (comp == null || !comp.isEnabled()) {
										continue;
									}

									if (comp instanceof TCheckBox) {
										TCheckBox checkBox = (TCheckBox) comp;
										checkBox.setSelected(!checkBox.isSelected());

									} else if (comp instanceof TTableRadioGroup) {
										TTableRadioGroup radio = (TTableRadioGroup) comp;
										radio.nextSelected();
									}

									// �f�[�^�̕�
									if (!(comp instanceof TTableCheckBox)) {
										Object obj = TTable.this.getDataSource().getTableDataItem(row,
											checkBoxColumnNumber);

										if (obj instanceof Boolean) {
											ds_.setCell(row, checkBoxColumnNumber, !((Boolean) obj));
										}
									}
								}

								setSelectedCells(selectRanges);
							}

						} catch (Exception e) {
							ev.consume();
						}

						break;
				}
			}
		});
	}

	/**
	 * �e�R���e�i�����ǂ�A�ŏ���Frame��Ԃ�.
	 * 
	 * @return ���t���[��
	 */
	public Frame getParentFrame() {
		for (Container p = this.getParent(); p != null; p = p.getParent()) {
			if (p instanceof Frame) {
				return (Frame) p;
			}
		}
		return null;
	}

	/**
	 * �s�^�C�g��messageID����.
	 */
	public void setRowTitleMessageID(List<String> list) {
		if (list == null) {
			this.rowTitleMessageID = null;
			return;
		}
		this.rowTitleMessageID = (String[]) list.toArray();
	}

	/**
	 * ��^�C�g��messageID����.
	 */
	public void setColumnTitleMessageID(List<String> list) {
		if (list == null) {
			this.colTitleMessageID = null;
			return;
		}
		this.colTitleMessageID = (String[]) list.toArray();
	}

	/**
	 * �s�^�C�g��messageID����.
	 */
	public void setRowTitleMessageID(String[] messageIDs) {
		this.rowTitleMessageID = messageIDs;
	}

	/**
	 * ��^�C�g��messageID����.
	 */
	public void setColumnTitleMessageID(String[] messageIDs) {
		this.colTitleMessageID = messageIDs;
	}

	/**
	 * �s�^�C�g��messageID����.
	 */
	public String[] getRowTitleMessageID() {
		return this.rowTitleMessageID;
	}

	/**
	 * ��^�C�g��messageID����.
	 */
	public String[] getColumnTitleMessageID() {
		return this.colTitleMessageID;
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
	 * 
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
	 * �e�[�u���s�𕡐��s�I��(��΂��I���͕s��)<br>
	 * setSelectMultiRange()�Ƃ̕��p�͕s��<br>
	 * false���w�肳�ꂽ�ꍇ�A�f�t�H���g�̒P�s�I���ɖ߂�
	 * 
	 * @param bool �����s�Ftrue /�P�s�Ffalse
	 */
	public void setSelectMaltiLine(boolean bool) {
		this.isSelectMultiLine = bool;

		setSelectionPolicy(SelectionModel.SELECT_RANGE);
	}

	/**
	 * �e�[�u���s�𕡐��s�͈̔͑I��(��΂��I�����\)<br>
	 * setSelectMaltiLine()�Ƃ̕��p�͕s��<br>
	 * false���w�肳�ꂽ�ꍇ�A�f�t�H���g�̒P�s�I���ɖ߂�
	 * 
	 * @param bool �����͈́Ftrue /�P�s�Ffalse
	 */
	public void setSelectMultiRange(boolean bool) {
		this.isSelectMultiLine = bool;

		setSelectionPolicy(bool ? SelectionModel.SELECT_MULTIRANGE : SelectionModel.SELECT_RANGE);
	}

	/**
	 * row��label�ɍ��Ԃ�U�邩�I�� ���f�t�H���g�ݒ�͍��Ԃ���
	 * 
	 * @param bool ���Ԃ���Ftrue /���ԂȂ��Ffalse
	 */
	public void setRowLabelNumber(boolean bool) {
		this.isRowLabelNumber = bool;
	}

	/**
	 * �ԍ��t�����n�߂�s�ԍ�
	 * 
	 * @param startLabelNumberRow �s�ԍ�
	 */
	public void setStartLabelNumberRow(int startLabelNumberRow) {
		this.startLabelNumberRow = startLabelNumberRow;
	}

	/**
	 * �`�F�b�N�{�b�N�X�̂����ԍ� ���f�t�H���g��0(��ԍ�)
	 * 
	 * @param number ��ԍ�
	 */
	public void setCheckBoxColumnNumber(int number) {
		this.checkBoxColumnNumber = number;
	}

	/**
	 * �`�F�b�N�{�b�N�X�̂őS�`�F�b�N�ł��邩 ���f�t�H���g�͂ł���
	 * 
	 * @param bool �ł���Ftrue /�ł��Ȃ��Ffalse
	 */
	public void setAllCheck(boolean bool) {
		this.isAllCheck = bool;
	}

	/**
	 * �f�[�^�\�[�X�擾
	 * 
	 * @return TableDataModel
	 */
	@Override
	public TableDataModel getDataSource() {
		return super.getDataSource();
	}

	/**
	 * �e�[�u���Ƀf�[�^�\�[�X���Z�b�g
	 * 
	 * @param ds �f�[�^�\�[�X
	 */
	public void setDataSource(JCVectorDataSource ds) {
		super.setDataSource(ds);

		resetDataSource(ds);
	}

	/**
	 * ���ɃZ�b�g�����f�[�^�\�[�X���Đݒ肷��.<br>
	 * ��DataSource���̂��ς���Ă���ꍇ�͕ύX����Ȃ��̂Œ���.<br>
	 * ��CellEditor�̒��ł�setDataSource()��NG.<br>
	 * 
	 * @param ds_ �f�[�^�\�[�X
	 */
	public void resetDataSource(JCVectorDataSource ds_) {
		this.ds = ds_;

		// �\�[�g�̃e�[�u����������ݒ�

		sortList = new LinkedList<Boolean>();
		for (int i = 0; i < ds_.getNumColumns(); i++) {
			sortList.add(false);
		}

		// row��label�ɍ��Ԃ�U��
		if (isRowLabelNumber) {

			for (int i = 0, row = startLabelNumberRow; row <= this.getNumRows(); i++, row++) {
				ds_.setRowLabel(row, String.valueOf(i + 1));
			}
		} else {

			// �J�������x���̍Đݒ�
			Vector dsRLabels = ds_.getRowLabels();
			if (dsRLabels != null && !dsRLabels.isEmpty()) {
				rowLabels = dsRLabels;
			}

			if (rowLabels != null) {
				ds_.setRowLabels(rowLabels);
			}
		}

		// �J�������x���̍Đݒ�
		Vector dsCLabels = ds_.getColumnLabels();
		if (dsCLabels != null && !dsCLabels.isEmpty()) {
			columnLabels = dsCLabels;
		}

		// �J�������x���ݒ�
		if (columnLabels != null) {
			ds_.setColumnLabels(columnLabels);
		}

		// �T�C�Y�w�肪����΁A�s�̏c�T�C�Y��S�s�ɐݒ�
		if (cellSize != null) {
			for (int i = 0, row = startLabelNumberRow; row <= this.getNumRows(); i++, row++) {
				setRowCellSize(row, cellSize);
			}
		}

		// �ێ����Ă������l���X�N���[���o�[�ɃZ�b�g
		super.getVertSB().setValue(currentVValue);
		super.getHorizSB().setValue(currentHValue);
	}

	/**
	 * �t�@���N�V�����L�[����.
	 * 
	 * @param ev �L�[�C�x���g
	 */
	protected void handleKeyPressed(KeyEvent ev) {

		TGuiUtil.dispatchPanelBusinessFunctionKey(this, ev);
	}

	// �֗��p���\�b�h

	/**
	 * �������⏕. �X�v���b�h�̃J�����ݒ�
	 * 
	 * @param columnLabelMessageIDs �w�b�_�[�^�C�g��ID
	 * @param columnCharWidths �Z����
	 */
	public void initSpreadSheet(String[] columnLabelMessageIDs, int[] columnCharWidths) {
		// �^�C�g����WordID�̃Z�b�g
		if (columnLabelMessageIDs != null) {
			this.setColumnTitleMessageID(columnLabelMessageIDs);
			this.setColumnLabels(columnLabelMessageIDs);
		}

		this.setColumnWidths(columnCharWidths);
	}

	/**
	 * �Z�����̃Z�b�g
	 * 
	 * @param widths �����X�g
	 */
	public void setColumnWidths(int... widths) {
		// �Z�����̃Z�b�g
		for (int i = 0; i < widths.length; i++) {
			if (widths[i] == 0) {
				this.setColumnHidden(i, true);
			} else {
				this.setCharWidth(i, widths[i]);
			}

			this.setMinWidth(i, -1);
		}
	}

	/**
	 * @see com.klg.jclass.table.JCTable#setCharWidth(int, int)
	 */
	@Override
	public void setCharWidth(int i, int j) {
		super.setCharWidth(i, (int) (j * 1.2));
	}

	/**
	 * �s�N�Z���ł̃Z�����̃Z�b�g
	 * 
	 * @param widths �����X�g
	 */
	public void setColumnPixelWidths(int... widths) {

		// �Z�����̃Z�b�g
		for (int i = 0; i < widths.length; i++) {
			this.setPixelWidth(i, widths[i]);
		}
	}

	/**
	 * ����̗�(�s�N�Z��)���擾����(TTable���x���ݒ肪�O��)
	 * 
	 * @return �񕝃��X�g
	 */
	public int[] getColumnPixelWidths() {
		getColumnWidthValues();

		int[] widths = new int[columnLabels.size()];
		for (int i = 0; i < columnLabels.size(); i++) {
			widths[i] = getPixelWidth(i);
		}

		return widths;
	}

	/**
	 * �w�b�_�̍����ݒ�
	 * 
	 * @param size ����
	 */
	public void setHeaderHeight(int size) {
		this.setPixelHeight(JCTableEnum.LABEL, size);
	}

	/**
	 * �f�[�^�s�̍����ݒ�.<br>
	 * �i�w�b�_�͕ύX���Ȃ��j
	 * 
	 * @param size ����
	 */
	public void setRowHeight(int size) {
		int headSize = this.getPixelHeight(JCTableEnum.LABEL);
		this.setPixelHeight(JCTableEnum.ALL, size);
		setHeaderHeight(headSize);
	}

	/**
	 * �s���x���̐ݒ�
	 * 
	 * @param titles �s���x��
	 */
	public void setRowLabels(String[] titles) {

		this.rowLabels = new Vector(titles.length);

		for (String title : titles) {
			this.rowLabels.add(title);
		}
	}

	/**
	 * ��(�J����)���x���̐ݒ�
	 * 
	 * @param titles �񃉃x��
	 */
	public void setColumnLabels(String[] titles) {

		this.columnLabels = new Vector(titles.length);

		for (String title : titles) {
			this.columnLabels.add(title);
		}
	}

	/**
	 * �s�̏c�T�C�Y(����)���w��.<br>
	 * �T�C�Y�́A���s�������w�肷��.
	 * 
	 * @param size �T�C�Y(�s��)
	 */
	public void setRowHeightSize(int size) {
		this.cellSize = new CellSize(size);
	}

	/**
	 * Enter�L�[�Ń{�^�����s���s�����ǂ���
	 * 
	 * @param isConnected true:�{�^�����s���s��(�A��������)
	 */
	public void setEnterToButton(boolean isConnected) {
		this.isEnterToButton = isConnected;
	}

	/**
	 * Enter�L�[�Ń{�^�����s���s�����ǂ���
	 * 
	 * @return true:�{�^�����s���s��
	 */
	public boolean isEnterToButton() {
		return this.isEnterToButton;
	}

	/**
	 * ���胉�x���̐F��ύX����
	 * 
	 * @param labelRowNum �Ώۃ��x��Index
	 * @param color �ύX�F
	 */
	public void setLabelColor(int labelRowNum, Color color) {

		CellStyleModel model = new JCCellStyle(getDefaultLabelStyle());
		model.setForeground(color);

		setCellStyle(JCTableEnum.LABEL, labelRowNum, model);
	}

	/**
	 * �w���𒆉��񂹃X�^�C���ɕύX����
	 * 
	 * @param rowNums ��Index���X�g
	 */
	public void setCenterStyle(int... rowNums) {

		CellStyleModel model = new JCCellStyle(getDefaultCellStyle());
		model.setHorizontalAlignment(SwingConstants.CENTER);

		for (int rowNum : rowNums) {
			setCellStyle(JCTableEnum.ALLCELLS, rowNum, model);
		}
	}

	/**
	 * �w�����E�񂹃X�^�C���ɕύX����
	 * 
	 * @param rowNums ��Index���X�g
	 */
	public void setRightStyle(int... rowNums) {

		CellStyleModel model = new JCCellStyle(getDefaultCellStyle());
		model.setHorizontalAlignment(SwingConstants.RIGHT);

		for (int rowNum : rowNums) {
			setCellStyle(JCTableEnum.ALLCELLS, rowNum, model);
		}
	}

	/**
	 * �X�v���b�h���C�x���g�o�^. �w�肳�ꂽ�{�^���̉����C�x���g���N����.
	 * 
	 * @param btn_ �Ώۂ̃{�^��
	 */
	public void addSpreadSheetSelectChange(TButton btn_) {
		this.btn = btn_;
	}

	/**
	 * �}�E�X����ƃ{�^����A��������ׂ̃��X�i�[
	 */
	protected class MouseButtonConnectedListener extends MouseAdapter {

		// �}�E�X
		@Override
		public void mouseClicked(MouseEvent ev) {
			// ���N���b�N
			JCCellPosition cp = TTable.this.XYToCell(ev.getX(), ev.getY());

			if (ev.getButton() == MouseEvent.BUTTON1 && cp.row != JCTableEnum.LABEL && cp.column != JCTableEnum.LABEL) {

				// �X�v���b�h�V�[�g�Ń_�u���N���b�N���Abtn����������
				if ((ev.getClickCount() >= 2) && TTable.this.btn != null) {

					if (!TTable.this.isFocusOwner()) {
						ev.consume();
						return;
					}

					TTable.this.btn.doClick();

					try {
						Thread.sleep(100);
					} catch (InterruptedException ex) {
						// �A�����s�h�~�p�̃^�C�����O
					}
				}
			}
		}
	}

	/**
	 * �X�N���[����̏���Event
	 * 
	 * @see com.klg.jclass.table.JCScrollListener#afterScroll(com.klg.jclass.table.JCScrollEvent)
	 */
	public void afterScroll(JCScrollEvent e) {
		// �����X�N���[���o�[�̂Ƃ��̂݁A�l��ێ�
		if (e.getDirection() == VERTSB) {
			// ���݂̃X�N���[���o�[�̒l��ϐ��ɕێ�
			currentVValue = e.getValue();
		}

		if (e.getDirection() == Adjustable.HORIZONTAL) {
			currentHValue = e.getValue();
		}
	}

	/**
	 * �X�N���[�����̏���Event
	 * 
	 * @see com.klg.jclass.table.JCScrollListener#scroll(com.klg.jclass.table.JCScrollEvent)
	 */
	public void scroll(JCScrollEvent arg0) {
		// ��������(Override�p)
	}

	/**
	 * �\�[�g�@�\�t���O�ݒ�<br>
	 * �R���|�[�l���g�t���̃e�[�u���i�R���|�{�b�N�X�Ȃǁj�͐ݒ�ł��Ȃ��B
	 * 
	 * @param isSort true : �\�[�g�@�\�t��
	 */
	public void setSort(boolean isSort) {
		this.sort = isSort;
	}

	/**
	 * �\�[�g�@�\�t���O�ݒ� ��/��
	 * 
	 * @return true:��
	 */
	public boolean isSort() {
		return this.sort;
	}

	/**
	 * ���t�^�̃J�����C���f�b�N�X�w�� <br>
	 * YYYY/MM/DD�̌`����String�ϊ����A�\�������ꍇ�B
	 * 
	 * @param index
	 */
	public void setDateStringColumn(int index) {
		dateSortIndexList.add(index);
	}

	/**
	 * Number�^�̃J�����C���f�b�N�X�w��<br>
	 * ������String�ɕϊ����A�\�������J�����̏ꍇ�B
	 * 
	 * @param index
	 */
	public void setNumberStringColumn(int index) {
		numberSortIndexList.add(index);
	}

	/**
	 * Number�^�̃J����List���N���A����
	 */
	public void clearNumberStringColumn() {
		numberSortIndexList.clear();
	}

	/**
	 * ���t�^�̃J����List���N���A����
	 */
	public void clearDateStringColumn() {
		dateSortIndexList.clear();
	}

	/**
	 * �`�F�b�N�{�b�N�X�R���|�[�l���g���w��J����(�S�s)�֐ݒ肷��.<br>
	 * �J������0�Ԗڂ֐ݒ�.
	 */
	public void setCheckBoxComponents() {
		this.setCheckBoxComponents(0);
	}

	/**
	 * �`�F�b�N�{�b�N�X�R���|�[�l���g���w��J����(�S�s)�֐ݒ肷��.<br>
	 * 
	 * @param column �J����
	 */
	public void setCheckBoxComponents(int column) {
		this.setCheckBoxColumnNumber(column);

		for (int i = 0; i < this.getNumRows(); i++) {
			this.setCheckBoxComponent(i, column);
		}
	}

	/**
	 * �`�F�b�N�{�b�N�X�R���|�[�l���g���w��s�A�J�����֐ݒ肷��.<br>
	 * �I����Ԃ͔�I��.
	 * 
	 * @param row �s
	 * @param column �J����
	 */
	public void setCheckBoxComponent(int row, int column) {

		this.setCheckBoxComponent(row, column, false);
	}

	/**
	 * �`�F�b�N�{�b�N�X�R���|�[�l���g���w��s�A�J�����֐ݒ肷��.<br>
	 * 
	 * @param row �s
	 * @param column �J����
	 * @param isSelected true:�I��
	 */
	public void setCheckBoxComponent(int row, int column, boolean isSelected) {
		TCheckBox checkBox = new TTableCheckBox(this, row, column);
		checkBox.setSelected(isSelected);

		this.setComponent(row, column, checkBox);
	}

	/**
	 * �ō���(0��)�̃`�F�b�N�{�b�N�X�R���|�[�l���g�̑I����Ԃ�ύX����.
	 * 
	 * @param row �s
	 * @param isSelected true:�I��
	 */
	public void setSelectedCheckBox(int row, boolean isSelected) {
		setSelectedCheckBox(row, 0, isSelected);
	}

	/**
	 * �`�F�b�N�{�b�N�X�R���|�[�l���g�̑I����Ԃ�ύX����.
	 * 
	 * @param row �s
	 * @param column �J����
	 * @param isSelected true:�I��
	 */
	public void setSelectedCheckBox(int row, int column, boolean isSelected) {
		TCheckBox checkBox = (TCheckBox) this.getComponent(row, column);
		checkBox.setSelected(isSelected);
	}

	/**
	 * �ō���(0��)�̃`�F�b�N�{�b�N�X�R���|�[�l���g�̎w��s�A�J�����̑I����Ԃ�Ԃ�.
	 * 
	 * @param row �s
	 * @return true:�I��
	 */
	public boolean isCheckedCheckBox(int row) {
		return isCheckedCheckBox(row, 0);
	}

	/**
	 * �`�F�b�N�{�b�N�X�R���|�[�l���g�̎w��s�A�J�����̑I����Ԃ�Ԃ�.
	 * 
	 * @param row �s
	 * @param column �J����
	 * @return true:�I��
	 */
	public boolean isCheckedCheckBox(int row, int column) {
		TCheckBox checkBox = (TCheckBox) this.getComponent(row, column);

		if (checkBox == null) {
			return false;
		}

		return checkBox.isSelected();
	}

	/**
	 * �ō���(0��)�̃`�F�b�N�{�b�N�X�R���|�[�l���g�̑����Ԃ�ύX����.
	 * 
	 * @param row �s
	 * @param isEnabled true:�ҏW��
	 */
	public void setEnabledCheckBox(int row, boolean isEnabled) {
		setEnabledCheckBox(row, 0, isEnabled);
	}

	/**
	 * �`�F�b�N�{�b�N�X�R���|�[�l���g�̑����Ԃ�ύX����.
	 * 
	 * @param row �s
	 * @param column �J����
	 * @param isEnabled true:�ҏW��
	 */
	public void setEnabledCheckBox(int row, int column, boolean isEnabled) {
		TCheckBox checkBox = (TCheckBox) this.getComponent(row, column);
		checkBox.setEnabled(isEnabled);
	}

	/**
	 * ���W�I�{�^���O���[�v�R���|�[�l���g���w��J����(�S�s)�֐ݒ肷��.<br>
	 * �J������0�Ԗڂ֐ݒ�.
	 */
	public void setRadioGroupComponents() {
		this.setRadioGroupComponents(0);
	}

	/**
	 * ���W�I�{�^���O���[�v�R���|�[�l���g���w��J����(�S�s)�֐ݒ肷��.
	 * 
	 * @param column �J����
	 */
	public void setRadioGroupComponents(int column) {
		this.setCheckBoxColumnNumber(column);

		for (int i = 0; i < this.getNumRows(); i++) {
			this.setRadioGroupComponent(i, column);
		}
	}

	/**
	 * ���W�I�{�^���O���[�v�R���|�[�l���g���w��J����(�S�s)�֐ݒ肷��.
	 * 
	 * @param column �J����
	 * @param index �I�����W�I�{�^��Index
	 */
	public void setRadioGroupComponents(int column, int index) {
		this.setCheckBoxColumnNumber(column);

		for (int i = 0; i < this.getNumRows(); i++) {
			this.setRadioGroupComponent(i, column, index);
		}
	}

	/**
	 * ���W�I�{�^���O���[�v�R���|�[�l���g���w��s�A�J�����֐ݒ肷��.<br>
	 * Index�� 0�i���[�j.
	 * 
	 * @param row �s
	 * @param column �J����
	 */
	public void setRadioGroupComponent(int row, int column) {

		this.setRadioGroupComponent(row, column, 0);
	}

	/**
	 * ���W�I�{�^���O���[�v�R���|�[�l���g���w��s�A�J�����֐ݒ肷��.
	 * 
	 * @param row �s
	 * @param column �J����
	 * @param index �I�����W�I�{�^��Index
	 */
	public void setRadioGroupComponent(int row, int column, int index) {
		TTableRadioGroup radio = new TTableRadioGroup(this, row);
		radio.setSelectedIndex(index);

		this.setComponent(row, column, radio);
	}

	/**
	 * ���W�I�{�^���O���[�v�R���|�[�l���g�̑I����Ԃ�ύX����.
	 * 
	 * @param row �s
	 * @param column �J����
	 * @param index �I�����W�I�{�^��Index
	 */
	public void setSelectedRadioGroup(int row, int column, int index) {
		TTableRadioGroup radio = (TTableRadioGroup) this.getComponent(row, column);
		radio.setSelectedIndex(index);
	}

	/**
	 * ���W�I�{�^���O���[�v�R���|�[�l���g�̎w��s�A�J�����̑I����Ԃ�Ԃ�.
	 * 
	 * @param row �s
	 * @param column �J����
	 * @return �I�����W�I�{�^��Index
	 */
	public int getRadioGroupIndex(int row, int column) {
		TTableRadioGroup radio = (TTableRadioGroup) this.getComponent(row, column);
		if (radio == null) {
			return -1;
		}

		return radio.getSelectedIndex();
	}

	/**
	 * ���s�[�g�z�F�𖳌��ɂ���
	 */
	public void setRepeatBackgroundOff() {
		this.getDefaultCellStyle().setRepeatBackground(JCTableEnum.REPEAT_NONE);
	}

	/**
	 * �I���s���擾����.
	 * 
	 * @return �I���s
	 */
	public int[] getSelectedRows() {
		Set<Integer> list = new TreeSet<Integer>();

		Collection<JCCellRange> collection = getSelectedCells();

		if (collection == null) {
			return new int[0];
		}

		for (JCCellRange vtr : collection) {

			// �I���͍̂s���I����
			int start = vtr.start_row;
			int end = vtr.end_row;

			if (start > end) {
				// start��end����ւ�
				start = start ^ end;
				end = start ^ end;
				start = start ^ end;
			}

			while (start <= end) {
				list.add(start++);
			}
		}

		int[] rtArray = new int[list.size()];
		int i = 0;
		for (Integer num : list) {
			rtArray[i++] = num;
		}

		return rtArray;
	}

	/**
	 * �ŏI�I���s���擾����.(�t�H�[�J�X�s�Ƃ͕�)
	 * 
	 * @return �ŏI�I���s
	 */
	public int getLastSelectedRow() {
		Collection<JCCellRange> collection = getSelectedCells();

		if (collection == null) {
			return -999;
		}

		int endRow = -999;
		for (JCCellRange vtr : collection) {

			// �I���͍̂s���I����
			endRow = vtr.end_row;
		}

		return endRow;
	}

	/**
	 * ENTER�L�[�łǂ̃Z���Ɉړ����邩���w�肷��.<br>
	 * JCTableEnum.TRAVERSE_DOWN<br>
	 * JCTableEnum.TRAVERSE_RIGHT<br>
	 * JCTableEnum.TRAVERSE_LEFT<br>
	 * JCTableEnum.TRAVERSE_UP
	 * 
	 * @param type �ړ��^�C�v
	 */
	public void setEnterTraverseType(int type) {
		enterTraverseType = type;

		switch (type) {
			case JCTableEnum.TRAVERSE_RIGHT:
				enterReverseType = JCTableEnum.TRAVERSE_LEFT;

				break;

			case JCTableEnum.TRAVERSE_LEFT:
				enterReverseType = JCTableEnum.TRAVERSE_RIGHT;

				break;

			case JCTableEnum.TRAVERSE_UP:
				enterReverseType = JCTableEnum.TRAVERSE_DOWN;

				break;
		}
	}

	/**
	 * �X�N���[���o�[���ŏ�s�Ɉړ�����B
	 */
	public void setScrollTop() {
		JScrollBar bar = this.getVertSB();
		bar.setValue(0);
	}

	/**
	 * �X�N���[���o�[���ŉ��s�Ɉړ�����B
	 */
	public void setScrollUnder() {
		JScrollBar bar = this.getVertSB();
		bar.setValue(bar.getMaximum() - bar.getVisibleAmount());
	}

	/**
	 * �X�N���[���o�[�����Ɉړ�����B
	 */
	public void setScrollLeft() {
		JScrollBar bar = this.getHorizSB();
		bar.setValue(0);
	}

	/**
	 * �X�N���[���o�[�������ʒu�Ɉړ�����B
	 */
	public void setScrollDefault() {
		setScrollTop();
		setScrollLeft();
	}

	/**
	 * Object�̔�r�N���X
	 */
	private class ObjectComparator implements Comparator {

		/**  */
		private static final int TYPE_DATE = 1;

		/**  */
		private static final int TYPE_NUMBER = 2;

		/**  */
		private int type = 0;

		/**
		 * �R���X�g���N�^
		 * 
		 * @param index �J����Index
		 */
		public ObjectComparator(int index) {

			if (dateSortIndexList.contains(index)) {
				type = TYPE_DATE;

			} else if (numberSortIndexList.contains(index)) {
				type = TYPE_NUMBER;
			}
		}

		/**
		 * ���t��r����
		 * 
		 * @param o1 ��r�Ώۂ̍ŏ��̃I�u�W�F�N�g
		 * @param o2 ��r�Ώۂ� 2 �Ԗڂ̃I�u�W�F�N�g
		 * @return boolean ��r����
		 */
		public int compare(Object o1, Object o2) {

			// ����null�̏ꍇ����r����K�v�Ȃ��B
			if (Util.isNullOrEmpty(o1) && Util.isNullOrEmpty(o2)) {
				return 0;
			}
			// null�����͂��ꂽ�ꍇ�Anull��������������
			else if (Util.isNullOrEmpty(o1) && !Util.isNullOrEmpty(o2)) {
				return -1;
			}
			// null�����͂��ꂽ�ꍇ�Anull��������������
			else if (!Util.isNullOrEmpty(o1) && Util.isNullOrEmpty(o2)) {
				return 1;
			}

			switch (type) {
				case TYPE_DATE: // ���t
					return dateCompare(o1, o2);

				case TYPE_NUMBER: // ���l
					return numberCompare(o1, o2);

				default:
					return objectCompare(o1, o2);
			}
		}

		/**
		 * Object��r <br>
		 * �w���Object��String,BigDecimal,Date,int�^�łȂ��ꍇ�͔�r�Ȃ��B
		 * 
		 * @param o1
		 * @param o2
		 * @return ��r����
		 */
		private int objectCompare(Object o1, Object o2) {

			// String�̏ꍇ
			if (o1.getClass() == String.class) {
				String o1String = (String) o1;
				String o2String = (String) o2;
				return o1String.compareTo(o2String);
			}

			// BigDecimal�̏ꍇ
			if (o1.getClass() == BigDecimal.class) {
				BigDecimal o1Decimal = (BigDecimal) o1;
				BigDecimal o2Decimal = (BigDecimal) o2;
				return o1Decimal.compareTo(o2Decimal);
			}

			// Date�̏ꍇ
			if (o1.getClass() == Date.class) {
				Date o1Date = (Date) o1;
				Date o2Date = (Date) o2;
				return o1Date.compareTo(o2Date);
			}

			// int�̏ꍇ
			if (o1.getClass() == Integer.class) {
				Integer o1Date = (Integer) o1;
				Integer o2Date = (Integer) o2;
				return o1Date.compareTo(o2Date);
			}

			return 0;
		}

		/**
		 * ���t��r
		 * 
		 * @param o1
		 * @param o2
		 * @return ��r����
		 */
		private int dateCompare(Object o1, Object o2) {

			try {
				String o1String = (String) o1;
				String o2String = (String) o2;

				Date o1Date = DateUtil.toYMDDate(o1String);
				Date o2Date = DateUtil.toYMDDate(o2String);

				return o1Date.compareTo(o2Date);

			} catch (ParseException ex) {
				// ���t�^�ϊ��ŃG���[�����������ꍇ�A��r���ʂȂ��B
				return 0;
			}
		}

		/**
		 * �����^��String�ɕϊ������J�����̏ꍇ
		 * 
		 * @param o1
		 * @param o2
		 * @return ��r����
		 */
		private int numberCompare(Object o1, Object o2) {
			String o1String = (String) o1;
			String o2String = (String) o2;

			// �J���}���������ABigDecimal�Ƃ��Ĕ�r����B
			BigDecimal o1Number = new BigDecimal(o1String.replace(",", ""));
			BigDecimal o2Number = new BigDecimal(o2String.replace(",", ""));

			return o1Number.compareTo(o2Number);
		}
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public void setComponent(int arg0, int arg1, TTextField arg2) {

		arg2.setForeground(Color.BLACK);
		super.setComponent(arg0, arg1, arg2);

	}

	/**
	 * �e�[�u����T�C�Y�������ݒ肷�邩�ǂ���
	 * 
	 * @return true:�����ݒ�
	 */
	public boolean isAutoColumnWidthSet() {
		return autoColumnWidthSet;
	}

	/**
	 * �e�[�u����T�C�Y�������ݒ肷�邩�ǂ���
	 * 
	 * @param autoColumnWidthSet true:�����ݒ�
	 */
	public void setAutoColumnWidthSet(boolean autoColumnWidthSet) {
		this.autoColumnWidthSet = autoColumnWidthSet;
	}

}
