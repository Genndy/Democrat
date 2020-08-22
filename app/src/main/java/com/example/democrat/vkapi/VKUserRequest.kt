package com.example.democrat.vkapi

import com.example.democrat.data.model.User
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKUsersRequest: VKRequest<List<User>> {
    constructor(uids: IntArray = intArrayOf()): super("users.get") {
        if (uids.isNotEmpty()) {
            addParam("user_ids", uids.joinToString(","))
        }
        addParam("fields", "photo_200")
    }

    override fun parse(r: JSONObject): List<User> {
        val users = r.getJSONArray("response")
        val result = ArrayList<User>()
        for (i in 0 until users.length()) {
          //  result.add(User.parse(users.getJSONObject(i)))
        }
        return result
    }
}