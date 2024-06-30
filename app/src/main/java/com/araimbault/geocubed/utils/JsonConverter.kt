package com.araimbault.geocubed.utils

import org.json.JSONObject

fun gsonToJSONObject (jsonString: String): JSONObject {
    val jsonObject = JSONObject(jsonString)
    println("---------------------")
    println("Gson to json : $jsonObject")
    println("---------------------")
    return jsonObject
}
