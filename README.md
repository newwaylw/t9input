# t9input
An implementation of simply t9 input based on Trie
for https://www.hackerrank.com/challenges/t9-predictions

The script cleansort.py is used to produce the sorted unigram from
the data. e.g.

cat t9Dictionary t9TextCorpus | cleansort.py > unigram.txt

a copy of the unigram.txt is placed in test/resources/
