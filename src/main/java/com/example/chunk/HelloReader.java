package com.example.chunk;


import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@StepScope
@Slf4j
public class HelloReader implements ItemReader<String>{ // ItemReaderを実装する。ジェネリクスで指定したデータ型をreadメソッドの戻り値型にする。
	
	// 出力用文字列
	private String[] input = {"Hello", "World", "aaa", "bbb", null, "The World"};
	
	private int index = 0;
	
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		// jobExecutionContextの取得
		ExecutionContext jobExecutionContext = stepExecution.getJobExecution().getExecutionContext();
		
		// Mapに登録
		jobExecutionContext.put("jobKey", "jobValue");
		
		
		// stepExecutionContextの取得
		ExecutionContext stepExecutionContext = stepExecution.getExecutionContext();
		
		// Mapに登録
		stepExecutionContext.put("stepKey", "stepValue");
		
	}

	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		// 配列の文字列を取得
		String message = input[index++];
		log.info(message);
		
		return message; // ItemProcessor, またはItemWriterに渡される
	}
	
	

}
