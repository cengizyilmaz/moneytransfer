package com.moneytransfer.api.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.moneytransfer.api.dao.DAOManager;
import com.moneytransfer.api.dao.local.LocalDAOExecutor;
import com.moneytransfer.api.dao.local.LocalDAOExecutorFactory;
import com.moneytransfer.api.dao.model.Customer;
import com.moneytransfer.api.util.DAOOperationTypes;
import com.moneytransfer.api.util.ExecutionResult;

/**
 * @author Cengiz YILMAZ
 * Customer REST Service for create a Customer, modify the customer, delete the customer 
 * and get the list of Customers.
 * One customer can have multiple accounts.
 * Account Rest Service defined in  the path /customer.
 * GET method: calls to list method for gathering the customers
 * POST method : calls the create method in order to create new customer
 * PUT method: modify the customer information
 * DELETE method: delete the specific customer.
 */
@Path("customer")
public class CustomerService {
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Customer customer) {
		LocalDAOExecutor<Customer, Integer> localDAO = LocalDAOExecutorFactory.getInstance()
				.buildExecutor(Customer.class, DAOOperationTypes.SAVE, customer);
		DAOManager.Instance().execute(DAOOperationTypes.SAVE, localDAO);
		localDAO.setResultConsumer(result -> result.stream().findFirst().ifPresent(item -> {
			customer.setId(item.getId());
			customer.setEmail(item.getEmail());
			customer.setName(item.getName());
			customer.setPhonenumber(item.getPhonenumber());
			customer.setSurname(item.getSurname());
		}));
		return Response.ok(customer, MediaType.APPLICATION_JSON).status(201).build();
	}

	@PUT
	public Response update(Customer customer) {
		LocalDAOExecutor<Customer, Integer> customerDAO = LocalDAOExecutorFactory.getInstance()
				.buildExecutor(Customer.class, DAOOperationTypes.MODIFY, customer);
		DAOManager.Instance().execute(DAOOperationTypes.MODIFY, customerDAO);
		return customerDAO.getExecutionResult()==ExecutionResult.SUCCESS?Response.ok(customer, MediaType.APPLICATION_JSON).build():Response.status(400, "Unable to modify the Account").build();

	}

	@DELETE
	@Path("{customerId}")
	public Response delete(@PathParam("customerId") Integer id) {
		Customer customer=new Customer();
		customer.setId(id);
		ResponseBuilder responseBuilder=Response.ok(customer, MediaType.APPLICATION_JSON);
		LocalDAOExecutor<Customer, Integer> customerDAO = LocalDAOExecutorFactory.getInstance()
				.buildExecutor(Customer.class, DAOOperationTypes.DELETE, customer);
		customerDAO.setResultConsumer(result->{
			if(null==result) {
				responseBuilder.status(Status.NOT_FOUND);
			}
			
		});
		DAOManager.Instance().execute(DAOOperationTypes.DELETE, customerDAO);
		
		return customerDAO.getExecutionResult()==ExecutionResult.SUCCESS?responseBuilder.build():responseBuilder.status(400, "Unable to delete the Account").build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> list() {
		LocalDAOExecutor<Customer, Integer> localDAO = LocalDAOExecutorFactory.getInstance()
				.buildExecutor(Customer.class, DAOOperationTypes.LIST, null);
		List<Customer> result = new ArrayList<Customer>();
		localDAO.setResultConsumer(item -> {
			result.addAll(item);
		});
		DAOManager.Instance().execute(DAOOperationTypes.LIST, localDAO);
		return result;

	}

}
