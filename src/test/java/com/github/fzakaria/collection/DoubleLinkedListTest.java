package com.github.fzakaria.collection;

import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

/**
 * Unit test for {@link DoubleLinkedList}
 */
public class DoubleLinkedListTest {

    @Test
    public void testAddEnd() {
        DoubleLinkedList<Integer> list = DoubleLinkedList.create(1, 2);
        list.insertEnd(new DoubleLinkedList.Node<>(3));
        DoubleLinkedList<Integer> expected = DoubleLinkedList.create(1, 2, 3);
        Assertions.assertThat(list).containsExactlyElementsOf(expected);
    }

    @Test
    public void testAddBeginning() {
        DoubleLinkedList<Integer> list = DoubleLinkedList.create(2, 3);
        list.insertBeginning(new DoubleLinkedList.Node<>(1));
        DoubleLinkedList<Integer> expected = DoubleLinkedList.create(1, 2, 3);
        Assertions.assertThat(list).containsExactlyElementsOf(expected);
    }

    @Test
    public void testRemoval() {
        DoubleLinkedList<Integer> list = new DoubleLinkedList<>();
        for (int i = 1 ; i <= 3; i++) {
            list.insertEnd(new DoubleLinkedList.Node<>(i));
        }
        DoubleLinkedList.Node<Integer> nodeToRemove = new DoubleLinkedList.Node<>(4);
        list.insertEnd(nodeToRemove);

        for (int i = 5; i <= 8; i++) {
            list.insertEnd(new DoubleLinkedList.Node<>(i));
        }

        list.remove(nodeToRemove);
        DoubleLinkedList<Integer> expected = DoubleLinkedList.create(1, 2, 3, 5, 6, 7, 8);
        Assertions.assertThat(list).containsExactlyElementsOf(expected);
    }

    @Test
    public void testIterator() {
        DoubleLinkedList<Integer> list = DoubleLinkedList.create(1, 2, 3, 4, 5, 6, 7);
        int expected = 1;
        for (Integer i : list) {
            Assertions.assertThat(i).isEqualTo(expected);
            expected += 1;
        }
        //we increment one additional time
        Assertions.assertThat(expected).isEqualTo(8);
    }

    @Test
    public void testStreamBasic() {
        DoubleLinkedList<Integer> list = DoubleLinkedList.create(1, 2, 3, 4, 5, 6, 7);
        list.stream().forEach(System.out::println);
        int sum = list.stream().reduce(0, Integer::sum);
        int expected = 28;
        Assertions.assertThat(sum).isEqualTo(expected);
    }

    @Test
    public void testStreamFlatten() {
        DoubleLinkedList<List<Integer>> list = DoubleLinkedList.create(Lists.newArrayList(1, 2, 3),
                                                                       Lists.newArrayList(4, 5),
                                                                       Lists.newArrayList(6, 7));
        int sum = list.stream().flatMap(Collection::stream).reduce(0, Integer::sum);
        int expected = 28;
        Assertions.assertThat(sum).isEqualTo(expected);
    }

}
