package net.rbbs1.yutori.yutoriboard

import android.util.Log
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import android.content.ContentValues.TAG
import okhttp3.*
import java.io.IOException
import okhttp3.OkHttpClient
import android.os.AsyncTask
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import java.io.InputStream
import java.io.UnsupportedEncodingException
import java.net.URLEncoder


class Threads (mRecyclerView: RecyclerView, mSwipeRefreshLayout: SwipeRefreshLayout, threads:MutableList<Thread>) : AsyncTask<String, Void, JsonObject>() {
    val mRecyclerView: RecyclerView = mRecyclerView
    val mSwipeRefreshLayout: SwipeRefreshLayout = mSwipeRefreshLayout
    val threads:MutableList<Thread> = threads

    class url(page: Int = 1, k: Int = 20, type: String = "") {
        var url : String? = null

        init {
            val point = BuildConfig.YUTORI_THREAD_LIST
            val app_token = ""
            val token = ""
            var url = ""
            if (token != "") {
                try {
                    url = point + "?app=" + app_token + "&token=" + URLEncoder.encode(token, "UTF-8") + "&page=" + page + "&k=" + k + "&type=" + type
                } catch (e: UnsupportedEncodingException) {
                    url = ""
                }

            }
            if (url == "") {
                url = point + "?app=" + app_token + "&page=" + page + "&k=" + k + "&type=" + type
            }

            Log.i(TAG, "get threads URL: " + url)

            this.url = url
        }

        fun string() : String? {
            return this.url
        }
    }

    override fun onPreExecute() {
        mSwipeRefreshLayout.setRefreshing(true)
    }

    override fun doInBackground(vararg params: String): JsonObject? {
        var obj: JsonObject? = null

        // リクエストオブジェクトを作って
        val request = Request.Builder()
                .url(params[0])
                .get()
                .build()

        // クライアントオブジェクトを作って
        val client = OkHttpClient()

        // リクエストして結果を受け取って
        try {
            val response = client.newCall(request).execute()
            val result = response?.body()?.byteStream()
            obj = Parser().parse(result as InputStream) as JsonObject
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // 返す
        return obj
    }

    override fun onPostExecute(obj: JsonObject?) {
        val items:MutableList<Thread> = mutableListOf()
        if (obj != null){
            if (obj["List"] != null) {
                for (item in obj["List"] as JsonArray<JsonObject>) {
                    val thread = Thread(item)
                    items.add(thread)
                }
            }
            threads.addAll(items)
            if (this.mRecyclerView.adapter == null) {
                this.mRecyclerView.setAdapter(ThreadAdapter(threads))
            } else {
                this.mRecyclerView.adapter.notifyDataSetChanged()
            }
        }
        mSwipeRefreshLayout.setRefreshing(false)
    }

}
