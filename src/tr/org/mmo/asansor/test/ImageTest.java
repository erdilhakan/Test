package tr.org.mmo.asansor.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;


import org.apache.commons.dbutils.DbUtils;



import tr.org.mmo.asansor.dao.DAOBase;

public class ImageTest {
	
	

	

	private static Connection con=null;
	public  void insertPDF(String filename) {
        int len;
        String query;
        PreparedStatement pstmt;
        
        try {
        	con=DAOBase.instance().getConnection();
    		con.setAutoCommit(false);
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            len = (int)file.length();
            query = ("insert into test.image(name,iname) VALUES(?,?)");
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,file.getName());
            
            
            //method to insert a stream of bytes
            pstmt.setBinaryStream(2, fis, len); 
            pstmt.executeUpdate();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	DbUtils.closeQuietly(con);
        }
    }
    
    public void getPDFData() {
        
        byte[] fileBytes;
        String query;
       PreparedStatement ps=null;
       String path=((ServletContext) FacesContext.getCurrentInstance()
               .getExternalContext().getContext()).getRealPath("\\resources\\raporlar\\");
        try {
        	
        	con=DAOBase.instance().getConnection();
    		con.setAutoCommit(false);
            query = 
              "select iname from test.image where id=?";
         ps=con.prepareStatement(query);
         ps.setInt(1,2);
          
          
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                fileBytes = rs.getBytes(1);
               
                OutputStream targetFile=  new FileOutputStream(
                        path+"\\rapor.pdf");
                targetFile.write(fileBytes);
                targetFile.close();
               
            }
           
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
        	DbUtils.closeQuietly(con);
        }
       // return fos;
    }

}
