package jp.co.ais.trans.master.common;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import com.klg.jclass.table.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ���ʃ}�X�^�����_�C�A���O
 * 
 * @author Yit
 */
public class REFDialogMasterCtrl extends TAppletClientBase {

	/** �_�C�A���O */
	protected REFDialogMaster dialog;

	/** �����T�[�u���b�g */
	protected static final String TARGET_SERVLET = "REFDialogMasterServlet";

	/** �Ȗڃ}�X�^�ꗗ */
	public static final int KMK_MST = 1;

	/** �⏕�Ȗڃ}�X�^�ꗗ */
	public static final int HKM_MST = 2;

	/** ����Ȗڃ}�X�^�ꗗ */
	public static final int UKM_MST = 3;

	/** �x�������}�X�^�ꗗ */
	public static final int PAYMENTCON_MST = 4;

	/** �Ȗڑ̌n�}�X�^�ꗗ */
	public static final int KMK_TK_MST = 5;

	/** �Ј��}�X�^�ꗗ */
	public static final int EMP_MST = 10;

	/** ��s�����}�X�^ */
	public static final int BANK_ACCOUNT = 11;

	/** �����}�X�^ */
	public static final int CUSTOMER_MST = 12;

	/** �ʉ݃}�X�^ */
	public static final int CURRENCY_MST = 13;

	/** ����}�X�^ */
	public static final int DEPARTMENT_MST = 14;

	/** �`�[�E�v�ƍs�E�v�}�X�^�i���ʁj */
	public static final int MEMO_MST = 15;

	/** ����Ń}�X�^ */
	public static final int TAX_MST = 16;

	/** �Ј������}�X�^ */
	public static final int EMP_SEARCH_MST = 17;

	/** �x�������@�}�X�^ */
	public static final int PAYMENT_MST = 18;

	/** ����}�X�^�ꗗ */
	public static final int BMN_MST = 19;

	/** �Ǘ�1�}�X�^�ꗗ */
	public static final int KNR1_MST = 20;

	/** �Ǘ�2�}�X�^�ꗗ */
	public static final int KNR2_MST = 21;

	/** �Ǘ�3�}�X�^�ꗗ */
	public static final int KNR3_MST = 22;

	/** �Ǘ�4�}�X�^�ꗗ */
	public static final int KNR4_MST = 23;

	/** �Ǘ�5�}�X�^�ꗗ */
	public static final int KNR5_MST = 24;

	/** �Ǘ�6�}�X�^�ꗗ */
	public static final int KNR6_MST = 25;

	/** ���ݒ�}�X�^ */
	public static final int ENV_MST = 28;

	/** ��s�����}�X�^(��s���ɋ�s�}�X�^.��s��+��s�}�X�^.�x�X����\������) */
	public static final int BANK_ACCOUNT_B = 29;

	/** �v���� */
	public static final int APP_KAI = 30;

	/** ��s�}�X�^ */
	public static final int BNK_BNK = 31;

	/** ��s�}�X�^�i�x�X�j */
	public static final int BNK_STN = 32;

	/** �A�N�Z�X�e�[�v */
	protected int gamenType = 0;

	/** �f�k�Ȗڐ���敪 */
	protected String kmkCntGl = "";

	/** �ȖڃR�[�h */
	protected String kmkCode = "";

	/** �`�[���t */
	protected String slipDate = "";

	/** BS��������敪 */
	protected String kesiKbn = "";

	/** �W�v�敪 */
	protected String sumKbn = "";

	/** ��ЃR�[�h */
	protected String kaiCode = "";

	/** �U�֓`�[�����׸� */
	protected String furikaeFlg = "";

	/** AR�Ȗڐ���敪 */
	protected String kmkCntAr = "";

	/** AR�Ȗڐ���敪(�����p) */
	protected String kmkCntUnAr = "";

	/** AP�Ȗڐ���敪 */
	protected String kmkCntAp = "";

	/** �o��Z�`�[�����׸� */
	protected String keihiFlg = "";

	/** ���v������׸� */
	protected String saimuFlg = "";

	/** ���v������׸� */
	protected String saikenFlg = "";

	/** �������`�[�����׸� */
	protected String saikesiFlg = "";

	/** ���Y�v��`�[�����׸� */
	protected String sisanFlg = "";

	/** �x���˗��`�[�����׸� */
	protected String siharaiFlg = "";

	/** �⏕�ȖڃR�[�h */
	protected String hkmCode = "";

	/** ����R�[�h */
	protected String bmnCode = "";

	/** �]���֑Ώۃt���O */
	protected String excFlg = "";

	/** �����`�[���̓t���O */
	protected String nyuKin = "";

	/** �o���`�[���̓t���O */
	protected String shutsuKin = "";

	/** �g�D�R�[�h */
	protected String dpkSsk = "";

	/** �z������(0:�܂� 1:�܂܂Ȃ�) */
	protected String bmnKbn = "";

	/** ��ʕ��庰�� */
	protected String upBmnCode = "";

	/** �K�w���� */
	protected String dpkLvl = "";

	/** �������庰�� */
	protected String baseBmnCode = "";

	/** �����K�w���� */
	protected String baseDpkLvl = "";

	/** �J�n�R�[�h */
	protected String beginCode = "";

	/** �I���R�[�h */
	protected String endCode = "";

	/** �Ȗڑ̌n�R�[�h */
	protected String kmtCode = "";

	/** �Ȗڑ̌n�t���O */
	protected String kmtFlg = "";

	/** �Ȗڑ̌n�敪 */
	protected String kmtKbn = "";

	/** �Ȗڎ�� */
	protected String kmkShu = "";

	/** �ݎ؋敪 */
	protected String dcKbn = "";

	/** �⏕�敪 */
	protected String hkmKbn = "";

	/** ����敪 */
	protected String ukmKbn = "";

	/** �Œ蕔�庰�� */
	protected String koteiDepCode = "";

	/** ����ź��� */
	protected String zeiCode = "";

	/** �������̓t���O */
	protected String triCodeFlg = "";

	/** ���������̓t���O */
	protected String hasFlg = "";

	/** �Ј����̓t���O */
	protected String empCodeFlg = "";

	/** �Ǘ��P�����׸� */
	protected String knrFlg1 = "";

	/** �Ǘ��Q�����׸� */
	protected String knrFlg2 = "";

	/** �Ǘ��R�����׸� */
	protected String knrFlg3 = "";

	/** �Ǘ��S�����׸� */
	protected String knrFlg4 = "";

	/** �Ǘ��T�����׸� */
	protected String knrFlg5 = "";

	/** �Ǘ��U�����׸� */
	protected String knrFlg6 = "";

	/** ���v�P�����׸� */
	protected String hmFlg1 = "";

	/** ���v�Q�����׸� */
	protected String hmFlg2 = "";

	/** ���v�R�����׸� */
	protected String hmFlg3 = "";

	/** ����ېœ����׸� */
	protected String uriZeiFlg = "";

	/** �d���ېœ����׸� */
	protected String sirZeiFlg = "";

	/** ���ʉ݃t���O */
	protected String mcrFlg = "";

	/** BG�Ȗڐ���敪 */
	protected String kmkCntBg = "";

	/** ���E���Z����敪 */
	protected String kmkCntSousai = "";

	/** ��������R�[�h�敪 */
	protected String depCodeKbn = "";

	/** ���Z�Ώۃt���O */
	protected String clearanceFlg = "";

	/** ��������R�[�h */
	protected String depCode = "";

	/** �Q�ƃJ������ : DataSource�ɑ΂�getNumColumn()�ݒ萔�ȏ�̎Q�Ƃ�����Ƃ��A���̃J��������ݒ肷�� */
	protected int numReferColumn = 0;

	/** �ʉ݋敪 */
	private boolean curKbn;

	/** �T�[�o�[�� */
	private String serverName;

	/** �J�n�R�[�h */
	private String startCode = "";

	/** ��s�����R�[�h */
	protected List<String> cbkCodes;

	/** �R�[�h���X�g */
	protected List<String> codes;

	/** dialog�p�p�����^ */
	protected Map<String, String> paramMap;

	/** �p�����[�^�N���X�ێ��p */
	protected TransferBase paramObject;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param gamenKbn ��ʋ敪
	 */
	public REFDialogMasterCtrl(TPanel parent, int gamenKbn) {
		this(parent.getParentFrame(), gamenKbn);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param gamenKbn ��ʋ敪
	 */
	public REFDialogMasterCtrl(Frame parent, int gamenKbn) {
		dialog = new REFDialogMaster(parent, true, this, gamenKbn);

		gamenType = gamenKbn;
		lockBtn(false);
	}

	/**
	 * �R���X�g���N�^(�_�C�A���O��)
	 * 
	 * @param parent
	 * @param gamenKbn
	 */
	public REFDialogMasterCtrl(Dialog parent, int gamenKbn) {
		dialog = new REFDialogMaster(parent, true, this, gamenKbn);

		gamenType = gamenKbn;
		lockBtn(false);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param comp �ΏۃR���|�[�l���g
	 * @param gamenKbn ��ʋ敪
	 */
	public REFDialogMasterCtrl(Component comp, int gamenKbn) {
		Component parent = TGuiUtil.getParentFrameOrDialog2(comp);

		if (parent instanceof Frame) {
			dialog = new REFDialogMaster((Frame) parent, true, this, gamenKbn);
		} else if (parent instanceof Dialog) {
			dialog = new REFDialogMaster((Dialog) parent, true, this, gamenKbn);
		}

		gamenType = gamenKbn;
		lockBtn(false);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param comp �ΏۃR���|�[�l���g
	 * @param gamenKbn ��ʋ敪
	 * @param param �e��p�����[�^(�^�C�g����)
	 */
	public REFDialogMasterCtrl(Component comp, int gamenKbn, Map param) {

		param.put("kbn", String.valueOf(gamenKbn));// ��ʍ\���W��

		Component parent = TGuiUtil.getParentFrameOrDialog2(comp);

		if (parent instanceof Frame) {
			dialog = new REFDialogMaster((Frame) parent, true, this, param);
		} else if (parent instanceof Dialog) {
			dialog = new REFDialogMaster((Dialog) parent, true, this, param);
		}

		gamenType = gamenKbn;
		lockBtn(false);
	}

	/**
	 * �\��<br>
	 * �����_�C�A���O�G���A�ҏW��(true)
	 */
	public void show() {
		dialog.setVisible(true);
	}

	/**
	 * �m��{�^���������ꂽ���ǂ���
	 * 
	 * @return �m��̏ꍇtrue
	 */
	public boolean isSettle() {
		return dialog.isSettle;
	}

	/**
	 * �m��{�^������
	 * 
	 * @param bol
	 */
	void lockBtn(boolean bol) {
		dialog.btnSettle.setEnabled(bol);
	}

	/**
	 * ����
	 */
	void disposeDialog() {
		dialog.setVisible(false);
		dialog.dispose();
	}

	/**
	 * ��������
	 */
	public void searchData() {
		searchData(true);
	}

	/**
	 * ��������
	 * 
	 * @param msgFlg ���b�Z�[�W�\���t���O true�F�\�� false:�\�����Ȃ�
	 */
	public void searchData(boolean msgFlg) {

		try {
			this.reflesh();

			if (dialog.ssJournal.getDataSource().getNumRows() == 0) {
				// �������ʖ������b�Z�[�W�\��
				if (msgFlg) {
					super.showMessage(dialog, "W00100");
				}
				dialog.txtCode.requestFocus();
				return;
			} else {
				dialog.ssJournal.requestFocus();
			}
		} catch (IOException e) {
			// ����ɏ�������܂���ł���
			errorHandler(dialog, e, "E00009");
		}
	}

	/**
	 * �f�k�Ȗڐ���敪��ݒ肷��B<BR>
	 * 
	 * @param kmkCntGl �f�k�Ȗڐ���敪
	 */
	public void setKmkCntGl(String kmkCntGl) {
		this.kmkCntGl = kmkCntGl;
	}

	/**
	 * �ȖڃR�[�h��ݒ肷��B<BR>
	 * 
	 * @param strKmkCode �ȖڃR�[�h
	 */
	public void setKmkCode(String strKmkCode) {
		this.kmkCode = strKmkCode;
	}

	/**
	 * �`�[���t��ݒ肷��B
	 * 
	 * @param slipDate �`�[���t
	 */
	public void setSlipDate(String slipDate) {
		this.slipDate = slipDate;
	}

	/**
	 * BS��������敪��ݒ肷��B
	 * 
	 * @param kesiKbn BS��������敪
	 */
	public void setKesiKbn(String kesiKbn) {
		this.kesiKbn = kesiKbn;
	}

	/**
	 * �W�v�敪��ݒ肷��B
	 * 
	 * @param sumKbn �W�v�敪
	 */
	public void setSumKbn(String sumKbn) {
		this.sumKbn = sumKbn;
	}

	/**
	 * �Ȗڎ�ʂ�ݒ肷��B
	 * 
	 * @param kmkShu �Ȗڎ��
	 */
	public void setKmkShu(String kmkShu) {
		this.kmkShu = kmkShu;
	}

	/**
	 * �ݎ؋敪��ݒ肷��B
	 * 
	 * @param dcKbn �ݎ؋敪
	 */
	public void setDcKbn(String dcKbn) {
		this.dcKbn = dcKbn;
	}

	/**
	 * �⏕�敪��ݒ肷��B
	 * 
	 * @param hkmKbn �⏕�敪
	 */
	public void setHkmKbn(String hkmKbn) {
		this.hkmKbn = hkmKbn;
	}

	/**
	 * ����敪��ݒ肷��B
	 * 
	 * @param ukmKbn ����敪
	 */
	public void setUkmKbn(String ukmKbn) {
		this.ukmKbn = ukmKbn;
	}

	/**
	 * ��ЃR�[�h��ݒ肷��B
	 * 
	 * @param kaiCode ��ЃR�[�h
	 */
	public void setKaiCode(String kaiCode) {
		this.kaiCode = kaiCode;
	}

	/**
	 * �U�֓`�[�����׸ނ�ݒ肷��B
	 * 
	 * @param furikaeFlg �U�֓`�[�����׸�
	 */
	public void setFurikaeFlg(String furikaeFlg) {
		this.furikaeFlg = furikaeFlg;
	}

	/**
	 * AR����敪��ݒ肷��B
	 * 
	 * @param kmkCntAr AR����敪
	 */
	public void setkmkCntAr(String kmkCntAr) {
		this.kmkCntAr = kmkCntAr;
	}

	/**
	 * AR����敪��ݒ肷��B(�����p)
	 * 
	 * @param kmkCntUnAr AR����敪
	 */
	public void setkmkCntUnAr(String kmkCntUnAr) {
		this.kmkCntUnAr = kmkCntUnAr;
	}

	/**
	 * AP����敪��ݒ肷��B
	 * 
	 * @param kmkCntAp AR����敪
	 */
	public void setkmkCntAp(String kmkCntAp) {
		this.kmkCntAp = kmkCntAp;
	}

	/**
	 * �Œ蕔�庰�ނ�ݒ肷��B
	 * 
	 * @param koteiDepCode �Œ蕔�庰��
	 */
	public void setKoteiDepCode(String koteiDepCode) {
		this.koteiDepCode = koteiDepCode;
	}

	/**
	 * ����ź��ނ�ݒ肷��B
	 * 
	 * @param zeiCode ����ź���
	 */
	public void setZeiCode(String zeiCode) {
		this.zeiCode = zeiCode;
	}

	/**
	 * �o��Z�`�[�����׸ނ�ݒ肷��B
	 * 
	 * @param keihiFlg �o��Z�`�[�����׸�
	 */
	public void setKeihiFlg(String keihiFlg) {
		this.keihiFlg = keihiFlg;
	}

	/**
	 * ���v������׸ނ�ݒ肷��B
	 * 
	 * @param saimuFlg ���v������׸�
	 */
	public void setSaimuFlg(String saimuFlg) {
		this.saimuFlg = saimuFlg;
	}

	/**
	 * ���v������׸ނ�ݒ肷��B
	 * 
	 * @param saikenFlg ���v������׸�
	 */
	public void setSaikenFlg(String saikenFlg) {
		this.saikenFlg = saikenFlg;
	}

	/**
	 * �������`�[�����׸ނ�ݒ肷��B
	 * 
	 * @param saikesiFlg �������`�[�����׸�
	 */
	public void setSaikesiFlg(String saikesiFlg) {
		this.saikesiFlg = saikesiFlg;
	}

	/**
	 * ���Y�v��`�[�����׸ނ�ݒ肷��B
	 * 
	 * @param sisanFlg ���Y�v��`�[�����׸�
	 */
	public void setSisanFlg(String sisanFlg) {
		this.sisanFlg = sisanFlg;
	}

	/**
	 * �x���˗��`�[�����׸ނ�ݒ肷��B
	 * 
	 * @param siharaiFlg �x���˗��`�[�����׸�
	 */
	public void setSiharaiFlg(String siharaiFlg) {
		this.siharaiFlg = siharaiFlg;
	}

	/**
	 * ���������׸ނ�ݒ肷��B
	 * 
	 * @param triCodeFlg ���������׸�
	 */
	public void setTriCodeFlag(String triCodeFlg) {
		this.triCodeFlg = triCodeFlg;
	}

	/**
	 * �����������׸ނ�ݒ肷��B
	 * 
	 * @param hasFlg �����������׸�
	 */
	public void setHasFlg(String hasFlg) {
		this.hasFlg = hasFlg;
	}

	/**
	 * �Ј������׸ނ�ݒ肷��B
	 * 
	 * @param empCodeFlg �Ј������׸�
	 */
	public void setEmpCodeFlg(String empCodeFlg) {
		this.empCodeFlg = empCodeFlg;
	}

	/**
	 * �⏕�ȖڃR�[�h��ݒ肷��B
	 * 
	 * @param hkmCode �⏕�ȖڃR�[�h
	 */
	public void setHkmCode(String hkmCode) {
		this.hkmCode = hkmCode;
	}

	/**
	 * ����R�[�h��ݒ肷��B
	 * 
	 * @param bmnCode ����R�[�h
	 */
	public void setBmnCode(String bmnCode) {
		this.bmnCode = bmnCode;
	}

	/**
	 * �]���֑Ώۃt���O��ݒ肷��B
	 * 
	 * @param excFlg �]���֑Ώۃt���O
	 */
	public void setExcFlg(String excFlg) {
		this.excFlg = excFlg;
	}

	/**
	 * �����`�[���̓t���O��ݒ肷��B
	 * 
	 * @param nyuKin �����`�[���̓t���O
	 */
	public void setNyuKin(String nyuKin) {
		this.nyuKin = nyuKin;
	}

	/**
	 * �o���`�[���̓t���O��ݒ肷��B
	 * 
	 * @param shutsuKin �o���`�[���̓t���O
	 */
	public void setShutsuKin(String shutsuKin) {
		this.shutsuKin = shutsuKin;
	}

	/**
	 * �g�D�R�[�h�t���O��ݒ肷��B
	 * 
	 * @param dpkSsk �g�D�R�[�h
	 */
	public void setDpkSsk(String dpkSsk) {
		this.dpkSsk = dpkSsk;
	}

	/**
	 * �z������(0:�܂� 1:�܂܂Ȃ�)
	 * 
	 * @param bmnKbn �z������
	 */
	public void setBmnKbn(String bmnKbn) {
		this.bmnKbn = bmnKbn;
	}

	/**
	 * ��ʕ��庰��
	 * 
	 * @param upBmnCode ��ʕ��庰��
	 */
	public void setUpBmnCode(String upBmnCode) {
		this.upBmnCode = upBmnCode;
	}

	/**
	 * �K�w����
	 * 
	 * @param dpkLvl �K�w����
	 */
	public void setDpkLvl(String dpkLvl) {
		this.dpkLvl = dpkLvl;
	}

	/**
	 * �������庰��
	 * 
	 * @param baseBmnCode �������庰��
	 */
	public void setBaseBmnCode(String baseBmnCode) {
		this.baseBmnCode = baseBmnCode;
	}

	/**
	 * �����K�w����
	 * 
	 * @param baseDpkLvl �����K�w����
	 */
	public void setBaseDpkLvl(String baseDpkLvl) {
		this.baseDpkLvl = baseDpkLvl;
	}

	/**
	 * �J�n�R�[�h
	 * 
	 * @param beginCode �J�n�R�[�h
	 */
	public void setBeginCode(String beginCode) {
		this.beginCode = beginCode;
	}

	/**
	 * �I���R�[�h
	 * 
	 * @param endCode �I���R�[�h
	 */
	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}

	/**
	 * �Ȗڑ̌n�R�[�h
	 * 
	 * @param kmtCode �Ȗڑ̌n�R�[�h
	 */
	public void setKmtCode(String kmtCode) {
		this.kmtCode = kmtCode;
	}

	/**
	 * �Ȗڑ̌n�t���O
	 * 
	 * @param kmtFlg �Ȗڑ̌n�t���O
	 */
	public void setKmtFlg(String kmtFlg) {
		this.kmtFlg = kmtFlg;
	}

	/**
	 * �Ȗڑ̌n�敪
	 * 
	 * @param kmtKbn �Ȗڑ̌n�敪
	 */
	public void setKmtKbn(String kmtKbn) {
		this.kmtKbn = kmtKbn;
	}

	/**
	 * �Ǘ��P�����׸�
	 * 
	 * @param knrFlg1 �Ǘ��P�����׸
	 */
	public void setKnrFlg1(String knrFlg1) {
		this.knrFlg1 = knrFlg1;
	}

	/**
	 * �Ǘ��Q�����׸�
	 * 
	 * @param knrFlg2 �Ǘ��Q�����׸
	 */
	public void setKnrFlg2(String knrFlg2) {
		this.knrFlg2 = knrFlg2;
	}

	/**
	 * �Ǘ��R�����׸�
	 * 
	 * @param knrFlg3 �Ǘ��R�����׸
	 */
	public void setKnrFlg3(String knrFlg3) {
		this.knrFlg3 = knrFlg3;
	}

	/**
	 * �Ǘ��S�����׸�
	 * 
	 * @param knrFlg4 �Ǘ��S�����׸
	 */
	public void setKnrFlg4(String knrFlg4) {
		this.knrFlg4 = knrFlg4;
	}

	/**
	 * �Ǘ��T�����׸�
	 * 
	 * @param knrFlg5 �Ǘ��T�����׸
	 */
	public void setKnrFlg5(String knrFlg5) {
		this.knrFlg5 = knrFlg5;
	}

	/**
	 * �Ǘ��U�����׸�
	 * 
	 * @param knrFlg6 �Ǘ��U�����׸
	 */
	public void setKnrFlg6(String knrFlg6) {
		this.knrFlg6 = knrFlg6;
	}

	/**
	 * ���v�P�����׸�
	 * 
	 * @param hmFlg1 ���v�P�����׸�
	 */
	public void setHmFlg1(String hmFlg1) {
		this.hmFlg1 = hmFlg1;
	}

	/**
	 * ���v�Q�����׸�
	 * 
	 * @param hmFlg2 ���v�Q�����׸�
	 */
	public void setHmFlg2(String hmFlg2) {
		this.hmFlg2 = hmFlg2;
	}

	/**
	 * ���v�R�����׸�
	 * 
	 * @param hmFlg3 ���v�R�����׸�
	 */
	public void setHmFlg3(String hmFlg3) {
		this.hmFlg3 = hmFlg3;
	}

	/**
	 * ����ېœ����׸�
	 * 
	 * @param uriZeiFlg ����ېœ����׸�
	 */
	public void setUriZeiFlg(String uriZeiFlg) {
		this.uriZeiFlg = uriZeiFlg;
	}

	/**
	 * �d���ېœ����׸�
	 * 
	 * @param sirZeiFlg �d���ېœ����׸�
	 */
	public void setSirZeiFlg(String sirZeiFlg) {
		this.sirZeiFlg = sirZeiFlg;
	}

	/**
	 * ���ʉ݃t���O��ݒ肷��B
	 * 
	 * @param mcrFlg ���ʉ݃t���O
	 */
	public void setMcrFlg(String mcrFlg) {
		this.mcrFlg = mcrFlg;
	}

	/**
	 * BG�Ȗڐ���敪��ݒ肷��B
	 * 
	 * @param kmkCntBg BG�Ȗڐ���敪
	 */
	public void setKmkCntBg(String kmkCntBg) {
		this.kmkCntBg = kmkCntBg;
	}

	/**
	 * ���E���Z����敪��ݒ肷��B
	 * 
	 * @param kmkCntSousai ���E���Z����敪
	 */
	public void setKmkCntSousai(String kmkCntSousai) {
		this.kmkCntSousai = kmkCntSousai;
	}

	/**
	 * ��������R�[�h�敪
	 * 
	 * @param depCodeKbn ��������R�[�h�敪
	 */
	public void setDepCodeKbn(String depCodeKbn) {
		this.depCodeKbn = depCodeKbn;
	}

	/**
	 * ��������R�[�h
	 * 
	 * @param depCode ��������R�[�h
	 */
	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	/**
	 * �ʉ݋敪�̎擾
	 * 
	 * @return curKbn
	 */
	public boolean isCurKbn() {
		return curKbn;
	}

	/**
	 * �ʉ݋敪�̐ݒ�
	 * 
	 * @param curKbn
	 */
	public void setCurKbn(boolean curKbn) {
		this.curKbn = curKbn;
	}

	/**
	 * dialog�p �p�����^�i��s�����j
	 * 
	 * @param paramMap �l�̏��idailog�̂S�̓��̓t�B�[���h����j
	 */
	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	/**
	 * ���������쐬
	 */
	protected void setCondition() {
		switch (gamenType) {
			case KMK_MST:// �Ȗڈꗗ�}�X�^����
				setKmkMstCondition();
				break;
			case HKM_MST:
				// �⏕�Ȗڃ}�X�^�ꗗ
				setHkmMstCondition();
				break;
			case UKM_MST:
				// ����Ȗڃ}�X�^�ꗗ
				setUkmMstCondition();
				break;
			case BMN_MST:
				// ����}�X�^�ꗗ
				setBmnMstCondition();
				break;
			case ENV_MST:
				// ����}�X�^�ꗗ
				setEnvMstCondition();
				break;
			case KMK_TK_MST:
				// �Ȗڑ̌n�}�X�^�ꗗ
				setKmkTkMstCondition();
				break;
			case BANK_ACCOUNT:// ��s�����}�X�^�ꗗ
				setSearchCondition(serverName);
				break;
			case CUSTOMER_MST: // �����}�X�^�ꗗ
				setCustomerCondition(serverName);
				break;
			case CURRENCY_MST:// �ʉ݃}�X�^�ꗗ
				setCurrencyCondition(serverName);
				break;
			case DEPARTMENT_MST:// ����}�X�^�ꗗ
				setDepartmentCondition(serverName);
				break;
			case MEMO_MST:// �E�v�}�X�^�ꗗ
				setMemoCondition(serverName);
				break;
			case TAX_MST:// ����Ń}�X�^�ꗗ
				setTaxCondition(serverName);
				break;
			case EMP_SEARCH_MST:// �Ј������}�X�^�ꗗ
				setEmployeeCondition(serverName);
				break;
			case PAYMENT_MST:// �x�������@�}�X�^�ꗗ
				setPaymentCondition(serverName);
				break;
			case PAYMENTCON_MST:// �x�������}�X�^�ꗗ
				setPaymentConCondition(serverName);
				break;
			case EMP_MST:
				// �Ј��}�X�^�ꗗ
				setEmpMstCondition();
				break;
			case KNR1_MST:
				// �Ǘ�1�}�X�^�ꗗ
				setKnrMstCondition("1");
				break;
			case KNR2_MST:
				// �Ǘ�2�}�X�^�ꗗ
				setKnrMstCondition("2");
				break;
			case KNR3_MST:
				// �Ǘ�3�}�X�^�ꗗ
				setKnrMstCondition("3");
				break;
			case KNR4_MST:
				// �Ǘ�4�}�X�^�ꗗ
				setKnrMstCondition("4");
				break;
			case KNR5_MST:
				// �Ǘ�5�}�X�^�ꗗ
				setKnrMstCondition("5");
				break;
			case KNR6_MST:
				// �Ǘ�6�}�X�^�ꗗ
				setKnrMstCondition("6");
				break;
			case BANK_ACCOUNT_B:// ��s�����}�X�^�ꗗ
				setBankSearchCondition(serverName);
				break;
			case APP_KAI:// �v����
				setAppropriateCompanyCondition();
				break;
			case BNK_BNK:// ��s�ꗗ
				setBnkMstCondition(serverName);
				break;
			case BNK_STN:// ��s�x�X�ꗗ
				setBnkStnMstCondition(serverName);
				break;
			default:
				break;
		}
	}

	/**
	 * ��ʃ��t���b�V��
	 * 
	 * @throws IOException
	 */
	protected void reflesh() throws IOException {
		// ��ʋ敪�ɂ��A��ʕʃT�u���b�g�ɑ��M����p�����^��ݒ�B
		setCondition();
		// �J�n�R�[�h
		addSendValues("START_CODE", Util.avoidNull(this.beginCode));
		// �I���R�[�h
		addSendValues("END_CODE", Util.avoidNull(this.endCode));
		// ���M
		if (!request(TARGET_SERVLET)) {
			errorHandler(dialog);
		}

		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = new Vector<Vector>();
		Iterator recordIte;

		// �Ј����X�g�d��������
		if (gamenType == REFDialogMasterCtrl.EMP_SEARCH_MST) {

			// �d�����ʗpmap�ƍč\���p���X�g�𐶐�����B
			List list = getResultList();
			Map<String, String> b = new HashMap<String, String>();
			List<Object> distinctlist = new ArrayList<Object>();

			// �Ј��R�[�h���d�����Ă��Ȃ��Ј��������X�g�ɉ�����B
			for (Object empObject : list) {
				List emplist = (List) empObject;
				String empName = (String) emplist.get(1);
				if (!b.containsKey(empName)) {
					b.put(empName, "");
					distinctlist.add(empObject);
				}
			}
			recordIte = distinctlist.iterator();

		}

		// Object���X�g�̏ꍇ
		else if (gamenType == REFDialogMasterCtrl.BNK_BNK || gamenType == REFDialogMasterCtrl.BNK_STN) {
			List<TransferBase> list = (List<TransferBase>) getResultObject();

			// �X�v���b�h���f
			setObjectList(list);
			return;
		}

		else {
			recordIte = getResultList().iterator();
		}

		for (int row = 0; recordIte.hasNext(); row++) {
			List dataList = (List) recordIte.next();

			Vector<String> colum = new Vector<String>();
			switch (gamenType) {
				case REFDialogMasterCtrl.KMK_MST:// �Ȗڈꗗ�}�X�^����
					// �ȖڃR�[�h�A�Ȗڗ��́A�Ȗڌ�������
					colum.add((String) dataList.get(1));
					colum.add((String) dataList.get(3));
					colum.add((String) dataList.get(4));
					colum.add((String) dataList.get(8));
					break;
				case REFDialogMasterCtrl.HKM_MST:
					// �⏕�Ȗڃ}�X�^�ꗗ
					// �⏕�ȖڃR�[�h�A�⏕�Ȗڗ��́A�⏕�Ȗڌ�������
					colum.add((String) dataList.get(2));
					colum.add((String) dataList.get(4));
					colum.add((String) dataList.get(5));
					colum.add((String) dataList.get(6));
					break;
				case REFDialogMasterCtrl.UKM_MST:
					// ����Ȗڃ}�X�^�ꗗ
					// ����ȖڃR�[�h�A����Ȗڗ��́A����Ȗڌ�������
					colum.add((String) dataList.get(3));
					colum.add((String) dataList.get(5));
					colum.add((String) dataList.get(6));
					break;
				case REFDialogMasterCtrl.BMN_MST:// ����}�X�^�ꗗ
					// �R�[�h�A���́A��������
					colum.add((String) dataList.get(1));
					colum.add((String) dataList.get(3));
					colum.add((String) dataList.get(4));
					break;
				case REFDialogMasterCtrl.EMP_MST:// �Ј��}�X�^�ꗗ
					// �R�[�h�A���́A��������
					colum.add((String) dataList.get(1));
					colum.add((String) dataList.get(3));
					colum.add((String) dataList.get(4));
					break;
				case REFDialogMasterCtrl.ENV_MST:// ���ݒ�}�X�^�ꗗ
					// �R�[�h�A����
					colum.add((String) dataList.get(0));
					colum.add((String) dataList.get(1));
					break;
				case REFDialogMasterCtrl.BANK_ACCOUNT: // ��s�����}�X�^�ꗗ
					// ��s�����A��s�x�X���́A�����ԍ�
					colum.add((String) dataList.get(1));
					colum.add((String) dataList.get(2));
					colum.add(getWordByDepositType((String) dataList.get(10)) + " " + (String) dataList.get(11));
					break;
				case REFDialogMasterCtrl.CUSTOMER_MST:// ����� �}�X�^�ꗗ

					colum.add((String) dataList.get(1));
					colum.add((String) dataList.get(3));
					colum.add((String) dataList.get(4));
					break;
				case REFDialogMasterCtrl.CURRENCY_MST:// �ʉ݃}�X�^�ꗗ

					colum.add((String) dataList.get(1));
					colum.add((String) dataList.get(3));
					colum.add((String) dataList.get(4));
					break;
				case REFDialogMasterCtrl.DEPARTMENT_MST:// ����}�X�^�ꗗ

					colum.add((String) dataList.get(1));
					colum.add((String) dataList.get(3));
					colum.add((String) dataList.get(4));
					break;
				case REFDialogMasterCtrl.MEMO_MST:// �s�E�v�E�`�[�E�v�}�X�^�ꗗ
					colum.add((String) dataList.get(3));
					colum.add((String) dataList.get(4));
					colum.add((String) dataList.get(5));
					break;
				case REFDialogMasterCtrl.PAYMENT_MST:// �x�������@�}�X�^�ꗗ
					colum.add((String) dataList.get(1));
					colum.add((String) dataList.get(2));
					colum.add((String) dataList.get(3));
					break;
				case REFDialogMasterCtrl.PAYMENTCON_MST:// �x�������}�X�^�ꗗ
					colum.add((String) dataList.get(0));
					colum.add((String) dataList.get(1));
					break;
				case REFDialogMasterCtrl.EMP_SEARCH_MST:// �Ј������}�X�^�ꗗ
				case REFDialogMasterCtrl.KMK_TK_MST:// �Ȗڑ̌n�}�X�^�ꗗ
				case REFDialogMasterCtrl.TAX_MST:// ����Ń}�X�^�ꗗ
				case REFDialogMasterCtrl.KNR1_MST:// �Ǘ�1�}�X�^�ꗗ
				case REFDialogMasterCtrl.KNR2_MST:// �Ǘ�2�}�X�^�ꗗ
				case REFDialogMasterCtrl.KNR3_MST:// �Ǘ�3�}�X�^�ꗗ
				case REFDialogMasterCtrl.KNR4_MST:// �Ǘ�4�}�X�^�ꗗ
				case REFDialogMasterCtrl.KNR5_MST:// �Ǘ�5�}�X�^�ꗗ
				case REFDialogMasterCtrl.KNR6_MST:// �Ǘ�6�}�X�^�ꗗ
					colum.add((String) dataList.get(1));
					colum.add((String) dataList.get(3));
					colum.add((String) dataList.get(4));
					break;
				case REFDialogMasterCtrl.BANK_ACCOUNT_B: // ��s�����}�X�^�ꗗ
					// ��s�����A��s��+�x�X���A�����ԍ�
					colum.add((String) dataList.get(1));
					colum.add((String) dataList.get(24) + " " + (String) dataList.get(25));
					colum.add(getWordByDepositType((String) dataList.get(10)) + " " + (String) dataList.get(11));
					break;
				case REFDialogMasterCtrl.APP_KAI: // �v���Јꗗ
					colum.add((String) dataList.get(0));
					colum.add((String) dataList.get(1));
					break;
				case REFDialogMasterCtrl.BNK_BNK: // ��s�ꗗ
					colum.add(((BNK_MST) dataList).getBNK_CODE());
					colum.add(((BNK_MST) dataList).getBNK_NAME_S());
					colum.add(((BNK_MST) dataList).getBNK_NAME_K());
					break;
				case REFDialogMasterCtrl.BNK_STN: // ��s�x�X�ꗗ
					colum.add(((BNK_MST) dataList).getBNK_STN_CODE());
					colum.add(((BNK_MST) dataList).getBNK_STN_NAME_S());
					colum.add(((BNK_MST) dataList).getBNK_STN_NAME_K());
					break;
				default:
					break;
			}

			cells.add(row, colum);
		}

		lockBtn(cells.size() != 0);

		dialog.setDataList(cells);
	}

	/**
	 * �X�v���b�h���f
	 * 
	 * @param list �I�u�W�F�N�g���X�g
	 */
	protected void setObjectList(List<TransferBase> list) {
		Vector<Vector> cells = new Vector<Vector>();

		for (TransferBase base : list) {
			Vector<String> colum = new Vector<String>();

			switch (gamenType) {
				case REFDialogMasterCtrl.BNK_BNK: // ��s�ꗗ
					colum.add(((BNK_MST) base).getBNK_CODE());
					colum.add(((BNK_MST) base).getBNK_NAME_S());
					colum.add(((BNK_MST) base).getBNK_NAME_K());
					break;
				case REFDialogMasterCtrl.BNK_STN: // ��s�x�X�ꗗ
					colum.add(((BNK_MST) base).getBNK_STN_CODE());
					colum.add(((BNK_MST) base).getBNK_STN_NAME_S());
					colum.add(((BNK_MST) base).getBNK_STN_NAME_K());
					break;
				default:
					break;
			}
			cells.add(colum);
		}

		lockBtn(cells.size() != 0);

		dialog.setDataList(cells);
	}

	/**
	 * ���ݑI������Ă���Z���̏����擾<BR>
	 * 
	 * @return ��񃊃X�g
	 */
	public String[] getCurrencyInfo() {

		// �I������Ă���s��1�ڂ̃J�������擾
		int numRow = dialog.ssJournal.getCurrentRow();
		TableDataModel model = dialog.ssJournal.getDataView();

		int numClmn = ((0 < this.numReferColumn) ? this.numReferColumn : dialog.ssJournal.getDataSource()
			.getNumColumns());

		String[] result = new String[numClmn];
		for (int i = 0; i < numClmn; i++) {
			result[i] = (String) model.getTableDataItem(numRow, i);
		}

		return result;
	}

	/**
	 * ���ݑI������Ă���Z���̏����擾<BR>
	 * 
	 * @param clmn �Q�ƃJ������
	 * @return ��񃊃X�g
	 */
	public String[] getCurrencyInfo(int clmn) {

		this.setNumReferColumn(clmn);

		return this.getCurrencyInfo();
	}

	/**
	 * �Ȗڈꗗ���������ݒ�
	 */
	protected void setKmkMstCondition() {
		// ��ЃR�[�h
		String strKaiCode = "";
		if (this.kaiCode != null && !"".equals(this.kaiCode)) {
			strKaiCode = Util.avoidNull(this.kaiCode);
		} else {
			strKaiCode = super.getLoginUserCompanyCode();
		}
		// �ȖڃR�[�h
		String strKmkCode = StringUtil.convertPrm(dialog.txtCode.getText());
		// �Ȗڗ���
		String strKmkName = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// �Ȗڌ�������
		String strKmkName_K = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// ���M����p�����[�^��ݒ�
		addSendValues("FLAG", "KMK_MST");
		// ��ЃR�[�h
		addSendValues("KAI_CODE", Util.avoidNull(strKaiCode));
		// �ȖڃR�[�h
		addSendValues("KMK_CODE", (Util.avoidNull(strKmkCode)));
		// �Ȗڗ���
		addSendValues("KMK_NAME", (Util.avoidNull(strKmkName)));
		// �Ȗڌ�������
		addSendValues("KMK_NAME_K", (Util.avoidNull(strKmkName_K)));
		// �`�[���t
		addSendValues("SLIP_DATE", Util.avoidNull(this.slipDate));
		// BS��������敪
		addSendValues("KESI_KBN", Util.avoidNull(this.kesiKbn));
		// �W�v�敪
		addSendValues("SUM_KBN", Util.avoidNull(this.sumKbn));
		// �f�k�Ȗڐ���敪
		addSendValues("KMK_CNT_GL", Util.avoidNull(this.kmkCntGl));
		// ����R�[�h
		addSendValues("BMN_CODE", Util.avoidNull(this.bmnCode));
		// �]���֑Ώۃt���O
		addSendValues("EXC_FLG", Util.avoidNull(this.excFlg));
		// �����`�[���̓t���O
		addSendValues("NYU_KIN", Util.avoidNull(this.nyuKin));
		// �o���`�[���̓t���O
		addSendValues("SHUTSU_KIN", Util.avoidNull(this.shutsuKin));
		// �U�֓`�[���̓t���O
		addSendValues("FURIKAE_FLG", Util.avoidNull(this.furikaeFlg));
		// �Ȗڑ̌n�敪
		addSendValues("KMT_KBN", Util.avoidNull(this.kmtKbn));
		// �Ȗڑ̌n�R�[�h
		addSendValues("KMT_CODE", Util.avoidNull(this.kmtCode));
		// AR����敪
		addSendValues("KMK_CNT_AR", Util.avoidNull(this.kmkCntAr));
		// AR����敪(�����p)
		addSendValues("KMK_CNT_UN_AR", Util.avoidNull(this.kmkCntUnAr));
		// AP����敪
		addSendValues("KMK_CNT_AP", Util.avoidNull(this.kmkCntAp));
		// �o��Z�`�[���̓t���O
		addSendValues("KEIHI_FLG", Util.avoidNull(this.keihiFlg));
		// ���v����̓t���O
		addSendValues("SAIMU_FLG", Util.avoidNull(this.saimuFlg));
		// ���v����̓t���O
		addSendValues("SAIKEN_FLG", Util.avoidNull(this.saikenFlg));
		// �������`�[���̓t���O
		addSendValues("SAIKESI_FLG", Util.avoidNull(this.saikesiFlg));
		// ���Y�v��`�[���̓t���O
		addSendValues("SISAN_FLG", Util.avoidNull(this.sisanFlg));
		// �x���˗��`�[���̓t���O
		addSendValues("SIHARAI_FLG", Util.avoidNull(this.siharaiFlg));

		// �Ȗڎ��
		addSendValues("KMK_SHU", Util.avoidNull(this.kmkShu));
		// �ݎ؋敪
		addSendValues("DC_KBN", Util.avoidNull(this.dcKbn));
		// �⏕�敪
		addSendValues("HKM_KBN", Util.avoidNull(this.hkmKbn));
		// �Œ蕔�庰��
		addSendValues("KOTEI_DEP_CODE", Util.avoidNull(this.koteiDepCode));
		// ����ź���
		addSendValues("ZEI_CODE", Util.avoidNull(this.zeiCode));
		// ���������׸�
		addSendValues("TRI_CODE_FLG", Util.avoidNull(this.triCodeFlg));
		// �����������׸�
		addSendValues("HAS_FLG", Util.avoidNull(this.hasFlg));
		// �Ј������׸�
		addSendValues("EMP_CODE_FLG", Util.avoidNull(this.empCodeFlg));
		// �Ǘ�1
		addSendValues("KNR_FLG1", Util.avoidNull(this.knrFlg1));
		// �Ǘ�2
		addSendValues("KNR_FLG2", Util.avoidNull(this.knrFlg2));
		// �Ǘ�3
		addSendValues("KNR_FLG3", Util.avoidNull(this.knrFlg3));
		// �Ǘ�4
		addSendValues("KNR_FLG4", Util.avoidNull(this.knrFlg4));
		// �Ǘ�5
		addSendValues("KNR_FLG5", Util.avoidNull(this.knrFlg5));
		// �Ǘ�6
		addSendValues("KNR_FLG6", Util.avoidNull(this.knrFlg6));
		// ���v1
		addSendValues("HM_FLG1", Util.avoidNull(this.hmFlg1));
		// ���v2
		addSendValues("HM_FLG2", Util.avoidNull(this.hmFlg2));
		// ���v3
		addSendValues("HM_FLG3", Util.avoidNull(this.hmFlg3));
		// ����ېœ����׸�
		addSendValues("URI_ZEI_FLG", Util.avoidNull(this.uriZeiFlg));
		// �d���ېœ����׸�
		addSendValues("SIR_ZEI_FLG", Util.avoidNull(this.sirZeiFlg));
		// ���ʉݓ����׸�
		addSendValues("MCR_FLG", Util.avoidNull(this.mcrFlg));
		// BG�Ȗڐ���敪
		addSendValues("KMK_CNT_BG", Util.avoidNull(this.kmkCntBg));
		// ���E�Ȗڐ���敪
		addSendValues("KMK_CNT_SOUSAI", Util.avoidNull(this.kmkCntSousai));
		// ���Z�Ώۋ敪
		addSendValues("KMK_CLR_FLG", Util.avoidNull(this.clearanceFlg));

		// �J�n�R�[�h
		addSendValues("BEGIN_CODE", Util.avoidNull(this.beginCode));
		// �I���R�[�h
		addSendValues("END_CODE", Util.avoidNull(this.endCode));

		// �Ȗڑ̌n�R�[�h
		addSendValues("KMK_TK_CODE", Util.avoidNull(this.kmtCode));
		// �Ȗڑ̌n�t���O
		addSendValues("KMK_TK_FLG", Util.avoidNull(this.kmtFlg));
	}

	/**
	 * �⏕�Ȗڗ����������ݒ�
	 */
	private void setHkmMstCondition() {
		// ��ЃR�[�h
		String strKaiCode = "";
		if (this.kaiCode != null && !"".equals(this.kaiCode)) {
			strKaiCode = Util.avoidNull(this.kaiCode);
		} else {
			strKaiCode = super.getLoginUserCompanyCode();
		}
		// �⏕�ȖڃR�[�h
		String strHkmCode = StringUtil.convertPrm(dialog.txtCode.getText());
		// �⏕�Ȗڗ���
		String strHkmName = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// �⏕�Ȗڌ�������
		String strHkmName_K = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// ���M����p�����[�^��ݒ�
		addSendValues("FLAG", "HKM_MST");
		// ��ЃR�[�h
		addSendValues("KAI_CODE", Util.avoidNull(strKaiCode));
		// �ȖڃR�[�h
		addSendValues("KMK_CODE", Util.avoidNull(this.kmkCode));
		// �⏕�ȖڃR�[�h
		addSendValues("HKM_CODE", (Util.avoidNull(strHkmCode)));
		// �⏕�Ȗڗ���
		addSendValues("HKM_NAME", (Util.avoidNull(strHkmName)));
		// �⏕�Ȗڌ�������
		addSendValues("HKM_NAME_K", (Util.avoidNull(strHkmName_K)));
		// �`�[���t
		addSendValues("SLIP_DATE", Util.avoidNull(this.slipDate));
		// �U�֓`�[���̓t���O
		addSendValues("FURIKAE_FLG", Util.avoidNull(this.furikaeFlg));
		// �����`�[���̓t���O
		addSendValues("NYU_KIN", Util.avoidNull(this.nyuKin));
		// �o���`�[���̓t���O
		addSendValues("SHUTSU_KIN", Util.avoidNull(this.shutsuKin));
		// �o��Z�`�[���̓t���O
		addSendValues("KEIHI_FLG", Util.avoidNull(this.keihiFlg));
		// ���v����̓t���O
		addSendValues("SAIMU_FLG", Util.avoidNull(this.saimuFlg));
		// ���v����̓t���O
		addSendValues("SAIKEN_FLG", Util.avoidNull(this.saikenFlg));
		// �������`�[���̓t���O
		addSendValues("SAIKESI_FLG", Util.avoidNull(this.saikesiFlg));
		// ���Y�v��`�[���̓t���O
		addSendValues("SISAN_FLG", Util.avoidNull(this.sisanFlg));
		// �x���˗��`�[���̓t���O
		addSendValues("SIHARAI_FLG", Util.avoidNull(this.siharaiFlg));

		// �]���֑Ώۃt���O
		addSendValues("EXC_FLG", Util.avoidNull(this.excFlg));
		// ����敪
		addSendValues("UKM_KBN", Util.avoidNull(this.ukmKbn));
		// ����ź���
		addSendValues("ZEI_CODE", Util.avoidNull(this.zeiCode));
		// ���������׸�
		addSendValues("TRI_CODE_FLG", Util.avoidNull(this.triCodeFlg));
		// �����������׸�
		addSendValues("HAS_FLG", Util.avoidNull(this.hasFlg));
		// �Ј������׸�
		addSendValues("EMP_CODE_FLG", Util.avoidNull(this.empCodeFlg));
		// �Ǘ�1
		addSendValues("KNR_FLG1", Util.avoidNull(this.knrFlg1));
		// �Ǘ�2
		addSendValues("KNR_FLG2", Util.avoidNull(this.knrFlg2));
		// �Ǘ�3
		addSendValues("KNR_FLG3", Util.avoidNull(this.knrFlg3));
		// �Ǘ�4
		addSendValues("KNR_FLG4", Util.avoidNull(this.knrFlg4));
		// �Ǘ�5
		addSendValues("KNR_FLG5", Util.avoidNull(this.knrFlg5));
		// �Ǘ�6
		addSendValues("KNR_FLG6", Util.avoidNull(this.knrFlg6));
		// ���v1
		addSendValues("HM_FLG1", Util.avoidNull(this.hmFlg1));
		// ���v2
		addSendValues("HM_FLG2", Util.avoidNull(this.hmFlg2));
		// ���v3
		addSendValues("HM_FLG3", Util.avoidNull(this.hmFlg3));
		// ����ېœ����׸�
		addSendValues("URI_ZEI_FLG", Util.avoidNull(this.uriZeiFlg));
		// �d���ېœ����׸�
		addSendValues("SIR_ZEI_FLG", Util.avoidNull(this.sirZeiFlg));
		// ���ʉݓ����׸�
		addSendValues("MCR_FLG", Util.avoidNull(this.mcrFlg));

		// �J�n�R�[�h
		addSendValues("BEGIN_CODE", Util.avoidNull(this.beginCode));
		// �I���R�[�h
		addSendValues("END_CODE", Util.avoidNull(this.endCode));
	}

	/**
	 * ����Ȗڈꗗ���������ݒ�
	 */
	private void setUkmMstCondition() {
		// ��ЃR�[�h
		String strKaiCode = "";
		if (this.kaiCode != null && !"".equals(this.kaiCode)) {
			strKaiCode = Util.avoidNull(this.kaiCode);
		} else {
			strKaiCode = super.getLoginUserCompanyCode();
		}
		// ����ȖڃR�[�h
		String strUkmCode = StringUtil.convertPrm(dialog.txtCode.getText());
		// ����Ȗڗ���
		String strUkmName = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// ����Ȗڌ�������
		String strUkmName_K = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// ���M����p�����[�^��ݒ�
		addSendValues("FLAG", "UKM_MST");
		// ��ЃR�[�h
		addSendValues("KAI_CODE", Util.avoidNull(strKaiCode));
		// �ȖڃR�[�h
		addSendValues("KMK_CODE", Util.avoidNull(this.kmkCode));
		// �⏕�ȖڃR�[�h
		addSendValues("HKM_CODE", Util.avoidNull(this.hkmCode));
		// ����ȖڃR�[�h
		addSendValues("UKM_CODE", (Util.avoidNull(strUkmCode)));
		// ����Ȗڗ���
		addSendValues("UKM_NAME", (Util.avoidNull(strUkmName)));
		// ����Ȗڌ�������
		addSendValues("UKM_NAME_K", (Util.avoidNull(strUkmName_K)));
		// �`�[���t
		addSendValues("SLIP_DATE", Util.avoidNull(this.slipDate));
		// �U�֓`�[���̓t���O
		addSendValues("FURIKAE_FLG", Util.avoidNull(this.furikaeFlg));
		// �����`�[���̓t���O
		addSendValues("NYU_KIN", Util.avoidNull(this.nyuKin));
		// �o���`�[���̓t���O
		addSendValues("SHUTSU_KIN", Util.avoidNull(this.shutsuKin));
		// �o��Z�`�[���̓t���O
		addSendValues("KEIHI_FLG", Util.avoidNull(this.keihiFlg));
		// ���v����̓t���O
		addSendValues("SAIMU_FLG", Util.avoidNull(this.saimuFlg));
		// ���v����̓t���O
		addSendValues("SAIKEN_FLG", Util.avoidNull(this.saikenFlg));
		// �������`�[���̓t���O
		addSendValues("SAIKESI_FLG", Util.avoidNull(this.saikesiFlg));
		// ���Y�v��`�[���̓t���O
		addSendValues("SISAN_FLG", Util.avoidNull(this.sisanFlg));
		// �x���˗��`�[���̓t���O
		addSendValues("SIHARAI_FLG", Util.avoidNull(this.siharaiFlg));

		// �]���֑Ώۃt���O
		addSendValues("EXC_FLG", Util.avoidNull(this.excFlg));
		// ����ź���
		addSendValues("ZEI_CODE", Util.avoidNull(this.zeiCode));
		// ���������׸�
		addSendValues("TRI_CODE_FLG", Util.avoidNull(this.triCodeFlg));
		// �����������׸�
		addSendValues("HAS_FLG", Util.avoidNull(this.hasFlg));
		// �Ј������׸�
		addSendValues("EMP_CODE_FLG", Util.avoidNull(this.empCodeFlg));
		// �Ǘ�1
		addSendValues("KNR_FLG1", Util.avoidNull(this.knrFlg1));
		// �Ǘ�2
		addSendValues("KNR_FLG2", Util.avoidNull(this.knrFlg2));
		// �Ǘ�3
		addSendValues("KNR_FLG3", Util.avoidNull(this.knrFlg3));
		// �Ǘ�4
		addSendValues("KNR_FLG4", Util.avoidNull(this.knrFlg4));
		// �Ǘ�5
		addSendValues("KNR_FLG5", Util.avoidNull(this.knrFlg5));
		// �Ǘ�6
		addSendValues("KNR_FLG6", Util.avoidNull(this.knrFlg6));
		// ���v1
		addSendValues("HM_FLG1", Util.avoidNull(this.hmFlg1));
		// ���v2
		addSendValues("HM_FLG2", Util.avoidNull(this.hmFlg2));
		// ���v3
		addSendValues("HM_FLG3", Util.avoidNull(this.hmFlg3));
		// ����ېœ����׸�
		addSendValues("URI_ZEI_FLG", Util.avoidNull(this.uriZeiFlg));
		// �d���ېœ����׸�
		addSendValues("SIR_ZEI_FLG", Util.avoidNull(this.sirZeiFlg));
		// ���ʉݓ����׸�
		addSendValues("MCR_FLG", Util.avoidNull(this.mcrFlg));

		// �J�n�R�[�h
		addSendValues("BEGIN_CODE", Util.avoidNull(this.beginCode));
		// �I���R�[�h
		addSendValues("END_CODE", Util.avoidNull(this.endCode));
	}

	/**
	 * ����}�X�^�ꗗ���������ݒ�
	 */
	private void setBmnMstCondition() {
		// ��ЃR�[�h
		String strKaiCode = "";
		if (this.kaiCode != null && !"".equals(this.kaiCode)) {
			strKaiCode = Util.avoidNull(this.kaiCode);
		} else {
			strKaiCode = super.getLoginUserCompanyCode();
		}
		// ����R�[�h
		String strKnrCode = StringUtil.convertPrm(dialog.txtCode.getText());
		// ���嗪��
		String strKnrName = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// ���匟������
		String strKnrName_K = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// ���M����p�����[�^��ݒ�
		addSendValues("FLAG", "BMN_MST");
		// ��ЃR�[�h
		addSendValues("KAI_CODE", Util.avoidNull(strKaiCode));
		// ����R�[�h
		addSendValues("DEP_CODE", (Util.avoidNull(strKnrCode)));
		// ���嗪��
		addSendValues("DEP_NAME", (Util.avoidNull(strKnrName)));
		// ���匟������
		addSendValues("DEP_NAME_K", (Util.avoidNull(strKnrName_K)));
		// �`�[���t
		addSendValues("SLIP_DATE", Util.avoidNull(this.slipDate));
		// �g�D�R�[�h
		addSendValues("DPK_SSK", Util.avoidNull(this.dpkSsk));
		// �z������(0:�܂� �A1:�܂܂Ȃ��A2:��ʕ��庰�ޑI���̏ꍇ)
		addSendValues("BMN_KBN", Util.avoidNull(this.bmnKbn));
		// ��ʕ��庰��
		addSendValues("UP_BMN_CODE", Util.avoidNull(this.upBmnCode));
		// �K�w����
		addSendValues("DPK_LVL", Util.avoidNull(this.dpkLvl));

		// �������庰��
		addSendValues("BASE_BMN_CODE", Util.avoidNull(this.baseBmnCode));
		// �����K�w����
		addSendValues("BASE_DPK_LVL", Util.avoidNull(this.baseDpkLvl));
	}

	/**
	 * ���ݒ�}�X�^�ꗗ���������ݒ�
	 */
	private void setEnvMstCondition() {

		// ��ЃR�[�h
		String CompCode = "";
		if (this.kaiCode != null && !"".equals(this.kaiCode)) {
			CompCode = Util.avoidNull(this.kaiCode);
		} else {
			CompCode = super.getLoginUserCompanyCode();
		}

		// ��ЃR�[�h
		String strKaiCode = StringUtil.convertPrm(dialog.txtCode.getText());
		// ��З���
		String strKaiNameS = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());

		// ���M����p�����[�^��ݒ�
		addSendValues("FLAG", "ENV_MST");
		// ��ЃR�[�h
		addSendValues("KAI_CODE", Util.avoidNull(strKaiCode));
		// ��З���
		addSendValues("KAI_NAME_S", Util.avoidNull(strKaiNameS));

		// ���O�C����ЃR�[�h
		addSendValues("LOGIN_KAI_CODE", Util.avoidNull(CompCode));
		// �g�D�R�[�h
		addSendValues("DPK_SSK", Util.avoidNull(this.dpkSsk));
		// �z�����(0:�܂� �A1:�܂܂Ȃ��A2:��ʉ�к��ޑI���̏ꍇ)
		addSendValues("COMPANY_KBN", Util.avoidNull(this.bmnKbn));
		// ��ʉ�к���
		addSendValues("UP_COMPANY_CODE", Util.avoidNull(this.upBmnCode));
		// �K�w����
		addSendValues("DPK_LVL", Util.avoidNull(this.dpkLvl));
		// ������к���
		addSendValues("BASE_COMPANY_CODE", Util.avoidNull(this.baseBmnCode));
		// �����K�w����
		addSendValues("BASE_DPK_LVL", Util.avoidNull(this.baseDpkLvl));
	}

	/**
	 * �w�b�_�ƃJ�������擾
	 * 
	 * @return map
	 */
	public Map setCmpCondition() {
		// ��ЃR�[�h�擾
		TCompanyInfo compInfo = TClientLoginInfo.getInstance().getCompanyInfo();

		// �Ǘ����̂̎擾
		String[] name = compInfo.getManageDivNames();

		Map map = new HashMap();
		map.put("kmkName", Util.avoidNull(Util.isNullOrEmpty(compInfo.getItemName()) ? getWord("C00077") : compInfo
			.getItemName()));
		map.put("hkmName", Util.avoidNull(Util.isNullOrEmpty(compInfo.getSubItemName()) ? getWord("C00488") : compInfo
			.getSubItemName()));
		map.put("ukmName", Util.avoidNull(Util.isNullOrEmpty(compInfo.getBreakDownItemName()) ? getWord("C00025")
			: compInfo.getBreakDownItemName()));

		map.put("knrName1", Util.avoidNull(Util.isNullOrEmpty(name[0]) ? getWord("C01025") : name[0]));
		map.put("knrName2", Util.avoidNull(Util.isNullOrEmpty(name[1]) ? getWord("C01027") : name[1]));
		map.put("knrName3", Util.avoidNull(Util.isNullOrEmpty(name[2]) ? getWord("C01029") : name[2]));
		map.put("knrName4", Util.avoidNull(Util.isNullOrEmpty(name[3]) ? getWord("C01031") : name[3]));
		map.put("knrName5", Util.avoidNull(Util.isNullOrEmpty(name[4]) ? getWord("C01033") : name[4]));
		map.put("knrName6", Util.avoidNull(Util.isNullOrEmpty(name[5]) ? getWord("C01035") : name[5]));

		return map;
	}

	/**
	 * ��s�����̌��������ݒ�
	 * 
	 * @param flag
	 */
	private void setSearchCondition(String flag) {

		// �R�[�h
		String triCode = StringUtil.convertPrm(dialog.txtCode.getText());
		// ����
		String triNameS = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// ������
		String triNameK = StringUtil.convertPrm(dialog.txtNameForSearch.getText());
		// ���M����p�����[�^��ݒ�
		addSendValues("FLAG", flag);
		// ��ЃR�[�h
		addSendValues("KaiCode", Util.avoidNull(paramMap.get("companyCode")));
		// �R�[�h
		addSendValues("Code", Util.avoidNull(triCode));
		// ���� - accoututil�̃��C���h�J�[�h�Ή�
		addSendValues("NameS", Util.avoidNull(triNameS));
		// ������
		addSendValues("NameK", Util.avoidNull(triNameK));
		// �Ј��x�����t���O
		addSendValues("empKbn", Util.avoidNull(paramMap.get("empKbn")));
		// �ЊO�x�����t���O
		addSendValues("outKbn", Util.avoidNull(paramMap.get("outKbn")));
		// �L������ ��������
		addSendValues("termBasisDate", Util.avoidNull(paramMap.get("termBasisDate")));

	}

	/**
	 * ��s�����̌��������ݒ�
	 * 
	 * @param flag
	 */
	private void setBankSearchCondition(String flag) {

		// �R�[�h
		String cbkCode = StringUtil.convertPrm(dialog.txtCode.getText());
		// ����
		String cbkNameS = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// ������
		String cbkNameK = StringUtil.convertPrm(dialog.txtNameForSearch.getText());
		// ���M����p�����[�^��ݒ�
		addSendValues("FLAG", flag);
		// ��ЃR�[�h
		addSendValues("KaiCode", Util.avoidNull(paramMap.get("companyCode")));
		// �R�[�h
		addSendValues("Code", Util.avoidNull(cbkCode));
		// ����
		addSendValues("NameS", Util.avoidNull(cbkNameS));
		// ������
		addSendValues("NameK", Util.avoidNull(cbkNameK));
		// �Ј��x���t���O
		addSendValues("empKbn", Util.avoidNull(paramMap.get("empKbn")));
		// �ЊO�x���t���O
		addSendValues("outKbn", Util.avoidNull(paramMap.get("outKbn")));
		// �L������ ��������
		addSendValues("termBasisDate", Util.avoidNull(paramMap.get("termBasisDate")));
		// ��s�������X�g
		addSendObject(cbkCodes);
	}

	/**
	 * �����̌��������ݒ�
	 * 
	 * @param flag
	 */
	protected void setCustomerCondition(String flag) {

		// �R�[�h
		String triCode = StringUtil.convertPrm(dialog.txtCode.getText());
		// ����
		String triNameS = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// ������
		String triNameK = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// ���M����p�����[�^��ݒ�
		addSendValues("FLAG", flag);
		// ��ЃR�[�h
		addSendValues("kaiCode", Util.avoidNull(paramMap.get("companyCode")));
		// �R�[�h
		addSendValues("triCode", Util.avoidNull(triCode));
		// ���� - accoututil�̃��C���h�J�[�h�Ή�
		addSendValues("sName", Util.avoidNull(triNameS));
		// ������
		addSendValues("kName", Util.avoidNull(triNameK));
		// �Ј��x�����t���O
		addSendValues("siire", Util.avoidNull(paramMap.get("siire")));
		// �ЊO�x�����t���O
		addSendValues("tokui", Util.avoidNull(paramMap.get("tokui")));
		// �L������ ��������
		addSendValues("termBasisDate", Util.avoidNull(paramMap.get("termBasisDate")));
		// �J�n�R�[�h
		addSendValues("beginCode", Util.avoidNull(paramMap.get("beginCode")));
		// �I���R�[�h
		addSendValues("endCode", Util.avoidNull(paramMap.get("endCode")));

	}

	/**
	 * �ʉ݃t�B�[���h�}�X�^�ꗗ���������ݒ�
	 * 
	 * @param flag
	 */
	private void setCurrencyCondition(String flag) {

		// �R�[�h
		String Code = StringUtil.convertPrm(dialog.txtCode.getText());
		// ����
		String NameS = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// ������
		String NameK = StringUtil.convertPrm(dialog.txtNameForSearch.getText());
		// ���M����p�����[�^��ݒ�
		addSendValues("FLAG", flag);
		// ��ЃR�[�h
		addSendValues("kaiCode", Util.avoidNull(paramMap.get("companyCode")));
		// �R�[�h
		addSendValues("curCode", Util.avoidNull(Code));
		// ���� - accoututil�̃��C���h�J�[�h�Ή�
		addSendValues("sName", Util.avoidNull(NameS));
		// ������
		addSendValues("kName", Util.avoidNull(NameK));
		// �L������ ��������
		addSendValues("termBasisDate", Util.avoidNull(paramMap.get("termBasisDate")));
		// �J�n�R�[�h
		addSendValues("beginCode", Util.avoidNull(paramMap.get("beginCode")));
		// �I���R�[�h
		addSendValues("endCode", Util.avoidNull(paramMap.get("endCode")));

	}

	/**
	 * �v���Ѓt�B�[���h���������ݒ�
	 */
	private void setAppropriateCompanyCondition() {

		AppropriateCompany param = new AppropriateCompany();
		// �R�[�h
		param.setKAI_CODE(StringUtil.convertPrm(dialog.txtCode.getText()));

		param.setKAI_NAME_S(StringUtil.convertPrm(dialog.txtAbbreviationName.getText()));

		// �J�n�R�[�h
		param.setStrCompanyCode(beginCode);

		// �I���R�[�h
		param.setEndCompanyCode(endCode);

		// �ʉ݋敪
		param.setCurKbn(curKbn);

		// ���M����p�����[�^��ݒ�
		addSendValues("FLAG", "APP_KAI");

		this.addSendDto(param);

	}

	/**
	 * ����t�B�[���h�}�X�^�ꗗ���������ݒ�
	 * 
	 * @param flag
	 */
	private void setDepartmentCondition(String flag) {

		// �R�[�h
		String Code = StringUtil.convertPrm(dialog.txtCode.getText());
		// ����
		String NameS = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// ������
		String NameK = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// ���M����p�����[�^��ݒ�
		addSendValues("FLAG", flag);
		// ��ЃR�[�h
		addSendValues("kaiCode", Util.avoidNull(paramMap.get("companyCode")));
		// �R�[�h
		addSendValues("depCode", Util.avoidNull(Code));
		// ���� - accoututil�̃��C���h�J�[�h�Ή�
		addSendValues("sName", Util.avoidNull(NameS));
		// ������
		addSendValues("kName", Util.avoidNull(NameK));
		// �L������ ��������
		addSendValues("termBasisDate", Util.avoidNull(paramMap.get("termBasisDate")));
		// �g�D��
		addSendValues("organization", Util.avoidNull(paramMap.get("organization")));
		// ��ʕ���
		addSendValues("upper", paramMap.get("upper"));
		// ��ʕ��僌�x��
		addSendValues("level", Util.avoidNull(paramMap.get("level")));
		// �W�v����t���O
		addSendValues("sum", Util.avoidNull(paramMap.get("sum")));
		// ���͕���t���O
		addSendValues("input", Util.avoidNull(paramMap.get("input")));
		// �J�n�R�[�h
		addSendValues("beginCode", Util.avoidNull(paramMap.get("beginCode")));
		// �I���R�[�h
		addSendValues("endCode", Util.avoidNull(paramMap.get("endCode")));
	}

	/**
	 * �s�E�v�E�`�[�E�v�t�B�[���h�}�X�^�ꗗ���������ݒ�
	 * 
	 * @param flag
	 */
	private void setMemoCondition(String flag) {

		// �R�[�h
		String Code = StringUtil.convertPrm(dialog.txtCode.getText());
		// ����
		String NameS = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// ������
		String NameK = StringUtil.convertPrm(dialog.txtNameForSearch.getText());
		// ���M����p�����[�^��ݒ�
		addSendValues("FLAG", flag);
		// ��ЃR�[�h
		addSendValues("kaiCode", Util.avoidNull(paramMap.get("companyCode")));
		// �R�[�h
		addSendValues("tekCode", Util.avoidNull(Code));
		// ���� - accoututil�̃��C���h�J�[�h�Ή�
		addSendValues("sName", Util.avoidNull(NameS));
		// ������
		addSendValues("kName", Util.avoidNull(NameK));
		// �L������ ��������
		addSendValues("termBasisDate", Util.avoidNull(paramMap.get("termBasisDate")));
		// �f�[�^�^�C�v
		addSendValues("slipType", Util.avoidNull(paramMap.get("slipType")));
		// �E�v�敪
		addSendValues("memoType", Util.avoidNull(paramMap.get("memoType")));
	}

	/**
	 * �Ј������t�B�[���h�}�X�^�ꗗ���������ݒ�
	 * 
	 * @param flag
	 */
	private void setEmployeeCondition(String flag) {

		// �R�[�h
		String Code = StringUtil.convertPrm(dialog.txtCode.getText());
		// ����
		String NameS = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());

		// ������
		String NameK = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// ���M����p�����[�^��ݒ�
		addSendValues("FLAG", flag);
		// ��ЃR�[�h
		addSendValues("kaiCode", Util.avoidNull(paramMap.get("companyCode")));
		// �R�[�h
		addSendValues("empCode", Util.avoidNull(Code));
		// ���� - accoututil�̃��C���h�J�[�h�Ή�
		addSendValues("sName", Util.avoidNull(NameS));
		// ������
		addSendValues("kName", Util.avoidNull(NameK));
		// ���[�U�o�^�t���O
		addSendValues("user", Util.avoidNull(paramMap.get("user")));
		// ����ݒ�
		addSendValues("depCode", Util.avoidNull(paramMap.get("depCode")));
		// �L������ ��������
		addSendValues("termBasisDate", Util.avoidNull(paramMap.get("termBasisDate")));
		// �J�n�R�[�h
		addSendValues("beginCode", Util.avoidNull(paramMap.get("beginCode")));
		// �I���R�[�h
		addSendValues("endCode", Util.avoidNull(paramMap.get("endCode")));
	}

	/**
	 * �x�������@�}�X�^�ꗗ���������ݒ�
	 * 
	 * @param flag
	 */
	private void setPaymentCondition(String flag) {

		// �R�[�h
		String Code = StringUtil.convertPrm(dialog.txtCode.getText());
		// ����
		String NameS = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// ������
		String NameK = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// ���M����p�����[�^��ݒ�
		addSendValues("FLAG", flag);
		// ��ЃR�[�h
		addSendValues("kaiCode", Util.avoidNull(paramMap.get("companyCode")));
		// �R�[�h
		addSendValues("hohCode", Util.avoidNull(Code));
		// ���� - accoututil�̃��C���h�J�[�h�Ή�
		addSendValues("sName", Util.avoidNull(NameS));
		// ������
		addSendValues("kName", Util.avoidNull(NameK));
		// �x���Ώۋ敪
		addSendValues("sihKbn", Util.avoidNull(paramMap.get("sihKbn")));
		// �x�����@�����R�[�h
		addSendValues("naiCode", Util.avoidNull(paramMap.get("naiCode")));
		// �x�����@�����R�[�h NOT����
		addSendValues("notNaiCode", Util.avoidNull(paramMap.get("notNaiCode")));
		// �L������ ��������
		addSendValues("termBasisDate", Util.avoidNull(paramMap.get("termBasisDate")));

		// �R�[�h���X�g
		addSendObject(codes);
	}

	/**
	 * �x�������}�X�^�ꗗ���������ݒ�
	 * 
	 * @param flag
	 */
	private void setPaymentConCondition(String flag) {

		// �R�[�h
		String Code = StringUtil.convertPrm(dialog.txtCode.getText());

		// ���M����p�����[�^��ݒ�
		addSendValues("FLAG", flag);
		// ��ЃR�[�h
		addSendValues("kaiCode", Util.avoidNull(paramMap.get("companyCode")));
		// �R�[�h
		addSendValues("tjkCode", Util.avoidNull(Code));
		// �����R�[�h
		addSendValues("triCode", Util.avoidNull(paramMap.get("triCode")));

		// �L������ ��������
		addSendValues("termBasisDate", Util.avoidNull(paramMap.get("termBasisDate")));
	}

	/**
	 * ����Ń}�X�^�ꗗ���������ݒ�
	 * 
	 * @param flag
	 */
	private void setTaxCondition(String flag) {

		// �R�[�h
		String Code = StringUtil.convertPrm(dialog.txtCode.getText());
		// ����
		String NameS = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// ������
		String NameK = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// ���M����p�����[�^��ݒ�
		addSendValues("FLAG", flag);
		// ��ЃR�[�h
		addSendValues("kaiCode", Util.avoidNull(paramMap.get("companyCode")));
		// �R�[�h
		addSendValues("zeiCode", Util.avoidNull(Code));
		// ���� - accoututil�̃��C���h�J�[�h�Ή�
		addSendValues("sName", Util.avoidNull(NameS));
		// ������
		addSendValues("kName", Util.avoidNull(NameK));
		// �L������ ��������
		addSendValues("termBasisDate", Util.avoidNull(paramMap.get("termBasisDate")));

		// ����グ�敪
		addSendValues("sales", Util.avoidNull(paramMap.get("sales")));
		// �d����敪
		addSendValues("purchase", Util.avoidNull(paramMap.get("purchase")));
		// �ΏۊO�敪
		addSendValues("elseTax", Util.avoidNull(paramMap.get("elseTax")));
	}

	/**
	 * �Ј��}�X�^�ꗗ���������ݒ�
	 */
	private void setEmpMstCondition() {
		// ��ЃR�[�h
		String strKaiCode = "";
		if (this.kaiCode != null && !"".equals(this.kaiCode)) {
			strKaiCode = Util.avoidNull(this.kaiCode);
		} else {
			strKaiCode = super.getLoginUserCompanyCode();
		}
		// �Ј��R�[�h
		String strEmpCode = StringUtil.convertPrm(dialog.txtCode.getText());
		// �Ј�����
		String strEmpName = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// �Ј���������
		String strEmpName_K = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// ���M����p�����[�^��ݒ�
		addSendValues("FLAG", "EMP_MST");
		// ��ЃR�[�h
		addSendValues("KAI_CODE", Util.avoidNull(strKaiCode));
		// �Ј��R�[�h
		addSendValues("EMP_CODE", Util.avoidNull(strEmpCode));
		// �Ј�����
		addSendValues("EMP_NAME", Util.avoidNull(strEmpName));
		// �Ј���������
		addSendValues("EMP_NAME_K", Util.avoidNull(strEmpName_K));
		// �`�[���t
		addSendValues("SLIP_DATE", Util.avoidNull(this.slipDate));
		// ��������R�[�h�敪
		addSendValues("DEP_CODE_KBN", Util.avoidNull(this.depCodeKbn));
		// ��������R�[�h
		addSendValues("DEP_CODE", Util.avoidNull(this.depCode));
	}

	/**
	 * �Ǘ�1�}�X�^�ꗗ���������ݒ�
	 * 
	 * @param strMstKbn (1-6)�Ǘ��P�|�Ǘ��U
	 */
	protected void setKnrMstCondition(String strMstKbn) {
		// ��ЃR�[�h
		String strKaiCode = "";
		if (this.kaiCode != null && !"".equals(this.kaiCode)) {
			strKaiCode = Util.avoidNull(this.kaiCode);
		} else {
			strKaiCode = super.getLoginUserCompanyCode();
		}
		// �Ǘ��R�[�h
		String strKnrCode = StringUtil.convertPrm(dialog.txtCode.getText());
		// �Ǘ�����
		String strKnrName = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// �Ǘ���������
		String strKnrName_K = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// ���M����p�����[�^��ݒ�
		addSendValues("FLAG", "KNR_MST");
		addSendValues("KNR_FLAG", strMstKbn);
		// ��ЃR�[�h
		addSendValues("KAI_CODE", Util.avoidNull(strKaiCode));
		// �Ǘ��R�[�h
		addSendValues("KNR_CODE", Util.avoidNull(strKnrCode));
		// �Ǘ�����
		addSendValues("KNR_NAME", Util.avoidNull(strKnrName));
		// �Ǘ��R�[�h��������
		addSendValues("KNR_NAME_K", Util.avoidNull(strKnrName_K));
		// �`�[���t
		addSendValues("SLIP_DATE", Util.avoidNull(this.slipDate));
		// �J�n�R�[�h
		addSendValues("START_CODE", Util.avoidNull(this.beginCode));
		// �I���R�[�h
		addSendValues("END_CODE", Util.avoidNull(this.endCode));

	}

	/**
	 * �Ȗڑ̌n���̃}�X�^�ꗗ���������ݒ�
	 */
	private void setKmkTkMstCondition() {
		// ��ЃR�[�h
		String strKaiCode = "";
		if (this.kaiCode != null && !"".equals(this.kaiCode)) {
			strKaiCode = Util.avoidNull(this.kaiCode);
		} else {
			strKaiCode = super.getLoginUserCompanyCode();
		}
		// �Ȗڑ̌n�R�[�h
		String strKmtCode = StringUtil.convertPrm(dialog.txtCode.getText());
		// �Ȗڑ̌n����
		String strKmtName = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// �Ȗڑ̌n��������
		String strKmtName_K = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// ���M����p�����[�^��ݒ�
		addSendValues("FLAG", "KMT_MST");
		// ��ЃR�[�h
		addSendValues("KAI_CODE", Util.avoidNull(strKaiCode));
		// �Ȗڑ̌n�R�[�h
		addSendValues("KMT_CODE", Util.avoidNull(strKmtCode));
		// �Ȗڑ̌n����
		addSendValues("KMT_NAME", Util.avoidNull(strKmtName));
		// �Ȗڑ̌n��������
		addSendValues("KMT_NAME_K", Util.avoidNull(strKmtName_K));
	}

	/**
	 * ��s�}�X�^�ꗗ�����ݒ�
	 * 
	 * @param flag �����t���O
	 */
	private void setBnkMstCondition(String flag) {
		BnkMstParameter param = (BnkMstParameter) paramObject;
		param.setLikeBnkCode(StringUtil.convertPrm(dialog.txtCode.getText()));
		param.setLikeBnkName(StringUtil.convertPrm(dialog.txtAbbreviationName.getText()));
		param.setLikeBnkNameK(StringUtil.convertPrm(dialog.txtNameForSearch.getText()));

		// ���M����p�����[�^��ݒ�
		addSendValues("FLAG", flag); // flag
		addSendObject(param);// ��s�}�X�^�p�����[�^
	}

	/**
	 * ��s�x�X�}�X�^�ꗗ�����ݒ�
	 * 
	 * @param flag �����t���O
	 */
	private void setBnkStnMstCondition(String flag) {
		BnkMstParameter param = (BnkMstParameter) paramObject;
		param.setLikeBnkStnCode(StringUtil.convertPrm(dialog.txtCode.getText()));
		param.setLikeBnkStnName(StringUtil.convertPrm(dialog.txtAbbreviationName.getText()));
		param.setLikeBnkStnNameK(StringUtil.convertPrm(dialog.txtNameForSearch.getText()));

		// ���M����p�����[�^��ݒ� "BNK_STN_MST"
		addSendValues("FLAG", flag); // flag
		addSendObject(param);// ��s�}�X�^�p�����[�^
	}

	/**
	 * �R���o�[�g�p(�}�X�^�ꗗ)
	 * 
	 * @param word
	 * @return �}�X�^�ꗗ
	 */
	public String convertTop(String word) {
		String wordID = getWord("C02406");
		String top = word + wordID;
		return top;
	}

	/**
	 * �R���o�[�g�p(�R�[�h)
	 * 
	 * @param word
	 * @return �R�[�h
	 */
	public String convertCode(String word) {
		String wordID = getWord("C00174");
		String code = word + wordID;
		return code;
	}

	/**
	 * �R���o�[�g�p(����)
	 * 
	 * @param word
	 * @return ����
	 */
	public String convertNameS(String word) {
		String wordID = getWord("C00548");
		String nameS = word + wordID;
		return nameS;
	}

	/**
	 * �R���o�[�g�p(��������)
	 * 
	 * @param word
	 * @return ��������
	 */
	public String convertNameK(String word) {
		String wordID = getWord("C00160");
		String nameK = word + wordID;
		return nameK;
	}

	/**
	 * @return numReferColumn
	 */
	public int getNumReferColumn() {
		return numReferColumn;
	}

	/**
	 * @param numReferColumn �ݒ肷�� numReferColumn
	 */
	public void setNumReferColumn(int numReferColumn) {
		this.numReferColumn = numReferColumn;
	}

	/**
	 * @return ServerName
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * @param serverName ServerName
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * @return �J�n�R�[�h
	 */
	public String getStartCode() {
		return startCode;
	}

	/**
	 * @param startCode �J�n�R�[�h
	 */
	public void setStartCode(String startCode) {
		this.startCode = startCode;
	}

	/**
	 * @return �Ȗڐ��Z�Ώۃt���O
	 */
	public String getClearanceFlg() {
		return clearanceFlg;
	}

	/**
	 * @param clearanceFlg 1:���Z�Ώ�
	 */
	public void setClearanceFlg(String clearanceFlg) {
		this.clearanceFlg = clearanceFlg;
	}

	/**
	 * ��s�����R�[�h�ݒ�
	 * 
	 * @param cbkCodes
	 */
	public void setCbkCodes(List<String> cbkCodes) {
		this.cbkCodes = cbkCodes;
	}

	/**
	 * �R�[�h���X�g�ݒ�
	 * 
	 * @param codes
	 */
	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	/**
	 * �a����ڂ̑�����Ή�
	 * 
	 * @param kbn
	 * @return �P��
	 */
	protected String getWordByDepositType(String kbn) {
		if ("1".equals(kbn)) {
			return getWord("C00463"); // ���� C00463
		} else if ("2".equals(kbn)) {
			return getWord("C00397"); // ���� C00397
		} else if ("3".equals(kbn)) {
			return getWord("C00045"); // �O�� C00045
		} else {
			return getWord("C00368"); // ���~ C00368
		}
	}

	/**
	 * �p�����[�^�N���X�ݒ�
	 * 
	 * @param paramObject �p�����[�^�N���X
	 */
	public void setParamObject(TransferBase paramObject) {
		this.paramObject = paramObject;
	}

	/**
	 * REF��ʂ̺��ނ�ݒ肷��
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		dialog.txtCode.setValue(code);
	}
}
