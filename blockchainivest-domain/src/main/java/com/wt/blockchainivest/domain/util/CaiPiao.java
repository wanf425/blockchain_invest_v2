package com.wt.blockchainivest.domain.util;

import java.util.*;

/**
 * @program: asset
 * @description:
 * @author: wang tao
 * @create: 2020-04-21 15:50
 */
public class CaiPiao {

    public static void main(String[] args) {

        for(int j= 0;j<10;j++) {
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            for (int i = 0; i < 1000000; i++) {
                String numbers = getNumbers();
                if (map.containsKey(numbers)) {
                    map.put(numbers, map.get(numbers) + 1);
                } else {
                    map.put(numbers, 1);
                }
            }

            Map<Integer, List<String>> map2 =
                    new TreeMap<Integer, List<String>>((k1, k2) -> {
                        return k1 - k2;
                    });

            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                if (entry.getValue() > 4) {
                    if (map2.containsKey(entry.getValue())) {
                        map2.get(entry.getValue()).add(entry.getKey());
                    } else {
                        List<String> list = new ArrayList<>();
                        list.add(entry.getKey());
                        map2.put(entry.getValue(), list);
                    }
                }
            }

            for (Map.Entry<Integer, List<String>> entry : map2.entrySet()) {
                System.out.println("重复次数：" + entry.getKey());
                for (String s : entry.getValue()) {
                    System.out.println("号码：" + s);
                }

                System.out.print("----------");
            }
        }

    }

    private static String getNumbers() {
        List<Integer> pre = getRandomList(5, 35);
        List<Integer> after = getRandomList(2, 12);

        return getResult(pre, after);
    }

    private static List<Integer> getRandomList(int length, int maxNumber) {
        List<Integer> list = new ArrayList<>();
        Random rd = new Random();
        for (int i = 0; i < length; i++) {
            int number = rd.nextInt(maxNumber + 1);

            if (number > 0 && !list.contains(number)) {
                list.add(number);
            } else {
                i--;
            }
        }

        return list;
    }

    private static String getResult(List<Integer> pre, List<Integer> after) {
        StringBuffer result = new StringBuffer("");

        pre.sort((a, b) -> {
            return a - b;
        });

        after.sort((a, b) -> {
            return a - b;
        });

        String split = " ";
        for (Integer number : pre) {
            result.append(number).append(split);
        }

        for (Integer number : after) {
            result.append(number).append(split);
        }

//        System.out.println(result);
        return result.toString();
    }
}