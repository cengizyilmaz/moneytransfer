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
import com.moneytransfer.api.dao.model.Transaction;
import com.moneytransfer.api.service.TransactionService;

/**
 * @author Cengiz YILMAZ
 * Transaction Unit Test cases
 *
 */
public class TransactionTest     extends JerseyTest {
	@Override
	public Application configure() {
		Customer fromCustomer=new Customer("fromCustomer","fromCustomer","fromCustomer@fromCustomer.com","7654321","fromCustomer Address");
		fromCustomer.setId(88);
		MemoryStore.<String, Integer, Customer>getInstance().getEntity().persist(fromCustomer.getClass().getCanonicalName(),88,fromCustomer);
		Account fromAccount=new Account("fromAccount",12345,new BigDecimal(100),"USD");
		fromAccount.setCustomer(fromCustomer);
		fromAccount.setId(888);
		MemoryStore.<String, Integer, Account>getInstance().getEntity().persist(fromAccount.getClass().getCanonicalName(),888,fromAccount);
		
		Customer toCustomer=new Customer("toCustomer","toCustomer","toCustomer@toCustomer.com","7654321","toCustomer Address");
		toCustomer.setId(77);
		MemoryStore.<String, Integer, Customer>getInstance().getEntity().persist(fromCustomer.getClass().getCanonicalName(),88,fromCustomer);
		Account toAccount=new Account("toAccount",67890,new BigDecimal(100),"USD");
		toAccount.setCustomer(toCustomer);
		toAccount.setId(777);
		MemoryStore.<String, Integer, Account>getInstance().getEntity().persist(toAccount.getClass().getCanonicalName(),777,toAccount);
		
		Account toAccountGBP=new Account("toAccountGBP",78901,new BigDecimal(100),"GBP");
		toAccountGBP.setCustomer(toCustomer);
		toAccountGBP.setId(666);
		MemoryStore.<String, Integer, Account>getInstance().getEntity().persist(toAccountGBP.getClass().getCanonicalName(),666,toAccountGBP);

		return new ResourceConfig(TransactionService.class);
	}
	@Test
	public void tesFetchAll() {
		Response response = target("transaction").request().get();
		assertEquals("should return status 200", 200, response.getStatus());
		System.out.println(response.getStatus());
	
	}
	@Test
	public void testTransfer() {
		Transaction transaction =new Transaction();
		transaction.setId(55);
		transaction.setAmount(new BigDecimal(10));
		Response output=target("transaction/888/777").request().post(Entity.entity(transaction,MediaType.APPLICATION_JSON));
		System.out.println("Transaction Test from Customer-1 Account to Customer-2 Account"+output.getStatus());
		assertEquals("Should return status 201", 201, output.getStatus());
	}
	@Test
	public void testInvalidFromTransfer() {
		Transaction transaction =new Transaction();
		transaction.setId(55);
		transaction.setAmount(new BigDecimal(10));
		Response output=target("transaction/000/777").request().post(Entity.entity(transaction,MediaType.APPLICATION_JSON));
		System.out.println("Transaction Test from Invalid Customer-1 Account to Customer-2 Account"+output.getStatus());
		assertEquals("Should return status 404", 404, output.getStatus());
	}
	@Test
	public void testInvalidToTransfer() {
		Transaction transaction =new Transaction();
		transaction.setId(55);
		transaction.setAmount(new BigDecimal(10));
		Response output=target("transaction/888/000").request().post(Entity.entity(transaction,MediaType.APPLICATION_JSON));
		System.out.println("Transaction Test from Invalid Customer-1 Account to Customer-2 Account"+output.getStatus());
		assertEquals("Should return status 404", 404, output.getStatus());
	}
	@Test
	public void testInSufficientBalanceTransfer() {
		Transaction transaction =new Transaction();
		transaction.setId(55);
		transaction.setAmount(new BigDecimal(1000));
		Response output=target("transaction/888/777").request().post(Entity.entity(transaction,MediaType.APPLICATION_JSON));
		System.out.println("Transaction Test from Insufficient Balance of  Customer-1 Account to Customer-2 Account"+output.getStatus());
		assertEquals("Should return status 201", 201, output.getStatus());
	}
}
