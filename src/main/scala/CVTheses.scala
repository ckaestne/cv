package de.stner.cv


object CVTheses {

    import StructureTheses._
    import Coauthors._

    val advisedTheses: Seq[AThesis] = Seq(

        //TODO: Chu-Pan
        //TODO: Committees: Rinjiard, Mauricio, YoungSeok Yoon

        Thesis(
            Zhou,
            "Improving Collaboration Efficiency in Fork-based Development",
            CMU, (5, 2020), PhDThesis,
            Some(PDFFile("diss20Zhou.pdf"))
        ),
        Thesis(
            Meinicke,
            "Variational Debugging: Understanding Differences among Executions",
            MD, (1, 2019), PhDThesis,
            Some(PDFFile("diss19Meinicke.pdf"))
        ),

        Thesis(
            Thuem,
            "Reasoning about Feature Model Edits",
            MD, (6, 2008), Studienarbeit,
            Some(PDFFile("thesisthuem.pdf")),
            "Results published as conference paper at the International Conference on Software Engineering (ICSE), 2009."
        ),

        GermanThesis(
            Person("Axel", "Hoffmann"),
            "Nachvollziehbare Bewirtschaftung gewachsener Datenbestände großer Unternehmen für das Controlling",
            "Traceable Management of Very Big Data Collections for Financial Controlling",
            MD, (8, 2008), Studienarbeit
        ),

        GermanThesis(
            Person("Christian", "Hübner"),
            "Unterstützung der Requirementsanalyse von Navigationssoftware auf Grundlage feature-basierter Domänen-Modelle",
            "Support for Requirements Engineering of Satellite Navigation Systems based on Feature-Oriented Domain Models",
            MD, (12, 2008), Diplomarbeit
        ),

        Thesis(
            Person("Janet", "Feigenspan"),
            "Requirements and design for a language-independent IDE framework to support feature-oriented programming",
            MD, (2, 2009), Studienarbeit,
            Some(PDFFile("thesisfeigenspan.pdf"))
        ),

        Thesis(
            Person("Stefan", "Kegel"),
            "Streamed verification of a data stream management benchmark",
            MD, (4, 2009), Studienarbeit,
            Some(PDFFile("thesiskegel.pdf"))
        ),

        GermanThesis(
            Person("Chau", "Le Minh"),
            "Evaluation feature-basierter service-orientierter Architekturen am Beispiel eines Domotic-Szenarios",
            "Feature-Based Service-Orientented Architectures for Domotic Scenarios",
            MD, (6, 2009), Diplomarbeit,
            Some(PDFFile("thesisleminh.pdf"))
        ),

        GermanThesis(
            Person("Malte", "Rosenthal"),
            "Alternative Features in Colored Featherweight Java",
            "Alternative Features in Colored Featherweight Java",
            PA, (7, 2009), Diplomarbeit,
            Some(PDFFile("thesisrosenthal.pdf"))
        ),
        Thesis(
            Person("Janet", "Feigenspan"),
            "Empirical Comparison of FOSD Approaches Regarding Program Comprehension -- A Feasibility Study",
            MD, (8, 2009), Diplomarbeit,
            Some(PDFFile("thesisfeigenspan_diplom.pdf")),
            "**Best-thesis award by Metop Research Center and Research Award by IHK Magdeburg.** The results were published as part of a journal paper in Empirical Software Engineering, 2012."
        ),

        GermanThesis(
            Person("Dirk", "Aporius"),
            "Verringerung des redundanten Softwareentwicklungsaufwandes für Portable Systeme",
            "Reducing Software Development Effort for Portable Devices",
            MD, (10, 2009), Diplomarbeit
        ),

        GermanThesis(
            Person("Andreas", "Schulze"),
            "Systematische Analyse von Feature-Interaktionen in Softwareproduktlinien",
            "Analysis of Feature Interactions in Software Product Lines",
            MD, (11, 2009), Diplomarbeit,
            Some(PDFFile("thesisschulze.pdf"))
        ),

        Thesis(
            Thuem,
            "A Machine-Checked Proof for a Product-Line-Aware Type System",
            MD, (1, 2010), Diplomarbeit,
            Some(PDFFile("thesisthuem.pdf")),
            "**Best-thesis award of the Denert Foundation for Software Engineering.** Results published as part of a journal paper in ACM Transactions on Software Engineering and Methodology (TOSEM), 2011"
        ),
        GermanThesis(
            Person("Christian", "Becker"),
            "Entwicklung eines nativen Compilers für Feature-orientierte Programmierung",
            "Development of a Native Feature-Oriented Compiler",
            MD, (6, 2010), MastersThesis,
            Some(PDFFile("thesisbecker.pdf"))
        ),
        GermanThesis(
            Person("Alexander", "Dreiling"),
            "Feature Mining: Semiautomatische Transition von (Alt-)Systemen zu Software-Produktlinien",
            "Feature Mining: Semiautomatic Transition from Legacy Systems to Software Product Lines",
            MD, (7, 2010), Diplomarbeit,
            Some(PDFFile("thesisdreiling.pdf")),
            "A journal paper about the results is currently under review"
        ),
        GermanThesis(
            Person("Andy", "Kenner"),
            "Statische Referenzanalyse in C-Präprozessor-konfigurierten Anwendungen",
            "Static Reference Analysis of Implementations with C-Preprocessor Variability",
            MD, (8, 2010), Diplomarbeit,
            Some(PDFFile("thesiskenner.pdf")),
            "Results published as a workshop paper at FOSD 2010"
        ),
        GermanThesis(
            Person("Matthias", "Ritter"),
            "Softwareschutz auf Quellcode-Ebene durch Techniken der Softwareproduktlinienentwicklung",
            "Software Protection at Source Code Level with Product-Line Techniques",
            MD, (9, 2010), Diplomarbeit
        ),
        Thesis(
            Person("Constanze", "Adler"),
            "Optional Composition -- A Solution to the Optional Feature Problem?",
            MD, (12, 2010), MastersThesis
        ),
        Thesis(
            Person("Steffen", "Haase"),
            "A Program Slicing Approach to Feature Identification in Legacy C Code",
            MD, (2, 2012), Diplomarbeit,
            Some(PDFFile("thesishaase.pdf"))
        ),

        GermanThesis(
            Person("Markus", "Kreutzer"),
            "Statische Analyse von Produktlinien",
            "Static Analysis of Product Lines",
            MR, (4, 2012), BachelorsThesis
        ),

        Thesis(
            Person("Jonas", "Pusch"),
            "Variability-Aware Interpretation",
            MR, (11, 2012), BachelorsThesis,
            Some(PDFFile("thesispusch.pdf"))
        ),
        Thesis(
            Person("Jens", "Meinicke"),
            "VarexJ: A Variability-Aware Interpreter for Java Applications",
            MD, (12, 2014), MastersThesis
        )
    )
}
