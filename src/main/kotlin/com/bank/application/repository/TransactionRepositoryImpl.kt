package com.bank.application.repository

import com.bank.application.domain.Transaction
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component

@Component
class TransactionRepositoryImpl(private val mongoTemplate: MongoTemplate): TransactionQueryRepository {
    override fun findAllByQuery(query: Query): List<Transaction> {
        return mongoTemplate.find(query, Transaction::class.java)
    }
}