package com.ef;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.ef.log.service.LogAnalyserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Parser {

	public static void main(String[] args) {
		log.debug("Initializing spring context started ");
		AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
		log.debug("Initializing spring context completed");
		try {
			Command command = new Command(args);
			log.info("Command Object : "+command);
			LogAnalyserService service =context.getBean(LogAnalyserService.class);
			service.analyse(command);			
		}catch(Exception ex) {
			log.error("Exception while creating Command object", ex);
			log.error("Please check the Arguments and try again");
		}
		
	}
}
