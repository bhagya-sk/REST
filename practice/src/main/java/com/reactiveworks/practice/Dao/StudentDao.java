package com.reactiveworks.practice.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.reactiveworks.practice.db.DBUtil;
import com.reactiveworks.practice.db.eceptions.DBOperationFailureException;
import com.reactiveworks.practice.db.eceptions.DataBaseAccessException;
import com.reactiveworks.practice.db.eceptions.InvalidDBRecordFormatException;
import com.reactiveworks.practice.model.Student;

public class StudentDao {

	private static final String SELECT_QUERY = "SELECT * FROM student ;";

	public List<Student> getStudents()
			throws DataBaseAccessException, InvalidDBRecordFormatException, DBOperationFailureException {
		List<Student> studentsList = new ArrayList<Student>();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement statement = null;
		Student studentObj = null;
		try {

			connection = DBUtil.getdbconnection();
			statement = connection.prepareStatement(SELECT_QUERY);
			res = statement.executeQuery(SELECT_QUERY);
			while (res.next()) {
				studentObj = new Student();
				studentObj.setName(res.getString(2));
				studentObj.setPercentage(res.getDouble(3));
				studentObj.setRollNo(res.getInt(1));
				studentsList.add(studentObj);
			}

		} catch (SQLException exp) {

			throw new DataBaseAccessException("unable to access product database" + exp);
		} catch (NumberFormatException numFormatExp) {

			throw new InvalidDBRecordFormatException("format of product with id ", numFormatExp);
		} finally {

			DBUtil.cleanupdbresources(res, statement, connection);

		}

		return studentsList;

	}

	public Student getStudent(int rollNo)
			throws DataBaseAccessException, DBOperationFailureException, InvalidDBRecordFormatException {
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement statement = null;
		Student studentObj = null;
		try {

			connection = DBUtil.getdbconnection();
			statement = connection.prepareStatement("SELECT * FROM student WHERE rollNo=?");
			statement.setInt(1, rollNo);
			res = statement.executeQuery();
			studentObj = new Student();
			if (res.next()) {

				studentObj.setName(res.getString(2));
				studentObj.setPercentage(res.getDouble(3));
				studentObj.setRollNo(res.getInt(1));
			}

		} catch (SQLException exp) {

			throw new DataBaseAccessException("unable to access product database", exp);
		} catch (NumberFormatException numFormatExp) {

			throw new InvalidDBRecordFormatException("format of product with id ", numFormatExp);
		} finally {

			DBUtil.cleanupdbresources(res, statement, connection);

		}

		return studentObj;
	}

	public void deleteStudent(int rollNo)
			throws DataBaseAccessException, DBOperationFailureException, InvalidDBRecordFormatException {
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {

			connection = DBUtil.getdbconnection();
			statement = connection.prepareStatement("DELETE FROM student WHERE rollNo=?");
			statement.setInt(1, rollNo);
			statement.executeUpdate();

		} catch (SQLException exp) {

			throw new DataBaseAccessException("unable to access product database" + exp);
		} catch (NumberFormatException numFormatExp) {

			throw new InvalidDBRecordFormatException("format of product with id ", numFormatExp);
		} finally {

			DBUtil.cleanupdbresources(res, statement, connection);

		}

	}

	public void updateStudent(Student student)
			throws DataBaseAccessException, DBOperationFailureException, InvalidDBRecordFormatException {
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {

			connection = DBUtil.getdbconnection();
			statement = connection.prepareStatement("UPDATE student SET percentage=? WHERE rollNo=?;");
			statement.setDouble(1, student.getPercentage());
			statement.setInt(2, student.getRollNo());
			statement.executeUpdate();
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			DBUtil.cleanupdbresources(res, statement, connection);

		}

	}

	public void createStudent(Student student) throws DataBaseAccessException, InvalidDBRecordFormatException {
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {

			connection = DBUtil.getdbconnection();
			statement = connection.prepareStatement("INSERT INTO student VALUES (?,?,?);");
			statement.setInt(1, student.getRollNo());
			statement.setString(2, student.getName());
			statement.setDouble(3, student.getPercentage());
			statement.executeUpdate();

		} catch (SQLException | DBOperationFailureException e) {
			e.printStackTrace();
		}  finally {

			DBUtil.cleanupdbresources(res, statement, connection);

		}

	}
	
	public void createStudents(List<Student> students) throws DataBaseAccessException, InvalidDBRecordFormatException {
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {

			connection = DBUtil.getdbconnection();
			statement = connection.prepareStatement("INSERT INTO student VALUES (?,?,?);");
			for(int index=0;index<students.size();index++) {
				statement.setInt(1, students.get(index).getRollNo());
				statement.setString(2, students.get(index).getName());
				statement.setDouble(3, students.get(index).getPercentage());
				statement.addBatch();
			}
			statement.executeBatch();

		} catch (SQLException | DBOperationFailureException e) {
			e.printStackTrace();
		}  finally {

			DBUtil.cleanupdbresources(res, statement, connection);

		}

	}

}
