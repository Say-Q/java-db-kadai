package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Posts_Chapter07 {
	public static void main(String[] args) {
		
		Connection con = null;
		PreparedStatement statement = null;
		
		//ユーザーリスト
		String[][] list = {
				{"1003", "2023-02-08", "昨日の夜は徹夜でした・・", "13"},
				{"1002", "2023-02-08", "お疲れ様です！", "12"},
				{"1003", "2023-02-09", "今日も頑張ります！", "18"},
				{"1001", "2023-02-09", "無理は禁物ですよ！", "17"},
				{"1002", "2023-02-10", "明日から連休ですね！", "20"}
				
		};
		
		try {
			//データベースに接続
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"My1984SQL0702kubo"
					
			);
			
			System.out.println("データベース接続成功");
			
			//SQLクエリを準備
			String sql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES(?,?,?,?);";
			statement = con.prepareStatement(sql);
			
			System.out.println("レコード追加を実行します");
			//リストの1行目から順番に読み込む
			int rowCnt = 0;
			for (int i = 0; i < list.length; i++) {
				//SQLクエリの「?」部分をリストのデータに書き換え
				statement.setString(1, list[i][0]);
				statement.setString(2, list[i][1]);
				statement.setString(3, list[i][2]);
				statement.setString(4, list[i][3]);
				
				//SQLクエリを実行（DBMSに送信）
				rowCnt += statement.executeUpdate();
				
				}

			System.out.println(rowCnt + "件のレコードが追加されました");
				
			try (Statement  selectstatement =  con.createStatement()) {

				//SQLクエリを準備
				String selectsql = "SELECT posted_at,post_content,likes FROM posts WHERE user_id = 1002;";

				//SQQLクエリを実行（DBMSに送信）
				ResultSet result = selectstatement.executeQuery(selectsql);
				
				//SQLクエリの実行結果を抽出
				System.out.println("ユーザーIDが1002のレコードを検索しました");

				int selectCnt = 0; //
				
				while (result.next()) {
					String at = result.getString("posted_at");
					String content = result.getString("post_content");
					int likes = result.getInt("likes");
				
					selectCnt++;
					System.out.println(selectCnt + "件目：投稿日時=" + at + "／投稿内容=" + content + "／いいね数：" + likes);
				}

			} catch (SQLException e) { e.printStackTrace(); }
				
		} catch (SQLException e) {
		
			System.out.println("エラー発生：" + e.getMessage());

		} finally {
			//使用したオブジェクトを開放
			if( statement != null ) {
				try { statement.close(); } catch (SQLException ignore) {}
				
			}
			
			if( con != null ) {
				try { con.close(); } catch (SQLException ignore) {}
			}
		}
	}

}
