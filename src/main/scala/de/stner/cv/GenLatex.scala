package de.stner.cv

import java.io.FileWriter


class StringTexHelper(str: String) {
    def toTex = str

    def toAscii = str

    def markdownToTex = str
}


object GenLatex extends App {

    import CV._

    implicit def stringTexWrapper(string: String) = new StringTexHelper(string)

    def section(name: String, body: String): String =
        "\\pagebreak[3]\n\\section{" + name + "}\n" + body + "\n"

    def subsection(name: String, body: String): String =
        "\\subsection{" + name + "}\n" + body + "\n"

    def inCV(body: String): String = "\\begin{CV}\n" + body + "\\end{CV}\n"

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
                thesis.author.abbrvname,
                thesis.when._2,
                thesis.shortText(x => "``" + x + "''"),
                if (thesis.note.isEmpty) "" else " \\emph{-- " + thesis.note.markdownToTex + "}"
            )
        )
        subsection("Advising", inCV(r.mkString))
    }

    val header = """
            \\documentclass[a4paper,10pt]{letter}
            \\usepackage{../mycv}
            \\usepackage{eurosym}
            \\usepackage[stable]{footmisc}
            \\addtolength{\\textheight}{10mm}
            \\usepackage{pifont}
            \\newcommand\\selectedsymbol{\\ding{77}}
            \\newcommand\\selected{\\hspace{0pt}\\setlength{\\marginparsep}{-5.9cm}\\reversemarginpar\\marginpar{\\selectedsymbol}}
            \\begin{document}"""
    val footer = """\\end{document}  """

    var output = ""

    output += "\\chapter{" + name.toTex + "\\hfill {\\normalfont\\small\\isotoday}}"
    output += "\\section{Curriculum Vitae}"
    output += """\\begin{CV}
    \\item[Affiliation]
    	Researcher (Post-Doc) \\\\
    	Philipps University Marburg \\\\
    	Hans-Meerwein-Str, 35032 Marburg, Germany
    \\item[Contact]
    	0049 6421 28 25349 (Office)\\\\
    	0049 6421 28 25419  (Fax)\\\\
    \\href{mailto:christian.kaestner@uni-marburg.de}{christian.kaestner@uni-marburg.de}
    \\item[]Born 1982 in Schwedt/Oder, Germany; German citizenship
    \\end{CV}

    \\section{Profile}
    \\begin{CV}
    \\item[] Post-doctoral researcher at the Philipps University Marburg interested in controlling the complexity caused by variability in software systems. Developing mechanisms, languages, and tools to implement variability in a disciplined way, to detect errors, and to improve program comprehension in systems with a high amount of variability. %Currently, I investigate approaches to parse and type check all compile-time configurations of the Linux kernel in the TypeChef project.
    \\end{CV}"""


    output += section("Teaching and Advising", courses() + seminars() + theses())



    println(output)
    val fw = new FileWriter("out.tex")
    fw.write(header)
    fw.write(output)
    fw.close()

}
