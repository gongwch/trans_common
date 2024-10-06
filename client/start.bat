@ECHO OFF

TITLE TRANS Client Start...

SET LIB_PATH=..\login\
SET CONFIG_PATH=client.properties;.
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%trans-1-dss_c.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%trans-commons2.3_sg_c.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%trans-account-ap2.3_sg_c.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%trans-account-ap_c.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%trans-account-ar2.3_sg_c.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%trans-account-ar_c.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%trans-account-fa2.2_c.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%trans-account-gl2.3_sg_c.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%trans-account-gl_c.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%trans-account-lm2.2_c.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%trans-account-lm_c.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%trans-owner2.2_c.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%trans-tc2.2_c.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%ais-lookandfeel2.2_c.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%commons-codec-1.3.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%commons-fileupload-1.2.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%commons-httpclient-3.0.1.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%commons-lang-2.0.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%commons-logging-1.0.4.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%jcchart.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%jcelements.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%jcfield.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%jnlp.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%jcgauge.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%jcjarmaster.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%jcpagelayout.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%jctable.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%log4j-1.2.8.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%s2-framework-2.3.23.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%servlet-api.jar
SET CONFIG_PATH=%CONFIG_PATH%;%LIB_PATH%swing-layout-1.0.jar
SET CLASSPATH=%CONFIG_PATH%

REM ECHO %CLASSPATH%

start javaw -classpath "%CLASSPATH%" -XX:MaxPermSize=256m -Xmx512m jp.co.ais.trans2.common.client.TDssStart

REM PAUSE
EXIT