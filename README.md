# Money Transfer REST API
Money Transfer REST API 
## Introduction
Money transfer REST API between Customers Account. The REST API support the following action
1- Customer: Create, Modify, Delete, List
2- Account: Create, Modify, Delete, List
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
## Documantation
There are 3 model object to make the money transfer. The model object and relation between these objects as follow
### Customer --(1-n)--> Account: 
The customer object is used to define the person who have accounts. One Customer has multiple Accounts.
### Account ------ Transaction:
The Account object has the balance value and unique identifier. The money transfer is done between account. The account identifier should be known.  The Account has to be currency. "GBP","USD","EUR" and "JPY" is supported currency symbol.
Transaction object has amount value and the amount of this value is removed from sender balance and added to reciever account according to Currency. The currency conversion is done during the transaction.

When the program is executed, the rest api is exposed according to port defined..
### Customer Creation
In order to transfer money, the first step is to create a customer.
http://localhost:{port}/api/customer with POST method is used to customer creation. The body needs to supply according to Customer object model in JSON Format.
The resulted customer object is return if it is successed in 201 ok message. 
## Customer
### Customer Modification
The customer informations can be modified through this interface.
http://localhost:{port}/api/customer with PUT method is used to customer creation. The body needs to supply according to Customer object model in JSON Format.
The resulted customer object is return if it is successed in 200 ok message. 

### Customer Deletion
The customer  can be deleted through this interface.
http://localhost:{port}/api/customer/{customerId} with Delete method is used to customer deletion. 
The resulted customer object is return if it is successed in 200 ok message. 

### Customer List
The List of customers  can be viewed through this interface.
http://localhost:{port}/api/customer with Delete method is used to list the customers. 
The list of customer object is return if it is successed in 200 ok message. 
