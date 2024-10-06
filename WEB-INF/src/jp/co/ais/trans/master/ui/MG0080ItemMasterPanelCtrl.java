package jp.co.ais.trans.master.ui;

import java.io.*;
import java.text.*;
import java.util.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;

/**
 * �Ȗڃ}�X�^��ʃR���g���[��
 */
public class MG0080ItemMasterPanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	protected static final String PROGRAM_ID = "MG0080";

	/** �V�K�ƕ��ʋ敪 */
	protected static int INSERT_KBN = 0;

	/** �ҏW�敪 */
	protected static int UPD_KBN = 1;

	/**
	 * �ڑ���Servlet����
	 * 
	 * @return Servlet��
	 */
	protected String getServletName() {
		return "MG0080ItemMasterServlet";
	}

	/** �p�l�� */
	public MG0080ItemMasterPanel panel;

	/** �Ȗڎ�� */
	public Map kmkShuMap;

	/** �⏕�敪 */
	public Map hkmKbnMap;

	/** �f�k�Ȗڐ���敪 */
	public Map kmkCntGlMap;

	/** AP�Ȗڐ���敪 */
	public Map kmkCntApMap;

	/** AR�Ȗڐ���敪 */
	public Map kmkCntArMap;

	/** �������̓t���O */
	public Map triCodeFlgMap;

	/** �]���֑Ώۃt���O */
	public Map excFlgMap;

	/** BG�Ȗڐ���敪 */
	public Map kmkCntBgMap;

	/** ���E�Ȗڐ���敪 */
	public Map kmkCntSousaiMap;

	/** BS��������敪 */
	public Map kesiKbnMap;

	/** �Ȗڃ}�X�^�ҏW��� */
	MG0080EditDisplayDialogCtrl dialog;

	/**
	 * �R���X�g���N�^.
	 */

	public MG0080ItemMasterPanelCtrl() {
		try {
			// �ꗗ��ʂ̏�����
			panel = new MG0080ItemMasterPanel(this);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00010");
		}

		// �Ȗڎ�ʂ̏�����
		kmkShuMap = new LinkedHashMap();
		kmkShuMap.put("0", getWord("C02108"));
		kmkShuMap.put("1", getWord("C02109"));
		kmkShuMap.put("2", getWord("C02110"));
		kmkShuMap.put("3", getWord("C02111"));
		kmkShuMap.put("9", getWord("C02112"));

		// �⏕�敪�̏�����
		hkmKbnMap = new LinkedHashMap();
		hkmKbnMap.put("0", getWord("C00412"));
		hkmKbnMap.put("1", getWord("C00006"));

		// �f�k�Ȗڐ���敪�̏�����
		kmkCntGlMap = new LinkedHashMap();
		kmkCntGlMap.put("00", getWord("C00372"));
		kmkCntGlMap.put("04", getWord("C02114"));
		kmkCntGlMap.put("05", getWord("C02115"));
		kmkCntGlMap.put("07", getWord("C02117"));

		// AP�Ȗڐ���敪�̏�����
		kmkCntApMap = new LinkedHashMap();
		kmkCntApMap.put("00", getWord("C00372"));
		kmkCntApMap.put("01", getWord("C02118"));
		kmkCntApMap.put("02", getWord("C02119"));

		// AR�Ȗڐ���敪�̏�����
		kmkCntArMap = new LinkedHashMap();
		kmkCntArMap.put("00", getWord("C00372"));
		kmkCntArMap.put("01", getWord("C02120"));
		kmkCntArMap.put("02", getWord("C02121"));

		// �������̓t���O�̏�����
		triCodeFlgMap = new LinkedHashMap();
		triCodeFlgMap.put("0", getWord("C01279"));
		triCodeFlgMap.put("2", getWord("C00401"));
		triCodeFlgMap.put("3", getWord("C00203"));
		triCodeFlgMap.put("4", getWord("C02122"));

		// �]���֑Ώۃt���O�̏�����
		excFlgMap = new LinkedHashMap();
		excFlgMap.put("0", getWord("C02099"));
		excFlgMap.put("1", getWord("C02123"));
		excFlgMap.put("2", getWord("C02124"));

		// BG�Ȗڐ���敪�̏�����
		kmkCntBgMap = new LinkedHashMap();
		kmkCntBgMap.put("00", getWord("C00372"));
		kmkCntBgMap.put("11", getWord("C02125"));

		// ���E�Ȗڐ���敪�̏�����
		kmkCntSousaiMap = new LinkedHashMap();
		kmkCntSousaiMap.put("0", getWord("C02099"));
		kmkCntSousaiMap.put("1", getWord("C02100"));

		// BS��������敪�̏�����
		kesiKbnMap = new LinkedHashMap();
		kesiKbnMap.put("0", getWord("C02099"));
		kesiKbnMap.put("1", getWord("C02100"));

		setBeginItemCondition();
		setEndItemCondition();
	}

	/**
	 * �p�l���擾
	 * 
	 * @return �ʉ݃}�X�^�p�l��
	 */
	public TPanelBusiness getPanel() {
		return this.panel;
	}

	/**
	 * 
	 */
	public void setBeginItemCondition() {
		// ���O�C�����[�U�[�̉�ЃR�[�h
		String itemCode = Util.avoidNull(panel.ctrlBeginItem.getValue());
		panel.ctrlBeginItem.getInputParameter().setCompanyCode(super.getLoginUserCompanyCode());
		panel.ctrlBeginItem.getInputParameter().setItemCode(itemCode);
	}

	/**
	 * 
	 */
	public void setEndItemCondition() {
		// ���O�C�����[�U�[�̉�ЃR�[�h
		String itemCode = Util.avoidNull(panel.ctrlEndItem.getValue());
		panel.ctrlEndItem.getInputParameter().setCompanyCode(super.getLoginUserCompanyCode());
		panel.ctrlEndItem.getInputParameter().setItemCode(itemCode);
	}

	/**
	 * �V�K�o�^����
	 */
	void insert() {
		try {
			// �ҏW��ʂ̏�����
			createEditDisplayDialog("C01698");
			// ���X�g�{�b�N�X�̒l�����
			dialog.setSelectedMap(kmkShuMap, hkmKbnMap, kmkCntGlMap, kmkCntApMap, kmkCntArMap, triCodeFlgMap,
				excFlgMap, kmkCntBgMap, kmkCntSousaiMap, kesiKbnMap);

			// �ҏW��ʂ̕\��
			dialog.show();

			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) {
				return;
			}

			// �f�[�^���ҏW����
			modify(dialog.getDataList(), true);

			// �I���s�ێ�����
			String kmkCode = dialog.getDataList().get("kmkCode").toString();
			modifySpreadRow(kmkCode, INSERT_KBN);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �_�C�A���O����
	 * 
	 * @param titleID
	 */
	protected void createEditDisplayDialog(String titleID) {
		dialog = new MG0080EditDisplayDialogCtrl(panel.getParentFrame(), titleID);
	}

	/**
	 * �ҏW����
	 */

	void update() {
		try {
			// �ҏW��ʂ̏�����
			createEditDisplayDialog("C00977");

			// ���X�g�{�b�N�X�̒l�����
			dialog.setSelectedMap(kmkShuMap, hkmKbnMap, kmkCntGlMap, kmkCntApMap, kmkCntArMap, triCodeFlgMap,
				excFlgMap, kmkCntBgMap, kmkCntSousaiMap, kesiKbnMap);

			// ���O�s���擾����
			int nomRow = panel.ssItem.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssItem.getDataSource();

			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// �ȖڃR�[�h
			map.put("kmkCode", model.getTableDataItem(nomRow, 1));
			// �Ȗږ���
			map.put("kmkName", model.getTableDataItem(nomRow, 2));
			// �Ȗڗ���
			map.put("kmkName_S", model.getTableDataItem(nomRow, 3));
			// �Ȗڌ�������
			map.put("kmkName_K", model.getTableDataItem(nomRow, 4));
			// �W�v�敪
			map.put("sumKbn", model.getTableDataItem(nomRow, 46));
			// �Ȗڎ��
			map.put("kmkShu", model.getTableDataItem(nomRow, 47));
			// �ݎ؋敪
			map.put("dcKbn", model.getTableDataItem(nomRow, 48));
			// �⏕�敪
			map.put("hkmKbn", model.getTableDataItem(nomRow, 49));
			// �Œ蕔�庰��
			map.put("koteiDepCode", model.getTableDataItem(nomRow, 9));
			// ����ŃR�[�h
			map.put("zeiCode", model.getTableDataItem(nomRow, 10));
			// �f�k�Ȗڐ���敪
			map.put("kmkCntGl", model.getTableDataItem(nomRow, 50));
			// AP�Ȗڐ���敪
			map.put("kmkCntAp", model.getTableDataItem(nomRow, 51));
			// AR�Ȗڐ���敪
			map.put("kmkCntAr", model.getTableDataItem(nomRow, 52));
			// BG�Ȗڐ���敪
			map.put("kmkCntBg", model.getTableDataItem(nomRow, 53));
			// �������̓t���O
			map.put("triCodeFlg", model.getTableDataItem(nomRow, 54));
			// �����������׸�
			map.put("hasFlg", model.getTableDataItem(nomRow, 55));
			// ���E�Ȗڐ���敪
			map.put("kmkCntSousai", model.getTableDataItem(nomRow, 56));
			// BS��������敪
			map.put("kesiKbn", model.getTableDataItem(nomRow, 57));
			// �]���֑Ώۃt���O
			map.put("excFlg", model.getTableDataItem(nomRow, 58));
			// �����`�[�����׸�
			map.put("glFlg1", model.getTableDataItem(nomRow, 59));
			// �o���`�[�����׸�
			map.put("glFlg2", model.getTableDataItem(nomRow, 60));
			// �U�֓`�[�����׸�
			map.put("glFlg3", model.getTableDataItem(nomRow, 61));
			// �o��Z�`�[�����׸�
			map.put("apFlg1", model.getTableDataItem(nomRow, 62));
			// �������`�[�����׸�
			map.put("apFlg2", model.getTableDataItem(nomRow, 63));
			// ���v��`�[�����׸�
			map.put("arFlg1", model.getTableDataItem(nomRow, 64));
			// �������`�[�����׸�
			map.put("arFlg2", model.getTableDataItem(nomRow, 65));
			// ���Y�v��`�[�����׸�
			map.put("faFlg1", model.getTableDataItem(nomRow, 66));
			// �x���˗��`�[�����׸�
			map.put("faFlg2", model.getTableDataItem(nomRow, 67));
			// ���ʉݓ��̓t���O
			map.put("mcrFlg", model.getTableDataItem(nomRow, 68));
			// �Ј����̓t���O
			map.put("empCodeFlg", model.getTableDataItem(nomRow, 69));
			// �Ǘ��P-6���̓t���O
			map.put("knrFlg1", model.getTableDataItem(nomRow, 70));
			map.put("knrFlg2", model.getTableDataItem(nomRow, 71));
			map.put("knrFlg3", model.getTableDataItem(nomRow, 72));
			map.put("knrFlg4", model.getTableDataItem(nomRow, 73));
			map.put("knrFlg5", model.getTableDataItem(nomRow, 74));
			map.put("knrFlg6", model.getTableDataItem(nomRow, 75));
			// ���v1-3���̓t���O
			map.put("hmFlg1", model.getTableDataItem(nomRow, 76));
			map.put("hmFlg2", model.getTableDataItem(nomRow, 77));
			map.put("hmFlg3", model.getTableDataItem(nomRow, 78));
			// ����ېœ��̓t���O
			map.put("uriZeiFlg", model.getTableDataItem(nomRow, 79));
			// �d���ېœ��̓t���O
			map.put("sirZeiFlg", model.getTableDataItem(nomRow, 80));
			// �ؕ������R�[�h
			map.put("sknCodeDr", model.getTableDataItem(nomRow, 42));
			// �ݕ������R�[�h
			map.put("sknCodeCr", model.getTableDataItem(nomRow, 43));
			// �J�n�N����
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 44)));
			// �I���N����
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 45)));
			// ������ʂ̐ݒ�
			map.put("flag", "update");

			// �f�[�^����ʂɐݒ肷��
			dialog.setValues(map);
			dialog.show();

			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) {
				return;
			}

			// �f�[�^���ҏW����
			modify(dialog.getDataList(), false);

			// �X�v���b�h�X�V
			String kmkCode = dialog.getDataList().get("kmkCode").toString();
			modifySpreadRow(kmkCode, UPD_KBN);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * ���ʏ���
	 */
	public void copy() {
		try {
			// �ҏW��ʂ̏�����
			createEditDisplayDialog("C01738");

			// ���X�g�{�b�N�X�̒l�����
			dialog.setSelectedMap(kmkShuMap, hkmKbnMap, kmkCntGlMap, kmkCntApMap, kmkCntArMap, triCodeFlgMap,
				excFlgMap, kmkCntBgMap, kmkCntSousaiMap, kesiKbnMap);
			// ���O�s���擾����
			int nomRow = panel.ssItem.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssItem.getDataSource();

			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// �ȖڃR�[�h
			map.put("kmkCode", model.getTableDataItem(nomRow, 1));
			// �Ȗږ���
			map.put("kmkName", model.getTableDataItem(nomRow, 2));
			// �Ȗڗ���
			map.put("kmkName_S", model.getTableDataItem(nomRow, 3));
			// �Ȗڌ�������
			map.put("kmkName_K", model.getTableDataItem(nomRow, 4));
			// �W�v�敪
			map.put("sumKbn", model.getTableDataItem(nomRow, 46));
			// �Ȗڎ��
			map.put("kmkShu", model.getTableDataItem(nomRow, 47));
			// �ݎ؋敪
			map.put("dcKbn", model.getTableDataItem(nomRow, 48));
			// �⏕�敪
			map.put("hkmKbn", model.getTableDataItem(nomRow, 49));
			// �Œ蕔�庰��
			map.put("koteiDepCode", model.getTableDataItem(nomRow, 9));
			// ����ŃR�[�h
			map.put("zeiCode", model.getTableDataItem(nomRow, 10));
			// �f�k�Ȗڐ���敪
			map.put("kmkCntGl", model.getTableDataItem(nomRow, 50));
			// AP�Ȗڐ���敪
			map.put("kmkCntAp", model.getTableDataItem(nomRow, 51));
			// AR�Ȗڐ���敪
			map.put("kmkCntAr", model.getTableDataItem(nomRow, 52));
			// BG�Ȗڐ���敪
			map.put("kmkCntBg", model.getTableDataItem(nomRow, 53));
			// �������̓t���O
			map.put("triCodeFlg", model.getTableDataItem(nomRow, 54));
			// �����������׸�
			map.put("hasFlg", model.getTableDataItem(nomRow, 55));
			// ���E�Ȗڐ���敪
			map.put("kmkCntSousai", model.getTableDataItem(nomRow, 56));
			// BS��������敪
			map.put("kesiKbn", model.getTableDataItem(nomRow, 57));
			// �]���֑Ώۃt���O
			map.put("excFlg", model.getTableDataItem(nomRow, 58));
			// �����`�[�����׸�
			map.put("glFlg1", model.getTableDataItem(nomRow, 59));
			// �o���`�[�����׸�
			map.put("glFlg2", model.getTableDataItem(nomRow, 60));
			// �U�֓`�[�����׸�
			map.put("glFlg3", model.getTableDataItem(nomRow, 61));
			// �o��Z�`�[�����׸�
			map.put("apFlg1", model.getTableDataItem(nomRow, 62));
			// �������`�[�����׸�
			map.put("apFlg2", model.getTableDataItem(nomRow, 63));
			// ���v��`�[�����׸�
			map.put("arFlg1", model.getTableDataItem(nomRow, 64));
			// �������`�[�����׸�
			map.put("arFlg2", model.getTableDataItem(nomRow, 65));
			// ���Y�v��`�[�����׸�
			map.put("faFlg1", model.getTableDataItem(nomRow, 66));
			// �x���˗��`�[�����׸�
			map.put("faFlg2", model.getTableDataItem(nomRow, 67));
			// ���ʉݓ��̓t���O
			map.put("mcrFlg", model.getTableDataItem(nomRow, 68));
			// �Ј����̓t���O
			map.put("empCodeFlg", model.getTableDataItem(nomRow, 69));
			// �Ǘ��P-6���̓t���O
			map.put("knrFlg1", model.getTableDataItem(nomRow, 70));
			map.put("knrFlg2", model.getTableDataItem(nomRow, 71));
			map.put("knrFlg3", model.getTableDataItem(nomRow, 72));
			map.put("knrFlg4", model.getTableDataItem(nomRow, 73));
			map.put("knrFlg5", model.getTableDataItem(nomRow, 74));
			map.put("knrFlg6", model.getTableDataItem(nomRow, 75));
			// ���v1-3���̓t���O
			map.put("hmFlg1", model.getTableDataItem(nomRow, 76));
			map.put("hmFlg2", model.getTableDataItem(nomRow, 77));
			map.put("hmFlg3", model.getTableDataItem(nomRow, 78));
			// ����ېœ��̓t���O
			map.put("uriZeiFlg", model.getTableDataItem(nomRow, 79));
			// �d���ېœ��̓t���O
			map.put("sirZeiFlg", model.getTableDataItem(nomRow, 80));
			// �ؕ������R�[�h
			map.put("sknCodeDr", model.getTableDataItem(nomRow, 42));
			// �ݕ������R�[�h
			map.put("sknCodeCr", model.getTableDataItem(nomRow, 43));
			// �J�n�N����
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 44)));
			// �I���N����
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 45)));
			// ������ʂ̐ݒ�
			map.put("flag", "insert");

			// �f�[�^����ʂɐݒ肷��
			dialog.setValues(map);
			dialog.show();

			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) {
				return;
			}

			// �f�[�^���ҏW��
			modify(dialog.getDataList(), true);

			String kmkCode = dialog.getDataList().get("kmkCode").toString();
			modifySpreadRow(kmkCode, INSERT_KBN);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �ύX����
	 * 
	 * @param map �f�[�^
	 * @param isInsert �V�K(true) or �X�V(false)
	 * @throws IOException
	 */
	public void modify(Map map, boolean isInsert) throws IOException {
		addSendValues("flag", isInsert ? "insert" : "update");
		// ��ЃR�[�h
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// �ȖڃR�[�h
		addSendValues("kmkCode", (String) map.get("kmkCode"));
		// �Ȗږ���
		addSendValues("kmkName", (String) map.get("kmkName"));
		// �Ȗڗ���
		addSendValues("kmkName_S", (String) map.get("kmkName_S"));
		// �Ȗڌ�������
		addSendValues("kmkName_K", (String) map.get("kmkName_K"));
		// �W�v�敪
		addSendValues("sumKbn", (String) map.get("sumKbn"));
		// �Ȗڎ��
		addSendValues("kmkShu", (String) map.get("kmkShu"));
		// �ݎ؋敪
		addSendValues("dcKbn", (String) map.get("dcKbn"));
		// �⏕�敪
		addSendValues("hkmKbn", (String) map.get("hkmKbn"));
		// �f�k�Ȗڐ���敪
		addSendValues("kmkCntGl", (String) map.get("kmkCntGl"));
		// AP�Ȗڐ���敪
		addSendValues("kmkCntAp", (String) map.get("kmkCntAp"));
		// AR�Ȗڐ���敪
		addSendValues("kmkCntAr", (String) map.get("kmkCntAr"));
		// �Œ蕔�庰��
		addSendValues("koteiDepCode", (String) map.get("koteiDepCode"));
		// ����ŃR�[�h
		addSendValues("zeiCode", (String) map.get("zeiCode"));
		// �����`�[�����׸�
		addSendValues("glFlg1", (String) map.get("glFlg1"));
		// �o���`�[�����׸�
		addSendValues("glFlg2", (String) map.get("glFlg2"));
		// �U�֓`�[�����׸�
		addSendValues("glFlg3", (String) map.get("glFlg3"));
		// �o��Z�`�[�����׸�
		addSendValues("apFlg1", (String) map.get("apFlg1"));
		// �������`�[�����׸�
		addSendValues("apFlg2", (String) map.get("apFlg2"));
		// ���v��`�[�����׸�
		addSendValues("arFlg1", (String) map.get("arFlg1"));
		// �������`�[�����׸�
		addSendValues("arFlg2", (String) map.get("arFlg2"));
		// ���Y�v��`�[�����׸�
		addSendValues("faFlg1", (String) map.get("faFlg1"));
		// �x���˗��`�[�����׸�
		addSendValues("faFlg2", (String) map.get("faFlg2"));
		// �������̓t���O
		addSendValues("triCodeFlg", (String) map.get("triCodeFlg"));
		// �����������׸�
		addSendValues("hasFlg", (String) map.get("hasFlg"));
		// �Ј����̓t���O
		addSendValues("empCodeFlg", (String) map.get("empCodeFlg"));
		// �Ǘ��P-6���̓t���O
		addSendValues("knrFlg1", (String) map.get("knrFlg1"));
		addSendValues("knrFlg2", (String) map.get("knrFlg2"));
		addSendValues("knrFlg3", (String) map.get("knrFlg3"));
		addSendValues("knrFlg4", (String) map.get("knrFlg4"));
		addSendValues("knrFlg5", (String) map.get("knrFlg5"));
		addSendValues("knrFlg6", (String) map.get("knrFlg6"));
		// ���v1-3���̓t���O
		addSendValues("hmFlg1", (String) map.get("hmFlg1"));
		addSendValues("hmFlg2", (String) map.get("hmFlg2"));
		addSendValues("hmFlg3", (String) map.get("hmFlg3"));
		// ����ېœ��̓t���O
		addSendValues("uriZeiFlg", (String) map.get("uriZeiFlg"));
		// �d���ېœ��̓t���O
		addSendValues("sirZeiFlg", (String) map.get("sirZeiFlg"));
		// ���ʉݓ��̓t���O
		addSendValues("mcrFlg", (String) map.get("mcrFlg"));
		// �]���֑Ώۃt���O
		addSendValues("excFlg", (String) map.get("excFlg"));
		// BG�Ȗڐ���敪
		addSendValues("kmkCntBg", (String) map.get("kmkCntBg"));
		// �ؕ������R�[�h
		addSendValues("sknCodeDr", (String) map.get("sknCodeDr"));
		// �ݕ������R�[�h
		addSendValues("sknCodeCr", (String) map.get("sknCodeCr"));
		// ���E�Ȗڐ���敪
		addSendValues("kmkCntSousai", (String) map.get("kmkCntSousai"));
		// BS��������敪
		addSendValues("kesiKbn", (String) map.get("kesiKbn"));
		// �J�n�N����
		addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// �I���N����
		addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		// �v���O�����h�c
		addSendValues("prgID", PROGRAM_ID);

		// �T�[�u���b�g�̐ڑ���
		if (!request(getServletName())) {
			// �T�[�o���̃G���[�ꍇ
			errorHandler(panel.getParentFrame());
		}
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�X�V
	 * 
	 * @param kmkCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String kmkCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(kmkCode, updKbn);
		TTable ssPanelSpread = panel.ssItem;
		int row = ssPanelSpread.getCurrentRow();
		panel.setDataList(cells);
		if (cells.size() == 0) {
			ssPanelSpread.setRowSelection(-999, 0);
		} else {
			// �V�K�ƕ��ʏꍇ
			if (updKbn == INSERT_KBN) {
				if (cells.size() != 1) {
					int lastRow = cells.size() - 1;
					selectSpreadRow(lastRow);
					// �ێ����Ă������l���X�N���[���o�[�ɃZ�b�g
					ssPanelSpread.getVertSB().setValue(lastRow * ssPanelSpread.getPixelHeight(row));
				} else {
					selectSpreadRow(0);
				}

			} else if (updKbn == UPD_KBN) {
				// �ҏW�ꍇ
				selectSpreadRow(row);
				// �ێ����Ă������l���X�N���[���o�[�ɃZ�b�g
				ssPanelSpread.getVertSB().setValue(row * ssPanelSpread.getPixelHeight(row));
			}
			ssPanelSpread.getHorizSB().setValue(0);
		}
		// �{�^�����b�N����
		lockBtn(panel.ssItem.getDataSource().getNumRows() != 0);
	}

	/**
	 * �{�^��(�V�K�A�폜�A�ҏW�A���ʁA���X�g�o��)���b�N
	 * 
	 * @param boo
	 */
	void lockBtn(boolean boo) {
		// �폜�{�^�����b�N�̏���
		panel.btnDelete.setEnabled(boo);
		// �ҏW�{�^�����b�N�̏���
		panel.btnEdit.setEnabled(boo);
		// ���ʃ{�^�����b�N�̏���
		panel.btnCopy.setEnabled(boo);
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�擾
	 * 
	 * @param kmkCode
	 * @param updKbn
	 * @return �X�V�̃Z�[��
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected Vector<Vector> setModifyCell(String kmkCode, int updKbn) throws IOException, TRequestException {
		String kaiCode = getLoginUserCompanyCode();
		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginKmkCode", Util.avoidNull(kmkCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("endKmkCode", Util.avoidNull(kmkCode));

		// �T�[�u���b�g�̐ڑ���
		if (!request(getServletName())) {
			throw new TRequestException();
		}
		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssItem.getDataSource();
		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = modifyDs.getCells();

		Iterator recordIte = getResultList().iterator();

		// �l�����邩�ǂ���
		if (!recordIte.hasNext()) {
			showMessage(panel, "W00100", "");
		}

		Vector<String> colum = new Vector<String>();

		for (int row = 0; recordIte.hasNext(); row++) {
			// ���ʂ̓��e���擾����
			Iterator dataIte = ((List) recordIte.next()).iterator();

			// ���ʂ�ǉ�����
			for (int column = 0; dataIte.hasNext(); column++) {
				colum.add(column, (String) dataIte.next());
			}

			String impossible = this.getWord("C01279");
			String possible = this.getWord("C01276");

			// �]���ɑΉ�����
			String text;
			String value;

			boolean inPut = !colum.get(5).equals("0");

			// �f�k�Ȗڐ���敪
			String kmkCntGl = colum.get(9);
			// AP�Ȗڐ���敪
			String kmkCntAp = colum.get(10);
			// AR�Ȗڐ���敪
			String kmkCntAr = colum.get(11);
			// BG�Ȗڐ���敪
			String kmkCntBg = colum.get(39);
			// �������̓t���O
			String triCodeFlg = colum.get(23);
			// �����������׸�
			String hasFlg = colum.get(24);
			// ���E�Ȗڐ���敪
			String kmkCntSousai = colum.get(42);
			// BS��������敪
			String kesiKbn = colum.get(43);
			// �]���֑Ώۃt���O
			String excFlg = colum.get(38);
			// �����`�[�����׸�
			String glFlg1 = colum.get(14);
			// �o���`�[�����׸�
			String glFlg2 = colum.get(15);
			// �U�֓`�[�����׸�
			String glFlg3 = colum.get(16);
			// �o��Z�`�[�����׸�
			String apFlg1 = colum.get(17);
			// �������`�[�����׸�
			String apFlg2 = colum.get(18);
			// ���v��`�[�����׸�
			String arFlg1 = colum.get(19);
			// �������`�[�����׸�
			String atFlg2 = colum.get(20);
			// ���Y�v��`�[�����׸�
			String faFlg1 = colum.get(21);
			// �x���˗��`�[�����׸�
			String faFlg2 = colum.get(22);
			// ���ʉݓ��̓t���O
			String mcrFlg = colum.get(37);
			// �Ј����̓t���O
			String empCodeFlg = colum.get(25);
			// �Ǘ��P-6���̓t���O
			String knrFlg1 = colum.get(26);
			String knrFlg2 = colum.get(27);
			String knrFlg3 = colum.get(28);
			String knrFlg4 = colum.get(29);
			String knrFlg5 = colum.get(30);
			String knrFlg6 = colum.get(31);
			// ���v1���̓t���O
			String hmFlg1 = colum.get(32);
			String hmFlg2 = colum.get(33);
			String hmFlg3 = colum.get(34);
			// ����ېœ��̓t���O
			String uriZeiFlg = colum.get(35);
			// �d���ېœ��̓t���O
			String sirZeiFlg = colum.get(36);

			// �Œ蕔�庰��
			colum.set(9, colum.get(12));
			// ����ŃR�[�h
			colum.set(10, colum.get(13));

			// �ؕ������R�[�h
			colum.set(42, colum.get(40));
			// �ݕ������R�[�h
			colum.set(43, colum.get(41));

			// �W�v�敪
			colum.add(46, "");
			value = colum.get(5);
			if ("0".equals(value)) {
				text = this.getWord("C02157");
			} else if ("1".equals(value)) {
				text = this.getWord("C02158");
			} else {
				text = this.getWord("C02159");
			}
			colum.set(46, value);
			colum.set(5, text);

			// �Ȗڎ��
			colum.add(47, "");
			value = colum.get(6);
			text = (String) kmkShuMap.get(value);
			colum.set(47, value);
			colum.set(6, text);

			// �ݎ؋敪
			colum.add(48, "");
			value = colum.get(7);
			text = ("0".equals(value) ? this.getWord("C01125") : this.getWord("C01228"));
			colum.set(48, value);
			colum.set(7, text);

			// �⏕�敪
			colum.add(49, "");
			value = colum.get(8);
			text = (String) hkmKbnMap.get(value);
			colum.set(49, value);
			colum.set(8, inPut ? "" : text);

			// �f�k�Ȗڐ���敪
			colum.add(50, "");
			value = kmkCntGl;
			text = (String) kmkCntGlMap.get(value);
			colum.set(50, value);
			colum.set(11, inPut ? "" : text);

			// AP�Ȗڐ���敪
			colum.add(51, "");
			value = kmkCntAp;
			text = (String) kmkCntApMap.get(value);
			colum.set(51, value);
			colum.set(12, inPut ? "" : text);

			// AR�Ȗڐ���敪
			colum.add(52, "");
			value = kmkCntAr;
			text = (String) kmkCntArMap.get(value);
			colum.set(52, value);
			colum.set(13, inPut ? "" : text);

			// BG�Ȗڐ���敪
			colum.add(53, "");
			value = kmkCntBg;
			text = (String) kmkCntBgMap.get(value);
			colum.set(53, value);
			colum.set(14, inPut ? "" : text);

			// �������̓t���O
			colum.add(54, "");
			value = triCodeFlg;
			text = (String) triCodeFlgMap.get(value);
			colum.set(54, value);
			colum.set(15, inPut ? "" : text);

			// �����������׸�
			colum.add(55, "");
			value = hasFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(55, value);
			colum.set(16, inPut ? "" : text);

			// ���E�Ȗڐ���敪
			colum.add(56, "");
			value = kmkCntSousai;
			text = (String) kmkCntSousaiMap.get(value);
			colum.set(56, value);
			colum.set(17, inPut ? "" : text);

			// BS��������敪
			colum.add(57, "");
			value = kesiKbn;
			text = ("0".equals(value) ? this.getWord("C02099") : this.getWord("C02100"));
			colum.set(57, value);
			colum.set(18, inPut ? "" : text);

			// �]���֑Ώۃt���O
			colum.add(58, "");
			value = excFlg;
			text = (String) excFlgMap.get(value);
			colum.set(58, value);
			colum.set(19, inPut ? "" : text);

			// �����`�[�����׸�
			colum.add(59, "");
			value = glFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(59, value);
			colum.set(20, inPut ? "" : text);

			// �o���`�[�����׸�
			colum.add(60, "");
			value = glFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(60, value);
			colum.set(21, inPut ? "" : text);

			// �U�֓`�[�����׸�
			colum.add(61, "");
			value = glFlg3;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(61, value);
			colum.set(22, inPut ? "" : text);

			// �o��Z�`�[�����׸�
			colum.add(62, "");
			value = apFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(62, value);
			colum.set(23, inPut ? "" : text);

			// �������`�[�����׸�
			colum.add(63, "");
			value = apFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(63, value);
			colum.set(24, inPut ? "" : text);

			// ���v��`�[�����׸�
			colum.add(64, "");
			value = arFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(64, value);
			colum.set(25, inPut ? "" : text);

			// �������`�[�����׸�
			colum.add(65, "");
			value = atFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(65, value);
			colum.set(26, inPut ? "" : text);

			// ���Y�v��`�[�����׸�
			colum.add(66, "");
			value = faFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(66, value);
			colum.set(27, inPut ? "" : text);

			// �x���˗��`�[�����׸�
			colum.add(67, "");
			value = faFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(67, value);
			colum.set(28, inPut ? "" : text);

			// ���ʉݓ��̓t���O
			colum.add(68, "");
			value = mcrFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(68, value);
			colum.set(29, inPut ? "" : text);

			String possible1 = this.getWord("C02371");
			// �Ј����̓t���O
			colum.add(69, "");
			value = empCodeFlg;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(69, value);
			colum.set(30, inPut ? "" : text);

			// �Ǘ��P���̓t���O
			colum.add(70, "");
			value = knrFlg1;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(70, value);
			colum.set(31, inPut ? "" : text);

			// �Ǘ�2���̓t���O
			colum.add(71, "");
			value = knrFlg2;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(71, value);
			colum.set(32, inPut ? "" : text);

			// �Ǘ�3���̓t���O
			colum.add(72, "");
			value = knrFlg3;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(72, value);
			colum.set(33, inPut ? "" : text);

			// �Ǘ�4���̓t���O
			colum.add(73, "");
			value = knrFlg4;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(73, value);
			colum.set(34, inPut ? "" : text);

			// �Ǘ�5���̓t���O
			colum.add(74, "");
			value = knrFlg5;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(74, value);
			colum.set(35, inPut ? "" : text);

			// �Ǘ�6���̓t���O
			colum.add(75, "");
			value = knrFlg6;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(75, value);
			colum.set(36, inPut ? "" : text);

			// ���v1���̓t���O
			colum.add(76, "");
			value = hmFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(76, value);
			colum.set(37, inPut ? "" : text);

			// ���v2���̓t���O
			colum.add(77, "");
			value = hmFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(77, value);
			colum.set(38, inPut ? "" : text);

			// ���v3���̓t���O
			colum.add(78, "");
			value = hmFlg3;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(78, value);
			colum.set(39, inPut ? "" : text);

			// ����ېœ��̓t���O
			colum.add(79, "");
			value = uriZeiFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(79, value);
			colum.set(40, inPut ? "" : text);

			// �d���ېœ��̓t���O
			colum.add(80, "");
			value = sirZeiFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(80, value);
			colum.set(41, inPut ? "" : text);

			try {
				colum.set(44, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(44))));
				colum.set(45, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(45))));
			} catch (ParseException e) {
				ClientLogger.error(this.getClass().getName() + ":" + colum.get(9), e);
			}
		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssItem.getCurrentRow(), colum);
		}
		return cells;
	}

	/**
	 * �폜����
	 */
	void delete() {
		if (this.showConfirmMessage(panel, "Q00007", "")) {
			try {
				// �I������Ă���s��1�ڂ�2�ڂ̃J�������擾
				int nomRow = panel.ssItem.getCurrentRow();
				// �f�[�^�W���擾����
				TableDataModel model = panel.ssItem.getDataSource();

				String kaiCode = (String) model.getTableDataItem(nomRow, 0); // ��ЃR�[�h
				String kmkCode = (String) model.getTableDataItem(nomRow, 1); // �ȖڃR�[�h
				// ������ʂ̐ݒ�
				addSendValues("flag", "delete");
				// ��ЃR�[�h�̐ݒ�
				addSendValues("kaiCode", kaiCode);
				// �ȖڃR�[�h�̎擾
				addSendValues("kmkCode", kmkCode);

				// �T�[�u���b�g�̐ڑ���
				if (!request(getServletName())) {
					errorHandler(panel.getParentFrame());
					return;
				}

				deleteSpreadRow();

			} catch (IOException e) {
				// ����ɏ�������܂���ł���
				errorHandler(panel.getParentFrame(), e, "E00009");
			}
		}
	}

	/**
	 * �폜�ꍇ�A�X�v���b�h�X�V
	 */
	protected void deleteSpreadRow() {
		int row = panel.ssItem.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssItem.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssItem.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssItem.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// �ێ����Ă������l���X�N���[���o�[�ɃZ�b�g
			panel.ssItem.getVertSB().setValue(0);
			panel.ssItem.getHorizSB().setValue(0);
		}
		// �{�^�����b�N����
		lockBtn(panel.ssItem.getDataSource().getNumRows() != 0);
	}

	/**
	 * �}�X�^�I���s��Ԃ̕ێ�
	 * 
	 * @param row
	 */
	protected void selectSpreadRow(int row) {
		panel.ssItem.setRowSelection(row, row);
		panel.ssItem.setCurrentCell(row, 0);
	}

	/**
	 * ��������
	 * 
	 * @return boolean
	 */
	boolean find() {
		try {
			String beginItem = panel.ctrlBeginItem.getValue().toString();
			String endItem = panel.ctrlEndItem.getValue().toString();

			if (Util.isSmallerThen(beginItem, endItem) == false) {
				// �x�����b�Z�[�W�\��
				panel.ctrlBeginItem.getField().requestFocus();
				showMessage(panel, "W00036", "C00077");
				return false;
			}

			// �\���f�[�^�̎擾
			return reflesh();
		} catch (IOException e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
		return false;
	}

	/**
	 * ��ʃ��t���b�V��
	 * 
	 * @return boolean
	 * @throws IOException
	 */
	public boolean reflesh() throws IOException {
		boolean dataExists = true;
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();

		// �J�n�R�[�h�̎擾
		String beginKmkCode = panel.ctrlBeginItem.getValue();
		// �I���R�[�h�̎擾
		String endKmkCode = panel.ctrlEndItem.getValue();

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginKmkCode", Util.avoidNull(beginKmkCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("endKmkCode", Util.avoidNull(endKmkCode));

		// ���M
		if (!request(getServletName())) {
			errorHandler(panel.getParentFrame());
			return false;
		}

		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = new Vector<Vector>();

		// ���ʂ��擾����
		Iterator recordIte = getResultList().iterator();

		// �l�����邩�ǂ���
		if (!recordIte.hasNext()) {
			panel.ctrlBeginItem.getField().requestFocus();
			showMessage(panel, "W00100");

			dataExists = false;
		}

		for (int row = 0; recordIte.hasNext(); row++) {
			// ���ʂ̓��e���擾����
			Iterator dataIte = ((List) recordIte.next()).iterator();

			// colum�̏�����
			Vector<String> colum = new Vector<String>();
			// ���ʂ�ǉ�����
			for (int column = 0; dataIte.hasNext(); column++) {
				colum.add(column, (String) dataIte.next());
			}

			String impossible = this.getWord("C01279");
			String possible = this.getWord("C01276");

			// �]���ɑΉ�����
			String text;
			String value;

			boolean inPut = !colum.get(5).equals("0");

			// �f�k�Ȗڐ���敪
			String kmkCntGl = colum.get(9);
			// AP�Ȗڐ���敪
			String kmkCntAp = colum.get(10);
			// AR�Ȗڐ���敪
			String kmkCntAr = colum.get(11);
			// BG�Ȗڐ���敪
			String kmkCntBg = colum.get(39);
			// �������̓t���O
			String triCodeFlg = colum.get(23);
			// �����������׸�
			String hasFlg = colum.get(24);
			// ���E�Ȗڐ���敪
			String kmkCntSousai = colum.get(42);
			// BS��������敪
			String kesiKbn = colum.get(43);
			// �]���֑Ώۃt���O
			String excFlg = colum.get(38);
			// �����`�[�����׸�
			String glFlg1 = colum.get(14);
			// �o���`�[�����׸�
			String glFlg2 = colum.get(15);
			// �U�֓`�[�����׸�
			String glFlg3 = colum.get(16);
			// �o��Z�`�[�����׸�
			String apFlg1 = colum.get(17);
			// �������`�[�����׸�
			String apFlg2 = colum.get(18);
			// ���v��`�[�����׸�
			String arFlg1 = colum.get(19);
			// �������`�[�����׸�
			String atFlg2 = colum.get(20);
			// ���Y�v��`�[�����׸�
			String faFlg1 = colum.get(21);
			// �x���˗��`�[�����׸�
			String faFlg2 = colum.get(22);
			// ���ʉݓ��̓t���O
			String mcrFlg = colum.get(37);
			// �Ј����̓t���O
			String empCodeFlg = colum.get(25);
			// �Ǘ��P-6���̓t���O
			String knrFlg1 = colum.get(26);
			String knrFlg2 = colum.get(27);
			String knrFlg3 = colum.get(28);
			String knrFlg4 = colum.get(29);
			String knrFlg5 = colum.get(30);
			String knrFlg6 = colum.get(31);
			// ���v1���̓t���O
			String hmFlg1 = colum.get(32);
			String hmFlg2 = colum.get(33);
			String hmFlg3 = colum.get(34);
			// ����ېœ��̓t���O
			String uriZeiFlg = colum.get(35);
			// �d���ېœ��̓t���O
			String sirZeiFlg = colum.get(36);

			// �Œ蕔�庰��
			colum.set(9, colum.get(12));
			// ����ŃR�[�h
			colum.set(10, colum.get(13));

			// �ؕ������R�[�h
			colum.set(42, colum.get(40));
			// �ݕ������R�[�h
			colum.set(43, colum.get(41));

			// �W�v�敪
			colum.add(46, "");
			value = colum.get(5);
			if ("0".equals(value)) {
				text = this.getWord("C02157");
			} else if ("1".equals(value)) {
				text = this.getWord("C02158");
			} else {
				text = this.getWord("C02159");
			}
			colum.set(46, value);
			colum.set(5, text);

			// �Ȗڎ��
			colum.add(47, "");
			value = colum.get(6);
			text = (String) kmkShuMap.get(value);
			colum.set(47, value);
			colum.set(6, text);

			// �ݎ؋敪
			colum.add(48, "");
			value = colum.get(7);
			text = ("0".equals(value) ? this.getWord("C01125") : this.getWord("C01228"));
			colum.set(48, value);
			colum.set(7, text);

			// �⏕�敪
			colum.add(49, "");
			value = colum.get(8);
			text = (String) hkmKbnMap.get(value);
			colum.set(49, value);
			colum.set(8, inPut ? "" : text);

			// �f�k�Ȗڐ���敪
			colum.add(50, "");
			value = kmkCntGl;
			text = (String) kmkCntGlMap.get(value);
			colum.set(50, value);
			colum.set(11, inPut ? "" : text);

			// AP�Ȗڐ���敪
			colum.add(51, "");
			value = kmkCntAp;
			text = (String) kmkCntApMap.get(value);
			colum.set(51, value);
			colum.set(12, inPut ? "" : text);

			// AR�Ȗڐ���敪
			colum.add(52, "");
			value = kmkCntAr;
			text = (String) kmkCntArMap.get(value);
			colum.set(52, value);
			colum.set(13, inPut ? "" : text);

			// BG�Ȗڐ���敪
			colum.add(53, "");
			value = kmkCntBg;
			text = (String) kmkCntBgMap.get(value);
			colum.set(53, value);
			colum.set(14, inPut ? "" : text);

			// �������̓t���O
			colum.add(54, "");
			value = triCodeFlg;
			text = (String) triCodeFlgMap.get(value);
			colum.set(54, value);
			colum.set(15, inPut ? "" : text);

			// �����������׸�
			colum.add(55, "");
			value = hasFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(55, value);
			colum.set(16, inPut ? "" : text);

			// ���E�Ȗڐ���敪
			colum.add(56, "");
			value = kmkCntSousai;
			text = (String) kmkCntSousaiMap.get(value);
			colum.set(56, value);
			colum.set(17, inPut ? "" : text);

			// BS��������敪
			colum.add(57, "");
			value = kesiKbn;
			text = ("0".equals(value) ? this.getWord("C02099") : this.getWord("C02100"));
			colum.set(57, value);
			colum.set(18, inPut ? "" : text);

			// �]���֑Ώۃt���O
			colum.add(58, "");
			value = excFlg;
			text = (String) excFlgMap.get(value);
			colum.set(58, value);
			colum.set(19, inPut ? "" : text);

			// �����`�[�����׸�
			colum.add(59, "");
			value = glFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(59, value);
			colum.set(20, inPut ? "" : text);

			// �o���`�[�����׸�
			colum.add(60, "");
			value = glFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(60, value);
			colum.set(21, inPut ? "" : text);

			// �U�֓`�[�����׸�
			colum.add(61, "");
			value = glFlg3;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(61, value);
			colum.set(22, inPut ? "" : text);

			// �o��Z�`�[�����׸�
			colum.add(62, "");
			value = apFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(62, value);
			colum.set(23, inPut ? "" : text);

			// �������`�[�����׸�
			colum.add(63, "");
			value = apFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(63, value);
			colum.set(24, inPut ? "" : text);

			// ���v��`�[�����׸�
			colum.add(64, "");
			value = arFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(64, value);
			colum.set(25, inPut ? "" : text);

			// �������`�[�����׸�
			colum.add(65, "");
			value = atFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(65, value);
			colum.set(26, inPut ? "" : text);

			// ���Y�v��`�[�����׸�
			colum.add(66, "");
			value = faFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(66, value);
			colum.set(27, inPut ? "" : text);

			// �x���˗��`�[�����׸�
			colum.add(67, "");
			value = faFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(67, value);
			colum.set(28, inPut ? "" : text);

			// ���ʉݓ��̓t���O
			colum.add(68, "");
			value = mcrFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(68, value);
			colum.set(29, inPut ? "" : text);

			String possible1 = this.getWord("C02371");
			// �Ј����̓t���O
			colum.add(69, "");
			value = empCodeFlg;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(69, value);
			colum.set(30, inPut ? "" : text);

			// �Ǘ��P���̓t���O
			colum.add(70, "");
			value = knrFlg1;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(70, value);
			colum.set(31, inPut ? "" : text);

			// �Ǘ�2���̓t���O
			colum.add(71, "");
			value = knrFlg2;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(71, value);
			colum.set(32, inPut ? "" : text);

			// �Ǘ�3���̓t���O
			colum.add(72, "");
			value = knrFlg3;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(72, value);
			colum.set(33, inPut ? "" : text);

			// �Ǘ�4���̓t���O
			colum.add(73, "");
			value = knrFlg4;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(73, value);
			colum.set(34, inPut ? "" : text);

			// �Ǘ�5���̓t���O
			colum.add(74, "");
			value = knrFlg5;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(74, value);
			colum.set(35, inPut ? "" : text);

			// �Ǘ�6���̓t���O
			colum.add(75, "");
			value = knrFlg6;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(75, value);
			colum.set(36, inPut ? "" : text);

			// ���v1���̓t���O
			colum.add(76, "");
			value = hmFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(76, value);
			colum.set(37, inPut ? "" : text);

			// ���v2���̓t���O
			colum.add(77, "");
			value = hmFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(77, value);
			colum.set(38, inPut ? "" : text);

			// ���v3���̓t���O
			colum.add(78, "");
			value = hmFlg3;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(78, value);
			colum.set(39, inPut ? "" : text);

			// ����ېœ��̓t���O
			colum.add(79, "");
			value = uriZeiFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(79, value);
			colum.set(40, inPut ? "" : text);

			// �d���ېœ��̓t���O
			colum.add(80, "");
			value = sirZeiFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(80, value);
			colum.set(41, inPut ? "" : text);

			try {
				colum.set(44, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(44))));
				colum.set(45, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(45))));
			} catch (ParseException e) {
				ClientLogger.error(this.getClass().getName() + ":" + colum.get(9), e);
			}

			cells.add(row, colum);
		}

		panel.setDataList(cells);

		return dataExists;
	}

	/**
	 * �G�N�Z�����X�g�o��
	 */

	void outptExcel() {

		try {
			panel.ctrlBeginItem.getField().requestFocus();
			// �m�F�_�C�A���O�\��
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}

			Map conds = new LinkedHashMap();
			// ���M����p�����[�^��ݒ�
			conds.put("flag", "report");
			// ��ЃR�[�h�̎擾
			conds.put("kaiCode", getLoginUserCompanyCode());
			// �J�n�R�[�h�̎擾
			conds.put("beginKmkCode", panel.ctrlBeginItem.getValue());
			// �I���R�[�h�̎擾
			conds.put("endKmkCode", panel.ctrlEndItem.getValue());
			conds.put("langCode", getLoginLanguage());
			this.download(panel, getServletName(), conds);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}
}
