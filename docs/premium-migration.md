# Premium Account Migration (Secure, Opt-In)

This fork now includes a built-in premium migration scaffold for offline-mode accounts.

## Important protocol note

The Minecraft protocol does not provide a reliable server-side way to prove that a joining player owns a Mojang account without an authenticated session flow. Because of that, the built-in implementation intentionally does not claim unsupported verification behavior. It remains opt-in and safe by default.

## Configuration

The following settings are available under the top-level `premium` block:

```yaml
premium:
  enabled: false
  auto-migrate: true
  delete-password-after-migration: true
  protect-premium-usernames: true
  auto-update-username: true
  create-backups: true
```

## Security behavior

- Premium migration is disabled by default.
- Existing AuthMe behavior remains unchanged unless the feature is explicitly enabled.
- Verification failures never trigger migration or account changes.
- The service exposes a public migration API for future integrations.
