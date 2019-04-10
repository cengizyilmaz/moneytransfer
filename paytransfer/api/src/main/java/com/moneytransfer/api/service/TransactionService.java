package com.moneytransfer.api.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.moneytransfer.api.dao.DAOManager;
import com.moneytransfer.api.dao.local.LocalDAOExecutor;
import com.moneytransfer.api.dao.local.LocalDAOExecutorFactory;
import com.moneytransfer.api.dao.model.Account;
import com.moneytransfer.api.dao.model.Transaction;
import com.moneytransfer.api.util.CurrencyConverter;
import com.moneytransfer.api.util.DAOOperationTypes;

/**
 * @author Cengiz YILMAZ
 * Transaction Rest Service for the path /transaction which used for money transfer between accounts.
 * GET method: calls to list method and return list of transactions
 * POST method : calls to transfer method. 
 * The path defined as /{fromAccount}/{toAccount} path 
 * and transfer money from one account to other and rebalance the accounts

 */
@Path("transaction")
public class TransactionService {
	@POST
	@Path("{fromAccount}/{toAccount}")
	public Response transfer(Transaction transaction, @PathParam("fromAccount") Integer from,
			@PathParam("toAccount") Integer to) {
		Account fromAccount = new Account();
		fromAccount.setId(from);
		Account toAccount = new Account();
		toAccount.setId(to);
		ResponseBuilder resultBuilder = Response.ok(transaction, MediaType.APPLICATION_JSON).status(201);

		LocalDAOExecutor<Account, Integer> fromAccountDAO = LocalDAOExecutorFactory.getInstance()
				.buildExecutor(Account.class, DAOOperationTypes.GET, fromAccount);
		fromAccountDAO.setResultConsumer(fromResult -> fromResult.stream().findFirst().ifPresentOrElse(fromItem -> {
			LocalDAOExecutor<Account, Integer> toAccountDAO = LocalDAOExecutorFactory.getInstance()
					.buildExecutor(Account.class, DAOOperationTypes.GET, toAccount);
			toAccountDAO.setResultConsumer(toResult -> toResult.stream().findFirst().ifPresentOrElse(toItem -> {
				if (fromItem.getBalance().compareTo(transaction.getAmount()) >= 0) {
					transaction.setFrom(fromItem);
					transaction.setTo(toItem);
					transaction.setDate(LocalDateTime.now());
					BigDecimal fromBalance = fromItem.getBalance();
					BigDecimal toBalance = toItem.getBalance();
					BigDecimal toTransactionAmount = transaction.getAmount();
					if (fromBalance.compareTo(toTransactionAmount) < 0) {
						resultBuilder.status(400, "Not Enough Balance");
					} else {
						if (!fromItem.getCurrency().equals(toItem.getCurrency())) {
							toTransactionAmount = CurrencyConverter.getInstance().convert(fromItem.getCurrency(),
									toItem.getCurrency(), transaction.getAmount());
						}

						fromItem.setBalance(fromBalance.subtract(transaction.getAmount()));
						toItem.setBalance(toBalance.add(toTransactionAmount));
						LocalDAOExecutor<Transaction, Integer> transactionDAO = LocalDAOExecutorFactory.getInstance()
								.buildExecutor(Transaction.class, DAOOperationTypes.SAVE, transaction);
						DAOManager.Instance().execute(DAOOperationTypes.SAVE, transactionDAO);
					}
				}
			},()->{
				resultBuilder.status(Status.NOT_FOUND);
			}));
			DAOManager.Instance().execute(DAOOperationTypes.GET, toAccountDAO);
		},()->{
			resultBuilder.status(Status.NOT_FOUND);
		}));
		DAOManager.Instance().execute(DAOOperationTypes.GET, fromAccountDAO);
		return resultBuilder.build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Transaction> list(@QueryParam("fromAccount") Integer fromAccount,
			@QueryParam("toAccount") Integer toAccount) {
		LocalDAOExecutor<Transaction, Integer> transactionDAO = LocalDAOExecutorFactory.getInstance()
				.buildExecutor(Transaction.class, DAOOperationTypes.LIST, null);
		List<Transaction> result = new ArrayList<Transaction>();
		transactionDAO.setResultConsumer(item -> {
			result.addAll(item);
		});
		return result;
	}

}
