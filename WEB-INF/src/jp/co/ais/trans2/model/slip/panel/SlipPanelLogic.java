package jp.co.ais.trans2.model.slip.panel;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * “`•[‰æ–ÊClassæ“¾ˆ—
 */
public abstract class SlipPanelLogic extends TModel {

	/** “`•[í•Ê */
	protected String slipType;

	/** ƒf[ƒ^‹æ•ª */
	protected String dataType;

	/**
	 * “`•[í•Ê
	 * 
	 * @param slipType “`•[í•Ê
	 */
	public void setSlipType(String slipType) {
		this.slipType = slipType;
	}

	/**
	 * “`•[í•Êæ“¾
	 * 
	 * @return “`•[í•Ê
	 * @throws TException
	 */
	public SlipType getSlipType() throws TException {
		return getSlipType(this.slipType);
	}

	/**
	 * “`•[í•Ê
	 * 
	 * @param typeNo “`•[í•Ê”Ô†
	 * @return “`•[í•Ê
	 * @throws TException
	 */
	public SlipType getSlipType(String typeNo) throws TException {
		SlipTypeManager slipTypeManager = (SlipTypeManager) getComponent(SlipTypeManager.class);
		SlipType type = slipTypeManager.get(getCompanyCode(), typeNo);

		if (type == null) {
			// “`•[í•Ê[{0}]‚ªİ’è‚³‚ê‚Ä‚¢‚Ü‚¹‚ñB
			throw new TException("I00128", typeNo);
		}

		return type;
	}

	/**
	 * ƒf[ƒ^‹æ•ª
	 * 
	 * @param dataKind ƒf[ƒ^‹æ•ª
	 */
	public void setDataType(String dataKind) {
		this.dataType = dataKind;
	}

	/**
	 * @param prgCode ƒvƒƒOƒ‰ƒ€ƒR[ƒh
	 * @return Class
	 */
	public abstract Class getSlipPanelClass(String prgCode);

}
