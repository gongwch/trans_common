package jp.co.ais.trans2.common.gui.ac;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.model.ui.payment.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.cargo.*;
import jp.co.ais.trans2.model.code.*;
import jp.co.ais.trans2.model.country.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.payment.*;
import jp.co.ais.trans2.model.port.*;
import jp.co.ais.trans2.model.user.*;
import jp.co.ais.trans2.model.vessel.*;

/**
 * デフォルト処理
 */
public class RefableCommitAdapter extends CommitAdapter {

	/** 対象コンポ */
	protected TReference ref;

	/**
	 * コンストラクター
	 * 
	 * @param ref
	 */
	public RefableCommitAdapter(TReference ref) {
		this.ref = ref;
	}

	@Override
	public void commit(Object value) {
		if (value != null) {
			if (value instanceof Port && ref instanceof TPortReference) {
				((TPortReference) ref).setEntity((Port) value);

			} else if (value instanceof Vessel && ref instanceof TVesselReference) {
				((TVesselReference) ref).setEntity((Vessel) value);

			} else if (value instanceof Customer && ref instanceof TCustomerReference) {
				((TCustomerReference) ref).setEntity((Customer) value);

			} else if (value instanceof Department && ref instanceof TDepartmentReference) {
				((TDepartmentReference) ref).setEntity((Department) value);

			} else if (value instanceof Employee && ref instanceof TEmployeeReference) {
				((TEmployeeReference) ref).setEntity((Employee) value);

			} else if (value instanceof User && ref instanceof TUserReference) {
				((TUserReference) ref).setEntity((User) value);

			} else if (value instanceof Currency && ref instanceof TCurrencyReference) {
				((TCurrencyReference) ref).setEntity((Currency) value);

			} else if (value instanceof OP_CODE_MST && ref instanceof TCodeReference) {
				((TCodeReference) ref).setEntity((OP_CODE_MST) value);

			} else if (value instanceof Cargo && ref instanceof TCargoReference) {
				((TCargoReference) ref).setEntity((Cargo) value);

			} else if (value instanceof Country && ref instanceof TCountryReference) {
				((TCountryReference) ref).setEntity((Country) value);

			} else if (value instanceof PaymentSetting && ref instanceof TPaymentSettingReference) {
				((TPaymentSettingReference) ref).setEntity((PaymentSetting) value);

			} else if (value instanceof PaymentMethod && ref instanceof TPaymentMethodReference) {
				((TPaymentMethodReference) ref).setEntity((PaymentMethod) value);

			} else if (value instanceof BankAccount && ref instanceof TBankAccountReference) {
				((TBankAccountReference) ref).setEntity((BankAccount) value);

			} else if (value instanceof AutoCompletable) {
				ref.code.setText(((AutoCompletable) value).getCode());
				ref.name.setText(((AutoCompletable) value).getName());

			} else if (value instanceof TReferable) {
				ref.code.setText(((TReferable) value).getCode());
				ref.name.setText(((TReferable) value).getNames());

			} else {
				if (ref.name.getAutoComplete() != null && ref.name.getAutoComplete().isAllowNotExists()) {
					ref.code.setText("");
					ref.name.setText(value.toString());
				} else {
					ref.code.setText(value.toString());
					ref.name.setText("");
				}
			}
		} else {
			ref.clear();
		}
	}
}
