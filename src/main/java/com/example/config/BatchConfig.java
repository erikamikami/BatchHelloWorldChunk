package com.example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.example.chunk.HelloProcessor;
import com.example.chunk.HelloReader;
import com.example.chunk.HelloWriter;

@Component
@EnableBatchProcessing
public class BatchConfig {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private HelloReader helloReader;
	
	@Autowired
	private HelloProcessor helloProcessor;
	
	@Autowired
	private HelloWriter helloWriter;
	
	@Autowired
	private JobExecutionListener jobListener;
	
	@Autowired
	private StepExecutionListener stepListener;
	
	// Stepを生成
	@Bean
	public Step chunkStep() {
		return stepBuilderFactory.get("HelloCunkStep")
								.<String, String>chunk(3) // <Input, Output> 引数は1度に処理する件数
								.reader(helloReader)
								.processor(helloProcessor)
								.writer(helloWriter)
								.listener(stepListener)
								.build();
	}
	
	// jobを生成
	@Bean
	public Job chunkJob() {
		return jobBuilderFactory.get("HelloChunkJob")
								.incrementer(new RunIdIncrementer())
								.start(chunkStep())
								.listener(jobListener)
								.build();
	}
	
	

}
