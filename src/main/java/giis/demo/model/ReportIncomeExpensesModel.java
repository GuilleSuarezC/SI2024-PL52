package giis.demo.model;

import java.util.List;
import giis.demo.util.Database;
import giis.demo.util.Util;
import giis.demo.util.ApplicationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class ReportIncomeExpensesModel {
	private Database db = new Database();
    
	//Consultas SQL
	/*
	private static final String SQL_GET_ACTIVITIES = "WITH BalanceSummary AS (\r\n"
			+ "    SELECT\r\n"
			+ "        e.event_id,\r\n"
			+ "        e.event_name,\r\n"
			+ "        e.event_date,\r\n"
			+ "        e.event_endDate,\r\n"
			+ "        e.event_status,\r\n"
			+ "        SUM(CASE WHEN b.amount > 0 THEN b.amount ELSE 0 END) AS total_income,\r\n"
			+ "        SUM(CASE WHEN b.amount < 0 THEN b.amount ELSE 0 END) AS total_expenses,\r\n"
			+ "        SUM(CASE WHEN b.amount > 0 AND b.balance_status = 'Paid' THEN b.amount ELSE 0 END) AS paid_income,\r\n"
			+ "        SUM(CASE WHEN b.amount < 0 AND b.balance_status = 'Paid' THEN b.amount ELSE 0 END) AS paid_expenses\r\n"
			+ "    FROM Event e\r\n"
			+ "    LEFT JOIN Balance b ON e.event_id = b.event_id\r\n"
			+ "    WHERE e.event_date BETWEEN ? AND ? -- Intervalo de fechas\r\n"
			+ "    AND (e.event_status = ? OR ? = 'All') -- Filtrar por estado\r\n"
			+ "    GROUP BY e.event_id, e.event_name, e.event_date, e.event_endDate, e.event_status\r\n"
			+ ")\r\n"
			+ "SELECT \r\n"
			+ "    event_id,\r\n"
			+ "    event_name,\r\n"
			+ "    event_date,\r\n"
			+ "    event_endDate,\r\n"
			+ "    event_status,\r\n"
			+ "    total_income,\r\n"
			+ "    paid_income,\r\n"
			+ "    total_expenses,\r\n"
			+ "    paid_expenses,\r\n"
			+ "    (total_income + total_expenses) AS estimated_balance,\r\n"
			+ "    (paid_income + paid_expenses) AS paid_balance\r\n"
			+ "FROM BalanceSummary";
	*/
	private static final String SQL_GET_ACTIVITIES = 
		    "WITH BalanceSummary AS (\r\n" +
		    "    SELECT\r\n" +
		    "        e.event_id,\r\n" +
		    "        e.event_name,\r\n" +
		    "        e.event_date,\r\n" +
		    "        e.event_endDate,\r\n" +
		    "        e.event_status,\r\n" +
		    "        SUM(CASE \r\n" +
		    "            WHEN b.amount > 0 AND s.sponsorship_id IS NOT NULL THEN b.amount \r\n" +
		    "            ELSE 0 \r\n" +
		    "        END) AS total_sponsorship_income,\r\n" +
		    "        SUM(CASE \r\n" +
		    "            WHEN b.amount > 0 AND s.sponsorship_id IS NULL THEN b.amount \r\n" +
		    "            ELSE 0 \r\n" +
		    "        END) AS total_other_income,\r\n" +
		    "        SUM(CASE \r\n" + 
		    "            WHEN b.amount > 0 AND s.sponsorship_id IS NOT NULL THEN IFNULL(m.movement_paid, 0)\r\n" + 
		    "            ELSE 0\r\n" + 
		    "        END) AS sponsorship_income_paid," +
		    "        SUM(CASE \r\n" +
		    "            WHEN b.amount > 0 AND s.sponsorship_id IS NULL THEN IFNULL(m.movement_paid, 0) \r\n" +
		    "            ELSE 0 \r\n" +
		    "        END) AS other_income_paid,\r\n" +
		    "        SUM(CASE \r\n" +
		    "            WHEN b.amount < 0 THEN b.amount \r\n" +
		    "            ELSE 0 \r\n" +
		    "        END) AS total_expenses,\r\n" +
		    "        SUM(CASE \r\n" +
		    "            WHEN b.amount < 0 THEN IFNULL(m.movement_paid, 0) * -1 \r\n" +
		    "            ELSE 0 \r\n" +
		    "        END) AS paid_expenses\r\n" +
		    "    FROM Event e\r\n" +
		    "    LEFT JOIN Balance b ON e.event_id = b.event_id\r\n" +
		    "    LEFT JOIN Sponsorship s ON b.balance_id = s.balance_id\r\n" +
		    "	 LEFT JOIN (\r\n" +
		    " 	 	SELECT balance_id, SUM(movement_amount) as movement_paid\r\n" +
		    " 		FROM Movement GROUP BY balance_id) m ON b.balance_id = m.balance_id\r\n" +
		    "    WHERE e.event_date BETWEEN ? AND ?\r\n" +
		    "    AND (e.event_status = ? OR ? = 'All')\r\n" +
		    "    GROUP BY e.event_id, e.event_name, e.event_date, e.event_endDate, e.event_status\r\n" +
		    ")\r\n" +
		    "SELECT \r\n" +
		    "    event_id,\r\n" +
		    "    event_name,\r\n" +
		    "    event_date,\r\n" +
		    "    event_endDate,\r\n" +
		    "    event_status,\r\n" +
		    "    total_sponsorship_income,\r\n" +
		    "    total_other_income,\r\n" +
		    "    sponsorship_income_paid,\r\n" +
		    "    other_income_paid,\r\n" +
		    "    total_expenses,\r\n" +
		    "    paid_expenses,\r\n" +
		    "    (total_sponsorship_income + total_other_income + total_expenses) AS estimated_balance,\r\n" +
		    "    (sponsorship_income_paid + other_income_paid + paid_expenses) AS paid_balance\r\n" +
		    "FROM BalanceSummary";



	public List<ReportDTO>  getActivities(String startDate, String endDate, String eventStatus) {
    	return db.executeQueryPojo(ReportDTO.class, SQL_GET_ACTIVITIES, startDate, endDate, eventStatus, eventStatus);
    }
	
    private void validateNotNull(Object obj, String message) {
    	if (obj== null || obj.toString().trim().isEmpty()) {
    		throw new ApplicationException(message);
    	}
    }
}