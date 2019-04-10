package com.moneytransfer.api.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter {
	private static CurrencyConverter instance=null;
	private Map<String,Map<String,BigDecimal>> convertRate=null;
	private CurrencyConverter() {
		convertRate=new HashMap<String, Map<String,BigDecimal>>();
		Map<String,BigDecimal> gbpConvertion=new HashMap<String, BigDecimal>();
		gbpConvertion.put("USD"	,new BigDecimal(1.3));
		gbpConvertion.put("EUR", new BigDecimal(1.6));
		gbpConvertion.put("JPY",new BigDecimal(145.3));
		convertRate.put("GBP", gbpConvertion);
		Map<String,BigDecimal> usdConvertion =new HashMap<String, BigDecimal>();
		usdConvertion.put("GBP"	,new BigDecimal(0.77));
		usdConvertion.put("EUR", new BigDecimal(0.88));
		usdConvertion.put("JPY",new BigDecimal(111.0));
		convertRate.put("USD", usdConvertion);
		Map<String,BigDecimal> eurConverstion =new HashMap<String, BigDecimal>();
		eurConverstion.put("GBP"	,new BigDecimal(0.62));
		eurConverstion.put("USD", new BigDecimal(1.2));
		eurConverstion.put("JPY",new BigDecimal(125.3));
		convertRate.put("EUR", eurConverstion);
		Map<String,BigDecimal> jpyConverstion =new HashMap<String, BigDecimal>();
		jpyConverstion.put("GBP"	,new BigDecimal(0.007));
		jpyConverstion.put("USD", new BigDecimal(0.009));
		jpyConverstion.put("EUR",new BigDecimal(0.008));
		convertRate.put("JPY", eurConverstion);

	}
	public static CurrencyConverter getInstance() {
		if(null==instance) {
			instance=new CurrencyConverter();
		}
		return instance;
	}
	public boolean checkSymbol(String symbol) {
		return convertRate.containsKey(symbol);
		
	}
	public BigDecimal convert(String fromSymbol,String toSymbol,BigDecimal rate) {
		if(convertRate.containsKey(fromSymbol)) {
			Map<String,BigDecimal> mapConvert=convertRate.get(fromSymbol);
			if(mapConvert.containsKey(toSymbol)) {
				return mapConvert.get(toSymbol).multiply(rate);
			}
		}
		return null;
	}
	

}
