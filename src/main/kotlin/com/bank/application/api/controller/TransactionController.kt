package com.bank.application.api.controller

import com.bank.application.api.dto.TransactionDTO
import com.bank.application.api.dto.TransactionDetailsRequest
import com.bank.application.service.TransactionService
import io.swagger.annotations.*
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid


@Api("Operations about transactions")
@RestController
@RequestMapping("/transactions")
class TransactionController(
        private val transactionService: TransactionService
) {

    @ApiOperation(value = "Finds transaction by customer ids and account types ids.",
            notes = "Multiple status values can be provided with comma seperated strings",
            response = TransactionDTO::class,
            responseContainer = "List"
    )
    @ApiImplicitParams(*[
        ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                value = "Results page you want to retrieve (0..N)"),
        ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                value = "Number of records per page."),
        ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                value = "Sorting criteria in the format: property(,asc|desc). " +
                        "Default sort order is ascending. " +
                        "Multiple sort criteria are supported.")
    ])
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Returns found transactions"),
        ApiResponse(code = 500, message = "Service error"),
        ApiResponse(code = 400, message = "Bad parameters")])
    @GetMapping
    fun getTransactions(
            @ApiParam("Search parameters", required = false)
            @Valid
            transactionDetailsRequest: TransactionDetailsRequest,
            @PageableDefault(sort = ["transactionDate", "amount", "id"], direction = Sort.Direction.ASC, value = 50)
            @ApiIgnore("Ignored because swagger ui shows the wrong params, instead they are explained in the implicit params")
            pageable: Pageable): Collection<TransactionDTO> {
        return transactionService.getAll(transactionDetailsRequest, pageable)
    }
}