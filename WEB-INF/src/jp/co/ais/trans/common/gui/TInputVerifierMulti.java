package jp.co.ais.trans.common.gui;

import java.util.*;

import javax.swing.*;

/**
 * ����InputVerifier��o�^����ׂ̃N���X
 */
public class TInputVerifierMulti extends TInputVerifier {

	/** ����InputVerifier */
	public List<InputVerifier> verifiers = new LinkedList<InputVerifier>();

	/**
	 * InputVerifier�ǉ�
	 * 
	 * @param verifier �ǉ�Verifier
	 */
	public void add(InputVerifier verifier) {

		if (!verifiers.contains(verifier)) {
			verifiers.add(verifier);
		}
	}

	@Override
	public boolean verifyCommit(JComponent comp) {

		for (InputVerifier verifier : verifiers) {
			if (verifier instanceof TInputVerifier) {
				if (!((TInputVerifier) verifier).verifyCommit(comp)) {
					return false;
				}
			} else {
				if (!verifier.verify(comp)) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * InputVerify���폜
	 * 
	 * @param inputVerifier InputVerify
	 */
	public void remove(InputVerifier inputVerifier) {
		verifiers.remove(inputVerifier);
	}
}
