package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * ���[�U�[�}�X�^�̎w����ʃ��C�A�E�g
 * 
 * @author AIS
 */
public class MG0026UserAuthUpdatePanel extends TMainPanel {

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** ���b�N�A�E�g�ő�� */
	public TLabelComboBox rockOutMaxCount;

	/** ���b�N�A�E�g�ő�񐔃��x�� */
	public TLabel rockOutMaxLabel;

	/** ���b�N�A�E�g�������� */
	public TLabelNumericField rockOutLatTime;

	/** ���b�N�A�E�g�������ԃ��x�� */
	public TLabel rockOutLatLabel;

	/** �Œ�p�X���[�h�� */
	public TLabelComboBox minPasslength;

	/** �Œ�p�X���[�h���̃��x�� */
	public TLabel minPassLabel;

	/** �p�X���[�h���� */
	public TLabelNumericField passTerm;

	/** �p�X���[�h���Ԃ̃��x�� */
	public TLabel passTermLabel;

	/** ���G���x�� */
	public TLabelComboBox difficultLevel;

	/** ����ێ��� */
	public TLabelComboBox histryCount;

	/** ���Ԑ؂ꃁ�b�Z�[�W�ʒm */
	public TLabelNumericField messagePshu;

	/** ���Ԑ؂ꃁ�b�Z�[�W�ʒm�̃��x�� */
	public TLabel messagePshuLabel;

	@Override
	public void initComponents() {

		btnSettle = new TImageButton(IconType.SETTLE);
		rockOutMaxCount = new TLabelComboBox();
		rockOutMaxLabel = new TLabel();
		rockOutLatTime = new TLabelNumericField();
		rockOutLatLabel = new TLabel();
		minPasslength = new TLabelComboBox();
		minPassLabel = new TLabel();
		passTerm = new TLabelNumericField();
		passTermLabel = new TLabel();
		difficultLevel = new TLabelComboBox();
		histryCount = new TLabelComboBox();
		messagePshu = new TLabelNumericField();
		messagePshuLabel = new TLabel();

	}

	@Override
	public void allocateComponents() {

		// �㕔
		pnlBody.setLayout(null);
		pnlBody.setMaximumSize(new Dimension(100, 1000));
		pnlBody.setMinimumSize(new Dimension(100, 1000));
		pnlBody.setPreferredSize(new Dimension(100, 1000));

		// �m��{�^��
		int x = 400;
		int y = 600;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(HEADER_LEFT_X, HEADER_Y);
		pnlHeader.add(btnSettle);

		// ���b�N�A�E�g�ő��
		rockOutMaxCount.setLabelSize(200);
		rockOutMaxCount.setComboSize(100);
		rockOutMaxCount.setSize(330, 20);
		rockOutMaxCount.setLocation(x - 400, y - 580);
		pnlBody.add(rockOutMaxCount);
		// ���b�N�A�E�g�ő�񐔂̉�̃��x��
		rockOutMaxLabel.setSize(30, 20);
		rockOutMaxLabel.setLocation(x + -70, y - 580);
		rockOutMaxLabel.setLangMessageID("C01761");
		pnlBody.add(rockOutMaxLabel);

		// ���b�N�A�E�g��������
		rockOutLatTime.setLabelSize(200);
		rockOutLatTime.setFieldSize(100);
		rockOutLatTime.setSize(350, 20);
		rockOutLatTime.setLocation(x - 410, y - 550);
		rockOutLatTime.setMaxLength(4);
		rockOutLatTime.setNumericFormat("####");
		rockOutLatTime.setValue("0");

		rockOutLatTime.setAllowedSpace(false);
		pnlBody.add(rockOutLatTime);
		// ���b�N�A�E�g�������Ԃ̃��x��
		rockOutLatLabel.setSize(30, 20);
		rockOutLatLabel.setLocation(x - 70, y - 550);
		rockOutLatLabel.setLangMessageID("C02778");
		pnlBody.add(rockOutLatLabel);

		// �Œ�p�X���[�h��
		minPasslength.setLabelSize(200);
		minPasslength.setComboSize(100);
		minPasslength.setSize(330, 20);
		minPasslength.setLocation(x - 400, y - 520);
		minPasslength.setLangMessageID("C02775");
		pnlBody.add(minPasslength);
		// �Œ�p�X���[�h���̃��x��
		minPassLabel.setSize(30, 20);
		minPassLabel.setLocation(x - 70, y - 520);
		minPassLabel.setLangMessageID("C02161");
		pnlBody.add(minPassLabel);

		// �p�X���[�h����
		passTerm.setLabelSize(200);
		passTerm.setFieldSize(100);
		passTerm.setSize(350, 20);
		passTerm.setLocation(x - 410, y - 490);
		passTerm.setMaxLength(3);
		passTerm.setNumericFormat("###");
		passTerm.setValue("0");
		passTerm.setAllowedSpace(false);

		pnlBody.add(passTerm);
		// �p�X���[�h���Ԃ̃��x��
		passTermLabel.setSize(30, 20);
		passTermLabel.setLocation(x - 70, y - 490);
		passTermLabel.setLangMessageID("C02779");
		pnlBody.add(passTermLabel);

		// ���G���x��
		difficultLevel.setLabelSize(200);
		difficultLevel.setComboSize(100);
		difficultLevel.setSize(330, 20);
		difficultLevel.setLocation(x - 400, y - 460);

		pnlBody.add(difficultLevel);

		// ����ێ���
		histryCount.setLabelSize(200);
		histryCount.setComboSize(100);
		histryCount.setSize(330, 20);
		histryCount.setLangMessageID("C02777");
		histryCount.setLocation(x - 400, y - 430);
		pnlBody.add(histryCount);

		// �����؂ꃁ�b�Z�[�W�ʒm
		messagePshu.setLabelSize(200);
		messagePshu.setFieldSize(100);
		messagePshu.setSize(350, 20);
		messagePshu.setLocation(x - 410, y - 400);
		messagePshu.setMaxLength(3);
		messagePshu.setLangMessageID("C04285");
		messagePshu.setValue("0");
		messagePshu.setNumericFormat("###");
		messagePshu.setAllowedSpace(false);

		pnlBody.add(messagePshu);

		// �����؂ꃁ�b�Z�[�W�ʒm�̃��x��
		messagePshuLabel.setSize(30, 20);
		messagePshuLabel.setLocation(x - 70, y - 400);
		messagePshuLabel.setLangMessageID("C04286");
		pnlBody.add(messagePshuLabel);

	}

	@Override
	public void setTabIndex() {
		int i = 0;
		rockOutMaxCount.setTabControlNo(i++);
		rockOutLatTime.setTabControlNo(i++);
		minPasslength.setTabControlNo(i++);
		passTerm.setTabControlNo(i++);
		difficultLevel.setTabControlNo(i++);
		histryCount.setTabControlNo(i++);
		messagePshu.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
	}

}
