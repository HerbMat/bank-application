package com.bank.application.service

import com.bank.application.api.dto.TransactionDTO
import com.bank.application.api.dto.TransactionDetailsRequest
import com.bank.application.api.toTransactionDTO
import com.bank.application.repository.TransactionRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

/**
 * Service for managing transactions
 */
@Service
class TransactionService(private val transactionRepository: TransactionRepository) {

    /**
     * Retrieves transactions based on criteria specified in [TransactionDetailsRequest]
     * @param transactionDetailsRequest request with transaction criterias.
     * @param pageable requested page information.
     *
     * @return page specified by pageable for given criteria. If there are no results returns empty collection.
     */
    fun getAll(transactionDetailsRequest: TransactionDetailsRequest, pageable: Pageable): List<TransactionDTO> {
        return transactionRepository.findAllByQuery(buildCriteriaQuery(transactionDetailsRequest).with(pageable))
                .map { it.toTransactionDTO() }
    }

    private fun buildCriteriaQuery(transactionDetailsRequest: TransactionDetailsRequest): Query {
        val criteria = Criteria()
        transactionDetailsRequest.accountTypes?.let {
            if (isThereCriteriaToAdd(it)) {
                criteria.and("accountType.\$id").`in`(it.split(","))
            }
        }
        transactionDetailsRequest.customerIds?.let {
            if (isThereCriteriaToAdd(it)) {
                criteria.and("customer.\$id").`in`(it.split(","))
            }
        }
        val query = Query()
        query.addCriteria(criteria)

        return query
    }

    private fun isThereCriteriaToAdd(listString: String): Boolean = listString.isNotBlank() && listString != "ALL"
}