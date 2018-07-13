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
public class HundredsDispenser implements Dispenser {

	// @Autowired
	private Dispenser nextDispenser;
	private static Logger log = LoggerFactory.getLogger(TensDispenser.class);

	@Override
	public void addDenomination(Integer amount, Map<Integer, Integer> map,
			Map<Integer, Integer> availableDenomination) {
		if (amount >= 100) {
			int num = amount / 100;
			int remainder = amount % 100;

			Integer prev = availableDenomination.get(100);
			// check if available then remove from denomination map
			availableDenomination.compute(100, (k, v) -> {
				int o = v - num;
				if (o <= 0) {
					return v - (num + o);
				} else {
					return o;
				}
			});

			Integer now = availableDenomination.get(100);
			int added = prev - now;

			// check if available then add to result map
			if (map.containsKey(100)) {
				map.put(100, map.get(100) + added);
			} else {
				map.put(100, added);
			}

			log.info("new map: " + map.entrySet().toString());
			int remainingCash = amount - 100 * added;

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
