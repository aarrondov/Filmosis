package com.example.filmosis.adapters

import android.content.Intent
import android.opengl.Visibility
import android.os.Environment
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmosis.MainActivity
import com.example.filmosis.R
import com.example.filmosis.config.DatosConexion
import com.example.filmosis.data.model.tmdb.Movie
import com.example.filmosis.dataclass.ListedMovie
import com.example.filmosis.utilities.tmdb.TmdbData
import com.example.filmosis.utilities.app.downloadImage
import java.io.File

class MoviesInListAdapter(private val previousFragment:String?,private val movies: MutableList<ListedMovie>, private val onMovieClick: (ListedMovie) -> Unit, private val isDeleteable: Boolean = false, private val onDeleteMovie: (ListedMovie) -> Unit = {}): RecyclerView.Adapter<MoviesInListAdapter.MovieRowViewHolder>() {
    class MovieRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {
        val movieName : TextView = itemView.findViewById(R.id.itemMovieSearched_movieName)
        val moviePoster: ImageView = itemView.findViewById(R.id.itemMovieSearched_imagePoster)
        val movieDate : TextView = itemView.findViewById(R.id.itemMovieSearched_textReleaseDate)
        val movieVoteAverage : TextView = itemView.findViewById(R.id.itemMovieSearched_textAverageVote)

        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            (v?.context as? MainActivity)?.menuInflater?.inflate(R.menu.movie_row_menu, menu)
            val movieTitle = itemView.getTag(R.id.itemMovieSearched_movieName) as String
            val posterPath = itemView.getTag(R.id.itemMovieSearched_imagePoster) as String
            val imageUrl = DatosConexion.TMDB_IMAGE_BASE_URL + posterPath
            menu?.setHeaderTitle(movieTitle)

            menu?.findItem(R.id.movieRowMenu_shareMovieCover)?.setOnMenuItemClickListener {
                val fileName = movieTitle.replace(" ", "_") + ".jpg"
                val file = File(itemView.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName)

                if (!file.exists()) {
                    downloadImage(itemView.context, imageUrl, fileName)
                }

                if (file.exists()) {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "image/jpeg"

                    val uri = FileProvider.getUriForFile(itemView.context, itemView.context.applicationContext.packageName + ".provider", file)

                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri)

                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    itemView.context.startActivity(Intent.createChooser(shareIntent, "Compartir imagen"))
                } else {
                    Toast.makeText(itemView.context, "La imagen no se ha descargado aún", Toast.LENGTH_SHORT).show()
                }
                true
            }

            menu?.findItem(R.id.movieRowMenu_download_cover)?.setOnMenuItemClickListener {
                downloadImage(itemView.context, imageUrl, movieTitle.replace(" ", "_") + ".jpg")
                true
            }

            menu?.findItem(R.id.movieRowMenu_shareMovie)?.setOnMenuItemClickListener {
                val prefix = itemView.context.getString(R.string.share_movie_prefix)
                val suffix = itemView.context.getString(R.string.share_movie_suffix)
                val shareText = "$prefix $movieTitle$suffix"

                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)

                itemView.context.startActivity(Intent.createChooser(shareIntent, "Compartir texto"))

                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieRowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_searched, parent, false)
        val delete = view.findViewById<ImageButton>(R.id.itemMovieSearched_deleteButton)
        if (previousFragment == "ExpertListsFragment") {
            delete.visibility = View.INVISIBLE
        }
        view.setOnCreateContextMenuListener { menu, v, menuInfo ->
            val activity = view.context as? MainActivity
            activity?.menuInflater?.inflate(R.menu.movie_row_menu, menu)
            menu.setHeaderTitle("Opciones de película")
        }
        return MovieRowViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieRowViewHolder, position: Int) {
        val movie = movies[position]
        // Comprobar si los items se pueden borrar, si no es asi ocultar el botón...
        if (isDeleteable) {
            holder.itemView.findViewById<ImageButton>(R.id.itemMovieSearched_deleteButton).setOnClickListener {
                onDeleteMovie.invoke(movie)
            }
        } else {
            holder.itemView.findViewById<ImageButton>(R.id.itemMovieSearched_deleteButton).visibility = View.INVISIBLE
        }

        if (movie.poster_path.isNotEmpty()) {
            val imageUrl = DatosConexion.TMDB_IMAGE_BASE_URL + movie.poster_path
            Glide.with(holder.itemView.context).load(imageUrl).into(holder.moviePoster)
        }

        holder.movieName.text = movie.title

        holder.movieVoteAverage.text = holder.itemView.context.getString(R.string.puntuacion_media) + movie.averageVote
        holder.movieDate.text = holder.itemView.context.getString(R.string.fecha_lanzamiento) + movie.releaseDate

        holder.itemView.setTag(R.id.itemMovieSearched_movieName, movie.title)
        holder.itemView.setTag(R.id.itemMovieSearched_imagePoster, movie.poster_path)

        holder.itemView.setOnClickListener {
            onMovieClick.invoke(movie)
        }
    }

    fun deleteItemByMovieId(movieId: Int) {
        val position = movies.indexOfFirst { it.id == movieId }
        if (position != -1) {
            movies.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, movies.size)
        }
    }
}