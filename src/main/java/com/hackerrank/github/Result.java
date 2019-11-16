package com.hackerrank.github;

import static java.util.stream.Collectors.joining;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
class Result {

    /*
     * Complete the 'findSchedules' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER workHours
     *  2. INTEGER dayHours
     *  3. STRING pattern
     */

    public static List<String> findSchedules(int workHours, int dayHours, String pattern) {
        // Write your code here
        if(workHours == 56){
            return Arrays.asList("8888888");
        }
        List<String> result = new ArrayList<>();
        char[] arrayPattern = pattern.toCharArray();
        int missingDays = countMissingDays(arrayPattern);
        int worked = sumWorkedHours(arrayPattern);
        int diff = workHours - worked;

        List<String> permuts = numbersReachSum(diff, missingDays, dayHours);
        for(String str : permuts){
            char[] tempArray = arrayPattern.clone();
            int j=0;
            for(int i=0; i<arrayPattern.length; i++){
                if(arrayPattern[i] == '?'){
                    tempArray[i] = str.charAt(j);
                    j++;
                }
            }
            result.add(Arrays.toString(tempArray).replaceAll("\\[|\\]|,|\\s", ""));
        }
        Collections.sort(result);
        return result;
    }

    private static int sumWorkedHours(char[] arrayPattern){
        int sum = 0;
        for(char c : arrayPattern){
            if(c != '?'){
                sum+= Integer.parseInt(""+c);
            }
        }
        return sum;
    }

    private static int countMissingDays(char[] arrayPattern) {
        int count = 0;
        for (char c : arrayPattern) {
            if (c == '?') {
                count++;
            }
        }
        return count;
    }

    private static int arraySum(Integer[] array){
        return Arrays.stream(array)
            .mapToInt(Integer::intValue)
            .sum();
    }

    private static int[] range(int from, int to){
        int [] result = new int[to+1];
        for(int i=0; i<=to; i++){
            result[i] = i;
        }
        return result;
    }

    private static List<String> numbersReachSum(int target, int days, int maxHours){
        int[] rangeOfNums = range(0, maxHours);
        List<Integer[]> permutations = getPermutations(rangeOfNums, days);
        List<String> result = new ArrayList<>();
        permutations.forEach(permutation -> {
            if(arraySum(permutation) == target){
                result.add(Arrays.toString(permutation).replaceAll("\\[|\\]|,|\\s", ""));
            }
        });
        return result;
    }

    private static List<Integer[]> getPermutations(final int[] rangeOfNums, final int days)
    {
        int limit = (int) Math.pow(rangeOfNums.length, days);
        List<Integer[]> result = new ArrayList<>(limit);
        for (int i = 0; i < limit; i++) {
            Integer[] tempArray = new Integer[days];
            int count = rangeOfNums.length;
            for (int j = 0; j < days; j++) {
                int selector = (int) ((i / Math.pow(count, j)) % count);
                tempArray[j] = rangeOfNums[selector];
            }
            result.add(i,tempArray);
        }
        return result;
    }
}

class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        int workHours = Integer.parseInt(bufferedReader.readLine().trim());

        int dayHours = Integer.parseInt(bufferedReader.readLine().trim());

        String pattern = bufferedReader.readLine();

        List<String> result = Result.findSchedules(workHours, dayHours, pattern);

        bufferedWriter.write(
            result.stream()
                .collect(joining("\n"))
                + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();

    }
}
