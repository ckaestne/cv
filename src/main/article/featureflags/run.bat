wget -O post.md https://docs.google.com/document/d/1N88dzb5rrID23KrdgxfVydjPaUdmugM1vzS9MYECo44/export?format=txt
cp _head.xml index.xml
pandoc --bibliography dblp2_short.bib --filter pandoc-citeproc post.md >> index.xml
cat _tail.xml >> index.xml
