#!/usr/bin/env python
# This is just a little Python script, originally from the Brython project, to run a local webserver.
# Run `python server.py` in the website directory and then go to http://localhost:8000

import sys

if sys.version < '3':
   import BaseHTTPServer as server
   from CGIHTTPServer import CGIHTTPRequestHandler
else:
   import http.server as server
   from http.server import CGIHTTPRequestHandler

server_address = ('', 8000)
handler = CGIHTTPRequestHandler
httpd = server.HTTPServer(server_address, handler)
print("server running on port %s" % server_address[1])
httpd.serve_forever()
