package jp.co.ais.trans2.common.objsave;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.dnd.*;
import jp.co.ais.trans2.model.save.*;

/**
 * �I�u�W�F�N�g�ۑ��p���X�i�[
 * 
 * @param <T> �ۑ��I�u�W�F�N�g�̃N���X
 */
public abstract class TObjectSaveListener<T> extends TController {

	/** �p�l�� */
	protected TMainObjectSavePanel mainView;

	/**
	 * �R���X�g���N�^.<br>
	 * �V�[�P���X�L�[��1�œo�^�����.
	 */
	public TObjectSaveListener() {
		// �����Ȃ�
	}

	/**
	 * �Ȃ�
	 */
	@Override
	public void start() {
		// �Ȃ�
	}

	/**
	 * @see jp.co.ais.trans.common.client.TPanelCtrlBase#getPanel()
	 */
	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * �ۑ��p�l���̐ݒ�(�C�x���g�o�^)
	 * 
	 * @param panel �p�l��
	 */
	public void setPanel(TMainObjectSavePanel panel) {
		this.mainView = panel;

		initEvent();
	}

	/**
	 * DnD�C�x���g�o�^
	 */
	protected void initEvent() {

		// �p�l��
		mainView.pnlDnDMain.setDnDListener(new TDnDAdapter() {

			@Override
			public Object getDragData() {
				return getSaveObject();
			}

			@Override
			public void dropData(Component component, Point point, Object value) {
				setSaveObject((T) value);
			}
		});

		// ��ʏ�ԕۑ��p���X�g
		mainView.lstDnDSave.setDnDListener(new TDnDAdapter() {

			@Override
			public void dropData(Component component, Point point, final Object value) {

				// TODO:�^�C�v�����`�F�b�N

				JPopupMenu popup = new JPopupMenu();

				// ���O��t���Ēǉ�
				final JMenuItem addItem = new JMenuItem(getWord("C11137"));
				// �㏑���ۑ�
				final JMenuItem updItem = new JMenuItem(getWord("C11138"));

				// �㏑���̈�Ȃ�ǉ�
				final int index = mainView.lstDnDSave.locationToIndex(point);
				Rectangle rect = mainView.lstDnDSave.getCellBounds(index, index);

				ActionListener listener = new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						DefaultListModel model = (DefaultListModel) mainView.lstDnDSave.getModel();

						Object obj = value;

						if ((obj instanceof SaveElement)) {
							String name;
							if (!e.getSource().equals(updItem)) {
								// �o�^�L�[
								name = JOptionPane.showInputDialog(getWord("C11139"));

								if (Util.isNullOrEmpty(name)) {
									return;
								}

							} else {
								name = getKey((T) model.getElementAt(index));
							}

							((SaveElement) obj).setKey(name);

						} else {
							SaveElement element = new SaveElement();
							element.setKey(getKey((T) obj));
							element.setValue(obj);
							obj = element;
						}

						if (index > 0) {
							model.setElementAt(obj, index);

						} else {
							model.addElement(obj);
						}

						saveObjectList(mainView.lstDnDSave);
					}

				};

				popup.add(addItem);
				addItem.addActionListener(listener);

				if (index != -1 && point.y <= rect.y + rect.height) {
					popup.add(updItem);
					updItem.addActionListener(listener);
				}

				popup.show(mainView.lstDnDSave, (int) point.getX(), (int) point.getY());
			}

			@Override
			public Object getDragData() {
				return ((SaveElement) mainView.lstDnDSave.getSelectedValue()).getValue();
			}
		});

		mainView.lstDnDSave.addMouseListener(new MouseAdapter() {

			/** ���j���[ */
			protected JPopupMenu popup = new JPopupMenu();

			/** �N���b�N�C�x���g */
			@Override
			public void mouseClicked(final MouseEvent e) {

				if (!mainView.lstDnDSave.isShowing() || !mainView.lstDnDSave.isEnabled()) {
					return;
				}

				if (!SwingUtilities.isRightMouseButton(e)) {
					return;
				}

				Point point = e.getPoint();
				final int index = mainView.lstDnDSave.locationToIndex(point);
				Rectangle rect = mainView.lstDnDSave.getCellBounds(index, index);

				if (mainView.lstDnDSave.getModel().getSize() == 0) {
					return;
				}

				boolean hasRemove = true;
				if (index == -1 || point.y > rect.y + rect.height) {
					hasRemove = false;
				} else {
					mainView.lstDnDSave.setSelectedIndex(index);
				}

				popup.removeAll();

				// �폜
				JMenuItem remove = new JMenuItem(getWord("C01544"));
				remove.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent ex) {
						DefaultListModel model = (DefaultListModel) mainView.lstDnDSave.getModel();

						deleteObject(mainView.lstDnDSave, index);
						model.remove(index);
					}
				});

				// �S�폜
				JMenuItem removeAll = new JMenuItem(getWord("C11309")); // �S�폜
				removeAll.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent ex) {
						DefaultListModel model = (DefaultListModel) mainView.lstDnDSave.getModel();

						deleteAllObject();
						model.removeAllElements();
					}
				});

				if (hasRemove) {
					popup.add(remove);
				}

				popup.add(removeAll);
				popup.show(e.getComponent(), e.getX(), e.getY());
			}

		});
	}

	/**
	 * �I�u�W�F�N�g����ǂݍ���Ń��X�g�ɕ\������.
	 */
	public void readObjectList() {

		try {
			List<OBJ_SAVE> list = (List<OBJ_SAVE>) request(SaveManager.class, "readObject", getCompany().getCode(),
				getUser().getCode(), getProgramCode(), null);

			DefaultListModel model = (DefaultListModel) mainView.lstDnDSave.getModel();

			for (OBJ_SAVE save : list) {
				SaveElement element = new SaveElement();
				element.setKey(save.getKEY());
				element.setValue(save.getSaveObject());
				model.addElement(element);
			}

		} catch (TException ex) {
			errorHandler(ex);
		}
	}

	/**
	 * ���X�g�ɐݒ肳��Ă���I�u�W�F�N�g����ۑ�����.
	 * 
	 * @param listBox ���X�g
	 */
	public void saveObjectList(TDnDList listBox) {
		try {

			ListModel model = listBox.getModel();

			List<OBJ_SAVE> list = new ArrayList<OBJ_SAVE>(model.getSize());
			for (int i = 0; i < model.getSize(); i++) {
				SaveElement element = (SaveElement) model.getElementAt(i);

				OBJ_SAVE bean = new OBJ_SAVE();
				bean.setKAI_CODE(getCompany().getCode());
				bean.setUSR_ID(getUser().getCode());
				bean.setPRG_ID(getProgramCode());
				bean.setSEQ(i + 1);
				bean.setKEY(element.getKey());
				bean.setSaveObject(element.getValue());

				list.add(bean);
			}

			request(SaveManager.class, "saveObject", list);

		} catch (Exception ex) {
			errorHandler(ex);
		}
	}

	/**
	 * �S�č폜����
	 */
	public void deleteAllObject() {

		OBJ_SAVE bean = new OBJ_SAVE();
		bean.setKAI_CODE(getCompany().getCode());
		bean.setUSR_ID(getUser().getCode());
		bean.setPRG_ID(getProgramCode());

		deleteObject(bean);
	}

	/**
	 * �폜����
	 * 
	 * @param listBox ���X�g
	 * @param index �폜�ΏۃC���f�b�N�X -1:ALL
	 */
	public void deleteObject(TDnDList listBox, int index) {

		if (index == -1) {
			return;
		}

		ListModel model = listBox.getModel();

		SaveElement element = (SaveElement) model.getElementAt(index);

		OBJ_SAVE bean = new OBJ_SAVE();
		bean.setKAI_CODE(getCompany().getCode());
		bean.setUSR_ID(getUser().getCode());
		bean.setPRG_ID(getProgramCode());
		bean.setSEQ(index + 1);
		bean.setKEY(element.getKey());
		bean.setSaveObject(element.getValue());

		// �폜����
		deleteObject(bean);
	}

	/**
	 * �폜����
	 * 
	 * @param bean �폜�Ώ�
	 */
	public void deleteObject(OBJ_SAVE bean) {
		try {
			request(SaveManager.class, "deleteObject", bean);
		} catch (Exception ex) {
			errorHandler(ex);
		}
	}

	/**
	 * �ۑ��L�[�̎擾
	 * 
	 * @param obj �ΏۃI�u�W�F�N�g
	 * @return �ۑ��L�[
	 */
	public String getKey(T obj) {
		return obj.toString();
	}

	/**
	 * �v���O����ID�擾
	 * 
	 * @return �v���O����ID
	 */
	@Override
	public abstract String getProgramCode();

	/**
	 * �ۑ��I�u�W�F�N�g�̐ݒ�
	 * 
	 * @param obj �ΏۃI�u�W�F�N�g
	 */
	public abstract void setSaveObject(T obj);

	/**
	 * �ۑ��I�u�W�F�N�g�̎擾
	 * 
	 * @return �I�u�W�F�N�g
	 */
	public abstract T getSaveObject();
}
