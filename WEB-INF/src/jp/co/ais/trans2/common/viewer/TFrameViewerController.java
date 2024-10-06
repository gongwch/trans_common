package jp.co.ais.trans2.common.viewer;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ledger.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * TFrameViewerのコントローラ
 * 
 * @author AIS
 */
public class TFrameViewerController extends TController {

	/** viewer */
	protected TFrameViewer viewer;

	/** layout */
	protected TViewerLayout layout;

	/** LedgerBook */
	protected LedgerBook book;

	/** 総ページ数 */
	protected int totalPage = 0;

	/** 現在選択されている頁番号 */
	public int currentSheetNo = 0;

	/**
	 * コンストラクタ
	 * 
	 * @param viewer
	 */
	public TFrameViewerController(TFrameViewer viewer) {
		this.viewer = viewer;
		initView();
	}

	/**
	 * @return bookを戻します。
	 */
	public LedgerBook getBook() {
		return book;
	}

	/**
	 * @param book bookを設定します。
	 */
	public void setBook(LedgerBook book) {
		this.book = book;
	}

	/**
	 * Viwerの初期化
	 */
	protected void initView() {

		showCurrentPageNo();

		viewer.setExtendedState(Frame.MAXIMIZED_BOTH);

		// 前頁
		viewer.btnPrev.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnPrev_Click();
			}
		});

		// 次頁
		viewer.btnNext.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnNext_Click();
			}
		});

		// 1頁
		viewer.btnFirst.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnFirst_Click();
			}
		});

		// 最終頁ボタン
		viewer.btnLast.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnLast_Click();
			}

		});

		// ページ数直接入力
		viewer.currentPage.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(@SuppressWarnings("unused") JComponent comp) {
				return currentPage_verifyCommit();
			}
		});

		// 閉じるボタン
		viewer.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnClose_Click();
			}

		});

	}

	/**
	 * [前頁]ボタン押下
	 */
	protected void btnPrev_Click() {
		try {
			currentSheetNo--;
			showSheet(currentSheetNo);
		} catch (TException e) {
			errorHandler(e);
		}
	}

	/**
	 * [次頁]ボタン押下
	 */
	protected void btnNext_Click() {
		try {
			currentSheetNo++;
			showSheet(currentSheetNo);
		} catch (TException e) {
			errorHandler(e);
		}
	}

	/**
	 * [1頁]ボタン押下
	 */
	protected void btnFirst_Click() {
		try {
			currentSheetNo = 0;
			showSheet(currentSheetNo);
		} catch (TException e) {
			errorHandler(e);
		}
	}

	/**
	 * [最終頁]ボタン押下
	 */
	protected void btnLast_Click() {
		try {
			currentSheetNo = totalPage - 1;
			showSheet(currentSheetNo);
		} catch (TException e) {
			errorHandler(e);
		}
	}

	/**
	 * [閉じるボタン押下]
	 */
	protected void btnClose_Click() {
		try {
			viewer.setVisible(false);
			viewer.dispose();
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * ページ直接入力
	 * 
	 * @return
	 */
	protected boolean currentPage_verifyCommit() {
		try {
			currentSheetNo = viewer.currentPage.getInt() - 1;
			if (currentSheetNo >= 0 && currentSheetNo < totalPage) {
				showSheet(currentSheetNo);
				return true;
			}
			return true;
		} catch (TException e) {
			errorHandler(e);
		}

		return true;
	}

	/**
	 * ボタン制御
	 */
	public void controlBtn() {

		viewer.btnFirst.setEnabled(true);
		viewer.btnPrev.setEnabled(true);
		viewer.btnNext.setEnabled(true);
		viewer.btnLast.setEnabled(true);

		if (currentSheetNo == 0) {
			// 先頭ページ表示時
			viewer.btnFirst.setEnabled(false);
			viewer.btnPrev.setEnabled(false);
		}
		if (currentSheetNo == totalPage - 1) {
			// 最終ページ表示時
			viewer.btnNext.setEnabled(false);
			viewer.btnLast.setEnabled(false);
		}
	}

	/**
	 * Viewerを表示する
	 * 
	 * @param book
	 * @param layout
	 */
	public void show(LedgerBook book, TViewerLayout layout) throws TException {

		layout.setBook(book);

		addContents(book);
		setBook(book);
		setLayout(layout);

		// タイトル表示
		viewer.setTitle(book.getTitle());

		// アイコン
		if (TMainCtrl.getInstance().icon != null) {
			viewer.setIconImage(TMainCtrl.getInstance().icon);
		}

		// 総ページ数表示
		setTotalPage(book.getSheetCount());
		viewer.totalPage.setText("/ " + getTotalPage());

		// 目次設定
		setContents();

		// viewer初期化
		layout.init(viewer);
		setStorableKey();
		viewer.init();

		// 1ページ目を表示
		showSheet(0);

		viewer.setVisible(true);

	}

	public void addContents(LedgerBook book) {
		// TODO
		/*
		 * try { Class clazz = book.getSheetAt(0).getHeader().getClass(); Method[] methods = clazz.getMethods(); for
		 * (int i = 0; i < methods.length - 1; i++) { LedgerContentsAnnotation annotation =
		 * methods[i].getAnnotation(LedgerContentsAnnotation.class); if (annotation != null) { if (annotation.key() ==
		 * 2) { String methodName = methods[i].getName(); int j = 0; String bak = ""; for (LedgerSheet sheet :
		 * book.getSheet()) { Method method = clazz.getMethod(methodName); String ret =
		 * (String)method.invoke(sheet.getHeader()); if (!bak.equals(ret)) { LedgerContents contents = new
		 * LedgerContents(); contents.setChapTitle(ret); contents.setSheetNo(j+1); book.addContents(contents); } j++;
		 * bak = ret; } } } } }catch (Exception e) { e.printStackTrace(); }
		 */
	}

	/**
	 * 指定番号のシートを表示する
	 * 
	 * @param sheetNo
	 */
	public void showSheet(int sheetNo) throws TException {
		LedgerSheet sheet = book.getSheetAt(sheetNo);
		layout.drawHeader(sheet.getHeader());
		layout.drawDetail(sheet.getDetail());
		showCurrentPageNo();
		controlBtn();
	}

	/**
	 * 現在ページ番号を表示
	 */
	public void showCurrentPageNo() {
		viewer.currentPage.setNumber(currentSheetNo + 1);
	}

	/**
	 * @return layoutを戻します。
	 */
	public TViewerLayout getLayout() {
		return layout;
	}

	/**
	 * @param layout layoutを設定します。
	 */
	public void setLayout(TViewerLayout layout) {
		this.layout = layout;
	}

	/**
	 * @return 総ページ数を設定します
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * 総ページ数を戻します
	 * 
	 * @param totalPage
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * 目次タイトルをセット
	 */
	public void setContents() {
		// TODO
		String[] chapLabels = new String[book.getContents().size()];

		for (int i = 0; i < book.getContents().size(); i++) {
			chapLabels[i] = book.getContents().get(i).getChapTitle();
		}

		// viewer.contents.setModel(chapLabels);
	}

	@Override
	public void start() {
		//
	}

	@Override
	public TPanelBusiness getPanel() {
		//
		return null;
	}

	protected void setStorableKey() {

		TFrameViewerStorableKey vKey = new TFrameViewerStorableKey(getLayout());

		try {

			Class c = layout.getClass();
			Field[] fields = c.getFields();
			if (fields != null) {
				for (Field field : fields) {
					Object obj = field.get(layout);
					if (obj instanceof TStorable) {
						TStorable storable = (TStorable) obj;
						TStorableKey key = storable.getKey();
						if (key == null) {
							key = vKey;
						}
						storable.restoreComponent(key);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
