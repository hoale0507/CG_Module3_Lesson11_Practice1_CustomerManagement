package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.service.CustomerService;
import com.codegym.service.CustomerServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CustomerServlet", value = "/customers")
public class CustomerServlet extends HttpServlet {
    private CustomerService customerService = new CustomerServiceImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action){
            case "create":
                showCreateForm(request,response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                showDeleteForm(request, response);
                break;
            case "view":{
                int id = Integer.parseInt(request.getParameter("id"));
                Customer customer = customerService.findById(id);
                request.setAttribute("customer",customer);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/view.jsp");
                dispatcher.forward(request,response);
                break;
            }
            default:
                listCustomers(request,response);
                break;
        }
    }

    private void showDeleteForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Customer customer = customerService.findById(id);
        request.setAttribute("customer",customer);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/delete.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Customer customer = customerService.findById(id);
        RequestDispatcher dispatcher;
        if(customer == null){
            dispatcher = request.getRequestDispatcher("error-404.jsp");
        } else {
            request.setAttribute("customer",customer);
            dispatcher = request.getRequestDispatcher("/customer/edit.jsp");
        }
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action){
            case "create":{
                createNewCustomer(request, response);
                break;
            }
            case "edit":
                editCustomer(request, response);
                break;
            case "delete":
                int id = Integer.parseInt(request.getParameter("id"));
                customerService.remove(id);
                request.setAttribute("message","Customer info was deleted successfully");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/delete.jsp");
//                dispatcher.forward(request,response);
                response.sendRedirect("/customers");
                break;
            default:
                break;
        }
    }

    private void editCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        Customer customer = new Customer(id,name,email,address);
        customerService.update(id,customer);
        request.setAttribute("message","Customer info updated successfully!");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/edit.jsp");
        dispatcher.forward(request, response);
    }

    private void createNewCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        int id = (int)(Math.random()*10000);
        Customer customer = new Customer(id,name,email,address);
        customerService.save(customer);
        request.setAttribute("message","Customer was created successfully!!");
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer/create.jsp");
        dispatcher.forward(request, response);
    }

    private void listCustomers(HttpServletRequest request, HttpServletResponse response) {
        List<Customer> customers = customerService.findAll();
        request.setAttribute("customers", customers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer/list.jsp");
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer/create.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
