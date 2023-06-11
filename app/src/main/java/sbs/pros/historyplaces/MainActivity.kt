package sbs.pros.historyplaces

import android.Manifest
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import sbs.pros.historyplaces.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mapview: MapView? = null
    private var mapObjects: MapObjectCollection? = null
    val mutableMap: MutableMap<Int, PlaceObject> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("024ae79a-58dc-4626-ac7e-1ba6ba83121e")
        MapKitFactory.initialize(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // настройка состояний нижнего экрана
        //bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        // настройка максимальной высоты
        //bottomSheetBehavior.peekHeight = 120

        // настройка возможности скрыть элемент при свайпе вниз
        //bottomSheetBehavior.isHideable = true

        // настройка колбэков при изменениях
        /*bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {}
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })*/

        val fab = binding.fab
        fab.setOnClickListener {
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                //val dialog = DialogScreen.getDialog(this, 1, applicationContext.resources.getString(R.string.reason));
                //dialog.show();
                val fragmentManager = supportFragmentManager
                ReasonDialogFragment().show(
                    fragmentManager, ReasonDialogFragment.TAG
                )
            } else {
                singlePermission.launch(Manifest.permission.CAMERA)
            }
        }

        var mapview: MapView = findViewById(R.id.mapview)
        mapview.map.move(
            CameraPosition(Point(43.592812, 39.977536), 9.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0F),
            null
        )
        mapObjects = mapview.map.mapObjects.addCollection()

        addPoint(1, Point(43.568149, 39.742334), PlaceObjectUserData( 1,"Сочинский дендрарий", "Красивый зелёный парк, где собраны разные виды растений, в том числе из экзотических стран", "Курортный просп., 74"))
        addPoint(2, Point(43.580055, 39.718808), PlaceObjectUserData(2,"Морской порт", "Морской порт считается визитной карточкой и федеральным памятником архитектуры центрального Сочи и местом интересной индивидуальной экскурсии, проходящей по основному зданию морского вокзала и прилегающей территории порта с видом на пришвартованные яхты", "улица Круизная Гавань, 4/5"))
        addPoint(3, Point(43.668032, 40.257377), PlaceObjectUserData( 3,"Красная поляна", "Рядом с поселком возведены объекты горного кластера Зимних Олимпийских игр 2014 года и открыты 3 всесезонных курорта: горно-туристический центр «Газпром», «Роза Хутор» и «Красная Поляна»", "Новое Краснополянское шоссе"))
        addPoint(4, Point(43.535228, 39.876484), PlaceObjectUserData( 4,"Тисо-самшитовая роща", "Тисо-самшитовая роща — реликтовый памятник природы и популярный туристический объект", "Хостинский район"))
        addPoint(5, Point(43.545026, 39.801563), PlaceObjectUserData( 5,"Дача Сталина", "Дача Сталина находится на территории действующего санатория «Золотой колос» и открыта к посещению для всех желающих. Дача состоит из нескольких построек: жилых помещений и административных зданий, а весь комплекс является музейным достоянием страны.", "Курортный проспект, 120к2"))
        addPoint(6, Point(43.550482, 39.844747), PlaceObjectUserData( 6,"Смотровая башня на горе Ахун", "Массив Ахун - пятикилометровый хребет черноморского побережья, объединяющий три вершины: Большой Ахун - 663 м, Малый Ахун - 503 м, Подахунок - 389 м.", "улица Дорога на Большой Ахун"))

    }

    private val placeObjectTapListener =
        MapObjectTapListener { mapObject, point ->
            if (mapObject is PlacemarkMapObject) {
                val place = mapObject
                val userData = place.userData
                if (userData is PlaceObjectUserData) {
                    // получение вью нижнего экрана
                    val llBottomSheet: LinearLayout = findViewById(R.id.bottom_sheet)

                    // настройка поведения нижнего экрана
                    val bottomSheetBehavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(llBottomSheet)

                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

                    val peekText: TextView = findViewById(R.id.peek_text)

                    peekText.text = userData.title


                    val contentText: TextView = findViewById(R.id.content_text)

                    contentText.text = "Адрес: " + userData.address

                    val descriptionText: TextView = findViewById(R.id.description_text)

                    descriptionText.text = "Описание: " + userData.description

                    val imageView1: ImageView = findViewById(R.id.imageView1)
                    val imageView2: ImageView = findViewById(R.id.imageView2)
                    val imageView3: ImageView = findViewById(R.id.imageView3)

                    val id = userData.id

                    imageView1.setImageDrawable(Drawable.createFromStream(this.assets.open("$id/1.jpeg"), null))
                    imageView2.setImageDrawable(Drawable.createFromStream(this.assets.open("$id/2.jpeg"), null))
                    imageView3.setImageDrawable(Drawable.createFromStream(this.assets.open("$id/3.jpeg"), null))
                }
            }
            true
        }

    fun getBitmapFromVectorDrawable(drawableId: Int): Bitmap? {
        var drawable = ContextCompat.getDrawable(this, drawableId) ?: return null

        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888) ?: return null
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

    override fun onStop() {
        mapview?.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapview?.onStart()
    }

    private val resultLauncher = registerForActivityResult(MyCameraxActivityContract()) { result ->
        if (result != null) {
            val placeObject = mutableMap[result.toInt()]
            val point = placeObject?.point
            val data = placeObject?.userData
            mapview?.map?.move(
                CameraPosition(point!!, 12.0f, 0.0f, 0.0f),
                Animation(Animation.Type.SMOOTH, 0F),
                null
            )

            // получение вью нижнего экрана
            val llBottomSheet: LinearLayout = findViewById(R.id.bottom_sheet)

            // настройка поведения нижнего экрана
            val bottomSheetBehavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(llBottomSheet)

            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

            val contentText: TextView = findViewById(R.id.content_text)

            contentText.text = data?.address

        }
    }
    private val singlePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        when {
            granted -> {
                resultLauncher.launch("ID?")
            }
            !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                //DialogScreen.getDialog(Activity(), 1, applicationContext.resources.getString(R.string.no_qr))
                val fragmentManager = supportFragmentManager
                NoQRDialogFragment().show(
                    fragmentManager, NoQRDialogFragment.TAG)
            }
            else -> {
                //DialogScreen.getDialog(Activity(), 1, applicationContext.resources.getString(R.string.please_camera))
                val fragmentManager = supportFragmentManager
                PleaseCameraDialogFragment().show(
                    fragmentManager, PleaseCameraDialogFragment.TAG)
            }
        }
    }

    /*private fun addPoint(id: Int, latitude: Double, longitude: Double, title: String, description: String, address: String) {
        val placemark = mapview!!.map.mapObjects.addCollection().addPlacemark(Point(latitude, longitude))
        placemark.userData = PlaceObjectUserData(id, title, description, address)
    }

    private fun getPlaces(url: String) {
        val request = JsonObjectRequest(
            Request.Method.GET,
            url, null, Response.Listener<JSONObject?> {
                fun onResponse(response: JSONObject) {
                    try {
                        val id = response.getInt("id")
                        val latitude = response.getDouble("latitude")
                        val longitude = response.getDouble("longitude")
                        val title = response.getJSONObject("title").toString()
                        val description = response.getJSONObject("description").toString()
                        val address = response.getJSONObject("address").toString()

                        addPoint(id, latitude, longitude, title, description, address)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }) { error ->
            error.printStackTrace()
        }
        Volley.newRequestQueue(this).add(request) // добавляем запрос в очередь
    }*/

    private fun addPoint(int: Int, point: Point, userData: PlaceObjectUserData) {
        val placemark = mapObjects?.addPlacemark(point)
        placemark?.setIcon(
            ImageProvider.fromBitmap(getBitmapFromVectorDrawable(R.drawable.baseline_place_24)))

        placemark?.userData = userData
        placemark?.addTapListener(placeObjectTapListener)

        mutableMap[int] = PlaceObject(point, userData)
    }
}