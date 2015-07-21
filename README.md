# gu
assorted utilities relevant to internal operations

## mp\_event\_dump

Downloads raw event data from Mixpanel.

### Installation

With bundler:

	$ cd mp_event_dump
	$ bundle install

With the install script:

	$ cd mp_event_dump
	$ [sudo] bash ./install.sh

### Usage

	Usage: mp_event_dump [options] events...
	    -f, --from-date=DATE             The start of the date range
	    -t, --to-date=DATE               The end of the date range
	    -q, --query=QUERY                The query to apply
	    -o, --output=TYPE                json, pretty-json, or csv
	    -h, --help                       Show this message
	Built-in queries:
	    ios
	    ios.ble
	    android
	    android.ble

## waves

Proof-of-concept waveform generation for `.wav` files. Produces either JSON or CSV output.

### Usage

Open the `waves` project in IntellJ. Once open, choose Build -> Build Artifacts… After this completes, you can use the `waves` wrapper in the project root to run the tool.

	usage: waves
	 -i,--input <arg>                [Required] A wav file to analyze
	 -o,--output <arg>               Where to place the output
	 -s,--samples-per-second <arg>   The samples per second to use
	 -t,--output-type <arg>          The type of output to produce, json or
	                                 csv
