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
                          kind: PublicationKind,
                          authors: List[Person],
                          title: String,
                          venue: Venue,
                          pages: Range,
                          topics: Seq[Topic])

case class Thesis(
                     author: Person,
                     kind: Publication,
                     title: String,
                     school: String,
                     comments: String)

trait Venue {
    def year: Int

    def name: String

    def kind: PublicationKind

    def publisher: String

    def acceptanceRate: Option[(Int, Int)] = None
}

sealed abstract class PublicationKind

case class Journal() extends PublicationKind

case class InProceedings() extends PublicationKind

case class TechnicalReport() extends PublicationKind


case class Topic(name: String) extends PublicationKind


sealed abstract class Language

object English extends Language {
    override def toString = "English"
}

object German extends Language {
    override def toString = "German"
}

sealed abstract class Term(val year: Int) extends Comparable[Term]

case class SummerTerm(ayear: Int) extends Term(ayear) {
    def compareTo(that: Term) = that match {
        case SummerTerm(thatYear) => year.compareTo(thatYear)
        case WinterTerm(thatYear) => if (year == thatYear) -1 else year.compareTo(thatYear)
    }

    override def toString = "Summer Term " + year
}

case class WinterTerm(ayear: Int) extends Term(ayear) {
    def compareTo(that: Term) = that match {
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
    override def isSeminar = true
}


case class Committee(venue: Venue, role: CommitteeRole)

abstract class CommitteeRole(title: String, abbreviation: String)

object PC extends CommitteeRole("Program-Committee Member", "PC")

object OC extends CommitteeRole("Organization-Committee Member", "OC")

object TC extends CommitteeRole("Tool-Demonstration-Committee Member", "TC")


case class URL(link: String) {
    override def toString() = {
        //check that link is valid when printing it
        try {
            val connection = new java.net.URL(link).openConnection()
            connection.connect()
        } catch {
            case e => System.err.println("Cannot resolve URL " + link + " (" + e + ")")
        }
        link
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