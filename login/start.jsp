<%@ page contentType="application/x-java-jnlp-file" %>
<%@ page import="jp.co.ais.trans.common.config.*" %>
<%@ page import="jp.co.ais.trans.common.util.*" %>
<%

    // prevents caching at the proxy server
    response.setDateHeader ("Expires", 0);
    
    String tokenString = Util.avoidNullNT(request.getParameter("token"));
    
%> 
<?xml version="1.0" encoding="UTF-8"?>
<jnlp 
  spec="1.5+" 
  version="2.21"
  codebase="http://192.168.1.135:8010/trans2_2e"
  href="login/start.jsp?token=<%=tokenString%>">

  <information> 
    <title>TRANS2.2</title> 
    <vendor>ais</vendor>

    <homepage href="http://www.a-i-s.co.jp/"/>
    <description>TRANS2.2 Application</description>
    <description kind="short">TRANS2.2</description>
    <icon href="login/images/logo_e.png"/><!--ICON-->
    
    <offline-allowed/> 
    
    <shortcut online="true">
      <desktop/>
      <menu submenu="AIS"/>
    </shortcut>
    
  </information>

  <security>
    <all-permissions />
  </security>

  <resources>
    <java version="1.6.0+"
          href="http://java.sun.com/products/autodl/j2se"
          max-heap-size="512m"
    />
    <property name="jnlp.p.trans.version" value="2.21"/>
    <property name="jnlp.p.trans.login.default.prev" value="true"/>
    <property name="jnlp.p.trans.slip.use.bs" value="true"/>
    <property name="jnlp.p.trans.slip.group.accounting" value="true"/>
    <property name="jnlp.p.applet.width" value="1280"/>
    <property name="jnlp.p.applet.height" value="768"/>
    <property name="jnlp.p.trans.ref.table.focusable" value="true"/>
    <property name="jnlp.p.trans.slip.detail.change.company.no.clear" value="true"/>
    <property name="jnlp.p.trans.slip.bs.use.kcompany" value="true"/>
    <property name="jnlp.p.trans.TC0010.owner.cooperation" value="true"/>
    <property name="jnlp.p.favicon.file.name" value="logo_e.png"/>
    <property name="jnlp.p.trans.textfield.focus.highlight" value="true"/>
    <property name="jnlp.p.trans.slip.use.attachment" value="true"/>
    <property name="jnlp.p.trans.slip.use.attachment.max.size" value="4"/>
    <property name="jnlp.p.trans.slip.splitpane" value="true"/>
    <property name="jnlp.p.trans.slip.use.approve.history" value="true"/>
    <property name="jnlp.p.login.background.image" value="sunshine_trans.png"/>
    <property name="jnlp.p.trans.login.show.title" value="true"/>
    <property name="jnlp.p.login.title.fontcolor" value="0,0,0"/>
    <property name="jnlp.p.login.label.forecolor" value="0,0,0"/>
    <property name="jnlp.p.login.input.start.x" value="175"/>
    <property name="jnlp.p.login.input.start.y" value="115"/>
    <property name="jnlp.p.login.button.start.x" value="135"/>
    <property name="jnlp.p.login.button.start.y" value="205"/>
    <property name="jnlp.p.trans.slip.drilldown.use.group.company" value="true"/>
    <property name="jnlp.p.trans.excel.xml" value="true"/>
    <property name="jnlp.p.trans.show.used.request.memory" value="true"/>
    <property name="jnlp.p.trans.show.used.request.memory.min.MB" value="1"/>
    <property name="jnlp.p.trans.shipcost.use.attachment" value="true"/>
    <property name="jnlp.p.trans.GL0460.use.BG" value="true"/>
    <property name="jnlp.p.trans.single.login.token" value="<%=tokenString%>"/>

    <jar href="login/ais-lookandfeel2.2_c.jar"/>
    <jar href="login/trans-commons2.2_c.jar" main="true"/>
    <jar href="login/trans-account-gl2.2_c.jar"/>
    <jar href="login/trans-account-ap2.2_c.jar"/>
    <jar href="login/trans-account-ep2.2_c.jar"/>
    <jar href="login/trans-account-ar2.2_c.jar"/>
    <jar href="login/trans-account-lm2.2_c.jar"/>
    <jar href="login/trans-account-pc2.2_c.jar"/>
    <jar href="login/trans-account-fa2.2_c.jar"/>
    <jar href="login/trans-account-bg2.2_c.jar"/>
    <jar href="login/trans-account-tj1.0_c.jar"/>
    <jar href="login/trans-tc2.2_c.jar"/>
    <jar href="login/trans-owner2.2_c.jar"/>
    <jar href="login/trans-pe2.2_c.jar"/>
    <jar href="login/trans-bm2.2_c.jar"/>
    <jar href="login/trans-sc2.2_c.jar"/>
    <jar href="login/trans-account-vs_c.jar"/>
    <jar href="login/trans-account-ap_c.jar"/>
    <jar href="login/trans-account-ar_c.jar"/>
    <jar href="login/trans-account-gl_c.jar"/>
    <jar href="login/trans-account-pc_c.jar"/>
    <jar href="login/trans-agency-hb2.2_c.jar"/>
    <jar href="login/commons-codec-1.9.jar"/>
    <jar href="login/commons-fileupload-1.2.jar"/>
    <jar href="login/commons-httpclient-3.0.1.jar"/>
    <jar href="login/commons-lang-2.0.jar"/>
    <jar href="login/commons-logging-1.1.3.jar"/>
    <jar href="login/jcchart.jar"/>
    <jar href="login/jcelements.jar"/>
    <jar href="login/jcfield.jar"/>
    <jar href="login/jcgauge.jar"/>
    <jar href="login/jcjarmaster.jar"/>
    <jar href="login/jcpagelayout.jar"/>
    <jar href="login/jctable.jar"/>
    <jar href="login/log4j-1.2.17.jar"/>
    <jar href="login/s2-framework-2.3.23.jar"/>
    <jar href="login/swing-layout-1.0.jar"/>
    <jar href="login/servlet-api.jar"/>
  </resources>

  <application-desc main-class="jp.co.ais.trans2.common.client.TSingleStart">
    <argument>system.name</argument>
    <argument>★TRANS2.2 エンハンス★</argument>
  </application-desc>

</jnlp>
