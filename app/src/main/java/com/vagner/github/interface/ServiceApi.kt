import com.vagner.github.model.Github
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ServicesApi {
    @GET("repositories")
    fun listAll(@QueryMap filtros: Map<String, String>): Call<Github>
}