package com.hongguo.grpc.client;

import com.hongguo.grpc.data.*;
import com.hongguo.grpc.service.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * @author hongguo_cheng
 * @date 2019-03-10
 * @description
 */
public class StudentClient {
    private ManagedChannel channel;
    private StudentServiceGrpc.StudentServiceBlockingStub studentServiceBlockingStub;
    private StudentServiceGrpc.StudentServiceStub studentServiceStub;

    public StudentClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext().build());
    }

    public StudentClient(ManagedChannel channel) {
        this.channel = channel;
        studentServiceBlockingStub = StudentServiceGrpc.newBlockingStub(channel);
        studentServiceStub = StudentServiceGrpc.newStub(channel);
    }

    public void shutdown() throws Exception {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void getStudentsByAge(int age) {
        StudentRequest request = StudentRequest.newBuilder().setAge(age).build();

        Iterator<StudentResponse> iterator = null;
        try {
            iterator = studentServiceBlockingStub.getStudentsByAge(request);
            while (iterator.hasNext()) {
                StudentResponse response = iterator.next();
                System.out.println(response.getName() + ", " + response.getCity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getStudentByHobbies() throws Exception {
        StreamObserver<StudentResponseWrapper> studentResponseWrapperStreamObserver =
                new StreamObserver<StudentResponseWrapper>() {
                    @Override
                    public void onNext(StudentResponseWrapper value) {
                        value.getStudentResponseList().forEach(studentResponse -> {
                            System.out.println(studentResponse.getName());
                            System.out.println(studentResponse.getAge());
                            System.out.println(studentResponse.getCity());
                            System.out.println("****************");
                        });
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println(t.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("completed!");
                    }
                };
        StreamObserver<StudentHobbyRequest> observer = studentServiceStub
                .getStudentByHobbies(studentResponseWrapperStreamObserver);

        observer.onNext(StudentHobbyRequest.newBuilder().addHobby("篮球").build());
        observer.onNext(StudentHobbyRequest.newBuilder().addHobby("足球").build());
        observer.onNext(StudentHobbyRequest.newBuilder().addHobby("排球").build());
        observer.onNext(StudentHobbyRequest.newBuilder().addHobby("橄榄球").build());
        observer.onCompleted();
        Thread.sleep(3000);
    }

    private void getUUIDFromServer() {
        StreamObserver<TalkResponse> streamObserver = new StreamObserver<TalkResponse>() {
            @Override
            public void onNext(TalkResponse value) {
                System.out.println(value.getUuid());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("completed");
            }
        };
        StreamObserver<TalkRequest> observer = studentServiceStub.biTalk(streamObserver);
        for (int i = 0; i < 10; i++) {
            observer.onNext(TalkRequest.newBuilder().setMsg(LocalDateTime.now().toString()).build());
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        observer.onCompleted();
    }

    public static void main(String[] args) throws Exception {
        StudentClient client = new StudentClient("localhost", 50051);
        try {
            client.getUUIDFromServer();
        } finally {
            client.shutdown();
        }
    }
}
