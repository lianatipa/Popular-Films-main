package es.programacionmultimedia.detail
//Data class ini memudahkan representasi data film dalam aplikasi, khususnya saat data ini diterima dari sumber eksternal atau saat digunakan dalam komponen-komponen antarmuka pengguna

data class FilmDataView(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val rating: Double,
    val director: String?,
    val videoId: String?
)