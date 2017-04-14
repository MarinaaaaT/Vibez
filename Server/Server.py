from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
import ast

class handler(BaseHTTPRequestHandler):
    def set_headers(self):
        self.send_response(200)
        self.send_header('Content-type', 'text/html')
        self.end_headers()

    def do_GET(self):
        self.set_headers()
        #remove first slash
        requested_name = self.raw_requestline.replace('GET ', '').split(' HTTP')[0][1:]

        try:
            #is this a store or get
            store_or_get = requested_name.split('/')[0].lower()
            #to what document? user_data, all_users, or all_data
            doc = requested_name.split('/')[1].lower()
            #data necessary for this doc
            data = requested_name.split('/')[2]

        except IndexError:
            self.wfile.write('<html><body><h1>Query in this format GET_OR_STORE/DOCNAME/DATA:DATA:DATA </h1></body></html>\n')
            return




        if store_or_get == 'get':
            if doc == 'all_users':
                try: 
                    #format = get/all_users/USER
                    all_users = open('all_users', 'r')
                    users_data = all_users.read()
                    all_users.close()
                    self.wfile.write('<html><body><h1> %s </h1></body></html>' % (users_data))

                except IOError:
                    self.wfile.write('<html><body><h1>No user data available.</h1></body></html>\n')
                

            elif doc == ("%s_data" % (data)):

                try:
                    #format = get/USER_data/USER
                    user_data_File = open("%s_data" % (data), 'r')
                    user_data = user_data_File.read()
                    user_data_File.close()
                    
                    self.wfile.write('<h1> GOT </h1>')

                except IOError:
                    self.wfile.write('<html><body><h1>No data available for this user.</h1></body></html>\n')

            elif doc == 'all_data':
                try:
                    #format = get/all_data/now
                    all_data_File = open('all_data', 'r')
                    all_data = all_data_File.read()
                    all_data_File.close()

                except IOError:
                    self.wfile.write('<html><body><h1>No data available.</h1></body></html>\n')

            else:
                self.wfile.write('<html><body><h1> Not a valid document. Try all_data, USER_data, or all_users. </h1></body></html>')

        elif store_or_get == 'store':
            
            if doc == 'all_users':
                (username, password) = data.split(':')
                with open('all_users', 'ab') as content:
                    content.write('''%s : %s \n''' % (username, password))
                self.wfile.write('<html><body><h1>Stored </h1></body></html>\n')

            elif doc == 'all_data':
                (time, mood, location) = data.split(':')
                with open('all_data', 'ab') as content:
                    content.write('''%s : %s : %s \n''' % (time, mood, location))
                self.wfile.write('<html><body><h1>Stored </h1></body></html>\n')

            elif doc == '%s_data' % (data.split(':'))[0]:
                (username, time, mood, location) = data.split(':')
                with open('all_data', 'ab') as content:
                    content.write('''%s : %s : %s \n''' % (time, mood, location))
                self.wfile.write('<html><body><h1>Stored </h1></body></html>\n')

            else:
                self.wfile.write('<html><body><h1> Not a valid document. Try all_data, USER_data, or all_users. </h1></body></html>\n')

        else:
            self.wfile.write('<html><body><h1> Not a valid query. Try store or get. </h1></body></html>\n')
            return

        return

def run():
    http_serv = HTTPServer(('', 8820), handler)
    print 'Starting server'
    http_serv.serve_forever()

if __name__ == "__main__":
    run()
                
