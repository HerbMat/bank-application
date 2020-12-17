package com.bank.application.service

import com.bank.application.api.dto.TransactionDetailsRequest
import com.bank.application.repository.TransactionRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.query.Query
import spock.lang.Specification

class TransactionServiceSpec extends Specification {
    def transactionRepository = Mock(TransactionRepository)
    def transactionService = new TransactionService(transactionRepository)

    def "It tries to load collection for empty query"() {
        given:
            def transactionDetailsRequest = new TransactionDetailsRequest("", "")
            def expectedResult = []
        when:
            def result = transactionService.getAll(transactionDetailsRequest, Pageable.unpaged())
        then:
            result == expectedResult
            1 * transactionRepository.findAllByQuery(_) >> {
                arguments ->
                    def query = arguments[0] as Query
                    assert !query.getQueryObject().containsKey("customer.\$id")
                            && !query.getQueryObject().containsKey("accountType.\$id")
                    return  expectedResult
            }
    }

    def "It tries to load collection for ALL account types and 1 customer id"() {
        given:
        def transactionDetailsRequest = new TransactionDetailsRequest("ALL", "1")
        when:
        transactionService.getAll(transactionDetailsRequest, Pageable.unpaged())
        then:
        1 * transactionRepository.findAllByQuery(_) >> {
            arguments ->
                def query = arguments[0] as Query
                assert query.getQueryObject().containsKey("customer.\$id")
                        && !query.getQueryObject().containsKey("accountType.\$id")
                        && query.getQueryObject().get("customer.\$id")["\$in"][0] == "1"
                return  []
        }
    }

    def "It tries to load collection for 2 account type and ALL customer ids"() {
        given:
        def transactionDetailsRequest = new TransactionDetailsRequest("2", "ALL")
        when:
        transactionService.getAll(transactionDetailsRequest, Pageable.unpaged())
        then:
        1 * transactionRepository.findAllByQuery(_) >> {
            arguments ->
                def query = arguments[0] as Query
                assert !query.getQueryObject().containsKey("customer.\$id")
                        && query.getQueryObject().containsKey("accountType.\$id")
                        && query.getQueryObject().get("accountType.\$id")["\$in"][0] == "2"
                return  []
        }
    }

    def "It tries to load collection for 2,4 account types and 3,5 customer ids"() {
        given:
        def transactionDetailsRequest = new TransactionDetailsRequest("2,4", "3,5")
        when:
        transactionService.getAll(transactionDetailsRequest, Pageable.unpaged())
        then:
        1 * transactionRepository.findAllByQuery(_) >> {
            arguments ->
                def query = arguments[0] as Query
                assert query.getQueryObject().containsKey("customer.\$id")
                        && query.getQueryObject().containsKey("accountType.\$id")
                        && query.getQueryObject().get("customer.\$id")["\$in"][0] == "3"
                        && query.getQueryObject().get("customer.\$id")["\$in"][1] == "5"
                        && query.getQueryObject().get("accountType.\$id")["\$in"][0] == "2"
                        && query.getQueryObject().get("accountType.\$id")["\$in"][1] == "4"
                return  []
        }
    }
}
