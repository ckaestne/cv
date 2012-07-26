package de.stner.cv

import java.util.{GregorianCalendar, Date}
import xml.{Node, NodeSeq}


object News {

    case class NewsItem(date: Date, title: String, body: String)

    val news: List[NewsItem] =
        NewsItem(new GregorianCalendar(2012, 8, 15).getTime, "Moving to CMU", "Body") ::
            NewsItem(new GregorianCalendar(2012, 7, 1).getTime, "OOPSLA paper accepted", "Body2") :: Nil


}
