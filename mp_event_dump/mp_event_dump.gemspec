Gem::Specification.new do |s|
  s.name        = 'mp_event_dump'
  s.version     = '0.1.0'
  s.summary     = "Downloads raw event data from Mixpanel."
  s.authors     = ["Kevin MacWhinnie"]
  s.email       = 'km@sayhello.com'
  s.files       = ["bin/mp_event_dump"]
  s.homepage    = 'https://github.com/hello/gu'
  
  s.add_dependency('colorize')
  s.add_dependency('mixpanel_client')
end
