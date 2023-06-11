package sbs.pros.historyplaces

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class App(): Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("024ae79a-58dc-4626-ac7e-1ba6ba83121e")
    }
}