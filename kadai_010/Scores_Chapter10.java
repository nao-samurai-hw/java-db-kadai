package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Scores_Chapter10 {

	public static void main(String[] args) {

		Connection conn = null;
		Statement stmt = null;

		try {
			// (1)データベースに接続する　getConnection()メソッド
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/challange_java",
					"root",
					"ぱすわーど"
					);

			System.out.println("データベース接続成功：" + conn);

			System.out.println("レコード更新を実行します");

			// (2) SQLクエリを準備するcreateStatement()メソッド
			stmt = conn.createStatement();
			String sqlUpdate = "UPDATE scores SET score_math = 95, score_english = 80 WHERE id = 5;";

			// (3)点数データを更新するexecuteUpdate()メソッド
			int rowCnt = stmt.executeUpdate(sqlUpdate);
			System.out.println(rowCnt + "件のレコードが更新されました");

			/* 
			 * (4)点数順に並べるexecuteQuery()メソッド
			 * (5)並べ替え結果を表示するResultSetクラス
			 */			
			String sqlSort = "SELECT * FROM scores ORDER BY score_math DESC, score_english DESC;";
			ResultSet result = stmt.executeQuery(sqlSort);

			System.out.println("数学・英語の点数が高い順に並べ替えました");
			
			while (result.next()) {
				int studentId = result.getInt("id");
				String studentName = result.getString("name");
				int scoreMath = result.getInt("score_math");
				int scoreEnglish = result.getInt("score_english");
				
				System.out.println(result.getRow() + "件目："
						+ "生徒ID=" + studentId
						+ "／氏名=" + studentName
						+ "／数学=" + scoreMath
						+ "／英語=" + scoreEnglish);
			}
			

		} catch (SQLException e) {
			System.out.println("エラー：" + e.getMessage());
		} finally {
			// データ領域を解放する
			if (stmt != null) {
				try { stmt.close();} catch (SQLException ignore) {}
			}

			if (conn != null) {
				try {stmt.close();} catch (SQLException ignore) {}
			}

		}

	}

}
