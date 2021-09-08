package com.app.chat.spark;

import com.app.chat.model.WordFrequency;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class WordCountJob {

    private final JavaSparkContext sc;

    @Autowired
    public WordCountJob(JavaSparkContext javaSparkContext) {
        this.sc = javaSparkContext;
    }

    public List<WordFrequency> run(String username){
        List<WordFrequency> wordFrequencyList = new ArrayList<>();

        JavaRDD<String> lines = sc.textFile(username + ".txt");
        JavaRDD<String> words = lines.flatMap(line -> Arrays.asList(line.split(" ")).iterator());

        Map<String, Long> wordCounts = words.countByValue();

        for(Map.Entry<String, Long> entry : wordCounts.entrySet()) {
            WordFrequency frequency = new WordFrequency();
            frequency.setWord(entry.getKey());
            frequency.setCount(entry.getValue());

            wordFrequencyList.add(frequency);
        }

        return wordFrequencyList;
    }
 }
