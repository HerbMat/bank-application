package com.bank.application.repository

import com.bank.application.domain.Transaction
import org.springframework.data.mongodb.core.query.Query

interface TransactionQueryRepository {
    fun findAllByQuery(query: Query): List<Transaction>
}