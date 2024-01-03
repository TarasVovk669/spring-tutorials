package com.grpc.demo.service;

import com.grpc.demo.gen.*;
import com.grpc.demo.metadata.Role;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import static com.grpc.demo.metadata.MetadataInterceptor.KEY;
import static com.grpc.demo.utils.Const.BALANCE_MAP;

public class BankService extends BankServiceGrpc.BankServiceImplBase {
    @Override
    public void manageBalanceStream(BalanceRequest request, StreamObserver<Balance> responseObserver) {
        System.out.println("Received request for account: " + request.getAccountNumber());

        var balance = BALANCE_MAP.get(request.getAccountNumber());

        if (null != balance) {
            if (request.getAmount() == 0 || request.getOperation().equals(Operation.EMP)) {
                responseObserver.onError(Status.FAILED_PRECONDITION.augmentDescription("Operation and amount must be present").asRuntimeException());
            } else {
                var balanceAmount = request.getOperation().equals(Operation.ADD)
                        ? balance.getBalance() + request.getAmount()
                        : balance.getBalance() - request.getAmount();

                for (int i = 0; i < 10; i++) {
                    balance = Balance.newBuilder()
                            .setBalance(balanceAmount) //only for test
                            .setCurrency(balance.getCurrency())
                            .build();
                    responseObserver.onNext(balance);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                BALANCE_MAP.put(request.getAccountNumber(), balance);
                responseObserver.onCompleted();
            }
        } else {
            responseObserver.onError(new StatusRuntimeException(Status.NOT_FOUND.augmentDescription("Balance not found")));
        }
    }

    @Override
    public void manageBalance(BalanceRequest request, StreamObserver<Balance> responseObserver) {
        var balance = BALANCE_MAP.get(request.getAccountNumber());

        if (null != balance) {
            if (request.getAmount() == 0 || request.getOperation().equals(Operation.EMP)) {
                responseObserver.onError(new StatusRuntimeException(Status.INVALID_ARGUMENT.augmentDescription("Operation and amount must be present")));
            } else {
                var balanceAmount = request.getOperation().equals(Operation.ADD)
                        ? balance.getBalance() + request.getAmount()
                        : balance.getBalance() - request.getAmount();
                balance = Balance.newBuilder()
                        .setBalance(balanceAmount)
                        .setCurrency(balance.getCurrency())
                        .build();
                BALANCE_MAP.put(request.getAccountNumber(), balance);
                responseObserver.onNext(balance);
                responseObserver.onCompleted();
            }
        } else {
            responseObserver.onError(new StatusRuntimeException(Status.NOT_FOUND.augmentDescription("Balance not found")));
        }
    }

    @Override
    public void getAllBalance(EmptyRequest request, StreamObserver<BalanceResponseList> responseObserver) {
        responseObserver.onNext(BalanceResponseList.newBuilder()
                .addAllBalanceList(BALANCE_MAP.values()).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getBalance(BalanceRequest request, StreamObserver<Balance> responseObserver) {
        System.out.println("Received request for account: " + request.getAccountNumber());
        var balance = BALANCE_MAP.get(request.getAccountNumber());

        Role role = KEY.get(); //!!!we must use the same object because this is thread local
        if(role.equals(Role.ADMIN)){
            balance = Balance.newBuilder()
                    .setBalance(1)
                    .setCurrency(Currency.EUR)
                    .build();
        }
        if (null != balance) {
            responseObserver.onNext(balance);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new StatusRuntimeException(Status.NOT_FOUND.augmentDescription("Balance not found")));
        }
    }
}
