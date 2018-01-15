package de.stner.cv

import java.io.File
import java.net.URI
import java.text.SimpleDateFormat

import de.stner.cv.StructureTheses.AThesis
import org.apache.commons.io.FileUtils

import scala.xml._
import scala.xml.dtd.{DocType, PublicID}


object GenHtml extends App with RSSFeed {

    import CV._
    val pageRoot = new URI("https://www.cs.cmu.edu/~ckaestne/")



    trait PlainHtmlFormater extends Formater[NodeSeq] {
        def title(t: String): NodeSeq = <strong>{t}</strong>

        def journal(s: String): NodeSeq = <em>{s}</em>

        def concat(c: NodeSeq*): NodeSeq = c.flatten

        def concatL(c: Seq[NodeSeq]): NodeSeq = c.flatten

        def newBlock: NodeSeq = Text("")

        def text(s: String): NodeSeq = Text(s)

        def person(person: Person): NodeSeq = Text(person.fullname)

        def markdown(m: String): NodeSeq = m.markdownToHtml
    }

    object FancyPersonHtmlFormater extends PlainHtmlFormater {
        override def person(person: Person): NodeSeq = renderPerson(person)

        def renderPerson(person: Person, fullname: Boolean = false, htmlClass: String = "author"): NodeSeq = {
            val name = if (fullname) person.fullname else person.abbrvname
            if (person.url.isDefined && person.affiliation.isDefined)
                <a href={person.url.get.toString()} class={htmlClass} title={person.fullname + " • " + person.affiliation.get}>{name}</a>
            else if (!person.url.isDefined && person.affiliation.isDefined)
                <span class={htmlClass} title={person.fullname + " • " + person.affiliation.get}>{name}</span>
            else if (person.url.isDefined && !person.affiliation.isDefined)
                <a href={person.url.get.toString()}  class={htmlClass} title={person.fullname}>{name}</a>
            else
                <span class={htmlClass} title={person.fullname}>{name}</span>
        }
    }


    def printCourse(course: Course, withKind: Boolean = false): Elem =
        <span>
        {if (course.url != null)
            <a href={course.url.toString()}>{course.title}</a>
        else course.title}
        {(if (course.kind.isSeminar) "-- Seminar"
        else if (withKind) {
            if (course.kind.isLecture && course.kind.isExercise) "-- Lecture + Exercise"
            else if (course.kind.isLecture) "-- Lecture"
            else if (course.kind.isExercise) "-- Exercise"
            else if (course.kind.isTutorium) "-- TA"
            else ""
        } else "").markdownToHtml}
    </span>

    def printTeaching(teaching: Seq[Course]): NodeSeq = {
        val byTerm = teaching.groupBy(_.term)
        for (term <- byTerm.keys.toSeq.sorted.reverse) yield row(<span>{term}</span>,
      <ul>{
           for (course <- byTerm(term))
               yield <li>{printCourse(course)}</li>
         }</ul>)
    }.flatten


    def printThesis(thesis: AThesis) =
        <dd>
          <a name={thesis.genKey} class="sectionanch"></a>{thesis.author.fullname}.
          <strong>{ thesis.title.markdownToHtml}</strong>{thesis.title.endDot()}
            {thesis.kind.name}, {thesis.where.name}, {thesis.where.country}, {thesis.monthStr} {thesis.year}.
            {if (!thesis.note.isEmpty) <em>{thesis.note.markdownToHtml}</em> }
        [&nbsp;<a href={"./bibtex.html#" + thesis.genKey}>bib</a>
            {if (thesis.pdf != null)
            <span>| <a href={thesis.pdf.toString()}>.pdf</a></span>
            }]
        </dd>


    def printSupervisedTheses(theses: Seq[AThesis]) = rowH2(
        "Supervised Theses", "theses",
          <div class="bib"><dl>
              {for (thesis <- theses.reverse) yield printThesis(thesis) }
              </dl></div> )


    def getPublicationClassTags(p: Publication): String = {
        var tags = Seq("pub")
        tags = (if (p.isSelected) "selected" else "regular") +: tags
        tags = p.topics.map(_.key) ++ tags
        tags = p.venue.kind.key +: tags
        tags.mkString(" ")
    }


    def printPublication(p: Publication): NodeSeq = {
        val links = p.links + (BIB -> URL("./bibtex.html#" + p.genKey))
               <dd class={getPublicationClassTags(p)} id={p.genId}><div>
                  <a name={p.genKey} class="sectionanch"></a>
                  {p.render(DefaultBibStyle, FancyPersonHtmlFormater)}
               [&nbsp;{
                   for ((key, url) <- links.dropRight(1))
                       yield <a href={url.toString}>{key.print}</a> :+ ", "
               } <a href={links.last._2.toString}>{links.last._1.print}</a> ]
                   {if (!p.isHideAbstract && p.abstr != "")   <blockquote><p>{p.abstr.markdownToHtml}</p></blockquote> }
               </div></dd>
    }

    def printPublicationRow(p: Publication): NodeSeq = row(
    <span class="pubshort">{p.venue.short} {p.venue.year}</span>,
    <div class="bib"><dl>{printPublication(p)}</dl></div>
    )

    private def sep =  <span class="sep">|</span>

    //get all topics, sorted by number of publications
    def getTopics(pubs: Seq[Publication]): Seq[(Topic, Int)] =
        pubs.flatMap(p => p.topics.map((_, p))).groupBy(_._1).mapValues(_.size).toSeq.sortBy(_._2).reverse

    def getKinds(pubs: Seq[Publication]): Seq[(PublicationKind, Int)] =
        pubs.groupBy(_.venue.kind).mapValues(_.size).toSeq.sortBy(_._1)

    def printFilterHeader(p: Seq[Publication]) = <div id="pubfilter" style="display:none">
        <form action="javascript:updatepub()" method="post" >
           <ul>
              <li >
        	     <strong>Filter:</strong>
        		 <span style="clear:left;"><input type="radio" id="filter_allpub" value="All publications" name="filter_selected" checked="checked" /><label for="filter_allpub">All publications</label></span>
        		 <span style="clear:left;"><input type="radio" id="filter_keypub" value="Key publications" name="filter_selected"                   /><label for="filter_keypub">Key publications</label></span>
                 {sep}
                 <label for="filter_topic">By topic: </label>
                 <select style="width:150px" id="filter_topic">
                    <option value="all"  selected="selected">All</option>
                    {for (t <- getTopics(p)) yield <option value={t._1.key}>{t._1.name} ({t._2})</option> }
                 </select>
                 {sep}
                 <label for="filter_kind">By publication</label>
                 <select style="width:150px" id="filter_kind">
                    <option value="all" selected="selected">All</option>
                    {for (t <- getKinds(p)) yield <option value={t._1.key}>{t._1.name} ({t._2})</option> }
                  </select>
              </li>


              <li class="form-line" id="id_6">
                  <strong>Group by:</strong>

                  <span><input type="radio" id="nogroup"   name="q6_groupBy" value="None (chronologically)" checked="checked" /><label for="nogroup"  >None (chronologically)</label></span>
                  <span><input type="radio" id="groupKind" name="q6_groupBy" value="Publication kind" />                        <label for="groupKind">Publication kind</label></span>
                  <span><input type="radio" id="groupYear" name="q6_groupBy" value="Year" />                                    <label for="groupYear">Year</label></span>
                  <span><input type="radio" id="groupTop"  name="q6_groupBy" value="Topic" />                                   <label for="groupTop" >Topic</label></span>
              </li>

              <li class="form-line" id="id_3">
                <span style="clear:left;"><input type="checkbox" id="showabstracts" checked="checked" /><label for="showabstracts">Show abstracts</label></span>
              </li>
            </ul>
        </form>
    </div>

    def printGroupingHeader(title: String, pubs: Seq[Publication]) =
        "{title:\"%s\",pubs:[%s]}".format(title, pubs.map("\"" + _.genId + "\"").mkString(","))

    // <div><h3>{title}</h3>{ pubs.map(_.genId).mkString(",")}</div>
    def printGroupingHeaders(pubs: Seq[Publication]) = {
        "function pubheaderByYear() { return [%s]; }".format((for (g <- pubs.reverse.groupBy(_.venue.year).toSeq.sortBy(_._1).reverse) yield printGroupingHeader(g._1.toString, g._2)).mkString(",")) +
            "function pubheaderByKind() { return [%s]; }".format((for (g <- pubs.reverse.groupBy(_.venue.kind).toSeq.sortBy(_._1)) yield printGroupingHeader(g._1.name, g._2)).mkString(",")) +
            "function pubheaderByTopic() { return [%s]; }".format((for (g <- pubs.reverse.flatMap(p => p.topics.map(t => (t, p))).groupBy(_._1).toSeq.sortBy(_._2.size).reverse) yield printGroupingHeader(g._1.name, g._2.map(_._2))).mkString(","))
    }

    def printCopyrightNotice() = <p class="copyrightnotice">Copyright Notice: This material is presented to ensure timely
              dissemination of scholarly and technical work. Copyright and all rights
              therein are retained by authors or by other copyright holders. All
              persons copying this information are expected to adhere to the terms
              and constraints invoked by each author's copyright. In most cases,
              these works may not be reposted without the explicit permission of the
              copyright holder.</p>

    // or this <a href={URL("http://www.informatik.uni-marburg.de/~kaestner/publist.pdf").toString}>.pdf</a>
    def printKeyPublications(pubs: Seq[Publication]): NodeSeq =
        rowH2_(<span>Selected Publications {rssLogo("pub.rss", "Full publication feed")}</span>, "publications",
        <p>For a complete list of publications, see the <a href="publications.html">publication page</a>.</p>) ++ {
            for (p <- pubs.filter(_.isSelected).reverse) yield printPublicationRow(p)
        }.flatten ++
            row(null,
                <p><a href="publications.html">more...</a></p> ++
                    printCopyrightNotice())

    //        A full publication list is available as <a href={URL("http://www.informatik.uni-marburg.de/~kaestner/publist.pdf").toString}>.pdf</a>.</p>
    def printPublications(pubs: Seq[Publication]) =
        rowH2_(<span>Publications {rssLogo("pub.rss", "Publication feed")}</span>, "publications",
    <div class="bib">
        <p>Key publications highlighted in yellow.</p>
        {printFilterHeader(pubs)}
        <dl id="pubmain" class="fullpublist">
        {for (p <- pubs.reverse) yield printPublication(p)}
        </dl>
        <div id="pubgen"></div>
        {printCopyrightNotice()}
    </div>)

    def printCommittee(c: Committee, comma: Boolean) = <span><span class="committee"><a href={c.venue.url.getOrElse(".").toString()} title={c.venue.name}>{c.venue.short}&nbsp;{c.venue.year}</a>&nbsp;(<span title={c.role.map(_.title).mkString(", ")}>{c.role.map(_.abbreviation).mkString(", ")}</span>)</span>{if (comma) ","} </span>

    def printCommitteePicture(): NodeSeq = 
      <a href="http://program-transformation.org/GPCE13">
    <img title="Generative Programming and Component Engineering 2013" src="GPCE-2013.png" alt="GPCE2013" width="180" height="220" />
    </a>

    def printCommittees(committees: Seq[Committee]) = rowH2("Service", "service",
            <div>{
                for (c <- committees_conferences.dropRight(1))
                    yield printCommittee(c, true)
            }{printCommittee(committees_conferences.last, false)}</div> <div>&nbsp;</div>
                <div>{
                for (c <- committees_workshops.dropRight(1))
                    yield printCommittee(c, true)
            }{printCommittee(committees_workshops.last, false)}</div>/*,
        printCommitteePicture()*/
    )


    def printPrivate(): NodeSeq = rowH2(
        "Private Interests", "private",
        <p><a href={URL("http://www.flickr.com/photos/p0nk/sets/72157627689187184/").toString}>Jugg</a><a href={URL("juggling.xhtml").toString}>ling</a>,
        <a href={URL("http://www.flickr.com/photos/p0nk/sets/72157611890103649/").toString}>Cooking</a>,
        <a href={URL("http://boardgamegeek.com/collection/user/chk49").toString}>Board games</a>,
        <a href={URL("concerts.xhtml").toString}>Concerts</a>
        </p> ++ printTwitterWidget())

    def printSpelling() =
        <p class="spelling">
        My last name Kästner is pronounced <a href="http://en.wikipedia.org/wiki/Wikipedia:IPA_for_German">[ˈkɛstnɐ]</a>.
        It is a quite common German last name, well known for the author and poet <a href="http://en.wikipedia.org/wiki/Erich_Kästner">Erich Kästner</a>.
        The umlaut <strong style="font-size:+2">ä</strong> is signficiant for the pronunciation.
        The valid ASCII spelling is <span class="code">Kaestner</span>, not <span class="code">Kastner</span>.
        To correctly typeset the name in LaTeX use <span class="code">K{{\"a}}stner</span>.
        To create the &auml; in Windows enter 0228 while pressing the ALT key or simply copy it from this page. <a href="javascript:toggleSpelling()" style="font-size:small;">[close]</a>
        </p>

    def printSpellingLink() = <span style={"position:absolute;top:" + navBarHeight}><a href="spelling.html" id="spellinglink">[pronunciation and spelling]</a></span>


    val monthyear = new SimpleDateFormat("MMM. yyyy")

    def printAward(award: AwardOrGrant) = row(<span class="date">{monthyear format award.date}</span>,
        <a href={award.url.toString}>{award.name.markdownToHtml}</a> :+ {
            award match {
                case Award(_, _, _, extraLinks, _) if !extraLinks.isEmpty =>
                    <span> ({
                    for (ex <- extraLinks.dropRight(1))
                        yield <span><a href={extraLinks.head._1.toString}>{extraLinks.head._2}</a>, </span>
                    }{<span><a href={extraLinks.last._1.toString}>{extraLinks.last._2}</a></span>})</span>
                case Grant(_, _, _, from, to, ag, budget) =>
                    <span>{" " + ag + ". " + (monthyear format from) + " – " + (monthyear format to) /*+", "+(new DecimalFormat("###,###").format(budget.value).replace(",", " "))+(
                        budget match { case EUR(_) => " EUR"; case USD(_) => " USD"}
                        )*/}</span>
                case _ => <span></span>
            }
        }
    )

    def printAwards(awards: Seq[AwardOrGrant]) = rowH2("Grants & Awards", "awards") ++ {
        for (r <- awards) yield printAward(r)
    }.flatten


    def printProjects(pr: Seq[(URL, String, String, Option[String])]): NodeSeq =
        rowH2("Research Projects & Software", "software") ++ {
            for (p <- pr) yield
                row(<span class="toolname">{p._2}</span>,
                  <div><a href={p._1.toString}>{p._3}</a>{if (p._4.isDefined) " (" + p._4.get + ")"}</div>, "tool")
        }.flatten

    def printTitle(spellingHint: Boolean = false, withLink: Boolean = false): NodeSeq = {
        def addLink(v: NodeSeq) = if (withLink) <a href="http://www.cs.cmu.edu/~ckaestne/">{v}</a> else v

        <div class="grid_3 ">&nbsp;</div> :+
        	<div class="grid_9 header headline">
                {if (spellingHint)
        <div>{addLink(<h1 style="display: inline;" itemprop="name">Christian Kästner</h1>)} {printSpellingLink}</div>
            else addLink(<h1 itemprop="name">Christian Kästner</h1>)}
                <div id="spellingbox" style="display:none">{printSpelling()}</div>
                <p><span itemprop="role">Assistant Professor</span> · <span itemprop="affiliation">Carnegie Mellon University</span> · Institute for Software Research</p>
                <meta itemprop="url" content="http://www.cs.cmu.edu/~ckaestne/" />
        	</div> :+
        	<div class="clear margin_40">&nbsp;</div>
    }

    def nbsp: Node = <span>&nbsp;</span>.child.head

    def row(left: NodeSeq, right: NodeSeq, marginafter: String = "") =
        <div class="grid_3 right">{if (left == null) nbsp else left}</div> :+
        	<div class="grid_9">{if (right == null) nbsp else right}</div> :+
        	<div class={if (marginafter.length > 0) "clear margin_" + marginafter else "clear"}>&nbsp;</div>

    def printPicture(): NodeSeq = <img src="me.jpg" class="sideimg" alt="Christian Kästner" />

    def printGroupPicture(): NodeSeq = <a href="group17.jpg"><img src="group17-min.jpg" alt="Team photo Dec. 2017" class="sideimg" /></a>

    def rowH2(title: String, anchor: String, body: NodeSeq = Nil, leftExtra: NodeSeq = null): NodeSeq = rowH2_(Text(title), anchor, body, leftExtra)

    def rowH2_(title: Node, anchor: String, body: NodeSeq = Nil, leftExtra: NodeSeq = null): NodeSeq = <div class="clear margin_h2">&nbsp;</div> +: row(leftExtra, <h2><a name={anchor} class="sectionanch"></a>{title}</h2> ++: body)

    def printNews(full: Boolean = false): NodeSeq = rowH2_(<span>News {rssLogo("news.rss", "News feed")}</span>, "news") ++ {
        val news = if (full) News.news else News.news.take(3)
        for (newsItem <- news)
            yield row(
                {<span class="newsdate">{new SimpleDateFormat("d MMM. yyyy") format newsItem.date}</span>}, {
                    <div class="newsheadline"><a name={newsItem.getID()}  class="sectionanch"></a>{newsItem.title}</div> :+
            <div class="newsbody">{newsItem.body}</div>
                }, "newsitem")
    }.flatten ++ {
        row(null, if (!full) <a href="news.html">Older News...</a> else <a href="index.html">Back...</a>)
    }

    def printTeachingSummary(teaching: Seq[Course]) =
        rowH2("Teaching", "teaching") ++
            printTeaching(teaching.filter(_.term >= WinterTerm(2014))) ++
            row(null,
                <div>See also the full <a href="teaching.html">teaching history</a>.</div>
                )

    def printCoolWall() =
        rowH2("FOSD Cool Wall", "coolwall",
            <p>The cool wall was created and evolved during the yearly FOSD  meetings (see <a href="http://fosd.net">fosd.net</a>). With it, we encourage researchers to look for better tool names. Up to 2012, the listing was completely subjective, feel free to complain. Starting 2013, we started <a href="coolwallvoting.jpg">voting</a>. In 2013 and 2014 we even gave out a <a href="coolwallaward.jpg">Coolest Tool Name award</a>. Unfortunately, the 2014 listing is incomplete, as the photos of the votes got lost.</p> :+
        <a href="coolwall2016.pdf"><img src="coolwall2016.png" alt="Cool Wall 2016" id="coolwall" /></a>)


    def printTwitterWidget(): NodeSeq =
    <p><a class="twitter-timeline"  href="https://twitter.com/p0nk"  data-widget-id="252400439511879680">twitter</a>
      <script>{"""!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+'://platform.twitter.com/widgets.js';fjs.parentNode.insertBefore(js,fjs);}}(document,'script','twitter-wjs');"""}</script>
    </p>


    def printFullBibtex(): NodeSeq =
        for (p <- CV.publications)
            yield <div><a name={p.genKey} class="sectionanch"></a><pre>{p.toBibtex()}</pre></div>

    def printStudents(students: List[(String, List[(Person, Option[String])])]): NodeSeq =
        rowH2("Team", "team") ++ students.flatMap(printStudentSection)

    private def printStudentSection(s: (String, List[(Person, Option[String])])): NodeSeq =
        row(<span>{if (s._1=="Current") printGroupPicture() else s._1}</span>,
        <ul>{for ((student, str) <- s._2) yield <li>{FancyPersonHtmlFormater.renderPerson(student, true, "")} {str.getOrElse("")}</li>}</ul>)


    def mainPage: NodeSeq =
        <div itemscope="" itemtype="http://data-vocabulary.org/Person">{
        printTitle(true) ++
            row(printPicture(), printSummary())}</div> ++
            printNews() ++
            ResearchGenHtml.printResearchOverview(Research.themes) ++
            printTeachingSummary(teaching) ++
            printStudents(students) ++
            printCommittees(committees) ++
            //            printAwards(awards) ++
            //            printProjects(projects) ++
            printKeyPublications(publications) ++
            printCoolWall() ++
            printPrivate()


    def teachingPage: NodeSeq = printTitle() ++ rowH2("Teaching History", "teachinghistory") ++ printTeaching(teaching)

    def newsPage: NodeSeq = printTitle() ++ printNews(true)

    case class Nav(title: String, link: String, subnav: List[Nav] = Nil)

    def navigationLinks = List(
        Nav("News", "index.html#news"),
        Nav("Research", "index.html#researchoverview",
            Nav("Overview", "index.html#researchoverview") ::
                Research.themes.map(t => Nav(t.title, "research.html#" + t.key))),
        Nav("Publications", "index.html#publications", List(
            Nav("Selected Publications", "index.html#publications"),
            Nav("All Publications", "publications.html"))),
        Nav("Teaching", "index.html#teaching", List(
            Nav("Current Teaching", "index.html#teaching"),
            Nav("Teaching History", "teaching.html"))),
        Nav("Team", "index.html#team"),
        Nav("Misc", "index.html#coolwall", List(
            Nav("Articles", "articles.html"),
            Nav("Service", "index.html#service"),
            Nav("FOSD Cool Wall", "index.html#coolwall"),
            Nav("Juggling", "juggling.xhtml"),
            Nav("Other Interests", "index.html#private")))
    )

    private def renderNav(linkToRoot: URI)(n: Nav): NodeSeq = {
        val l = <a href={(linkToRoot resolve n.link).toASCIIString}>{n.title}</a>
        if (n.subnav.isEmpty) l
        else
           <span class="dropdown">{l}<ul class="dropdown-content">{
                for (sn <- n.subnav) yield
                    <li><a href={(linkToRoot resolve sn.link).toASCIIString}>{sn.title}</a></li>
                }</ul>
           </span>
    }


    def navigationBar(pathToRoot: URI) =
        <div class="navbar">
            <div class="container_12">
                <div class="grid_3 right"><a href={(pathToRoot resolve "index.html#").toASCIIString}>Christian Kästner</a></div>
                <div class="grid_9">{navigationLinks.map(renderNav(pathToRoot)).reduce(_ ++ Text(" · ") ++ _)}</div>
            </div>
        </div>

    def navBarHeight = "6ex"

    def printDoc(body: NodeSeq, title: String, file: File, extraHeader: NodeSeq = null, pathToRoot: URI = pageRoot) = {

        val doct = DocType("html", PublicID("-//W3C//DTD XHTML 1.0 Strict//EN", "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"), Nil)

        val doc =
        <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
                <link rel="stylesheet" type="text/css" media="all" href={(pathToRoot resolve "css/reset.css").toASCIIString} />
                <link rel="stylesheet" type="text/css" media="all" href={(pathToRoot resolve "css/text.css").toASCIIString} />
                <link rel="stylesheet" type="text/css" media="all" href={(pathToRoot resolve "css/960.css").toASCIIString} />
                <link rel="stylesheet" type="text/css" media="all" href={(pathToRoot resolve "css/jquery.tweet.css").toASCIIString} />
                <link href='https://fonts.googleapis.com/css?family=Roboto:400,700,400italic|Roboto+Slab:400,700' rel='stylesheet' type='text/css' />
                <script type="text/javascript" src={(pathToRoot resolve "js/jquery-1.7.2.min.js").toASCIIString}></script>
                <script type="text/javascript" src={(pathToRoot resolve "js/script.js").toASCIIString}></script>
                {extraHeader}
                <title>{title}</title>
            </head>
            <body>{navigationBar(pathToRoot)}<div class="container_12" style={"margin-top:" + navBarHeight}>{body}</div></body>
        </html>
        scala.xml.XML.save(file.getAbsolutePath(), doc, "UTF-8", doctype = doct)
    }

    def pubPage = CV.url + "publications.html"

    def getJSHeaderPublications() = <script type="text/javascript" src="js/pubfilter.js"></script> :+ <script type="text/javascript">{ scala.xml.Unparsed(printGroupingHeaders(publications)) }</script>

    def printArticles(articleDir: File, targetPath: File) {
        val articles = for (articleSubdir <- articleDir.listFiles(); if articleSubdir.isDirectory) yield {
            val contentFile = new File(articleSubdir, "index.xml")
            assert(contentFile.exists(), "index.xml not found in " + articleSubdir)
            val xml = XML.loadFile(contentFile)
            val title = (xml \\ "article" \ "title").text
            val dateNode = xml \\ "article" \ "date"
            val content = xml \\ "article" \ "content"
            assert(title.nonEmpty, "no title defined")
            assert(title.nonEmpty, "no content defined")

            val targetDir = new File(targetPath, articleSubdir.getName)
            targetDir.mkdir()
            val body = printTitle(withLink = true) ++
                rowH2(title, ResearchStructure.stringToKey(title), content, <span>{dateNode.text}</span>) ++
                printCommentWidget() ++
                row(nbsp, <a href="..">back to main page</a>)
            printDoc(body, title.toString(), new File(targetDir, "index.html"), pathToRoot = pageRoot)

            val parserSDF=new SimpleDateFormat("d MMM yyyy")
            val date = parserSDF.parse(dateNode.text)

            (title, articleSubdir.getName, dateNode.text, date)
        }

        val articleList =
                for ((title, dir, date, _)<- articles.sortBy(_._4)) yield
                    row(<span>{date}</span>,  <p><a href={dir+"/"}>{title}</a></p>)

        printDoc(printTitle(withLink = true) ++
            rowH2("Articles", ResearchStructure.stringToKey("Articles")) ++ articleList.toList.flatten,
            "Articles", new File(targetPath, "articles.html"))
    }



    def printCommentWidget() = row(nbsp, <div id="disqus_thread"></div> :+
    <script type="text/javascript"><!--
        var disqus_shortname = 'christiankaestner'; // required: replace example with your forum shortname
        (function() {
            var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
    dsq.src = '//' + disqus_shortname + '.disqus.com/embed.js';
    (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
        })();
    --></script> :+
    <noscript>Enable JavaScript to view <a href="http://disqus.com/?ref_noscript">comments.</a></noscript>
    )



    /** central build activities */
    println("copying files.")
    val targetPath = new File("target/site")
    targetPath.mkdirs()
    FileUtils.cleanDirectory(targetPath)

    FileUtils.copyDirectory(new File("src/main/site"), targetPath)

    val pdfDir = new File(targetPath, "pdf")
    pdfDir.mkdir()
    FileUtils.copyDirectory(new File("src/main/pdf"), pdfDir)

    val articleDir = new File("src/main/article")
    FileUtils.copyDirectory(articleDir, targetPath)

    println("generating html.")
    printDoc(mainPage, CV.name + " :: CMU", new File(targetPath, "index.html"), getNewsRSSHeader() ++ getPubsRSSHeader())
    printDoc(teachingPage, CV.name + " :: Teaching :: CMU", new File(targetPath, "teaching.html"))
    printDoc(newsPage, CV.name + " :: News :: CMU", new File(targetPath, "news.html"))
    printDoc(printTitle() ++ row(null, printSpelling()), CV.name + " :: Spelling", new File(targetPath, "spelling.html"))
    printDoc(printTitle() ++ printPublications(publications) ++ printSupervisedTheses(advisedTheses), CV.name + " :: Publications :: CMU", new File(targetPath, "publications.html"), getJSHeaderPublications() ++ getPubsRSSHeader())
    printDoc(printTitle() ++ row(null, printFullBibtex()), CV.name + " :: Bibtex", new File(targetPath, "bibtex.html"))
    printNewsFeed(new File(targetPath, "news.rss"))
    printPubsFeed(new File(targetPath, "pub.rss"))
    printArticles(articleDir, targetPath)
    printDoc(ResearchGenHtml.researchPage, "Research Overview :: " + CV.name + " :: CMU", new File(targetPath, "research.html"))
    println("done.")

}
