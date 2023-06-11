package sbs.pros.historyplaces

import com.yandex.mapkit.geometry.Point

open class PlaceObjectUserData internal constructor(val id: Int, val title: String, val description: String, val address: String)

open class PlaceObject internal constructor(val point: Point, val userData: PlaceObjectUserData)