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
 * オブジェクト保存用リスナー
 * 
 * @param <T> 保存オブジェクトのクラス
 */
public abstract class TObjectSaveListener<T> extends TController {

	/** パネル */
	protected TMainObjectSavePanel mainView;

	/**
	 * コンストラクタ.<br>
	 * シーケンスキーは1で登録される.
	 */
	public TObjectSaveListener() {
		// 処理なし
	}

	/**
	 * なし
	 */
	@Override
	public void start() {
		// なし
	}

	/**
	 * @see jp.co.ais.trans.common.client.TPanelCtrlBase#getPanel()
	 */
	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * 保存パネルの設定(イベント登録)
	 * 
	 * @param panel パネル
	 */
	public void setPanel(TMainObjectSavePanel panel) {
		this.mainView = panel;

		initEvent();
	}

	/**
	 * DnDイベント登録
	 */
	protected void initEvent() {

		// パネル
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

		// 画面状態保存用リスト
		mainView.lstDnDSave.setDnDListener(new TDnDAdapter() {

			@Override
			public void dropData(Component component, Point point, final Object value) {

				// TODO:タイプ厳密チェック

				JPopupMenu popup = new JPopupMenu();

				// 名前を付けて追加
				final JMenuItem addItem = new JMenuItem(getWord("C11137"));
				// 上書き保存
				final JMenuItem updItem = new JMenuItem(getWord("C11138"));

				// 上書き領域なら追加
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
								// 登録キー
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

			/** メニュー */
			protected JPopupMenu popup = new JPopupMenu();

			/** クリックイベント */
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

				// 削除
				JMenuItem remove = new JMenuItem(getWord("C01544"));
				remove.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent ex) {
						DefaultListModel model = (DefaultListModel) mainView.lstDnDSave.getModel();

						deleteObject(mainView.lstDnDSave, index);
						model.remove(index);
					}
				});

				// 全削除
				JMenuItem removeAll = new JMenuItem(getWord("C11309")); // 全削除
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
	 * オブジェクト情報を読み込んでリストに表示する.
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
	 * リストに設定されているオブジェクト情報を保存する.
	 * 
	 * @param listBox リスト
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
	 * 全て削除処理
	 */
	public void deleteAllObject() {

		OBJ_SAVE bean = new OBJ_SAVE();
		bean.setKAI_CODE(getCompany().getCode());
		bean.setUSR_ID(getUser().getCode());
		bean.setPRG_ID(getProgramCode());

		deleteObject(bean);
	}

	/**
	 * 削除処理
	 * 
	 * @param listBox リスト
	 * @param index 削除対象インデックス -1:ALL
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

		// 削除処理
		deleteObject(bean);
	}

	/**
	 * 削除処理
	 * 
	 * @param bean 削除対象
	 */
	public void deleteObject(OBJ_SAVE bean) {
		try {
			request(SaveManager.class, "deleteObject", bean);
		} catch (Exception ex) {
			errorHandler(ex);
		}
	}

	/**
	 * 保存キーの取得
	 * 
	 * @param obj 対象オブジェクト
	 * @return 保存キー
	 */
	public String getKey(T obj) {
		return obj.toString();
	}

	/**
	 * プログラムID取得
	 * 
	 * @return プログラムID
	 */
	@Override
	public abstract String getProgramCode();

	/**
	 * 保存オブジェクトの設定
	 * 
	 * @param obj 対象オブジェクト
	 */
	public abstract void setSaveObject(T obj);

	/**
	 * 保存オブジェクトの取得
	 * 
	 * @return オブジェクト
	 */
	public abstract T getSaveObject();
}
