package com.moneytransfer.api.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Cengiz YILMAZ
 * Id generator for objects
 */
public class IdGenerator {

	private static AtomicInteger counter=new AtomicInteger();

	public static int getNextUniqueIndex() {
	    return counter.getAndIncrement();
	}
}
