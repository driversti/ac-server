package com.github.driversti.ac.domain.brand

import static com.github.driversti.ac.common.RegexpConstants.WRONG_BRAND_NAME
import static com.github.driversti.ac.domain.brand.BrandTestData.DEFUNCT
import static com.github.driversti.ac.domain.brand.BrandTestData.FOUNDED
import static com.github.driversti.ac.domain.brand.BrandTestData.brandAddBuilder
import static com.github.driversti.ac.domain.brand.BrandTestData.brandBuilder
import static com.github.driversti.ac.domain.brand.BrandTestData.fromEntity
import static com.github.driversti.ac.domain.brand.BrandTestData.getBRAND_NAME
import static org.springframework.http.HttpMethod.GET

import com.github.driversti.ac.IntegrationTest
import com.github.driversti.ac.errorhandling.ApiError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import spock.lang.Unroll

class BrandControllerIT extends IntegrationTest {


    @Autowired
    private BrandRepository repository

    @Unroll
    def "should be possible to add a new brand with required fields"(BrandAddRequest dto) {
        expect:
        def response = testRestTemplate.postForEntity("/brand/add", dto, BrandAddResponse)
        response.with {
            statusCode == HttpStatus.OK
            body.with {
                id != null
                name == dto.name
                founded == dto.founded
                defunct == dto.defunct
            }
        }

        where:
        dto                                               | _
        new BrandAddRequest(BRAND_NAME, null, null)       | _
        new BrandAddRequest(BRAND_NAME, FOUNDED, null)    | _
        new BrandAddRequest(BRAND_NAME, FOUNDED, DEFUNCT) | _
    }

    def "should not be possible to add the same brand more that one time"() {
        given:
        final Brand brand = brandBuilder().build()
        repository.save(brand)
        final BrandAddRequest dto = fromEntity(brand)

        expect:
        def response = testRestTemplate.postForEntity("/brand/add", dto, ApiError)
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body.message == "Brand $brand.name already exists"
    }

    @Unroll
    def "should not be possible to save a new brand with wrong name"() {
        expect:
        def response = testRestTemplate.postForEntity("/brand/add", dto, ApiError)
        response.with {
            statusCode == HttpStatus.BAD_REQUEST
            body.with {
                message == "Validation error"
                validationErrors.size() == 1
                validationErrors[0].with {
                    field == fieldName
                    message == errorMessage
                    rejectedValue == rejectedValue
                }
            }
        }

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

    def "should not be possible to save a new brand if the defunct date is before the founded date"() {
        given:
        def brandAddRequest = brandAddBuilder().founded(DEFUNCT).defunct(FOUNDED).build()

        when:
        def response = testRestTemplate.postForEntity("/brand/add", brandAddRequest, ApiError)

        then:
        response.with {
            statusCode == HttpStatus.BAD_REQUEST
            body.with {
                errorCode == HttpStatus.BAD_REQUEST.value()
                message == "Validation error"
                validationErrors[0].message == "The founded date must be before the defunct date"
                validationErrors[0].field == null
                validationErrors[0].rejectedValue == null
            }
        }
    }

    def "should return all brands"() {
        given: "a few brands in the DB"
        def b1 = brandBuilder().name("Opel").build()
        def b2 = brandBuilder().name("KIA").build()
        def b3 = brandBuilder().name("Mazda").build()
        def brands = [b1, b2, b3]
        repository.saveAll(brands)

        expect:
        def responseType = new ParameterizedTypeReference<List<BrandOverviewResponse>>() {}
        def response = testRestTemplate.exchange("/brand/overview", GET, HttpEntity.EMPTY, responseType)
        response.with {
            assert statusCode == HttpStatus.OK
            body.with {
                assert it.size() == brands.size()
                assert it*.name == brands*.name
            }
        }
    }
}
