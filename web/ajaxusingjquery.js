
/**
 *  jQuery implementation of the Ajax request to fetch the news from server
 *  
 *  Author: vravuri
 *  Created on : Sep 21, 2012, 2:12:48 AM
 */
$(document).ready(function(){
    $("#button").click(function(){
        $.ajax({
            url:"RSSNewsFeedReaderServlet",
            data: {
                newssource: $("#newssource").attr("value"),
                newstopic: $("#newstopic").attr("value")
            },
            beforeSend: function() {
                $("#feeddisplay").slideUp("fast");
                $("#feedcontent").html("");
                
            },
            success: function(feedContent){
                var feeds = feedContent.getElementsByTagName("feeds")[0];
                var feedList = feeds.getElementsByTagName("feed");
                $("#feeddisplay").attr("hidden");
                // Parse the XML and create the news feed list
                for (var I = 0 ; I < feedList.length ; I++) {
                    $("#feedcontent").append("<li><a href=\"" 
                        + feedList[I].getElementsByTagName("link")[0].firstChild.nodeValue 
                        + "\">" + feedList[I].getElementsByTagName("title")[0].firstChild.nodeValue 
                        + "</a></li>");
                }
    
                // Set the topic name and source to the label                
                if ($("#newssource").attr("value") == "SMH")
                    $("#newstype").html(" - " 
                        + $("#newstopic").attr("value") 
                        + ", Sydney Morning Herald");
                else if ($("#newssource").attr("value") == "NYT")
                    $("#newstype").html(" - " 
                        + $("#newstopic").attr("value") 
                        + ", New York Times");
                else
                    $("#newstype").html(" - " 
                        + $("#newstopic").attr("value") 
                        + ", " + $("#newssource").attr("value"));
    
                // Remove the div tag from hidden state so that it can display the news
                $("#feeddisplay").slideDown("slow");
            }
        });
    });
});