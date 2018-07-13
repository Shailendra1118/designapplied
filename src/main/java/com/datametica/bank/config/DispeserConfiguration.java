/**
 * 
 */
package com.datametica.bank.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.datametica.bank.dispensers.Dispenser;
import com.datametica.bank.dispensers.FiftiesDispenser;
import com.datametica.bank.dispensers.FiveHundredsDispenser;
import com.datametica.bank.dispensers.HundredsDispenser;
import com.datametica.bank.dispensers.TensDispenser;
import com.datametica.bank.dispensers.ThousandsDispenser;
import com.datametica.bank.dispensers.TwentiesDispenser;
import com.datametica.bank.dispensers.TwoHundredsDispenser;

/**
 * @author Shailendra
 *
 */

@Configuration
public class DispeserConfiguration {

	@Bean
	@Qualifier("thousandsDispenser")
	public Dispenser createThousandsDispenser() {
		ThousandsDispenser td = new ThousandsDispenser();
		td.setNextDispenser(createFiveHundredsDispenser());
		return td;
	}

	@Bean
	@Qualifier("fiveHundredsDispenser")
	public Dispenser createFiveHundredsDispenser() {
		FiveHundredsDispenser fhd = new FiveHundredsDispenser();
		fhd.setNextDispenser(createTwoHundredsDispenser());
		return fhd;
	}

	@Bean
	@Qualifier("twoHundredsDispenser")
	public Dispenser createTwoHundredsDispenser() {
		TwoHundredsDispenser td = new TwoHundredsDispenser();
		td.setNextDispenser(createHundredsDispenser());
		return td;
	}

	@Bean
	@Qualifier("hundredsDispenser")
	public Dispenser createHundredsDispenser() {
		HundredsDispenser td = new HundredsDispenser();
		td.setNextDispenser(createFiftiesDispenser());
		return td;
	}

	@Bean
	@Qualifier("fiftiesDispenser")
	public Dispenser createFiftiesDispenser() {
		FiftiesDispenser td = new FiftiesDispenser();
		td.setNextDispenser(createTwentiesDispenser());
		return td;
	}

	@Bean
	@Qualifier("twentiesDispenser")
	public Dispenser createTwentiesDispenser() {
		TwentiesDispenser td = new TwentiesDispenser();
		td.setNextDispenser(createTensDispenser());
		return td;
	}

	@Bean
	@Qualifier("tensDispenser")
	public Dispenser createTensDispenser() {
		TensDispenser td = new TensDispenser();
		return td;
	}
}
