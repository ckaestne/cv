package de.stner.cv


object CV {

  val name = "Christian Kästner"
  val title = "Dr.-Ing."
  val imgURL = "http://www.informatik.uni-marburg.de/~kaestner/me3.jpg"
  val description = "I am a post-doctoral researcher at the Philipps University Marburg interested in controlling the complexity caused by variability in software systems. I develop mechanisms, languages, and tools to implement variability in a disciplined way, to detect errors, and to improve program comprehension in systems with a high amount of variability. Currently, I investigate approaches to parse and type check all compile-time configurations of the Linux kernel in the TypeChef project.\n" +
    "\n" +
    "I received my Master's in 2007 and my PhD in 2010 from the University of Magdeburg. For my work on virtual separation of concerns, I received the prestigious GI-Dissertation Award for the best computer-science dissertation 2010 in Germany/Austria/Switzerland. I am a member of the IFIP 2.11 working group on program generation."

  val address = """
   Philipps Universität Marburg (Link)
   Department of Computer Science and Mathematics (Link)
   Hans-Meerwein Str., 35032 Marburg, Germany

   Office: 05-D06

   Phone: ++49 6421 28 25349
   Fax: ++49 6421 28 25419
   E-mail: christian.kaestner (at) uni-marburg.de
"""

  val teaching = Seq(
    Course("Empirical Methods for Computer Scientists",
      new URL("http://www.uni-marburg.de/fb12/ps/teaching/ss12/em"),
      German, SummerTerm(2012)
    ),
    Course("Software Product Lines: Concepts and Implementation",
      new URL("http://www.uni-marburg.de/fb12/ps/teaching/ss11/spl"),
      German, SummerTerm(2011)
    ),
    Course("Software Engineering",
      new URL("http://www.uni-marburg.de/fb12/ps/teaching/ws10/eise"),
      German, WinterTerm(2010)
    )
  )

  val researchInterests = Seq(
    "Virtual separation of concerns",
    "Feature-oriented software development (FOSD), type systems, module systems, parsers, refactoring, aspect-orientation, multidimensional separation of concerns",
    "Software product lines, program synthesis, feature interactions, feature location, empirical analyses"
  )

  val committees = Seq[Committee]()

  val awards = Seq[String]()
  val projects = Seq[String]()

  val publications = Seq[Publication]()
  val supervised = Seq[Thesis]()
}
