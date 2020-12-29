<%@ include file="/WEB-INF/template/include.jsp" %>

<%@ include file="/WEB-INF/template/header.jsp" %>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap.min.js"></script>

<style>
    .headText {
        background: rgb(0, 3, 36);
        background: linear-gradient(90deg, rgba(0, 3, 36, 1) 0%, rgba(9, 121, 97, 1) 54%, rgba(0, 212, 255, 1) 100%);
        color: white;
    }
</style>
<script type="text/javascript">
    function getVisitRooms() {
        let url = "/openmrs/module/queuemanagement/visitrooms.form";
        $.get(url, function (data1, status1) {
            console.log("Rooms : " + data1 + "\nStatus: " + status1);
            let queueTable = $('#patientQueue');
            queueTable.find("tbody tr").remove();
            for (let i = 0; i < data1.length; i++) {
                let visitRooms = data1[i];
                console.log(visitRooms);
                let url = "/openmrs/module/queuemanagement/queueByVisitroom.form?visitroom=" + visitRooms;
                $.get(url, function (queueData, status2) {
                    console.log(queueData);
                    let queues = "";
                    for (let j in queueData)
                        queues += queueData[j].token + " >> ";
                    console.log(queues);
                    queueTable.append("<tr><td>"
                        + visitRooms + "</td><td>"
                        + queues + "</td></tr>");
                    console.log("Queue Data: " + queues + "\nStatus: " + status2);
                });
            }
        });
    };
    $(document).ready(function () {
        getVisitRooms();
        setTimeout(function(){
            location.reload(true);
        }, 60000);
    });
</script>

<h2 class="headText">Patients Queue Dashboard</h2>
<br/>
<div class="container row">
    <table id="patientQueue" class="table table-striped table-bordered" style="width:100%">
        <thead class="thead-dark">
        <tr>
            <th>OPD Visit Room</th>
            <th>Queues by Serial</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <%--           <c:forEach var="developer" items="${visitrooms}">
                           <td>${developer.jobid}</td>
                       </c:forEach>
                       <c:forEach var="developer" items="${listDeveloper}">
                           <td>${developer.jobid}</td>
                       </c:forEach>--%>
        </tr>
        </tbody>
    </table>

</div>


<%@ include file="/WEB-INF/template/footer.jsp" %>
