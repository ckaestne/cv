package de.stner.cv

import java.io.{File, FileNotFoundException}
import java.text.SimpleDateFormat
import java.util.{Date, Locale}
import de.stner.cv.GenHtml.PlainHtmlFormater

import java.time.{LocalDateTime, LocalTime, ZoneOffset}
import java.time.format.DateTimeFormatter
import scala.xml.{Node, NodeSeq, XML}

/**
  * Created by ckaestne on 3/5/2016.
  */
trait RSSFeed {

    def pubPage: String

    implicit def stringTexWrapper(string: String) = new StringTexHelper(string)


    protected val RFC822 = DateTimeFormatter.RFC_1123_DATE_TIME

    def printNewsFeed(file: File) =
        printFeed(CV.name + " :: News Feed", newsRSSURL, CV.url, "News feed for " + CV.name, {
            for (newsitem <- News.news) yield
                <item>
                    <title>{newsitem.title}</title>
                    <author>kaestner@cs.cmu.edu (Christian Kästner)</author>
                    <description>{newsitem.body.toString()}</description>
                    <pubDate>{RFC822.format(LocalDateTime.of(newsitem.date, LocalTime.of(0,0)).atOffset(ZoneOffset.UTC))}</pubDate>
                    <guid>{CV.url + "#" + newsitem.getID()}</guid>
                    <link>{CV.url + "#" + newsitem.getID()}</link>
                </item>
        }, file)

    protected def renderPubRSS(pub: Publication): String = {
        <div><p>{pub.render(new BibStyle {
            override def withAcceptanceRate = false
        }, new PlainHtmlFormater {})}
        </p>
            {if (!pub.isHideAbstract && pub.abstr != "") <br/><br/>  <blockquote><p>{pub.abstr.markdownToHtml}</p></blockquote> }
        </div>
            .toString
    }

    val rssFeedURL = "https://www.cs.cmu.edu/~ckaestne/pub.rss"
    val newsRSSURL = "https://www.cs.cmu.edu/~ckaestne/news.rss"

    def printPubsFeed(file: File) = try {
        val rss = XML.load(new java.net.URL(rssFeedURL))

        printFeed(CV.name + " :: Publication Feed", rssFeedURL, pubPage, "Publication feed for " + CV.name, {
            for (pub <- CV.publications) yield {
                val pubId = pubPage + "#" + pub.genKey
                val pubDesc = renderPubRSS(pub).toString

                val oldEntry = (rss \\ "item") filter (e => (e \ "guid").text.trim == pubId)
                val isNew = oldEntry.isEmpty
                val isUpdated = !isNew && (oldEntry \ "description").text.replaceAll("[ \\n\\t]","") != pubDesc.replaceAll("[ \\n\\t]","")
                val oldPubDate = (oldEntry \ "pubDate").text
                val pubDate = if (isNew || isUpdated || oldPubDate.isEmpty)
                    RFC822.format(LocalDateTime.now().atOffset(ZoneOffset.UTC)) else oldPubDate
                val authors = pub.authors.map(_.abbrvname).mkString(", ")


                <item>
                    <title>{pub.title}</title>
                    <author>kaestner@cs.cmu.edu ({authors})</author>
                    <link>{pubId}</link>
                    <description>{pubDesc}</description>
                    <guid>{pubId}</guid>
                    <pubDate>{pubDate}</pubDate>
                </item>
                }
        }, file)
    } catch {
        case e: FileNotFoundException => throw new RuntimeException("cannot open existing RSS feed")
    }


    def printFeed(title: String, feedURL: String, webURL: String, desc: String, items: NodeSeq, file: File): Unit = {

        val doc: Node =
            <rss version="2.0" xmlns:atom="http://www.w3.org/2005/Atom">
                <channel>
                    <title>{title}</title>
                    <link>{webURL}</link>
                    <description>{desc}</description>
                    <language>en-us</language>
                    <pubDate>{RFC822.format(LocalDateTime.now().atOffset(ZoneOffset.UTC))}</pubDate>
                    <atom:link href={feedURL} rel="self" type="application/rss+xml" />
                    {items}
                </channel>
            </rss>
        scala.xml.XML.save(file.getAbsolutePath(), doc, "UTF-8", true)
    }

    def rssLogo(link: String, title: String): Node =
        <a href={link} title={title}><img src="rss.png" alt={title} /></a>

    def getNewsRSSHeader() =
            <link title="News Feed" type="application/rss+xml" rel="alternate" href="news.rss" />

    def getPubsRSSHeader() =
            <link title="Publication Feed" type="application/rss+xml" rel="alternate" href="pub.rss" />


}
