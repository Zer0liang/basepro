package com.chechetimes.wzl;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class LruTest {

    private static TreeSet<Node> nodeTreeSet = new TreeSet<>();

    @Data
    static class Node implements Comparable<Node> {
        private int item;
        private LocalDateTime createTime;
        private AtomicInteger count;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;
            Node node = (Node) o;
            return getItem() == node.getItem();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getItem());
        }

        @Override
        public int compareTo(Node o) {
            if (this.count.get() > o.getCount().get()) {
                return -1;
            } else {
                if (this.createTime.isBefore(o.getCreateTime())) {
                    return 1;
                } else if (this.createTime.isAfter(o.getCreateTime())) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }

        @Override
        public String toString() {
            return "Node{" +
                    "item=" + item +
                    ", createTime=" + createTime +
                    ", count=" + count +
                    '}';
        }
    }

    public static void addNode(int item, LocalDateTime localDateTime, AtomicInteger count) {
        Node node = new Node();
        node.setItem(item);
        node.setCreateTime(localDateTime);
        node.setCount(count);
        nodeTreeSet.add(node);
    }

    public static void addNode(int item) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addNode(item, LocalDateTime.now(), new AtomicInteger(1));
    }

    public static void equalNode(int item) {
        Node node = findNodeByItem(item);
        if (node == null) {
            return;
        }
        node.getCount().incrementAndGet();
        nodeTreeSet.remove(node);
        nodeTreeSet.add(node);
    }

    private static Node findNodeByItem(int item) {
        return nodeTreeSet.stream().filter(it -> it.getItem() == item).findAny().orElse(null);
    }

    public static void main(String[] args) {
        addNode(1);
        addNode(6);
        addNode(4);
        addNode(20);

        equalNode(6);

        addNode(26);
        equalNode(26);

        Iterator<Node> it = nodeTreeSet.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

}
