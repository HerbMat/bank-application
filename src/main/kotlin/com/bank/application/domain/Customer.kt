package com.bank.application.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document
data class Customer(
        @Id
        val id: String,
        val firstName: String,
        val lastName: String,
        val lastLoginBalance: BigDecimal
)