package de.stner.cv

import java.net.URL


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

sealed abstract class Term(year:Int)
case class SummerTerm(year: Int) extends Term(year)
case class WinterTerm(year: Int) extends Term(year)


case class Course(title: String, url:URL, language: Language = English, term:Term)

case class Committee(venue:Venue, role:CommitteeRole)

abstract class CommitteeRole(title:String, abbreviation:String)
object PC extends CommitteeRole("Program-Committee Member", "PC")
object OC extends CommitteeRole("Organization-Committee Member", "OC")
object TC extends CommitteeRole("Tool-Demonstration-Committee Member", "TC")