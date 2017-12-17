import random
import sys

if (len(sys.argv)==2):
	for i in range(0, int(sys.argv[1])):
		x = random.randint(7500, 12500)
		print("%d" % (x))
else:
	for i in range(0, 11):
		x = random.randint(7500, 12500)
		print("%d" % (x))