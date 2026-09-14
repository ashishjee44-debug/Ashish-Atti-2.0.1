# Trusted backend

Use Firebase Cloud Functions or another trusted backend for:
- setting/removing admin custom claims
- creating employee accounts
- generating monthly reports
- immutable audit logging
- detecting duplicate or impossible attendance patterns
- server-side policy checks

Never ship a service-account private key in the Android app.

Example admin-claim operation (run only from a trusted backend):
`setCustomUserClaims(uid, { admin: true })`
