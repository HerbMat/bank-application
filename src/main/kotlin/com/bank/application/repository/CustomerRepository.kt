package com.bank.application.repository

import com.bank.application.domain.Customer
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: MongoRepository<Customer, String> {
}