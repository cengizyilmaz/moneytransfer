package com.moneytransfer.api;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.moneytransfer.api.dao.local.MemoryStore;
import com.moneytransfer.api.dao.model.Customer;
import com.moneytransfer.api.service.CustomerService;


/**
 * Unit test for Customer Rest Service which is called in "/customer".
 */
public class CustomerTest 
    extends JerseyTest
{
	@Override
	public Application configure() {
		Customer customer=new Customer("testModify","testModifySurname","testModify@testModify.com","7654321","testModify Address");
		customer.setId(88);
		MemoryStore.<String, Integer, Customer>getInstance().getEntity().persist(customer.getClass().getCanonicalName(),88,customer);

		return new ResourceConfig(CustomerService.class);
	}

	@Test
	public void tesFetchAll() {
		Response response = target("customer").request().get();
		assertEquals("should return status 200", 200, response.getStatus());
		System.out.println("Rest request for Fetch all of the Customer "+response.getStatus());
	
	}
	
	@Test
	public void testCreate() {
		Customer customer=new Customer("testName","testSurname","test@test.com","1234567","testAddress");
		Response output=target("customer").request().post(Entity.entity(customer,MediaType.APPLICATION_JSON));
		System.out.println("Rest request to create a new Customer with status code"+output.getStatus());
		assertEquals("Should return status 201", 201, output.getStatus());
	}
	@Test
	public void testModify() {
		Customer customer=new Customer("testName","testSurname","test@test.com","1234567","testAddress");
		customer.setId(88);
		Response output=target("customer").request().put(Entity.entity(customer,MediaType.APPLICATION_JSON));
		System.out.println("Rest request result to modify the existing customer"+output.getStatus());
		assertEquals("Should return status 200", 200, output.getStatus());
	}
	@Test
	public void testDelete() {
		Response output=target("customer/88").request().delete();
		System.out.println("Rest request result to delete the existing customer"+output.getStatus());
		assertEquals("Should return status 200", 200, output.getStatus());
	}
	

}
