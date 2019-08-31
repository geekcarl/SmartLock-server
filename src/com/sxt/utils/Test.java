package com.sxt.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public   class  Test {
    public   static   void  main(String[] args)  {
    	
       PropertyConfigurator.configure( "C:/java/apache-tomcat-6.0.41-windows-x64/apache-tomcat-6.0.41/webapps/newinterlockwebservice/WEB-INF/classes/log4j.properties" );
       Logger logger  =  Logger.getLogger(Test.class );
       logger.debug( " debug " );
       logger.error( " error " );
   } 
}