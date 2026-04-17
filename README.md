# 📰 News Reader App - Praktikum PAM Minggu 6

Aplikasi **News Reader** ini dikembangkan sebagai tugas praktikum mata kuliah Pengembangan Aplikasi Mobile (PAM). Aplikasi ini mendemonstrasikan integrasi REST API menggunakan Ktor Client dalam ekosistem Kotlin Multiplatform.

## Identitas Mahasiswa
- **Nama:** Pradana Figo Ariasya
- **NIM:** 123140063
- **Program Studi:** Teknik Informatika
- **Institusi:** Institut Teknologi Sumatera (ITERA)

## Fitur Aplikasi
1. **Fetching Data Berita:** Mengambil data berita secara real-time dari NewsAPI.
2. **Manajemen UI States:** Implementasi status *Loading* (shimmer/progress), *Success* (menampilkan list), dan *Error* (penanganan gagal koneksi).
3. **Detail Screen:** Navigasi untuk melihat isi berita secara lengkap.
4. **Pull to Refresh:** Fitur untuk memperbarui berita dengan gestur tarik bawah.
5. **Search & Category Filter:** Memungkinkan pengguna mencari berita berdasarkan kata kunci atau kategori tertentu (AI, Mobile, Google, dll).
6. **Profil & Portfolio:** Halaman profil kustom yang menampilkan identitas mahasiswa dan proyek unggulan.

## Arsitektur & Teknologi
- **Bahasa:** Kotlin
- **Framework UI:** Jetpack Compose / Compose Multiplatform
- **Arsitektur:** MVVM (Model-View-ViewModel) dengan Repository Pattern
- **Networking:** Ktor Client
- **Serialization:** Kotlinx Serialization (JSON)
- **Image Loading:** Coil 3

## Dokumentasi (Screenshot)

| Loading State | Success State | Error State |
|:---:|:---:|:---:|
| <img width="720" height="1600" alt="image" src="https://github.com/user-attachments/assets/478e9305-ca57-4298-b6b7-e93b4f593278" />| <img width="720" height="1600" alt="image" src="https://github.com/user-attachments/assets/152fee0e-4e83-41f2-bfdf-748c132cc02a" />| <img width="720" height="1600" alt="image" src="https://github.com/user-attachments/assets/0f5b0e49-6f0d-42e6-b639-a43765d6aa22" />|

| Detail Screen | Menu Favorite| Hamburger Menu |
|:---:|:---:|:---:|
| <img width="720" height="1600" alt="image" src="https://github.com/user-attachments/assets/0e0045ef-2e2c-4add-8da8-66a228a44f18" />| <img width="720" height="1600" alt="image" src="https://github.com/user-attachments/assets/1204882c-757a-45a5-933e-2794093ed7ba" />| <img width="720" height="1600" alt="image" src="https://github.com/user-attachments/assets/74e7c0aa-dbaa-4540-bb31-bb67a2686b64" />|

##  Video Demo
Video demo fitur aplikasi dapat diakses melalui tautan berikut:
https://github.com/user-attachments/assets/d79af54c-b025-413d-9977-bae6ce7e70a9
---
© 2026 Pradana Figo Ariasya - 123140063.
