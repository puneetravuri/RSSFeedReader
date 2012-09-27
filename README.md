RSSFeedReader
=============

A news feed reader web aplication that is built using Ajax and jQuery.

It supports a pre-configured set of news topics and news sources.
The client is implemented based on Ajax to communicate with the server.


Architecture :-
1. The client web page has option to choose the news topic (World/Business/Technology) and news source (NYT/BBC/SMH).
2. When user submits his/her choice of news feed, these parameters are sent asynchronously to the server.
1. On the server, the servlet :-
    A. Takes the required parameters from the request
    B. Retrieves the corresponding RSS
    C. Transforms it into a custom XML
    C. Returns it back to the client.
2. The client then parses the custom XML and displays the content on the browser.


Features :-
1. jQuery to handle Ajax request/response and animations.
2. News website RSS transformation into custom XML DOM using XSLT.
3. CSS applied on webpage to have a better look and feel.
4. Javascript functions that use jQuery and donot use jQuery - both have been implemented for the sake of comparison.
   The usage of these methods can be swtiched by changing the html on the view.
