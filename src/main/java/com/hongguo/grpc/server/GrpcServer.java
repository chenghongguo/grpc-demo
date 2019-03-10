package com.hongguo.grpc.server;

import com.hongguo.grpc.service.StudentServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @author hongguo_cheng
 * @date 2019-03-10
 * @description
 */
public class GrpcServer {

    private Server server;

    private void start() throws IOException {
        int port = 50051;
        server = ServerBuilder.forPort(port).addService(new StudentServiceImpl()).build().start();
        System.out.println("server started!");
    }

    private void stop() throws Exception {
        if (null != server) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws Exception {
        if (null != server) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws Exception {
        GrpcServer server = new GrpcServer();
        server.start();
        server.blockUntilShutdown();
    }
}
