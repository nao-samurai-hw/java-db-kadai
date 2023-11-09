package kadai_004;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Employees_Chapter04 {

	public static void main(String[] args) {

		Connection conn = null;
		Statement stmt = null;

		try {
			// 1.データベースに接続
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/challange_java",
					"root",
					"パスワード"
					);

			System.out.println("データベース接続成功：" + conn);

			// 2.SQLクエリの準備
			stmt = conn.createStatement();
			String sql = """
					CREATE TABLE employees(
					id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
					name VARCHAR(60) NOT NULL,
					email VARCHAR(255) NOT NULL,
					age INT(11),
					address VARCHAR(255)
					);
					""";

			// 3.SQLクエリを実行
			int row = stmt.executeUpdate(sql); // 更新されたレコードの行
			System.out.println("社員テーブルを作成しました:更新レコード数=" + row);

			// 接続に失敗した場合
		} catch (SQLException e) { 
			System.out.println("エラー：" + e.getMessage());
		} finally {
			// 4.実行後の処理 (close()でデータ領域を解放)
			if (stmt != null) {
				try { stmt.close();
				} catch (SQLException ignore) {}
			}
			if (conn != null) {
				try { conn.close();
				} catch (SQLException ignore) {}
			}
		}
	}




}


