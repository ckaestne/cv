package de.stner.cv

import de.stner.cv.CVPublications._


object Venues {


    case class ConferenceFactory(short: String, initYear: Int, longPattern: String, urlFactory: URLFactory = NoURLFactory, isWorkshop: Boolean = false, publisher: Publisher = null) {
        def apply(year: Int) = {
            val r = if (isWorkshop)
                Workshop(short, year, longName(year), urlFactory(year))
            else Conference(short, year, longName(year), urlFactory(year))
            if (publisher != null) r.publisher(publisher) else r
        }

        def longName(year: Int) = longPattern.replace("%num%", num(year - initYear + 1))

        def num(n: Int) = n.toString + (n.toString.last match {
            case '1' => "st"
            case '2' => "nd"
            case '3' => "rd"
            case _ => "th"
        })
    }

    case class JournalFactory(short: String, name: String) {
        def apply(year: Int) = Journal(short, year, name, null)
    }

    trait URLFactory {
        def apply(year: Int): URL
    }

    case class URLPattern(p: String) extends URLFactory {
        def apply(year: Int): URL = URL(p.replace("%yy%", year.toString.takeRight(2)).replace("%yyyy%", year.toString))
    }

    object NoURLFactory extends URLFactory {
        def apply(year: Int): URL = null
    }


    val MDTR = Publisher("University of Magdeburg", "Magdeburg, Germany")
    val IEEE = Publisher("IEEE Computer Society", "Los Alamitos, CA")


    val GPCE = ConferenceFactory("GPCE", 2002,
        "%num% ACM International Conference on Generative Programming and Component Engineering",
        URLPattern("http://program-transformation.org/GPCE%yy%"))
    val FOSD = ConferenceFactory("FOSD", 2009,
        "%num% Int'l Workshop on Feature-Oriented Software Development",
        URLPattern("http://fosd.net/%yyyy%"), true)

    val ECOOP = ConferenceFactory("ECOOP", 2002,
        "European Conference on Object-Oriented Programming")
    val SLE = ConferenceFactory("SLE", 2002,
        "International Conference on Software Language Engineering")
    val AOSD = ConferenceFactory("AOSD", 2002,
        "International Conference on Aspect-Oriented Software Development")
    val ASE = ConferenceFactory("ASE", 2002,
        "International Conference on Automated Software Engineering")
    val SPLC = ConferenceFactory("SPLC", 1997, "%num% International Software Product Line Conference")

    val AI = JournalFactory("AI", "Acta Informatica")
    val SPE = JournalFactory("SPE", "Software: Practice and Experience")
    val SCP = JournalFactory("SCP", "Science of Computer Programming")
    val TOSEM = JournalFactory("TOSEM", "ACM Transactions on Software Engineering and Methodology")
    val JSS = JournalFactory("JSS", "Journal of Systems and Software")
    val STTT = JournalFactory("STTT", "Software Tools for Technology Transfer")
    val IST = JournalFactory("IST", "Information and Software Technology")
    val TSE = JournalFactory("TSE", "IEEE Transactions on Software Engineering")
    val IS = JournalFactory("IS", "IEEE Software")
    val DKE = JournalFactory("DKE", "Data & Knowledge Engineering")
    val TII = JournalFactory("TII", "IEEE Transactions on Industrial Informatics")
    val JOT = JournalFactory("JOT", "Journal of Object Technology (JOT)")

    val ACoM = ConferenceFactory("ACoM", 0,
        "ICSE Workshop on Assessment of Contemporary Modularization Techniques (ACoM)", NoURLFactory, true, IEEE)

    val RAMSE = ConferenceFactory("RAM-SE", 2003,
        "%num% Workshop on Reflection, AOP and Meta-Data for Software Evolution", NoURLFactory, true)


}
