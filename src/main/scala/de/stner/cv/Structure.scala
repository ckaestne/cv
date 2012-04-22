package de.stner.cv


case class Person(
                   firstname: String,
                   lastname: String,
                   url: URL,
                   affiliation: String = "")

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

object English extends Language

object German extends Language

sealed abstract class Term(year: Int) extends Comparable[Term]

case class SummerTerm(year: Int) extends Term(year) {
  def compareTo(that: Term) = that match {
    case SummerTerm(thatYear) => year.compareTo(thatYear)
    case WinterTerm(thatYear) => if (year == thatYear) -1 else year.compareTo(thatYear)
  }
  override def toString = "Summer Term "+year
}

case class WinterTerm(year: Int) extends Term(year) {
  def compareTo(that: Term) = that match {
    case SummerTerm(thatYear) => if (year == thatYear) 1 else year.compareTo(thatYear)
    case WinterTerm(thatYear) => year.compareTo(thatYear)
  }
  override def toString = "Winter Term "+year+"/"+(year+1-2000)
}


case class Course(title: String, url: URL, language: Language = English, term: Term)

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