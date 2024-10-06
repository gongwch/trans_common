package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.util.*;

/**
 * �ʉ݊��Z�t�B�[���h�R���g���[��
 */
public class TCurrencyExchangeFieldCtrl extends TAppletClientBase {

	/** �����T�[�u���b�g */
	protected static final String TARGET_SERVLET = "InformationServlet";

	/** �t�B�[���h */
	protected TCurrencyExchangeField panel;

	/** �I��ʉ݂̏����_�ȉ����� */
	protected int digit = 0;

	/** ��ʉ݂̏����_�ȉ����� */
	protected int baseDigit = 0;

	/** ��ʉ݃R�[�h */
	protected String baseCurrencyCode = "";

	/** �ʉ݃��[�g */
	protected String currencyRate = "";

	/** �ʉ݃R�[�h�̌��� */
	protected int currencyCodeDigit = 0;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param panel �t�B�[���h
	 */
	public TCurrencyExchangeFieldCtrl(TCurrencyExchangeField panel) {
		this.panel = panel;

		// ��ʂ̏��������s��
		initCtrlValue();
	}

	/**
	 * ��ʂ̏��������s��<BR>
	 * �R���|�[�l���g�̐���A�����l�̐ݒ�
	 */
	public void initCtrlValue() {
		// ��Џ��擾
		TCompanyInfo compInfo = TClientLoginInfo.getInstance().getCompanyInfo();

		// ���O�C����Ђ̊�ʉ݂��擾����
		baseCurrencyCode = compInfo.getBaseCurrencyCode();

		// ���O�C����Ђ̊�ʉ݂̏����_�������擾����
		digit = compInfo.getBaseCurrencyDigit();
		baseDigit = compInfo.getBaseCurrencyDigit();

		// �ʉ݃R�[�h�Ɋ�ʉ݃R�[�h���Z�b�g
		panel.getCurrencyField().setValue(compInfo.getBaseCurrencyCode());

		// �������z����͉�
		panel.getNumInputAmount().setValue(NumberFormatUtil.formatNumber("0", digit));
		panel.getNumInputAmount().setEditable(true);

		// ����z�͓��͕s��
		panel.getNumBaseCurrencyAmount().setValue(NumberFormatUtil.formatNumber("0", baseDigit));
		panel.getNumBaseCurrencyAmount().setEditable(false);

		panel.getCurrencyField().setTermBasisDate(DateUtil.toYMDString(panel.getSelectedDate()));
	}

	/**
	 * �ʉ݃R�[�h���X�g�t�H�[�J�X����<BR>
	 * 
	 * @param currencyCode
	 */
	public void currencyFieldFocusLost(String currencyCode) {
		try {
			// �ʉ݃R�[�h�u�����N�̏ꍇ
			if (Util.isNullOrEmpty(currencyCode)) {
				// ���[�g���u�����N�Őݒ肷��B
				panel.getNumRate().setValue("");
				// ����z�̓u�����N�ŃZ�b�g����B
				panel.getNumBaseCurrencyAmount().setValue("");
				panel.getNumBaseCurrencyAmount().setEditable(false);

				return;
			}

			// ���t�f�[�^�擾
			String rateBaseDate = DateUtil.toYMDString(panel.getSelectedDate());

			// �ʉ݃R�[�h�ƃ��[�g����t�����ɒʉ݃f�[�^���擾����
			Map<String, String> map = findCurData(currencyCode, rateBaseDate);

			if (map.isEmpty()) {
				// ���[�g���u�����N�Őݒ肷��B
				panel.getNumRate().setValue("");
				// ����z�̓u�����N�ŃZ�b�g����B
				panel.getNumBaseCurrencyAmount().setValue("");
				panel.getNumBaseCurrencyAmount().setEditable(false);
			}

			// ����t���O
			map.put("OperationFlag", "currencyField");
			// �ʉ݃f�[�^�����Ɋe�t�B�[���h�̒l���擾
			setEachFieldByCurData(map);
			if (!"".equals(Util.avoidNull(map.get("DEC_KETA")))) {
				currencyCodeDigit = Integer.parseInt(Util.avoidNull(map.get("DEC_KETA")));
			}
		} catch (TRequestException ex) {
			errorHandler(panel);
		} catch (Exception e) {
			errorHandler(panel, e, "E00009");
		}
	}

	/**
	 * ���[�g���X�g�t�H�[�J�X<BR>
	 * 
	 * @return true:���폈��
	 */
	public boolean numRateLostFocus() {
		try {
			// �l���������͉������Ȃ�
			if (!panel.getNumRate().isValueChanged()) {
				return true;
			}

			// �ʉ݃R�[�h���擾
			String currencyCode = panel.getCurrencyField().getValue();

			// ���[�g�A�ʉ݃R�[�h���u�����N�̏ꍇ
			if (Util.isNullOrEmpty(panel.getNumRate().getValue()) || Util.isNullOrEmpty(currencyCode)) {
				// ����z�̓u�����N�ŃZ�b�g����B
				panel.getNumBaseCurrencyAmount().setValue("");
				panel.getNumBaseCurrencyAmount().setEditable(false);
				return true;
			}

			// ���t�f�[�^�擾
			String rateBaseDate = DateUtil.toYMDString(panel.getSelectedDate());

			// �ʉ݃R�[�h�ƃ��[�g����t�����ɒʉ݃f�[�^���擾����
			Map<String, String> map = findCurData(currencyCode, rateBaseDate);

			if (map.isEmpty()) {
				// ����z�̓u�����N�ŃZ�b�g����B
				panel.getNumBaseCurrencyAmount().setValue("");
				panel.getNumBaseCurrencyAmount().setEditable(false);
			}

			// �ʉ݃f�[�^�����Ɋe�t�B�[���h�̒l���擾
			setEachFieldByCurData(map);

		} catch (TRequestException ex) {
			errorHandler(panel);
		} catch (Exception e) {
			errorHandler(panel, e, "E00009");
		}
		return true;
	}

	/**
	 * ���͋��z���X�g�t�H�[�J�X���̍��v���z�v�Z
	 */
	public void numInputAmountLostFocus() {
		try {

			// �l���������̓t�H�[�}�b�g�̂ݍs��
			if (!panel.getNumInputAmount().isValueChanged()) {
				String amount = panel.getNumInputAmount().getValue();
				if (!panel.getNumInputAmount().isFormatterExist()) {
					panel.getNumInputAmount().setValue(NumberFormatUtil.formatNumber(amount, currencyCodeDigit));
				} else {
					if (Util.isNullOrEmpty(amount)) {
						amount = "0";
					}
					panel.getNumInputAmount().setValue(amount);
				}

				return;
			}

			// ���z�I�[�o�[�t���[�`�F�b�N������
			checkOverAmount();

			// �ʉ݃R�[�h
			String currencyCode = Util.avoidNull(panel.getCurrencyField().getValue());

			// ���t�f�[�^�擾
			String rateBaseDate = DateUtil.toYMDString(panel.getSelectedDate());

			// �f�[�^�擾
			Map<String, String> map = findCurData(currencyCode, rateBaseDate);

			// �ʉ݃f�[�^�����Ƀt�B�[���h�̒l���擾
			setEachFieldByCurData(map);

		} catch (TRequestException ex) {
			errorHandler(panel);
		} catch (Exception e) {
			errorHandler(panel, e, "E00009");
		}
	}

	/**
	 * ����z�t�B�[���h���X�g�t�H�[�J�X��
	 */
	public void numBaseCurrencyAmountLostFocus() {

		// ���z�I�[�o�[�t���[�`�F�b�N������
		checkOverAmount();
		// ����z���擾
		String text = panel.getNumBaseCurrencyAmount().getValue();
		// �M�݋��z���t�H�[�}�b�g����]
		if (!panel.getNumInputAmount().isFormatterExist()) {
			panel.getNumBaseCurrencyAmount().setValue(NumberFormatUtil.formatNumber(text, baseDigit));
		} else {
			if (Util.isNullOrEmpty(text)) {
				text = "0";
			}
			panel.getNumBaseCurrencyAmount().setValue(text);
		}

	}

	/**
	 * ���z�I�[�o�[�t���[�`�F�b�N������
	 */
	public void checkOverAmount() {

		// ���͋��z�I�[�o�[�t���[�`�F�b�N
		if (Util.isOverAmount(panel.getNumInputAmount().getValue())) {
			panel.getNumInputAmount().setValue(""); // ���͋��z
			panel.getNumBaseCurrencyAmount().setValue(""); // ����z
			showMessage(panel, "W00211");
			panel.getNumInputAmount().clearOldText();
			panel.getNumInputAmount().requestFocus();
			return;
		}

		// �M�݋��z�I�[�o�[�t���[�`�F�b�N
		if (Util.isOverAmount(panel.getNumBaseCurrencyAmount().getValue())) {
			panel.getNumBaseCurrencyAmount().setValue(""); // ����z
			showMessage(panel, "W00211");
			panel.getNumBaseCurrencyAmount().clearOldText();
			panel.getNumBaseCurrencyAmount().requestFocus();
			return;
		}

	}

	/**
	 * �ʉ݃f�[�^�����Ɋe�t�B�[���h�̒l���擾
	 * 
	 * @param map �ʉ݃f�[�^
	 * @throws IOException
	 * @throws TRequestException
	 * @throws NumberFormatException
	 */
	protected void setEachFieldByCurData(Map<String, String> map) throws IOException, NumberFormatException,
		TRequestException {

		// �����_�ȉ�����
		String strDecKeta = Util.avoidNull(map.get("DEC_KETA"));
		// ����t���O
		String StrOperationFlag = Util.avoidNull(map.get("OperationFlag"));

		// �����_�ȉ�����
		digit = 0;
		if (!"".equals(strDecKeta)) {
			digit = Integer.parseInt(strDecKeta);
		}

		// �����_�ȉ��������Z�b�g
		panel.setDigit(digit);

		// �ʉ݃R�[�h
		String currencyCode = Util.avoidNull(panel.getCurrencyField().getValue());
		// ���[�g����t
		String rateBaseDate = DateUtil.toYMDString(panel.getSelectedDate());

		if (!Util.isNullOrEmpty(currencyCode)) {

			// ���[�g�̎擾
			currencyRate = "";
			// ���[�g�𒼐ڕύX�����ꍇ
			if ("".equals(StrOperationFlag) && !Util.isNullOrEmpty(panel.getNumRate().getValue())) {
				currencyRate = Util.avoidNull(panel.getNumRate().getValue());
			} else {
				// ��ʉ݂̏ꍇ
				currencyRate = "1";

				if (!baseCurrencyCode.equals(panel.getCurrencyField().getValue())) {
					// ���[�g����t�̃��[�g�擾
					currencyRate = getForeignRate(currencyCode, rateBaseDate);
				}
			}

			// ���[�g�����݂��Ȃ��ꍇ
			if ("-1.0".equals(currencyRate) || Util.isNullOrEmpty(panel.getCurrencyField().getValue())) {
				// ���[�g���u�����N�Őݒ肷��
				panel.getNumRate().setValue("");
				panel.getNumRate().setEditable(true);
				// ����z�̓u�����N�ŃZ�b�g����
				panel.getNumBaseCurrencyAmount().setValue("");
				panel.getNumBaseCurrencyAmount().setEditable(false);
				return;
			}

			panel.getNumRate().setEditable(true);
			panel.getNumRate().setValue(currencyRate);
		}

		// ���͋��z�Ɗ���z�����߂�
		calcAmount();
	}

	/**
	 * ���͋��z�ƖM�݋��z�̂����߂�
	 * 
	 * @throws IOException
	 * @throws TRequestException
	 * @throws NumberFormatException
	 */
	protected void calcAmount() throws NumberFormatException, TRequestException, IOException {

		// �ʉ݃R�[�h
		String currencyCode = panel.getCurrencyField().getValue();

		if (Util.isNullOrEmpty(currencyCode) || Util.isNullOrEmpty(panel.getNumRate().getValue())) {
			panel.getNumInputAmount().setValue(
				NumberFormatUtil.formatNumber(panel.getNumInputAmount().getValue(), currencyCodeDigit));
			return;
		}

		// ���[�g����t
		String rateBaseDate = DateUtil.toYMDString(panel.getSelectedDate());

		// �������z�t�H�[�}�b�g���ăZ�b�g����
		String inputAmount = Util.avoidNull(panel.getNumInputAmount().getValue());
		if (!panel.getNumInputAmount().isFormatterExist()) {
			inputAmount = NumberFormatUtil.formatNumber(inputAmount, digit);
			panel.getNumInputAmount().setValue(inputAmount);
		} else {
			if (Util.isNullOrEmpty(inputAmount)) {
				inputAmount = "0";
			}
			panel.getNumInputAmount().setValue(inputAmount);
		}

		// ����z�ɑ΂��A���͋��z����ʉ݊��Z�����l���Z�b�g����
		String baseCurrencyAmount = inputAmount.replace(",", "");

		if (!baseCurrencyCode.equals(currencyCode)) {
			baseCurrencyAmount = convertForeign(baseCurrencyAmount, Double.valueOf(currencyRate), rateBaseDate,
				currencyCode);
		}

		panel.getNumBaseCurrencyAmount().setEditable(true);
		if (!panel.getNumInputAmount().isFormatterExist()) {
			panel.getNumBaseCurrencyAmount().setValue(NumberFormatUtil.formatNumber(baseCurrencyAmount, baseDigit));
		} else {
			if (Util.isNullOrEmpty(baseCurrencyAmount)) {
				baseCurrencyAmount = "0";
			}
			panel.getNumBaseCurrencyAmount().setValue(baseCurrencyAmount);
		}
		// ��ʉ݂Ɠ��͂��ꂽ�ʉ݃R�[�h����v����ꍇ
		if (baseCurrencyCode.equals(currencyCode)) {
			panel.getNumRate().setEditable(false);
			panel.getNumBaseCurrencyAmount().setEditable(false);
		}

		// ���z�I�[�o�[�t���[�`�F�b�N������
		checkOverAmount();
	}

	/**
	 * �ʉ݃R�[�h�ƃ��[�g����t�����ɒʉ݃f�[�^���擾����
	 * 
	 * @param curCode �ʉ݃R�[�h
	 * @param rateBaseDate ���t
	 * @return �ʉ݃f�[�^
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected Map<String, String> findCurData(String curCode, String rateBaseDate) throws TRequestException,
		IOException {
		// �ʉ݃t���O
		addSendValues("FLAG", "FindCurData");
		// �ʉ݃R�[�h
		addSendValues("CUR_CODE", curCode);
		// ���[�g����t
		addSendValues("DATE", rateBaseDate);

		// �T�[�u���b�g�̐ڑ���
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}

		// �f�[�^�擾
		return getResult();
	}

	/**
	 * �O�݂���ʉ݂Ɋ��Z����
	 * 
	 * @param money �O�݋��z
	 * @param rate ���Z���[�g
	 * @param rateBaseDate
	 * @param curCode
	 * @return ���Z���z
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected String convertForeign(String money, double rate, String rateBaseDate, String curCode)
		throws TRequestException, IOException {

		if (Util.isNullOrEmpty(rate) || baseCurrencyCode.equals(curCode)) {
			return money;
		}

		// �O�݂���ʉ݂Ɋ��Z�t���O
		addSendValues("FLAG", "convertForeign");
		// ���[�g����t
		addSendValues("rateBaseDate", rateBaseDate);
		// ��ʉݺ���
		addSendValues("baseCurCode", baseCurrencyCode);
		// �ʉ݃R�[�h
		addSendValues("foreginCurCode", curCode);
		// �O�݋��z
		addSendValues("money", String.valueOf(money));
		// ���Z���[�g
		addSendValues("rate", String.valueOf(rate));

		// ���M
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}

		// �f�[�^�擾
		Map<String, String> map = getResult();
		return Util.avoidNull(map.get("baseMoney"));
	}

	/**
	 * �O�݂̏ꍇ�A���[�g���擾����
	 * 
	 * @param curCode �ʉ݃R�[�h
	 * @param date �w����t
	 * @return ���[�g
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected String getForeignRate(String curCode, String date) throws IOException, TRequestException {
		// ���[�g�擾�t���O
		addSendValues("FLAG", "FindRate");
		// �ʉ݃R�[�h
		addSendValues("CUR_CODE", curCode);
		// �w����t
		addSendValues("OCCUR_DATE", date);

		// �T�[�u���b�g�̐ڑ���
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}

		// �f�[�^�擾
		Map<String, String> map = getResult();

		return Util.avoidNull(map.get("CUR_RATE"));
	}
}