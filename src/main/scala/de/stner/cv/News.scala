package de.stner.cv

import java.util.{GregorianCalendar, Date}


object News {

    case class NewsItem(date: Date, title: String, body: String)

    val news: List[NewsItem] =
        NewsItem(new GregorianCalendar(2012, 9-1, 7).getTime, "FOSD and GPCE in Dresden", "I'll be in Dresden, Germany in the last week of September to present our paper Toward Variability-Aware Testing at the FOSD workshop. I also will be giving a tech talk on Variability-Aware Analysis at GPCE. Come and join, it will be fun events. (FOSD is still accepting lightning talks and tool demos.)") ::
        NewsItem(new GregorianCalendar(2012, 8-1, 25).getTime, "Moving to CMU", "Just im time for the fall term, I'm moving from Marburg, Germany to Pittsburgh, PA to start a faculty position at CMU. Starting next week, you can reach me there.") ::
            Nil


}
