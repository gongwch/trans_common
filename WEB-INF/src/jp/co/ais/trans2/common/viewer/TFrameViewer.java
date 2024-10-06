package jp.co.ais.trans2.common.viewer;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ledger.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * TFrameViewer。TFrame上にBookを描画する
 * 
 * @author AIS
 */
public class TFrameViewer extends TFrame implements TViewer {

	/** トップ部 */
	public TPanel pnlTop;

	/** トップ部の余りフィールド */
	public TPanel pnlTopAdd;

	/** 境界線 */
	public JSeparator sep;

	/** ボディ部 */
	public TPanel pnlBody;

	/** ボディ部のヘッダー */
	public TPanel pnlHeader;

	/** ボディ部のヘッダー */
	public TPanel pnlDetail;

	/** 1頁ボタン */
	public TImageButton btnFirst;

	/** 前頁ボタン */
	public TImageButton btnPrev;

	/** 次頁ボタン */
	public TImageButton btnNext;

	/** 最終頁ボタン */
	public TImageButton btnLast;

	/** 現在ページ */
	public TNumericField currentPage;

	/** 総ページ */
	public TLabel totalPage;

	/** 閉じるボタン */
	public TButton btnClose;

	/** コントローラ */
	public TFrameViewerController controller;

	/**
	 *
	 *
	 */
	public TFrameViewer() {

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();

		// タブ順設定
		setTabIndex();

		controller = new TFrameViewerController(this);

		// カスタマイズフレームの保存
		TMainCtrl.getInstance().addFrame(this);

	}

	/**
	 * コンポーネント初期化
	 */
	protected void initComponents() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		pnlTop = new TPanel();
		pnlTopAdd = new TPanel();
		sep = new JSeparator();
		pnlBody = new TPanel();
		pnlHeader = new TPanel();
		pnlDetail = new TPanel();
		btnFirst = new TImageButton(IconType.ALLOW_FIRST);
		btnPrev = new TImageButton(IconType.ALLOW_PREVIOUS);
		btnNext = new TImageButton(IconType.ALLOW_NEXT);
		btnLast = new TImageButton(IconType.ALLOW_LAST);
		currentPage = new TNumericField();
		totalPage = new TLabel();
		btnClose = new TButton();
	}

	/**
	 * コンポーネント配置
	 */
	protected void allocateComponents() {

		setLayout(new GridBagLayout());

		setPreferredSize(new Dimension(800, 600));
		setSize(820, 650);

		// ヘッダー
		pnlTop.setPreferredSize(new Dimension(0, 50));
		pnlTop.setMaximumSize(new Dimension(0, 50));
		pnlTop.setMinimumSize(new Dimension(0, 50));
		pnlTop.setLayout(new GridBagLayout());
		pnlTop.setBackground(new Color(255, 255, 255));

		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(pnlTop, gc);

		// 1頁ボタン
		btnFirst.setOpaque(false);
		btnFirst.setPreferredSize(new Dimension(100, 25));
		btnFirst.setMaximumSize(new Dimension(100, 25));
		btnFirst.setMinimumSize(new Dimension(100, 25));
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 10, 0, 0);
		pnlTop.add(btnFirst, gc);

		// 前頁ボタン
		btnPrev.setOpaque(false);
		btnPrev.setPreferredSize(new Dimension(100, 25));
		btnPrev.setMaximumSize(new Dimension(100, 25));
		btnPrev.setMinimumSize(new Dimension(100, 25));
		gc = new GridBagConstraints();
		pnlTop.add(btnPrev, gc);

		// 次頁ボタン
		btnNext.setOpaque(false);
		btnNext.setPreferredSize(new Dimension(100, 25));
		btnNext.setMaximumSize(new Dimension(100, 25));
		btnNext.setMinimumSize(new Dimension(100, 25));
		gc = new GridBagConstraints();
		pnlTop.add(btnNext, gc);

		// 最終頁ボタン
		btnLast.setOpaque(false);
		btnLast.setPreferredSize(new Dimension(100, 25));
		btnLast.setMaximumSize(new Dimension(100, 25));
		btnLast.setMinimumSize(new Dimension(100, 25));
		gc = new GridBagConstraints();
		pnlTop.add(btnLast, gc);

		// 現在ページ
		currentPage.setPreferredSize(new Dimension(50, 25));
		currentPage.setMaximumSize(new Dimension(50, 25));
		currentPage.setMinimumSize(new Dimension(50, 25));
		currentPage.setPositiveOnly(true);
		currentPage.setMaxLength(6);
		gc = new GridBagConstraints();
		pnlTop.add(currentPage, gc);

		// 総ページ数
		totalPage.setPreferredSize(new Dimension(70, 25));
		totalPage.setMaximumSize(new Dimension(70, 25));
		totalPage.setMinimumSize(new Dimension(70, 25));
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 5, 0, 0);
		pnlTop.add(totalPage, gc);

		pnlTopAdd.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 5, 0, 0);
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlTopAdd.setOpaque(false);
		pnlTop.add(pnlTopAdd, gc);

		// 閉じるボタン
		btnClose.setLangMessageID("C02374");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setOpaque(false);
		btnClose.setPreferredSize(new Dimension(100, 25));
		btnClose.setMaximumSize(new Dimension(100, 25));
		btnClose.setMinimumSize(new Dimension(100, 25));
		btnClose.setEnterFocusable(true);

		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 0, 10);
		pnlTop.add(btnClose, gc);

		// 境界線
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridy = 1;
		add(sep, gc);

		// ボディ
		pnlBody.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridy = 2;
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		add(pnlBody, gc);

		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlHeader, gc);

		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.gridy = 1;
		pnlBody.add(pnlDetail, gc);

	}

	/**
	 * 初期処理
	 */
	public void init() {
		initFrame();
	}

	/**
	 * @see jp.co.ais.trans2.common.viewer.TViewer#show(jp.co.ais.trans2.common.ledger.LedgerBook,
	 *      jp.co.ais.trans2.common.viewer.TViewerLayout)
	 */
	public void show(LedgerBook book, TViewerLayout layout) throws TException {

		try {

			controller.show(book, layout);

		} catch (TException e) {
			setVisible(false);
			dispose();
			throw e;
		}

	}

	/**
	 * 目次のポップアップ幅設定
	 * 
	 * @author AIS
	 */
	class contentsPopupMenuListener implements PopupMenuListener {

		/**  */
		private static final int POPUP_MIN_WIDTH = 320;

		/**  */
		private boolean adjusting = false;

		public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

			JComboBox cbo = (JComboBox) e.getSource();
			Dimension size = cbo.getSize();
			if (size.width >= POPUP_MIN_WIDTH) {
				return;
			}
			if (!adjusting) {
				adjusting = true;
				cbo.setSize(POPUP_MIN_WIDTH, size.height);
				cbo.showPopup();
			}
			cbo.setSize(size);
			adjusting = false;
		}

		public void popupMenuWillBecomeInvisible(@SuppressWarnings("unused") PopupMenuEvent e) {
			//
		}

		public void popupMenuCanceled(@SuppressWarnings("unused") PopupMenuEvent e) {
			//
		}
	}

	/**
	 * bookを保存する
	 * 
	 * @param book
	 */
	protected void saveBook(LedgerBook book) {
		FileUtil.saveObject(book, book.getClass().getSimpleName());
	}

	/**
	 * Tab順設定
	 */
	public void setTabIndex() {
		int i = 1;
		btnFirst.setTabControlNo(i++);
		btnPrev.setTabControlNo(i++);
		btnNext.setTabControlNo(i++);
		btnLast.setTabControlNo(i++);
		currentPage.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}
