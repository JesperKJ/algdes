#!/bin/sh
for FILE in *-tsp.txt

do
	echo $FILE
	base=${FILE%-in.txt}
    java ClosestPoints < $FILE
done
