/**
 * 
 */
package com.datametica.bank.dispensers;

import java.util.Map;

/**
 * @author Shailendra
 *
 */
public interface Dispenser {

	/**
	 * @param amount
	 * @param map
	 *            denomination map
	 */
	void addDenomination(Integer amount, Map<Integer, Integer> map,
			Map<Integer, Integer> denomination);

	/**
	 * @param disp
	 *            the next dispenser
	 */
	void setNextDispenser(Dispenser disp);

	// void checkEnoughCash();

}
