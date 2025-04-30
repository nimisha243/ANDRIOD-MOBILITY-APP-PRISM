# API Documentation

## Overview

The Mobility App uses a RESTful API for communication with the backend server. The API is implemented using Retrofit and follows REST principles.

## Base URL

```
https://api.mobility-app.com/v1
```

## Authentication

All API requests require authentication using a JWT token:

```http
Authorization: Bearer <token>
```

## API Endpoints

### User Management

#### Login
```http
POST /auth/login
Content-Type: application/json

{
    "email": "string",
    "password": "string"
}

Response:
{
    "token": "string",
    "user": {
        "id": "string",
        "name": "string",
        "email": "string",
        "phone": "string"
    }
}
```

#### Register
```http
POST /auth/register
Content-Type: application/json

{
    "name": "string",
    "email": "string",
    "password": "string",
    "phone": "string"
}

Response:
{
    "token": "string",
    "user": {
        "id": "string",
        "name": "string",
        "email": "string",
        "phone": "string"
    }
}
```

### Ride Management

#### Schedule Ride
```http
POST /rides/schedule
Content-Type: application/json

{
    "pickupLocation": {
        "latitude": number,
        "longitude": number,
        "address": "string"
    },
    "dropoffLocation": {
        "latitude": number,
        "longitude": number,
        "address": "string"
    },
    "scheduledTime": "string (ISO 8601)",
    "vehicleType": "string",
    "notes": "string"
}

Response:
{
    "id": "string",
    "status": "string",
    "estimatedPrice": number,
    "driver": {
        "id": "string",
        "name": "string",
        "phone": "string",
        "vehicle": {
            "type": "string",
            "model": "string",
            "plateNumber": "string"
        }
    }
}
```

#### Get Ride Details
```http
GET /rides/{rideId}

Response:
{
    "id": "string",
    "status": "string",
    "pickupLocation": {
        "latitude": number,
        "longitude": number,
        "address": "string"
    },
    "dropoffLocation": {
        "latitude": number,
        "longitude": number,
        "address": "string"
    },
    "scheduledTime": "string (ISO 8601)",
    "estimatedPrice": number,
    "driver": {
        "id": "string",
        "name": "string",
        "phone": "string",
        "vehicle": {
            "type": "string",
            "model": "string",
            "plateNumber": "string"
        }
    }
}
```

#### Cancel Ride
```http
POST /rides/{rideId}/cancel

Response:
{
    "success": boolean,
    "message": "string"
}
```

### Payment

#### Process Payment
```http
POST /payments/process
Content-Type: application/json

{
    "rideId": "string",
    "amount": number,
    "currency": "string",
    "paymentMethod": {
        "type": "string",
        "token": "string"
    }
}

Response:
{
    "id": "string",
    "status": "string",
    "amount": number,
    "currency": "string",
    "timestamp": "string (ISO 8601)"
}
```

#### Save Payment Method
```http
POST /payments/methods
Content-Type: application/json

{
    "type": "string",
    "token": "string",
    "isDefault": boolean
}

Response:
{
    "id": "string",
    "type": "string",
    "last4": "string",
    "expiryMonth": number,
    "expiryYear": number,
    "isDefault": boolean
}
```

### Location Tracking

#### Update Driver Location
```http
POST /rides/{rideId}/location
Content-Type: application/json

{
    "latitude": number,
    "longitude": number,
    "timestamp": "string (ISO 8601)"
}

Response:
{
    "success": boolean
}
```

#### Get Driver Location
```http
GET /rides/{rideId}/location

Response:
{
    "latitude": number,
    "longitude": number,
    "timestamp": "string (ISO 8601)"
}
```

## Error Responses

All error responses follow this format:

```http
{
    "error": {
        "code": "string",
        "message": "string",
        "details": object
    }
}
```

Common error codes:
- `400`: Bad Request
- `401`: Unauthorized
- `403`: Forbidden
- `404`: Not Found
- `500`: Internal Server Error

## Rate Limiting

API requests are limited to:
- 100 requests per minute per IP
- 1000 requests per hour per user

## WebSocket Events

Real-time updates are provided through WebSocket connections:

```javascript
ws://api.mobility-app.com/v1/ws
```

Events:
- `ride.status_update`
- `driver.location_update`
- `payment.status_update`

## Implementation Example

```kotlin
interface MobilityApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("rides/schedule")
    suspend fun scheduleRide(@Body request: ScheduleRideRequest): Response<RideResponse>

    @GET("rides/{rideId}")
    suspend fun getRideDetails(@Path("rideId") rideId: String): Response<RideResponse>

    @POST("rides/{rideId}/cancel")
    suspend fun cancelRide(@Path("rideId") rideId: String): Response<CancelRideResponse>
}
```

## Testing

API endpoints can be tested using:
- Postman collections
- Unit tests with MockWebServer
- Integration tests with the actual API

## Security

- All requests must use HTTPS
- JWT tokens expire after 24 hours
- Sensitive data is encrypted
- Rate limiting is enforced
- Input validation is performed 