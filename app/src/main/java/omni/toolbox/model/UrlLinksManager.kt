package omni.toolbox.model

import android.content.Context
import org.json.JSONArray
import java.io.InputStreamReader

data class LinkItem(
    val title: String,
    val url: String,
    val urls: List<String>,
    val category: String
)

object UrlLinksManager {
    private var cachedLinks: List<LinkItem>? = null

    fun getLinks(context: Context): List<LinkItem> {
        if (cachedLinks != null) return cachedLinks!!
        val list = mutableListOf<LinkItem>()
        try {
            val inputStream = context.assets.open("url_links.json")
            val reader = InputStreamReader(inputStream)
            val jsonString = reader.readText()
            reader.close()
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val title = obj.getString("title")
                val url = obj.getString("url")
                val category = obj.getString("category")
                val urlsList = mutableListOf<String>()
                if (obj.has("urls")) {
                    val urlsArr = obj.getJSONArray("urls")
                    for (j in 0 until urlsArr.length()) {
                        urlsList.add(urlsArr.getString(j))
                    }
                } else {
                    urlsList.add(url)
                }
                list.add(LinkItem(title, url, urlsList, category))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        cachedLinks = list
        return list
    }
}
