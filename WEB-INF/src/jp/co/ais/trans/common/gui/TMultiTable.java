package jp.co.ais.trans.common.gui;

import java.awt.event.*;
import java.util.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

/**
 * TTable�ɁA�����s�I�����\�ɂ���Table.
 */
@Deprecated
public class TMultiTable extends TTable {

	/**
	 * Constructor.
	 */
	public TMultiTable() {
		super();
		// �����I����
		super.setSelectMultiRange(true);
	}

	/**
	 * �L�[���X�i�[
	 */
	@Override
	protected void setKeyListener() {
		// �L�[�p���X�i
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent ev) {
				// �t�@���N�V�����L�[�p�n���h��
				handleKeyPressed(ev);

				// Enter�L�[�����i�f�[�^�ďo���Ȃ��ꍇ�͎��s�I���j
				if (ev.getKeyCode() == KeyEvent.VK_ENTER) {
					ev.consume();

					if (TMultiTable.this.btn != null && isEnterToButton) {
						TMultiTable.this.btn.doClick();
					} else {
						TMultiTable.this.traverse(JCTableEnum.TRAVERSE_DOWN);
						TMultiTable.this.requestFocus();// �t�H�[�J�X���V�[�g�ɖ߂�
					}
					TMultiTable.this
						.setRowSelection(TMultiTable.this.getCurrentRow(), TMultiTable.this.getCurrentRow());
				}
			}

			@Override
			public void keyReleased(KeyEvent ev) {

				// PageUp,PageDown�L�[����
				if (ev.getKeyCode() == KeyEvent.VK_PAGE_UP || ev.getKeyCode() == KeyEvent.VK_PAGE_DOWN
					|| ev.getKeyCode() == KeyEvent.VK_TAB) {
					TMultiTable.this.traverse(JCTableEnum.TRAVERSE_TO_CELL);
					TMultiTable.this
						.setRowSelection(TMultiTable.this.getCurrentRow(), TMultiTable.this.getCurrentRow());
				}

				// �X�y�[�X�L�[����(�`�F�b�N�{�b�N�X������ꍇ����)
				if (ev.getKeyCode() == KeyEvent.VK_SPACE) {

					JCVectorDataSource ds = (JCVectorDataSource) TMultiTable.this.getDataSource();

					TMultiTable table = TMultiTable.this;

					// �I�΂ꂽ�f�[�^
					Collection collection = TMultiTable.this.getSelectedCells();

					JCCellRange[] list = (JCCellRange[]) collection.toArray(new JCCellRange[collection.size()]);

					// ��s�ł��I������Ă��邩
					boolean isCheckLine = false;
					// �S�s�I������Ă��邩
					boolean isAllCheckLine = true;

					// �����I���\���̂�
					if (isSelectMultiLine) {
						// �S���`�F�b�N����Ă��邩�ǂ����m�F
						for (JCCellRange range : list) {
							// �I���̏��߂̍s
							int startRow = range.start_row;
							// �I���͍̂s���I����
							int endRow = range.end_row;

							// �I���I���s < �I���J�n�s�̏ꍇ�͓����B
							if (endRow < startRow) {
								int tmp;
								tmp = endRow;
								endRow = startRow;
								startRow = tmp;
							}

							// ��s�ł��`�F�b�N�����Ă邩����
							for (; startRow <= endRow; startRow++) {
								TCheckBox checkBox = (TCheckBox) table.getComponent(startRow, checkBoxColumnNumber);
								if (checkBox.isSelected()) {
									isCheckLine = true;
									if (isAllCheckLine) {
										isAllCheckLine = true;
									}
								} else {
									isAllCheckLine = false;
								}
							}
						}
					}

					// �`�F�b�N����
					for (JCCellRange range : list) {
						// �I���̏��߂̍s
						int startRow = range.start_row;
						// �I���͍̂s���I����
						int endRow = range.end_row;

						// �I���I���s < �I���J�n�s�̏ꍇ�͓����B
						if (endRow < startRow) {
							int tmp;
							tmp = endRow;
							endRow = startRow;
							startRow = tmp;
						}

						try {
							Boolean status = (Boolean) table.getDataSource().getTableDataItem(startRow,
								checkBoxColumnNumber);
							ds.setCell(startRow, checkBoxColumnNumber, !status);

						} catch (Exception e) {
							ev.consume();
						}

						// �����s�I���\��
						if (isSelectMultiLine) {
							// �`�F�b�N����
							for (; startRow <= endRow; startRow++) {
								TCheckBox checkBox = (TCheckBox) table.getComponent(startRow, checkBoxColumnNumber);
								if (isAllCheckLine) {
									checkBox.setSelected(false);
								} else if (isCheckLine) {
									checkBox.setSelected(true);
								} else {
									checkBox.setSelected(!checkBox.isSelected());
								}
							}

						}
						// �P��s�̂�
						else {
							try {
								TCheckBox checkBox = (TCheckBox) table.getComponent(startRow, checkBoxColumnNumber);
								checkBox.setSelected(!checkBox.isSelected());
							} catch (Exception e) {
								ev.consume();
							}
						}

					}

				}
			}

		});

	}

}
