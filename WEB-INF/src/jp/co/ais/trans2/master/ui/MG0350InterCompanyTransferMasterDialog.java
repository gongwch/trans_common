package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * ��Њԕt�փ}�X�^�̕ҏW���
 * 
 * @author AIS
 */
public class MG0350InterCompanyTransferMasterDialog extends TDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = 8557714357271090392L;

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �R�[�h */
	public TCompanyReference ctrlCompany;

	/** �t�֌v�㕔��R�[�h */
	public TDepartmentReference ctrlDepartment;

	/** �t�։ȖڃR�[�h */
	public TItemGroup ctrlItem;

	/** �����R�[�h */
	public TCustomerReference ctrlCustomer;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0350InterCompanyTransferMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();

		ctrlCompany = new TCompanyReference();
		ctrlDepartment = new TDepartmentReference();
		ctrlItem = new TItemGroup();
		ctrlCustomer = new TCustomerReference();

	}

	@Override
	public void allocateComponents() {

		setSize(680, 350);

		// �m��{�^��
		int x = 550 - 110 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, HEADER_Y);
		btnSettle.setEnterFocusable(true);
		pnlHeader.add(btnSettle);

		// �߂�{�^��
		x = 550;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x, HEADER_Y);
		pnlHeader.add(btnClose);

		TPanel pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(480, 155));
		pnlBodyTop.setMinimumSize(new Dimension(480, 155));
		pnlBodyTop.setPreferredSize(new Dimension(480, 155));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// �V�X�e���敪
		x = 10;
		int y = 10;

		// �t�։�ЃR�[�h
		ctrlCompany.setLocation(x, y);
		pnlBodyTop.add(ctrlCompany);

		// �t�֌v�㕔��R�[�h
		ctrlDepartment.setLocation(x, y = y + 35);
		pnlBodyTop.add(ctrlDepartment);

		// �t�։ȖڃR�[�h
		ctrlItem.setLocation(x, y = y + 25);
		pnlBodyTop.add(ctrlItem);

		// �����R�[�h
		ctrlCustomer.setLocation(x, y = y + 65);
		pnlBodyTop.add(ctrlCustomer);

	}

	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlCompany.setTabControlNo(i++);
		ctrlDepartment.setTabControlNo(i++);
		ctrlItem.setTabControlNo(i++);
		ctrlCustomer.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);

	}

}
