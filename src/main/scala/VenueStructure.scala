package de.stner.cv


object VenueStructure {

    val publicationKinds = Seq(KBook, KJournal, KInConferenceProceedings, KInvited, KWorkshopDemoTool, KTechnicalReport, KMisc).sorted

    object KJournal extends PublicationKind("Refereed Journal Articles", 1)

    object KInConferenceProceedings extends PublicationKind("Refereed Conference Papers", 2)

    object KInvited extends PublicationKind("Invited Papers", 3)

    object KWorkshopDemoTool extends PublicationKind("Refereed Workshop Papers, Posters, and Tool Demos", 4)

    object KTechnicalReport extends PublicationKind("Technical Reports", 5)

    object KMisc extends PublicationKind("Miscellaneous", 6)

    object KBook extends PublicationKind("Books", 0)


    object Conference {
        def apply(short: String, year: Int, name: String, url: URL = null) =
            Venue(short, year, name, KInConferenceProceedings, if (url == null) None else Some(url))
    }

    object Workshop {
        def apply(short: String, year: Int, name: String, url: URL = null) =
            Venue(short, year, name, KWorkshopDemoTool, if (url == null) None else Some(url))
    }

    object Journal {
        def apply(short: String, year: Int, name: String, url: URL = null) =
            Venue(short, year, name, KJournal, if (url == null) None else Some(url))
    }


    case class ConferenceFactory(short: String, initYear: Int, longPattern: String, urlFactory: URLFactory = NoURLFactory, isWorkshop: Boolean = false, publisher: Publisher = null) {
        def apply(year: Int) = {
            def uf = if (urlFactory == null) NoURLFactory else urlFactory
            val r = if (isWorkshop)
                Workshop(short, year, longName(year), uf(year))
            else Conference(short, year, longName(year), uf(year))
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

    case class JournalFactory(short: String, name: String, publisher: Publisher = null, url: Option[URL]=None) {
        def apply(year: Int) = Journal(short, year, name, null).publisher(publisher).copy(url=url)
    }

    trait URLFactory {
        def apply(year: Int): URL
    }

    case class URLPattern(p: String) extends URLFactory {
        def apply(year: Int): URL = URL(p.replace("%yy%", year.toString.takeRight(2)).replace("%yyyy%", year.toString))
    }

    case class URLMap(map: Map[Int, URL]) extends URLFactory {
        def apply(year: Int): URL = map.getOrElse(year, null)
    }

    object NoURLFactory extends URLFactory {
        def apply(year: Int): URL = null
    }


}
