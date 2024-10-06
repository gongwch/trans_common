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
 * TFrameViewer�̃R���g���[��
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

	/** ���y�[�W�� */
	protected int totalPage = 0;

	/** ���ݑI������Ă���Ŕԍ� */
	public int currentSheetNo = 0;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param viewer
	 */
	public TFrameViewerController(TFrameViewer viewer) {
		this.viewer = viewer;
		initView();
	}

	/**
	 * @return book��߂��܂��B
	 */
	public LedgerBook getBook() {
		return book;
	}

	/**
	 * @param book book��ݒ肵�܂��B
	 */
	public void setBook(LedgerBook book) {
		this.book = book;
	}

	/**
	 * Viwer�̏�����
	 */
	protected void initView() {

		showCurrentPageNo();

		viewer.setExtendedState(Frame.MAXIMIZED_BOTH);

		// �O��
		viewer.btnPrev.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnPrev_Click();
			}
		});

		// ����
		viewer.btnNext.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnNext_Click();
			}
		});

		// 1��
		viewer.btnFirst.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnFirst_Click();
			}
		});

		// �ŏI�Ń{�^��
		viewer.btnLast.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnLast_Click();
			}

		});

		// �y�[�W�����ړ���
		viewer.currentPage.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(@SuppressWarnings("unused") JComponent comp) {
				return currentPage_verifyCommit();
			}
		});

		// ����{�^��
		viewer.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnClose_Click();
			}

		});

	}

	/**
	 * [�O��]�{�^������
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
	 * [����]�{�^������
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
	 * [1��]�{�^������
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
	 * [�ŏI��]�{�^������
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
	 * [����{�^������]
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
	 * �y�[�W���ړ���
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
	 * �{�^������
	 */
	public void controlBtn() {

		viewer.btnFirst.setEnabled(true);
		viewer.btnPrev.setEnabled(true);
		viewer.btnNext.setEnabled(true);
		viewer.btnLast.setEnabled(true);

		if (currentSheetNo == 0) {
			// �擪�y�[�W�\����
			viewer.btnFirst.setEnabled(false);
			viewer.btnPrev.setEnabled(false);
		}
		if (currentSheetNo == totalPage - 1) {
			// �ŏI�y�[�W�\����
			viewer.btnNext.setEnabled(false);
			viewer.btnLast.setEnabled(false);
		}
	}

	/**
	 * Viewer��\������
	 * 
	 * @param book
	 * @param layout
	 */
	public void show(LedgerBook book, TViewerLayout layout) throws TException {

		layout.setBook(book);

		addContents(book);
		setBook(book);
		setLayout(layout);

		// �^�C�g���\��
		viewer.setTitle(book.getTitle());

		// �A�C�R��
		if (TMainCtrl.getInstance().icon != null) {
			viewer.setIconImage(TMainCtrl.getInstance().icon);
		}

		// ���y�[�W���\��
		setTotalPage(book.getSheetCount());
		viewer.totalPage.setText("/ " + getTotalPage());

		// �ڎ��ݒ�
		setContents();

		// viewer������
		layout.init(viewer);
		setStorableKey();
		viewer.init();

		// 1�y�[�W�ڂ�\��
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
	 * �w��ԍ��̃V�[�g��\������
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
	 * ���݃y�[�W�ԍ���\��
	 */
	public void showCurrentPageNo() {
		viewer.currentPage.setNumber(currentSheetNo + 1);
	}

	/**
	 * @return layout��߂��܂��B
	 */
	public TViewerLayout getLayout() {
		return layout;
	}

	/**
	 * @param layout layout��ݒ肵�܂��B
	 */
	public void setLayout(TViewerLayout layout) {
		this.layout = layout;
	}

	/**
	 * @return ���y�[�W����ݒ肵�܂�
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * ���y�[�W����߂��܂�
	 * 
	 * @param totalPage
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * �ڎ��^�C�g�����Z�b�g
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
