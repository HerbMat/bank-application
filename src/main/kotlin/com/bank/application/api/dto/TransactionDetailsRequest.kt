package com.bank.application.api.dto

import com.bank.application.validation.constraint.IdList
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("Container for transactions search criteria")
data class TransactionDetailsRequest(
        @ApiModelProperty("List of account types ids. It follows pattern [0-9],[0-9] or [0-9] or ALL", example = "ALL")
        @IdList(message = "{account.type.list.invalid}")
        val accountTypes: String?,

        @ApiModelProperty("List of customer ids. It follows pattern [0-9],[0-9] or [0-9] or ALL", example = "ALL")
        @IdList(message = "{customer.list.invalid}")
        val customerIds: String?
)