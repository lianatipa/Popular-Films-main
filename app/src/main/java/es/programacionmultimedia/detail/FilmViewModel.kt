package es.programacionmultimedia.detail

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import es.programacionmultimedia.domain.usecase.GetFilmUseCase
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject
// digunakan untuk memberi tahu Hilt bahwa kelas ini adalah bagian dari arsitektur ViewModel.
@HiltViewModel
class FilmViewModel @Inject constructor(
    private val useCase: GetFilmUseCase
) : ViewModel(), LifecycleObserver {

    private val filmLiveData = MutableLiveData<FilmDataView>()
    val film: LiveData<FilmDataView> = filmLiveData
    var job: Job? = null
    //digunakan untuk memuat informasi film
    fun loadFilm(id: Int) {
        val language = Locale.getDefault().language

        job = CoroutineScope(Dispatchers.IO).launch {
            val loadedFilm = useCase.execute(id, language)
            withContext(Dispatchers.Main) {
                //berfungsi menyimpan informasi film
                loadedFilm?.let {
                    //berfungsi untuk diakses oleh kelas luar untuk mengamati perubahan data film
                    filmLiveData.value = FilmDataView(
                        it.id,
                        it.title,
                        it.description,
                        it.imageUrl,
                        it.rating,
                        it.director,
                        it.trailerId
                    )
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        //digunakan untuk menyimpan pekerjaan yang sedang berjalan. Pekerjaan ini akan digunakan untuk menjalankan tugas asinkron dengan menggunakan coroutine.
        job?.cancel()
    }
}