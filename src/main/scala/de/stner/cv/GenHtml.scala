package de.stner.cv

import java.io.FileWriter


object GenHtml extends App {

    import CV._


    def printCourse(course: Course) = <a href={course.url.toString()}>{course.title}</a>

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


    val output =
    <div>
        <h1>{name}</h1>
        {printTeaching(teaching)}
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
