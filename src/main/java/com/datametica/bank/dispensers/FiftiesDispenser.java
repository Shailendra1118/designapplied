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
public class FiftiesDispenser implements Dispenser {

	final static Integer FIFTY = 50;
	private static Logger log = LoggerFactory.getLogger(TensDispenser.class);
	private Dispenser nextDispenser;

	@Override
	public void addDenomination(Integer amount, Map<Integer, Integer> map,
			Map<Integer, Integer> denomination) {
		if (amount >= FIFTY) {
			int num = amount / FIFTY;
			int remainder = amount % FIFTY;

			Integer prev = denomination.get(FIFTY);
			// check if available then remove from denomination map
			denomination.compute(FIFTY, (k, v) -> {
				int o = v - num;
				if (o <= 0) {
					return v - (num + o);
				} else {
					return o;
				}
			});

			Integer now = denomination.get(FIFTY);
			// check if available then add to result map
			int added = prev - now;
			if (map.containsKey(FIFTY)) {
				map.put(FIFTY, map.get(FIFTY) + added);
			} else {
				map.put(FIFTY, added);
			}

			log.info("new map: " + map.entrySet().toString());

			int remainingCash = amount - FIFTY * added;
			if (remainingCash != 0 && remainder == 0) {
				throw new NoCashAvailableException();
			}

			if (remainder != 0)
				this.nextDispenser.addDenomination(remainingCash, map,
						denomination);
		} else {
			this.nextDispenser.addDenomination(amount, map, denomination);
		}

	}

	@Override
	public void setNextDispenser(Dispenser disp) {
		this.nextDispenser = disp;
	}
}
