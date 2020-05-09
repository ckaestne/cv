sbt test
sbt "runMain de.stner.cv.GenLatex"
cd target/pdf
pdflatex -interaction=nonstopmode cv.tex
cd ../..
sbt "runMain de.stner.cv.GenHtml"
cp target/pdf/cv.pdf target/site/cv.pdf
#rsync -vzr target/site/ /afs/cs/user/ckaestne/www/
rsync -vzur target/site/ ckaestne@linux.gp.cs.cmu.edu:www/
