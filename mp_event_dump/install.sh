#!/bin/bash

DIR=$(dirname "$0")
gem build $DIR/mp_event_dump.gemspec
gem install $DIR/mp_event_dump-*.gem
