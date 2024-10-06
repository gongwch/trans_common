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
 * TFrameViewer�BTFrame���Book��`�悷��
 * 
 * @author AIS
 */
public class TFrameViewer extends TFrame implements TViewer {

	/** �g�b�v�� */
	public TPanel pnlTop;

	/** �g�b�v���̗]��t�B�[���h */
	public TPanel pnlTopAdd;

	/** ���E�� */
	public JSeparator sep;

	/** �{�f�B�� */
	public TPanel pnlBody;

	/** �{�f�B���̃w�b�_�[ */
	public TPanel pnlHeader;

	/** �{�f�B���̃w�b�_�[ */
	public TPanel pnlDetail;

	/** 1�Ń{�^�� */
	public TImageButton btnFirst;

	/** �O�Ń{�^�� */
	public TImageButton btnPrev;

	/** ���Ń{�^�� */
	public TImageButton btnNext;

	/** �ŏI�Ń{�^�� */
	public TImageButton btnLast;

	/** ���݃y�[�W */
	public TNumericField currentPage;

	/** ���y�[�W */
	public TLabel totalPage;

	/** ����{�^�� */
	public TButton btnClose;

	/** �R���g���[�� */
	public TFrameViewerController controller;

	/**
	 *
	 *
	 */
	public TFrameViewer() {

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();

		// �^�u���ݒ�
		setTabIndex();

		controller = new TFrameViewerController(this);

		// �J�X�^�}�C�Y�t���[���̕ۑ�
		TMainCtrl.getInstance().addFrame(this);

	}

	/**
	 * �R���|�[�l���g������
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
	 * �R���|�[�l���g�z�u
	 */
	protected void allocateComponents() {

		setLayout(new GridBagLayout());

		setPreferredSize(new Dimension(800, 600));
		setSize(820, 650);

		// �w�b�_�[
		pnlTop.setPreferredSize(new Dimension(0, 50));
		pnlTop.setMaximumSize(new Dimension(0, 50));
		pnlTop.setMinimumSize(new Dimension(0, 50));
		pnlTop.setLayout(new GridBagLayout());
		pnlTop.setBackground(new Color(255, 255, 255));

		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(pnlTop, gc);

		// 1�Ń{�^��
		btnFirst.setOpaque(false);
		btnFirst.setPreferredSize(new Dimension(100, 25));
		btnFirst.setMaximumSize(new Dimension(100, 25));
		btnFirst.setMinimumSize(new Dimension(100, 25));
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 10, 0, 0);
		pnlTop.add(btnFirst, gc);

		// �O�Ń{�^��
		btnPrev.setOpaque(false);
		btnPrev.setPreferredSize(new Dimension(100, 25));
		btnPrev.setMaximumSize(new Dimension(100, 25));
		btnPrev.setMinimumSize(new Dimension(100, 25));
		gc = new GridBagConstraints();
		pnlTop.add(btnPrev, gc);

		// ���Ń{�^��
		btnNext.setOpaque(false);
		btnNext.setPreferredSize(new Dimension(100, 25));
		btnNext.setMaximumSize(new Dimension(100, 25));
		btnNext.setMinimumSize(new Dimension(100, 25));
		gc = new GridBagConstraints();
		pnlTop.add(btnNext, gc);

		// �ŏI�Ń{�^��
		btnLast.setOpaque(false);
		btnLast.setPreferredSize(new Dimension(100, 25));
		btnLast.setMaximumSize(new Dimension(100, 25));
		btnLast.setMinimumSize(new Dimension(100, 25));
		gc = new GridBagConstraints();
		pnlTop.add(btnLast, gc);

		// ���݃y�[�W
		currentPage.setPreferredSize(new Dimension(50, 25));
		currentPage.setMaximumSize(new Dimension(50, 25));
		currentPage.setMinimumSize(new Dimension(50, 25));
		currentPage.setPositiveOnly(true);
		currentPage.setMaxLength(6);
		gc = new GridBagConstraints();
		pnlTop.add(currentPage, gc);

		// ���y�[�W��
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

		// ����{�^��
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

		// ���E��
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridy = 1;
		add(sep, gc);

		// �{�f�B
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
	 * ��������
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
	 * �ڎ��̃|�b�v�A�b�v���ݒ�
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
	 * book��ۑ�����
	 * 
	 * @param book
	 */
	protected void saveBook(LedgerBook book) {
		FileUtil.saveObject(book, book.getClass().getSimpleName());
	}

	/**
	 * Tab���ݒ�
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
