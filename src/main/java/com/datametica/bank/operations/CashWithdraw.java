/**
 * 
 */
package com.datametica.bank.operations;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.datametica.bank.config.DispeserConfiguration;
import com.datametica.bank.dispensers.ThousandsDispenser;

/**
 * @author Shailendra
 *
 */
@Component
public class CashWithdraw implements CommandLineRunner {

	private static Logger log = LoggerFactory.getLogger(CashWithdraw.class);
	private static final Map<Integer, Integer> availableDenomination = new ConcurrentHashMap<>();
	static {
		availableDenomination.put(1000, 5);
		availableDenomination.put(500, 15);
		availableDenomination.put(200, 10);
		availableDenomination.put(100, 20);
		availableDenomination.put(50, 20);
		availableDenomination.put(20, 30);
		availableDenomination.put(10, 30);
	}

	@Override
	@SuppressWarnings("resource")
	public void run(String... args) throws Exception {
		Scanner input = null;
		System.out.println("Enter amount to dispense");
		input = new Scanner(System.in);
		int amount = 0;
		amount = input.nextInt();
		System.out.println("Recieved request for: " + amount);

		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				DispeserConfiguration.class);

		ThousandsDispenser te = ctx.getBean(ThousandsDispenser.class);
		Map<Integer, Integer> result = new HashMap<Integer, Integer>();

		te.addDenomination(amount, result, availableDenomination);

		log.info("Dispensing...");
		for (Entry<Integer, Integer> e : result.entrySet()) {
			log.info(e.getValue() + " notes of " + e.getKey());
		}

		log.info("Available denominations after this transaction...");
		for (Entry<Integer, Integer> e : availableDenomination.entrySet()) {
			log.info(e.getValue() + " notes of " + e.getKey());
		}

		input.close();

	}
}
