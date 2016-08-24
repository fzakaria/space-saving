# SpaceSaving

Space Saving algorithm implementation in Java, also know as "HeavyHitter"

The purpose of the algorithm is to find the most frequently used items from an infinite stream.
In other words, even if you have millions of different values, the algorithm will try to find the most frequently used
within some acceptable error bound.

The datastructure used is described by the paper [Efficient Computation of Frequent and Top-k Elements in Data Streams](http://www.cse.ust.hk/~raywong/comp5331/References/EfficientComputationOfFrequentAndTop-kElementsInDataStreams.pdf)
by *Ahmed Metwally , Divyakant Agrawal, and Amr El Abbadi*

It is a a ϴ(1) "constant time" insert and  ϴ(k) lookup for the *topk* entries.

## Aside
This repository is created because although other implementations existed, I was frustrated that
they did not include any unit tests or thorough documentation explaining some of the error bounds.
