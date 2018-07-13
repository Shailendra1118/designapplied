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
public class TwentiesDispenser implements Dispenser {

	private static Logger log = LoggerFactory.getLogger(TensDispenser.class);
	private Dispenser nextDispenser;

	@Override
	public void addDenomination(Integer amount, Map<Integer, Integer> map,
			Map<Integer, Integer> availableDenomination) {
		if (amount >= 20) {
			int num = amount / 20;
			int remainder = amount % 20;

			Integer prev = availableDenomination.get(20);
			// check if available then remove from denomination map
			availableDenomination.compute(20, (k, v) -> {
				int o = v - num;
				if (o <= 0) {
					return v - (num + o);
				} else {
					return o;
				}
			});

			Integer now = availableDenomination.get(20);
			int added = prev - now;

			// check if available then add to result map
			if (map.containsKey(20)) {
				map.put(20, map.get(20) + added);
			} else {
				map.put(20, added);
			}

			log.info("new map: " + map.entrySet().toString());
			int remainingCash = amount - 20 * added;
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

	@Override
	public void setNextDispenser(Dispenser disp) {
		this.nextDispenser = disp;

	}

}
