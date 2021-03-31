#echo "SSH password:"
#read -s
#export SSHPASS=$PROMPT

#sbt test
sbt "runMain de.stner.cv.GenLatex"
cd target/pdf
pdflatex -interaction=nonstopmode cv.tex
cd ../..
sbt "runMain de.stner.cv.GenHtml"
cp target/pdf/cv.pdf target/site/cv.pdf
#rsync -vzr target/site/ /afs/cs/user/ckaestne/www/

read -p "Press enter to continue"
#rsync --rsh='sshpass -e ssh -l ckaestne' -vzur target/site/ ckaestne@linux.gp.cs.cmu.edu:www/
rsync -vzur target/site/ ckaestne@linux.gp.cs.cmu.edu:www/
#export SSHPASS x
