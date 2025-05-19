import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.contrib.RecyclerViewActions.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import com.uxdesign.cpp.R
import org.hamcrest.Matchers.startsWith
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CatalogoEndToEndTest {

    @Test
    fun flujoCompleto_login_menu_catalogo_seleccion_detalle_pedido_finalizar() {
        // 1. Lanzar MainActivity
        ActivityScenario.launch(com.uxdesign.ccp_frontend.MainActivity::class.java)

        // 2. Ingresar credenciales
        onView(withId(R.id.editUsuario)).perform(typeText("pepe@correo.com"), closeSoftKeyboard())
        onView(withId(R.id.editClave)).perform(typeText("pepe"), closeSoftKeyboard())

        // 3. Presionar "Ingresar"
        onView(withId(R.id.buttonIngresar)).perform(click())

        // 4. Esperar a que cargue MenuActivity
        Thread.sleep(5000) // Aumentar si la red es lenta

        // 5. Verificar que se cargó el botón "Crear Pedido" y hacer clic
        onView(withId(R.id.buttonPedido)).check(matches(isDisplayed()))
        onView(withId(R.id.buttonPedido)).perform(click())

        // 6. Esperar a que cargue el catálogo
        Thread.sleep(8000)

        // 7. Verificar que se muestra el catálogo
        onView(withId(R.id.recyclerViewProductos)).check(matches(isDisplayed()))

        // 8. Seleccionar un producto
        onView(withId(R.id.recyclerViewProductos))
            .perform(actionOnItemAtPosition<androidx.recyclerview.widget.RecyclerView.ViewHolder>(0, click()))

        // 9. Verificar pantalla de detalle
        onView(withId(R.id.textNombreProducto)).check(matches(isDisplayed()))

        Thread.sleep(7000)

        // 10. Ingresar cantidad
        onView(withId(R.id.editCantidad)).perform(typeText("1"), closeSoftKeyboard())
        onView(withId(R.id.editValor)).check(matches(withText(startsWith("$"))))

        // 11. Agregar producto
        onView(withId(R.id.buttonAgregar)).perform(click())

        Thread.sleep(7000)

        // 12. Finalizar pedido
        onView(withId(R.id.buttonFin)).check(matches(isDisplayed()))
        onView(withId(R.id.buttonFin)).perform(click())

        // 13. Verificar campos del formulario
        onView(withId(R.id.editCliente)).check(matches(isDisplayed()))
        onView(withId(R.id.editFechaEntrega)).check(matches(isDisplayed()))
        onView(withId(R.id.editNumProductos)).check(matches(isDisplayed()))
        onView(withId(R.id.editTotal)).check(matches(isDisplayed()))
        onView(withId(R.id.editComentarios)).check(matches(isDisplayed()))

        // 14. Completar formulario
        onView(withId(R.id.editNumProductos)).perform(typeText("1"), closeSoftKeyboard())
        onView(withId(R.id.editTotal)).perform(typeText("$100.00"), closeSoftKeyboard())
        onView(withId(R.id.editComentarios)).perform(typeText("Pedido de prueba para finalizar."), closeSoftKeyboard())

        // 15. Registrar pedido
        onView(withId(R.id.buttonRegistrar)).perform(click())

        Thread.sleep(2000)

        // 16. Confirmar mensaje de éxito
        //onView(withText("El pedido ha sido registrado exitosamente"))
          //  .check(matches(isDisplayed()))

        // 17. Verificar regreso al catálogo
        onView(withId(R.id.recyclerViewProductos))
            .check(matches(isDisplayed()))
    }
}

