package emp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.DAO;
import emp.Employee;

public class EmpDAO { // DB 사용하는 메소드
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public void updateEmployee(Employee emp) {
		conn = DAO.getConnect();
		String sql = "update emp_temp set salary=? where employee_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, emp.getSalary());
			pstmt.setInt(2, emp.getEmployeeId());
			int r = pstmt.executeUpdate();
			System.out.println(r + "건이 변경되었습니다.");

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteEmp(int empId) {
		conn = DAO.getConnect();
		String sql = "delete from emp_temp where employee_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empId);
			int r = pstmt.executeUpdate();
			System.out.println(r + "건이 삭제됨");
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void deleteEmployee(int empNo) {
		conn = DAO.getConnect();
		String sql = "delete from emp_temp where employee_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empNo);
			int r = pstmt.executeUpdate();
			System.out.println(r + "건이 삭제됨");
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public Employee getEmployee(int empId) {
		conn = DAO.getConnect();
		String sql = "select * from emp_temp where employee_id = ?";
//		String sql1 = "{? = call get_dept_name(?)}";
		Employee emp = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empId);
			rs = pstmt.executeQuery();
//			CallableStatement cstmt = conn.prepareCall(sql1);
//			cstmt.registerOutParameter(1, java.sql.Types.VARCHAR);
//			cstmt.setInt(2, empId);
//			cstmt.execute();
//			String deptName = cstmt.getString(1);

			if (rs.next()) {
				emp = new Employee();
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setHireDate(rs.getString("hire_date"));
				emp.setJobId(rs.getString("job_id"));
				emp.setSalary(rs.getInt("salary"));
//				emp.setDeptName(deptName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		return emp;

	}

	public void insertEmpProc(Employee emp) {
		conn = DAO.getConnect();
		String sql = "{call add_new_member(?,?,?,?,?,?)}";
		try {
			CallableStatement cstmt = conn.prepareCall(sql);
			cstmt.setString(1, emp.getFirstName());
			cstmt.setString(2, emp.getLastName());
			cstmt.setInt(3, emp.getSalary());
			cstmt.setString(4, emp.getJobId());
			cstmt.setString(5, emp.getHireDate());
			cstmt.setString(6, emp.getEmail());
			cstmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	public void insertEmp(Employee emp) {
		conn = DAO.getConnect();
		String sql = "insert into emp_temp(employee_id, " + "first_name, last_name, email, job_id, hire_date, salary)"
				+ "values (employees_seq.nextval,?,?,?,?,?,?)";
		int rCnt = 0; // 순서를 담는 변수
		try {
			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(++rCnt, emp.getEmployeeId());
			pstmt.setString(++rCnt, emp.getFirstName());
			pstmt.setString(++rCnt, emp.getLastName());
			pstmt.setString(++rCnt, emp.getEmail());
			pstmt.setString(++rCnt, emp.getJobId());
			pstmt.setString(++rCnt, emp.getHireDate());
			pstmt.setInt(++rCnt, emp.getSalary());
			int r = pstmt.executeUpdate();
			System.out.println(r + " 건이 입력되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Employee> getEmpList() {
		List<Employee> list = new ArrayList<>();
		conn = DAO.getConnect();
		String sql = "select * from emp_temp  order by 1 desc";
		Employee emp = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emp = new Employee();
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setHireDate(rs.getString("hire_date"));
				emp.setEmail(rs.getString("email"));
				emp.setSalary(rs.getInt("salary"));
				emp.setJobId(rs.getString("job_id"));
				list.add(emp);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

}
