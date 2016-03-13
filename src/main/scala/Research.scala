package de.stner.cv

import java.io.File

import de.stner.cv.GenPubList.TextFormater

import scala.xml.{Elem, NodeSeq, Text}


object ResearchStructure {

    case class Theme(title: String, shortdescription: String, description: NodeSeq, topics: List[ResearchTopic]) {
        def key =  stringToKey(title)
    }

    case class ResearchTopic(title: String, description: NodeSeq, pictureFile: Option[String] = None, insights: List[Elem] = Nil, tools: List[Software] = Nil, collaborators: List[Person] = Nil) {
        def key =  stringToKey(title)
    }


    case class Software(title: String, onelineDescription: String, url: URL)

    private def stringToKey(s: String) = s.split("\\s").map(_.replaceAll("[^\\p{L}\\p{Nd}]+","")).filter(_.length>3).take(4).map(_.capitalize).mkString

}

object ResearchGenHtml extends App {

    import GenHtml._
    import Research.themes
    import ResearchStructure._


    def researchPage = printTitle() ++
        printResearchOverview(themes) ++
        printResearch(themes)

    def printResearchOverview(themes: List[Theme]): NodeSeq = rowH2("Research Overview") ++ themes.map(printThemeShort).flatten

    def printThemeShort(theme: Theme): NodeSeq =
        row(null,
            <div class="researchthemesummary">
                <h3><a name={theme.key}></a>{theme.title}</h3>
                <p class="researchthemedesc">{theme.shortdescription} <a href={"research.html#"+theme.key}>show details...</a></p>
                <p class="researchthemetopics">Topics: {theme.topics.map(printTopicLink).reduce(_ ++ Text(" · ") ++ _)}</p>
            </div>
        )

    def printTopicLink(topic: ResearchTopic): NodeSeq =
        <a href={"research.html#"+topic.key}>{topic.title}</a>


    def printResearch(themes: List[Theme]): NodeSeq = themes.map(printThemeDetailed).flatten

    def printThemeDetailed(theme: Theme): NodeSeq =
        rowH2(theme.title, theme.description) ++
            theme.topics.map(printTopicLong).flatten

    def printTopicLong(topic: ResearchTopic): NodeSeq =
        row(topic.pictureFile.map(p => <img src={p} alt="" />).getOrElse(null),
            <div class="researchtopic">
                <h3><a name={topic.key}></a>{topic.title}</h3>
                {topic.description}
            </div>
        )

    val targetPath = new File("target/site")
    printDoc(researchPage, "Research Overview :: " + CV.name + " :: CMU", new File(targetPath, "research.html"))


}


object Research {

    import CVPublications._
    import ResearchStructure._

    def themes = List(qualityAssurance,
        Theme("Working with Imperfect Modularity", "", <p></p>, List(
            ResearchTopic("Evolution in Software Ecosystems; Module Systems and Awareness", <p></p>),
            ResearchTopic("Distributed and Decentralized Software Development", <p></p>),
            ResearchTopic("Virtual Separation of Concerns", <p></p>),
            ResearchTopic("Conceptual Discussions", <p></p>)
        )),
        Theme("Maintenance and Implementation of Highly-Configurable Systems", "", <p></p>, List(
            ResearchTopic("Reverse Engineering Variability Implementations", <p></p>),
            ResearchTopic("Feature-Oriented Programming (FeatureHouse, FeatureIDE), Tool Support", <p></p>),
            ResearchTopic("Assessing and Understanding Configuration-Related Complexity", <p></p>),
            ResearchTopic("Understanding Preprocessor Use", <p></p>),
            ResearchTopic("Tracking Load-Time Configuration Options, Slicing", <p></p>),
            ResearchTopic("Feature Interactions, Isolation", <p></p>),
            ResearchTopic("Module Systems for Variability and Evolution", <p></p>),
            ResearchTopic("Build Systems", <p></p>)
        )),
        Theme("Variability Mechanisms Beyond Configurable Software Systems", "", <p></p>, List(
            ResearchTopic("Developer Support and QA for PHP", <p></p>),
            ResearchTopic("Sensitivity Analysis", <p></p>),
            ResearchTopic("Build systems", <p></p>)
        )),
        Theme("Other Topics", "", <p></p>, List(
            ResearchTopic("fMRI (Understanding Complexity)", <p></p>)
        ))
    )


    def cite(p: Publication*): NodeSeq = <span>[{p.map(citeOne).reduce(_ ++ Text(", ") ++ _)}]</span>

    def citeOne(p: Publication): NodeSeq = <a href={""} title={p.render(SimpleBibStyle, TextFormater)}>{p.venue.short} {p.venue.year}</a>

    def qualityAssurance = Theme(
        "Quality Assurance for Highly-Configurable Software Systems",
        "We investigate approaches to scale various quality assurance strategies, including parsing, type " +
            "checking, data-flow analysis, and testing, to entire configuration spaces in order to find variability bugs" +
            "and detect feature interactions.",
        <p>
          We investigate approaches to scale various quality assurance strategies to entire configuration
          spaces. Although configurability in software and software product lines is important to customize
          it for different stakeholders and usage scenarios, it causes key challenges for quality assurance.
          Configuration options can change the behavior of a system and can interact with other configuration
          options, leading to an exponential configuration space, often complicated further by constraints.
          For example, the Linux kernel has 13,000 compile-time options alone that could be combined into up
          to 2^13000 configurations; a huge configuration space that can obviously not be assured by looking
          at one configuration at a time. We investigate a wide range of approaches (including parsing,
          type checking, data-flow analysis, testing, energy analysis, and sampling) for a large number of
          languages and environments (conditional compilation in C, load-time options in Java, plugin
          mechanisms in PHP). A typical goal is to make assurance judgements for the entire configuration
          space that is comparable to but much faster than looking at each configuration separately,
          typically by exploiting the similarities among configurations.
        </p><p>
          I think variability-related complexity is a fascinating topic. On the surface it seems impossible
          to deal with the complexity of these huge configuration spaces and it surprising how developers
          cope at all, but a closer look reveals a lot of structure to how variability is used and how it
          is different from other sources of complexity. Configuration spaces are large but finite, which
          opens many interesting possibilities for automated reasoning. Developers intentionally share many
          implementations across configurations and most configuration options intentionally do not interact.
          It is this balance between sharing and variability in finite spaces that allows many innovative
          quality assurance strategies that might not be applicable for other sources of complexity; of
          course other analyses outside of configurable software systems with similar characteristics can
          benefit from the same insights.
        </p>,
        List(variationalAnalysis,
            unpreprocessedC,
            vtypechecking,
            vexecution,
            ResearchTopic("Sampling", <p></p>),
            ResearchTopic("Feature Interactions", <p></p>),
            ResearchTopic("Variational Specifications", <p></p>),
            ResearchTopic("Assuring and Understanding Quality Attributes as Performance and Energy ", <p></p>)
        )
    )

    def variationalAnalysis = ResearchTopic(
        "Variational Analysis",
        <p>
          Variational analysis is the idea of analyzing the entire configuration space at once instead of
          analyzing individual configurations either brute force or by sampling. Variational analysis typically
          represents the entire program and all its variations together and considers those variations in every
          analysis step. For example, if a variable in a program can have three alternative types depending on
          two configuration options, a type checker would track all alternative types and their corresponding
          conditions and make sure that they are used appropriately in every context.
        </p><p>
          Variational analysis is typically sound and complete with regard to a brute-force strategy -- that is
          it finds exactly the same issues a baseline brute-force analysis would find and introduces no false
          positives -- but much faster because it exploits the similarities in the program, such as checking only
          the three alternative types of the variable instead of determining the variable’s type in every single
          configuration separately. While variations in different parts of the program can interact and lead to
          an exponential explosion, we have shown empirically for many problems that those interactions,
          although they exist, are often relatively rare and local, enabling to scale variational analyses to
          huge configuration spaces as in the Linux kernel with 13000 compile-time options. We tend to talk of
          lifting a traditional analysis, such as type checking, to a variational analysis that efficiently
          computes equivalent results scaling for large configuration spaces.
        </p><p>
          We have investigated variational analyses and built tools for parsing {cite(oopsla11_typechef)}, type checking {cite(ase08, tosem12, oopsla12)},
          linker checks (compositionality) {cite(oopsla12)}, control-flow and data-flow analysis {cite(fse13)}, and testing {cite(icse14_varex, fosd12_varex)}.
          Furthermore, we have investigated general principles, patterns, and data structures for variational
          analyses {cite(fse13, onward14)} and performed a survey of the field {cite(csur14)}.
        </p> ,
        Some("research/commuting.png"),
        insights = List(<p>Sharing similarities among variations in the analyzed artifacts and tracking the variations as part of the analysis enables scaling variational analyses.</p>,
            <p>Intelligent variability encoding and SAT solvers and BDDs help to efficiently reason about large, finite, and possibly constrained configuration spaces.</p>,
            <p>A variational analysis can detect exactly the same issues that a corresponding traditional analysis would find by analyzing each configuration separately, but it can do so much faster and hence scale to much larger configuration spaces.</p>),
        tools = List(typechef, varex, varexj, cide) //LoTrack, Emergo, ...
    )


    def unpreprocessedC = ResearchTopic(
        "Analysis of Unpreprocessed C Code",
        <p>
            With our TypeChef infrastructure, we have built the first sound and complete parser and analysis
            infrastructure for unpreprocessed C code. Most analyses for C code work on preprocessed code, after
            expanding macros, including files, and deciding conditional compilation conditions. By preprocessing
            code though, we only see one configuration and loose information about all other configurations. For
            the same reason, building tools such as refactoring engines that work on unpreprocessed code that a
            developer edits is very challenging. Before TypeChef, parsing unpreprocessed C code was only possible
            with unsound heuristics or by restricting heavily how developers could use the C preprocessor. The
            TypeChef lexer and parser explore all possible branches of conditional compilation decisions, all
            possible macro expansions, and file inclusions and build an abstract syntax tree representing all
            preprocessor variability of the unpreprocessed C code through choice nodes. TypeChef is sound and
            complete with regard to parsing all preprocessed configurations in a brute-force fashion, but much
            faster: it finds exactly the same bugs and does not reject code that could be parsed after preprocessing.
        </p><p>
            On top of the TypeChef parser, we have built a variational type system,
            variational linker checks, a variational data-flow analysis framework,
            facilities for variational pointer analysis and variational call graphs.
            Others have used lightweight variations [] or have used TypeChef as the basis
            for imports [] and refactoring engines [] that could handle the C
            preprocessor.
        </p><p>
            TypeChef has been used to analyze the entire configuration space of the Linux
            kernel’s x86 architecture (>6000 compile-time options), of Busybox (>800
            compile-time options), OpenSSL, and other highly configurable systems written
            in C.
        </p>,
        //[typechef architecture picture; maybe splitting and joining of the parser?]
        tools = List(typechef)
        //Key insight: Parsing unpreprocessed C code can be expressed as an instance of the idea of variational analysis. Unpreprocessed C code can parsed soundly and completely by treating conditional compilation as variability and exploring all possible variations while sharing the commonalities. TypeChef can parse code bases as the Linux kernel with huge configuration spaces, for which a brute-force analysis would never scale.
        //        Tools: TypeChef link, mbeddr, flavios extension, refactoring engine
    )


    def vtypechecking = ResearchTopic(
        "Variational Type Checking and Data-Flow Analysis",
        <p>
            We have built variational type systems, linker checks, and data-flow analyses
            for compile-time variability in Java and C, again instances of the general
            idea of variational analysis. When faced with compile-time variability,
            compiler errors in certain configurations are a common problem, for example,
            when a function is called in a configuration in which it is not defined. Such
            problems may surface only in configurations that require specific combinations
            of multiple options, for example enabling some and disabling others. When end-
            users perform the configuration as in Linux and most plugin systems, one would
            like to ensure the absence of configuration-related compilation errors for the
            entire configuration space.
        </p><p>
            We built our first variational type system for compile-time variability in
            Java code and found bugs in various JavaME applications that used compile-time
            variability []. Subsequently, we have implemented a variational type system
            and variational linker checks for unpreprocessed C code on top of TypeChef and
            found found various variability-related errors in real world software systems,
            sometimes involving more than 10 configuration options []. A key mechanism to
            scale the analysis of large code bases was to perform compositional analysis
            analyzing one C file at a time and subsequently performing variational linker
            checks, for which we have designed and formalized a variational module system
            [].
        </p><p>
            Furthermore, we have built a variational static analysis framework on top of
            TypeChef that can perform typical data-flow analyses as constant propagation
            and taint tracking over all compile-time configurations. In addition to the
            possibility of finding bugs, these analyses provide a foundation for sound
            variational refactorings of unpreprocessed C code [].
        </p>,
        //[variational hallo-world example]
        tools = List(typechef, cide)
        //Insight: Type checking all compile-time configurations of large real-world systems as the Linux kernel can efficiently be done with a variational type system that tracks all variations and shares commonalities during the analysis.
        //            Insight: For a compositional variational analysis, variability must be encoded in module interfaces. A variational module system additionally provides a foundations for product lines of product lines.
    )

    def vexecution = ResearchTopic(
        "Variational Execution (Testing)",
        <p>
            With variational execution, we try to bring variational analysis to testing
            and dynamic analyses. Again, we track differences and share similarities
            across configurations, but instead of tracking alternative types, we track
            alternative values of variables during execution. That is, we attempt to
            execute a program across all configurations at once: we track all distinct
            values a variable could have in any configuration and explore all branches on
            configuration-related control-flow decisions. In contrast to symbolic
            execution, we use only concrete values, but a variable may have multiple
            concrete values in different configurations. When hitting assertions or
            output, we can simply check all possible values for the entire configuration
            space. Again, we track differences only where they matter and share
            similarities across configurations, which allows us to scale over brute-force
            approaches.
        </p><p>
            We have experimented with different implementations, primarily by lifting
            interpreters. We have experimental environments for PHP [] and Java [].
        </p><p>
            Our primary goal is testing highly configurable systems, but we expect that we
            will use the infrastructure for many applications of dynamic analyses to large
            but finite configuration spaces beyond the traditional product line field,
            such as dynamic information-flow analysis and policy enforcement [].
        </p>,
        //[technical picture from a presention?]
        tools = List(varex, varexj)
        //Insight: Variational analysis can be applied also to interpreters that now track multiple concrete values for different configurations.
        //            Insights: Interactions exist among configuration options during program execution and are important and difficult to detect, but interactions tend to be rare, local, and involve only a subset of configuration options, allowing variational execution to scale in many settings.
    )


    val typechef = Software("TypeChef", "Parsing and Analyzing Unpreprocessed C Code", URL("https://github.com/ckaestne/TypeChef"))
    val varex = Software("Varex", "Variational Execution for PHP", URL("https://github.com/git1997/VarExecution"))
    val varexj = Software("VarexJ", "Variational Execution for Java, built on top of Java Pathfinder", URL("https://github.com/meinicke/VarexJ"))
    val cide = Software("CIDE", "Feature-Oriented Analysis and Decomposition of Legacy Code", URL("http://fosd.net/cide/"))


}
