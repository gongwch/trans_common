package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;

/**
 * タブ入替用モデル
 */
public class TTabbedPainSwitchModel {

	/** DnD判別名 */
	protected static final String NAME = TTabbedPane.class.getName();

	/** ドロップ場所のライン幅 */
	protected static final int LINEWIDTH = 3;

	/** ゴースト */
	protected final GhostGlassPane glassPane = new GhostGlassPane();

	/** 位置 */
	protected final Rectangle lineRect = new Rectangle();

	/** ドロップ場所のライン色 */
	protected final Color lineColor = new Color(10, 10, 10);

	/** Index */
	protected int dragTabIndex = -1;

	/** 適用TabbedPane */
	protected TTabbedPane tabbedPane;

	/**
	 * タブペインにモデルを適用する
	 * 
	 * @param pane 対象タブペイン
	 */
	public void accept(TTabbedPane pane) {
		this.tabbedPane = pane;

		init();
	}

	/**
	 * 初期化
	 */
	protected void init() {
		final DragSourceListener dsl = new DragSourceAdapter() {

			@Override
			public void dragEnter(DragSourceDragEvent e) {
				e.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
			}

			@Override
			public void dragExit(DragSourceEvent e) {
				e.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
				lineRect.setRect(0, 0, 0, 0);
				glassPane.setPoint(new Point(-1000, -1000));
				glassPane.repaint();
			}

			@Override
			public void dragOver(DragSourceDragEvent e) {
				Point glassPt = e.getLocation();
				SwingUtilities.convertPointFromScreen(glassPt, glassPane);
				int targetIdx = getTargetTabIndex(glassPt);

				if (getTabAreaBounds().contains(glassPt) && targetIdx >= 0 && targetIdx != dragTabIndex
					&& targetIdx != dragTabIndex + 1) {
					e.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
					glassPane.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

				} else {
					e.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
					glassPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}

			@Override
			public void dragDropEnd(DragSourceDropEvent e) {
				lineRect.setRect(0, 0, 0, 0);
				dragTabIndex = -1;
				glassPane.setVisible(false);
				glassPane.setImage(null);
			}
		};

		final Transferable t = new Transferable() {

			protected final DataFlavor FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, NAME);

			public Object getTransferData(DataFlavor flavor) {
				return tabbedPane;
			}

			public DataFlavor[] getTransferDataFlavors() {
				DataFlavor[] f = new DataFlavor[1];
				f[0] = this.FLAVOR;
				return f;
			}

			public boolean isDataFlavorSupported(DataFlavor flavor) {
				return flavor.getHumanPresentableName().equals(NAME);
			}
		};

		final DragGestureListener dgl = new DragGestureListener() {

			public void dragGestureRecognized(DragGestureEvent e) {

				InputEvent ie = e.getTriggerEvent();

				if (ie.getModifiers() != InputEvent.BUTTON1_MASK) {
					return;
				}

				if (tabbedPane.getTabCount() <= 1) {
					return;
				}

				Point tabPt = e.getDragOrigin();
				dragTabIndex = tabbedPane.indexAtLocation(tabPt.x, tabPt.y);

				// "disabled tab problem".
				if (dragTabIndex < 0 || !tabbedPane.isEnabledAt(dragTabIndex)) {
					return;
				}

				initGlassPane(e.getComponent(), e.getDragOrigin());

				try {
					e.startDrag(DragSource.DefaultMoveDrop, t, dsl);

				} catch (InvalidDnDOperationException idoe) {
					idoe.printStackTrace();
				}
			}
		};

		new DropTarget(glassPane, DnDConstants.ACTION_COPY_OR_MOVE, new CDropTargetListener(), true);
		new DragSource().createDefaultDragGestureRecognizer(tabbedPane, DnDConstants.ACTION_COPY_OR_MOVE, dgl);
	}

	/**
	 * @param glassPt
	 * @return index
	 */
	public int getTargetTabIndex(Point glassPt) {
		Point tabPt = SwingUtilities.convertPoint(glassPane, glassPt, tabbedPane);
		boolean isTB = tabbedPane.getTabPlacement() == SwingConstants.TOP
			|| tabbedPane.getTabPlacement() == SwingConstants.BOTTOM;

		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			Rectangle r = tabbedPane.getBoundsAt(i);
			if (isTB) {
				r.setRect(r.x - r.width / 2, r.y, r.width, r.height);

			} else {
				r.setRect(r.x, r.y - r.height / 2, r.width, r.height);
			}

			if (r.contains(tabPt)) {
				return i;
			}
		}

		Rectangle r = tabbedPane.getBoundsAt(tabbedPane.getTabCount() - 1);
		if (isTB) {
			r.setRect(r.x + r.width / 2, r.y, r.width, r.height);

		} else {
			r.setRect(r.x, r.y + r.height / 2, r.width, r.height);
		}

		return r.contains(tabPt) ? tabbedPane.getTabCount() : -1;
	}

	/**
	 * ドロップ用リスナー
	 */
	protected class CDropTargetListener extends DropTargetAdapter {

		/** ポイント */
		protected Point _glassPt = new Point();

		/**
		 * @see java.awt.dnd.DropTargetAdapter#dragEnter(java.awt.dnd.DropTargetDragEvent)
		 */
		@Override
		public void dragEnter(DropTargetDragEvent e) {
			if (isDragAcceptable(e)) e.acceptDrag(e.getDropAction());
			else e.rejectDrag();
		}

		/**
		 * @see java.awt.dnd.DropTargetAdapter#dragOver(java.awt.dnd.DropTargetDragEvent)
		 */
		@Override
		public void dragOver(final DropTargetDragEvent e) {
			Point glassPt = e.getLocation();

			switch (tabbedPane.getTabPlacement()) {
				case SwingConstants.TOP:
				case SwingConstants.BOTTOM:
					initTargetLeftRightLine(getTargetTabIndex(glassPt));
					break;

				default:
					initTargetTopBottomLine(getTargetTabIndex(glassPt));
					break;
			}

			glassPane.setPoint(glassPt);

			if (!_glassPt.equals(glassPt)) {
				glassPane.repaint();
			}

			_glassPt = glassPt;
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#drop(java.awt.dnd.DropTargetDropEvent)
		 */
		public void drop(DropTargetDropEvent e) {
			if (isDropAcceptable(e)) {
				convertTab(dragTabIndex, getTargetTabIndex(e.getLocation()));
				e.dropComplete(true);

			} else {
				e.dropComplete(false);
			}
			tabbedPane.repaint();
		}

		/**
		 * @param e
		 * @return drag可能か
		 */
		protected boolean isDragAcceptable(DropTargetDragEvent e) {
			Transferable t = e.getTransferable();
			if (t == null) {
				return false;
			}

			DataFlavor[] f = e.getCurrentDataFlavors();
			if (t.isDataFlavorSupported(f[0]) && dragTabIndex >= 0) {
				return true;
			}

			return false;
		}

		/**
		 * @param e
		 * @return drop可能か
		 */
		protected boolean isDropAcceptable(DropTargetDropEvent e) {
			Transferable t = e.getTransferable();
			if (t == null) {
				return false;
			}

			DataFlavor[] f = t.getTransferDataFlavors();
			if (t.isDataFlavorSupported(f[0]) && dragTabIndex >= 0) {
				return true;
			}
			return false;
		}
	}

	/**
	 * @param prev
	 * @param next
	 */
	protected void convertTab(int prev, int next) {
		if (next < 0 || prev == next) {
			return;
		}

		Component cmp = tabbedPane.getComponentAt(prev);
		Component tab = tabbedPane.getTabComponentAt(prev);
		String str = tabbedPane.getTitleAt(prev);
		Icon icon = tabbedPane.getIconAt(prev);
		String tip = tabbedPane.getToolTipTextAt(prev);
		Color color = tabbedPane.getBackgroundAt(prev);
		boolean flg = tabbedPane.isEnabledAt(prev);
		int tgtindex = prev > next ? next : next - 1;

		tabbedPane.remove(prev);
		tabbedPane.insertTab(str, icon, cmp, tip, tgtindex);
		tabbedPane.setEnabledAt(tgtindex, flg);
		tabbedPane.setBackgroundAt(tgtindex, color);

		// When you drag'n'drop a disabled tab, it finishes enabled and selected.
		// pointed out by dlorde
		if (flg) {
			tabbedPane.setSelectedIndex(tgtindex);
		}

		// I have a component in all tabs (jlabel with an X to close the tab) and when i move a tab the component
		// disappear.
		// pointed out by Daniel Dario Morales Salas
		tabbedPane.setTabComponentAt(tgtindex, tab);
	}

	/**
	 * @param next
	 */
	protected void initTargetLeftRightLine(int next) {
		if (next < 0 || dragTabIndex == next || next - dragTabIndex == 1) {
			lineRect.setRect(0, 0, 0, 0);

		} else if (next == 0) {
			Rectangle r = SwingUtilities.convertRectangle(tabbedPane, tabbedPane.getBoundsAt(0), glassPane);
			lineRect.setRect(r.x - LINEWIDTH / 2, r.y, LINEWIDTH, r.height);

		} else {
			Rectangle r = SwingUtilities.convertRectangle(tabbedPane, tabbedPane.getBoundsAt(next - 1), glassPane);
			lineRect.setRect(r.x + r.width - LINEWIDTH / 2, r.y, LINEWIDTH, r.height);
		}
	}

	/**
	 * @param next
	 */
	protected void initTargetTopBottomLine(int next) {
		if (next < 0 || dragTabIndex == next || next - dragTabIndex == 1) {
			lineRect.setRect(0, 0, 0, 0);

		} else if (next == 0) {
			Rectangle r = SwingUtilities.convertRectangle(tabbedPane, tabbedPane.getBoundsAt(0), glassPane);
			lineRect.setRect(r.x, r.y - LINEWIDTH / 2, r.width, LINEWIDTH);

		} else {
			Rectangle r = SwingUtilities.convertRectangle(tabbedPane, tabbedPane.getBoundsAt(next - 1), glassPane);
			lineRect.setRect(r.x, r.y + r.height - LINEWIDTH / 2, r.width, LINEWIDTH);
		}
	}

	/**
	 * ゴーストパネル初期化
	 * 
	 * @param c
	 * @param tabPt
	 */
	protected void initGlassPane(Component c, Point tabPt) {
		tabbedPane.getRootPane().setGlassPane(glassPane);

		Rectangle rect = tabbedPane.getBoundsAt(dragTabIndex);
		BufferedImage image = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		c.paint(g);
		rect.x = rect.x < 0 ? 0 : rect.x;
		rect.y = rect.y < 0 ? 0 : rect.y;
		image = image.getSubimage(rect.x, rect.y, rect.width, rect.height);
		glassPane.setImage(image);

		Point glassPt = SwingUtilities.convertPoint(c, tabPt, glassPane);
		glassPane.setPoint(glassPt);
		glassPane.setVisible(true);
	}

	/**
	 * タブ位置特定
	 * 
	 * @return 位置
	 */
	protected Rectangle getTabAreaBounds() {

		Rectangle tabbedRect = tabbedPane.getBounds();

		// pointed out by daryl. NullPointerException: i.e. addTab("Tab",null)
		// Rectangle compRect = getSelectedComponent().getBounds();
		Component comp = tabbedPane.getSelectedComponent();
		int idx = 0;

		while (comp == null && idx < tabbedPane.getTabCount()) {
			comp = tabbedPane.getComponentAt(idx++);
		}

		Rectangle compRect = (comp == null) ? new Rectangle() : comp.getBounds();

		int tabPlacement_ = tabbedPane.getTabPlacement();
		if (tabPlacement_ == SwingConstants.TOP) {
			tabbedRect.height = tabbedRect.height - compRect.height;

		} else if (tabPlacement_ == SwingConstants.BOTTOM) {
			tabbedRect.y = tabbedRect.y + compRect.y + compRect.height;
			tabbedRect.height = tabbedRect.height - compRect.height;

		} else if (tabPlacement_ == SwingConstants.LEFT) {
			tabbedRect.width = tabbedRect.width - compRect.width;

		} else if (tabPlacement_ == SwingConstants.RIGHT) {
			tabbedRect.x = tabbedRect.x + compRect.x + compRect.width;
			tabbedRect.width = tabbedRect.width - compRect.width;
		}

		tabbedRect.grow(2, 2);

		return tabbedRect;
	}

	/**
	 * ドラック時のゴーストPane
	 */
	protected class GhostGlassPane extends JPanel {

		/** コンポジット */
		protected final AlphaComposite composite;

		/** ポイント */
		protected Point location = new Point(0, 0);

		/** イメージ */
		protected BufferedImage draggingGhost = null;

		/**
		 * コンストラクタ.
		 */
		public GhostGlassPane() {
			setOpaque(false);
			composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
		}

		/**
		 * @param draggingGhost
		 */
		public void setImage(BufferedImage draggingGhost) {
			this.draggingGhost = draggingGhost;
		}

		/**
		 * @param location
		 */
		public void setPoint(Point location) {
			this.location = location;
		}

		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setComposite(composite);

			if (draggingGhost != null) {
				double xx = location.getX() - (draggingGhost.getWidth(this) / 2d);
				double yy = location.getY() - (draggingGhost.getHeight(this) / 2d);
				g2.drawImage(draggingGhost, (int) xx, (int) yy, null);
			}

			if (dragTabIndex >= 0) {
				g2.setPaint(lineColor);
				g2.fill(lineRect);
			}
		}
	}
}
