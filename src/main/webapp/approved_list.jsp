<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Approved Products Before Transfer</title>
    <link rel="stylesheet" href="css/staff_list_styles.css"> 
</head>
<body>
    <div class="staff-list-container" style="max-width: 800px;">
        <header class="staff-list-header" style="border-bottom: none; font-size: 1.2em;">
            View list of Outbound Order approved products before transfer process
        </header>

        <div class="staff-order-list">
            <table>
                <thead>
                    <tr>
                        <th class="col-stt">Stt</th>
                        <th class="col-id">ID</th>
                        <th class="col-ref">Reference No</th>
                        <th>Create date</th>
                        <th>Create by</th>
                        <th>Status</th>
                        <th class="col-action">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="col-stt">1</td>
                        <td class="col-id">x01</td>
                        <td class="col-ref">Xxx01</td>
                        <td>24/09/2025</td>
                        <td>NHV</td>
                        <td><span class="status-pending-red">Pending</span></td>
                        <td class="col-action">
                            <button class="confirm-complete-btn">Confirm Complete</button>
                        </td>
                    </tr>
                    <tr>
                        <td class="col-stt">2</td>
                        <td class="col-id">x01</td>
                        <td class="col-ref">Xxx01</td>
                        <td>24/09/2025</td>
                        <td>NHV</td>
                        <td><span class="status-complete-green">Complete</span></td>
                        <td class="col-action">
                            <span style="color: #666; font-size: 0.9em;">Completed</span>
                        </td>
                    </tr>
                    
                    <tr>
                        <td class="col-stt">3</td>
                        <td class="col-id">x02</td>
                        <td class="col-ref">Xxx02</td>
                        <td>25/09/2025</td>
                        <td>ABC</td>
                        <td><span class="status-pending-red">Pending</span></td>
                        <td class="col-action">
                            <button class="confirm-complete-btn">Confirm Complete</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>