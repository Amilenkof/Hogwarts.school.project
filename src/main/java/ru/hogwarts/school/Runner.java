//package ru.hogwarts.school;
//
//public class Runner {
//    public static int winner(int x, int y, int z) {
//        int max = Math.max(x, y);
//        int min = Math.min(x, y);
//
//        if (((z - max) > 2)) {
//            return Math.max((z - max), 0);
//
//        } else if ((z - max) < 2) {
//
//            int dif = z - max;
//            if (z-max<0){
//                return Math.max(2 - dif, 0);
//            }
//            return
//
//        }
//        return -1;
//    }
//    public static void main(String[] args) {
//
//        System.out.println("winner(10, 15, 25) = " + winner(10, 15, 25));
//        System.out.println("winner= " + winner(8, 8, 15));
//        System.out.println("winner= " + winner(31, 30, 28));
//        System.out.println("winner= " + winner(131, 30, 28));
//
//        System.out.println("winner(1,4,5) = " + winner(1, 4, 5));
//
//    }
//}
