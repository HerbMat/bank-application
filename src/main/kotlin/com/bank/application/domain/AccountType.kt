package com.bank.application.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class AccountType(
        @Id
        val id: String,
        val name: String
)