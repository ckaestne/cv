package de.stner.cv

import java.io.File
import de.stner.cv.GenPubList.TextFormater

import scala.xml.{NodeSeq, Text}


object ResearchStructure {

    /** A top-level research area shown on the front page and the research page. */
    case class ResearchArea(eyebrow: String, title: String, description: NodeSeq, keywork: NodeSeq, foundational: Boolean = false) {
        def key = stringToKey(title)
    }

    def stringToKey(s: String) = s.split("\\s").map(_.replaceAll("[^\\p{L}\\p{Nd}]+", "")).filter(_.length > 3).take(4).map(_.capitalize).mkString

}

object ResearchGenHtml extends App {

    import de.stner.cv.GenHtml._
    import de.stner.cv.Research._
    import de.stner.cv.ResearchStructure._


    def researchPage = printTitle() ++ printResearchBlock()

    /** The research section, shared by the front page and the standalone research page. */
    def printResearchBlock(): NodeSeq =
        rowH2("Research", "research") ++
            row(null, <p>{researchIntro}</p>) ++
            row(null, <div class="researchtheme">{imperfectModularity}</div>) ++
            areas.flatMap(printArea)

    def printArea(area: ResearchArea): NodeSeq =
        row(null,
            <div class={if (area.foundational) "researcharea foundational" else "researcharea"}>
                <a name={area.key} class="sectionanch"></a>
                <div class="eyebrow">{area.eyebrow}</div>
                <h3>{area.title}</h3>
                {area.description}
                <div class="keyrefs">{area.keywork}</div>
            </div>, "researcharea")

    def targetPath = new File("target/site")

    printDoc(researchPage, "Research :: " + CV.name + " :: CMU", new File(targetPath, "research.html"))

}


object Research {

    import de.stner.cv.CVPublications._
    import de.stner.cv.ResearchStructure._
    import de.stner.cv.CVMedia._

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


    // ------------------------------------------------------------------
    //  Front-page research narrative: an intro, a unifying theme, and
    //  three research areas (current work first, foundational work last).
    // ------------------------------------------------------------------

    def researchIntro: NodeSeq =
        <span>My broad goal in research is to help software engineers build complex systems in the real
            world, and to do so responsibly. My group and I work in three main areas: engineering
            AI-powered software systems, software supply-chain security, and scaling software variability and
            reuse. Methodologically, I lean on empirical methods and pragmatic tool building, often in
            interdisciplinary collaborations spanning AI, human-computer interaction, security, and the social
            sciences.</span>

    def imperfectModularity: NodeSeq =
        <span><strong>A unifying theme: <em>imperfect modularity.</em></strong> Modularity is our central
            principle for managing complexity, but it rests on assumptions that are often unrealistic in real
            systems: stable requirements, interface specifications that capture all interactions, and internals
            that can be hidden behind clean abstractions. Much of my work explores exactly the places where
            modularity breaks down (feature interactions, breaking changes across ecosystems, and
            AI components that are fundamentally anti-modular) and how tooling, evidence, and
            process can help developers cope.</span>

    def areas: List[ResearchArea] = List(mlai, opensource, variability)


    def mlai = ResearchArea(
        "Current focus",
        "AI Engineering / AI-Powered Software Systems",
        <p>
            Software systems increasingly rely on AI components for core functionality, opening up
            capabilities that were previously out of reach, but also breaking traditional assumptions about
            specifications, testing, and modularity. AI components are fundamentally unreliable, yet the
            engineering needed to build dependable systems <em>around</em> them is rarely taken seriously in
            practice. Much of my research and teaching focuses on how to responsibly build software with AI
            components, sometimes called AI engineering: improving collaboration between software engineers
            and data scientists, grounding testing in requirements engineering, and designing for safety, risk,
            and production operation. More recently, AI coding agents are disrupting how software itself is built,
            raising the urgency further.
        </p>,
        <span>Representative work: interdisciplinary collaboration {cite(icse22_seai)}, 
            grounding model testing in system requirements {cite(emnlp23,ase24,icsenier23,acl26)},
            anticipating and mitigating model mistakes {cite(chi26,cain25,arxiv26_guardrails)}, and
            the open-access textbook <em>Machine Learning in Production</em>
            {cite(mlipbook)}. <a href="publications.html">Related publications &rarr;</a></span>
    )


    def opensource = ResearchArea(
        "Current focus",
        "Software Supply Chain Security / Open Source Sustainability",
        <p>
            Nearly all modern software is built on open source, the critical infrastructure maintained largely by
            volunteers with no formal obligation to the many projects that depend on them. This makes the software
            supply chain a growing security concern: malicious package updates, compromised dependencies, and fake
            or abandoned projects can ripple across the ecosystem, while the trust signals developers have long
            relied on are eroding. My research studies these threats empirically and builds interventions that
            help: lightweight sandboxes for risky dependencies, tools that flag suspicious updates, and
            notifications for abandoned dependencies. This work sits within a broader interest in how open-source
            communities collaborate, sustain themselves, and cope with change. 
            This work is supported by the NSF Frontier <a href="https://s3c2.org" title="Secure Software Supply Chain
            Center">Secure Software Supply Chain Center (S3C2)</a>.
        </p>,
        <span>Representative work: sandboxing {cite(icse21_malicious)},
            fake stars {cite(icse26_fakestars)}, dependency abandonment {cite(fse23,icse25_abandonment,
            icse26_abandonment)}, and coordination across forks in open source {cite(fse19forks, icse20forks)}.
             &mdash;
            <a href="publications.html">related publications &rarr;</a></span>
    )


    def variability = ResearchArea(
        "Earlier work",
        "Scaling Software Variability and Reuse",
        <p>
            Variability and reuse are how complex modern software scales: a single configurable codebase like the
            Linux kernel or a modern web framework can serve millions of distinct use cases. But that leverage
            comes at a cost when thousands of interacting options create combinatorial spaces that resist
            traditional testing, leaving defects that hide in specific configurations. My earlier research
            championed pragmatic, disciplined variability management with tooling rather than new language
            constructs, and showed how to scale analyses across exponential configuration spaces&mdash;such as
            type-checking all 2<sup>14k</sup> compile-time configurations of the Linux kernel using SAT solvers.
            The tools we built, like CIDE and TypeChef, were adopted widely in the research community.
        </p>,
        <span>Representative work: variability-aware parsing and analysis that scales to the Linux kernel
            {cite(oopsla11_typechef,tosem18)}, testing and debugging highly configurable systems 
            {cite(icse22_perf,icse16,tse17_lotrack)},
            and performance-influence models {cite(fse15_influence,fse18)}. &mdash;
            <a href="publications.html">related publications &rarr;</a></span>,
        foundational = true
    )

}
