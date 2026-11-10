package com.bar.service;

import com.bar.model.GymClass;
import com.bar.model.Instructor;
import com.bar.repository.GymClassRepository;

import java.util.List;

public class GymClassService extends CrudService<GymClass> {

    private final GymClassRepository classes;
    private final CrudService<Instructor> instructors;

    public GymClassService(GymClassRepository classes, CrudService<Instructor> instructors) {
        super(classes, "class");
        this.classes = classes;
        this.instructors = instructors;
    }

    public List<GymClass> findByInstructor(long instructorId) {
        instructors.findById(instructorId);
        return classes.findByInstructorId(instructorId);
    }

    @Override
    protected void checkReferences(GymClass gymClass) {
        instructors.checkExists(gymClass.getInstructorId());
    }
}
