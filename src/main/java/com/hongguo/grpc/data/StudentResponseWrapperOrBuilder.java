// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Student.proto

package com.hongguo.grpc.data;

public interface StudentResponseWrapperOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.hongguo.proto.StudentResponseWrapper)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated .com.hongguo.proto.StudentResponse studentResponse = 1;</code>
   */
  java.util.List<com.hongguo.grpc.data.StudentResponse> 
      getStudentResponseList();
  /**
   * <code>repeated .com.hongguo.proto.StudentResponse studentResponse = 1;</code>
   */
  com.hongguo.grpc.data.StudentResponse getStudentResponse(int index);
  /**
   * <code>repeated .com.hongguo.proto.StudentResponse studentResponse = 1;</code>
   */
  int getStudentResponseCount();
  /**
   * <code>repeated .com.hongguo.proto.StudentResponse studentResponse = 1;</code>
   */
  java.util.List<? extends com.hongguo.grpc.data.StudentResponseOrBuilder> 
      getStudentResponseOrBuilderList();
  /**
   * <code>repeated .com.hongguo.proto.StudentResponse studentResponse = 1;</code>
   */
  com.hongguo.grpc.data.StudentResponseOrBuilder getStudentResponseOrBuilder(
      int index);
}