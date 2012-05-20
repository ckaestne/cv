package de.stner.cv


object CVPublications {

    import Venues._


    object KJournal extends PublicationKind("Refereed Journal Articles", 1)

    object KInConferenceProceedings extends PublicationKind("Refereed Conference Papers", 2)

    object KInvited extends PublicationKind("Invited Papers", 3)

    object KWorkshopDemoTool extends PublicationKind("Refereed Workshop Papers, Posters, and Tool Demos", 4)

    object KTechnicalReport extends PublicationKind("Technical Report", 5)

    object KMisc extends PublicationKind("Miscellaneous", 6)


    object Conference {
        def apply(short: String, year: Int, name: String, url: URL = null) =
            Venue(short, year, name, KInConferenceProceedings, if (url == null) None else Some(url))
    }

    object Workshop {
        def apply(short: String, year: Int, name: String, url: URL = null) =
            Venue(short, year, name, KWorkshopDemoTool, if (url == null) None else Some(url))
    }

    object Journal {
        def apply(short: String, year: Int, name: String, url: URL = null) =
            Venue(short, year, name, KJournal, if (url == null) None else Some(url))
    }

    object TechReport {

        def apply(year: Int, publisher: Publisher, number: String) =
            Venue("", year, "", KTechnicalReport, None).publisher(publisher).number(number)

    }


    val Kaestner = Person("Christian", "Kästner", URL("http://www.uni-marburg.de/fb12/ps/team/kaestner"), "Philipps University Marburg")
    val Apel = Person("Sven", "Apel")
    val Saake = Person("Gunter", "Saake")
    val Feigenspan = Person("Janet", "Feigenspan")
    val Leich = Person("Thomas", "Leich")
    val Kuhlemann = Person("Martin", "Kuhlemann")
    val Trujillo = Person("Salvador", "Trujillo")
    val Batory = Person("Don", "Batory")
    val Lengauer = Person("Christian", "Lengauer")
    val Moeller = Person("Bernhard", "Möller")
    val Siegmund = Person("Norbert", "Siegmund")
    val Rosenmueller = Person("Marko", "Rosenmüller")
    val Pukall = Person("Mario", "Pukall")
    val Rahman = Person("Syed Saif", "ur Rahman")
    val Thuem = Person("Thomas", "Thüm")
    val Groesslinger = Person("Armin", "Größlinger")
    val Heidenreich = Person("Florian", "Heidenreich")
    val Sunkle = Person("Sagar", "Sunkle")
    val Liebig = Person("Jörg", "Liebig")
    val Goetz = Person("Sebastian", "Götz")
    val Cazzola = Person("Walter", "Cazzola")
    val Cook = Person("William R.", "Cook")
    val MSchulze = Person("Michael", "Schulze")
    val Dachselt = Person("Raimund", "Dachselt")
    val Papendieck = Person("Maria", "Papendieck")
    val Frisch = Person("Mathias", "Frisch")
    val SSchulze = Person("Sandro", "Schulze")
    val Scholz = Person("Wolfgang", "Scholz")
    val Kolesnikov = Person("Sergiy", "Kolesnikov")
    val Kenner = Person("Andy", "Kenner")
    val Haase = Person("Steffen", "Haase")
    val Giarrusso = Person("Paolo G.", "Giarrusso")
    val Ostermann = Person("Klaus", "Ostermann")
    val Grebhahn = Person("Alexander", "Grebhahn")
    val Schroeter = Person("Reimar", "Schröter")
    val Stengel = Person("Michael", "Stengel")
    val Koeppen = Person("Veit", "Köppen")
    val Rendel = Person("Tillmann", "Rendel")
    val Erdweg = Person("Sebastian", "Erdweg")
    val Khan = Person("Ateeq", "Khan")

    val fop = Topic("Feature-oriented programming")
    val aop = Topic("Aspect-oriented programming")
    val spl = Topic("Software product lines")
    val vsoc = Topic("Virtual separation of concerns")
    val nfp = Topic("Nonfunctional properties")
    val soa = Topic("SOA")
    val vaanalysis = Topic("Variability-aware analysis")
    val fmanalysis = Topic("Feature-model analysis")
    val dsu = Topic("Dynamic software updates")
    val multiple = Topic("Multi Product Lines")
    val tmdb = Topic("Tailor-made data management")
    val interactions = Topic("Feature interactions")
    val adoption = Topic("Product-line adoption (mining, refactoring, ...)")
    val overview = Topic("Overview papers")
    val experiment = Topic("Controlled experiment")
    val merge = Topic("Software merging")
    val typechef = Topic("TypeChef")
    val modularity = Topic("Modularity")
    val empirical = Topic("Empirical evaluation")
    val programcomprehension = Topic("Program comprehension")


    //shorthands because they are reused
    val GPCE08 = GPCE(2008).month(8).location("Nashville, TN, USA").isbn("978-1-60558-267-2").acceptanceRate(16, 55)
    val GPCE09 = GPCE(2009).month(10).location("Denver, CO, USA").isbn("978-1-60558-828-5").acceptanceRate(19, 62)

    val APSEC08 = Conference("APSEC", 2008, "15th Asia-Pacific Software Engineering Conference").month(12).isbn("978-0-7695-3446-6").issn("1530-1362").location("Beijing, China").publisher(IEEE).acceptanceRate(66, 221)
    val MCGPLE08 = Workshop("McGPLE", 2008, "GPCE Workshop on Modularization, Composition and Generative Techniques for Product Line Engineering").month(10).location("Nashville, TN, USA").publisher(PATR)
    //    .number("MIP - 0802"),
    val ICSE09 = ICSE(2009).month(5).location("Vancouver, Canada").publisher(IEEE).isbn("978-1-4244-3452-7").issn("0270-5257").acceptanceRate(50, 405)
    val BTW09 = Conference("BTW", 2009, "13. GI-Fachtagung Datenbanksysteme für Business, Technologie und Web").month(3).series("Lecture Notes in Informatics").volume("P-144").isbn("978-3-88579-238-3").issn("1617-5468").publisher(GI).location("Münster, Germany")
    val SC09 = Conference("SC", 2009, "8th International Conference on Software Composition (SC)").series("Lecture Notes in Computer Science").volume(5634).publisher(Springer).location("Zurich, Switzerland").issn("0302-9743").isbn("978-3-642-02654-6").acceptanceRate(10, 30).month(7)
    val FOSD09 = FOSD(2009).location("Denver, CO, USA").isbn("978-1-60558-567-3").month(10)
    val FOSD10 = FOSD(2010).location("Eindhoven, The Netherlands").isbn("978-1-4503-0208-1").acceptanceRate(11, 20).month(10)
    val ICSE11Demo = ICSE(2011).subtitle("Demonstration Track").acceptanceRate(22, 60).location("Waikiki, Honolulu, HI").publisher(ACM).isbn("978-1-4503-0445-0")
    val SPLC11 = SPLC(2011).publisher(IEEE).location("Munich").month(8)

    val publications = Seq(
        Publication(
            Seq(Kaestner, Apel, Saake),
            "Implementing Bounded Aspect Quantification in AspectJ",
            RAMSE(2006).month(7).location("Nantes, France").publisher(MDTR),
            Pages(111, 122),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/RAM-SE2006.pdf")),
            """The integration of aspects into the methodology of stepwise software
            development and evolution is still an open issue. This paper
            focuses on the global quantification mechanism of nowadays
            aspect-oriented languages that contradicts basic principles of this
            methodology. One potential solution to this problem is to bound the
            potentially global effects of aspects to a set of local development
            steps. We discuss several alternatives to implement such bounded
            aspect quantification in AspectJ. Afterwards, we describe a concrete
            approach that relies on meta-data and pointcut restructuring
            in order to control the quantification of aspects. Finally, we discuss
            open issues and further work."""
        ).topic(fop, aop)
        ,


        Publication(
            Seq(Apel, Kaestner, Leich, Saake),
            "Aspect Refinement",
            TechReport(2006, MDTR, "10").month(8),
            null,
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/TechReport_10_2006.pdf")),
            """Stepwise refinement (SWR) is fundamental to software engineering.
       As aspect-oriented programming (AOP) gains momentum in
       software development, aspects should be subject to SWR as well. In this
       paper, we introduce the notion of aspect refinement that unifies AOP
       and SWR. To reflect this unification to programming language level, we
       present an implementation technique for refining aspects based on mixin
       composition. Specifically, we propose a set of concrete mechanisms for
       refining all kinds of structural elements of aspects in a uniform way
       (methods, pointcuts, advice). To underpin our proposal, we contribute
       a formal syntax and semantics specification as well as a fully functional
       compiler on top of AspectJ. We apply our approach to a non-trivial case
       study and derive several programming guidelines."""
        ).crosscite("superseded by \\cite{AKLS:TOOLS07}}").topic(fop, aop),

        Publication(
            Seq(Apel, Kaestner, Kuhlemann, Leich),
            "Modularität von Softwarebausteinen: Aspekte versus Merkmale",
            Journal("iX", 2006, "iX Magazin f{\"u}r Professionelle Informationstechnik").number("10").month(10),
            Pages(116, 122),
            Map(HTTP -> URL("http://www.heise.de/kiosk/archiv/ix/06/10/116_Objektorientierte_Grenzen")),
            """Schon seit einigen Jahren macht die aspektorientierte Programmierung von sich reden. Daneben zieht in j{\"u}ngster Zeit die merkmalsorientierte Programmierung die Aufmerksamkeit auf sich. Beide verfolgen mit der Verbesserung der Modularit{\"a}t von Softwarebausteinen {\"a}hnliche Ziele, realisieren dies aber auf unterschiedliche Art und Weise - jeweils mit Vor- und Nachteilen.}""").
            topic(fop, aop),



        //TODO Publication(
        //  Seq(Kaestner),
        //  "Aspect-Oriented Refactoring of {Berkeley DB}",
        //  school =	 "University of Magdeburg",
        //  address =	 "Germany",
        //  month =	 mar,
        //.year(2007)
        //  type={Diplomarbeit},
        //Map(PDF->URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/thesis_final.pdf"))
        //}

        Publication(
            Seq(Apel, Kaestner, Trujillo),
            "On the Necessity of Empirical Studies in the Assessment of Modularization Mechanisms for Crosscutting Concerns",
            ACoM(2007).location("Minneapolis, MN, USA").month(5),
            null,
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/ACoM2007.pdf")),
            """Collaborations are a frequently occurring class of crosscutting
        concerns. Prior work has argued that collaborations
        are better implemented using Collaboration Languages
        (CLs) rather than AspectJ-like Languages (ALs).
        The main argument is that aspects flatten the objectoriented
        structure of a collaboration, and introduce more
        complexity rather than benefits -- in other words, CLs and
        ALs differ with regard to program comprehension. To explore
        the effects of CL and AL modularization mechanisms
        on program comprehension, we propose to conduct a series
        of experiments. We present ideas on how to arrange such
        experiments that should serve as a starting point and foster
        a discussion with other researchers.""").topic(fop, aop, experiment),


        Publication(
            Seq(Apel, Kaestner, Leich, Saake),
            "Aspect Refinement - Unifying {AOP} and Stepwise Refinement",
            JOT(2007).volume(6).number(9).month(10),
            Pages(13, 33),

            Map(PDF -> URL("http://www.jot.fm/issues/issue_2007_10/paper1.pdf"),
                HTTP -> URL("http://www.jot.fm/issues/issue_2007_10/paper1/index.html")),
            """Stepwise refinement (SWR) is fundamental to software engineering. As aspectoriented
       programming (AOP) is gaining momentum in software development, aspects
       should be considered in the light of SWR. In this paper, we elaborate the notion of
       aspect refinement that unifies AOP and SWR at the architectural level. To reflect this
       unification to the programming language level, we present an implementation technique
       for refining aspects based on mixin composition along with a set of language
       mechanisms for refining all kinds of structural elements of aspects in a uniform way
       (methods, pointcuts, advice). To underpin our proposal, we contribute a fully functional
       compiler on top of AspectJ, present a non-trivial, medium-sized case study, and
       derive a set of programming guidelines.""").note("Special Issue: TOOLS EUROPE 2007").
            topic(fop, aop),
        //	nocomment={Acceptance rate: 31\,\% (24/78)},

        Publication(
            Seq(Kaestner, Apel, Batory),
            "A Case Study Implementing Features Using {AspectJ}",
            SPLC(2007).month(9).publisher(IEEE).location("Kyoto, Japan"),
            //	.isbn("0-7695-2888-0"),
            Pages(223, 232),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/splc07.pdf")),

            """Software product lines aim to create highly configurable
           programs from a set of features. Common belief and recent
           studies suggest that aspects are well-suited for implementing
           features. We evaluate the suitability of AspectJ with respect
           to this task by a case study that refactors the embedded
           database system Berkeley DB into 38 features. Contrary to
           our initial expectations, the results were not encouraging.
           As the number of aspects in a feature grows, there is a noticeable
           decrease in code readability and maintainability.
           Most of the unique and powerful features of AspectJ were
           not needed. We document where AspectJ is unsuitable for
           implementing features of refactored legacy applications and
           explain why.""").selected().note("Acceptance rate: 35 % (28/80)").topic(fop, aop, spl),


        Publication(
            Seq(Kaestner),
            "{CIDE}: Decomposing Legacy Applications into Features",
            SPLCDemo(2007).location("Kyoto, Japan").isbn("978-4-7649-0342-5"),
            Pages(149, 150),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/splc07demo.pdf")),
            """Taking an extractive approach to decompose a legacy application
    into features is difficult and laborious with current
    approaches and tools. We present a prototype of a tooldriven
    approach that largely hides the complexity of the task."""
        ).topic(vsoc),


        Publication(
            Seq(Kaestner, Kuhlemann, Batory),
            "Automating Feature-Oriented Refactoring of Legacy Applications",
            Workshop("WRT", 2007, "ECOOP Workshop on Refactoring Tools").location("Berlin, Germany").month(7).publisher(TUBerlin).issn("1436-9915"),
            Pages(62, 63),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/ecooprefactoring.pdf")),
            """Creating a software product line from a legacy application
         is a difficult task. We propose a tool that helps automating
         tedious tasks of refactoring legacy applications
         into features and frees the developer from the burden of
         performing laborious routine implementations."""
        ).topic(vsoc, aop, fop),


        Publication(
            Seq(Apel, Lengauer, Batory, Moeller, Kaestner),
            "An Algebra for Feature-Oriented Software Development",
            TechReport(2007, PATR, "MIP-0706"),
            null,
            Map(PDF -> URL("http://www.infosun.fim.uni-passau.de/cl/staff/apel/publications/mip-0706.pdf")),
            """Feature-Oriented Software Development (FOSD) provides a
 multitude of formalisms, methods, languages, and tools for building variable,
 customizable, and extensible software. Along different lines of research
 different ideas of what a feature is have been developed. Although
 the existing approaches have similar goals, their representations and formalizations
 have not been integrated so far into a common framework.
 We present a feature algebra as a foundation of FOSD. The algebra captures
 the key ideas and provides a common ground for current and future
 research in this field, in which also alternative options can be explored.""").
            crosscite("superseded by \\cite{ALMK:AMAST08} and \\cite{ALMK:SCP10}").
            topic(fop),


        Publication(
            Seq(Trujillo, Kaestner, Apel),
            "Product Lines that supply other Product Lines: A Service-Oriented Approach",
            Workshop("SOAPL", 2007, "SPLC Workshop on  Service-Oriented Architectures and Product Lines").month(9).location("Kyoto, Japan").publisher(SEI),
            Pages(69, 76),
            Map(),
            """Software product line is a paradigm to develop a family
      of software products with the goal of reuse. In this paper, we
      focus on a scenario in which different products from different
      product lines are combined together in a third product
      line to yield more elaborate products, i.e., a product line
      consumes products from third product line suppliers. The
      issue is not how different products can be produced separately,
      but how they can be combined together. We propose
      a service-oriented architecture where product lines are regarded
      as services, yielding a service-oriented product line.
      This paper illustrates the approach with an example for a
      service-oriented architecture of a web portal product line
      supplied by portlet product lines.  """).topic(spl),

        Publication(
            Seq(Apel, Kaestner, Kuhlemann, Leich),
            "Pointcuts, Advice, Refinements, and Collaborations: Similarities, Differences, and Synergies",
            ISSE(2007).month(12).volume(3).number("3-4").issn("1614-5046").publisher(Springer).month(12),
            Pages(281, 289),
            Map(HTTP -> URL("http://www.springerlink.com/content/08m600873g3044t4/"),
                PDF -> URL("http://www.infosun.fim.uni-passau.de/cl/staff/apel/publications/ISSE2007.pdf")),
            """Aspect-oriented programming (AOP) is a novel programming paradigm
  that aims at modularizing complex software. It embraces
  several mechanisms including (1) pointcuts and advice as well as
  (2) refinements and collaborations. Though all these mechanisms
  deal with crosscutting concerns, i.e., a special class of design and
  implementation problems that challenge traditional programming
  paradigms, they do so in different ways. In this article we explore
  their relationship and their impact on software modularity. This
  helps researchers and practitioners to understand their differences
  and guides to use the right mechanism for the right problem."""
        ).topic(aop, fop),

        Publication(
            Seq(Kuhlemann, Kaestner),
            "Reducing the Complexity of {AspectJ} Mechanisms for Recurring Extensions",
            Workshop("AOPLE", 2007, "GPCE Workshop on Aspect-Oriented Product Line Engineering").location("Salzburg, Austria"),
            Pages(14, 19),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/aople07.pdf")),
            """Aspect-Oriented Programming (AOP) aims at modularizing crosscutting
    concerns. AspectJ is a popular AOP language extension for
    Java that includes numerous sophisticated mechanisms for implementing
    crosscutting concerns modularly in one aspect. The language
    allows to express complex extensions, but at the same time
    the complexity of some of those mechanisms hamper the writing
    of simple and recurring extensions, as they are often needed especially
    in software product lines. In this paper we propose an AspectJ
    extension that introduces a simplified syntax for simple and
    recurring extensions. We show that our syntax proposal improves
    evolvability and modularity in AspectJ programs by avoiding those
    mechanisms that may harm evolution and modularity if misused.
    We show that the syntax is applicable for up to 74\% of all pointcut
    and advice mechanisms by analysing three AspectJ case studies.""").topic(aop, fop),


        Publication(
            Seq(Siegmund, Kuhlemann, Rosenmueller, Kaestner, Saake),
            "Integrated Product Line Model for Semi-Automated Product Derivation Using Non-Functional Properties",
            VAMOS(2008).month(1).location("Essen, Germany").issn("1860-2770").publisher(TREssen),
            Pages(25, 23),
            Map(PDF -> URL("http://www.icb.uni-due.de/fileadmin/ICB/research/research_reports/icb_report_22.pdf")),
            """Software product lines (SPL) allow to generate tailormade
       software by manually configuring reusable core assets.
       However, SPLs with hundreds of features and millions
       of possible products require an appropriate support
       for semi-automated product derivation. This derivation has
       to be based on non-functional properties that are related to
       core assets and domain features. Both elements are part
       of different models connected via complex mappings. We
       propose a model that integrates features and core assets in
       order to allow semi-automated product derivation.""").topic(nfp, spl),


        Publication(
            Seq(Kaestner, Apel, Kuhlemann),
            "Granularity in Software Product Lines",
            ICSE(2008).month(5).location("Leipzig, Germany").isbn("978-1-60558-079-1").publisher(ACM),
            Pages(311, 320),
            Map(DOI -> URL("http://doi.acm.org/10.1145/1368088.1368131"),
                ACMLink -> URL("http://dl.acm.org/authorize?063977"),
                PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/icse2008.pdf"),
                EPUB -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/icse2008_ereader.pdf")),
            """
      Building software product lines (SPLs) with features is a challenging
      task. Many SPL implementations support features with coarse
      granularity - e.g., the ability to add and wrap entire methods. However,
      fine-grained extensions, like adding a statement in the middle
      of a method, either require intricate workarounds or obfuscate the
      base code with annotations. Though many SPLs can and have been
      implemented with the coarse granularity of existing approaches,
      fine-grained extensions are essential when extracting features from
      legacy applications. Furthermore, also some existing SPLs could
      benefit from fine-grained extensions to reduce code replication or
      improve readability. In this paper, we analyze the effects of feature
      granularity in SPLs and present a tool, called Colored IDE (CIDE),
      that allows features to implement coarse-grained and fine-grained
      extensions in a concise way. In two case studies, we show how CIDE
      simplifies SPL development compared to traditional approaches.""").
            selected().acceptanceRate(56, 371).topic(vsoc, fop, aop),

        Publication(
            Seq(Apel, Kaestner, Lengauer),
            "Research Challenges in the Tension Between Features and Services",
            Workshop("SDSOA", 2008, "ICSE Workshop on Systems Development in SOA Environments").month(5).
                location("Leipzig, Germany").
                isbn("978-1-60558-029-6").publisher(ACM),
            Pages(53, 58),
            Map(DOI -> URL("http://doi.acm.org/10.1145/1370916.1370930"),
                PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/sdsoa2008.pdf")),
            """We present a feature-based approach, known from software
         product lines, to the development of service-oriented architectures.
         We discuss five benefits of such an approach: improvements
         in modularity, variability, uniformity, specifiability,
         and typeability. Subsequently, we review preliminary
         experiences and results, and propose an agenda for further
         research in this direction.""").topic(fop, soa),


        Publication(
            Seq(Kaestner, Apel, Trujillo, Kuhlemann, Batory),
            "Language-Independent Safe Decomposition of Legacy~Applications into Features",
            TechReport(2008, MDTR, "2").month(3),
            null,
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/gcidetech.pdf")),
            """
           Software product lines (SPL) usually consist of code and
           non-code artifacts written in different languages. Often
           they are created by decomposing legacy applications into
           features. To handle different artifacts uniformly (code, documentation,
           models, etc.), current SPL technologies either
           use an approach that is so general that it works on character
           or token level, but can easily introduce subtle errors; or they
           provide specialized tools for a low number of languages.
           Syntax errors that only occur in certain variants are difficult
           to detect, as the exploding number of variants makes a manual
           testing unfeasible. In this paper, we present CIDE, an
           generic SPL tool that can ensure syntactic correctness for
           all variants. We show CIDE's underlying mechanism that
           abstracts from textual representation and generalize it from
           Java to arbitrary languages. Furthermore, we automate the
           generation of safe plug-ins for additional languages from
           annotated grammars. To demonstrate CIDE's capabilities,
           we applied it to a series of case studies with artifacts from
           different languages, including Java, C\#, C, Haskell, ANTLR,
           and XML.""").hideabstract().
            crosscite("superseded by \\cite{KATKB:TOOLS09}").
            topic(vsoc),


        Publication(
            Seq(Apel, Kaestner, Lengauer),
            "An Overview of Feature Featherweight Java",
            TechReport(2008, PATR, "MIP-0802").month(4),
            null,
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/ffjtech.pdf")),
            """Feature-oriented programming (FOP) is a paradigm that incorporates
      programming language technology, program generation techniques,
      and stepwise refinement. In their GPCE'07 paper, Thaker et al.
      suggest the development of a type system for FOP in order to guarantee
      safe feature composition. We present such a type system along with a
      calculus for a simple feature-oriented, Java-like language, called Feature
      Featherweight Java (FFJ). Furthermore, we explore several extensions of
      FFJ and how they affect type soundness.""")
            .hideabstract().crosscite("extended material for \\cite{AKL:GPCE08}").
            topic(fop, vaanalysis),

        Publication(
            Seq(Apel, Lengauer, Moeller, Kaestner),
            "An Algebra for Features and Feature Composition",
            Conference("AMAST", 2008, "12th International Conference on Algebraic Methodology and Software Technology").
                series("Lecture Notes in Computer Science").volume(5140).
                publisher(Springer).month(7),
            Pages(36, 50),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/AMAST08.pdf"),
                DOI -> URL("http://dx.doi.org/10.1007/978-3-540-79980-1_4")),
            """Feature-Oriented Software Development (FOSD) provides a multitude
        of formalisms, methods, languages, and tools for building variable, customizable,
        and extensible software. Along different lines of research, different notions of
        a feature have been developed. Although these notions have similar goals, no
        common basis for evaluation, comparison, and integration exists. We present a
        feature algebra that captures the key ideas of feature orientation and provides
        a common ground for current and future research in this field, in which also
        alternative options can be explored.""").
            acceptanceRate(27, 58).
            crosscite("extended by \\cite{ALMK:SCP10}").
            topic(fop),


        Publication(
            Seq(Kaestner, Apel),
            "Type-checking Software Product Lines - A Formal Approach",
            ASE(2008).month(9).location("L'Aquila, Italy").issn("1527-1366").isbn("978-1-4244-2187-9"),
            Pages(258, 267),
            Map(DOI -> URL("http://dx.doi.org/10.1109/ASE.2008.36"),
                PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/ASE08.pdf")),
            """
            A software product line (SPL) is an efficient means
            to generate a family of program variants for a domain from
            a single code base. However, because of the potentially high
            number of possible program variants, it is difficult to test all
            variants and ensure properties like type-safety for the entire SPL.
            While first steps to type-check an entire SPL have been taken,
            they are informal and incomplete. In this paper, we extend the
            Featherweight Java (FJ) calculus with feature annotations to be
            used for SPLs. By extending FJ's type system, we guarantee that
            -- given a well-typed SPL -- all possible program variants are welltyped
            as well. We show how results from this formalization reflect
            and help implementing our own language-independent SPL tool
            CIDE.""").
            selected().
            acceptanceRate(30, 280).
            crosscite("extended by \\cite{KATS:TOSEM11}").
            topic(vaanalysis, vsoc),


        Publication(
            Seq(Person("Chang Hwan Peter", "Kim"), Kaestner, Batory),
            "On the Modularity of Feature Interactions",
            GPCE08,
            Pages(23, 34),
            Map(DOI -> URL("http://doi.acm.org/10.1145/1449913.1449919"),
                ACMLink -> URL("http://dl.acm.org/authorize?036803"),
                PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/GPCE08-Kim.pdf")),
            """
     Feature modules are the building blocks of programs in software
     product lines (SPLs).A foundational assumption of feature - based
     program synthesis is that features are composed in a predefined
     order.Recent work on virtual separation of concerns reveals a new
     model of feature interactions that shows that feature modules can be
     quantized as compositions of smaller modules called derivatives.
     We present this model and examine some of its unintuitive consequences,
     namely, that (1) a given program can be reconstructed by
     composing features in any order, and (2) the contents of a feature
     module (as expressed as a composition of derivatives) is determined
     automatically by a feature order.We show that different
     orders allow one to ` adjust ' the contents of a feature module to isolate
     and study the impact of interactions that a feature has with
     other features.Using derivatives, we show the utility of generalizing
     safe composition (SC), a basic analysis of SPLs that verifies
     program type - safety, to prove that every legal composition of derivatives
     (and thus any composition order of features) produces a typesafe
     program, which is a much stronger SC property.""").
            topic(vsoc, vaanalysis),


        Publication(
            Seq(Apel, Kaestner, Batory),
            "Program Refactoring using Functional Aspects",
            GPCE08,
            Pages(161, 170),
            Map(DOI -> URL("http://doi.acm.org/10.1145/1449913.1449938"),
                PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/GPCE08-FA.pdf"),
                ACMLink -> URL("http://dl.acm.org/authorize?036826")),
            """A functional aspect is an aspect that has the semantics of a transformation;
          it is a function that maps a program to an advised program.
          Functional aspects are composed by function composition. In
          this paper, we explore functional aspects in the context of aspect-oriented
          refactoring. We show that refactoring legacy applications
          using functional aspects is just as flexible as traditional aspects in
          that (a) the order in which aspects are refactored does not matter,
          and (b) the number of potential aspect interactions is decreased.
          We analyze several aspect-oriented programs of different sizes to
          support our claims.""").topic(fop, aop),

        Publication(
            Seq(Apel, Kaestner, Lengauer),
            "{Feature Featherweight Java}: A Calculus for Feature-Oriented Programming and Stepwise Refinement",
            GPCE08,
            Pages(101, 112),
            Map(DOI -> URL("http://doi.acm.org/10.1145/1449913.1449931"),
                ACMLink -> URL("http://dl.acm.org/authorize?036811"),
                PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/GPCE08-FFJ.pdf")),
            """ Feature - oriented programming (FOP) is a paradigm that incorporates programming language technology, program generation techniques, and stepwise refinement.In their GPCE ' 07 paper, Thaker et al.suggest the development of a type system for FOP to guarantee safe feature composition, i.e, to guarantee the absence of type errors during feature composition.We present such a type system along with a calculus for a simple feature - oriented, Java - like language, called Feature Featherweight Java (FFJ).Furthermore, we explore four extensions of FFJ and how they affect type soundness.""").
            crosscite("extended by \\cite{AKGL:JASE10}").
            topic(fop, vaanalysis),


        Publication(
            Seq(Kaestner, Trujillo, Apel),
            "Visualizing Software Product Line Variabilities in Source Code",
            Workshop("ViSPLE", 2008, "2nd International SPLC Workshop on Visualisation in Software Product Line Engineering").
                month(9).location("Limerick, Ireland").isbn("978-1-905952-06-9"),
            Pages(303, 313),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/ViSPLE08.pdf")),
            """ Implementing software product lines is a challenging task.Depending on the implementation technique the code that realizes a feature is often scattered across multiple code units.This way it becomes difficult to trace features in source code which hinders maintenance and evolution.While previous effort on visualization technologies in software product lines has focused mainly on the feature model, we suggest tool support for feature traceability in the code base.With our tool CIDE, we propose an approach based on filters and views on source code in order to visualize and trace features in source code.""").
            topic(vsoc),


        Publication(
            Seq(Pukall, Kaestner, Saake),
            "Towards Unanticipated Runtime Adaptation of {Java} Applications",
            APSEC08,
            Pages(85, 92),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/APSEC08-runtime.pdf")),
            """ Modifying an application usually means to stop the application,
        apply the changes, and start the application
        again.That means, the application is not available for at
        least a short time period.This is not acceptable for highly
        available applications.One reasonable approach which
        faces the problem of unavailability is to change highly
        available applications at runtime.To allow extensive runtime
        adaptation the application must be enabled for unanticipated
        changes even of already executed program parts.
        This is due to the fact that it is not predictable what changes
        become necessary and when they have to be applied.Since
        Java is commonly used for developing highly available applications,
        we discuss its shortcomings and opportunities
        regarding unanticipated runtime adaptation.We present
        an approach based on Java HotSwap and object wrapping
        which overcomes the identified shortcomings and evaluate
        it in a case study.""").
            topic(dsu)
        ,


        Publication(
            Seq(Siegmund, Rosenmueller, Kuhlemann, Kaestner, Saake),
            "Measuring Non-functional Properties in Software Product Lines for Product Derivation",
            APSEC08,
            Pages(187, 194),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/APSEC08-nfp.pdf")),
            """Software product lines (SPLs) enable stakeholders to derive
            different software products for a domain while providing
            a high degree of reuse of their code units. Software
            products are derived in a configuration process by combining
            different code units. This configuration process becomes
            complex if SPLs contain hundreds of features. In
            many cases, a stakeholder is not only interested in functional
            but also in resulting non-functional properties of a
            desired product. Because SPLs can be used in different application
            scenarios alternative implementations of already
            existing functionality are developed to meet special nonfunctional
            requirements, like restricted binary size and performance
            guarantees. To enable these complex configurations
            we discuss and present techniques to measure nonfunctional
            properties of software modules and use these values
            to compute SPL configurations optimized to the users
            needs.""").topic(spl, nfp),


        Publication(
            Seq(Rosenmueller, Siegmund, Rahman, Kaestner),
            "Modeling Dependent Software Product Lines",
            MCGPLE08,
            Pages(13, 18),
            Map(PDF -> URL("http://www.infosun.fim.uni-passau.de/cl/staff/apel/McGPLE2008/papers/McGPLE2008Proceedings.pdf")),
            """ Software product line development is a mature technique to implement
        similar programs tailored to serve the needs of multiple
        users while providing a high degree of reuse.This approach also
        scales for larger product lines that use smaller product lines to fulfill
        special tasks.In such compositions of SPLs, the interacting product
        lines depend on each other and programs generated from these
        product lines have to be correctly configured to ensure correct communication
        between them.Constraints between product lines can
        be used to allow only valid combinations of generated programs.
        This, however, is not sufficient if multiple instances of one product
        line are involved.In this paper we present an approach that uses
        UML and OO concepts to model compositions of SPLs.The model
        extends the approach of constraints between SPLs to constraints between
        instances of SPLs and integrates SPL specialization.Based
        on this model we apply a feature - oriented approach to simplify the
        configuration of complete SPL compositions.""").topic(multiple, spl)
        ,

        Publication(
            Seq(Kaestner, Apel),
            "Integrating Compositional and Annotative Approaches for Product Line Engineering",
            MCGPLE08,
            Pages(35, 40),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/McGPLE08.pdf")),

            """Software product lines can be implemented with many different approaches. However, there are common underlying mechanisms which allow a classification into compositional and annotative approaches. While research focuses mainly on composition approaches like aspect- or feature-oriented programming because those support feature traceability and modularity, in practice annotative approaches like preprocessors are common as they are easier to adopt. In this paper, we compare both groups of approaches and find complementary strengths. We propose an integration of compositional and annotative approaches to combine advantages, increase flexibility for the developer, and ease adoption.""").
            topic(fop, vsoc, spl, adoption),


        Publication(
            Seq(Apel, Kaestner, Lengauer),
            "{FeatureHouse}: Language-Independent, Automated Software Composition",
            ICSE09,

            Pages(221, 231),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/icse2009_fh.pdf")),
            """
      Superimposition is a composition technique that has
      been applied successfully in many areas of software development.
      Although superimposition is a general-purpose
      concept, it has been (re)invented and implemented individually
      for various kinds of software artifacts. We unify
      languages and tools that rely on superimposition by using
      the language-independent model of feature structure trees
      (FSTs). On the basis of the FST model, we propose a general
      approach to the composition of software artifacts written
      in different languages, Furthermore, we offer a supporting
      framework and tool chain, called FEATUREHOUSE. We
      use attribute grammars to automate the integration of additional
      languages, in particular, we have integrated Java,
      C\#, C, Haskell, JavaCC, and XML. Several case studies
      demonstrate the practicality and scalability of our approach
      and reveal insights into the properties a language must have
      in order to be ready for superimposition.""").selected().
            topic(fop),


        Publication(
            Seq(Thuem, Batory, Kaestner),
            "Reasoning about Edits to Feature Models",
            ICSE09,
            Pages(254, 264),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/icse2009_fm.pdf")),
            """
         Features express the variabilities and commonalities
         among programs in a software product line (SPL).A feature
         model defines the valid combinations of features, where each
         combination corresponds to a program in an SPL.SPLs
         and their feature models evolve over time.We classify the
         evolution of a feature model via modifications as refactorings,
         specializations, generalizations, or arbitrary edits.We
         present an algorithm to reason about feature model edits
         to help designers determine how the program membership
         of an SPL has changed.Our algorithm takes two feature
         models as input (before and after edit versions), where the
         set of features in both models are not necessarily the same,
         and it automatically computes the change classification.Our
         algorithm is able to give examples of added or deleted products
         and efficiently classifies edits to even large models that
         have thousands of features.""").
            selected().
            topic(spl, fmanalysis),


        Publication(
            Seq(Apel, Kaestner, Groesslinger, Lengauer),
            "On Feature Orientation and Functional Programming",
            TechReport(2008, PATR, "MIP-0806").month(11),
            null,
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/mip-0806.pdf")),
            """
        The separation of concerns is a fundamental principle in software engineering.
        Crosscutting concerns are concerns that do not align with hierarchical
        and block decomposition supported by mainstream programming languages.In
        the past, crosscutting concerns have been studied mainly in the context of object orientation.
        Feature orientation is a novel programming paradigm that supports
        the implementation of crosscutting concerns in a system with a hierarchical
        block structure.We explore the problem of crosscutting concerns in functional
        programming and propose two solutions based on feature orientation.Two case
        studies support our claims. """).
            hideabstract().crosscite("superseded by \\cite{AKGL:SC09}").topic(fop),


        Publication(
            Seq(Apel, Kaestner, Lengauer),
            "{Vergleich und Integration von Komposition und Annotation zur Implementierung von Produktlinien}",
            Conference("SE", 2009, "Software Engineering 2009 -- Fachtagung des GI-Fachbereichs Softwaretechnik").month(3).
                series("Lecture Notes in Informatics").
                publisher(GI).
                volume("P-143").
                location("Kaiserslautern, Germany"),
            Pages(101, 112),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/SE2009.pdf"),
                HTTP -> URL("http://www.gi-ev.de/service/publikationen/lni/gi-edition-proceedings-2009/gi-edition-lecture-notes-in-informatics-lni-p-143.html")),
            """Es gibt eine Vielzahl sehr unterschiedlicher Techniken, Sprachen und Werkzeuge
       zur Entwicklung von Softwareproduktlinien. Trotzdem liegen gemeinsame Mechanismen
       zu Grunde, die eine Klassifikation in Kompositions- und Annotationsansatz
       erlauben. W{\"a}hrend der Kompositionsansatz in der Forschung groï¿½e Beachtung findet,
       kommt im industriellen Umfeld haupts{\"a}chlich der Annotationsansatz zur Anwendung.
       Wir analysieren und vergleichen beide Ans{\"a}tze anhand von drei repr{\"a}sentativen Vertretern
       und identifizieren anhand von sechs Kriterien individuelle St{\"a}rken und Schw{\"a}chen.
       Wir stellen fest, dass die jeweiligen St{\"a}rken und Schw{\"a}chen komplement{\"a}r sind. Aus
       diesem Grund schlagen wir die Integration des Kompositions- und Annotationsansatzes
       vor, um so die Vorteile beider zu vereinen, dem Entwickler eine breiteres Spektrum
       an Implementierungsmechanismen zu Verf{\"u}gung zu stellen und die Einf{\"u}hrung von
       Produktlinientechnologie in bestehende Softwareprojekte zu erleichtern.""").
            topic(fop, vsoc),

        Publication(
            Seq(Siegmund, Kaestner, Rosenmueller, Heidenreich, Apel, Saake),
            "Bridging the Gap between Variability in Client Application and Database Schema",
            BTW09,
            Pages(297, 306),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~rosenmue/publications/SKRH08dbschema.pdf"),
                HTTP -> URL("http://www.gi-ev.de/service/publikationen/lni/gi-edition-proceedings-2009/gi-edition-lecture-notes-in-informatics-lni-p-144.html")),
            """
Database schemas are used to describe the logical design of a database.
Diverse groups of users have different perspectives on the schema which leads to
different local schemas.Research has focused on view integration to generate a global,
consistent schema out of different local schemas or views.However, this approach
seems to be too constrained when the generated global view should be variable and
only a certain subset is needed.Variable schemas are needed in software product lines
in which products are tailored to the needs of stakeholders.We claim that traditional
modeling techniques are not sufficient for expressing a variable database schema.We
show that software product line methodologies, when applied to the database schemas,
overcome existing limitations and allow the generation of tailor - made database schemas.""").
            topic(fop, vsoc, tmdb, spl),


        Publication(
            Seq(Rosenmueller, Kaestner, Siegmund, Sunkle, Apel, Leich, Saake),
            "SQL \\`{a} la Carte -- Toward Tailor-made Data Management",
            BTW09,
            Pages(117, 136),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~rosenmue/publications/RKSS08sql.pdf"),
                HTTP -> URL("http://www.gi-ev.de/service/publikationen/lni/gi-edition-proceedings-2009/gi-edition-lecture-notes-in-informatics-lni-p-144.html")),
            """
  The size of the structured query language (SQL) continuously increases.
  Extensions of SQL for special domains like stream processing or sensor networks come
  with own extensions, more or less unrelated to the standard. In general, underlying
  DBMS support only a subset of SQL plus vendor specific extensions. In this paper,
  we analyze application domains where special SQL dialects are needed or are already
  in use. We show how SQL can be decomposed to create an extensible family of SQL
  dialects. Concrete dialects, e.g., a dialect for web databases, can be generated from
  such a family by choosing SQL features  \`{a} la carte. A family of SQL dialects simplifies
  analysis of the standard when deriving a concrete dialect, makes it easy to understand
  parts of the standard, and eases extension for new application domains. It is also the
  starting point for developing tailor-made data management solutions that support only
  a subset of SQL. We outline how such customizable DBMS can be developed and what
  benefits, e.g., improved maintainability and performance, we can expect from this.""").
            topic(tmdb, spl),


        Publication(
            Seq(Kaestner, Thuem, Saake, Feigenspan, Leich, Person("Fabian", "Wielgorz"), Apel),
            "{FeatureIDE}: Tool Framework for Feature-Oriented Software Development",
            ICSE09.acceptanceRate(24, 72),
            Pages(611, 614),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/icse2009_featureide_demo.pdf")),
            """
       Tools support is crucial for the acceptance of a new programming
       language. However, providing such tool support
       is a huge investment that can usually not be provided
       for a research language. With FeatureIDE, we have built
       an IDE for AHEAD that integrates all phases of featureoriented
       software development. To reuse this investment for
       other tools and languages, we refactored FeatureIDE into
       an open source framework that encapsulates the common
       ideas of feature-oriented software development and that can
       be reused and extended beyond AHEAD. Among others, we
       implemented extensions for FeatureC++ and FeatureHouse,
       but in general, FeatureIDE is open for everybody to showcase
       new research results and make them usable to a wide
       audience of students, researchers, and practitioners."""
        ).note("Formal Demonstration paper").topic(fop, spl),


        Publication(
            Seq(Kaestner, Apel, Trujillo, Kuhlemann, Batory),
            "Guaranteeing Syntactic Correctness for all Product Line Variants: A Language-Independent Approach",
            Conference("TOOLS EUROPE", 2009, "47th International Conference Objects, Models, Components, Patterns").
                issn("1865-1348},isbn={978-3-642-02570-9").
                series("Lecture Notes in Business Information Processing").volume(33).
                month(6).publisher(Springer).
                location("Zurich, Switzerland").
                acceptanceRate(19, 67),
            Pages(175, 194),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/tools09.pdf"),
                HTTP -> URL("http://www.springerlink.com/content/t8752407443k83j2"),
                DOI -> URL("10.1007/978-3-642-02571-6")),
            """A software product line (SPL) is a family of related program variants in
     a well-defined domain, generated from a set of features. A fundamental difference
     from classical application development is that engineers develop not a single
     program but a whole family with hundreds to millions of variants. This makes
     it infeasible to separately check every distinct variant for errors. Still engineers
     want guarantees on the entire SPL. A further challenge is that an SPL may contain
     artifacts in different languages (code, documentation, models, etc.) that should be
     checked. In this paper, we present CIDE, an SPL development tool that guarantees
     syntactic correctness for all variants of an SPL. We show how CIDE's underlying
     mechanism abstracts from textual representation and we generalize it to arbitrary
     languages. Furthermore, we automate the generation of safe plug-ins for additional
     languages from annotated grammars. To demonstrate the language-independent
     capabilities, we applied CIDE to a series of case studies with artifacts written in
     Java, C++, C, Haskell, ANTLR, HTML, and XML.""").
            selected().topic(vsoc, vaanalysis, spl),


        Publication(
            Seq(Person("Stefan", "Boxleitner"), Apel, Kaestner),
            "Language-Independent Quantification and Weaving for Feature Composition",
            SC09,
            Pages(45, 54),
            Map(HTTP -> URL("http://www.springerlink.com/content/p2p728q15r347576/"),
                DOI -> URL("10.1007/978-3-642-02655-3_5"),
                PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/sc09_mod.pdf")),
            """
  Based on a general model of feature composition, we present a composition
  language that enables programmers by means of quantification and weaving
  to formulate extensions to programs written in different languages.We explore
  the design space of composition languages that rely on quantification and weaving
  and discuss our choices.We outline a tool that extends an existing infrastructure
  for feature composition and discuss results of three initial case studies.""").
            note("Short Paper").topic(aop, fop),


        Publication(
            Seq(Apel, Kaestner, Groesslinger, Lengauer),
            "Feature (De)composition in Functional Programming",
            SC09,
            Pages(9, 26),
            Map(HTTP -> URL("http://www.springerlink.com/content/m0q4530571t18042/"),
                DOI -> URL("10.1007/978-3-642-02655-3_3"),
                PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/sc09_fc.pdf")),
            """
           The separation of concerns is a fundamental principle in software engineering.
           Crosscutting concerns are concerns that do not align with hierarchical
           and block decomposition supported by mainstream programming languages. In
           the past, crosscutting concerns have been studied mainly in the context of object
           orientation. Feature orientation is a novel programming paradigm that supports
           the (de)composition of crosscutting concerns in a system with a hierarchical
           block structure. By means of two case studies we explore the problem of crosscutting
           concerns in functional programming and propose two solutions based on
           feature orientation.""").
            topic(fop),


        Publication(
            Seq(Apel, Person("Florian", "Janda"), Trujillo, Kaestner),
            "Model Superimposition in Software Product Lines",
            Conference("ICMT", 2009, "2nd International Conference on Model Transformation").
                series("Lecture Notes in Computer Science").volume(5563).publisher(Springer).
                location("Zurich, Switzerland").issn("0302-9743").isbn("978-3-642-02407-8").month(6).
                acceptanceRate(14, 67),
            Pages(4, 19),

            Map(HTTP -> URL("http://www.springerlink.com/content/2k512285p2lv6j04/"),
                DOI -> URL("10.1007/978-3-642-02408-5_2"),
                PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/icmt09.pdf")),
            """
  In software product line engineering, feature composition generates
  software tailored to specific requirements from a common set of artifacts.Superimposition
  is a popular technique to merge code pieces belonging to different
  features.The advent of model - driven development raises the question of how to
  support the variability of software product lines in modeling techniques.We propose
  to use superimposition as a model composition technique in order to support
  variability.We analyze the feasibility of superimposition as a model composition
  technique, offer a corresponding tool for model composition, and discuss our experiences
  with three case studies (including one industrial study) using this tool.""").
            topic(fop),


        Publication(
            Seq(Person("Friedrich", "Steimann"), Person("Thomas", "Pawlitzki"), Apel, Kaestner),
            "Types and Modularity for Implicit Invocation with Implicit Announcement",
            TOSEM(2010).number(1).volume(20),
            PagesStr("Article 1; 43 pages"),
            Map(ACMLink -> URL("http://dl.acm.org/authorize?387243"),
                PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/TOSEM2009.pdf"),
                DOI -> URL("http://doi.acm.org/10.1145/1767751.1767752")),
            """Through implicit invocation, procedures are called without explicitly referencing them. Implicit announcement
adds to this implicitness by not only keeping implicit which procedures are called, but also where or when --
under implicit invocation with implicit announcement, the call site contains no signs of that, or what it calls.
Recently, aspect-oriented programming has popularized implicit invocation with implicit announcement as a
possibility to separate concerns that lead to interwoven code if conventional programming techniques are used.
However, as has been noted elsewhere, as currently implemented it establishes strong implicit dependencies
between components, hampering independent software development and evolution. To address this problem, we
present a type-based modularization of implicit invocation with implicit announcement that is inspired by how
interfaces and exceptions are realized in JAVA. By extending an existing compiler and by rewriting several
programs to make use of our proposed language constructs, we found that the imposed declaration clutter tends
to be moderate; in particular, we found that for general applications of implicit invocation with implicit announcement,
fears that programs utilizing our form of modularization become unreasonably verbose are unjustified.""").
            topic(aop),

        Publication(
            Seq(Kaestner, Apel, Saake),
            "{Sichere Produktlinien: Herausforderungen f{\"u}r Syntax- und Typ-Pr{\"u}fungen}",
            Workshop("", 2009, "26. Workshop der GI-Fachgruppe Programmiersprachen und Rechenkonzepte").location("Bad Honnef").month(5).publisher(Publisher("University of Kiel", "Kiel, Germany")).number("0915"), //           editor={Bernd Brassel and Michael Hanus},
            Pages(37, 38),
            Map(HTTP -> URL("http://www.informatik.uni-kiel.de/ifi/forschung/technische-berichte/bericht/?tx_publication_pi1%5Bsingle%5D=162")),
            "").topic(vaanalysis, spl),

        Publication(
            Seq(Kaestner, Apel, Rahman, Rosenmueller, Batory, Saake),
            "On the Impact of the Optional Feature Problem: Analysis and Case Studies",
            SPLC(2009).month(8).publisher(SEI).isbn("978-0-9786956-2-0").location("San Francisco, CA, USA").
                acceptanceRate(30, 83),
            Pages(181, 190),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/splc09.pdf")),
            """ A software product - line is a family of related programs that are distinguished in terms of features.A feature implements a stakeholders ' requirement.Different program variants specified by distinct feature selections are produced from a common code base.The optional feature problem describes a common mismatch between variability intended in the domain and dependencies in the implementation.When this occurs, some variants that are valid in the domain cannot be produced due to implementation issues.There are many different solutions to the optional feature problem, but they all suffer from drawbacks such as reduced variability, increased development effort, reduced efficiency, or reduced source code quality.In this paper, we examine the impact of the optional feature problem in two case studies in the domain of embedded database systems, and we survey different state - of - the - art solutions and their trade - offs.Our intension is to raise awareness of the problem, to guide developers in selecting an appropriate solution for their product - line project, and to identify opportunities for future research.""").
            selected().topic(spl, interactions),

        Publication(
            Seq(Kaestner, Apel, Kuhlemann),
            "{LJ}$^{AR}$: A Model of Refactoring Physically and Virtually Separated Features",
            TechReport(2009, MDTR, "08").month(5),
            null,
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/forschung/cide/LJARTech/ljartech.pdf")),
            """
          Physical separation with class refinements and method refinements ï¿½ la AHEAD
          and virtual separation using annotations ï¿½ la \#ifdef or CIDE are two competing
          groups of implementation approaches for software product lines with complementary
          advantages. Although both groups have been mainly discussed in isolation,
          we strive for an integration to leverage the respective advantages. In this paper, we
          provide the basis for such an integration by providing a model that supports both,
          physical and virtual separation, and by describing refactorings in both directions.
          We prove the refactorings complete, such that every virtually separated product
          line can be automatically transformed into a physically separated one (replacing
          annotations by refinements) and vice versa. To demonstrate the feasibility of our
          approach, we have implemented the refactorings in our tool CIDE and conducted
          four case studies.""").hideabstract().crosscite("extended material for \\cite{KAK:GPCE09}").
            topic(fop, vsoc, adoption),


        Publication(
            Seq(Apel, Kaestner, Groesslinger, Lengauer),
            "Type-Safe Feature-Oriented Product Lines",
            TechReport(2009, PATR, "MIP-0909").month(6),
            null,
            Map(PDF -> URL("http://www.infosun.fim.uni-passau.de/cl/publications/docs/MIP-0909.pdf")),
            """
        A feature-oriented product line is a family of programs that share a
        common set of features. A feature implements a stakeholder's requirement, represents
        a design decision and configuration option and, when added to a program,
        involves the introduction of new structures, such as classes and methods, and the
        refinement of existing ones, such as extending methods. With feature-oriented
        decomposition, programs can be generated, solely on the basis of a user's selection
        of features, by the composition of the corresponding feature code. A key
        challenge of feature-oriented product line engineering is how to guarantee the
        correctness of an entire feature-oriented product line, i.e., of all of the member
        programs generated from different combinations of features. As the number of
        valid feature combinations grows progressively with the number of features, it
        is not feasible to check all individual programs. The only feasible approach is
        to have a type system check the entire code base of the feature-oriented product
        line. We have developed such a type system on the basis of a formal model of a
        feature-oriented Java-like language. We demonstrate that the type system ensures
        that every valid program of a feature-oriented product line is well-typed and that
        the type system is complete.""").hideabstract.
            crosscite("superseded by \\cite{AKGL:JASE10}").
            topic(vaanalysis, fop),


        Publication(
            Seq(Apel, Kaestner),
            "An Overview of Feature-Oriented Software Development",
            JOT(2009).volume(8).number(5).month("July/August"),
            Pages(49, 84),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/JOT09_OverviewFOSD.pdf"),
                HTTP -> URL("http://www.jot.fm/issues/issue_2009_07/column5/index.html")),
            """ Feature - oriented software development (FOSD) is a paradigm for the construction,
        customization, and synthesis of large - scale software systems.In this survey, we give
        an overview and a personal perspective on the roots of FOSD, connections to other
        software development paradigms, and recent developments in this field.Our aim is to
        point to connections between different lines of research and to identify open issues.""").
            note("Refereed Column").
            selected().topic(fop, vsoc, overview),

        Publication(
            Seq(Kuhlemann, Batory, Kaestner),
            "Safe Composition of Non-Monotonic Features",
            GPCE09,
            Pages(177, 185),
            Map(ACMLink -> URL("http://dl.acm.org/authorize?131383"),
                DOI -> URL("http://doi.acm.org/10.1145/1621607.1621634"),
                PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~mkuhlema/publications/GPCE09.pdf")),
            """
        Programs can be composed from features. We want to verify automatically
        that all legal combinations of features can be composed
        safely without errors. Prior work on this problem assumed that features
        add code monotonically. We generalize prior work to enable
        features to both add and remove code, describe our analyses and
        implementation, and review case studies. We observe that more
        expressive features can increase the complexity of developed programs
        rapidly -- up to the point where automated concepts as presented
        in this paper are not a helpful tool but a necessity for verification."""
        ).topic(fop, vaanalysis),


        Publication(
            Seq(Kaestner, Apel, Kuhlemann),
            "A Model of Refactoring Physically and Virtually Separated Features",
            GPCE09,
            Pages(157, 166),
            Map(DOI -> URL("http://doi.acm.org/10.1145/1621607.1621632"),
                ACMLink -> URL("http://dl.acm.org/authorize?131381"),
                PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/GPCE09-LJAR.pdf")),
            """Physical separation with class refinements and method refinements à la AHEAD and virtual separation using annotations à la \emph{\#ifdef} or CIDE are two competing groups of implementation approaches for software product lines with complementary advantages.Although both groups have been mainly discussed in isolation, we strive for an integration to leverage the respective advantages.In this paper, we provide the basis for such an integration by providing a model that supports both, physical and virtual separation, and by describing refactorings in both directions.We prove the refactorings complete, such that every virtually separated product line can be automatically transformed into a physically separated one (replacing annotations by refinements) and vice versa.To demonstrate the feasibility of our approach, we have implemented the refactorings in our tool CIDE and conducted four case studies.""").
            topic(vsoc, fop, adoption).selected(),


        Publication(
            Seq(Kaestner, Apel),
            "Virtual Separation of Concerns -- A Second Chance for Preprocessors",
            JOT(2009).volume(8).number(6).month(9),
            Pages(59, 78),
            Map(PDF -> URL("http://www.jot.fm/issues/issue_2009_09/column5.pdf"),
                HTTP -> URL("http://www.jot.fm/issues/issue_2009_09/column5/")),
            """
        Conditional compilation with preprocessors like cpp is a simple but effective means to
        implement variability. By annotating code fragments with \emph{\#ifdef} and \emph{\#endif} directives,
        different program variants with or without these fragments can be created, which
        can be used (among others) to implement software product lines. Although, preprocessors
        are frequently used in practice, they are often criticized for their negative effect
        on code quality and maintainability. In contrast to modularized implementations, for
        example using components or aspects, preprocessors neglect separation of concerns,
        are prone to introduce subtle errors, can entirely obfuscate the source code, and limit
        reuse. Our aim is to rehabilitate the preprocessor by showing how simple tool support
        can address these problems and emulate some benefits of modularized implementations.
        At the same time we emphasize unique benefits of preprocessors, like simplicity
        and language independence. Although we do not have a definitive answer on how to
        implement variability, we want highlight opportunities to improve preprocessors and
        encourage research toward novel preprocessor-based approaches."""
        ).note("Refereed Column").selected().topic(vsoc, overview),

        Publication(
            Seq(Apel, Liebig, Kaestner, Kuhlemann, Leich),
            "An Orthogonal Access Modifier Model for Feature-Oriented Programming",
            FOSD09,
            Pages(27, 34),
            Map(PDF -> URL("http://www.infosun.fim.uni-passau.de/cl/publications/docs/FOSD2009am.pdf"),
                DOI -> URL("http://doi.acm.org/10.1145/1629716.1629723")),
            """In feature-oriented programming (FOP), a programmer decomposes
        a program in terms of features. Ideally, features
        are implemented modularly so that they can be developed in
        isolation. Access control is an important ingredient to attain
        feature modularity as it provides mechanisms to hide and
        expose internal details of a module's implementation. But
        developers of contemporary feature-oriented languages did
        not consider access control mechanisms so far. The absence
        of a well-defined access control model for FOP breaks the
        encapsulation of feature code and leads to unexpected and
        undefined program behaviors as well as inadvertent type errors,
        as we will demonstrate. The reason for these problems
        is that common object-oriented modifiers, typically provided
        by the base language, are not expressive enough for FOP and
        interact in subtle ways with feature-oriented language mechanisms.
        We raise awareness of this problem, propose three
        feature-oriented modifiers for access control, and present an
        orthogonal access modifier model. """).topic(fop),

        Publication(
            Seq(Feigenspan, Kaestner, Apel, Leich),
            "How to Compare Program Comprehension in FOSD Empirically -- An Experience Report",
            FOSD09,
            Pages(55, 62),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/FOSD09_emp.pdf"),
                DOI -> URL("http://doi.acm.org/10.1145/1629716.1629728")),
            """
        There are many different implementation approaches to realize the
        vision of feature oriented software development, ranging from simple
        preprocessors, over feature-oriented programming, to sophisticated
        aspect-oriented mechanisms. Their impact on readability and
        maintainability (or program comprehension in general) has caused
        a debate among researchers, but sound empirical results are missing.
        We report experience from our endeavor to conduct experiments
        to measure the influence of different implementation mechanisms
        on program comprehension. We describe how to design such
        experiments and report from possibilities and pitfalls we encountered.
        Finally, we present some early results of our first experiment
        on comparing CPP with CIDE.    """).topic(vsoc, experiment),

        //       TODO Publication(
        //           editor={Apel and William Cook and Krzysztof Czarnecki and Kaestner and Neil Loughran and Oscar Nierstrasz},
        //         "Proceedings of the First International Workshop on Feature-Oriented Software Development {(FOSD)}, October 6, 2009, Denver, Colorado, USA",
        //           publisher=ACM,address=ACMAddr,
        //        .location("Denver, CO, USA"),
        //           .isbn("978-1-60558-567-3"),
        //           month=oct,year=2009,
        //        Map(PDF->URL("http://www.infosun.fim.uni-passau.de/cl/staff/apel/FOSD2009/FOSD2009_Printed_Proceedings.pdf")),
        //           http={http://portal.acm.org/citation.cfm?id=1629716}
        //        }

        Publication(
            Seq(Pukall, Kaestner, Goetz, Cazzola, Saake),
            "Flexible Runtime Program Adaptations in {Java} - A Comparison",
            TechReport(2009, MDTR, "14").month(11),
            null,
            Map(PDF -> URL("http://www.cs.uni-magdeburg.de/fin_media/downloads/forschung/preprints/2009/TechReport14-p-1591.pdf")),
            "").hideabstract().
            topic(dsu),


        //        TODO Publication(
        //           Seq(Kuhlemann, Kaestner, Apel),
        //         "Reducing Code Replication in Delegation-Based {Java} Programs",
        //           booktitle = {Java Software and Embedded Systems},
        //           .isbn("978-1-60741-661-6"),
        //        .year(2010)
        //        Pages(171,183),
        //        .publisher("Nova Science Publishers, Inc."),
        //        .addess("Hauppauge, NY"),
        //           editor = {Mattis Hayes and Isaiah Johansen},
        //        Map(HTTP->URL("https://www.novapublishers.com/catalog/product_info.php?products_id=10125"))
        //        }
        //        }



        Publication(
            Seq(Kaestner, Apel, Saake),
            "Virtuelle Trennung von Belangen (Präprozessor 2.0)",
            Conference("SE", 2010, "Software Engineering 2010 -- Fachtagung des GI-Fachbereichs Softwaretechnik").month(2).
                series("Lecture Notes in Informatics").
                publisher(GI).
                volume("P-159").
                location("Paderborn, Germany").
                acceptanceRate(17, 47),
            Pages(165, 176),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/SE2010.pdf")),

            """
        Bedingte Kompilierung mit Präprozessoren wie \emph{cpp} ist ein einfaches, aber wirksames Mittel zur Implementierung von Variabilität in Softwareproduktlinien.
          Durch das Annotieren von Code-Fragmenten mit \emph{\#ifdef} und \emph{\#endif} können verschiedene Programmvarianten mit oder ohne diesen Fragmenten generiert werden. Obwohl Präprozessoren häufig in der Praxis verwendet werden, werden sie oft für ihre negativen Auswirkungen auf  Codequalität und Wartbarkeit kritisiert. Im Gegensatz zu modularen Implementierungen, etwa mit Komponenten oder Aspekte, vernachlässigen Präprozessoren die Trennung von Belangen im Quelltext, sind anfällig für subtile Fehler und verschlechtern die Lesbarkeit des Quellcodes.
          Wir zeigen, wie einfache Werkzeugunterstützung diese Probleme adressieren und zum Teil beheben bzw. die Vorteile einer modularen Implementierung emulieren kann. Gleichzeitig zeigen wir Vorteile von Präprozessoren wie Einfachheit und Sprachunabhängigkeit auf.""").
            topic(vsoc, overview),

        Publication(
            Seq(Apel, Liebig, Lengauer, Kaestner, Cook),
            "Semistructured Merge in Revision Control Systems",
            VAMOS(2010).month(1).publisher(TREssen),
            Pages(13, 20),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/~ckaestne/VaMoS2010.pdf")),
            """
        Revision control systems are a major means to
        manage versions and variants of today's software systems. An
        ongoing problem in these systems is how to resolve conflicts
        when merging independently developed revisions. Unstructured
        revision control systems are purely text-based and solve conflicts
        based on textual similarity. Structured revision control systems
        are tailored to specific languages and use language-specific
        knowledge for conflict resolution. We propose semistructured
        revision control systems to inherit the strengths of both classes
        of systems: generality and expressiveness. The idea is to provide
        structural information of the underlying software artifacts in the
        form of annotated grammars, which is motivated by recent work
        on software product lines. This way, a wide variety of languages
        can be supported and the information provided can assist the
        resolution of conflicts. We have implemented a preliminary
        tool and report on our experience with merging Java artifacts.
        We believe that drawing a connection between revision control
        systems and product lines has benefits for both fields.  """).
            topic(merge),


        Publication(
            Seq(Liebig, Apel, Lengauer, Kaestner, MSchulze),
            "An Analysis of the Variability in Forty Preprocessor-Based Software Product Lines",
            ICSE(2010).month(5).location("Cape Town, South Africa").acceptanceRate(52, 380).publisher(ACM),
            Pages(105, 114),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/publikationen/ps/auto/LALKS:ICSE10.pdf"),
                ACMLink -> URL("http://dl.acm.org/authorize?369011"),
                DOI -> URL("http://doi.acm.org/10.1145/1806799.1806819")),
            """
Over 30 years ago, the preprocessor cpp was developed to
extend the programming language C by lightweight metaprogramming
capabilities.Despite its error - proneness and low
abstraction level, the cpp is still widely being used in presentday
software projects to implement variable software.However,
not much is known about \ emph {
how
} the cpp is employed
to implement variability.To address this issue, we have
analyzed forty open - source software projects written in C.
Specifically, we answer the following questions: How does program
size influence variability ? How complex are extensions
made via cpp 's variability mechanisms ? At which level of
granularity are extensions applied ? What is the general type
of extensions ? These questions revive earlier discussions on
understanding and refactoring of the preprocessor.To answer
them, we introduce several metrics measuring the variability,
complexity, granularity, and type of extensions.Based on
the data obtained, we suggest alternative implementation
techniques.The data we have collected can influence other
research areas, such as language design and tool support.""").
            selected().topic(vsoc, empirical),


        Publication(
            Seq(Apel, Kaestner, Groesslinger, Lengauer),
            "Type Safety for Feature-Oriented Product Lines",
            JASE(2010).issn("0928-8910").volume(17).number(3),
            Pages(251, 300),
            Map(HTTP -> URL("http://www.springerlink.com/content/fh1725331424x665/"),
                PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/publikationen/ps/auto/AKGL:JASE10.pdf"),
                DOI -> URL("http://www.springerlink.com/openurl.asp?genre=article&id=doi:10.1007/s10515-010-0066-8")),
            """
A feature-oriented product line is a family of programs that share a common set
of features. A feature implements a stakeholder's requirement and represents a design decision
or configuration option. When added to a program, a feature involves the introduction
of new structures, such as classes and methods, and the refinement of existing ones, such
as extending methods. A feature-oriented decomposition enables a generator to create an
executable program by composing feature code solely on the basis of the feature selection
of a user -- no other information needed. A key challenge of product line engineering is to
guarantee that only well-typed programs are generated. As the number of valid feature combinations
grows combinatorially with the number of features, it is not feasible to type check
all programs individually. The only feasible approach is to have a type system check the
entire code base of the feature-oriented product line. We have developed such a type system
on the basis of a formal model of a feature-oriented Java-like language. The type system
guaranties type safety for feature-oriented product lines. That is, it ensures that every valid
program of a well-typed product line is well-typed. Our formal model including type system
is sound and complete.  """).
            selected().
            crosscite("extended version of \\cite{AKL:GPCE08}").
            topic(vaanalysis, fop),

        Publication(
            Seq(Apel, Lengauer, Moeller, Kaestner),
            "An Algebraic Foundation for Automatic Feature-Based Program Synthesis",
            SCP(2010).volume(75).number(11).month(11).issn("0167-6423"),
            Pages(1022, 1047),
            Map(DOI -> URL("http://dx.doi.org/10.1016/j.scico.2010.02.001"),
                PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/publikationen/ps/auto/ALMK:SCP10.pdf")),
            """Feature-Oriented Software Development (FOSD) provides a multitude of formalisms, methods,
languages, and tools for building variable, customizable, and extensible software. Along
different lines of research, different notions of a feature have been developed. Although these
notions have similar goals, no common basis for evaluation, comparison, and integration
exists. We present a feature algebra that captures the key ideas of feature orientation and
provides a common ground for current and future research in this field, in which also alternative
options can be explored. Furthermore, our algebraic framework is meant to serve as a
basis for the upcoming development paradigms automatic feature-based program synthesis
and architectural metaprogramming.""").
            crosscite("extended version of \\cite{ALMK:AMAST08}").
            selected().
            topic(fop), //foundation


        Publication(
            Seq(Feigenspan, Kaestner, Frisch, Dachselt, Apel),
            "Visual Support for Understanding Product Lines",
            ICPC(2010).issn("1063-6897").isbn("978-1-4244-7604-6"),
            Pages(34, 35),
            Map(DOI -> URL("http://dx.doi.org/10.1109/ICPC.2010.15"),
                PDF -> URL("http://www.informatik.uni-marburg.de/~kaestner/icpc2010_demo.pdf")),
            """The C preprocessor is often used in practice to
implement variability in software product lines. Using \#ifdef
statements provokes problems such as obfuscated source code,
yet they will still be used in practice at least in the medium-term
future. With CIDE, we demonstrate a tool to improve understanding
and maintaining code that contains \#ifdef statements
by visualizing them with colors and providing different views on
the code.""").
            note("Demonstration paper").topic(vsoc),


        //TODO Publication(
        //Seq(Kaestner),
        //"Virtual Separation of Concerns: Toward Preprocessors 2.0",
        //school={University of Magdeburg},
        //.selected(),
        //month=May,
        //.publisher("Logos Verlag"),
        //publisheraddress={Berlin},
        //.isbn("978-3-8325-2527-9"),
        //.note("Logos Verlag Berlin, isbn 978-3-8325-2527-9"),
        //Map(DOI->URL("http://edoc.bibliothek.uni-halle.de/servlets/DocumentServlet?id=8044")),
        //Map(HTTP->URL("http://logos-verlag.de/cgi-bin/engbuchmid?isbn=2527&lng=deu&id=")),
        //Map(PDF->URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/publikationen/ps/10/diss_kaestner.pdf")),
        //.year(2010)
        //"""
        //Conditional compilation with preprocessors such as \emph{cpp} is a simple but effective means to implement variability.
        //By annotating code fragments with \emph{\#ifdef} and \emph{\#endif} directives, different program variants with or without these annotated fragments can be created, which can be used (among others) to implement software product lines. Although, such annotation-based approaches are frequently used in practice, researchers often criticize them for their negative effect on code quality and maintainability. In contrast to modularized implementations such as components or aspects, annotation-based implementations typically neglect separation of concerns, can entirely obfuscate the source code, and are prone to introduce subtle errors.
        //
        //Our goal is to rehabilitate annotation-based approaches by showing how tool support can address these problems. With views, we emulate modularity; with a visual representation of annotations, we reduce source code obfuscation and increase program comprehension; and with disciplined annotations and a product-line--aware type system, we prevent or detect syntax and type errors in the entire software product line. At the same time we emphasize unique benefits of annotations, including  simplicity, expressiveness, and being language independent.  All in all, we provide tool-based separation of concerns without necessarily dividing source code into physically separated modules; we name this approach \emph{virtual separation of concerns}.
        //
        //We argue that with these improvements over contemporary preprocessors, virtual separation of concerns can compete with modularized implementation mechanisms.
        //Despite our focus on annotation-based approaches, we do intend not give a definite answer on how to implement software product lines. Modular implementations and annotation-based implementations both have their advantages; we even present an integration and migration path between them.
        //Our goal is to rehabilitate preprocessors and show that they are not a lost cause as many researchers think. On the contrary, we argue that -- with the presented improvements -- annotation-based approaches are a serious alternative for product-line implementation.""")
        //}}


        Publication(
            Seq(SSchulze, Apel, Kaestner),
            "Code Clones in Feature-Oriented Software Product Lines",
            GPCE(2010).month(10).acceptanceRate(18, 59).location("Eindhoven, The Netherlands"),
            Pages(103, 112),
            Map(PDF -> URL("http://www.informatik.uni-marburg.de/~kaestner/GPCE10_clones.pdf"),
                ACMLink -> URL("http://dl.acm.org/authorize?379692")),
            """Some limitations of object-oriented mechanisms are known to
cause code clones (e.g., extension using inheritance). Novel programming
paradigms such as feature-oriented programming (FOP)
aim at alleviating these limitations. However, it is an open issue
whether FOP is really able to avoid code clones or whether it even
facilitates (FOP-specific) clones. To address this issue, we conduct
an empirical analysis on ten feature-oriented software product lines
with respect to code cloning. We found that there is a considerable
amount of clones in feature-oriented software product lines and
that a large fraction of these clones is FOP-specific (i.e., caused by
limitations of feature-oriented mechanisms). Based on our results,
we initiate a discussion on the reasons for FOP-specific clones and
on how to cope with them. We exemplary show how such clones
can be removed by the application of refactoring.""").topic(fop, empirical), //code clones

        Publication(
            Seq(Apel, Scholz, Lengauer, Kaestner),
            "Dependences and Interactions in Feature-Oriented Design",
            Conference("ISSRE", 2010, "21st IEEE International Symposium on Software Reliability Engineering").month(10).
                location("San Jose, CA").publisher(IEEE).acceptanceRate(40, 130),
            Pages(161, 170),
            Map(PDF -> URL("http://www.informatik.uni-marburg.de/~kaestner/ISSRE10.pdf")),
            """Feature-oriented software development (FOSD) aims
at the construction, customization, and synthesis of large-scale
software systems. We propose a novel software design paradigm,
called feature-oriented design, which takes the distinguishing
properties of FOSD into account, especially the clean and consistent
mapping between features and their implementations as well
as the tendency of features to interact inadvertently. We extend
the lightweight modeling language Alloy with support for feature-oriented
design and call the extension FeatureAlloy. By means of
an implementation and four case studies, we demonstrate how
feature-oriented design with FeatureAlloy facilitates separation
of concerns, variability, and reuse of models of individual features
and helps in defining and detecting semantic dependences and
interactions between features.  """).topic(vaanalysis, fop, interactions),

        Publication(
            Seq(Apel, Kolesnikov, Liebig, Kaestner, Kuhlemann, Leich),
            "Access Control in Feature-Oriented Programming",
            SCP(2012).subtitle("Special Issue on Feature-Oriented Software Development").month(3).volume(77).number(3),
            Pages(174, 187),
            Map(HTTP -> URL("http://www.informatik.uni-marburg.de/~kaestner/SCP_FOSD2010.pdf"),
                DOI -> URL("http://dx.doi.org/10.1016/j.scico.2010.07.005")),
            """In feature-oriented programming (FOP) a programmer decomposes a program in
terms of features. Ideally, features are implemented modularly so that they can be
developed in isolation. Access control is an important ingredient to attain feature
modularity as it provides mechanisms to hide and expose internal details of a module's
implementation. But developers of contemporary feature-oriented languages
have not considered access control mechanisms so far. The absence of a well-defined
access control model for FOP breaks encapsulation of feature code and leads to unexpected
program behaviors and inadvertent type errors. We raise awareness of this
problem, propose three feature-oriented access modifiers, and present a corresponding
access modifier model. We offer an implementation of the model on the basis of
a fully-fledged feature-oriented compiler. Finally, by analyzing ten feature-oriented
programs, we explore the potential of feature-oriented modifiers in FOP.""").
            topic(fop, vaanalysis),


        Publication(
            Seq(Apel, Scholz, Lengauer, Kaestner),
            "Language-Independent Reference Checking in Software Product Lines",
            FOSD10,
            Pages(64, 71),
            Map(HTTP -> URL("http://www.informatik.uni-marburg.de/~kaestner/FOSD10-ftweezer.pdf"),
                ACMLink -> URL("http://dl.acm.org/authorize?315789")),
            """
\emph{Feature-Oriented Software Development} (FOSD) is a paradigm
for the development of software product lines. A challenge
in FOSD is to guarantee that all software systems of
a software product line are correct. Recent work on type
checking product lines can provide a guarantee of type correctness
without generating all possible systems. We generalize
previous results by abstracting from the specifics of
particular programming languages. In a first attempt, we
present a reference-checking algorithm that performs key
tasks of product-line type checking independently of the target
programming language. Experiments with two sample
product lines written in Java and C are encouraging and
give us confidence that this approach is promising. """).
            topic(vaanalysis, fop),

        Publication(
            Seq(Kenner, Kaestner, Haase, Leich),
            "TypeChef: Toward Type Checking #ifdef Variability in C",
            FOSD10,
            Pages(25, 32),
            Map(HTTP -> URL("http://www.informatik.uni-marburg.de/~kaestner/FOSD10-typechef.pdf"),
                ACMLink -> URL("http://dl.acm.org/authorize?315774")),
            """Software product lines have gained momentum as an approach to
generate many variants of a program, each tailored to a specific
use case, from a common code base. However, the implementation
of product lines raises new challenges, as potentially millions of
program variants are developed in parallel. In prior work, we and
others have developed product-line--aware type systems to detect
type errors in a product line, without generating all variants. With
\emph{TypeChef}, we build a similar type checker for product lines written
in C that implements variability with \emph{\#ifdef} directives of the
C preprocessor. However, a product-line--aware type system for C
is more difficult than expected due to several peculiarities of the
preprocessor, including lexical macros and unrestricted use of \emph{\#ifdef}
directives. In this paper, we describe the problems faced and our
progress to solve them with \emph{TypeChef}. Although \emph{TypeChef} is still
under development and cannot yet process arbitrary C code, we
demonstrate its capabilities so far with a case study: By type checking
the open-source web server \emph{Boa} with potentially $2^{110}$ variants,
we found type errors in several variants.""").
            topic(vaanalysis, vsoc).crosscite("extended by \\cite{KGREOB:OOPSLA11}"),


        //TODO Publication (
        //editor = {
        //Apel and Batory and Krzysztof Czarnecki and Florian Heidenreich and Kaestner and Oscar Nierstrasz
        //},
        //"Proceedings of the Second International Workshop on Feature-Oriented Software Development {(FOSD)}, October 10, 2010, Eindhoven, The Netherlands",
        //publisher = ACM, address = ACMAddr,
        //.location ("Eindhoven, The Netherlands"),
        //.isbn ("978-1-4503-0208-1"),
        //month = oct, year = 2010,
        //Map (PDF -> URL ("http://www.infosun.fim.uni-passau.de/spl/apel/FOSD2010/FOSD2010proceedings.pdf") ),
        //http = {
        //http :// portal.acm.org / citation.cfm ? id = 1868688
        //}
        //}


        Publication(
            Seq(Kaestner, Giarrusso, Ostermann),
            "Partial Preprocessing C Code for Variability Analysis",
            VAMOS(2011).month(1).location("Namur, Belgium").isbn("978-1-4503-0570-9").publisher(ACM).acceptanceRate(21, 38),
            Pages(137, 140),
            Map(ACMLink -> URL("http://dl.acm.org/authorize?468359"),
                HTTP -> URL("http://www.informatik.uni-marburg.de/~kaestner/vamos11.pdf")),
            """
The C preprocessor is commonly used to implement
variability. Given a feature selection, code fragments
can be excluded from compilation with \#ifdef and similar
directives. However, the token-based nature of the C preprocessor
makes variability implementation difficult and errorprone.
Additionally, variability mechanisms are intertwined
with macro definitions, macro expansion, and file inclusion.
To determine whether a code fragment is compiled, the entire
file must be preprocessed. We present a partial preprocessor
that preprocesses file inclusion and macro expansion, but
retains variability information for further analysis.We describe
the mechanisms of the partial preprocessor, provide a full
implementation, and present some initial experimental results.
The partial preprocessor is part of a larger endeavor in
the TypeChef project to check variability implementations
(syntactic correctness, type correctness) in C projects such as
the Linux kernel.   """).topic(vaanalysis, vsoc, typechef),


        Publication(
            Seq(Liebig, Kaestner, Apel),
            "Analyzing the Discipline of Preprocessor Annotations in 30 Million Lines of C Code",
            Conference("AOSD", 2011, "10th ACM International Conference on Aspect-Oriented Software Development").publisher(ACM).
                month(3).acceptanceRate(21, 92),
            Pages(191, 202),
            Map(HTTP -> URL("http://www.informatik.uni-marburg.de/~kaestner/AOSD11.pdf"),
                ACMLink -> URL("http://dl.acm.org/authorize?473237")),
            """The C preprocessor cpp is a widely used tool for implementing
variable software. It enables programmers to express
variable code of features that may crosscut the entire implementation
with conditional compilation. The C preprocessor
relies on simple text processing and is independent of the host
language (C, C++, Java, and so on). Language independent
text processing is powerful and expressive|programmers can
make all kinds of annotations in the form of \#ifdefs but
can render unpreprocessed code difficult to process automatically
by tools, such as code aspect refactoring, concern
management, and also static analysis and variability-aware
type checking. We distinguish between disciplined annotations,
which align with the underlying source-code structure,
and undisciplined annotations, which do not align with the
structure and hence complicate tool development. This distinction
raises the question of how frequently programmers
use undisciplined annotations and whether it is feasible to
change them to disciplined annotations to simplify tool development
and to enable programmers to use a wide variety of
tools in the first place. By means of an analysis of 40 mediumsized
to large-sized C programs, we show empirically that
programmers use cpp mostly in a disciplined way: about 85\,\%
of all annotations respect the underlying source-code structure.
Furthermore, we analyze the remaining undisciplined
annotations, identify patterns, and discuss how to transform
them into a disciplined form.""").
            topic(vsoc, empirical, adoption).selected(),


        Publication(
            Seq(Kaestner, Apel, Thuem, Saake),
            "Type Checking Annotation-Based Product Lines",
            TOSEM(2012).volume(21).number(3),
            ToAppear(),
            Map(HTTP -> URL("http://www.informatik.uni-marburg.de/~kaestner/tosem11.pdf"),
                EPUB -> URL("http://www.informatik.uni-marburg.de/~kaestner/tosem11_ereader.pdf")),
            """
Software-product-line engineering is an efficient means to generate a family of program variants for a domain
from a single code base. However, because of the potentially high number of possible program variants, it is
difficult to test them all and ensure properties like type safety for the entire product line. We present a
product-line--aware type system that can type check an entire software product line without generating each variant in
isolation. Specifically, we extend the Featherweight Java calculus with feature annotations for product-line development
and prove formally that all program variants generated from a well-typed product line are well-typed.
Furthermore, we present a solution to the problem of typing mutually exclusive features. We discuss how results
from our formalization helped implementing our own product-line tool CIDE for full Java and report of
experience with detecting type errors in four existing software-product-line implementations.""").
            topic(vaanalysis, vsoc).selected().
            note("to appear; submitted 8 Jun 2010, accepted 4 Jan 2011").
            crosscite("extended version of \\cite{KA:ASE08}"),

        Publication(
            Seq(Pukall, Grebhahn, Schroeter, Kaestner, Cazzola, Goetz),
            "{JavaAdaptor}: Unrestricted Dynamic Software Updates for {Java}",
            ICSE11Demo,
            Pages(989, 991),
            Map(HTTP -> URL("http://www.informatik.uni-marburg.de/~kaestner/icse2011_demo_ja.pdf"),
                ACMLink -> URL("http://dl.acm.org/authorize?414157"),
                DOI -> URL("http://doi.acm.org/10.1145/1985793.1985970")),
            "").hideabstract().topic(dsu),


        Publication(
            Seq(Stengel, Feigenspan, Frisch, Kaestner, Apel, Dachselt),
            "{View Infinity}: A Zoomable Interface for Feature-Oriented Software Development",
            ICSE11Demo,
            Pages(1031, 1033),
            Map(HTTP -> URL("http://www.informatik.uni-marburg.de/~kaestner/icse2011_demo_vi.pdf"),
                ACMLink -> URL("http://dl.acm.org/authorize?414168"),
                DOI -> URL("http://doi.acm.org/10.1145/1985793.1985987")),
            "").hideabstract().topic(vsoc),

        Publication(
            Seq(Feigenspan, MSchulze, Papendieck, Kaestner, Dachselt, Koeppen, Frisch),
            "Using Background Colors to Support Program Comprehension in Software Product Lines",
            EASE(2011).publisher(Publisher("Institution of Engineering and Technology", "")).acceptanceRate(20, 50),
            Pages(66, 75),
            Map(HTTP -> URL("http://www.informatik.uni-marburg.de/~kaestner/ease2011.pdf")),
            """
Background: Software product line engineering provides
an effective mechanism to implement variable software.
However, the usage of preprocessors, which is typical in industry,
is heavily criticized, because it often leads to obfuscated code.
Using background colors to support comprehensibility has shown
effective, however, scalability to large software product lines
(SPLs) is questionable.Aim: Our goal is to implement and
evaluate scalable usage of background colors for industrial - sized
SPLs.Method: We designed and implemented scalable concepts
in a tool called FeatureCommander.To evaluate its effectiveness,
we conducted a controlled experiment with a large real - world
SPL with over 160, 000 lines of code and 340 features.We
used a within - subjects design with treatments colors and no
colors.We compared correctness and response time of tasks for
both treatments.Results: For certain kinds of tasks, background
colors improve program comprehension.Furthermore, subjects
generally favor background colors.Conclusion: We show that
background colors can improve program comprehension in large
SPLs.Based on these encouraging results, we will continue our
work improving program comprehension in large SPLs. """).
            topic(experiment, vsoc).crosscite("superseeded by \\cite{FSPKDKFS:IET12} and \\cite{FKALSDPLS:ESE12}"),

        Publication(
            Seq(Ostermann, Giarrusso, Kaestner, Rendel),
            "Revisiting Information Hiding: Reflections on Classical and Nonclassical Modularity",
            ECOOP(2011).acceptanceRate(26, 100).series(LNCS).volume(6813),
            Pages(155, 178),
            Map(DOI -> URL("http://dx.doi.org/10.1007/978-3-642-22655-7_8"),
                HTTP -> URL("http://www.informatik.uni-marburg.de/~kaestner/ecoop11.pdf"),
                DOI -> URL("http://www.informatik.uni-marburg.de/~kaestner/ecoop11_ereader.pdf")),
            """
What is modularity? Which kind of modularity should developers strive for?
Despite decades of research on modularity, these basic questions have
no definite answer. We submit that the common understanding of
modularity, and in particular its notion of information hiding, is
deeply rooted in classical logic.
We analyze how classical modularity, based on classical logic, fails to
address the needs of developers of large software systems, and
encourage researchers to explore alternative visions of
modularity, based on nonclassical logics, and henceforth called
nonclassical
modularity.""").
            selected().topic(modularity),


        Publication(
            Seq(Apel, Heidenreich, Kaestner, Rosenmueller),
            "Third International Workshop on Feature-Oriented Software Development {(FOSD 2011)}",
            SPLC11,
            Pages(337, 338),
            Map(PDF -> URL("http://www.infosun.fim.uni-passau.de/cl/publications/docs/SPLC2011fosd.pdf"),
                HTTP -> URL("http://fosd.net/2011")),
            ""
        ),


        Publication(
            Seq(Siegmund, Rosenmueller, Kaestner, Giarrusso, Apel, Kolesnikov),
            "Scalable Prediction of Non-functional Properties in Software Product Lines",
            SPLC11.acceptanceRate(20, 69),
            Pages(160, 169),
            Map(HTTP -> URL("http://www.informatik.uni-marburg.de/~kaestner/SPLC11_nfp.pdf")),
            """A software product line (SPL) is a family of related software products, from which users can derive a product that fulfills their needs.
Often, users expect a product to have specific non-functional properties, for example, to not exceed a footprint limit or to respond in a given time frame. Unfortunately, it is usually not feasible to generate and measure non-functional properties for each possible product of an SPL in isolation, because an SPL can contain millions of products. Hence, we propose an approach to \emph{estimate} each product's non-functional properties in advance, based on the product's configuration. To this end, we approximate non-functional properties \emph{per features} and per feature interaction. We generate and measure a small set of products and approximated non-functional properties by comparing the measurements. Our approach is implementation independent and language independent. We present three different approaches with different trade-offs regarding accuracy and required number of measurements. With nine case studies, we demonstrate that our approach can predict non-functional properties with an accuracy of 2\%.""").
            note("\\textbf{Best Paper Award}").topic(nfp, spl, empirical),


        Publication(
            Seq(Thuem, Kaestner, Erdweg, Siegmund),
            "Abstract Features in Feature Modeling",
            SPLC11.acceptanceRate(20, 69),
            Pages(191, 200),
            Map(HTTP -> URL("http://www.informatik.uni-marburg.de/~kaestner/SPLC11_af.pdf")),
            """
A software product line is a set of program variants, typically generated from a common code base. Feature models describe variability in product lines by documenting features and their valid combinations. In product-line engineering, we need to reason about variability and program variants for many different tasks. For example, given a feature model, we might want to determine the number of all valid feature combinations or detect specific feature combinations for testing.
However, we found that contemporary reasoning approaches can only reason about feature combinations, not about program variants, because they do not take abstract features into account. Abstract features are features used to structure a feature model that, however, do not have any impact at implementation level. Using existing feature-model reasoning mechanisms for product variants leads to incorrect results.
We raise awareness of the problem of abstract features for different kinds of analyses on feature models. We argue that, in order to reason about program variants, abstract features should be made explicit in feature models. We present a technique based on propositional formulas to reason about program variants. In practice, our technique can save effort that is caused by considering the same program variant multiple times, for example, in product-line testing.""").
            topic(spl, fmanalysis),

        Publication(
            Seq(Feigenspan, Apel, Liebig, Kaestner),
            "Exploring Software Measures to Assess Program Comprehension",
            ESEM(2011).location("Banff").month(9).acceptanceRate(33, 105),
            Pages(1, 10, "paper 3"),
            Map(PDF -> URL("http://wwwiti.cs.uni-magdeburg.de/iti_db/publikationen/ps/auto/FALK11.pdf")),
            """Software measures are often used to assess program comprehension, although their applicability is discussed controversially. Often, their application is based on plausibility arguments, which however is not sufficient to decide whether and how software measures are good predictors for program comprehension. Our goal is to evaluate whether and how software measures and program comprehension correlate. To this end, we carefully designed an experiment. We used four different measures that are often used to judge the quality of source code: complexity, lines of code, concern attributes, and concern operations. We measured how subjects understood two comparable software systems that differ in their implementation, such that one implementation promised considerable benefits in terms of better software measures. We did not observe a difference in program comprehension of our subjects as the software measures suggested it. To explore how software measures and program comprehension could correlate, we used several variants of computing the software measures. This brought them closer to our observed result, however, not as close as to confirm a relationship between software measures and program comprehension. Having failed to establish a relationship, we present our findings as an open issue to the community and initiate a discussion on the role of software measures as comprehensibility predictors.""").
            topic(experiment, empirical, programcomprehension),

        //TODO Publication (
        //Seq (Kaestner),
        //"Virtuelle Trennung von Belangen",
        //Book("Ausgezeichnete Informatikdissertationen 2010",2011,GI).series(LNI).volume("D-11").isbn("9783885794158"),
        //Pages(121, 130),
        //    Map(PDF->URL("http://www.informatik.uni-marburg.de/~kaestner/gi11_kurz.pdf")),
        //"""
        //Bedingte Kompilierung ist ein einfaches und h\"aufig benutztes Mittel zur
        //Implementierung von Variabilit\"at in Softwareproduktlinien, welches aber aufgrund
        //negativer Auswirkungen auf Codequalit\"at und Wartbarkeit stark kritisiert wird. Wir
        //zeigen wie Werkzeugunterst\"utzung -- Sichten, Visualisierung, kontrollierte Annotationen,
        //Produktlinien-Typsystem -- die wesentlichen Probleme beheben kann und viele
        //Vorteile einer modularen Entwicklung emuliert. Wir bieten damit eine Alternative zur
        //klassischen Trennung von Belangen mittels Modulen. Statt Quelltext notwendigerweise
        //in Dateien zu separieren erzielen wir eine virtuelle Trennung von Belangen durch
        //entsprechender Werkzeugunterst\"uzung. """).
        //note("invited paper").
        //crosscite("(German summary of \\cite{kaestnerDiss})"),


        Publication(
            Seq(Khan, Kaestner, Koeppen, Saake),
            "Service Variability Patterns",
            Workshop("Variability@ER", 2011, "ER Workshop on Software Variability Management").publisher(Springer).
                series(LNCS).volume(6999),
            Pages(130, 140),
            Map(HTTP -> URL("http://www.springerlink.com/content/g22235r0561200m3/")),
            "").hideabstract(),

        Publication(
            Seq(Siegmund, Rosenmueller, Kuhlemann, Kaestner, Apel, Saake),
            "SPL Conqueror: Toward Optimization of Non-functional Properties in Software Product Lines",
            Journal("SQJ", 2011, "Software Quality Journal").
                subtitle("Special issue on Quality Engineering for Software Product Lines"),
            ToAppear(),
            Map(PDF -> URL("http://www.informatik.uni-marburg.de/~kaestner/jsoftwarequality11.pdf"),
                HTTP -> URL("http://www.springerlink.com/content/ax788q46h1702j34/"),
                DOI -> URL("10.1007/s11219-011-9152-9")),
            """A software product line (SPL) is a family of related programs of a domain.
The programs of an SPL are distinguished in terms of features, which are end-uservisible
characteristics of programs. Based on a selection of features, stakeholders can
derive tailor-made programs that satisfy functional requirements. Besides functional requirements,
different application scenarios raise the need for optimizing non-functional
properties of a variant. The diversity of application scenarios leads to heterogeneous
optimization goals with respect to non-functional properties (e.g., performance vs.
footprint vs. energy optimized variants). Hence, an SPL has to satisfy different and
sometimes contradicting requirements regarding non-functional properties. Usually, the
actually required non-functional properties are not known before product derivation
and can vary for each application scenario and customer. Allowing stakeholders to derive
optimized variants requires to measure non-functional properties after the SPL is
developed. Unfortunately, the high variability provided by SPLs complicates measurement
and optimization of non-functional properties due to a large variant space.
With SPL Conqueror, we provide a holistic approach to optimize non-functional
properties in SPL engineering. We show how non-functional properties can be qualitatively
specified and quantitatively measured in the context of SPLs. Furthermore, we
discuss the variant-derivation process in SPL Conqueror that reduces the effort of computing
an optimal variant. We demonstrate the applicability of our approach by means
of nine case studies of a broad range of application domains (e.g., database management
and operating systems). Moreover, we show that SPL Conqueror is implementation and
language independent by using SPLs that are implemented with different mechanisms,
such as conditional compilation and feature-oriented programming.""").
            topic(nfp, spl).note("online first"),



        /*
Publication (
Seq (Apel, J {
\ "o}rg Liebig, Benjamin Brandl, Lengauer, Kaestner),
"Semistructured Merge: Rethinking Merge in Revision Control Systems",
booktitle = {
Proceedings of the European Software Engineering Conference and the ACM SIGSOFT Symposium on the Foundations of Software Engineering (ESEC / FSE)
},
.year (2011) month = sep,
.location ("Szeged, Hungary"),
publisher = ACM, address = ACMAddr,
Pages(190, 200),
"""
An ongoing problem in revision control systems is how to resolve
conflicts in a merge of independently developed revisions. Unstructured
revision control systems are purely text-based and solve
conflicts based on textual similarity. Structured revision control
systems are tailored to specific languages and use language-specific
knowledge for conflict resolution. We propose semistructured revision
control systems that inherit the strengths of both classes of
systems: generality and expressiveness. The idea is to provide structural
information of the underlying software artifacts---declaratively,
in the form of annotated grammars. This way, a wide variety of languages
can be supported and the information provided can assist the
automatic resolution of two classes of conflicts: ordering conflicts
and semantic conflicts. The former can be resolved independently
of the language and the latter can be resolved using specific conflict
handlers supplied by the user. We have been developing a tool that
supports semistructured merge and conducted an empirical study on
24 software projects developed in Java, C\#, and Python comprising
180 merge scenarios. We found that semistructured merge reduces
the number of conflicts in 60\,\% of the sample merge scenarios by,
on average, 34\,\%. Our study reveals that renaming is challenging
in that it can significantly increase the number of conflicts during
semistructured merge, which we discuss.     """)
}	,
.note("Acceptance rate: 17\,\% (34/203)"),
Map(HTTP->URL("http://www.informatik.uni-marburg.de/~kaestner/esec11.pdf")),
}



Publication(
Seq(Erdweg, Rendel, Kaestner, Ostermann),
"SugarJ: Library-based Syntactic Language Extensibility",
booktitle={Proceedings of the 26th Annual ACM SIGPLAN Conference on Object-Oriented Programming, Systems, Languages, and Applications (OOPSLA)},
.note("Acceptance rate: 37\,\% (61/166); \textbf{Distinguished Paper Award}"),
.isbn("978-1-4503-0940-0"),
.location("Portland, OR"),
Pages(391,406),
publisher=ACM,address=ACMAddr,
month=oct,year=2011,
"""
Existing approaches to extend a programming language with
syntactic sugar often leave a bitter taste, because they cannot
be used with the same ease as the main extension mechanism
of the programming language --- libraries.Sugar libraries are
a novel approach for syntactically extending a programming
language within the language.A sugar library is like an ordinary
library, but can, in addition, export syntactic sugar
for using the library.Sugar libraries maintain the composability
and scoping properties of ordinary libraries and are
hence particularly well - suited for embedding a multitude of
domain - specific languages into a host language.They also
inherit the self - applicability of libraries, which means that
the syntax extension mechanism can be applied in the definition
of sugar libraries themselves.

To demonstrate the expressiveness and applicability of
sugar libraries, we have developed SugarJ, a language on
top of Java, SDF and Stratego that supports syntactic extensibility.
SugarJ employs a novel incremental parsing mechanism
that allows changing the syntax within a source file.We
demonstrate SugarJ by five language extensions, including
embeddings of XML and closures in Java, all available as
sugar libraries.We illustrate the utility of self - applicability
by embedding XML Schema, a metalanguage to define
XML languages.                                """)
},
Map (PDF -> URL ("http://www.informatik.uni-marburg.de/~seba/publications/sugarj.pdf") ),
Map (DOI -> URL ("http://doi.acm.org/10.1145/2048066.2048099") ),
}


Publication (
Seq (Erdweg, Sebastian, Kats, Lennart C.L., Rendel, Tillmann, K \ "{a}stner, Christian, Ostermann, Klaus, Visser, Eelco),
"Library-Based Model-Driven Software Development with {SugarJ}",
booktitle = {
Companion of the 26 th Annual ACM SIGPLAN Conference on Object - Oriented Programming, Systems, Languages, and Applications (OOPSLA)
},
.year (2011)
.isbn ("978-1-4503-0942-4"),
.location ("Portland, OR"),
Pages(17, 18),
numpages = {
2
},
Map (DOI -> URL ("http://doi.acm.org/10.1145/2048147.2048156") ),
publisher = ACM, address = ACMAddr,
.note ("Demonstration paper"),
.crosscite ("demonstration accompying \cite{ERKO:OOPSLA11} and \cite{ELRLPV:GPCE11}")
}

Publication (
Seq (Erdweg, Sebastian, Kats, Lennart C.L., Rendel, Tillmann, K \ "{a}stner, Christian, Ostermann, Klaus, Visser, Eelco),
"{SugarJ}: Library-Based Language Extensibility",
booktitle = {
Companion of the 26 th Annual ACM SIGPLAN Conference on Object - Oriented Programming, Systems, Languages, and Applications (OOPSLA)
},
.year (2011)
.isbn ("978-1-4503-0942-4"),
.location ("Portland, OR"),
Pages(187, 188),
numpages = {
2
},
Map (DOI -> URL ("http://doi.acm.org/10.1145/2048147.2048199") ),
publisher = ACM, address = ACMAddr,
.note ("Poster"),
.crosscite ("poster accompying \cite{ERKO:OOPSLA11} and \cite{ELRLPV:GPCE11}")
}




Publication (
Seq (Kaestner, Giarrusso, Rendel, Erdweg, Ostermann, Thorsten Berger),
"Variability-Aware Parsing in the Presence of Lexical Macros and Conditional Compilation",
booktitle = {
Proceedings of the 26 th Annual ACM SIGPLAN Conference on Object - Oriented Programming, Systems, Languages, and Applications (OOPSLA)
},
.note ("Acceptance rate: 37\,\% (61/166)"),
.location ("Portland, OR"),
Pages(805, 824),
Map (DOI -> URL ("http://doi.acm.org/10.1145/2048066.2048128") ),
.isbn ("978-1-4503-0940-0"),
publisher = ACM, address = ACMAddr,
month = oct, year = 2011,
.selected (),
"""
In many projects, lexical preprocessors are used to manage
different variants of the project (using conditional compilation)
and to define compile-time code transformations (using
macros). Unfortunately, while being a simply way to implement
variability, conditional compilation and lexical macros
hinder automatic analysis, even though such analysis would
be urgently needed to combat variability-induced complexity.
To analyze code with its variability, we need to parse
it without preprocessing it. However, current parsing solutions
use heuristics, support only a subset of the language, or
suffer from exponential explosion. As part of the TypeChef
project, we contribute a novel variability-aware parser that
can parse unpreprocessed code without heuristics in practicable
time. Beyond the obvious task of detecting syntax errors,
our parser paves the road for further analysis, such as
variability-aware type checking. We implement variabilityaware
parsers for Java and GNU C and demonstrate practicability
by parsing the product line MobileMedia and the
entire X86 architecture of the Linux kernel with 6065 variable
features.                          """)
},
Map(PDF->URL("http://www.informatik.uni-marburg.de/~kaestner/oopsla11_typechef.pdf"))
}

Publication(
"{FeatureCommander}: Colorful \#ifdef World",
Seq(Feigenspan, Papendieck, Kaestner, Frisch, Dachselt),
booktitle={Proceedings of the 15th International Software Product Line Conference (SPLC), second volume (Demonstration)},
publisher=ACM,address=ACMAddr,
.location("Munich"),
.year(2011)month=sep,
Map(HTTP->URL("http://www.informatik.uni-marburg.de/~kaestner/SPLC11_demo.pdf")),
Map(DOI->URL("http://doi.acm.org/10.1145/2019136.2019192")),
Pages(48:1,48:2),
.isbn("978-1-4503-0789-5"),
}



Publication(
"The Road to Feature Modularity?",
Seq(Kaestner, Apel, Ostermann),
booktitle={Proceedings of the Third Workshop on Feature-Oriented Software Development (FOSD)},
.year(2011)month=sep,
.location("Munich"),
publisher=ACM,address=ACMAddr,
Pages(5:1,5:8),
.isbn("978-1-4503-0789-5"),
Map(DOI->URL("http://doi.acm.org/10.1145/2019136.2019142")),
.selected(),
"""Modularity of feature representations has been a long standing
goal of feature-oriented software development. While
some researchers regard feature modules and corresponding
composition mechanisms as a modular solution, other researchers
have challenged the notion of feature modularity
and pointed out that most feature-oriented implementation
mechanisms lack proper interfaces and support neither modular
type checking nor separate compilation. We step back
and reflect on the feature-modularity discussion. We distinguish
two notions of modularity, \emph{cohesion} without interfaces
and \emph{information hiding} with interfaces, and point out the
different expectations that, we believe, are the root of many
heated discussions. We discuss whether feature interfaces
should be desired and weigh their potential benefits and
costs, specifically regarding crosscutting, granularity, feature
interactions, and the distinction between closed-world and
open-world reasoning. Because existing evidence for and
against feature modularity and feature interfaces is shaky
and inconclusive, more research is needed, for which we
outline possible directions.  """)
},
Map (PDF -> URL ("http://www.informatik.uni-marburg.de/~kaestner/FOSD11-modularity.pdf") ),
}

Publication (
author = {
Erdweg and Lennart C.L.Kats and Rendel and
Kaestner and Ostermann and Eelco Visser
},
"Growing a Language Environment with Editor Libraries",
booktitle = {
Proceedings of the 10 th International Conference on Generative Programming and Component Engineering (GPCE)
},
publisher = ACM, address = ACMAddr,
.location ("Portland, OR"),
Map (PDF -> URL ("http://www.informatik.uni-marburg.de/~kaestner/gpce11.pdf") ),
.note ("Acceptance rate: 31\,\% (18/58)"),
Pages(167, 176),
Map (DOI -> URL ("http://doi.acm.org/10.1145/2047862.2047891") ),
.isbn ("978-1-4503-0689-8"),
.year (2011) month = oct,
"""
Large software projects consist of code written in a multitude of different
(possibly domain-specific) languages, which are often deeply interspersed even
in single files. While many proposals exist on how to integrate languages
semantically and syntactically, the question of how to support this scenario in
integrated development environments (IDEs) remains open: How can standard IDE
services, such as syntax highlighting, outlining, or reference resolving, be
provided in an extensible and compositional way, such that an open mix of
languages is supported in a single file?

Based on our library-based syntactic extension language for Java, SugarJ, we
propose to make IDEs extensible by organizing editor services in editor
libraries. Editor libraries are libraries written in the object language,
SugarJ, and hence activated and composed through regular import statements on a
file-by-file basis.  We have implemented an IDE for editor libraries on top of
SugarJ and the Eclipse-based Spoofax language workbench.  We have validated
editor libraries by evolving this IDE into a fully-fledged and schema-aware XML
editor as well as an extensible Latex editor, which we used for writing this
paper.                """)
}
}

Publication(
Seq(Kuhlemann, Kaestner, Apel, Saake),
"An Algebra for Refactoring and Feature-Oriented Programming",
.institution("School of Computer Science, University of Magdeburg")
.addess("Germany"),
.year(2011)
.number({FIN-006-2011})
Map(HTTP->URL("http://www.cs.uni-magdeburg.de/fin_media/downloads/forschung/technical_reports_und_preprints/2011/TechReport06-p-2138.pdf"))
}



Publication(
Seq(Kaestner, Alexander Dreiling, Ostermann),
"Variability Mining with {LEADT}",
.year(2011)
institution = MRTech,
month=Sep,
.number({01/2011})
"""
Software product line engineering is an efficient means to generate a set of tailored software products from a common implementation.
However, adopting a product-line approach poses a major challenge and significant risks, since typically legacy code must be migrated toward a product line. Our aim is to lower the adoption barrier by providing semiautomatic tool support---called \emph{variability mining}---to support developers in locating, documenting, and extracting implementations of product-line features from legacy code. Variability mining combines prior work on concern location, reverse engineering, and variability-aware type systems, but is tailored specifically for the use in product lines. Our work extends prior work in three important aspects: (1) we provide a \emph{consistency indicator} based on a variability-aware type system, (2) we mine features at a \emph{fine level of granularity}, and (3) we exploit \emph{domain knowledge} about the relationship between features when available. With a quantitative study, we demonstrate that variability mining can efficiently support developers in locating features.""")
Map (PDF -> URL ("http://www.informatik.uni-marburg.de/~kaestner/tr_leadt2011.pdf") ),
http = {
http :// www.uni - marburg.de / fb12 / forschung / berichte / berichteinformtk
}
}


Publication (
Seq (Pukall, Kaestner, Cazzola, Sebastian G {
\ "o}tz, Alexander Grebhahn, Reimar Schr\"oter, Saake),
"{JavAdaptor}: Flexible Runtime Updates of {Java} Applications",
journal = {
Software: Practice and Experience
},
.year (2012)
.note ("online first"),
"""
Software is changed frequently during its life cycle. New requirements come and bugs must be fixed.
To update an application it usually must be stopped, patched, and restarted. This causes time periods of
unavailability which is always a problem for highly available applications. Even for the development of
complex applications restarts to test new program parts can be time consuming and annoying. Thus, we
aim at dynamic software updates to update programs at runtime. There is a large body of research on
dynamic software updates, but so far, existing approaches have shortcomings either in terms of flexibility or
performance. In addition, some of them depend on specific runtime environments and dictate the program’s
architecture. We present JavAdaptor, the first runtime update approach based on Java that (a) offers
flexible dynamic software updates, (b) is platform independent, (c) introduces only minimal performance
overhead, and (d) does not dictate the program architecture. JavAdaptor combines schema changing class
replacements by class renaming and caller updates with Java HotSwap using containers and proxies. It runs
on top of all major standard Java virtual machines.We evaluate our approach’s applicability and performance
in non-trivial case studies and compare it to existing dynamic software update approaches.      """)
},
Map(PDF->URL("http://www.informatik.uni-marburg.de/~kaestner/SPE12_JavAdaptor.pdf")),
http={http://onlinelibrary.wiley.com/doi/10.1002/spe.2107/abstract},
Map(DOI->URL("10.1002/spe.2107"))
}




Publication(
Seq(Apel, Kaestner, Lengauer),
"Language-Independent and Automated Software Composition: The {FeatureHouse} Experience",
journal={IEEE Transactions on Software Engineering (TSE)},
.year(2012)
.note("to appear; submitted 21 Oct 2010, accepted 29 Nov 2011"),
"""Superimposition is a composition technique that has been applied successfully in many areas of software development. Although superimposition is a general-purpose concept, it has been (re)invented and implemented individually for various kinds of software artifacts. We unify languages and tools that rely on superimposition by using the language-independent model of \emph{feature structure trees} (FSTs). On the basis of the FST model, we propose a general approach to the composition of software artifacts written in different languages. Furthermore, we offer a supporting framework and tool chain, called FeatureHouse. We use attribute grammars to automate the integration of additional languages. In particular, we have integrated Java, C\#, C, Haskell, Alloy, and JavaCC. A substantial number of case studies demonstrate the practicality and scalability of our approach and reveal insights into the properties that a language must have in order to be ready for superimposition. We discuss perspectives of our approach and demonstrate how we extended FeatureHouse with support for XML languages (in particular, XHTML, XMI/UML, and Ant) and alternative composition approaches (in particular, aspect weaving). Rounding off our previous work, we provide here a holistic view of the FeatureHouse approach based on rich experience with numerous languages and case studies and reflections on several years of research.""")
Map (PDF -> URL ("http://www.informatik.uni-marburg.de/~kaestner/tse_fh.pdf") ),
http = {
http :// doi.ieeecomputersociety.org / 10.1109 / TSE .2011 .120
},
.selected ()
}

Publication (
Seq (Siegmund, Sergiy S.Kolesnikov, Kaestner, Apel, Batory, Marko Rosenm {
\ "u}ller, Saake),
"Predicting Performance via Automated Feature-Interaction Detection",
booktitle = {
Proceedings of the 34 th International Conference on Software Engineering (ICSE)
},
.year (2012)
.note ("to appear; submitted 29 Sep 2011, accepted 26 Jan 2012, acceptance rate 21\,\% (87/408)"),
"""
Customizable programs and program families
provide user-selectable features to tailor a program to an
application scenario. Knowing in advance which feature selection
yields the best performance is difficult because a
direct measurement of all possible feature combinations is
infeasible. Our work aims at predicting program performance
based on selected features. The challenge is predicting performance
accurately when features interact. An interaction
occurs when a feature combination has an unexpected influence
on performance. We present a method that automatically
detects performance feature interactions to improve prediction
accuracy. To this end, we propose three heuristics to reduce the
number of measurements required to detect interactions. Our
evaluation consists of six real-world case studies from varying
domains (e.g. databases, compression libraries, and web server)
using different configuration techniques (e.g., configuration files
and preprocessor flags). Results show, on average, a prediction
accuracy of 95\,\%. """)
},
Map(HTTP->URL("http://www.informatik.uni-marburg.de/~kaestner/icse12.pdf")),
.selected()
}


Publication(
Seq(Kaestner),
"Virtual Separation of Concerns: Toward Preprocessors 2.0",
journal={it--Information Technology},
.year(2012)
.volume(54)number=1,
Map(DOI->URL("http://www.oldenbourg-link.com/doi/abs/10.1524/itit.2012.0662")),
Map(HTTP->URL("http://it-information-technology.de/")),
Map(PDF->URL("http://www.informatik.uni-marburg.de/~kaestner/itit12.pdf")),
Pages(42,46),
.crosscite("invited summary of \cite{kaestnerDiss}")
}


Publication(
Seq(Feigenspan, Kaestner, J{\"o}rg Liebig, Apel, Stefan Hanenberg),
"Measuring Programming Experience",
booktitle = {Proceedings of the 20th International Conference on Program Comprehension (ICPC)},
.year(2012)
publisher=IEEE,address=IEEEAddr,
.note("accepted for publication; acceptance rate: 41\,\% (21/51)"),
"""
Programming experience is an important confounding
parameter in controlled experiments regarding program
comprehension.In literature, ways to measure or control programming
experience vary.Often, researchers neglect it or do
not specify how they controlled it.We set out to find a well - defined
understanding of programming experience and a way to measure
it.From published comprehension experiments, we extracted
questions that assess programming experience.In a controlled experiment,
we compare the answers of 128 students to these questions
with their performance in solving program - comprehension
tasks.We found that self estimation seems to be a reliable way
to measure programming experience.Furthermore, we applied
exploratory factor analysis to extract a model of programming
experience.With our analysis, we initiate a path toward measuring
programming experience with a valid and reliable tool,
so that we can control its influence on program comprehension.  """)
}
}





Publication (
Seq (Feigenspan, MSchulze, Papendieck, Kaestner, Dachselt, Veit K \ "oppen, Frisch, Saake),
"Supporting Program Comprehension in Large Preprocessor-Based Software Product Lines",
journal = {
IET Software
},
.year (2012)
.note ("to appear; accepted 11 Apr 2012"),
"""
Background: Software product line engineering provides an effective mechanism to implement variable software.
However, the usage of preprocessors to realize variability, which is typical in industry, is heavily criticized, because
it often leads to obfuscated code. Using background colours to highlight preprocessor statements to support
comprehensibility has shown effective, however, scalability to large software product lines (SPLs) is questionable.
Aim: Our goal is to implement and evaluate scalable usage of background colours for industrial-sized SPLs. Method:
We designed and implemented scalable concepts in a tool called FeatureCommander. To evaluate its effectiveness, we
conducted a controlled experiment with a large real-world SPL with over 99,000 lines of code and 340 features. We
used a within-subjects design with treatments colours and no colours. We compared correctness and response time of
tasks for both treatments. Results: For certain kinds of tasks, background colours improve program comprehension.
Furthermore, subjects generally favour background colours compared to no background colours. Additionally, subjects
who worked with background colours had to use the search functions less frequently. Conclusion: We show that
background colours can improve program comprehension in large SPLs. Based on these encouraging results, we will
continue our work on improving program comprehension in large SPLs.""")
},
.crosscite("extended version of \cite{FSPKDKF:EASE11}, see also \cite{FKALSDPLS:ESE12}")
}

        */


        Publication(
            Seq(Feigenspan, Kaestner, Apel, Liebig, MSchulze, Dachselt, Papendieck, Leich, Saake),
            "Do Background Colors Improve Program Comprehension in the #ifdef Hell?",
            ESE(2012),
            null,
            Map(PDF -> URL("http://www.informatik.uni-marburg.de/~kaestner/ese12.pdf"),
                HTTP -> URL("http://www.springerlink.com/content/w537p64015xp367v/")
            ),
            """Software-product-line engineering aims at the development of variable
        and reusable software systems. In practice, software product lines are
        often implemented with preprocessors. Preprocessor directives are easy to use,
        and many mature tools are available for practitioners. However, preprocessor
        directives have been heavily criticized in academia and even referred to
        as ``\#ifdef hell'', because they introduce threats to program comprehension
        and correctness. There are many voices that suggest to use other implementation
        techniques instead, but these voices ignore the fact that a transition
        from preprocessors to other languages and tools is tedious, erroneous, and expensive
        in practice. Instead, we and others propose to increase the readability
        of preprocessor directives by using background colors to highlight source code
        annotated with ifdef directives. In three controlled experiments with over 70
        subjects in total, we evaluate whether and how background colors improve
        program comprehension in preprocessor-based implementations. Our results
        demonstrate that background colors have the potential to improve program
        comprehension, independently of size and programming language of the underlying
        product. Additionally, we found that subjects generally favor background
        colors. We integrate these and other findings in a tool called FeatureCommander,
        which facilitates program comprehension in practice and which can serve
        as a basis for further research.""").
            note("online first").selected().topic(experiment, vsoc)


    )
}