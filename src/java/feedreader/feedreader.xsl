<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : feedreader.xsl
    Created on : September 20, 2012, 2:13 AM
    Author     : vravuri
    Description:
        This file transforms RSS 2.0 feed XML to a custom XML that will be parsed at client side
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:atom="http://www.w3.org/2005/Atom" version="1.0">

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <feeds>
            <xsl:for-each select="rss/channel/item">
                <feed>
                    <link>
                        <xsl:value-of select="link"/>
                    </link>
                    <title>
                        <xsl:value-of select="title"/>
                    </title>
                </feed>
            </xsl:for-each>
        </feeds>
    </xsl:template>

</xsl:stylesheet>
