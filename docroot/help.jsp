<%
/**************************************************************************
Copyright (c) 2011-2013:
Istituto Nazionale di Fisica Nucleare (INFN), Italy
Consorzio COMETA (COMETA), Italy

See http://www.infn.it and and http://www.consorzio-cometa.it for details on 
the copyright holders.

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
****************************************************************************/
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javax.portlet.*"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<portlet:defineObjects/>

<script type="text/javascript">
    $(document).ready(function() {
              
    $('.slideshow').cycle({
	fx: 'fade' // choose your transition type (fade, scrollUp, shuffle, etc)
    });
    
    // Roller
    $('#sonification_footer').rollchildren({
        delay_time         : 3000,
        loop               : true,
        pause_on_mouseover : true,
        roll_up_old_item   : true,
        speed              : 'slow'   
    });
    
    //var $tumblelog = $('#tumblelog');  
    $('#tumblelog').imagesLoaded( function() {
      $tumblelog.masonry({
        columnWidth: 440
      });
    });
});
</script>
                    
<br/>

<fieldset>
<legend>About the project</legend>

<section id="content">

<div id="tumblelog" class="clearfix">
    
  <div class="story col3">    
  <p style="text-align:justify; position: relative;">
  ClustalW2 is a general purpose multiple sequence alignment program for DNA or proteins. 
  It produces biologically meaningful multiple sequence alignments of divergent sequences. 
  It calculates the best match for the selected sequences, and lines them up so that the identities, 
  similarities and differences can be seen. Evolutionary relationships can be seen via viewing 
  Cladograms or Phylograms.
  </p>      
  </div>
                                     

  <div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">
      <h2>
      <a href="mailto:info@Domenico.Vicinanza@dante.net">
      <img width="100" 
           src="<%= renderRequest.getContextPath()%>/images/contact6.jpg" 
           title="Get in touch with the project"/></a>Contacts
      </h2>
      <p style="text-align:justify;">Giuseppe LA ROCCA<i> &mdash; (Responsible for GRID deployment)</i></p>      
  </div>               
    
  <div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 13px;">
        <h2>Sponsors & Credits</h2>
        <table border="0">                        
            <tr>                
            <td>
            <p align="justify">
            <a href="http://www.eumedconnect.net/">
                <img align="center" width="150"                      
                     src="<%= renderRequest.getContextPath()%>/images/EUMEDconnect2_300dpi_RGB.png" 
                     border="0" title="The EUMEDCONNECT Project" />
            </a>
            </p>
            </td>
            
            <td>&nbsp;&nbsp;</td>
            
            <td>
            <p align="justify">
            <a href="http://ei4africa.eu/">
                <img align="center" width="250"
                     src="<%= renderRequest.getContextPath()%>/images/ei4africa.png" 
                     src="http://www.eumedgrid.eu/images/stories/eumedgrid_logo.png" 
                     border="0" title="ei4africa | e-Infrastructure for Africa" />
            </a>
            </p>
            </td>
            </tr>                                  
        </table>   
  </div>
</div>
</section>
</fieldset>
           
<div id="sonification_footer" 
     style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">    
    <div>ClustalW2 portlet ver. 1.0.1</div>
    <div>Istituto Nazionale di Fisica Nucleare (INFN), Catania, Italy</div>
    <div>Copyright © 2013. All rights reserved</div>
</div>