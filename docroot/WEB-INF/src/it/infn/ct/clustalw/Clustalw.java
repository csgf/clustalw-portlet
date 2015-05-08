/*
 *************************************************************************
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
 ***************************************************************************
 */
package it.infn.ct.clustalw;

// import liferay libraries
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;

// import DataEngine libraries
import com.liferay.portal.util.PortalUtil;
import it.infn.ct.GridEngine.InformationSystem.BDII;
import it.infn.ct.GridEngine.Job.*;

// import generic Java libraries
import it.infn.ct.GridEngine.UsersTracking.UsersTrackingDBInterface;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URI;

// import portlet libraries
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

// Importing Apache libraries
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.portlet.PortletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Clustalw extends GenericPortlet {

    private static Log log = LogFactory.getLog(Clustalw.class);

    @Override
    protected void doEdit(RenderRequest request,
            RenderResponse response)
            throws PortletException, IOException
    {

        PortletPreferences portletPreferences =
                (PortletPreferences) request.getPreferences();

        response.setContentType("text/html");
        
        // Get the CLUSTALW INFRASTRUCTURE from the portlet preferences for the COMETA VO
        String cometa_clustalw_INFRASTRUCTURE = portletPreferences.getValue("cometa_clustalw_INFRASTRUCTURE", "N/A");
        // Get the CLUSTALW VONAME from the portlet preferences for the COMETA VO
        String cometa_clustalw_VONAME = portletPreferences.getValue("cometa_clustalw_VONAME", "N/A");
        // Get the CLUSTALW TOPPBDII from the portlet preferences for the COMETA VO
        String cometa_clustalw_TOPBDII = portletPreferences.getValue("cometa_clustalw_TOPBDII", "N/A");
        // Get the CLUSTALW WMS from the portlet preferences for the COMETA VO
        String[] cometa_clustalw_WMS = portletPreferences.getValues("cometa_clustalw_WMS", new String[5]);
        // Get the CLUSTALW ETOKENSERVER from the portlet preferences for the COMETA VO
        String cometa_clustalw_ETOKENSERVER = portletPreferences.getValue("cometa_clustalw_ETOKENSERVER", "N/A");
        // Get the CLUSTALW MYPROXYSERVER from the portlet preferences for the COMETA VO
        String cometa_clustalw_MYPROXYSERVER = portletPreferences.getValue("cometa_clustalw_MYPROXYSERVER", "N/A");
        // Get the CLUSTALW PORT from the portlet preferences for the COMETA VO
        String cometa_clustalw_PORT = portletPreferences.getValue("cometa_clustalw_PORT", "N/A");
        // Get the CLUSTALW ROBOTID from the portlet preferences for the COMETA VO
        String cometa_clustalw_ROBOTID = portletPreferences.getValue("cometa_clustalw_ROBOTID", "N/A");
        // Get the CLUSTALW ROLE from the portlet preferences for the COMETA VO
        String cometa_clustalw_ROLE = portletPreferences.getValue("cometa_clustalw_ROLE", "N/A");
        // Get the CLUSTALW RENEWAL from the portlet preferences for the COMETA VO
        String cometa_clustalw_RENEWAL = portletPreferences.getValue("cometa_clustalw_RENEWAL", "checked");
        // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the COMETA VO
        String cometa_clustalw_DISABLEVOMS = portletPreferences.getValue("cometa_clustalw_DISABLEVOMS", "unchecked");
        
        // Get the CLUSTALW INFRASTRUCTURE from the portlet preferences for the GARUDA VO
        String garuda_clustalw_INFRASTRUCTURE = portletPreferences.getValue("garuda_clustalw_INFRASTRUCTURE", "N/A");
        // Get the CLUSTALW VONAME from the portlet preferences for the GARUDA VO
        String garuda_clustalw_VONAME = portletPreferences.getValue("garuda_clustalw_VONAME", "N/A");
        // Get the CLUSTALW TOPPBDII from the portlet preferences for the GARUDA VO
        String garuda_clustalw_TOPBDII = portletPreferences.getValue("garuda_clustalw_TOPBDII", "N/A");
        // Get the CLUSTALW WMS from the portlet preferences for the GARUDA VO
        String[] garuda_clustalw_WMS = portletPreferences.getValues("garuda_clustalw_WMS", new String[5]);
        // Get the CLUSTALW ETOKENSERVER from the portlet preferences for the GARUDA VO
        String garuda_clustalw_ETOKENSERVER = portletPreferences.getValue("garuda_clustalw_ETOKENSERVER", "N/A");
        // Get the CLUSTALW MYPROXYSERVER from the portlet preferences for the GARUDA VO
        String garuda_clustalw_MYPROXYSERVER = portletPreferences.getValue("garuda_clustalw_MYPROXYSERVER", "N/A");
        // Get the CLUSTALW PORT from the portlet preferences for the GARUDA VO
        String garuda_clustalw_PORT = portletPreferences.getValue("garuda_clustalw_PORT", "N/A");
        // Get the CLUSTALW ROBOTID from the portlet preferences for the GARUDA VO
        String garuda_clustalw_ROBOTID = portletPreferences.getValue("garuda_clustalw_ROBOTID", "N/A");
        // Get the CLUSTALW ROLE from the portlet preferences for the GARUDA VO
        String garuda_clustalw_ROLE = portletPreferences.getValue("garuda_clustalw_ROLE", "N/A");
        // Get the CLUSTALW RENEWAL from the portlet preferences for the GARUDA VO
        String garuda_clustalw_RENEWAL = portletPreferences.getValue("garuda_clustalw_RENEWAL", "checked");
        // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the GARUDA VO
        String garuda_clustalw_DISABLEVOMS = portletPreferences.getValue("garuda_clustalw_DISABLEVOMS", "unchecked");

        // Get the CLUSTALW INFRASTRUCTURE from the portlet preferences for the GRIDIT VO
        String gridit_clustalw_INFRASTRUCTURE = portletPreferences.getValue("gridit_clustalw_INFRASTRUCTURE", "N/A");
        // Get the CLUSTALW VONAME from the portlet preferences for the GRIDIT VO
        String gridit_clustalw_VONAME = portletPreferences.getValue("gridit_clustalw_VONAME", "N/A");
        // Get the CLUSTALW TOPPBDII from the portlet preferences for the GRIDIT VO
        String gridit_clustalw_TOPBDII = portletPreferences.getValue("gridit_clustalw_TOPBDII", "N/A");
        // Get the CLUSTALW WMS from the portlet preferences for the GRIDIT VO
        String[] gridit_clustalw_WMS = portletPreferences.getValues("gridit_clustalw_WMS", new String[5]);
        // Get the CLUSTALW ETOKENSERVER from the portlet preferences for the GRIDIT VO
        String gridit_clustalw_ETOKENSERVER = portletPreferences.getValue("gridit_clustalw_ETOKENSERVER", "N/A");
        // Get the CLUSTALW MYPROXYSERVER from the portlet preferences for the GRIDIT VO
        String gridit_clustalw_MYPROXYSERVER = portletPreferences.getValue("gridit_clustalw_MYPROXYSERVER", "N/A");
        // Get the CLUSTALW PORT from the portlet preferences for the GRIDIT VO
        String gridit_clustalw_PORT = portletPreferences.getValue("gridit_clustalw_PORT", "N/A");
        // Get the CLUSTALW ROBOTID from the portlet preferences for the GRIDIT VO
        String gridit_clustalw_ROBOTID = portletPreferences.getValue("gridit_clustalw_ROBOTID", "N/A");
        // Get the CLUSTALW ROLE from the portlet preferences for the GRIDIT VO
        String gridit_clustalw_ROLE = portletPreferences.getValue("gridit_clustalw_ROLE", "N/A");
        // Get the CLUSTALW RENEWAL from the portlet preferences for the GRIDIT VO
        String gridit_clustalw_RENEWAL = portletPreferences.getValue("gridit_clustalw_RENEWAL", "checked");
        // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the GRIDIT VO
        String gridit_clustalw_DISABLEVOMS = portletPreferences.getValue("gridit_clustalw_DISABLEVOMS", "unchecked");
        
        // Get the CLUSTALW INFRASTRUCTURE from the portlet preferences for the GILDA VO
        String gilda_clustalw_INFRASTRUCTURE = portletPreferences.getValue("gilda_clustalw_INFRASTRUCTURE", "N/A");
        // Get the CLUSTALW VONAME from the portlet preferences for the GILDA VO
        String gilda_clustalw_VONAME = portletPreferences.getValue("gilda_clustalw_VONAME", "N/A");
        // Get the CLUSTALW TOPPBDII from the portlet preferences for the GILDA VO
        String gilda_clustalw_TOPBDII = portletPreferences.getValue("gilda_clustalw_TOPBDII", "N/A");
        // Get the CLUSTALW WMS from the portlet preferences for the GILDA VO
        String[] gilda_clustalw_WMS = portletPreferences.getValues("gilda_clustalw_WMS", new String[5]);
        // Get the CLUSTALW ETOKENSERVER from the portlet preferences for the GILDA VO
        String gilda_clustalw_ETOKENSERVER = portletPreferences.getValue("gilda_clustalw_ETOKENSERVER", "N/A");
        // Get the CLUSTALW MYPROXYSERVER from the portlet preferences for the GILDA VO
        String gilda_clustalw_MYPROXYSERVER = portletPreferences.getValue("gilda_clustalw_MYPROXYSERVER", "N/A");
        // Get the CLUSTALW PORT from the portlet preferences for the GILDA VO
        String gilda_clustalw_PORT = portletPreferences.getValue("gilda_clustalw_PORT", "N/A");
        // Get the CLUSTALW ROBOTID from the portlet preferences for the GILDA VO
        String gilda_clustalw_ROBOTID = portletPreferences.getValue("gilda_clustalw_ROBOTID", "N/A");
        // Get the CLUSTALW ROLE from the portlet preferences for the GILDA VO
        String gilda_clustalw_ROLE = portletPreferences.getValue("gilda_clustalw_ROLE", "N/A");
        // Get the CLUSTALW RENEWAL from the portlet preferences for the GILDA VO
        String gilda_clustalw_RENEWAL = portletPreferences.getValue("gilda_clustalw_RENEWAL", "checked");
        // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the GILDA VO
        String gilda_clustalw_DISABLEVOMS = portletPreferences.getValue("gilda_clustalw_DISABLEVOMS", "unchecked");

        // Get the CLUSTALW INFRASTRUCTURE from the portlet preferences for the EUMED VO
        String eumed_clustalw_INFRASTRUCTURE = portletPreferences.getValue("eumed_clustalw_INFRASTRUCTURE", "N/A");
        // Get the CLUSTALW VONAME from the portlet preferences for the EUMED VO
        String eumed_clustalw_VONAME = portletPreferences.getValue("eumed_clustalw_VONAME", "N/A");
        // Get the CLUSTALW TOPPBDII from the portlet preferences for the EUMED VO
        String eumed_clustalw_TOPBDII = portletPreferences.getValue("eumed_clustalw_TOPBDII", "N/A");
        // Get the CLUSTALW WMS from the portlet preferences for the EUMED VO
        String[] eumed_clustalw_WMS = portletPreferences.getValues("eumed_clustalw_WMS", new String[5]);
        // Get the CLUSTALW ETOKENSERVER from the portlet preferences for the EUMED VO
        String eumed_clustalw_ETOKENSERVER = portletPreferences.getValue("eumed_clustalw_ETOKENSERVER", "N/A");
        // Get the CLUSTALW MYPROXYSERVER from the portlet preferences for the EUMED VO
        String eumed_clustalw_MYPROXYSERVER = portletPreferences.getValue("eumed_clustalw_MYPROXYSERVER", "N/A");
        // Get the CLUSTALW PORT from the portlet preferences for the EUMED VO
        String eumed_clustalw_PORT = portletPreferences.getValue("eumed_clustalw_PORT", "N/A");
        // Get the CLUSTALW ROBOTID from the portlet preferences for the EUMED VO
        String eumed_clustalw_ROBOTID = portletPreferences.getValue("eumed_clustalw_ROBOTID", "N/A");
        // Get the CLUSTALW ROLE from the portlet preferences for the EUMED VO
        String eumed_clustalw_ROLE = portletPreferences.getValue("eumed_clustalw_ROLE", "N/A");
        // Get the CLUSTALW RENEWAL from the portlet preferences for the EUMED VO
        String eumed_clustalw_RENEWAL = portletPreferences.getValue("eumed_clustalw_RENEWAL", "checked");
        // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the EUMED VO
        String eumed_clustalw_DISABLEVOMS = portletPreferences.getValue("eumed_clustalw_DISABLEVOMS", "unchecked");

        // Get the CLUSTALW INFRASTRUCTURE from the portlet preferences for the GISELA VO
        String gisela_clustalw_INFRASTRUCTURE = portletPreferences.getValue("gisela_clustalw_INFRASTRUCTURE", "N/A");
        // Get the CLUSTALW VONAME from the portlet preferences for the GISELA VO
        String gisela_clustalw_VONAME = portletPreferences.getValue("gisela_clustalw_VONAME", "N/A");
        // Get the CLUSTALW TOPPBDII from the portlet preferences for the GISELA VO
        String gisela_clustalw_TOPBDII = portletPreferences.getValue("gisela_clustalw_TOPBDII", "N/A");
        // Get the CLUSTALW WMS from the portlet preferences for the GISELA VO
        String[] gisela_clustalw_WMS = portletPreferences.getValues("gisela_clustalw_WMS", new String[5]);
        // Get the CLUSTALW ETOKENSERVER from the portlet preferences for the GISELA VO
        String gisela_clustalw_ETOKENSERVER = portletPreferences.getValue("gisela_clustalw_ETOKENSERVER", "N/A");
        // Get the CLUSTALW MYPROXYSERVER from the portlet preferences for the GISELA VO
        String gisela_clustalw_MYPROXYSERVER = portletPreferences.getValue("gisela_clustalw_MYPROXYSERVER", "N/A");
        // Get the CLUSTALW PORT from the portlet preferences for the GISELA VO
        String gisela_clustalw_PORT = portletPreferences.getValue("gisela_clustalw_PORT", "N/A");
        // Get the CLUSTALW ROBOTID from the portlet preferences for the GISELA VO
        String gisela_clustalw_ROBOTID = portletPreferences.getValue("gisela_clustalw_ROBOTID", "N/A");
        // Get the CLUSTALW ROLE from the portlet preferences for the GISELA VO
        String gisela_clustalw_ROLE = portletPreferences.getValue("gisela_clustalw_ROLE", "N/A");
        // Get the CLUSTALW RENEWAL from the portlet preferences for the GISELA VO
        String gisela_clustalw_RENEWAL = portletPreferences.getValue("gisela_clustalw_RENEWAL", "checked");
        // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the GISELA VO
        String gisela_clustalw_DISABLEVOMS = portletPreferences.getValue("gisela_clustalw_DISABLEVOMS", "unchecked");
        
        // Get the CLUSTALW INFRASTRUCTURE from the portlet preferences for the SAGRID VO
        String sagrid_clustalw_INFRASTRUCTURE = portletPreferences.getValue("sagrid_clustalw_INFRASTRUCTURE", "N/A");
        // Get the CLUSTALW VONAME from the portlet preferences for the SAGRID VO
        String sagrid_clustalw_VONAME = portletPreferences.getValue("sagrid_clustalw_VONAME", "N/A");
        // Get the CLUSTALW TOPPBDII from the portlet preferences for the SAGRID VO
        String sagrid_clustalw_TOPBDII = portletPreferences.getValue("sagrid_clustalw_TOPBDII", "N/A");
        // Get the CLUSTALW WMS from the portlet preferences for the SAGRID VO
        String[] sagrid_clustalw_WMS = portletPreferences.getValues("sagrid_clustalw_WMS", new String[5]);
        // Get the CLUSTALW ETOKENSERVER from the portlet preferences for the SAGRID VO
        String sagrid_clustalw_ETOKENSERVER = portletPreferences.getValue("sagrid_clustalw_ETOKENSERVER", "N/A");
        // Get the CLUSTALW MYPROXYSERVER from the portlet preferences for the SAGRID VO
        String sagrid_clustalw_MYPROXYSERVER = portletPreferences.getValue("sagrid_clustalw_MYPROXYSERVER", "N/A");
        // Get the CLUSTALW PORT from the portlet preferences for the SAGRID VO
        String sagrid_clustalw_PORT = portletPreferences.getValue("sagrid_clustalw_PORT", "N/A");
        // Get the CLUSTALW ROBOTID from the portlet preferences for the SAGRID VO
        String sagrid_clustalw_ROBOTID = portletPreferences.getValue("sagrid_clustalw_ROBOTID", "N/A");
        // Get the CLUSTALW ROLE from the portlet preferences for the SAGRID VO
        String sagrid_clustalw_ROLE = portletPreferences.getValue("sagrid_clustalw_ROLE", "N/A");
        // Get the CLUSTALW RENEWAL from the portlet preferences for the SAGRID VO
        String sagrid_clustalw_RENEWAL = portletPreferences.getValue("sagrid_clustalw_RENEWAL", "checked");
        // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the SAGRID VO
        String sagrid_clustalw_DISABLEVOMS = portletPreferences.getValue("sagrid_clustalw_DISABLEVOMS", "unchecked");

        // Get the CLUSTALW APPID from the portlet preferences
        String clustalw_APPID = portletPreferences.getValue("clustalw_APPID", "N/A");
        // Get the CLUSTALW LOG LEVEL from the portlet preferences
        String clustalw_LOGLEVEL = portletPreferences.getValue("clustalw_LOGLEVEL", "INFO");
        // Get the CLUSTALW OUTPUT from the portlet preferences
        String clustalw_OUTPUT_PATH = portletPreferences.getValue("clustalw_OUTPUT_PATH", "/tmp");
        // Get the CLUSTALW SFOTWARE from the portlet preferences
        String clustalw_SOFTWARE = portletPreferences.getValue("clustalw_SOFTWARE", "N/A");
        // Get the TRACKING_DB_HOSTNAME from the portlet preferences
        String TRACKING_DB_HOSTNAME = portletPreferences.getValue("TRACKING_DB_HOSTNAME", "N/A");
        // Get the TRACKING_DB_USERNAME from the portlet preferences
        String TRACKING_DB_USERNAME = portletPreferences.getValue("TRACKING_DB_USERNAME", "N/A");
        // Get the TRACKING_DB_PASSWORD from the portlet preferences
        String TRACKING_DB_PASSWORD = portletPreferences.getValue("TRACKING_DB_PASSWORD", "N/A");
        // Get the SMTP_HOST from the portlet preferences
        String SMTP_HOST = portletPreferences.getValue("SMTP_HOST", "N/A");
        // Get the SENDER MAIL from the portlet preferences
        String SENDER_MAIL = portletPreferences.getValue("SENDER_MAIL", "N/A");
        // Get the list of enabled Infrastructures
        String[] infras = portletPreferences.getValues("clustalw_ENABLEINFRASTRUCTURE", new String[7]);

        // Set the default portlet preferences
        request.setAttribute("gridit_clustalw_INFRASTRUCTURE", gridit_clustalw_INFRASTRUCTURE.trim());
        request.setAttribute("gridit_clustalw_VONAME", gridit_clustalw_VONAME.trim());
        request.setAttribute("gridit_clustalw_TOPBDII", gridit_clustalw_TOPBDII.trim());
        request.setAttribute("gridit_clustalw_WMS", gridit_clustalw_WMS);
        request.setAttribute("gridit_clustalw_ETOKENSERVER", gridit_clustalw_ETOKENSERVER.trim());
        request.setAttribute("gridit_clustalw_MYPROXYSERVER", gridit_clustalw_MYPROXYSERVER.trim());
        request.setAttribute("gridit_clustalw_PORT", gridit_clustalw_PORT.trim());
        request.setAttribute("gridit_clustalw_ROBOTID", gridit_clustalw_ROBOTID.trim());
        request.setAttribute("gridit_clustalw_ROLE", gridit_clustalw_ROLE.trim());
        request.setAttribute("gridit_clustalw_RENEWAL", gridit_clustalw_RENEWAL);
        request.setAttribute("gridit_clustalw_DISABLEVOMS", gridit_clustalw_DISABLEVOMS);
        
        request.setAttribute("garuda_clustalw_INFRASTRUCTURE", garuda_clustalw_INFRASTRUCTURE.trim());
        request.setAttribute("garuda_clustalw_VONAME", garuda_clustalw_VONAME.trim());
        request.setAttribute("garuda_clustalw_TOPBDII", garuda_clustalw_TOPBDII.trim());
        request.setAttribute("garuda_clustalw_WMS", garuda_clustalw_WMS);
        request.setAttribute("garuda_clustalw_ETOKENSERVER", garuda_clustalw_ETOKENSERVER.trim());
        request.setAttribute("garuda_clustalw_MYPROXYSERVER", garuda_clustalw_MYPROXYSERVER.trim());
        request.setAttribute("garuda_clustalw_PORT", garuda_clustalw_PORT.trim());
        request.setAttribute("garuda_clustalw_ROBOTID", garuda_clustalw_ROBOTID.trim());
        request.setAttribute("garuda_clustalw_ROLE", garuda_clustalw_ROLE.trim());
        request.setAttribute("garuda_clustalw_RENEWAL", garuda_clustalw_RENEWAL);
        request.setAttribute("garuda_clustalw_DISABLEVOMS", garuda_clustalw_DISABLEVOMS);
        
        request.setAttribute("cometa_clustalw_INFRASTRUCTURE", cometa_clustalw_INFRASTRUCTURE.trim());
        request.setAttribute("cometa_clustalw_VONAME", cometa_clustalw_VONAME.trim());
        request.setAttribute("cometa_clustalw_TOPBDII", cometa_clustalw_TOPBDII.trim());
        request.setAttribute("cometa_clustalw_WMS", cometa_clustalw_WMS);
        request.setAttribute("cometa_clustalw_ETOKENSERVER", cometa_clustalw_ETOKENSERVER.trim());
        request.setAttribute("cometa_clustalw_MYPROXYSERVER", cometa_clustalw_MYPROXYSERVER.trim());
        request.setAttribute("cometa_clustalw_PORT", cometa_clustalw_PORT.trim());
        request.setAttribute("cometa_clustalw_ROBOTID", cometa_clustalw_ROBOTID.trim());
        request.setAttribute("cometa_clustalw_ROLE", cometa_clustalw_ROLE.trim());
        request.setAttribute("cometa_clustalw_RENEWAL", cometa_clustalw_RENEWAL);
        request.setAttribute("cometa_clustalw_DISABLEVOMS", cometa_clustalw_DISABLEVOMS);
        
        request.setAttribute("gilda_clustalw_INFRASTRUCTURE", gilda_clustalw_INFRASTRUCTURE.trim());
        request.setAttribute("gilda_clustalw_VONAME", gilda_clustalw_VONAME.trim());
        request.setAttribute("gilda_clustalw_TOPBDII", gilda_clustalw_TOPBDII.trim());
        request.setAttribute("gilda_clustalw_WMS", gilda_clustalw_WMS);
        request.setAttribute("gilda_clustalw_ETOKENSERVER", gilda_clustalw_ETOKENSERVER.trim());
        request.setAttribute("gilda_clustalw_MYPROXYSERVER", gilda_clustalw_MYPROXYSERVER.trim());
        request.setAttribute("gilda_clustalw_PORT", gilda_clustalw_PORT.trim());
        request.setAttribute("gilda_clustalw_ROBOTID", gilda_clustalw_ROBOTID.trim());
        request.setAttribute("gilda_clustalw_ROLE", gilda_clustalw_ROLE.trim());
        request.setAttribute("gilda_clustalw_RENEWAL", gilda_clustalw_RENEWAL);
        request.setAttribute("gilda_clustalw_DISABLEVOMS", gilda_clustalw_DISABLEVOMS);

        request.setAttribute("eumed_clustalw_INFRASTRUCTURE", eumed_clustalw_INFRASTRUCTURE.trim());
        request.setAttribute("eumed_clustalw_VONAME", eumed_clustalw_VONAME.trim());
        request.setAttribute("eumed_clustalw_TOPBDII", eumed_clustalw_TOPBDII.trim());
        request.setAttribute("eumed_clustalw_WMS", eumed_clustalw_WMS);
        request.setAttribute("eumed_clustalw_ETOKENSERVER", eumed_clustalw_ETOKENSERVER.trim());
        request.setAttribute("eumed_clustalw_MYPROXYSERVER", eumed_clustalw_MYPROXYSERVER.trim());
        request.setAttribute("eumed_clustalw_PORT", eumed_clustalw_PORT.trim());
        request.setAttribute("eumed_clustalw_ROBOTID", eumed_clustalw_ROBOTID.trim());
        request.setAttribute("eumed_clustalw_ROLE", eumed_clustalw_ROLE.trim());
        request.setAttribute("eumed_clustalw_RENEWAL", eumed_clustalw_RENEWAL);
        request.setAttribute("eumed_clustalw_DISABLEVOMS", eumed_clustalw_DISABLEVOMS);                

        request.setAttribute("gisela_clustalw_INFRASTRUCTURE", gisela_clustalw_INFRASTRUCTURE.trim());
        request.setAttribute("gisela_clustalw_VONAME", gisela_clustalw_VONAME.trim());
        request.setAttribute("gisela_clustalw_TOPBDII", gisela_clustalw_TOPBDII.trim());
        request.setAttribute("gisela_clustalw_WMS", gisela_clustalw_WMS);
        request.setAttribute("gisela_clustalw_ETOKENSERVER", gisela_clustalw_ETOKENSERVER.trim());
        request.setAttribute("gisela_clustalw_MYPROXYSERVER", gisela_clustalw_MYPROXYSERVER.trim());
        request.setAttribute("gisela_clustalw_PORT", gisela_clustalw_PORT.trim());
        request.setAttribute("gisela_clustalw_ROBOTID", gisela_clustalw_ROBOTID.trim());
        request.setAttribute("gisela_clustalw_ROLE", gisela_clustalw_ROLE.trim());
        request.setAttribute("gisela_clustalw_RENEWAL", gisela_clustalw_RENEWAL);
        request.setAttribute("gisela_clustalw_DISABLEVOMS", gisela_clustalw_DISABLEVOMS);
        
        request.setAttribute("sagrid_clustalw_INFRASTRUCTURE", sagrid_clustalw_INFRASTRUCTURE.trim());
        request.setAttribute("sagrid_clustalw_VONAME", sagrid_clustalw_VONAME.trim());
        request.setAttribute("sagrid_clustalw_TOPBDII", sagrid_clustalw_TOPBDII.trim());
        request.setAttribute("sagrid_clustalw_WMS", sagrid_clustalw_WMS);
        request.setAttribute("sagrid_clustalw_ETOKENSERVER", sagrid_clustalw_ETOKENSERVER.trim());
        request.setAttribute("sagrid_clustalw_MYPROXYSERVER", sagrid_clustalw_MYPROXYSERVER.trim());
        request.setAttribute("sagrid_clustalw_PORT", sagrid_clustalw_PORT.trim());
        request.setAttribute("sagrid_clustalw_ROBOTID", sagrid_clustalw_ROBOTID.trim());
        request.setAttribute("sagrid_clustalw_ROLE", sagrid_clustalw_ROLE.trim());
        request.setAttribute("sagrid_clustalw_RENEWAL", sagrid_clustalw_RENEWAL);
        request.setAttribute("sagrid_clustalw_DISABLEVOMS", sagrid_clustalw_DISABLEVOMS);

        request.setAttribute("clustalw_ENABLEINFRASTRUCTURE", infras);
        request.setAttribute("clustalw_APPID", clustalw_APPID.trim());
        request.setAttribute("clustalw_LOGLEVEL", clustalw_LOGLEVEL.trim());
        request.setAttribute("clustalw_OUTPUT_PATH", clustalw_OUTPUT_PATH.trim());
        request.setAttribute("clustalw_SOFTWARE", clustalw_SOFTWARE.trim());
        request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
        request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
        request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
        request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
        request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());        

        log.info("\nStarting the EDIT mode...with this settings"
        + "\ncometa_clustalw_INFRASTRUCTURE: " + cometa_clustalw_INFRASTRUCTURE
        + "\ncometa_clustalw_VONAME: " + cometa_clustalw_VONAME
        + "\ncometa_clustalw_TOPBDII: " + cometa_clustalw_TOPBDII                    
        + "\ncometa_clustalw_ETOKENSERVER: " + cometa_clustalw_ETOKENSERVER
        + "\ncometa_clustalw_MYPROXYSERVER: " + cometa_clustalw_MYPROXYSERVER
        + "\ncometa_clustalw_PORT: " + cometa_clustalw_PORT
        + "\ncometa_clustalw_ROBOTID: " + cometa_clustalw_ROBOTID
        + "\ncometa_clustalw_ROLE: " + cometa_clustalw_ROLE
        + "\ncometa_clustalw_RENEWAL: " + cometa_clustalw_RENEWAL
        + "\ncometa_clustalw_DISABLEVOMS: " + cometa_clustalw_DISABLEVOMS
                
        + "\n\ngaruda_clustalw_INFRASTRUCTURE: " + garuda_clustalw_INFRASTRUCTURE
        + "\ngaruda_clustalw_VONAME: " + garuda_clustalw_VONAME
        + "\ngaruda_clustalw_TOPBDII: " + garuda_clustalw_TOPBDII                    
        + "\ngaruda_clustalw_ETOKENSERVER: " + garuda_clustalw_ETOKENSERVER
        + "\ngaruda_clustalw_MYPROXYSERVER: " + garuda_clustalw_MYPROXYSERVER
        + "\ngaruda_clustalw_PORT: " + garuda_clustalw_PORT
        + "\ngaruda_clustalw_ROBOTID: " + garuda_clustalw_ROBOTID
        + "\ngaruda_clustalw_ROLE: " + garuda_clustalw_ROLE
        + "\ngaruda_clustalw_RENEWAL: " + garuda_clustalw_RENEWAL
        + "\ngaruda_clustalw_DISABLEVOMS: " + garuda_clustalw_DISABLEVOMS 
                
        + "\n\ngridit_clustalw_INFRASTRUCTURE: " + gridit_clustalw_INFRASTRUCTURE
        + "\ngridit_clustalw_VONAME: " + gridit_clustalw_VONAME
        + "\ngridit_clustalw_TOPBDII: " + gridit_clustalw_TOPBDII                    
        + "\ngridit_clustalw_ETOKENSERVER: " + gridit_clustalw_ETOKENSERVER
        + "\ngridit_clustalw_MYPROXYSERVER: " + gridit_clustalw_MYPROXYSERVER
        + "\ngridit_clustalw_PORT: " + gridit_clustalw_PORT
        + "\ngridit_clustalw_ROBOTID: " + gridit_clustalw_ROBOTID
        + "\ngridit_clustalw_ROLE: " + gridit_clustalw_ROLE
        + "\ngridit_clustalw_RENEWAL: " + gridit_clustalw_RENEWAL
        + "\ngridit_clustalw_DISABLEVOMS: " + gridit_clustalw_DISABLEVOMS
                
        + "\n\ngilda_clustalw_INFRASTRUCTURE: " + gilda_clustalw_INFRASTRUCTURE
        + "\ngilda_clustalw_VONAME: " + gilda_clustalw_VONAME
        + "\ngilda_clustalw_TOPBDII: " + gilda_clustalw_TOPBDII                    
        + "\ngilda_clustalw_ETOKENSERVER: " + gilda_clustalw_ETOKENSERVER
        + "\ngilda_clustalw_MYPROXYSERVER: " + gilda_clustalw_MYPROXYSERVER
        + "\ngilda_clustalw_PORT: " + gilda_clustalw_PORT
        + "\ngilda_clustalw_ROBOTID: " + gilda_clustalw_ROBOTID
        + "\ngilda_clustalw_ROLE: " + gilda_clustalw_ROLE
        + "\ngilda_clustalw_RENEWAL: " + gilda_clustalw_RENEWAL
        + "\ngilda_clustalw_DISABLEVOMS: " + gilda_clustalw_DISABLEVOMS

        + "\n\neumed_clustalw_INFRASTRUCTURE: " + eumed_clustalw_INFRASTRUCTURE
        + "\neumed_clustalw_VONAME: " + eumed_clustalw_VONAME
        + "\neumed_clustalw_TOPBDII: " + eumed_clustalw_TOPBDII                    
        + "\neumed_clustalw_ETOKENSERVER: " + eumed_clustalw_ETOKENSERVER
        + "\neumed_clustalw_MYPROXYSERVER: " + eumed_clustalw_MYPROXYSERVER
        + "\neumed_clustalw_PORT: " + eumed_clustalw_PORT
        + "\neumed_clustalw_ROBOTID: " + eumed_clustalw_ROBOTID
        + "\neumed_clustalw_ROLE: " + eumed_clustalw_ROLE
        + "\neumed_clustalw_RENEWAL: " + eumed_clustalw_RENEWAL
        + "\neumed_clustalw_DISABLEVOMS: " + eumed_clustalw_DISABLEVOMS

        + "\n\ngisela_clustalw_INFRASTRUCTURE: " + gisela_clustalw_INFRASTRUCTURE
        + "\ngisela_clustalw_VONAME: " + gisela_clustalw_VONAME
        + "\ngisela_clustalw_TOPBDII: " + gisela_clustalw_TOPBDII                   
        + "\ngisela_clustalw_ETOKENSERVER: " + gisela_clustalw_ETOKENSERVER
        + "\ngisela_clustalw_MYPROXYSERVER: " + gisela_clustalw_MYPROXYSERVER
        + "\ngisela_clustalw_PORT: " + gisela_clustalw_PORT
        + "\ngisela_clustalw_ROBOTID: " + gisela_clustalw_ROBOTID
        + "\ngisela_clustalw_ROLE: " + gisela_clustalw_ROLE
        + "\ngisela_clustalw_RENEWAL: " + gisela_clustalw_RENEWAL
        + "\ngisela_clustalw_DISABLEVOMS: " + gisela_clustalw_DISABLEVOMS
                
        + "\n\nsagrid_clustalw_INFRASTRUCTURE: " + sagrid_clustalw_INFRASTRUCTURE
        + "\nsagrid_clustalw_VONAME: " + sagrid_clustalw_VONAME
        + "\nsagrid_clustalw_TOPBDII: " + sagrid_clustalw_TOPBDII                   
        + "\nsagrid_clustalw_ETOKENSERVER: " + sagrid_clustalw_ETOKENSERVER
        + "\nsagrid_clustalw_MYPROXYSERVER: " + sagrid_clustalw_MYPROXYSERVER
        + "\nsagrid_clustalw_PORT: " + sagrid_clustalw_PORT
        + "\nsagrid_clustalw_ROBOTID: " + sagrid_clustalw_ROBOTID
        + "\nsagrid_clustalw_ROLE: " + sagrid_clustalw_ROLE
        + "\nsagrid_clustalw_RENEWAL: " + sagrid_clustalw_RENEWAL
        + "\nsagrid_clustalw_DISABLEVOMS: " + sagrid_clustalw_DISABLEVOMS
                 
        + "\nclustalw_APPID: " + clustalw_APPID
        + "\nclustalw_LOGLEVEL: " + clustalw_LOGLEVEL
        + "\nclustalw_OUTPUT_PATH: " + clustalw_OUTPUT_PATH
        + "\nclustalw_SOFTWARE: " + clustalw_SOFTWARE
        + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
        + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
        + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
        + "\nSMTP Server: " + SMTP_HOST
        + "\nSender: " + SENDER_MAIL);

        PortletRequestDispatcher dispatcher =
                getPortletContext().getRequestDispatcher("/edit.jsp");

        dispatcher.include(request, response);
    }

    @Override
    protected void doHelp(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {
        //super.doHelp(request, response);

        response.setContentType("text/html");

        log.info("\nStarting the HELP mode...");
        PortletRequestDispatcher dispatcher =
                getPortletContext().getRequestDispatcher("/help.jsp");

        dispatcher.include(request, response);
    }

    @Override
    protected void doView(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {

        PortletPreferences portletPreferences =
                (PortletPreferences) request.getPreferences();

        response.setContentType("text/html");

        //java.util.Enumeration listPreferences = portletPreferences.getNames();
        PortletRequestDispatcher dispatcher = null;
        
        String cometa_clustalw_TOPBDII = "";
        String cometa_clustalw_VONAME = "";
        String garuda_clustalw_TOPBDII = "";
        String garuda_clustalw_VONAME = "";
        String gridit_clustalw_TOPBDII = "";
        String gridit_clustalw_VONAME = "";
        String gilda_clustalw_TOPBDII = "";
        String gilda_clustalw_VONAME = "";
        String eumed_clustalw_TOPBDII = "";
        String eumed_clustalw_VONAME = "";
        String gisela_clustalw_TOPBDII = "";
        String gisela_clustalw_VONAME = "";
        String sagrid_clustalw_TOPBDII = "";
        String sagrid_clustalw_VONAME = "";
        
        String cometa_clustalw_ENABLEINFRASTRUCTURE="";
        String garuda_clustalw_ENABLEINFRASTRUCTURE="";
        String gridit_clustalw_ENABLEINFRASTRUCTURE="";
        String gilda_clustalw_ENABLEINFRASTRUCTURE="";
        String eumed_clustalw_ENABLEINFRASTRUCTURE="";
        String gisela_clustalw_ENABLEINFRASTRUCTURE="";
        String sagrid_clustalw_ENABLEINFRASTRUCTURE="";
        String[] infras = new String[7];
                
        String[] clustalw_INFRASTRUCTURES = 
                portletPreferences.getValues("clustalw_ENABLEINFRASTRUCTURE", new String[7]);
        
        for (int i=0; i<clustalw_INFRASTRUCTURES.length; i++) {            
            if (clustalw_INFRASTRUCTURES[i]!=null && clustalw_INFRASTRUCTURES[i].equals("cometa")) 
                { cometa_clustalw_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n COMETA!"); }
            if (clustalw_INFRASTRUCTURES[i]!=null && clustalw_INFRASTRUCTURES[i].equals("garuda")) 
                { garuda_clustalw_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n GARUDA!"); }
            if (clustalw_INFRASTRUCTURES[i]!=null && clustalw_INFRASTRUCTURES[i].equals("gridit")) 
                { gridit_clustalw_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n GRIDIT!"); }
            if (clustalw_INFRASTRUCTURES[i]!=null && clustalw_INFRASTRUCTURES[i].equals("gilda")) 
                { gilda_clustalw_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n GILDA!"); }
            if (clustalw_INFRASTRUCTURES[i]!=null && clustalw_INFRASTRUCTURES[i].equals("eumed")) 
                { eumed_clustalw_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n EUMED!"); }
            if (clustalw_INFRASTRUCTURES[i]!=null && clustalw_INFRASTRUCTURES[i].equals("gisela")) 
                { gisela_clustalw_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n GISELA!"); }
            if (clustalw_INFRASTRUCTURES[i]!=null && clustalw_INFRASTRUCTURES[i].equals("sagrid")) 
                { sagrid_clustalw_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n SAGRID!"); }
        }
                
        // Get the CLUSTALW APPID from the portlet preferences
        String clustalw_APPID = portletPreferences.getValue("clustalw_APPID", "N/A");
        // Get the LOGLEVEL from the portlet preferences
        String clustalw_LOGLEVEL = portletPreferences.getValue("clustalw_LOGLEVEL", "INFO");
        // Get the CLUSTALW APPID from the portlet preferences
        String clustalw_OUTPUT_PATH = portletPreferences.getValue("clustalw_OUTPUT_PATH", "N/A");
        // Get the CLUSTALW SOFTWARE from the portlet preferences
        String clustalw_SOFTWARE = portletPreferences.getValue("clustalw_SOFTWARE", "N/A");
        // Get the TRACKING_DB_HOSTNAME from the portlet preferences
        String TRACKING_DB_HOSTNAME = portletPreferences.getValue("TRACKING_DB_HOSTNAME", "N/A");
        // Get the TRACKING_DB_USERNAME from the portlet preferences
        String TRACKING_DB_USERNAME = portletPreferences.getValue("TRACKING_DB_USERNAME", "N/A");
        // Get the TRACKING_DB_PASSWORD from the portlet preferences
        String TRACKING_DB_PASSWORD = portletPreferences.getValue("TRACKING_DB_PASSWORD", "N/A");
        // Get the SMTP_HOST from the portlet preferences
        String SMTP_HOST = portletPreferences.getValue("SMTP_HOST", "N/A");
        // Get the SENDER_MAIL from the portlet preferences
        String SENDER_MAIL = portletPreferences.getValue("SENDER_MAIL", "N/A");
        
        if (cometa_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[0]="cometa";
            // Get the CLUSTALW INFRASTRUCTURE from the portlet preferences for the COMETA VO
            String cometa_clustalw_INFRASTRUCTURE = portletPreferences.getValue("cometa_clustalw_INFRASTRUCTURE", "N/A");
            // Get the CLUSTALW VONAME from the portlet preferences for the COMETA VO
            cometa_clustalw_VONAME = portletPreferences.getValue("cometa_clustalw_VONAME", "N/A");
            // Get the CLUSTALW TOPPBDII from the portlet preferences for the COMETA VO
            cometa_clustalw_TOPBDII = portletPreferences.getValue("cometa_clustalw_TOPBDII", "N/A");
            // Get the CLUSTALW WMS from the portlet preferences for the COMETA VO
            String[] cometa_clustalw_WMS = portletPreferences.getValues("cometa_clustalw_WMS", new String[5]);
            // Get the CLUSTALW ETOKENSERVER from the portlet preferences for the COMETA VO
            String cometa_clustalw_ETOKENSERVER = portletPreferences.getValue("cometa_clustalw_ETOKENSERVER", "N/A");
            // Get the CLUSTALW MYPROXYSERVER from the portlet preferences for the COMETA VO
            String cometa_clustalw_MYPROXYSERVER = portletPreferences.getValue("cometa_clustalw_MYPROXYSERVER", "N/A");
            // Get the CLUSTALW PORT from the portlet preferences for the COMETA VO
            String cometa_clustalw_PORT = portletPreferences.getValue("cometa_clustalw_PORT", "N/A");
            // Get the CLUSTALW ROBOTID from the portlet preferences for the COMETA VO
            String cometa_clustalw_ROBOTID = portletPreferences.getValue("gridit_clustalw_ROBOTID", "N/A");
            // Get the CLUSTALW ROLE from the portlet preferences for the COMETA VO
            String cometa_clustalw_ROLE = portletPreferences.getValue("cometa_clustalw_ROLE", "N/A");
            // Get the CLUSTALW RENEWAL from the portlet preferences for the COMETA VO
            String cometa_clustalw_RENEWAL = portletPreferences.getValue("cometa_clustalw_RENEWAL", "checked");
            // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the COMETA VO
            String cometa_clustalw_DISABLEVOMS = portletPreferences.getValue("cometa_clustalw_DISABLEVOMS", "unchecked");
            
            // Fetching all the WMS Endpoints for the COMETA VO
            String cometa_WMS = "";
            if (cometa_clustalw_ENABLEINFRASTRUCTURE.equals("checked")) {
                if (cometa_clustalw_WMS!=null) {
                    //log.info("length="+cometa_clustalw_WMS.length);
                    for (int i = 0; i < cometa_clustalw_WMS.length; i++)
                        if (!(cometa_clustalw_WMS[i].trim().equals("N/A")) ) 
                            cometa_WMS += cometa_clustalw_WMS[i] + " ";                        
                } else { log.info("WMS not set for COMETA!"); cometa_clustalw_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("cometa_clustalw_INFRASTRUCTURE", cometa_clustalw_INFRASTRUCTURE.trim());
            request.setAttribute("cometa_clustalw_VONAME", cometa_clustalw_VONAME.trim());
            request.setAttribute("cometa_clustalw_TOPBDII", cometa_clustalw_TOPBDII.trim());
            request.setAttribute("cometa_clustalw_WMS", cometa_WMS);
            request.setAttribute("cometa_clustalw_ETOKENSERVER", cometa_clustalw_ETOKENSERVER.trim());
            request.setAttribute("cometa_clustalw_MYPROXYSERVER", cometa_clustalw_MYPROXYSERVER.trim());
            request.setAttribute("cometa_clustalw_PORT", cometa_clustalw_PORT.trim());
            request.setAttribute("cometa_clustalw_ROBOTID", cometa_clustalw_ROBOTID.trim());
            request.setAttribute("cometa_clustalw_ROLE", cometa_clustalw_ROLE.trim());
            request.setAttribute("cometa_clustalw_RENEWAL", cometa_clustalw_RENEWAL);
            request.setAttribute("cometa_clustalw_DISABLEVOMS", cometa_clustalw_DISABLEVOMS);
            
            //request.setAttribute("clustalw_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("clustalw_APPID", clustalw_APPID.trim());
            request.setAttribute("clustalw_LOGLEVEL", clustalw_LOGLEVEL.trim());
            request.setAttribute("clustalw_OUTPUT_PATH", clustalw_OUTPUT_PATH.trim());
            request.setAttribute("clustalw_SOFTWARE", clustalw_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        if (garuda_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[1]="garuda";
            // Get the CLUSTALW INFRASTRUCTURE from the portlet preferences for the GARUDA VO
            String garuda_clustalw_INFRASTRUCTURE = portletPreferences.getValue("garuda_clustalw_INFRASTRUCTURE", "N/A");
            // Get the CLUSTALW VONAME from the portlet preferences for the GARUDA VO
            garuda_clustalw_VONAME = portletPreferences.getValue("garuda_clustalw_VONAME", "N/A");
            // Get the CLUSTALW TOPPBDII from the portlet preferences for the GARUDA VO
            garuda_clustalw_TOPBDII = portletPreferences.getValue("garuda_clustalw_TOPBDII", "N/A");
            // Get the CLUSTALW WMS from the portlet preferences for the GARUDA VO
            String[] garuda_clustalw_WMS = portletPreferences.getValues("garuda_clustalw_WMS", new String[5]);
            // Get the CLUSTALW ETOKENSERVER from the portlet preferences for the GARUDA VO
            String garuda_clustalw_ETOKENSERVER = portletPreferences.getValue("garuda_clustalw_ETOKENSERVER", "N/A");
            // Get the CLUSTALW MYPROXYSERVER from the portlet preferences for the GARUDA VO
            String garuda_clustalw_MYPROXYSERVER = portletPreferences.getValue("garuda_clustalw_MYPROXYSERVER", "N/A");
            // Get the CLUSTALW PORT from the portlet preferences for the GARUDA VO
            String garuda_clustalw_PORT = portletPreferences.getValue("garuda_clustalw_PORT", "N/A");
            // Get the CLUSTALW ROBOTID from the portlet preferences for the GARUDA VO
            String garuda_clustalw_ROBOTID = portletPreferences.getValue("garuda_clustalw_ROBOTID", "N/A");
            // Get the CLUSTALW ROLE from the portlet preferences for the GARUDA VO
            String garuda_clustalw_ROLE = portletPreferences.getValue("garuda_clustalw_ROLE", "N/A");
            // Get the CLUSTALW RENEWAL from the portlet preferences for the GARUDA VO
            String garuda_clustalw_RENEWAL = portletPreferences.getValue("garuda_clustalw_RENEWAL", "checked");
            // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the GARUDA VO
            String garuda_clustalw_DISABLEVOMS = portletPreferences.getValue("garuda_clustalw_DISABLEVOMS", "unchecked");
            
            // Fetching all the WMS Endpoints for the GARUDA VO
            String garuda_WMS = "";
            if (garuda_clustalw_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (garuda_clustalw_WMS!=null) {
                    //log.info("length="+garuda_clustalw_WMS.length);
                    for (int i = 0; i < garuda_clustalw_WMS.length; i++)
                        if (!(garuda_clustalw_WMS[i].trim().equals("N/A")) ) 
                            garuda_WMS += garuda_clustalw_WMS[i] + " ";                        
                } else { log.info("WSGRAM not set for GARUDA!"); garuda_clustalw_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("garuda_clustalw_INFRASTRUCTURE", garuda_clustalw_INFRASTRUCTURE.trim());
            request.setAttribute("garuda_clustalw_VONAME", garuda_clustalw_VONAME.trim());
            request.setAttribute("garuda_clustalw_TOPBDII", garuda_clustalw_TOPBDII.trim());
            request.setAttribute("garuda_clustalw_WMS", garuda_WMS);
            request.setAttribute("garuda_clustalw_ETOKENSERVER", garuda_clustalw_ETOKENSERVER.trim());
            request.setAttribute("garuda_clustalw_MYPROXYSERVER", garuda_clustalw_MYPROXYSERVER.trim());
            request.setAttribute("garuda_clustalw_PORT", garuda_clustalw_PORT.trim());
            request.setAttribute("garuda_clustalw_ROBOTID", garuda_clustalw_ROBOTID.trim());
            request.setAttribute("garuda_clustalw_ROLE", garuda_clustalw_ROLE.trim());
            request.setAttribute("garuda_clustalw_RENEWAL", garuda_clustalw_RENEWAL);
            request.setAttribute("garuda_clustalw_DISABLEVOMS", garuda_clustalw_DISABLEVOMS);
            
            //request.setAttribute("clustalw_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("clustalw_APPID", clustalw_APPID.trim());
            request.setAttribute("clustalw_LOGLEVEL", clustalw_LOGLEVEL.trim());
            request.setAttribute("clustalw_OUTPUT_PATH", clustalw_OUTPUT_PATH.trim());
            request.setAttribute("clustalw_SOFTWARE", clustalw_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        if (gridit_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[2]="gridit";
            // Get the CLUSTALW INFRASTRUCTURE from the portlet preferences for the GRIDIT VO
            String gridit_clustalw_INFRASTRUCTURE = portletPreferences.getValue("gridit_clustalw_INFRASTRUCTURE", "N/A");
            // Get the CLUSTALW VONAME from the portlet preferences for the GRIDIT VO
            gridit_clustalw_VONAME = portletPreferences.getValue("gridit_clustalw_VONAME", "N/A");
            // Get the CLUSTALW TOPPBDII from the portlet preferences for the GRIDIT VO
            gridit_clustalw_TOPBDII = portletPreferences.getValue("gridit_clustalw_TOPBDII", "N/A");
            // Get the CLUSTALW WMS from the portlet preferences for the GRIDIT VO
            String[] gridit_clustalw_WMS = portletPreferences.getValues("gridit_clustalw_WMS", new String[5]);
            // Get the CLUSTALW ETOKENSERVER from the portlet preferences for the GRIDIT VO
            String gridit_clustalw_ETOKENSERVER = portletPreferences.getValue("gridit_clustalw_ETOKENSERVER", "N/A");
            // Get the CLUSTALW MYPROXYSERVER from the portlet preferences for the GRIDIT VO
            String gridit_clustalw_MYPROXYSERVER = portletPreferences.getValue("gridit_clustalw_MYPROXYSERVER", "N/A");
            // Get the CLUSTALW PORT from the portlet preferences for the GRIDIT VO
            String gridit_clustalw_PORT = portletPreferences.getValue("gridit_clustalw_PORT", "N/A");
            // Get the CLUSTALW ROBOTID from the portlet preferences for the GRIDIT VO
            String gridit_clustalw_ROBOTID = portletPreferences.getValue("gridit_clustalw_ROBOTID", "N/A");
            // Get the CLUSTALW ROLE from the portlet preferences for the GRIDIT VO
            String gridit_clustalw_ROLE = portletPreferences.getValue("gridit_clustalw_ROLE", "N/A");
            // Get the CLUSTALW RENEWAL from the portlet preferences for the GRIDIT VO
            String gridit_clustalw_RENEWAL = portletPreferences.getValue("gridit_clustalw_RENEWAL", "checked");
            // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the GRIDIT VO
            String gridit_clustalw_DISABLEVOMS = portletPreferences.getValue("gridit_clustalw_DISABLEVOMS", "unchecked");
            
            // Fetching all the WMS Endpoints for the GRIDIT VO
            String gridit_WMS = "";
            if (gridit_clustalw_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (gridit_clustalw_WMS!=null) {
                    //log.info("length="+gridit_clustalw_WMS.length);
                    for (int i = 0; i < gridit_clustalw_WMS.length; i++)
                        if (!(gridit_clustalw_WMS[i].trim().equals("N/A")) ) 
                            gridit_WMS += gridit_clustalw_WMS[i] + " ";                        
                } else { log.info("WMS not set for GRIDIT!"); gridit_clustalw_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("gridit_clustalw_INFRASTRUCTURE", gridit_clustalw_INFRASTRUCTURE.trim());
            request.setAttribute("gridit_clustalw_VONAME", gridit_clustalw_VONAME.trim());
            request.setAttribute("gridit_clustalw_TOPBDII", gridit_clustalw_TOPBDII.trim());
            request.setAttribute("gridit_clustalw_WMS", gridit_WMS);
            request.setAttribute("gridit_clustalw_ETOKENSERVER", gridit_clustalw_ETOKENSERVER.trim());
            request.setAttribute("gridit_clustalw_MYPROXYSERVER", gridit_clustalw_MYPROXYSERVER.trim());
            request.setAttribute("gridit_clustalw_PORT", gridit_clustalw_PORT.trim());
            request.setAttribute("gridit_clustalw_ROBOTID", gridit_clustalw_ROBOTID.trim());
            request.setAttribute("gridit_clustalw_ROLE", gridit_clustalw_ROLE.trim());
            request.setAttribute("gridit_clustalw_RENEWAL", gridit_clustalw_RENEWAL);
            request.setAttribute("gridit_clustalw_DISABLEVOMS", gridit_clustalw_DISABLEVOMS);
            
            //request.setAttribute("clustalw_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("clustalw_APPID", clustalw_APPID.trim());
            request.setAttribute("clustalw_LOGLEVEL", clustalw_LOGLEVEL.trim());
            request.setAttribute("clustalw_OUTPUT_PATH", clustalw_OUTPUT_PATH.trim());
            request.setAttribute("clustalw_SOFTWARE", clustalw_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        if (gilda_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[3]="gilda";
            // Get the CLUSTALW INFRASTRUCTURE from the portlet preferences for the GILDA VO
            String gilda_clustalw_INFRASTRUCTURE = portletPreferences.getValue("gilda_clustalw_INFRASTRUCTURE", "N/A");
            // Get the CLUSTALW VONAME from the portlet preferences for the GILDA VO
            gilda_clustalw_VONAME = portletPreferences.getValue("gilda_clustalw_VONAME", "N/A");
            // Get the CLUSTALW TOPPBDII from the portlet preferences for the GILDA VO
            gilda_clustalw_TOPBDII = portletPreferences.getValue("gilda_clustalw_TOPBDII", "N/A");
            // Get the CLUSTALW WMS from the portlet preferences for the GILDA VO
            String[] gilda_clustalw_WMS = portletPreferences.getValues("gilda_clustalw_WMS", new String[5]);
            // Get the CLUSTALW ETOKENSERVER from the portlet preferences for the GILDA VO
            String gilda_clustalw_ETOKENSERVER = portletPreferences.getValue("gilda_clustalw_ETOKENSERVER", "N/A");
            // Get the CLUSTALW MYPROXYSERVER from the portlet preferences for the GILDA VO
            String gilda_clustalw_MYPROXYSERVER = portletPreferences.getValue("gilda_clustalw_MYPROXYSERVER", "N/A");
            // Get the CLUSTALW PORT from the portlet preferences for the GILDA VO
            String gilda_clustalw_PORT = portletPreferences.getValue("gilda_clustalw_PORT", "N/A");
            // Get the CLUSTALW ROBOTID from the portlet preferences for the GILDA VO
            String gilda_clustalw_ROBOTID = portletPreferences.getValue("gilda_clustalw_ROBOTID", "N/A");
            // Get the CLUSTALW ROLE from the portlet preferences for the GILDA VO
            String gilda_clustalw_ROLE = portletPreferences.getValue("gilda_clustalw_ROLE", "N/A");
            // Get the CLUSTALW RENEWAL from the portlet preferences for the GILDA VO
            String gilda_clustalw_RENEWAL = portletPreferences.getValue("gilda_clustalw_RENEWAL", "checked");
            // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the GILDA VO
            String gilda_clustalw_DISABLEVOMS = portletPreferences.getValue("gilda_clustalw_DISABLEVOMS", "unchecked");
                                    
            // Fetching all the WMS Endpoints for the GILDA VO
            String gilda_WMS = "";
            if (gilda_clustalw_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (gilda_clustalw_WMS!=null) {
                    //log.info("length="+gilda_clustalw_WMS.length);
                    for (int i = 0; i < gilda_clustalw_WMS.length; i++)
                        if (!(gilda_clustalw_WMS[i].trim().equals("N/A")) ) 
                            gilda_WMS += gilda_clustalw_WMS[i] + " ";                        
                } else { log.info("WMS not set for GILDA!"); gilda_clustalw_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("gilda_clustalw_INFRASTRUCTURE", gilda_clustalw_INFRASTRUCTURE.trim());
            request.setAttribute("gilda_clustalw_VONAME", gilda_clustalw_VONAME.trim());
            request.setAttribute("gilda_clustalw_TOPBDII", gilda_clustalw_TOPBDII.trim());
            request.setAttribute("gilda_clustalw_WMS", gilda_WMS);
            request.setAttribute("gilda_clustalw_ETOKENSERVER", gilda_clustalw_ETOKENSERVER.trim());
            request.setAttribute("gilda_clustalw_MYPROXYSERVER", gilda_clustalw_MYPROXYSERVER.trim());
            request.setAttribute("gilda_clustalw_PORT", gilda_clustalw_PORT.trim());
            request.setAttribute("gilda_clustalw_ROBOTID", gilda_clustalw_ROBOTID.trim());
            request.setAttribute("gilda_clustalw_ROLE", gilda_clustalw_ROLE.trim());
            request.setAttribute("gilda_clustalw_RENEWAL", gilda_clustalw_RENEWAL);
            request.setAttribute("gilda_clustalw_DISABLEVOMS", gilda_clustalw_DISABLEVOMS);

            //request.setAttribute("clustalw_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("clustalw_APPID", clustalw_APPID.trim());
            request.setAttribute("clustalw_LOGLEVEL", clustalw_LOGLEVEL.trim());
            request.setAttribute("clustalw_OUTPUT_PATH", clustalw_OUTPUT_PATH.trim());
            request.setAttribute("clustalw_SOFTWARE", clustalw_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        if (eumed_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[4]="eumed";
            // Get the CLUSTALW INFRASTRUCTURE from the portlet preferences for the EUMED VO
            String eumed_clustalw_INFRASTRUCTURE = portletPreferences.getValue("eumed_clustalw_INFRASTRUCTURE", "N/A");
            // Get the CLUSTALW VONAME from the portlet preferences for the EUMED VO
            eumed_clustalw_VONAME = portletPreferences.getValue("eumed_clustalw_VONAME", "N/A");
            // Get the CLUSTALW TOPPBDII from the portlet preferences for the EUMED VO
            eumed_clustalw_TOPBDII = portletPreferences.getValue("eumed_clustalw_TOPBDII", "N/A");
            // Get the CLUSTALW WMS from the portlet preferences for the EUMED VO
            String[] eumed_clustalw_WMS = portletPreferences.getValues("eumed_clustalw_WMS", new String[5]);
            // Get the CLUSTALW ETOKENSERVER from the portlet preferences for the EUMED VO
            String eumed_clustalw_ETOKENSERVER = portletPreferences.getValue("eumed_clustalw_ETOKENSERVER", "N/A");
            // Get the CLUSTALW MYPROXYSERVER from the portlet preferences for the EUMED VO
            String eumed_clustalw_MYPROXYSERVER = portletPreferences.getValue("eumed_clustalw_MYPROXYSERVER", "N/A");
            // Get the CLUSTALW PORT from the portlet preferences for the EUMED VO
            String eumed_clustalw_PORT = portletPreferences.getValue("eumed_clustalw_PORT", "N/A");
            // Get the CLUSTALW ROBOTID from the portlet preferences for the EUMED VO
            String eumed_clustalw_ROBOTID = portletPreferences.getValue("eumed_clustalw_ROBOTID", "N/A");
            // Get the CLUSTALW ROLE from the portlet preferences for the EUMED VO
            String eumed_clustalw_ROLE = portletPreferences.getValue("eumed_clustalw_ROLE", "N/A");
            // Get the CLUSTALW RENEWAL from the portlet preferences for the EUMED VO
            String eumed_clustalw_RENEWAL = portletPreferences.getValue("eumed_clustalw_RENEWAL", "checked");
            // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the EUMED VO
            String eumed_clustalw_DISABLEVOMS = portletPreferences.getValue("eumed_clustalw_DISABLEVOMS", "unchecked");
                                    
            // Fetching all the WMS Endpoints for the EUMED VO
            String eumed_WMS = "";
            if (eumed_clustalw_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (eumed_clustalw_WMS!=null) {
                    //log.info("length="+eumed_clustalw_WMS.length);
                    for (int i = 0; i < eumed_clustalw_WMS.length; i++)
                        if (!(eumed_clustalw_WMS[i].trim().equals("N/A")) ) 
                            eumed_WMS += eumed_clustalw_WMS[i] + " ";                        
                } else { log.info("WMS not set for EUMED!"); eumed_clustalw_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("eumed_clustalw_INFRASTRUCTURE", eumed_clustalw_INFRASTRUCTURE.trim());
            request.setAttribute("eumed_clustalw_VONAME", eumed_clustalw_VONAME.trim());
            request.setAttribute("eumed_clustalw_TOPBDII", eumed_clustalw_TOPBDII.trim());
            request.setAttribute("eumed_clustalw_WMS", eumed_WMS);
            request.setAttribute("eumed_clustalw_ETOKENSERVER", eumed_clustalw_ETOKENSERVER.trim());
            request.setAttribute("eumed_clustalw_MYPROXYSERVER", eumed_clustalw_MYPROXYSERVER.trim());
            request.setAttribute("eumed_clustalw_PORT", eumed_clustalw_PORT.trim());
            request.setAttribute("eumed_clustalw_ROBOTID", eumed_clustalw_ROBOTID.trim());
            request.setAttribute("eumed_clustalw_ROLE", eumed_clustalw_ROLE.trim());
            request.setAttribute("eumed_clustalw_RENEWAL", eumed_clustalw_RENEWAL);
            request.setAttribute("eumed_clustalw_DISABLEVOMS", eumed_clustalw_DISABLEVOMS);

            //request.setAttribute("clustalw_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("clustalw_APPID", clustalw_APPID.trim());
            request.setAttribute("clustalw_LOGLEVEL", clustalw_LOGLEVEL.trim());
            request.setAttribute("clustalw_OUTPUT_PATH", clustalw_OUTPUT_PATH.trim());
            request.setAttribute("clustalw_SOFTWARE", clustalw_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }

        if (gisela_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[5]="gisela";
            // Get the CLUSTALW INFRASTRUCTURE from the portlet preferences for the GISELA VO
            String gisela_clustalw_INFRASTRUCTURE = portletPreferences.getValue("gisela_clustalw_INFRASTRUCTURE", "N/A");
            // Get the CLUSTALW VONAME from the portlet preferences for the GISELA VO
            gisela_clustalw_VONAME = portletPreferences.getValue("gisela_clustalw_VONAME", "N/A");
            // Get the CLUSTALW TOPPBDII from the portlet preferences for the GISELA VO
            gisela_clustalw_TOPBDII = portletPreferences.getValue("gisela_clustalw_TOPBDII", "N/A");
            // Get the CLUSTALW WMS from the portlet preferences for the GISELA VO
            String[] gisela_clustalw_WMS = portletPreferences.getValues("gisela_clustalw_WMS", new String[5]);
            // Get the CLUSTALW ETOKENSERVER from the portlet preferences for the GISELA VO
            String gisela_clustalw_ETOKENSERVER = portletPreferences.getValue("gisela_clustalw_ETOKENSERVER", "N/A");
            // Get the CLUSTALW MYPROXYSERVER from the portlet preferences for the GISELA VO
            String gisela_clustalw_MYPROXYSERVER = portletPreferences.getValue("gisela_clustalw_MYPROXYSERVER", "N/A");
            // Get the CLUSTALW PORT from the portlet preferences for the GISELA VO
            String gisela_clustalw_PORT = portletPreferences.getValue("gisela_clustalw_PORT", "N/A");
            // Get the CLUSTALW ROBOTID from the portlet preferences for the GISELA VO
            String gisela_clustalw_ROBOTID = portletPreferences.getValue("gisela_clustalw_ROBOTID", "N/A");
            // Get the CLUSTALW ROLE from the portlet preferences for the GISELA VO
            String gisela_clustalw_ROLE = portletPreferences.getValue("gisela_clustalw_ROLE", "N/A");
            // Get the CLUSTALW RENEWAL from the portlet preferences for the GISELA VO
            String gisela_clustalw_RENEWAL = portletPreferences.getValue("gisela_clustalw_RENEWAL", "checked");
            // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the GISELA VO
            String gisela_clustalw_DISABLEVOMS = portletPreferences.getValue("gisela_clustalw_DISABLEVOMS", "unchecked");              
            
            // Fetching all the WMS Endpoints for the GISELA VO
            String gisela_WMS = "";
            if (gisela_clustalw_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (gisela_clustalw_WMS!=null) {
                    //log.info("length="+gisela_clustalw_WMS.length);
                    for (int i = 0; i < gisela_clustalw_WMS.length; i++)
                        if (!(gisela_clustalw_WMS[i].trim().equals("N/A")) ) 
                            gisela_WMS += gisela_clustalw_WMS[i] + " ";                        
                } else { log.info("WMS not set for GISELA!"); gisela_clustalw_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("gisela_clustalw_INFRASTRUCTURE", gisela_clustalw_INFRASTRUCTURE.trim());
            request.setAttribute("gisela_clustalw_VONAME", gisela_clustalw_VONAME.trim());
            request.setAttribute("gisela_clustalw_TOPBDII", gisela_clustalw_TOPBDII.trim());
            request.setAttribute("gisela_clustalw_WMS", gisela_WMS);
            request.setAttribute("gisela_clustalw_ETOKENSERVER", gisela_clustalw_ETOKENSERVER.trim());
            request.setAttribute("gisela_clustalw_MYPROXYSERVER", gisela_clustalw_MYPROXYSERVER.trim());
            request.setAttribute("gisela_clustalw_PORT", gisela_clustalw_PORT.trim());
            request.setAttribute("gisela_clustalw_ROBOTID", gisela_clustalw_ROBOTID.trim());
            request.setAttribute("gisela_clustalw_ROLE", gisela_clustalw_ROLE.trim());
            request.setAttribute("gisela_clustalw_RENEWAL", gisela_clustalw_RENEWAL);
            request.setAttribute("gisela_clustalw_DISABLEVOMS", gisela_clustalw_DISABLEVOMS);

            //request.setAttribute("clustalw_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("clustalw_APPID", clustalw_APPID.trim());
            request.setAttribute("clustalw_LOGLEVEL", clustalw_LOGLEVEL.trim());
            request.setAttribute("clustalw_OUTPUT_PATH", clustalw_OUTPUT_PATH.trim());
            request.setAttribute("clustalw_SOFTWARE", clustalw_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        if (sagrid_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[6]="sagrid";
            // Get the CLUSTALW INFRASTRUCTURE from the portlet preferences for the SAGRID VO
            String sagrid_clustalw_INFRASTRUCTURE = portletPreferences.getValue("sagrid_clustalw_INFRASTRUCTURE", "N/A");
            // Get the CLUSTALW VONAME from the portlet preferences for the SAGRID VO
            sagrid_clustalw_VONAME = portletPreferences.getValue("sagrid_clustalw_VONAME", "N/A");
            // Get the CLUSTALW TOPPBDII from the portlet preferences for the SAGRID VO
            sagrid_clustalw_TOPBDII = portletPreferences.getValue("sagrid_clustalw_TOPBDII", "N/A");
            // Get the CLUSTALW WMS from the portlet preferences for the SAGRID VO
            String[] sagrid_clustalw_WMS = portletPreferences.getValues("sagrid_clustalw_WMS", new String[5]);
            // Get the CLUSTALW ETOKENSERVER from the portlet preferences for the SAGRID VO
            String sagrid_clustalw_ETOKENSERVER = portletPreferences.getValue("sagrid_clustalw_ETOKENSERVER", "N/A");
            // Get the CLUSTALW MYPROXYSERVER from the portlet preferences for the SAGRID VO
            String sagrid_clustalw_MYPROXYSERVER = portletPreferences.getValue("sagrid_clustalw_MYPROXYSERVER", "N/A");
            // Get the CLUSTALW PORT from the portlet preferences for the SAGRID VO
            String sagrid_clustalw_PORT = portletPreferences.getValue("sagrid_clustalw_PORT", "N/A");
            // Get the CLUSTALW ROBOTID from the portlet preferences for the SAGRID VO
            String sagrid_clustalw_ROBOTID = portletPreferences.getValue("sagrid_clustalw_ROBOTID", "N/A");
            // Get the CLUSTALW ROLE from the portlet preferences for the SAGRID VO
            String sagrid_clustalw_ROLE = portletPreferences.getValue("sagrid_clustalw_ROLE", "N/A");
            // Get the CLUSTALW RENEWAL from the portlet preferences for the SAGRID VO
            String sagrid_clustalw_RENEWAL = portletPreferences.getValue("sagrid_clustalw_RENEWAL", "checked");
            // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the SAGRID VO
            String sagrid_clustalw_DISABLEVOMS = portletPreferences.getValue("sagrid_clustalw_DISABLEVOMS", "unchecked");              
            // Fetching all the WMS Endpoints for the SAGRID VO
            String sagrid_WMS = "";
            if (sagrid_clustalw_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (sagrid_clustalw_WMS!=null) {
                    //log.info("length="+sagrid_clustalw_WMS.length);
                    for (int i = 0; i < sagrid_clustalw_WMS.length; i++)
                        if (!(sagrid_clustalw_WMS[i].trim().equals("N/A")) ) 
                            sagrid_WMS += sagrid_clustalw_WMS[i] + " ";                        
                } else { log.info("WMS not set for SAGRID!"); sagrid_clustalw_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("sagrid_clustalw_INFRASTRUCTURE", sagrid_clustalw_INFRASTRUCTURE.trim());
            request.setAttribute("sagrid_clustalw_VONAME", sagrid_clustalw_VONAME.trim());
            request.setAttribute("sagrid_clustalw_TOPBDII", sagrid_clustalw_TOPBDII.trim());
            request.setAttribute("sagrid_clustalw_WMS", sagrid_WMS);
            request.setAttribute("sagrid_clustalw_ETOKENSERVER", sagrid_clustalw_ETOKENSERVER.trim());
            request.setAttribute("sagrid_clustalw_MYPROXYSERVER", sagrid_clustalw_MYPROXYSERVER.trim());
            request.setAttribute("sagrid_clustalw_PORT", sagrid_clustalw_PORT.trim());
            request.setAttribute("sagrid_clustalw_ROBOTID", sagrid_clustalw_ROBOTID.trim());
            request.setAttribute("sagrid_clustalw_ROLE", sagrid_clustalw_ROLE.trim());
            request.setAttribute("sagrid_clustalw_RENEWAL", sagrid_clustalw_RENEWAL);
            request.setAttribute("sagrid_clustalw_DISABLEVOMS", sagrid_clustalw_DISABLEVOMS);

            //request.setAttribute("clustalw_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("clustalw_APPID", clustalw_APPID.trim());
            request.setAttribute("clustalw_LOGLEVEL", clustalw_LOGLEVEL.trim());
            request.setAttribute("clustalw_OUTPUT_PATH", clustalw_OUTPUT_PATH.trim());
            request.setAttribute("clustalw_SOFTWARE", clustalw_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        // Save in the preferences the list of supported infrastructures 
        request.setAttribute("clustalw_ENABLEINFRASTRUCTURE", infras);        

         HashMap<String,Properties> GPS_table = new HashMap<String, Properties>();
         HashMap<String,Properties> GPS_queue = new HashMap<String, Properties>();

         // ********************************************************
         List<String> CEqueues_cometa = null;                  
         List<String> CEqueues_garuda = null;
         List<String> CEqueues_gridit = null;
         List<String> CEqueues_gilda = null;
         List<String> CEqueues_eumed = null;
         List<String> CEqueues_gisela = null;
         List<String> CEqueues_sagrid = null;
         
         List<String> CEs_list_cometa = null;
         List<String> CEs_list_garuda = null;
         List<String> CEs_list_gridit = null;
         List<String> CEs_list_gilda = null;
         List<String> CEs_list_eumed = null;
         List<String> CEs_list_gisela = null;
         List<String> CEs_list_sagrid = null;
         
         BDII bdii = null;

         try {
                if (cometa_clustalw_ENABLEINFRASTRUCTURE.equals("checked") && 
                   (!cometa_clustalw_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<COMETA>*RESOURCES*-----");
                    bdii = new BDII(new URI(cometa_clustalw_TOPBDII));
                    CEs_list_cometa = 
                            getListofCEForSoftwareTag(cometa_clustalw_VONAME,
                                                      cometa_clustalw_TOPBDII,
                                                      clustalw_SOFTWARE);
                    
                    CEqueues_cometa = 
                            bdii.queryCEQueues(cometa_clustalw_VONAME);
                }
                
                //=========================================================
                // IMPORTANT: THIS FIX IS ONLY FOR INSTANCIATE THE 
                //            CHAIN INTEROPERABILITY DEMO                
                //=========================================================
                // ===== ONLY FOR THE CHAIN INTEROPERABILITY DEMO =====
                if (garuda_clustalw_ENABLEINFRASTRUCTURE.equals("checked") && 
                   (!garuda_clustalw_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<GARUDA>*RESOURCES*-----");
                    CEs_list_garuda = new ArrayList();
                    CEs_list_garuda.add("xn03.ctsf.cdacb.in");
                    
                    CEqueues_garuda = new ArrayList();
                    CEqueues_garuda.add("wsgram://xn03.ctsf.cdacb.in:8443/GW");
                }
                // ===== ONLY FOR THE CHAIN INTEROPERABILITY DEMO =====
             
                if (gridit_clustalw_ENABLEINFRASTRUCTURE.equals("checked") && 
                   (!gridit_clustalw_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<GRIDIT>*RESOURCES*-----");
                    bdii = new BDII(new URI(gridit_clustalw_TOPBDII));
                    CEs_list_gridit = 
                                getListofCEForSoftwareTag(gridit_clustalw_VONAME,
                                                          gridit_clustalw_TOPBDII,
                                                          clustalw_SOFTWARE);
                    
                    CEqueues_gridit = 
                                bdii.queryCEQueues(gridit_clustalw_VONAME);
                }
                
                if (gilda_clustalw_ENABLEINFRASTRUCTURE.equals("checked") && 
                   (!gilda_clustalw_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<GILDA>*RESOURCES*-----");
                    bdii = new BDII(new URI(gilda_clustalw_TOPBDII));
                    CEs_list_gilda = 
                                getListofCEForSoftwareTag(gilda_clustalw_VONAME,
                                                          gilda_clustalw_TOPBDII,
                                                          clustalw_SOFTWARE);
                    
                    CEqueues_gilda = 
                            bdii.queryCEQueues(gilda_clustalw_VONAME);
                }
                
                if (eumed_clustalw_ENABLEINFRASTRUCTURE.equals("checked") && 
                   (!eumed_clustalw_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<EUMED>*RESOURCES*-----");
                    bdii = new BDII(new URI(eumed_clustalw_TOPBDII));
                    CEs_list_eumed = 
                                getListofCEForSoftwareTag(eumed_clustalw_VONAME,
                                                          eumed_clustalw_TOPBDII,
                                                          clustalw_SOFTWARE);
                    
                    CEqueues_eumed = 
                            bdii.queryCEQueues(eumed_clustalw_VONAME);
                }
                
                if (gisela_clustalw_ENABLEINFRASTRUCTURE.equals("checked") &&
                   (!gisela_clustalw_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<GISELA>*RESOURCES*-----");
                    bdii = new BDII(new URI(gisela_clustalw_TOPBDII));
                    CEs_list_gisela = 
                                getListofCEForSoftwareTag(gisela_clustalw_VONAME,
                                                          gisela_clustalw_TOPBDII,
                                                          clustalw_SOFTWARE);
                    
                    CEqueues_gisela = 
                            bdii.queryCEQueues(gisela_clustalw_VONAME);
                }
                
                if (sagrid_clustalw_ENABLEINFRASTRUCTURE.equals("checked") &&
                   (!sagrid_clustalw_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<SAGRID>*RESOURCES*-----");
                    bdii = new BDII(new URI(sagrid_clustalw_TOPBDII));
                    CEs_list_sagrid = 
                                getListofCEForSoftwareTag(sagrid_clustalw_VONAME,
                                                          sagrid_clustalw_TOPBDII,
                                                          clustalw_SOFTWARE);
                    
                    CEqueues_sagrid = 
                            bdii.queryCEQueues(sagrid_clustalw_VONAME);
                }
                
                // Merging the list of CEs and queues
                List<String> CEs_list_TOT = new ArrayList<String>();
                if (cometa_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_cometa);
                if (gridit_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_gridit);
                // ===== ONLY FOR THE CHAIN INTEROPERABILITY DEMO =====
                if (garuda_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_garuda);
                // ===== ONLY FOR THE CHAIN INTEROPERABILITY DEMO =====
                if (gilda_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_gilda);
                if (eumed_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_eumed);
                if (gisela_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_gisela);
                if (sagrid_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_sagrid);
                                
                List<String> CEs_queue_TOT = new ArrayList<String>();
                if (cometa_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_cometa);
                if (gridit_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_gridit);
                if (gilda_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_gilda);                
                if (eumed_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_eumed);
                if (gisela_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_gisela);
                if (sagrid_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_sagrid); 
                
                //=========================================================
                // IMPORTANT: INSTANCIATE THE UsersTrackingDBInterface
                //            CLASS USING THE EMPTY CONSTRUCTOR WHEN
                //            WHEN THE PORTLET IS DEPLOYED IN PRODUCTION!!!
                //=========================================================
                /*UsersTrackingDBInterface DBInterface =
                        new UsersTrackingDBInterface(
                                TRACKING_DB_HOSTNAME.trim(),
                                TRACKING_DB_USERNAME.trim(),
                                TRACKING_DB_PASSWORD.trim());*/
                
                UsersTrackingDBInterface DBInterface =
                        new UsersTrackingDBInterface();
                    
                if ( (CEs_list_TOT != null) && (!CEs_queue_TOT.isEmpty()) )
                {
                    log.info("NOT EMPTY LIST!");
                    // Fetching the list of CEs publushing the SW
                    for (String CE:CEs_list_TOT) 
                    {
                        //log.info("Fetching the CE="+CE);
                        Properties coordinates = new Properties();
                        Properties queue = new Properties();

                        float coords[] = DBInterface.getCECoordinate(CE);                        

                        String GPS_LAT = Float.toString(coords[0]);
                        String GPS_LNG = Float.toString(coords[1]);

                        coordinates.setProperty("LAT", GPS_LAT);
                        coordinates.setProperty("LNG", GPS_LNG);
                        log.info("Fetching CE settings for [ " + CE + 
                                 " ] Coordinates [ " + GPS_LAT + 
                                 ", " + GPS_LNG + " ]");

                        // Fetching the Queues
                        for (String CEqueue:CEs_queue_TOT) {
                                if (CEqueue.contains(CE))
                                    queue.setProperty("QUEUE", CEqueue);
                        }

                        // Saving the GPS location in a Java HashMap
                        GPS_table.put(CE, coordinates);

                        // Saving the queue in a Java HashMap
                        GPS_queue.put(CE, queue);
                    }
                } else log.info ("EMPTY LIST!");
             } catch (URISyntaxException ex) {
               Logger.getLogger(Clustalw.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NamingException e) {}

            // Checking the HashMap
            Set set = GPS_table.entrySet();
            Iterator iter = set.iterator();
            while ( iter.hasNext() )
            {
                Map.Entry entry = (Map.Entry)iter.next();
                log.info(" - GPS location of the CE " +
                           entry.getKey() + " => " + entry.getValue());
            }

            // Checking the HashMap
            set = GPS_queue.entrySet();
            iter = set.iterator();
            while ( iter.hasNext() )
            {
                Map.Entry entry = (Map.Entry)iter.next();
                log.info(" - Queue " +
                           entry.getKey() + " => " + entry.getValue());
            }

            Gson gson = new GsonBuilder().create();
            request.setAttribute ("GPS_table", gson.toJson(GPS_table));
            request.setAttribute ("GPS_queue", gson.toJson(GPS_queue));

            // ********************************************************

            dispatcher = getPortletContext().getRequestDispatcher("/view.jsp");       
            dispatcher.include(request, response);
    }

    // The init method will be called when installing for the first time the portlet
    // This is the right time to setup the default values into the preferences
    @Override
    public void init() throws PortletException {
        super.init();
    }

    @Override
    public void processAction(ActionRequest request,
                              ActionResponse response)
                throws PortletException, IOException {

        String action = "";

        // Get the action to be processed from the request
        action = request.getParameter("ActionEvent");

        // Determine the username and the email address
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);        
        User user = themeDisplay.getUser();
        String username = user.getScreenName();
        String emailAddress = user.getDisplayEmailAddress();        

        PortletPreferences portletPreferences =
                (PortletPreferences) request.getPreferences();                
        
        if (action.equals("CONFIG_CLUSTALW_PORTLET")) {
            log.info("\nPROCESS ACTION => " + action);
            
            // Get the CLUSTALW APPID from the portlet request
            String clustalw_APPID = request.getParameter("clustalw_APPID");
            // Get the LOGLEVEL from the portlet request
            String clustalw_LOGLEVEL = request.getParameter("clustalw_LOGLEVEL");
            // Get the CLUSTALW OUTPUT from the portlet request
            String clustalw_OUTPUT_PATH = request.getParameter("clustalw_OUTPUT_PATH");
            // Get the CLUSTALW SOFTWARE from the portlet request
            String clustalw_SOFTWARE = request.getParameter("clustalw_SOFTWARE");
            // Get the TRACKING_DB_HOSTNAME from the portlet request
            String TRACKING_DB_HOSTNAME = request.getParameter("TRACKING_DB_HOSTNAME");
            // Get the TRACKING_DB_USERNAME from the portlet request
            String TRACKING_DB_USERNAME = request.getParameter("TRACKING_DB_USERNAME");
            // Get the TRACKING_DB_PASSWORD from the portlet request
            String TRACKING_DB_PASSWORD = request.getParameter("TRACKING_DB_PASSWORD");
            // Get the SMTP_HOST from the portlet request
            String SMTP_HOST = request.getParameter("SMTP_HOST");
            // Get the SENDER_MAIL from the portlet request
            String SENDER_MAIL = request.getParameter("SENDER_MAIL");
            String[] infras = new String[7];
            
            String cometa_clustalw_ENABLEINFRASTRUCTURE = "unchecked";
            String garuda_clustalw_ENABLEINFRASTRUCTURE = "unchecked";
            String gridit_clustalw_ENABLEINFRASTRUCTURE = "unchecked";
            String gilda_clustalw_ENABLEINFRASTRUCTURE = "unchecked";
            String eumed_clustalw_ENABLEINFRASTRUCTURE = "unchecked";
            String gisela_clustalw_ENABLEINFRASTRUCTURE = "unchecked";
            String sagrid_clustalw_ENABLEINFRASTRUCTURE = "unchecked";
        
            String[] clustalw_INFRASTRUCTURES = request.getParameterValues("clustalw_ENABLEINFRASTRUCTURES");
        
            if (clustalw_INFRASTRUCTURES != null) {
                Arrays.sort(clustalw_INFRASTRUCTURES);                    
                cometa_clustalw_ENABLEINFRASTRUCTURE = 
                    Arrays.binarySearch(clustalw_INFRASTRUCTURES, "cometa") >= 0 ? "checked" : "unchecked";
                garuda_clustalw_ENABLEINFRASTRUCTURE = 
                    Arrays.binarySearch(clustalw_INFRASTRUCTURES, "garuda") >= 0 ? "checked" : "unchecked";
                gridit_clustalw_ENABLEINFRASTRUCTURE = 
                    Arrays.binarySearch(clustalw_INFRASTRUCTURES, "gridit") >= 0 ? "checked" : "unchecked";
                gilda_clustalw_ENABLEINFRASTRUCTURE = 
                    Arrays.binarySearch(clustalw_INFRASTRUCTURES, "gilda") >= 0 ? "checked" : "unchecked";
                eumed_clustalw_ENABLEINFRASTRUCTURE = 
                    Arrays.binarySearch(clustalw_INFRASTRUCTURES, "eumed") >= 0 ? "checked" : "unchecked";
                gisela_clustalw_ENABLEINFRASTRUCTURE = 
                    Arrays.binarySearch(clustalw_INFRASTRUCTURES, "gisela") >= 0 ? "checked" : "unchecked";
                sagrid_clustalw_ENABLEINFRASTRUCTURE = 
                    Arrays.binarySearch(clustalw_INFRASTRUCTURES, "sagrid") >= 0 ? "checked" : "unchecked";
            }
        
            if (cometa_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[0]="cometa";
                // Get the CLUSTALW INFRASTRUCTURE from the portlet request for the COMETA VO
                String cometa_clustalw_INFRASTRUCTURE = request.getParameter("cometa_clustalw_INFRASTRUCTURE");
                // Get the CLUSTALW VONAME from the portlet request for the COMETA VO
                String cometa_clustalw_VONAME = request.getParameter("cometa_clustalw_VONAME");
                // Get the CLUSTALW TOPBDII from the portlet request for the COMETA VO
                String cometa_clustalw_TOPBDII = request.getParameter("cometa_clustalw_TOPBDII");
                // Get the CLUSTALW WMS from the portlet request for the COMETA VO
                String[] cometa_clustalw_WMS = request.getParameterValues("cometa_clustalw_WMS");
                // Get the CLUSTALW ETOKENSERVER from the portlet request for the COMETA VO
                String cometa_clustalw_ETOKENSERVER = request.getParameter("cometa_clustalw_ETOKENSERVER");
                // Get the CLUSTALW MYPROXYSERVER from the portlet request for the COMETA VO
                String cometa_clustalw_MYPROXYSERVER = request.getParameter("cometa_clustalw_MYPROXYSERVER");
                // Get the CLUSTALW PORT from the portlet request for the COMETA VO
                String cometa_clustalw_PORT = request.getParameter("cometa_clustalw_PORT");
                // Get the CLUSTALW ROBOTID from the portlet request for the COMETA VO
                String cometa_clustalw_ROBOTID = request.getParameter("cometa_clustalw_ROBOTID");
                // Get the CLUSTALW ROLE from the portlet request for the COMETA VO
                String cometa_clustalw_ROLE = request.getParameter("cometa_clustalw_ROLE");
                // Get the CLUSTALW OPTIONS from the portlet request for the COMETA VO
                String[] cometa_clustalw_OPTIONS = request.getParameterValues("cometa_clustalw_OPTIONS");                

                String cometa_clustalw_RENEWAL = "";
                String cometa_clustalw_DISABLEVOMS = "";

                if (cometa_clustalw_OPTIONS == null){
                    cometa_clustalw_RENEWAL = "checked";
                    cometa_clustalw_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(cometa_clustalw_OPTIONS);
                    // Get the CLUSTALW RENEWAL from the portlet preferences for the COMETA VO
                    cometa_clustalw_RENEWAL = Arrays.binarySearch(cometa_clustalw_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the COMETA VO
                    cometa_clustalw_DISABLEVOMS = Arrays.binarySearch(cometa_clustalw_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                
                int nmax=0;                
                for (int i = 0; i < cometa_clustalw_WMS.length; i++)
                    if ( cometa_clustalw_WMS[i]!=null && (!cometa_clustalw_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] cometa_clustalw_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    cometa_clustalw_WMS_trimmed[i]=cometa_clustalw_WMS[i].trim();
                    log.info ("\n\nCOMETA [" + i + "] WMS=[" + cometa_clustalw_WMS_trimmed[i] + "]");
                }
                
                // Set the portlet preferences
                portletPreferences.setValue("cometa_clustalw_INFRASTRUCTURE", cometa_clustalw_INFRASTRUCTURE.trim());
                portletPreferences.setValue("cometa_clustalw_VONAME", cometa_clustalw_VONAME.trim());
                portletPreferences.setValue("cometa_clustalw_TOPBDII", cometa_clustalw_TOPBDII.trim());
                portletPreferences.setValues("cometa_clustalw_WMS", cometa_clustalw_WMS_trimmed);
                portletPreferences.setValue("cometa_clustalw_ETOKENSERVER", cometa_clustalw_ETOKENSERVER.trim());
                portletPreferences.setValue("cometa_clustalw_MYPROXYSERVER", cometa_clustalw_MYPROXYSERVER.trim());
                portletPreferences.setValue("cometa_clustalw_PORT", cometa_clustalw_PORT.trim());
                portletPreferences.setValue("cometa_clustalw_ROBOTID", cometa_clustalw_ROBOTID.trim());
                portletPreferences.setValue("cometa_clustalw_ROLE", cometa_clustalw_ROLE.trim());
                portletPreferences.setValue("cometa_clustalw_RENEWAL", cometa_clustalw_RENEWAL);
                portletPreferences.setValue("cometa_clustalw_DISABLEVOMS", cometa_clustalw_DISABLEVOMS);                
                
                portletPreferences.setValue("clustalw_APPID", clustalw_APPID.trim());
                portletPreferences.setValue("clustalw_LOGLEVEL", clustalw_LOGLEVEL.trim());
                portletPreferences.setValue("clustalw_OUTPUT_PATH", clustalw_OUTPUT_PATH.trim());
                portletPreferences.setValue("clustalw_SOFTWARE", clustalw_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                if (clustalw_LOGLEVEL.trim().equals("VERBOSE")) {
                log.info("\n\nPROCESS ACTION => " + action
                    + "\n- Storing the CLUSTALW portlet preferences ..."
                    + "\ncometa_clustalw_INFRASTRUCTURE: " + cometa_clustalw_INFRASTRUCTURE
                    + "\ncometa_clustalw_VONAME: " + cometa_clustalw_VONAME
                    + "\ncometa_clustalw_TOPBDII: " + cometa_clustalw_TOPBDII                    
                    + "\ncometa_clustalw_ETOKENSERVER: " + cometa_clustalw_ETOKENSERVER
                    + "\ncometa_clustalw_MYPROXYSERVER: " + cometa_clustalw_MYPROXYSERVER
                    + "\ncometa_clustalw_PORT: " + cometa_clustalw_PORT
                    + "\ncometa_clustalw_ROBOTID: " + cometa_clustalw_ROBOTID
                    + "\ncometa_clustalw_ROLE: " + cometa_clustalw_ROLE
                    + "\ncometa_clustalw_RENEWAL: " + cometa_clustalw_RENEWAL
                    + "\ncometa_clustalw_DISABLEVOMS: " + cometa_clustalw_DISABLEVOMS
                        
                    + "\n\nclustalw_ENABLEINFRASTRUCTURE: " + "cometa"
                    + "\nclustalw_APPID: " + clustalw_APPID
                    + "\nclustalw_LOGLEVEL: " + clustalw_LOGLEVEL
                    + "\nclustalw_OUTPUT_PATH: " + clustalw_OUTPUT_PATH
                    + "\nclustalw_SOFTWARE: " + clustalw_SOFTWARE
                    + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                    + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                    + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                    + "\nSMTP_HOST: " + SMTP_HOST
                    + "\nSENDER_MAIL: " + SENDER_MAIL);
                }
            }
            
            if (garuda_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[1]="garuda";
                // Get the CLUSTALW INFRASTRUCTURE from the portlet request for the GARUDA VO
                String garuda_clustalw_INFRASTRUCTURE = request.getParameter("garuda_clustalw_INFRASTRUCTURE");
                // Get the CLUSTALW VONAME from the portlet request for the GARUDA VO
                String garuda_clustalw_VONAME = request.getParameter("garuda_clustalw_VONAME");
                // Get the CLUSTALW TOPBDII from the portlet request for the GARUDA VO
                String garuda_clustalw_TOPBDII = request.getParameter("garuda_clustalw_TOPBDII");
                // Get the CLUSTALW WMS from the portlet request for the GARUDA VO
                String[] garuda_clustalw_WMS = request.getParameterValues("garuda_clustalw_WMS");
                // Get the CLUSTALW ETOKENSERVER from the portlet request for the GARUDA VO
                String garuda_clustalw_ETOKENSERVER = request.getParameter("garuda_clustalw_ETOKENSERVER");
                // Get the CLUSTALW MYPROXYSERVER from the portlet request for the GARUDA VO
                String garuda_clustalw_MYPROXYSERVER = request.getParameter("garuda_clustalw_MYPROXYSERVER");
                // Get the CLUSTALW PORT from the portlet request for the GARUDA VO
                String garuda_clustalw_PORT = request.getParameter("garuda_clustalw_PORT");
                // Get the CLUSTALW ROBOTID from the portlet request for the GARUDA VO
                String garuda_clustalw_ROBOTID = request.getParameter("garuda_clustalw_ROBOTID");
                // Get the CLUSTALW ROLE from the portlet request for the GARUDA VO
                String garuda_clustalw_ROLE = request.getParameter("garuda_clustalw_ROLE");
                // Get the CLUSTALW OPTIONS from the portlet request for the GARUDA VO
                String[] garuda_clustalw_OPTIONS = request.getParameterValues("garuda_clustalw_OPTIONS"); 

                String garuda_clustalw_RENEWAL = "";
                String garuda_clustalw_DISABLEVOMS = "";

                if (garuda_clustalw_OPTIONS == null){
                    garuda_clustalw_RENEWAL = "checked";
                    garuda_clustalw_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(garuda_clustalw_OPTIONS);
                    // Get the CLUSTALW RENEWAL from the portlet preferences for the GARUDA VO
                    garuda_clustalw_RENEWAL = Arrays.binarySearch(garuda_clustalw_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the GARUDA VO
                    garuda_clustalw_DISABLEVOMS = Arrays.binarySearch(garuda_clustalw_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                
                int nmax=0;                
                for (int i = 0; i < garuda_clustalw_WMS.length; i++)
                    if ( garuda_clustalw_WMS[i]!=null && (!garuda_clustalw_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] garuda_clustalw_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    garuda_clustalw_WMS_trimmed[i]=garuda_clustalw_WMS[i].trim();
                    log.info ("\n\nGARUDA [" + i + "] WSGRAM=[" + garuda_clustalw_WMS_trimmed[i] + "]");
                }
                
                // Set the portlet preferences
                portletPreferences.setValue("garuda_clustalw_INFRASTRUCTURE", garuda_clustalw_INFRASTRUCTURE.trim());
                portletPreferences.setValue("garuda_clustalw_VONAME", garuda_clustalw_VONAME.trim());
                portletPreferences.setValue("garuda_clustalw_TOPBDII", garuda_clustalw_TOPBDII.trim());
                portletPreferences.setValues("garuda_clustalw_WMS", garuda_clustalw_WMS_trimmed);
                portletPreferences.setValue("garuda_clustalw_ETOKENSERVER", garuda_clustalw_ETOKENSERVER.trim());
                portletPreferences.setValue("garuda_clustalw_MYPROXYSERVER", garuda_clustalw_MYPROXYSERVER.trim());
                portletPreferences.setValue("garuda_clustalw_PORT", garuda_clustalw_PORT.trim());
                portletPreferences.setValue("garuda_clustalw_ROBOTID", garuda_clustalw_ROBOTID.trim());
                portletPreferences.setValue("garuda_clustalw_ROLE", garuda_clustalw_ROLE.trim());
                portletPreferences.setValue("garuda_clustalw_RENEWAL", garuda_clustalw_RENEWAL);
                portletPreferences.setValue("garuda_clustalw_DISABLEVOMS", garuda_clustalw_DISABLEVOMS);
                
                portletPreferences.setValue("clustalw_APPID", clustalw_APPID.trim());
                portletPreferences.setValue("clustalw_LOGLEVEL", clustalw_LOGLEVEL.trim());
                portletPreferences.setValue("clustalw_OUTPUT_PATH", clustalw_OUTPUT_PATH.trim());
                portletPreferences.setValue("clustalw_SOFTWARE", clustalw_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                if (clustalw_LOGLEVEL.trim().equals("VERBOSE")) {
                log.info("\n\nPROCESS ACTION => " + action
                + "\n- Storing the CLUSTALW portlet preferences ..."
                + "\ngaruda_clustalw_INFRASTRUCTURE: " + garuda_clustalw_INFRASTRUCTURE
                + "\ngaruda_clustalw_VONAME: " + garuda_clustalw_VONAME
                + "\ngaruda_clustalw_TOPBDII: " + garuda_clustalw_TOPBDII                    
                + "\ngaruda_clustalw_ETOKENSERVER: " + garuda_clustalw_ETOKENSERVER
                + "\ngaruda_clustalw_MYPROXYSERVER: " + garuda_clustalw_MYPROXYSERVER
                + "\ngaruda_clustalw_PORT: " + garuda_clustalw_PORT
                + "\ngaruda_clustalw_ROBOTID: " + garuda_clustalw_ROBOTID
                + "\ngaruda_clustalw_ROLE: " + garuda_clustalw_ROLE
                + "\ngaruda_clustalw_RENEWAL: " + garuda_clustalw_RENEWAL
                + "\ngaruda_clustalw_DISABLEVOMS: " + garuda_clustalw_DISABLEVOMS
                        
                + "\n\nclustalw_ENABLEINFRASTRUCTURE: " + "garuda"
                + "\nclustalw_APPID: " + clustalw_APPID
                + "\nclustalw_LOGLEVEL: " + clustalw_LOGLEVEL
                + "\nclustalw_OUTPUT_PATH: " + clustalw_OUTPUT_PATH
                + "\nclustalw_SOFTWARE: " + clustalw_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                }
            }

            if (gridit_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[2]="gridit";
                // Get the CLUSTALW INFRASTRUCTURE from the portlet request for the GRIDIT VO
                String gridit_clustalw_INFRASTRUCTURE = request.getParameter("gridit_clustalw_INFRASTRUCTURE");
                // Get the CLUSTALW VONAME from the portlet request for the GRIDIT VO
                String gridit_clustalw_VONAME = request.getParameter("gridit_clustalw_VONAME");
                // Get the CLUSTALW TOPBDII from the portlet request for the GRIDIT VO
                String gridit_clustalw_TOPBDII = request.getParameter("gridit_clustalw_TOPBDII");
                // Get the CLUSTALW WMS from the portlet request for the GRIDIT VO
                String[] gridit_clustalw_WMS = request.getParameterValues("gridit_clustalw_WMS");
                // Get the CLUSTALW ETOKENSERVER from the portlet request for the GRIDIT VO
                String gridit_clustalw_ETOKENSERVER = request.getParameter("gridit_clustalw_ETOKENSERVER");
                // Get the CLUSTALW MYPROXYSERVER from the portlet request for the GRIDIT VO
                String gridit_clustalw_MYPROXYSERVER = request.getParameter("gridit_clustalw_MYPROXYSERVER");
                // Get the CLUSTALW PORT from the portlet request for the GRIDIT VO
                String gridit_clustalw_PORT = request.getParameter("gridit_clustalw_PORT");
                // Get the CLUSTALW ROBOTID from the portlet request for the GRIDIT VO
                String gridit_clustalw_ROBOTID = request.getParameter("gridit_clustalw_ROBOTID");
                // Get the CLUSTALW ROLE from the portlet request for the GRIDIT VO
                String gridit_clustalw_ROLE = request.getParameter("gridit_clustalw_ROLE");
                // Get the CLUSTALW OPTIONS from the portlet request for the GRIDIT VO
                String[] gridit_clustalw_OPTIONS = request.getParameterValues("gridit_clustalw_OPTIONS");                

                String gridit_clustalw_RENEWAL = "";
                String gridit_clustalw_DISABLEVOMS = "";

                if (gridit_clustalw_OPTIONS == null){
                    gridit_clustalw_RENEWAL = "checked";
                    gridit_clustalw_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(gridit_clustalw_OPTIONS);
                    // Get the CLUSTALW RENEWAL from the portlet preferences for the GRIDIT VO
                    gridit_clustalw_RENEWAL = Arrays.binarySearch(gridit_clustalw_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the GRIDIT VO
                    gridit_clustalw_DISABLEVOMS = Arrays.binarySearch(gridit_clustalw_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                
                int nmax=0;                
                for (int i = 0; i < gridit_clustalw_WMS.length; i++)
                    if ( gridit_clustalw_WMS[i]!=null && (!gridit_clustalw_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] gridit_clustalw_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    gridit_clustalw_WMS_trimmed[i]=gridit_clustalw_WMS[i].trim();
                    log.info ("\n\nGRIDIT [" + i + "] WMS=[" + gridit_clustalw_WMS_trimmed[i] + "]");
                }
                
                // Set the portlet preferences
                portletPreferences.setValue("gridit_clustalw_INFRASTRUCTURE", gridit_clustalw_INFRASTRUCTURE.trim());
                portletPreferences.setValue("gridit_clustalw_VONAME", gridit_clustalw_VONAME.trim());
                portletPreferences.setValue("gridit_clustalw_TOPBDII", gridit_clustalw_TOPBDII.trim());
                portletPreferences.setValues("gridit_clustalw_WMS", gridit_clustalw_WMS_trimmed);
                portletPreferences.setValue("gridit_clustalw_ETOKENSERVER", gridit_clustalw_ETOKENSERVER.trim());
                portletPreferences.setValue("gridit_clustalw_MYPROXYSERVER", gridit_clustalw_MYPROXYSERVER.trim());
                portletPreferences.setValue("gridit_clustalw_PORT", gridit_clustalw_PORT.trim());
                portletPreferences.setValue("gridit_clustalw_ROBOTID", gridit_clustalw_ROBOTID.trim());
                portletPreferences.setValue("gridit_clustalw_ROLE", gridit_clustalw_ROLE.trim());
                portletPreferences.setValue("gridit_clustalw_RENEWAL", gridit_clustalw_RENEWAL);
                portletPreferences.setValue("gridit_clustalw_DISABLEVOMS", gridit_clustalw_DISABLEVOMS);                
                
                portletPreferences.setValue("clustalw_APPID", clustalw_APPID.trim());
                portletPreferences.setValue("clustalw_LOGLEVEL", clustalw_LOGLEVEL.trim());
                portletPreferences.setValue("clustalw_OUTPUT_PATH", clustalw_OUTPUT_PATH.trim());
                portletPreferences.setValue("clustalw_SOFTWARE", clustalw_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                if (clustalw_LOGLEVEL.trim().equals("VERBOSE")) {                
                log.info("\n\nPROCESS ACTION => " + action
                + "\n- Storing the CLUSTALW portlet preferences ..."
                + "\ngridit_clustalw_INFRASTRUCTURE: " + gridit_clustalw_INFRASTRUCTURE
                + "\ngridit_clustalw_VONAME: " + gridit_clustalw_VONAME
                + "\ngridit_clustalw_TOPBDII: " + gridit_clustalw_TOPBDII                    
                + "\ngridit_clustalw_ETOKENSERVER: " + gridit_clustalw_ETOKENSERVER
                + "\ngridit_clustalw_MYPROXYSERVER: " + gridit_clustalw_MYPROXYSERVER
                + "\ngridit_clustalw_PORT: " + gridit_clustalw_PORT
                + "\ngridit_clustalw_ROBOTID: " + gridit_clustalw_ROBOTID
                + "\ngridit_clustalw_ROLE: " + gridit_clustalw_ROLE
                + "\ngridit_clustalw_RENEWAL: " + gridit_clustalw_RENEWAL
                + "\ngridit_clustalw_DISABLEVOMS: " + gridit_clustalw_DISABLEVOMS
                        
                + "\n\nclustalw_ENABLEINFRASTRUCTURE: " + "gridit"
                + "\nclustalw_APPID: " + clustalw_APPID
                + "\nclustalw_LOGLEVEL: " + clustalw_LOGLEVEL
                + "\nclustalw_OUTPUT_PATH: " + clustalw_OUTPUT_PATH
                + "\nclustalw_SOFTWARE: " + clustalw_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                }
            }
            
            if (gilda_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[3]="gilda";
                // Get the CLUSTALW INFRASTRUCTURE from the portlet request for the GILDA VO
                String gilda_clustalw_INFRASTRUCTURE = request.getParameter("gilda_clustalw_INFRASTRUCTURE");
                // Get the CLUSTALW VONAME from the portlet request for the GILDA VO
                String gilda_clustalw_VONAME = request.getParameter("gilda_clustalw_VONAME");
                // Get the CLUSTALW TOPBDII from the portlet request for the GILDA VO
                String gilda_clustalw_TOPBDII = request.getParameter("gilda_clustalw_TOPBDII");
                // Get the CLUSTALW WMS from the portlet request for the GILDA VO
                String[] gilda_clustalw_WMS = request.getParameterValues("gilda_clustalw_WMS");
                // Get the CLUSTALW ETOKENSERVER from the portlet request for the GILDA VO
                String gilda_clustalw_ETOKENSERVER = request.getParameter("gilda_clustalw_ETOKENSERVER");
                // Get the CLUSTALW MYPROXYSERVER from the portlet request for the GILDA VO
                String gilda_clustalw_MYPROXYSERVER = request.getParameter("gilda_clustalw_MYPROXYSERVER");
                // Get the CLUSTALW PORT from the portlet request for the GILDA VO
                String gilda_clustalw_PORT = request.getParameter("gilda_clustalw_PORT");
                // Get the CLUSTALW ROBOTID from the portlet request for the GILDA VO
                String gilda_clustalw_ROBOTID = request.getParameter("gilda_clustalw_ROBOTID");
                // Get the CLUSTALW ROLE from the portlet request for the GILDA VO
                String gilda_clustalw_ROLE = request.getParameter("gilda_clustalw_ROLE");
                // Get the CLUSTALW OPTIONS from the portlet request for the GILDA VO
                String[] gilda_clustalw_OPTIONS = request.getParameterValues("gilda_clustalw_OPTIONS");                

                String gilda_clustalw_RENEWAL = "";
                String gilda_clustalw_DISABLEVOMS = "";

                if (gilda_clustalw_OPTIONS == null){
                    gilda_clustalw_RENEWAL = "checked";
                    gilda_clustalw_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(gilda_clustalw_OPTIONS);
                    // Get the CLUSTALW RENEWAL from the portlet preferences for the GILDA VO
                    gilda_clustalw_RENEWAL = Arrays.binarySearch(gilda_clustalw_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the GILDA VO
                    gilda_clustalw_DISABLEVOMS = Arrays.binarySearch(gilda_clustalw_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                
                int nmax=0;                
                for (int i = 0; i < gilda_clustalw_WMS.length; i++)
                    if ( gilda_clustalw_WMS[i]!=null && (!gilda_clustalw_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] gilda_clustalw_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    gilda_clustalw_WMS_trimmed[i]=gilda_clustalw_WMS[i].trim();
                    log.info ("\n\nCOMETA [" + i + "] WMS=[" + gilda_clustalw_WMS_trimmed[i] + "]");
                }
                
                // Set the portlet preferences
                portletPreferences.setValue("gilda_clustalw_INFRASTRUCTURE", gilda_clustalw_INFRASTRUCTURE.trim());
                portletPreferences.setValue("gilda_clustalw_VONAME", gilda_clustalw_VONAME.trim());
                portletPreferences.setValue("gilda_clustalw_TOPBDII", gilda_clustalw_TOPBDII.trim());
                portletPreferences.setValues("gilda_clustalw_WMS", gilda_clustalw_WMS_trimmed);
                portletPreferences.setValue("gilda_clustalw_ETOKENSERVER", gilda_clustalw_ETOKENSERVER.trim());
                portletPreferences.setValue("gilda_clustalw_MYPROXYSERVER", gilda_clustalw_MYPROXYSERVER.trim());
                portletPreferences.setValue("gilda_clustalw_PORT", gilda_clustalw_PORT.trim());
                portletPreferences.setValue("gilda_clustalw_ROBOTID", gilda_clustalw_ROBOTID.trim());
                portletPreferences.setValue("gilda_clustalw_ROLE", gilda_clustalw_ROLE.trim());
                portletPreferences.setValue("gilda_clustalw_RENEWAL", gilda_clustalw_RENEWAL);
                portletPreferences.setValue("gilda_clustalw_DISABLEVOMS", gilda_clustalw_DISABLEVOMS);
                
                portletPreferences.setValue("clustalw_APPID", clustalw_APPID.trim());
                portletPreferences.setValue("clustalw_LOGLEVEL", clustalw_LOGLEVEL.trim());
                portletPreferences.setValue("clustalw_OUTPUT_PATH", clustalw_OUTPUT_PATH.trim());
                portletPreferences.setValue("clustalw_SOFTWARE", clustalw_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                if (clustalw_LOGLEVEL.trim().equals("VERBOSE")) {
                log.info("\n\nPROCESS ACTION => " + action
                    + "\n- Storing the CLUSTALW portlet preferences ..."                    
                    + "\n\ngilda_clustalw_INFRASTRUCTURE: " + gilda_clustalw_INFRASTRUCTURE
                    + "\ngilda_clustalw_VONAME: " + gilda_clustalw_VONAME
                    + "\ngilda_clustalw_TOPBDII: " + gilda_clustalw_TOPBDII                    
                    + "\ngilda_clustalw_ETOKENSERVER: " + gilda_clustalw_ETOKENSERVER
                    + "\ngilda_clustalw_MYPROXYSERVER: " + gilda_clustalw_MYPROXYSERVER
                    + "\ngilda_clustalw_PORT: " + gilda_clustalw_PORT
                    + "\ngilda_clustalw_ROBOTID: " + gilda_clustalw_ROBOTID
                    + "\ngilda_clustalw_ROLE: " + gilda_clustalw_ROLE
                    + "\ngilda_clustalw_RENEWAL: " + gilda_clustalw_RENEWAL
                    + "\ngilda_clustalw_DISABLEVOMS: " + gilda_clustalw_DISABLEVOMS

                    + "\n\nclustalw_ENABLEINFRASTRUCTURE: " + "gilda"
                    + "\nclustalw_APPID: " + clustalw_APPID
                    + "\nclustalw_LOGLEVEL: " + clustalw_LOGLEVEL
                    + "\nclustalw_OUTPUT_PATH: " + clustalw_OUTPUT_PATH
                    + "\nclustalw_SOFTWARE: " + clustalw_SOFTWARE
                    + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                    + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                    + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                    + "\nSMTP_HOST: " + SMTP_HOST
                    + "\nSENDER_MAIL: " + SENDER_MAIL);
                }
            }

            if (eumed_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[4]="eumed";
                // Get the CLUSTALW INFRASTRUCTURE from the portlet request for the EUMED VO
                String eumed_clustalw_INFRASTRUCTURE = request.getParameter("eumed_clustalw_INFRASTRUCTURE");
                // Get the CLUSTALW VONAME from the portlet request for the EUMED VO
                String eumed_clustalw_VONAME = request.getParameter("eumed_clustalw_VONAME");
                // Get the CLUSTALW TOPBDII from the portlet request for the EUMED VO
                String eumed_clustalw_TOPBDII = request.getParameter("eumed_clustalw_TOPBDII");
                // Get the CLUSTALW WMS from the portlet request for the EUMED VO
                String[] eumed_clustalw_WMS = request.getParameterValues("eumed_clustalw_WMS");
                // Get the CLUSTALW ETOKENSERVER from the portlet request for the EUMED VO
                String eumed_clustalw_ETOKENSERVER = request.getParameter("eumed_clustalw_ETOKENSERVER");
                // Get the CLUSTALW MYPROXYSERVER from the portlet request for the EUMED VO
                String eumed_clustalw_MYPROXYSERVER = request.getParameter("eumed_clustalw_MYPROXYSERVER");
                // Get the CLUSTALW PORT from the portlet request for the EUMED VO
                String eumed_clustalw_PORT = request.getParameter("eumed_clustalw_PORT");
                // Get the CLUSTALW ROBOTID from the portlet request for the EUMED VO
                String eumed_clustalw_ROBOTID = request.getParameter("eumed_clustalw_ROBOTID");
                // Get the CLUSTALW ROLE from the portlet request for the EUMED VO
                String eumed_clustalw_ROLE = request.getParameter("eumed_clustalw_ROLE");
                // Get the CLUSTALW OPTIONS from the portlet request for the EUMED VO
                String[] eumed_clustalw_OPTIONS = request.getParameterValues("eumed_clustalw_OPTIONS");                

                String eumed_clustalw_RENEWAL = "";
                String eumed_clustalw_DISABLEVOMS = "";

                if (eumed_clustalw_OPTIONS == null){
                    eumed_clustalw_RENEWAL = "checked";
                    eumed_clustalw_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(eumed_clustalw_OPTIONS);
                    // Get the CLUSTALW RENEWAL from the portlet preferences for the EUMED VO
                    eumed_clustalw_RENEWAL = Arrays.binarySearch(eumed_clustalw_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the GRIDIT VO
                    eumed_clustalw_DISABLEVOMS = Arrays.binarySearch(eumed_clustalw_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                
                int nmax=0;                
                for (int i = 0; i < eumed_clustalw_WMS.length; i++)
                    if ( eumed_clustalw_WMS[i]!=null && (!eumed_clustalw_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] eumed_clustalw_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    eumed_clustalw_WMS_trimmed[i]=eumed_clustalw_WMS[i].trim();
                    log.info ("\n\nEUMED [" + i + "] WMS=[" + eumed_clustalw_WMS_trimmed[i] + "]");
                }
                
                // Set the portlet preferences
                portletPreferences.setValue("eumed_clustalw_INFRASTRUCTURE", eumed_clustalw_INFRASTRUCTURE.trim());
                portletPreferences.setValue("eumed_clustalw_VONAME", eumed_clustalw_VONAME.trim());
                portletPreferences.setValue("eumed_clustalw_TOPBDII", eumed_clustalw_TOPBDII.trim());
                portletPreferences.setValues("eumed_clustalw_WMS", eumed_clustalw_WMS_trimmed);
                portletPreferences.setValue("eumed_clustalw_ETOKENSERVER", eumed_clustalw_ETOKENSERVER.trim());
                portletPreferences.setValue("eumed_clustalw_MYPROXYSERVER", eumed_clustalw_MYPROXYSERVER.trim());
                portletPreferences.setValue("eumed_clustalw_PORT", eumed_clustalw_PORT.trim());
                portletPreferences.setValue("eumed_clustalw_ROBOTID", eumed_clustalw_ROBOTID.trim());
                portletPreferences.setValue("eumed_clustalw_ROLE", eumed_clustalw_ROLE.trim());
                portletPreferences.setValue("eumed_clustalw_RENEWAL", eumed_clustalw_RENEWAL);
                portletPreferences.setValue("eumed_clustalw_DISABLEVOMS", eumed_clustalw_DISABLEVOMS); 
                
                portletPreferences.setValue("clustalw_APPID", clustalw_APPID.trim());
                portletPreferences.setValue("clustalw_LOGLEVEL", clustalw_LOGLEVEL.trim());
                portletPreferences.setValue("clustalw_OUTPUT_PATH", clustalw_OUTPUT_PATH.trim());
                portletPreferences.setValue("clustalw_SOFTWARE", clustalw_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                if (clustalw_LOGLEVEL.trim().equals("VERBOSE")) {
                log.info("\n\nPROCESS ACTION => " + action
                + "\n- Storing the CLUSTALW portlet preferences ..."                    
                + "\n\neumed_clustalw_INFRASTRUCTURE: " + eumed_clustalw_INFRASTRUCTURE
                + "\neumed_clustalw_VONAME: " + eumed_clustalw_VONAME
                + "\neumed_clustalw_TOPBDII: " + eumed_clustalw_TOPBDII                    
                + "\neumed_clustalw_ETOKENSERVER: " + eumed_clustalw_ETOKENSERVER
                + "\neumed_clustalw_MYPROXYSERVER: " + eumed_clustalw_MYPROXYSERVER
                + "\neumed_clustalw_PORT: " + eumed_clustalw_PORT
                + "\neumed_clustalw_ROBOTID: " + eumed_clustalw_ROBOTID
                + "\neumed_clustalw_ROLE: " + eumed_clustalw_ROLE
                + "\neumed_clustalw_RENEWAL: " + eumed_clustalw_RENEWAL
                + "\neumed_clustalw_DISABLEVOMS: " + eumed_clustalw_DISABLEVOMS

                + "\n\nclustalw_ENABLEINFRASTRUCTURE: " + "eumed"
                + "\nclustalw_APPID: " + clustalw_APPID
                + "\nclustalw_LOGLEVEL: " + clustalw_LOGLEVEL
                + "\nclustalw_OUTPUT_PATH: " + clustalw_OUTPUT_PATH
                + "\nclustalw_SOFTWARE: " + clustalw_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                }
            }

            if (gisela_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[5]="gisela";
                // Get the CLUSTALW INFRASTRUCTURE from the portlet request for the GISELA VO
                String gisela_clustalw_INFRASTRUCTURE = request.getParameter("gisela_clustalw_INFRASTRUCTURE");
                // Get the CLUSTALW VONAME from the portlet request for the GISELA VO
                String gisela_clustalw_VONAME = request.getParameter("gisela_clustalw_VONAME");
                // Get the CLUSTALW TOPBDII from the portlet request for the GISELA VO
                String gisela_clustalw_TOPBDII = request.getParameter("gisela_clustalw_TOPBDII");
                // Get the CLUSTALW WMS from the portlet request for the GISELA VO
                String[] gisela_clustalw_WMS = request.getParameterValues("gisela_clustalw_WMS");
                // Get the CLUSTALW ETOKENSERVER from the portlet request for the GISELA VO
                String gisela_clustalw_ETOKENSERVER = request.getParameter("gisela_clustalw_ETOKENSERVER");
                // Get the CLUSTALW MYPROXYSERVER from the portlet request for the GISELA VO
                String gisela_clustalw_MYPROXYSERVER = request.getParameter("gisela_clustalw_MYPROXYSERVER");
                // Get the CLUSTALW PORT from the portlet request for the GISELA VO
                String gisela_clustalw_PORT = request.getParameter("gisela_clustalw_PORT");
                // Get the CLUSTALW ROBOTID from the portlet request for the GISELA VO
                String gisela_clustalw_ROBOTID = request.getParameter("gisela_clustalw_ROBOTID");
                // Get the CLUSTALW ROLE from the portlet request for the GISELA VO
                String gisela_clustalw_ROLE = request.getParameter("gisela_clustalw_ROLE");
                // Get the CLUSTALW OPTIONS from the portlet request for the GISELA VO
                String[] gisela_clustalw_OPTIONS = request.getParameterValues("gisela_clustalw_OPTIONS");                

                String gisela_clustalw_RENEWAL = "";
                String gisela_clustalw_DISABLEVOMS = "";

                if (gisela_clustalw_OPTIONS == null){
                    gisela_clustalw_RENEWAL = "checked";
                    gisela_clustalw_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(gisela_clustalw_OPTIONS);
                    // Get the CLUSTALW RENEWAL from the portlet preferences for the GISELA VO
                    gisela_clustalw_RENEWAL = Arrays.binarySearch(gisela_clustalw_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the GISELA VO
                    gisela_clustalw_DISABLEVOMS = Arrays.binarySearch(gisela_clustalw_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }                       

                int nmax=0;                
                for (int i = 0; i < gisela_clustalw_WMS.length; i++)
                    if ( gisela_clustalw_WMS[i]!=null && (!gisela_clustalw_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] gisela_clustalw_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    gisela_clustalw_WMS_trimmed[i]=gisela_clustalw_WMS[i].trim();
                    log.info ("\n\nGISELA [" + i + "] WMS=[" + gisela_clustalw_WMS_trimmed[i] + "]");
                }

                // Set the portlet preferences
                portletPreferences.setValue("gisela_clustalw_INFRASTRUCTURE", gisela_clustalw_INFRASTRUCTURE.trim());
                portletPreferences.setValue("gisela_clustalw_VONAME", gisela_clustalw_VONAME.trim());
                portletPreferences.setValue("gisela_clustalw_TOPBDII", gisela_clustalw_TOPBDII.trim());
                portletPreferences.setValues("gisela_clustalw_WMS", gisela_clustalw_WMS_trimmed);
                portletPreferences.setValue("gisela_clustalw_ETOKENSERVER", gisela_clustalw_ETOKENSERVER.trim());
                portletPreferences.setValue("gisela_clustalw_MYPROXYSERVER", gisela_clustalw_MYPROXYSERVER.trim());
                portletPreferences.setValue("gisela_clustalw_PORT", gisela_clustalw_PORT.trim());
                portletPreferences.setValue("gisela_clustalw_ROBOTID", gisela_clustalw_ROBOTID.trim());
                portletPreferences.setValue("gisela_clustalw_ROLE", gisela_clustalw_ROLE.trim());
                portletPreferences.setValue("gisela_clustalw_RENEWAL", gisela_clustalw_RENEWAL);
                portletPreferences.setValue("gisela_clustalw_DISABLEVOMS", gisela_clustalw_DISABLEVOMS);
                
                portletPreferences.setValue("clustalw_APPID", clustalw_APPID.trim());
                portletPreferences.setValue("clustalw_LOGLEVEL", clustalw_LOGLEVEL.trim());
                portletPreferences.setValue("clustalw_OUTPUT_PATH", clustalw_OUTPUT_PATH.trim());
                portletPreferences.setValue("clustalw_SOFTWARE", clustalw_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                if (clustalw_LOGLEVEL.trim().equals("VERBOSE")) {
                log.info("\n\nPROCESS ACTION => " + action
                + "\n- Storing the CLUSTALW portlet preferences ..."                    
                + "\n\ngisela_clustalw_INFRASTRUCTURE: " + gisela_clustalw_INFRASTRUCTURE
                + "\ngisela_clustalw_VONAME: " + gisela_clustalw_VONAME
                + "\ngisela_clustalw_TOPBDII: " + gisela_clustalw_TOPBDII                    
                + "\ngisela_clustalw_ETOKENSERVER: " + gisela_clustalw_ETOKENSERVER
                + "\ngisela_clustalw_MYPROXYSERVER: " + gisela_clustalw_MYPROXYSERVER
                + "\ngisela_clustalw_PORT: " + gisela_clustalw_PORT
                + "\ngisela_clustalw_ROBOTID: " + gisela_clustalw_ROBOTID
                + "\ngisela_clustalw_ROLE: " + gisela_clustalw_ROLE
                + "\ngisela_clustalw_RENEWAL: " + gisela_clustalw_RENEWAL
                + "\ngisela_clustalw_DISABLEVOMS: " + gisela_clustalw_DISABLEVOMS

                + "\n\nclustalw_ENABLEINFRASTRUCTURE: " + "gisela"
                + "\nclustalw_APPID: " + clustalw_APPID
                + "\nclustalw_LOGLEVEL: " + clustalw_LOGLEVEL
                + "\nclustalw_OUTPUT_PATH: " + clustalw_OUTPUT_PATH
                + "\nclustalw_SOFTWARE: " + clustalw_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                }
            }
            
            if (sagrid_clustalw_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[6]="sagrid";
                // Get the CLUSTALW INFRASTRUCTURE from the portlet request for the SAGRID VO
                String sagrid_clustalw_INFRASTRUCTURE = request.getParameter("sagrid_clustalw_INFRASTRUCTURE");
                // Get the CLUSTALW VONAME from the portlet request for the SAGRID VO
                String sagrid_clustalw_VONAME = request.getParameter("sagrid_clustalw_VONAME");
                // Get the CLUSTALW TOPBDII from the portlet request for the SAGRID VO
                String sagrid_clustalw_TOPBDII = request.getParameter("sagrid_clustalw_TOPBDII");
                // Get the CLUSTALW WMS from the portlet request for the SAGRID VO
                String[] sagrid_clustalw_WMS = request.getParameterValues("sagrid_clustalw_WMS");
                // Get the CLUSTALW ETOKENSERVER from the portlet request for the SAGRID VO
                String sagrid_clustalw_ETOKENSERVER = request.getParameter("sagrid_clustalw_ETOKENSERVER");
                // Get the CLUSTALW MYPROXYSERVER from the portlet request for the SAGRID VO
                String sagrid_clustalw_MYPROXYSERVER = request.getParameter("sagrid_clustalw_MYPROXYSERVER");
                // Get the CLUSTALW PORT from the portlet request for the SAGRID VO
                String sagrid_clustalw_PORT = request.getParameter("sagrid_clustalw_PORT");
                // Get the CLUSTALW ROBOTID from the portlet request for the SAGRID VO
                String sagrid_clustalw_ROBOTID = request.getParameter("sagrid_clustalw_ROBOTID");
                // Get the CLUSTALW ROLE from the portlet request for the SAGRID VO
                String sagrid_clustalw_ROLE = request.getParameter("sagrid_clustalw_ROLE");
                // Get the CLUSTALW OPTIONS from the portlet request for the SAGRID VO
                String[] sagrid_clustalw_OPTIONS = request.getParameterValues("sagrid_clustalw_OPTIONS");                

                String sagrid_clustalw_RENEWAL = "";
                String sagrid_clustalw_DISABLEVOMS = "";

                if (sagrid_clustalw_OPTIONS == null){
                    sagrid_clustalw_RENEWAL = "checked";
                    sagrid_clustalw_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(sagrid_clustalw_OPTIONS);
                    // Get the CLUSTALW RENEWAL from the portlet preferences for the SAGRID VO
                    sagrid_clustalw_RENEWAL = Arrays.binarySearch(sagrid_clustalw_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the SAGRID VO
                    sagrid_clustalw_DISABLEVOMS = Arrays.binarySearch(sagrid_clustalw_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }                       

                int nmax=0;                
                for (int i = 0; i < sagrid_clustalw_WMS.length; i++)
                    if ( sagrid_clustalw_WMS[i]!=null && (!sagrid_clustalw_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] sagrid_clustalw_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    sagrid_clustalw_WMS_trimmed[i]=sagrid_clustalw_WMS[i].trim();
                    log.info ("\n\nSAGRID [" + i + "] WMS=[" + sagrid_clustalw_WMS_trimmed[i] + "]");
                }

                // Set the portlet preferences
                portletPreferences.setValue("sagrid_clustalw_INFRASTRUCTURE", sagrid_clustalw_INFRASTRUCTURE.trim());
                portletPreferences.setValue("sagrid_clustalw_VONAME", sagrid_clustalw_VONAME.trim());
                portletPreferences.setValue("sagrid_clustalw_TOPBDII", sagrid_clustalw_TOPBDII.trim());
                portletPreferences.setValues("sagrid_clustalw_WMS", sagrid_clustalw_WMS_trimmed);
                portletPreferences.setValue("sagrid_clustalw_ETOKENSERVER", sagrid_clustalw_ETOKENSERVER.trim());
                portletPreferences.setValue("sagrid_clustalw_MYPROXYSERVER", sagrid_clustalw_MYPROXYSERVER.trim());
                portletPreferences.setValue("sagrid_clustalw_PORT", sagrid_clustalw_PORT.trim());
                portletPreferences.setValue("sagrid_clustalw_ROBOTID", sagrid_clustalw_ROBOTID.trim());
                portletPreferences.setValue("sagrid_clustalw_ROLE", sagrid_clustalw_ROLE.trim());
                portletPreferences.setValue("sagrid_clustalw_RENEWAL", sagrid_clustalw_RENEWAL);
                portletPreferences.setValue("sagrid_clustalw_DISABLEVOMS", sagrid_clustalw_DISABLEVOMS);
                
                portletPreferences.setValue("clustalw_APPID", clustalw_APPID.trim());
                portletPreferences.setValue("clustalw_LOGLEVEL", clustalw_LOGLEVEL.trim());
                portletPreferences.setValue("clustalw_OUTPUT_PATH", clustalw_OUTPUT_PATH.trim());
                portletPreferences.setValue("clustalw_SOFTWARE", clustalw_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                if (clustalw_LOGLEVEL.trim().equals("VERBOSE")) {
                log.info("\n\nPROCESS ACTION => " + action
                + "\n- Storing the CLUSTALW portlet preferences ..."                    
                + "\n\nsagrid_clustalw_INFRASTRUCTURE: " + sagrid_clustalw_INFRASTRUCTURE
                + "\nsagrid_clustalw_VONAME: " + sagrid_clustalw_VONAME
                + "\nsagrid_clustalw_TOPBDII: " + sagrid_clustalw_TOPBDII                    
                + "\nsagrid_clustalw_ETOKENSERVER: " + sagrid_clustalw_ETOKENSERVER
                + "\nsagrid_clustalw_MYPROXYSERVER: " + sagrid_clustalw_MYPROXYSERVER
                + "\nsagrid_clustalw_PORT: " + sagrid_clustalw_PORT
                + "\nsagrid_clustalw_ROBOTID: " + sagrid_clustalw_ROBOTID
                + "\nsagrid_clustalw_ROLE: " + sagrid_clustalw_ROLE
                + "\nsagrid_clustalw_RENEWAL: " + sagrid_clustalw_RENEWAL
                + "\nsagrid_clustalw_DISABLEVOMS: " + sagrid_clustalw_DISABLEVOMS

                + "\n\nclustalw_ENABLEINFRASTRUCTURE: " + "sagrid"
                + "\nclustalw_APPID: " + clustalw_APPID
                + "\nclustalw_LEVEL: " + clustalw_LOGLEVEL
                + "\nclustalw_OUTPUT_PATH: " + clustalw_OUTPUT_PATH
                + "\nclustalw_SOFTWARE: " + clustalw_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                }
            }
            
            for (int i=0; i<infras.length; i++)
            log.info("\n - Infrastructure Enabled = " + infras[i]);
            portletPreferences.setValues("clustalw_ENABLEINFRASTRUCTURE", infras);
            portletPreferences.setValue("cometa_clustalw_ENABLEINFRASTRUCTURE",infras[0]);
            portletPreferences.setValue("garuda_clustalw_ENABLEINFRASTRUCTURE",infras[1]);
            portletPreferences.setValue("gridit_clustalw_ENABLEINFRASTRUCTURE",infras[2]);
            portletPreferences.setValue("gilda_clustalw_ENABLEINFRASTRUCTURE",infras[3]);
            portletPreferences.setValue("eumed_clustalw_ENABLEINFRASTRUCTURE",infras[4]);
            portletPreferences.setValue("gisela_clustalw_ENABLEINFRASTRUCTURE",infras[5]);
            portletPreferences.setValue("sagrid_clustalw_ENABLEINFRASTRUCTURE",infras[6]);

            portletPreferences.store();
            response.setPortletMode(PortletMode.VIEW);
        } // end PROCESS ACTION [ CONFIG_CLUSTALW_PORTLET ]
        

        if (action.equals("SUBMIT_CLUSTALW_PORTLET")) {
            log.info("\nPROCESS ACTION => " + action);            
            InfrastructureInfo infrastructures[] = new InfrastructureInfo[7];
            int NMAX=0;                        

            // Get the CLUSTALW APPID from the portlet preferences
            String clustalw_APPID = portletPreferences.getValue("clustalw_APPID", "N/A");
            // Get the LOGLEVEL from the portlet preferences
            String clustalw_LOGLEVEL = portletPreferences.getValue("clustalw_LOGLEVEL", "INFO");
            // Get the CLUSTALW APPID from the portlet preferences
            String clustalw_OUTPUT_PATH = portletPreferences.getValue("clustalw_OUTPUT_PATH", "/tmp");
            // Get the CLUSTALW SOFTWARE from the portlet preferences
            String clustalw_SOFTWARE = portletPreferences.getValue("clustalw_SOFTWARE", "N/A");
            // Get the TRACKING_DB_HOSTNAME from the portlet request
            String TRACKING_DB_HOSTNAME = portletPreferences.getValue("TRACKING_DB_HOSTNAME", "N/A");
            // Get the TRACKING_DB_USERNAME from the portlet request
            String TRACKING_DB_USERNAME = portletPreferences.getValue("TRACKING_DB_USERNAME", "N/A");
            // Get the TRACKING_DB_PASSWORD from the portlet request
            String TRACKING_DB_PASSWORD = portletPreferences.getValue("TRACKING_DB_PASSWORD","N/A");
            // Get the SMTP_HOST from the portlet request
            String SMTP_HOST = portletPreferences.getValue("SMTP_HOST","N/A");
            // Get the SENDER_MAIL from the portlet request
            String SENDER_MAIL = portletPreferences.getValue("SENDER_MAIL","N/A");        
            
            String cometa_clustalw_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("cometa_clustalw_ENABLEINFRASTRUCTURE","null");
            String garuda_clustalw_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("garuda_clustalw_ENABLEINFRASTRUCTURE","null");
            String gridit_clustalw_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("gridit_clustalw_ENABLEINFRASTRUCTURE","null");
            String gilda_clustalw_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("gilda_clustalw_ENABLEINFRASTRUCTURE","null");
            String eumed_clustalw_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("eumed_clustalw_ENABLEINFRASTRUCTURE","null");
            String gisela_clustalw_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("gisela_clustalw_ENABLEINFRASTRUCTURE","null");
            String sagrid_clustalw_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("sagrid_clustalw_ENABLEINFRASTRUCTURE","null");
        
            if (cometa_clustalw_ENABLEINFRASTRUCTURE != null &&
                cometa_clustalw_ENABLEINFRASTRUCTURE.equals("cometa"))
            {                
                NMAX++;                
                // Get the CLUSTALW VONAME from the portlet preferences for the COMETA VO
                String cometa_clustalw_INFRASTRUCTURE = portletPreferences.getValue("cometa_clustalw_INFRASTRUCTURE", "N/A");
                // Get the CLUSTALW VONAME from the portlet preferences for the COMETA VO
                String cometa_clustalw_VONAME = portletPreferences.getValue("cometa_clustalw_VONAME", "N/A");
                // Get the CLUSTALW TOPPBDII from the portlet preferences for the COMETA VO
                String cometa_clustalw_TOPBDII = portletPreferences.getValue("cometa_clustalw_TOPBDII", "N/A");
                // Get the CLUSTALW WMS from the portlet preferences for the COMETA VO                
                String[] cometa_clustalw_WMS = portletPreferences.getValues("cometa_clustalw_WMS", new String[5]);
                // Get the CLUSTALW ETOKENSERVER from the portlet preferences for the COMETA VO
                String cometa_clustalw_ETOKENSERVER = portletPreferences.getValue("cometa_clustalw_ETOKENSERVER", "N/A");
                // Get the CLUSTALW MYPROXYSERVER from the portlet preferences for the COMETA VO
                String cometa_clustalw_MYPROXYSERVER = portletPreferences.getValue("cometa_clustalw_MYPROXYSERVER", "N/A");
                // Get the CLUSTALW PORT from the portlet preferences for the COMETA VO
                String cometa_clustalw_PORT = portletPreferences.getValue("cometa_clustalw_PORT", "N/A");
                // Get the CLUSTALW ROBOTID from the portlet preferences for the COMETA VO
                String cometa_clustalw_ROBOTID = portletPreferences.getValue("cometa_clustalw_ROBOTID", "N/A");
                // Get the CLUSTALW ROLE from the portlet preferences for the COMETA VO
                String cometa_clustalw_ROLE = portletPreferences.getValue("cometa_clustalw_ROLE", "N/A");
                // Get the CLUSTALW RENEWAL from the portlet preferences for the COMETA VO
                String cometa_clustalw_RENEWAL = portletPreferences.getValue("cometa_clustalw_RENEWAL", "checked");
                // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the COMETA VO
                String cometa_clustalw_DISABLEVOMS = portletPreferences.getValue("cometa_clustalw_DISABLEVOMS", "unchecked");
                // Get the random CE for the ClustalW portlet               
                //RANDOM_CE = getRandomCE(cometa_clustalw_VONAME, cometa_clustalw_TOPBDII, clustalw_SOFTWARE);
                
                if (clustalw_LOGLEVEL.trim().equals("VERBOSE")) {
                log.info("\n- Getting the CLUSTALW portlet preferences ..."
                + "\ncometa_clustalw_INFRASTRUCTURE: " + cometa_clustalw_INFRASTRUCTURE
                + "\ncometa_clustalw_VONAME: " + cometa_clustalw_VONAME
                + "\ncometa_clustalw_TOPBDII: " + cometa_clustalw_TOPBDII                    
                + "\ncometa_clustalw_ETOKENSERVER: " + cometa_clustalw_ETOKENSERVER
                + "\ncometa_clustalw_MYPROXYSERVER: " + cometa_clustalw_MYPROXYSERVER
                + "\ncometa_clustalw_PORT: " + cometa_clustalw_PORT
                + "\ncometa_clustalw_ROBOTID: " + cometa_clustalw_ROBOTID
                + "\ncometa_clustalw_ROLE: " + cometa_clustalw_ROLE
                + "\ncometa_clustalw_RENEWAL: " + cometa_clustalw_RENEWAL
                + "\ncometa_clustalw_DISABLEVOMS: " + cometa_clustalw_DISABLEVOMS
                 
                + "\n\nclustalw_ENABLEINFRASTRUCTURE: " + cometa_clustalw_ENABLEINFRASTRUCTURE
                + "\nclustalw_APPID: " + clustalw_APPID
                + "\nclustalw_LOGLEVEL: " + clustalw_LOGLEVEL
                + "\nclustalw_OUTPUT_PATH: " + clustalw_OUTPUT_PATH
                + "\nclustalw_SOFTWARE: " + clustalw_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                }
                
                // Defining the WMS list for the "COMETA" Infrastructure
                int nmax=0;
                for (int i = 0; i < cometa_clustalw_WMS.length; i++)
                    if ((cometa_clustalw_WMS[i]!=null) && (!cometa_clustalw_WMS[i].equals("N/A"))) nmax++;

                String wmsList[] = new String [nmax];
                for (int i = 0; i < nmax; i++)
                {
                    if (cometa_clustalw_WMS[i]!=null) {
                    wmsList[i]=cometa_clustalw_WMS[i].trim();
                    log.info ("\n\n[" + nmax
                                      + "] Submitting to COMETA ["
                                      + i
                                      + "] using WMS=["
                                      + wmsList[i]
                                      + "]");
                    }
                }

                infrastructures[0] = new InfrastructureInfo(
                    cometa_clustalw_VONAME,
                    cometa_clustalw_TOPBDII,
                    wmsList,
                    cometa_clustalw_ETOKENSERVER,
                    cometa_clustalw_PORT,
                    cometa_clustalw_ROBOTID,
                    cometa_clustalw_VONAME,
                    cometa_clustalw_ROLE,
                    "VO-" + cometa_clustalw_VONAME + "-" + clustalw_SOFTWARE);
            }
            
            if (garuda_clustalw_ENABLEINFRASTRUCTURE != null &&
                garuda_clustalw_ENABLEINFRASTRUCTURE.equals("garuda"))
            {
                NMAX++;                
                // Get the CLUSTALW VONAME from the portlet preferences for the GARUDA VO
                String garuda_clustalw_INFRASTRUCTURE = portletPreferences.getValue("garuda_clustalw_INFRASTRUCTURE", "N/A");
                // Get the CLUSTALW VONAME from the portlet preferences for the GARUDA VO
                String garuda_clustalw_VONAME = portletPreferences.getValue("garuda_clustalw_VONAME", "N/A");
                // Get the CLUSTALW TOPPBDII from the portlet preferences for the GARUDA VO
                String garuda_clustalw_TOPBDII = portletPreferences.getValue("garuda_clustalw_TOPBDII", "N/A");
                // Get the CLUSTALW WMS from the portlet preferences for the GARUDA VO                
                String[] garuda_clustalw_WMS = portletPreferences.getValues("garuda_clustalw_WMS", new String[5]);
                // Get the CLUSTALW ETOKENSERVER from the portlet preferences for the GARUDA VO
                String garuda_clustalw_ETOKENSERVER = portletPreferences.getValue("garuda_clustalw_ETOKENSERVER", "N/A");
                // Get the CLUSTALW MYPROXYSERVER from the portlet preferences for the GARUDA VO
                String garuda_clustalw_MYPROXYSERVER = portletPreferences.getValue("garuda_clustalw_MYPROXYSERVER", "N/A");
                // Get the CLUSTALW PORT from the portlet preferences for the GARUDA VO
                String garuda_clustalw_PORT = portletPreferences.getValue("garuda_clustalw_PORT", "N/A");
                // Get the CLUSTALW ROBOTID from the portlet preferences for the GARUDA VO
                String garuda_clustalw_ROBOTID = portletPreferences.getValue("garuda_clustalw_ROBOTID", "N/A");
                // Get the CLUSTALW ROLE from the portlet preferences for the GARUDA VO
                String garuda_clustalw_ROLE = portletPreferences.getValue("garuda_clustalw_ROLE", "N/A");
                // Get the CLUSTALW RENEWAL from the portlet preferences for the GARUDA VO
                String garuda_clustalw_RENEWAL = portletPreferences.getValue("garuda_clustalw_RENEWAL", "checked");
                // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the GARUDA VO
                String garuda_clustalw_DISABLEVOMS = portletPreferences.getValue("garuda_clustalw_DISABLEVOMS", "unchecked"); 
                
                if (clustalw_LOGLEVEL.trim().equals("VERBOSE")) {
                log.info("\n- Getting the CLUSTALW portlet preferences ..."
                + "\ngaruda_clustalw_INFRASTRUCTURE: " + garuda_clustalw_INFRASTRUCTURE
                + "\ngaruda_clustalw_VONAME: " + garuda_clustalw_VONAME
                + "\ngaruda_clustalw_TOPBDII: " + garuda_clustalw_TOPBDII                    
                + "\ngaruda_clustalw_ETOKENSERVER: " + garuda_clustalw_ETOKENSERVER
                + "\ngaruda_clustalw_MYPROXYSERVER: " + garuda_clustalw_MYPROXYSERVER
                + "\ngaruda_clustalw_PORT: " + garuda_clustalw_PORT
                + "\ngaruda_clustalw_ROBOTID: " + garuda_clustalw_ROBOTID
                + "\ngaruda_clustalw_ROLE: " + garuda_clustalw_ROLE
                + "\ngaruda_clustalw_RENEWAL: " + garuda_clustalw_RENEWAL
                + "\ngaruda_clustalw_DISABLEVOMS: " + garuda_clustalw_DISABLEVOMS
                   
                + "\n\nclustalw_ENABLEINFRASTRUCTURE: " + garuda_clustalw_ENABLEINFRASTRUCTURE
                + "\nclustalw_APPID: " + clustalw_APPID
                + "\nclustalw_LOGLEVEL: " + clustalw_LOGLEVEL
                + "\nclustalw_OUTPUT_PATH: " + clustalw_OUTPUT_PATH
                + "\nclustalw_SOFTWARE: " + clustalw_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                }
                
                // Defining the WMS list for the "GARUDA" Infrastructure
                int nmax=0;
                for (int i = 0; i < garuda_clustalw_WMS.length; i++)
                    if ((garuda_clustalw_WMS[i]!=null) && (!garuda_clustalw_WMS[i].equals("N/A"))) nmax++;

                String wmsList[] = new String [nmax];
                for (int i = 0; i < nmax; i++)
                {
                    if (garuda_clustalw_WMS[i]!=null) {
                    wmsList[i]=garuda_clustalw_WMS[i].trim();
                    log.info ("\n\n[" + nmax
                                      + "] Submitting to GARUDA ["
                                      + i
                                      + "] using WSGRAM=["
                                      + wmsList[i]
                                      + "]");
                    }
                }

                infrastructures[1] = new InfrastructureInfo(
                    "GARUDA",
                    "wsgram",
                    "",
                    wmsList,
                    garuda_clustalw_ETOKENSERVER,
                    garuda_clustalw_PORT,
                    garuda_clustalw_ROBOTID,
                    garuda_clustalw_VONAME,
                    garuda_clustalw_ROLE);
            }
            
            if (gridit_clustalw_ENABLEINFRASTRUCTURE != null &&
                gridit_clustalw_ENABLEINFRASTRUCTURE.equals("gridit"))
            {
                NMAX++;                
                // Get the CLUSTALW VONAME from the portlet preferences for the GRIDIT VO
                String gridit_clustalw_INFRASTRUCTURE = portletPreferences.getValue("gridit_clustalw_INFRASTRUCTURE", "N/A");
                // Get the CLUSTALW VONAME from the portlet preferences for the GRIDIT VO
                String gridit_clustalw_VONAME = portletPreferences.getValue("gridit_clustalw_VONAME", "N/A");
                // Get the CLUSTALW TOPPBDII from the portlet preferences for the GRIDIT VO
                String gridit_clustalw_TOPBDII = portletPreferences.getValue("gridit_clustalw_TOPBDII", "N/A");
                // Get the CLUSTALW WMS from the portlet preferences for the GRIDIT VO                
                String[] gridit_clustalw_WMS = portletPreferences.getValues("gridit_clustalw_WMS", new String[5]);
                // Get the CLUSTALW ETOKENSERVER from the portlet preferences for the GRIDIT VO
                String gridit_clustalw_ETOKENSERVER = portletPreferences.getValue("gridit_clustalw_ETOKENSERVER", "N/A");
                // Get the CLUSTALW MYPROXYSERVER from the portlet preferences for the GRIDIT VO
                String gridit_clustalw_MYPROXYSERVER = portletPreferences.getValue("gridit_clustalw_MYPROXYSERVER", "N/A");
                // Get the CLUSTALW PORT from the portlet preferences for the GRIDIT VO
                String gridit_clustalw_PORT = portletPreferences.getValue("gridit_clustalw_PORT", "N/A");
                // Get the CLUSTALW ROBOTID from the portlet preferences for the GRIDIT VO
                String gridit_clustalw_ROBOTID = portletPreferences.getValue("gridit_clustalw_ROBOTID", "N/A");
                // Get the CLUSTALW ROLE from the portlet preferences for the GRIDIT VO
                String gridit_clustalw_ROLE = portletPreferences.getValue("gridit_clustalw_ROLE", "N/A");
                // Get the CLUSTALW RENEWAL from the portlet preferences for the GRIDIT VO
                String gridit_clustalw_RENEWAL = portletPreferences.getValue("gridit_clustalw_RENEWAL", "checked");
                // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the GRIDIT VO
                String gridit_clustalw_DISABLEVOMS = portletPreferences.getValue("gridit_clustalw_DISABLEVOMS", "unchecked"); 
                // Get the random CE for the Clustalw portlet               
                //RANDOM_CE = getRandomCE(gridit_clustalw_VONAME, gridit_clustalw_TOPBDII, clustalw_SOFTWARE);
                
                if (clustalw_LOGLEVEL.trim().equals("VERBOSE")) {
                log.info("\n- Getting the CLUSTALW portlet preferences ..."
                + "\ngridit_clustalw_INFRASTRUCTURE: " + gridit_clustalw_INFRASTRUCTURE
                + "\ngridit_clustalw_VONAME: " + gridit_clustalw_VONAME
                + "\ngridit_clustalw_TOPBDII: " + gridit_clustalw_TOPBDII                    
                + "\ngridit_clustalw_ETOKENSERVER: " + gridit_clustalw_ETOKENSERVER
                + "\ngridit_clustalw_MYPROXYSERVER: " + gridit_clustalw_MYPROXYSERVER
                + "\ngridit_clustalw_PORT: " + gridit_clustalw_PORT
                + "\ngridit_clustalw_ROBOTID: " + gridit_clustalw_ROBOTID
                + "\ngridit_clustalw_ROLE: " + gridit_clustalw_ROLE
                + "\ngridit_clustalw_RENEWAL: " + gridit_clustalw_RENEWAL
                + "\ngridit_clustalw_DISABLEVOMS: " + gridit_clustalw_DISABLEVOMS
                   
                + "\n\nclustalw_ENABLEINFRASTRUCTURE: " + gridit_clustalw_ENABLEINFRASTRUCTURE
                + "\nclustalw_APPID: " + clustalw_APPID
                + "\nclustalw_LOGLEVEL: " + clustalw_LOGLEVEL                        
                + "\nclustalw_OUTPUT_PATH: " + clustalw_OUTPUT_PATH
                + "\nclustalw_SOFTWARE: " + clustalw_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                }
                
                // Defining the WMS list for the "GRIDIT" Infrastructure
                int nmax=0;
                for (int i = 0; i < gridit_clustalw_WMS.length; i++)
                    if ((gridit_clustalw_WMS[i]!=null) && (!gridit_clustalw_WMS[i].equals("N/A"))) nmax++;

                String wmsList[] = new String [nmax];
                for (int i = 0; i < nmax; i++)
                {
                    if (gridit_clustalw_WMS[i]!=null) {
                    wmsList[i]=gridit_clustalw_WMS[i].trim();
                    log.info ("\n\n[" + nmax
                                      + "] Submitting to GRIDIT ["
                                      + i
                                      + "] using WMS=["
                                      + wmsList[i]
                                      + "]");
                    }
                }

                infrastructures[2] = new InfrastructureInfo(
                    gridit_clustalw_VONAME,
                    gridit_clustalw_TOPBDII,
                    wmsList,
                    gridit_clustalw_ETOKENSERVER,
                    gridit_clustalw_PORT,
                    gridit_clustalw_ROBOTID,
                    gridit_clustalw_VONAME,
                    gridit_clustalw_ROLE,
                    "VO-" + gridit_clustalw_VONAME + "-" + clustalw_SOFTWARE);
            }
            
            if (gilda_clustalw_ENABLEINFRASTRUCTURE != null &&
                gilda_clustalw_ENABLEINFRASTRUCTURE.equals("gilda"))
            {
                NMAX++;                
                // Get the CLUSTALW VONAME from the portlet preferences for the GILDA VO
                String gilda_clustalw_INFRASTRUCTURE = portletPreferences.getValue("gilda_clustalw_INFRASTRUCTURE", "N/A");
                // Get the CLUSTALW VONAME from the portlet preferences for the GILDA VO
                String gilda_clustalw_VONAME = portletPreferences.getValue("gilda_clustalw_VONAME", "N/A");
                // Get the CLUSTALW TOPPBDII from the portlet preferences for the GILDA VO
                String gilda_clustalw_TOPBDII = portletPreferences.getValue("gilda_clustalw_TOPBDII", "N/A");
                // Get the CLUSTALW WMS from the portlet preferences for the GILDA VO
                String[] gilda_clustalw_WMS = portletPreferences.getValues("gilda_clustalw_WMS", new String[5]);
                // Get the CLUSTALW ETOKENSERVER from the portlet preferences for the GILDA VO
                String gilda_clustalw_ETOKENSERVER = portletPreferences.getValue("gilda_clustalw_ETOKENSERVER", "N/A");
                // Get the CLUSTALW MYPROXYSERVER from the portlet preferences for the GILDA VO
                String gilda_clustalw_MYPROXYSERVER = portletPreferences.getValue("gilda_clustalw_MYPROXYSERVER", "N/A");
                // Get the CLUSTALW PORT from the portlet preferences for the GILDA VO
                String gilda_clustalw_PORT = portletPreferences.getValue("gilda_clustalw_PORT", "N/A");
                // Get the CLUSTALW ROBOTID from the portlet preferences for the GILDA VO
                String gilda_clustalw_ROBOTID = portletPreferences.getValue("gilda_clustalw_ROBOTID", "N/A");
                // Get the CLUSTALW ROLE from the portlet preferences for the GILDA VO
                String gilda_clustalw_ROLE = portletPreferences.getValue("gilda_clustalw_ROLE", "N/A");
                // Get the CLUSTALW RENEWAL from the portlet preferences for the GILDA VO
                String gilda_clustalw_RENEWAL = portletPreferences.getValue("gilda_clustalw_RENEWAL", "checked");
                // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the GILDA VO
                String gilda_clustalw_DISABLEVOMS = portletPreferences.getValue("gilda_clustalw_DISABLEVOMS", "unchecked");
                // Get the random CE for the Clustalw portlet               
                //RANDOM_CE = getRandomCE(gilda_clustalw_VONAME, gilda_clustalw_TOPBDII, clustalw_SOFTWARE);
                
                if (clustalw_LOGLEVEL.trim().equals("VERBOSE")) {
                log.info("\n- Getting the CLUSTALW portlet preferences ..."
                    + "\n\ngilda_clustalw_INFRASTRUCTURE: " + gilda_clustalw_INFRASTRUCTURE
                    + "\ngilda_clustalw_VONAME: " + gilda_clustalw_VONAME
                    + "\ngilda_clustalw_TOPBDII: " + gilda_clustalw_TOPBDII                    
                    + "\ngilda_clustalw_ETOKENSERVER: " + gilda_clustalw_ETOKENSERVER
                    + "\ngilda_clustalw_MYPROXYSERVER: " + gilda_clustalw_MYPROXYSERVER
                    + "\ngilda_clustalw_PORT: " + gilda_clustalw_PORT
                    + "\ngilda_clustalw_ROBOTID: " + gilda_clustalw_ROBOTID
                    + "\ngilda_clustalw_ROLE: " + gilda_clustalw_ROLE
                    + "\ngilda_clustalw_RENEWAL: " + gilda_clustalw_RENEWAL
                    + "\ngilda_clustalw_DISABLEVOMS: " + gilda_clustalw_DISABLEVOMS

                    + "\n\nclustalw_ENABLEINFRASTRUCTURE: " + gilda_clustalw_ENABLEINFRASTRUCTURE
                    + "\nclustalw_APPID: " + clustalw_APPID
                    + "\nclustalw_LOGLEVEL: " + clustalw_LOGLEVEL                        
                    + "\nclustalw_OUTPUT_PATH: " + clustalw_OUTPUT_PATH
                    + "\nclustalw_SOFTWARE: " + clustalw_SOFTWARE
                    + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                    + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                    + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                    + "\nSMTP_HOST: " + SMTP_HOST
                    + "\nSENDER_MAIL: " + SENDER_MAIL);
                }
                
                // Defining the WMS list for the "GILDA" Infrastructure
                int nmax=0;
                for (int i = 0; i < gilda_clustalw_WMS.length; i++)
                    if ((gilda_clustalw_WMS[i]!=null) && (!gilda_clustalw_WMS[i].equals("N/A"))) nmax++;
                
                String wmsList[] = new String [nmax];
                for (int i = 0; i < nmax; i++)
                {
                    if (gilda_clustalw_WMS[i]!=null) {
                    wmsList[i]=gilda_clustalw_WMS[i].trim();
                    log.info ("\n\n[" + nmax
                                      + "] Submitting to GILDA ["
                                      + i
                                      + "] using WMS=["
                                      + wmsList[i]
                                      + "]");
                    }
                }

                infrastructures[3] = new InfrastructureInfo(
                    gilda_clustalw_VONAME,
                    gilda_clustalw_TOPBDII,
                    wmsList,
                    gilda_clustalw_ETOKENSERVER,
                    gilda_clustalw_PORT,
                    gilda_clustalw_ROBOTID,
                    gilda_clustalw_VONAME,
                    gilda_clustalw_ROLE,
                    "VO-" + gilda_clustalw_VONAME + "-" + clustalw_SOFTWARE);
            }
            
            if (eumed_clustalw_ENABLEINFRASTRUCTURE != null &&
                eumed_clustalw_ENABLEINFRASTRUCTURE.equals("eumed"))
            {
                NMAX++;                
                // Get the CLUSTALW VONAME from the portlet preferences for the EUMED VO
                String eumed_clustalw_INFRASTRUCTURE = portletPreferences.getValue("eumed_clustalw_INFRASTRUCTURE", "N/A");
                // Get the CLUSTALW VONAME from the portlet preferences for the EUMED VO
                String eumed_clustalw_VONAME = portletPreferences.getValue("eumed_clustalw_VONAME", "N/A");
                // Get the CLUSTALW TOPPBDII from the portlet preferences for the EUMED VO
                String eumed_clustalw_TOPBDII = portletPreferences.getValue("eumed_clustalw_TOPBDII", "N/A");
                // Get the CLUSTALW WMS from the portlet preferences for the EUMED VO
                String[] eumed_clustalw_WMS = portletPreferences.getValues("eumed_clustalw_WMS", new String[5]);
                // Get the CLUSTALW ETOKENSERVER from the portlet preferences for the EUMED VO
                String eumed_clustalw_ETOKENSERVER = portletPreferences.getValue("eumed_clustalw_ETOKENSERVER", "N/A");
                // Get the CLUSTALW MYPROXYSERVER from the portlet preferences for the EUMED VO
                String eumed_clustalw_MYPROXYSERVER = portletPreferences.getValue("eumed_clustalw_MYPROXYSERVER", "N/A");
                // Get the CLUSTALW PORT from the portlet preferences for the EUMED VO
                String eumed_clustalw_PORT = portletPreferences.getValue("eumed_clustalw_PORT", "N/A");
                // Get the CLUSTALW ROBOTID from the portlet preferences for the EUMED VO
                String eumed_clustalw_ROBOTID = portletPreferences.getValue("eumed_clustalw_ROBOTID", "N/A");
                // Get the CLUSTALW ROLE from the portlet preferences for the EUMED VO
                String eumed_clustalw_ROLE = portletPreferences.getValue("eumed_clustalw_ROLE", "N/A");
                // Get the CLUSTALW RENEWAL from the portlet preferences for the EUMED VO
                String eumed_clustalw_RENEWAL = portletPreferences.getValue("eumed_clustalw_RENEWAL", "checked");
                // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the EUMED VO
                String eumed_clustalw_DISABLEVOMS = portletPreferences.getValue("eumed_clustalw_DISABLEVOMS", "unchecked");
                // Get the random CE for the Clustalw portlet               
                //RANDOM_CE = getRandomCE(eumed_clustalw_VONAME, eumed_clustalw_TOPBDII, clustalw_SOFTWARE);
                
                if (clustalw_LOGLEVEL.trim().equals("VERBOSE")) {
                log.info("\n- Getting the CLUSTALW portlet preferences ..."
                + "\n\neumed_clustalw_INFRASTRUCTURE: " + eumed_clustalw_INFRASTRUCTURE
                + "\neumed_clustalw_VONAME: " + eumed_clustalw_VONAME
                + "\neumed_clustalw_TOPBDII: " + eumed_clustalw_TOPBDII                    
                + "\neumed_clustalw_ETOKENSERVER: " + eumed_clustalw_ETOKENSERVER
                + "\neumed_clustalw_MYPROXYSERVER: " + eumed_clustalw_MYPROXYSERVER
                + "\neumed_clustalw_PORT: " + eumed_clustalw_PORT
                + "\neumed_clustalw_ROBOTID: " + eumed_clustalw_ROBOTID
                + "\neumed_clustalw_ROLE: " + eumed_clustalw_ROLE
                + "\neumed_clustalw_RENEWAL: " + eumed_clustalw_RENEWAL
                + "\neumed_clustalw_DISABLEVOMS: " + eumed_clustalw_DISABLEVOMS

                + "\n\nclustalw_ENABLEINFRASTRUCTURE: " + eumed_clustalw_ENABLEINFRASTRUCTURE
                + "\nclustalw_APPID: " + clustalw_APPID
                + "\nclustalw_LOGLEVEL: " + clustalw_LOGLEVEL                        
                + "\nclustalw_OUTPUT_PATH: " + clustalw_OUTPUT_PATH
                + "\nclustalw_SOFTWARE: " + clustalw_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                }
                
                // Defining the WMS list for the "EUMED" Infrastructure
                int nmax=0;
                for (int i = 0; i < eumed_clustalw_WMS.length; i++)
                    if ((eumed_clustalw_WMS[i]!=null) && (!eumed_clustalw_WMS[i].equals("N/A"))) nmax++;
                
                String wmsList[] = new String [nmax];
                for (int i = 0; i < nmax; i++)
                {
                    if (eumed_clustalw_WMS[i]!=null) {
                    wmsList[i]=eumed_clustalw_WMS[i].trim();
                    log.info ("\n\n[" + nmax
                                      + "] Submitting to EUMED ["
                                      + i
                                      + "] using WMS=["
                                      + wmsList[i]
                                      + "]");
                    }
                }

                infrastructures[4] = new InfrastructureInfo(
                    eumed_clustalw_VONAME,
                    eumed_clustalw_TOPBDII,
                    wmsList,
                    eumed_clustalw_ETOKENSERVER,
                    eumed_clustalw_PORT,
                    eumed_clustalw_ROBOTID,
                    eumed_clustalw_VONAME,
                    eumed_clustalw_ROLE,
                    "VO-" + eumed_clustalw_VONAME + "-" + clustalw_SOFTWARE);
            }

            if (gisela_clustalw_ENABLEINFRASTRUCTURE != null &&
                gisela_clustalw_ENABLEINFRASTRUCTURE.equals("gisela")) 
            {
                NMAX++;                
                // Get the CLUSTALW VONAME from the portlet preferences for the GISELA VO
                String gisela_clustalw_INFRASTRUCTURE = portletPreferences.getValue("gisela_clustalw_INFRASTRUCTURE", "N/A");
                // Get the CLUSTALW VONAME from the portlet preferences for the GISELA VO
                String gisela_clustalw_VONAME = portletPreferences.getValue("gisela_clustalw_VONAME", "N/A");
                // Get the CLUSTALW TOPPBDII from the portlet preferences for the GISELA VO
                String gisela_clustalw_TOPBDII = portletPreferences.getValue("gisela_clustalw_TOPBDII", "N/A");
                // Get the CLUSTALW WMS from the portlet preferences for the GISELA VO
                String[] gisela_clustalw_WMS = portletPreferences.getValues("gisela_clustalw_WMS", new String[5]);
                // Get the CLUSTALW ETOKENSERVER from the portlet preferences for the GISELA VO
                String gisela_clustalw_ETOKENSERVER = portletPreferences.getValue("gisela_clustalw_ETOKENSERVER", "N/A");
                // Get the CLUSTALW MYPROXYSERVER from the portlet preferences for the GISELA VO
                String gisela_clustalw_MYPROXYSERVER = portletPreferences.getValue("gisela_clustalw_MYPROXYSERVER", "N/A");
                // Get the CLUSTALW PORT from the portlet preferences for the GISELA VO
                String gisela_clustalw_PORT = portletPreferences.getValue("gisela_clustalw_PORT", "N/A");
                // Get the CLUSTALW ROBOTID from the portlet preferences for the GISELA VO
                String gisela_clustalw_ROBOTID = portletPreferences.getValue("gisela_clustalw_ROBOTID", "N/A");
                // Get the CLUSTALW ROLE from the portlet preferences for the GISELA VO
                String gisela_clustalw_ROLE = portletPreferences.getValue("gisela_clustalw_ROLE", "N/A");
                // Get the CLUSTALW RENEWAL from the portlet preferences for the GISELA VO
                String gisela_clustalw_RENEWAL = portletPreferences.getValue("gisela_clustalw_RENEWAL", "checked");
                // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the GISELA VO
                String gisela_clustalw_DISABLEVOMS = portletPreferences.getValue("gisela_clustalw_DISABLEVOMS", "unchecked");          
                // Get the random CE for the Clustalw portlet               
                //RANDOM_CE = getRandomCE(gisela_clustalw_VONAME, gisela_clustalw_TOPBDII, clustalw_SOFTWARE);
                
                if (clustalw_LOGLEVEL.trim().equals("VERBOSE")) {
                log.info("\n- Getting the CLUSTALW portlet preferences ..."
                + "\n\ngisela_clustalw_INFRASTRUCTURE: " + gisela_clustalw_INFRASTRUCTURE
                + "\ngisela_clustalw_VONAME: " + gisela_clustalw_VONAME
                + "\ngisela_clustalw_TOPBDII: " + gisela_clustalw_TOPBDII                        
                + "\ngisela_clustalw_ETOKENSERVER: " + gisela_clustalw_ETOKENSERVER
                + "\ngisela_clustalw_MYPROXYSERVER: " + gisela_clustalw_MYPROXYSERVER
                + "\ngisela_clustalw_PORT: " + gisela_clustalw_PORT
                + "\ngisela_clustalw_ROBOTID: " + gisela_clustalw_ROBOTID
                + "\ngisela_clustalw_ROLE: " + gisela_clustalw_ROLE
                + "\ngisela_clustalw_RENEWAL: " + gisela_clustalw_RENEWAL
                + "\ngisela_clustalw_DISABLEVOMS: " + gisela_clustalw_DISABLEVOMS

                + "\n\nclustalw_ENABLEINFRASTRUCTURE: " + gisela_clustalw_ENABLEINFRASTRUCTURE
                + "\nclustalw_APPID: " + clustalw_APPID
                + "\nclustalw_LOGLEVEL: " + clustalw_LOGLEVEL                        
                + "\nclustalw_OUTPUT_PATH: " + clustalw_OUTPUT_PATH
                + "\nclustalw_SOFTWARE: " + clustalw_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                }
                
                // Defining the WMS list for the "GISELA" Infrastructure
                int nmax=0;
                for (int i = 0; i < gisela_clustalw_WMS.length; i++)
                    if ((gisela_clustalw_WMS[i]!=null) && (!gisela_clustalw_WMS[i].equals("N/A"))) nmax++;
                
                String wmsList[] = new String [gisela_clustalw_WMS.length];
                for (int i = 0; i < gisela_clustalw_WMS.length; i++)
                {
                    if (gisela_clustalw_WMS[i]!=null) {
                    wmsList[i]=gisela_clustalw_WMS[i].trim();
                    log.info ("\n\nSubmitting for GISELA [" + i + "] using WMS=[" + wmsList[i] + "]");
                    }
                }

                infrastructures[5] = new InfrastructureInfo(
                    gisela_clustalw_VONAME,
                    gisela_clustalw_TOPBDII,
                    wmsList,
                    gisela_clustalw_ETOKENSERVER,
                    gisela_clustalw_PORT,
                    gisela_clustalw_ROBOTID,
                    gisela_clustalw_VONAME,
                    gisela_clustalw_ROLE,
                    "VO-" + gisela_clustalw_VONAME + "-" + clustalw_SOFTWARE);
            }
            
            if (sagrid_clustalw_ENABLEINFRASTRUCTURE != null &&
                sagrid_clustalw_ENABLEINFRASTRUCTURE.equals("sagrid")) 
            {
                NMAX++;                
                // Get the CLUSTALW VONAME from the portlet preferences for the SAGRID VO
                String sagrid_clustalw_INFRASTRUCTURE = portletPreferences.getValue("sagrid_clustalw_INFRASTRUCTURE", "N/A");
                // Get the CLUSTALW VONAME from the portlet preferences for the SAGRID VO
                String sagrid_clustalw_VONAME = portletPreferences.getValue("sagrid_clustalw_VONAME", "N/A");
                // Get the CLUSTALW TOPPBDII from the portlet preferences for the SAGRID VO
                String sagrid_clustalw_TOPBDII = portletPreferences.getValue("sagrid_clustalw_TOPBDII", "N/A");
                // Get the CLUSTALW WMS from the portlet preferences for the SAGRID VO
                String[] sagrid_clustalw_WMS = portletPreferences.getValues("sagrid_clustalw_WMS", new String[5]);
                // Get the CLUSTALW ETOKENSERVER from the portlet preferences for the SAGRID VO
                String sagrid_clustalw_ETOKENSERVER = portletPreferences.getValue("sagrid_clustalw_ETOKENSERVER", "N/A");
                // Get the CLUSTALW MYPROXYSERVER from the portlet preferences for the SAGRID VO
                String sagrid_clustalw_MYPROXYSERVER = portletPreferences.getValue("sagrid_clustalw_MYPROXYSERVER", "N/A");
                // Get the CLUSTALW PORT from the portlet preferences for the SAGRID VO
                String sagrid_clustalw_PORT = portletPreferences.getValue("sagrid_clustalw_PORT", "N/A");
                // Get the CLUSTALW ROBOTID from the portlet preferences for the SAGRID VO
                String sagrid_clustalw_ROBOTID = portletPreferences.getValue("sagrid_clustalw_ROBOTID", "N/A");
                // Get the CLUSTALW ROLE from the portlet preferences for the SAGRID VO
                String sagrid_clustalw_ROLE = portletPreferences.getValue("sagrid_clustalw_ROLE", "N/A");
                // Get the CLUSTALW RENEWAL from the portlet preferences for the SAGRID VO
                String sagrid_clustalw_RENEWAL = portletPreferences.getValue("sagrid_clustalw_RENEWAL", "checked");
                // Get the CLUSTALW DISABLEVOMS from the portlet preferences for the SAGRID VO
                String sagrid_clustalw_DISABLEVOMS = portletPreferences.getValue("sagrid_clustalw_DISABLEVOMS", "unchecked");          
                // Get the random CE for the Clustalw portlet               
                //RANDOM_CE = getRandomCE(sagrid_clustalw_VONAME, sagrid_clustalw_TOPBDII, clustalw_SOFTWARE);
                
                if (clustalw_LOGLEVEL.trim().equals("VERBOSE")) {
                log.info("\n- Getting the CLUSTALW portlet preferences ..."
                + "\n\nsagrid_clustalw_INFRASTRUCTURE: " + sagrid_clustalw_INFRASTRUCTURE
                + "\nsagrid_clustalw_VONAME: " + sagrid_clustalw_VONAME
                + "\nsagrid_clustalw_TOPBDII: " + sagrid_clustalw_TOPBDII                        
                + "\nsagrid_clustalw_ETOKENSERVER: " + sagrid_clustalw_ETOKENSERVER
                + "\nsagrid_clustalw_MYPROXYSERVER: " + sagrid_clustalw_MYPROXYSERVER
                + "\nsagrid_clustalw_PORT: " + sagrid_clustalw_PORT
                + "\nsagrid_clustalw_ROBOTID: " + sagrid_clustalw_ROBOTID
                + "\nsagrid_clustalw_ROLE: " + sagrid_clustalw_ROLE
                + "\nsagrid_clustalw_RENEWAL: " + sagrid_clustalw_RENEWAL
                + "\nsagrid_clustalw_DISABLEVOMS: " + sagrid_clustalw_DISABLEVOMS

                + "\n\nclustalw_ENABLEINFRASTRUCTURE: " + sagrid_clustalw_ENABLEINFRASTRUCTURE
                + "\nclustalw_APPID: " + clustalw_APPID
                + "\nclustalw_LOGLEVEL: " + clustalw_LOGLEVEL                        
                + "\nclustalw_OUTPUT_PATH: " + clustalw_OUTPUT_PATH
                + "\nclustalw_SOFTWARE: " + clustalw_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                }
                
                // Defining the WMS list for the "SAGRID" Infrastructure
                int nmax=0;
                for (int i = 0; i < sagrid_clustalw_WMS.length; i++)
                    if ((sagrid_clustalw_WMS[i]!=null) && (!sagrid_clustalw_WMS[i].equals("N/A"))) nmax++;
                
                String wmsList[] = new String [sagrid_clustalw_WMS.length];
                for (int i = 0; i < sagrid_clustalw_WMS.length; i++)
                {
                    if (sagrid_clustalw_WMS[i]!=null) {
                    wmsList[i]=sagrid_clustalw_WMS[i].trim();
                    log.info ("\n\nSubmitting for SAGRID [" + i + "] using WMS=[" + wmsList[i] + "]");
                    }
                }

                infrastructures[6] = new InfrastructureInfo(
                    sagrid_clustalw_VONAME,
                    sagrid_clustalw_TOPBDII,
                    wmsList,
                    sagrid_clustalw_ETOKENSERVER,
                    sagrid_clustalw_PORT,
                    sagrid_clustalw_ROBOTID,
                    sagrid_clustalw_VONAME,
                    sagrid_clustalw_ROLE,
                    "VO-" + sagrid_clustalw_VONAME + "-" + clustalw_SOFTWARE);
            }

            String[] CLUSTALW_Parameters = new String [30];

            // Upload the input settings for the application
            CLUSTALW_Parameters = uploadClustalwSettings( request, response, username );

            if (clustalw_LOGLEVEL.trim().equals("VERBOSE")) {
            log.info("\n- General Input Parameters: ");
            log.info("\n- ASCII or Text = " + CLUSTALW_Parameters[0]);
            log.info("\n- Clustalw Type = " + CLUSTALW_Parameters[1]);
            log.info("\n- CLUSTALW_CE = " + CLUSTALW_Parameters[2]);
            log.info("\n- Enable Notification = " + CLUSTALW_Parameters[3]);
            log.info("\n- Alignment Type = " + CLUSTALW_Parameters[5]);
            log.info("\n- Description = " + CLUSTALW_Parameters[4]);
            
            // Advanced Settings for PROTEIN (FAST)...
            log.info("\n- Step 2) Settings for Sequence Type = PROTEIN, Alignment Type = FAST: ");
            log.info("\n- KTUP = " + CLUSTALW_Parameters[6]);
            log.info("\n- WINDOW Length = " + CLUSTALW_Parameters[7]);
            log.info("\n- TopDiag = " + CLUSTALW_Parameters[8]);
            log.info("\n- Score = " + CLUSTALW_Parameters[9]);
            log.info("\n- PairGap = " + CLUSTALW_Parameters[10]);
            
            // Advanced Settings for PROTEIN (SLOW)...
            log.info("\n- Step 2) Settings for Sequence Type = PROTEIN, Alignment Type = SLOW: ");
            log.info("\n- Protein Weight Matrix = " + CLUSTALW_Parameters[11]);
            log.info("\n- GapOpen = " + CLUSTALW_Parameters[12]);
            log.info("\n- GapExtension = " + CLUSTALW_Parameters[13]);
            
            // Advanced Settings for DNA (SLOW)...
            log.info("\n- Step 2) Settings for Sequence Type = DNA, Alignment Type = SLOW: ");
            log.info("\n- Dna Weight Matrix = " + CLUSTALW_Parameters[14]);
            log.info("\n- GapOpen = " + CLUSTALW_Parameters[12]);
            log.info("\n- GapExtension = " + CLUSTALW_Parameters[13]);
            
            // Advanced Settings for DNA (FAST)...
            log.info("\n- Step 2) Settings for Sequence Type = DNA, Alignment Type = FAST: ");
            log.info("\n- Dna KTUP = " + CLUSTALW_Parameters[15]);
            log.info("\n- Dna Window Length = " + CLUSTALW_Parameters[16]);
            log.info("\n- Dna TopDiag = " + CLUSTALW_Parameters[17]);
            log.info("\n- Dna Score = " + CLUSTALW_Parameters[9]);
            log.info("\n- Dna PairGap = " + CLUSTALW_Parameters[10]);
            
            // Advanced Settings for PROTEIN (SLOW/FAST)...
            log.info("\n- Step 3) Settings for Sequence Type = PROTEIN, Alignment Type = SLOW/FAST: ");            
            log.info("\n- Matrix = " + CLUSTALW_Parameters[18]);
            log.info("\n- GapOpen = " + CLUSTALW_Parameters[12]);            
            log.info("\n- GapExtension = " + CLUSTALW_Parameters[19]);
            log.info("\n- NoEndGaps = " + CLUSTALW_Parameters[20]);
            log.info("\n- Iteration = " + CLUSTALW_Parameters[21]);            
            log.info("\n- Clustering = " + CLUSTALW_Parameters[22]);
            log.info("\n- GapDistances = " + CLUSTALW_Parameters[23]);
            log.info("\n- Numiter = " + CLUSTALW_Parameters[24]);
            log.info("\n- Output = " + CLUSTALW_Parameters[25]);
            log.info("\n- OutOrder = " + CLUSTALW_Parameters[26]);
            
            // Advanced Settings for DNA (SLOW/FAST)...
            log.info("\n- Step 3) Settings for Sequence Type = DNA, Alignment Type = SLOW/FAST: ");            
            log.info("\n- DNA Matrix = " + CLUSTALW_Parameters[27]);
            log.info("\n- Gap Open = " + CLUSTALW_Parameters[12]);
            log.info("\n- GapExtension = " + CLUSTALW_Parameters[13]);
            
            log.info("\n- NoEndGaps = " + CLUSTALW_Parameters[20]);
            log.info("\n- Iteration = " + CLUSTALW_Parameters[21]);
            log.info("\n- Clustering = " + CLUSTALW_Parameters[22]);
            log.info("\n- DNA GapDistances = " + CLUSTALW_Parameters[28]);
            log.info("\n- DNA Numiter = " + CLUSTALW_Parameters[29]);
            log.info("\n- Output = " + CLUSTALW_Parameters[25]);
            log.info("\n- OutOrder = " + CLUSTALW_Parameters[26]); 
            }
            
            // Preparing to submit CLUSTALW jobs in different grid infrastructure..
            //=============================================================
            // IMPORTANT: INSTANCIATE THE MultiInfrastructureJobSubmission
            //            CLASS USING THE EMPTY CONSTRUCTOR WHEN
            //            WHEN THE PORTLET IS DEPLOYED IN PRODUCTION!!!
            //=============================================================
            /*MultiInfrastructureJobSubmission ClustalwMultiJobSubmission =
            new MultiInfrastructureJobSubmission(TRACKING_DB_HOSTNAME,
                                                 TRACKING_DB_USERNAME,
                                                 TRACKING_DB_PASSWORD);*/
            
            MultiInfrastructureJobSubmission ClustalwMultiJobSubmission =
                new MultiInfrastructureJobSubmission();

            // Set the list of infrastructure(s) activated for the CLUSTALW portlet           
            if (infrastructures[0]!=null) {
                if (clustalw_LOGLEVEL.trim().equals("VERBOSE"))
                log.info("\n- Adding the COMETA Infrastructure.");
                ClustalwMultiJobSubmission.addInfrastructure(infrastructures[0]); 
            }
            if (infrastructures[1]!=null) {
                if (clustalw_LOGLEVEL.trim().equals("VERBOSE"))
                log.info("\n- Adding the GARUDA Infrastructure.");
                ClustalwMultiJobSubmission.addInfrastructure(infrastructures[1]); 
            }
            if (infrastructures[2]!=null) {
                if (clustalw_LOGLEVEL.trim().equals("VERBOSE"))
                log.info("\n- Adding the GRIDIT Infrastructure.");
                ClustalwMultiJobSubmission.addInfrastructure(infrastructures[2]); 
            }
            if (infrastructures[3]!=null) {
                if (clustalw_LOGLEVEL.trim().equals("VERBOSE"))
                log.info("\n- Adding the GILDA Infrastructure.");
                ClustalwMultiJobSubmission.addInfrastructure(infrastructures[3]);
            }
            if (infrastructures[4]!=null) {
                if (clustalw_LOGLEVEL.trim().equals("VERBOSE"))
                log.info("\n- Adding the EUMED Infrastructure.");
                ClustalwMultiJobSubmission.addInfrastructure(infrastructures[4]);
            }
            if (infrastructures[5]!=null) {
                if (clustalw_LOGLEVEL.trim().equals("VERBOSE"))
                log.info("\n- Adding the GISELA Infrastructure.");
                ClustalwMultiJobSubmission.addInfrastructure(infrastructures[5]);
            }
            if (infrastructures[6]!=null) {
                if (clustalw_LOGLEVEL.trim().equals("VERBOSE"))
                log.info("\n- Adding the SAGRID Infrastructure.");
                ClustalwMultiJobSubmission.addInfrastructure(infrastructures[6]);
            }

            String ClustalwFilesPath = getPortletContext().getRealPath("/") +
                                    "WEB-INF/config"; 
            
            InfrastructureInfo infrastructure = 
                    ClustalwMultiJobSubmission.getInfrastructure();
            
            // Set the Output path forresults
            ClustalwMultiJobSubmission.setOutputPath(clustalw_OUTPUT_PATH);

            // Set the StandardOutput for CLUSTALW
            ClustalwMultiJobSubmission.setJobOutput("std.out");

            // Set the StandardError for CLUSTALW
            ClustalwMultiJobSubmission.setJobError("std.err");
            
            // Set the Executable for CLUSTALW
            ClustalwMultiJobSubmission.setExecutable("start_clustalW2.sh");
            
            /* Set INPUTFILE name and TYPE ( protein, dna ) */
            /*String tmpArgs = "-INFILE=" 
                             + CLUSTALW_Parameters[0] 
                             + " -TYPE="
                             + CLUSTALW_Parameters[1]
                             + " ";*/
            
            String tmpArgs = " -TYPE=" + CLUSTALW_Parameters[1] + " ";

            // A.) ALIGNMENT TYPE = [ SLOW ]
            if (CLUSTALW_Parameters[5].equals("slow")) 
            {
                if (clustalw_LOGLEVEL.trim().equals("VERBOSE"))
                log.info ("\n\nAlignment Type selected [ SLOW ] ");
                                
                if (CLUSTALW_Parameters[1].equals("protein")) 
                {                                        
                    tmpArgs += "-PWMATRIX=" + CLUSTALW_Parameters[11] + " ";                                        
                    tmpArgs += "-MATRIX=" + CLUSTALW_Parameters[18] + " ";
                }
                
                else if (CLUSTALW_Parameters[1].equals("dna"))
                {
                    tmpArgs += "-PWDNAMATRIX=" + CLUSTALW_Parameters[14] + " ";                                        
                    tmpArgs += "-DNAMATRIX=" + CLUSTALW_Parameters[27] + " ";
                }
                                
                tmpArgs += "-PWGAPOPEN=" + CLUSTALW_Parameters[12] + " ";                          
                tmpArgs += "-PWGAPEXT=" + CLUSTALW_Parameters[13] + " ";
            }
                        
            if (CLUSTALW_Parameters[5].equals("fast")) 
            {
                if (clustalw_LOGLEVEL.trim().equals("VERBOSE"))
                log.info ("\n\nAlignment Type selected [ FAST ] ");
                                
                if (CLUSTALW_Parameters[1].equals("protein")) 
                {                                    
                    tmpArgs += "-KTUP=" + CLUSTALW_Parameters[6] + " ";                                    
                    tmpArgs += "-WINDOW=" + CLUSTALW_Parameters[7] + " ";                                        
                    tmpArgs += "-TOPDIAGS=" + CLUSTALW_Parameters[8] + " ";
                } 
                
                else if (CLUSTALW_Parameters[1].equals("dna")) 
                {                    
                    tmpArgs += "-KTUP=" + CLUSTALW_Parameters[15] + " ";                                    
                    tmpArgs += "-WINDOW=" + CLUSTALW_Parameters[16] + " ";                                        
                    tmpArgs += "-TOPDIAGS=" + CLUSTALW_Parameters[17] + " ";
                }
                                
                tmpArgs += "-SCORE=" + CLUSTALW_Parameters[9] + " ";                                
                tmpArgs += "-PAIRGAP=" + CLUSTALW_Parameters[10] + " ";
                                
                if (CLUSTALW_Parameters[1].equals("protein"))                 
                    tmpArgs += "-PWMATRIX=" + CLUSTALW_Parameters[18] + " ";                
                                
                else if (CLUSTALW_Parameters[1].equals("dna"))                                
                    tmpArgs += "-PWDNAMATRIX=" + CLUSTALW_Parameters[27] + " ";                
            }
            
            /* Multiple Alignments */                        
            tmpArgs += "-GAPOPEN=" + CLUSTALW_Parameters[12] + " ";                        
            tmpArgs += "-GAPEXT=" + CLUSTALW_Parameters[19] + " ";
                        
            if (CLUSTALW_Parameters[1].equals("protein")) 
                tmpArgs += "-GAPDIST=" + CLUSTALW_Parameters[23] + " ";            
                        
            else if (CLUSTALW_Parameters[1].equals("dna"))            
                tmpArgs += "-GAPDIST=" + CLUSTALW_Parameters[28] + " ";
                        
            tmpArgs += "-ENDGAPS=" + CLUSTALW_Parameters[20] + " ";                        
            tmpArgs += "-ITERATION=" + CLUSTALW_Parameters[21] + " ";
                        
            if (CLUSTALW_Parameters[1].equals("protein"))               
                tmpArgs += "-NUMITER=" + CLUSTALW_Parameters[24] + " ";
           
            else if (CLUSTALW_Parameters[1].equals("dna"))
                tmpArgs += "-NUMITER=" + CLUSTALW_Parameters[29] + " ";            
            
            tmpArgs += "-CLUSTERING=" + CLUSTALW_Parameters[22] + " ";                        
            tmpArgs += "-OUTPUT=" + CLUSTALW_Parameters[25] + " ";                        
            tmpArgs += "-OUTORDER=" + CLUSTALW_Parameters[26] + " ";            
            tmpArgs += "-ALIGN ";
             
            log.info("\n\n- ARGS = " + tmpArgs);
                
            // Set the Argument for CLUSTALW
            String Arguments = CLUSTALW_Parameters[0] + "," + tmpArgs;            
                
            // Set the list of Arguments for CLUSTALW
            ClustalwMultiJobSubmission.setArguments(Arguments);
                
            String InputSandbox = ClustalwFilesPath + "/start_clustalW2.sh" +
                                  "," +
                                  ClustalwFilesPath + "/clustalw2" +
                                  "," +
                                  CLUSTALW_Parameters[0];

            // Set InputSandbox files (string with comma separated list of file names)
            ClustalwMultiJobSubmission.setInputFiles(InputSandbox);                                

            // OutputSandbox (string with comma separated list of file names)                
            String ClustalwFiles="outputs.tar.gz";

            // Set the OutputSandbox files (string with comma separated list of file names)
            ClustalwMultiJobSubmission.setOutputFiles(ClustalwFiles 
                                                      + "," 
                                                      + "output.README");            
            
            // Set the queue if it's defined
            // This option is not supported in multi-infrastructures mode
            if (NMAX==1) {
                if (!CLUSTALW_Parameters[2].isEmpty())
                    ClustalwMultiJobSubmission.setJobQueue(CLUSTALW_Parameters[2]);
                //else //ClustalwMultiJobSubmission.setRandomCE(true);
                  //  ClustalwMultiJobSubmission.setJobQueue(RANDOM_CE);
            }
            
            InetAddress addr = InetAddress.getLocalHost();           
            Company company;
            
            try {
                company = PortalUtil.getCompany(request);
                String gateway = company.getName();
                
                // Send a notification email to the user if enabled.
                if (CLUSTALW_Parameters[3]!=null)
                    if ( (SMTP_HOST==null) || 
                         (SMTP_HOST.trim().equals("")) ||
                         (SMTP_HOST.trim().equals("N/A")) ||
                         (SENDER_MAIL==null) || 
                         (SENDER_MAIL.trim().equals("")) ||
                         (SENDER_MAIL.trim().equals("N/A"))
                       )
                    log.info ("\nThe Notification Service is not properly configured!!");
                    
                 else {
                        // Enabling Job's notification via email
                        ClustalwMultiJobSubmission.setUserEmail(emailAddress);
                        
                        sendHTMLEmail(username, 
                                      emailAddress, 
                                      SENDER_MAIL, 
                                      SMTP_HOST, 
                                      "CLUSTALW", 
                                      gateway);
                }
                
                // Submitting...
                log.info("\n- ClustalW job submittion in progress using JSAGA JobEngine");
                ClustalwMultiJobSubmission.submitJobAsync(
                    infrastructure,
                    username,
                    addr.getHostAddress()+":8162",
                    Integer.valueOf(clustalw_APPID),
                    CLUSTALW_Parameters[4]);
                
            } catch (PortalException ex) {
                Logger.getLogger(Clustalw.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SystemException ex) {
                Logger.getLogger(Clustalw.class.getName()).log(Level.SEVERE, null, ex);
            }                        
        } // end PROCESS ACTION [ SUBMIT_CLUSTALW_PORTLET ]
    }

    @Override
    public void serveResource(ResourceRequest request, ResourceResponse response)
                throws PortletException, IOException
    {
        //super.serveResource(request, response);

        PortletPreferences portletPreferences = (PortletPreferences) request.getPreferences();

        final String action = (String) request.getParameter("action");

        if (action.equals("get-ratings")) {
            //Get CE Ratings from the portlet preferences
            String clustalw_CE = (String) request.getParameter("clustalw_CE");

            String json = "{ \"avg\":\"" + 
        	          portletPreferences.getValue(clustalw_CE+"_avg", "0.0") +
                    	  "\", \"cnt\":\"" + 
			  portletPreferences.getValue(clustalw_CE+"_cnt", "0") + "\"}";

            response.setContentType("application/json");
            response.getPortletOutputStream().write( json.getBytes() );

        } else if (action.equals("set-ratings")) {

            String clustalw_CE = (String) request.getParameter("clustalw_CE");
            int vote = Integer.parseInt(request.getParameter("vote"));

             double avg = Double.parseDouble(portletPreferences.getValue(clustalw_CE+"_avg", "0.0"));
             long cnt = Long.parseLong(portletPreferences.getValue(clustalw_CE+"_cnt", "0"));

             portletPreferences.setValue(clustalw_CE+"_avg", Double.toString(((avg*cnt)+vote) / (cnt +1)));
             portletPreferences.setValue(clustalw_CE+"_cnt", Long.toString(cnt+1));

             portletPreferences.store();
        }
    }


    // Upload CLUSTALW input files
    public String[] uploadClustalwSettings(ActionRequest actionRequest,
                                        ActionResponse actionResponse, String username)
    {
        String[] CLUSTALW_Parameters = new String [30];
        boolean status;

        // Check that we have a file upload request
        boolean isMultipart = PortletFileUpload.isMultipartContent(actionRequest);

        if (isMultipart) {
            // Create a factory for disk-based file items.
            DiskFileItemFactory factory = new DiskFileItemFactory();

            // Set factory constrains
            File CLUSTALW_Repository = new File ("/tmp");
            if (!CLUSTALW_Repository.exists()) status = CLUSTALW_Repository.mkdirs();
            factory.setRepository(CLUSTALW_Repository);

            // Create a new file upload handler.
            PortletFileUpload upload = new PortletFileUpload(factory);

            try {
                    // Parse the request
                    List items = upload.parseRequest(actionRequest);

                    // Processing items
                    Iterator iter = items.iterator();

                    while (iter.hasNext())
                    {
                        FileItem item = (FileItem) iter.next();

                        String fieldName = item.getFieldName();
                        
                        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                        String timeStamp = dateFormat.format(Calendar.getInstance().getTime());

                        // Processing a regular form field
                        if ( item.isFormField() )
                        {                                                        
                            if (fieldName.equals("clustalw_textarea")) 
                            {
                                CLUSTALW_Parameters[0]=
                                        CLUSTALW_Repository +
                                        "/" + timeStamp +
                                        "_" + username +
                                        ".txt";
                            
                                // Store the textarea in a ASCII file
                                storeString(CLUSTALW_Parameters[0], 
                                            item.getString());                               
                            }
                            
                            if (fieldName.equals("clustalw_type"))
                                CLUSTALW_Parameters[1]=item.getString();
                                                        
                            if (fieldName.equals("clustalw_CE"))
                                CLUSTALW_Parameters[2]=item.getString();                                                       
                            
                        } else {
                            // Processing a file upload
                            if (fieldName.equals("clustalw_file"))
                            {                                                               
                                log.info("\n- Uploading the following user's file: "
                                       + "\n[ " + item.getName() + " ]"
                                       + "\n[ " + item.getContentType() + " ]"
                                       + "\n[ " + item.getSize() + "KBytes ]"
                                       );                               

                                // Writing the file to disk
                                String uploadClustalwFile = 
                                        CLUSTALW_Repository +
                                        "/" + timeStamp +
                                        "_" + username +
                                        "_" + item.getName();

                                log.info("\n- Writing the user's file: [ "
                                        + uploadClustalwFile.toString()
                                        + " ] to disk");

                                item.write(new File(uploadClustalwFile)); 
                                
                                CLUSTALW_Parameters[0]=uploadClustalwFile;                                
                            }
                        }
                        
                        if (fieldName.equals("EnableNotification"))
                                CLUSTALW_Parameters[3]=item.getString(); 
                        
                        if (fieldName.equals("clustalw_desc"))                                
                                if (item.getString().equals("Please, insert here a description for your job"))
                                    CLUSTALW_Parameters[4]="Sequence Alignment Simulation Started";
                                else
                                    CLUSTALW_Parameters[4]=item.getString();
                        
                        // Advanced Settings here..
                        if (fieldName.equals("clustalw_alignmenttype"))
                                CLUSTALW_Parameters[5]=item.getString(); 
                                                
                        if (fieldName.equals("clustalw_protein_ktup"))
                                CLUSTALW_Parameters[6]=item.getString();
                        
                        if (fieldName.equals("clustalw_protein_window"))
                                CLUSTALW_Parameters[7]=item.getString();
                        
                        if (fieldName.equals("clustalw_protein_topdiag"))
                                CLUSTALW_Parameters[8]=item.getString();
                        
                        if (fieldName.equals("clustalw_score"))
                                CLUSTALW_Parameters[9]=item.getString();
                        
                        if (fieldName.equals("clustalw_pairgap"))
                                CLUSTALW_Parameters[10]=item.getString();
                        
                        if (fieldName.equals("clustalw_pwmatrix"))
                                CLUSTALW_Parameters[11]=item.getString();
                        
                        if (fieldName.equals("clustalw_pwgapopen"))
                                CLUSTALW_Parameters[12]=item.getString();
                        
                        if (fieldName.equals("clustalw_pwgapext"))
                                CLUSTALW_Parameters[13]=item.getString();
                        
                        if (fieldName.equals("clustalw_pwdnamatrix"))
                                CLUSTALW_Parameters[14]=item.getString();
                        
                        if (fieldName.equals("clustalw_dna_ktup"))
                                CLUSTALW_Parameters[15]=item.getString();
                        
                        if (fieldName.equals("clustalw_dna_windowlength"))
                                CLUSTALW_Parameters[16]=item.getString();
                        
                        if (fieldName.equals("clustalw_dna_topdiag"))
                                CLUSTALW_Parameters[17]=item.getString();
                        
                        if (fieldName.equals("clustalw_matrix"))
                                CLUSTALW_Parameters[18]=item.getString();
                        
                        if (fieldName.equals("clustalw_gapext"))
                                CLUSTALW_Parameters[19]=item.getString();
                        
                        if (fieldName.equals("clustalw_noendgaps"))
                                CLUSTALW_Parameters[20]=item.getString();
                        
                        if (fieldName.equals("clustalw_iteration"))
                                CLUSTALW_Parameters[21]=item.getString();
                        
                        if (fieldName.equals("clustalw_clustering"))
                                CLUSTALW_Parameters[22]=item.getString();
                        
                        if (fieldName.equals("clustalw_gapdistances"))
                                CLUSTALW_Parameters[23]=item.getString();
                        
                        if (fieldName.equals("clustalw_numiter"))
                                CLUSTALW_Parameters[24]=item.getString();
                        
                        if (fieldName.equals("clustalw_output"))
                                CLUSTALW_Parameters[25]=item.getString();
                        
                        if (fieldName.equals("clustalw_outorder"))
                                CLUSTALW_Parameters[26]=item.getString();
                        
                        if (fieldName.equals("clustalw_dnamatrix"))
                                CLUSTALW_Parameters[27]=item.getString();
                        
                        if (fieldName.equals("clustalw-dnagapdistances"))
                                CLUSTALW_Parameters[28]=item.getString();
                        
                        if (fieldName.equals("clustalw_dnanumiter"))
                                CLUSTALW_Parameters[29]=item.getString();
                                                
                    } // end while
            } catch (FileUploadException ex) {
              Logger.getLogger(Clustalw.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
              Logger.getLogger(Clustalw.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return CLUSTALW_Parameters;
    }
    
    // Retrieve a random Computing Element
    // matching the Software Tag for the CLUSTALW application    
    public String getRandomCE(String clustalw_VONAME,
                              String clustalw_TOPBDII,
                              String clustalw_SOFTWARE)
                              throws PortletException, IOException
    {
        String randomCE = null;
        BDII bdii = null;    
        List<String> CEqueues = null;
                        
        log.info("\n- Querying the Information System [ " + 
                  clustalw_TOPBDII + 
                  " ] and retrieving a random CE matching the SW tag [ VO-" + 
                  clustalw_VONAME +
                  "-" +
                  clustalw_SOFTWARE + " ]");  

       try {               

                bdii = new BDII( new URI(clustalw_TOPBDII) );
                
                // Get the list of the available queues
                CEqueues = bdii.queryCEQueues(clustalw_VONAME);
                
                // Get the list of the Computing Elements for the given SW tag
                randomCE = bdii.getRandomCEForSWTag("VO-" + 
                                              clustalw_VONAME + 
                                              "-" +
                                              clustalw_SOFTWARE);
                
                // Fetching the Queues
                for (String CEqueue:CEqueues) {
                    if (CEqueue.contains(randomCE))
                        randomCE=CEqueue;
                }

        } catch (URISyntaxException ex) {
                Logger.getLogger(Clustalw.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
                Logger.getLogger(Clustalw.class.getName()).log(Level.SEVERE, null, ex);
        }                   

        return randomCE;
    }

    // Retrieve the list of Computing Elements
    // matching the Software Tag for the CLUSTALW application    
    public List<String> getListofCEForSoftwareTag(String clustalw_VONAME,
                                                  String clustalw_TOPBDII,
                                                  String clustalw_SOFTWARE)
                                throws PortletException, IOException
    {
        List<String> CEs_list = null;
        BDII bdii = null;        
        
        log.info("\n- Querying the Information System [ " + 
                  clustalw_TOPBDII + 
                  " ] and looking for CEs matching the SW tag [ VO-" + 
                  clustalw_VONAME +
                  "-" +
                  clustalw_SOFTWARE + " ]");  

       try {               
           
                bdii = new BDII( new URI(clustalw_TOPBDII) );                
                
                if (!clustalw_SOFTWARE.trim().isEmpty())  
                    
                    CEs_list = bdii.queryCEForSWTag("VO-" +
                                                    clustalw_VONAME +
                                                    "-" +
                                                    clustalw_SOFTWARE);                
                //else
                //    CEs_list = bdii.getGlueCEInfoHostNames(clustalw_VONAME);
                
                // Fetching the CE hostnames
                for (String CE:CEs_list) 
                    log.info("\n- CE host found = " + CE);

        } catch (URISyntaxException ex) {
                Logger.getLogger(Clustalw.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
                Logger.getLogger(Clustalw.class.getName()).log(Level.SEVERE, null, ex);
        }

        return CEs_list;
    }

    // Get the GPS location of the given grid resource
    public String[] getCECoordinate(RenderRequest request,
                                    String CE)
                                    throws PortletException, IOException
    {
        String[] GPS_locations = null;
        BDII bdii = null;

        PortletPreferences portletPreferences =
                (PortletPreferences) request.getPreferences();

        // Get the CLUSTALW TOPPBDII from the portlet preferences
        String cometa_clustalw_TOPBDII = 
                portletPreferences.getValue("cometa_clustalw_TOPBDII", "N/A");
        String gridit_clustalw_TOPBDII = 
                portletPreferences.getValue("gridit_clustalw_TOPBDII", "N/A");
        String gilda_clustalw_TOPBDII = 
                portletPreferences.getValue("gilda_clustalw_TOPBDII", "N/A");
        String eumed_clustalw_TOPBDII = 
                portletPreferences.getValue("eumed_clustalw_TOPBDII", "N/A");
        String gisela_clustalw_TOPBDII = 
                portletPreferences.getValue("gisela_clustalw_TOPBDII", "N/A");
        String sagrid_clustalw_TOPBDII = 
                portletPreferences.getValue("sagrid_clustalw_TOPBDII", "N/A");
        
        // Get the CLUSTALW ENABLEINFRASTRUCTURE from the portlet preferences
        String clustalw_ENABLEINFRASTRUCTURE = 
                portletPreferences.getValue("clustalw_ENABLEINFRASTRUCTURE", "N/A");

            try {
                if ( clustalw_ENABLEINFRASTRUCTURE.equals("cometa") )
                     bdii = new BDII( new URI(cometa_clustalw_TOPBDII) );
                
                if ( clustalw_ENABLEINFRASTRUCTURE.equals("gridit") )
                     bdii = new BDII( new URI(gridit_clustalw_TOPBDII) );
                
                if ( clustalw_ENABLEINFRASTRUCTURE.equals("gilda") )
                     bdii = new BDII( new URI(gilda_clustalw_TOPBDII) );                

                if ( clustalw_ENABLEINFRASTRUCTURE.equals("eumed") )
                     bdii = new BDII( new URI(eumed_clustalw_TOPBDII) );

                if ( clustalw_ENABLEINFRASTRUCTURE.equals("gisela") )
                    bdii = new BDII( new URI(gisela_clustalw_TOPBDII) );
                
                if ( clustalw_ENABLEINFRASTRUCTURE.equals("sagrid") )
                    bdii = new BDII( new URI(sagrid_clustalw_TOPBDII) );

                GPS_locations = bdii.queryCECoordinate("ldap://" + CE + ":2170");

            } catch (URISyntaxException ex) {
                Logger.getLogger(Clustalw.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Clustalw.class.getName()).log(Level.SEVERE, null, ex);
            }

            return GPS_locations;
    }
    
    private void storeString (String fileName, String fileContent) 
                              throws IOException 
    { 
        log.info("\n- Writing textarea in a ASCII file [ " + fileName + " ]");
        // Removing the Carriage Return (^M) from text
        String pattern = "[\r]";
        String stripped = fileContent.replaceAll(pattern, "");        
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));        
        writer.write(stripped);
        writer.write("\n");
        writer.close();        
    }
    
    private void sendHTMLEmail (String USERNAME,
                                String TO, 
                                String FROM, 
                                String SMTP_HOST, 
                                String ApplicationAcronym,
                                String GATEWAY)
    {
                
        log.info("\n- Sending email notification to the user " + USERNAME + " [ " + TO + " ]");
        
        log.info("\n- SMTP Server = " + SMTP_HOST);
        log.info("\n- Sender = " + FROM);
        log.info("\n- Receiver = " + TO);
        log.info("\n- Application = " + ApplicationAcronym);
        log.info("\n- Gateway = " + GATEWAY);        
        
        // Assuming you are sending email from localhost
        String HOST = "localhost";
        
        // Get system properties
        Properties properties = System.getProperties();
        properties.setProperty(SMTP_HOST, HOST);
        
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);
        
        try {
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(FROM));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(TO));
         //message.addRecipient(Message.RecipientType.CC, new InternetAddress(FROM));

         // Set Subject: header field
         message.setSubject(" [liferay-sg-gateway] - [ " + GATEWAY + " ] ");

	 Date currentDate = new Date();
	 currentDate.setTime (currentDate.getTime());

         // Send the actual HTML message, as big as you like
         message.setContent(
	 "<br/><H4>" +         
	 "<img src=\"http://fbcdn-profile-a.akamaihd.net/hprofile-ak-snc6/195775_220075701389624_155250493_n.jpg\" width=\"100\">Science Gateway Notification" +
	 "</H4><hr><br/>" +
         "<b>Description:</b> Notification for the application <b>[ " + ApplicationAcronym + " ]</b><br/><br/>" +         
         "<i>The application has been successfully submitted from the [ " + GATEWAY + " ]</i><br/><br/>" +
         "<b>TimeStamp:</b> " + currentDate + "<br/><br/>" +
	 "<b>Disclaimer:</b><br/>" +
	 "<i>This is an automatic message sent by the Science Gateway based on Liferay technology.<br/>" + 
	 "If you did not submit any jobs through the Science Gateway, please " +
         "<a href=\"mailto:" + FROM + "\">contact us</a></i>",         
	 "text/html");

         // Send message
         Transport.send(message);         
      } catch (MessagingException mex) { mex.printStackTrace(); }        
    }
}
