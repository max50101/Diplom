package com.example.diplom.news

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFragment : Fragment() {
    lateinit var listNews:RecyclerView
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var newsList:MutableList<RssItem>
    val RSS_FEED_LINK_SPORT_RU = "https://www.sport.ru/rssfeeds/football.rss";
    val RSS_FEED_LINK_SPORTS_RU = "https://www.sports.ru/rss/rubric.xml?s=208";
    val RSS_FEED_LINK_SPORT_EXPRESS = "https://www.sport-express.ru/services/materials/news/football/se/";
    val RSS_FEED_LINK_EUROFOOTBALL = "https://www.euro-football.ru/category/rss";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_news, container, false)
        listNews=view.findViewById(R.id.news)
        listNews.layoutManager=LinearLayoutManager(requireContext())
        newsList= mutableListOf()
        RssFeedFetcher(this).execute(URL(RSS_FEED_LINK_SPORT_RU),URL(RSS_FEED_LINK_SPORTS_RU), URL(RSS_FEED_LINK_EUROFOOTBALL),URL(RSS_FEED_LINK_SPORT_EXPRESS))
        return view
    }
    fun updateRV(rssItemsL: MutableList<RssItem>) {
        if (rssItemsL != null && !rssItemsL.isEmpty()) {
            newsList.addAll(rssItemsL)
            val formatter =  SimpleDateFormat("EEE, dd MMM yy HH:mm:ss zzz", Locale.ENGLISH);
            val result=newsList.sortedByDescending {
                formatter.parse(it.pubDate)
            }
             val adapter=NewsAdapter(requireContext(), result as MutableList<RssItem>)
            listNews.adapter=adapter

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    class RssFeedFetcher(val context: NewsFragment) : AsyncTask<URL, Void, MutableList<RssItem>>() {
        val reference = WeakReference(context)
        private var stream: InputStream? = null;
        override fun doInBackground(vararg params: URL?): MutableList<RssItem>? {
            var rssItems= mutableListOf<RssItem>()
            for(i in 0 until params.size) {
                var tempList= mutableListOf<RssItem>()
                val connect = params[i]?.openConnection() as HttpURLConnection
                connect.readTimeout = 20000
                connect.connectTimeout = 20000
                connect.requestMethod = "GET"
                connect.connect();
                val responseCode: Int = connect.responseCode;
                if (responseCode == 200) {
                    stream = connect.inputStream;
                    try {
                        val parser = RssParser()
                        tempList = parser.parse(stream!!)
                        tempList.forEach {
                            rssItems!!.add(it)
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            return rssItems
        }
        override fun onPostExecute(result: MutableList<RssItem>?) {
            super.onPostExecute(result)
            if (result != null && !result.isEmpty()) {
                reference.get()?.updateRV(result)
            }
        }
    }

}