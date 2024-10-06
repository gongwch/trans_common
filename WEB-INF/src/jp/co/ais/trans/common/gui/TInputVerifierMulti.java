package jp.co.ais.trans.common.gui;

import java.util.*;

import javax.swing.*;

/**
 * •¡”InputVerifier‚ğ“o˜^‚·‚éˆ×‚ÌƒNƒ‰ƒX
 */
public class TInputVerifierMulti extends TInputVerifier {

	/** •¡”InputVerifier */
	public List<InputVerifier> verifiers = new LinkedList<InputVerifier>();

	/**
	 * InputVerifier’Ç‰Á
	 * 
	 * @param verifier ’Ç‰ÁVerifier
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
	 * InputVerify‚ğíœ
	 * 
	 * @param inputVerifier InputVerify
	 */
	public void remove(InputVerifier inputVerifier) {
		verifiers.remove(inputVerifier);
	}
}
