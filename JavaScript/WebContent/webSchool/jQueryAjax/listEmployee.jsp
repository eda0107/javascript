<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String empId=request.getParameter("empId");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>listEmployee.html</title>
<script src="../jquery/jquery.min.js"></script>
<script>
	var focusId =<%=empId%>;
		$.ajax({
		url : "../../EmpServlet",
		dataType : "json",

		success : function(result) {
			console.log(result);

			var table, tr, th, td;
			table = $("<table />");
			table.attr("border", "1");
			//th 타이틀 가지고 오는 부분
			tr = $("<tr />");
			for (title in result[0]) {
				th = $("<th />").text(title);
				tr.append(th);
			}
			tr.append($("<th />").text("삭제")); //헤더에 del 버튼 만들기 위한 컬럼 추가
			table.append(tr);

			//td 데이터를 가지고 보여주는 부분
			$.each(result, function(i, o) { //인덱스 값 i, 오브젝트 값은 o로 받음

				tr = $("<tr />").attr("id", o.employeeId).dblclick(changeFunc);
				for (field in o) {
					if (field == "employeeId") {
						var ahref = $("<a />").text(o[field]);
						ahref.attr("href", "updateEmployee.jsp?empId="
								+ o[field]);
						td = $("<td />").html(ahref);
						tr.append(td);

					} else {
						td = $("<td />").text(o[field]);
						tr.append(td);

					}

				}
				if (focusId == o.employeeId) {
					console.log(o)
					tr.css("background-color", "red");
					tr.focus();

				}
				var del = $("<button />").text("del");
				del.click(delFunc);
				tr.append($("<td />").html(del));
				table.append(tr);

			})

			$("#show").append(table);
		}
	})

	function changeFunc() {
		var deptName = $(this).children().eq(0).text();
		var firstName = $(this).children().eq(1).text();
		var jobId = $(this).children().eq(2).text();
		var lastName = $(this).children().eq(3).text();
		var hireDate = $(this).children().eq(4).text();
		var empId = $(this).children().eq(5).text();
		var salary = $(this).children().eq(6).text();
		var email = $(this).children().eq(7).text();

		var tr = $("<tr />");
		tr.append($("<td />").text(deptName));
		tr.append($("<td />").text(firstName));
		tr.append($("<td />").text(jobId));
		tr.append($("<td />").text(lastName));
		tr.append($("<td />").text(hireDate));
		tr.append($("<td />").text(empId));
		tr.append($("<td />").html($("<input />").val(salary))); //text 부분에 넣으면 바꿀 수가 없음 //변경하기 위해서 html~ val
		tr.append($("<td />").text(email));
		tr.append($("<td />").html(
				$("<button />").text("변경").dblclick(updateFunc))); //변경 버튼 나오게

		$("#" + empId).after(tr);
		//after(tr)-> $ 다음에 tr태그를 위치시키겠다
		$("#" + empId).css("display", "none");

	}

	function updateFunc() {
		console.log("변경");
	}

	function delFunc() { //db의 내용 지우는 것
		console.log("delFunc");
		var empId = $(this).parent().parent().attr("id");
		$.ajax({
			url : "../../DeleteEmpServ",
			data : "empId=" + empId,
			// 			$("#"+empId).remove();
			success : function() {
				console.log("del success");
			}

		})
		$(this).parent().parent().remove(); //this->button del을 가르킴(적용대상)
		//td->tr로 가서 tr을 삭제해야 하니까 parent()x2

	}
</script>
</head>
<body>
	<a href="insertEmployee.html">입력화면</a>
	<!-- 	<a href="listEmployee.html">리스트</a> -->
	<p id="show"></p>



</body>
</html>