#!/bin/bash

# NOTE:
#   Not the perfect benchmark, as `nc` doesn't wait for a response
#   before it sends more data, just as long as I don't send through
#   more than than about 2M ids in one bench, it's good enough to
#   stress the server.

NUMBER_OF_IDS=10000

echo "Fetching $NUMBER_OF_IDS ids (x3)"

time yes GET | head -n $NUMBER_OF_IDS | nc 127.0.0.1 8888 > bench_out1 &
time yes GET | head -n $NUMBER_OF_IDS | nc 127.0.0.1 8888 > bench_out2 &
time yes GET | head -n $NUMBER_OF_IDS | nc 127.0.0.1 8888 > bench_out3 &

