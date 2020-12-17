package com.bank.application.api.controller

import com.bank.application.api.dto.TransactionDTO
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@WithMockUser(username = "test", password = "test")
class TransactionControllerSpec extends Specification {

    private static final String TRANSACTIONS_ENDPOINT = "/transactions"
    private static final String TRADING_ACCOUNT_TYPE_NAME = "trading account"
    private static final String FIRST_CUSTOMER_FIRST_NAME = "Andrzej"
    private static final String FIRST_CUSTOMER_LAST_NAME = "Kowalski"
    private static final String SAVING_ACCOUNT_TYPE_NAME = "saving account"
    private static final String FIFTH_CUSTOMER_FIRST_NAME = "Anna"
    private static final String FIFTH_CUSTOMER_LAST_NAME = "Rzymowska"
    private static final String CURRENCY_ACCOUNT_TYPE_NAME = "currency account"
    private static final String SECOND_CUSTOMER_FIRST_NAME = "Adrianna"
    private static final String SECOND_CUSTOMER_LAST_NAME = "Nowak"

    @Autowired
    private MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper

    def "For empty params should return all records"() {
        when:
            def response = mockMvc
                    .perform(get(TRANSACTIONS_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                    ).andReturn()
                    .response
        then:
            response.status == HttpStatus.OK.value()
        and:
            verifyAll(objectMapper.readValue(response.contentAsString, List<TransactionDTO>)) {
                it.size() == 23
            }
    }

    def "For params with value 'ALL' should return all records"() {
        when:
            def response = mockMvc
                    .perform(get(TRANSACTIONS_ENDPOINT)
                            .param("accountTypes", "ALL")
                            .param("customerIds", "ALL")
                            .contentType(MediaType.APPLICATION_JSON)
                    ).andReturn()
                    .response
        then:
            response.status == HttpStatus.OK.value()
        and:
            verifyAll(objectMapper.readValue(response.contentAsString, List<TransactionDTO>)) {
                it.size() == 23
            }
    }

    def "For account type 4 should return 3 records"() {
        when:
            def response = mockMvc
                    .perform(get(TRANSACTIONS_ENDPOINT)
                            .param("accountTypes", "4")
                            .contentType(MediaType.APPLICATION_JSON)
                    ).andReturn()
                    .response
        then:
            response.status == HttpStatus.OK.value()
        and:
            verifyAll(objectMapper.readValue(response.contentAsString, TransactionDTO[]).toList()) {
                it.size() == 3
                it.every { it.accountTypeName == TRADING_ACCOUNT_TYPE_NAME }
            }
    }

    def "For account type 4 and customer Id 1 should return 1 record"() {
        when:
            def response = mockMvc
                    .perform(get(TRANSACTIONS_ENDPOINT)
                            .param("accountTypes", "4")
                            .param("customerIds", "1")
                            .contentType(MediaType.APPLICATION_JSON)
                    ).andReturn()
                    .response
        then:
            response.status == HttpStatus.OK.value()
        and:
            verifyAll(objectMapper.readValue(response.contentAsString, TransactionDTO[]).toList()) {
                it.size() == 2
                it.every { it.accountTypeName == TRADING_ACCOUNT_TYPE_NAME
                        && it.customerFirstName == FIRST_CUSTOMER_FIRST_NAME
                        && it.customerLastName == FIRST_CUSTOMER_LAST_NAME
                }
            }
    }

    def "For account types 2,4 and customer Id 1 should return 4 records"() {
        when:
            def response = mockMvc
                    .perform(get(TRANSACTIONS_ENDPOINT)
                            .param("accountTypes", "2,4")
                            .param("customerIds", "1")
                            .contentType(MediaType.APPLICATION_JSON)
                    ).andReturn()
                    .response
        then:
            response.status == HttpStatus.OK.value()
        and:
            verifyAll(objectMapper.readValue(response.contentAsString, TransactionDTO[]).toList()) {
                it.size() == 4
                it.every { (it.accountTypeName == TRADING_ACCOUNT_TYPE_NAME || it.accountTypeName == SAVING_ACCOUNT_TYPE_NAME)
                        && it.customerFirstName == FIRST_CUSTOMER_FIRST_NAME
                        && it.customerLastName == FIRST_CUSTOMER_LAST_NAME
                }
            }
    }

    def "For account types 2 and customer Id 1,5 should return 2 records"() {
        when:
            def response = mockMvc
                    .perform(get(TRANSACTIONS_ENDPOINT)
                            .param("accountTypes", "2")
                            .param("customerIds", "1,5")
                            .contentType(MediaType.APPLICATION_JSON)
                    ).andReturn()
                    .response
        then:
            response.status == HttpStatus.OK.value()
        and:
            verifyAll(objectMapper.readValue(response.contentAsString, TransactionDTO[]).toList()) {
                it.size() == 3
                it.every { it.accountTypeName == SAVING_ACCOUNT_TYPE_NAME
                        && ((it.customerFirstName == FIRST_CUSTOMER_FIRST_NAME && it.customerLastName == FIRST_CUSTOMER_LAST_NAME)
                            || (it.customerFirstName == FIFTH_CUSTOMER_FIRST_NAME && it.customerLastName == FIFTH_CUSTOMER_LAST_NAME))
                }
            }
    }

    def "For account types 2,3 and customer Id 1,5 should return 2 records"() {
        when:
            def response = mockMvc
                    .perform(get(TRANSACTIONS_ENDPOINT)
                            .param("accountTypes", "2,3")
                            .param("customerIds", "1,5")
                            .contentType(MediaType.APPLICATION_JSON)
                    ).andReturn()
                    .response
        then:
            response.status == HttpStatus.OK.value()
        and:
            verifyAll(objectMapper.readValue(response.contentAsString, TransactionDTO[]).toList()) {
                it.size() == 4
                it.every { (it.accountTypeName == SAVING_ACCOUNT_TYPE_NAME || it.accountTypeName == CURRENCY_ACCOUNT_TYPE_NAME)
                        && ((it.customerFirstName == FIRST_CUSTOMER_FIRST_NAME && it.customerLastName == FIRST_CUSTOMER_LAST_NAME)
                        || (it.customerFirstName == FIFTH_CUSTOMER_FIRST_NAME && it.customerLastName == FIFTH_CUSTOMER_LAST_NAME))
                }
            }
    }

    def "For account types 3 and customer Id 2should return 1 record"() {
        when:
            def response = mockMvc
                    .perform(get(TRANSACTIONS_ENDPOINT)
                            .param("accountTypes", "3")
                            .param("customerIds", "2")
                            .contentType(MediaType.APPLICATION_JSON)
                    ).andReturn()
                    .response
        then:
            response.status == HttpStatus.OK.value()
        and:
            verifyAll(objectMapper.readValue(response.contentAsString, TransactionDTO[]).toList()) {
                it.size() == 1
                it.every { it.accountTypeName == CURRENCY_ACCOUNT_TYPE_NAME
                        && it.customerFirstName == SECOND_CUSTOMER_FIRST_NAME
                        && it.customerLastName == SECOND_CUSTOMER_LAST_NAME
                }
            }
    }

    def "For bad input should return error messages"() {
        when:
        def response = mockMvc
                .perform(get(TRANSACTIONS_ENDPOINT)
                        .param("accountTypes", "AB")
                        .param("customerIds", ",,")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andReturn()
                .response
        then:
        response.status == HttpStatus.BAD_REQUEST.value()
        and:
        verifyAll(objectMapper.readValue(response.contentAsString, String[]).toList()) {
            it.size() == 2
            it.containsAll(
                    "Account types should follow pattern [0-9],[0-9] or [0-9] or ALL",
                    "Customers should follow pattern [0-9],[0-9] or [0-9] or ALL"
            )
        }
    }
}
