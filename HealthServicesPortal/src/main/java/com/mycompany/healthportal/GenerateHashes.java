package com.mycompany.healthportal;

public class GenerateHashes {

    public static void main(String[] args) {

        System.out.println("Ahmed: " + PasswordUtil.hashPassword("ahmed123"));
        System.out.println("Reem: " + PasswordUtil.hashPassword("reem123"));
        System.out.println("Karam: " + PasswordUtil.hashPassword("karam123"));
        System.out.println("Farah: " + PasswordUtil.hashPassword("farah123"));
        System.out.println("Omar: " + PasswordUtil.hashPassword("omar123"));
        System.out.println("Mariam: " + PasswordUtil.hashPassword("mariam123"));
    }
}
