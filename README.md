# gu
assorted utilities relevant to internal operations

## mp_event_dump

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
