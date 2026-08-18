# Smart Accident Alert System

A historical end-to-end prototype combining an Android application with a vehicle-connected hardware concept for automated crash response, emergency communication, and vehicle telemetry.

## Product brief

**Problem:** After a serious road incident, a driver or passenger may be unable to communicate their location and need for assistance. Existing emergency workflows often depend on a person being conscious, having access to a phone, and knowing whom to contact.

**Users:** Drivers and passengers, trusted emergency contacts, and organizations responsible for vehicle safety.

**Concept:** Use a vehicle-connected module to interpret telemetry, send relevant signals to an Android application, and support an SOS workflow. The broader prototype also explored vehicle status, location sharing, a black-box concept, and personal-safety workflows.

## Product system

```text
Vehicle signals / hardware prototype
              ↓
      Telemetry interpretation
              ↓
        Android application
              ↓
 Location + emergency notification
              ↓
       Trusted human response
```

The repository contains the Android portion of the prototype, including emergency contacts, GPS/location behavior, notifications, vehicle-status surfaces, follow-me functionality, and personal-safety screens.

## Key product decisions

- **Automatic detection with a human-safe fallback:** an alerting system should shorten response time without making it difficult to cancel a false trigger.
- **Location as part of the emergency payload:** notifying a contact without actionable location data limits the value of the alert.
- **One connected experience:** crash response, telemetry, and safety features were explored in one mobile product rather than as unrelated demonstrations.
- **Prototype before production hardware:** the mobile workflow helped expose interaction, permission, reliability, and integration questions early.

## What production validation would require

- Controlled tests of crash-detection sensitivity and false-positive rates.
- Explicit cancellation, escalation, and delivery-confirmation behavior.
- Permission, privacy, retention, and encryption reviews for location and vehicle data.
- Offline and low-connectivity behavior.
- Hardware qualification and testing across vehicle types.
- Legal and safety review before representing the product as an emergency service.

## Status

This is an early educational prototype preserved as a product and technical artifact. It is not actively maintained and must not be used as a production emergency or vehicle-safety system.
