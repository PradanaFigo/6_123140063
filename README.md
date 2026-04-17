# 📰 News Reader App - Praktikum PAM Minggu 6

Aplikasi **News Reader** ini dikembangkan sebagai tugas praktikum mata kuliah Pengembangan Aplikasi Mobile (PAM). Aplikasi ini mendemonstrasikan integrasi REST API menggunakan Ktor Client dalam ekosistem Kotlin Multiplatform.

## 👤 Identitas Mahasiswa
- **Nama:** Pradana Figo Ariasya
- **NIM:** 123140063
- **Program Studi:** Teknik Informatika
- **Institusi:** Institut Teknologi Sumatera (ITERA)

## ✨ Fitur Aplikasi
1. **Fetching Data Berita:** Mengambil data berita secara real-time dari NewsAPI.
2. **Manajemen UI States:** Implementasi status *Loading* (shimmer/progress), *Success* (menampilkan list), dan *Error* (penanganan gagal koneksi).
3. **Detail Screen:** Navigasi untuk melihat isi berita secara lengkap.
4. **Pull to Refresh:** Fitur untuk memperbarui berita dengan gestur tarik bawah.
5. **Search & Category Filter:** Memungkinkan pengguna mencari berita berdasarkan kata kunci atau kategori tertentu (AI, Mobile, Google, dll).
6. **Profil & Portfolio:** Halaman profil kustom yang menampilkan identitas mahasiswa dan proyek unggulan.

## 🛠️ Arsitektur & Teknologi
- **Bahasa:** Kotlin
- **Framework UI:** Jetpack Compose / Compose Multiplatform
- **Arsitektur:** MVVM (Model-View-ViewModel) dengan Repository Pattern
- **Networking:** Ktor Client
- **Serialization:** Kotlinx Serialization (JSON)
- **Image Loading:** Coil 3

## 📸 Dokumentasi (Screenshot)

| Loading State | Success State | Error State |
|:---:|:---:|:---:|
| ![Loading](images/loading.png) | ![Success](images/success.png) | ![Error](images/error.png) |

| Detail Screen | Menu Profile | Hamburger Menu |
|:---:|:---:|:---:|
| ![Detail](images/detail.png) | ![Profile](images/profile.png) | ![Menu](images/menu.png) |

## 🎥 Video Demo
Video demo fitur aplikasi (durasi ~30 detik) dapat diakses melalui tautan berikut:
👉 **[Link Video Demo Praktikum](MASUKKAN_LINK_VIDEO_DRIVE_ATAU_YOUTUBE_DISINI)**

---
© 2026 Pradana Figo Ariasya - 123140063.