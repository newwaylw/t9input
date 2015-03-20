#! /usr/bin/python
# -*- coding: utf-8 -*-
import re,sys
from collections import Counter

cnt = Counter()
f = open(sys.argv[1], 'r')
for line in f:
    words = re.split("\s+",line.strip())
    for w in words:
        w =re.sub(r'^[^a-zA-Z]+','',w)
        w = re.sub(r'[^a-zA-Z]+$','',w)
        w = re.sub(r'[\x21-\x26]+','',w)
        w = re.sub(r'[\x28-\x2F]+','',w)
        w = re.sub(r'[\x3A-\x40]+','',w)
        w = re.sub(r'[\x5B-\x5E]+','',w)
        w = re.sub(r'[\x60]+','',w)
        w = re.sub(r'[\x7B-\x7F]+','',w)
        w = re.sub(r'[0-9]+','',w)
        w = w.strip()
        cnt[w]+=1

cnt = sorted(cnt.items(), key=lambda x: (-x[1],x[0]))
for k,v in cnt:
    print k
