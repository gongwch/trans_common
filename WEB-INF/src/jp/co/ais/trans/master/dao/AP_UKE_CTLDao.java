package jp.co.ais.trans.master.dao;

import java.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 受付番号コントロールDao
 */
public interface AP_UKE_CTLDao {

	public Class BEAN = AP_UKE_CTL.class;

    public List getAllAP_UKE_CTL();
	
    //指定されたデータの取得
    public String getAP_UKE_CTLByKaicodeutkdate_ARGS = "KAI_CODE, UTK_DATE";
    public AP_UKE_CTL getAP_UKE_CTLByKaicodeutkdate(String kaiCode, Date utkDate);
	
    public void insert(AP_UKE_CTL dto);

    public void update(AP_UKE_CTL dto);

    public void delete(AP_UKE_CTL dto);


}

