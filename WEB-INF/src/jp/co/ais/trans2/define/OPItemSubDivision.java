package jp.co.ais.trans2.define;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * OP Item Sub Division
 */
public enum OPItemSubDivision implements TEnumRadio {

	/** FRT-FRT : FREIGHT **/
	FRT_FRT("FRT-FRT"),

	/** FRT-WAYCRG : WAYCARGO **/
	FRT_WAYCRG("FRT-WAYCRG"),

	/** FRT-IFT : FREIGHT INITIAL FREIGHT **/
	FRT_IFT("FRT-IFT"),

	/** FRT-BFT : FREIGHT BALANCE FREIGHT **/
	FRT_BFT("FRT-BFT"),

	/** FRT-EXT : FREIGHT EXTRA FREIGHT **/
	FRT_EXT("FRT-EXT"),

	/** FRT-ADF : FREIGHT ADD FREIGHT **/
	FRT_ADF("FRT-ADF"),

	/** FRT-DEML : FREIGHT DEMURRAGE(LD) **/
	FRT_DEML("FRT-DEML"),

	/** FRT-DEMD : FREIGHT DEMURRAGE(DC) **/
	FRT_DEMD("FRT-DEMD"),

	/** FRT-DEMAP : FREIGHT DEMURRAGE(AP) êœógã§í  **/
	FRT_DEMAP("FRT-DEMAP"),

	/** FRT-DESL : FREIGHT DESPATCH(LD) **/
	FRT_DESL("FRT-DESL"),

	/** FRT-DESD : FREIGHT DESPATCH(DC) **/
	FRT_DESD("FRT-DESD"),

	/** FRT-DESAP : FREIGHT DESPATCH(AP) êœógã§í  **/
	FRT_DESAP("FRT-DESAP"),

	/** FRT-BAF : FREIGHT BAF **/
	FRT_BAF("FRT-BAF"),

	/** FRT-OVR : FREIGHT OVERRAGE **/
	FRT_OVR("FRT-OVR"),

	/** FRT-DFT : FREIGHT DEAD FREIGHT **/
	FRT_DFT("FRT-DFT"),

	/** FRT-OZR : FREIGHT OTHER FREIGHT **/
	FRT_OZR("FRT-OZR"),

	/** FRT-ADC : FREIGHT ADCOM **/
	FRT_ADC("FRT-ADC"),

	/** FRT-ADCIKO : FREIGHT ADCOM(à⁄çsêÍóp) AMOUNTÇ™ÇOÇ≈ÅAAD AMOUNTÇÕì¸óÕâ¬î\ **/
	FRT_ADC_IKO("FRT-ADCIKO"),

	/** FRT-ADCADD : FREIGHT ADCOM(éËìÆí«â¡óp) **/
	FRT_ADC_ADD("FRT-ADCADD"),

	/** FRT-AR : FREIGHT AR **/
	FRT_AR("FRT-AR"),

	/** FRT-AP : FREIGHT AP **/
	FRT_AP("FRT-AP"),

	/** FRT-GLAR : FREIGHT GLAR **/
	FRT_GL_AR("FRT-GLAR"),

	/** FRT-GLAP : FREIGHT GLAP **/
	FRT_GL_AP("FRT-GLAP"),

	/** FREIGHT DEM/DES ç¬ñ± */
	FRT_D_D_AP("FRT-D-D-AP"),

	/** FREIGHT DEM/DES ç¬å† */
	FRT_D_D_AR("FRT-D-D-AR"),

	/** FRT-VRAR : FREIGHT â∫ï•â^í¿ê∏éZAR **/
	FRT_VRAR("FRT-VRAR"),

	/** FRT-VRAP : FREIGHT â∫ï•â^í¿ê∏éZAP **/
	FRT_VRAP("FRT-VRAP"),

	/** FRT-AR : FREIGHT AR **/
	FRT_AR_W("FRT-AR-W"),

	/** FRT-AP : FREIGHT AP **/
	FRT_AP_W("FRT-AP-W"),

	/** FRT-VRAR : FREIGHT â∫ï•â^í¿ê∏éZAR **/
	FRT_VRAR_W("FRT-VRAR-W"),

	/** FRT-VRAP : FREIGHT â∫ï•â^í¿ê∏éZAP **/
	FRT_VRAP_W("FRT-VRAP-W"),

	/** FRT-OWN : FREIGHT OWNER **/
	FRT_OWN("FRT-OWN"),

	/** FRT-CHR : FREIGHT CHARTERER **/
	FRT_CHR("FRT-CHR"),

	/** FRT-FRT-R : VR EXPS FREIGHT **/
	FRT_FRT_R("FRT-FRT-R"),

	/** FRT-IFT-R : VR EXPS FREIGHT INITIAL FREIGHT **/
	FRT_IFT_R("FRT-IFT-R"),

	/** FRT-BFT-R : VR EXPS FREIGHT BALANCE FREIGHT **/
	FRT_BFT_R("FRT-BFT-R"),

	/** FRT-EXT-R : VR EXPS FREIGHT EXTRA FREIGHT **/
	FRT_EXT_R("FRT-EXT-R"),

	/** FRT-ADF-R : VR EXPS FREIGHT ADD FREIGHT **/
	FRT_ADF_R("FRT-ADF-R"),

	/** FRT-DEML-R : VR EXPS FREIGHT DEMURRAGE(LD) **/
	FRT_DEML_R("FRT-DEML-R"),

	/** FRT-DEMD-R : VR EXPS FREIGHT DEMURRAGE(DC) **/
	FRT_DEMD_R("FRT-DEMD-R"),

	/** FRT-DEMAP-R : VR EXPS FREIGHT DEMURRAGE(AP) êœógã§í  **/
	FRT_DEMAP_R("FRT-DEMAPR"),

	/** FRT-DESL-R : VR EXPS FREIGHT DESPATCH(LD) **/
	FRT_DESL_R("FRT-DESL-R"),

	/** FRT-DESD-R : VR EXPS FREIGHT DESPATCH(DC) **/
	FRT_DESD_R("FRT-DESD-R"),

	/** FRT-DESAP-R : VR EXPS FREIGHT DESPATCH(AP) êœógã§í  **/
	FRT_DESAP_R("FRT-DESAPR"),

	/** FRT-BAF-R : VR EXPS FREIGHT BAF **/
	FRT_BAF_R("FRT-BAF-R"),

	/** FRT-OVR-R : VR EXPS FREIGHT OVERRAGE **/
	FRT_OVR_R("FRT-OVR-R"),

	/** FRT-DFT-R : VR EXPS FREIGHT DEAD FREIGHT **/
	FRT_DFT_R("FRT-DFT-R"),

	/** FRT-OZR-R : VR EXPS FREIGHT OTHER FREIGHT **/
	FRT_OZR_R("FRT-OZR-R"),

	/** FRT-OWN-R : VR EXPS FREIGHT LESS BROKERAGE(OWN) **/
	FRT_OWN_R("FRT-OWN-R"),

	/** FRT-CHR-R : VR EXPS FREIGHT CHARTERER **/
	FRT_CHR_R("FRT-CHR-R"),

	/** FRT-ADC-R : VR EXPS FREIGHT ADCOM **/
	FRT_ADC_R("FRT-ADC-R"),

	/** FRT-ADCIKR : VR EXPS FREIGHT ADCOM(à⁄çsêÍóp) AMOUNTÇ™ÇOÇ≈ÅAAD AMOUNTÇÕì¸óÕâ¬î\ **/
	FRT_ADC_IKO_R("FRT-ADCIKR"),

	/** FRT-ADCADD : VR EXPS FREIGHT ADCOM(éËìÆí«â¡óp) **/
	FRT_ADC_ADD_R("FRT-ADCADR"),

	/** FRT-NBF-R : VR EXPS EXPENSEÇÃEXTRA FREIGHTÇ…èWåvÇ∑ÇÈÅBSETTLEëŒè€äO **/
	FRT_NBF_R("FRT-NBF-R"),

	/** FRT-BFT-BL : LINER BASE FREIGHT */
	FRT_BFT_BL("FRT-BFT-BL"),

	/** FRT-ADC-BL : LINER ADCOM */
	FRT_ADC_BL("FRT-ADC-BL"),

	/** FRT-SCG-BL : LINER SURCHARGE */
	FRT_SCG_BL("FRT-SCG-BL"),

	/** FRT-LCG-BL : LINER LOCAL CHARGABLE ITEM */
	FRT_LCG_BL("FRT-LCG-BL"),

	/** BUNKER TYPE CODE ñ˚éÌÉ}ÉXÉ^àÀë∂ **/
	BUNKER_TYPE_CODE(""),

	/** BKR-OZR : BUNKER OTHER BUNKER EXPENSE **/
	BKR_OZR("BKR-OZR"),

	/** BKR-OLF : BUNKER OTHER BUNKER OIL FENCE **/
	BKR_OLF("BKR-OLF"),

	/** BKR-PRO : BUNKER BUNKER PROFIT **/
	BKR_PRO("BKR-PRO"),

	/** BKR-LOS : BUNKER BUNKER LOSS **/
	BKR_LOS("BKR-LOS"),

	/** BKR-OFH : BUNKER OFF HIRE BUNKER **/
	BKR_OFH("BKR-OFH"),

	/** BKR-AR : BUNKER AR **/
	BKR_AR("BKR-AR"),

	/** BKR-AP : BUNKER AP **/
	BKR_AP("BKR-AP"),

	/** BKR-BAL : BUNKER BUNKER BALANCE **/
	BKR_BAL("BKR-BAL"),

	/** ADV-ADV : ADVANCE **/
	ADV_ADV("ADV-ADV"),

	/** ADV-AP : ADVANCE AP **/
	ADV_AP("ADV-AP"),

	/** ADV-AR : ADVANCE AR **/
	ADV_AR("ADV-AR"),

	/** ADV-DA : ADVANCE(DA-DESK) **/
	ADV_DA("ADV-DA"),

	/** ADV-DA-AP : ADVANCE AP(DA-DESK) **/
	ADV_DA_AP("ADV-DA-AP"),

	/** ADV-DA-AR : ADVANCE AR(DA-DESK) **/
	ADV_DA_AR("ADV-DA-AR"),

	/** PCG-PCG : PORT CHARGE **/
	PCG_PCG("PCG-PCG"),

	/** PCG-OZR : PORT CHARGE OTHER PORT CHARGE **/
	PCG_OZR("PCG-OZR"),

	/** PCG-AP : PORT CHARGE AP **/
	PCG_AP("PCG-AP"),

	/** PCG-AR : PORT CHARGE AR **/
	PCG_AR("PCG-AR"),

	/** PCG-BAL : PORT CHARGE PORT CHARGE BALANCE **/
	PCG_BAL("PCG-BAL"),

	/** PCG-NA-AP : PORT CHARGE ì‡çq AP **/
	PCG_NA_AP("PCG-NA-AP"),

	/** PCG-NA-AR : PORT CHARGE ì‡çq AR **/
	PCG_NA_AR("PCG-NA-AR"),

	/** PCG-NA-BAL : PORT CHARGE ì‡çq PORT CHARGE BALANCE **/
	PCG_NA_BAL("PCG-NA-BAL"),

	/** PCG-AP_DA : PORT CHARGE AP (DA-Deskêÿë÷óp) **/
	PCG_AP_DA("PCG-AP-DA"),

	/** PCG-AR_DA : PORT CHARGE AR (DA-Deskêÿë÷óp) **/
	PCG_AR_DA("PCG-AR-DA"),

	/** PCG-BAL_DA : PORT CHARGE PORT CHARGE BALANCE (DA-Deskêÿë÷óp) **/
	PCG_BAL_DA("PCG-BAL-DA"),

	/** PCG-UIS : PORT CHARGE â^çqàœëı(êUë÷) **/
	PCG_UIS("PCG-UIS"),

	/** PCG-OWN : PORT CHARGE OWNERS ACCOUNT **/
	PCG_OWN("PCG-OWN"),

	/** PCG-CHR : PORT CHARGE CHARTERERS ACCOUNT **/
	PCG_CHR("PCG-CHR"),

	/** PCG-AGC : PORT CHARGE AGENCY **/
	PCG_AGC("PCG-AGC"),

	/** PCG-OWC : PORT CHARGE OWNER'S COMMISSION ACCOUNT */
	PCG_OWC("PCG-OWC"),

	/** PCG-CRG : PORT CHARGE ç`Ç…ïRÇ√Ç≠â›ï®îÔ */
	PCG_CRG("PCG-CRG"),

	/** PCG-PRV : PORT CHARGE PROV SETTLE */
	PCG_PRV("PCG-PRV"),

	/** PCG-PRV : PORT CHARGE PROV âºï•ã‡ */
	PCG_TMP("PCG-TMP"),

	/** PCG-BLANK : PORT CHARGE ãÛçsï\é¶óp */
	PCG_BLANK("PCG-BLANK"),

	/** BRO-BRO : BROKERAGE **/
	BRO_BRO("BRO-BRO"),

	/** BRO-ACC : BROKERAGE BROKERS ACCOUNT **/
	BRO_ACC("BRO-ACC"),

	/** BRO-AP : BROKERAGE AP **/
	BRO_AP("BRO-AP"),

	/** BRO-AP : BROKERAGE AR **/
	BRO_AR("BRO-AR"),

	/** BRO-BAL : BROKERAGE BAL **/
	BRO_BAL("BRO-BAL"),

	/** OTHER EXPENSE OEX_OEX **/
	OEX_OEX("OEX-OEX"),

	/** OTHER EXPENSE OEX_CHARTERER **/
	OEX_CHR("OEX-CHR"),

	/** OTHER EXPENSE OEX_OWNER **/
	OEX_OWN("OEX-OWN"),

	/** OTHER EXPENSE OEX_UIS â^çqàœëı(êUë÷) **/
	OEX_UIS("OEX-UIS"),

	/** OEX-AP : OTHER EXPENSE AP **/
	OEX_AP("OEX-AP"),

	/** OEX-AR : OTHER EXPENSE AR **/
	OEX_AR("OEX-AR"),

	/** OEX-OWC : OTHER EXPENSE OWNER'S COMMISSIONóLACCOUNT */
	OEX_OWC("OEX-OWC"),

	/** OEX-BAL : OTHER EXPENSE BAL **/
	OEX_BAL("OEX-BAL"),

	/** OEX-EAP : OTHER EXPENSE EST AP **/
	OEX_EAP("OEX-EAP"),

	/** OEX-EAR : OTHER EXPENSE EST AR **/
	OEX_EAR("OEX-EAR"),

	/** HIR-HIR : HIRE **/
	HIR_HIR("HIR-HIR"),

	/** HIR-BBCHIRE */
	HIR_BBCHIR("HIR-BBCHIR"),

	/** HIR-SM FEE */
	HIR_SMFEE("HIR-SMFEE"),

	/** HIR-OFH : HIRE OFF HIRE **/
	HIR_OFH("HIR-OFH"),

	/** HIR-ORV : HIRE OTHER REVENUE **/
	HIR_ORV("HIR-ORV"),

	/** HIR-BLB : HIRE OTHER REVENUE(BALLAST BOUNUS) **/
	HIR_BLB("HIR-BLB"),

	/** HIR-OEX : HIRE OTHER EXPENSE **/
	HIR_OEX("HIR-OEX"),

	/** HIR-AR : HIRE AR **/
	HIR_AR("HIR-AR"),

	/** HIR-AP : HIRE AP **/
	HIR_AP("HIR-AP"),

	/** HIR-TRP-AR : HIRE íZä˙ AR(TC-INóp) **/
	HIR_TRP_AR("HIR-TRP-AR"),

	/** HIR-TRP-AP : HIRE íZä˙ AP(TC-INóp) **/
	HIR_TRP_AP("HIR-TRP-AP"),

	/** HIR-ADJ : HIRE ADJUSTMENT **/
	HIR_ADJ("HIR-ADJ"),

	/** HIR-ADC : HIRE ADCOM **/
	HIR_ADC("HIR-ADC"),

	/** HIR-CVE : HIRE CVE **/
	HIR_CVE("HIR-CVE"),

	/** HIR-BRO : HIRE BROKERAGE */
	HIR_BRO("HIR-BRO"),

	/** HIR-OWN : HIRE OWNER **/
	HIR_OWN("HIR-OWN"),

	/** HIR-CHR : HIRE CHARTERER */
	HIR_CHR("HIR-CHR"),

	/** HIR-OWC : HIRE OWNER'S COMMISSIONóLACCOUNT */
	HIR_OWC("HIR-OWC"),

	/** HIRE-OAC : HIRE OWNER'S ACCOUNT COMMISSION */
	HIR_OAC("HIR-OAC"),

	/** HIR-GNS : HIREå∏ëπéÊïˆ */
	HIR_GNS("HIR-GNS"),

	/** HIR-NDS : HIREèÊèoîÔóp */
	HIR_NDS("HIR-NDS"),

	/** HIR-KOG : HIREí«â¡çHéñîÔ */
	HIR_KOG("HIR-KOG"),

	/** HIR-GNSHIR : HIRE GNSÇÃëäéËÉAÉCÉeÉÄ */
	HIR_GNSHIR("HIR-GNSHIR"),

	/** HIR-NDSHIR : HIRE NDSÇÃëäéËÉAÉCÉeÉÄ */
	HIR_NDSHIR("HIR-NDSHIR"),

	/** HIR-KOGHIR : HIRE KOGÇÃëäéËÉAÉCÉeÉÄ */
	HIR_KOGHIR("HIR-KOGHIR"),

	/** BKR-OWR : BUNKER SUPPLY OWNER */
	BKR_OWR("BKR-OWR"),

	/** OPF-OPF : â^çqàœëıóø */
	OPF_OPF("OPF-OPF"),
	
	/** OSHIDE : â^çqàœëıêøãÅóp */
	OSHIDE("OSHIDE");

	/** íl */
	public String value;

	/**
	 * ÉRÉìÉXÉgÉâÉNÉ^Å[
	 * 
	 * @param value íl
	 */
	OPItemSubDivision(String value) {
		this.value = value;
	}

	/**
	 * ñºèÃéÊìæ
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * ñºèÃÇï‘Ç∑
	 * 
	 * @param type
	 * @return ñºèÃ
	 */
	public static String getName(OPItemSubDivision type) {
		if (type == null) {
			return "";
		}

		return type.value;
	}

	/**
	 * EnumÇï‘Ç∑
	 * 
	 * @param value
	 * @return Enum
	 */
	public static OPItemSubDivision get(String value) {
		for (OPItemSubDivision em : values()) {
			if (em.value.equals(value)) {
				return em;
			}
		}

		return null;
	}

	/**
	 * @param value
	 * @return true:â∫ï•Ç¢â^í¿ópSUBãÊï™
	 */
	public static boolean isRelectSubDivision(String value) {
		if (Util.isNullOrEmpty(value)) {
			return false;
		}

		return value.endsWith("-R") || FRT_DESAP_R.value.equals(value) || FRT_DEMAP_R.value.equals(value)
			|| FRT_ADC_IKO_R.value.equals(value) || FRT_ADC_ADD_R.value.equals(value);
	}
}
