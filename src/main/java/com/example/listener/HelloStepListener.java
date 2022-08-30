package com.example.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HelloStepListener implements StepExecutionListener{
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		log.info("Before step:{}", stepExecution);
		
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info("After step:{}", stepExecution);
		return stepExecution.getExitStatus(); // step終了時のステータスを返す必要がある
	}

}
