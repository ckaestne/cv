package de.stner.cv
import de.stner.cv.Coauthors.{Bogart, Kaestner, Overbey, Trockman, Vasilescu, Zhou}
import de.stner.cv.Citable

import java.time.LocalDate

object CVMedia {






    val onPaperTitlesBlog15 = MediumBlog("On Paper Titles (Bad Ideas, Rejected Ideas, and Final Titles)", URL("https://ckaestne.medium.com/on-paper-titles-bad-ideas-rejected-ideas-and-final-titles-cf51b8a5be24"), LocalDate.of(2015,3,13))
    val teachingSCwithTravisBlog14 = MediumBlog("Teaching Software Construction with Travis CI", URL("https://ckaestne.medium.com/teaching-software-construction-with-travis-ci-3d3d5428d10a"), LocalDate.of(2014,7,2))

    val breakingChangesWeb = Website("How to break an API?", URL("http://breakingapis.org/"), LocalDate.of(2017, 5, 1), List(Kaestner, Bogart))
    val jsconf17Talk = Youtube("How to Break an API: How Community Values Influence Practices | JSConf EU 2017", "xJHeHCZtmAU", LocalDate.of(2017, 5, 31), kind="Talk")

    val infographicBadges = Website("Infographic: npm badges", URL("https://cmustrudel.github.io/announcement/badges"), LocalDate.of(2018, 3, 18), List(Trockman, Kaestner, Vasilescu), kind="Infographic")
    val infographicDonations = Website("Infographic: Donations in Open Source", URL("https://cmustrudel.github.io/announcement/donations"), LocalDate.of(2020, 6, 18), List(Overbey), kind="Infographic")

    val featureFlagsBlog19 = MediumBlog("Feature Flags vs Configuration Options — Same Difference?", URL("https://ckaestne.medium.com/feature-flags-vs-configuration-options-same-difference-81d06c3ec1c3"), LocalDate.of(2019,5,20))

    val ccTalk20 = Youtube("Software Engineering for ML-Enabled Systems | Code & Supply", "9_xeTHaTcCQ", LocalDate.of(2020,4,28))
    val semlaTalk20 = Youtube("Engineering AI-Enabled Systems with Interdisciplinary Teams | SEMLA'20", "", LocalDate.of(2020,6,18))
    val seetTalk20 = Youtube("Teaching Software Engineering for AI-Enabled Systems", "Nk0Sy6Sx3IY", LocalDate.of(2020,6,5))

    val seaiMedium20MLisRE = MediumBlog("Machine Learning is Requirements Engineering", URL("https://medium.com/ckaestne/machine-learning-is-requirements-engineering-8957aee55ef4"), LocalDate.of(2020,3,8), selected = true)
    val seaiMedium20Testing = MediumBlog("A Software Testing View on Machine Learning Model Quality", URL("https://ckaestne.medium.com/a-software-testing-view-on-machine-learning-model-quality-d508cb9e20a6"), LocalDate.of(2020,6,7))
    val seaiMedium20WorldMachine = MediumBlog("The World and the Machine and Responsible Machine Learning", URL("https://ckaestne.medium.com/the-world-and-the-machine-and-responsible-machine-learning-1ae72353c5ae"), LocalDate.of(2020,10,5))
    val seaiMedium20Process = MediumBlog("On the process for building software with ML components", URL("https://ckaestne.medium.com/on-the-process-for-building-software-with-ml-components-c54bdb86db24"), LocalDate.of(2020,11,1))

    val stateOfTheSource20Talk = Youtube("State of the Source 2020: Analyzing Tens of Terabytes of Public Trace Data & Open Source Sustainabilty", "y4cpIaN3tFc", LocalDate.of(2020,9,18), List(Vasilescu, Kaestner))

    val podcast20SustainFork = Podcast("What the Fork? Shurui Zhou on Forking in Open Source | Sustain Podcast", URL("https://podcast.sustainoss.org/53"), LocalDate.of(2020,9,11), List(Zhou))

    val seaibib = Website("Software Engineering for AI/ML -- An Annotated Bibliography", URL("https://github.com/ckaestne/seaibib"),
        LocalDate.of(2020, 1, 31), selected=true)

}
