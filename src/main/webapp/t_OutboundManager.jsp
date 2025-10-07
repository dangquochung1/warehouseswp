<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Outbound Management - Giao diện đơn giản</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="container">
        <header class="header">
            Outbound management
        </header>

        <div class="main-content">
            <aside class="sidebar">
                <div class="sidebar-item active">Dashboard</div>
                <div class="sidebar-item">Create outbound order</div>
            </aside>

            <section class="content-area">
    <div class="order-summary">
        <div class="summary-box">
            Total Order: ${totalOutboundNumber}
        </div>
        <div class="summary-box">
            Pending: ${totalPendingNumber}
        </div>
        <div class="summary-box">
            In Progress: ${totalInProgressNumber}
        </div>
        <div class="summary-box">
            Completed: ${totalCompletedNumber}
        </div>
    </div>

                <div class="controls">
                    <input type="text" placeholder="search order..." class="search-input">
                    <button class="filter-button">filter 1</button>
                    <button class="filter-button">filter 2</button>
                </div>

                <h2 class="list-heading">Outbound Order list</h2>
                <div class="order-list">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Create date</th>
                                <th>Create by</th>
                                <th>Status</th>
                                <th>Status</th>
                                <th class="col-action">Action</th> 
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>1001</td>
                                <td>2024-01-01</td>
                                <td>User A</td>
                                <td>Pending</td>
                                <td>Pending</td>
                                <td class="col-action">
                                    <div class="action-buttons-table">
                                        <button class="action-btn-mini view-btn">View</button>
                                        <button class="action-btn-mini update-btn">Update</button>
                                        <button class="action-btn-mini delete-btn">Delete</button>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>1002</td>
                                <td>2024-01-02</td>
                                <td>User B</td>
                                <td>Complete</td>
                                <td>Complete</td>
                                <td class="col-action">
                                    <div class="action-buttons-table">
                                        <button class="action-btn-mini view-btn">View</button>
                                        <button class="action-btn-mini update-btn">Update</button>
                                        <button class="action-btn-mini delete-btn">Delete</button>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </section>
        </div>
    </div>
</body>
</html>