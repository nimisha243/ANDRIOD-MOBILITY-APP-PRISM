package com.worklet.mobility.data.beckn

import com.worklet.mobility.data.beckn.model.SearchRequest
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Call
import retrofit2.Response

class BecknRepositoryTest {
    @Test
    fun testSearchRides_success() = runBlocking {
        // Arrange
        val mockService = Mockito.mock(BecknService::class.java)
        val mockCall = Mockito.mock(Call::class.java) as Call<SearchRequest>
        val mockResponse = Response.success(SearchRequest(
            context = SearchRequest.Context(
                country = "IN",
                city = "std:080",
                bap_id = "test-bap",
                bap_uri = "https://test-bap.com",
                transaction_id = "txn-123",
                message_id = "msg-123",
                timestamp = "2024-05-01T12:00:00Z"
            ),
            message = SearchRequest.Message(
                intent = SearchRequest.Intent(
                    fulfillment = SearchRequest.Fulfillment(
                        start = SearchRequest.Location(gps = "12.9716,77.5946"),
                        end = SearchRequest.Location(gps = "12.2958,76.6394")
                    )
                )
            )
        ))
        Mockito.`when`(mockService.searchRides(Mockito.any())).thenReturn(mockCall)
        Mockito.`when`(mockCall.execute()).thenReturn(mockResponse)

        val repository = BecknRepository(mockService)
        val request = mockResponse.body()!!

        // Act
        val result = repository.searchRides(request)

        // Assert
        println("Result: $result")
        assert(result.isSuccess)
    }

    @Test
    fun testConfirmRide_success() = runBlocking {
        // Arrange
        val mockService = Mockito.mock(BecknService::class.java)
        val mockCall = Mockito.mock(Call::class.java) as Call<com.worklet.mobility.data.beckn.model.ConfirmRequest>
        val mockResponse = Response.success(
            com.worklet.mobility.data.beckn.model.ConfirmRequest(
                context = com.worklet.mobility.data.beckn.model.SearchRequest.Context(
                    country = "IN",
                    city = "std:080",
                    bap_id = "test-bap",
                    bap_uri = "https://test-bap.com",
                    transaction_id = "txn-456",
                    message_id = "msg-456",
                    timestamp = "2024-05-01T12:10:00Z"
                ),
                message = com.worklet.mobility.data.beckn.model.ConfirmRequest.Message(
                    order = com.worklet.mobility.data.beckn.model.ConfirmRequest.Order(
                        provider = com.worklet.mobility.data.beckn.model.ConfirmRequest.Provider(id = "provider-1"),
                        items = listOf(com.worklet.mobility.data.beckn.model.ConfirmRequest.Item(id = "item-1")),
                        billing = com.worklet.mobility.data.beckn.model.ConfirmRequest.Billing(
                            name = "Test User",
                            phone = "1234567890",
                            email = "test@example.com"
                        ),
                        fulfillment = com.worklet.mobility.data.beckn.model.ConfirmRequest.Fulfillment(
                            start = com.worklet.mobility.data.beckn.model.SearchRequest.Location(gps = "12.9716,77.5946"),
                            end = com.worklet.mobility.data.beckn.model.SearchRequest.Location(gps = "12.2958,76.6394")
                        )
                    )
                )
            )
        )
        Mockito.`when`(mockService.confirmRide(Mockito.any())).thenReturn(mockCall)
        Mockito.`when`(mockCall.execute()).thenReturn(mockResponse)

        val repository = BecknRepository(mockService)
        val request = mockResponse.body()!!

        // Act
        val result = repository.confirmRide(request)

        // Assert
        println("Confirm Result: $result")
        assert(result.isSuccess)
    }
} 