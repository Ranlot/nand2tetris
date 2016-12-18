#!/usr/bin/env bash

#just out of curiosity, it may be interesting to see which jump conditions
#are actually generated by the compiler

cat src/main/resources/Pong.asm | grep ';' | awk -F ';' '{print $2}' | sort | uniq -c | sort -k1 -gr

#TODO: make a loop over all asm files and gather statistics