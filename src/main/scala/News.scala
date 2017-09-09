package de.stner.cv

import java.util.{GregorianCalendar, Date}
import java.text.SimpleDateFormat
import xml.NodeSeq


object News {

    case class NewsItem(date: Date, title: String, body: NodeSeq) {
        def getID() = new SimpleDateFormat("yyyy-MM-dd").format(date)+"_"+title.replaceAll("[\\W]|_", "_")
    }

    val news: List[NewsItem] =
        NewsItem(new GregorianCalendar(2017, 6-1, 29).getTime, "Differential Testing for KConfigReader",
          <span>I talked about this at FOSD'16 and wanted to write it down since: My experience with using differential testing systematically
          in developing <a href="https://github.com/ckaestne/kconfigreader">KConfigReader</a>. I have finally written this up
          and you can find it on <a href="https://arxiv.org/abs/1706.09357">arxiv</a>.</span>) ::
        NewsItem(new GregorianCalendar(2017, 6-1, 5).getTime, "jsconf.eu talk: How to Break an API",
          <span>I recently gave a talk at <a href="https://2017.nodejs.eu/">jsconf.eu</a> about our interviews and survey regarding how developers deal with breaking changes, and how community values influence practices. A recording is now available:<br>
          <iframe width="560" height="315" src="https://www.youtube.com/embed/xJHeHCZtmAU" frameborder="0" allowfullscreen="true"></iframe></br>
          Detailed results can be found on <a href="http://breakingapis.org">breakingapis.org</a></span>) ::
        NewsItem(new GregorianCalendar(2016, 9-1, 20).getTime, "Ecosystem Survey",
          <span>I'm really excited about our ongoing research on change in software ecosystems. Based on our <a href="http://breakingapis.org/fse2016.pdf">FSE'16 interview study</a> of how developers negotiate costs around breaking changes and how practices seem to align well within an ecosystem but differ significantly across ecosystems, we are currently running a large scale survey across 28 ecosystems, asking about ecosystem values, community health, and various practices related to change planning and dealing with changes in dependencies. We encourage all developers familiar with any of those ecosystems to participate in our survey. Both results of our previous study and the survey can be found on <a href="http://breakingapis.org">http://breakingapis.org</a>.</span>) ::
        NewsItem(new GregorianCalendar(2016, 7-1, 13).getTime, "Postdoc position available",
          <span>(Update July 29: The postdoc position in my group has been filled.) I'm looking for a postdoc as well as interested undergradudate or even visiting students for a project applying software analysis and machine learning to improve evolution and configuration of robotics software. Details, see here: <a href="https://www.cs.cmu.edu/~ckaestne/postdoc16/">Post-doc position at Carnegie Mellon University</a>.</span>) ::
        NewsItem(new GregorianCalendar(2016, 4-1, 16).getTime, "On the ICSE 2017 Author Limit Policy",
          <span>A short comment why I think the recently announced submission limit for ICSE 2017 is hurting collaboration and causing anxiety in our community: <a href="https://www.cs.cmu.edu/~ckaestne/icse17/">ICSE 2017s author limit considered harmful for collaboration</a>.
                </span>) ::
        NewsItem(new GregorianCalendar(2016, 4-1, 15).getTime, "NSF Career Award",
          <span>The NSF awarded a career grant for our work on <a href="https://www.cs.cmu.edu/~ckaestne/research.html#VariationalExecutionTesting">variational execution</a> and understanding feature interactions. We have a small press release about it: <a href="http://isri.cmu.edu/news/2016/20160412-kastner-nsf.html">Saving Software from Itself</a>.
                </span>) ::
        NewsItem(new GregorianCalendar(2016, 3-1, 29).getTime, "ASE 2015 Doctoral Symposium Keynote",
          <span>Before I completely forget: I repeated my keynote talk from the ASE 2015 Doctoral Symposium here at a CMU seminar and had the chance of recording it. Here it is: <a href="https://scs.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=5c80c0ae-4420-4b0a-ab35-58ad4817c6fa">Starting an Academic Career: Reflection on Habits that Worked for Me</a>.
                </span>) ::
        NewsItem(new GregorianCalendar(2016, 1-1, 24).getTime, "FOSD Meeting 2016",
            <span>The deadline for submitting a talk abstract for the <a href="http://fosd.net/2016">FOSD Meeting 2016</a> in Copenhagen is approaching soon.
                This year, Ștefan Stănciulescu, Claus Brabrand, and Andrzej Wąsowski will host the meeting at ITU Copenhagen.
                Really looking forward to it. Consider attending if you are interested in research on variability implementations.
                Contact me in case of questions.</span>) ::
        NewsItem(new GregorianCalendar(2015, 3-1, 13).getTime, "The Love/Hate Relationship with the Preprocessor at ECOOP 2015",
          <span>Last summer, we interviewed quite a number of open source developers about their perceptions of the C preprocessor. The resulting paper has now been accepted at ECOOP 2015. While we still need a few days on producing the final version, I have used this as an opportunity to write a few words on <a href="ontitles/">(rejected) paper titles</a></span>) ::
        NewsItem(new GregorianCalendar(2014, 7-1, 2).getTime, "Teaching Software Construction with Travis CI",
          <span>We had great experiences introducing continuous integration tools into our software construction course using Travis CI and I've been meaning to write down our experience for a while now. I finally got around doing that: <a href="travis">Teaching Software Construction with Travis CI</a>. Feedback is welcome.</span>) :: 
        NewsItem(new GregorianCalendar(2014, 5-1, 10).getTime, "FOSD Meeting 2015",
          <span>The FOSD Meeting 2014 last week in Dagstuhl was great fun. Thanks for everybody who came, presented, and discussed.
          We have already started planning for 2015. We will be in Austria in <a href="http://www.akademietraunkirchen.com/en">Traunkirchen</a> the week before ICSE.
          We will likely expand it to a 4.5 day meeting for up to 50 participants, with social events and keynotes. Looking forward to seeing you all there.</span>) ::
        NewsItem(new GregorianCalendar(2014, 3-1, 27).getTime, "Research Experiences for Undergraduates in Software Engineering",
          <span>We are looking for a number of undergraduate students who would be interested in doing software-engineering research at CMU this summer. I'd be interested in working with somebody on several of my projects (around TypeChef, Github, or Energy Measurements). If you might be interested spending the summer in Pittsburgh, please consider applying. See our <a href="http://www.isri.cs.cmu.edu/education/reu-se/">REU-SE website</a> for projects and details.</span>) ::
        NewsItem(new GregorianCalendar(2014, 2-1, 24).getTime, "Huffington Post Article",
          <span>Chris Parnin has written an article about our fMRI research for the Huffington Post: <a href="http://www.huffingtonpost.com/chris-parnin/scientists-begin-looking-_b_4829981.html">Scientists Begin Looking at Programmers' Brains: The Neuroscience of Programming</a>. Join the discussion <a href="http://science.slashdot.org/story/14/02/23/0239241/the-neuroscience-of-computer-programming">here</a>.</span>) ::
        NewsItem(new GregorianCalendar(2014, 1-1, 16).getTime, "Going to Hyderabad",
            <span>We have been so fortunate that four submissions to ICSE 2014 in Hyderabad have been accepted. I'm excited to present our fMRI study in which we analyzed how novices programmers understand program code, our variability-aware execution experiments to test WordPress with many optional plugins, our reverse engineering efforts trying to extract configuration constraints from C implementations, and our work on evaluating emergent interfaces supporting virtual separation of concerns. Preprints will follow as soon as we finish the camera ready version. Seems I need to make travel plans.</span>) ::
        NewsItem(new GregorianCalendar(2013, 10-1, 23).getTime, "New students",
            <span>I am looking for at least two new Ph.D. students to start in Fall 2014. Topics include reverse engineering variability implementations, testing and analysis of highly configurable systems, and deadling with imperfect modularity in software ecosystems; experience with software analysis, testing, product lines, or empirical research methods is appreciated. All admitted Ph.D. students are fully funded. Please consider applying to our <a href="http://www.isri.cmu.edu/education/se-phd/">CMU SE PhD program</a>.</span>) ::
        NewsItem(new GregorianCalendar(2013, 8-1, 16).getTime, "Variability Mining paper accepted",
          <span>Some papers have a history. We started our work on variability mining in 2009. A corresponing paper has matured over time and has now finally been accepted at IEEE Transactions for Software Engineering. I've uploaded a preprint <a href="http://www.cs.cmu.edu/~ckaestne/pdf/tse_fm.pdf">here</a>. If you are interested how feature location techniques can be tuned to locate product-line features in legacy code, check it out.</span>) ::
        NewsItem(new GregorianCalendar(2013, 6-1, 28).getTime, "NSF Grant: Reverse Engineering Variability Implementations",
          <span>I'm happy to announce that NSF will sponsor our work on reverse engineering variability implementations. After years of looking into preprocessors, this will allow us to take closer looks at build systems, branches, patches, and runtime variability. I am actively recruiting now for this project. If you are a CMU graduate student and could imagine doing research in this field, feel free to send me an email or drop by my office. If you are interested in starting a PhD around these topics, consider applying to the <a href="http://www.isri.cmu.edu/education/se-phd/">CMU SE PhD program</a>.</span>) ::
        NewsItem(new GregorianCalendar(2013, 4-1, 17).getTime, "Book available for preorder: Feature-Oriented Software Product Lines",
          <span>Our book "Feature-Oriented Software Product Lines" can now be preordered from <a href="http://www.springer.com/computer/swe/book/978-3-642-37520-0">Springer</a> and <a href="http://www.amazon.com/Feature-Oriented-Software-Product-Lines-Implementation/dp/3642375200/">Amazon</a> and is scheduled to be shipped May 31st.</span>) ::
        NewsItem(new GregorianCalendar(2013, 3-1, 21).getTime, "TypeChef release 3.4",
            <span>TypeChef 0.3.4 is finally released and contains many updates and bugfixes. This version is finally able to typecheck the entire subsystem of the Linux kernel we have been targeting. For code and changelog see the <a href="https://github.com/ckaestne/TypeChef">github page</a></span>) ::
        NewsItem(new GregorianCalendar(2013, 2-1, 17).getTime, "Dagstuhl Variability Analysis and FOSD",
            <span>Next week, I will attend the Dagstuhl seminar <a href="http://www.dagstuhl.de/en/program/calendar/semhp/?semnr=13091">Analysis, Test and Verification in The Presence of Variability</a> and will run the <a href="http://fosd.net/treffen2013">German FOSD student meeting</a>, also in Dagstuhl this year. Looking forward to many inspiring discussions.</span>) ::
        NewsItem(new GregorianCalendar(2013, 2-1, 12).getTime, "Book: Feature-Oriented Software Product Lines",
            <span>After over a year of work, we (Sven Apel, Don Batory, Gunter Saake, and I) finally finished the final draft of our book "Feature-Oriented Software Product Lines: Concepts and Implementation", a textbook for our product-line lectures. The book will be published by Springer and will appear late Spring.</span>) ::
        NewsItem(new GregorianCalendar(2012, 9-1, 30).getTime, "GPCE 2013",
            <span>I will chair the program committee of <a href="http://program-transformation.org/GPCE13">GPCE 2013</a>, colocated with SPLASH in Indianapolis. Already start thinking about submitting there next spring :). Updates to follow.</span>) ::
        NewsItem(new GregorianCalendar(2012, 9-1, 7).getTime, "FOSD and GPCE in Dresden",
            <span>I'll be in Dresden, Germany in the last week of September to present our paper <a href="pdf/FOSD12_testing.pdf">Toward Variability-Aware Testing</a> at the <a href="http://fosd.net/2012/">FOSD workshop</a>. I also will be giving a tech talk on Variability-Aware Analysis at <a href="http://program-transformation.org/GPCE12">GPCE</a>, together with Sven Apel. Come and join, it will be fun events. (FOSD is still accepting lightning talks and tool demos.)</span>) ::
        NewsItem(new GregorianCalendar(2012, 8-1, 25).getTime, "Moving to CMU",
            <span>Just im time for the fall term, I'm moving from Marburg, Germany to Pittsburgh, PA to start a faculty position at CMU. Starting next week, you can reach me there.</span>) ::
            Nil


}
