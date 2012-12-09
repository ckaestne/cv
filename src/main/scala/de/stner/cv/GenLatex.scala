package de.stner.cv

import java.io.FileWriter
import java.text.{DecimalFormat, SimpleDateFormat}


object GenLatex extends App {

    import CV._

    implicit def stringTexWrapper(string: String) = new StringTexHelper(string)


    object LatexFormater extends Formater[String] {
        def title(t: String): String = "\\textbf{" + t + "}"

        def journal(s: String): String = "\\emph{" + s + "}"

        def concat(c: String*): String = c.mkString

        def concatL(c: Seq[String]): String = c.mkString

        def newBlock: String = "\\newBlock "

        def text(s: String): String = s

        def person(person: Person): String = person.fullname

        def markdown(m: String): String = m.markdownToTex(true)
    }


    def section(name: String, body: String): String =
        "\\pagebreak[3]\n\\section{" + name + "}\n" + body + "\n"

    def subsection(name: String, body: String): String =
        "\\subsection{" + name + "}\n" + body + "\n"

    def inCV(body: String): String = "\\begin{CV}\n" + body + "\\end{CV}\n"

    def printAwards(): String = {
        val r = awards.map({
            case Award(name, _, date, _, budget) => "\\item[%s] %s".format({monthyear format date}, name.markdownToTex(true)) + budget.map(b => " (%s)".format(formatBudget(b))).getOrElse("")
            case _ => ""
        })
        inCV(r.mkString("\n"))
    }

    def formatBudget(b: Budget) = b match {
        case EUR(v) => "{\\EUR{%s}}".format(new DecimalFormat("###,###").format(v).replace(",", "\\,"))
        case USD(v) => "{${%s}}".format(new DecimalFormat("###,###").format(v).replace(",", "\\,"))
    }

    val monthyear = new SimpleDateFormat("MMM. yyyy")

    def printGrants(): String = {
        val r = awards.map({
            case Grant(name, _, _, from, to, ag, budget) =>
                "\\item[] %s\\\\%s. %s, %s -- %s".format(
                    name.markdownToTex(true),
                    ag, formatBudget(budget),
                    monthyear format from, monthyear format to)
            case _ => ""
        })
        inCV(r.mkString("\n"))
    }

    def printInvitedTalks(): String = {
        val r = invitedTalks.map(it =>
            "\\item[%s] %s,\\\\%s".format(
                monthyear format it.when,
                it.title.markdownToTex(true),
                it.where)
        )
        inCV(r.mkString("\n"))
    }

    def courses(): String = {
        val l = teaching.filter(_.kind.isLecture).groupBy(c => (c.title, c.language)).toList.
            sortBy(c => c._2.map(_.term).max).reverse
        val r = l.map({
            case ((t, l), c) => "\\item[%s] %s (in %s)".format(c.map(_.term.year).sorted.mkString(", "), t, l)
        })
        subsection("Courses", inCV(r.mkString("\n")))
    }

    def seminars(): String = {
        val isRelevant = (c: Course) => c.kind.isSeminar || c.kind.isExercise || c.kind.isTutorium
        val l = teaching.filter(isRelevant).groupBy(c => (c.title, c.language)).toList.
            sortBy(c => c._2.map(_.term).max).reverse
        var r = l.map({
            case ((t, l), c) => "\\item[%s] %s (in %s)".format(c.map(_.term.year).sorted.mkString(", "), t, l)
        })
        val projectYears = teachingProjects.map(_.term)
        r = r :+ "\\item[%d--%d] Supervised %d Student Software Projects".format(projectYears.min.year, projectYears.max.year, teachingProjects.size)
        subsection("Exercise Classes, Seminars, and Others", inCV(r.mkString("\n")))
    }

    def theses(): String = {
        val r = advisedTheses.reverse.map(
            thesis => "\\item[%s, %d] %s.%s\n".format(
                thesis.author.abbrvname.toTex,
                thesis.when._2,
                thesis.shortText(x => "``" + x + "''"),
                if (thesis.note.isEmpty) "" else " \\emph{-- " + thesis.note.markdownToTex() + "}"
            )
        )
        subsection("Advising", inCV(r.mkString))
    }

    //    def printCommittee()

    def printCommittee(c: Committee) =
        "\\item[%s %d] %s".format(c.venue.short.toTex, c.venue.year, c.venue.name.toTex) +
            (if (c.role != OC && c.role != PC) " -- " + c.role.title else "") + "\n"

    def organizationCommittees(): String = subsection("Organization Committees", inCV(
        committees.filter(_.role == OC).map(printCommittee).mkString +
            "\\item[FOSD-Tr.\\ 2009-12] Annual German Student Meeting on Feature-Oriented Software Development (2009 Passau, 2010 Magdeburg, 2011 Dresden, 2012 Dagstuhl)\n"
    ))

    def programCommittees(): String = subsection("Program Committees", inCV(
        committees.filter(_.role != OC).map(printCommittee).mkString
    ))

    def printReview(r: Review) =
        "\\item[%s %d] %s".format(r.venue.short.toTex, r.venue.year, r.venue.name.toTex) +
            (if (!r.invitedBy.isEmpty) " (invited by " + r.invitedBy + ")" else "") + "\n"

    def reviewing(): String = subsection("Reviewing", inCV(reviews.map(printReview).mkString))

    def printPublication(p: Publication) =
        "\\bibitem{%s} %s\n\n".format(p.genKey, p.render(DefaultBibStyle, LatexFormater).markdownToTex(false))

    def publications(): String =
        "\\section{Publications \\hfill \\small \\normalfont  h-index: \\href{http://scholar.google.com/citations?user=PR-ZnJUAAAAJ}{20} ~~ g-index: 35}%\"C Kaester\" or \"C Kastner\" or \"C K?stner\"\n    \\begin{CV}\n    \\item[] Key publications are highlighted with \\selectedsymbol. PDF versions available online:\\\\\\url{http://www.uni-marburg.de/fb12/ps/team/kaestner}.\n    \\end{CV}\n    \\begin{thebibliography}{10}" +
            (for (p <- CV.publications) yield printPublication(p)).mkString + "\\end{thebibliography}{10}"

    val header = "\\documentclass[a4paper,10pt]{letter}\n\\usepackage{../papers/cv/mycv}\n\\usepackage{eurosym}\n\\usepackage[stable]{footmisc}\n\\addtolength{\\textheight}{10mm}\n\\usepackage{pifont}\n\\newcommand\\selectedsymbol{\\ding{77}}\n\\newcommand\\selected{\\hspace{0pt}\\setlength{\\marginparsep}{-5.9cm}\\reversemarginpar\\marginpar{\\selectedsymbol}}\n\\frenchspacing\n\\begin{document}"
    val footer = "\\end{document}"

    var output = ""

    output += "\n\\chapter{" + name.toTex + "\\hfill {\\normalfont\\small\\isotoday}}"
    output += "\n\\section{Curriculum Vitae}"
    output += "\n" * 3 + CV.headerCVLatex() + "\n" * 3


    output += section("Awards and Honors", printAwards())
    output += section("Research Grants", printGrants())
    output += section("Invited Talks", printInvitedTalks())

    output += section("Teaching and Advising", courses() + seminars() + theses())

    output += section("Professional Service", organizationCommittees() + programCommittees() + reviewing())

    output += publications()

    output += "\\end{document}"

    println(output)
    val fw = new FileWriter("out.tex")
    fw.write(header)
    fw.write(output)
    fw.close()

}
