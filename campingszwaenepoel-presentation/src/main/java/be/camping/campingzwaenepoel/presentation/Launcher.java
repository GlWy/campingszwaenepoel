package be.camping.campingzwaenepoel.presentation;

import org.springframework.context.support.ClassPathXmlApplicationContext;

class Launcher {
		
    void launch() {
        String[] contextPaths = new String[] {"/spring/applicationContext.xml"};
        new ClassPathXmlApplicationContext(contextPaths);
    }
}