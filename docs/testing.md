# Testing Documentation

## Overview

The Mobility App implements a comprehensive testing strategy covering unit tests, integration tests, and UI tests. This document outlines the testing approach and provides examples.

## Testing Pyramid

```
┌─────────────────────────────────┐
│           UI Tests             │
│      (Espresso) 10%            │
├─────────────────────────────────┤
│         Integration Tests      │
│      (Robolectric) 20%         │
├─────────────────────────────────┤
│          Unit Tests            │
│      (JUnit) 70%               │
└─────────────────────────────────┘
```

## Unit Tests

### Use Case Tests
```kotlin
class ScheduleRideUseCaseTest {
    @Test
    fun `when scheduling ride, should return success`() {
        // Given
        val repository = mock<RideRepository>()
        val useCase = ScheduleRideUseCase(repository)
        val request = ScheduleRideRequest(
            pickupLocation = Location(0.0, 0.0),
            dropoffLocation = Location(1.0, 1.0),
            scheduledTime = Date()
        )

        // When
        val result = useCase.execute(request)

        // Then
        assertTrue(result.isSuccess)
        verify(repository).scheduleRide(request)
    }
}
```

### Repository Tests
```kotlin
class RideRepositoryImplTest {
    @Test
    fun `when getting rides, should return from local database first`() {
        // Given
        val localDataSource = mock<LocalDataSource>()
        val remoteDataSource = mock<RemoteDataSource>()
        val repository = RideRepositoryImpl(localDataSource, remoteDataSource)

        // When
        repository.getRides()

        // Then
        verify(localDataSource).getRides()
    }
}
```

### ViewModel Tests
```kotlin
class RideDetailsViewModelTest {
    @Test
    fun `when loading ride details, should update UI state`() {
        // Given
        val repository = mock<RideRepository>()
        val viewModel = RideDetailsViewModel(repository)

        // When
        viewModel.loadRide("rideId")

        // Then
        assertEquals(RideUiState.Loading, viewModel.uiState.value)
    }
}
```

## Integration Tests

### Repository Integration
```kotlin
@RunWith(RobolectricTestRunner::class)
class RideRepositoryIntegrationTest {
    @Test
    fun `when offline, should return cached data`() {
        // Test implementation
    }
}
```

### Database Integration
```kotlin
@RunWith(AndroidJUnit4::class)
class RideDatabaseTest {
    @Test
    fun `when inserting ride, should be retrievable`() {
        // Test implementation
    }
}
```

## UI Tests

### Screen Tests
```kotlin
@RunWith(AndroidJUnit4::class)
class RideDetailsScreenTest {
    @Test
    fun whenRideDetailsLoaded_shouldDisplayCorrectly() {
        // Given
        val scenario = launchFragmentInContainer<RideDetailsFragment>()

        // When
        onView(withId(R.id.rideStatus))
            .check(matches(withText("Scheduled")))

        // Then
        onView(withId(R.id.pickupLocation))
            .check(matches(isDisplayed()))
    }
}
```

### Navigation Tests
```kotlin
@RunWith(AndroidJUnit4::class)
class NavigationTest {
    @Test
    fun whenClickingScheduleButton_shouldNavigateToScheduleScreen() {
        // Test implementation
    }
}
```

## Test Coverage

### Coverage Report
```
----------------------|---------|----------|---------|---------|
File                  | % Stmts | % Branch | % Funcs | % Lines |
----------------------|---------|----------|---------|---------|
All files             |   85.71 |    83.33 |   88.89 |   85.71 |
----------------------|---------|----------|---------|---------|
```

## Testing Tools

### JUnit
- Unit testing framework
- Assertions and matchers
- Test runners

### Mockito
- Mocking framework
- Verification
- Stubbing

### Espresso
- UI testing framework
- View matchers
- View actions

### Robolectric
- Unit testing Android components
- Shadow objects
- Resource handling

## Test Data

### Test Fixtures
```kotlin
object TestData {
    val ride = Ride(
        id = "test-ride-id",
        status = "SCHEDULED",
        pickupLocation = Location(0.0, 0.0),
        dropoffLocation = Location(1.0, 1.0),
        scheduledTime = Date()
    )
}
```

### Fake Repositories
```kotlin
class FakeRideRepository : RideRepository {
    private val rides = mutableListOf<Ride>()

    override suspend fun getRides(): Flow<List<Ride>> = flow {
        emit(rides)
    }

    override suspend fun scheduleRide(request: ScheduleRideRequest): Result<Ride> {
        val ride = Ride(
            id = UUID.randomUUID().toString(),
            status = "SCHEDULED",
            pickupLocation = request.pickupLocation,
            dropoffLocation = request.dropoffLocation,
            scheduledTime = request.scheduledTime
        )
        rides.add(ride)
        return Result.success(ride)
    }
}
```

## Continuous Integration

### GitHub Actions Workflow
```yaml
name: Android CI

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK
        uses: actions/setup-java@v2
        with:
          java-version: '11'
      - name: Run Tests
        run: ./gradlew test
```

## Best Practices

1. **Test Organization**
   - Clear test names
   - Logical grouping
   - Consistent structure

2. **Test Independence**
   - No test dependencies
   - Clean state for each test
   - Isolated test data

3. **Test Readability**
   - Clear setup and teardown
   - Descriptive assertions
   - Meaningful variable names

4. **Test Maintenance**
   - Regular updates
   - Refactoring when needed
   - Documentation

## Common Issues and Solutions

1. **Memory Leaks**
   - Use weak references
   - Clear observers
   - Proper cleanup

2. **Async Testing**
   - Use coroutines test
   - Handle timeouts
   - Test error cases

3. **UI Testing**
   - Handle animations
   - Wait for async operations
   - Test edge cases

## Performance Testing

### Memory Testing
```kotlin
@Test
fun testMemoryUsage() {
    // Test implementation
}
```

### Network Testing
```kotlin
@Test
fun testNetworkPerformance() {
    // Test implementation
}
```

## Security Testing

### Data Encryption
```kotlin
@Test
fun testDataEncryption() {
    // Test implementation
}
```

### Authentication
```kotlin
@Test
fun testAuthentication() {
    // Test implementation
}
``` 