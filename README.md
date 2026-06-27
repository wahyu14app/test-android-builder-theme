# Android Builder Theme

Proyek Android sederhana yang dapat dibangun melalui GitHub Actions.

## Fitur
- Menerima input dari workflow_dispatch: sellerId, appName, themeId, themeVersion, themeColor, callbackUrl
- Menggunakan buildConfigField dan resValue untuk mengirim parameter ke aplikasi
- Menghasilkan artifact APK release di GitHub Actions

## Cara menjalankan

### Lokal
```bash
./gradlew assembleRelease -PsellerId='32935e09-7ce2-4df0-832d-bbb23a2ccf49' -PappName='Minat' -PthemeId='theme_gaming' -PthemeVersion='1.0.0' -PthemeColor='#ff0000' -PcallbackUrl='https://your-callback-url'
```

### GitHub Actions
1. Buka tab Actions di repository GitHub Anda.
2. Pilih workflow "Build Android App".
3. Klik "Run workflow".
4. Isi input yang sesuai.

Payload dispatch yang Anda kirimkan cocok dengan workflow ini.
