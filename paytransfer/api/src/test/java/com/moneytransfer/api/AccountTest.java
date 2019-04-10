package com.moneytransfer.api;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.moneytransfer.api.dao.local.MemoryStore;
import com.moneytransfer.api.dao.model.Account;
import com.moneytransfer.api.dao.model.Customer;
import com.moneytransfer.api.service.AccountService;

/**
 * @author Cengiz YILMAZ
 * Account Rest Service which is called in "/account".
 */
public class AccountTest   extends JerseyTest{

	@Override
	public Application configure() {
		Customer customer=new Customer("testName","testSurname","test@test.com","1234567","testAddress");
		customer.setId(0);
		MemoryStore.<String, Integer, Customer>getInstance().getEntity().persist(customer.getClass().getCanonicalName(),0,customer);

		Account testAccount=new Account("testAccount",12345,new BigDecimal(100),"USD");
		testAccount.setCustomer(customer);
		testAccount.setId(888);
		MemoryStore.<String, Integer, Account>getInstance().getEntity().persist(testAccount.getClass().getCanonicalName(),888,testAccount);
		
		return new ResourceConfig(AccountService.class);
	}
	@Test
	public void testCreate() {
		Account account=new Account("TestAccount",12345,new BigDecimal(100),"USD");
		Response output=target("account/0").request().post(Entity.entity(account,MediaType.APPLICATION_JSON));
		System.out.println("Account Created with REST Status Code:"+output.getStatus());
		assertEquals("Should return status 201", 201, output.getStatus());
	}
	@Test
	public void testNotFoundCreate() {
		Account account=new Account("TestAccount",12345,new BigDecimal(100),"USD");
		Response output=target("account/99").request().post(Entity.entity(account,MediaType.APPLICATION_JSON));
		System.out.println("Account Not Created due to not found Customer with REST Status Code:"+output.getStatus());
		assertEquals("Should return status 404", 404, output.getStatus());
	}
	@Test
	public void testInvalidCurrencySymbolCreate() {
		Account account=new Account("TestAccount",12345,new BigDecimal(100),"TRY");
		Response output=target("account/99").request().post(Entity.entity(account,MediaType.APPLICATION_JSON));
		System.out.println("Account Not Created due to invalid currency Symbol Customer with REST Status Code:"+output.getStatus());
		assertEquals("Should return status 419", 419, output.getStatus());
	}
	@Test
	public void testModify() {
		Account testAccount=new Account("modifyAccount",12345,new BigDecimal(100),"USD");
		testAccount.setId(888);
		Response output=target("account").request().put(Entity.entity(testAccount,MediaType.APPLICATION_JSON));
		System.out.println("Account modify successfully"+output.getStatus());
		assertEquals("Should return status 200", 200, output.getStatus());
	}
	@Test
	public void testDelete() {
	
		Response output=target("account/888").request().delete();
		System.out.println("Account Created with REST Status Code:"+output.getStatus());
		assertEquals("Should return status 200", 200, output.getStatus());
	}
}
