package com.bank.application.api.dto

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.math.BigDecimal
import java.time.LocalDateTime

@ApiModel("Transaction data container")
data class TransactionDTO(
        @ApiModelProperty("Transaction id", example = "777")
        val id: String,

        @ApiModelProperty("Transaction amount", example = "44.22")
        val amount: BigDecimal,

        @ApiModelProperty("Name of account type which performed transaction", example = "trading account")
        val accountTypeName: String,

        @ApiModelProperty("First name of customer who made transaction", example = "Joe")
        val customerFirstName: String,

        @ApiModelProperty("Last name of customer who made transaction", example = "Doe")
        val customerLastName: String,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty("Date of transaction", example = "2010-12-16 08:37:21")
        val transactionDate: LocalDateTime
)