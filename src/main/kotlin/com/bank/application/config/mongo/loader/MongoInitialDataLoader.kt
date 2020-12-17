package com.bank.application.config.mongo.loader

import com.bank.application.domain.AccountType
import com.bank.application.domain.Customer
import com.bank.application.domain.Transaction
import com.bank.application.utils.CSVCollectionFileReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * It loads initial entity collection from file.
 */
object MongoInitialDataLoader {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    fun getAccountTypes(): List<AccountType> {
        return CSVCollectionFileReader
                .extractCollectionDataFromFile("accountypes.csv")
                .map {
                    AccountType(it["account_type"]!!, it["name"].orEmpty())
                }
    }

    fun getCustomers(): List<Customer> {
        return CSVCollectionFileReader
                .extractCollectionDataFromFile("customers.csv")
                .map {
                    Customer(
                            it["id"]!!,
                            it["first_name"].orEmpty(),
                            it["last_name"].orEmpty(),
                            (it["last_login_balance"] ?: "0").toBigDecimal()
                    )
                }
    }

    fun getTransactions(customerLoader: (customerId: String) -> Customer, accountTypeLoader: (accountTypeId: String) -> AccountType): List<Transaction> {
        return CSVCollectionFileReader
                .extractCollectionDataFromFile("transactions.csv")
                .map { Transaction(
                        it["transaction_id"]!!,
                        (it["transaction_amount"] ?: "0").toBigDecimal(),
                        accountTypeLoader(it["account_type"]!!),
                        customerLoader(it["customer_id"]!!),
                        LocalDateTime.parse(it["transaction_date"], formatter)
                ) }
    }
}