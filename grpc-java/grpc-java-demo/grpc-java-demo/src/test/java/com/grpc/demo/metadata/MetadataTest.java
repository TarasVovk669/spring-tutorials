package com.grpc.demo.metadata;

import com.grpc.demo.deadline.DeadlineInterceptor;
import com.grpc.demo.gen.Balance;
import com.grpc.demo.gen.BalanceRequest;
import com.grpc.demo.gen.BankServiceGrpc;
import io.grpc.Deadline;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.TimeUnit;

import static com.grpc.demo.metadata.Utils.getMetadata;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MetadataTest {

    private BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;

    @BeforeAll
    public void setup(){
        ManagedChannel localhost = ManagedChannelBuilder.forAddress("localhost", 8081)
                .intercept(MetadataUtils.newAttachHeadersInterceptor(getMetadata()))
                .usePlaintext()
                .build();

        this.bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(localhost);
    }

    @Test
    public void metadataTest1(){
        Balance balance = this.bankServiceBlockingStub
                .getBalance(BalanceRequest.newBuilder()
                        .setAccountNumber(5321L).build());
        System.out.println(balance);
    }

    @Test
    public void metadataTest2(){
        Balance balance = this.bankServiceBlockingStub
                .withCallCredentials(new JWTCredentials("jwt-qwerty123-ADMIN"))
                .getBalance(BalanceRequest.newBuilder()
                        .setAccountNumber(5321L).build());
        System.out.println(balance);
    }


}
