package jp.co.ais.trans2.model.management;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.define.*;

/**
 * 管理会計切り口条件
 * 
 * @author AIS
 */
public class ManagementAngleSearchCondition extends TransferBase implements Cloneable {

	/** serialVersionUID */
	private static final long serialVersionUID = 2820041634952978193L;

	/** 管理切り口 */
	protected ManagementAngle managementAngle = ManagementAngle.NONE;

	/** コード開始 */
	protected String codeFrom = null;

	/** コード終了 */
	protected String codeTo = null;

	/** コード個別選択 */
	protected List<String> optionalCodes = null;

	/**
	 * クローン
	 */
	@Override
	public ManagementAngleSearchCondition clone() {
		try {
			ManagementAngleSearchCondition bean = (ManagementAngleSearchCondition) super.clone();
			if (optionalCodes != null) {
				bean.optionalCodes = new ArrayList<String>(optionalCodes);
			}
			return bean;
		} catch (CloneNotSupportedException e) {
			throw new TRuntimeException(e);
		}
	}

	public String getCodeFrom() {
		return codeFrom;
	}

	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	public String getCodeTo() {
		return codeTo;
	}

	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	public ManagementAngle getManagementAngle() {
		return managementAngle;
	}

	public void setManagementAngle(ManagementAngle managementAngle) {
		this.managementAngle = managementAngle;
	}

	public List<String> getOptionalCodes() {
		return optionalCodes;
	}

	public void setOptionalCodes(List<String> optionalCodes) {
		this.optionalCodes = optionalCodes;
	}

}
