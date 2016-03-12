package de.stner.cv

import org.scalatest.FunSuite
import java.io.File

class Validate extends FunSuite {

    import CV._
    import CVTheses.advisedTheses

    def checkURL(url: URL, c: Any = null) {
        assert(url == null || url.check, "URL cannot be resolved " + url + " from " + c)
    }


    test("check urls: committees") {
        committees.map(c => c.venue.url.map(checkURL(_, c)))
    }
    test("check urls: reviews") {
        reviews.map(c => c.venue.url.map(checkURL(_, c)))
    }


    test("check urls: teaching") {
        teaching.map(c => checkURL(c.url, c))
        teachingProjects.map(c => checkURL(c.url, c))
    }

    test("check urls: publications") {
        publications.map(p => p.links.values.map(checkURL(_, p)))
    }

    test("check urls: advised theses") {
        advisedTheses.map(c => checkURL(c.pdf, c))
    }

    test("check urls: authors") {
        val allAuthors=publications.map(_.authors).flatten.toSet
        allAuthors.map(c => c.url.map(checkURL(_, c)))
    }


    test("all pdf links in publications should be local") {
        for (p <- publications) {
            p.links.get(PDF) map {
                case PDFLink(_) => //great
                case HTTPLink(url, _) =>
                    System.err.println("Warning: publication %s has remote pdf link %s, consider a local one".format(p.genKey, url))
            }
        }
    }

    test("all publications should have pdfs (Warning level)") {
        for (p <- publications)
            if (!p.links.isEmpty && !p.links.contains(PDF))
                if (p.pages == ToAppear())
                    System.err.println("Info: publication %s still marked as `to appear'".format(p.genKey))
                else
                    System.err.println("Warning: publication %s does not have a pdf link".format(p.genKey))
    }

    test("unlinked pdfs") {
        assert(Config.pdfPath.exists(), "pdf path not found " + Config.pdfPath)
        for (f <- Config.pdfPath.listFiles() /*; if (f.getName.toLowerCase.endsWith(".pdf"))*/ ) {
            def hasFile(p: Publication): Boolean =
                p.links.values.exists({
                    case PDFLink(file) => new File(Config.pdfPath, file) == f
                    case _ => false
                })
            def hasFileT(p: StructureTheses.AThesis): Boolean = p.pdf match {
                case PDFLink(file) => new File(Config.pdfPath, file) == f
                case _ => false
            }

            if (!publications.exists(hasFile) && !advisedTheses.exists(hasFileT))
                System.err.println("Warning: file %s is not linked from any publication".format(f))
        }
    }


}
