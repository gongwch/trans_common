package jp.co.ais.trans2.model.department;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.common.model.ui.*;

/**
 * 部門組織階層Entity
 * 
 * @author AIS
 */
public class DepartmentOrganization extends TransferBase {

	/** 会社コード */
	protected String companyCode;

	/** 組織コード */
	protected String code;

	/** 組織名称 */
	protected String name = null;

	/** 部門コード */
	protected String depCode;

	/** 部門名称 */
	protected String depName;

	/** 部門略称 */
	protected String depNames;

	/** レベル */
	protected int level;

	/** レベル０ */
	protected String level0;

	/** レベル０名称 */
	protected String level0Name;

	/** レベル１ */
	protected String level1;

	/** レベル１名称 */
	protected String level1Name;

	/** レベル２ */
	protected String level2;

	/** レベル２名称 */
	protected String level2Name;

	/** レベル３ */
	protected String level3;

	/** レベル３名称 */
	protected String level3Name;

	/** レベル４ */
	protected String level4;

	/** レベル４名称 */
	protected String level4Name;

	/** レベル５ */
	protected String level5;

	/** レベル５名称 */
	protected String level5Name;

	/** レベル６ */
	protected String level6;

	/** レベル６名称 */
	protected String level6Name;

	/** レベル７ */
	protected String level7;

	/** レベル７名称 */
	protected String level7Name;

	/** レベル８ */
	protected String level8;

	/** レベル８名称 */
	protected String level8Name;

	/** レベル９ */
	protected String level9;

	/** レベル９名称 */
	protected String level9Name;

	/** 登録日付 */
	protected Date inpDate;

	/** 組織レベル0名称 */
	protected String DPK_LVL_0_NAME = null;

	/** 組織レベル1名称 */
	protected String DPK_LVL_1_NAME = null;

	/** 組織レベル2名称 */
	protected String DPK_LVL_2_NAME = null;

	/** 組織レベル3名称 */
	protected String DPK_LVL_3_NAME = null;

	/** 組織レベル4名称 */
	protected String DPK_LVL_4_NAME = null;

	/** 組織レベル5名称 */
	protected String DPK_LVL_5_NAME = null;

	/** 組織レベル6名称 */
	protected String DPK_LVL_6_NAME = null;

	/** 組織レベル7名称 */
	protected String DPK_LVL_7_NAME = null;

	/** 組織レベル8名称 */
	protected String DPK_LVL_8_NAME = null;

	/** 組織レベル9名称 */
	protected String DPK_LVL_9_NAME = null;

	/**
	 * 会社コードを戻します。
	 * 
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コードを設定します。
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 組織コードを戻します。
	 * 
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 組織コードを設定します。
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 組織名称の取得
	 * 
	 * @return name 組織名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 組織名称の設定
	 * 
	 * @param name 組織名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 部門コードを戻します。
	 * 
	 * @return depCode
	 */
	public String getDepCode() {
		return depCode;
	}

	/**
	 * 部門コードを設定します。
	 * 
	 * @param depCode
	 */
	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	/**
	 * 部門名称を戻します。
	 * 
	 * @return depName
	 */
	public String getDepName() {
		return depName;
	}

	/**
	 * 部門名称を設定します。
	 * 
	 * @param depName
	 */
	public void setDepName(String depName) {
		this.depName = depName;
	}

	/**
	 * 部門略称を戻します。
	 * 
	 * @return depNames
	 */
	public String getDepNames() {
		return depNames;
	}

	/**
	 * 部門略称を設定します。
	 * 
	 * @param depNames
	 */
	public void setDepNames(String depNames) {
		this.depNames = depNames;
	}

	/**
	 * レベルを戻します。
	 * 
	 * @return level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * レベルを設定します。
	 * 
	 * @param level
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * レベル０を戻します。
	 * 
	 * @return level0
	 */
	public String getLevel0() {
		return level0;
	}

	/**
	 * レベル０を設定します。
	 * 
	 * @param level0
	 */
	public void setLevel0(String level0) {
		this.level0 = level0;
	}

	/**
	 * レベル０名称を戻します。
	 * 
	 * @return level0Name
	 */
	public String getLevel0Name() {
		return level0Name;
	}

	/**
	 * レベル０名称を設定します。
	 * 
	 * @param level0Name
	 */
	public void setLevel0Name(String level0Name) {
		this.level0Name = level0Name;
	}

	/**
	 * レベル１を戻します。
	 * 
	 * @return level1
	 */
	public String getLevel1() {
		return level1;
	}

	/**
	 * レベル１を設定します。
	 * 
	 * @param level1
	 */
	public void setLevel1(String level1) {
		this.level1 = level1;
	}

	/**
	 * レベル１名称を戻します。
	 * 
	 * @return level1Name
	 */
	public String getLevel1Name() {
		return level1Name;
	}

	/**
	 * レベル１名称を設定します。
	 * 
	 * @param level1Name
	 */
	public void setLevel1Name(String level1Name) {
		this.level1Name = level1Name;
	}

	/**
	 * レベル２を戻します。
	 * 
	 * @return level2
	 */
	public String getLevel2() {
		return level2;
	}

	/**
	 * レベル２を設定します。
	 * 
	 * @param level2
	 */
	public void setLevel2(String level2) {
		this.level2 = level2;
	}

	/**
	 * レベル２名称を戻します。
	 * 
	 * @return level2Name
	 */
	public String getLevel2Name() {
		return level2Name;
	}

	/**
	 * レベル２名称を設定します。
	 * 
	 * @param level2Name
	 */
	public void setLevel2Name(String level2Name) {
		this.level2Name = level2Name;
	}

	/**
	 * レベル３を戻します。
	 * 
	 * @return level3
	 */
	public String getLevel3() {
		return level3;
	}

	/**
	 * レベル３を設定します。
	 * 
	 * @param level3
	 */
	public void setLevel3(String level3) {
		this.level3 = level3;
	}

	/**
	 * レベル３名称を戻します。
	 * 
	 * @return level3Name
	 */
	public String getLevel3Name() {
		return level3Name;
	}

	/**
	 * レベル３名称を設定します。
	 * 
	 * @param level3Name
	 */
	public void setLevel3Name(String level3Name) {
		this.level3Name = level3Name;
	}

	/**
	 * レベル４を戻します。
	 * 
	 * @return level4
	 */
	public String getLevel4() {
		return level4;
	}

	/**
	 * レベル４を設定します。
	 * 
	 * @param level4
	 */
	public void setLevel4(String level4) {
		this.level4 = level4;
	}

	/**
	 * レベル４名称を戻します。
	 * 
	 * @return level4Name
	 */
	public String getLevel4Name() {
		return level4Name;
	}

	/**
	 * レベル４名称を設定します。
	 * 
	 * @param level4Name
	 */
	public void setLevel4Name(String level4Name) {
		this.level4Name = level4Name;
	}

	/**
	 * レベル５を戻します。
	 * 
	 * @return level5
	 */
	public String getLevel5() {
		return level5;
	}

	/**
	 * レベル５を設定します。
	 * 
	 * @param level5
	 */
	public void setLevel5(String level5) {
		this.level5 = level5;
	}

	/**
	 * レベル５名称を戻します。
	 * 
	 * @return level5Name
	 */
	public String getLevel5Name() {
		return level5Name;
	}

	/**
	 * レベル５名称を設定します。
	 * 
	 * @param level5Name
	 */
	public void setLevel5Name(String level5Name) {
		this.level5Name = level5Name;
	}

	/**
	 * レベル６を戻します。
	 * 
	 * @return level6
	 */
	public String getLevel6() {
		return level6;
	}

	/**
	 * レベル６を設定します。
	 * 
	 * @param level6
	 */
	public void setLevel6(String level6) {
		this.level6 = level6;
	}

	/**
	 * レベル６名称を戻します。
	 * 
	 * @return level6Name
	 */
	public String getLevel6Name() {
		return level6Name;
	}

	/**
	 * レベル６名称を設定します。
	 * 
	 * @param level6Name
	 */
	public void setLevel6Name(String level6Name) {
		this.level6Name = level6Name;
	}

	/**
	 * レベル７を戻します。
	 * 
	 * @return level7
	 */
	public String getLevel7() {
		return level7;
	}

	/**
	 * レベル７を設定します。
	 * 
	 * @param level7
	 */
	public void setLevel7(String level7) {
		this.level7 = level7;
	}

	/**
	 * レベル７名称を戻します。
	 * 
	 * @return level7Name
	 */
	public String getLevel7Name() {
		return level7Name;
	}

	/**
	 * レベル７名称を設定します。
	 * 
	 * @param level7Name
	 */
	public void setLevel7Name(String level7Name) {
		this.level7Name = level7Name;
	}

	/**
	 * レベル８を戻します。
	 * 
	 * @return level8
	 */
	public String getLevel8() {
		return level8;
	}

	/**
	 * レベル８を設定します。
	 * 
	 * @param level8
	 */
	public void setLevel8(String level8) {
		this.level8 = level8;
	}

	/**
	 * レベル８名称を戻します。
	 * 
	 * @return level8Name
	 */
	public String getLevel8Name() {
		return level8Name;
	}

	/**
	 * レベル８名称を設定します。
	 * 
	 * @param level8Name
	 */
	public void setLevel8Name(String level8Name) {
		this.level8Name = level8Name;
	}

	/**
	 * レベル９を戻します。
	 * 
	 * @return level9
	 */
	public String getLevel9() {
		return level9;
	}

	/**
	 * レベル９を設定します。
	 * 
	 * @param level9
	 */
	public void setLevel9(String level9) {
		this.level9 = level9;
	}

	/**
	 * レベル９名称を戻します。
	 * 
	 * @return level9Name
	 */
	public String getLevel9Name() {
		return level9Name;
	}

	/**
	 * レベル９名称を設定します。
	 * 
	 * @param level9Name
	 */
	public void setLevel9Name(String level9Name) {
		this.level9Name = level9Name;
	}

	/**
	 * 登録日付を戻します。
	 * 
	 * @return inpDate
	 */
	public Date getInpDate() {
		return inpDate;
	}

	/**
	 * * 登録日付を設定します。
	 * 
	 * @param inpDate
	 */
	public void setInpDate(Date inpDate) {
		this.inpDate = inpDate;
	}

	/**
	 * レベル0名称の取得
	 * 
	 * @return DPK_LVL_0_NAME レベル0名称
	 */
	public String getDPK_LVL_0_NAME() {
		return DPK_LVL_0_NAME;
	}

	/**
	 * レベル0名称の設定
	 * 
	 * @param DPK_LVL_0_NAME レベル0名称
	 */
	public void setDPK_LVL_0_NAME(String DPK_LVL_0_NAME) {
		this.DPK_LVL_0_NAME = DPK_LVL_0_NAME;
	}

	/**
	 * 組織レベル1名称の取得
	 * 
	 * @return DPK_LVL_1_NAME 組織レベル1名称
	 */
	public String getDPK_LVL_1_NAME() {
		return DPK_LVL_1_NAME;
	}

	/**
	 * 組織レベル1名称の設定
	 * 
	 * @param DPK_LVL_1_NAME 組織レベル1名称
	 */
	public void setDPK_LVL_1_NAME(String DPK_LVL_1_NAME) {
		this.DPK_LVL_1_NAME = DPK_LVL_1_NAME;
	}

	/**
	 * 組織レベル2名称の取得
	 * 
	 * @return DPK_LVL_2_NAME 組織レベル2名称
	 */
	public String getDPK_LVL_2_NAME() {
		return DPK_LVL_2_NAME;
	}

	/**
	 * 組織レベル2名称の設定
	 * 
	 * @param DPK_LVL_2_NAME 組織レベル2名称
	 */
	public void setDPK_LVL_2_NAME(String DPK_LVL_2_NAME) {
		this.DPK_LVL_2_NAME = DPK_LVL_2_NAME;
	}

	/**
	 * 組織レベル3名称の取得
	 * 
	 * @return DPK_LVL_3_NAME 組織レベル3名称
	 */
	public String getDPK_LVL_3_NAME() {
		return DPK_LVL_3_NAME;
	}

	/**
	 * 組織レベル3名称の設定
	 * 
	 * @param DPK_LVL_3_NAME 組織レベル3名称
	 */
	public void setDPK_LVL_3_NAME(String DPK_LVL_3_NAME) {
		this.DPK_LVL_3_NAME = DPK_LVL_3_NAME;
	}

	/**
	 * 組織レベル4名称の取得
	 * 
	 * @return DPK_LVL_4_NAME 組織レベル4名称
	 */
	public String getDPK_LVL_4_NAME() {
		return DPK_LVL_4_NAME;
	}

	/**
	 * 組織レベル4名称の設定
	 * 
	 * @param DPK_LVL_4_NAME 組織レベル4名称
	 */
	public void setDPK_LVL_4_NAME(String DPK_LVL_4_NAME) {
		this.DPK_LVL_4_NAME = DPK_LVL_4_NAME;
	}

	/**
	 * 組織レベル5名称の取得
	 * 
	 * @return DPK_LVL_5_NAME 組織レベル5名称
	 */
	public String getDPK_LVL_5_NAME() {
		return DPK_LVL_5_NAME;
	}

	/**
	 * 組織レベル5名称の設定
	 * 
	 * @param DPK_LVL_5_NAME 組織レベル5名称
	 */
	public void setDPK_LVL_5_NAME(String DPK_LVL_5_NAME) {
		this.DPK_LVL_5_NAME = DPK_LVL_5_NAME;
	}

	/**
	 * 組織レベル6名称の取得
	 * 
	 * @return DPK_LVL_6_NAME 組織レベル6名称
	 */
	public String getDPK_LVL_6_NAME() {
		return DPK_LVL_6_NAME;
	}

	/**
	 * 組織レベル6名称の設定
	 * 
	 * @param DPK_LVL_6_NAME 組織レベル6名称
	 */
	public void setDPK_LVL_6_NAME(String DPK_LVL_6_NAME) {
		this.DPK_LVL_6_NAME = DPK_LVL_6_NAME;
	}

	/**
	 * 組織レベル7名称の取得
	 * 
	 * @return DPK_LVL_7_NAME 組織レベル7名称
	 */
	public String getDPK_LVL_7_NAME() {
		return DPK_LVL_7_NAME;
	}

	/**
	 * 組織レベル7名称の設定
	 * 
	 * @param DPK_LVL_7_NAME 組織レベル7名称
	 */
	public void setDPK_LVL_7_NAME(String DPK_LVL_7_NAME) {
		this.DPK_LVL_7_NAME = DPK_LVL_7_NAME;
	}

	/**
	 * 組織レベル8名称の取得
	 * 
	 * @return DPK_LVL_8_NAME 組織レベル8名称
	 */
	public String getDPK_LVL_8_NAME() {
		return DPK_LVL_8_NAME;
	}

	/**
	 * 組織レベル8名称の設定
	 * 
	 * @param DPK_LVL_8_NAME 組織レベル8名称
	 */
	public void setDPK_LVL_8_NAME(String DPK_LVL_8_NAME) {
		this.DPK_LVL_8_NAME = DPK_LVL_8_NAME;
	}

	/**
	 * 組織レベル9名称の取得
	 * 
	 * @return DPK_LVL_9_NAME 組織レベル9名称
	 */
	public String getDPK_LVL_9_NAME() {
		return DPK_LVL_9_NAME;
	}

	/**
	 * 組織レベル9名称の設定
	 * 
	 * @param DPK_LVL_9_NAME 組織レベル9名称
	 */
	public void setDPK_LVL_9_NAME(String DPK_LVL_9_NAME) {
		this.DPK_LVL_9_NAME = DPK_LVL_9_NAME;
	}

}
