package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class  Posts_Chapter07{

	public static void main(String[] args) {

		Connection conn = null;
		PreparedStatement stmt = null;

		String[][] postsList = {
				{"1003","2023-02-08", "昨日の夜は徹夜でした・・", "13"},
				{"1002", "2023-02-08", "お疲れ様です！", "12"},
				{"1003", "2023-02-09", "今日も頑張ります！", "18"},
				{"1001", "2023-02-09", "無理は禁物ですよ！", "17"},
				{"1002", "2023-02-10", "明日から連休ですね！", "20"}
		};


		try {
			// (1)データベースに接続する getConnection()メソッド
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/challange_java",
					"root",
					"？？？？？？"
					);
			System.out.println("データベース接続成功：" + conn);

			System.out.println("レコード追加を実行します");

			// (2) SQLクエリを準備する createStatement()メソッド
			String sqlInsert ="""
					INSERT INTO posts (user_id, posted_at, post_content, likes) 
					VALUES (?, ?, ?, ?);
					""";
			stmt = conn.prepareStatement(sqlInsert);

			int cnt = 0;
			for (int i = 0; i < postsList.length; i++) {

				stmt.setInt(1, Integer.parseInt(postsList[i][0]));
				stmt.setString(2, postsList[i][1]);
				stmt.setString(3, postsList[i][2]);
				stmt.setInt(4, Integer.parseInt(postsList[i][3]));

				// (3)投稿データを追加する　executeUpdate()メソッド
				stmt.executeUpdate();

				cnt++;
			}
			System.out.println(cnt + "件のレコードが追加されました");


			// (4)投稿データを検索する executeQuery()メソッド
			// (5)検索結果を抽出・表示する ResultSetクラス
			String sqlSelect ="SELECT posted_at, post_content, likes FROM posts WHERE user_id = 1002";
			ResultSet result = stmt.executeQuery(sqlSelect);

			System.out.println("ユーザーIDが1002のレコードを検索しました");
			
			while(result.next()) {
				Date postedAt = result.getDate("posted_at");
				String postContent = result.getString("post_content");
				int likes = result.getInt("likes");
				System.out.println(result.getRow() + "件目：投稿日時=" + postedAt +
						"／投稿内容=" + postContent + "／いいね数=" + likes);		 
			}


		} catch (SQLException e) {
			System.out.println("エラー：" + e.getMessage());
		} finally {

			//オブジェクトの解放
			if (stmt != null) {
				try {stmt.close();} catch(SQLException ignore) {}
			}
			
			if (conn != null) {
				try {conn.close();} catch(SQLException ignore) {}
			}
			
		}

	}
}
