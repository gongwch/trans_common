package jp.co.ais.trans2.model.currency;

import java.io.*;
import java.util.*;

/**
 * ���[�g�ꊇ�捞�C���^�[�t�F�C�X
 */
public interface RateConverter {

	/**
	 * ��荞�񂾃t�@�C�������[�g�̏��ɂ��ĕԂ�
	 * 
	 * @param file
	 * @param companyCode
	 * @return List<Rate>
	 * @throws TErroneousRateDataException
	 */
	public List<Rate> toRateData(File file, String companyCode) throws TErroneousRateDataException;

}