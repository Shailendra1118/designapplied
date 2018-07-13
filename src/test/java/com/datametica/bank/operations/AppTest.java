package com.datametica.bank.operations;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.datametica.bank.dispensers.Dispenser;
import com.datametica.bank.dispensers.FiftiesDispenser;
import com.datametica.bank.dispensers.FiveHundredsDispenser;
import com.datametica.bank.dispensers.HundredsDispenser;
import com.datametica.bank.dispensers.TensDispenser;
import com.datametica.bank.dispensers.TwentiesDispenser;
import com.datametica.bank.dispensers.TwoHundredsDispenser;
import com.datametica.bank.vo.NoCashAvailableException;

public class AppTest {

	static Dispenser disp = null;
	Dispenser thd = null;
	Dispenser hd = null;
	Dispenser fh = null;
	Dispenser th = null;
	Dispenser tenh = null;
	private static Map<Integer, Integer> availableDenomination = null;

	@BeforeClass
	public static void setUp() {
		availableDenomination = new ConcurrentHashMap<Integer, Integer>();
		availableDenomination.put(500, 5);
		availableDenomination.put(200, 5);
		availableDenomination.put(100, 5);
		availableDenomination.put(50, 5);
		availableDenomination.put(20, 2);
		availableDenomination.put(10, 10);

	}

	@Before
	public void init() {
		disp = new FiveHundredsDispenser();
		Dispenser thd = new TwoHundredsDispenser();
		Dispenser hd = new HundredsDispenser();
		Dispenser fh = new FiftiesDispenser();
		Dispenser th = new TwentiesDispenser();
		Dispenser tenh = new TensDispenser();

		disp.setNextDispenser(thd);
		thd.setNextDispenser(hd);
		hd.setNextDispenser(fh);
		fh.setNextDispenser(th);
		th.setNextDispenser(tenh);
	}

	@Test
	public void normalWithdrawl() {

		Map<Integer, Integer> result = new HashMap<Integer, Integer>();
		disp.addDenomination(250, result, availableDenomination);
		Assert.assertTrue(result.get(200).equals(1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void noTensWithdrawl() {
		Map<Integer, Integer> result = new HashMap<Integer, Integer>();
		disp.addDenomination(252, result, availableDenomination);
		Assert.assertTrue(result.get(200).equals(1));
	}

	@Test(expected = NoCashAvailableException.class)
	public void overWithdrawl() {
		Map<Integer, Integer> result = new HashMap<Integer, Integer>();
		disp.addDenomination(25000, result, availableDenomination);
		Assert.assertTrue(result.get(200).equals(1));
	}

	@After
	public void destroy() {
		availableDenomination = null;
		thd = null;
		hd = null;
		fh = null;
		th = null;
		tenh = null;

	}
}
