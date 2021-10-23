# Import files ---------------------------------------------------------------

import requests # Import the requests library for HTTP requests
import PIL.Image as img # Import the PIL library for image processing
from io import BytesIO  # Import the BytesIO library for image processing
import sys, os  # import sys and os to handle client-side operations

# Define colors constants -----------------------------------------------------

RED = "\033[1;31m"
GREEN = "\033[1;32m"
RESET = "\033[0m"

# Program Functions ------------------------------------------------------------

# page is the URL of the page to be downloaded
# payload is the data to be requested from the server
def request (page, file):
    req = str(page+file)
    r = requests.get(req)
    print(GREEN + r.url + RESET +"\n")
    return r

# image is the image to be opened with PIL
# image is the content from the HTTP request that must be opened as Bytes
def openImg(image):
    i = img.open(image) # Open the image
    i.show()    # Show the image on the screen
    i.close()   # Close the image pointer, the image will still be in the forked memory until closed

# terminate the program and force kill the process
def shutdown():
    print(RED + "<-- The program has ended -->" + RESET)
    os.system("sleep 3")    # Sleep 3 seconds
    os.system("clear")      # Clear the screen
    sys.exit()

# start the program and it's main loop
def startup():
    input(GREEN + "Press Enter to start the program" + RESET)
    os.system("clear")
    print(GREEN + "<-- Starting the program -->\n" + RESET)
    # Get input values -- page and payload
    page = input(GREEN + "Enter the URL of the page: " + RESET)
    return page

# main loop of the program, it will run until the user wants to end the program
# server is the web server to be requested through HTTP
def main(server):
    while True:
        print(''' 
            Possible payloads:
            1. None
            2. text.txt
            3. image.jpg
            4. animation.gif
            5. DISCONNECT
        ''')
        i = int(input(GREEN + "Enter the data to be requested from the server: " + RESET))
        if(i % 5 == 2):
            r = request(server, "/text.txt")  # Send the request
            print(GREEN + "The response is: \n" + RED + str(r.content)+ RESET +"\n") # Print the response
        elif(i % 5 == 3):
            r = request(server, "/image.jpg")  # Send the request
            if(r.status_code == 200):
                print(GREEN + "The response is: \n" + RED + str(r.content)+ RESET +"\n") # Print the response
                openImg(BytesIO(r.content)) # Open the image
        elif(i % 5 == 4):
            r = request(server, "/animation.gif")  # Send the request
            if(r.status_code == 200):
                print(GREEN + "The response is: \n" + RED + str(r.content)+ RESET +"\n") # Print the response
                openImg(BytesIO(r.content)) # Open the image
        elif(i % 5 == 0):
            return
        else:
            r = request(server, "")  # Send the request
            print(GREEN + "The response is: \n" + RED + str(r.content)+ RESET +"\n") # Print the response


# Main program ---------------------------------------------------------------
if __name__ == "__main__":
    main(startup()) # Start the program
    # Close the program
    shutdown()
    
