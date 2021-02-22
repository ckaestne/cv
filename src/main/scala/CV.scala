package de.stner.cv


import java.time.LocalDate
import scala.xml.NodeSeq


object CV {

    import de.stner.cv.CVPublications._
    import de.stner.cv.VenueStructure._


    val name = "Christian Kästner"
    val url = "http://www.cs.cmu.edu/~ckaestne/"

    def printSummary(): NodeSeq =
        <p>I am an associate professor in the School of Computer Science at Carnegie Mellon University, interested in <strong>limits of modularity</strong>
            and <strong>complexity</strong> caused by <strong>variability</strong> in software systems. I develop mechanisms,
            languages, and tools to <strong>implement variability in a disciplined way</strong> despite
            imperfect modularity,
            to understand <strong>feature interactions</strong> and <strong>interoperability</strong> issues,
            to <strong>detect errors</strong>, to help with <strong>nonmodular changes</strong>, and to <strong>improve program comprehension</strong>
            in software systems, typically systems with a high amount of variability. Among others, I have developed approaches to parse and
            type check all compile-time configurations of the <strong>Linux kernel</strong> in the
            <strong>TypeChef</strong> project. I am also interested in <strong>open-source sustainability</strong>
            and <strong>software engineering for AI-enabled systems</strong>.</p>
            <p>I currently serve as the director of the <a href="https://se-phd.isri.cmu.edu">CMU Software Engineering Ph.D. Program</a>.</p> :+
            <p>Profiles:
                <a href="cv.pdf">Curriculum vitae</a>,
                <a href="http://scholar.google.com/citations?user=PR-ZnJUAAAAJ">Google Scholar</a>,
                <a href="http://dl.acm.org/author_page.cfm?id=81331495728">ACM</a>,
                <a href="http://www.informatik.uni-trier.de/~ley/db/indices/a-tree/k/K=auml=stner:Christian.html">dblp</a><span style="display:none">,
                <a href="https://plus.google.com/113955799521066229715" rel="publisher">Google+</a>
            </span>.</p> :+
            <p>
                <div><a href="http://www.isri.cmu.edu/">Institute for Software Research</a></div>
                <div><a href="http://www.cs.cmu.edu/">School of Computer Science</a></div>
                <div><a href="http://www.cmu.edu/">Carnegie Mellon University</a></div>
                <div>&nbsp;</div>
                <div>Office: TCS <a href="https://vignette.wikia.nocookie.net/juggle/images/e/e2/534_200.gif">345</a></div>
                <div>Email: kaestner (at) cs.cmu.edu</div>
                <div itemprop="address" itemscope=" " itemtype="http://data-vocabulary.org/Address">
                    Mailing Address: C. Kaestner, ISR - TCS Hall 430,
                    <span itemprop="street-address">4665 Forbes Avenue</span>,
                    <span itemprop="locality">Pittsburgh</span>,
                    <span itemprop="region">PA</span> <span itemprop="postal-code">15213</span>,
                    <span itemprop="country">USA</span>
                </div>
            </p>


    def headerCVLatex() =
        """\begin{CV}
          |\item[Affiliation]
          |    Associate Professor \\
          |    School of Computer Science, Institute for Software Research\\
          |    Carnegie Mellon University
          |\item[Contact]
          |    +1 412 268 5254 (Office)\\
          |    \href{mailto:kaestner@cs.cmu.edu}{kaestner@cs.cmu.edu}
          |\item[Mailing Address]
          |    Christian Kaestner\\
          |     ISR - TCS Hall 430\\
          |     4665 Forbes Avenue\\
          |     Pittsburgh, PA 15213, USA
          |\end{CV}
          |
          |\section{Profile}
          |\begin{CV}
          |\item[]
          |     Associate professor in the Institute of Software Research at the Carnegie Mellon University interested in \emph{limits of modularity} and \emph{variability} in software systems,
          |     as well as open-source sustainability and software engineering for AI-enabled systems. Developing mechanisms, languages, and tools to implement variability in a disciplined way,
          |     to detect errors, to help with nonmodular changes, and to improve program comprehension in software systems, often focused on systems with a high amount of variability.
          |     Also interested in \emph{open-source sustainability} and \emph{software engineering for AI-enabled systems}.
          |\end{CV}
          |
          |\section{Education}
          |\begin{CV}
          |\item[Apr.\ 2007 -- May 2010]
          |    Doctoral degree in computer science (Doktor-Ingenieur),\\
          |    University of Magdeburg, Germany, \\
          |    Summa cum laude (with distinction)\\[.5ex]
          |	   Committee: Prof.\ Gunter Saake (University of Magdeburg), Prof.\ Don Batory (University of Texas at Austin), Prof.\ Krzysztof Czarnecki (University of Waterloo)
          |\item[Oct.\ 2002 -- Mar.\ 2007]
          |    Diploma degree in business information systems \\(M.Sc.\ equivalent; Diplom-Wirtschaftsinformatiker), \\University of Magdeburg, Germany,\\Grade ``1.0'' (with distinction)
          |\end{CV}
          |
          |\section{Academic Employment}
          |\begin{CV}
          |\item[since Sep. 2019]
          | Director of the Software Engineering Ph.D. Program,\\
          | Carnegie Mellon University
          |\item[since Jul. 2018]
          |	Associate Professor,\\
          |	Carnegie Mellon University
          |\item[Sep. 2012 -- Jun. 2018]
          |	Assistant Professor,\\
          |	Carnegie Mellon University
          |\item[Jul.\ 2010 -- Aug. 2012]
          |	Researcher (Post-Doc),\\
          |	Host: Prof.\ Klaus Ostermann, \\
          |	Philipps University Marburg, Germany
          |\item[Apr.\ 2007 -- Jun. 2010]
          |	Research Assistant,\\
          |	Host: Prof.\ Gunter Saake,\\
          |	University of Magdeburg, Germany
          |\item[Oct.\ 2006 -- Mar.\ 2007]
          |    Visiting scholar, \\
          |    Host: Prof. Don Batory \\
          |    University of Texas at Austin, USA
          |\item[Oct.\ 2005 -- Sep.\ 2006]
          |    Student Research Assistent,\\
          |    Host: Prof.\ Gunter Saake,\\
          |		University of Magdeburg, Germany
          |\end{CV}
          | """.stripMargin


    val teaching = Seq(
        Course("Paper Reading Group",
            "",
            URL("https://github.com/cmuvariability/PaperReadingGroup/wiki"),
            English, Continuous("PRG"), Seminar
        ),
        Course("17-445/645 Machine Learning in Production / 11-695 AI Engineering",
            "",
            URL("https://ckaestne.github.io/seai/"),
            English, SpringTerm(2021), new CourseKind with Lecture
        ),
        Course("17-445/645 Software Engineering for AI-Enabled Systems",
            "",
            URL("https://ckaestne.github.io/seai/"),
            English, FallTerm(2020), new CourseKind with Lecture
        ),
        Course("17-445/645 Software Engineering for AI-Enabled Systems",
            "",
            URL("https://ckaestne.github.io/seai/"),
            English, SummerTerm(2020), new CourseKind with Lecture
        ),
        Course("17-654 Analysis of Software Artifacts",
            "",
            URL("http://mse.isri.cmu.edu/software-engineering/Courses/17-654-analysis-of-software-artifacts.html"),
            English, SpringTerm(2020), new CourseKind with Lecture
        ),
        Course("17-445/645 Software Engineering for AI-Enabled Systems",
            "",
            URL("https://ckaestne.github.io/seai/"),
            English, FallTerm(2019), new CourseKind with Lecture
        ),
        Course("17-654 Analysis of Software Artifacts",
            "",
            URL("http://mse.isri.cmu.edu/software-engineering/Courses/17-654-analysis-of-software-artifacts.html"),
            English, SpringTerm(2019), new CourseKind with Lecture
        ),
        Course("17-313 Foundations of Software Engineering",
            "",
            URL("http://www.cs.cmu.edu/~ckaestne/15313/"),
            English, FallTerm(2018), new CourseKind with Lecture
        ),
        Course("17-654 Analysis of Software Artifacts",
            "",
            URL("http://mse.isri.cmu.edu/software-engineering/Courses/17-654-analysis-of-software-artifacts.html"),
            English, SpringTerm(2018), new CourseKind with Lecture
        ),
        Course("15-313 Foundations of Software Engineering",
            "",
            URL("http://www.cs.cmu.edu/~ckaestne/15313/"),
            English, FallTerm(2017), new CourseKind with Lecture
        ),
        Course("15-214 Principles of Software Construction: Objects, Design, and Concurrency",
            "",
            URL("https://www.cs.cmu.edu/~ckaestne/15214/s2017/"),
            English, SpringTerm(2017), new CourseKind with Lecture
        ),
        Course("15-313 Foundations of Software Engineering",
            "",
            URL("http://www.cs.cmu.edu/~ckaestne/15313/"),
            English, FallTerm(2016), new CourseKind with Lecture
        ),
        Course("17-708 Software Product Lines: Concepts and Implementation",
            "",
            URL("http://www.cs.cmu.edu/~ckaestne/17708/"),
            English, FallTerm(2015), new CourseKind with Lecture
        ),
        Course("15-313 Foundations of Software Engineering",
            "",
            URL("http://www.cs.cmu.edu/~ckaestne/15313/"),
            English, FallTerm(2015), new CourseKind with Lecture
        ),
        Course("15-214 Principles of Software Construction: Objects, Design, and Concurrency",
            "",
            URL("http://www.cs.cmu.edu/~charlie/courses/15-214/"),
            English, SpringTerm(2015), new CourseKind with Lecture
        ),
        Course("15-313 Foundations of Software Engineering",
            "",
            URL("http://www.cs.cmu.edu/~ckaestne/15313/2014/"),
            English, FallTerm(2014), new CourseKind with Lecture
        ),
        Course("15-214 Principles of Software Construction: Objects, Design, and Concurrency",
            "",
            URL("http://www.cs.cmu.edu/~charlie/courses/15-214/"),
            English, SpringTerm(2014), new CourseKind with Lecture
        ),
        Course("15-313 Foundations of Software Engineering",
            "",
            URL("http://www.cs.cmu.edu/~ckaestne/15313/2013/"),
            English, FallTerm(2013), new CourseKind with Lecture
        ),
        Course("15-214 Principles of Software Construction: Objects, Design, and Concurrency",
            "",
            URL("http://www.cs.cmu.edu/~charlie/courses/15-214/"),
            English, SpringTerm(2013), new CourseKind with Lecture
        ),
        Course("Empirical Methods for Computer Scientists",
            "Einführung in empirische Methoden für Informatiker",
            URL("http://www.uni-marburg.de/fb12/ps/teaching/ss12/em"),
            German, SummerTerm(2012), new CourseKind with Lecture with Exercise
        ),
        Course("Software Product Lines: Concepts and Implementation",
            "Softwareproduktlinien: Konzepte und Implementierung",
            URL("http://www.uni-marburg.de/fb12/ps/teaching/ss11/spl"),
            German, SummerTerm(2011), new CourseKind with Lecture with Exercise
        ),
        Course("Software Engineering",
            "Einführung in die Softwaretechnik",
            URL("http://www.uni-marburg.de/fb12/ps/teaching/ws10/eise"),
            German, WinterTerm(2010), new CourseKind with Lecture,
            "shared with K. Ostermann"
        ),
        Course("Software Product Lines",
            "Software-Produktlinien",
            URL("http://www.uni-marburg.de/fb12/ps/teaching/ws10/splseminar"),
            German, WinterTerm(2010), Seminar
        ),
        Course("Concepts of Database Implementation",
            "Datenbankenimplementierungstechniken (DB2)",
            URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/db2/2010"),
            German, SummerTerm(2010), new CourseKind with Exercise
        ),
        Course("Student Conference on Software Engineering and Database Systems", "",
            URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/studconf"),
            English, SummerTerm(2010), new CourseKind with Lecture
        ),
        Course("Product-Line Implementation for Tailor-Made Data Management",
            "Erweiterte Programmierkonzepte für maßgeschneiderte Datenhaltung (EPMD)",
            URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/epmd/2009/"),
            German, WinterTerm(2009), new CourseKind with Lecture with Exercise
        ),
        Course("Specification Technologies",
            "Spezifikationstechnik",
            URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/spt/2009/"),
            German, SummerTerm(2009), new CourseKind with Exercise
        ),
        Course("Concepts of Database Implementation",
            "Datenbankenimplementierungstechniken (DB2)",
            URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/db2/2009"),
            German, SummerTerm(2009), new CourseKind with Exercise
        ),
        Course("Student Conference on Software Engineering and Database Systems", "",
            URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/studconf"),
            English, SummerTerm(2009), new CourseKind with Lecture
        ),
        Course("Product-Line Implementation for Tailor-Made Data Management",
            "Erweiterte Programmierkonzepte für maßgeschneiderte Datenhaltung (EPMD)",
            URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/epmd/2008/"),
            German, WinterTerm(2008), new CourseKind with Lecture with Exercise,
            "Shared with G. Saake"
        ),
        Course("Concepts of Database Implementation",
            "Datenbankenimplementierungstechniken (DB2)",
            URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/db2/2008"),
            German, SummerTerm(2008), new CourseKind with Exercise
        ),
        Course("Student Conference on Software Engineering and Database Systems", "",
            URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/studconf"),
            English, SummerTerm(2008), new CourseKind with Lecture
        ),
        Course("Product-Line Implementation for Tailor-Made Data Management",
            "Erweiterte Programmierkonzepte für maßgeschneiderte Datenhaltung (EPMD)",
            URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/epmd/2007/"),
            German, WinterTerm(2007), new CourseKind with Exercise
        ),
        Course("Concepts of Database Implementation",
            "Datenbankenimplementierungstechniken (DB2)",
            URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/db2/2007"),
            German, SummerTerm(2007), new CourseKind with Exercise
        ),
        Course("Advanced Database Models",
            "Advanced Database Models (ADBM)",
            URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/adbm/"),
            English, SummerTerm(2007), new CourseKind with Exercise
        ),
        Course("Cost Accounting",
            "Kostentheorie und Kostenrechnung",
            null,
            German, WinterTerm(2005), Tutorium),
        Course("Algorithms and Data Structures",
            "Einführung in Algorithmen und Datenstrukturen",
            null,
            German, SummerTerm(2004), Tutorium),
        Course("Cost Accounting",
            "Kostentheorie und Kostenrechnung",
            null,
            German, WinterTerm(2004), Tutorium)
    )

    val teachingProjects = Seq(
        TeachingProject("Patientenbefragung", SummerTerm(2011), "Fortgeschrittenenpraktikum"),
        TeachingProject("Demoproduktlinie Computerspiel", WinterTerm(2010), "Fortgeschrittenenpraktikum"),
        TeachingProject("Demoproduktlinie Tetris", WinterTerm(2010), "Fortgeschrittenenpraktikum"),
        TeachingProject("Webuni", SummerTerm(2009), "IT-Projekt"),
        TeachingProject("FOP-Demo Application (Tank Game)", SummerTerm(2009), "Laborpraktikum"),
        TeachingProject("Bibliographieverwaltung", WinterTerm(2008), "Softwarepraktikum"),
        TeachingProject("Stundenplanprogramm fuer Univis", WinterTerm(2008), "Laborpraktikum", URL("http://www.uni-magdeburg.de/mytt/")),
        TeachingProject("Entwicklung einer IDE fuer Software-Produktlinien", WinterTerm(2008), "Laborpraktikum", URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/laborws0708/")),
        TeachingProject("Verknuepfung zwischen VR und semantischen Informationen", SummerTerm(2008), "IT-Projekt"),
        TeachingProject("Stundenplanprogramm fuer Univis", WinterTerm(2007), "IT-Projekt", URL("http://www.uni-magdeburg.de/mytt/"))
    )

    val memberships = Seq(
        "IFIP Working Group 2.11 (Program Generation)",
        "Association for Computing Machinery (ACM)",
        //"Gesellschaft für Informatik (GI)",
        "Deutscher Hochschulverband"
    )

    val advisedTheses = CVTheses.advisedTheses


    import Venues._
    val committees = Seq[Committee](
        Committee(ICSE(2022).url(URL("https://conf.researchr.org/home/icse-2022")), ConfChair, PC),
        Committee(ASE(2021).url(URL("https://conf.researchr.org/home/ase-2021")), PC),
        Committee(ESECFSE(2021).url(URL("https://2021.esec-fse.org/")), PC),
        Committee(VAMOS(2021), PC),
        Committee(ASE(2020).url(URL("https://conf.researchr.org/home/ase-2020")), PC),
        Committee(SPLC(2020).url(URL("http://www.splc.net")), PC),
        Committee(VAMOS(2020).url(URL("https://vamos2020.dbse.iti.cs.ovgu.de")), PC),
        Committee(ICSE(2020).url(URL("https://conf.researchr.org/home/icse-2020")), PC, OtherChair("Student Mentoring Program Co-Chair", "SMP Chair")),
        Committee(ESECFSE(2019).url(URL("https://esec-fse19.ut.ee/")), PC, OtherChair("Journal First Co-Chair", "JF Chair")),
        Committee(ASE(2019).url(URL("https://2019.ase-conferences.org/")), PC),
        Committee(SPLC(2019).url(URL("http://www.splc.net")), PC),
        Committee(VAMOS(2019).url(URL("https://vamos2019.github.io/")), PC),
        Committee(OOPSLA(2019), ERC),
        Committee(ICSE(2019), OtherChair("Student Mentoring Program Co-Chair", "SMP Chair")),
        Committee(Conference("ICSE-NIER", 2019,
            "ICSE New Ideas and Emerging Results (NIER)",
            URL("https://2019.icse-conferences.org/home")), PC),
        Committee(ASE(2018).url(URL("http://ase2018.com/")), PCCChair),
        Committee(ICSE(2018).url(URL("http://www.icse2018.org/")), PC),
        Committee(Workshop("SPLTea", 2018, "Third International Workshop on Software Product Line Teaching",
            URL("http://spltea.irisa.fr/")), PC),
        Committee(Conference("SE", 2018,
            "Software Engineering Conference of the Gesellschaft für Informatik (GI)",
            URL("https://se18.uni-ulm.de/")), PC),
        Committee(VAMOS(2018).url(URL("https://vamos2018.wordpress.com/")), PC),
        Committee(Workshop("WAPI", 2017,
          "ICSE Workshop on API Usage and Evolution",
          URL("https://w-api.github.io/")), PC),
        Committee(ECOOP(2017).url(URL("http://conf.researchr.org/home/pldi-ecoop-2017")), PC),
        Committee(ASE(2017).url(URL("http://ase2017.org/")), PC, DSCChair),
        Committee(ICSE(2017).url(URL("http://icse2017.gatech.edu/")), PC),
        Committee(ESECFSE(2017).url(URL("http://esec-fse17.uni-paderborn.de/")), PC),
        Committee(VAMOS(2017).url(URL("https://vamos2017.wordpress.com/")), PC),
        Committee(Workshop("RELENG", 2016, "4th International Workshop on Release Engineering",
            URL("http://releng.polymtl.ca")), PC),
        Committee(ASE(2016).url(URL("http://ase2016.org/home.html")), ERC/*, PubC*/),
        Committee(ECOOP(2016).url(URL("http://2016.ecoop.org/")), ERC),
        Committee(SPLC(2016).url(URL("http://www.splc2016.net")), PC),
        Committee(VAMOS(2016).url(URL("https://vamos2016.wordpress.com/")), PC),
        Committee(Conference("MV", 2016,
            "MODULARITY 2016 - Modularity Visions Track",
            URL("http://2016.modularity.info/track/modularity-2016-mvpapers")), PC),
        Committee(ASE(2015).url(URL("http://ase2015.unl.edu/")), DS),
        Committee(ASE(2015).url(URL("http://ase2015.unl.edu/")), PC),
        Committee(Conference("SBCARS", 2015,
            "Brazilian Symposium on Software Components, Architectures and Reuse",
            URL("http://cbsoft.org/sbcars2015/")), PC),
        Committee(VAMOS(2015), PC),
        Committee(SPLC(2015), PC),
        Committee(GPCE(2015), GeneralChair),
        Committee(Workshop("SPLTea", 2015, "Second International Workshop on Software Product Line Teaching",
            URL("http://spltea.irisa.fr/")), PC),
        Committee(Workshop("ICSE-D", 2015,
            "International Conference on Software Engineering -- Demonstrations Committee",
            URL("http://2015.icse-conferences.org/call-dates/call-for-contributions/demonstrations")), PC),
        Committee(Workshop("MultiPLE", 2014,
            "SPLC Workshop on Multi Product Line Engineering",
            URL("https://sites.google.com/site/wmultiple2014/")), PC),
        Committee(Workshop("SPLat", 2014, "Software Product Line Analysis Tools 2014",
            URL("https://www.win.tue.nl/~evink/research/Postscript/splatworkshop2014.pdf")), PC),
        Committee(Workshop("SPLTea", 2014, "First International Workshop on Software Product Line Teaching",
            URL("http://spltea.irisa.fr/")), PC),
        Committee(Workshop("REVE", 2014, "2nd Workshop on Reverse Variability Engineering"), PC),
        Committee(FOSD(2014), OC),
        Committee(SPLC(2014).url(URL("http://www.splc2014.net")), PC),
        Committee(ASE(2014).url(URL("http://ase2014.org/")), PC),
        Committee(ECOOP(2014), ERC),
        Committee(Workshop("ICSE-TB", 2014,
            "ICSE 2014 - Tutorial and Technical Briefings Track",
            URL("http://2014.icse-conferences.org/")), PC),
        Committee(Conference("MV", 2014,
            "MODULARITY 2014 - Modularity Visions Track",
            URL("http://aosd.net/2014/mvtrack/")), PC),
        Committee(VAMOS(2014), PC),
        Committee(GPCE(2014), SC),
        Committee(OOPSLA(2013).url(URL("http://splashcon.org/2013")), PC),
        Committee(GPCE(2013), PCChair),
        Committee(Workshop("SCORE", 2013,
            "Student Contest on Software Engineering at ICSE",
            URL("http://score-contest.org/2013/")), PC),
        Committee(Conference("SE", 2013,
            "SE 13 -- GI Konferenz Software Engineering",
            URL("http://www.se2013.rwth-aachen.de/")), PC),
        Committee(Workshop("MPLE", 2013,
            "SPLC Workshop on Multi Product Line Engineering",
            URL("https://sites.google.com/site/wmultiple2013/")), PC),
        Committee(FOSD(2013), OC),
        Committee(VAMOS(2013), PC),
        Committee(Workshop("REVE", 2013, "1st Workshop on Reverse Variability Engineering"), PC),
        Committee(Workshop("SLE-DS", 2012,
            "International Conference on Software Language Engineering - Doctoral Symposium",
            URL("http://planet-sl.org/sle2012")), PC),
        Committee(GPCE(2012), PC),
        Committee(FOSD(2012), OC),
        Committee(Workshop("SPLC-TD", 2012,
            "16th International Software Product Line Conference - Tools and Demonstrations Track",
            URL("http://www.splc2012.net")), PC),
        Committee(Workshop("NFPinDSML", 2012,
            "4th  Workshop on Non-functional System Properties and Domain Specific Modeling Languages",
            URL("https://nfpindsml.semtech.athabascau.ca/", true)), PC),
        Committee(RAMSE(2012).url(
            URL("http://www-users.cs.york.ac.uk/~manuel/Events/RAM-SE12/RAM-SE12/Description.html")), PC),
        Committee(Workshop("ESCOT", 2012,
            "3rd International Workshop on Empirical Evaluation of Software Composition Techniques"), OC),
        Committee(Workshop("MISS", 2012,
            "2nd Workshop on Modularity in Systems Software",
            URL("http://www.aosd.net/workshops/miss/")), PC),
        Committee(Workshop("PEPM", 2012,
            "21st ACM SIGPLAN Workshop on Partial Evaluation and Program Manipulation",
            URL("http://www.program-transformation.org/PEPM12")), PC),
        Committee(VAMOS(2012), PC),
        Committee(Conference("SC", 2011,
            "10th International Conference on Software Composition",
            URL("http://www.infosun.fim.uni-passau.de/spl/SC2011/")), PC),
        Committee(GPCE(2011), PC),
        Committee(FOSD(2011), OC),
        Committee(Workshop("FREECO", 2011,
            "ECOOP Workshop on Free Composition",
            URL("http://trese.ewi.utwente.nl/workshops/FREECO/FREECO11/home.html")), PC),
        Committee(FOSD(2010), OC),
        Committee(Workshop("ASE-TD", 2010,
            "25th International Conference on Automated Software Engineering -- Tool Demonstration Committee",
            URL("http://soft.vub.ac.be/ase2010/")), PC),
        Committee(Workshop("PLEERPS", 2010,
            "Workshop on Product-Line Engineering for Enterprise Resource Planning Systems",
            URL("http://researchr.org/conference/pleerps-2010")), PC),
        Committee(FOSD(2009), OC)
    )

    val committees_conferences = committees.filterNot(_.venue.kind == KWorkshopDemoTool)
    val committees_workshops = committees.filter(_.venue.kind == KWorkshopDemoTool)

    val editor: Seq[Editorship] = Seq(
        Editorship("Associate Editor", TOSEM(2020), 2019, None)
    )

    val reviews: Seq[Review] = Seq(
        Review(TSE(2020)),
        Review(JASE(2020)),
        Review(EMSE(2020)),
        Review(TOSEM(2019)),
        Review(TSE(2019)),
        Review(TOSEM(2018)),
        Review(IST(2018)),
        Review(JASE(2018)),
        Review(TSE(2018)),
        Review(EMSE(2017)),
        Review(TSE(2017)),
        Review(TSE(2016)),
        Review(SOSYM(2016)),
        Review(COMLAN(2016)),
        Review(TSE(2015)),
        Review(SPE(2015)),
        Review(SCP(2014)), //Science of Computer Programming
        Review(JOSER(2014)), //Science of Computer Programming
        Review(TSE(2014)), //IEEE Transactions on Software Engineering
        Review(TOPLAS(2012)),
        Review(EMSE(2012)),
        Review(JSEP(2012)), //Journal of Software: Evolution and Process
        Review(HOSC(2012)), //Higher-Order and Symbolic Computation
        Review(AI(2012)), //Acta Informatica
        Review(SPE(2012)), //Software: Practice and Experience
        Review(SCP(2012)), //Science of Computer Programming
        Review(TOSEM(2011)), //ACM Transactions on Software Engineering and Methodology
        Review(JSS(2012)), //Journal of Systems and Software
        Review(IST(2011)), //Information and Software Technology
        Review(STTT(2011)), //Software Tools for Technology Transfer
        Review(SCP(2011)), //Science of Computer Programming
        Review(ECOOP(2011), "Sven Apel"), //European Conference on Object-Oriented Programming  (invited by Sven Apel)
        Review(AOSD(2011), "Sven Apel"), //International Conference on Aspect-Oriented Software Development \\(invited by Sven Apel)
        Review(IST(2010)), //Information and Software Technology
        Review(TSE(2010)), //IEEE Transactions on Software Engineering
        Review(SLE(2010), "Gabriele Taentzer"), //International Conference on Software Language Engineering \\(invited by Gabriele Taentzer)
        Review(SCP(2010)), //Science of Computer Programming
        Review(ASE(2010), "Sven Apel"), //International Conference on Automated Software Engineering \\(invited by Sven Apel)
        Review(IS(2009)), //IEEE Software
        Review(GPCE(2009), "Sven Apel"), //International Conference on Generative Programming and Component Engineering  (invited by Sven Apel)
        Review(ASE(2009), "Sven Apel"), //International Conference on Automated Software Engineering \\ (invited by Sven Apel)
        Review(DKE(2008), "Gunter Saake"), //Data \& Knowledge Engineering (Elsevier Journal; invited by Gunter Saake)
        Review(TII(2008), "Gunter Saake"), //IEEE Transactions on Industrial Informatics (invited by Gunter Saake)
        Review(GPCE(2008), "Christian Lengauer")
    )


    val awards: Seq[AwardOrGrant] = Seq(
        Award(
            "Distinguished Program Committee Member Award at the International Conference on Automated Software Engineering (ASE) 2020",
            URL("https://conf.researchr.org/home/ase-2020"),
            LocalDate.of(2020, 11, 1),
            Nil
        ),
        Award(
            "Most Influential Paper Award at the International Conference on Software Product Lines (SPLC) 2019",
            URL("https://splc2019.net"),
            LocalDate.of(2019, 9, 12),
            Nil
        ),
        Award(
            "Distinguished Reviewer Award at the International Conference on Automated Software Engineering (ASE) 2017",
            URL("http://ase2017.org"),
            LocalDate.of(2017, 11, 1),
            Nil
        ),
        Award(
            "Distinguished Reviewer Award at the International Conference on Software Engineering (ICSE) 2017",
            URL("http://icse2017.gatech.edu/"),
            LocalDate.of(2017, 5, 20),
            Nil
        ),
        Award(
            "Distinguished Reviewer Award at the International Conference on Automated Software Engineering (ASE) 2015",
            URL("http://ase2015.unl.edu/"),
            LocalDate.of(2015, 11, 12),
            Nil
        ),
//        Grant("Grant: Reverse Engineering Variability Implementations",
//            URL("http://www.nsf.gov/awardsearch/showAward?AWD_ID=1318808"),
//            LocalDate.of(2013, 6, 24),
//            LocalDate.of(2013, 9, 1),
//            LocalDate.of(2016, 8, 31),
//            "National Science Foundation",
//            USD(400797)
//        ),
//        Grant("Grant: Pythia -- Techniques and Prediction Models for Sustainable Product-Line Engineering",
//            URL("http://www.infosun.fim.uni-passau.de/spl/pythia/"),
//            LocalDate.of(2012, 6, 1),
//            LocalDate.of(2012, 10, 1),
//            LocalDate.of(2015, 10, 1),
//            "German Research Foundation",
//            EUR(250000)
//        ),
        Award(
            "**GI-Dissertationspreis:** Best Dissertation Award of the German Computer Science Association, 2010",
            URL("http://www.gi.de/wir-ueber-uns/wettbewerbe/gi-dissertationspreis.html"),
            LocalDate.of(2011, 10, 1),
            List(
                (URL("http://www.gi.de/no_cache/aktuelles/meldungsdetails/meldung/beste-informatikdissertation-im-deutschsprachigen-raum-gi-dissertationspreis-fr-christian-kstner-387.html"), "Annoucement"),
                (URL("http://www.flickr.com/photos/p0nk/6214434320/"), "Photo")
            )
//            Some(EUR(5000))
        ),
        Award(
            "Distinguished-Paper Award at the International Conference on Object-Oriented Programming, Systems, Languages, and Applications (OOPSLA) 2011",
            URL("http://splashcon.org/2011/program/273"),
            LocalDate.of(2011, 10, 1),
            (URL("http://www.flickr.com/photos/p0nk/6317966864/in/photostream"), "Photo") :: Nil
        ),
        Award(
            "Best-Research-Paper Award at the International Software Product Line Conference 2011",
            URL("http://www.splc2011.net/program-1/best-paper-awards/index.html"),
            LocalDate.of(2011, 8, 1)
        ),
        Award(
            "Best-Dissertation Award of the School of Computer Science, University of Magdeburg, 2010",
            URL("http://www.cs.uni-magdeburg.de/Bester_Doktorand.html"),
            LocalDate.of(2010, 11, 1),
            Nil
        ),
        Award(
            "Research Award of the School of Computer Science, University of Magdeburg for the Best Paper, 2009",
            URL("http://www.cs.uni-magdeburg.de/Die+FIN/Auszeichnungen/Forschungspreis+der+Fakultät-p-324.html"),
            LocalDate.of(2009, 12, 1),
            Nil
        ),
        Award(
            "Software Engineering Award of the Denert Foundation for the Best Master's Thesis, 2007",
            URL("http://www.denert-stiftung.de/"),
            LocalDate.of(2007, 9, 1),
            (URL("http://www.flickr.com/photos/p0nk/6318925958/in/photostream"), "Photo") :: Nil
//            Some(EUR(2000))
        ),
        Award(
            "Best-Graduate Award of the School of Computer Science, University of Magdeburg, 2007",
            URL("http://www.cs.uni-magdeburg.de/Bester_Absolvent.html"),
            LocalDate.of(2007, 10, 1),
            (URL("http://www.flickr.com/photos/p0nk/6318413771/in/photostream"), "Photo") :: Nil
        ),
        Award("Student Scholarship of the Germany Academic Exchange Service",
            URL("http://www.daad.de/"),
            LocalDate.of(2006, 9, 1)
        )
    )
//    val projects: Seq[(URL, String, String, Option[String])] = Seq(
//        (URL("http://ckaestne.github.com/TypeChef/"), "TypeChef", "Variability-Aware Analysis and Parsing of C Code", None),
//        (URL("https://github.com/ckaestne/LEADT"), "Feature Mining", "Consistent Semi-Automatic Detection of Product-Line Features", None),
//        (URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/research/cide/"), "CIDE", "Virtual Separation of Concerns", None),
//        (URL("http://www.sugarj.org/"), "SugarJ", "Library-based Syntactic Language Extensibility", None),
//        (URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/research/featureide/"), "FeatureIDE", "Tool Support for Feature-Oriented Software Development", None),
//        (URL("http://www.infosun.fim.uni-passau.de/cl/apel/fh/"), "FeatureHouse", "Language-Independent, Automatic Software Composition", None),
////        (URL("http://www.fame-dbms.org/"), "FAME-DBMS", "Tailor-Made Data Management", Some("finished")),
//        (URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/research/arj/"), "ARJ", "Extending AspectJ with Aspect Refinement and Mixin-Based Aspect Inheritance", Some("finished"))
//        //        (URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/research/ajdtstats/"), "AJDTStats: A Statistics Collector for AJDT", Some("finished")),
//        //        (URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/research/berkeley/"), "Aspect-oriented refactoring of Berkeley DB", Some("finished"))
//    )

    //name, title, url
//    val software: Seq[(String, String, URL)] = Seq(
//        ("TypeChef",
//            "Parsing and Analyzing #ifdef Variability in C Code",
//            URL("https://github.com/ckaestne/TypeChef")),
//        ("CIDE",
//            "Feature-Oriented Analysis and Decomposition of Legacy Code",
//            URL("http://fosd.net/cide/")),
//        ("FeatureIDE",
//            "A Tool Framework for Feature-Oriented Software Development",
//            URL("http://fosd.net/featureide/")),
//        ("LEADT",
//            "Consistent Semi-Automatic Detection of Product-Line Features",
//            URL("http://fosd.net/leadt/")),
//        ("FeatureHouse",
//            "Language-Independent, Automated Software Composition",
//            URL("http://fosd.net/fh/")),
//        ("ARJ",
//            "Extending AspectJ with Aspect Refinement and Mixin-Based Aspect Inheritance",
//            URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/forschung/arj/"))
//    )

    lazy val publications: List[Publication] = {
        //get members via reflection
        import reflect.runtime.universe._
        val im = reflect.runtime.currentMirror reflect CVPublications
        typeOf[CVPublications.type].members.
            filter(m=>m.isMethod && m.asMethod.isAccessor).
            map(m=>im.reflectMethod(m.asMethod).apply().asInstanceOf[Publication]).toList.reverse
    }
    lazy val media: List[Media] = {
        //get members via reflection
        import reflect.runtime.universe._
        val im = reflect.runtime.currentMirror reflect CVMedia
        typeOf[CVMedia.type].members.
            filter(m=>m.isMethod && m.asMethod.isAccessor).
            map(m=>im.reflectMethod(m.asMethod).apply().asInstanceOf[Media]).toList.sorted
    }


    val parsingandtypecheckingLinux = "Parsing and Type Checking all 2^10000 Configurations of the Linux Kernel"
    val vsoc = "Virtual Separation of Concerns: Toward Preprocessors 2.0"
    val invitedTalks: Seq[InvitedTalk] = Seq(
        InvitedTalk(time(2020, 9), "Analyzing Tens of Terabytes of Public Trace Data & Open Source Sustainabilty", "State of the Source"),
        InvitedTalk(time(2020, 6), "Engineering AI-Enabled Systems with Interdisciplinary Teams", "Software Engineering for Machine Learning Applications (SEMLA) International Symposium"),
        InvitedTalk(time(2020, 4), "Software Engineering for ML-Enabled Systems", "Code and Supply Meetup"),
        InvitedTalk(time(2020, 3), "Teaching Software Engineering for AI-Enabled Systems", "Dagstuhl Seminar 20091: SE4ML - Software Engineering for AI-ML-based Systems"),
        InvitedTalk(time(2019, 9), "Performance Analysis for Highly-Configurable Systems", "Keynote for the 2019 System and Software Product Line Conference (SPLC)"),
        InvitedTalk(time(2019, 9), "Granularity in Software Product Lines: 12 Years Later", "Most Influential Paper Award Talk at the 2019 System and Software Product Line Conference (SPLC)"),
        InvitedTalk(time(2019, 9), "Efficiently Finding Higher-Order Mutants", "Saarland University"),
        InvitedTalk(time(2019, 2), "How to Break an API: How Community Values Influence Practices", "University of South Carolina"),
        InvitedTalk(time(2018, 7), "How to Break an API: How Community Values Influence Practices", "École Polytechnique de Montréal"),
        InvitedTalk(time(2017, 5), "How to Break an API: How Community Values Influence Practices", "Speaker at jsconf.eu"),
        InvitedTalk(time(2016, 5), "Quality Assurance for Highly-Configurable Systems", "Keynote Talk for the VACE Workshop at ICSE'16"),
        InvitedTalk(time(2015, 11), "Starting an Academic Career -- Reflecting on Habits that Worked for Me", "Keynote Talk for the ASE 2015 Doctoral Symposium"),
        InvitedTalk(time(2015, 10), "Parsing Unpreprocessed C Code - The TypeChef Experience", "Keynote Talk for the Parsing@SLE Workshop at SPLASH'15"),
        InvitedTalk(time(2015, 9), "Understanding Feature Interactions: From Bugs to Performance Surprises", "Keynote at Brazilian Symposium on Software Components, Architectures, and Reuse (SBCARS)"),
        InvitedTalk(time(2014, 7), "Feature Interactions in Software Systems An Implementation Perspective", "Dagstuhl Seminar 14281: Feature Interactions: The Next Generation"),
        InvitedTalk(time(2014, 6), "Analyzing Highly-Configurable Software Systems", "University of Toronto -- Workshop on the State of the Art in Automated Software Engineering Research"),
        InvitedTalk(time(2014, 4), "Quality Assurance for Highly-Configurable Systems", "University of Magdeburg, Germany"),
        InvitedTalk(time(2014, 4), "Analysis of Software Product Lines", "Dagstuhl Seminar 14172: Unifying Product and Software Configuration"),
        InvitedTalk(time(2013, 12), "Variability Mining", "University of Waterloo -- Product Line Engineering Workshop"),
        InvitedTalk(time(2013, 12), parsingandtypecheckingLinux, "University of Nebraska at Lincoln, Lincoln, NE"),
        InvitedTalk(time(2013, 8), "Analyzing Highly Configurable Systems: From Linux to Eclipse", "Technical University Darmstadt, Germany"),
        InvitedTalk(time(2013, 8), "Accepting Change - Awareness instead of Stability Guarantees", "University of Passau, Germany"),
        InvitedTalk(time(2013, 2), "Analyzing the #ifdef Hell with TypeChef -- Or the Quest for Realistic Subjects in Product-Line Analysis", "Dagstuhl Seminar 13091: Analysis, Test and Verification in The Presence of Variability"),
        InvitedTalk(time(2012, 12), "A Variability-Aware Module System", "Dagstuhl Seminar 12511: Divide and Conquer: the Quest for Compositional Design and Analysis"),
        InvitedTalk(time(2012, 4), parsingandtypecheckingLinux, "University of Passau, Germany"),
        InvitedTalk(time(2012, 4), parsingandtypecheckingLinux, "Carnegie Mellon University, Pittsburgh, PA"),
        InvitedTalk(time(2012, 4), parsingandtypecheckingLinux, "University of Edinburgh, UK"),
        InvitedTalk(time(2012, 3), parsingandtypecheckingLinux, "Purdue University, West Lafayette, IN"),
        InvitedTalk(time(2011, 12), parsingandtypecheckingLinux, "Technical University Ilmenau, Germany"),
        InvitedTalk(time(2011, 10), "Virtual Separation of Concerns", "Oregon State University, Corvallis, OR"),
        InvitedTalk(time(2011, 10), "Modularity in Feature-Oriented Software Development", "University of Texas at Austin, TX"),
        InvitedTalk(time(2011, 6), vsoc, "Kolloquium zum GI Dissertationspreis 2010 at Dagstuhl 11222"),
        InvitedTalk(time(2011, 1), "Variability-Aware Analysis: Type Checking entire Product Lines", "Dagstuhl Seminar 11021: Feature-Oriented Software Development"),
        InvitedTalk(time(2010, 12), "Modularity -- Current State and Challenges", "University of Waterloo, ON"),
        InvitedTalk(time(2010, 12), "Variability Analysis of C Code in the Presence of Lexical Macros and Conditional Compilation", "IFIP WG 2.11 Meeting, Waterloo, ON"),
        InvitedTalk(time(2010, 7), "From Aspectual Decomposition to Virtual Separation of Concerns", "Colloquium Honorary Doctorate Ernst Denert, University of Kaiserslautern"),
        InvitedTalk(time(2010, 5), vsoc, "Philipps University Marburg, Germany"),
        InvitedTalk(time(2010, 4), vsoc, "University of Namur (FUNDP), Belgium"),
        InvitedTalk(time(2010, 3), vsoc, "IFIP WG 2.11 Meeting, St. Andrews, UK"),
        InvitedTalk(time(2008, 7), "Decomposing Berkeley DB: Granularity and Interactions", "Dagstuhl Seminar 08281: Software Engineering for Tailor-made Data Management")
    )

    //1=January
    def time(year: Int, month: Int): LocalDate = LocalDate.of(year, month, 1)


    /**
     * list of groups
     *
     * each group has a list of persons with potential comments
     */
    def students: List[(String, List[(Person, Option[String])])] = List(
        ("Current", current),
        ("Former", former)
//        ("Close external collaborations / coadvising", collaboratingStudents),
    )

    import Coauthors._

    val current = List(
        //(Ahmad, Some("(coadvised with Jonathan Aldrich)")),
        (Ferreira, Some("(PhD student since 2014)")),
        (Velez, Some("(PhD student since 2016)")),
        (Wong, Some("(PhD student since 2014)")),
        (Person("Chenyang", "Yang", "Peking University"), Some("(REU student, 2020)")),
        (Person("Hannah", "Hartnett", "Colby College"), Some("(REU student, 2020)")),
        (Person("Helen", "Dong", "Carnegie Mellon University"), Some("(REU student, 2020)")),
        (Person("Isabel", "Gan", "Carnegie Mellon University"), Some("(REU student, 2020)")),
        (Person("Sophia", "Cohen", "Wesleyan University"), Some("(REU student, 2020)")),
        (Person("Yuan (Cindy)", "Jiang", "Carnegie Mellon University"), Some("(REU student, 2020)"))
    )
    val former = List(
        (Zhou, Some("(PhD 2014-2020, now Assistant Professor at University of Toronto)")),
        (Meinicke, Some("(visiting scholar, 2014, 2016-2020, now at Google)")),
        (Jamshidi, Some("(postdoc 2016-2018, now Assistant Professor at University of South Carolina)")),
        (Medeiros, Some("(visiting scholar, 2014, now faculty at Federal Institute of Alagoas)")),
        (Stanciulescu, Some("(visiting scholar, 2016-2017, now researcher at ABB)")),

        (Ahmad, Some("(graduated MSc, 2016)")),
        (Person("Kattiana","Constantino", "Federal University of Minas Gerais"), Some("(visiting scholar 2018-2019)")),
        (Figueiredo, Some("(visiting scholar, 2017-2018)")),
        (Person("Raman", "Goyal"), Some("(visiting scholar, 2015, now at Epic)")),
        (Person("Htut Khine", "Htay Win"), Some("(REU student, 2015)")),
        (Kawthekar, Some("(visiting scholar, 2016, now graduate student at Stanford)")),
        (Person("Stefan", "Mühlbauer", "Technical University Braunschweig"), Some("(visiting scholar, 2016, now PhD student at University of Weimar)")),
        (Person("Kyle", "McDonell", "Colby College"), Some("(REU student, 2016)")),
        (Person("Xia", "Xiao", "Dickinson College"), Some("(REU student, 2016)")),
        (Person("Jean", "Melo", URL("http://itu.dk/people/jeam/"), "IT University Copenhagen"), Some("(visiting scholar, 2016-2017, now at Configit)")),
        (Trockman, Some("(REU student, 2017, now PhD student at CMU)")),
        (Person("Lukas", "Lazarek", "UMass Lowell"), Some("(REU student, 2017, now PhD student at Northwestern)")),
        (Person("Changming", "Xu", "Washington University in St. Louis"), Some("(REU student, 2017)")),
        (Person("Hannah", "Reiling", "University of Pittsburgh"), Some("(REU student, 2017)")),
        (Soares, Some("(visiting scholar, 2017-2018)")),

        (Ren, Some("(REU student, 2018)")),
        (Chen, Some("(REU student, 2018, now PhD student at CMU)")),
        (Person("Kalil","Garrett", "Georgia State University"), Some("(REU student, 2018, now PhD student at CMU)")),
        (Person("Nga","Huynh","Bunker Hill Community College"), Some("(REU student, 2018)")),
        (Miller, Some("(REU student, 2018)")),
        (Person("Sophie", "Rosas-Smith","Wellesley College"), Some("(REU student, 2018)")),

        (Person("Annika","Esau","University of Idaho"), Some("(REU student, 2019)")),
        (Overney, Some("(REU student, 2019)")),
        (Raman, Some("(REU student, 2019)")),
        (Person("Sydney","Covitz","Swarthmore College"), Some("(REU student, 2019)")),

        (Person("Jerry", "Lu", "University of Pennsylvania"), Some("(REU student, 2020)")),
        (Person("Madison", "Janes", "Fairmont State University"), Some("(REU student, 2020)")),
        (Person("Mark", "Chen", "Carnegie Mellon University"), Some("(REU student, 2020)")),
        (Person("Mehal ", "Kashyap", "Carnegie Mellon University"), Some("(REU student, 2020)")),
        (Person("Priscila", "Santiesteban", "Coe College"), Some("(REU student, 2020)"))
    )
    val collaboratingStudents = List(
        (HNguyen, Some("(Iowa State, graduated 2017, now at Google)")),
        (AlKofahi, Some("(Iowa State)")),
        (Lillack, Some("(University of Leipzig, graduated 2017)")),
        (Meinicke, Some("(University of Magdeburg)")),
        (Hunsen, Some("(working with Sven Apel at the University of Passau)")),
        (Lessenich, Some("(working with Sven Apel at the University of Passau)"))
    )

}
