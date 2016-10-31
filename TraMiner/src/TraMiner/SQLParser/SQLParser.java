package TraMiner.SQLParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.List;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;



public class SQLParser {
	 public static String readTxtFile(String filePath){
	        try {
	                String encoding="GBK";
	                File file=new File(filePath);
	                if(file.isFile() && file.exists()){ 
	                    InputStreamReader read = new InputStreamReader(
	                    new FileInputStream(file),encoding);
	                    BufferedReader bufferedReader = new BufferedReader(read);
	                    String lineTxt = null;
	                    while((lineTxt = bufferedReader.readLine()) != null){
	                       return lineTxt;
	                    }
	                    read.close();
	        }else{
	            System.out.println("can not find the file");
	        }
	        } catch (Exception e) {
	            System.out.println("can not read the file");
	            e.printStackTrace();
	        }
			return null;
	     
	    }
	 public void appendStr(String str){
		 
	 }
	public static void main(String[] args) {
		String sql="";
		sql=SQLParser.readTxtFile("c:/input.txt");
		if(sql!=null&&!sql.equals("")){
			StringBuffer sbuf=new StringBuffer();
			Statement stat;
			try {
				stat = new CCJSqlParserManager().parse(new StringReader(sql));
				if(sql.toLowerCase().startsWith("select")){
					Select select = (Select) stat;
					PlainSelect ps=(PlainSelect) select.getSelectBody();
					Expression where = (ps).getWhere();
					sbuf.append("\r\n").append("Statement - [Select]");
					sbuf.append("\r\n").append("   Select - [Select]");
					List selectItems=ps.getSelectItems();
					sbuf.append("\r\n").append("	   SelectItemList - "
							+selectItems.getClass().getSimpleName()+" - "+selectItems);
					for (int i = 0; i < selectItems.size(); i++) {
						sbuf.append("\r\n").append("	     item - "+selectItems.get(i));
					}
					
					sbuf.append("\r\n").append("	   FromItem - [table] -"+ps.getFromItem());
					List JoinsList=ps.getJoins();
					if(JoinsList!=null){
						sbuf.append("\r\n").append("	   JoinsList - "
								+JoinsList.getClass().getSimpleName()+" - "+JoinsList);
						for (int i = 0; i < JoinsList.size(); i++) {
							sbuf.append("\r\n").append("	     join - "+JoinsList.get(i));
						}
					}
					if(where!=null){
						sbuf.append("\r\n").append("	   WhereClause - "
								+where.getClass().getSimpleName()+" - "+where);
					}
					
					List groupList=ps.getGroupByColumnReferences();
					if(groupList!=null){
						sbuf.append("\r\n").append("	   GroupByColumnReferences - "
								+groupList.getClass().getSimpleName()+" - "+groupList);
						for (int i = 0; i < groupList.size(); i++) {
							sbuf.append("\r\n").append("	     group - "+groupList.get(i));
						}
					}
					
					if(ps.getHaving()!=null){
						sbuf.append("\r\n").append("	   Having - "+ps.getHaving().getClass().getSimpleName()+" - "+ps.getHaving());
					}
					
					List orderbylist=ps.getOrderByElements();
					if(orderbylist!=null){
						sbuf.append("\r\n").append("	   OrderByElements - "
								+orderbylist.getClass().getSimpleName()+" - "+orderbylist);
						for (int i = 0; i < orderbylist.size(); i++) {
							sbuf.append("\r\n").append("	     order by - "+orderbylist.get(i));
						}
					}
				}else if(sql.toLowerCase().startsWith("delete")){
					Delete delete=(Delete) stat;
					sbuf.append("\r\n").append("Statement - [Delete]");
					sbuf.append("\r\n").append("   Delete - [Delete]");
					sbuf.append("\r\n").append("	   table - "+delete.getTable());
					Expression where = delete.getWhere();
					if(where!=null){
						sbuf.append("\r\n").append("	   WhereClause - "
								+where.getClass().getSimpleName()+" - "+where);
					}
				}else if(sql.toLowerCase().startsWith("update")){
					Update update=(Update) stat;
					sbuf.append("\r\n").append("Statement - [Update]");
					sbuf.append("\r\n").append("   Delete - [Update]");
					sbuf.append("\r\n").append("	   table - "+update.getTable());
					List Columns=update.getColumns();
					if(Columns!=null){
						sbuf.append("\r\n").append("	   Columns - "
								+Columns.getClass().getSimpleName()+" - "+Columns);
						for (int i = 0; i < Columns.size(); i++) {
							sbuf.append("\r\n").append("	     column - "+Columns.get(i));
						}
					}
					
					List Expressions=update.getExpressions();
					if(Expressions!=null){
						sbuf.append("\r\n").append("	   Expressions - "
								+Expressions.getClass().getSimpleName()+" - "+Expressions);
						for (int i = 0; i < Expressions.size(); i++) {
							sbuf.append("\r\n").append("	     expressions - "+Expressions.get(i));
						}
					}
					 
					Expression where = update.getWhere();
					if(where!=null){
						sbuf.append("\r\n").append("	   WhereClause - "
								+where.getClass().getSimpleName()+" - "+where);
					}
				}else if(sql.toLowerCase().startsWith("insert")){
					Insert insert=(Insert) stat;
					sbuf.append("\r\n").append("Statement - [Insert]");
					sbuf.append("\r\n").append("   Insert - [Insert]");
					sbuf.append("\r\n").append("	   table - "+insert.getTable());
					//获取插入列
					List Columns=insert.getColumns();
					if(Columns!=null){
						sbuf.append("\r\n").append("	   Columns - "
								+Columns.getClass().getSimpleName()+" - "+Columns);
						for (int i = 0; i < Columns.size(); i++) {
							sbuf.append("\r\n").append("	     column - "+Columns.get(i));
						}
					}
					ItemsList insertlist=insert.getItemsList();
					if(insertlist!=null){
						sbuf.append("\r\n").append("	   itemslist - "
								+insertlist.getClass().getSimpleName()+" - "+insertlist);
					}
				}
				
				System.out.println(sbuf.toString());
			} catch (Exception e) {
				//get the exception information
				sbuf.append("SQL is wrong");
				e.printStackTrace();
			}
			FileOutputStream fs = null;
			try {
				//read from file
				fs = new FileOutputStream(new File("c:\\output.txt"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			PrintStream p = new PrintStream(fs);
			p.println(sbuf.toString());
			p.close();
			
		}else{
			FileOutputStream fs = null;
			try {
				//output to file
				fs = new FileOutputStream(new File("c:\\output.txt"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			PrintStream p = new PrintStream(fs);
			p.println("can not find SQL query");
			p.close();
		}
		
		
		
		
		
	}
}
