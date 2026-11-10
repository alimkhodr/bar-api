package com.bar.http;

import com.bar.model.Instructor;
import com.bar.service.CrudService;
import com.bar.service.GymClassService;

public class InstructorHandler extends CrudHandler<Instructor> {

    private final GymClassService classes;

    public InstructorHandler(CrudService<Instructor> instructors, GymClassService classes) {
        super(instructors, Instructor.class);
        this.classes = classes;
    }

    @Override
    protected Response nested(Request request, long id) {
        if (request.size() == 2 && request.segment(1).equals("classes")) {
            return get(request, () -> classes.findByInstructor(id));
        }
        return super.nested(request, id);
    }
}
