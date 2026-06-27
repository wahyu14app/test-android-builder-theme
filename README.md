# Android Builder Theme

Proyek Android sederhana yang dapat dibangun melalui GitHub Actions dan dipakai sebagai template untuk integrasi webhook QianPulsa white-label.

## Fitur
- Menerima input dari workflow_dispatch: sellerId, appName, themeId, themeVersion, themeColor, callbackUrl
- Menggunakan buildConfigField dan resValue untuk mengirim parameter ke aplikasi
- Menghasilkan artifact APK release di GitHub Actions
- Mengirim callback sukses/gagal ke URL QianPulsa dengan header x-hub-signature-256

## Panduan Integrasi GitHub Action Webhook (QianPulsa White-Label)

Sistem ini berfungsi untuk menerima permintaan build aplikasi Android dari platform utama QianPulsa, memproses build, lalu mengirimkan webhook callback kembali ke server QianPulsa dengan hasil URL APK.

### 1. Menerima Data dari Server QianPulsa (Trigger)
Server QianPulsa akan memanggil repositori ini menggunakan GitHub Actions REST API melalui workflow_dispatch. Data payload yang dikirim dalam object inputs adalah:

- sellerId: ID unik seller/toko
- appName: Nama aplikasi yang diatur oleh seller
- themeId: ID tema
- themeVersion: Versi tema
- themeColor: Kode warna Hex untuk tema aplikasi
- callbackUrl: URL server QianPulsa tujuan callback

### 2. Workflow GitHub Actions
Workflow yang tersedia di [.github/workflows/build.yml](.github/workflows/build.yml) sudah menangkap input tersebut dan menjalankan build APK.

Setelah build selesai, workflow akan mengirim callback ke URL yang diterima dari input callbackUrl dengan header:

- x-hub-signature-256: isi dengan nilai secret yang disimpan di GitHub Repository Secret

### 3. Secret yang Wajib Disiapkan
Di GitHub repository, buat repository secret bernama:

- QIANPULSA_WEBHOOK_SECRET

Nilainya harus sama dengan nilai GITHUB_WEBHOOK_SECRET di environment/backend QianPulsa. Jika belum diset, gunakan nilai fallback:

- fallback_secret_qianpulsa_123

### 4. Format Callback
Callback HTTP POST harus dikirim ke URL yang diterima dari input callbackUrl.

Payload sukses:

```json
{
  "sellerId": "<seller-id>",
  "status": "SUCCESS",
  "apkUrl": "<url-apk>",
  "themeId": "<theme-id>",
  "themeVersion": "<theme-version>"
}
```

Payload gagal:

```json
{
  "sellerId": "<seller-id>",
  "status": "FAILED",
  "errorMessage": "Proses build gagal di tahap kompilasi GitHub Action."
}
```

## Cara menjalankan

### Lokal
```bash
./gradlew assembleRelease -PsellerId='32935e09-7ce2-4df0-832d-bbb23a2ccf49' -PappName='Minat' -PthemeId='theme_gaming' -PthemeVersion='1.0.0' -PthemeColor='#ff0000' -PcallbackUrl='https://your-callback-url'
```

### GitHub Actions
1. Buka tab Actions di repository GitHub Anda.
2. Pilih workflow "Build White-Label Android App".
3. Klik "Run workflow".
4. Isi input yang sesuai.
5. Pastikan repository secret QIANPULSA_WEBHOOK_SECRET telah diset.

Payload dispatch yang Anda kirimkan cocok dengan workflow ini.
