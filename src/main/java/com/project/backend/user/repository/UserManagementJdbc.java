package com.project.backend.user.repository;

import com.project.backend.user.entity.Schoolenum;
import com.project.backend.user.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserManagementJdbc {
    // MySQL 데이터베이스 연결 정보
    private static final String DB_URL = "jdbc:mysql://localhost:3306/meister";
    private static final String DB_USERNAME = "hajin";
    private static final String DB_PASSWORD = "1234";

    // 데이터베이스 연결을 위한 Connection 객체 반환
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    // 사용자 정보 저장
    public static void saveUser(User user) {
        String insertQuery = "INSERT INTO user_table (email, name, userid, userType, password, school_name, admission_year, approved_yn, last_login_time, mail_key, mail_auth, withdrawed, withdrawed_time, github_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getUserid());
            preparedStatement.setString(4, user.getUserType());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getSchoolName().name());
            preparedStatement.setInt(7, user.getSchoolYear());
            preparedStatement.setString(8, user.getApprovedYn());
            preparedStatement.setString(9, user.getLastLoginTime());
            preparedStatement.setString(10, user.getMailKey());
            preparedStatement.setString(11, user.getMailAuth());
            preparedStatement.setString(12, user.getWithdrawed_yn());
            preparedStatement.setString(13, user.getWithdrawed_time());
            preparedStatement.setString(14, user.getGithub_link());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " rows inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 사용자 정보 조회 (이메일을 통한 조회)
    public static User findUserByEmail(String email) {
        String selectQuery = "SELECT * FROM user_table WHERE email = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractUserFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static User findUserByUserIdx(int userIdx) {
        String selectQuery = "SELECT * FROM user_table WHERE useridx = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setInt(1, userIdx);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractUserFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // withdrawed 상태의 사용자 수 조회
    public static int countByWithdrawed(String withdrawed) {
        String countQuery = "SELECT COUNT(*) FROM user_table WHERE withdrawed = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(countQuery)) {

            preparedStatement.setString(1, withdrawed);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    // 사용자 정보 수정
    public static void updateUser(User user) {
        String updateQuery = "UPDATE user_table SET email = ?, name = ?, userid = ?, userType = ?, password = ?, school_name = ?, admission_year = ?, approved_yn = ?, last_login_time = ?, mail_key = ?, mail_auth = ?, withdrawed = ?, withdrawed_time = ?, github_id = ? " +
                "WHERE useridx = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getUserid());
            preparedStatement.setString(4, user.getUserType());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getSchoolName().name());
            preparedStatement.setInt(7, user.getSchoolYear());
            preparedStatement.setString(8, user.getApprovedYn());
            preparedStatement.setString(9, user.getLastLoginTime());
            preparedStatement.setString(10, user.getMailKey());
            preparedStatement.setString(11, user.getMailAuth());
            preparedStatement.setString(12, user.getWithdrawed_yn());
            preparedStatement.setString(13, user.getWithdrawed_time());
            preparedStatement.setString(14, user.getGithub_link());
            preparedStatement.setInt(15, user.getUserIdx());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " rows updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 사용자 정보 삭제
    public static void deleteUser(int userIdx) {
        String deleteQuery = "DELETE FROM user_table WHERE useridx = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setInt(1, userIdx);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " rows deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 모든 사용자 정보 조회 (페이징)
    public static List<User> getUsersByWithdrawed(String withdrawedYn, int offset, int limit) {
        String selectQuery = "SELECT * FROM user_table WHERE withdrawed = ? LIMIT ?, ?";

        List<User> users = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setString(1, withdrawedYn);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, limit);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = extractUserFromResultSet(resultSet);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    // ResultSet으로부터 User 객체 추출
    private static User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserIdx(resultSet.getInt("useridx"));
        user.setEmail(resultSet.getString("email"));
        user.setName(resultSet.getString("name"));
        user.setUserid(resultSet.getString("userid"));
        user.setUserType(resultSet.getString("userType"));
        user.setPassword(resultSet.getString("password"));
        user.setSchoolName(Schoolenum.valueOf(resultSet.getString("school")));
        user.setSchoolYear(resultSet.getInt("admissionYear"));
        user.setApprovedYn(resultSet.getString("approvedYn"));
        user.setLastLoginTime(resultSet.getString("lastLoginTime"));
        user.setMailKey(resultSet.getString("mailKey"));
        user.setMailAuth(resultSet.getString("mailAuth"));
        user.setWithdrawed_yn(resultSet.getString("withdrawed"));
        user.setWithdrawed_time(resultSet.getString("withdrawed_time"));
        user.setGithub_link(resultSet.getString("githubId"));

        return user;
    }
}
