package de.stner.cv

import de.stner.cv.StructureTheses.AThesis
import xml._
import dtd.{PublicID, DocType}


object GenHtml extends App {

    import CV._

    implicit def stringTexWrapper(string: String) = new StringTexHelper(string)


    def printCourse(course: Course, withKind: Boolean = false): Elem = <span>
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

    def printTeaching(teaching: Seq[Course]) = {
    <div><h2>Teaching</h2>{
    val byTerm = teaching.groupBy(_.term)
    for (term <- byTerm.keys.toSeq.sorted.reverse)
    yield <div><p>{term}</p>
      <ul>{
           for (course <- byTerm(term))
           yield <li>{printCourse(course)}</li>
         }</ul>
        </div>
      }</div>
    }

    def printThesis(thesis: AThesis) =
        <dd>
          <a name={thesis.genKey}>{thesis.author.fullname}</a>.
          <strong>{ thesis.title.markdownToHtml}</strong>{thesis.title.endDot()}
            {thesis.kind.name}, {thesis.where.name}, {thesis.where.country}, {thesis.monthStr} {thesis.year}.
            {if (!thesis.note.isEmpty) <em>{thesis.note.markdownToHtml}</em> }
        [ <a href={"./thesis_bib.html#" + thesis.genKey}>bib</a>
            {if (thesis.pdf != null)
            <span>| <a href={thesis.pdf.toString()}>.pdf</a></span>
            }]
        </dd>


    def printSupervisedTheses(theses: Seq[AThesis]) = {
      <div><h2>Supervised Theses</h2>
          <div class="bib"><dl>
              {for (thesis <- theses.reverse) yield printThesis(thesis) }
              </dl></div></div>
    }

    def getPublicationClassTags(p: Publication): String = {
        var tags = Seq("pub")
        tags = (if (p.isSelected) "selected" else "regular") +: tags
        tags = p.topics.map(_.key) ++ tags
        tags = p.venue.kind.key +: tags
        tags.mkString(" ")
    }


    def printPublication(p: Publication) = {
        val links = p.links + (BIB -> URL("./thesis_bib.html#" + p.genKey))
               <dd class={getPublicationClassTags(p)} id={p.genId}><div>
                   <a name={p.genKey} />
                   {p.render(DefaultBibStyle).markdownToHtml}
               [ {
                   for ((key, url) <- links.dropRight(1))
                   yield <a href={url.toString}>{key.print}</a> :+ ", "
               } <a href={links.last._2.toString}>{links.last._1.print}</a> ]
                   {if (!p.isHideAbstract && p.abstr != "")   <blockquote>{p.abstr.markdownToHtml}</blockquote> }
               </div></dd>
    }

    private def sep =  <strong class="sep">|</strong>

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
        		 <span style="clear:left;"><input type="radio" id="filter_allpub" value="All publications" name="filter_selected" checked="1" /><label for="filter_allpub">All publications</label></span>
        		 <span style="clear:left;"><input type="radio" id="filter_keypub" value="Key publications" name="filter_selected"             /><label for="filter_keypub">Key publications</label></span>
                 {sep}
                 <label for="filter_topic">By topic: </label>
                 <select style="width:150px" id="filter_topic">
                    <option value="all"  selected="1">All</option>
                    {for (t <- getTopics(p)) yield <option value={t._1.key}>{t._1.name} ({t._2})</option> }
                 </select>
                 {sep}
                 <label for="filter_kind">By publication</label>
                 <select style="width:150px" id="filter_kind">
                    <option value="all" selected="1">All</option>
                    {for (t <- getKinds(p)) yield <option value={t._1.key}>{t._1.name} ({t._2})</option> }
                  </select>
              </li>


              <li class="form-line" id="id_6">
                  <strong>Group by</strong>

                  <span><input type="radio" id="nogroup"   name="q6_groupBy" value="None (chronologically)" checked="1" /><label for="nogroup"  >None (chronologically)</label></span>
                  <span><input type="radio" id="groupKind" name="q6_groupBy" value="Publication kind" />                  <label for="groupKind">Publication kind</label></span>
                  <span><input type="radio" id="groupYear" name="q6_groupBy" value="Year" />                              <label for="groupYear">Year</label></span>
                  <span><input type="radio" id="groupTop"  name="q6_groupBy" value="Topic" />                             <label for="groupTop" >Topic</label></span>
              </li>

              <li class="form-line" id="id_3">
                <span style="clear:left;"><input type="checkbox" id="showabstracts" checked="1" /><label for="showabstracts">Show abstracts</label></span>
              </li>
            </ul>
        </form>
    </div>

    def printGroupingHeader(title: String, pubs: Seq[Publication]) =
        "{title:\"%s\",pubs:[%s]}".format(title, pubs.map("\"" + _.genId + "\"").mkString(","))

    // <div><h3>{title}</h3>{ pubs.map(_.genId).mkString(",")}</div>
    def printGroupingHeaders(pubs: Seq[Publication]) = {
        "function pubheaderByYear() { return [%s]; }".format((for (g <- pubs.groupBy(_.venue.year).toSeq.sortBy(_._1).reverse) yield printGroupingHeader(g._1.toString, g._2)).mkString(",")) +
            "function pubheaderByKind() { return [%s]; }".format((for (g <- pubs.groupBy(_.venue.kind).toSeq.sortBy(_._1)) yield printGroupingHeader(g._1.name, g._2)).mkString(",")) +
            "function pubheaderByTopic() { return [%s]; }".format((for (g <- pubs.flatMap(p => p.topics.map(t => (t, p))).groupBy(_._1).toSeq.sortBy(_._2.size).reverse) yield printGroupingHeader(g._1.name, g._2.map(_._2))).mkString(","))
    }

    def printPublications(pubs: Seq[Publication]) =
    <div>
        <h2>Publications <span class="small">(<a href={URL("http://www.informatik.uni-marburg.de/~kaestner/publist.pdf").toString}>.pdf</a>)</span></h2>
        Key publications highlighted in yellow.
        {printFilterHeader(pubs)}
        <div id="pubmain">
        {for (p <- pubs.reverse) yield printPublication(p)}
        </div>
        <div id="pubgen"></div>
        <p class="copyrightnotice">Copyright Notice: This material is presented to ensure timely
        dissemination of scholarly and technical work. Copyright and all rights
        therein are retained by authors or by other copyright holders. All
        persons copying this information are expected to adhere to the terms
        and constraints invoked by each author's copyright. In most cases,
        these works may not be reposted without the explicit permission of the
        copyright holder.</p>
    </div>

    def printCommittee(c: Committee, comma: Boolean) = <span><a href={c.venue.url.toString()}>{c.venue.short} {c.venue.year}</a> (<span title={c.role.title}>{c.role.abbreviation}</span>){if (comma) ","} </span>

    def printCommittees(committees: Seq[Committee]) =
        <div>
            <h2>Committees</h2>
            {
                for (c <- committees.dropRight(1))
                yield printCommittee(c, true)
            }{printCommittee(committees.last, false)}
        </div>


    def printPrivate() = {
        <h2>Private Interests</h2>
        <p><a href={URL("http://www.flickr.com/photos/p0nk/sets/72157627689187184/").toString}>Juggling</a>,
        <a href={URL("http://www.flickr.com/photos/p0nk/sets/72157611890103649/").toString}>Cooking</a>,
        <a href={URL("http://boardgamegeek.com/collection/user/chk49").toString}>Board games</a>,
        Concerts (<a href={URL("http://www.informatik.uni-marburg.de/~kaestner/concerts.xhtml").toString}>past</a>
        &amp; <a href={URL("http://www.last.fm/user/christianwebuni/events").toString}>future</a>)</p>
    }

    val output =
    <div>
        <h1>{name}</h1>
        {printTeaching(teaching.filter(_.term >= WinterTerm(2010)))}
        {printSupervisedTheses(advisedTheses)}
        {printCommittees(committees)}
        {printPublications(publications)}
        {printPrivate()}
    </div>


    //    println(output)
    //    val fw = new FileWriter("out.html")
    //    fw.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">")
    val doct = DocType("html", PublicID("-//W3C//DTD XHTML 1.0 Strict//EN", "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"), Nil)

    val doc =
        <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
                <link rel="Stylesheet" type="text/css" href="src/main/site/cv.css" />
                <script type="text/javascript" src="src/main/site/jquery-1.7.2.min.js"></script>
                <script type="text/javascript" src="src/main/site/pubfilter.js"></script>
                <script type="text/javascript">{ scala.xml.Unparsed(printGroupingHeaders(publications)) }</script>
                <title>Christian Kästner</title>
            </head>
            <body>{output}</body>
        </html>
    scala.xml.XML.save("out.html", doc, "UTF-8", doctype = doct)
    //    fw.close()

}
