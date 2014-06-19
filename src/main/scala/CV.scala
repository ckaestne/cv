package de.stner.cv

import xml.NodeSeq
import java.util.{Date, GregorianCalendar}


object CV {

    import Venues._
    import CVPublications._


    val name = "Christian Kästner"
    val url = "http://www.cs.cmu.edu/~ckaestne/"

    def printSummary(): NodeSeq =
        <p>I am an assistant professor in the Institute of Software Research at the Carnegie Mellon University interested in controlling the <strong>complexity</strong> caused by <strong>variability</strong> in software systems. I develop mechanisms,
        languages, and tools to
        <strong>implement variability in a disciplined way</strong>,
        to <strong>detect errors</strong>, and
        to <strong>improve program comprehension</strong> in systems with a high amount of variability. Currently, I investigate approaches to parse and type check all compile-time configurations of the <strong>Linux kernel</strong> in the <strong>TypeChef</strong> project.</p> :+
    <p>My research in keywords:
        variability and reuse;
        software product lines;
        multidimensional separation of concerns;
        modularity, cohesion, information hiding and module systems;
        program comprehension;
        virtual separation of concerns;
        feature-oriented software development;
        parsers, type systems, and lightweight program analyses;
        refactoring;
        aspect-orientation;
        program synthesis;
        feature interactions;
        feature location;
        Linux;
        and empirical methods.</p> :+
        <p>Profiles: 
          <a href="cv.pdf">Curriculum vitae</a>,
          <a href="http://scholar.google.com/citations?user=PR-ZnJUAAAAJ">Google Scholar</a>, 
          <a href="http://academic.research.microsoft.com/Author/3564951">Microsoft Academic</a>, 
          <a href="http://dl.acm.org/author_page.cfm?id=81331495728">ACM</a>, 
          <a href="http://www.informatik.uni-trier.de/~ley/db/indices/a-tree/k/K=auml=stner:Christian.html">dblp</a><span style="display:none">, <a href="https://plus.google.com/113955799521066229715" rel="publisher">Google+</a></span>.</p> :+
        <p><div>Institute for Software Research (<a href="http://www.isri.cmu.edu/">Link</a>)</div>
            <div>School of Computer Science (<a href="http://www.cs.cmu.edu/">Link</a>)</div>
            <div>Carnegie Mellon University (<a href="http://www.cmu.edu/">Link</a>)</div>
            <div itemprop="address" itemscope="" itemtype="http://data-vocabulary.org/Address"><span itemprop="street-address">5000 Forbes Avenue</span>, <span itemprop="locality">Pittsburgh</span>, <span itemprop="region">PA</span> <span itemprop="postal-code">15213</span>, <span itemprop="country">USA</span></div>
            <div>&nbsp;</div>
            <div>Office: Wean 5122</div>
            <div>&nbsp;</div>
            <div>E-mail: kaestner (at) cs.cmu.edu</div>
            <div>Phone: +1 412 268 5254</div>
	</p>


    def headerCVLatex() =
        """\begin{CV}
          |\item[Affiliation]
          |    Assistant Professor \\
          |    Carnegie Mellon University \\
          |    Institute for Software Research \\
          |    5000 Forbes Avenue, Pittsburgh, PA 15213, USA
          |\item[Contact]
          |    +1 412 268 5254 (Office)\\
          |    \href{mailto:kaestner@cs.cmu.edu}{kaestner@cs.cmu.edu}
          |%\item[]Born 1982 in Schwedt/Oder, Germany; German citizenship
          |\end{CV}
          |
          |\section{Profile}
          |\begin{CV}
          |\item[] Assistant professor in the Institute of Software Research at the Carnegie Mellon University interested in controlling the \emph{complexity} caused by \emph{variability} in software systems. Developing mechanisms, languages, and tools to implement variability in a disciplined way, to detect errors, and to improve program comprehension in systems with a high amount of variability.
          |\end{CV}
          |
          |\section{Education}
          |\begin{CV}
          |\item[Apr.\ 2007 -- May 2010]
          |    Doctoral degree in computer science (Doktor-Ingenieur),\\
          |    University of Magdeburg, Germany, \\
          |    Grade summa cum laude (with distinction)\\[.5ex]
          |		Reviewers: Prof.\ Gunter Saake (University of Magdeburg), Prof.\ Don Batory (University of Texas at Austin), Prof.\ Krzysztof Czarnecki (University of Waterloo)
          |\item[Oct.\ 2002 -- Mar.\ 2007]
          |    Diploma degree in business information systems \\(M.Sc.\ equivalent; Diplom-Wirtschaftsinformatiker), \\University of Magdeburg, Germany,\\Grade ``1.0'' (with distinction)
          |\end{CV}
          |
          |\section{Academic Employment}
          |\begin{CV}
          |\item[since Sep. 2012]
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
        "Gesellschaft für Informatik (GI)",
        "Deutscher Hochschulverband"
    )

    val advisedTheses = CVTheses.advisedTheses


    val researchInterests = Seq(
        "Virtual separation of concerns",
        "Feature-oriented software development (FOSD), type systems, module systems, parsers, refactoring, aspect-orientation, multidimensional separation of concerns",
        "Software product lines, program synthesis, feature interactions, feature location, empirical analyses"
    )

    val committees = Seq[Committee](
        Committee(Workshop("ICSE-D", 2015,
            "International Conference on Software Engineering -- Demonstrations Committee",
            URL("http://2015.icse-conferences.org/call-dates/call-for-contributions/demonstrations")), PC),
        Committee(Workshop("MultiPLE", 2014,
            "SPLC Workshop on Multi Product Line Engineering",
            URL("https://sites.google.com/site/wmultiple2014/")), PC),
        Committee(Workshop("SPLat", 2014, "Software Product Line Analysis Tools 2014",
            URL("")), PC),
        Committee(Workshop("SPLTea", 2014, "First International Workshop on Software Product Line Teaching",
            URL("http://spltea.irisa.fr/")), PC),
        Committee(Workshop("REVE", 2014, "2nd Workshop on Reverse Variability Engineering",
            URL("http://www.sea.jku.at/reve2014/")), PC),
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
        Committee(Workshop("REVE", 2013, "1st Workshop on Reverse Variability Engineering",
            URL("http://www.sea.jku.at/reve2013/")), PC),
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
            "3rd International Workshop on Empirical Evaluation of Software Composition Techniques",
            URL("http://dawis2.icb.uni-due.de/events/escot2012/")), OC),
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

    val reviews: Seq[Review] = Seq(
        Review(SCP(2014)), //Science of Computer Programming
        Review(JOSER(2014)), //Science of Computer Programming
        Review(TSE(2014)), //IEEE Transactions on Software Engineering
        Review(TOPLAS(2012)),
        Review(ESE(2012)),
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
        Grant("Grant: Reverse Engineering Variability Implementations",
            URL("http://www.nsf.gov/awardsearch/showAward?AWD_ID=1318808"),
            new GregorianCalendar(2013, 6 - 1, 24).getTime,
            new GregorianCalendar(2013, 9 - 1, 1).getTime,
            new GregorianCalendar(2016, 8 - 1, 31).getTime,
            "National Science Foundation",
            USD(400797)
        ),
        Grant("Grant: Pythia -- Techniques and Prediction Models for Sustainable Product-Line Engineering",
            URL("http://www.infosun.fim.uni-passau.de/spl/pythia/"),
            new GregorianCalendar(2012, 6 - 1, 1).getTime,
            new GregorianCalendar(2012, 10 - 1, 1).getTime,
            new GregorianCalendar(2015, 10 - 1, 1).getTime,
            "German Research Foundation",
            EUR(250000)
        ),
        Award(
            "**GI-Dissertationspreis:** Best Dissertation Award of the German Computer Science Association, 2010",
            URL("http://www.gi.de/wir-ueber-uns/wettbewerbe/gi-dissertationspreis.html"),
            new GregorianCalendar(2011, 10 - 1, 1).getTime,
            List(
                (URL("http://www.gi.de/no_cache/aktuelles/meldungsdetails/meldung/beste-informatikdissertation-im-deutschsprachigen-raum-gi-dissertationspreis-fr-christian-kstner-387.html"), "Annoucement"),
                (URL("http://www.flickr.com/photos/p0nk/6214434320/"), "Photo")
            ),
            Some(EUR(5000))
        ),
        Award(
            "Distinguished-Paper Award at the International Conference on Object-Oriented Programming, Systems, Languages, and Applications (OOPSLA) 2011",
            URL("http://splashcon.org/2011/program/273"),
            new GregorianCalendar(2011, 10 - 1, 1).getTime,
            (URL("http://www.flickr.com/photos/p0nk/6317966864/in/photostream"), "Photo") :: Nil
        ),
        Award(
            "Best-Research-Paper Award at the International Software Product Line Conference 2011",
            URL("http://www.splc2011.net/program-1/best-paper-awards/index.html"),
            new GregorianCalendar(2011, 8 - 1, 1).getTime
        ),
        Award(
            "Best-Dissertation Award of the School of Computer Science, University of Magdeburg, 2010",
            URL("http://www.cs.uni-magdeburg.de/Bester_Doktorand.html"),
            new GregorianCalendar(2010, 11 - 1, 1).getTime,
            Nil,
            Some(EUR(1000))
        ),
        Award(
            "Research Award of the School of Computer Science, University of Magdeburg for the Best Paper, 2009",
            URL("http://www.cs.uni-magdeburg.de/Die+FIN/Auszeichnungen/Forschungspreis+der+Fakultät-p-324.html"),
            new GregorianCalendar(2009, 12 - 1, 1).getTime,
            Nil,
            Some(EUR(1000))
        ),
        Award(
            "Software Engineering Award of the Denert Foundation for the Best Master's Thesis, 2007",
            URL("http://www.denert-stiftung.de/"),
            new GregorianCalendar(2007, 9 - 1, 1).getTime,
            (URL("http://www.flickr.com/photos/p0nk/6318925958/in/photostream"), "Photo") :: Nil,
            Some(EUR(2000))
        ),
        Award(
            "Best-Graduate Award of the School of Computer Science, University of Magdeburg, 2007",
            URL("http://www.cs.uni-magdeburg.de/Bester_Absolvent.html"),
            new GregorianCalendar(2007, 10 - 1, 1).getTime,
            (URL("http://www.flickr.com/photos/p0nk/6318413771/in/photostream"), "Photo") :: Nil
        ),
        Award("Student Scholarship of the Germany Academic Exchange Service",
            URL("http://www.daad.de/"),
            new GregorianCalendar(2006, 9 - 1, 1).getTime
        )
    )
    val projects: Seq[(URL, String, String, Option[String])] = Seq(
        (URL("http://ckaestne.github.com/TypeChef/"), "TypeChef", "Variability-Aware Analysis and Parsing of C Code", None),
        (URL("https://github.com/ckaestne/LEADT"), "Feature Mining", "Consistent Semi-Automatic Detection of Product-Line Features", None),
        (URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/research/cide/"), "CIDE", "Virtual Separation of Concerns", None),
        (URL("http://www.sugarj.org/"), "SugarJ", "Library-based Syntactic Language Extensibility", None),
        (URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/research/featureide/"), "FeatureIDE", "Tool Support for Feature-Oriented Software Development", None),
        (URL("http://www.infosun.fim.uni-passau.de/cl/apel/fh/"), "FeatureHouse", "Language-Independent, Automatic Software Composition", None),
        (URL("http://www.fame-dbms.org/"), "FAME-DBMS", "Tailor-Made Data Management", Some("finished")),
        (URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/research/arj/"), "ARJ", "Extending AspectJ with Aspect Refinement and Mixin-Based Aspect Inheritance", Some("finished"))
        //        (URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/research/ajdtstats/"), "AJDTStats: A Statistics Collector for AJDT", Some("finished")),
        //        (URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/research/berkeley/"), "Aspect-oriented refactoring of Berkeley DB", Some("finished"))
    )

    //name, title, url
    val software: Seq[(String, String, URL)] = Seq(
        ("TypeChef",
            "Parsing and Analyzing #ifdef Variability in C Code",
            URL("https://github.com/ckaestne/TypeChef")),
        ("CIDE",
            "Feature-Oriented Analysis and Decomposition of Legacy Code",
            URL("http://fosd.net/cide/")),
        ("FeatureIDE",
            "A Tool Framework for Feature-Oriented Software Development",
            URL("http://fosd.net/featureide/")),
        ("LEADT",
            "Consistent Semi-Automatic Detection of Product-Line Features",
            URL("http://fosd.net/leadt/")),
        ("FeatureHouse",
            "Language-Independent, Automated Software Composition",
            URL("http://fosd.net/fh/")),
        ("ARJ",
            "Extending AspectJ with Aspect Refinement and Mixin-Based Aspect Inheritance",
            URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/forschung/arj/"))
    )

    //title+name,where,url
    val references: Seq[(String, String, URL)] = Seq(
        ("Prof. Don Batory, Ph.D.", "University of Texas at Austin", URL("http://www.cs.utexas.edu/~dsb/")),
        ("Prof. Krzysztof Czarnecki, Ph.D.", "University of Waterloo", URL("http://gsd.uwaterloo.ca/kczarnec")),
        ("Prof. Christian Lengauer, Ph.D.", "University of Passau", URL("http://www.infosun.fim.uni-passau.de/cl/staff/lengauer/")),
        ("Prof. Dr. Klaus Ostermann", "Philipps University Marburg", URL("http://www.mathematik.uni-marburg.de/~kos/")),
        ("Prof. Dr. Gunter Saake", "University of Magdeburg", URL("http://wwwiti.cs.uni-magdeburg.de/~saake/"))
    )

    val publications = CVPublications.publications


    val parsingandtypecheckingLinux = "Parsing and Type Checking all 2^10000 Configurations of the Linux Kernel"
    val vsoc = "Virtual Separation of Concerns: Toward Preprocessors 2.0"
    val invitedTalks: Seq[InvitedTalk] = Seq(
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
    def time(year: Int, month: Int): Date = new GregorianCalendar(year, month - 1, 1).getTime
}
