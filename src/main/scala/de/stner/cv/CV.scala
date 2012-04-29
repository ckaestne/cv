package de.stner.cv


object CV {

    val name = "Christian Kästner"
    val title = "Dr.-Ing."
    val imgURL = "http://www.informatik.uni-marburg.de/~kaestner/me3.jpg"
    val description = "I am a post-doctoral researcher at the Philipps University Marburg interested in controlling the complexity caused by variability in software systems. I develop mechanisms, languages, and tools to implement variability in a disciplined way, to detect errors, and to improve program comprehension in systems with a high amount of variability. Currently, I investigate approaches to parse and type check all compile-time configurations of the Linux kernel in the TypeChef project.\n" +
      "\n" +
      "I received my Master's in 2007 and my PhD in 2010 from the University of Magdeburg. For my work on virtual separation of concerns, I received the prestigious GI-Dissertation Award for the best computer-science dissertation 2010 in Germany/Austria/Switzerland. I am a member of the IFIP 2.11 working group on program generation."

    val address = """
   Philipps Universität Marburg (Link)
   Department of Computer Science and Mathematics (Link)
   Hans-Meerwein Str., 35032 Marburg, Germany

   Office: 05-D06

   Phone: ++49 6421 28 25349
   Fax: ++49 6421 28 25419
   E-mail: christian.kaestner (at) uni-marburg.de
"""

    val teaching = Seq(
        Course("Empirical Methods for Computer Scientists",
            "Einführung in empirische Methoden für Informatiker",
            new URL("http://www.uni-marburg.de/fb12/ps/teaching/ss12/em"),
            German, SummerTerm(2012), new CourseKind with Lecture with Exercise
        ),
        Course("Software Product Lines: Concepts and Implementation",
            "Softwareproduktlinien: Konzepte und Implementierung",
            new URL("http://www.uni-marburg.de/fb12/ps/teaching/ss11/spl"),
            German, SummerTerm(2011), new CourseKind with Lecture with Exercise
        ),
        Course("Software Engineering",
            "Einführung in die Softwaretechnik",
            new URL("http://www.uni-marburg.de/fb12/ps/teaching/ws10/eise"),
            German, WinterTerm(2010), new CourseKind with Lecture,
            "shared with K. Ostermann"
        ),
        Course("Software Product Lines",
            "Software-Produktlinien",
            new URL("http://www.uni-marburg.de/fb12/ps/teaching/ws10/splseminar"),
            German, WinterTerm(2010), Seminar
        ),
        Course("Concepts of Database Implementation",
            "Datenbankenimplementierungstechniken (DB2)",
            new URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/db2/2010"),
            German, SummerTerm(2010), new CourseKind with Exercise
        ),
        Course("Student Conference on Software Engineering and Database Systems", "",
            new URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/studconf"),
            English, SummerTerm(2010), new CourseKind with Lecture
        ),
        Course("Product-Line Implementation for Tailor-Made Data Management",
            "Erweiterte Programmierkonzepte für maßgeschneiderte Datenhaltung (EPMD)",
            new URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/epmd/2009/"),
            German, WinterTerm(2009), new CourseKind with Lecture with Exercise
        ),
        Course("Specification Technologies",
            "Spezifikationstechnik",
            new URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/spt/2009/"),
            German, SummerTerm(2009), new CourseKind with Exercise
        ),
        Course("Concepts of Database Implementation",
            "Datenbankenimplementierungstechniken (DB2)",
            new URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/db2/2009"),
            German, SummerTerm(2009), new CourseKind with Exercise
        ),
        Course("Student Conference on Software Engineering and Database Systems", "",
            new URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/studconf"),
            English, SummerTerm(2009), new CourseKind with Lecture
        ),
        Course("Product-Line Implementation for Tailor-Made Data Management",
            "Erweiterte Programmierkonzepte für maßgeschneiderte Datenhaltung (EPMD)",
            new URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/epmd/2008/"),
            German, WinterTerm(2008), new CourseKind with Lecture with Exercise,
            "Shared with G. Saake"
        ),
        Course("Concepts of Database Implementation",
            "Datenbankenimplementierungstechniken (DB2)",
            new URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/db2/2008"),
            German, SummerTerm(2008), new CourseKind with Exercise
        ),
        Course("Student Conference on Software Engineering and Database Systems", "",
            new URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/studconf"),
            English, SummerTerm(2008), new CourseKind with Lecture
        ),
        Course("Product-Line Implementation for Tailor-Made Data Management",
            "Erweiterte Programmierkonzepte für maßgeschneiderte Datenhaltung (EPMD)",
            new URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/epmd/2007/"),
            German, WinterTerm(2007), new CourseKind with Exercise
        ),
        Course("Concepts of Database Implementation",
            "Datenbankenimplementierungstechniken (DB2)",
            new URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/db2/2007"),
            German, SummerTerm(2007), new CourseKind with Exercise
        ),
        Course("Advanced Database Models",
            "Advanced Database Models (ADBM)",
            new URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/adbm/"),
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
        TeachingProject("Patientenbefragung",SummerTerm(2011),"Fortgeschrittenenpraktikum"),
        TeachingProject("Demoproduktlinie Computerspiel",WinterTerm(2010),"Fortgeschrittenenpraktikum"),
        TeachingProject("Demoproduktlinie Tetris",WinterTerm(2010),"Fortgeschrittenenpraktikum"),
        TeachingProject("Webuni",SummerTerm(2009),"IT-Projekt"),
        TeachingProject("FOP-Demo Application (Tank Game)",SummerTerm(2009),"Laborpraktikum"),
        TeachingProject("Bibliographieverwaltung",WinterTerm(2008),"Softwarepraktikum"),
        TeachingProject("Stundenplanprogramm fuer Univis",WinterTerm(2008),"Laborpraktikum",new URL("http://www.uni-magdeburg.de/mytt/")),
        TeachingProject("Entwicklung einer IDE fuer Software-Produktlinien",WinterTerm(2008),"Laborpraktikum",new URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/lehre/laborws0708/")),
        TeachingProject("Verknuepfung zwischen VR und semantischen Informationen",SummerTerm(2008),"IT-Projekt"),
        TeachingProject("Stundenplanprogramm fuer Univis",WinterTerm(2007),"IT-Projekt",new URL("http://www.uni-magdeburg.de/mytt/"))
    )


    val advisedTheses = CVTheses.advisedTheses




    val researchInterests = Seq(
        "Virtual separation of concerns",
        "Feature-oriented software development (FOSD), type systems, module systems, parsers, refactoring, aspect-orientation, multidimensional separation of concerns",
        "Software product lines, program synthesis, feature interactions, feature location, empirical analyses"
    )

    val committees = Seq[Committee]()

    val awards = Seq[String]()
    val projects = Seq[String]()

    val publications = Seq[Publication]()
    val supervised = Seq[Thesis]()
}
