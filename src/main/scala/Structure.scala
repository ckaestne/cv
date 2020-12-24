package de.stner.cv

import de.stner.cv.VenueStructure._

import java.io.File
import java.time.LocalDate

object Config {
    val pdfWebPath = "pdf/"

    val pdfPath = new File("src/main/pdf/")
}

object Person {
    def apply(first: String, last: String, url: URL, affil: String) = new Person(first, last, Some(url), Some(affil))

    def apply(first: String, last: String, affil: String) = new Person(first, last, None, Some(affil))

    def apply(first: String, last: String) = new Person(first, last, None, None)
}

case class Person(
                     firstname: String,
                     lastname: String,
                     url: Option[URL] = None,
                     affiliation: Option[String] = None) {
    def fullname: String = firstname + " " + lastname

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
        def renderRest[A](pub: Publication, style: BibStyle, formater: Formater[A]): A = {
            assert(pub.venue.kind == KInConferenceProceedings || pub.venue.kind == KWorkshopDemoTool, "InProceedings with venue.kind=" + pub.venue.kind + " not supported")
            formater.concat(
                formater.text("In "),
                formater.journal("Proceedings of the %s (%s)".format(pub.venue.name, pub.venue.short)),
                formater.text(", "),
                pub.venue.renderVolSeries(formater),
                renderPages(pub, formater),
                pub.venue.renderPublisher(formater), pub.venue.renderDate(formater), formater.dot
            )

        }

        override def getBibtexFields(p: Publication) = super.getBibtexFields(p) + ("booktitle" -> "Proceedings of the %s (%s)".format(p.venue.name, p.venue.short))
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

        def renderRest[A](pub: Publication, style: BibStyle, formater: Formater[A]): A = {
            assert(pub.venue.kind == KJournal || pub.venue.kind == KInvited, "Article with venue.kind=" + pub.venue.kind + " not supported " + pub.title)
            formater.concat(
                formater.journal(
                    pub.venue.name +
                        (if (pub.venue.short != "") " (" + pub.venue.short + ")" else "") +
                        pub.venue.specialIssue.map(", Special Issue on " + _).getOrElse("")),
                formater.text(", "),
                formater.text(renderVolNumber(pub.venue)),
                renderPages(pub, true, formater),
                pub.venue.renderDate(formater),
                formater.dot
            )
        }

        override def getBibtexFields(p: Publication) = super.getBibtexFields(p) + ("journal" -> p.venue.name)
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
        new Publication(renderer, authors, title, Venue(null, year, typ, KMisc, None).month(month).publisher(school), null, links, abstr)

    val renderer = new PublicationRenderer {
        def renderRest[A](pub: Publication, style: BibStyle, formater: Formater[A]): A =
            formater.concat(
                formater.text(pub.venue.name), formater.text(", "), pub.venue.renderPublisher(formater), pub.venue.renderDate(formater), formater.dot
            )

        override def getBibtexFields(p: Publication) = super.getBibtexFields(p) + ("institution" -> p.venue.publisher.map(_.name).getOrElse(""))
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
        def renderRest[A](pub: Publication, style: BibStyle, formater: Formater[A]): A = {
            assert(pub.venue.kind == KTechnicalReport, "TechReport with venue.kind=" + pub.venue.kind + " not supported")
            formater.concat(
                formater.text("Technical Report " + pub.venue.number.get + ", "),
                pub.venue.renderPublisher(formater),
                pub.venue.renderDate(formater),
                formater.dot
            )
        }

        override def getBibtexFields(p: Publication) = super.getBibtexFields(p) + ("institution" -> p.venue.publisher.map(_.name).getOrElse(""))
    }
}


object Book {
    def apply(authors: Seq[Person],
              title: String,
              venue_ : Venue /*title and short are not used!)*/ ,
              links: Map[LinkKind, URL],
              abstr: String) =
        new Publication(rendererAuthors, authors, title, venue_, null, links, abstr)

    val rendererAuthors = new PublicationRenderer {
        def renderRest[A](p: Publication, style: BibStyle, formater: Formater[A]): A = formater.none

        override def render[A](pub: Publication, style: BibStyle, formater: Formater[A]): A =
            formater.concat(
                renderAuthors(pub, formater),
                formater.dot, formater.space, formater.newBlock,
                formater.title(pub.title), formater.text(endDot(pub.title)),
                formater.space, formater.newBlock,
                pub.venue.renderPublisher(formater), pub.venue.renderDate(formater), formater.dot,
                (pub.note.map(t => formater.concat(formater.space, formater.newBlock, formater.markdown(t), formater.dot)).getOrElse(formater.none))
            )

        override def getBibtexAuthorField(p: Publication): Map[String, String] =
            Map("author" -> p.authors.map(_.fullname.toTex).mkString(" and "))
    }
}

object BookEd {
    def apply(editors: Seq[Person],
              title: String,
              venue_ : Venue /*title and short are not used!)*/ ,
              links: Map[LinkKind, URL],
              abstr: String) =
        new Publication(rendererEditors, editors, title, venue_, null, links, abstr)

    val rendererEditors = new PublicationRenderer {
        def renderRest[A](p: Publication, style: BibStyle, formater: Formater[A]): A = formater.none

        override def render[A](pub: Publication, style: BibStyle, formater: Formater[A]): A =
            formater.concat(
                renderAuthors(pub, formater),
                formater.text(", editor" + (if (pub.authors.size > 1) "s" else "")),
                formater.dot, formater.space, formater.newBlock,
                formater.title(pub.title), formater.text(endDot(pub.title)),
                formater.space, formater.newBlock,
                pub.venue.renderPublisher(formater), pub.venue.renderDate(formater), formater.dot,
                (pub.note.map(t => formater.concat(formater.space, formater.newBlock, formater.markdown(t), formater.dot)).getOrElse(formater.none))
            )

        override def getBibtexAuthorField(p: Publication): Map[String, String] =
            Map("editor" -> p.authors.map(_.fullname.toTex).mkString(" and "))
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
        def renderRest[A](pub: Publication, style: BibStyle, formater: Formater[A]): A =
            formater.concat(formater.text("In "), formater.journal(pub.venue.name), formater.text(", "),
                pub.venue.renderVolSeries(formater),
                renderPages(pub, formater),
                pub.venue.renderPublisher(formater),
                pub.venue.renderDate(formater), formater.dot
            )

        override def getBibtexFields(p: Publication) = super.getBibtexFields(p) + ("booktitle" -> p.venue.name)
    }
}

/**
 * default renderer, subclass for specifics
 */
abstract class PublicationRenderer {
    implicit def stringTexWrapper(string: String) = new StringTexHelper(string)

    def endDot(s: String) = if (Set('.', '!', '?') contains s.last) "" else "."

    def render[A](p: Publication, style: BibStyle, formater: Formater[A]): A =
        formater.concat(
            renderAuthors(p, formater),
            formater.dot, formater.space, formater.newBlock,
            formater.title(p.title), formater.text(p.title.endDot()),
            formater.space, formater.newBlock,
            renderRest(p, style, formater),
            (if (style.withAcceptanceRate) renderAcceptanceRate(p, formater) else formater.none),
            (p.note.map(t => formater.concat(formater.space, formater.newBlock, formater.markdown(t), formater.dot)).getOrElse(formater.none))
        )

    def renderRest[A](p: Publication, style: BibStyle, formater: Formater[A]): A

    def renderAuthors[A](p: Publication, formater: Formater[A]): A = {
        assert(!p.authors.isEmpty, "no authors for " + this)
        if (p.authors.size == 1) formater.person(p.authors.head)
        else formater.concat(
            formater.concatL(p.authors.dropRight(1).map(a => formater.concat(formater.person(a), formater.text(", ")))),
            formater.text("and "),
            formater.person(p.authors.last))
    }

    private def pextr(e: String): String = if (e == null) "" else ", " + e

    def renderPages[A](p: Publication, formater: Formater[A]): A = formater.text(renderPages(p))

    def renderPages[A](p: Publication, short: Boolean, formater: Formater[A]): A = formater.text(renderPages(p, short))

    def renderPages(p: Publication, short: Boolean = false): String = {
        if (p.pages == null) ""
        else p.pages match {
            case Pages(a, b, e) if (a == b) => (if (short) "" else "page ") + a + pextr(e) + ", "
            case Pages(a, b, e) => (if (short) "" else "pages ") + "%s--%s%s, ".format(a, b, pextr(e))
            case PagesStr(s) => s + ", "
            case ToAppear() => ""
        }
    }

    def renderAcceptanceRate[A](p: Publication, formater: Formater[A]): A = formater.text(
        p.venue.acceptanceRate.map(
            e => " Acceptance rate: %d %% (%d/%d).".format(scala.math.round(e._1.toDouble / e._2.toDouble * 100), e._1, e._2)
        ).getOrElse("")
    )

    protected def getBibtexAuthorField(p: Publication): Map[String, String] =
        Map("author" -> p.authors.map(_.fullname.toTex).mkString(" and "))

    def getBibtexFields(p: Publication): Map[String, String] = {
        var r = getBibtexAuthorField(p) ++ Map(
            "title" -> p.title,
            "year" -> p.venue.year.toString
        )
        if (p.pages != null) p.pages match {
            case Pages(a, b, e) => r += ("pages" -> "%s--%s%s".format(a, b, pextr(e)))
            case PagesStr(s) => r += ("pages" -> s)
            case _ =>
        }
        if (p.note.isDefined)
            r += ("note" -> p.note.get)
        for ((lkind, url) <- p.links)
            r += (lkind.bibtexKey -> url.toString)
        if (p.venue.url.isDefined)
            r += ("vurl" -> p.venue.url.get.toString)
        if (p.venue.publisher.isDefined && p.venue.publisher.get != null)
            r = r + ("publisher" -> p.venue.publisher.get.name) + ("address" -> p.venue.publisher.get.address)
        if (p.venue.location.isDefined)
            r += ("location" -> p.venue.location.get)
        if (p.venue.month.isDefined)
            r += ("month" -> p.venue.month.get.toBibString)
        if (p.venue.number.isDefined)
            r += ("number" -> p.venue.number.get.toString)
        if (p.venue.volume.isDefined)
            r += ("volume" -> p.venue.volume.get.toString)
        if (p.venue.series.isDefined)
            r += ("series" -> p.venue.series.get.toString)

        r
    }

    def toBibtex(p: Publication): String =
        "@" + (p.venue.kind match {
            case KInConferenceProceedings => "inproceedings"
            case KWorkshopDemoTool => "inproceedings"
            case KJournal => "article"
            case KTechnicalReport => "techreport"
            case KBook => "book"
            case KMisc => "misc"
            case KInvited => "misc"
        }) + "{" + p.genKey + ",\n" +
            getBibtexFields(p).map(v => "\t" + v._1 + "={" + v._2 + "},").mkString("\n") +
            "\n}"

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
    def render[A](style: BibStyle, formater: Formater[A]): A = renderer.render(this, style, formater)

    def toBibtex(): String = renderer.toBibtex(this)

    override def toString(): String =  render(DefaultBibStyle, GenPubList.TextFormater)

}

trait Formater[A] {
    def title(t: String): A

    def journal(s: String): A

    def concat(c: A*): A

    def concatL(c: Seq[A]): A

    def newBlock: A

    def text(s: String): A

    def space: A = text(" ")

    def dot: A = text(".")

    def none: A = text("")

    def person(person: Person): A

    def markdown(m: String): A
}


trait BibStyle {
    def withAcceptanceRate: Boolean
}

object DefaultBibStyle extends BibStyle {
    def withAcceptanceRate: Boolean = true
}

object SimpleBibStyle extends BibStyle {
    def withAcceptanceRate: Boolean = false
}

trait LinkKind {
    def print: String

    def bibtexKey: String = print
}

object PDF extends LinkKind {
    def print = ".pdf"

    override def bibtexKey = "pdf"
}

object HTTP extends LinkKind {
    def print = "http"

    override def bibtexKey = "url"
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

object Video extends LinkKind {
    def print = "video"
}
object Teaser extends LinkKind {
    def print = "teaser"
}

object YouTube {
  def apply(videoKey: String) = HTTPLink("https://www.youtube.com/watch?v=" + videoKey)
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

sealed abstract class Month {
    def toBibString: String
}

case class MonthNr(nr: Int) extends Month {
    def toBibString: String = nr.toString
}

case class MonthStr(str: String) extends Month {
    def toBibString: String = str
}


case class Venue(short: String, year: Int, name: String, kind: PublicationKind, url: Option[URL] = None, publisher: Option[Publisher] = None, acceptanceRate: Option[(Int, Int)] = None, location: Option[String] = None, month: Option[Month] = None, number: Option[String] = None, volume: Option[String] = None, series: Option[String] = None, specialIssue: Option[String] = None) {

    def copy(short: String = this.short, year: Int = this.year, name: String = this.name, kind: PublicationKind = this.kind, url: Option[URL] = this.url, publisher: Option[Publisher] = this.publisher, acceptanceRate: Option[(Int, Int)] = this.acceptanceRate, location: Option[String] = this.location, month: Option[Month] = this.month, number: Option[String] = this.number, volume: Option[String] = this.volume, series: Option[String] = this.series, specialIssue: Option[String] = this.specialIssue) =
        Venue(short, year, name, kind, url, publisher, acceptanceRate, location, month, number, volume, series, specialIssue)

    def issn(s: String) = this

    def isbn(s: String): Venue = this

    def editor(s: String): Venue = this


    def name(n: String): Venue = copy(name = n)

    def subtitle(n: String): Venue = copy(name = this.name + " (" + n + ")")

    def location(loc: String): Venue = copy(location = Some(loc))

    def kind(k: PublicationKind): Venue = copy(kind = k)

    def publisher(pub: Publisher): Venue = copy(publisher = Some(pub))

    def month(m: Int): Venue = copy(month = Some(MonthNr(m)))

    def month(m: String): Venue = copy(month = Some(MonthStr(m)))

    def url(u: URL): Venue = copy(url = Some(u))

    def number(u: String): Venue = copy(number = Some(u))

    def number(u: Int): Venue = copy(number = Some(u.toString))

    def volume(u: String): Venue = copy(volume = Some(u))

    def volume(u: Int): Venue = copy(volume = Some(u.toString))

    def series(u: String): Venue = copy(series = Some(u))

    def acceptanceRate(accepted: Int, submitted: Int): Venue = copy(acceptanceRate = Some((accepted, submitted)))

    def specialIssueOn(str: String): Venue = copy(specialIssue = Some(str))

    def renderDate[A](formater: Formater[A]): A = formater.text(
        month.map({
            case MonthNr(i) => TextHelper.renderMonth(i)
            case MonthStr(s) => s
        }).map(_ + " ").getOrElse("") + year)

    def renderPublisher[A](formater: Formater[A]): A = formater.text(publisher.map(_.render + ", ").getOrElse(""))

    def renderVolSeries[A](formater: Formater[A]): A =
        if (volume.isDefined && series.isDefined)
            formater.concat(formater.text("volume %s of ".format(volume.get)), formater.journal(series.get), formater.text(", "))
        else if (volume.isDefined && !series.isDefined)
            formater.text("volume %s, ".format(volume.get))
        else formater.none

    override def toString = s"Venue($name $year ($short))"
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

sealed abstract class Term(val year: Int, private val priority: Int) extends Ordered[Term] {
    def toShortString: String
    def compare(that: Term): Int = {
        if (this.priority== -1) 1
        else if (that.priority== -1) -1
        else if (year.compareTo(that.year)==0) this.priority.compareTo(that.priority)
        else  year.compareTo(that.year)
    }
}

case class SummerTerm(ayear: Int) extends Term(ayear,2) {
    override def toString = "Summer " + year
    def toShortString: String = "S"+year.toString().takeRight(2)
}

case class SpringTerm(ayear: Int) extends Term(ayear,1) {
    override def toString = "Spring " + year
    def toShortString: String = "S"+year.toString().takeRight(2)
}

case class WinterTerm(ayear: Int) extends Term(ayear,4) {
    override def toString = "Winter " + year + "/" + (year + 1 - 2000)
    def toShortString: String = "F"+year.toString().takeRight(2)
}

case class FallTerm(ayear: Int) extends Term(ayear,3) {
    override def toString = "Fall " + year
    def toShortString: String = "F"+year.toString().takeRight(2)
  }

case class Continuous(label: String) extends Term(5000, -1) {
    override def toString=label
    def toShortString: String = label
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


case class Committee(venue: Venue, role: CommitteeRole*)

abstract class CommitteeRole(val title: String, val abbreviation: String)

object PC extends CommitteeRole("Program-Committee Member", "PC")
object ERC extends CommitteeRole("External-Review-Committee Member", "ERC")

object GeneralChair extends CommitteeRole("General Chair", "General Chair")
object PCChair extends CommitteeRole("Program-Committee Chair", "PC Chair")
object PCCChair extends CommitteeRole("Program-Committee Co-Chair", "PC Co-Chair")
object ConfChair extends CommitteeRole("Conference Chair", "Conference Chair")
case class OtherChair(atitle: String, abbrev: String) extends CommitteeRole(atitle, abbrev)

object OC extends CommitteeRole("Organization-Committee Member", "OC")

object SC extends CommitteeRole("Steering-Committee Member", "SC")
object DS extends CommitteeRole("Doctorial Symposium Committee Member", "DS")
object DSCChair extends CommitteeRole("Doctoral Symposium Co-Chair", "DS Chair")

object CommitteeRoles {
    val organizationRoles = Set[CommitteeRole](GeneralChair, PCChair, PCCChair, ConfChair, OC, SC, DSCChair)
    val pcRoles = Set(PC, ERC)
    val pcAndDsRoles = pcRoles+DS
}

case class OtherCommittee(long: String, short: String) extends CommitteeRole(long, short)

case class Review(
                     venue: Venue,
                     invitedBy: String = ""
                     )

case class Editorship(roletitle: String, journal: Venue, startYear: Int, endYear: Option[Int]) {
    def yearRange: String = if (endYear.isDefined) (startYear+"-"+endYear.get) else ("since "+startYear)
    def yearRangeL: String = if (endYear.isDefined) (startYear+"--"+endYear.get) else ("since "+startYear)
}

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
            case e: Throwable => false
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

/** award name supports markdown */
sealed abstract class AwardOrGrant(val name: String, val url: URL, val date: LocalDate)

case class Award(aname: String, aurl: URL, dateAnnounced: LocalDate, extraLinks: List[(URL, String)] = Nil, budget: Option[Budget] = None) extends AwardOrGrant(aname, aurl, dateAnnounced)

case class Grant(aname: String, aurl: URL, dateAnnounced: LocalDate, dateBegin: LocalDate, dateEnd: LocalDate, foundingAg: String, budget: Budget) extends AwardOrGrant(aname, aurl, dateAnnounced)

sealed abstract class Budget(val value: Int)

case class EUR(v: Int) extends Budget(v)

case class USD(v: Int) extends Budget(v)


case class InvitedTalk(when: LocalDate, title: String, where: String)
