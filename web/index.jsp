<%-- 
    Document   : index
    Created on : Sep 20, 2012, 1:24:08 AM
    Author     : vravuri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" language="javascript" src="jquery-1.8.2.js"></script>
        <script type="text/javascript" language="javascript" src="ajaxusingjquery.js"></script> 
        <!-- <script type="text/javascript" language="javascript" src="ajaxwithoutusingjquery.js"></script> -->
        <link rel="stylesheet" href="rssstyle.css" type="text/css" />
        <title>RSS Feed Reader</title>
    </head>
    <body>
        <table>
            <tr>
                <td colspan="2">
                    <h2>RSS Feed Reader</h2>
                </td>
            </tr>
            <tr>
                <td>Select News Topic</td>
                <td>
                    <select id="newstopic">
                        <option value="Business">Business</option>
                        <option value="Technology">Technology</option>
                        <option value="World">World News</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Select News Source</td>
                <td>
                    <select id="newssource">
                        <option value="NYT">New York Times</option>
                        <option value="BBC">BBC</option>
                        <option value="SMH">Sydney Morning Herald</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" id="button" value="Submit"/>
                    <!-- <input type="submit" id="button" onClick="getFeed()" value="Submit"/> -->
                </td>
            </tr>
        </table>
        <div id="feeddisplay" hidden>
            <h3>&nbsp;&nbsp;&nbsp;News Feeds<label id="newstype"></label></h3>
            <ul id="feedcontent"></ul>
        </div>
    </body>
</html>
