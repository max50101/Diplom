package com.example.diplom.news
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
class RssParser {
    private val rssItems = ArrayList<RssItem>()
    private var rssItem : RssItem ?= null
    private var text: String? = null
    fun parse(inputStream: InputStream):MutableList<RssItem> {
        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()
            parser.setInput(inputStream, null)
            var eventType = parser.eventType
            var foundItem = false
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagname = parser.name
                when (eventType) {
                    XmlPullParser.START_TAG -> if (tagname.equals("item", ignoreCase = true)) {
                        // create a new instance of employee
                        foundItem = true
                        rssItem = RssItem()
                    }else if(tagname.contains("content",ignoreCase = true)){
                        val image=parser.getAttributeValue(0)
                        rssItem!!.image=image
                    }
                    XmlPullParser.TEXT -> text = parser.text
                    XmlPullParser.END_TAG -> if (tagname.equals("item", ignoreCase = true)) {
                        // add employee object to list
                        rssItem?.let { rssItems.add(it) }
                        foundItem = false
                    } else if ( foundItem && tagname.equals("title", ignoreCase = true)) {
                        rssItem!!.title = text.toString()
                    } else if (foundItem && tagname.equals("link", ignoreCase = true)) {
                        rssItem!!.link = text.toString()
                    } else if (foundItem && tagname.equals("pubDate", ignoreCase = true)) {
                        rssItem!!.pubDate = text.toString()
                    } else if (foundItem && tagname.equals("social-media", ignoreCase = true)) {
                        val image=parser.getAttributeValue(0)
                        rssItem!!.image=image
                    }else if(foundItem && tagname.contains("media", ignoreCase = true)){
                        val image=parser.getAttributeValue(0)
                        rssItem!!.image=image
                    }
                }
                eventType = parser.next()
            }
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return rssItems
    }
}