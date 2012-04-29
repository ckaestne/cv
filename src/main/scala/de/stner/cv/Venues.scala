package de.stner.cv


object Venues {

    case class ConferenceFactory(short: String, initYear: Int, longPattern: String, urlFactory: URLFactory = NoURLFactory, isWorkshop: Boolean = false) {
        def apply(year: Int) =
            if (isWorkshop)
                Workshop(short, year, longName(year), urlFactory(year))
            else Conference(short, year, longName(year), urlFactory(year))

        def longName(year: Int) = longPattern.replace("%num%", num(year - initYear + 1))

        def num(n: Int) = n.toString + (n.toString.last match {
            case '1' => "st"
            case '2' => "nd"
            case '3' => "rd"
            case _ => "th"
        })
    }

    case class JournalFactory(short: String, name: String) {
        def apply(year: Int) = Journal(short, year, name)
    }

    trait URLFactory {
        def apply(year: Int): URL
    }

    case class URLPattern(p: String) extends URLFactory {
        def apply(year: Int): URL = URL(p.replace("%yy%", year.toString.takeRight(2)).replace("%yyyy%", year.toString))
    }

    object NoURLFactory extends URLFactory {
        def apply(year: Int) = null
    }

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


}
