# Deployment checklist

## Firebase
- Enable Authentication.
- Create Firestore in production mode.
- Add Android app SHA-256 fingerprints.
- Add `google-services.json`.
- Enable App Check.
- Deploy `firestore.rules`.

## Android
- Test Android 8+ and current Android versions.
- Test precise vs approximate location.
- Test GPS disabled.
- Test airplane mode / no internet.
- Test mock-location developer settings.
- Test clock changes.
- Test permission denial.
- Test battery optimization restrictions.

## Privacy
- Tell employees what location data is collected and why.
- If selfies are enabled, document purpose, retention and access.
- Minimize retention.
- Provide a deletion/retention process.
- Restrict report access to authorized administrators.

## Operational safeguards
- Server timestamps are authoritative.
- Never trust a client boolean such as `isPresent=true`.
- Keep an audit log for admin changes.
- Rate-limit repeated attendance attempts.
