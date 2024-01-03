package com.grpc.demo.demo;

import com.grpc.demo.gen.*;

import java.util.List;

public class CredApp {
    public static void main(String[] args) {
        PhoneCred phoneCred = PhoneCred.newBuilder()
                .setPhone("+345678987654")
                .setPass("qwertyu")
                .build();

        EmailCred emailCred = EmailCred.newBuilder()
                .setEmail("email@email.com")
                .setPassword("qwaszx1234")
                .build();

        Cred cred = Cred.newBuilder()
                .setEmailCred(emailCred)
                .setPhoneCred(phoneCred)
                .build();

        System.out.println(cred);

        Person p = Person.newBuilder()
                .setName("Taras")
                .setId(6691)
                .setAddress(Address.newBuilder()
                        .setCity("Kyiv")
                        .setStreet("Peremohu")
                        .setNumberStreet(11)
                        .build())
                .addAllCar(List.of(
                        Car.newBuilder()
                                .setName("Audi")
                                .setModel("A6")
                                .setColor("black")
                                .build(),
                        Car.newBuilder()
                                .setName("Porsche")
                                .setModel("911")
                                .setColor("white")
                                .build()))
                .build();

        var dealer = Dealer.newBuilder()
                .setName("Ivan")
                .setPriority(Priority.HIGHT)
                .putCars(1L, Car.newBuilder()
                        .setName("Audi")
                        .setModel("A6")
                        .setColor("black")
                        .build())
                .putCars(2L, Car.newBuilder()
                        .setName("Audi")
                        .setModel("Q8")
                        .setColor("black")
                        .build())
                .build();

        System.out.println(p);
        System.out.println(dealer);
        System.out.println(dealer.getPriority());
        System.out.println(dealer.getPhoto());
        System.out.println(dealer.getIsA());
    }
}
