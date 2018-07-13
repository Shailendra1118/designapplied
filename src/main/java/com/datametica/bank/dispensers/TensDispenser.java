/**
 * 
 */
package com.datametica.bank.dispensers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datametica.bank.vo.NoCashAvailableException;

/**
 * @author Shailendra
 *
 */
public class TensDispenser implements Dispenser {

	private static Logger log = LoggerFactory.getLogger(TensDispenser.class);

	@Override
	public void addDenomination(Integer amount, Map<Integer, Integer> map,
			Map<Integer, Integer> availableDenomination) {
		if (amount >= 10) {
			int num = amount / 10;
			int remainder = amount % 10;

			Integer prev = availableDenomination.get(10);
			// check if available then remove from denomination map
			availableDenomination.compute(10, (k, v) -> {
				int o = v - num;
				if (o <= 0) {
					return v - (num + o);
				} else {
					return o;
				}
			});

			Integer now = availableDenomination.get(10);
			int added = prev - now;

			// check if available then add to result map
			if (map.containsKey(10)) {
				map.put(10, map.get(10) + added);
			} else {
				map.put(10, added);
			}

			log.info("new map: {}", map.entrySet().toString());

			int remainingCash = amount - 10 * added;

			if (remainingCash != 0 && remainder == 0) {
				throw new NoCashAvailableException();
			}

			if (remainder != 0) {
				log.error("Amount is not a multiple of 10s.");
				throw new IllegalArgumentException(
						"Only multiple of 10s are allowed to be withdrawl.");
			}
		} else {
			log.error("Amount is not a multiple of 10s.");
			throw new IllegalArgumentException(
					"Only multiple of 10s are allowed to be withdrawl.");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.datametica.bank.dispensers.Dispenser#setNextDispenser(com.datametica.bank.dispensers.Dispenser)
	 */
	@Override
	public void setNextDispenser(Dispenser disp) {
		// This is last
	}

}
