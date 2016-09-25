/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author gvpm
 */
public class Somatorio extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//Get inicio e fim parameters
        Enumeration<String> parameterNames = request.getParameterNames();
        int inicio = 0, fim = 0;

        boolean isInicioSet = false, isFimSet = false;
//checks if all paremeters are all right
        while (parameterNames.hasMoreElements()) {
            String current = parameterNames.nextElement();
            if (current.compareTo("inicio") == 0) {
                inicio = Integer.parseInt(request.getParameter(current));
                isInicioSet = true;

            }
            if (current.compareTo("fim") == 0) {
                fim = Integer.parseInt(request.getParameter(current));
                isFimSet = true;

            }

        }
//case when both parameters are set
        if (isFimSet & isInicioSet == true) {

//-----------------------------Total Uses -----------------------
            int totalUses;
            int currentTotalUses = 1;
            ServletContext servletContext = request.getServletContext();
            Object attribute = servletContext.getAttribute("totalUses");
            if (attribute != null) {
                totalUses = (int) attribute;
                currentTotalUses = totalUses;
                totalUses++;
                servletContext.setAttribute("totalUses", totalUses);

            } else {
                servletContext.setAttribute("totalUses", 1);
            }
//-----------------------------Total Uses -----------------------

//-----------------------------Session Uses End -----------------------
            int sessionUses;
            int currentSessionCounter = 1;
            HttpSession session = request.getSession();
            Object sessionCounter = session.getAttribute("sessionCounter");
            if (sessionCounter != null) {
                sessionUses = (int) sessionCounter;
                currentSessionCounter = sessionUses;
                sessionUses++;
                session.setAttribute("sessionCounter", sessionUses);

            } else {
                session.setAttribute("sessionCounter", 1);

            }
//-----------------------------Session Uses End -----------------------     

//-----------------------------Cookie Uses -----------------------
            boolean cookieFound = false;
            int cookieUses;
            int currentCookieCounter = 1;
            Cookie[] cookies = request.getCookies();

            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    Cookie cookie1 = cookies[i];
                    if (cookie1.getName().equals("cookieCounter")) {

                        String sIntCounter = cookie1.getValue();
                        cookieUses = Integer.parseInt(sIntCounter);
                        currentCookieCounter = cookieUses;
                        cookieUses++;
                        sIntCounter = "" + cookieUses;
                        cookie1.setValue(sIntCounter);
                        response.addCookie(cookie1);
                        cookieFound = true;
                    }

                }
                if (!cookieFound) {//caso onde tem cookie no browser mas nenhum do meu servlet

                    String s = "" + 1;
                    response.addCookie(new Cookie("cookieCounter", s));
                    currentCookieCounter = 1;

                }
            } else {
                String s = "" + 1;
                response.addCookie(new Cookie("cookieCounter", s));
                //currentCookieCounter = 1;
            }
//-----------------------------Cookie Uses End -----------------------            

            response.setContentType("text/html;charset=UTF-8");
            
//-----------------------------Somatorio -----------------------               
            int somatorio = 0;
            int contador = inicio;

            while (contador <= fim) {
                somatorio += contador;
                contador++;
//-----------------------------Somatorio End -----------------------                

            }//does all the printing
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Somatorio</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Somatorio de " + inicio + " a " + fim + " = " + somatorio + "</h1>");
                out.println("<h1>Você usou " + currentSessionCounter + " vezes nessa sessão</h1>");
                out.println("<h1>Você usou " + currentCookieCounter + " vezes nesse browser</h1>");
                out.println("<h1>O servlet foi usado " + currentTotalUses + " vezes no total</h1>");
                out.println("</body>");
                out.println("</html>");
            }
        } else {//case when parameters are not ser, goes to erro page, that contains a form

            //goto form page
            response.sendRedirect("erro.html");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
