# Accident Alert System

An Android and vehicle-telemetry prototype exploring how a mobile companion could surface diagnostic data and support location-aware emergency workflows.

> **Project status:** archival prototype. The application targets an older Android toolchain and is not production-ready or intended for real emergency use.

## Product concept

After a crash or vehicle fault, useful signals are often split across the vehicle, the driver's phone, and emergency contacts. This prototype connects those surfaces in one mobile workflow:

```text
Paired vehicle device → Bluetooth telemetry → Android dashboard
                                              ├─ Diagnostic details
                                              ├─ Device location
                                              ├─ SOS contacts and SMS
                                              └─ Follow-me reminder
```

## Prototype capabilities

- Connects to a paired Bluetooth device using the Serial Port Profile UUID.
- Parses a four-value, comma-separated telemetry message.
- Displays telemetry through a drill-down dashboard of vehicle systems.
- Reads device latitude and longitude through Android location providers.
- Lets a user select and store SOS contacts locally.
- Sends SMS alerts through the device's native SMS service.
- Schedules a follow-me reminder with Android's alarm service.

## Technology

- Java
- Android SDK 26; minimum SDK 21
- Classic Bluetooth sockets
- Android location, contacts, SMS, alarms, and SQLite APIs
- Gradle Android plugin 3.2.1 and legacy Android Support libraries

## Explore the code

- [`MainActivity.java`](app/src/main/java/xyz/abhishekshah/accidentalertsystem/MainActivity.java) coordinates Bluetooth connectivity, telemetry parsing, location, and navigation.
- [`Dashboard.java`](app/src/main/java/xyz/abhishekshah/accidentalertsystem/Dashboard.java) routes telemetry values into diagnostic detail views.
- [`ContactsActivity.java`](app/src/main/java/xyz/abhishekshah/accidentalertsystem/ContactsActivity.java) manages emergency contacts and SMS behavior.
- [`GPSTracker.java`](app/src/main/java/xyz/abhishekshah/accidentalertsystem/GPSTracker.java) wraps network and GPS location providers.

## Build notes

The repository preserves the original prototype. Building it today may require an older Android Studio/JDK environment or a deliberate migration from JCenter, Android Support libraries, and SDK 26 to current Android tooling.

The application also requires runtime access to Bluetooth, location, contacts, and SMS. Use test devices and test contacts only.

## What this project demonstrates

The project is an early exploration of end-to-end product prototyping across hardware signals, mobile interaction design, data interpretation, and safety-sensitive user workflows. A production implementation would require validated crash detection, explicit consent and permission flows, delivery monitoring, security review, accessibility testing, and partnerships with emergency-service providers.
