package com.bank.application.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDateTime

@Document
data class Transaction(
        @Id
        val id: String,
        val amount: BigDecimal,

        @DBRef
        val accountType: AccountType,

        @DBRef
        val customer: Customer,
        val transactionDate: LocalDateTime
)