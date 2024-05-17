package com.example.filmosis.fragments
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.ScrollView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmosis.AlarmNotification
import com.example.filmosis.AlarmNotification.Companion.NOTIFICATION_ID

import com.example.filmosis.R
import com.example.filmosis.adapters.CarouselMoviesAdapter
import com.example.filmosis.adapters.ServicioAdapter
import com.example.filmosis.data.access.tmdb.MoviesAccess
import com.example.filmosis.data.model.tmdb.Movie
import com.example.filmosis.dataclass.Network

import com.example.filmosis.utilities.tmdb.TmdbSearchQueries
import com.google.android.material.button.MaterialButton
import java.util.Calendar
import java.util.Locale

/**
 *Fragmento para la pantalla de inicio de la aplicacio.
 * Dara la bienvenida al usuario que haya iniciado sesion
 * y una lista de peliculas.
 * **/
private const val CHANNEL_ID = "mi_canal_id"
class HomeFragment : Fragment() {
    private val moviesAccess = MoviesAccess()

    private lateinit var rvPopular: RecyclerView
    private lateinit var rvUpcoming: RecyclerView
    private lateinit var rvRecommend: RecyclerView
    private lateinit var rvServicios: RecyclerView

    private var moviesListPopulares: ArrayList<Movie> = ArrayList()
    private var moviesListSoon: ArrayList<Movie> = ArrayList()
    private var recommendedMovies: ArrayList<Movie> = ArrayList()
    private var services: ArrayList<Network> = ArrayList()

    private var ids: ArrayList<Int> = ArrayList<Int>().apply {
        addAll(listOf(49, 213, 2739,18,94,50,2740))
    }

    companion object {
        const val MY_CHANNEL_ID = "myChannel"
        const val PERMISSION_REQUEST_CODE = 1001
    }



//    private lateinit var tvRecom: TextView
//    private lateinit var tvProx: TextView
//    private lateinit var tvPopu: TextView

    val channelID = "filmosis"

    private lateinit var moviesAdapter: CarouselMoviesAdapter

    private lateinit var scrollView: ScrollView

    /**
     * Infla la vista del fragmento
     *
     * @param inflater Se emplea para inflar la vista
     * @param container El contenedor al que se pone la vista
     * @param savedInstanceState informacion previamente guardado del fragmento
     * @return la vista inflada del fragmento
     * **/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        createChannel()
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    /**
     * Configura las vistas tras haberse creado la vista del fragmento
     *
     * @param view La vista creada
     * @param savedInstanceState Informacion del estado previamente guardado
     *
     **/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)


        // Creando canal
//        val channelName = "filmosis"
//
//        // Construyendo el canal
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
//            val manager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            manager.createNotificationChannel(channel)
//
//            buttonNotify.setOnClickListener {
//                Log.d("NotificationFragment", "Button clicked")
//
//                val notification = NotificationCompat.Builder(requireContext(), channelID).also { n ->
//                    n.setContentTitle("Notificación")
//                    n.setContentText("Texto de ejemplo")
//                    n.setSmallIcon(R.drawable.logofilmosispremium)
//                }.build()
//
//                val notificationManager = NotificationManagerCompat.from(requireContext())
//                if (ActivityCompat.checkSelfPermission(
//                        requireContext(),
//                        Manifest.permission.POST_NOTIFICATIONS
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                    Log.d("NotificationFragment", "Permission not granted")
//                    // Solicitar permiso
//                    val PERMISSION_REQUEST_CODE = 1001
//                    ActivityCompat.requestPermissions(
//                        requireActivity(),
//                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
//                        PERMISSION_REQUEST_CODE
//                    )
//                } else {
//                    Log.d("NotificationFragment", "Permission granted")
//                    // Mostrar notificación
//                    notificationManager.notify(1, notification)
//                }
//            }
//        }





    }

    private fun scheduleNotification(titulo: String, descripcion: String) {
        val intent = Intent(requireContext().applicationContext,AlarmNotification::class.java)
        intent.putExtra("title",titulo)
        intent.putExtra("content",descripcion)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext().applicationContext,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,Calendar.getInstance().timeInMillis + 7000,pendingIntent)
        Log.d("Notification","Notification set")
    }


    fun createChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                MY_CHANNEL_ID,
                "Filmosis_channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Prueba"
            }

            val notificationManager:NotificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

//    fun createSimpleNotification() {
//        // Crear un Intent para abrir MainActivity y cargar HomeFragment
//        val intent = Intent(requireContext(), MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_IMMUTABLE else 0
//        Log.d("flag", flag.toString()) //67108864 (FLAG_INMUTABLE)
//
//        val pendingIntent: PendingIntent = PendingIntent.getActivity(requireContext(), 0, intent, flag)
//
//        val builder = NotificationCompat.Builder(requireContext(), MY_CHANNEL_ID)
//            .setSmallIcon(R.drawable.logofilmosispremium)
//            .setContentTitle("My title")
//            .setContentText("Esto es un ejemplo")
//            .setStyle(NotificationCompat.BigTextStyle().bigText("Hola que tal Hola que tal Hola que tal Hola que tal Hola que tal Hola que tal Hola que tal Hola que tal Hola que tal "))
//            .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
//            .setContentIntent(pendingIntent)
////            .setContentIntent(PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)) //FATAL EXCEPTION: main
////        //Process: com.example.filmosis, PID: 9556
////        //java.lang.IllegalArgumentException: com.example.filmosis: Targeting S+ (version 31 and above) requires that one of FLAG_IMMUTABLE or FLAG_MUTABLE be specified when creating a PendingIntent.
////        //Strongly consider using FLAG_IMMUTABLE,
//
//        // Mostrar la notificación
//        with(NotificationManagerCompat.from(requireContext())){
//            if (ActivityCompat.checkSelfPermission(
//                    requireContext(),
//                    Manifest.permission.POST_NOTIFICATIONS
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                ActivityCompat.requestPermissions(
//                    requireActivity(),
//                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
//                    PERMISSION_REQUEST_CODE
//                )
//            }
//            notify(1, builder.build())
//        }
//    }




    /**
     * To'do lo que queremos que se visualice sobre el fragmento. La configuracion
     * @param view la vista del fragmento
     * **/

    private fun setup(view: View) {

        val saludoUsuTextView: TextView = view.findViewById(R.id.home_tvUsuario)

        val prefs = requireActivity().getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val username = prefs.getString("username", null)

        initSearchViewAndSearchFilter(view)

        val currentLanguage = Locale.getDefault().language
        Log.d("Idioma", currentLanguage)
        //        scrollView.findViewById<ScrollView>(R.id.scrollViewVerTodo)

        saludoUsuTextView.text = "$username"

        //RecyclerView para las peliculas populares
        rvPopular = view.findViewById(R.id.moviesRecyclerView)
        //para mejorar el rendimiento, le indicamos que el tamano del contenido no cambiara
        rvPopular.setHasFixedSize(true)
        addMoviesToList()

        //RecyclerView para las pelis que saldran proximamente :)
        rvUpcoming = view.findViewById(R.id.moviesSoonRecyclerView)
        rvUpcoming.setHasFixedSize(true)
        addMoviesUpComingToList()

        //RecyclerView para las pelis recomendadas :)
        rvRecommend = view.findViewById(R.id.movieRecomendedRecyclerView)
        rvRecommend.setHasFixedSize(true)
        addMoviesRecommendedToList()

//        servicios
        rvServicios = view.findViewById(R.id.serviciosRecyclerview)
        rvServicios.setHasFixedSize(true)
        cargarServicios()








        //navegar
        val buttonPopu : MaterialButton = view.findViewById(R.id.buttonShowAllPopulares)
        buttonPopu.setOnClickListener {

            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            val nuevoFragmento = VerTodoFragment()
            transaction.replace(R.id.fragmentContainerView, nuevoFragmento)
            transaction.addToBackStack(null)
            transaction.commit()
//            scrollToSection(tvPopu)

        }

        val buttonRecom : MaterialButton = view.findViewById(R.id.buttonShowAllRecomendaciones)
        buttonRecom.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            val nuevoFragmento = VerTodoFragment()

            // Crear un Bundle con los argumentos
            val bundle = Bundle()
            bundle.putInt("scrollTargetId", R.id.tvRecomendados)
            nuevoFragmento.arguments = bundle

            transaction.replace(R.id.fragmentContainerView, nuevoFragmento)
            transaction.addToBackStack(null)
            transaction.commit()
        }


        val button : MaterialButton = view.findViewById(R.id.buttonShowAllProximamente)
        button.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            val nuevoFragmento = VerTodoFragment()
            transaction.replace(R.id.fragmentContainerView, nuevoFragmento)
            transaction.addToBackStack(null)
            transaction.commit()
//            scrollToSection(tvProx)


        }

        val buttonservice : MaterialButton = view.findViewById(R.id.buttonShowAllServices)
        buttonservice.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            val nuevoFragmento = ServiceVerTodoFragment()
            transaction.replace(R.id.fragmentContainerView, nuevoFragmento)
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }

    /**
     * Inicializa la barra de busqeda y el filtro de busqueda
     * @param view la vista del fragmento
     * **/

    private fun initSearchViewAndSearchFilter(view: View) {
        val searchView: SearchView = view.findViewById(R.id.home_searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    performSearch(TmdbSearchQueries.MOVIES_SEARCH, query)
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        val searchFilterImageButton : ImageButton = view.findViewById(R.id.home_searchFilterImageButton)
        searchFilterImageButton.setOnClickListener {
            showSearchFilterMenu(searchFilterImageButton)
        }
    }

    /**
     *Realiza una bsuqeda donde emplea un texto informando al usuario sobre que tiene que ser la busqueda
     *
     * @param searchType El tipo de busqueda a realizar
     * @param hint El texto que informa(pista sugerencia) al usuario
     **/
    private fun performSearchWithQueryHint(searchType: TmdbSearchQueries, hint: String) {
        val searchView: SearchView = requireView().findViewById(R.id.home_searchView)
        searchView.queryHint = hint
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(queryText: String?): Boolean {
                if (!queryText.isNullOrBlank()) {
                    performSearch(searchType, queryText)
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    /**
     * Muestra el menu de filtro de busqueda
     *
     * @param anchor La vista a la se ancla el menu
     * **/
    private fun showSearchFilterMenu(anchor: View) {
        val popupMenu = PopupMenu(requireContext(), anchor)
        popupMenu.menuInflater.inflate(R.menu.search_filter_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.searchFilterMenu_searchMovies -> {
                    performSearchWithQueryHint(TmdbSearchQueries.MOVIES_SEARCH, "Buscar películas")
                    true
                }
                R.id.searchFilterMenu_searchPersons -> {
                    performSearchWithQueryHint(TmdbSearchQueries.PERSONS_SEARCH, "Buscar personas")
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

    /**
     * Realiza una busqueda segun el tipo de busqueda seleccionada y la consulta proporcionada
     *
     * @param searchType El tipo de busqueda a realizar
     * @param query la consulta en la barra de busqueda
     * **/
    private fun performSearch(searchType: TmdbSearchQueries, query: String) {
        val fragmentManager = requireActivity().supportFragmentManager
        when (searchType) {
            TmdbSearchQueries.MOVIES_SEARCH -> {
                fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, MoviesSearchedFragment.newInstance(query))
                    .addToBackStack(null)
                    .commit()
            }
            TmdbSearchQueries.PERSONS_SEARCH -> {
                fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, PersonsSearchedFragment.newInstance(query))
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

//    private fun scrollToSection(textView:TextView) {
//        scrollView.post {
//            scrollView.smoothScrollTo(0, textView.top)
//        }
//    }

    /**
     * Anade las peliculas populares al recycler view
     * Si se clicka a a una de las peliculas, navegara a otro fragmento
     **/
    private fun addMoviesToList() {
        moviesAccess.listPopularMovies { result ->
            moviesListPopulares.clear()
            result.forEach { movie ->
                moviesListPopulares.add(movie)
            }

            rvPopular.adapter = CarouselMoviesAdapter(moviesListPopulares) { movieClicked ->

                val fragmentManager = requireActivity().supportFragmentManager
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView, PeliculaSeleccionadaFragment.newInstance(movieClicked.id))
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }
    /**
     * Anade las peliculas que saldran proximamente al recycler view
     * Si se clicka a a una de las peliculas, navegara a otro fragmento
     **/
    private fun addMoviesUpComingToList(){
        moviesAccess.listUpcomingMovies { results ->
            moviesListSoon.clear()
            results.forEach{movie->
                moviesListSoon.add(movie)
            }

            rvUpcoming.adapter = CarouselMoviesAdapter(moviesListSoon){movieClicked ->
                val fragmentManager = requireActivity().supportFragmentManager
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView, PeliculaSeleccionadaFragment.newInstance(movieClicked.id))
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }

    /**
     * Anade las peliculas recomendados al recycler view
     * Si se clicka a a una de las peliculas, navegara a otro fragmento
     **/
    private fun addMoviesRecommendedToList() {
        //aqui le paso el id de una pelicula cualquiera(en este caso he pillado la de DUNE)
        moviesAccess.listRecommendedMovies(movieId=438631) { results ->
            recommendedMovies.clear()
            results.forEach { movie ->
                recommendedMovies.add(movie)
            }

            rvRecommend.adapter = CarouselMoviesAdapter(recommendedMovies) { movieClicked ->
                val fragmentManager = requireActivity().supportFragmentManager
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView, PeliculaSeleccionadaFragment.newInstance(movieClicked.id))
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }

    /**
     * Anade los servicios al recycler view
     **/

    private fun cargarServicios() {
        for (id in ids) {
            moviesAccess.fetchNetworkDetails(id) { result ->
                if (result != null) {
                    services.add(result)

                    rvServicios.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
                    rvServicios.adapter = ServicioAdapter(services)
                } else {
                    println("Error: No se pudo obtener los detalles de la red.")
                }
            }
        }

    }

}
