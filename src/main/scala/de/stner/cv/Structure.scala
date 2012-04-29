package de.stner.cv


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
                          topics: Seq[Topic] = Seq()) {
    def selected() = this

    def note(s: String) = this

    def crosscite(s: String) = this
}

trait LinkKind

object PDF extends LinkKind

object HTTP extends LinkKind

case class Thesis(
                     author: Person,
                     kind: Publication,
                     title: String,
                     school: String,
                     comments: String)

case class Venue(short: String, year: Int, name: String, kind: PublicationKind, url: Option[URL], publisher: Option[Publisher] = None, acceptanceRate: Option[(Int, Int)] = None, location: Option[String] = None, month: Option[Int] = None, number: Option[String] = None, volume: Option[String] = None) {
    def location(loc: String): Venue = Venue(short, year, name, kind, url, publisher, acceptanceRate, Some(loc), month, number, volume)

    def publisher(pub: Publisher): Venue = Venue(short, year, name, kind, url, Some(pub), acceptanceRate, location, month, number, volume)

    def month(m: Int): Venue = Venue(short, year, name, kind, url, publisher, acceptanceRate, location, Some(m), number, volume)

    def url(u: URL): Venue = Venue(short, year, name, kind, Some(u), publisher, acceptanceRate, location, month, number, volume)

    def number(u: String): Venue = Venue(short, year, name, kind, url, publisher, acceptanceRate, location, month, Some(u), volume)

    def number(u: Int): Venue = Venue(short, year, name, kind, url, publisher, acceptanceRate, location, month, Some(u.toString), volume)

    def volume(u: String): Venue = Venue(short, year, name, kind, url, publisher, acceptanceRate, location, month, number, Some(u))

    def volume(u: Int): Venue = Venue(short, year, name, kind, url, publisher, acceptanceRate, location, month, number, Some(u.toString))
}

case class Publisher(name: String, address: String)

object Conference {
    def apply(short: String, year: Int, name: String, url: URL = null) =
        Venue(short, year, name, KInConferenceProceedings, if (url == null) None else Some(url))
}

object Workshop {
    def apply(short: String, year: Int, name: String, url: URL = null) =
        Venue(short, year, name, KInWorkshopProceedings, if (url == null) None else Some(url))
}

object Journal {
    def apply(short: String, year: Int, name: String, url: URL = null) =
        Venue(short, year, name, KJournal, if (url == null) None else Some(url))
}

object TechReport {

    def apply(year: Int, publisher: Publisher, number: String) =
        Venue("", year, "", KTechnicalReport, None).publisher(publisher).number(number)

}

sealed abstract class PublicationKind

object KJournal extends PublicationKind

object KInConferenceProceedings extends PublicationKind

object KInWorkshopProceedings extends PublicationKind

object KTechnicalReport extends PublicationKind


case class Topic(name: String) extends PublicationKind


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

        def monthStr: String = month match {
            case 1 => "January"
            case 2 => "February"
            case 3 => "March"
            case 4 => "April"
            case 5 => "May"
            case 6 => "June"
            case 7 => "July"
            case 8 => "August"
            case 9 => "September"
            case 10 => "October"
            case 11 => "November"
            case 12 => "December"
            case e => throw new RuntimeException("Invalid month " + e + " in " + this)
        }
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