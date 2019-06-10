BASIC USE:

The following two test files are provided to help you test your project 4.

commands1108.txt
	This file contains commands to insert, query, and delete from the
	splay tree.

response1108.txt
	This file contains the correct responses from the above commands.


Thus, to test your code, try this on lectura:

	java -ea CityRoster <commands1108.txt | diff - response1108

If there are differences, that indicates errors.  Compare your program's
response to the reference responses in response1108.
If there is no output, "silence is golden" -- this indicates success.

For a better sense of which response goes with which command, see
test1108.txt, which has the commands and responses interleaved.





ADVANCED USE:

Also I've provided some scripts you could use for further testing.

generate.py - a python script that will generate further test files,
	both commands and reference responses (mixed together).
filter_cmds.bash - a bash script (just a grep) to extract the commands
	from the output of generate.py
filter_resp.bash - a bash script to extract responses from the output
	of generate.py

How to use:

./generate.py >some_file.txt  # generate mixed commands/responses
./filter_cmds.bash <some_file.txt >commands_new.txt
./filter_resp.bash <some_file.txt >response_new.txt
java -ea CityRoster <commands_new.txt | diff - response_new.txt

