<%
/**************************************************************************
Copyright (c) 2011-2013:
Istituto Nazionale di Fisica Nucleare (INFN), Italy
Consorzio COMETA (COMETA), Italy
    
See http://www.infn.it and and http://www.consorzio-cometa.it for details 
on the copyright holders.q
    
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
    
http://www.apache.org/licenses/LICENSE-2.0
    
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
    
@author <a href="mailto:giuseppe.larocca@ct.infn.it">Giuseppe La Rocca</a>
**************************************************************************/
%>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>
<%@ page import="com.liferay.portal.model.Company" %>
<%@ page import="javax.portlet.*" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects/>

<%
  Company company = PortalUtil.getCompany(request);
  String gateway = company.getName();
%>

<jsp:useBean id="GPS_table" class="java.lang.String" scope="request"/>
<jsp:useBean id="GPS_queue" class="java.lang.String" scope="request"/>

<jsp:useBean id="cometa_clustalw_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_clustalw_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_clustalw_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_clustalw_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_clustalw_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_clustalw_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_clustalw_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_clustalw_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_clustalw_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_clustalw_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_clustalw_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="garuda_clustalw_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_clustalw_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_clustalw_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_clustalw_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_clustalw_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_clustalw_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_clustalw_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_clustalw_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_clustalw_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_clustalw_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_clustalw_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="gridit_clustalw_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_clustalw_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_clustalw_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_clustalw_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_clustalw_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_clustalw_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_clustalw_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_clustalw_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_clustalw_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_clustalw_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_clustalw_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="gilda_clustalw_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_clustalw_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_clustalw_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_clustalw_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_clustalw_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_clustalw_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_clustalw_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_clustalw_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_clustalw_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_clustalw_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_clustalw_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="eumed_clustalw_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_clustalw_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_clustalw_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_clustalw_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_clustalw_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_clustalw_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_clustalw_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_clustalw_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_clustalw_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_clustalw_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_clustalw_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="gisela_clustalw_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_clustalw_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_clustalw_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_clustalw_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_clustalw_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_clustalw_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_clustalw_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_clustalw_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_clustalw_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_clustalw_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_clustalw_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="sagrid_clustalw_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_clustalw_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_clustalw_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_clustalw_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_clustalw_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_clustalw_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_clustalw_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_clustalw_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_clustalw_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_clustalw_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_clustalw_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="clustalw_ENABLEINFRASTRUCTURE" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="clustalw_APPID" class="java.lang.String" scope="request"/>
<jsp:useBean id="clustalw_OUTPUT_PATH" class="java.lang.String" scope="request"/>
<jsp:useBean id="clustalw_SOFTWARE" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_HOSTNAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_USERNAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_PASSWORD" class="java.lang.String" scope="request"/>
<jsp:useBean id="SMTP_HOST" class="java.lang.String" scope="request"/>
<jsp:useBean id="SENDER_MAIL" class="java.lang.String" scope="request"/>

<script type="text/javascript">
    
    var latlng2markers = [];
    var icons = [];
    
    icons["plus"] = new google.maps.MarkerImage(
          '<%= renderRequest.getContextPath()%>/images/plus_new.png',
          new google.maps.Size(16,16),
          new google.maps.Point(0,0),
          new google.maps.Point(8,8));
    
    icons["minus"] = new google.maps.MarkerImage(
          '<%= renderRequest.getContextPath()%>/images/minus_new.png',
          new google.maps.Size(16,16),
          new google.maps.Point(0,0),
          new google.maps.Point(8,8));
    
    icons["ce"] = new google.maps.MarkerImage(
            '<%= renderRequest.getContextPath()%>/images/ce-run.png',
            new google.maps.Size(16,16),
            new google.maps.Point(0,0),
            new google.maps.Point(8,8));
    
    function hideMarkers(markersMap,map) 
    {
            for( var k in markersMap) 
            {
                if (markersMap[k].markers.length >1) {
                    var n = markersMap[k].markers.length;
                    var centerMark = new google.maps.Marker({
                        title: "Coordinates:" + markersMap[k].markers[0].getPosition().toString(),
                        position: markersMap[k].markers[0].getPosition(),
                        icon: icons["plus"]
                    });
                    for ( var i=0 ; i<n ; i++ ) 
                        markersMap[k].markers[i].setVisible(false);
                    
                    centerMark.setMap(map);
                    google.maps.event.addListener(centerMark, 'click', function() {
                        var markersMap = latlng2markers;
                        var k = this.getPosition().toString();
                        var visibility = markersMap[k].markers[0].getVisible();
                        if (visibility == false ) {
                            splitMarkersOnCircle(markersMap[k].markers,
                            markersMap[k].connectors,
                            this.getPosition(),
                            map
                        );
                            this.setIcon(icons["minus"]);
                        }
                        else {
                            var n = markersMap[k].markers.length;
                            for ( var i=0 ; i<n ; i++ ) {
                                markersMap[k].markers[i].setVisible(false);
                                markersMap[k].connectors[i].setMap(null);
                            }
                            markersMap[k].connectors = [];
                            this.setIcon(icons["plus"]);
                        }
                    });
                }
            }
    }
    
    function splitMarkersOnCircle(markers, connectors, center, map) 
    {
            var z = map.getZoom();
            var r = 64.0 / (z*Math.exp(z/2));
            var n = markers.length;
            var dtheta = 2.0 * Math.PI / n
            var theta = 0;
            
            for ( var i=0 ; i<n ; i++ ) 
            {
                var X = center.lat() + r*Math.cos(theta);
                var Y = center.lng() + r*Math.sin(theta);
                markers[i].setPosition(new google.maps.LatLng(X,Y));
                markers[i].setVisible(true);
                theta += dtheta;
                
                var line = new google.maps.Polyline({
                    path: [center,new google.maps.LatLng(X,Y)],
                    clickable: false,
                    strokeColor: "#0000ff",
                    strokeOpacity: 1,
                    strokeWeight: 2
                });
                
                line.setMap(map);
                connectors.push(line);
            }
    }
    
    function updateAverage(name) {
        
        $.getJSON('<portlet:resourceURL><portlet:param name="action" value="get-ratings"/></portlet:resourceURL>&clustalw_CE='+name,
        function(data) {                                               
            console.log(data.avg);
            $("#fake-stars-on").width(Math.round(parseFloat(data.avg)*20)); // 20 = 100(px)/5(stars)
            $("#fake-stars-cap").text(new Number(data.avg).toFixed(2) + " (" + data.cnt + ")");
        });                
    }
    
    // Create the Google Map JavaScript APIs V3
    function initialize(lat, lng, zoom) {
        console.log(lat);
        console.log(lng);
        console.log(zoom);
        
        var myOptions = {
            zoom: zoom,
            center: new google.maps.LatLng(lat, lng),
            mapTypeId: google.maps.MapTypeId.ROADMAP
        }
        
        var map = new google.maps.Map(document.getElementById('map_canvas'), myOptions);  
        var image = new google.maps.MarkerImage('<%= renderRequest.getContextPath() %>/images/ce-run.png');
        
        var strVar="";
        strVar += "<table>";
        strVar += "<tr>";
        strVar += "<td>";
        strVar += "Vote the resource availability";
        strVar += "<\/td>";
        strVar += "<\/tr>";
        strVar += "<tr><td>\&nbsp;\&nbsp;";
        strVar += "<\/td><\/tr>";
        
        strVar += "<tr>";
        strVar += "<td>";
        strVar += "Rating: <span id=\"stars-cap\"><\/span>";
        strVar += "<div id=\"stars-wrapper2\">";
        strVar += "<select name=\"selrate\">";
        strVar += "<option value=\"1\">Very poor<\/option>";
        strVar += "<option value=\"2\">Not that bad<\/option>";
        strVar += "<option value=\"3\" selected=\"selected\">Average<\/option>";
        strVar += "<option value=\"4\">Good<\/option>";
        strVar += "<option value=\"5\">Perfect<\/option>";
        strVar += "<\/select>";
        strVar += "<\/div>";
        strVar += "<\/td>";
        strVar += "<\/tr>";

        strVar += "<tr>";        
        strVar += "<td>";
        strVar += "Average: <span id=\"fake-stars-cap\"><\/span>";
        strVar += "";
        strVar += "<div id=\"fake-stars-off\" class=\"stars-off\" style=\"width:100px\">";
        strVar += "<div id=\"fake-stars-on\" class=\"stars-on\"><\/div>";
        strVar += "";
        strVar += "<\/div>";
        strVar += "<\/td>";
        strVar += "<\/tr>";
        strVar += "<\/table>";
    
        var data = <%= GPS_table %>;
        var queues = <%= GPS_queue %>;
        
        var infowindow = new google.maps.InfoWindow();
        google.maps.event.addListener(infowindow, 'closeclick', function() {
            this.setContent('');
        });
        
        var infowindowOpts = { 
            maxWidth: 200
        };
               
       infowindow.setOptions(infowindowOpts);       
       
       var markers = [];
       for( var key in data ){
                        
            var LatLong = new google.maps.LatLng(parseFloat(data[key]["LAT"]), 
                                                 parseFloat(data[key]["LNG"]));                    
            
            // Identify locations on the map
            var marker = new google.maps.Marker ({
                animation: google.maps.Animation.DROP,
                position: LatLong,
                icon: image,
                title : key
            });    
  
            // Add the market to the map
            marker.setMap(map);
            
            var latlngKey=marker.position.toString();
            if ( latlng2markers[latlngKey] == null )
                latlng2markers[latlngKey] = {markers:[], connectors:[]};
            
            latlng2markers[latlngKey].markers.push(marker);
            markers.push(marker);
        
            google.maps.event.addListener(marker, 'click', function() {
             
            var ce_hostname = this.title;
            
            google.maps.event.addListenerOnce(infowindow, 'domready', function() {
                                        
                    $("#stars-wrapper2").stars({
                        cancelShow: false, 
                        oneVoteOnly: true,
                        inputType: "select",
                        callback: function(ui, type, value)
                        { 
                            $.getJSON('<portlet:resourceURL><portlet:param name="action" value="set-ratings"/></portlet:resourceURL>' +
                                '&clustalw_CE=' + ce_hostname + 
                                '&vote=' + value);
                            
                            updateAverage(ce_hostname);                      
                        }
                    });
                    
                    updateAverage(ce_hostname);               
                });              
                                                
                infowindow.setContent('<h3>' + ce_hostname + '</h3><br/>' + strVar);
                infowindow.open(map, this);
                                           
                var CE_queue = (queues[ce_hostname]["QUEUE"]);
                $('#clustalw_CE').val(CE_queue);
                
                marker.setMap(map);
            }); // function                             
        } // for
        
        hideMarkers(latlng2markers, map);
        var markerCluster = new MarkerClusterer(map, markers, {maxZoom: 3, gridSize: 20});
    }    
</script>

<script type="text/javascript">  
    var EnabledInfrastructure = null;           
    var infrastructures = new Array();  
    var i = 0;    
    
    <c:forEach items="<%= clustalw_ENABLEINFRASTRUCTURE %>" var="current">
    <c:choose>
    <c:when test="${current!=null}">
        infrastructures[i] = '<c:out value='${current}'/>';       
        i++;  
    </c:when>
    </c:choose>
    </c:forEach>
        
    for (var i = 0; i < infrastructures.length; i++) {
       console.log("Reading array = " + infrastructures[i]);
       if (infrastructures[i]=="cometa") EnabledInfrastructure='cometa';
       if (infrastructures[i]=="garuda") EnabledInfrastructure='garuda';
       if (infrastructures[i]=="gridit") EnabledInfrastructure='gridit';
       if (infrastructures[i]=="gilda")  EnabledInfrastructure='gilda';
       if (infrastructures[i]=="eumed")  EnabledInfrastructure='eumed';
       if (infrastructures[i]=="gisela") EnabledInfrastructure='gisela';
       if (infrastructures[i]=="sagrid") EnabledInfrastructure='sagrid';
    }
    
    var NMAX = infrastructures.length;
    //console.log (NMAX);
    
    $(document).ready(function() 
    {   
        $('#clustalw_textarea').html("Add here your sequence or use the default demo");
        
        // Toggling the hidden div for the first time.        
        if ($('#divToToggle').css('display') == 'block')         
            $('#divToToggle').toggle();
        
        if ($('#divToToggle2').css('display') == 'block')         
            $('#divToToggle2').toggle();
        
        if ($('#divToToggle3').css('display') == 'block')         
            $('#divToToggle3').toggle();
        
        var lat; var lng; var zoom;
        var found=0;
        
        if (parseInt(NMAX)>1) { 
            console.log ("More than one infrastructure has been configured!");
            $("#error_infrastructure img:last-child").remove();
            $('#error_infrastructure').append("<img width='70' src='<%= renderRequest.getContextPath()%>/images/world.png' border='0'> More than one infrastructure has been configured!");
            lat=19;lng=14;zoom=2; found=1;
        } else if (EnabledInfrastructure=='cometa') {
            console.log ("Start up: enabled the COMETA Grid Infrastructure!");
            $('#cometa_clustalw_ENABLEINFRASTRUCTURE').attr('checked','checked');
            /*$('#cometa_enabled').show();            
            $('#gridit_enabled').hide();
            $('#gilda_enabled').hide();
            $('#eumed_enabled').hide();            
            $('#gisela_enabled').hide();*/
            //$('#error_infrastructure').hide();
            lat=37;lng=14;zoom=7;
            found=1;
        } else if (EnabledInfrastructure=='garuda') {
            console.log ("Start up: enabling garuda!");
            $('#garuda_astra_ENABLEINFRASTRUCTURE').attr('checked','checked');
            lat=29.15;lng=77.41;zoom=4;
            found=1;
        } else if (EnabledInfrastructure=='gridit') {        
            console.log ("Start up: enabled the Italian Grid Infrastructure!");
            $('#gridit_clustalw_ENABLEINFRASTRUCTURE').attr('checked','checked');
            /*$('#gridit_enabled').show();            
            $('#cometa_enabled').hide();
            $('#gilda_enabled').hide();
            $('#eumed_enabled').hide();
            $('#gisela_enabled').hide();*/
            //$('#error_infrastructure').hide();     
            lat=42;lng=12;zoom=5;
            found=1;            
        } else if (EnabledInfrastructure=='eumed') {       
            console.log ("Start up: enabled the Mediterranen Grid Infrastructure!");
            $('#eumed_clustalw_ENABLEINFRASTRUCTURE').attr('checked','checked');
            /*$('#eumed_enabled').show();            
            $('#gridit_enabled').hide();
            $('#cometa_enabled').hide();
            $('#gilda_enabled').hide();
            $('#gisela_enabled').hide();*/
            //$('#error_infrastructure').hide();            
            lat=34;lng=20;zoom=4;
            found=1;
        } else if (EnabledInfrastructure=='gilda') {
            console.log ("Start up: enabled the Indian Grid Infrastructure!");
            $('#gilda_clustalw_ENABLEINFRASTRUCTURE').attr('checked','checked');
            /*$('#gilda_enabled').show();
            $('#eumed_enabled').hide();            
            $('#gridit_enabled').hide();
            $('#cometa_enabled').hide();            
            $('#gisela_enabled').hide();*/
            //$('#error_infrastructure').hide();            
            lat=42;lng=12;zoom=5;
            found=1;    
        } else if (EnabledInfrastructure=='gisela') {        
            console.log ("Start up: enabled the Latin America Grid Infrastructure!");
            $('#gisela_clustalw_ENABLEINFRASTRUCTURE').attr('checked','checked');
            /*$('#gisela_enabled').show();            
            $('#cometa_enabled').hide();
            $('#gridit_enabled').hide();
            $('#eumed_enabled').hide();
            $('#gilda_enabled').hide();*/
            //$('#error_infrastructure').hide();            
            lat=2;lng=-36;zoom=2;
            found=1;            
        } else if (EnabledInfrastructure=='sagrid') {
            console.log ("Start up: enabled the sagrid VO!");
            $('#sagrid_astra_ENABLEINFRASTRUCTURE').attr('checked','checked');
            /*$('#gisela_enabled').show();            
            $('#cometa_enabled').hide();
            $('#gridit_enabled').hide();
            $('#eumed_enabled').hide();
            $('#gilda_enabled').hide();*/
            //$('#error_infrastructure').hide();
            lat=-16;lng=-24;zoom=2;
            found=1;
        }                 
                
        if (found==0) { 
            console.log ("None of the grid infrastructures have been configured!");
            $("#error_infrastructure img:last-child").remove();
            $('#error_infrastructure').append("<img width='35' src='<%= renderRequest.getContextPath()%>/images/Warning.png' border='0'> None of the available grid infrastructures have been configured!");
        }                
        
        var accOpts = {
            change: function(e, ui) {                       
                $("<div style='width:650px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;'>").addClass("notify ui-corner-all").text(ui.newHeader.find("a").text() +
                    " was activated... ").appendTo("#error_message").fadeOut(2500, function(){ $(this).remove(); });               
                // Get the active option                
                var active = $("#accordion").accordion("option", "active");
                if (active==1) initialize(lat, lng, zoom);
            },
            autoHeight: false
        };
        
        // Create the accordions
        //$("#accordion").accordion({ autoHeight: false });
        $("#accordion").accordion(accOpts);
        
        // Create the sliders
        $( "#slider-clustalw-protein-ktup" ).slider({
            orientation: "horizontal",
            range: "min",
            value: 1,
            min: 1,
            max: 5,
            slide: function( event, ui ) {
                $( "#clustalw_protein_ktup" ).val( ui.value );
                $( "input[type=hidden][name='clustalw_protein_ktup']").val( ui.value);
            }
        });
        $( "#clustalw_protein_ktup" ).val( $( "#slider-clustalw-protein-ktup" ).slider( "value" ) );
        
        // Create the sliders
        $( "#slider-clustalw-dna-ktup" ).slider({
            orientation: "horizontal",
            range: "min",
            value: 1,
            min: 1,
            max: 5,
            slide: function( event, ui ) {
                $( "#clustalw_dna_ktup" ).val( ui.value );
                $( "input[type=hidden][name='clustalw_dna_ktup']").val( ui.value);
            }
        });
        $( "#clustalw_dna_ktup" ).val( $( "#slider-clustalw-dna-ktup" ).slider( "value" ) );
            
        // Create the sliders
        $( "#slider-clustalw-protein-windowlength" ).slider({
            orientation: "horizontal",
            range: "min",
            value: 5,
            min: 0,
            max: 10,
            slide: function( event, ui ) {
                $( "#clustalw_protein_window" ).val( ui.value );
                $( "input[type=hidden][name='clustalw_protein_window']").val( ui.value);
            }
        });
        $( "#clustalw_protein_window" ).val( $( "#slider-clustalw-protein-windowlength" ).slider( "value" ) );
        
        // Create the sliders
        $( "#slider-clustalw-dna-windowlength" ).slider({
            orientation: "horizontal",
            range: "min",
            value: 5,
            min: 0,
            max: 10,
            slide: function( event, ui ) {
                $( "#clustalw_dna_window" ).val( ui.value );
                $( "input[type=hidden][name='clustalw_dna_window']").val( ui.value);
            }
        });
        $( "#clustalw_dna_window" ).val( $( "#slider-clustalw-dna-windowlength" ).slider( "value" ) );        
        
        // Create the sliders
        $( "#slider-clustalw-protein-topdiag" ).slider({
            orientation: "horizontal",
            range: "min",
            value: 5,
            min: 1,
            max: 10,
            slide: function( event, ui ) {
                $( "#clustalw_protein_topdiag" ).val( ui.value );
                $( "input[type=hidden][name='clustalw_protein_topdiag']").val( ui.value);
            }
        });
        
        $( "#clustalw-protein-topdiag" ).val( $( "#slider-clustalw-protein-topdiag" ).slider( "value" ) );
        
        // Create the sliders
        $( "#slider-clustalw-dna-topdiag" ).slider({
            orientation: "horizontal",
            range: "min",
            value: 5,
            min: 1,
            max: 10,
            slide: function( event, ui ) {
                $( "#clustalw_dna_topdiag" ).val( ui.value );
                $( "input[type=hidden][name='clustalw_dna_topdiag']").val( ui.value);
            }
        });
        
        $( "#clustalw_dna_topdiag" ).val( $( "#slider-clustalw-dna-topdiag" ).slider( "value" ) );
        
        // Create the sliders
        /*$( "#slider-time" ).slider({
            orientation: "horizontal",
            range: "min",
            value: 120,
            min: 0,
            max: 200,
            slide: function( event, ui ) {
                $( "#time" ).val( ui.value );
                $( "input[type=hidden][name='time']").val( ui.value);
            }
        });*/
        
        // Create the sliders
        $( "#slider-clustalw-gapdistances" ).slider({
            orientation: "horizontal",
            range: "min",
            value: 5,
            min: 1,
            max: 10,
            slide: function( event, ui ) {
                $( "#clustalw_gapdistances" ).val( ui.value );
                $( "input[type=hidden][name='clustalw_gapdistances']").val( ui.value);
            }
        });
        
        $( "#clustalw_gapdistances" ).val( $( "#slider-clustalw-gapdistances" ).slider( "value" ) );
        
        // Create the sliders
        $( "#slider-clustalw-dnagapdistances" ).slider({
            orientation: "horizontal",
            range: "min",
            value: 5,
            min: 1,
            max: 10,
            slide: function( event, ui ) {
                $( "#clustalw_dnagapdistances" ).val( ui.value );
                $( "input[type=hidden][name='clustalw_dnagapdistances']").val( ui.value);
            }
        });
        
        $( "#clustalw_dnagapdistances" ).val( $( "#slider-clustalw-dnagapdistances" ).slider( "value" ) );
        
        // Create the sliders
        $( "#slider-clustalw-numiter" ).slider({
            orientation: "horizontal",
            range: "min",
            value: 1,
            min: 1,
            max: 10,
            slide: function( event, ui ) {
                $( "#clustalw_numiter" ).val( ui.value );
                $( "input[type=hidden][name='clustalw_numiter']").val( ui.value);
            }
        });
        
        $( "#clustalw_numiter" ).val( $( "#slider-clustalw-numiter" ).slider( "value" ) );
        
        // Create the sliders
        $( "#slider-clustalw-dnanumiter" ).slider({
            orientation: "horizontal",
            range: "min",
            value: 1,
            min: 1,
            max: 10,
            slide: function( event, ui ) {
                $( "#clustalw_dnanumiter" ).val( ui.value );
                $( "input[type=hidden][name='clustalw_dnanumiter']").val( ui.value);
            }
        });
        
        $( "#clustalw_dnanumiter" ).val( $( "#slider-clustalw-dnanumiter" ).slider( "value" ) );
          
        // Validate input form
        //$('#commentForm').validate();
        $('#commentForm').validate({
            rules: {
                clustalw_protein_ktup: {
                    required: true,
                    min: 0,
                    max: 255                    
                },
                clustalw_protein_window: {                    
                    required: true,
                    range: [ 0, 255 ]
                }
            }                                  
        });
        
        // Check file input size with jQuery (Max. 2.5MB)
        $('input[type=file][name=\'clustalw_file\']').bind('change', function() {
            if (this.files[0].size/1000 > 25600) {     
                // Remove the img and text (if any)
                $("#error_message img:last-child").remove();
                $("#error_message").empty();
                $('#error_message').append("<img width='35' src='<%= renderRequest.getContextPath()%>/images/Warning.png' border='0'> The user demo file must be less than 2.5MB");
                $("#error_message").css({"color":"red","font-size":"14px"});
                // Removing the input file
                $('input[type=\'file\'][name=\'clustalw_file\']').val('');
                return false;
            }           
        });                
        
        $("#commentForm").bind('submit', function() {        
                        
            var flag=true;
            // Remove the img and text error (if any)
            $("#error_message img:last-child").remove();
            $("#error_message").empty();
            
            // Check if the range of (clustalw_protein_ktup and clustalw_protein_window) params are ok.
            if ( parseInt($("#clustalw_protein_window").val()) < parseInt($("#clustalw_protein_ktup").val()) ) {                
                $('#error_message').append("<img width='35' src='<%= renderRequest.getContextPath()%>/images/Warning.png' border='0'> Wrong parameters range");
                $("#error_message").css({"color":"red","font-size":"14px"});                   
                return false;
                flag=false;
            }
            
            // Check if the uploaded file is a .txt file.
            if ($('input:checked[type=\'radio\'][name=\'clustalw_demo\']').val() == "clustalw_ASCII")
            {
                var ext = ($('input[type=file][name=\'clustalw_file\']').val().split('.').pop().toLowerCase());                
                if($.inArray(ext, ['txt']) == -1) {                    
                    $('#error_message').append("<img width='35' src='<%= renderRequest.getContextPath()%>/images/Warning.png' border='0'> Invalid file format");
                    $("#error_message").css({"color":"red","font-size":"14px"});                   
                    return false;
                    flag=false;
                }                
            } else
                      
            // Check if the input settings are valid before to
            // display the warning message.
            if ( (($('input:checked[type=\'radio\'][name=\'clustalw_demo\']').val() == "clustalw_ASCII") &&
                 ($('input[type=file][name=clustalw_file]').val() == "")) ||
                
                 (($('input:checked[type=\'radio\'][name=\'clustalw_demo\']').val() == "clustalw_TEXTAREA") &&
                 $('#clustalw_textarea').val() == "") ||
                
                 (($('input:checked[type=\'radio\'][name=\'clustalw_demo\']').val() != "clustalw_ASCII") &&
                 ($('input:checked[type=\'radio\'][name=\'clustalw_demo\']').val() != "clustalw_TEXTAREA")) ) 
            {            
                // Display the warning message  
                $('#error_message').append("<img width='35' src='<%= renderRequest.getContextPath()%>/images/Warning.png' border='0'> You missed many settings! They have been highlighted below.");
                $("#error_message").css({"color":"red","font-size":"14px"});
                flag=false;
            }
            
            if (flag) {
                $("#error_message").css({"color":"red","font-size":"14px", "font-family": "Tahoma,Verdana,sans-serif,Arial"});                
                $('#error_message').append("<img width='30' src='<%= renderRequest.getContextPath()%>/images/button_ok.png' border='0'> Submission in progress...")(30000, function(){ $(this).remove(); });
            }            
        });
                   
        // Roller
        $('#clustalw_footer').rollchildren({
            delay_time         : 3000,
            loop               : true,
            pause_on_mouseover : true,
            roll_up_old_item   : true,
            speed              : 'slow'   
        });
        
        $("#stars-wrapper1").stars({
            cancelShow: false,
            captionEl: $("#stars-cap"),
            callback: function(ui, type, value)
            {
                $.getJSON("ratings.php", {rate: value}, function(json)
                {                                        
                    $("#fake-stars-on").width(Math.round( $("#fake-stars-off").width()/ui.options.items*parseFloat(json.avg) ));
                    $("#fake-stars-cap").text(json.avg + " (" + json.votes + ")");
                });
            }
         });                  
         
    });

    function enable_ClustalwDemo(f) 
    {
        var _fasta="";
        _fasta += ">seq0\n";
        _fasta += "FQTWEEFSRAAEKLYLADPMKVRVVLKYRHVDGNLCIKVTDDLVCLVYRTDQAQDVKKIEKF\n";
        _fasta += ">seq1\n";
        _fasta += "KYRTWEEFTRAAEKLYQADPMKVRVVLKYRHCDGNLCIKVTDDVVCLLYRTDQAQDVKKIEKFHSQLMRLME LKVTDNKECLKFKTDQAQEAKKMEKLNNIFFTLM\n"; 
        _fasta += ">seq2\n";
        _fasta += "EEYQTWEEFARAAEKLYLTDPMKVRVVLKYRHCDGNLCMKVTDDAVCLQYKTDQAQDVKKVEKLHGK\n";
        _fasta += ">seq3\n";
        _fasta += "MYQVWEEFSRAVEKLYLTDPMKVRVVLKYRHCDGNLCIKVTDNSVCLQYKTDQAQDVK\n";
        _fasta += ">seq4\n"; 
        _fasta += "EEFSRAVEKLYLTDPMKVRVVLKYRHCDGNLCIKVTDNSVVSYEMRLFGVQKDNFALEHSLL\n"; 
        _fasta += ">seq5\n"; 
        _fasta += "SWEEFAKAAEVLYLEDPMKCRMCTKYRHVDHKLVVKLTDNHTVLKYVTDMAQDVKKIEKLTTLLMR\n"; 
        _fasta += ">seq6\n"; 
        _fasta += "FTNWEEFAKAAERLHSANPEKCRFVTKYNHTKGELVLKLTDDVVCLQYSTNQLQDVKKLEKLSSTLLRSI\n"; 
        _fasta += ">seq7\n"; 
        _fasta += "SWEEFVERSVQLFRGDPNATRYVMKYRHCEGKLVLKVTDDRECLKFKTDQAQDAKKMEKLNNIFF\n";
        _fasta += ">seq8\n"; 
        _fasta += "SWDEFVDRSVQLFRADPESTRYVMKYRHCDGKLVLKVTDNKECLKFKTDQAQEAKKMEKLNNIFFTLM\n";
        _fasta += ">seq9\n"; 
        _fasta += "KNWEDFEIAAENMYMANPQNCRYTMKYVHSKGHILLKMSDNVKCVQYRAENMPDLKK\n";
        _fasta += ">seq10\n"; 
        _fasta += "FDSWDEFVSKSVELFRNHPDTTRYVVKYRHCEGKLVLKVTDNHECLKFKTDQAQDAKKMEK";
        
        if ($('input:checked[type=\'radio\'][name=\'clustalw_demo\']',f).val() == "clustalw_ASCII") {
            // Enabling the uploading of the user ASCII file
            $('input[type=\'file\'][name=\'clustalw_file\']').removeAttr('disabled');
            // Disabling the specification of the clustalw text via textarea
            $('#clustalw_textarea').html("Add here your sequence or use the default demo");            
            $('#clustalw_textarea').attr('disabled','disabled');
        } else {        
            // Disabling the uploading of the user file
            $('input[type=\'file\'][name=\'clustalw_file\']').attr('disabled','disabled');
            // Enabling the specification of the clustalw text via textarea            
            $('#clustalw_textarea').html(_fasta);
            $('#clustalw_textarea').removeAttr('disabled');
        }     
    }
    
    function update_radio() {        
        if ($('#divToToggle').css('display') == 'block')
        {        
            $('#aTag').html('Set 2 - Set up your Pairwise Alignment Options &#9660');        
            $('#divToToggle').toggle();
        }
    }
    
    function DisableElement() {
     /*   if (($("select option:selected").val()=="PROTEIN") ||
             ($("select option:selected").val()=="DNA"))            
            {
                // Firstly remove the selection (if any)
                $("#EnableMIDIAnalysis").attr("checked", false);
                // Lastly disable the checkbox
                $("#EnableMIDIAnalysis").attr("disabled", true);
            }
        else 
            $("#EnableMIDIAnalysis").removeAttr("disabled");*/
                
        if ($('#divToToggle').css('display') == 'block')
            $('#divToToggle').toggle();
        
        if ($('#divToToggle2').css('display') == 'block')
            $('#divToToggle2').toggle();
        
        if ($('#divToToggle3').css('display') == 'block')
            $('#divToToggle3').toggle();
    }
    
    function toggleAndChangeText() 
    {                
        if ($('#divToToggle').css('display') == 'none') 
        {
            // Collapse the div
            if ($('input[name=clustalw_alignmenttype]:checked', '#commentForm').val()=='slow') 
                var string = "Step 2 - Slow Pairwise Aligment Options &#9658";
            else 
                var string = "Step 2 - Fast Pairwise Aligment Options &#9658";
                        
            $('#aTag').html(string);
            
            if ($("select option:selected").val()=="protein" &&
                $('input[name=clustalw_alignmenttype]:checked', '#commentForm').val()=='slow') 
            {
                console.log("Step 2 - Configure advanced settings for the sequence PROTEIN and alignment type SLOW");
                $('#PROTEIN_SLOWToggle').show();
                $('#PROTEIN_FASTToggle').hide();
                $('#DNA_SLOWToggle').hide();
                $('#DNA_FASTToggle').hide();
            }
            
            if ($("select option:selected").val()=="protein" &&
                $('input[name=clustalw_alignmenttype]:checked', '#commentForm').val()=='fast') 
            {
                console.log("Step 2 - Configure advanced settings for the sequence PROTEIN and alignment type FAST");
                $('#PROTEIN_FASTToggle').show();
                $('#PROTEIN_SLOWToggle').hide();  
                $('#DNA_SLOWToggle').hide();
                $('#DNA_FASTToggle').hide();
            }
            
            if ($("select option:selected").val()=="dna" &&
                $('input[name=clustalw_alignmenttype]:checked', '#commentForm').val()=='slow') 
            {
                console.log("Step 2 - Configure advanced settings for the sequence DNA and alignment type SLOW");
                $('#DNA_SLOWToggle').show();
                $('#PROTEIN_FASTToggle').hide();  
                $('#PROTEIN_SLOWToggle').hide();
                $('#DNA_FASTToggle').hide();
            }
            if ($("select option:selected").val()=="dna" &&
                $('input[name=clustalw_alignmenttype]:checked', '#commentForm').val()=='fast') 
            {
                console.log("Step 2 - Configure advanced settings for the sequence DNA and alignment type FAST");
                $('#DNA_FASTToggle').show();
                $('#DNA_SLOWToggle').hide();
                $('#PROTEIN_FASTToggle').hide();  
                $('#PROTEIN_SLOWToggle').hide();                
            }            
        } else 
            // Expand the div
            $('#aTag').html('Set 2 - Set up your Pairwise Alignment Options &#9660');
        
        $('#divToToggle').toggle();
}

function toggleAndChangeText2()
{
    if ($('#divToToggle2').css('display') == 'none') 
    {        
        // Expand the div
        $('#aTag2').html('Set 3 - Set your Multiple Sequence Alignment Options &#9658');   
        
        if ($("select option:selected").val()=="protein")
        {
            console.log("Step 3 - Configure advanced settings for Sequence PROTEIN and alignment type SLOW/FAST");
            $('#PROTEIN_3Toggle').show();
            $('#DNA_3Toggle').hide();            
        }
        
        if ($("select option:selected").val()=="dna")
        {
            console.log("Step 3 - Configure advanced settings for Sequence DNA and alignment type SLOW/FAST");            
            $('#DNA_3Toggle').show();
            $('#PROTEIN_3Toggle').hide();
        }
        
    } else 
            // Expand the div
            $('#aTag2').html('Set 3 - Set your Multiple Sequence Alignment Options &#9660');
        
    $('#divToToggle2').toggle();
}

function toggleAndChangeText3()
{
    if ($('#divToToggle3').css('display') == 'none') 
    {        
        // Expand the div
        $('#aTag3').html('Set 4 - Set your Advanced Output Options &#9658');
        $('#OUTPUT_Toggle').show();
    } else 
            // Expand the div
            $('#aTag3').html('Set 4 - Set your Advanced Output Options &#9660');
        
    $('#divToToggle3').toggle();
}
    
</script>

<br/>
<form enctype="multipart/form-data" 
      id="commentForm" 
      action="<portlet:actionURL><portlet:param name="ActionEvent" 
      value="SUBMIT_CLUSTALW_PORTLET"/></portlet:actionURL>"      
      method="POST">

<fieldset>
<legend>ClustalW2 Input Form</legend>
<div style="margin-left:15px" id="error_message"></div>

<!-- Accordions -->
<div id="accordion" style="width:650px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">
<h3><a href="#">
    <img width="32" 
         align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/glass_numbers_1.png" />
    <b>Portlet Settings</b>
    <img width="45" align="absmiddle" 
         src="<%=renderRequest.getContextPath()%>/images/info_image.png"/>
    </a>
</h3>
<div> <!-- Inizio primo accordion -->
<p>The portlet has been configured for:</p>
<table id="results" border="0" width="600">
    
<!-- COMETA -->
<tr></tr>
<tr>
    <td>  
        <c:forEach var="enabled" items="<%= clustalw_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='cometa'}">
                <c:set var="results_cometa" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_cometa=='true'}">        
            <input type="checkbox" 
                   id="cometa_clustalw_ENABLEINFRASTRUCTURE"
                   name="cometa_clustalw_ENABLEINFRASTRUCTURE"
                   size="55px;"
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The COMETA Grid Infrastructure
                       
            </c:when>
        </c:choose>
    </td>
</tr>

<!-- GARUDA -->
<tr></tr>
<tr>
    <td>      
        <c:forEach var="enabled" items="<%= clustalw_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='garuda'}">
                <c:set var="results_garuda" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_garuda=='true'}">        
            <input type="checkbox" 
                   id="garuda_clustalw_ENABLEINFRASTRUCTURE"
                   name="garuda_clustalw_ENABLEINFRASTRUCTURE"
                   size="55px;" 
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The GARUDA Infrastructure
                    
            </c:when>
        </c:choose>       
    </td>
</tr>

<!-- GRIDIT -->
<tr></tr>
<tr>
    <td>      
        <c:forEach var="enabled" items="<%= clustalw_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='gridit'}">
                <c:set var="results_gridit" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_gridit=='true'}">        
            <input type="checkbox" 
                   id="gridit_clustalw_ENABLEINFRASTRUCTURE"
                   name="gridit_clustalw_ENABLEINFRASTRUCTURE"
                   size="55px;" 
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The Italian Grid Infrastructures
                    
            </c:when>
        </c:choose>       
    </td>
</tr>

<!-- GILDA -->
<tr></tr>
<tr>
    <td>
        <c:forEach var="enabled" items="<%= clustalw_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='gilda'}">
                <c:set var="results_gilda" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_gilda=='true'}">        
            <input type="checkbox" 
                   id="gilda_clustalw_ENABLEINFRASTRUCTURE"
                   name="gilda_clustalw_ENABLEINFRASTRUCTURE"
                   size="55px;"
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The GILDA t-Infrastructure
                    
            </c:when>
        </c:choose>
    </td>
</tr>

<!-- EUMED -->
<tr></tr>
<tr>
    <td>
        <c:forEach var="enabled" items="<%= clustalw_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='eumed'}">
                <c:set var="results_eumed" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_eumed=='true'}">        
            <input type="checkbox" 
                   id="eumed_clustalw_ENABLEINFRASTRUCTURE"
                   name="eumed_clustalw_ENABLEINFRASTRUCTURE"
                   size="55px;"
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The Mediterranean Grid Infrastructure
                 
            </c:when>
        </c:choose>
    </td>
</tr>

<!-- GISELA -->
<tr></tr>
<tr>
    <td>
        <c:forEach var="enabled" items="<%= clustalw_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='gisela'}">
                <c:set var="results_gisela" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_gisela=='true'}">        
            <input type="checkbox" 
                   id="gisela_clustalw_ENABLEINFRASTRUCTURE"
                   name="gisela_clustalw_ENABLEINFRASTRUCTURE"
                   size="55px;"
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The Latin America Grid Infrastructure
                  
            </c:when>
        </c:choose>                
    </td>
</tr>

<!-- SAGRID -->
<tr></tr>
<tr>
    <td>
        <c:forEach var="enabled" items="<%= clustalw_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='sagrid'}">
                <c:set var="results_sagrid" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_sagrid=='true'}">        
            <input type="checkbox" 
                   id="sagrid_clustalw_ENABLEINFRASTRUCTURE"
                   name="sagrid_clustalw_ENABLEINFRASTRUCTURE"
                   size="55px;"
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The South African Grid Infrastructure
                   
            </c:when>
        </c:choose>                
    </td>
</tr>

</table>
<br/>
<div style="margin-left:15px" 
     id="error_infrastructure" 
     style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px; display:none;">    
</div>
<br/>

<p align="center">
<img width="120" src="<%=renderRequest.getContextPath()%>/images/separatore.gif"/>
</p>

<p align="justify">
<u>Instructions for users</u>:<br/>
~ This portlet performs the Multiple Sequence Alignment (ClustalW2) for DNA or proteins.<br/>
~ It attempts to calculate the best match for the selected sequences, and lines them up<br/>
  so that the identities, similarities and differences can be seen.<br/><br/>
  
  ~ The present service is based on the following standards and software frameworks:<br/>
  <p align="center">
      
  <!--a href="#">
  <img width="75" src="<%=renderRequest.getContextPath()%>/images/clustalw_big.png"/></a-->
  
  <a href="http://grid.in2p3.fr/jsaga/">
  <img width="200" src="<%=renderRequest.getContextPath()%>/images/logo-jsaga.png"/></a>

  <a href="http://www.catania-science-gateways.it">
  <img width="100" src="<%=renderRequest.getContextPath()%>/images/CataniaScienceGateways.png"/></a>
  </p>
  
  <img width="20" src="<%=renderRequest.getContextPath()%>/images/help.png" title="Read the Help"/>
   For further details, please click
   <a href="<portlet:renderURL portletMode='HELP'><portlet:param name='action' value='./help.jsp' />
            </portlet:renderURL>">here</a>
   <br/>
</p>

  Inputs:<br/>
~ This program accepts a wide range of input formats, including:<br/>
  NBRF/PIR, FASTA, EMBL/Swiss-Prot, Clustal, GCC/MSF, GCG9 RSF, and GDE.<br/>
~ The algorithm and some alignment options.<br/><br/>

Each run will produce:<br/>
~ std.out: the standard output file;<br/>
~ std.err: the standard error file;<br/>
~ tar.gz:  the results of the sequence alignment.<br/>

<br/>
<table id="results" border="0" width="600">
    <tr>
        <td>
        <u>About ClustalW2:</u><br/>
        <p align="justify">
        ClustalW2 is a general purpose multiple sequence alignment program for DNA or proteins. 
        It produces biologically meaningful multiple sequence alignments of divergent sequences. 
        It calculates the best match for the selected sequences, and lines them up so that the identities, 
        similarities and differences can be seen. Evolutionary relationships can be seen via viewing 
        Cladograms or Phylograms.
        </p>
        </td>
        <td>&nbsp;</td>
        <td width="90" align="center">
            <a href="#">
            <img width="75"                  
                 src="<%=renderRequest.getContextPath()%>/images/clustalw_big.png"/>
            </a>
        </td>
        <td>&nbsp;</td>
    </tr>
</table>

For further information, please refer to the output.README file produced during the run.
<br/><br/>

<p>If you need to change some preferences, please contact the
<a href="mailto:credentials-admin@ct.infn.it?Subject=Request for Technical Support [<%=gateway%> Science Gateway]&Body=Describe Your Problems&CC=sg-licence@ct.infn.it"> administrator</a>
</p>

<liferay-ui:ratings
    className="<%= it.infn.ct.clustalw.Clustalw.class.getName()%>"
    classPK="<%= request.getAttribute(WebKeys.RENDER_PORTLET).hashCode()%>" />
    
<!--div id="pageNavPosition"></div-->
</div> <!-- Fine Primo Accordion -->

<h3><a href="#">
    <img width="32" 
         align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/glass_numbers_2.png" />
    <b>The Infrastructure(s)</b>
    <img width="55" align="absmiddle" 
         src="<%=renderRequest.getContextPath()%>/images/grid.png"/>
    </a>
</h3>           
<div> <!-- Inizio Terzo accordion -->
    <p align="justify">
    See with the Google Map API the current status of the computing testbed.
    Select the GPS location of the grid resource where you want run your sequence
    alignment <u>OR, BETTER,</u> let the Science Gateway to choose the best one for you!
    </p>
    <p>This option is <u>NOT SUPPORTED</u> in multi-infrastructure mode!</p>

    <table border="0">
        <tr>
            <td><legend>Legend</legend></td>
            <td>&nbsp;<img src="<%=renderRequest.getContextPath()%>/images/plus_new.png"/></td>
            <td>&nbsp;Split close sites&nbsp;</td>
        
            <td><img src="<%=renderRequest.getContextPath()%>/images/minus_new.png"/></td>
            <td>&nbsp;Unsplit close sites&nbsp;</td>
            
            <td><img src="<%=renderRequest.getContextPath()%>/images/ce-run.png"/></td>
            <td>&nbsp;Computing resource&nbsp;</td>
        </tr>    
        <tr><td>&nbsp;</td></tr>
    </table>

    <legend>
        <div id="map_canvas" style="width:570px; height:600px;"></div>
    </legend>

    <input type="hidden" 
           name="clustalw_CE" 
           id="clustalw_CE"
           size="25px;" 
           class="textfield ui-widget ui-widget-content"
           value=""/>                  
</div> <!-- Fine Secondo Accordion -->        

<h3><a href="#">
    <img width="32" 
         align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/glass_numbers_3.png" />
    <b>Sequences & Options
    </b>
    <img width="40" align="absmiddle" 
         src="<%=renderRequest.getContextPath()%>/images/icon_small_settings.png"/>
    </a>
</h3>           
<div> <!-- Inizio Terzo accordion -->
<p>Please, enter the sequence you want to analyze in the text-area below, <u>OR</u> upload a file</p>
<table border="0" width="500">
    <tr>
        <td width="160">
            <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Choose the input type of your sequences"/>
            
            <label for="clustalw_type">Choose the type of your sequences, between </label><em>*</em>
            
            <select name="clustalw_type" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px;"
                onChange="DisableElement();">                                
            
            <option value="protein">Protein</option>        
            <option value="dna">DNA</option>            
            </select>
        </td>
    </tr>        
    
    <tr>        
        <td width="160">
            <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Upload your sequence to be processed as ASCII file"/>
            
            <input type="radio" 
                   name="clustalw_demo"
                   id="clustalw_demo"
                   value="clustalw_ASCII"
                   class="required"
                   onchange="enable_ClustalwDemo(this.form);"/>Upload your input file (Max 2,5MB) <em>*</em>
            
             <input type="file" name="clustalw_file" width="500" class="required" 
                    style="padding-left: 1px; border-style: solid; border-color: gray; border-width: 1px; padding-left: 1px;"
                    disabled="disabled"/>
        </td>
    </tr>
    
    <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Use the texta-area to insert the sequence to be processed"/>
        
            <input type="radio" 
                   name="clustalw_demo" 
                   id="clustalw_demo"
                   value="clustalw_TEXTAREA" 
                   class="required"
                   onchange="enable_ClustalwDemo(this.form);"/>Insert here the sequence to be analyzed<em>*</em>
        </td>
    <tr>
        <td>            
            <textarea id="clustalw_textarea" 
                      name="clustalw_textarea"
                      style="padding-left: 1px; border-style: solid; border-color: grey; border-width: 1px; padding-left: 1px;"
                      class="required"
                      disabled="disabled"
                      rows="5" cols="70">                
            </textarea>            
        </td>
    </tr>
    
    <tr>
        <td width="160">
            <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Choose the alignment type of your sequences"/>
            
            <label for="clustalw_alignmenttype">Alignment type </label><em>*</em>
            
            <input type="radio" 
                   name="clustalw_alignmenttype"
                   id="clustalw_alignmenttype"
                   value="slow"
                   class="required"
                   checked="checked"
                   onchange="update_radio(this.form);"/> Slow, but accurate
            
            <input type="radio" 
                   name="clustalw_alignmenttype"
                   id="clustalw_alignmenttype"
                   value="fast"
                   class="required"
                   onchange="update_radio(this.form);"/> Fast, but approximate          
        </td>
    </tr>
    
    <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Choose a description for your run "/>
        
        <label for="clustalw_desc">Description</label>
                      
        <input type="text"                
               id="clustalw_desc"
               name="clustalw_desc"
               style="padding-left: 1px; border-style: solid; border-color: grey; border-width: 1px; padding-left: 1px;"
               value="Please, insert here a description for your job"
               size="57" />
        </td>           
    </tr>
    
    <tr><td><br/></td></tr>
            
    <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Enable email notification to the user"/>
        
        <c:set var="enabled_SMTP" value="<%= SMTP_HOST %>" />
        <c:set var="enabled_SENDER" value="<%= SENDER_MAIL %>" />
        <c:choose>
        <c:when test="${empty enabled_SMTP || empty enabled_SENDER}">
        <input type="checkbox" 
               name="EnableNotification"
               disabled="disable"
               value="yes" /> Notification
        </c:when>
        <c:otherwise>
        <input type="checkbox" 
               name="EnableNotification"               
               value="yes" /> Notification
        </c:otherwise>
        </c:choose>
        
        <img width="70"
             id="EnableNotificationid"             
             src="<%= renderRequest.getContextPath()%>/images/mailing2.png" 
             border="0"/>
        </td>                
    </tr>
    
    <tr><td><br/></td></tr>
    
    <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Customize some ClustalW2 algorithm's parameters (for experts)"/>
        
        <a id="aTag" href="javascript:toggleAndChangeText();">
        Set 2 - Set up your Pairwise Alignment Options &#9660;        
        </a>
        
        <div id="divToToggle" display="none">
        
        <!-- ------------------------------- -->
        <!--  PROTEIN_FAST Advanced Settings  -->
        <!-- ------------------------------- -->
        <div id="div1" display="none">
        <br/>The default settings will fulfill the needs of most users.<br/>
        Configure advanced settings for the selected alignment options (if needed).<br/><br/>
        
        <table id="PROTEIN_FASTToggle" border="0">
        <tr> 
        <td width="200">
            <img width="30" 
                 align="absmiddle"
                 src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
                 border="0" title="Please select the KTUP (Word Size) [1, 5]"/>

            <label for="clustalw_protein_ktup">KTUP (Word Size) <em>*</em></label>                        
        </td>

        <td width="300"><div align="absmiddle" id="slider-clustalw-protein-ktup"></div></td>
        <td width="50" align="right">
            <input type="hidden" 
               name="clustalw_protein_ktup"
               value="1"/>
            
            <input type="text" 
               id="clustalw_protein_ktup"
               value="1"
               disabled="disabled"                  
               style="width:30px; border:0; background:#C9C9C9; color:black; font-weight:bold;"
               class="textfield ui-widget ui-widget-content ui-state-focus"/>
        </td>
        </tr>
        
        <tr> 
        <td width="150">
            <img width="30" 
                 align="absmiddle"
                 src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
                 border="0" title="Please select the WINDOW LENGTH [0, 10]"/>

            <label for="clustalw_protein_window">WINDOW LENGTH <em>*</em></label>                        
        </td>

        <td width="350"><div align="absmiddle" id="slider-clustalw-protein-windowlength"></div></td>
        <td width="50" align="right">
            <input type="hidden" 
               name="clustalw_protein_window"
               value="5"/>
            
            <input type="text" 
               id="clustalw_protein_window"
               value="5"
               disabled="disabled"                  
               style="width:30px; border:0; background:#C9C9C9; color:black; font-weight:bold;"
               class="textfield ui-widget ui-widget-content ui-state-focus"/>
        </td>
        </tr>                
        
        <tr> 
        <td width="150">
            <img width="30" 
                 align="absmiddle"
                 src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
                 border="0" title="Please select the TOPDIAG Length [0, 10]"/>

            <label for="clustalw_protein_topdiag">TOPDIAG <em>*</em></label>                        
        </td>

        <td width="350"><div align="absmiddle" id="slider-clustalw-protein-topdiag"></div></td>
        <td width="50" align="right">
            <input type="hidden" 
               name="clustalw_protein_topdiag"
               value="5"/>
            
            <input type="text" 
               id="clustalw_protein_topdiag"
               value="5"
               disabled="disabled"                  
               style="width:30px; border:0; background:#C9C9C9; color:black; font-weight:bold;"
               class="textfield ui-widget ui-widget-content ui-state-focus"/>
        </td>
        </tr>
        
        <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the SCORE TYPE to be used"/>
        
        <label for="clustalw_score">SCORE TYPE </label><em>*</em>
        </td>
            
        <td>
        <select name="clustalw_score" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">                                
            
        <option value="percent" selected="selected">Percent</option>        
        <option value="absolute">Absolute</option>
        </select>
        </td>                
        </tr>
                
        <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"             
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the Pairgap to be used"/>
        
        <label for="clustalw_pairgap">PAIRGAP </label><em>*</em>
        </td>
        
        <td>
        <select name="clustalw_pairgap" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">
            
        <option value="3" selected="seletect">3</option>
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="4">4</option>
        <option value="5">5</option>
        <option value="10">10</option>
        <option value="25">25</option>
        <option value="50">50</option>
        <option value="100">100</option>
        <option value="250">250</option>
        <option value="500">500</option>
        </select>
        </td>                
        </tr>
        
        </table>
        </div>
        <!-- ------------------------------- -->
        <!--  PROTEIN_FAST Advanced Settings  -->
        <!-- ------------------------------- -->
        
        <!-- ------------------------------- -->
        <!-- PROTEIN_SLOW Advanced Settings -->
        <!-- ------------------------------- -->        
        <table id="PROTEIN_SLOWToggle" border="0">
        <tr>
        <td width="200">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the Protein Weight Matrix to be used"/>
        
        <label for="clustalw_pwmatrix">Protein Weight Matrix </label><em>*</em>
        </td>
        
        <td>
        <select name="clustalw_pwmatrix" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">
            
        <option value="gonnet" selected="selected">Gonnet</option>
        <option value="blosum">BLOSUM</option>
        <option value="pam">PAM</option>
        <option value="id">ID</option>
        </select>
        </td>                
        </tr>
        
        <tr>
        <td width="200">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the GAP OPEN to be used"/>
        
        <label for="clustalw_gapopen">GAP OPEN </label><em>*</em>
        </td>
        
        <td>
        <select name="clustalw_pwgapopen" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">
                    
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="5">5</option>
        <option value="10" selected="selected">10</option>
        <option value="25">25</option>
        <option value="50">50</option>
        <option value="100">100</option>
        </select>
        </td>                
        </tr>
        
        <tr>
        <td width="200">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the GAP EXTENTION to be used"/>
        
        <label for="clustalw_pwgapext">GAP EXTENTION </label><em>*</em>
        </td>
        
        <td>
        <select name="clustalw_pwgapext" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">
            
        <option value="0.05">0.05</option>
        <option value="0.1" selected="selected">0.1</option>        
        <option value="0.5">0.5</option>
        <option value="1.0">1.0</option>
        <option value="2.5">2.5</option>
        <option value="5.0">5.0</option>
        <option value="7.5">7.5</option>
        <option value="10.0">10.0</option>
        </select>
        </td>                
        </tr>
        
        </table>        
        <!-- ------------------------------- -->
        <!-- PROTEIN_SLOW Advanced Settings -->
        <!-- ------------------------------- -->
        
        <!-- ------------------------------- -->
        <!-- DNA_SLOW Advanced Settings -->
        <!-- ------------------------------- -->
        <table id="DNA_SLOWToggle" border="0">
        <tr>
        <td width="200">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the DNA Weight Matrix to be used"/>
        
        <label for="clustalw_pwdnamatrix">DNA Weight Matrix </label><em>*</em>
        </td>
        
        <td>
        <select name="clustalw_pwdnamatrix" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">
            
        <option value="IUB" selected="selected">IUB</option>
        <option value="ClustalW">ClustalW</option>
        </select>
        </td>                
        </tr>
        
        <tr>
        <td width="200">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the GAP OPEN to be used"/>
        
        <label for="clustalw_pwgapopen">GAP OPEN </label><em>*</em>
        </td>
        
        <td>
        <select name="clustalw_pwgapopen" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">
                            
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="5">5</option>
        <option value="10" selected="selected">10</option>
        <option value="25">25</option>
        <option value="50">50</option>
        <option value="100">100</option>
        </select>
        </td>                
        </tr>
        
        <tr>
        <td width="200">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the GAP EXTENTION to be used"/>
        
        <label for="clustalw_pwgapext">GAP EXTENTION </label><em>*</em>
        </td>
        
        <td>
        <select name="clustalw_pwgapext" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">
            
        <option value="0.05">0.05</option>
        <option value="0.1" selected="selected">0.1</option>        
        <option value="0.5">0.5</option>
        <option value="1.0">1.0</option>
        <option value="2.5">2.5</option>
        <option value="5.0">5.0</option>
        <option value="7.5">7.5</option>
        <option value="10.0">10.0</option>
        </select>
        </td>                
        </tr>
        </table>
        <!-- ------------------------------- -->
        <!-- DNA_SLOW Advanced Settings -->
        <!-- ------------------------------- -->
        
        <!-- ------------------------------- -->
        <!-- DNA_FAST Advanced Settings -->
        <!-- ------------------------------- --> 
        <table id="DNA_FASTToggle" border="0">
        <tr> 
        <td width="200">
            <img width="30" 
                 align="absmiddle"
                 src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
                 border="0" title="Please select the KTUP (Word Size) [1, 5]"/>

            <label for="clustalw_dna_ktup">KTUP (Word Size) <em>*</em></label>                        
        </td>

        <td width="300"><div align="absmiddle" id="slider-clustalw-dna-ktup"></div></td>
        <td width="50" align="right">
            <input type="hidden" 
               name="clustalw_dna_ktup"
               value="1"/>
            
            <input type="text" 
               id="clustalw_dna_ktup"
               value="1"
               disabled="disabled"                  
               style="width:30px; border:0; background:#C9C9C9; color:black; font-weight:bold;"
               class="textfield ui-widget ui-widget-content ui-state-focus"/>
        </td>
        </tr>
        
        <tr> 
        <td width="150">
            <img width="30" 
                 align="absmiddle"
                 src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
                 border="0" title="Please select the WINDOW LENGTH [0, 10]"/>

            <label for="clustalw_dna_windowlength">WINDOW LENGTH <em>*</em></label>                        
        </td>

        <td width="350"><div align="absmiddle" id="slider-clustalw-dna-windowlength"></div></td>
        <td width="50" align="right">
            <input type="hidden" 
               name="clustalw_dna_window"
               value="5"/>
            
            <input type="text" 
               id="clustalw_dna_window"
               value="5"
               disabled="disabled"                  
               style="width:30px; border:0; background:#C9C9C9; color:black; font-weight:bold;"
               class="textfield ui-widget ui-widget-content ui-state-focus"/>
        </td>
        </tr>                
        
        <tr> 
        <td width="150">
            <img width="30" 
                 align="absmiddle"
                 src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
                 border="0" title="Please select the TOPDIAG Length [0, 10]"/>

            <label for="clustalw_dna_topdiag">TOPDIAG <em>*</em></label>                        
        </td>

        <td width="350"><div align="absmiddle" id="slider-clustalw-dna-topdiag"></div></td>
        <td width="50" align="right">
            <input type="hidden" 
               name="clustalw_dna_topdiag"
               value="5"/>
            
            <input type="text" 
               id="clustalw_dna_topdiag"
               value="5"
               disabled="disabled"                  
               style="width:30px; border:0; background:#C9C9C9; color:black; font-weight:bold;"
               class="textfield ui-widget ui-widget-content ui-state-focus"/>
        </td>
        </tr>
        
        <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the SCORE TYPE to be used"/>
        
        <label for="clustalw_score">SCORE TYPE </label><em>*</em>
        </td>
            
        <td>
        <select name="clustalw_score" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">                                
            
        <option value="percent" selected="selected">Percent</option>        
        <option value="absolute">Absolute</option>
        </select>
        </td>                
        </tr>
                
        <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the Pairgap to be used"/>
        
        <label for="clustalw_pairgap">PAIRGAP </label><em>*</em>
        </td>
        
        <td>
        <select name="clustalw_pairgap" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">
            
        <option value="3" selected="selected">3</option>
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="4">4</option>
        <option value="5">5</option>
        <option value="10">10</option>
        <option value="25">25</option>
        <option value="50">50</option>
        <option value="100">100</option>
        <option value="250">250</option>
        <option value="500">500</option>
        </select>
        </td>                
        </tr>        
        </table>
        <!-- ------------------------------- -->
        <!-- DNA_FAST Advanced Settings -->
        <!-- ------------------------------- -->                 
        </div>
        </td>
    </tr>
    
    <tr><td><br/></td></tr>
        
    <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Customize some ClustalW2 algorithm's parameters (for experts)"/>
        
        <a id="aTag2" href="javascript:toggleAndChangeText2();">
        Set 3 - Set up your Multiple Sequence Alignment Options &#9660;        
        </a>
        
        <div id="divToToggle2" display="none"> 
            
        <!-- ----------------------------------- -->
        <!--  PROTEIN_3Toggle Advanced Settings  -->
        <!-- ----------------------------------- -->
        <div id="div2" display="none">
        <br/>The default settings will fulfill the needs of most users.<br/>
        Configure advanced settings for the selected alignment options (if needed).<br/><br/>
        
        <table id="PROTEIN_3Toggle" border="0">
            
        <tr>
        <td width="200">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the Protein Weight Matrix to be used"/>
        
        <label for="clustalw_matrix">Protein Weight Matrix </label><em>*</em>
        </td>
        
        <td>
        <select name="clustalw_matrix" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">
            
        <option value="gonnet" selected="selected">Gonnet</option>
        <option value="blosum">BLOSUM</option>
        <option value="pam">PAM</option>
        <option value="id">ID</option>
        </select>
        </td>                
        </tr>    
            
        <tr>
        <td width="200">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the GAP OPEN to be used"/>
        
        <label for="clustalw_gapopen">GAP OPEN </label><em>*</em>
        </td>
        
        <td>
        <select name="clustalw_gapopen" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">
                    
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="5">5</option>
        <option value="10" selected="selected">10</option>
        <option value="25">25</option>
        <option value="50">50</option>
        <option value="100">100</option>
        </select>
        </td>                
        </tr>
        
        <tr>
        <td width="200">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the GAP EXTENTION to be used"/>
        
        <label for="clustalw_gapext">GAP EXTENSION </label><em>*</em>
        </td>
        
        <td>
        <select name="clustalw_gapext" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">
            
        <option value="0.20" selected="selected">0.20</option>
        <option value="0.05">0.05</option>
        <option value="0.5">0.5</option>
        <option value="1.0">1.0</option>
        <option value="2.5">2.5</option>
        <option value="5.0">5.0</option>
        <option value="7.5">7.5</option>
        <option value="10.0">10.0</option>
        </select>
        </td>                
        </tr>
        
        <tr>
        <td width="200">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the NO END GAPS to be used"/>
        
        <label for="clustalw_noendgaps">NO END GAPS </label><em>*</em>
        </td>
        
        <td>
        <select name="clustalw_noendgaps" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">
            
        <option value="false" selected="selected">no</option>
        <option value="true">yes</option>
        </select>
        </td>                
        </tr>
        
        <tr>
        <td width="200">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the ITERATION to be used"/>
        
        <label for="clustalw_iteration">ITERATION </label><em>*</em>
        </td>
        
        <td>
        <select name="clustalw_iteration" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">
            
        <option value="none" selected="selected">none</option>
        <option value="tree">tree</option>
        <option value="alignment">alignment</option>
        </select>
        </td>                
        </tr>
        
        <tr>
        <td width="200">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the CLUSTERING to be used"/>
        
        <label for="clustalw_clustering">CLUSTERING </label><em>*</em>
        </td>
        
        <td>
        <select name="clustalw_clustering" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">
            
        <option value="NJ" selected="selected">NJ</option>
        <option value="UPGMA">UPGMA</option>        
        </select>
        </td>                
        </tr>
        
        <tr> 
        <td width="200">
            <img width="30" 
                 align="absmiddle"
                 src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
                 border="0" title="Please select the GAP DISTANCES [1, 10]"/>

            <label for="clustalw_gapdistances">GAP DISTANCES <em>*</em></label>                        
        </td>

        <td width="300"><div align="absmiddle" id="slider-clustalw-gapdistances"></div></td>
        <td width="50" align="right">
            <input type="hidden" 
               name="clustalw_gapdistances"
               value="5"/>
            
            <input type="text" 
               id="clustalw_gapdistances"
               value="5"
               disabled="disabled"                  
               style="width:30px; border:0; background:#C9C9C9; color:black; font-weight:bold;"
               class="textfield ui-widget ui-widget-content ui-state-focus"/>
        </td>
        </tr>
        
        <tr> 
        <td width="200">
            <img width="30" 
                 align="absmiddle"
                 src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
                 border="0" title="Please select the NUMITER [1, 10]"/>

            <label for="clustalw_numiter">NUMITER <em>*</em></label>                        
        </td>

        <td width="300"><div align="absmiddle" id="slider-clustalw-numiter"></div></td>
        <td width="50" align="right">
            <input type="hidden" 
               name="clustalw_numiter"
               value="1"/>
            
            <input type="text" 
               id="clustalw_numiter"
               value="1"
               disabled="disabled"                  
               style="width:30px; border:0; background:#C9C9C9; color:black; font-weight:bold;"
               class="textfield ui-widget ui-widget-content ui-state-focus"/>
        </td>
        </tr>
        
        <tr><td><br/></td></tr>
                
        </table>
                 
        <!-- ------------------------------- -->
        <!--  DNA_3Toggle Advanced Settings  -->
        <!-- ------------------------------- -->                 
        <table id="DNA_3Toggle" border="0">            
        <tr>
        <td width="200">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the DNA Weight Matrix to be used"/>
        
        <label for="clustalw_dnamatrix">DNA Weight Matrix </label><em>*</em>
        </td>
        
        <td>
        <select name="clustalw_dnamatrix" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">
            
        <option value="iub" selected="selected">IUB</option>
        <option value="clustalw">ClustalW</option>
        </select>
        </td>                
        </tr>    
            
        <tr>
        <td width="200">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the GAP OPEN to be used"/>
        
        <label for="clustalw_gapopen">GAP OPEN </label><em>*</em>
        </td>
        
        <td>
        <select name="clustalw_gapopen" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">
                    
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="5">5</option>
        <option value="10" selected="selected">10</option>
        <option value="25">25</option>
        <option value="50">50</option>
        <option value="100">100</option>
        </select>
        </td>                
        </tr>
        
        <tr>
        <td width="200">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the GAP EXTENTION to be used"/>
        
        <label for="clustalw_gapext">GAP EXTENSION </label><em>*</em>
        </td>
        
        <td>
        <select name="clustalw_gapext" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">
            
        <option value="0.20" selected="selected">0.20</option>
        <option value="0.05">0.05</option>
        <option value="0.5">0.5</option>
        <option value="1.0">1.0</option>
        <option value="2.5">2.5</option>
        <option value="5.0">5.0</option>
        <option value="7.5">7.5</option>
        <option value="10.0">10.0</option>
        </select>
        </td>                
        </tr>
        
        <tr>
        <td width="200">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the NO END GAPS to be used"/>
        
        <label for="clustalw_noendgaps">NO END GAPS </label><em>*</em>
        </td>
        
        <td>
        <select name="clustalw_noendgaps" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">
            
        <option value="false" selected="selected">no</option>
        <option value="true">yes</option>
        </select>
        </td>                
        </tr>
        
        <tr>
        <td width="200">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the ITERATION to be used"/>
        
        <label for="clustalw_iteration">ITERATION </label><em>*</em>
        </td>
        
        <td>
        <select name="clustalw_iteration" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">
            
        <option value="none" selected="selected">none</option>
        <option value="tree">tree</option>
        <option value="alignment">alignment</option>
        </select>
        </td>                
        </tr>
        
        <tr>
        <td width="200">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the CLUSTERING to be used"/>
        
        <label for="clustalw_clustering">CLUSTERING </label><em>*</em>
        </td>
        
        <td>
        <select name="clustalw_clustering" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">
            
        <option value="NJ" selected="selected">NJ</option>
        <option value="UPGMA">UPGMA</option>        
        </select>
        </td>                
        </tr>
        
        <tr> 
        <td width="200">
            <img width="30" 
                 align="absmiddle"
                 src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
                 border="0" title="Please select the GAP DISTANCES [1, 10]"/>

            <label for="clustalw_dnagapdistances">GAP DISTANCES <em>*</em></label>                        
        </td>

        <td width="300"><div align="absmiddle" id="slider-clustalw-dnagapdistances"></div></td>
        <td width="50" align="right">
            <input type="hidden" 
               name="clustalw_dnagapdistances"
               value="5"/>
            
            <input type="text" 
               id="clustalw_dnagapdistances"
               value="5"
               disabled="disabled"                  
               style="width:30px; border:0; background:#C9C9C9; color:black; font-weight:bold;"
               class="textfield ui-widget ui-widget-content ui-state-focus"/>
        </td>
        </tr>
        
        <tr> 
        <td width="200">
            <img width="30" 
                 align="absmiddle"
                 src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
                 border="0" title="Please select the NUMITER [1, 10]"/>

            <label for="clustalw_dnanumiter">NUMITER <em>*</em></label>                        
        </td>

        <td width="300"><div align="absmiddle" id="slider-clustalw-dnanumiter"></div></td>
        <td width="50" align="right">
            <input type="hidden" 
               name="clustalw_dnanumiter"
               value="1"/>
            
            <input type="text" 
               id="clustalw_dnanumiter"
               value="1"
               disabled="disabled"                  
               style="width:30px; border:0; background:#C9C9C9; color:black; font-weight:bold;"
               class="textfield ui-widget ui-widget-content ui-state-focus"/>
        </td>
        </tr>
        
        </table>                 
        </div>
        </td>
    </tr>
    
    <tr><td><br/></td></tr>
    
    <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Select the Output Options (for experts)"/>
        
        <a id="aTag3" href="javascript:toggleAndChangeText3();">
        Set 4 - Set up your Advanced Output Options &#9660;        
        </a>
        
        <div id="divToToggle3" display="none"> 
            
        <!-- ---------------------------------- -->
        <!--  OUTPUT OPTIONS Advanced Settings  -->
        <!-- ---------------------------------- -->
        <div id="div3" display="none">
        <br/>The default settings will fulfill the needs of most users.<br/>
        Configure advanced settings for the selected alignment options (if needed).<br/><br/>
        
        <table id="OUTPUT_Toggle" border="0">
        <tr>
        <td width="200">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the FORMAT to be used"/>
        
        <label for="clustalw_output">FORMAT </label><em>*</em>
        </td>
        
        <td>
        <select name="clustalw_output" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">
            
        <option value="aln1" selected="selected">Aln w/numbers</option>
        <option value="aln2">Aln wo/numbers</option>
        <option value="gcg">GCG MSF</option>
        <option value="phylip">PHYLIP</option>
        <option value="nexus">NEXUS</option>
        <option value="pir">NBRF/PIR</option>
        <option value="gde">GDE</option>
        <option value="fasta">Pearson/FASTA</option>
        </select>
        </td>
        </tr>
        
        <tr>
        <td width="200">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please select the ORDER to be used"/>
        
        <label for="clustalw_outorder">ORDER </label><em>*</em>
        </td>
        
        <td>
        <select name="clustalw_outorder" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; 
                       border-width: 1px; padding-left: 1px; width: 200px;">
            
        <option value="aligned" selected="selected">aligned</option>
        <option value="input">input</option>
        </select>
        </td>
        </tr>    
        </table>
             
        </div>
        </div>
        </td>
    </tr>

    <tr><td><br/></td></tr>

    <tr>                    
        <td align="left">
            <input type="image" 
                   src="<%= renderRequest.getContextPath()%>/images/start-icon.png"
                   width="60"                   
                   name="submit"
                   id ="submit" 
                   title="Run your Simulation!" />                    
        </td>
     </tr>                                            
</table>    
</div>	<!-- Fine Terzo Accordion -->
</div> <!-- Fine Accordions -->
</fieldset>    
</form>                                                                         

<div id="clustalw_footer" 
     style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">
    <div>ClustalW2 portlet ver. 1.0.1</div>
    <div>Istituto Nazionale di Fisica Nucleare (INFN), Catania, Italy</div>
    <div>Copyright © 2013. All rights reserved</div>    
</div>           
