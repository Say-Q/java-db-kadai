package kadai_10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Scores_Chapter10 {

	public static void main(String[] args) {

		Connection con = null;
		Statement statement = null;

		try {
			//データベースに接続
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"My1984SQL0702kubo"
			);

			System.out.println("データベース接続成功");

			//SQLクエリを準備
			statement = con.createStatement();
			String sql = "UPDATE scores SET score_math = 95,score_english = 80 WHERE id = 5;";

			//SQLクエリを実行（DBMSに送信）
			System.out.println("レコード更新を実行します");

			int rowCnt = statement.executeUpdate(sql);
			System.out.println(rowCnt + "件のレコードが更新されました");

			//SQLクエリを準備
			String selectsql = "SELECT * FROM scores ORDER BY score_math DESC, score_english DESC;";
		
			//SQLクエリを実行
			System.out.println("数学・英語の点数が高い順に並べ替えました");
			ResultSet resule = statement.executeQuery(selectsql);
			
			//SQLクエリの実行結果を抽出
			while ( resule.next() ) {
				int id = resule.getInt("id");
				String name = resule.getString("name");
				int score_math = resule.getInt("score_math");
				int score_english = resule.getInt("score_english");
				System.out.println(resule.getRow() + "件目：生徒ID=" + id +"／氏名=" + name
						           + "／数学=" + score_math + "／英語=" + score_english); 
			}

		} catch (SQLException e) {

			System.out.println("エラー発生：" + e.getMessage());

		} finally {
			//使用したオブジェクトを解放
			if (statement != null) {
				try { statement.close(); } catch (SQLException ignore) {}
			}
			if (con != null) {
				try { con.close(); } catch (SQLException ignore) {}
			}
		}

	}
}
