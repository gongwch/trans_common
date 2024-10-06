package jp.co.ais.trans2.op.model.item;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * OP�A�C�e���}�l�[�W������
 */
public class OPItemManagerImpl extends TModel implements OPItemManager {

	/** OWNR_SHIP_CODE ���p���� */
	public static boolean isUseOwnrShip = ServerConfig.isFlagOn("trans.op.use.item.ownr.ship");

	/**
	 * REF�p�f�[�^�̎擾
	 * 
	 * @param condition
	 * @return REF�p�f�[�^
	 * @throws TException
	 */
	public List<OPItem> get(OPItemSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		// �������ʊi�[�p�z�񐶐�
		List<OPItem> list = new ArrayList<OPItem>();

		try {

			// �������`�F�b�N����
			VCreator chk = new VCreator();
			chk.add("SELECT COUNT(1) ");
			chk.add(" FROM OP_ITEM_MST dtl ");
			chk.add("WHERE 1 = 1");

			// WHERE�����ǉ�
			addWhere(condition, chk);

			int count = DBUtil.selectOneInt(conn, chk.toSQL());
			if (count == 0) {
				return list;
			}

			VCreator sql = new VCreator();
			sql.add(" SELECT ");
			sql.add("  dtl.KAI_CODE ");
			sql.add(" ,dtl.ITEM_CODE ");
			sql.add(" ,dtl.ITEM_NAME ");
			sql.add(" ,dtl.ITEM_NAME_S ");
			sql.add(" ,dtl.ITEM_NAME_K ");
			sql.add(" ,dtl.ITEM_INV_NAME ");
			sql.add(" ,dtl.CTRT_TYPE ");
			sql.add(" ,dtl.SA_KBN ");
			sql.add(" ,dtl.DC_KBN ");
			sql.add(" ,dtl.ITEM_CTL_KBN ");
			sql.add(" ,dtl.ITEM_SUB_KBN ");
			sql.add(" ,dtl.ADCOM_KBN ");
			sql.add(" ,dtl.BRKR_KBN ");
			sql.add(" ,dtl.OCCUR_BASE_KBN ");
			if (isUseOwnrShip) {
				sql.add(" ,dtl.OWNR_SHIP_CODE ");
				sql.add(" ,ownrship.CODE_NAME AS OWNR_SHIP_NAME ");
			}
			sql.add(" ,dtl.KMK_CODE ");
			sql.add(" ,dtl.HKM_CODE ");
			sql.add(" ,dtl.UKM_CODE ");

			if (condition.isShowAccountName() || condition.isOnlyShowAccountName()) {
				// �Ȗ�-�⏕-����̖��̕\��
				sql.add(" ,kmk.KMK_NAME ");
				sql.add(" ,hkm.HKM_NAME ");
				sql.add(" ,ukm.UKM_NAME ");
				sql.add(" ,kmk.KMK_NAME_S ");
				sql.add(" ,hkm.HKM_NAME_S ");
				sql.add(" ,ukm.UKM_NAME_S ");

			} else {
				sql.add(" ,NULL KMK_NAME ");
				sql.add(" ,NULL HKM_NAME ");
				sql.add(" ,NULL UKM_NAME ");
				sql.add(" ,NULL KMK_NAME_S ");
				sql.add(" ,NULL HKM_NAME_S ");
				sql.add(" ,NULL UKM_NAME_S ");

			}

			if (condition.isShowAccountName()) {
				// �Ȗ�-�⏕-����̖��̕\��
				sql.add(" ,zei.ZEI_NAME ZEI_NAME");
				sql.add(" ,zei.ZEI_NAME_S ZEI_NAME_S");

				sql.add(" ,at_kmk.KMK_NAME AT_KMK_NAME ");
				sql.add(" ,at_hkm.HKM_NAME AT_HKM_NAME ");
				sql.add(" ,at_ukm.UKM_NAME AT_UKM_NAME ");
				sql.add(" ,zeiat.ZEI_NAME AT_ZEI_NAME ");
				sql.add(" ,at_kmk.KMK_NAME_S AT_KMK_NAME_S ");
				sql.add(" ,at_hkm.HKM_NAME_S AT_HKM_NAME_S ");
				sql.add(" ,at_ukm.UKM_NAME_S AT_UKM_NAME_S ");
				sql.add(" ,zeiat.ZEI_NAME_S AT_ZEI_NAME_S ");

				sql.add(" ,kuri_kmk.KMK_NAME KURI_KMK_NAME ");
				sql.add(" ,kuri_hkm.HKM_NAME KURI_HKM_NAME ");
				sql.add(" ,kuri_ukm.UKM_NAME KURI_UKM_NAME ");
				sql.add(" ,zeikuri.ZEI_NAME KURI_ZEI_NAME ");
				sql.add(" ,kuri_kmk.KMK_NAME_S KURI_KMK_NAME_S ");
				sql.add(" ,kuri_hkm.HKM_NAME_S KURI_HKM_NAME_S ");
				sql.add(" ,kuri_ukm.UKM_NAME_S KURI_UKM_NAME_S ");
				sql.add(" ,zeikuri.ZEI_NAME_S KURI_ZEI_NAME_S ");

				sql.add(" ,at_kuri_kmk.KMK_NAME AT_KURI_KMK_NAME  ");
				sql.add(" ,at_kuri_hkm.HKM_NAME AT_KURI_HKM_NAME ");
				sql.add(" ,at_kuri_ukm.UKM_NAME AT_KURI_UKM_NAME ");
				sql.add(" ,zeiatkuri.ZEI_NAME AT_KURI_ZEI_NAME ");
				sql.add(" ,at_kuri_kmk.KMK_NAME_S AT_KURI_KMK_NAME_S ");
				sql.add(" ,at_kuri_hkm.HKM_NAME_S AT_KURI_HKM_NAME_S ");
				sql.add(" ,at_kuri_ukm.UKM_NAME_S AT_KURI_UKM_NAME_S ");
				sql.add(" ,zeiatkuri.ZEI_NAME_S AT_KURI_ZEI_NAME_S ");

				sql.add(" ,est_kmk.KMK_NAME EST_KMK_NAME ");
				sql.add(" ,est_hkm.HKM_NAME EST_HKM_NAME ");
				sql.add(" ,est_ukm.UKM_NAME EST_UKM_NAME ");
				sql.add(" ,zeiest.ZEI_NAME EST_ZEI_NAME ");
				sql.add(" ,est_kmk.KMK_NAME_S EST_KMK_NAME_S ");
				sql.add(" ,est_hkm.HKM_NAME_S EST_HKM_NAME_S ");
				sql.add(" ,est_ukm.UKM_NAME_S EST_UKM_NAME_S ");
				sql.add(" ,zeiest.ZEI_NAME_S EST_ZEI_NAME_S ");

				sql.add(" ,at_est_kmk.KMK_NAME AT_EST_KMK_NAME  ");
				sql.add(" ,at_est_hkm.HKM_NAME AT_EST_HKM_NAME ");
				sql.add(" ,at_est_ukm.UKM_NAME AT_EST_UKM_NAME ");
				sql.add(" ,zeiatest.ZEI_NAME AT_EST_ZEI_NAME ");
				sql.add(" ,at_est_kmk.KMK_NAME_S AT_EST_KMK_NAME_S ");
				sql.add(" ,at_est_hkm.HKM_NAME_S AT_EST_HKM_NAME_S ");
				sql.add(" ,at_est_ukm.UKM_NAME_S AT_EST_UKM_NAME_S ");
				sql.add(" ,zeiatest.ZEI_NAME_S AT_EST_ZEI_NAME_S ");

				// �V��v�
				sql.add(" ,newaccdr_kmk.KMK_NAME NEW_ACC_DR_KMK_NAME ");
				sql.add(" ,newaccdr_hkm.HKM_NAME NEW_ACC_DR_HKM_NAME ");
				sql.add(" ,newaccdr_ukm.UKM_NAME NEW_ACC_DR_UKM_NAME ");
				sql.add(" ,newaccdr_zei.ZEI_NAME NEW_ACC_DR_ZEI_NAME ");
				sql.add(" ,newaccdr_kmk.KMK_NAME_S NEW_ACC_DR_KMK_NAME_S ");
				sql.add(" ,newaccdr_hkm.HKM_NAME_S NEW_ACC_DR_HKM_NAME_S ");
				sql.add(" ,newaccdr_ukm.UKM_NAME_S NEW_ACC_DR_UKM_NAME_S ");
				sql.add(" ,newaccdr_zei.ZEI_NAME_S NEW_ACC_DR_ZEI_NAME_S ");

				sql.add(" ,newacccr_kmk.KMK_NAME NEW_ACC_CR_KMK_NAME  ");
				sql.add(" ,newacccr_hkm.HKM_NAME NEW_ACC_CR_HKM_NAME ");
				sql.add(" ,newacccr_ukm.UKM_NAME NEW_ACC_CR_UKM_NAME ");
				sql.add(" ,newacccr_zei.ZEI_NAME NEW_ACC_CR_ZEI_NAME ");
				sql.add(" ,newacccr_kmk.KMK_NAME_S NEW_ACC_CR_KMK_NAME_S ");
				sql.add(" ,newacccr_hkm.HKM_NAME_S NEW_ACC_CR_HKM_NAME_S ");
				sql.add(" ,newacccr_ukm.UKM_NAME_S NEW_ACC_CR_UKM_NAME_S ");
				sql.add(" ,newacccr_zei.ZEI_NAME_S NEW_ACC_CR_ZEI_NAME_S ");

				sql.add(" ,newaccdr2_kmk.KMK_NAME NEW_ACC_DR_2_KMK_NAME ");
				sql.add(" ,newaccdr2_hkm.HKM_NAME NEW_ACC_DR_2_HKM_NAME ");
				sql.add(" ,newaccdr2_ukm.UKM_NAME NEW_ACC_DR_2_UKM_NAME ");
				sql.add(" ,newaccdr2_zei.ZEI_NAME NEW_ACC_DR_2_ZEI_NAME ");
				sql.add(" ,newaccdr2_kmk.KMK_NAME_S NEW_ACC_DR_2_KMK_NAME_S ");
				sql.add(" ,newaccdr2_hkm.HKM_NAME_S NEW_ACC_DR_2_HKM_NAME_S ");
				sql.add(" ,newaccdr2_ukm.UKM_NAME_S NEW_ACC_DR_2_UKM_NAME_S ");
				sql.add(" ,newaccdr2_zei.ZEI_NAME_S NEW_ACC_DR_2_ZEI_NAME_S ");

			} else {
				sql.add(" ,NULL ZEI_NAME");
				sql.add(" ,NULL ZEI_NAME_S");

				sql.add(" ,NULL AT_KMK_NAME ");
				sql.add(" ,NULL AT_HKM_NAME ");
				sql.add(" ,NULL AT_UKM_NAME ");
				sql.add(" ,NULL AT_ZEI_NAME ");
				sql.add(" ,NULL AT_KMK_NAME_S ");
				sql.add(" ,NULL AT_HKM_NAME_S ");
				sql.add(" ,NULL AT_UKM_NAME_S ");
				sql.add(" ,NULL AT_ZEI_NAME_S ");

				sql.add(" ,NULL KURI_KMK_NAME ");
				sql.add(" ,NULL KURI_HKM_NAME ");
				sql.add(" ,NULL KURI_UKM_NAME ");
				sql.add(" ,NULL KURI_ZEI_NAME ");
				sql.add(" ,NULL KURI_KMK_NAME_S ");
				sql.add(" ,NULL KURI_HKM_NAME_S ");
				sql.add(" ,NULL KURI_UKM_NAME_S ");
				sql.add(" ,NULL KURI_ZEI_NAME_S ");

				sql.add(" ,NULL AT_KURI_KMK_NAME  ");
				sql.add(" ,NULL AT_KURI_HKM_NAME ");
				sql.add(" ,NULL AT_KURI_UKM_NAME ");
				sql.add(" ,NULL AT_KURI_ZEI_NAME ");
				sql.add(" ,NULL AT_KURI_KMK_NAME_S ");
				sql.add(" ,NULL AT_KURI_HKM_NAME_S ");
				sql.add(" ,NULL AT_KURI_UKM_NAME_S ");
				sql.add(" ,NULL AT_KURI_ZEI_NAME_S ");

				sql.add(" ,NULL EST_KMK_NAME ");
				sql.add(" ,NULL EST_HKM_NAME ");
				sql.add(" ,NULL EST_UKM_NAME ");
				sql.add(" ,NULL EST_ZEI_NAME ");
				sql.add(" ,NULL EST_KMK_NAME_S ");
				sql.add(" ,NULL EST_HKM_NAME_S ");
				sql.add(" ,NULL EST_UKM_NAME_S ");
				sql.add(" ,NULL EST_ZEI_NAME_S ");

				sql.add(" ,NULL AT_EST_KMK_NAME  ");
				sql.add(" ,NULL AT_EST_HKM_NAME ");
				sql.add(" ,NULL AT_EST_UKM_NAME ");
				sql.add(" ,NULL AT_EST_ZEI_NAME ");
				sql.add(" ,NULL AT_EST_KMK_NAME_S ");
				sql.add(" ,NULL AT_EST_HKM_NAME_S ");
				sql.add(" ,NULL AT_EST_UKM_NAME_S ");
				sql.add(" ,NULL AT_EST_ZEI_NAME_S ");

				// �V��v�
				sql.add(" ,NULL NEW_ACC_DR_KMK_NAME ");
				sql.add(" ,NULL NEW_ACC_DR_HKM_NAME ");
				sql.add(" ,NULL NEW_ACC_DR_UKM_NAME ");
				sql.add(" ,NULL NEW_ACC_DR_ZEI_NAME ");
				sql.add(" ,NULL NEW_ACC_DR_KMK_NAME_S ");
				sql.add(" ,NULL NEW_ACC_DR_HKM_NAME_S ");
				sql.add(" ,NULL NEW_ACC_DR_UKM_NAME_S ");
				sql.add(" ,NULL NEW_ACC_DR_ZEI_NAME_S ");

				sql.add(" ,NULL NEW_ACC_CR_KMK_NAME  ");
				sql.add(" ,NULL NEW_ACC_CR_HKM_NAME ");
				sql.add(" ,NULL NEW_ACC_CR_UKM_NAME ");
				sql.add(" ,NULL NEW_ACC_CR_ZEI_NAME ");
				sql.add(" ,NULL NEW_ACC_CR_KMK_NAME_S ");
				sql.add(" ,NULL NEW_ACC_CR_HKM_NAME_S ");
				sql.add(" ,NULL NEW_ACC_CR_UKM_NAME_S ");
				sql.add(" ,NULL NEW_ACC_CR_ZEI_NAME_S ");

				sql.add(" ,NULL NEW_ACC_DR_2_KMK_NAME ");
				sql.add(" ,NULL NEW_ACC_DR_2_HKM_NAME ");
				sql.add(" ,NULL NEW_ACC_DR_2_UKM_NAME ");
				sql.add(" ,NULL NEW_ACC_DR_2_ZEI_NAME ");
				sql.add(" ,NULL NEW_ACC_DR_2_KMK_NAME_S ");
				sql.add(" ,NULL NEW_ACC_DR_2_HKM_NAME_S ");
				sql.add(" ,NULL NEW_ACC_DR_2_UKM_NAME_S ");
				sql.add(" ,NULL NEW_ACC_DR_2_ZEI_NAME_S ");
			}

			sql.add(" ,dtl.ZEI_CODE ");
			sql.add(" ,dtl.ROW_OUTLINE ");

			sql.add(" ,dtl.AT_KMK_CODE ");
			sql.add(" ,dtl.AT_HKM_CODE ");
			sql.add(" ,dtl.AT_UKM_CODE ");
			sql.add(" ,dtl.AT_ZEI_CODE ");

			sql.add(" ,dtl.KURI_KMK_CODE ");
			sql.add(" ,dtl.KURI_HKM_CODE ");
			sql.add(" ,dtl.KURI_UKM_CODE ");
			sql.add(" ,dtl.KURI_ZEI_CODE ");

			sql.add(" ,dtl.AT_KURI_KMK_CODE ");
			sql.add(" ,dtl.AT_KURI_HKM_CODE ");
			sql.add(" ,dtl.AT_KURI_UKM_CODE ");
			sql.add(" ,dtl.AT_KURI_ZEI_CODE ");

			sql.add(" ,dtl.EST_KMK_CODE ");
			sql.add(" ,dtl.EST_HKM_CODE ");
			sql.add(" ,dtl.EST_UKM_CODE ");
			sql.add(" ,dtl.EST_ZEI_CODE ");

			sql.add(" ,dtl.AT_EST_KMK_CODE ");
			sql.add(" ,dtl.AT_EST_HKM_CODE ");
			sql.add(" ,dtl.AT_EST_UKM_CODE ");
			sql.add(" ,dtl.AT_EST_ZEI_CODE ");

			// �V��v�
			sql.add(" ,dtl.NEW_ACC_DR_KMK_CODE ");
			sql.add(" ,dtl.NEW_ACC_DR_HKM_CODE ");
			sql.add(" ,dtl.NEW_ACC_DR_UKM_CODE ");
			sql.add(" ,dtl.NEW_ACC_DR_ZEI_CODE ");

			sql.add(" ,dtl.NEW_ACC_CR_KMK_CODE ");
			sql.add(" ,dtl.NEW_ACC_CR_HKM_CODE ");
			sql.add(" ,dtl.NEW_ACC_CR_UKM_CODE ");
			sql.add(" ,dtl.NEW_ACC_CR_ZEI_CODE ");

			sql.add(" ,dtl.NEW_ACC_DR_2_KMK_CODE ");
			sql.add(" ,dtl.NEW_ACC_DR_2_HKM_CODE ");
			sql.add(" ,dtl.NEW_ACC_DR_2_UKM_CODE ");
			sql.add(" ,dtl.NEW_ACC_DR_2_ZEI_CODE ");

			sql.add(" ,dtl.STR_DATE ");
			sql.add(" ,dtl.END_DATE ");
			sql.add(" ,bnkr.BNKR_TYPE_CODE ");
			sql.add(" ,dtl.VCVR_ITEM_CODE ");
			sql.add(" ,dtl.INP_DATE ");
			sql.add(" ,dtl.UPD_DATE ");
			sql.add(" ,dtl.PRG_ID ");
			sql.add(" ,dtl.USR_ID ");
			sql.add(" FROM OP_ITEM_MST dtl ");

			if (condition.isShowAccountName() || condition.isOnlyShowAccountName()) {
				// �Ȗ�-�⏕-����̖��̕\��

				sql.add(" LEFT JOIN KMK_MST kmk  ON kmk.KAI_CODE = ? ", getCompanyCode());
				sql.add("                       AND kmk.KMK_CODE = dtl.KMK_CODE ");
				sql.add(" LEFT JOIN HKM_MST hkm  ON hkm.KAI_CODE = ? ", getCompanyCode());
				sql.add("                       AND hkm.KMK_CODE = dtl.KMK_CODE ");
				sql.add("                       AND hkm.HKM_CODE = dtl.HKM_CODE ");
				sql.add(" LEFT JOIN UKM_MST ukm  ON ukm.KAI_CODE = ? ", getCompanyCode());
				sql.add("                       AND ukm.KMK_CODE = dtl.KMK_CODE ");
				sql.add("                       AND ukm.HKM_CODE = dtl.HKM_CODE ");
				sql.add("                       AND ukm.UKM_CODE = dtl.UKM_CODE ");
			}

			if (condition.isShowAccountName()) {
				// �Ȗ�-�⏕-����̖��̕\��

				// ����
				sql.add(" LEFT JOIN SZEI_MST zei ON zei.KAI_CODE = ? ", getCompanyCode());
				sql.add("                    AND    zei.ZEI_CODE = dtl.ZEI_CODE ");

				sql.add(" LEFT JOIN KMK_MST at_kmk  ON at_kmk.KAI_CODE = ? ", getCompanyCode());
				sql.add("                       AND at_kmk.KMK_CODE = dtl.AT_KMK_CODE ");
				sql.add(" LEFT JOIN HKM_MST at_hkm  ON at_hkm.KAI_CODE = ? ", getCompanyCode());
				sql.add("                       AND at_hkm.KMK_CODE = dtl.AT_KMK_CODE ");
				sql.add("                       AND at_hkm.HKM_CODE = dtl.AT_HKM_CODE ");
				sql.add(" LEFT JOIN UKM_MST at_ukm  ON at_ukm.KAI_CODE = ? ", getCompanyCode());
				sql.add("                       AND at_ukm.KMK_CODE = dtl.AT_KMK_CODE ");
				sql.add("                       AND at_ukm.HKM_CODE = dtl.AT_HKM_CODE ");
				sql.add("                       AND at_ukm.UKM_CODE = dtl.AT_UKM_CODE ");
				sql.add(" LEFT JOIN SZEI_MST zeiat ON zeiat.KAI_CODE = ? ", getCompanyCode());
				sql.add("                    AND    zeiat.ZEI_CODE = dtl.AT_ZEI_CODE ");

				// �J��
				sql.add(" LEFT JOIN KMK_MST kuri_kmk  ON kuri_kmk.KAI_CODE = ? ", getCompanyCode());
				sql.add("                       AND kuri_kmk.KMK_CODE = dtl.KURI_KMK_CODE ");
				sql.add(" LEFT JOIN HKM_MST kuri_hkm  ON kuri_hkm.KAI_CODE = ? ", getCompanyCode());
				sql.add("                       AND kuri_hkm.KMK_CODE = dtl.KURI_KMK_CODE ");
				sql.add("                       AND kuri_hkm.HKM_CODE = dtl.KURI_HKM_CODE ");
				sql.add(" LEFT JOIN UKM_MST kuri_ukm  ON kuri_ukm.KAI_CODE = ? ", getCompanyCode());
				sql.add("                       AND kuri_ukm.KMK_CODE = dtl.KURI_KMK_CODE ");
				sql.add("                       AND kuri_ukm.HKM_CODE = dtl.KURI_HKM_CODE ");
				sql.add("                       AND kuri_ukm.UKM_CODE = dtl.KURI_UKM_CODE ");
				sql.add(" LEFT JOIN SZEI_MST zeikuri ON zeikuri.KAI_CODE = ? ", getCompanyCode());
				sql.add("                    AND    zeikuri.ZEI_CODE = dtl.KURI_ZEI_CODE ");

				// �J������
				sql.add(" LEFT JOIN KMK_MST at_kuri_kmk  ON at_kuri_kmk.KAI_CODE = ? ", getCompanyCode());
				sql.add("                       AND at_kuri_kmk.KMK_CODE = dtl.AT_KURI_KMK_CODE ");
				sql.add(" LEFT JOIN HKM_MST at_kuri_hkm  ON at_kuri_hkm.KAI_CODE = ? ", getCompanyCode());
				sql.add("                       AND at_kuri_hkm.KMK_CODE = dtl.AT_KURI_KMK_CODE ");
				sql.add("                       AND at_kuri_hkm.HKM_CODE = dtl.AT_KURI_HKM_CODE ");
				sql.add(" LEFT JOIN UKM_MST at_kuri_ukm  ON at_kuri_ukm.KAI_CODE = ? ", getCompanyCode());
				sql.add("                       AND at_kuri_ukm.KMK_CODE = dtl.AT_KURI_KMK_CODE ");
				sql.add("                       AND at_kuri_ukm.HKM_CODE = dtl.AT_KURI_HKM_CODE ");
				sql.add("                       AND at_kuri_ukm.UKM_CODE = dtl.AT_KURI_UKM_CODE ");
				sql.add(" LEFT JOIN SZEI_MST zeiatkuri ON zeiatkuri.KAI_CODE = ? ", getCompanyCode());
				sql.add("                    AND    zeiatkuri.ZEI_CODE = dtl.AT_KURI_ZEI_CODE ");

				// �T�Z
				sql.add(" LEFT JOIN KMK_MST est_kmk  ON est_kmk.KAI_CODE = ? ", getCompanyCode());
				sql.add("                       AND est_kmk.KMK_CODE = dtl.EST_KMK_CODE ");
				sql.add(" LEFT JOIN HKM_MST est_hkm  ON est_hkm.KAI_CODE = ? ", getCompanyCode());
				sql.add("                       AND est_hkm.KMK_CODE = dtl.EST_KMK_CODE ");
				sql.add("                       AND est_hkm.HKM_CODE = dtl.EST_HKM_CODE ");
				sql.add(" LEFT JOIN UKM_MST est_ukm  ON est_ukm.KAI_CODE = ? ", getCompanyCode());
				sql.add("                       AND est_ukm.KMK_CODE = dtl.EST_KMK_CODE ");
				sql.add("                       AND est_ukm.HKM_CODE = dtl.EST_HKM_CODE ");
				sql.add("                       AND est_ukm.UKM_CODE = dtl.EST_UKM_CODE ");
				sql.add(" LEFT JOIN SZEI_MST zeiest ON zeiest.KAI_CODE = ? ", getCompanyCode());
				sql.add("                    AND    zeiest.ZEI_CODE = dtl.EST_ZEI_CODE ");

				// �T�Z����
				sql.add(" LEFT JOIN KMK_MST at_est_kmk  ON at_est_kmk.KAI_CODE = ? ", getCompanyCode());
				sql.add("                       AND at_est_kmk.KMK_CODE = dtl.AT_EST_KMK_CODE ");
				sql.add(" LEFT JOIN HKM_MST at_est_hkm  ON at_est_hkm.KAI_CODE = ? ", getCompanyCode());
				sql.add("                       AND at_est_hkm.KMK_CODE = dtl.AT_EST_KMK_CODE ");
				sql.add("                       AND at_est_hkm.HKM_CODE = dtl.AT_EST_HKM_CODE ");
				sql.add(" LEFT JOIN UKM_MST at_est_ukm  ON at_est_ukm.KAI_CODE = ? ", getCompanyCode());
				sql.add("                       AND at_est_ukm.KMK_CODE = dtl.AT_EST_KMK_CODE ");
				sql.add("                       AND at_est_ukm.HKM_CODE = dtl.AT_EST_HKM_CODE ");
				sql.add("                       AND at_est_ukm.UKM_CODE = dtl.AT_EST_UKM_CODE ");
				sql.add(" LEFT JOIN SZEI_MST zeiatest ON zeiatest.KAI_CODE = ? ", getCompanyCode());
				sql.add("                    AND    zeiatest.ZEI_CODE = dtl.AT_EST_ZEI_CODE ");

				// �V��v��ؕ�
				sql.add(" LEFT JOIN KMK_MST newaccdr_kmk  ON newaccdr_kmk.KAI_CODE = ? ", getCompanyCode());
				sql.add("                                AND newaccdr_kmk.KMK_CODE = dtl.NEW_ACC_DR_KMK_CODE ");
				sql.add(" LEFT JOIN HKM_MST newaccdr_hkm  ON newaccdr_hkm.KAI_CODE = ? ", getCompanyCode());
				sql.add("                                AND newaccdr_hkm.KMK_CODE = dtl.NEW_ACC_DR_KMK_CODE ");
				sql.add("                                AND newaccdr_hkm.HKM_CODE = dtl.NEW_ACC_DR_HKM_CODE ");
				sql.add(" LEFT JOIN UKM_MST newaccdr_ukm  ON newaccdr_ukm.KAI_CODE = ? ", getCompanyCode());
				sql.add("                                AND newaccdr_ukm.KMK_CODE = dtl.NEW_ACC_DR_KMK_CODE ");
				sql.add("                                AND newaccdr_ukm.HKM_CODE = dtl.NEW_ACC_DR_HKM_CODE ");
				sql.add("                                AND newaccdr_ukm.UKM_CODE = dtl.NEW_ACC_DR_UKM_CODE ");
				sql.add(" LEFT JOIN SZEI_MST newaccdr_zei ON newaccdr_zei.KAI_CODE = ? ", getCompanyCode());
				sql.add("                             AND    newaccdr_zei.ZEI_CODE = dtl.NEW_ACC_DR_ZEI_CODE ");

				// �V��v��ݕ�
				sql.add(" LEFT JOIN KMK_MST newacccr_kmk  ON newacccr_kmk.KAI_CODE = ? ", getCompanyCode());
				sql.add("                                AND newacccr_kmk.KMK_CODE = dtl.NEW_ACC_CR_KMK_CODE ");
				sql.add(" LEFT JOIN HKM_MST newacccr_hkm  ON newacccr_hkm.KAI_CODE = ? ", getCompanyCode());
				sql.add("                                AND newacccr_hkm.KMK_CODE = dtl.NEW_ACC_CR_KMK_CODE ");
				sql.add("                                AND newacccr_hkm.HKM_CODE = dtl.NEW_ACC_CR_HKM_CODE ");
				sql.add(" LEFT JOIN UKM_MST newacccr_ukm  ON newacccr_ukm.KAI_CODE = ? ", getCompanyCode());
				sql.add("                                AND newacccr_ukm.KMK_CODE = dtl.NEW_ACC_CR_KMK_CODE ");
				sql.add("                                AND newacccr_ukm.HKM_CODE = dtl.NEW_ACC_CR_HKM_CODE ");
				sql.add("                                AND newacccr_ukm.UKM_CODE = dtl.NEW_ACC_CR_UKM_CODE ");
				sql.add(" LEFT JOIN SZEI_MST newacccr_zei ON newacccr_zei.KAI_CODE = ? ", getCompanyCode());
				sql.add("                             AND    newacccr_zei.ZEI_CODE = dtl.NEW_ACC_CR_ZEI_CODE ");

				// �V��v��ؕ�2
				sql.add(" LEFT JOIN KMK_MST newaccdr2_kmk  ON newaccdr2_kmk.KAI_CODE = ? ", getCompanyCode());
				sql.add("                                AND newaccdr2_kmk.KMK_CODE = dtl.NEW_ACC_DR_2_KMK_CODE ");
				sql.add(" LEFT JOIN HKM_MST newaccdr2_hkm  ON newaccdr2_hkm.KAI_CODE = ? ", getCompanyCode());
				sql.add("                                AND newaccdr2_hkm.KMK_CODE = dtl.NEW_ACC_DR_2_KMK_CODE ");
				sql.add("                                AND newaccdr2_hkm.HKM_CODE = dtl.NEW_ACC_DR_2_HKM_CODE ");
				sql.add(" LEFT JOIN UKM_MST newaccdr2_ukm  ON newaccdr2_ukm.KAI_CODE = ? ", getCompanyCode());
				sql.add("                                AND newaccdr2_ukm.KMK_CODE = dtl.NEW_ACC_DR_2_KMK_CODE ");
				sql.add("                                AND newaccdr2_ukm.HKM_CODE = dtl.NEW_ACC_DR_2_HKM_CODE ");
				sql.add("                                AND newaccdr2_ukm.UKM_CODE = dtl.NEW_ACC_DR_2_UKM_CODE ");
				sql.add(" LEFT JOIN SZEI_MST newaccdr2_zei ON newaccdr2_zei.KAI_CODE = ? ", getCompanyCode());
				sql.add("                             AND    newaccdr2_zei.ZEI_CODE = dtl.NEW_ACC_DR_2_ZEI_CODE ");
			}
			sql.add(" LEFT JOIN CM_BNKR_TYPE_MST bnkr ON bnkr.KAI_CODE = ? ", getCompanyCode());
			sql.add("                            AND     bnkr.BNKR_TYPE_CODE = dtl.ITEM_SUB_KBN ");

			if (isUseOwnrShip) {
				String ownrship = OPCodeDivision.OWRSHIP.toString();

				sql.add(" LEFT OUTER JOIN OP_CODE_MST ownrship ON ownrship.KAI_CODE = ? ", getCompanyCode());
				sql.add("  	                          AND         ownrship.CODE     = dtl.OWNR_SHIP_CODE ");
				sql.add("  	                          AND         ownrship.CODE_DIV = ? ", ownrship);
				sql.add("                             AND         NVL(ownrship.LCL_KBN,0) = 0 ");

			}

			sql.add("WHERE 1 = 1");

			// WHERE�����ǉ�
			addWhere(condition, sql);

			sql.add(" ORDER BY ");
			sql.add("    dtl.KAI_CODE ");
			sql.add("   ,dtl.ITEM_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mapping(rs));
			}

			DBUtil.close(statement);
			DBUtil.close(rs);

		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return list;
	}

	/**
	 * WHERE�����ǉ�
	 * 
	 * @param condition
	 * @param sql
	 */
	protected void addWhere(OPItemSearchCondition condition, VCreator sql) {
		// ���������F��ЃR�[�h
		if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
			sql.add("AND   dtl.KAI_CODE = ?", condition.getCompanyCode());
		}
		// ���������F�R�[�h
		if (!Util.isNullOrEmpty(condition.getCode())) {
			sql.add("AND   dtl.ITEM_CODE = ?", condition.getCode());
		}
		// ���������F�R�[�h�O����v
		if (!Util.isNullOrEmpty(condition.getCodeLike())) {
			sql.addLikeFront("AND   dtl.ITEM_CODE ?", condition.getCodeLike());
		}
		// ���������F���̂����܂�
		if (!Util.isNullOrEmpty(condition.getNamesLike())) {
			sql.addNLikeAmbi("AND   dtl.ITEM_NAME_S ?", condition.getNamesLike());
		}
		// ���������F�������̂����܂�
		if (!Util.isNullOrEmpty(condition.getNamekLike())) {
			sql.addNLikeAmbi("AND   dtl.ITEM_NAME_K ?", condition.getNamekLike());
		}
		// ���������F�J�n�R�[�h
		if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
			sql.add("AND   dtl.ITEM_CODE >= ?", condition.getCodeFrom());
		}
		// ���������F�I���R�[�h
		if (!Util.isNullOrEmpty(condition.getCodeTo())) {
			sql.add("AND   dtl.ITEM_CODE <= ?", condition.getCodeTo());
		}

		if (condition.getContractType() != null) { // �_��^�C�v
			sql.add(" AND   dtl.CTRT_TYPE = ? ", condition.getContractType().value);
		}
		if (condition.getItemControlDivision() != null) { // �A�C�e������敪
			sql.add(" AND   dtl.ITEM_CTL_KBN = ? ", condition.getItemControlDivision().getName());
		}
		if (condition.getItemSubDivision() != null) { // �A�C�e��SUB�敪
			sql.add(" AND   dtl.ITEM_SUB_KBN = ? ", condition.getItemSubDivision().getName());
		}
		if (condition.getBrkrTypeCode() != null) { // �A�C�e��SUB�敪(BUNKER_TYPE)
			sql.add(" AND   dtl.ITEM_SUB_KBN = ? ", condition.getBrkrTypeCode());
		}
		if (condition.getItemSubDivisions() != null && !condition.getItemSubDivisions().isEmpty()) { // �A�C�e��SUB�敪(����)
			sql.add(" AND (1 = 2 ");
			for (OPItemSubDivision sub : condition.getItemSubDivisions()) {
				sql.add("      OR dtl.ITEM_SUB_KBN = ? ", sub.getName());
			}
			sql.add(" ) ");
		}
		if (condition.getDc() != null) { // �ݎ؋敪
			sql.add(" AND   dtl.DC_KBN = ? ", condition.getDc().value);
		}
		if (condition.getSa() != null) { // SA�敪
			sql.add(" AND   dtl.SA_KBN = ? ", condition.getSa().value);
		}

		if (condition.getADCOM_KBN() != -1) {
			sql.add(" AND   dtl.ADCOM_KBN = ? ", condition.getADCOM_KBN());
		}
		if (condition.getBRKR_KBN() != -1) {
			sql.add(" AND   dtl.BRKR_KBN = ? ", condition.getBRKR_KBN());
		}

		// �ŏI�X�V����
		if (condition.getLastUpdateDate() != null) {
			sql.adt(" AND  (dtl.INP_DATE > ? ", condition.getLastUpdateDate());
			sql.adt("    OR dtl.UPD_DATE > ?)", condition.getLastUpdateDate());
		}

		if (condition.getCodeList() != null && !condition.getCodeList().isEmpty()) {
			sql.add(" AND   dtl.ITEM_CODE IN ? ", condition.getCodeList());
		}
	}

	/**
	 * �������ʒl��Bean�ɃZ�b�g
	 * 
	 * @param rs ResultSet ��������
	 * @return Employee �������ʒl���Z�b�g���ꂽBean
	 * @throws Exception
	 */
	protected OPItem mapping(ResultSet rs) throws Exception {

		OPItem bean = createOPItem();

		bean.setKAI_CODE(rs.getString("KAI_CODE")); // ��ЃR�[�h
		bean.setITEM_CODE(rs.getString("ITEM_CODE")); // ITEM�R�[�h
		bean.setITEM_NAME(rs.getString("ITEM_NAME")); // ITEM����
		bean.setITEM_NAME_S(rs.getString("ITEM_NAME_S")); // ITEM����
		bean.setITEM_NAME_K(rs.getString("ITEM_NAME_K")); // ITEM��������
		bean.setITEM_INV_NAME(rs.getString("ITEM_INV_NAME")); // ��������
		bean.setCTRT_TYPE(rs.getInt("CTRT_TYPE")); // CONTRACT�^�C�v
		bean.setDC_KBN(rs.getInt("DC_KBN")); // �ݎ؋敪
		bean.setSA_KBN(rs.getInt("SA_KBN")); // SA�敪
		bean.setITEM_CTL_KBN(rs.getString("ITEM_CTL_KBN")); // �A�C�e������敪
		bean.setITEM_SUB_KBN(rs.getString("ITEM_SUB_KBN")); // �A�C�e��SUB�敪
		bean.setBNKR_TYPE_CODE(rs.getString("BNKR_TYPE_CODE"));
		bean.setADCOM_KBN(rs.getInt("ADCOM_KBN")); // �R�~�b�V�����敪
		bean.setBRKR_KBN(rs.getInt("BRKR_KBN")); // Brokerage�敪
		bean.setOCCUR_BASE_KBN(rs.getInt("OCCUR_BASE_KBN"));// ��������敪
		if (isUseOwnrShip) {
			bean.setOWNR_SHIP_CODE(rs.getString("OWNR_SHIP_CODE"));// OWNR_SHIP_CODE
			bean.setOWNR_SHIP_NAME(rs.getString("OWNR_SHIP_NAME"));// OWNR_SHIP_NAME
		}
		// �Ȗ�
		bean.setKMK_CODE(rs.getString("KMK_CODE")); // �ȖڃR�[�h
		bean.setHKM_CODE(rs.getString("HKM_CODE")); // �⏕�ȖڃR�[�h
		bean.setUKM_CODE(rs.getString("UKM_CODE")); // ����ȖڃR�[�h
		bean.setZEI_CODE(rs.getString("ZEI_CODE")); // ����ŃR�[�h

		bean.setKMK_NAME(rs.getString("KMK_NAME")); // �Ȗږ���
		bean.setHKM_NAME(rs.getString("HKM_NAME")); // �⏕�Ȗږ���
		bean.setUKM_NAME(rs.getString("UKM_NAME")); // ����Ȗږ���
		bean.setZEI_NAME(rs.getString("ZEI_NAME"));
		bean.setKMK_NAME_S(rs.getString("KMK_NAME_S")); // �Ȗڗ���
		bean.setHKM_NAME_S(rs.getString("HKM_NAME_S")); // �⏕�Ȗڗ���
		bean.setUKM_NAME_S(rs.getString("UKM_NAME_S")); // ����Ȗڗ���
		bean.setZEI_NAME_S(rs.getString("ZEI_NAME_S"));

		// ����
		bean.setAT_KMK_CODE(rs.getString("AT_KMK_CODE")); // AT�ȖڃR�[�h
		bean.setAT_HKM_CODE(rs.getString("AT_HKM_CODE")); // AT�⏕�ȖڃR�[�h
		bean.setAT_UKM_CODE(rs.getString("AT_UKM_CODE")); // AT����ȖڃR�[�h
		bean.setAT_ZEI_CODE(rs.getString("AT_ZEI_CODE")); // AT�Ȗڂ̏���ŃR�[�h

		bean.setAT_KMK_NAME(rs.getString("AT_KMK_NAME")); // �Ȗږ���
		bean.setAT_HKM_NAME(rs.getString("AT_HKM_NAME")); // �⏕�Ȗږ���
		bean.setAT_UKM_NAME(rs.getString("AT_UKM_NAME")); // ����Ȗږ���
		bean.setAT_ZEI_NAME(rs.getString("AT_ZEI_NAME"));
		bean.setAT_KMK_NAME_S(rs.getString("AT_KMK_NAME_S")); // �Ȗڗ���
		bean.setAT_HKM_NAME_S(rs.getString("AT_HKM_NAME_S")); // �⏕�Ȗڗ���
		bean.setAT_UKM_NAME_S(rs.getString("AT_UKM_NAME_S")); // ����Ȗڗ���
		bean.setAT_ZEI_NAME_S(rs.getString("AT_ZEI_NAME_S"));

		// �J��
		bean.setKURI_KMK_CODE(rs.getString("KURI_KMK_CODE")); // KURI�ȖڃR�[�h
		bean.setKURI_HKM_CODE(rs.getString("KURI_HKM_CODE")); // KURI�⏕�ȖڃR�[�h
		bean.setKURI_UKM_CODE(rs.getString("KURI_UKM_CODE")); // KURI����ȖڃR�[�h
		bean.setKURI_ZEI_CODE(rs.getString("KURI_ZEI_CODE")); // KURI�Ȗڂ̏���ŃR�[�h

		bean.setKURI_KMK_NAME(rs.getString("KURI_KMK_NAME")); // �Ȗږ���
		bean.setKURI_HKM_NAME(rs.getString("KURI_HKM_NAME")); // �⏕�Ȗږ���
		bean.setKURI_UKM_NAME(rs.getString("KURI_UKM_NAME")); // ����Ȗږ���
		bean.setKURI_ZEI_NAME(rs.getString("KURI_ZEI_NAME"));
		bean.setKURI_KMK_NAME_S(rs.getString("KURI_KMK_NAME_S")); // �Ȗڗ���
		bean.setKURI_HKM_NAME_S(rs.getString("KURI_HKM_NAME_S")); // �⏕�Ȗڗ���
		bean.setKURI_UKM_NAME_S(rs.getString("KURI_UKM_NAME_S")); // ����Ȗڗ���
		bean.setKURI_ZEI_NAME_S(rs.getString("KURI_ZEI_NAME_S"));

		// �J������
		bean.setAT_KURI_KMK_CODE(rs.getString("AT_KURI_KMK_CODE")); // AT KURI�ȖڃR�[�h
		bean.setAT_KURI_HKM_CODE(rs.getString("AT_KURI_HKM_CODE")); // AT KURI�⏕�ȖڃR�[�h
		bean.setAT_KURI_UKM_CODE(rs.getString("AT_KURI_UKM_CODE")); // AT KURI����ȖڃR�[�h
		bean.setAT_KURI_ZEI_CODE(rs.getString("AT_KURI_ZEI_CODE")); // AT KURI�Ȗڂ̏���ŃR�[�h

		bean.setAT_KURI_KMK_NAME(rs.getString("AT_KURI_KMK_NAME")); // �Ȗږ���
		bean.setAT_KURI_HKM_NAME(rs.getString("AT_KURI_HKM_NAME")); // �⏕�Ȗږ���
		bean.setAT_KURI_UKM_NAME(rs.getString("AT_KURI_UKM_NAME")); // ����Ȗږ���
		bean.setAT_KURI_ZEI_NAME(rs.getString("AT_KURI_ZEI_NAME"));
		bean.setAT_KURI_KMK_NAME_S(rs.getString("AT_KURI_KMK_NAME_S")); // �Ȗڗ���
		bean.setAT_KURI_HKM_NAME_S(rs.getString("AT_KURI_HKM_NAME_S")); // �⏕�Ȗڗ���
		bean.setAT_KURI_UKM_NAME_S(rs.getString("AT_KURI_UKM_NAME_S")); // ����Ȗڗ���
		bean.setAT_KURI_ZEI_NAME_S(rs.getString("AT_KURI_ZEI_NAME_S"));

		// �T�Z
		bean.setEST_KMK_CODE(rs.getString("EST_KMK_CODE")); // EST�ȖڃR�[�h
		bean.setEST_HKM_CODE(rs.getString("EST_HKM_CODE")); // EST�⏕�ȖڃR�[�h
		bean.setEST_UKM_CODE(rs.getString("EST_UKM_CODE")); // EST����ȖڃR�[�h
		bean.setEST_ZEI_CODE(rs.getString("EST_ZEI_CODE")); // EST�Ȗڂ̏���ŃR�[�h

		bean.setEST_KMK_NAME(rs.getString("EST_KMK_NAME")); // �Ȗږ���
		bean.setEST_HKM_NAME(rs.getString("EST_HKM_NAME")); // �⏕�Ȗږ���
		bean.setEST_UKM_NAME(rs.getString("EST_UKM_NAME")); // ����Ȗږ���
		bean.setEST_ZEI_NAME(rs.getString("EST_ZEI_NAME"));
		bean.setEST_KMK_NAME_S(rs.getString("EST_KMK_NAME_S")); // �Ȗڗ���
		bean.setEST_HKM_NAME_S(rs.getString("EST_HKM_NAME_S")); // �⏕�Ȗڗ���
		bean.setEST_UKM_NAME_S(rs.getString("EST_UKM_NAME_S")); // ����Ȗڗ���
		bean.setEST_ZEI_NAME_S(rs.getString("EST_ZEI_NAME_S"));

		// �T�Z����
		bean.setAT_EST_KMK_CODE(rs.getString("AT_EST_KMK_CODE")); // AT EST�ȖڃR�[�h
		bean.setAT_EST_HKM_CODE(rs.getString("AT_EST_HKM_CODE")); // AT EST�⏕�ȖڃR�[�h
		bean.setAT_EST_UKM_CODE(rs.getString("AT_EST_UKM_CODE")); // AT EST����ȖڃR�[�h
		bean.setAT_EST_ZEI_CODE(rs.getString("AT_EST_ZEI_CODE")); // AT EST�Ȗڂ̏���ŃR�[�h

		bean.setAT_EST_KMK_NAME(rs.getString("AT_EST_KMK_NAME")); // �Ȗږ���
		bean.setAT_EST_HKM_NAME(rs.getString("AT_EST_HKM_NAME")); // �⏕�Ȗږ���
		bean.setAT_EST_UKM_NAME(rs.getString("AT_EST_UKM_NAME")); // ����Ȗږ���
		bean.setAT_EST_ZEI_NAME(rs.getString("AT_EST_ZEI_NAME"));
		bean.setAT_EST_KMK_NAME_S(rs.getString("AT_EST_KMK_NAME_S")); // �Ȗڗ���
		bean.setAT_EST_HKM_NAME_S(rs.getString("AT_EST_HKM_NAME_S")); // �⏕�Ȗڗ���
		bean.setAT_EST_UKM_NAME_S(rs.getString("AT_EST_UKM_NAME_S")); // ����Ȗڗ���
		bean.setAT_EST_ZEI_NAME_S(rs.getString("AT_EST_ZEI_NAME_S"));

		// �V��v��ؕ�
		bean.setNEW_ACC_DR_KMK_CODE(rs.getString("NEW_ACC_DR_KMK_CODE")); // �ȖڃR�[�h
		bean.setNEW_ACC_DR_HKM_CODE(rs.getString("NEW_ACC_DR_HKM_CODE")); // �⏕�ȖڃR�[�h
		bean.setNEW_ACC_DR_UKM_CODE(rs.getString("NEW_ACC_DR_UKM_CODE")); // ����ȖڃR�[�h
		bean.setNEW_ACC_DR_ZEI_CODE(rs.getString("NEW_ACC_DR_ZEI_CODE")); // �Ȗڂ̏���ŃR�[�h

		bean.setNEW_ACC_DR_KMK_NAME(rs.getString("NEW_ACC_DR_KMK_NAME")); // �Ȗږ���
		bean.setNEW_ACC_DR_HKM_NAME(rs.getString("NEW_ACC_DR_HKM_NAME")); // �⏕�Ȗږ���
		bean.setNEW_ACC_DR_UKM_NAME(rs.getString("NEW_ACC_DR_UKM_NAME")); // ����Ȗږ���
		bean.setNEW_ACC_DR_ZEI_NAME(rs.getString("NEW_ACC_DR_ZEI_NAME"));
		bean.setNEW_ACC_DR_KMK_NAME_S(rs.getString("NEW_ACC_DR_KMK_NAME_S")); // �Ȗڗ���
		bean.setNEW_ACC_DR_HKM_NAME_S(rs.getString("NEW_ACC_DR_HKM_NAME_S")); // �⏕�Ȗڗ���
		bean.setNEW_ACC_DR_UKM_NAME_S(rs.getString("NEW_ACC_DR_UKM_NAME_S")); // ����Ȗڗ���
		bean.setNEW_ACC_DR_ZEI_NAME_S(rs.getString("NEW_ACC_DR_ZEI_NAME_S"));

		// �V��v��ݕ�
		bean.setNEW_ACC_CR_KMK_CODE(rs.getString("NEW_ACC_CR_KMK_CODE")); // �ȖڃR�[�h
		bean.setNEW_ACC_CR_HKM_CODE(rs.getString("NEW_ACC_CR_HKM_CODE")); // �⏕�ȖڃR�[�h
		bean.setNEW_ACC_CR_UKM_CODE(rs.getString("NEW_ACC_CR_UKM_CODE")); // ����ȖڃR�[�h
		bean.setNEW_ACC_CR_ZEI_CODE(rs.getString("NEW_ACC_CR_ZEI_CODE")); // �Ȗڂ̏���ŃR�[�h

		bean.setNEW_ACC_CR_KMK_NAME(rs.getString("NEW_ACC_CR_KMK_NAME")); // �Ȗږ���
		bean.setNEW_ACC_CR_HKM_NAME(rs.getString("NEW_ACC_CR_HKM_NAME")); // �⏕�Ȗږ���
		bean.setNEW_ACC_CR_UKM_NAME(rs.getString("NEW_ACC_CR_UKM_NAME")); // ����Ȗږ���
		bean.setNEW_ACC_CR_ZEI_NAME(rs.getString("NEW_ACC_CR_ZEI_NAME"));
		bean.setNEW_ACC_CR_KMK_NAME_S(rs.getString("NEW_ACC_CR_KMK_NAME_S")); // �Ȗڗ���
		bean.setNEW_ACC_CR_HKM_NAME_S(rs.getString("NEW_ACC_CR_HKM_NAME_S")); // �⏕�Ȗڗ���
		bean.setNEW_ACC_CR_UKM_NAME_S(rs.getString("NEW_ACC_CR_UKM_NAME_S")); // ����Ȗڗ���
		bean.setNEW_ACC_CR_ZEI_NAME_S(rs.getString("NEW_ACC_CR_ZEI_NAME_S"));

		// �V��v��ؕ�2
		bean.setNEW_ACC_DR_2_KMK_CODE(rs.getString("NEW_ACC_DR_2_KMK_CODE")); // �ȖڃR�[�h
		bean.setNEW_ACC_DR_2_HKM_CODE(rs.getString("NEW_ACC_DR_2_HKM_CODE")); // �⏕�ȖڃR�[�h
		bean.setNEW_ACC_DR_2_UKM_CODE(rs.getString("NEW_ACC_DR_2_UKM_CODE")); // ����ȖڃR�[�h
		bean.setNEW_ACC_DR_2_ZEI_CODE(rs.getString("NEW_ACC_DR_2_ZEI_CODE")); // �Ȗڂ̏���ŃR�[�h

		bean.setNEW_ACC_DR_2_KMK_NAME(rs.getString("NEW_ACC_DR_2_KMK_NAME")); // �Ȗږ���
		bean.setNEW_ACC_DR_2_HKM_NAME(rs.getString("NEW_ACC_DR_2_HKM_NAME")); // �⏕�Ȗږ���
		bean.setNEW_ACC_DR_2_UKM_NAME(rs.getString("NEW_ACC_DR_2_UKM_NAME")); // ����Ȗږ���
		bean.setNEW_ACC_DR_2_ZEI_NAME(rs.getString("NEW_ACC_DR_2_ZEI_NAME"));
		bean.setNEW_ACC_DR_2_KMK_NAME_S(rs.getString("NEW_ACC_DR_2_KMK_NAME_S")); // �Ȗڗ���
		bean.setNEW_ACC_DR_2_HKM_NAME_S(rs.getString("NEW_ACC_DR_2_HKM_NAME_S")); // �⏕�Ȗڗ���
		bean.setNEW_ACC_DR_2_UKM_NAME_S(rs.getString("NEW_ACC_DR_2_UKM_NAME_S")); // ����Ȗڗ���
		bean.setNEW_ACC_DR_2_ZEI_NAME_S(rs.getString("NEW_ACC_DR_2_ZEI_NAME_S"));

		bean.setROW_OUTLINE(rs.getString("ROW_OUTLINE")); // REMARKS

		bean.setVCVR_ITEM_CODE(rs.getString("VCVR_ITEM_CODE")); // VC/VR�ϊ��̕ϊ���A�C�e���R�[�h

		bean.setSTR_DATE(rs.getTimestamp("STR_DATE")); // �J�n�N����
		bean.setEND_DATE(rs.getTimestamp("END_DATE")); // �I���N����
		bean.setINP_DATE(rs.getTimestamp("INP_DATE"));
		bean.setUPD_DATE(rs.getTimestamp("UPD_DATE"));
		bean.setPRG_ID(rs.getString("PRG_ID"));
		bean.setUSR_ID(rs.getString("USR_ID"));

		return bean;
	}

	/**
	 * @return OPItem
	 */
	protected OPItem createOPItem() {
		return new OPItem();
	}

	/**
	 * SQL�p
	 */
	protected class VCreator extends SQLCreator {

		/**
		 * �R���X�g���N�^�[
		 */
		public VCreator() {
			crlf = " ";
		}
	}

	@Override
	public Boolean isExistControlType(String companyCode, String itemControlType, String subItemControlType)
		throws TException {

		OPItemSearchCondition condition = new OPItemSearchCondition();
		condition.setCompanyCode(companyCode);
		condition.setItemControlDivision(OPItemControlDivision.get(itemControlType));
		if (OPItemSubDivision.get(subItemControlType) != null) {
			condition.setItemSubDivision(OPItemSubDivision.get(subItemControlType));
		} else {
			condition.setBrkrTypeCode(subItemControlType);
		}

		List<OPItem> list = get(condition);
		if (list.isEmpty()) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isExist(OPItem bean) throws TException {
		OPItem item = getItem(bean.getKAI_CODE(), bean.getITEM_CODE());
		return (item == null) ? false : true;
	}

	/**
	 * OP�A�C�e������Ԃ� ��null���Ԃ�ꍇ�ɗv����
	 * 
	 * @throws TException
	 */
	public OPItem getItem(String company, String item) throws TException {

		if (Util.isNullOrEmpty(company) || Util.isNullOrEmpty(item)) {
			return null;
		}

		OPItemSearchCondition condition = new OPItemSearchCondition();
		condition.setCompanyCode(company);
		condition.setCode(item);

		List<OPItem> list = get(condition);

		if (list.isEmpty()) {
			return null;
		}

		return list.get(0);
	}

	/**
	 * �w������ɊY������A�C�e����Ԃ�
	 * 
	 * @param condition �A�C�e�����
	 * @return �A�C�e���R�[�h��Ԃ��܂�
	 * @throws TException
	 */
	public OPItem getCode(OPItemSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		OPItem bean = new OPItem();
		try {
			VCreator sql = new VCreator();
			sql.add(" SELECT ITEM_CODE ");
			sql.add(" FROM OP_ITEM_MST ");
			sql.add(" WHERE KAI_CODE  = ?", condition.getCompanyCode());
			sql.add(" AND   ITEM_CODE = ?", condition.getCode());

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				bean.setCode(rs.getString("ITEM_CODE"));
			}
			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return bean;
	}

	/**
	 * @see jp.co.ais.trans2.op.model.item.OPItemManager#entry(jp.co.ais.trans2.op.model.item.OPItem,
	 *      jp.co.ais.trans2.op.model.item.OPItem)
	 */
	@Override
	public OPItem entry(OPItem bean, OPItem oldBean) throws TException {
		// New or copy
		if (oldBean == null) {
			bean.setINP_DATE(getNow());
			bean.setUPD_DATE(null);
			// Modify
		} else {
			bean.setINP_DATE(oldBean.getINP_DATE());
			bean.setUPD_DATE(getNow());
		}

		bean.setPRG_ID(getProgramCode());
		bean.setUSR_ID(getUserCode());

		if (!isExist(bean)) {
			insert(bean);
		} else {
			// Mode MODIFY then have update
			if (oldBean != null) {
				modify(bean);
			}
			// Mode NEW/COPY if found exist record then no update.
			else {
				return null;
			}
		}

		return getItem(bean.getKAI_CODE(), bean.getITEM_CODE());
	}

	/**
	 * @see jp.co.ais.trans2.op.model.item.OPItemManager#insert(jp.co.ais.trans2.op.model.item.OPItem)
	 */
	@Override
	public void insert(OPItem bean) throws TException {
		Connection conn = DBUtil.getConnection();

		try {

			List<OPItem> list = new ArrayList<OPItem>();
			list.add(bean);

			insert(conn, list);

		} catch (Exception ex) {
			throw new TException(ex);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * @param conn
	 * @param list
	 * @throws Exception
	 */
	public void insert(Connection conn, List<OPItem> list) throws Exception {
		String tableName = "OP_ITEM_MST";

		for (OPItem bean : list) {
			VCreator sql = new VCreator();
			sql.add(" INSERT INTO  " + tableName);
			sql.add(" ( ");
			sql.add(" KAI_CODE ");
			sql.add(" , ITEM_CODE  ");
			sql.add(" , ITEM_NAME ");
			sql.add(" , ITEM_NAME_S ");
			sql.add(" , ITEM_NAME_K ");
			sql.add(" , ITEM_INV_NAME ");
			sql.add(" , CTRT_TYPE ");
			sql.add(" , SA_KBN ");
			sql.add(" , DC_KBN ");
			sql.add(" , ITEM_CTL_KBN ");
			sql.add(" , ITEM_SUB_KBN ");
			sql.add(" , ADCOM_KBN ");
			sql.add(" , BRKR_KBN ");
			sql.add(" , OCCUR_BASE_KBN ");
			if (isUseOwnrShip) {
				sql.add(" , OWNR_SHIP_CODE ");
			}
			sql.add(" , KMK_CODE ");
			sql.add(" , HKM_CODE ");
			sql.add(" , UKM_CODE ");
			sql.add(" , ZEI_CODE ");
			sql.add(" , AT_KMK_CODE ");
			sql.add(" , AT_HKM_CODE ");
			sql.add(" , AT_UKM_CODE ");
			sql.add(" , AT_ZEI_CODE ");
			sql.add(" , KURI_KMK_CODE ");
			sql.add(" , KURI_HKM_CODE ");
			sql.add(" , KURI_UKM_CODE ");
			sql.add(" , KURI_ZEI_CODE ");
			sql.add(" , AT_KURI_KMK_CODE ");
			sql.add(" , AT_KURI_HKM_CODE ");
			sql.add(" , AT_KURI_UKM_CODE ");
			sql.add(" , AT_KURI_ZEI_CODE ");
			sql.add(" , EST_KMK_CODE ");
			sql.add(" , EST_HKM_CODE ");
			sql.add(" , EST_UKM_CODE ");
			sql.add(" , EST_ZEI_CODE ");
			sql.add(" , AT_EST_KMK_CODE ");
			sql.add(" , AT_EST_HKM_CODE ");
			sql.add(" , AT_EST_UKM_CODE ");
			sql.add(" , AT_EST_ZEI_CODE ");
			sql.add(" , NEW_ACC_DR_KMK_CODE "); // �V��v�
			sql.add(" , NEW_ACC_DR_HKM_CODE ");
			sql.add(" , NEW_ACC_DR_UKM_CODE ");
			sql.add(" , NEW_ACC_DR_ZEI_CODE ");
			sql.add(" , NEW_ACC_CR_KMK_CODE ");
			sql.add(" , NEW_ACC_CR_HKM_CODE ");
			sql.add(" , NEW_ACC_CR_UKM_CODE ");
			sql.add(" , NEW_ACC_CR_ZEI_CODE ");
			sql.add(" , NEW_ACC_DR_2_KMK_CODE ");
			sql.add(" , NEW_ACC_DR_2_HKM_CODE ");
			sql.add(" , NEW_ACC_DR_2_UKM_CODE ");
			sql.add(" , NEW_ACC_DR_2_ZEI_CODE ");
			sql.add(" , ROW_OUTLINE ");
			sql.add(" , VCVR_ITEM_CODE "); // VC/VR�ϊ��̕ϊ���A�C�e���R�[�h
			sql.add(" , STR_DATE ");
			sql.add(" , END_DATE ");
			sql.add(" , INP_DATE ");
			sql.add(" , UPD_DATE ");
			sql.add(" , PRG_ID ");
			sql.add(" , USR_ID ");
			sql.add(" ) VALUES (");
			sql.add(" ? ", bean.getKAI_CODE());
			sql.add(" ,? ", bean.getITEM_CODE());
			sql.add(" ,? ", bean.getITEM_NAME());
			sql.add(" ,? ", bean.getITEM_NAME_S());
			sql.add(" ,? ", bean.getITEM_NAME_K());
			sql.add(" ,? ", bean.getITEM_INV_NAME());
			sql.add(" ,? ", bean.getCTRT_TYPE());
			sql.add(" ,? ", bean.getSA_KBN());
			sql.add(" ,? ", bean.getDC_KBN());
			sql.add(" ,? ", bean.getITEM_CTL_KBN());
			sql.add(" ,? ", bean.getITEM_SUB_KBN());
			sql.add(" ,? ", bean.getADCOM_KBN());
			sql.add(" ,? ", bean.getBRKR_KBN());
			sql.add(" ,? ", bean.getOCCUR_BASE_KBN());
			if (isUseOwnrShip) {
				sql.add(" ,? ", bean.getOWNR_SHIP_CODE());
			}
			sql.add(" ,? ", bean.getKMK_CODE());
			sql.add(" ,? ", bean.getHKM_CODE());
			sql.add(" ,? ", bean.getUKM_CODE());
			sql.add(" ,? ", bean.getZEI_CODE());
			sql.add(" ,? ", bean.getAT_KMK_CODE());
			sql.add(" ,? ", bean.getAT_HKM_CODE());
			sql.add(" ,? ", bean.getAT_UKM_CODE());
			sql.add(" ,? ", bean.getAT_ZEI_CODE());
			sql.add(" ,? ", bean.getKURI_KMK_CODE());
			sql.add(" ,? ", bean.getKURI_HKM_CODE());
			sql.add(" ,? ", bean.getKURI_UKM_CODE());
			sql.add(" ,? ", bean.getKURI_ZEI_CODE());
			sql.add(" ,? ", bean.getAT_KURI_KMK_CODE());
			sql.add(" ,? ", bean.getAT_KURI_HKM_CODE());
			sql.add(" ,? ", bean.getAT_KURI_UKM_CODE());
			sql.add(" ,? ", bean.getAT_KURI_ZEI_CODE());
			sql.add(" ,? ", bean.getEST_KMK_CODE());
			sql.add(" ,? ", bean.getEST_HKM_CODE());
			sql.add(" ,? ", bean.getEST_UKM_CODE());
			sql.add(" ,? ", bean.getEST_ZEI_CODE());
			sql.add(" ,? ", bean.getAT_EST_KMK_CODE());
			sql.add(" ,? ", bean.getAT_EST_HKM_CODE());
			sql.add(" ,? ", bean.getAT_EST_UKM_CODE());
			sql.add(" ,? ", bean.getAT_EST_ZEI_CODE());
			sql.add(" ,? ", bean.getNEW_ACC_DR_KMK_CODE()); // �V��v�
			sql.add(" ,? ", bean.getNEW_ACC_DR_HKM_CODE());
			sql.add(" ,? ", bean.getNEW_ACC_DR_UKM_CODE());
			sql.add(" ,? ", bean.getNEW_ACC_DR_ZEI_CODE());
			sql.add(" ,? ", bean.getNEW_ACC_CR_KMK_CODE());
			sql.add(" ,? ", bean.getNEW_ACC_CR_HKM_CODE());
			sql.add(" ,? ", bean.getNEW_ACC_CR_UKM_CODE());
			sql.add(" ,? ", bean.getNEW_ACC_CR_ZEI_CODE());
			sql.add(" ,? ", bean.getNEW_ACC_DR_2_KMK_CODE());
			sql.add(" ,? ", bean.getNEW_ACC_DR_2_HKM_CODE());
			sql.add(" ,? ", bean.getNEW_ACC_DR_2_UKM_CODE());
			sql.add(" ,? ", bean.getNEW_ACC_DR_2_ZEI_CODE());
			sql.add(" ,? ", bean.getROW_OUTLINE());
			sql.add(" ,? ", bean.getVCVR_ITEM_CODE()); // VC/VR�ϊ��̕ϊ���A�C�e���R�[�h
			sql.adt(" ,? ", bean.getSTR_DATE());
			sql.adt(" ,? ", bean.getEND_DATE());
			sql.adt(" ,? ", bean.getINP_DATE());
			sql.adt(" ,? ", bean.getUPD_DATE());
			sql.add(" ,? ", bean.getPRG_ID());
			sql.add(" ,? ", bean.getUSR_ID());
			sql.add(" ) ");

			DBUtil.execute(conn, sql);
		}

	}

	/**
	 * �A�C�e�������C������B
	 * 
	 * @param item
	 * @throws TException
	 */
	public void modify(OPItem item) throws TException {
		Connection conn = DBUtil.getConnection();

		try {
			String tableName = "OP_ITEM_MST";

			VCreator sql = new VCreator();
			sql.add(" UPDATE  " + tableName);
			sql.add(" SET ");
			sql.add(" KAI_CODE = ? ", item.getKAI_CODE());
			sql.add(" , ITEM_CODE = ? ", item.getITEM_CODE());
			sql.add(" , ITEM_NAME = ? ", item.getITEM_NAME());
			sql.add(" , ITEM_NAME_S = ? ", item.getITEM_NAME_S());
			sql.add(" , ITEM_NAME_K = ?  ", item.getITEM_NAME_K());
			sql.add(" , ITEM_INV_NAME = ?  ", item.getITEM_INV_NAME());
			sql.add(" , CTRT_TYPE = ? ", item.getCTRT_TYPE());
			sql.add(" , SA_KBN = ? ", item.getSA_KBN());
			sql.add(" , DC_KBN = ? ", item.getDC_KBN());
			sql.add(" , ITEM_CTL_KBN = ? ", item.getITEM_CTL_KBN());
			sql.add(" , ITEM_SUB_KBN = ? ", item.getITEM_SUB_KBN());
			sql.add(" , ADCOM_KBN = ? ", item.getADCOM_KBN());
			sql.add(" , BRKR_KBN = ? ", item.getBRKR_KBN());
			sql.add(" , OCCUR_BASE_KBN = ? ", item.getOCCUR_BASE_KBN());
			if (isUseOwnrShip) {
				sql.add(" , OWNR_SHIP_CODE = ? ", item.getOWNR_SHIP_CODE());
			}
			sql.add(" , KMK_CODE = ? ", item.getKMK_CODE());
			sql.add(" , HKM_CODE = ? ", item.getHKM_CODE());
			sql.add(" , UKM_CODE = ? ", item.getUKM_CODE());
			sql.add(" , ZEI_CODE = ? ", item.getZEI_CODE());
			sql.add(" , AT_KMK_CODE = ? ", item.getAT_KMK_CODE());
			sql.add(" , AT_HKM_CODE = ? ", item.getAT_HKM_CODE());
			sql.add(" , AT_UKM_CODE = ? ", item.getAT_UKM_CODE());
			sql.add(" , AT_ZEI_CODE = ? ", item.getAT_ZEI_CODE());
			sql.add(" , KURI_KMK_CODE = ? ", item.getKURI_KMK_CODE());
			sql.add(" , KURI_HKM_CODE = ? ", item.getKURI_HKM_CODE());
			sql.add(" , KURI_UKM_CODE = ? ", item.getKURI_UKM_CODE());
			sql.add(" , KURI_ZEI_CODE = ? ", item.getKURI_ZEI_CODE());
			sql.add(" , AT_KURI_KMK_CODE = ? ", item.getAT_KURI_KMK_CODE());
			sql.add(" , AT_KURI_HKM_CODE = ? ", item.getAT_KURI_HKM_CODE());
			sql.add(" , AT_KURI_UKM_CODE = ? ", item.getAT_KURI_UKM_CODE());
			sql.add(" , AT_KURI_ZEI_CODE = ? ", item.getAT_KURI_ZEI_CODE());
			sql.add(" , EST_KMK_CODE = ? ", item.getEST_KMK_CODE());
			sql.add(" , EST_HKM_CODE = ? ", item.getEST_HKM_CODE());
			sql.add(" , EST_UKM_CODE = ? ", item.getEST_UKM_CODE());
			sql.add(" , EST_ZEI_CODE = ? ", item.getEST_ZEI_CODE());
			sql.add(" , AT_EST_KMK_CODE = ? ", item.getAT_EST_KMK_CODE());
			sql.add(" , AT_EST_HKM_CODE = ? ", item.getAT_EST_HKM_CODE());
			sql.add(" , AT_EST_UKM_CODE = ? ", item.getAT_EST_UKM_CODE());
			sql.add(" , AT_EST_ZEI_CODE = ? ", item.getAT_EST_ZEI_CODE());
			sql.add(" , NEW_ACC_DR_KMK_CODE = ? ", item.getNEW_ACC_DR_KMK_CODE()); // �V��v�
			sql.add(" , NEW_ACC_DR_HKM_CODE = ? ", item.getNEW_ACC_DR_HKM_CODE());
			sql.add(" , NEW_ACC_DR_UKM_CODE = ? ", item.getNEW_ACC_DR_UKM_CODE());
			sql.add(" , NEW_ACC_DR_ZEI_CODE = ? ", item.getNEW_ACC_DR_ZEI_CODE());
			sql.add(" , NEW_ACC_CR_KMK_CODE = ? ", item.getNEW_ACC_CR_KMK_CODE());
			sql.add(" , NEW_ACC_CR_HKM_CODE = ? ", item.getNEW_ACC_CR_HKM_CODE());
			sql.add(" , NEW_ACC_CR_UKM_CODE = ? ", item.getNEW_ACC_CR_UKM_CODE());
			sql.add(" , NEW_ACC_CR_ZEI_CODE = ? ", item.getNEW_ACC_CR_ZEI_CODE());
			sql.add(" , NEW_ACC_DR_2_KMK_CODE = ? ", item.getNEW_ACC_DR_2_KMK_CODE());
			sql.add(" , NEW_ACC_DR_2_HKM_CODE = ? ", item.getNEW_ACC_DR_2_HKM_CODE());
			sql.add(" , NEW_ACC_DR_2_UKM_CODE = ? ", item.getNEW_ACC_DR_2_UKM_CODE());
			sql.add(" , NEW_ACC_DR_2_ZEI_CODE = ? ", item.getNEW_ACC_DR_2_ZEI_CODE());
			sql.add(" , ROW_OUTLINE = ? ", item.getROW_OUTLINE());
			sql.add(" , VCVR_ITEM_CODE = ? ", item.getVCVR_ITEM_CODE()); // VC/VR�ϊ��̕ϊ���A�C�e���R�[�h
			sql.adt(" , STR_DATE = ? ", item.getSTR_DATE());
			sql.adt(" , END_DATE = ? ", item.getEND_DATE());
			sql.adt(" , INP_DATE = ? ", item.getINP_DATE());
			sql.adt(" , UPD_DATE = ? ", item.getUPD_DATE());
			sql.add(" , PRG_ID = ? ", item.getPRG_ID());
			sql.add(" , USR_ID = ? ", item.getUSR_ID());
			sql.add(" WHERE 1 = 1 ");
			sql.add(" AND   KAI_CODE   = ?", item.getKAI_CODE());
			sql.add(" AND  ITEM_CODE    = ?", item.getITEM_CODE());

			DBUtil.execute(conn, sql);

		} catch (Exception ex) {
			throw new TException(ex);
		} finally {
			DBUtil.close(conn);
		}

	}

	@Override
	public byte[] getExcel(OPItemSearchCondition condition) throws TException {
		condition.setShowAccountName(true);
		List<OPItem> itemLst = get(condition);
		if (itemLst == null || itemLst.isEmpty()) {
			return null;
		}

		OPItemExcel layout = new OPItemExcel(getUser().getLanguage(), getCompany());
		byte[] data = layout.getExcel(itemLst);

		return data;
	}

	/**
	 * OP�A�C�e���폜
	 * 
	 * @param item
	 * @throws TException
	 */
	public void delete(OPItem item) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			VCreator s = new VCreator();
			s.add("DELETE FROM OP_ITEM_MST");
			s.add("WHERE KAI_CODE = ?", item.getKAI_CODE());
			s.add("  AND ITEM_CODE = ?", item.getCode());

			DBUtil.execute(conn, s);

			DBUtil.close(conn);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * OP�A�C�e���폜(�Ώۖ��킪���݂��ĂȂ��ꍇ)
	 * 
	 * @param conn
	 * @throws TException
	 */
	@Deprecated
	public void delete(Connection conn) throws TException {
		VCreator s = new VCreator();
		s.add(" DELETE FROM OP_ITEM_MST o ");
		s.add(" WHERE  KAI_CODE = ?", getCompanyCode());
		s.add(" AND    ITEM_CTL_KBN IN('BEX', 'BSK', 'BKR', 'BSR', 'BSP-OW') ");
		s.add(" AND    ITEM_CODE LIKE '%#%' "); // #���������f�[�^
		s.add(" AND NOT EXISTS (SELECT 1 ");
		s.add("                 FROM   CM_BNKR_TYPE_MST b ");
		s.add("                 WHERE  b.KAI_CODE = o.KAI_CODE ");
		s.add("                 AND    b.BNKR_TYPE_CODE = o.ITEM_SUB_CODE");
		s.add("                ) ");

		DBUtil.execute(conn, s);
	}

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(OPItemSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		boolean hasDelete = false;
		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT COUNT(1) ");
			sql.add(" FROM   OP_ITEM_MST dtl ");
			sql.add(" WHERE  dtl.KAI_CODE = ? ", condition.getCompanyCode()); // ��ЃR�[�h

			// �ŏI�X�V����
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (dtl.INP_DATE <= ? ", condition.getLastUpdateDate());
				sql.adt("    OR dtl.UPD_DATE <= ? ", condition.getLastUpdateDate());
				sql.add("    OR dtl.INP_DATE IS NULL AND dtl.UPD_DATE IS NULL) ");
			}

			// �폜���聁���ݎ����Ă��錏����DB�̉ߋ��̌����ƕs��v
			hasDelete = DBUtil.selectOneInt(conn, sql.toSQL()) != condition.getNowCount();

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return hasDelete;
	}

}
