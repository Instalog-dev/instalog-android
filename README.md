# Instalog SDK Documentation

## Installation
Add the Instalog dependency to your project.

## Initialization
Initialize the SDK in your Application class or MainActivity:

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Instalog.getInstance().initialize(
            key = "YOUR_API_KEY",
            context = this
        )
    }
}
```

## Basic Usage

### 1. Log Events
Track user actions and events in your app:

```kotlin
val instalog = Instalog.getInstance()
instalog.logEvent(context, InstalogLogModel(
    event = "button_clicked",
    params = mapOf("button_name" to "submit")
))
```

### 2. User Identification
Identify users to track their activity across sessions:

```kotlin
instalog.identifyUser("user123")
```

### 3. Feedback Collection
Show a feedback modal to collect user feedback:

```kotlin
instalog.showFeedbackModal(context)
```

### 4. Crash Reporting
Crash reporting is automatically enabled after initialization. No additional setup is required.

## Advanced Features

### Custom Alert Handler
Implement a custom alert handler to control how alerts are displayed:

```kotlin
class CustomAlertHandler : InstalogAlertDialogHandler {
    override fun show(data: InstalogAlertData) {
        // Implement your custom alert display logic
    }
}
```

// Set the custom handler during initialization
```kotlin
Instalog.getInstance().initialize(
    key = "YOUR_API_KEY",
    context = this,
    handler = CustomAlertHandler()
)
```
### Manual Crash Simulation
Simulate crashes for testing purposes:
```kotlin
Instalog.crash.simulateCrash()
```
## Best Practices

1. Initialize the SDK as early as possible in your application lifecycle
2. Identify users as soon as they log in
3. Use descriptive event names and consistent parameter names
4. Test crash reporting in a controlled environment before production
5. Handle user feedback promptly and professionally

## Troubleshooting

### Common Issues

1. SDK not initialized
    - Ensure you call `initialize()` before using other methods
    - Verify your API key is correct

2. Events not being tracked
    - Check your internet connection
    - Verify event names and parameters are valid

3. Crash reports not appearing
    - Ensure the SDK is initialized before any crashes occur
    - Check for proper permissions

For additional support, contact our developer support team at support@instalog.com

## API Reference

### Instalog Class

| Method | Description |
|--------|-------------|
| `initialize(key: String, context: Context, handler: InstalogAlertDialogHandler?)` | Initializes the SDK |
| `logEvent(context: Context, log: InstalogLogModel)` | Logs an event |
| `identifyUser(id: String)` | Identifies the current user |
| `showFeedbackModal(context: Context)` | Shows feedback collection UI |
| `getInstance(): Instalog` | Gets the singleton instance |

### Models

- `InstalogLogModel`: Represents a log event
- `InstalogCrashModel`: Represents crash data
- `InstalogFeedbackModel`: Represents user feedback

## License
The Instalog SDK is licensed under the MIT License. See LICENSE file for details.
