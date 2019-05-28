package com.cc.ccspace.facade.domain.common.test.sort;

import lombok.Data;

import java.util.*;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/5/28 20:43.
 */
public class CollectionsSortComparator {

    static int randomLen=5;
    public static void main(String[] args) {
//     利用自定义的Comparator比较器进行对象list排序
//         testComparatorSort();

//      利用实现Comparable接口复写compareTo方法进行对象列表的排序
          testComparableSort();

    }

    private static void testComparatorSort() {
        Person p1=new Person(20);
        Person p2=new Person(30);
        Person p3=new Person(40);
        Person p4=new Person(18);
        List<Person> pList=new ArrayList<>();

        pList.add(p1);
        pList.add(p2);
        pList.add(p3);
        pList.add(p4);
        addRandomPersonsToList(pList);

        System.out.println("排序前");
        for (Person p: pList) {
            System.out.println(p.getAge());
        }
//        Comparator<Person> pCmp=(Person o1,Person o2) -> (o2.getAge()-o1.getAge());
        Comparator<Person> pCmp=new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getAge()-o2.getAge();
            }
        };
        //lambda
//        Comparator<Person> pCmp=(Person o1,Person o2) -> (o1.getAge()-o2.getAge());
//        Comparator<Person> pCmp= Comparator.comparingInt(Person::getAge);
        Collections.sort(pList,pCmp);

        System.out.println("排序后");

        for (Person p: pList) {
            System.out.println(p.getAge());
        }
    }

    /**  * describe:
	 * @author CAI.F
	 * @date:  日期:2019/5/28 时间:22:09
	 * @param
	 */
    private static void testComparableSort() {

        SchoolReport sr=new SchoolReport(100);
        List<SchoolReport> srList=new ArrayList<>();
        addRandomSRsToList(srList);
        System.out.println("comparable 排序前");
        for (SchoolReport report:srList) {
            System.out.println(report.getScore());
        }

        System.out.println("comparable 排序后");
       /* Collections.sort(srList);
        for (SchoolReport report:srList) {
            System.out.println(report.getScore());
        }*/
        AlgorithmSuper as=new BubbleSort();

        SchoolReport [] reportArray={};
        reportArray= srList.toArray(reportArray);
        as.sort(reportArray);

        for (int i = 0; i < reportArray.length; i++) {
            System.out.println(reportArray[i].getScore());
        }


    }

    private static void addRandomPersonsToList(List<Person> pList) {
        Random r=new Random();
        for (int i = 0; i <randomLen ; i++) {
            Person p=new Person(r.nextInt(100));
            pList.add(p);
        }
    }

    private static void addRandomSRsToList(List<SchoolReport> srList) {
        Random r=new Random();
        for (int i = 0; i <randomLen ; i++) {
            SchoolReport sr=new SchoolReport(r.nextInt(100));
            srList.add(sr);
        }
    }


}

@Data
class Person{

    int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person(int age) {
        this.age = age;
    }
}

@Data
class SchoolReport implements Comparable{

    private int score;


    public SchoolReport(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(Object o) {
//        return this.getScore()-((SchoolReport)o).getScore();
        return ((SchoolReport)o).getScore()-this.getScore();
    }
}