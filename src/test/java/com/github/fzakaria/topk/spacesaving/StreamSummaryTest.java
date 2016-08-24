package com.github.fzakaria.topk.spacesaving;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.io.Resources;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Unit test for {@link StreamSummary}
 */
public class StreamSummaryTest {

    //This test is when we have enough counters for the whole stream
    @Test
    public void testStreamSummaryAllFitsInMemory() {
        //100 counters
        StreamSummary<String> streamSummary = new StreamSummary<>(0.01);
        String sample = "a b b c c c e e e e e d d d d g g g g g g g f f f f f f";
        Splitter.on(CharMatcher.WHITESPACE).split(sample).forEach(streamSummary::offer);
        List<Counter<String>> top3 = streamSummary.getTopK(3);
        List<String> top3Items = top3.stream().map(Counter::getItem).collect(Collectors.toList());
        Assertions.assertThat(top3Items).containsExactly("g", "f", "e");
    }

    //This test is when we have enough counters for the whole stream
    @Test
    public void testStreamSummaryAllFitsInMemoryButItemsSeenLargerThanCapacity() {
        //100 counters
        StreamSummary<String> streamSummary = new StreamSummary<>(0.01);
        int iterationRange = 200;
        //lets make sure we will iterate more than the capacity
        Assertions.assertThat(iterationRange).isGreaterThan(streamSummary.getCapacity());
        String sample = "a b b c c c e e e e e d d d d g g g g g g g f f f f f f";
        Splitter.on(CharMatcher.WHITESPACE).split(sample).forEach(streamSummary::offer);
        IntStream.range(0, 200).mapToObj(i -> "a").forEach(streamSummary::offer);
        List<Counter<String>> top3 = streamSummary.getTopK(3);
        List<String> top3Items = top3.stream().map(Counter::getItem).collect(Collectors.toList());
        Assertions.assertThat(top3Items).containsExactly("a", "g", "f");
    }

    /**
     * This test uses "lorem_ipsum.txt" which has 2500 words.
     * 573 unique words:
     * $tr -c '[:alnum:]' '[\n*]' < lorem_ipsum.txt | sort | uniq -c
     *
     * The full top 10 words are:
     * $tr -c '[:alnum:]' '[\n*]' < lorem_ipsum.txt | sort | uniq -c | sort -nr | head  -5
     * 43 et
     * 36 nec
     * 35 vel
     * 35 ac
     * 35 a
     */
    @Test
    public void testSmallFileExample() throws Exception {
        URL resourceFile = Resources.getResource("lorem_ipsum.txt");
        String text = Resources.toString(resourceFile, Charsets.UTF_8);
        //500 counters with error range of 5 words (2500 * 0.002)
        StreamSummary<String> streamSummary = new StreamSummary<>(0.002);
        Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings().split(text).forEach(streamSummary::offer);
        List<Counter<String>> top5 = streamSummary.getTopK(1);
        List<String> top5Items = top5.stream().map(Counter::getItem).collect(Collectors.toList());
        Assertions.assertThat(top5Items).containsExactly("et");
    }


}
