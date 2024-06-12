package it.advancia.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

import it.advancia.model.Employee;

@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class EmployeeController {

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Quarkus Example";
    }

    @GET
    @Path("{id}")
    public Employee findEmployeeById(@PathParam("id") Long id) {
        return (Employee) Employee.findByIdOptional(id).orElseThrow(NotFoundException::new);
    }

    @GET
    public List<Employee> getAllEmployee() {
        return Employee.listAll();
    }

    @POST
    @Transactional
    public Employee addEmployee(Employee employee) {
        Employee.persist(employee);
        return employee;
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Employee updateEmployee(@PathParam("id") Long id, Employee employee) {
        Optional<Employee> employeeById = Optional.ofNullable(findEmployeeById(id));
        employeeById.orElseThrow(NotFoundException::new);
        Employee empToUpdate = employeeById.get();
        empToUpdate.setName(employee.getName());
        empToUpdate.setRole(employee.getRole());
        Employee.getEntityManager().merge(empToUpdate);
        return employee;
    }


    @DELETE
    @Path("/{id}")
    @Transactional
    public void deleteEmployee(@PathParam("id") Long id) {
        Employee.deleteById(id);
    }
}
