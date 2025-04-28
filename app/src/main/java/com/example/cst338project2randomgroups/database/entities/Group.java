package com.example.cst338project2randomgroups.database.entities;

public class Group {
    private int groupId;
    private int classroomId;
    private int studentId;
    private int groupSize;

    public Group(int classroomId, int studentId, int groupSize){
        this.classroomId = classroomId;
        this.studentId = studentId;
        this.groupSize = groupSize;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setRosterId(int rosterId) {
        this.classroomId = rosterId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }
}
