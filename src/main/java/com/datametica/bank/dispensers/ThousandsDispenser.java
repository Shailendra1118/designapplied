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
public class ThousandsDispenser implements Dispenser {

	private Dispenser nextDispenser;
	private static Logger log = LoggerFactory.getLogger(TensDispenser.class);

	public void addDenomination(Integer amount, Map<Integer, Integer> map,
			Map<Integer, Integer> availableDenomination) {
		if (amount >= 1000) {
			int num = amount / 1000;
			int remainder = amount % 1000;

			Integer prev = availableDenomination.get(1000);
			// check if available then remove from denomination map
			availableDenomination.compute(1000, (k, v) -> {
				int o = v - num;
				if (o <= 0) {
					return v - (num + o);
				} else {
					return o;
				}
			});

			Integer now = availableDenomination.get(1000);
			int added = prev - now;
			// check if available then add to result map
			if (map.containsKey(1000)) {
				map.put(1000, map.get(1000) + added);
			} else {
				map.put(1000, added);
			}

			log.info("new map: " + map.entrySet().toString());
			int remainingCash = amount - 1000 * added;
			if (remainingCash != 0 && remainder == 0) {
				throw new NoCashAvailableException();
			}

			if (remainder != 0)
				this.nextDispenser.addDenomination(remainingCash, map,
						availableDenomination);
		} else {
			this.nextDispenser.addDenomination(amount, map,
					availableDenomination);
		}
	}

	public void setNextDispenser(Dispenser disp) {
		this.nextDispenser = disp;
	}

}
