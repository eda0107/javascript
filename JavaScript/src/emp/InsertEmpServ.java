package emp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertEmpServ")
public class InsertEmpServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InsertEmpServ() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EmpDAO dao = new EmpDAO();
		
		// 사용자가 입력한 항목의 값들(insertEmployee의 input 태그에서
		String firstName = request.getParameter("firstName"); // insertEmployee에서 input type 태그의 name="firstName"
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String hireDate = request.getParameter("hireDate");
		String salary = request.getParameter("salary");
		String jodId = request.getParameter("jobId");
		
		Employee emp = new Employee();
		
		emp.setFirstName(firstName);
		emp.setLastName(lastName);
		emp.setEmail(email);
		emp.setHireDate(hireDate);
		emp.setJobId(jodId);
		emp.setSalary(Integer.parseInt(salary));
		
		
		dao.insertEmp(emp);
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
