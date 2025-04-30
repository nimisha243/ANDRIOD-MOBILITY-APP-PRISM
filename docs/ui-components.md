# UI Components Documentation

## Overview

The Mobility App uses Material Design components and custom views to create a modern, user-friendly interface. This document details the UI components and their usage.

## Screen Layouts

### Home Screen
```
┌─────────────────────────────────┐
│           App Bar              │
├─────────────────────────────────┤
│                                 │
│    Welcome Message              │
│    Quick Actions                │
│                                 │
│    Recent Rides                 │
│    ┌─────────────────────┐     │
│    │ Ride Card           │     │
│    └─────────────────────┘     │
│                                 │
│    Upcoming Rides              │
│    ┌─────────────────────┐     │
│    │ Ride Card           │     │
│    └─────────────────────┘     │
│                                 │
└─────────────────────────────────┘
```

### Schedule Screen
```
┌─────────────────────────────────┐
│           App Bar              │
├─────────────────────────────────┤
│                                 │
│    Schedule List               │
│    ┌─────────────────────┐     │
│    │ Schedule Card       │     │
│    └─────────────────────┘     │
│                                 │
│    FAB: Add Schedule           │
│                                 │
└─────────────────────────────────┘
```

### Ride Details Screen
```
┌─────────────────────────────────┐
│           App Bar              │
├─────────────────────────────────┤
│                                 │
│    Ride Status                 │
│    Map View                    │
│                                 │
│    Ride Information            │
│    ┌─────────────────────┐     │
│    │ Details Card        │     │
│    └─────────────────────┘     │
│                                 │
│    Action Buttons              │
│    ┌─────────┐  ┌─────────┐   │
│    │ Cancel  │  │  Pay    │   │
│    └─────────┘  └─────────┘   │
│                                 │
└─────────────────────────────────┘
```

## Custom Components

### RideCard
```kotlin
class RideCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {
    // Implementation
}
```

Usage:
```xml
<com.example.mobilityapp.ui.components.RideCard
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="8dp"
    app:rideStatus="@string/status_scheduled"
    app:rideTime="2024-04-26 14:30"
    app:pickupLocation="123 Main St"
    app:dropoffLocation="456 Oak Ave" />
```

### LocationPicker
```kotlin
class LocationPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    // Implementation
}
```

Usage:
```xml
<com.example.mobilityapp.ui.components.LocationPicker
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="16dp"
    app:label="@string/pickup_location"
    app:hint="@string/enter_location" />
```

### PaymentMethodSelector
```kotlin
class PaymentMethodSelector @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    // Implementation
}
```

Usage:
```xml
<com.example.mobilityapp.ui.components.PaymentMethodSelector
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="16dp"
    app:paymentMethods="@array/payment_methods" />
```

## Material Design Components

### TextInputLayout
```xml
<com.google.android.material.textfield.TextInputLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="16dp"
    style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox">

    <com.google.android.material.textfield.TextInputEditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="@string/notes" />

</com.google.android.material.textfield.TextInputLayout>
```

### MaterialButton
```xml
<com.google.android.material.button.MaterialButton
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="16dp"
    android:text="@string/confirm"
    app:cornerRadius="8dp" />
```

### MaterialCardView
```xml
<com.google.android.material.card.MaterialCardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="8dp"
    app:cardCornerRadius="8dp"
    app:cardElevation="4dp">

    <!-- Card content -->

</com.google.android.material.card.MaterialCardView>
```

## Theme and Styles

### Colors
```xml
<resources>
    <color name="primary">#1976D2</color>
    <color name="primary_dark">#1565C0</color>
    <color name="accent">#FFC107</color>
    <color name="background">#FFFFFF</color>
    <color name="surface">#FFFFFF</color>
    <color name="error">#B00020</color>
    <color name="on_primary">#FFFFFF</color>
    <color name="on_accent">#000000</color>
    <color name="on_background">#000000</color>
    <color name="on_surface">#000000</color>
    <color name="on_error">#FFFFFF</color>
</resources>
```

### Typography
```xml
<resources>
    <style name="TextAppearance.App.Headline1" parent="TextAppearance.MaterialComponents.Headline1">
        <item name="fontFamily">@font/roboto</item>
        <item name="android:textSize">96sp</item>
    </style>

    <style name="TextAppearance.App.Headline2" parent="TextAppearance.MaterialComponents.Headline2">
        <item name="fontFamily">@font/roboto</item>
        <item name="android:textSize">60sp</item>
    </style>

    <!-- More text styles -->
</resources>
```

### Themes
```xml
<resources>
    <style name="Theme.MobilityApp" parent="Theme.MaterialComponents.DayNight.NoActionBar">
        <item name="colorPrimary">@color/primary</item>
        <item name="colorPrimaryDark">@color/primary_dark</item>
        <item name="colorAccent">@color/accent</item>
        <item name="android:windowBackground">@color/background</item>
        <item name="android:statusBarColor">@color/primary_dark</item>
    </style>
</resources>
```

## Animations

### Fade Transition
```xml
<?xml version="1.0" encoding="utf-8"?>
<alpha xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="300"
    android:fromAlpha="0.0"
    android:toAlpha="1.0" />
```

### Slide Transition
```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <translate
        android:duration="300"
        android:fromXDelta="100%"
        android:toXDelta="0%" />
</set>
```

## Accessibility

The app follows Material Design accessibility guidelines:

1. Content descriptions for images
2. Proper text contrast ratios
3. Touch target sizes
4. Screen reader support
5. Keyboard navigation

Example:
```xml
<ImageView
    android:layout_width="24dp"
    android:layout_height="24dp"
    android:contentDescription="@string/close_button_description"
    android:src="@drawable/ic_close" />
```

## Responsive Design

The app supports different screen sizes and orientations:

1. Layout variations for different screen sizes
2. Adaptive layouts using ConstraintLayout
3. Resource qualifiers for different configurations
4. Dynamic text sizing

Example:
```xml
<androidx.constraintlayout.widget.ConstraintLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!-- Responsive layout -->

</androidx.constraintlayout.widget.ConstraintLayout>
```

## Dark Theme Support

The app supports both light and dark themes:

```xml
<style name="Theme.MobilityApp" parent="Theme.MaterialComponents.DayNight.NoActionBar">
    <!-- Light theme attributes -->
</style>

<style name="Theme.MobilityApp" parent="Theme.MaterialComponents.DayNight.NoActionBar">
    <!-- Dark theme attributes -->
</style>
```

## UI Testing

The app includes UI tests using Espresso:

```kotlin
@RunWith(AndroidJUnit4::class)
class RideDetailsScreenTest {
    @Test
    fun testRideDetailsDisplay() {
        // Test implementation
    }
}
``` 