This project implements a simplified stock market simulation as a REST API.
It was created as a recruitment task and follows the provided specification 1:1.
The system consists of:

  - Bank – a single entity holding available stocks

  - Wallets – user portfolios that can buy and sell stocks

  - Audit Log – records all successful wallet operations




The application is implemented using Spring Boot and stores all state in memory.

Assumptions & Simplifications:

  - Stock price is fixed and always equals 1

  - Wallet balance (money) is not tracked

  - Buy/Sell operations are executed immediately

  - The Bank acts as the only liquidity provider

  - No persistence – application state lives in memory

  - Only successful wallet operations are logged



How to Run

1.Requirements

    - Java 17+
    
    - Maven

2.Run the application

    Main command
    - mvn spring-boot:run //port 8080
    
    Custom port
    - Shellmvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8080

    The application will be available at: http://localhost:8080


3.API Endpoints


-	Set bank stocks:
	
		  POST /stocks
    	Body:
    	{  "stocks": [    { "name": "apple", "quantity": 10 },    { "name": "tesla", "quantity": 5 }  ]}
    
    	Response
    	200 OK //everything is fine


-	Get bank state
  
			GET /stocksPokaż więcej wierszy
			Response
			{  "stocks": {    "apple": 10,    "tesla": 5  }}

-	Buy / Sell stock

			POST /wallets/{walletId}/stocks/{stockName}
			Body
			{ "type": "buy" } or { "type": "sell" }

			Rules:
			- 404 – stock does not exist in bank
			- 400 – insufficient stock in bank (buy)
			- 400 – insufficient stock in wallet (sell)
			- Wallet is created automatically if it does not exist
			- 200 – successful operation


-	Get wallet state
  
			GET /wallets/{walletId}
			Response
			{  "id": "w1",  "stocks": [    { "name": "apple", "quantity": 2 }  ]}

-	Get stock quantity in wallet
  
			GET /wallets/{walletId}/stocks/{stockName}
			Response:
			2

-	Audit log
  
			GET /log
			Response:
			{  "log": [{ "type": "buy", "wallet_id": "w1", "stock_name": "apple" },    { "type": "sell", "wallet_id": "w1", "stock_name": "apple" }]}
			Only successful operations are logged
			Order of operations is preserved

-	Chaos endpoint
  
			POST /chaos
			Kills the current application instance to simulate failure in a highly available environment.

Architecture Notes


  - Controllers expose REST API

  - Services (BankService, WalletService, LogService) manage application state

  - Bank is the single source of truth for stock quantities

  - All data stored in memory using Java collections

  - No external dependencies or databases


Status

  - All required endpoints implemented

  - Business rules strictly follow the specification

  - Application fully functional and testable via REST

Author
Piotr Bujak
