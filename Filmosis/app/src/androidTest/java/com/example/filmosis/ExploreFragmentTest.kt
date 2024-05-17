import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.filmosis.R
import com.example.filmosis.fragments.ExploreFragment
import org.junit.Before
import org.junit.Test

class ExploreFragmentTest {


    @Before
    fun setup() {
        val context: Context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testSearchView() {
        Espresso.onView(ViewMatchers.withId(R.id.explore_searchView))
            .perform(ViewActions.typeText("Avengers"))
            .perform(ViewActions.pressImeActionButton()) // Simula la acción de enviar la consulta de búsqueda

        // Nos aseguramos que el resultado de la búsqueda sea visible en el RecyclerView correspondiente
        Espresso.onView(ViewMatchers.withId(R.id.explore_moviesListsRecyclerView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testFilterButtons() {
        Espresso.onView(ViewMatchers.withId(R.id.explore_bestRatedBtn))
            .perform(ViewActions.click())

        // Nos aseguramos que RecyclerView de películas filtradas se actualice correctamente
        Espresso.onView(ViewMatchers.withId(R.id.explore_moviesListsRecyclerView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}
