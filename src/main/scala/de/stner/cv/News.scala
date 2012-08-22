package de.stner.cv

import java.util.{GregorianCalendar, Date}


object News {

    case class NewsItem(date: Date, title: String, body: String)

    val news: List[NewsItem] =
        NewsItem(new GregorianCalendar(2012, 8, 25).getTime, "Moving to CMU", "Just im time for the fall term, I'm moving from Marburg, Germany to Pittsburgh, PA to start a faculty position at CMU. Starting next week, you can reach me there.") ::
            //            NewsItem(new GregorianCalendar(2012, 7, 1).getTime, "OOPSLA paper accepted", "Body2") ::
            Nil


}
