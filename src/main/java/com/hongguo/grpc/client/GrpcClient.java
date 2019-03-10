package com.hongguo.grpc.client;

import com.hongguo.grpc.data.MyRequest;
import com.hongguo.grpc.data.MyResponse;
import com.hongguo.grpc.service.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author hongguo_cheng
 * @date 2019-03-10
 * @description
 */
public class GrpcClient {

    private ManagedChannel channel;
    private StudentServiceGrpc.StudentServiceBlockingStub studentServiceStub;

    public GrpcClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext().build());
    }

    public GrpcClient(ManagedChannel channel) {
        this.channel = channel;
        studentServiceStub = StudentServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws Exception {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void greet(String name) {
        MyRequest request = MyRequest.newBuilder().setUsername(name).build();

        MyResponse response = null;
        try {
            response = studentServiceStub.getRealNameByUsername(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(response.getRealname());
    }


    public static void main(String[] args) throws Exception {
        GrpcClient client = new GrpcClient("localhost", 50051);
        String st = "hello";
        try {
            client.greet(st);
        } finally {
            client.shutdown();
        }
    }
}
