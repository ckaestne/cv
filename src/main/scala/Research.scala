package de.stner.cv

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import com.mortennobel.imagescaling.{ResampleFilters, ResampleOp}
import de.stner.cv.GenPubList.TextFormater
import org.apache.commons.io.FileUtils

import scala.xml.{Elem, NodeSeq, Text}


object ResearchStructure {

    case class Theme(title: String, shortdescription: String, description: NodeSeq, topics: List[ResearchTopic]) {
        def key = stringToKey(title)
    }

    case class ResearchTopic(title: String, description: NodeSeq, pictureFile: Option[String] = None, insights: List[Elem] = Nil, tools: List[Software] = Nil, collaborators: List[Person] = Nil, scaleImg: Float = 1) {
        def key = stringToKey(title)
    }


    case class Software(title: String, onelineDescription: String, url: URL)

    def stringToKey(s: String) = s.split("\\s").map(_.replaceAll("[^\\p{L}\\p{Nd}]+", "")).filter(_.length > 3).take(4).map(_.capitalize).mkString

}

object ResearchGenHtml extends App {

    import de.stner.cv.GenHtml._
    import de.stner.cv.Research._
    import de.stner.cv.ResearchStructure._


    def researchPage = printTitle() ++
        printResearchOverview(themes) ++
        printResearch(themes)

    def printResearchOverview(themes: List[Theme]): NodeSeq = rowH2("Research Overview", "researchoverview") ++ themes.map(printThemeShort).flatten

    def printThemeShort(theme: Theme): NodeSeq =
        row(null,
            <div class="researchthemesummary">
                <h3>{theme.title}</h3>
                <p class="researchthemedesc">{theme.shortdescription}
                    {theme.topics.map(printTopicLink).reduce(_ ++ Text(" · ") ++ _)}</p>
            </div>
        )

    // <a href={"research.html#" + theme.key}>[details]</a>

    def printTopicLink(topic: ResearchTopic): NodeSeq =
        <a href={"research.html#" + topic.key}>{topic.title}</a>


    def printResearch(themes: List[Theme]): NodeSeq = themes.map(printThemeDetailed).flatten

    def printThemeDetailed(theme: Theme): NodeSeq =
        rowH2(theme.title, theme.key, theme.description) ++
            theme.topics.map(printTopicLong).flatten

    def imgWidth = 200 //px

    def printTopicLong(topic: ResearchTopic): NodeSeq = {
        val width = Math.round(imgWidth * topic.scaleImg)
        val picturePath = topic.pictureFile.map(p => {
            val imgDir = "research"
            assert(new File(sourcePath, p).exists(), s"image $p does not exist in $sourcePath")
            FileUtils.copyFile(new File(sourcePath, p), new File(new File(targetPath, imgDir), p), true)
            val smallImgFilename = p.dropRight(4) + "-min.png"
            scaleImage(new File(sourcePath, p), new File(new File(targetPath, imgDir), smallImgFilename), width)
            (imgDir + "/" + p, imgDir + "/" + smallImgFilename)
        })
        row(picturePath.map(p => <a href={p._1}><img class="topicimg" src={p._2} alt={topic.title} width={width + "px"} /></a>).getOrElse(null),
            <div class="researchtopic">
                <h3><a name={topic.key}  class="sectionanch"></a>{topic.title}</h3>
                {topic.description}
            </div>
        )
    }

    def trimImage(bmp: BufferedImage): BufferedImage = {
        val imgHeight = bmp.getHeight
        val imgWidth = bmp.getWidth

        def isNonWhite(rgb: Int) = rgb != Color.WHITE.getRGB &&
            (rgb >> 24 & 255) > 0

        //TRIM WIDTH
        var widthStart = imgWidth
        var widthEnd = 0
        var stop = false
        for (y <- 0 until imgHeight) {
            stop = false
            for (x <- (0 until imgWidth - 1).reverse) {
                if (!stop && isNonWhite(bmp.getRGB(x, y)) && x < widthStart) {
                    widthStart = x
                }
                if (!stop && isNonWhite(bmp.getRGB(x, y)) && x > widthEnd) {
                    widthEnd = x
                    stop = true
                }
            }
        }
        //TRIM HEIGHT
        var heightStart = imgHeight
        var heightEnd = 0
        for (x <- 0 until imgWidth) {
            stop = false
            for (y <- (0 until imgHeight - 1).reverse) {
                if (!stop && isNonWhite(bmp.getRGB(x, y)) && y < heightStart) {
                    heightStart = y
                }
                if (!stop && isNonWhite(bmp.getRGB(x, y)) && y > heightEnd) {
                    heightEnd = y
                    stop = true
                }
            }
        }

        val finalWidth = widthEnd - widthStart
        val finalHeight = heightEnd - heightStart

        val newImg = new BufferedImage(finalWidth, finalHeight,
            BufferedImage.TYPE_INT_ARGB)
        val g = newImg.createGraphics()
        g.drawImage(bmp, -widthStart, -heightStart, null)
        newImg
    }


    def scaleImage(inputFile: File, outputFile: File, width: Int) = {
        val image = trimImage(ImageIO.read(inputFile))

        val resampleOp = new ResampleOp(width, (width * image.getHeight) / image.getWidth)
        resampleOp.setFilter(ResampleFilters.getLanczos3Filter)
        val newImage = resampleOp.filter(image, null)

        ImageIO.write(newImage, "png", outputFile)
    }


    def targetPath = new File("target/site")

    def sourcePath = new File("src/main/resources/researchimg")

    printDoc(researchPage, "Research Overview :: " + CV.name + " :: CMU", new File(targetPath, "research.html"))


}


object Research {

    import de.stner.cv.CVPublications._
    import de.stner.cv.ResearchStructure._
    import de.stner.cv.CVMedia._

    def themes = List(seai,
        opensource,
        qualityAssurance,
        maintenanceAndImplOfVariability,
        imperfectModularity,
        beyondVariability,
        otherTopics
    )


    def cite(p: Citable*): NodeSeq = <span>[{p.map(citeOne).reduce(_ ++ Text(", ") ++ _)}]</span>

    def citeOne(p: Citable): NodeSeq = p match {
        case p: Publication =>
            <a href={"publications.html#" + p.genKey} title={p.render(SimpleBibStyle, TextFormater)}>{
                val ven = p.venue.short
                if (ven == null || ven.trim == "")
                    p.venue.publisher.map(_.name).getOrElse("")
                else ven
            } {p.venue.year}</a>
        case m: Media =>
            <a href={m.link.toString} title={m.title}>{m.citeKey}</a>
        case CiteExternal(authorYear, longForm, link) =>
            <a href={link.toString} title={longForm}>{authorYear}</a>
    }

    def linkTopic(topic: ResearchTopic, linkText: Option[String] = None): NodeSeq =
        <a href={"research.html#" + topic.key} title={topic.title}>{linkText.getOrElse(topic.title)}</a>

    def linkTool(sw: Software, linkText: Option[String] = None): NodeSeq =
        <a href={sw.url.toString} title={sw.title + ": " + sw.onelineDescription}>{linkText.getOrElse(sw.title)}</a>




    def seai = Theme(
        "Software Engineering for AI-Enabled Systems",
        "We explore how different facets of software engineering change with the introduction " +
            "of machine learning components in production systems, with an interest in interdisciplinary collaboration, quality assurance, " +
            "system-level thinking, safety, and better data science tools:",
        <p>
          We investigate how the introduction of machine learning in software projects (AI-enabled systems) changes the
            way that production systems are developed, tested, and maintained.
            This is true both for traditional software systems where now a machine-learned component
            is added (e.g., adding automated slide layout to PowerPoint) as well for systems
            build around machine-learning innovations (e.g., an automated audio transcription service).
            Machine learning models
            form heuristics that often work, but without clear specifications that could be checked
            as in traditional quality assurance work. Assuming that a model may make mistakes, the focus
            must increasingly be on system-level thinking, whole system design, testing in production,
            and requirements engineering. Our goal is to understand challenges and provide better
            techniques for design, quality assurance, maintenance, and operation of software systems
            with machine learning components. It is particularly interesting how to bring together
            team-members with different backgrounds (data scientists, software engineers, operators, ...)
            to build AI-enabled systems and transition them into production.
            </p><p>
             This is a recent and fast moving field, with many interesting research problems.
        </p>,
        List(capturingSEAI,
            seaicollaboration,
            notebooks,
        )
    )


    def capturingSEAI = ResearchTopic(
        "Capturing Software Engineering for AI-Enabled Systems",
        <p>
            Software engineering for AI-enabled systems is a new and emergent field, emphasizing
            that one has to look beyond the machine-learning model at the larger system to
            plan a successful software product with AI components in production.
            We are particularly interested in this from an education perspective {cite(icseseet20)}
            and from forming a community to understand the relevant topics in the field {cite(seaibib)},
            captured also in multiple talks {cite(semlaTalk20,ccTalk20,seetTalk20)}.
            This way, we tough on many topics, including quality assurance {cite(seaiMedium20MLisRE,seaiMedium20Testing)},
            software development processes {cite(seaiMedium20Process)}, requirements engineering {cite(seaiMedium20MLisRE,seaiMedium20WorldMachine)},
            safety, fairness, MLOps, and many others.
        </p>,
        Some("seaiprocess.png")
    )

    def seaicollaboration = ResearchTopic(
        "Interdisciplinary Collaboration in Engineering AI-Enabled Systems",
        <p>
            Interdisciplinary collaboration becomes a central point, as software engineers and
            data scientists each have distinct specialities, focus, goals, and experiences,
            but need to work together in building these AI-enabled systems. For example, software
            engineers need to work with data scientists to define quality expectations for machine-learned components
            beyond just prediction accuracy, but also covering performance, fairness, safety, and explainability
            requirements among many others. They also need to work together to understand how to design
            the overall system to deal with mistakes the model makes, typically with mitigation and
            system-design strategies outside the model {cite(semlaTalk20)}. This requires a fresh look at the development process
            of AI-enabled systems {cite(seaiMedium20Process)}.
        </p><p>
            Much of our work focuses on understanding collaboration points, such as the handoff
            of modeling code between data scientists and software engineers when transitioning
            a research project into a production system. We believe that there is high potential for
            deliberate collaboration, for capturing contracts at collaboration points, and for providing
            tooling and automation to catch common problems and make teams more effective.
        </p>,
        Some("seaivenn.png")
    )


    def notebooks = ResearchTopic(
        "Developer Tooling for Data Scientists",
        <p>
            Computational notebooks, such as Jupyter, are broadly adopted by data scientists,
            but offer a far less rich developer environment than what software engineers are used
            to. At the same time, data scientist's workflows and goals are different, so a direct
            translation of software engineering tools and practices is not appropriate.
            We investigate techniques to improve developer tooling for data scientistis, primarily
            targeted at notebooks. For example, can we nudge data scientists to consider
            various quality attributes beyond prediction accuracy, can we automatically derive documentation,
            can we provide debugging support, can we automatically migrate notebook code toward
            production-ready pipeline code and back? We develop analysis tools and notebook extensions
            to help data scientists in their work and in their collaboration with software engineers
            as part of a larger AI-enabled system.
        </p>,
        Some("seainotebook.png")
    )



    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //////////////////////////////////////////// open source /////////////////////////////////////////////


    def opensource = Theme(
        "Sustainability and Fairness in Open Source",
        "We study the dynamics of open source communities with a focus on unstanding and fostering fair and sustainable environments. " +
            "Primarily with empirical research methods, we explore topics, such as open source culture, coordination, stress and disengagement, " +
            "funding, and security:",
        <p>
            Open-source software is ubiquitous and plays such critical roles in today’s software infrastructure and
            economy that threats to its sustainability must be taken seriously. Unsustainable open-source infrastructure
            poses serious risks and undermines innovation for the economy as a whole.
            However, over the last 8-10 years open source has significantly changed—nowadays, open source is
            increasingly characterized by professionalization and commercial involvement, by high pressure through
            transparency, and by high demands and expectations from users, just to name a few.
            Participants in open source communities, many of which volunteer their time, are often exposed to high levels of stress
            and some even report burnout. We are interested in questions of individual fairness and how to shape
            open source culture to be sustainable. This involves many important research topics, such as the common collaboration
            and coordination mechanisms used, the role of money in open source, stress and burnout and toxic interactions,
            mechanisms to shape culture and encourage the adoption of sustainable practices, and many more.
            Our research aims to supplement discussions that are currently typically shaped by opinions and
            anecdotes with empirical evidence as well as to provide evidence-supported and validated interventions.
        </p>,
        List(sustainopensource,osscollaboration,ossadoption)
    )


    def sustainopensource = ResearchTopic(
        "Sustainability and Fairness in Open Source",
        <p>
            With the increasing commercialization and professionalization of open source new stressors around
            interactions between paid contributors and voluteers, high transparency, entitled users and many
            others emerge. We are interested in understanding current practices and suggesting interventions
            (e.g., tools and best practices) for more sustainable and equitable communities {cite(stateOfTheSource20Talk)}.
            This involves studying the reasons for disengagement and stress {cite(icsenier20, oss19)},
            studying community culture {cite(fse16)}, studying funding mechanisms such as donations {cite(icse20donations, infographicDonations)},
            and designing security mechanisms {cite(icsenier19,icse21_malicious)}.
          </p>,
        Some("donations.png")
    )


    def osscollaboration = ResearchTopic(
        "Collaboration and Coordination in Open Source",
        <p>
            Open source strives from the collaboration within and across multiple projects. Work is largely
            decentralized and loosely coordinated, providing new opportunities but also new challenges.
            Supporting better and more equitable collaboration in open source will help to sustain the
            communities of developers and maintainers.
            A key point of tension is the definition and change of interfaces between different dependent
            open source projects that become apparent around breaking changes;
            we found that different ecosystems and corresponding communities have adopted very different
            coordination and communication strategies around handling breaking changes and absorbing
            the corresponding costs {cite(fse16,breakingChangesWeb,jsconf17Talk)}.
            We found that repository badges are an effective way to signal qualities and encourage practices
            without explicit enforcement mechanisms {cite(icse18badges)}.
            We also studied how developers collaborate across forks of a repository {cite(icse20forks,fse19forks,saner19,icse18forks)}, finding among
            others several inefficiencies that can be addressed with different development practices {cite(fse19forks)}
            and opportunites to mitigate some inefficiences such as redundant development with suitable tooling  {cite(saner19,icse20forks)}.
         </p>,
        Some("breakingapis.png")
    )

    def ossadoption = ResearchTopic(
        "Adoption of Practices and Tooling",
        <p>
            There are many positive practices and useful forms of tooling that are often benefitial to
            open source projects and their sustainability. Understanding the costs and benefits is crutial,
            but so are strategies to encourage adoption of good practices and tools by the broader community.
            We studied the diffusion of practices {cite(fse20_diff)}, which can help design more effective
            communication strategies. Repository badges seem effective at nudging developers to adopt
            specific practices, such as submitting more pull requests with tests if the importance of
            test coverage is signaled through a badge {cite(icse18badges, infographicBadges)}.
            With the example of continuous integration, we also dug deeper to understand
            problems around tooling and how adoption (or abandonment) of tooling can be understood or
            influenced {cite(fse19ci,msr18travis)}.
        </p>,
        Some("badges.png")
    )

    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //////////////////////////////////////////// quality assurance /////////////////////////////////////////////


    def qualityAssurance = Theme(
        "Quality Assurance for Highly-Configurable Software Systems",
        "We explore approaches to scale quality assurance strategies, including parsing, type " +
            "checking, data-flow analysis, and testing, to huge configuration spaces in order to find variability bugs " +
            "and detect feature interactions:",
        <p>
          We investigate approaches to scale various quality assurance strategies to entire configuration
          spaces.
          Configuration options can change the behavior of a system and can interact with other configuration
          options, leading to an exponential configuration space, often complicated further by constraints.
          For example, the Linux kernel has 13,000 compile-time options alone that could be combined into up
          to 2^13000 configurations; a huge configuration space that can obviously not be assured by looking
          at one configuration at a time. We investigate a wide range of approaches (including parsing,
          type checking, data-flow analysis, testing, energy analysis, and sampling) for a large number of
          languages and environments (conditional compilation in C, load-time options in Java, plugin
          mechanisms in PHP). Our typical goal is to make assurance judgements for the entire configuration
          space that yields equivalent results but is much faster than looking at each configuration separately,
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
            sampling,
            featureInteraction,
            variationalSpecs,
            performance,
            security
        )
    )

    def variationalAnalysis = ResearchTopic(
        "Variational Analysis",
        <p>
          Variational analysis (or variability-aware analysis) is the idea of analyzing the entire configuration space at once instead of
          analyzing individual configurations either brute force or by sampling. Variational analysis typically
          represents the entire program and all its variations together and considers those variations in every
          analysis step. For example, if a variable in a program can have three alternative types depending on
          two configuration options, a type checker would track all alternative types and their corresponding
          conditions and make sure that they are used appropriately in every context.
        </p><p>
          Variational analysis is typically sound and complete with regard to a brute-force strategy&mdash;that is
          it finds exactly the same issues a baseline brute-force analysis would find and introduces no false
          positives&mdash;but much faster because it exploits the similarities in the program. While variations in different parts of the program can interact and lead to
          an exponential explosion, we have shown empirically for many problems that those interactions,
          although they exist, are often relatively rare and local, enabling to scale variational analyses to
          huge configuration spaces as in the Linux kernel with thousands of compile-time options.
        </p><p>
          We have investigated variational analyses and built tools for parsing {cite(oopsla11_typechef)}, type checking {cite(ase08, tosem12, oopsla12)},
          linker checks (compositionality) {cite(oopsla12)}, control-flow and data-flow analysis {cite(fse13)}, and testing {cite(icse14_varex, fosd12_varex, ase16, gpce18, oopsla18)}.
          Furthermore, we have investigated general principles, patterns, and data structures for variational
          analyses {cite(fse13, onward14)} and performed a survey of the field {cite(csur14)}.
        </p> ,
        Some("commuting.png"),
        insights = List(<p>Sharing similarities among variations in the analyzed artifacts and tracking the variations as part of the analysis enables scaling variational analyses.</p>,
            <p>Intelligent variability encoding and SAT solvers and BDDs help to efficiently reason about large, finite, and possibly constrained configuration spaces.</p>,
            <p>A variational analysis can detect exactly the same issues that a corresponding traditional analysis would find by analyzing each configuration separately, but it can do so much faster and hence scale to much larger configuration spaces.</p>),
        tools = List(typechef, varex, varexj, cide) //LoTrack, Emergo, ...
    )


    def unpreprocessedC = ResearchTopic(
        "Analysis of Unpreprocessed C Code",
        <p>
            With our {linkTool(typechef)} infrastructure, we have built the first sound and complete parser and analysis
            infrastructure for unpreprocessed C code. Most analyses for C code work on preprocessed code of a single configuration, after
            expanding macros, including files, and deciding conditional compilation conditions&mdash;by preprocessing
            code, they loose information about other configurations. For
            the same reason, tools such as refactoring engines that work on unpreprocessed code which a
            developer edits are very challenging to build. Before TypeChef, parsing unpreprocessed C code was only possible
            with unsound heuristics or by restricting heavily how developers could use the C preprocessor. The
            TypeChef lexer and parser explore all possible branches of conditional compilation decisions, all
            possible macro expansions, and file inclusions and build an abstract syntax tree representing all
            preprocessor variability of the unpreprocessed C code through choice nodes. TypeChef is sound and
            complete with regard to parsing all preprocessed configurations in a brute-force fashion, but much
            faster: it finds exactly the same bugs and does not reject code that could be parsed after preprocessing.
        </p><p>
            On top of the TypeChef parser, we have built a variational type system and
            variational linker checks {cite(oopsla12)}, a variational data-flow analysis framework {cite(fse13, tosem18)},
            facilities for variational pointer analysis and variational call graphs.
            Others have used lightweight variations {cite(flavioRef)} or have used TypeChef as the basis
            for imports {cite(mbeddr)} and refactoring engines {cite(joergRefactoringICSE)} that could handle the C
            preprocessor.
        </p><p>
            TypeChef has been used to analyze the entire configuration space of the Linux
            kernel’s x86 architecture (>6000 compile-time options), of Busybox (>800
            compile-time options), OpenSSL, and other highly configurable systems written
            in C.
        </p>,
        Some("parsing.png"), //[typechef architecture picture; maybe splitting and joining of the parser?]
        tools = List(typechef)
        //Key insight: Parsing unpreprocessed C code can be expressed as an instance of the idea of variational analysis. Unpreprocessed C code can parsed soundly and completely by treating conditional compilation as variability and exploring all possible variations while sharing the commonalities. TypeChef can parse code bases as the Linux kernel with huge configuration spaces, for which a brute-force analysis would never scale.
        //        Tools: TypeChef link, mbeddr, flavios extension, refactoring engine
    )

    def joergRefactoringICSE = CiteExternal("Liebig et al. 2014",
        "Liebig, J., Janker, A., Garbe, F., Apel, S. and Lengauer, C., 2015, May. Morpheus: Variability-aware refactoring in the wild. In Proceedings of the 37th International Conference on Software Engineering (pp. 380-391). IEEE Press.",
        URL("https://dl.acm.org/citation.cfm?id=2818803"))

    def mbeddr = CiteExternal("Szabó et al. 2015", "mbeddr project",
        URL("http://mbeddr.com/2015/03/05/pastingC.html"))

    def flavioRef = CiteExternal("Medeiros et al. 2014",
        "Medeiros, F., Ribeiro, M., & Gheyi, R. (2014). Investigating preprocessor-based syntax errors. ACM SIGPLAN Notices, 49(3), 75-84.",
        URL("https://dl.acm.org/citation.cfm?id=2517221"))


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
            variability {cite(ase08, tosem12)}. Subsequently, we have implemented a variational type system
            and variational linker checks for unpreprocessed C code on top of TypeChef and
            found found various variability-related errors in real world software systems,
            sometimes involving more than 10 configuration options. A key mechanism to
            scale the analysis of large code bases was to perform compositional analysis
            analyzing one C file at a time and subsequently performing variational linker
            checks, for which we have designed and formalized a variational module system
            {cite(oopsla12)}.
        </p><p>
            Furthermore, we have built a variational static analysis framework on top of
            TypeChef that can perform typical data-flow analyses as constant propagation
            and taint tracking over all compile-time configurations {cite(fse13,tosem18)}. In addition to the
            possibility of finding bugs, these analyses provide a foundation for sound
            variational refactorings of unpreprocessed C code {cite(joergRefactoringICSE)}.
        </p>,
        Some("helloworld.png"),
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
            We have experimented with different implementations, primarily by lifting
            interpreters. We created experimental variational interpreters for PHP {cite(icse14_varex)} and Java {cite(jensThesis,ase16,oopsla18)}.
        </p><p>
            Our primary goal is testing highly configurable systems, but we experimented with many other
            applications of dynamic analyses to large
            but finite configuration spaces beyond the traditional product line field,
            including higher-order mutation testing {cite(fse20_hom)} and program repair {cite(fsenier18)}.
            Others have used these techniques also for dynamic information-flow analysis and policy enforcement {cite(austinPoplRef, austinWSRef)}.
        </p>,
        Some("varexwp.png"), //[technical picture from a presention?]
        tools = List(varex, varexj)
        //Insight: Variational analysis can be applied also to interpreters that now track multiple concrete values for different configurations.
        //            Insights: Interactions exist among configuration options during program execution and are important and difficult to detect, but interactions tend to be rare, local, and involve only a subset of configuration options, allowing variational execution to scale in many settings.
    )

    val austinPoplRef = CiteExternal("Austin et al. 2012",
        "Austin, T. H., & Flanagan, C. (2012, January). Multiple facets for dynamic information flow. In ACM SIGPLAN Notices (Vol. 47, No. 1, pp. 165-178). ACM.",
        URL("https://dl.acm.org/citation.cfm?id=2103677"))
    val austinWSRef = CiteExternal("Austin et al. 2013",
        "Austin, T. H., Yang, J., Flanagan, C., & Solar-Lezama, A. (2013, June). Faceted execution of policy-agnostic programs. In Proceedings of the Eighth ACM SIGPLAN workshop on Programming languages and analysis for security (pp. 15-26). ACM.",
        URL("https://dl.acm.org/citation.cfm?id=2465121"))
    val jensThesis = CiteExternal("Meinicke 2015",
        "Jens Meinicke. VarexJ: A Variability-Aware Interpreter for Java Applications. Master thesis, University of Magdeburg, Germany, December 2014.",
        URL("http://wwwiti.cs.uni-magdeburg.de/~meinicke/"))


    def sampling = ResearchTopic("Sampling",
        <p>
            While I am personally biased toward sound variational analyses that cover entire configuration spaces,
            we have investigated sampling strategies toward quality assurance. Sampling is by its very nature incomplete
            with regard to the configuration space because it does not analyze all configurations, but it is typically
            much easier to perform. Among others, we have compared different sampling strategies and have compared
            variational analyses to sampling, where variational analysis can provide a ground truth about issues in
            a system. This allowed us, for example, to study whether the assumptions behind many sampling strategies,
            such as that most issues involve only few configuration options, hold in practice. In addition, we found
            that many sampling strategies are only easy to apply when making strong simplifying assumptions, such as
            that the system does not contain constraints or that each file can be analyzed separately {cite(fse13, icse16)}.
        </p>,
        Some("sampling.png"), scaleImg = .8f
        //            Insights: Most configuration-related issues indeed involve only few configuration options and can be found with combinatorial sampling. There are however also issues that involve more configuration options and are much harder to find.
        //    Insights: Most sampling strategies are challenging to set up, when considering constraints among options, analyzes that cross files, or variability from C header files.
    )

    def featureInteraction = ResearchTopic("Feature Interactions",
        <p>
            Feature interactions are still a mystery to me. They are <em>emergent properties</em>
            in a system and surface as interoperability problems. They emerge from a
            failure of compositionality, when the behavior of combining two parts is
            unexpected from their individual behaviors. In the common example of flood
            control and fire control in a building, both work fine in isolation but can be
            dangerous if combined incorrectly, as flood control can turn off water from
            the sprinklers in case of a fire. Interactions are often actually intended and
            desired, so pure isolation is rarely an optimal strategy; for example plugins
            of a blogging software should often interact to reach a common goal. Although
            feature interactions have been studied in depth from a requirements
            perspective in the 90s and some success has been achieved in understanding
            interactions and designing for them in the telecommunications domain,
            to me it is still largely unclear how we can detect them in practice or design
            new systems to allow intentional interactions but prevent undesired ones. For
            example, how can we ensure orthogonality or isolation of options except for
            those cases where interactions are explicitly intended? How can we help
            developers understand such issues despite huge exponential configuration
            spaces?
        </p><p>
            I am interested in understanding how interactions manifest in implementations,
            for example, at the level of data flow. To that end, we use variational
            analyses (data flow, variational execution) to understand systems. Only when
            we understand current practice can we identify common problems and successful
            patterns for managing interactions. We focus on interactions among options
            (compile-time, run-time, plugins), but take a very wide view on interactions
            that includes diverging behavior, alternative values of a variable depending
            on multiple options, structural interactions as nested #ifdefs, as well as
            {linkTopic(performance, Some("performance interactions"))}.
            I am mostly interested in interactions at the code level {cite(ase16,gpce18,emse19,vamos18,varvis)}, but with broad
            ranges of domains, such as infrastructure software, plugin systems, software
            ecosystems, home automation, or medical devices.
        </p>,
        Some("floodfire.png")
    )

    def variationalSpecs = ResearchTopic("Variational Specifications",
        <p>
            We explore different strategies to specify expected behavior in highly
            configurable systems. If options may interact, what correct behavior do we
            expect? How can we specify behavior in an exponential configuration space,
            when we obviously cannot think about every combination of options separately?
            How do we encode such specifications in test cases or for static analysis or
            verification tools? Global specifications that should uniformly hold across
            all configurations, such as all configurations should be well-typed or should
            not crash are easy to specify. I believe that the only meaningful scalable
            strategy to specify expected behavior and detect inconsistencies of
            interactions is to specify for each feature separately the expected behavior,
            independent of any other options. Such feature-specific specification will
            allow us to detect when that feature is negatively influenced by other
            options {cite(comnet13)}. This view shapes my view on interactions and is a main reason for
            pursuing variational execution as an efficient way of checking such
            specifications encoded as test cases in large configuration spaces. I believe
            that writing test cases that assert a feature’s behavior independent from
            other features, combined with variational execution, will allow us to get a
            handle on interoperability and detecting feature interactions {cite(icse14_varex)}.
        </p>,
        Some("featurespec.png")
        //            Insights: Correctness in highly configurable systems can only be specified effectively through global or per-feature specifications.
    )

    def performance = ResearchTopic("Assuring and Understanding Quality Attributes as Performance and Energy ",
        <p>
            We explore different approaches to understand how configuration options affect
            performance, power consumption, and other quality attributes of software
            systems. With their configuration options, many configurable software systems
            have a built-in potential to tweak performance and other quality attributes,
            for example to trade off faster encoding with lower video quality in a video
            encoder. However, understanding which configuration
            options affect performance or other quality attributes is difficult. Not all
            configuration options influence performance significantly and sometimes the
            interactions of multiple options have strong positive or negative
            influences&mdash;again the scale of the configuration space makes an analysis difficult.
        </p><p>
            We have focused on building performance-influence models and energy-influence
            models in a form of <em>sensitivity analysis</em> in which we measure quality
            attributes in various configurations and subsequently learn a model that
            describes which options and which interactions influence the quality
            attribute. These models are suitable for debugging, understanding, predicting,
            and optimizing quality attributes as performance for highly-configurable
            software systems. For example, by showing the influence of configuration
            options on energy consumption, we expect that users make more energy-conscious
            configuration decisions weighing the benefits of a feature against its
            cost&mdash;in a tool we call <em>green configurator</em>.
        </p><p>
            We have learned that we can build relatively accurate models even with few
            measurements and that there are various heuristics for learning about
            interactions, rooted in common observations in real-world systems {cite(splc11_nfp,icse12,fse15_influence)}.
            Initially, we
            focused mainly on a black-box approach, where we just measure performance
            while executing the system, but more recently, we explored how program analysis
            techniques of the implementation can inform the sampling strategy and reduce the number of
            measurements {cite(jase20,icse21_perf)}. In addition, we explored the use of transfer learning
            to reuse measurements for more accurate learning {cite(seams17,fse18,ase17pf)}.
        </p>,
        Some("greenconfig.png")
        //        Insight: Performance-influence models can describe which options or interactions affect the performance of a highly-configurable system. With moderate effort, we can build relatively accurate models for real-world software systems that are useful for debugging, understanding, prediction, and optimization.
        //    Insight: Most configuration options have only limited influence on most quality attributes. Considering interactions among options is important to achieve accuracy, but usually only few significant interactions occur in each system that can often be found with certain heuristics.
        //    Tools: SPLConqueror
    )

    def security = ResearchTopic("Security",
        <p>
            We investigate assurance strategies and certification mechanisms for highly
            configurable systems. Many recent high-profile security vulnerabilities have
            been configuration related. For example, the <em>Heartbleed</em> vulnerability occurred
            only in optional code that was enabled by default but probably not needed in
            many real-world scenarios. Vulnerabilities may also arise due to nontrivial
            interactions of multiple options, even when individual configurations or
            options have been analyzed. Reducing the attack surface by disabling unneeded
            functionality and performing security analysis over all configurations
            (variational analyses) can help addressing security challenges.
        </p><p>
            We look at security in highly-configurable systems from many angles. Among
            others we explore variational information-flow analyses to understand how
            flows differ in different configurations, we investigate whether configuration
            complexity statistically associates with vulnerabilities in the
            implementation {cite(splc16)}, we investigate how current certification approaches along
            the lines of <em>Common Criteria</em> can be improved toward supporting easier
            recertification and certification of systems composed from parts (ecosystems,
            plugin systems, configurable systems) {cite(certtr)},
            and we build techniques to contain malicious package updates {cite(icsenier19,icse21_malicious)}.
        </p>,
        Some("heartbleed.png"), scaleImg = 0.8f
        //        Insight: Performance-influence models can describe which options or interactions affect the performance of a highly-configurable system. With moderate effort, we can build relatively accurate models for real-world software systems that are useful for debugging, understanding, prediction, and optimization.
        //    Insight: Most configuration options have only limited influence on most quality attributes. Considering interactions among options is important to achieve accuracy, but usually only few significant interactions occur in each system that can often be found with certain heuristics.
        //    Tools: SPLConqueror
    )


    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //////////////////////////////////////////// imperfect modularity /////////////////////////////////////////////

    def imperfectModularity = Theme("Working with Imperfect Modularity",
        "We explore mechanisms to support developers in scenarios in which traditional modularity mechanisms face " +
            "challenges; among others, we explore strategies to complement modularity mechanisms " +
            "with tooling:",
        <p>
            We investigate mechanisms to support developers in scenarios where modularity, the separation of changeable
            internals from stable interfaces, reaches its limits. We then  complement traditional modularity
            mechanisms with tool support and awareness mechanisms. Challenges to classic modularity mechanisms
            includes crosscutting concerns and feature implementations that are difficult or inefficient to
            modularize, as well as large and distributed development activities in software ecosystems without
            central planning. Software product lines have been an excellent case study for modularity challenges, because variable
            source code often is an important concern that is often challenging to modularize, due to
            its scattered and fine-grained nature and common implementation with
            control-flow mechanisms (if statements) and conditional compilation (#ifdef directives).
        </p>,
        List(vsoc, awareness, concepts))


    def vsoc = ResearchTopic("Virtual Separation of Concerns",
        <p>
            Observing the common crosscutting implementations of features in product
            lines, often scattered with conditional compilation directives, we discussed
            whether modularizing those would be worth the required costs {cite(fosd11)}. We argue that
            for many kinds of implementations, it might be simpler to provide tool support
            that would mimic several benefits of modularity, while still preserving the
            simplicity of scattered implementations. In what we call <em>virtual separation of
            concerns</em>, tool support synthesizes editable views on individual features and
            configurations of a product. Developers can switch between different views,
            depending on their tasks; tooling emulates many navigation and cohesion
            benefits otherwise expected from modularity {cite(jot09_vsoc)}. Static analysis can even
            compute certain interfaces on demand (called emergent interfaces) that can
            summarize the interactions with hidden code {cite(icse14_emergo)}. Finally, we have developed
            prototype tools that would rewrite the actual code base between virtual and
            actual physical separation {cite(gpce09)}. While virtual separation of concerns cannot
            achieve separate compilation or open-world reasoning, it might be a
            lightweight practical alternative for many development scenarios where classic
            modularity is not practical or too expensive.
        </p>,
        Some("cide.png")
        //    [cide screenshot]
        //    Example insight: Tool support can emulate many, though not all benefits of modularity even in scattered crosscutting implementations.
    )

    def awareness = ResearchTopic("Awareness for Evolution in Software Ecosystems",
        <p>
            Breaking change is inevitable in the long run and we strive to complement
            classical modularity with tailored awareness mechanisms. The scale of software
            ecosystems makes it likely that interfaces among modules (packages,
            components, plugins) will eventually evolve with rippling effects for other
            modules. Their distributed nature makes central planning (e.g., a change
            control board) unrealistic. We expect that change is generally inevitable and,
            while modularity can encapsulate a lot of change, we prefer to support
            developers in those cases where modularity does not protect them from change.
        </p><p>
            We study how developers plan changes and cope with changes in practice and how
            those practices are influenced by community values and technologies of the
            ecosystem {cite(scgse15,fse16)}. Among others, we found that developers are overwhelmed with raw
            notification feeds but have found many mitigation strategies to cope with
            upstream change. We develop tailored awareness mechanisms that identify which
            changes are relevant for individual users of a software package to allow a
            timely reaction {cite(ieeesw15,jsep17)}.
        </p>,
        Some("ecosys.png") //    [node.js/cran/Eclipse logos? Something from the presentation in passau?]
        //
        //        Example insight: Raw notification feeds are leading to information overload but the actual amount of important information is often very manageable.
    )

    def concepts = ResearchTopic("Conceptual Discussions",
        <p>
            What might modularity look like for small and scattered features in a product
            line? How does one modularize interacting features to still enable flexible
            composition? Does classical logic correspond with how humans think about
            modules? How might alternative modularity mechanisms look like? Can tool
            support replace functions of traditional modularity?
        </p><p>
            We investigated from a conceptual perspective assumptions, costs, and
            limitations of modularity, both in the context of scattered variability
            implementations {cite(fosd11)} and more broadly from a philosophical perspective {cite(ecoop11)}.
        </p>,
        Some("micromod.png")
        //    [micromodularity picture]
        //        Example Insights: For many fine-grained, interacting, or crosscutting implementation the expected benefits of modularity may not justify the required overhead.
    )


    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //////////////////////////////////////////// maintenance /////////////////////////////////////////////


    def maintenanceAndImplOfVariability = Theme("Maintenance and Implementation of Highly-Configurable Systems",
        "We explore a wide range of different variability implementation mechanisms and their tradeoffs; in addition, " +
            "we explore reverse engineering and refactoring mechanisms for variability  " +
            "and support developers with variability-related maintenance:",
        <p>
            We are interested in how to best implement highly-configurable systems and software product lines to enable
            long-term maintenance. There is a huge range of implementation mechanisms, from branches in version control
            systems, to components and plugin systems, to #ifdefs and specialized languages for crosscutting
            implementations, all with their distinct tradeoffs {cite(fosplbook)}. As with much technical debt, many approaches
            that are easy to use in the short-term can turn into maintenance nightmares in the long run. In
            addition to analyzing tradeoffs of existing approaches and developing new implementation approaches,
            we research tooling, reverse engineering, and migration support for the various implementation techniques.
        </p>,
        List(reverseeng, featureflags, fop, configcomplexity, understandingifdef, lotrack, buildsys, modularityfi))


    def reverseeng = ResearchTopic("Reverse Engineering Variability Implementations",
        <p>
            We investigate approaches to recover essential
            information from legacy variability implementations. Product lines are often
            started with ad-hoc implementations such as branches, command-line options, or
            ifdefs without much regard for planning, but developers then often get
            increasingly frustrated with difficult maintenance and increasing complexity.
            In such cases, we intend to help developers in understanding the current
            implementations and supporting migrations to more disciplined mechanisms.
        </p><p>
            We have explored reverse engineering for conditional compilation, command-line
            options, branches, and build systems. We have built an infrastructure to parse
            unpreprocessed C code and we explored mechanisms to enforce more disciplined
            usage of the preprocessor {cite(oopsla11_typechef,tse17)},
            to extract constraints in the configuration space from such implementation {cite(icse14_mining)},
            and to refactor conditional compilation into other
            variability mechanisms {cite(gpce09)}. For load-time parameters like command-line options
            and configuration files, we use a variation of taint tracking to identify
            which (often scattered) statements in Java and Android programs are controlled
            by these options {cite(ase14)}. For branches and clone-and-own development we look
            into merging and feature location techniques to enable an integration of the
            various changes in the branches into disciplined variability mechanisms {cite(fse11,icse18forks)}.
            Finally, we also investigate mechanisms to extract configuration knowledge
            from build systems {cite(releng15,releng16,gpce17)}.
        </p> ,
        Some("splpromise.png")
    )


    def featureflags = ResearchTopic("Feature Flags",
        <p>
            Feature flags (or feature toggles) emerged recently as a design pattern of how to (often temporarily)
            provide variability in software systems for experimentation, for deployment, and
            for trunk-based development. Feature flags are broadly used in practice and
            share many similarities with traditional configuration options, but they also
            have distinct characteristics, for example, with regard to lifetime expectations,
            documentation, and testing.
            We have explored the phenomenon of feature flags and contrasted it with
            traditional configuration options in order to bring researchers in both communities closer
            and to learn from each other {cite(icseseip20, msr20, featureFlagsBlog19)}.
        </p> ,
        Some("featureflags.png")
    )


    def forks = ResearchTopic("Fork-Based Development",
        <p>
            Rather than planning variability in a single implementation, development of different
            variants in separate branches is a common practices, often called clone-and-own.
            We specifically looked into this phenomenon in open source, where it occurs
            frequently as forks on GitHub and other code sharing platforms. While forks are
            used for many different purposes, one often finds different functionality
            in different forks that may or may not be integrated or maintained separately.
            Among others, we have looked into how to detect features within forks {cite(icse18forks)}
            and looked at the history and perception of forking, especially with regard to
            social and hard forks and possible useful tooling {cite(icse20forks)}, as well as
            mechanisms to detect duplicate work {cite(saner19)} (see also our {linkTopic(osscollaboration,Some("Open Source"))}  research).
        </p>,
        Some("forkshistory.png")
        //    [fop collaboration diagram]
    )

    def fop = ResearchTopic("Feature-Oriented Programming",
        <p>
            We have explored alternative language features and composition mechanisms that
            can support implementing features in product lines. In early work, we explored
            aspect-oriented programming as a possible means to implement features
            (identifying lots of problems) {cite(splc07,jot07)} and compared aspect-oriented programming
            with feature-oriented programming {cite(isse07,fosd09_emp,fosplbook)}. Subsequently, we explored mechanisms to
            generalize feature-oriented programming to provide uniform mechanisms across
            multiple languages within the FeatureHouse tool suite {cite(icse09_fh,tse13,scp12)}, discuss modularity
            issues {cite(fosd11,scp12)}, and to perform various quality assurance activities, such as
            variational type checking, for these languages {cite(jase10)}. To support teaching of
            product line development and feature-oriented programming specifically, we
            developed an Eclipse plugin FeatureIDE that makes the languages and tools more
            accessible {cite(icse09_demo,scp14)}.
        </p>,
        Some("fop.png")
        //    [fop collaboration diagram]
    )


    def configcomplexity = ResearchTopic("Assessing and Understanding Configuration-Related Complexity",
        <p>
            We are interested in how developers understand highly-configurable systems and
            how they handle variability-related complexity. Variability introduces
            complexity into a system as developers have to think about multiple
            configurations at the same time. At the same time, the orthogonal nature and
            the huge but finite configuration spaces are different from many other sources
            of complexity. In fact, developers often (but not always) seem to be quite
            capable of handling configuration complexity despite enormous surface
            complexity one would expect from the exponentially growing configuration
            space: What distinguishes manageable from not manageable implementations? We
            have attempted to assess configuration complexity in systems with various
            metrics {cite(icse10,esem11,emse15)}, have analyzed how configurability relates to proneness for bugs
            and vulnerabilities {cite(splc16)}, and how we can support developers to handle the
            complexity with tools and visualizations {cite(emse12,icse11demo_vi,iet12,icse14_emergo,varvis)}.
        </p>,
        Some("vulndist.png")
        //    [distribution plots from gabriels paper]
    )


    def understandingifdef = ResearchTopic("Understanding Preprocessor Use",
        <p>
            We empirically analyze how the C preprocessor is used in practice and how
            developers perceive its use. Conditional compilation with the C preprocessor
            is one of the most commonly used variability-implementation mechanism in
            practice. We argue that, when used with some discipline and supported by
            tooling, conditional compilation can be an easy and effective implementation
            strategy {cite(jot09_vsoc,fosplbook)} (see also {linkTopic(vsoc,Some("virtual separation of concerns"))}), but uncontrolled use
            can hinder understanding, quality assurance, and tool support substantially.
            We have investigated at scale how the C preprocessor is used in practice, both
            in dozens of open source programs {cite(icse10,aosd11)} as well as in industrial software product
            lines {cite(emse15)}. In addition, we have interviewed C programmers to understand how
            they perceive the challenges of the C preprocessor {cite(fosplbook)}.
        </p>,
        Some("undiscipl.png")
        //        [undisciplined vim example]
        //    Insights: Among others, we found that developers strongly favor disciplined usage but nonetheless about 16 percent of all conditional-compilation directives are undisciplined in practice.
    )


    def lotrack = ResearchTopic("Tracking Load-Time Configuration Options",
        <p>
            We track how load-time configuration options are used within a program to
            identify where configuration-related code is actually implemented and which
            configurations interact. Load-time variability, for which configuration
            options are loaded from command-line options or configuration files and
            propagated through normal variables to control-flow decisions, is a common
            implementation strategy for variability. Unfortunately, in growing and
            evolving systems it can be difficult to keep track where the configurable part
            of the system is actually implemented as variability can become hard to
            distinguish from other computations in the system. We developed a specialized
            form of slicing, based on taint tracking, to identify where and how load-time
            options are used for control-flow decisions in the program. We create a
            configuration map that indicates for each line of code under which
            configurations it can be executed, similar to traceability one might expect
            from using conditional compilation directives around those code fragments {cite(ase14,tse17_lotrack)}.
            Exploiting the fact that configuration options are used differently from other
            state in the program, we achieve a highly accurate tracking in most programs.
        </p>,
        Some("lotrack.png") //        [config map from paper]
    )


    def buildsys = ResearchTopic("Build Systems",
        <p>
            We investigate how to extract configuration knowledge from build systems. Build systems control a
            significant amount of compile-time variability in many software systems, but are often encoded in
            various, hard to analyze tools and languages such as make. The complicated build logic of many project
            can make it difficult to answer simple questions, such as, in which configurations is a file even
            compiled or which extra parameters are passed in which configurations. The problem of extracting
            configuration knowledge is limiting many quality assurance and reverse engineering approaches in
            their accuracy. So far, we have pursued a static extraction approach based on symbolic execution
            of make files {cite(releng15)} and investigate other build systems and other analysis strategies {cite(releng16,gpce17)}.
        </p>,
        Some("make.png") //    [some picture from Shurui’s paper]
    )

    def modularityfi = ResearchTopic("Modularity and Feature Interactions",
        <p>
            We investigate how to modularity implement features in a way that avoids accidental feature interactions
            but still allows intended interactions among features. For example, a total isolation of features would
            be possible, but would prevent intended interactions as well. We study various ecosystems to understand
            what implementation mechanisms they use to control interactions, such as Android {cite(mobiledeli15,msr16)}, and explore
            alternative forms of implementation. Among others, we have designed a module system that makes
            variability inside a module and its interface very explicit {cite(oopsla12)}. However, how to best implement
            variability to tame interactions but allow sufficient flexibility is still an open research challenge.
        </p>,
        Some("androidiot.png") //    [android figure from mobiledeli paper?]
    )


    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //////////////////////////////////////////// beyond spl /////////////////////////////////////////////

    def beyondVariability = Theme("Variability Mechanisms Beyond Configurable Software Systems",
        "We explore how analyses developed for variability can solve problems in contexts " +
            "beyond software product lines, such as design space exploration, that share facets of the problem such as large " +
            "finite search spaces with similarities among candidates:",
        <p>
            We are increasingly interested in applying analysis mechanisms developed for configurable systems beyond
            traditional application areas of software product lines. Many technologies that we have developed for large
            finite configuration spaces apply also for other problems that explore large design spaces of different
            options, such as speculative analyses exploring effects of many potential changes and their interactions.
            Among others, we have also found, somewhat surprisingly, that our infrastructure for parsing unpreprocessed
            C code comes in very handy for analyzing PHP code.
        </p>,
        List(php, sensitivity, vtests))


    def php = ResearchTopic("Developer Support and Quality Assurance for PHP",
        <p>
            We developed a technique to analyze HTML, CSS, and Javascript code while it is
            still embedded as string literals in PHP code. PHP web application have the
            interesting characteristic of staged programs, where the server-side PHP code
            is executed to produce a client-side HTML and Javascript program. As a
            consequence, it is difficult to analyze the client-side code before the
            server-side code is executed. To solve this cross-stage analysis, we
            symbolically execute the server-side code to approximate all client-side
            output. The approximated client-side code contains symbolic values and
            decisions from control-flow decisions. Interestingly, this can be interpreted
            similar to unpreprocessed C code with conditional output and we can use the
            same {linkTopic(unpreprocessedC, Some("variational parsers"))} from TypeChef to build variational representations of
            the HTML DOM and JavaScript programs, and we can subsequently build
            variational call graphs on top to enable various forms of tool support from
            navigation, to bug detection, to test quality assessment, to refactoring, and to slicing {cite(fse14,fse15_webslice,icse15demo,jase18)}. This is a
            perfect example, where techniques originally developed for product lines can
            help to solve problems entirely outside the product line domain, in this case
            the analysis of staged programs.
        </p>,
        Some("varis.png") //        [ide snapshot from demo/parsing keynote]
        //        Tool: varis
    )

    def sensitivity: ResearchTopic = ResearchTopic("Sensitivity Analysis",
        <p>
            Performance analysis of highly configurable systems can be generalized as a
            form of sensitivity analysis. The same techniques we use to learn
            {linkTopic(performance, Some("performance-influence models"))} can be used to understand design spaces and
            consequences of changes in domains outside of traditional product lines, as
            long as we can evaluate the impact of these changes in an interpreter. For
            example, we can model which changes to parameters or variable types in a
            robotics or high-performance computing have a significant impact on
            performance or accuracy {cite(seams19,ieeesw19,wsr16)}. There is a broad field of potential application areas
            that potentially share similar characteristics about the nature of
            interactions of changes, where both black-box and white-box sensitivity-
            analysis techniques for highly-configurable systems can be reused.
        </p>,
        Some("hpc.png") //    [cobot picture?]
    )

    def vtests = ResearchTopic("Mutation Testing and Program Repair",
        <p>
            We explore variational-analysis techniques for exploring the effect and the
            interactions of multiple potential changes to a software system. By encoding
            each potential change as a configuration decision, we can use techniques such
            as variational execution to see which change or which combination of changes
            break a test. We found that these techniques allow efficient exploration
            of large search spaces, as used for mutation-based testing or automatic
            program repair {cite(fse20_hom,fsenier18)}.
        </p>,
        Some("genprog.png") //        [genprog picture?]
    )

    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //.
    //////////////////////////////////////////// misc /////////////////////////////////////////////


    def otherTopics = Theme("Other Topics",
        "We have collaborated on a number of other software engineering and programming languages topics, " +
            "including dynamic software updates, extensible domain-specific languages, " +
            "software merging, and various empirical methods topics:",
        <p>
            We are generally open to research and collaborations in a broad spectrum of software engineering questions.
            We have worked on dynamic software updates {cite(spe13)}, embedded and extensible domain specific languages {cite(oopsla11_sugarj, gpce11)},
            software merging {cite(fse11,ase17merge,jase17)}, and exploring various empirical methods, including assessing
            program comprehension as a confounding factor {cite(emse14)}, self-adaptation in robots {cite(seams19,ieeesw19)},
            and using fMRI scanners {cite(icse14_fmri,fse17,tse18_fmri)}.
        </p>,
        List(fmri)
    )


    def fmri = ResearchTopic("Understanding Program Comprehension with fMRI",
          <p>
              We analyzed how developers understand
              programs by directly observing brain area activations inside an fMRI scanner. While our initial project was
              focused on establishing the feasibility of fMRI as an technique for studying program comprehension (we
              confirmed many expected activations regarding short-term memory and language comprehension), this line
              of research can potentially help us in the long run to understand what makes programs complex and what
              form of complexity developers can handle well and what forms they struggle with. It may have further
              implications about how to teach programming or design programming languages {cite(icse14_fmri,fse17,tse18_fmri)}.
          </p>,
        Some("fmri.png")
    )


    val typechef = Software("TypeChef", "Parsing and Analyzing Unpreprocessed C Code", URL("https://github.com/ckaestne/TypeChef"))
    val varex = Software("Varex", "Variational Execution for PHP", URL("https://github.com/git1997/VarExecution"))
    val varexj = Software("VarexJ", "Variational Execution for Java, built on top of Java Pathfinder", URL("https://github.com/meinicke/VarexJ"))
    val cide = Software("CIDE", "Feature-Oriented Analysis and Decomposition of Legacy Code", URL("http://fosd.net/cide/"))


}
