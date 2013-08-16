package de.stner.cv

import de.stner.cv.StructureTheses.AThesis
import xml._
import dtd.{PublicID, DocType}
import java.text.{DecimalFormat, SimpleDateFormat}
import java.io.File
import org.apache.commons.io.FileUtils
import java.util.{Locale, Date}


object GenHtml extends App {

    import CV._

    implicit def stringTexWrapper(string: String) = new StringTexHelper(string)



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
        override def person(person: Person): NodeSeq =
            if (person.url.isDefined && person.affiliation.isDefined)
                <a href={person.url.get.toString()} class="author" title={person.fullname+" • "+person.affiliation.get}>{person.abbrvname}</a>
            else if (!person.url.isDefined && person.affiliation.isDefined)
                <span class="author" title={person.fullname+" • "+person.affiliation.get}>{person.abbrvname}</span>
            else if (person.url.isDefined && !person.affiliation.isDefined)
                <a href={person.url.get.toString()}  class="author" title={person.fullname}>{person.abbrvname}</a>
            else
                <span class="author" title={person.fullname}>{person.abbrvname}</span>

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
          <a name={thesis.genKey}></a>{thesis.author.fullname}.
          <strong>{ thesis.title.markdownToHtml}</strong>{thesis.title.endDot()}
            {thesis.kind.name}, {thesis.where.name}, {thesis.where.country}, {thesis.monthStr} {thesis.year}.
            {if (!thesis.note.isEmpty) <em>{thesis.note.markdownToHtml}</em> }
        [ <a href={"./bibtex.html#" + thesis.genKey}>bib</a>
            {if (thesis.pdf != null)
            <span>| <a href={thesis.pdf.toString()}>.pdf</a></span>
            }]
        </dd>


    def printSupervisedTheses(theses: Seq[AThesis]) = rowH2(
        "Supervised Theses",
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
                   <a name={p.genKey} />
                  {p.render(DefaultBibStyle, FancyPersonHtmlFormater)}
               [ {
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
        rowH2_(<span>Key Publications {rssLogo("pub.rss","Full publication feed")}</span>,
        <p>For a complete list of publications, see the <a href="publications.html">publication page</a>.</p>) ++ {
            for (p <- pubs.filter(_.isSelected).reverse) yield printPublicationRow(p)
        }.flatten ++
            row(null,
                <p><a href="publications.html">more...</a></p> ++
                    printCopyrightNotice())

    //        A full publication list is available as <a href={URL("http://www.informatik.uni-marburg.de/~kaestner/publist.pdf").toString}>.pdf</a>.</p>
    def printPublications(pubs: Seq[Publication]) =
        rowH2_(<span>Publications {rssLogo("pub.rss","Publication feed")}</span>,
    <div class="bib">
        <p>Key publications highlighted in yellow.</p>
        {printFilterHeader(pubs)}
        <dl id="pubmain" class="fullpublist">
        {for (p <- pubs.reverse) yield printPublication(p)}
        </dl>
        <div id="pubgen"></div>
        {printCopyrightNotice()}
    </div>)

    def printCommittee(c: Committee, comma: Boolean) = <span><a href={c.venue.url.getOrElse(".").toString()} title={c.venue.name}>{c.venue.short} {c.venue.year}</a> (<span title={c.role.title}>{c.role.abbreviation}</span>){if (comma) ","} </span>

    def printCommitteePicture(): NodeSeq = <a href="http://program-transformation.org/GPCE13">
    <img title="Generative Programming and Component Engineering 2013" src="GPCE-2013.png" alt="GPCE2013" width="180" height="220" />
    </a>

    def printCommittees(committees: Seq[Committee]) = rowH2("Committees",
            <div>{
                for (c <- committees.dropRight(1))
                yield printCommittee(c, true)
            }{printCommittee(committees.last, false)}</div>,
        printCommitteePicture()
    )


    def printPrivate(): NodeSeq = rowH2(
        "Private Interests",
        <p><a href={URL("http://www.flickr.com/photos/p0nk/sets/72157627689187184/").toString}>Jugg</a><a href={URL("juggling.xhtml").toString}>ling</a>,
        <a href={URL("http://www.flickr.com/photos/p0nk/sets/72157611890103649/").toString}>Cooking</a>,
        <a href={URL("http://boardgamegeek.com/collection/user/chk49").toString}>Board games</a>,
        Concerts (<a href={URL("concerts.xhtml").toString}>past</a>
        &amp; <a href={URL("http://www.last.fm/user/christianwebuni/events").toString}>future</a>)</p> ++ printTwitterWidget())

    def printSpelling() =
        <p class="spelling">
        My last name Kästner is pronounced <a href="http://en.wikipedia.org/wiki/Wikipedia:IPA_for_German">[ˈkɛstnɐ]</a>.
        It is a quite common German last name, well known for the author and poet <a href="http://en.wikipedia.org/wiki/Erich_Kästner">Erich Kästner</a>.
        The umlaut <strong style="font-size:+2">ä</strong> is signficiant for the pronunciation.
        The valid ASCII spelling is <span class="code">Kaestner</span>, not <span class="code">Kastner</span>.
        To correctly typeset the name in LaTeX use <span class="code">K{{\"a}}stner</span>.
        To create the &auml; in Windows enter 0228 while pressing the ALT key or simply copy it from this page. <a href="javascript:toggleSpelling()" style="font-size:small;">[close]</a>
        </p>

    def printSpellingLink() = <span style="font-size:small;position:absolute;top:0"><a href="spelling.html" id="spellinglink">[pronunciation and spelling]</a></span>


    def printResearchInterests(researchInterests: Seq[String]) =
        <h2>Research Interests</h2> :+
        <ul>
            <li><strong>{researchInterests.head}</strong></li>
            {for (r <- researchInterests.tail) yield <li>{r}</li>}
        </ul>


    val monthyear = new SimpleDateFormat("MMM. yyyy")
    def printAward(award: AwardOrGrant) = row(<span class="date">{monthyear format award.date}</span>,
        <a href={award.url.toString}>{award.name.markdownToHtml}</a> :+ {
            award match {
                case Award(_,_,_,extraLinks,_) if !extraLinks.isEmpty =>
                    <span> ({
                    for (ex <- extraLinks.dropRight(1))
                    yield <span><a href={extraLinks.head._1.toString}>{extraLinks.head._2}</a>, </span>
                    }{<span><a href={extraLinks.last._1.toString}>{extraLinks.last._2}</a></span>})</span>
                case Grant(_,_,_,from,to,ag,budget) =>
                    <span>{ag + ". "+(monthyear format from) +" – "+ (monthyear format to) +", "+(new DecimalFormat("###,###").format(budget.value).replace(",", " "))+(
                        budget match { case EUR(_) => " EUR"; case USD(_) => " USD"}
                        )}</span>
                case _ => <span></span>
            }
        }
    )

    def printAwards(awards: Seq[AwardOrGrant]) = rowH2("Grants & Awards") ++ {
        for (r <- awards) yield printAward(r)
    }.flatten


    def printProjects(pr: Seq[(URL, String, String, Option[String])]): NodeSeq =
        rowH2("Research Projects & Software") ++ {
            for (p <- pr) yield
                row(<span class="toolname">{p._2}</span>,
                  <div><a href={p._1.toString}>{p._3}</a>{if (p._4.isDefined) " (" + p._4.get + ")"}</div>, "tool")
        }.flatten

    def printTitle(spellingHint: Boolean = false): NodeSeq =
        <div class="grid_3 ">&nbsp;</div> :+
        	<div class="grid_9 header headline">
                {if (spellingHint)
        <div><h1 style="display: inline;" itemprop="name">Christian Kästner</h1> {printSpellingLink}</div>
            else  <h1 itemprop="name">Christian Kästner</h1>}
                <div id="spellingbox" style="display:none">{printSpelling()}</div>
                <p><span itemprop="role">Assistant Professor</span> · <span itemprop="affiliation">Carnegie Mellon University</span> · Institute for Software Research</p>
                <meta itemprop="url" content="http://www.cs.cmu.edu/~ckaestne/" />
        	</div> :+
        	<div class="clear margin_40">&nbsp;</div>

    val nbsp: Node = <span>&nbsp;</span>.child.head

    def row(left: NodeSeq, right: NodeSeq, marginafter: String = "") =
        <div class="grid_3 right">{if (left == null) nbsp else left}</div> :+
        	<div class="grid_9">{if (right == null) nbsp else right}</div> :+
        	<div class={if (marginafter.length > 0) "clear margin_" + marginafter else "clear"}>&nbsp;</div>

    def printPicture(): NodeSeq = <img src="me.jpg" alt="Christian Kästner" />

    def rowH2(title: String, body: NodeSeq = Nil, leftExtra: NodeSeq = null): NodeSeq = rowH2_(Text(title), body, leftExtra)
    def rowH2_(title: Node, body: NodeSeq = Nil, leftExtra: NodeSeq = null): NodeSeq = <div class="clear margin_h2">&nbsp;</div> +: row(leftExtra, <h2>{title}</h2> ++: body)

    def printNews(full:Boolean=false): NodeSeq = rowH2_(<span>News {rssLogo("news.rss","News feed")}</span>) ++ {
        val news=if (full) News.news else News.news.take(3)
        for (newsItem <- news)
        yield row(
        {<span class="newsdate">{new SimpleDateFormat("d MMM. yyyy") format newsItem.date}</span>}, {
            <div class="newsheadline"><a name={newsItem.getID()}></a>{newsItem.title}</div> :+
            <div class="newsbody">{newsItem.body}</div>
        }, "newsitem")
    }.flatten ++ {row(null, if (!full) <a href="news.html">Older News...</a> else <a href="index.html">Back...</a> )}

    def printTeachingSummary(teaching: Seq[Course]) =
        rowH2("Teaching") ++
            printTeaching(teaching.filter(_.term >= WinterTerm(2010))) ++
            row(null,
                <div>See also the full <a href="teaching.html">teaching history</a>.</div>
                )

    def printCoolWall() =
        rowH2("FOSD Cool Wall",
            <p>The cool wall was created and evolved during the yearly FOSD student meetings (see <a href="http://fosd.net">fosd.net</a>). With it, we would like to encourage researchers to look for better tool names. Up to 2012, the listing was completely subjective, feel free to complain. Starting 2013, we started <a href="coolwallvoting.jpg">voting</a> and giving out a <a href="coolwallaward.jpg">Coolest Tool Name award</a>.</p> :+
        <a href="coolwall2013.pdf"><img src="coolwall2013.png" alt="Cool Wall 2013" id="coolwall" /></a>)


    def printTwitterWidget(): NodeSeq =
     <p><div class="tweet"></div></p>


    def printFullBibtex(): NodeSeq =
        for (p <- CV.publications)
        yield <div><a name={p.genKey} /><pre>{p.toBibtex()}</pre></div>

    def mainPage: NodeSeq =
        <div itemscope="" itemtype="http://data-vocabulary.org/Person">{
        printTitle(true) ++
            row(printPicture(), printSummary())}</div> ++
            printNews() ++
            printTeachingSummary(teaching) ++
            printCommittees(committees) ++
            printAwards(awards) ++
            printProjects(projects) ++
            printKeyPublications(publications) ++
            printCoolWall() ++
            printPrivate()


    def teachingPage: NodeSeq = printTitle() ++ rowH2("Teaching History") ++ printTeaching(teaching)

    def newsPage: NodeSeq = printTitle() ++ printNews(true)

    def printDoc(body: NodeSeq, title: String, file: File, extraHeader: NodeSeq = null) = {

        val doct = DocType("html", PublicID("-//W3C//DTD XHTML 1.0 Strict//EN", "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"), Nil)

        val doc =
        <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
                <link rel="stylesheet" type="text/css" media="all" href="css/reset.css" />
                <link rel="stylesheet" type="text/css" media="all" href="css/text.css" />
                <link rel="stylesheet" type="text/css" media="all" href="css/960.css" />
                <link rel="stylesheet" type="text/css" media="all" href="css/jquery.tweet.css" />
                <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
                <script type="text/javascript" src="js/script.js"></script>
                <script type="text/javascript" src="js/jquery.tweet.js"></script>
                {extraHeader}
                <title>{title}</title>
            </head>
            <body><div class="container_12">{body}</div></body>
        </html>
        scala.xml.XML.save(file.getAbsolutePath(), doc, "UTF-8", doctype = doct)
    }

    private val RFC822 =
        new SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.US)

    def printNewsFeed(file: File) =
        printFeed(CV.name+" :: News Feed",CV.url,"News feed for "+CV.name,{
            for (newsitem<-News.news) yield
                    <item>
                        <title>{newsitem.title}</title>
                        <description>{newsitem.body}</description>
                        <pubDate>{RFC822.format(newsitem.date)}</pubDate>
                        <guid>{CV.url+"#"+newsitem.getID()}</guid>
                        <link>{CV.url+"#"+newsitem.getID()}</link>
                    </item>
        }, file)

    private val pubPage=CV.url+"publications.html"
    private def renderPubRSS(pub:Publication):String = {
        <div><p>{pub.render(new BibStyle {
             override def withAcceptanceRate=false
            }, new PlainHtmlFormater{})}
        </p>
        {if (!pub.isHideAbstract && pub.abstr != "") <br/><br/>  <blockquote><p>{pub.abstr.markdownToHtml}</p></blockquote> }
        </div>
        .toString
    }
    def printPubsFeed(file: File) =
        printFeed(CV.name+" :: Publication Feed",pubPage,"Publication feed for "+CV.name,{
            for (pub<-CV.publications) yield
                    <item>
                        <title>{pub.title}</title>
                        <link>{pubPage+"#"+pub.genKey}</link>
                        <description>{renderPubRSS(pub).toString}</description>
                        <guid>{pubPage+"#"+pub.genKey}</guid>
                    </item>
        }, file)



    def printFeed(title: String, url: String, desc:String, items:NodeSeq, file:File) {

        val doc:Node =
        <rss version="2.0">
            <channel>
                <title>{title}</title>
                <link>{url}</link>
                <description>{desc}</description>
                <language>en-us</language>
                <lastBuildDate>{RFC822.format(new Date())}</lastBuildDate>
                {items}
            </channel>
        </rss>
        scala.xml.XML.save(file.getAbsolutePath(), doc, "UTF-8", true)
    }
    def rssLogo(link:String, title:String):Node=
           <a href={link} title={title}><img src="rss.png" alt={title} /></a>

  def getNewsRSSHeader() =
    <link title="News Feed" type="application/rss+xml" rel="alternate" href="news.rss" />

  def getPubsRSSHeader() =
      <link title="Publication Feed" type="application/rss+xml" rel="alternate" href="pub.rss" />

  def getJSHeaderPublications() = <script type="text/javascript" src="js/pubfilter.js"></script> :+ <script type="text/javascript">{ scala.xml.Unparsed(printGroupingHeaders(publications)) }</script>


    /** central build activities */
    println("copying files.")
    val targetPath = new File("target/site")
    targetPath.mkdirs()
    FileUtils.cleanDirectory(targetPath)

    FileUtils.copyDirectory(new File("src/main/site"), targetPath)

    val pdfDir = new File(targetPath, "pdf")
    pdfDir.mkdir()
    FileUtils.copyDirectory(new File("src/main/pdf"), pdfDir)



    println("generating html.")
    printDoc(mainPage, CV.name + " :: CMU", new File(targetPath, "index.html"), getNewsRSSHeader() ++ getPubsRSSHeader())
    printDoc(teachingPage, CV.name + " :: Teaching :: CMU", new File(targetPath, "teaching.html"))
    printDoc(newsPage, CV.name + " :: News :: CMU", new File(targetPath, "news.html"))
    printDoc(printTitle() ++ row(null, printSpelling()), CV.name + " :: Spelling", new File(targetPath, "spelling.html"))
    printDoc(printTitle() ++ printPublications(publications) ++ printSupervisedTheses(advisedTheses), CV.name + " :: Publications :: CMU", new File(targetPath, "publications.html"), getJSHeaderPublications()++getPubsRSSHeader())
    printDoc(printTitle() ++ row(null, printFullBibtex()), CV.name + " :: Bibtex", new File(targetPath, "bibtex.html"))
    printNewsFeed(new File(targetPath,"news.rss"))
    printPubsFeed(new File(targetPath,"pub.rss"))
    println("done.")

}
