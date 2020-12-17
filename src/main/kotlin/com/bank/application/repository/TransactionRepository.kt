package com.bank.application.repository

import com.bank.application.domain.Transaction
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository: MongoRepository<Transaction, String>, TransactionQueryRepository {
}