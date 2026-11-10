package com.bar;

import com.bar.db.Database;
import com.bar.http.CrudHandler;
import com.bar.http.GymClassHandler;
import com.bar.http.InstructorHandler;
import com.bar.http.MemberHandler;
import com.bar.http.PlanHandler;
import com.bar.model.Equipment;
import com.bar.model.GymClass;
import com.bar.model.Instructor;
import com.bar.model.Plan;
import com.bar.repository.EquipmentRepository;
import com.bar.repository.GymClassRepository;
import com.bar.repository.InstructorRepository;
import com.bar.repository.LinkRepository;
import com.bar.repository.MemberRepository;
import com.bar.repository.PlanRepository;
import com.bar.service.AssociationService;
import com.bar.service.CrudService;
import com.bar.service.EnrollmentService;
import com.bar.service.GymClassService;
import com.bar.service.MemberService;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class App {

    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;

        Database database = new Database("jdbc:h2:./data/gym");
        database.init();

        CrudService<Plan> plans = new CrudService<>(new PlanRepository(database), "plan");
        CrudService<Instructor> instructors = new CrudService<>(new InstructorRepository(database), "instructor");
        CrudService<Equipment> equipment = new CrudService<>(new EquipmentRepository(database), "equipment");
        MemberService members = new MemberService(new MemberRepository(database), plans);
        GymClassService classes = new GymClassService(new GymClassRepository(database), instructors);

        EnrollmentService enrollments = new EnrollmentService(
                new LinkRepository(database, "class_members", "class_id", "member_id"), classes, members);
        AssociationService<GymClass, Equipment> classEquipment = new AssociationService<>(
                new LinkRepository(database, "class_equipment", "class_id", "equipment_id"), classes, equipment);

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/plans", new PlanHandler(plans, members));
        server.createContext("/members", new MemberHandler(members, enrollments));
        server.createContext("/instructors", new InstructorHandler(instructors, classes));
        server.createContext("/classes", new GymClassHandler(classes, enrollments, classEquipment));
        server.createContext("/equipment", new CrudHandler<>(equipment, Equipment.class));
        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        server.start();

        System.out.println("Server running on http://localhost:" + port);
    }
}
