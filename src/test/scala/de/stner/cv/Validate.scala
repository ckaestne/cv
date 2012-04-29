package de.stner.cv

import org.scalatest.FunSuite

class Validate extends FunSuite {

    import CV._

    def checkURL(url: URL, c: Any = null) {
        assert(url == null || url.check, "URL cannot be resolved " + url + " from " + c)
    }

    test("check urls: images") {
        checkURL(URL(imgURL))
    }

    test("check urls: committees") {
        committees.map(c => checkURL(c.venue.url, c))
    }
    test("check urls: reviews") {
        reviews.map(c => checkURL(c.venue.url, c))
    }

    test("check urls: teaching") {
        teaching.map(c => checkURL(c.url, c))
        teachingProjects.map(c => checkURL(c.url, c))
    }

    test("check urls: advised theses") {
        advisedTheses.map(c => checkURL(c.pdf, c))
    }

}
