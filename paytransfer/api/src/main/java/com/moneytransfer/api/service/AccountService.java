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
import com.moneytransfer.api.dao.model.Account;
import com.moneytransfer.api.dao.model.Customer;
import com.moneytransfer.api.util.CurrencyConverter;
import com.moneytransfer.api.util.DAOOperationTypes;
import com.moneytransfer.api.util.ExecutionResult;
import com.moneytransfer.api.util.IdGenerator;

/**
 * @author Cengiz YILMAZ
 * Account Rest Service for the path /account.
 * GET method: calls to list method
 * POST method : /account/{customerId} path and calls the create method in order to create new account
 * PUT method: modify the account information
 * DELETE method: delete the specific account.
 */
@Path("account")
public class AccountService {
	@POST
	@Path("{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Account account, @PathParam("customerId") Integer customerId) {
		if (null != account.getCurrency() && CurrencyConverter.getInstance().checkSymbol(account.getCurrency())) {
			Customer customer = new Customer();
			customer.setId(customerId);

			LocalDAOExecutor<Account, Integer> accountDAO = LocalDAOExecutorFactory.getInstance()
					.buildExecutor(Account.class, DAOOperationTypes.SAVE, account);
			LocalDAOExecutor<Customer, Integer> customerDAO = LocalDAOExecutorFactory.getInstance()
					.buildExecutor(Customer.class, DAOOperationTypes.GET, customer);
			ResponseBuilder resultBuilder=Response.ok(account, MediaType.APPLICATION_JSON).status(Status.CREATED);
			customerDAO.setResultConsumer(result -> result.stream().findFirst().ifPresentOrElse(item -> {
				account.setCustomer(item);
				account.setNumber(IdGenerator.getNextUniqueIndex());
				DAOManager.Instance().execute(DAOOperationTypes.SAVE, accountDAO);
			},()->{
				resultBuilder.status(Status.NOT_FOUND);
			}));
			DAOManager.Instance().execute(DAOOperationTypes.GET, customerDAO);
			accountDAO.setResultConsumer(result -> result.stream().findFirst().ifPresent(item -> {
				account.setBalance(item.getBalance());
				account.setId(item.getId());
				account.setName(item.getName());
				account.setNumber(item.getNumber());
			}));
			return resultBuilder.build();
		}
		return Response.status(419,"Currency should be assigned as GBP,EUR,USD or JPY").type(MediaType.APPLICATION_JSON).build();

	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Account account) {
		if (null != account.getCurrency() && CurrencyConverter.getInstance().checkSymbol(account.getCurrency())) {

			LocalDAOExecutor<Account, Integer> accountDAO = LocalDAOExecutorFactory.getInstance()
					.buildExecutor(Account.class, DAOOperationTypes.MODIFY, account);
			DAOManager.Instance().execute(DAOOperationTypes.MODIFY, accountDAO);
			return accountDAO.getExecutionResult() == ExecutionResult.SUCCESS
					? Response.ok(account, MediaType.APPLICATION_JSON).build()
					: Response.status(400, "Unable to modify the Account").build();
		}

		return Response.status(400, "Invalid Account data. Currency should be valid. Options: GBP,USD,EUR,JPY").build();

	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{accountId}")
	public Response delete(@PathParam("accountId") Integer id) {
		Account account = new Account();
		account.setId(id);
		ResponseBuilder responseBuilder=Response.ok(account, MediaType.APPLICATION_JSON);
		LocalDAOExecutor<Account, Integer> accountDAO = LocalDAOExecutorFactory.getInstance()
				.buildExecutor(Account.class, DAOOperationTypes.DELETE, account);
		accountDAO.setResultConsumer(result->{
			if(null==result) {
				responseBuilder.status(Status.NOT_FOUND);
			}
			
		});
		DAOManager.Instance().execute(DAOOperationTypes.DELETE, accountDAO);
		return accountDAO.getExecutionResult() == ExecutionResult.SUCCESS
				? responseBuilder.build()
				: Response.status(400, "Unable to delete the Account").build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Account> list() {
		LocalDAOExecutor<Account, Integer> localDAO = LocalDAOExecutorFactory.getInstance().buildExecutor(Account.class,
				DAOOperationTypes.LIST, null);
		List<Account> result = new ArrayList<Account>();
		localDAO.setResultConsumer(item -> {
			result.addAll(item);
		});
		DAOManager.Instance().execute(DAOOperationTypes.LIST, localDAO);
		return result;
	}

}
