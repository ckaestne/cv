package de.stner.cv

import java.util.{GregorianCalendar, Date}
import java.text.SimpleDateFormat
import xml.NodeSeq


object News {

    case class NewsItem(date: Date, title: String, body: NodeSeq) {
        def getID() = new SimpleDateFormat("yyyy-MM-dd").format(date)+"_"+title.replaceAll("[\\W]|_", "_")
    }

    val news: List[NewsItem] =
        NewsItem(new GregorianCalendar(2013, 4-1, 17).getTime, "Book available for preorder: Feature-Oriented Software Product Lines",
          <span>Our book "Feature-Oriented Software Product Lines" can now be preordered from <a href="http://www.springer.com/computer/swe/book/978-3-642-37520-0">Springer</a> and <a href="http://www.amazon.com/Feature-Oriented-Software-Product-Lines-Implementation/dp/3642375200/">Amazon</a> and is scheduled to be shipped May 31st.</span>) ::
        NewsItem(new GregorianCalendar(2013, 3-1, 21).getTime, "TypeChef release 3.4",
            <span>TypeChef 0.3.4 is finally released and contains many updates and bugfixes. This version is finally able to typecheck the entire subsystem of the Linux kernel we have been targeting. For code and changelog see the <a href="https://github.com/ckaestne/TypeChef">github page</a></span>) ::
        NewsItem(new GregorianCalendar(2013, 2-1, 17).getTime, "Dagstuhl Variability Analysis and FOSD",
            <span>Next week, I will attend the Dagstuhl seminar <a href="http://www.dagstuhl.de/en/program/calendar/semhp/?semnr=13091">Analysis, Test and Verification in The Presence of Variability</a> and will run the <a href="http://fosd.net/treffen2013">German FOSD student meeting</a>, also in Dagstuhl this year. Looking forward to many inspiring discussions.</span>) ::
        NewsItem(new GregorianCalendar(2013, 2-1, 12).getTime, "Book: Feature-Oriented Software Product Lines",
            <span>After over a year of work, we (Sven Apel, Don Batory, Gunter Saake, and I) finally finished the final draft of our book "Feature-Oriented Software Product Lines: Concepts and Implementation", a textbook for our product-line lectures. The book will be published by Springer and will appear late Spring.</span>) ::
        NewsItem(new GregorianCalendar(2012, 9-1, 30).getTime, "GPCE 2013",
            <span>I will chair the program committee of <a href="http://program-transformation.org/GPCE13">GPCE 2013</a>, colocated with SPLASH in Indianapolis. Already start thinking about submitting there next spring :). Updates to follow.</span>) ::
        NewsItem(new GregorianCalendar(2012, 9-1, 7).getTime, "FOSD and GPCE in Dresden",
            <span>I'll be in Dresden, Germany in the last week of September to present our paper <a href="pdf/FOSD12_testing.pdf">Toward Variability-Aware Testing</a> at the <a href="http://fosd.net/2012/">FOSD workshop</a>. I also will be giving a tech talk on Variability-Aware Analysis at <a href="http://program-transformation.org/GPCE12">GPCE</a>, together with Sven Apel. Come and join, it will be fun events. (FOSD is still accepting lightning talks and tool demos.)</span>) ::
        NewsItem(new GregorianCalendar(2012, 8-1, 25).getTime, "Moving to CMU",
            <span>Just im time for the fall term, I'm moving from Marburg, Germany to Pittsburgh, PA to start a faculty position at CMU. Starting next week, you can reach me there.</span>) ::
            Nil


}
