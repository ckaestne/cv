package de.stner.cv

import de.stner.cv.CVPublications._
import de.stner.cv.CV._
import java.io._
import java.text._


/**
 * simple textual rendering for the CMU reappointment and promotion committees
 */
object GenPubList extends App {


    implicit def stringTexWrapper(string: String) = new StringTexHelper(string)


    object TextFormater extends Formater[String] {
        def title(t: String): String = t

        def journal(s: String): String = s

        def concat(c: String*): String = c.mkString

        def concatL(c: Seq[String]): String = c.mkString

        def newBlock: String = " "

        def text(s: String): String = s

        def person(person: Person): String = person.fullname

        def markdown(m: String): String = m.markdownToPlainText
    }


    def section(name: String, body: String): String =
        name + "\n====\n\n" + body + "\n\n\n\n"

    var counter = 0

    def printPublication(p: Publication) = {
        counter += 1
        counter + ".  " + p.render(new BibStyle {
            override def withAcceptanceRate: Boolean = false
        }, TextFormater).markdownToPlainText + "\n\n"
    }

    def printPublications(title: String, pubs: Seq[Publication]) =
        section(title,
            (for (p <- pubs) yield printPublication(p)).mkString)


    def publications(): String = {

        var pubs = CV.publications.reverse.sortBy(-_.venue.year)

        val books = pubs.filter(KBook == _.venue.kind)
        val publishedJournal = pubs.filter(KJournal == _.venue.kind).filterNot(_.pages.isInstanceOf[ToAppear])
        val acceptedJournal = pubs.filter(KJournal == _.venue.kind).filter(_.pages.isInstanceOf[ToAppear])
        val conf = pubs.filter(KInConferenceProceedings == _.venue.kind).filterNot(_.note.getOrElse("").contains("Poster")) //.filter(_.pages.isInstanceOf[ToAppear])
        val workshop = pubs.filter(KWorkshopDemoTool == _.venue.kind).filterNot(_.note.getOrElse("").contains("Poster")) //.filter(_.pages.isInstanceOf[ToAppear])
        val techreps = pubs.filter(KTechnicalReport == _.venue.kind)
        val other = pubs diff (books ++ publishedJournal ++ acceptedJournal ++ conf ++ workshop ++ techreps)


        printPublications("BOOKS", books) ++
            printPublications("REFEREED JOURNAL PAPERS - PUBLISHED", publishedJournal) ++
            printPublications("REFEREED JOURNAL PAPERS - ACCEPTED", acceptedJournal) ++
            printPublications("REFEREED CONFERENCE PAPERS", conf) ++
            printPublications("REFEREED WORKSHOP PAPERS", workshop) ++
            printPublications("TECHNICAL REPORTS", techreps) ++
            printPublications("OTHER PUBLICATIONS", other)
    }
    val monthyear = new SimpleDateFormat("MMM. yyyy")

    def invitedTalks(): String =
        (for (it <- CV.invitedTalks)
        yield "%s\t%s, %s".format(
                monthyear format it.when,
                it.title.markdownToPlainText,
                it.where)
            ).reduce(_ + "\n" + _)


    //            CVPublications.publicationKinds.map(printPublicationType(_)).mkString("\n\n\n")

    def printCommittee(c: Committee) =
        "%s %d: %s".format(c.venue.short.toTex, c.venue.year, c.venue.name.toTex) +
            (if (c.role != PC) " -- " + c.role.title else "") + "\n"

    def printCommittees(): String = {
        section("Organization Committees",
            committees.filter(Set(OC, PCChair, SC) contains _.role).map(printCommittee).mkString +
                "Annual Meeting on Feature-Oriented Software Development (2009 Passau, 2010 Magdeburg, 2011 Dresden, 2012 Braunschweig, 2013 and 2014 Dagstuhl, 2015 Traunkirchen)\n"
        ) + section("Program Committees (Conferences)",
                committees_conferences.filterNot(Set(OC, PCChair) contains _.role).map(printCommittee).mkString
            )    +
                section("Program Committees (Workshops and Other)",
                    committees_workshops.filterNot(Set(OC, PCChair) contains _.role).filter(_.venue.kind == KWorkshopDemoTool).map(printCommittee).mkString
                )
    }

    var output = ""

    output += publications()


    //    println(output)
    var fw = new FileWriter("publist.txt")
    fw.write(output)
    fw.close()


     fw = new FileWriter("invitedtalks.txt")
    fw.write(invitedTalks())
    fw.close()


     fw = new FileWriter("committees.txt")
    fw.write(printCommittees())
    fw.close()
}
