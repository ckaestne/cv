package de.stner.cv

import java.io.FileWriter
import de.stner.cv.StructureTheses.AThesis
import xml._


object GenHtml extends App {

    import CV._


    def printCourse(course: Course, withKind: Boolean = false): Elem = <span>
        {if (course.url != null)
            <a href={course.url.toString()}>{course.title}</a>
        else course.title}
        {if (course.kind.isSeminar) "-- Seminar"
        else if (withKind) {
            if (course.kind.isLecture && course.kind.isExercise) "-- Lecture + Exercise"
            else if (course.kind.isLecture) "-- Lecture"
            else if (course.kind.isExercise) "-- Exercise"
            else if (course.kind.isTutorium) "-- TA"
        }}
    </span>

    def printTeaching(teaching: Seq[Course]) = {
    <div><h2>Teaching</h2>{
    val byTerm = teaching.groupBy(_.term)
    for (term <- byTerm.keys.toSeq.sorted.reverse)
    yield <p>{term}
      <ul>{
           for (course <- byTerm(term))
           yield <li>{printCourse(course)}</li>
         }</ul></p>

      }</div>
    }

    def printThesis(thesis: AThesis) = {
        <a name={thesis.genKey} />
        <dd>
            {thesis.author.fullname}.
          <strong>{thesis.title}</strong>.
            {thesis.kind.name}, {thesis.where.name}, {thesis.where.country}, {thesis.monthStr} {thesis.year}.
        [ <a href={"./thesis_bib.html#" + thesis.genKey}>bib</a>
            {if (thesis.pdf != null)
            <span>| <a href={thesis.pdf.toString()}>.pdf</a></span>
            }]
        </dd>
    }

    def printSupervisedTheses(theses: Seq[AThesis]) = {
      <div><h2>Supervised Theses</h2>
          <div class="bib"><dl>
              {for (thesis <- theses) yield printThesis(thesis) }
              </dl></div></div>
    }

    def printCommittee(c: Committee, comma: Boolean) = <span><a href={c.venue.url.toString()}>{c.venue.short} {c.venue.year}</a> (<span title={c.role.title}>{c.role.abbreviation}</span>){if (comma) ","} </span>

    def printCommittees(committees: Seq[Committee]) =
        <div>
            <div class="image-right captioned"><a href="http://program-transformation.org/GPCE12"><img title="Generative Programming and Component Engineering 2012" src="http://program-transformation.org/pub/GPCE12/Banner/GPCE-2012-button.png" alt="GPCE2012" width="135" height="200" /></a></div>
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
        {printPrivate()}
    </div>


    println(output)
    val fw = new FileWriter("out.html")
    fw.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">")
    fw.write(
        <html>
      {output}
      </html>.toString())
    fw.close()

}
