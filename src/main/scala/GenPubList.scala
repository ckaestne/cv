package de.stner.cv

import de.stner.cv.CVPublications._
import java.io._
import java.text.{DecimalFormat, SimpleDateFormat}
import org.apache.commons.io.FileUtils


/**
 * simple textual rendering for the CMU reappointment and promotion committees
 */
object GenPubList extends App {

    import CV._

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
        counter+".  "+p.render(new BibStyle {
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
        val confworkshop = pubs.filter(Set(KWorkshopDemoTool, KInConferenceProceedings) contains _.venue.kind).filterNot(_.note.getOrElse("").contains("Poster")) //.filter(_.pages.isInstanceOf[ToAppear])
        val techreps = pubs.filter(KTechnicalReport == _.venue.kind)
        val other = pubs diff (books ++ publishedJournal ++ acceptedJournal ++ confworkshop ++ techreps)


        printPublications("BOOKS", books) ++
            printPublications("REFEREED JOURNAL PAPERS - PUBLISHED", publishedJournal) ++
            printPublications("REFEREED JOURNAL PAPERS - ACCEPTED", acceptedJournal) ++
            printPublications("REFEREED CONFERENCE/WORKSHOP PAPERS", confworkshop) ++
            printPublications("TECHNICAL REPORTS", techreps) ++
            printPublications("OTHER PUBLICATIONS", other)
    }


    //            CVPublications.publicationKinds.map(printPublicationType(_)).mkString("\n\n\n")


    var output = ""

    output += publications()


    println(output)
    //    val fw = new FileWriter("publist.txt")
    //    fw.write(output)
    //    fw.close()

}
