/**
 * 
 */
package com.z.ngxcfg;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Administrator
 *
 */
public class AppTest {

    /**
     * @param args
     */
	@Test
    public void test(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        context.start();

        
        System.out.println("ooook");
        try {
            System.in.read();
        } catch (IOException e) {
            
            e.printStackTrace();
            context.close();
        }

    }

}
