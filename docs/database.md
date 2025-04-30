# Database Schema Documentation

## Overview

The Mobility App uses Room Database for local storage. The database schema is designed to support offline functionality and efficient data access.

## Entity Relationship Diagram

```
┌──────────────┐      ┌──────────────┐      ┌──────────────┐
│    User      │      │    Ride      │      │   Payment    │
├──────────────┤      ├──────────────┤      ├──────────────┤
│ id           │      │ id           │      │ id           │
│ name         │      │ userId       │──┐   │ rideId       │──┐
│ email        │      │ status       │  │   │ amount       │  │
│ phone        │      │ pickupLat    │  │   │ currency     │  │
│ createdAt    │      │ pickupLng    │  │   │ status       │  │
│ updatedAt    │      │ dropoffLat   │  │   │ createdAt    │  │
└──────────────┘      │ dropoffLng   │  │   └──────────────┘  │
                      │ scheduledTime│  │                      │
                      │ vehicleType  │  │                      │
                      │ notes        │  │                      │
                      │ createdAt    │  │                      │
                      │ updatedAt    │  │                      │
                      └──────────────┘  │                      │
                                       │                      │
                                       │                      │
┌──────────────┐      ┌──────────────┐ │                      │
│  Location    │      │  Vehicle     │ │                      │
├──────────────┤      ├──────────────┤ │                      │
│ id           │      │ id           │ │                      │
│ rideId       │──┐   │ type         │ │                      │
│ latitude     │  │   │ model        │ │                      │
│ longitude    │  │   │ plateNumber  │ │                      │
│ timestamp    │  │   │ createdAt    │ │                      │
│ accuracy     │  │   │ updatedAt    │ │                      │
└──────────────┘  │   └──────────────┘ │                      │
                  │                     │                      │
                  └─────────────────────┘                      │
                                                             │
                                                             │
┌──────────────┐      ┌──────────────┐                      │
│ SavedCard    │      │  Driver      │                      │
├──────────────┤      ├──────────────┤                      │
│ id           │      │ id           │                      │
│ userId       │      │ name         │                      │
│ type         │      │ phone        │                      │
│ last4        │      │ vehicleId    │──┐                   │
│ expiryMonth  │      │ createdAt    │  │                   │
│ expiryYear   │      │ updatedAt    │  │                   │
│ isDefault    │      └──────────────┘  │                   │
│ createdAt    │                        │                   │
│ updatedAt    │                        │                   │
└──────────────┘                        └───────────────────┘
```

## Tables

### User
```sql
CREATE TABLE user (
    id TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    phone TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

### Ride
```sql
CREATE TABLE ride (
    id TEXT PRIMARY KEY,
    user_id TEXT NOT NULL,
    status TEXT NOT NULL,
    pickup_lat REAL NOT NULL,
    pickup_lng REAL NOT NULL,
    dropoff_lat REAL NOT NULL,
    dropoff_lng REAL NOT NULL,
    scheduled_time TIMESTAMP NOT NULL,
    vehicle_type TEXT NOT NULL,
    notes TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);
```

### Location
```sql
CREATE TABLE location (
    id TEXT PRIMARY KEY,
    ride_id TEXT NOT NULL,
    latitude REAL NOT NULL,
    longitude REAL NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    accuracy REAL,
    FOREIGN KEY (ride_id) REFERENCES ride(id)
);
```

### Vehicle
```sql
CREATE TABLE vehicle (
    id TEXT PRIMARY KEY,
    type TEXT NOT NULL,
    model TEXT NOT NULL,
    plate_number TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

### Driver
```sql
CREATE TABLE driver (
    id TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    phone TEXT NOT NULL,
    vehicle_id TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (vehicle_id) REFERENCES vehicle(id)
);
```

### Payment
```sql
CREATE TABLE payment (
    id TEXT PRIMARY KEY,
    ride_id TEXT NOT NULL,
    amount REAL NOT NULL,
    currency TEXT NOT NULL,
    status TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (ride_id) REFERENCES ride(id)
);
```

### SavedCard
```sql
CREATE TABLE saved_card (
    id TEXT PRIMARY KEY,
    user_id TEXT NOT NULL,
    type TEXT NOT NULL,
    last4 TEXT NOT NULL,
    expiry_month INTEGER NOT NULL,
    expiry_year INTEGER NOT NULL,
    is_default BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);
```

## Data Access Objects (DAOs)

### UserDao
```kotlin
@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUser(userId: String): Flow<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}
```

### RideDao
```kotlin
@Dao
interface RideDao {
    @Query("SELECT * FROM ride WHERE user_id = :userId ORDER BY scheduled_time DESC")
    fun getRidesForUser(userId: String): Flow<List<Ride>>

    @Query("SELECT * FROM ride WHERE id = :rideId")
    fun getRide(rideId: String): Flow<Ride>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRide(ride: Ride)

    @Update
    suspend fun updateRide(ride: Ride)

    @Delete
    suspend fun deleteRide(ride: Ride)
}
```

### LocationDao
```kotlin
@Dao
interface LocationDao {
    @Query("SELECT * FROM location WHERE ride_id = :rideId ORDER BY timestamp DESC LIMIT 1")
    fun getLatestLocation(rideId: String): Flow<Location?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)
}
```

## Database Configuration

```kotlin
@Database(
    entities = [
        User::class,
        Ride::class,
        Location::class,
        Vehicle::class,
        Driver::class,
        Payment::class,
        SavedCard::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun rideDao(): RideDao
    abstract fun locationDao(): LocationDao
    abstract fun vehicleDao(): VehicleDao
    abstract fun driverDao(): DriverDao
    abstract fun paymentDao(): PaymentDao
    abstract fun savedCardDao(): SavedCardDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mobility_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
```

## Type Converters

```kotlin
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
```

## Migration Strategy

The database uses Room's migration system to handle schema changes:

```kotlin
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Add new columns or tables
        database.execSQL("ALTER TABLE ride ADD COLUMN estimated_price REAL")
    }
}
```

## Indexing Strategy

```sql
-- User table indexes
CREATE INDEX idx_user_email ON user(email);

-- Ride table indexes
CREATE INDEX idx_ride_user_id ON ride(user_id);
CREATE INDEX idx_ride_scheduled_time ON ride(scheduled_time);

-- Location table indexes
CREATE INDEX idx_location_ride_id ON location(ride_id);
CREATE INDEX idx_location_timestamp ON location(timestamp);
```

## Data Synchronization

The app implements a synchronization strategy to keep local and remote data in sync:

1. Local-first approach for better offline experience
2. Periodic background sync using WorkManager
3. Conflict resolution strategy
4. Data versioning

## Backup and Restore

The database supports backup and restore operations:

1. Automatic backup to cloud storage
2. Manual backup option
3. Restore from backup
4. Data encryption for backups 