#!/usr/bin/python3

import random;

def write_test(x, y):
	print('java -ea MultiplyDriver', x, y)
	print('# answer:', x*y)


if __name__ == '__main__':
	print('#!/bin/bash -e');

	for i in range(1000):
		x = random.getrandbits(500)
		y = random.getrandbits(500)
		if (random.getrandbits(1)):
			x = -x
		if (random.getrandbits(1)):
			y = -y

		write_test(x, y)

#	Let's also test a few fundamentals:
	x = random.getrandbits(500)
	write_test(1, x)
	x = random.getrandbits(500)
	write_test(1, -x)
	x = random.getrandbits(500)
	write_test(0, x)
	x = random.getrandbits(500)
	write_test(0, -x)
	x = random.getrandbits(500)
	write_test(-1, x)
	x = random.getrandbits(500)
	write_test(-1, -x)

