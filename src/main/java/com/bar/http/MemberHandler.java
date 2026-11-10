package com.bar.http;

import com.bar.model.Member;
import com.bar.service.EnrollmentService;
import com.bar.service.MemberService;

public class MemberHandler extends CrudHandler<Member> {

    private final EnrollmentService enrollments;

    public MemberHandler(MemberService members, EnrollmentService enrollments) {
        super(members, Member.class);
        this.enrollments = enrollments;
    }

    @Override
    protected Response nested(Request request, long id) {
        if (request.size() == 2 && request.segment(1).equals("classes")) {
            return get(request, () -> enrollments.findOwners(id));
        }
        return super.nested(request, id);
    }
}
