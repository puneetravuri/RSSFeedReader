
/*
 * Creates an XMLHTTPRequest for multiple browsers
 * 
 * Author: vravuri
 * Created on : Sep 22, 2012, 1:02:21 AM
 */
function newXMLHttpRequest() {
    var xmlreq = false;
    
    // For non-Microsoft browsers
    if (window.XMLHttpRequest) {
        xmlreq = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        try {
            
            // For Microsfot IE later versions
            xmlreq = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e1) {      
            try {
                
                // For Microsoft IE previous versions
                xmlreq = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e2) {
                
                // Not supported for any browser
                xmlreq = false;
            }
        }
    }
    
    return xmlreq;
}

/*
 *  Callback handler method
 */
function getReadyStateHandler(req, responseXmlHandler) {
    return function () {
        // If the request's status is "complete"
        if (req.readyState == 4) {
       
            // Check that we received a successful response from the server
            if (req.status == 200) {

                // Pass the XML payload of the response to the handler function.
                responseXmlHandler(req.responseXML);
            } else {

                // An HTTP problem has occurred
                alert("HTTP error " + req.status + ": " + req.statusText);
            }
        }
    }
}
 
/*
 *  Gets triggered when a new feed is requested by the user
 */
function getFeed()
{
    // Set the callback handler to displayFeed
    var request = newXMLHttpRequest();
    request.onreadystatechange = getReadyStateHandler(request, displayFeed);
    
    var newstopic = document.getElementById("newstopic").value;
    var newssource = document.getElementById("newssource").value;
    request.open("POST", "RSSNewsFeedReaderServlet", true);
    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    request.send("newstopic="+newstopic+"&newssource="+newssource);
}

/*
 * Call back method that displays the news feed content on the browser
 */
function displayFeed(feedContent)
{
    var pageContent = document.getElementById("feedcontent");
    pageContent.innerHTML = "";
    
    var feeds = feedContent.getElementsByTagName("feeds")[0];
    var feedList = feeds.getElementsByTagName("feed");
    
    // Parse the XML and create the news feed list
    for (var I = 0 ; I < feedList.length ; I++) {
        var feedItem = document.createElement("li");
        var feedLink = document.createElement("a");
        feedLink.setAttribute("href", feedList[I].getElementsByTagName("link")[0].firstChild.nodeValue);
        feedLink.appendChild(document.createTextNode(feedList[I].getElementsByTagName("title")[0].firstChild.nodeValue));        
        feedItem.appendChild(feedLink);
        pageContent.appendChild(feedItem);
    }
    
    // Set the topic name and source to the label
    var newstopic = document.getElementById("newstopic").value;
    var newssource = document.getElementById("newssource").value;
    var newsType = document.getElementById("newstype");
    newsType.innerHTML = "";
    
    if (newssource == "SMH")
        newsType.innerHTML = " - " + newstopic + ", Sydney Morning Herald";
    else if (newssource == "NYT")
        newsType.innerHTML = " - " + newstopic + ", New York Times";
    else
        newsType.innerHTML = " - " + newstopic + ", " + newssource;
    
    // Remove the div tag from hidden state so that it can display the news
    var displayFeed = document.getElementById("feeddisplay");
    displayFeed.removeAttribute("hidden");
}


