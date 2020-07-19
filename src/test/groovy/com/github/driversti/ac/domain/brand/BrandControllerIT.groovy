package com.github.driversti.ac.domain.brand

import static com.github.driversti.ac.IOUtil.readFromJson
import static com.github.driversti.ac.common.RegexpConstants.WRONG_BRAND_NAME
import static java.time.Month.JUNE
import static java.time.Month.MAY
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

import com.github.driversti.ac.IntegrationTest
import com.github.driversti.ac.errorhandling.ApiError
import java.time.LocalDate
import org.springframework.http.HttpStatus
import spock.lang.Unroll

class BrandControllerIT extends IntegrationTest {

    @Unroll
    def "should be possible to add a new brand with required fields"(BrandAddRequest dto) {
        given: "create request"
        def request = post("/brand/add")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))

        when: "try to create the brand"
        def response = mockMvc.perform(request)
                .andReturn().response

        then: "the successfully created brand expected"
        response.status == HttpStatus.OK.value()
        BrandAddResponse addedBrand = readFromJson(response.contentAsString, BrandAddResponse)
        addedBrand.id != null
        addedBrand.name == dto.name
        addedBrand.founded == dto.founded
        addedBrand.defunct == dto.defunct

        where:
        dto                                                                                     | _
        new BrandAddRequest("Horch", null, null)                                                | _
        new BrandAddRequest("Horch", LocalDate.of(1904, MAY, 10), null)                         | _
        new BrandAddRequest("Horch", LocalDate.of(1904, MAY, 10), LocalDate.of(1932, JUNE, 29)) | _
    }

    @Unroll
    def "should prohibit creation of a brand and return all violations"() {
        given: "create a request"
        def request = post("/brand/add")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))

        when: "try to create the brand"
        def response = mockMvc.perform(request)
                .andReturn().response

        then: "return BAD_REQUEST response with all violations"
        response.status == HttpStatus.BAD_REQUEST.value()
        ApiError apiError = readFromJson(response.contentAsString, ApiError)
        apiError.errorCode == HttpStatus.BAD_REQUEST.value()
        apiError.message == "Validation error"
        def validationErrors = apiError.validationErrors
        validationErrors.size() == 1
        validationErrors[0].field == fieldName
        validationErrors[0].message == errorMessage
        validationErrors[0].rejectedValue == rejectedValue

        where:
        dto                                         | fieldName | errorMessage                 | rejectedValue
        new BrandAddRequest()                       | "name"    | "Brand name cannot be empty" | null
        new BrandAddRequest(name: "aa")             | "name"    | WRONG_BRAND_NAME             | "aa"
        new BrandAddRequest(name: 1234)             | "name"    | WRONG_BRAND_NAME             | "1234"
        new BrandAddRequest(name: "12aa")           | "name"    | WRONG_BRAND_NAME             | "12aa"
        new BrandAddRequest(name: "abc12")          | "name"    | WRONG_BRAND_NAME             | "abc12"
        new BrandAddRequest(name: "abc 12")         | "name"    | WRONG_BRAND_NAME             | "abc 12"
        new BrandAddRequest(name: "abc  12")        | "name"    | WRONG_BRAND_NAME             | "abc  12"
        new BrandAddRequest(name: "abc-12")         | "name"    | WRONG_BRAND_NAME             | "abc-12"
        new BrandAddRequest(name: "abc - 12")       | "name"    | WRONG_BRAND_NAME             | "abc - 12"
        new BrandAddRequest(name: " Mercedes-Benz") | "name"    | WRONG_BRAND_NAME             | " Mercedes-Benz"
        new BrandAddRequest(name: "Mercedes_Benz")  | "name"    | WRONG_BRAND_NAME             | "Mercedes_Benz"
    }
}
