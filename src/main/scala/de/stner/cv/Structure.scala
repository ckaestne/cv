package de.stner.cv

import scala.None
import de.stner.cv.CVPublications._


case class Person(
                     firstname: String,
                     lastname: String,
                     url: URL = null,
                     affiliation: String = "") {
    def fullname = firstname + " " + lastname

    def abbrvname = firstname.charAt(0) + ". " + lastname
}

case class Publication(
                          authors: Seq[Person],
                          title: String,
                          venue: Venue,
                          pages: Range,
                          links: Map[LinkKind, URL],
                          abstr: String = "",
                          topics: Seq[Topic] = Seq(),
                          isSelected: Boolean = false,
                          note: Option[String] = None,
                          acceptanceRate: Option[(Int, Int)] = None) {


    def selected() = Publication(authors, title, venue, pages, links, abstr, topics, true, note, acceptanceRate)

    def topic(newTopics: Topic*) = Publication(authors, title, venue, pages, links, abstr, newTopics, isSelected, note, acceptanceRate)

    def note(s: String) = Publication(authors, title, venue, pages, links, abstr, topics, isSelected, Some(s), acceptanceRate)

    def crosscite(s: String) = this

    def acceptanceRate(accepted: Int, submitted: Int): Publication = Publication(authors, title, venue, pages, links, abstr, topics, isSelected, note, Some((accepted, submitted)))


    //*gen
    def genKey = authors.map(_.lastname.take(1)).mkString + ":" + venue.short + (venue.year.toString.takeRight(2))

    def genId = genKey.replaceAll("\\W", "")

    def renderAuthors(renderOne: Person => String): String = {
        assert(!authors.isEmpty, "no authors for " + this)
        if (authors.size == 1) renderOne(authors.head)
        else authors.dropRight(1).map(renderOne).mkString(", ") + ", and " + renderOne(authors.last)
    }

    /**
     * renders bibtex entry as line with markdown ** and *
     */
    def render(style: BibStyle) =
        renderAuthors(style.renderAuthor) + ". [!]" +
            "**" + title + "**. [!]" +
            renderRest(style) +
            (note.map("[!] " + _ + ".").getOrElse(""))

    def renderProceedings(style: BibStyle) =
        "In *Proceedings of the %s (%s)*, ".format(venue.name, venue.short) +
            venue.renderVolSeries + renderPages + venue.renderPublisher + venue.renderDate + "."

    private def renderRest(style: BibStyle) = venue.kind match {
        case KInConferenceProceedings => renderProceedings(style)
        case KWorkshopDemoTool => renderProceedings(style)
        case _ => "rendering of " + genKey + " not yet implemented"
    }

    def renderPages =
        if (pages != null && pages.start == pages.end) "page " + pages.head + ", "
        else if (pages != null && !pages.isEmpty) "pages %d--%d, ".format(pages.head, pages.last)
        else ""
}


trait BibStyle {
    def renderAuthor(p: Person): String
}

object DefaultBibStyle extends BibStyle {
    def renderAuthor(p: Person) = p.fullname
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

case class Venue(short: String, year: Int, name: String, kind: PublicationKind, url: Option[URL], publisher: Option[Publisher] = None, acceptanceRate: Option[(Int, Int)] = None, location: Option[String] = None, month: Option[Int] = None, number: Option[String] = None, volume: Option[String] = None, series: Option[String] = None) {
    def issn(s: String) = this

    def isbn(s: String): Venue = this


    def location(loc: String): Venue = Venue(short, year, name, kind, url, publisher, acceptanceRate, Some(loc), month, number, volume, series)

    def publisher(pub: Publisher): Venue = Venue(short, year, name, kind, url, Some(pub), acceptanceRate, location, month, number, volume, series)

    def month(m: Int): Venue = Venue(short, year, name, kind, url, publisher, acceptanceRate, location, Some(m), number, volume, series)

    def url(u: URL): Venue = Venue(short, year, name, kind, Some(u), publisher, acceptanceRate, location, month, number, volume, series)

    def number(u: String): Venue = Venue(short, year, name, kind, url, publisher, acceptanceRate, location, month, Some(u), volume, series)

    def number(u: Int): Venue = Venue(short, year, name, kind, url, publisher, acceptanceRate, location, month, Some(u.toString), volume, series)

    def volume(u: String): Venue = Venue(short, year, name, kind, url, publisher, acceptanceRate, location, month, number, Some(u), series)

    def volume(u: Int): Venue = Venue(short, year, name, kind, url, publisher, acceptanceRate, location, month, number, Some(u.toString), series)

    def series(u: String): Venue = Venue(short, year, name, kind, url, publisher, acceptanceRate, location, month, number, volume, Some(u))


    def renderDate: String =
        month.map(TextHelper.renderMonth).map(_ + " ").getOrElse("") + year

    def renderPublisher: String = publisher.map(_.render + ", ").getOrElse("")

    def renderVolSeries =
        if (volume.isDefined && series.isDefined)
            "volume %d of *%s*, ".format(volume.get, series.get)
        else if (volume.isDefined && !series.isDefined)
            "volume %d, ".format(volume.get)
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

object TC extends CommitteeRole("Tool-Demonstration-Committee Member", "TC")

case class OtherCommittee(long: String, short: String) extends CommitteeRole(long, short)

case class Review(
                     venue: Venue,
                     invitedBy: String = ""
                     )

class URLException(link: String, e: Exception) extends Exception {
    override def toString = "Cannot resolve URL " + link + " (" + e + ")"
}

case class URL(link: String, ignoreError: Boolean = false) {

    override def toString = {
        if (!check())
            System.err.println("Cannot resolve link " + link)
        link
    }

    def check(): Boolean = if (ignoreError) true
    else {
        //check that link is valid when printing it
        try {
            val connection = new java.net.URL(link).openConnection()
            connection.connect()
            true
        } catch {
            case e => false
        }
    }
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
        def genKey = "thesis" + author.lastname.toAscii + kind.baMarker

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