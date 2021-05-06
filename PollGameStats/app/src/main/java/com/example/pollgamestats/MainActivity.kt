package com.example.pollgamestats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val queue = Volley.newRequestQueue(this)
        //val url = "https://jsonplaceholder.typicode.com/posts/1"
        var url = "https://generic-game-service.herokuapp.com/Game/4r1w1/poll"
        // Request a string response from the provided URL.

        val stringRequest = object: StringRequest(
                Request.Method.GET, url,
                { response ->
                    // Display the first 500 characters of the response string.
                    // gameId = 4r1w1
                    /* curl -X 'GET' \
                          'https://generic-game-service.herokuapp.com/Game/4r1w1/poll' \
                          -H 'accept: application/json' \
                          -H 'Game-Service-Key: Gg1E6gJAAg'
                    Request URL
                        https://generic-game-service.herokuapp.com/Game/4r1w1/poll
                    */

                    val obj = JSONObject(response)
                    playersView.text = obj.getString("players")
                    gameStateView.text = obj.getString("state")
                    gameIdView.text = obj.getString("gameId")
                },
                {
                    playersView.text = "Error: no one wants to play this game"
                    gameStateView.text = "Error: we need a game state"
                    gameIdView.text = "Error: we annot play a game without a game id!!!"
                }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Game-Service-Key"] = "Gg1E6gJAAg"
                return headers
            }
        }


        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}