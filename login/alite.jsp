<%@ page import="jp.co.ais.trans.common.util.*" %>
<%@ page import="java.net.*" %>
<%
    // prevents caching at the proxy server
    response.setDateHeader ("Expires", 0);

    // Getting the URL parameters from the request
    String companyCode = Util.avoidNull(request.getParameter("company"));
    String userCode = Util.avoidNull(request.getParameter("user"));
    String pw = Util.avoidNull(request.getParameter("pw"));
    String ld = Util.avoidNull(request.getParameter("ld"));
    String[] prgArr = StringUtil.splitLimit(ld, ",", 2);
    
    String itemName = "";
    if (Util.isNullOrEmpty(companyCode)) {
        itemName = "[company]";
    } else if (Util.isNullOrEmpty(userCode)) {
        itemName = "[user]";
    }else if (Util.isNullOrEmpty(pw)) {
        itemName = "[password]";
    }
    
    String token = "";
    if (Util.isNullOrEmpty(itemName)) {
        // token”z—ñì¬
        String[] arr = new String[] { companyCode, userCode, pw };
        token = UTF8EncryptUtil.getToken(arr);
    }

    String url = "http://192.168.1.135:8010/trans2_2e/";
    
    String[] archives = new String[] {
         "trans-commons2.2_c.jar"
        ,"ais-lookandfeel2.2_c.jar"
        ,"trans-single-start_c.jar"
        ,"trans-account-gl2.2_c.jar"
        ,"trans-account-ap2.2_c.jar"
        ,"trans-account-ep2.2_c.jar"
        ,"trans-account-ar2.2_c.jar"
        ,"trans-account-lm2.2_c.jar"
        ,"trans-account-pc2.2_c.jar"
        ,"trans-account-fa2.2_c.jar"
        ,"trans-account-bg2.2_c.jar"
        ,"trans-account-tj1.0_c.jar"
        ,"trans-tc2.2_c.jar"
        ,"trans-owner2.2_c.jar"
        ,"trans-pe2.2_c.jar"
        ,"trans-bm2.2_c.jar"
        ,"trans-sc2.2_c.jar"
        ,"trans-account-vs_c.jar"
        ,"trans-account-ap_c.jar"
        ,"trans-account-ar_c.jar"
        ,"trans-account-gl_c.jar"
        ,"trans-account-pc_c.jar"
        ,"trans-agency-hb2.2_c.jar"
        ,"commons-codec-1.9.jar"
        ,"commons-fileupload-1.2.jar"
        ,"commons-httpclient-3.0.1.jar"
        ,"commons-lang-2.0.jar"
        ,"commons-logging-1.1.3.jar"
        ,"jcchart.jar"
        ,"jcelements.jar"
        ,"jcfield.jar"
        ,"jcgauge.jar"
        ,"jcjarmaster.jar"
        ,"jcpagelayout.jar"
        ,"jctable.jar"
        ,"log4j-1.2.17.jar"
        ,"s2-framework-2.3.23.jar"
        ,"swing-layout-1.0.jar"
        ,"servlet-api.jar"    
    };
    
    StringBuilder archiveBuff = new StringBuilder();
    for (String str : archives) {
        if (archiveBuff.length() > 0) {
            archiveBuff.append(",");
        }
        archiveBuff.append(str);
    }
    
    int width = 1024;
    int height = 768;
    
%>

<% if (!Util.isNullOrEmpty(token)) { %>
<script type="text/javascript"  src="deployJava.js"></script>
<script type="text/javascript">
    var attributes = {
        code:'jp.co.ais.trans2.common.ui.TAppletUI'
       ,archive:'<%=archiveBuff.toString()%>'
       ,width:<%=width%>
       ,height:<%=height%>
    };
    var parameters = {
        token:'<%=token%>'
       ,trans_program_code:'<%=prgArr[0]%>'
       ,trans_program_load_name:'<%=prgArr[1]%>'
       ,trans_url:'<%=url%>'
       ,Permissions:'all-permissions'
    };
    var version = '1.6';
    deployJava.runApplet(attributes, parameters, version);
</script>
<% } else { %>
Required Item <%=itemName%> has not been set up.
<% } %>