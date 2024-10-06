<%@ page import="jp.co.ais.trans.common.util.*" %>
<%@ page import="java.net.*" %>
<%
    // prevents caching at the proxy server
    response.setDateHeader ("Expires", 0);

    // Getting the URL parameters from the request
    String companyCode = Util.avoidNull(request.getParameter("company"));
    String userCode = Util.avoidNull(request.getParameter("user"));
    String pw = Util.avoidNull(request.getParameter("pw"));
    
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
%>
<% if (!Util.isNullOrEmpty(token)) { %>
<script type="text/javascript"  src="deployJava.js"></script>
<script type="text/javascript">
    var url = "start.jsp?token=<%=token%>";
    deployJava.launchWebStartApplication(url);
</script>
<% } else { %>
Required Item <%=itemName%> has not been set up.
<% } %>