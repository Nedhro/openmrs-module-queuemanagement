<meta http-equiv="Refresh" content="30"/>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Galada&display=swap">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap.min.js"></script>

<style>
		@import url('https://fonts.googleapis.com/css2?family=Galada&display=swap');
		@import url('fonts/Siyamrupali.ttf');
		@import url('fonts/Nikosh.ttf');

		div {
				font-family: 'Nikosh', Fallback, sans-serif !important;
		}

		.headText {
				background: rgb(0, 3, 36);
				background: linear-gradient(90deg, rgba(0, 3, 36, 1) 0%, rgba(9, 121, 97, 1) 54%, rgba(0, 212, 255, 1) 100%);
				color: white;
		}

		.footer {
				position: fixed;
				left: 0;
				bottom: 0;
				width: 100%;
				text-align: center;
		}

		.banglaFont {
				font-family: 'Nikosh', Fallback, sans-serif !important;
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
    });
</script>
<div class="container-fluid">
		<div class="row headText">
				<div class="col-md-12">
						<img src="/bahmni_config/openmrs/images/hospitalBanner.png" width="100%;">
				</div>
		</div>
		<h2 class="text-center font-weight-bold text-info"><span><spring:message code="queuemanagement.patientDashboard"/></span></h2>
		<div class="container row" style="width: 100%">
				<div class="col-md-12 m-1">
						<table id="patientQueue" class="table table-striped table-bordered" style="width:100%">
								<thead class="thead-dark">
								<tr>
										<th><span class="banglaFont">Room No</span></th>
										<th><span class="banglaFont">Patients Serial No</span></th>
								</tr>
								</thead>
								<tbody>
								<tr></tr>
								</tbody>
						</table>
				</div>
		</div>
</div>
<div class="container marquee" style="font-size: 18px; font-weight: bold; height: 25px;">
		<marquee width="100%" behavior="alternate" bgcolor="yellow">
				<span class="banglaFont">*** Please wear a mask</span>
				<span class="banglaFont">*** Maintain physical distance from each other </span>
				<span class="banglaFont">*** Follow the  hygiene rules ***</span>
		</marquee>
</div>

<div class="footer headText">
		<p><b>Powered By : </b> Crystal Technology Bangladesh Ltd.</p>
</div>


