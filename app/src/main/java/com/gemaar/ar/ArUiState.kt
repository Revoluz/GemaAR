package com.gemaar.ar

/**
 * State layar AR yang dipakai HUD.
 *
 * Error izin kamera / ketersediaan ARCore ditangani langsung oleh [ArController]
 * karena tombolnya butuh aksi dari sesi AR (request izin / buka Settings).
 */
sealed interface ArUiState {
    /** Kamera aktif, marker belum pernah terlihat. */
    data object Scanning : ArUiState

    /** Model Saron sedang dimuat. */
    data class ModelLoading(val progress: Float) : ArUiState

    /** Marker sedang tracking dan model siap. */
    data object Placed : ArUiState

    /** Marker pernah terlihat lalu hilang dari kamera. */
    data object MarkerLost : ArUiState

    /** Model atau aset marker gagal dimuat. */
    data class ModelError(val message: String) : ArUiState
}
