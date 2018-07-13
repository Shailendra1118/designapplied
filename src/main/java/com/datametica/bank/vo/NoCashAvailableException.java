/**
 * 
 */
package com.datametica.bank.vo;

/**
 * @author Shailendra
 *
 */
public class NoCashAvailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoCashAvailableException() {
		super("No Cash");
	}
}
