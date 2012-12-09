sbt test
sbt "run-main de.stner.cv.GenLatex"
cd target/pdf
pdflatex -interaction=nonstopmode cv.tex
cd ../..
sbt "run-main de.stner.cv.GenHtml"
cp target/pdf/cv.pdf target/site/cv.pdf
rsync -vzr target/site/ /afs/cs/user/ckaestne/www/
