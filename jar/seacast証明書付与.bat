@echo off

SET JAR_NAME=trans-0-seacast

DEL /F %JAR_NAME%_c.jar

@echo %JAR_NAME% •t—^ŠJŽn

jarsigner -storepass aistrans -keypass aistrans -signedjar %JAR_NAME%_c.jar %JAR_NAME%.jar account.information.system

@echo %JAR_NAME% •t—^Š®—¹

exit