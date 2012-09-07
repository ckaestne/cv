package de.stner.cv

import xml.NodeSeq
import java.util.GregorianCalendar


object CV {

    import Venues._
    import CVPublications._

    val name = "Christian Kästner"
    val title = "Dr.-Ing."
    val imgURL = "http://www.informatik.uni-marburg.de/~kaestner/me3.jpg"

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
        <p>Profiles: <a href="http://www.informatik.uni-marburg.de/~kaestner/cv.pdf">Curriculum vitae</a>, <a class="external-link" href="http://scholar.google.com/citations?user=PR-ZnJUAAAAJ">Google Scholar</a>, <a class="external-link" href="http://academic.research.microsoft.com/Author/3564951">Microsoft Academic</a>, <a href="http://dl.acm.org/author_page.cfm?id=81331495728">ACM</a>, <a class="external-link" href="http://www.informatik.uni-trier.de/~ley/db/indices/a-tree/k/K=auml=stner:Christian.html">dblp</a>.</p> :+
        <p> <div>Institute for Software Research (<a href="http://www.isri.cmu.edu/">Link</a>)</div>
            <div>School of Computer Science (<a href="http://www.cs.cmu.edu/">Link</a>)</div>
            <div>Carnegie Mellon University (<a href="http://www.cmu.edu/">Link</a>)</div>
            <div>5000 Forbes Avenue, Pittsburgh, PA 15213, USA</div>
            <div>&nbsp;</div>
            <div>Office: Wean 5122</div>
            <div>&nbsp;</div>
            <div>E-mail: kaestner (at) cs.cmu.edu</div>
            <div>Phone: ++1 412 268 5254</div>
	</p>



    val teaching = Seq(
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


    val advisedTheses = CVTheses.advisedTheses


    val researchInterests = Seq(
        "Virtual separation of concerns",
        "Feature-oriented software development (FOSD), type systems, module systems, parsers, refactoring, aspect-orientation, multidimensional separation of concerns",
        "Software product lines, program synthesis, feature interactions, feature location, empirical analyses"
    )

    val committees = Seq[Committee](
        Committee(Conference("SE", 2013,
            "SE 13 -- GI Konferenz Software Engineering",
            URL("http://www.se2013.rwth-aachen.de/")), PC),
        Committee(SLE(2012), DS),
        Committee(GPCE(2012), PC),
        Committee(FOSD(2012), OC),
        Committee(Conference("SPLC", 2012,
            "16th International Software Product Line Conference",
            URL("http://www.splc2012.net")), OtherCommittee("Tools and Demonstrations Track", "TC")),
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
        Committee(Conference("PEPM", 2012,
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
        Committee(Conference("ASE", 2010,
            "25th International Conference on Automated Software Engineering -- Tool Demonstration Committee",
            URL("http://soft.vub.ac.be/ase2010/")), TC),
        Committee(Workshop("PLEERPS", 2010,
            "Workshop on Product-Line Engineering for Enterprise Resource Planning Systems",
            URL("http://researchr.org/conference/pleerps-2010")), PC),
        Committee(FOSD(2009), OC)
    )
    val reviews: Seq[Review] = Seq(
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


    val awards: Seq[Award] = Seq(
        Award("Grant: Pythia -- Techniques and Prediction Models for Sustainable Product-Line Engineering. German Research Foundation. 250 000 EUR, October 2012 – October 2015.",
            URL("http://www.infosun.fim.uni-passau.de/spl/pythia/"),
            new GregorianCalendar(2012, 6, 1).getTime
        ),
        Award(
            "**GI-Dissertationspreis:** Best Disseration Award of the German Computer Science Association, 2010",
            URL("http://www.gi.de/wir-ueber-uns/wettbewerbe/gi-dissertationspreis.html"),
            new GregorianCalendar(2011, 10, 1).getTime,
            List(
                (URL("http://www.gi.de/no_cache/aktuelles/meldungsdetails/meldung/beste-informatikdissertation-im-deutschsprachigen-raum-gi-dissertationspreis-fr-christian-kstner-387.html"), "Annoucement"),
                (URL("http://www.flickr.com/photos/p0nk/6214434320/"), "Photo")
            )
        ),
        Award(
            "Distinguished-Paper Award at the International Conference on Object-Oriented Programming, Systems, Languages, and Applications (OOPSLA) 2011",
            URL("http://splashcon.org/2011/program/273"),
            new GregorianCalendar(2011, 10, 1).getTime,
            (URL("http://www.flickr.com/photos/p0nk/6317966864/in/photostream"), "Photo") :: Nil
        ),
        Award(
            "Best-Research-Paper Award at the International Software Product Line Conference 2011",
            URL("http://www.splc2011.net/program-1/best-paper-awards/index.html"),
            new GregorianCalendar(2011, 8, 1).getTime
        ),
        Award(
            "Best-Dissertation Award of the School of Computer Science, University of Magdeburg, 2010",
            URL("http://www.cs.uni-magdeburg.de/Bester_Doktorand.html"),
            new GregorianCalendar(2010, 11, 1).getTime
        ),
        Award(
            "Research Award of the School of Computer Science, University of Magdeburg for the Best Paper, 2009",
            URL("http://www.cs.uni-magdeburg.de/Die+FIN/Auszeichnungen/Forschungspreis+der+Fakultät-p-324.html"),
            new GregorianCalendar(2009, 12, 1).getTime
        ),
        Award(
            "Software Engineering Award of the Denert Foundation for the Best Master's Thesis, 2007",
            URL("http://www.denert-stiftung.de/"),
            new GregorianCalendar(2007, 9, 1).getTime,
            (URL("http://www.flickr.com/photos/p0nk/6318925958/in/photostream"), "Photo") :: Nil
        ),
        Award(
            "Best-Graduate Award of the School of Computer Science, University of Magdeburg, 2007",
            URL("http://www.cs.uni-magdeburg.de/Bester_Absolvent.html"),
            new GregorianCalendar(2007, 10, 1).getTime,
            (URL("http://www.flickr.com/photos/p0nk/6318413771/in/photostream"), "Photo") :: Nil
        )
    )
    val projects: Seq[(URL, String, String, Option[String])] = Seq(
        (URL("http://ckaestne.github.com/TypeChef/"), "TypeChef", "Variability-Aware Analysis and Parsing of C Code", None),
        (URL("http://www.informatik.uni-marburg.de/~kaestner/leadt/"), "Feature Mining", "Consistent Semi-Automatic Detection of Product-Line Features", None),
        (URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/research/cide/"), "CIDE", "Virtual Separation of Concerns", None),
        (URL("http://www.sugarj.org/"), "SugarJ", "Library-based Syntactic Language Extensibility", None),
        (URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/research/featureide/"), "FeatureIDE", "Tool Support for Feature-Oriented Software Development", None),
        (URL("http://www.infosun.fim.uni-passau.de/cl/apel/fh/"), "FeatureHouse", "Language-Independent, Automatic Software Composition", None),
        (URL("http://www.fame-dbms.org/"), "FAME-DBMS", "Tailor-Made Data Management", Some("finished")),
        (URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/research/arj/"), "ARJ", "Extending AspectJ with Aspect Refinement and Mixin-Based Aspect Inheritance", Some("finished"))
        //        (URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/research/ajdtstats/"), "AJDTStats: A Statistics Collector for AJDT", Some("finished")),
        //        (URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/research/berkeley/"), "Aspect-oriented refactoring of Berkeley DB", Some("finished"))
    )

    val publications = CVPublications.publications


}
