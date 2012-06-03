package de.stner.cv

import scala.None
import de.stner.cv.CVPublications._
import java.io.File

object Config {
    val pdfWebPath = "pdf/"

    val pdfPath = new File("src/main/pdf/")
}

case class Person(
                     firstname: String,
                     lastname: String,
                     url: URL = null,
                     affiliation: String = "") {
    def fullname = firstname + " " + lastname

    def abbrvname = firstname.charAt(0) + ". " + lastname
}

object InProceedings {
    def apply(authors: Seq[Person],
              title: String,
              venue_ : Venue,
              pages: PPages,
              links: Map[LinkKind, URL],
              abstr: String) =
        new Publication(renderer, authors, title, venue_, pages, links, abstr)

    val renderer = new PublicationRenderer {
        def renderRest(pub: Publication, style: BibStyle) = {
            assert(pub.venue.kind == KInConferenceProceedings || pub.venue.kind == KWorkshopDemoTool, "InProceedings with venue.kind=" + pub.venue.kind + " not supported")
            "In *Proceedings of the %s (%s)*, ".format(pub.venue.name, pub.venue.short) +
                pub.venue.renderVolSeries + renderPages(pub) + pub.venue.renderPublisher + pub.venue.renderDate + "."
        }
    }

}

object Article {
    def apply(authors: Seq[Person],
              title: String,
              venue_ : Venue,
              pages: PPages,
              links: Map[LinkKind, URL],
              abstr: String) =
        new Publication(renderer, authors, title, venue_, pages, links, abstr)

    val renderer = new PublicationRenderer {
        private def renderVolNumber(venue: Venue): String = (venue.volume, venue.number) match {
            case (v, Some(n)) => "%s(%s):".format(v.getOrElse(""), n)
            case (Some(v), None) => v + ":"
            case _ => ""
        }

        def renderRest(pub: Publication, style: BibStyle) = {
            assert(pub.venue.kind == KJournal || pub.venue.kind == KInvited, "Article with venue.kind=" + pub.venue.kind + " not supported " + pub.title)
            "*" + pub.venue.name + (if (pub.venue.short != "") " (" + pub.venue.short + ")" else "") + "*, " +
                renderVolNumber(pub.venue) + renderPages(pub, true) + pub.venue.renderDate + "."
        }
    }
}

object Thesis {
    def apply(authors: Seq[Person],
              title: String,
              year: Int, month: Int,
              typ: String,
              school: Publisher,
              links: Map[LinkKind, URL],
              abstr: String) =
        new Publication(renderer, authors, title, Venue(null, year, typ, KMisc, null).month(month).publisher(school), null, links, abstr)

    val renderer = new PublicationRenderer {
        def renderRest(pub: Publication, style: BibStyle) =
            pub.venue.name + ", " + pub.venue.renderPublisher + pub.venue.renderDate + "."
    }
}

object TechReport {
    def apply(authors: Seq[Person],
              title: String,
              year: Int, month: Int,
              school: Publisher,
              number: String,
              links: Map[LinkKind, URL],
              abstr: String) =
        new Publication(renderer, authors, title, Venue("", year, "", KTechnicalReport, None).month(month).publisher(school).number(number), null, links, abstr)

    val renderer = new PublicationRenderer {
        def renderRest(pub: Publication, style: BibStyle) = {
            assert(pub.venue.kind == KTechnicalReport, "TechReport with venue.kind=" + pub.venue.kind + " not supported")
            "Technical Report " + pub.venue.number.get + ", " + pub.venue.renderPublisher + pub.venue.renderDate + "."
        }
    }
}


object Book {
    def apply(editors: Seq[Person],
              title: String,
              venue_ : Venue /*title and short are not used!)*/ ,
              links: Map[LinkKind, URL],
              abstr: String) =
        new Publication(rendererEditors, editors, title, venue_, null, links, abstr)

    val rendererEditors = new PublicationRenderer {
        def renderRest(pub: Publication, style: BibStyle) = ""

        override def render(pub: Publication, style: BibStyle): String =
            renderAuthors(pub, style.renderAuthor) + ", editor" + (if (pub.authors.size > 1) "s" else "") + ". [!]" +
                "*" + pub.title + "*. [!]" +
                pub.venue.renderPublisher + pub.venue.renderDate + "." +
                (pub.note.map("[!] " + _ + ".").getOrElse(""))
    }
}

object InBook {
    //TODO fix rendering

    def apply(authors: Seq[Person],
              title: String,
              venue_ : Venue,
              pages: PPages,
              links: Map[LinkKind, URL],
              abstr: String) =
        new Publication(renderer, authors, title, venue_, pages, links, abstr)

    val renderer = new PublicationRenderer {
        def renderRest(pub: Publication, style: BibStyle) = {
            "In *%s*, ".format(pub.venue.name) +
                pub.venue.renderVolSeries + renderPages(pub) + pub.venue.renderPublisher + pub.venue.renderDate + "."
        }
    }
}

/**
 * default renderer, subclass for specifics
 */
abstract class PublicationRenderer {
    implicit def stringTexWrapper(string: String) = new StringTexHelper(string)


    def render(p: Publication, style: BibStyle): String =
        renderAuthors(p, style.renderAuthor) + ". [!]" +
            "**" + p.title + "**" + p.title.endDot + " [!]" +
            renderRest(p, style) +
            (if (style.withAcceptanceRate) renderAcceptanceRate(p) else "") +
            (p.note.map("[!] " + _ + ".").getOrElse(""))

    def renderRest(p: Publication, style: BibStyle): String

    def renderAuthors(p: Publication, renderOne: Person => String): String = {
        assert(!p.authors.isEmpty, "no authors for " + this)
        if (p.authors.size == 1) renderOne(p.authors.head)
        else p.authors.dropRight(1).map(renderOne).mkString(", ") + ", and " + renderOne(p.authors.last)
    }


    def renderPages(p: Publication, short: Boolean = false) = {
        def extr(e: String): String = if (e == null) "" else ", " + e
        if (p.pages == null) ""
        else p.pages match {
            case Pages(a, b, e) if (a == b) => (if (short) "" else "page ") + a + extr(e) + ", "
            case Pages(a, b, e) => (if (short) "" else "pages ") + "%s--%s%s, ".format(a, b, extr(e))
            case PagesStr(s) => s + ", "
            case ToAppear() => ""
        }
    }

    def renderAcceptanceRate(p: Publication) =
        p.venue.acceptanceRate.map(
            e => " Acceptance rate: %d %% (%d/%d).".format(scala.math.round(e._1.toDouble / e._2.toDouble * 100), e._1, e._2)
        ).getOrElse("")
}

class Publication(
                     val renderer: PublicationRenderer,
                     val authors: Seq[Person],
                     _title: String,
                     val venue: Venue,
                     val pages: PPages,
                     val links: Map[LinkKind, URL],
                     val abstr: String = "",
                     val topics: Seq[Topic] = Seq(),
                     val isSelected: Boolean = false,
                     val note: Option[String] = None,
                     val isHideAbstract: Boolean = false
                     ) {

    protected def copy(authors: Seq[Person] = this.authors,
                       title: String = this.title,
                       venue: Venue = this.venue,
                       pages: PPages = this.pages,
                       links: Map[LinkKind, URL] = this.links,
                       abstr: String = this.abstr,
                       topics: Seq[Topic] = this.topics,
                       isSelected: Boolean = this.isSelected,
                       note: Option[String] = this.note,
                       isHideAbstract: Boolean = this.isHideAbstract): Publication =
        new Publication(renderer, authors, title, venue, pages, links, abstr, topics, isSelected, note, isHideAbstract)


    def title = this._title.replace("{", "").replace("}", "")

    def selected() = copy(isSelected = true)

    def topic(newTopics: Topic*) = copy(topics = newTopics)

    def note(s: String) = copy(note = Some(s))

    def crosscite(s: String) = this

    def acceptanceRate(accepted: Int, submitted: Int): Publication = copy(venue = venue.acceptanceRate(accepted, submitted))

    def hideabstract() = copy(isHideAbstract = true)


    implicit def stringTexWrapper(string: String) = new StringTexHelper(string)

    //*gen
    def genKey = authors.map(_.lastname.take(1)).mkString + ":" + (venue.short.toId) + (venue.year.toString.takeRight(2))

    def genId = genKey.replaceAll("\\W", "")

    /**
     * renders bibtex entry as line with markdown ** and *
     */
    def render(style: BibStyle): String = renderer.render(this, style)
}


trait BibStyle {
    def renderAuthor(p: Person): String

    def withAcceptanceRate: Boolean
}

object DefaultBibStyle extends BibStyle {
    def renderAuthor(p: Person) = p.fullname

    def withAcceptanceRate: Boolean = true
}

trait LinkKind {
    def print: String
}

object PDF extends LinkKind {
    def print = ".pdf"
}

object HTTP extends LinkKind {
    def print = "http"
}

object BIB extends LinkKind {
    def print = "bib"
}

object DOI extends LinkKind {
    def print = "doi"

    def apply(doi: String) = HTTPLink("http://dx.doi.org/" + doi)
}

object EPUB extends LinkKind {
    def print = "epub"
}

object ACMLink extends LinkKind {
    def print = "acm"
}

case class Thesis(
                     author: Person,
                     kind: Publication,
                     title: String,
                     school: String,
                     comments: String)

sealed abstract class PPages

case class Pages(from: String, to: String, extras: String = null) extends PPages

object Pages {
    def apply(f: Int, t: Int): Pages = Pages(f.toString, t.toString, null)

    def apply(f: String, t: String): Pages = Pages(f.toString, t.toString, null)

    def apply(f: Int, t: Int, e: String): Pages = Pages(f.toString, t.toString, e)
}


case class PagesStr(str: String) extends PPages

case class ToAppear() extends PPages

sealed abstract class Month

case class MonthNr(nr: Int) extends Month

case class MonthStr(str: String) extends Month


case class Venue(short: String, year: Int, name: String, kind: PublicationKind, url: Option[URL] = None, publisher: Option[Publisher] = None, acceptanceRate: Option[(Int, Int)] = None, location: Option[String] = None, month: Option[Month] = None, number: Option[String] = None, volume: Option[String] = None, series: Option[String] = None) {

    def copy(short: String = this.short, year: Int = this.year, name: String = this.name, kind: PublicationKind = this.kind, url: Option[URL] = this.url, publisher: Option[Publisher] = this.publisher, acceptanceRate: Option[(Int, Int)] = this.acceptanceRate, location: Option[String] = this.location, month: Option[Month] = this.month, number: Option[String] = this.number, volume: Option[String] = this.volume, series: Option[String] = this.series) =
        Venue(short, year, name, kind, url, publisher, acceptanceRate, location, month, number, volume, series)

    def issn(s: String) = this

    def isbn(s: String): Venue = this

    def editor(s: String): Venue = this


    def name(n: String): Venue = copy(name = n)

    def subtitle(n: String): Venue = copy(name = this.name + " (" + n + ")")

    def location(loc: String): Venue = copy(location = Some(loc))

    def kind(k: PublicationKind): Venue = copy(kind = k)

    def publisher(pub: Publisher): Venue = Venue(short, year, name, kind, url, Some(pub), acceptanceRate, location, month, number, volume, series)

    def month(m: Int): Venue = Venue(short, year, name, kind, url, publisher, acceptanceRate, location, Some(MonthNr(m)), number, volume, series)

    def month(m: String): Venue = Venue(short, year, name, kind, url, publisher, acceptanceRate, location, Some(MonthStr(m)), number, volume, series)

    def url(u: URL): Venue = Venue(short, year, name, kind, Some(u), publisher, acceptanceRate, location, month, number, volume, series)

    def number(u: String): Venue = Venue(short, year, name, kind, url, publisher, acceptanceRate, location, month, Some(u), volume, series)

    def number(u: Int): Venue = Venue(short, year, name, kind, url, publisher, acceptanceRate, location, month, Some(u.toString), volume, series)

    def volume(u: String): Venue = Venue(short, year, name, kind, url, publisher, acceptanceRate, location, month, number, Some(u), series)

    def volume(u: Int): Venue = Venue(short, year, name, kind, url, publisher, acceptanceRate, location, month, number, Some(u.toString), series)

    def series(u: String): Venue = Venue(short, year, name, kind, url, publisher, acceptanceRate, location, month, number, volume, Some(u))

    def acceptanceRate(accepted: Int, submitted: Int): Venue = Venue(short, year, name, kind, url, publisher, Some((accepted, submitted)), location, month, number, volume, series)


    def renderDate: String =
        month.map({
            case MonthNr(i) => TextHelper.renderMonth(i)
            case MonthStr(s) => s
        }).map(_ + " ").getOrElse("") + year

    def renderPublisher: String = publisher.map(_.render + ", ").getOrElse("")

    def renderVolSeries =
        if (volume.isDefined && series.isDefined)
            "volume %s of *%s*, ".format(volume.get, series.get)
        else if (volume.isDefined && !series.isDefined)
            "volume %s, ".format(volume.get)
        else ""
}

case class Publisher(name: String, address: String) {
    def render: String = (if (address.isEmpty) "" else address + ": ") + name
}


abstract class PublicationKind(val name: String, val order: Int) extends Ordered[PublicationKind] {
    def key = this.getClass.getSimpleName.replace("$", "")

    def compare(that: PublicationKind) = order compare that.order
}


case class Topic(name: String) {
    def key = name.replaceAll("\\W", "")
}


sealed abstract class Language

object English extends Language {
    override def toString = "English"
}

object German extends Language {
    override def toString = "German"
}

sealed abstract class Term(val year: Int) extends Ordered[Term]

case class SummerTerm(ayear: Int) extends Term(ayear) {
    def compare(that: Term) = that match {
        case SummerTerm(thatYear) => year.compareTo(thatYear)
        case WinterTerm(thatYear) => if (year == thatYear) -1 else year.compareTo(thatYear)
    }

    override def toString = "Summer Term " + year
}

case class WinterTerm(ayear: Int) extends Term(ayear) {
    def compare(that: Term) = that match {
        case SummerTerm(thatYear) => if (year == thatYear) 1 else year.compareTo(thatYear)
        case WinterTerm(thatYear) => year.compareTo(thatYear)
    }

    override def toString = "Winter Term " + year + "/" + (year + 1 - 2000)
}


case class Course(
                     title: String,
                     titleAlt: String,
                     url: URL,
                     language: Language = English,
                     term: Term,
                     kind: CourseKind,
                     note: String = "") {
    def getTitleAlt = if (titleAlt.isEmpty) title else titleAlt
}

case class TeachingProject(
                              title: String,
                              term: Term,
                              kind: String,
                              url: URL = null
                              )

class CourseKind {
    def isLecture: Boolean = false

    def isExercise: Boolean = false

    def isSeminar: Boolean = false

    def isTutorium: Boolean = false
}

trait Lecture extends CourseKind {
    override def isLecture = true
}

trait Exercise extends CourseKind {
    override def isExercise = true
}

object Seminar extends CourseKind {
    override def isSeminar = true
}

object Tutorium extends CourseKind {
    override def isTutorium = true
}


case class Committee(venue: Venue, role: CommitteeRole)

abstract class CommitteeRole(val title: String, val abbreviation: String)

object PC extends CommitteeRole("Program-Committee Member", "PC")

object OC extends CommitteeRole("Organization-Committee Member", "OC")

object TC extends CommitteeRole("Tool-Demonstration Committee Member", "TC")

object DS extends CommitteeRole("Doctorial-Symposium Committee Member", "DC")

case class OtherCommittee(long: String, short: String) extends CommitteeRole(long, short)

case class Review(
                     venue: Venue,
                     invitedBy: String = ""
                     )

class URLException(link: String, e: Exception) extends Exception {
    override def toString = "Cannot resolve URL " + link + " (" + e + ")"
}

trait URL {
    def check(): Boolean
}

private[cv] case class HTTPLink(link: String, ignoreError: Boolean = false) extends URL {

    override def toString = link

    def check(): Boolean = if (ignoreError) true
    else {
        //check that link is valid when printing it
        try {
            val connection = new java.net.URL(link).openConnection()
            connection.setConnectTimeout(1000)
            connection.connect()
            true
        } catch {
            case e => false
        }
    }
}

private[cv] case class PDFLink(filename: String) extends URL {
    def check(): Boolean = new File(Config.pdfPath, filename).exists()

    override def toString = Config.pdfWebPath + filename
}

object URL {
    def apply(link: String, ignoreError: Boolean = false): URL = new HTTPLink(link, ignoreError)
}

object PDFFile {
    def apply(filename: String): URL = PDFLink(filename)
}


object StructureTheses {
    implicit def stringTexWrapper(string: String) = new StringTexHelper(string)

    sealed abstract class AThesis(
                                     val author: Person,
                                     val title: String,
                                     val where: School,
                                     val when: (Int, Int), //month, year
                                     val kind: ThesisKind,
                                     val pdf: URL,
                                     val note: String

                                     ) {
        def genKey = "thesis" + author.lastname.toId + kind.baMarker

        def toBibtex: String = {
            var r = """
                  @MastersThesis{%s,
                    AUTHOR = {%s},
                    TITLE = {%s},
                    SCHOOL = {%s},
                    TYPE = {%s},
                    ADDRESS = {%s},
                    MONTH = %d,
                    YEAR = %d,""".format(genKey, author.fullname.toTex, title.toTex, where.name.toTex, kind.name, where.country.toTex, when._1, when._2)
            if (pdf != null)
                r += "PDF={%s},".format(pdf)
            if (note != null && note.length > 0)
                r += "NOTE={%s},".format(note.toTex)
            r + "\n}\n"
        }

        def shortText(inQuotes: String => String): String

        def year = when._2

        def month = when._1

        def monthStr: String = TextHelper.renderMonth(month)
    }

    case class Thesis(
                         aauthor: Person,
                         atitle: String,
                         awhere: School,
                         awhen: (Int, Int), //month, year
                         akind: ThesisKind,
                         apdf: URL = null,
                         anote: String = ""
                         ) extends AThesis(aauthor, atitle, awhere, awhen, akind, apdf, anote) {
        def shortText(inQuotes: String => String): String = kind.nameAlt + " " + inQuotes(title)

    }

    case class GermanThesis(
                               aauthor: Person,
                               atitle: String,
                               translation: String,
                               awhere: School,
                               awhen: (Int, Int), //month, year
                               akind: ThesisKind,
                               apdf: URL = null,
                               anote: String = ""
                               ) extends AThesis(aauthor, atitle, awhere, awhen, akind, apdf, anote) {
        def shortText(inQuotes: String => String): String = kind.nameAlt + " on " + translation + " (in German)"
    }

    trait School {
        def name: String

        def country: String
    }

    trait ThesisKind {
        def name: String

        def nameAlt: String

        def baMarker: String = ""
    }

    object MastersThesis extends ThesisKind {
        def name = "Master's Thesis"

        def nameAlt = "Master's thesis"
    }

    object BachelorsThesis extends ThesisKind {
        def name = "Bachelor's Thesis"

        def nameAlt = "Bachelor's thesis"

        override def baMarker: String = "BA"
    }

    object Studienarbeit extends ThesisKind {
        def name = "Bachelor's Thesis (Studienarbeit)"

        def nameAlt = "Bachelor's thesis"

        override def baMarker: String = "S"
    }

    object Diplomarbeit extends ThesisKind {
        def name = "Master's Thesis (Diplomarbeit)"

        def nameAlt = "Diploma thesis"
    }

    object MD extends School {
        def name = "University of Magdeburg"

        def country = "Germany"
    }

    object MR extends School {
        def name = "University of Marburg"

        def country = "Germany"
    }

    object PA extends School {
        def name = "University of Passau"

        def country = "Germany"
    }

}