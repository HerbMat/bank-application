package com.bank.application.api

import com.bank.application.api.dto.TransactionDTO
import com.bank.application.domain.Transaction

fun Transaction.toTransactionDTO(): TransactionDTO {
    return TransactionDTO(
            id = id,
            amount = amount,
            accountTypeName = accountType.name,
            customerFirstName = customer.firstName,
            customerLastName = customer.lastName,
            transactionDate = transactionDate
    )
}