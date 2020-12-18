package com.bank.application.config.mongo

import com.bank.application.config.mongo.loader.MongoInitialDataLoader
import com.bank.application.repository.AccountTypeRepository
import com.bank.application.repository.CustomerRepository
import com.bank.application.repository.TransactionRepository
import org.apache.logging.log4j.LogManager
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

/**
 * On startup loads initial mongo collections to database.
 */
@ConditionalOnProperty(value = ["bank-application.mongo.load-data"], havingValue = "true")
@Component
class MongoDataLoaderListener(
        private val accountTypeRepository: AccountTypeRepository,
        private val customerRepository: CustomerRepository,
        private val transactionRepository: TransactionRepository
) : ApplicationListener<ApplicationStartedEvent> {
    companion object {
        val LOG = LogManager.getLogger()
    }

    override fun onApplicationEvent(event: ApplicationStartedEvent) {
        accountTypeRepository.deleteAll()
        customerRepository.deleteAll()
        transactionRepository.deleteAll()
        LOG.debug("Loading data to database.")
        accountTypeRepository.saveAll(MongoInitialDataLoader.getAccountTypes())
        LOG.debug("Account Types Saved")
        customerRepository.saveAll(MongoInitialDataLoader.getCustomers())
        LOG.debug("Customers Saved")
        transactionRepository.saveAll(MongoInitialDataLoader.getTransactions(
                { customerId -> customerRepository.findById(customerId).orElseThrow() },
                { accountTypeId -> accountTypeRepository.findById(accountTypeId).orElseThrow() }))
        LOG.debug("Transactions Saved")
    }
}