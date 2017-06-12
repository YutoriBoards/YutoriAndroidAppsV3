package net.rbbs1.yutori.yutoriboard

import com.beust.klaxon.JsonObject

class Res(id: Int, title: String, name: String?, user_id: String, device: String?, count: Int, time: Long, status: Int, permit: String, favorite: Boolean){

    constructor(obj: JsonObject) : this(obj["id"] as Int, obj["title"] as String, obj["name"] as String?, obj["user_id"] as String,
            obj["device"] as String?, obj["count"] as Int, (obj["time"] as Int) * 1000L,
            obj["status"] as Int, obj["permit"] as String, obj["favorite"] as Boolean) {
    }

}