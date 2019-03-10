package com.hongguo.grpc.service;

import com.google.protobuf.ProtocolStringList;
import com.hongguo.grpc.data.*;
import io.grpc.stub.StreamObserver;

import java.util.UUID;

/**
 * @author hongguo_cheng
 * @date 2019-03-10
 * @description
 */
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    @Override
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("request info :" + request.getUsername());
        MyResponse reply = MyResponse.newBuilder().setRealname("zhangsan").build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void getStudentsByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        System.out.println("request info :" + request.getAge());
        responseObserver.onNext(StudentResponse.newBuilder().setAge(request.getAge()).setName("张三").setCity("北京").build());
        responseObserver.onNext(StudentResponse.newBuilder().setAge(request.getAge()).setName("李四").setCity("上海").build());
        responseObserver.onNext(StudentResponse.newBuilder().setAge(request.getAge()).setName("王五").setCity("广州").build());
        responseObserver.onNext(StudentResponse.newBuilder().setAge(request.getAge()).setName("赵六").setCity("深圳").build());
        responseObserver.onNext(StudentResponse.newBuilder().setAge(request.getAge()).setName("田七").setCity("杭州").build());
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<StudentHobbyRequest> getStudentByHobbies(StreamObserver<StudentResponseWrapper> responseObserver) {
        return new StreamObserver<StudentHobbyRequest>() {
            @Override
            public void onNext(StudentHobbyRequest value) {
                ProtocolStringList hobbyList = value.getHobbyList();
                hobbyList.stream().forEach(hobby -> System.out.println(hobby));
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("error");
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(StudentResponseWrapper.newBuilder()
                        .addStudentResponse(StudentResponse.newBuilder().setCity("石家庄").setName("张三").setAge(20).build())
                        .addStudentResponse(StudentResponse.newBuilder().setAge(30).setName("李四").setCity("上海").build())
                        .addStudentResponse(StudentResponse.newBuilder().setAge(40).setName("王五").setCity("北京").build())
                        .addStudentResponse(StudentResponse.newBuilder().setAge(50).setName("赵六").setCity("广州").build())
                        .build());
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<TalkRequest> biTalk(StreamObserver<TalkResponse> responseObserver) {
        return new StreamObserver<TalkRequest>() {
            @Override
            public void onNext(TalkRequest value) {
                System.out.println(value.getMsg());
                responseObserver.onNext(TalkResponse.newBuilder().setUuid(UUID.randomUUID().toString()).build());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
