package com.bank.application.repository

import com.bank.application.domain.AccountType
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountTypeRepository: MongoRepository<AccountType, String> {
}