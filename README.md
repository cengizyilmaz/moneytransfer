# Money Transfer REST API

## Introduction
Money transfer REST API between Customers Account. The REST API support the following action <br />
1- Customer: Create, Modify, Delete, List <br />
2- Account: Create, Modify, Delete, List <br />
3- Transaction: Create, List

## Build
```
maven clean build
```
## Run
In order to run Api run following command. This will started in default port which is 8080
```
java -jar api-0.0.1-SNAPSHOT.jar
```
If the program is wanted to run other port, the port number needs to be added as argument.
```
java -jar api-0.0.1-SNAPSHOT.jar 7777
```
Executable jar is under the "Executable" folder
## Documantation
There are 3 model object to make the money transfer. The model object and relation between these objects as follow
### Customer --(1-n)--> Account: 
The customer object is used to define the person who have accounts. One Customer has multiple Accounts.
### Account ------ Transaction:
The Account object has the balance value and unique identifier. The money transfer is done between account. The account identifier should be known.  The Account has to be currency. "GBP","USD","EUR" and "JPY" is supported currency symbol.
Transaction object has amount value and the amount of this value is removed from sender balance and added to reciever account according to Currency. The currency conversion is done during the transaction.

## Customer
When the program is executed, the rest api is exposed according to port defined..
### Customer Creation
In order to transfer money, the first step is to create a customer.
http://localhost:{port}/api/customer with POST method is used to customer creation. The body needs to supply according to Customer object model in JSON Format.
The resulted customer object is return if it is successed in 201 ok message. 
![image](https://user-images.githubusercontent.com/3469219/55922044-2c0c2d00-5c08-11e9-9dc3-f5283e6f5e03.png)

### Customer Modification
The customer informations can be modified through this interface.
http://localhost:{port}/api/customer with PUT method is used to customer modification. The body needs to supply according to Customer object model in JSON Format.
The resulted customer object is return if it is successed in 200 ok message. 

### Customer Deletion
The customer  can be deleted through this interface.
http://localhost:{port}/api/customer/{customerId} with DELETE method is used to customer deletion. 
The resulted customer object is return if it is successed in 200 ok message. 

### Customer List
The List of customers  can be viewed through this interface.
http://localhost:{port}/api/customer with GET method is used to list the customers. 
The list of customer object is return if it is successed in 200 ok message. 

## Account
### Account Creation
After the customer created, the account could be created. 
http://localhost:{port}/api/account/{customerId} with POST method is used to account creation. The body needs to supply according to Account object model in JSON Format.
The resulted account object is return if it is successed in 201 ok message. 
![image](https://user-images.githubusercontent.com/3469219/55922155-a341c100-5c08-11e9-92a6-418c83c41365.png)

### Account Modification
The account informations can be modified through this interface.
http://localhost:{port}/api/account with PUT method is used to account modification. The body needs to supply according to Account object model in JSON Format.
The resulted account object is return if it is successed in 200 ok message. 

### Account Deletion
The Account  can be deleted through this interface.
http://localhost:{port}/api/account/{accountId} with DELETE method is used to accoint deletion. 
The resulted account object is return if it is successed in 200 ok message. 

### Account List
The List of account  can be viewed through this interface.
http://localhost:{port}/api/customer with GET method is used to list the accounts. 
The list of account object is return if it is successed in 200 ok message. 

![image](https://user-images.githubusercontent.com/3469219/55922290-32e76f80-5c09-11e9-82e1-3da71f08b350.png)

## Transaction
### Money Transfer
http://localhost:{port}/api/transaction/{fromAccountId}/{toAccountId} with POST method is used to transfer money from one account to another account. The body needs to supply according to Transaction object model in JSON Format.
The resulted account object is return if it is successed in 201 ok message. 

![image](https://user-images.githubusercontent.com/3469219/55922518-30394a00-5c0a-11e9-8a78-2f9049aadb79.png)


### Money Transfer List
http://localhost:{port}/api/transaction with GET method is used to list the transfer money operations from one account to another account.
The resulted account object is return if it is successed in 200 ok message.

