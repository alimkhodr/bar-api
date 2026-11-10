package com.bar.service;

import com.bar.exception.ConflictException;
import com.bar.model.GymClass;
import com.bar.model.Member;
import com.bar.repository.LinkRepository;

public class EnrollmentService extends AssociationService<GymClass, Member> {

    public EnrollmentService(LinkRepository enrollments, CrudService<GymClass> classes, CrudService<Member> members) {
        super(enrollments, classes, members);
    }

    @Override
    protected void beforeLink(GymClass gymClass, int enrolled) {
        if (gymClass.isFull(enrolled)) {
            throw new ConflictException("class " + gymClass.getId() + " is full");
        }
    }
}
